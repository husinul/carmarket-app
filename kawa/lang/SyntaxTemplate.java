package kawa.lang;

import gnu.expr.Compilation;
import gnu.kawa.xml.ElementType;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.IdentityHashMap;
import java.util.Vector;

public class SyntaxTemplate implements Externalizable {
    static final int BUILD_CONS = 1;
    static final int BUILD_DOTS = 5;
    static final int BUILD_LIST1 = 8;
    static final int BUILD_LITERAL = 4;
    static final int BUILD_MISC = 0;
    static final int BUILD_NIL = 16;
    static final int BUILD_SYNTAX = 24;
    static final int BUILD_VAR = 2;
    static final int BUILD_VAR_CAR = 3;
    static final int BUILD_VECTOR = 40;
    static final int BUILD_WIDE = 7;
    static final String dots3 = "...";
    Object[] literal_values;
    int max_nesting;
    String patternNesting;
    String template_program;

    protected SyntaxTemplate() {
    }

    public SyntaxTemplate(String patternNesting, String template_program, Object[] literal_values, int max_nesting) {
        this.patternNesting = patternNesting;
        this.template_program = template_program;
        this.literal_values = literal_values;
        this.max_nesting = max_nesting;
    }

    public SyntaxTemplate(Object template, SyntaxForm syntax, Translator tr) {
        String stringBuffer = (tr == null || tr.patternScope == null) ? ElementType.MATCH_ANY_LOCALNAME : tr.patternScope.patternNesting.toString();
        this.patternNesting = stringBuffer;
        StringBuffer program = new StringBuffer();
        Vector literals_vector = new Vector();
        convert_template(template, syntax, program, BUILD_MISC, literals_vector, new IdentityHashMap(), false, tr);
        this.template_program = program.toString();
        this.literal_values = new Object[literals_vector.size()];
        literals_vector.copyInto(this.literal_values);
    }

    public int convert_template(Object form, SyntaxForm syntax, StringBuffer template_program, int nesting, Vector literals_vector, Object seen, boolean isVector, Translator tr) {
        String form2;
        int i;
        while (form2 instanceof SyntaxForm) {
            syntax = (SyntaxForm) form2;
            form2 = syntax.getDatum();
        }
        if ((form2 instanceof Pair) || (form2 instanceof FVector)) {
            IdentityHashMap seen_map = (IdentityHashMap) seen;
            if (seen_map.containsKey(form2)) {
                tr.syntaxError("self-referential (cyclic) syntax template");
                return -2;
            }
            seen_map.put(form2, form2);
        }
        if (form2 instanceof Pair) {
            Pair pair = (Pair) form2;
            int ret_cdr = -2;
            int save_pc = template_program.length();
            Object car = pair.getCar();
            if (tr.matches(car, dots3)) {
                Pair cdr = Translator.stripSyntax(pair.getCdr());
                if (cdr instanceof Pair) {
                    Pair cdr_pair = cdr;
                    if (cdr_pair.getCar() == dots3 && cdr_pair.getCdr() == LList.Empty) {
                        form2 = dots3;
                    }
                }
            }
            int save_literals = literals_vector.size();
            template_program.append('\b');
            int num_dots3 = BUILD_MISC;
            LList rest = pair.getCdr();
            while (rest instanceof Pair) {
                Pair p = (Pair) rest;
                if (!tr.matches(p.getCar(), dots3)) {
                    break;
                }
                num_dots3 += BUILD_CONS;
                rest = p.getCdr();
                template_program.append('\u0005');
            }
            int ret_car = convert_template(car, syntax, template_program, nesting + num_dots3, literals_vector, seen, false, tr);
            if (rest != LList.Empty) {
                template_program.setCharAt(save_pc, (char) ((((template_program.length() - save_pc) - 1) << BUILD_VAR_CAR) + BUILD_CONS));
                ret_cdr = convert_template(rest, syntax, template_program, nesting, literals_vector, seen, isVector, tr);
            }
            if (num_dots3 > 0) {
                if (ret_car < 0) {
                    tr.syntaxError("... follows template with no suitably-nested pattern variable");
                }
                int i2 = num_dots3;
                while (true) {
                    i2--;
                    if (i2 < 0) {
                        break;
                    }
                    template_program.setCharAt((save_pc + i2) + BUILD_CONS, (char) ((ret_car << BUILD_VAR_CAR) + BUILD_DOTS));
                    int n = nesting + num_dots3;
                    if (n >= this.max_nesting) {
                        this.max_nesting = n;
                    }
                }
            }
            if (ret_car >= 0) {
                return ret_car;
            }
            if (ret_cdr >= 0) {
                return ret_cdr;
            }
            if (ret_car == -1 || ret_cdr == -1) {
                return -1;
            }
            if (isVector) {
                return -2;
            }
            literals_vector.setSize(save_literals);
            template_program.setLength(save_pc);
        } else if (form2 instanceof FVector) {
            template_program.append('(');
            return convert_template(LList.makeList((FVector) form2), syntax, template_program, nesting, literals_vector, seen, true, tr);
        } else if (form2 == LList.Empty) {
            template_program.append('\u0010');
            return -2;
        } else if (!(!(form2 instanceof Symbol) || tr == null || tr.patternScope == null)) {
            int pattern_var_num = indexOf(tr.patternScope.pattern_names, form2);
            if (pattern_var_num >= 0) {
                int var_nesting = this.patternNesting.charAt(pattern_var_num);
                int op = (var_nesting & BUILD_CONS) != 0 ? BUILD_VAR_CAR : BUILD_VAR;
                var_nesting >>= BUILD_CONS;
                if (var_nesting > nesting) {
                    tr.syntaxError("inconsistent ... nesting of " + form2);
                }
                template_program.append((char) ((pattern_var_num * BUILD_LIST1) + op));
                if (var_nesting != nesting) {
                    pattern_var_num = -1;
                }
                return pattern_var_num;
            }
        }
        int literals_index = indexOf(literals_vector, form2);
        if (literals_index < 0) {
            literals_index = literals_vector.size();
            literals_vector.addElement(form2);
        }
        if (form2 instanceof Symbol) {
            tr.noteAccess(form2, tr.currentScope());
        }
        if (!((form2 instanceof SyntaxForm) || form2 == dots3)) {
            template_program.append('\u0018');
        }
        template_program.append((char) ((literals_index * BUILD_LIST1) + BUILD_LITERAL));
        if (form2 == dots3) {
            i = -1;
        } else {
            i = -2;
        }
        return i;
    }

    static int indexOf(Vector vec, Object elem) {
        int len = vec.size();
        for (int i = BUILD_MISC; i < len; i += BUILD_CONS) {
            if (vec.elementAt(i) == elem) {
                return i;
            }
        }
        return -1;
    }

    private int get_count(Object var, int nesting, int[] indexes) {
        for (int level = BUILD_MISC; level < nesting; level += BUILD_CONS) {
            var = ((Object[]) var)[indexes[level]];
        }
        return ((Object[]) var).length;
    }

    public Object execute(Object[] vars, TemplateScope templateScope) {
        return execute(BUILD_MISC, vars, BUILD_MISC, new int[this.max_nesting], (Translator) Compilation.getCurrent(), templateScope);
    }

    public Object execute(Object[] vars, Translator tr, TemplateScope templateScope) {
        return execute(BUILD_MISC, vars, BUILD_MISC, new int[this.max_nesting], tr, templateScope);
    }

    Object get_var(int var_num, Object[] vars, int[] indexes) {
        Object var = vars[var_num];
        if (var_num < this.patternNesting.length()) {
            int var_nesting = this.patternNesting.charAt(var_num) >> BUILD_CONS;
            for (int level = BUILD_MISC; level < var_nesting; level += BUILD_CONS) {
                var = ((Object[]) var)[indexes[level]];
            }
        }
        return var;
    }

    LList executeToList(int pc, Object[] vars, int nesting, int[] indexes, Translator tr, TemplateScope templateScope) {
        int pc0 = pc;
        int ch = this.template_program.charAt(pc);
        while ((ch & BUILD_WIDE) == BUILD_WIDE) {
            pc += BUILD_CONS;
            ch = ((ch - 7) << 13) | this.template_program.charAt(pc);
        }
        if ((ch & BUILD_WIDE) == BUILD_VAR_CAR) {
            Pair p = (Pair) get_var(ch >> BUILD_VAR_CAR, vars, indexes);
            return Translator.makePair(p, p.getCar(), LList.Empty);
        } else if ((ch & BUILD_WIDE) == BUILD_DOTS) {
            int count = get_count(vars[ch >> BUILD_VAR_CAR], nesting, indexes);
            LList result = LList.Empty;
            Pair last = null;
            pc += BUILD_CONS;
            for (int j = BUILD_MISC; j < count; j += BUILD_CONS) {
                indexes[nesting] = j;
                LList list = executeToList(pc, vars, nesting + BUILD_CONS, indexes, tr, templateScope);
                if (last == null) {
                    result = list;
                } else {
                    last.setCdrBackdoor(list);
                }
                while (list instanceof Pair) {
                    last = (Pair) list;
                    list = (LList) last.getCdr();
                }
            }
            return result;
        } else {
            return new Pair(execute(pc0, vars, nesting, indexes, tr, templateScope), LList.Empty);
        }
    }

    Object execute(int pc, Object[] vars, int nesting, int[] indexes, Translator tr, TemplateScope templateScope) {
        int ch = this.template_program.charAt(pc);
        while ((ch & BUILD_WIDE) == BUILD_WIDE) {
            pc += BUILD_CONS;
            ch = ((ch - 7) << 13) | this.template_program.charAt(pc);
        }
        if (ch == BUILD_LIST1) {
            return executeToList(pc + BUILD_CONS, vars, nesting, indexes, tr, templateScope);
        }
        if (ch == BUILD_NIL) {
            return LList.Empty;
        }
        if (ch == BUILD_SYNTAX) {
            LList v = execute(pc + BUILD_CONS, vars, nesting, indexes, tr, templateScope);
            if (v != LList.Empty) {
                return SyntaxForms.makeForm(v, templateScope);
            }
            return v;
        } else if ((ch & BUILD_WIDE) == BUILD_CONS) {
            Object obj;
            Pair p = null;
            LList lList = null;
            do {
                pc += BUILD_CONS;
                LList q = executeToList(pc, vars, nesting, indexes, tr, templateScope);
                if (p == null) {
                    lList = q;
                } else {
                    p.setCdrBackdoor(q);
                }
                while (q instanceof Pair) {
                    p = (Pair) q;
                    q = p.getCdr();
                }
                pc += ch >> BUILD_VAR_CAR;
                ch = this.template_program.charAt(pc);
            } while ((ch & BUILD_WIDE) == BUILD_CONS);
            Object cdr = execute(pc, vars, nesting, indexes, tr, templateScope);
            if (p == null) {
                obj = cdr;
            } else {
                p.setCdrBackdoor(cdr);
            }
            return obj;
        } else if (ch == BUILD_VECTOR) {
            return new FVector((LList) execute(pc + BUILD_CONS, vars, nesting, indexes, tr, templateScope));
        } else {
            if ((ch & BUILD_WIDE) == BUILD_LITERAL) {
                return this.literal_values[ch >> BUILD_VAR_CAR];
            } else if ((ch & 6) == BUILD_VAR) {
                Object var = get_var(ch >> BUILD_VAR_CAR, vars, indexes);
                if ((ch & BUILD_WIDE) == BUILD_VAR_CAR) {
                    var = ((Pair) var).getCar();
                }
                return var;
            } else {
                throw new Error("unknown template code: " + ch + " at " + pc);
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.patternNesting);
        out.writeObject(this.template_program);
        out.writeObject(this.literal_values);
        out.writeInt(this.max_nesting);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.patternNesting = (String) in.readObject();
        this.template_program = (String) in.readObject();
        this.literal_values = (Object[]) in.readObject();
        this.max_nesting = in.readInt();
    }
}
