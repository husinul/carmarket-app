package kawa.standard;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class object extends Syntax {
    public static final Keyword accessKeyword;
    public static final Keyword allocationKeyword;
    public static final Keyword classNameKeyword;
    static final Symbol coloncolon;
    static final Keyword initKeyword;
    static final Keyword init_formKeyword;
    static final Keyword init_keywordKeyword;
    static final Keyword init_valueKeyword;
    static final Keyword initformKeyword;
    public static final Keyword interfaceKeyword;
    public static final object objectSyntax;
    public static final Keyword throwsKeyword;
    static final Keyword typeKeyword;
    Lambda lambda;

    static {
        objectSyntax = new object(SchemeCompilation.lambda);
        objectSyntax.setName("object");
        accessKeyword = Keyword.make("access");
        classNameKeyword = Keyword.make("class-name");
        interfaceKeyword = Keyword.make("interface");
        throwsKeyword = Keyword.make("throws");
        typeKeyword = Keyword.make("type");
        allocationKeyword = Keyword.make("allocation");
        initKeyword = Keyword.make("init");
        initformKeyword = Keyword.make("initform");
        init_formKeyword = Keyword.make("init-form");
        init_valueKeyword = Keyword.make("init-value");
        init_keywordKeyword = Keyword.make("init-keyword");
        coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    }

    public object(Lambda lambda) {
        this.lambda = lambda;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (!(form.getCdr() instanceof Pair)) {
            return tr.syntaxError("missing superclass specification in object");
        }
        Pair pair = (Pair) form.getCdr();
        Expression oexp = new ObjectExp();
        if (pair.getCar() instanceof FString) {
            if (!(pair.getCdr() instanceof Pair)) {
                return tr.syntaxError("missing superclass specification after object class name");
            }
            pair = (Pair) pair.getCdr();
        }
        Object[] saved = scanClassDef(pair, oexp, tr);
        if (saved == null) {
            return oexp;
        }
        rewriteClassDef(saved, tr);
        return oexp;
    }

    public Object[] scanClassDef(Pair pair, ClassExp oexp, Translator tr) {
        tr.mustCompileHere();
        Object superlist = pair.getCar();
        Pair components = pair.getCdr();
        Object classNamePair = null;
        LambdaExp method_list = null;
        LambdaExp last_method = null;
        long classAccessFlag = 0;
        Vector vector = new Vector(20);
        Pair obj = components;
        while (obj != LList.Empty) {
            while (obj instanceof SyntaxForm) {
                obj = ((SyntaxForm) obj).getDatum();
            }
            if (obj instanceof Pair) {
                Object savedPos2;
                pair = obj;
                Pair pair_car = pair.getCar();
                while (pair_car instanceof SyntaxForm) {
                    pair_car = ((SyntaxForm) pair_car).getDatum();
                }
                obj = pair.getCdr();
                Object savedPos1 = tr.pushPositionOf(pair);
                if (pair_car instanceof Keyword) {
                    while (obj instanceof SyntaxForm) {
                        obj = ((SyntaxForm) obj).getDatum();
                    }
                    if (obj instanceof Pair) {
                        if (pair_car == interfaceKeyword) {
                            if (obj.getCar() == Boolean.FALSE) {
                                oexp.setFlag(ModuleExp.NONSTATIC_SPECIFIED);
                            } else {
                                oexp.setFlag(ModuleExp.STATIC_SPECIFIED);
                            }
                            obj = obj.getCdr();
                            tr.popPositionOf(savedPos1);
                        } else if (pair_car == classNameKeyword) {
                            if (classNamePair != null) {
                                tr.error('e', "duplicate class-name specifiers");
                            }
                            classNamePair = obj;
                            obj = obj.getCdr();
                            tr.popPositionOf(savedPos1);
                        } else if (pair_car == accessKeyword) {
                            savedPos2 = tr.pushPositionOf(obj);
                            classAccessFlag = addAccessFlags(obj.getCar(), classAccessFlag, Declaration.CLASS_ACCESS_FLAGS, "class", tr);
                            if (oexp.nameDecl == null) {
                                tr.error('e', "access specifier for anonymous class");
                            }
                            tr.popPositionOf(savedPos2);
                            obj = obj.getCdr();
                            tr.popPositionOf(savedPos1);
                        }
                    }
                }
                if (pair_car instanceof Pair) {
                    LList pair2 = pair_car;
                    pair_car = pair2.getCar();
                    while (pair_car instanceof SyntaxForm) {
                        pair_car = ((SyntaxForm) pair_car).getDatum();
                    }
                    if ((pair_car instanceof String) || (pair_car instanceof Symbol) || (pair_car instanceof Keyword)) {
                        Declaration decl;
                        LList lList;
                        Pair typePair = null;
                        Pair sname = pair_car;
                        int allocationFlag = 0;
                        long accessFlag = 0;
                        if (sname instanceof Keyword) {
                            decl = null;
                            lList = pair2;
                        } else {
                            decl = oexp.addDeclaration((Object) sname);
                            decl.setSimple(false);
                            decl.setFlag(1048576);
                            Translator.setLine(decl, (Object) pair2);
                            lList = pair2.getCdr();
                        }
                        int nKeywords = 0;
                        boolean seenInit = false;
                        Pair initPair = null;
                        while (lList != LList.Empty) {
                            Pair pair3 = lList;
                            while (pair3 instanceof SyntaxForm) {
                                pair3 = ((SyntaxForm) pair3).getDatum();
                            }
                            pair = pair3;
                            Pair keyPair = pair;
                            Symbol key = pair.getCar();
                            while (key instanceof SyntaxForm) {
                                key = ((SyntaxForm) key).getDatum();
                            }
                            savedPos2 = tr.pushPositionOf(pair);
                            lList = pair.getCdr();
                            if ((key == coloncolon || (key instanceof Keyword)) && (lList instanceof Pair)) {
                                nKeywords++;
                                pair = (Pair) lList;
                                Object value = pair.getCar();
                                lList = pair.getCdr();
                                if (key == coloncolon || key == typeKeyword) {
                                    typePair = pair;
                                } else if (key == allocationKeyword) {
                                    if (allocationFlag != 0) {
                                        tr.error('e', "duplicate allocation: specification");
                                    }
                                    if (matches(value, "class", tr) || matches(value, "static", tr)) {
                                        allocationFlag = LambdaExp.OVERLOADABLE_FIELD;
                                    } else if (matches(value, "instance", tr)) {
                                        allocationFlag = LambdaExp.ATTEMPT_INLINE;
                                    } else {
                                        tr.error('e', "unknown allocation kind '" + value + "'");
                                    }
                                } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                                    if (seenInit) {
                                        tr.error('e', "duplicate initialization");
                                    }
                                    seenInit = true;
                                    if (key != initKeyword) {
                                        initPair = pair;
                                    }
                                } else if (key == init_keywordKeyword) {
                                    if (!(value instanceof Keyword)) {
                                        tr.error('e', "invalid 'init-keyword' - not a keyword");
                                    } else if (((Keyword) value).getName() != sname.toString()) {
                                        tr.error('w', "init-keyword option ignored");
                                    }
                                } else if (key == accessKeyword) {
                                    Object savedPos3 = tr.pushPositionOf(pair);
                                    accessFlag = addAccessFlags(value, accessFlag, Declaration.FIELD_ACCESS_FLAGS, "field", tr);
                                    tr.popPositionOf(savedPos3);
                                } else {
                                    tr.error('w', "unknown slot keyword '" + key + "'");
                                }
                            } else if (lList != LList.Empty || seenInit) {
                                if ((lList instanceof Pair) && nKeywords == 0 && !seenInit && typePair == null) {
                                    pair = (Pair) lList;
                                    if (pair.getCdr() == LList.Empty) {
                                        typePair = keyPair;
                                        initPair = pair;
                                        lList = pair.getCdr();
                                        seenInit = true;
                                    }
                                }
                                lList = null;
                                break;
                            } else {
                                initPair = keyPair;
                                seenInit = true;
                            }
                            tr.popPositionOf(savedPos2);
                        }
                        if (lList != LList.Empty) {
                            tr.error('e', "invalid argument list for slot '" + sname + '\'' + " args:" + (lList == null ? "null" : lList.getClass().getName()));
                            return null;
                        }
                        if (seenInit) {
                            Object obj2 = decl != null ? decl : allocationFlag == LambdaExp.OVERLOADABLE_FIELD ? Boolean.TRUE : Boolean.FALSE;
                            vector.addElement(obj2);
                            vector.addElement(initPair);
                        }
                        if (decl != null) {
                            if (typePair != null) {
                                decl.setType(tr.exp2Type(typePair));
                            }
                            if (allocationFlag != 0) {
                                decl.setFlag((long) allocationFlag);
                            }
                            if (accessFlag != 0) {
                                decl.setFlag(accessFlag);
                            }
                            decl.setCanRead(true);
                            decl.setCanWrite(true);
                        } else if (!seenInit) {
                            tr.error('e', "missing field name");
                            return null;
                        }
                    } else if (pair_car instanceof Pair) {
                        Pair mpair = pair_car;
                        Object mname = mpair.getCar();
                        if ((mname instanceof String) || (mname instanceof Symbol)) {
                            LambdaExp lexp = new LambdaExp();
                            Translator.setLine(oexp.addMethod(lexp, mname), (Object) mpair);
                            if (last_method == null) {
                                method_list = lexp;
                            } else {
                                last_method.nextSibling = lexp;
                            }
                            last_method = lexp;
                        } else {
                            tr.error('e', "missing method name");
                            return null;
                        }
                    } else {
                        tr.error('e', "invalid field/method definition");
                    }
                    tr.popPositionOf(savedPos1);
                } else {
                    tr.error('e', "object member not a list");
                    return null;
                }
            }
            tr.error('e', "object member not a list");
            return null;
        }
        if (classAccessFlag != 0) {
            oexp.nameDecl.setFlag(classAccessFlag);
        }
        return new Object[]{oexp, components, vector, method_list, superlist, classNamePair};
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteClassDef(java.lang.Object[] r53, kawa.lang.Translator r54) {
        /*
        r52 = this;
        r6 = 0;
        r38 = r53[r6];
        r38 = (gnu.expr.ClassExp) r38;
        r6 = 1;
        r17 = r53[r6];
        r6 = 2;
        r27 = r53[r6];
        r27 = (java.util.Vector) r27;
        r6 = 3;
        r34 = r53[r6];
        r34 = (gnu.expr.LambdaExp) r34;
        r6 = 4;
        r46 = r53[r6];
        r6 = 5;
        r14 = r53[r6];
        r0 = r34;
        r1 = r38;
        r1.firstChild = r0;
        r36 = kawa.lang.Translator.listLength(r46);
        if (r36 >= 0) goto L_0x002f;
    L_0x0024:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r8 = "object superclass specification not a list";
        r0 = r54;
        r0.error(r6, r8);
        r36 = 0;
    L_0x002f:
        r0 = r36;
        r0 = new gnu.expr.Expression[r0];
        r48 = r0;
        r21 = 0;
    L_0x0037:
        r0 = r21;
        r1 = r36;
        if (r0 >= r1) goto L_0x0087;
    L_0x003d:
        r0 = r46;
        r6 = r0 instanceof kawa.lang.SyntaxForm;
        if (r6 == 0) goto L_0x004a;
    L_0x0043:
        r46 = (kawa.lang.SyntaxForm) r46;
        r46 = r46.getDatum();
        goto L_0x003d;
    L_0x004a:
        r47 = r46;
        r47 = (gnu.lists.Pair) r47;
        r6 = 0;
        r0 = r54;
        r1 = r47;
        r6 = r0.rewrite_car(r1, r6);
        r48[r21] = r6;
        r6 = r48[r21];
        r6 = r6 instanceof gnu.expr.ReferenceExp;
        if (r6 == 0) goto L_0x0080;
    L_0x005f:
        r6 = r48[r21];
        r6 = (gnu.expr.ReferenceExp) r6;
        r6 = r6.getBinding();
        r20 = gnu.expr.Declaration.followAliases(r6);
        if (r20 == 0) goto L_0x0080;
    L_0x006d:
        r49 = r20.getValue();
        r0 = r49;
        r6 = r0 instanceof gnu.expr.ClassExp;
        if (r6 == 0) goto L_0x0080;
    L_0x0077:
        r49 = (gnu.expr.ClassExp) r49;
        r6 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r0 = r49;
        r0.setFlag(r6);
    L_0x0080:
        r46 = r47.getCdr();
        r21 = r21 + 1;
        goto L_0x0037;
    L_0x0087:
        if (r14 == 0) goto L_0x00ad;
    L_0x0089:
        r6 = r14;
        r6 = (gnu.lists.Pair) r6;
        r8 = 0;
        r0 = r54;
        r13 = r0.rewrite_car(r6, r8);
        r16 = r13.valueIfConstant();
        r0 = r16;
        r0 = r0 instanceof java.lang.CharSequence;
        r29 = r0;
        if (r29 == 0) goto L_0x00e7;
    L_0x009f:
        r15 = r16.toString();
        r6 = r15.length();
        if (r6 <= 0) goto L_0x00e7;
    L_0x00a9:
        r0 = r38;
        r0.classNameSpecifier = r15;
    L_0x00ad:
        r0 = r48;
        r1 = r38;
        r1.supers = r0;
        r0 = r38;
        r1 = r54;
        r0.setTypes(r1);
        r31 = r27.size();
        r21 = 0;
    L_0x00c0:
        r0 = r21;
        r1 = r31;
        if (r0 >= r1) goto L_0x00fe;
    L_0x00c6:
        r6 = r21 + 1;
        r0 = r27;
        r22 = r0.elementAt(r6);
        if (r22 == 0) goto L_0x00e4;
    L_0x00d0:
        r0 = r27;
        r1 = r21;
        r6 = r0.elementAt(r1);
        r22 = (gnu.lists.Pair) r22;
        r8 = 0;
        r0 = r38;
        r1 = r22;
        r2 = r54;
        rewriteInit(r6, r0, r1, r2, r8);
    L_0x00e4:
        r21 = r21 + 2;
        goto L_0x00c0;
    L_0x00e7:
        r0 = r54;
        r43 = r0.pushPositionOf(r14);
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r8 = "class-name specifier must be a non-empty string literal";
        r0 = r54;
        r0.error(r6, r8);
        r0 = r54;
        r1 = r43;
        r0.popPositionOf(r1);
        goto L_0x00ad;
    L_0x00fe:
        r0 = r54;
        r1 = r38;
        r0.push(r1);
        r7 = r34;
        r25 = 0;
        r18 = 0;
        r37 = r17;
    L_0x010d:
        r6 = gnu.lists.LList.Empty;
        r0 = r37;
        if (r0 == r6) goto L_0x032b;
    L_0x0113:
        r0 = r37;
        r6 = r0 instanceof kawa.lang.SyntaxForm;
        if (r6 == 0) goto L_0x0122;
    L_0x0119:
        r18 = r37;
        r18 = (kawa.lang.SyntaxForm) r18;
        r37 = r18.getDatum();
        goto L_0x0113;
    L_0x0122:
        r39 = r37;
        r39 = (gnu.lists.Pair) r39;
        r0 = r54;
        r1 = r39;
        r44 = r0.pushPositionOf(r1);
        r40 = r39.getCar();
        r33 = r18;
    L_0x0134:
        r0 = r40;
        r6 = r0 instanceof kawa.lang.SyntaxForm;
        if (r6 == 0) goto L_0x0143;
    L_0x013a:
        r33 = r40;
        r33 = (kawa.lang.SyntaxForm) r33;
        r40 = r33.getDatum();
        goto L_0x0134;
    L_0x0143:
        r37 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        r0 = r40;
        r6 = r0 instanceof gnu.expr.Keyword;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0164;
    L_0x014d:
        r0 = r37;
        r6 = r0 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0164;
    L_0x0153:
        r0 = r37;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0211 }
        r6 = r0;
        r37 = r6.getCdr();	 Catch:{ all -> 0x0211 }
        r0 = r54;
        r1 = r44;
        r0.popPositionOf(r1);
        goto L_0x010d;
    L_0x0164:
        r0 = r40;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0211 }
        r39 = r0;
        r40 = r39.getCar();	 Catch:{ all -> 0x0211 }
        r32 = r33;
    L_0x0170:
        r0 = r40;
        r6 = r0 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0181;
    L_0x0176:
        r0 = r40;
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0211 }
        r32 = r0;
        r40 = r32.getDatum();	 Catch:{ all -> 0x0211 }
        goto L_0x0170;
    L_0x0181:
        r0 = r40;
        r6 = r0 instanceof java.lang.String;	 Catch:{ all -> 0x0211 }
        if (r6 != 0) goto L_0x0193;
    L_0x0187:
        r0 = r40;
        r6 = r0 instanceof gnu.mapping.Symbol;	 Catch:{ all -> 0x0211 }
        if (r6 != 0) goto L_0x0193;
    L_0x018d:
        r0 = r40;
        r6 = r0 instanceof gnu.expr.Keyword;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x02b0;
    L_0x0193:
        r50 = 0;
        r35 = 0;
        r0 = r40;
        r6 = r0 instanceof gnu.expr.Keyword;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x01b5;
    L_0x019d:
        r12 = r39;
    L_0x019f:
        r23 = 0;
        r24 = 0;
    L_0x01a3:
        r6 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0211 }
        if (r12 == r6) goto L_0x0265;
    L_0x01a7:
        r6 = r12 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x01ba;
    L_0x01ab:
        r0 = r12;
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0211 }
        r33 = r0;
        r12 = r33.getDatum();	 Catch:{ all -> 0x0211 }
        goto L_0x01a7;
    L_0x01b5:
        r12 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        goto L_0x019f;
    L_0x01ba:
        r0 = r12;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0211 }
        r39 = r0;
        r30 = r39.getCar();	 Catch:{ all -> 0x0211 }
    L_0x01c3:
        r0 = r30;
        r6 = r0 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x01d0;
    L_0x01c9:
        r30 = (kawa.lang.SyntaxForm) r30;	 Catch:{ all -> 0x0211 }
        r30 = r30.getDatum();	 Catch:{ all -> 0x0211 }
        goto L_0x01c3;
    L_0x01d0:
        r0 = r54;
        r1 = r39;
        r45 = r0.pushPositionOf(r1);	 Catch:{ all -> 0x0211 }
        r12 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        r6 = coloncolon;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 == r6) goto L_0x01e8;
    L_0x01e2:
        r0 = r30;
        r6 = r0 instanceof gnu.expr.Keyword;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0237;
    L_0x01e8:
        r6 = r12 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0237;
    L_0x01ec:
        r35 = r35 + 1;
        r0 = r12;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0211 }
        r39 = r0;
        r51 = r39.getCar();	 Catch:{ all -> 0x0211 }
        r12 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        r6 = coloncolon;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 == r6) goto L_0x0207;
    L_0x0201:
        r6 = typeKeyword;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 != r6) goto L_0x021a;
    L_0x0207:
        r50 = r51;
    L_0x0209:
        r0 = r54;
        r1 = r45;
        r0.popPositionOf(r1);	 Catch:{ all -> 0x0211 }
        goto L_0x01a3;
    L_0x0211:
        r6 = move-exception;
    L_0x0212:
        r0 = r54;
        r1 = r44;
        r0.popPositionOf(r1);
        throw r6;
    L_0x021a:
        r6 = initKeyword;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 == r6) goto L_0x0232;
    L_0x0220:
        r6 = initformKeyword;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 == r6) goto L_0x0232;
    L_0x0226:
        r6 = init_formKeyword;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 == r6) goto L_0x0232;
    L_0x022c:
        r6 = init_valueKeyword;	 Catch:{ all -> 0x0211 }
        r0 = r30;
        if (r0 != r6) goto L_0x0209;
    L_0x0232:
        r23 = r39;
        r24 = r33;
        goto L_0x0209;
    L_0x0237:
        r6 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0211 }
        if (r12 != r6) goto L_0x0242;
    L_0x023b:
        if (r23 != 0) goto L_0x0242;
    L_0x023d:
        r23 = r39;
        r24 = r33;
        goto L_0x0209;
    L_0x0242:
        r6 = r12 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0264;
    L_0x0246:
        if (r35 != 0) goto L_0x0264;
    L_0x0248:
        if (r23 != 0) goto L_0x0264;
    L_0x024a:
        if (r50 != 0) goto L_0x0264;
    L_0x024c:
        r0 = r12;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0211 }
        r39 = r0;
        r6 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        r8 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0211 }
        if (r6 != r8) goto L_0x0264;
    L_0x0259:
        r50 = r30;
        r23 = r39;
        r24 = r33;
        r12 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        goto L_0x0209;
    L_0x0264:
        r12 = 0;
    L_0x0265:
        if (r23 == 0) goto L_0x029b;
    L_0x0267:
        r26 = r25 + 1;
        r0 = r27;
        r1 = r25;
        r19 = r0.elementAt(r1);	 Catch:{ all -> 0x0356 }
        r0 = r19;
        r6 = r0 instanceof gnu.expr.Declaration;	 Catch:{ all -> 0x0356 }
        if (r6 == 0) goto L_0x02a4;
    L_0x0277:
        r0 = r19;
        r0 = (gnu.expr.Declaration) r0;	 Catch:{ all -> 0x0356 }
        r6 = r0;
        r8 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r28 = r6.getFlag(r8);	 Catch:{ all -> 0x0356 }
    L_0x0282:
        r25 = r26 + 1;
        r0 = r27;
        r1 = r26;
        r6 = r0.elementAt(r1);	 Catch:{ all -> 0x0211 }
        if (r6 != 0) goto L_0x029b;
    L_0x028e:
        r0 = r19;
        r1 = r38;
        r2 = r23;
        r3 = r54;
        r4 = r24;
        rewriteInit(r0, r1, r2, r3, r4);	 Catch:{ all -> 0x0211 }
    L_0x029b:
        r0 = r54;
        r1 = r44;
        r0.popPositionOf(r1);
        goto L_0x010d;
    L_0x02a4:
        r6 = java.lang.Boolean.TRUE;	 Catch:{ all -> 0x0356 }
        r0 = r19;
        if (r0 != r6) goto L_0x02ad;
    L_0x02aa:
        r28 = 1;
        goto L_0x0282;
    L_0x02ad:
        r28 = 0;
        goto L_0x0282;
    L_0x02b0:
        r0 = r40;
        r6 = r0 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x0322;
    L_0x02b6:
        r42 = r54.currentScope();	 Catch:{ all -> 0x0211 }
        if (r33 == 0) goto L_0x02c5;
    L_0x02bc:
        r6 = r33.getScope();	 Catch:{ all -> 0x0211 }
        r0 = r54;
        r0.setCurrentScope(r6);	 Catch:{ all -> 0x0211 }
    L_0x02c5:
        r6 = "*init*";
        r8 = r7.getName();	 Catch:{ all -> 0x0211 }
        r6 = r6.equals(r8);	 Catch:{ all -> 0x0211 }
        if (r6 == 0) goto L_0x02d6;
    L_0x02d1:
        r6 = gnu.bytecode.Type.voidType;	 Catch:{ all -> 0x0211 }
        r7.setReturnType(r6);	 Catch:{ all -> 0x0211 }
    L_0x02d6:
        r0 = r39;
        kawa.lang.Translator.setLine(r7, r0);	 Catch:{ all -> 0x0211 }
        r0 = r54;
        r0 = r0.curMethodLambda;	 Catch:{ all -> 0x0211 }
        r41 = r0;
        r0 = r54;
        r0.curMethodLambda = r7;	 Catch:{ all -> 0x0211 }
        r0 = r52;
        r6 = r0.lambda;	 Catch:{ all -> 0x0211 }
        r0 = r40;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0211 }
        r8 = r0;
        r8 = r8.getCdr();	 Catch:{ all -> 0x0211 }
        r9 = r39.getCdr();	 Catch:{ all -> 0x0211 }
        if (r32 == 0) goto L_0x0320;
    L_0x02f8:
        if (r33 == 0) goto L_0x0304;
    L_0x02fa:
        r10 = r32.getScope();	 Catch:{ all -> 0x0211 }
        r11 = r33.getScope();	 Catch:{ all -> 0x0211 }
        if (r10 == r11) goto L_0x0320;
    L_0x0304:
        r11 = r32.getScope();	 Catch:{ all -> 0x0211 }
    L_0x0308:
        r10 = r54;
        r6.rewrite(r7, r8, r9, r10, r11);	 Catch:{ all -> 0x0211 }
        r0 = r41;
        r1 = r54;
        r1.curMethodLambda = r0;	 Catch:{ all -> 0x0211 }
        if (r33 == 0) goto L_0x031c;
    L_0x0315:
        r0 = r54;
        r1 = r42;
        r0.setCurrentScope(r1);	 Catch:{ all -> 0x0211 }
    L_0x031c:
        r7 = r7.nextSibling;	 Catch:{ all -> 0x0211 }
        goto L_0x029b;
    L_0x0320:
        r11 = 0;
        goto L_0x0308;
    L_0x0322:
        r6 = "invalid field/method definition";
        r0 = r54;
        r0.syntaxError(r6);	 Catch:{ all -> 0x0211 }
        goto L_0x029b;
    L_0x032b:
        r0 = r38;
        r6 = r0.initMethod;
        if (r6 == 0) goto L_0x0339;
    L_0x0331:
        r0 = r38;
        r6 = r0.initMethod;
        r0 = r38;
        r6.outer = r0;
    L_0x0339:
        r0 = r38;
        r6 = r0.clinitMethod;
        if (r6 == 0) goto L_0x0347;
    L_0x033f:
        r0 = r38;
        r6 = r0.clinitMethod;
        r0 = r38;
        r6.outer = r0;
    L_0x0347:
        r0 = r54;
        r1 = r38;
        r0.pop(r1);
        r0 = r38;
        r1 = r54;
        r0.declareParts(r1);
        return;
    L_0x0356:
        r6 = move-exception;
        r25 = r26;
        goto L_0x0212;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteClassDef(java.lang.Object[], kawa.lang.Translator):void");
    }

    private static void rewriteInit(Object d, ClassExp oexp, Pair initPair, Translator tr, SyntaxForm initSyntax) {
        boolean isStatic = d instanceof Declaration ? ((Declaration) d).getFlag(2048) : d == Boolean.TRUE;
        LambdaExp initMethod = isStatic ? oexp.clinitMethod : oexp.initMethod;
        if (initMethod == null) {
            initMethod = new LambdaExp(new BeginExp());
            initMethod.setClassMethod(true);
            initMethod.setReturnType(Type.voidType);
            if (isStatic) {
                initMethod.setName("$clinit$");
                oexp.clinitMethod = initMethod;
            } else {
                initMethod.setName("$finit$");
                oexp.initMethod = initMethod;
                initMethod.add(null, new Declaration(ThisExp.THIS_NAME));
            }
            initMethod.nextSibling = oexp.firstChild;
            oexp.firstChild = initMethod;
        }
        tr.push((ScopeExp) initMethod);
        LambdaExp saveLambda = tr.curMethodLambda;
        tr.curMethodLambda = initMethod;
        Expression initValue = tr.rewrite_car(initPair, initSyntax);
        if (d instanceof Declaration) {
            Declaration decl = (Declaration) d;
            Expression sexp = new SetExp(decl, initValue);
            sexp.setLocation(decl);
            decl.noteValue(null);
            initValue = sexp;
        } else {
            initValue = Compilation.makeCoercion(initValue, new QuoteExp(Type.voidType));
        }
        ((BeginExp) initMethod.body).add(initValue);
        tr.curMethodLambda = saveLambda;
        tr.pop(initMethod);
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else if (!(exp instanceof Pair)) {
            return false;
        } else {
            Object qvalue = tr.matchQuoted((Pair) exp);
            if (!(qvalue instanceof SimpleSymbol)) {
                return false;
            }
            value = qvalue.toString();
        }
        if (tag == null || tag.equals(value)) {
            return true;
        }
        return false;
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if (((-1 ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair p = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        LList cdr = pair.getCdr();
        if (cdr == LList.Empty || icar == 0) {
            return icar;
        }
        if (cdr instanceof Pair) {
            long icdr = matchAccess2((Pair) cdr, tr);
            if (icdr != 0) {
                return icar | icdr;
            }
        }
        return 0;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216;
        }
        if ("protected".equals(value)) {
            return 33554432;
        }
        if ("public".equals(value)) {
            return 67108864;
        }
        if ("package".equals(value)) {
            return 134217728;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0;
    }
}
