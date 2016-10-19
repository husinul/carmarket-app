package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Environment;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Translator;

public class CompileNamedPart {
    static final ClassType typeHasNamedParts;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression validateGetNamedPart(gnu.expr.ApplyExp r26, gnu.expr.InlineCalls r27, gnu.bytecode.Type r28, gnu.mapping.Procedure r29) {
        /*
        r26.visitArgs(r27);
        r4 = r26.getArgs();
        r0 = r4.length;
        r22 = r0;
        r23 = 2;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0026;
    L_0x0012:
        r22 = 1;
        r22 = r4[r22];
        r0 = r22;
        r0 = r0 instanceof gnu.expr.QuoteExp;
        r22 = r0;
        if (r22 == 0) goto L_0x0026;
    L_0x001e:
        r0 = r26;
        r0 = r0 instanceof gnu.kawa.functions.GetNamedExp;
        r22 = r0;
        if (r22 != 0) goto L_0x0027;
    L_0x0026:
        return r26;
    L_0x0027:
        r22 = 0;
        r8 = r4[r22];
        r9 = 0;
        r0 = r8 instanceof gnu.expr.ReferenceExp;
        r22 = r0;
        if (r22 == 0) goto L_0x004f;
    L_0x0032:
        r17 = r8;
        r17 = (gnu.expr.ReferenceExp) r17;
        r22 = "*";
        r23 = r17.getName();
        r22 = r22.equals(r23);
        if (r22 == 0) goto L_0x004b;
    L_0x0042:
        r22 = 1;
        r22 = r4[r22];
        r26 = makeGetNamedInstancePartExp(r22);
        goto L_0x0026;
    L_0x004b:
        r9 = r17.getBinding();
    L_0x004f:
        r22 = 1;
        r22 = r4[r22];
        r22 = (gnu.expr.QuoteExp) r22;
        r22 = r22.getValue();
        r13 = r22.toString();
        r18 = r8.getType();
        r22 = gnu.expr.QuoteExp.nullExp;
        r0 = r22;
        if (r8 != r0) goto L_0x0095;
    L_0x0067:
        r10 = 1;
    L_0x0068:
        r7 = r27.getCompilation();
        r11 = r7.getLanguage();
        r22 = 0;
        r0 = r22;
        r19 = r11.getTypeFor(r8, r0);
        if (r7 != 0) goto L_0x0097;
    L_0x007a:
        r6 = 0;
    L_0x007b:
        r14 = r26;
        r14 = (gnu.kawa.functions.GetNamedExp) r14;
        if (r19 == 0) goto L_0x00e7;
    L_0x0081:
        r22 = "<>";
        r0 = r22;
        r22 = r13.equals(r0);
        if (r22 == 0) goto L_0x00a3;
    L_0x008b:
        r26 = new gnu.expr.QuoteExp;
        r0 = r26;
        r1 = r19;
        r0.<init>(r1);
        goto L_0x0026;
    L_0x0095:
        r10 = 0;
        goto L_0x0068;
    L_0x0097:
        r0 = r7.curClass;
        r22 = r0;
        if (r22 == 0) goto L_0x00a0;
    L_0x009d:
        r6 = r7.curClass;
        goto L_0x007b;
    L_0x00a0:
        r6 = r7.mainClass;
        goto L_0x007b;
    L_0x00a3:
        r0 = r19;
        r0 = r0 instanceof gnu.bytecode.ObjectType;
        r22 = r0;
        if (r22 == 0) goto L_0x00e7;
    L_0x00ab:
        r22 = "new";
        r0 = r22;
        r22 = r13.equals(r0);
        if (r22 == 0) goto L_0x00bf;
    L_0x00b5:
        r22 = 78;
        r0 = r22;
        r26 = r14.setProcedureKind(r0);
        goto L_0x0026;
    L_0x00bf:
        r22 = "instance?";
        r0 = r22;
        r22 = r13.equals(r0);
        if (r22 == 0) goto L_0x00d3;
    L_0x00c9:
        r22 = 73;
        r0 = r22;
        r26 = r14.setProcedureKind(r0);
        goto L_0x0026;
    L_0x00d3:
        r22 = "@";
        r0 = r22;
        r22 = r13.equals(r0);
        if (r22 == 0) goto L_0x00e7;
    L_0x00dd:
        r22 = 67;
        r0 = r22;
        r26 = r14.setProcedureKind(r0);
        goto L_0x0026;
    L_0x00e7:
        r0 = r19;
        r0 = r0 instanceof gnu.bytecode.ObjectType;
        r22 = r0;
        if (r22 == 0) goto L_0x0168;
    L_0x00ef:
        r22 = r13.length();
        r23 = 1;
        r0 = r22;
        r1 = r23;
        if (r0 <= r1) goto L_0x0123;
    L_0x00fb:
        r22 = 0;
        r0 = r22;
        r22 = r13.charAt(r0);
        r23 = 46;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0123;
    L_0x010b:
        r26 = new gnu.expr.QuoteExp;
        r22 = new gnu.kawa.functions.NamedPart;
        r23 = 68;
        r0 = r22;
        r1 = r19;
        r2 = r23;
        r0.<init>(r1, r13, r2);
        r0 = r26;
        r1 = r22;
        r0.<init>(r1);
        goto L_0x0026;
    L_0x0123:
        r0 = r19;
        r22 = gnu.kawa.reflect.CompileReflect.checkKnownClass(r0, r7);
        if (r22 < 0) goto L_0x0026;
    L_0x012b:
        r19 = (gnu.bytecode.ObjectType) r19;
        r22 = gnu.expr.Compilation.mangleName(r13);
        r23 = 0;
        r0 = r19;
        r1 = r22;
        r2 = r23;
        r12 = gnu.kawa.reflect.ClassMethods.getMethods(r0, r1, r2, r6, r11);
        if (r12 == 0) goto L_0x0150;
    L_0x013f:
        r0 = r12.length;
        r22 = r0;
        if (r22 <= 0) goto L_0x0150;
    L_0x0144:
        r14.methods = r12;
        r22 = 83;
        r0 = r22;
        r26 = r14.setProcedureKind(r0);
        goto L_0x0026;
    L_0x0150:
        r3 = new gnu.expr.ApplyExp;
        r22 = gnu.kawa.reflect.SlotGet.staticField;
        r0 = r22;
        r3.<init>(r0, r4);
        r0 = r26;
        r3.setLine(r0);
        r0 = r27;
        r1 = r28;
        r26 = r0.visitApplyOnly(r3, r1);
        goto L_0x0026;
    L_0x0168:
        if (r19 == 0) goto L_0x016a;
    L_0x016a:
        r22 = gnu.expr.Compilation.typeClassType;
        r0 = r18;
        r1 = r22;
        r22 = r0.isSubtype(r1);
        if (r22 != 0) goto L_0x0026;
    L_0x0176:
        r22 = gnu.bytecode.Type.javalangClassType;
        r0 = r18;
        r1 = r22;
        r22 = r0.isSubtype(r1);
        if (r22 != 0) goto L_0x0026;
    L_0x0182:
        r0 = r18;
        r0 = r0 instanceof gnu.bytecode.ObjectType;
        r22 = r0;
        if (r22 == 0) goto L_0x0243;
    L_0x018a:
        r15 = r18;
        r15 = (gnu.bytecode.ObjectType) r15;
        r22 = gnu.expr.Compilation.mangleName(r13);
        r23 = 86;
        r0 = r22;
        r1 = r23;
        r12 = gnu.kawa.reflect.ClassMethods.getMethods(r15, r0, r1, r6, r11);
        if (r12 == 0) goto L_0x01af;
    L_0x019e:
        r0 = r12.length;
        r22 = r0;
        if (r22 <= 0) goto L_0x01af;
    L_0x01a3:
        r14.methods = r12;
        r22 = 77;
        r0 = r22;
        r26 = r14.setProcedureKind(r0);
        goto L_0x0026;
    L_0x01af:
        r22 = typeHasNamedParts;
        r0 = r18;
        r1 = r22;
        r22 = r0.isSubtype(r1);
        if (r22 == 0) goto L_0x0213;
    L_0x01bb:
        if (r9 == 0) goto L_0x01df;
    L_0x01bd:
        r22 = gnu.expr.Declaration.followAliases(r9);
        r20 = r22.getConstantValue();
        if (r20 == 0) goto L_0x01df;
    L_0x01c7:
        r21 = r20;
        r21 = (gnu.mapping.HasNamedParts) r21;
        r0 = r21;
        r22 = r0.isConstant(r13);
        if (r22 == 0) goto L_0x01df;
    L_0x01d3:
        r0 = r21;
        r20 = r0.get(r13);
        r26 = gnu.expr.QuoteExp.getInstance(r20);
        goto L_0x0026;
    L_0x01df:
        r22 = 2;
        r0 = r22;
        r5 = new gnu.expr.Expression[r0];
        r22 = 0;
        r23 = 0;
        r23 = r4[r23];
        r5[r22] = r23;
        r22 = 1;
        r23 = gnu.expr.QuoteExp.getInstance(r13);
        r5[r22] = r23;
        r22 = new gnu.expr.ApplyExp;
        r23 = typeHasNamedParts;
        r24 = "get";
        r25 = 1;
        r23 = r23.getDeclaredMethod(r24, r25);
        r0 = r22;
        r1 = r23;
        r0.<init>(r1, r5);
        r0 = r22;
        r1 = r26;
        r26 = r0.setLine(r1);
        r4 = r5;
        goto L_0x0026;
    L_0x0213:
        r16 = gnu.kawa.reflect.SlotGet.lookupMember(r15, r13, r6);
        if (r16 != 0) goto L_0x022b;
    L_0x0219:
        r22 = "length";
        r0 = r22;
        r22 = r13.equals(r0);
        if (r22 == 0) goto L_0x0243;
    L_0x0223:
        r0 = r18;
        r0 = r0 instanceof gnu.bytecode.ArrayType;
        r22 = r0;
        if (r22 == 0) goto L_0x0243;
    L_0x022b:
        r3 = new gnu.expr.ApplyExp;
        r22 = gnu.kawa.reflect.SlotGet.field;
        r0 = r22;
        r3.<init>(r0, r4);
        r0 = r26;
        r3.setLine(r0);
        r0 = r27;
        r1 = r28;
        r26 = r0.visitApplyOnly(r3, r1);
        goto L_0x0026;
    L_0x0243:
        r22 = r7.warnUnknownMember();
        if (r22 == 0) goto L_0x0026;
    L_0x0249:
        r22 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "no known slot '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r13);
        r24 = "' in ";
        r23 = r23.append(r24);
        r24 = r18.getName();
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r22;
        r1 = r23;
        r7.error(r0, r1);
        goto L_0x0026;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileNamedPart.validateGetNamedPart(gnu.expr.ApplyExp, gnu.expr.InlineCalls, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }

    public static Expression validateSetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 3 && (args[1] instanceof QuoteExp)) {
            Expression context = args[0];
            String mname = ((QuoteExp) args[1]).getValue().toString();
            Type type = context.getType();
            Compilation comp = visitor.getCompilation();
            Type typeval = comp.getLanguage().getTypeFor(context);
            ClassType caller = comp == null ? null : comp.curClass != null ? comp.curClass : comp.mainClass;
            ApplyExp original = exp;
            if (typeval instanceof ClassType) {
                exp = new ApplyExp(SlotSet.set$Mnstatic$Mnfield$Ex, args);
            } else if ((type instanceof ClassType) && SlotSet.lookupMember((ClassType) type, mname, caller) != null) {
                exp = new ApplyExp(SlotSet.set$Mnfield$Ex, args);
            }
            if (exp != original) {
                exp.setLine((Expression) original);
            }
            exp.setType(Type.voidType);
        }
        return exp;
    }

    public static Expression makeExp(Expression clas, Expression member) {
        Object combinedName = combineName(clas, member);
        Environment env = Environment.getCurrent();
        if (combinedName != null) {
            Translator tr = (Translator) Compilation.getCurrent();
            Object symbol = Namespace.EmptyNamespace.getSymbol(combinedName);
            Declaration decl = tr.lexical.lookup(symbol, false);
            if (!Declaration.isUnknown(decl)) {
                return new ReferenceExp(decl);
            }
            if (symbol != null && env.isBound(symbol, null)) {
                return new ReferenceExp(combinedName);
            }
        }
        if (clas instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) clas;
            if (rexp.isUnknown()) {
                Object rsym = rexp.getSymbol();
                if (env.get(rsym instanceof Symbol ? (Symbol) rsym : env.getSymbol(rsym.toString()), null) == null) {
                    try {
                        clas = QuoteExp.getInstance(Type.make(ObjectType.getContextClass(rexp.getName())));
                    } catch (Throwable th) {
                    }
                }
            }
        }
        Expression exp = new GetNamedExp(new Expression[]{clas, member});
        exp.combinedName = combinedName;
        return exp;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String combineName(gnu.expr.Expression r4, gnu.expr.Expression r5) {
        /*
        r1 = r5.valueIfConstant();
        r2 = r1 instanceof gnu.mapping.SimpleSymbol;
        if (r2 == 0) goto L_0x003b;
    L_0x0008:
        r2 = r4 instanceof gnu.expr.ReferenceExp;
        if (r2 == 0) goto L_0x0015;
    L_0x000c:
        r2 = r4;
        r2 = (gnu.expr.ReferenceExp) r2;
        r0 = r2.getSimpleName();
        if (r0 != 0) goto L_0x001f;
    L_0x0015:
        r2 = r4 instanceof gnu.kawa.functions.GetNamedExp;
        if (r2 == 0) goto L_0x003b;
    L_0x0019:
        r4 = (gnu.kawa.functions.GetNamedExp) r4;
        r0 = r4.combinedName;
        if (r0 == 0) goto L_0x003b;
    L_0x001f:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2 = r2.append(r0);
        r3 = 58;
        r2 = r2.append(r3);
        r2 = r2.append(r1);
        r2 = r2.toString();
        r2 = r2.intern();
    L_0x003a:
        return r2;
    L_0x003b:
        r2 = 0;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileNamedPart.combineName(gnu.expr.Expression, gnu.expr.Expression):java.lang.String");
    }

    public static Expression makeExp(Expression clas, String member) {
        return makeExp(clas, new QuoteExp(member));
    }

    public static Expression makeExp(Type type, String member) {
        return makeExp(new QuoteExp(type), new QuoteExp(member));
    }

    public static Expression validateNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        NamedPart namedPart = (NamedPart) proc;
        switch (namedPart.kind) {
            case 'D':
                Procedure slotProc;
                Expression[] xargs = new Expression[2];
                xargs[1] = QuoteExp.getInstance(namedPart.member.toString().substring(1));
                if (args.length > 0) {
                    xargs[0] = Compilation.makeCoercion(args[0], new QuoteExp(namedPart.container));
                    slotProc = SlotGet.field;
                } else {
                    xargs[0] = QuoteExp.getInstance(namedPart.container);
                    slotProc = SlotGet.staticField;
                }
                ApplyExp aexp = new ApplyExp(slotProc, xargs);
                aexp.setLine((Expression) exp);
                return visitor.visitApplyOnly(aexp, required);
            default:
                return exp;
        }
    }

    public static Expression validateNamedPartSetter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        NamedPart get = (NamedPart) ((NamedPartSetter) proc).getGetter();
        if (get.kind != 'D') {
            return exp;
        }
        Procedure slotProc;
        Expression[] xargs = new Expression[3];
        xargs[1] = QuoteExp.getInstance(get.member.toString().substring(1));
        xargs[2] = exp.getArgs()[0];
        if (exp.getArgCount() == 1) {
            xargs[0] = QuoteExp.getInstance(get.container);
            slotProc = SlotSet.set$Mnstatic$Mnfield$Ex;
        } else if (exp.getArgCount() != 2) {
            return exp;
        } else {
            xargs[0] = Compilation.makeCoercion(exp.getArgs()[0], new QuoteExp(get.container));
            slotProc = SlotSet.set$Mnfield$Ex;
        }
        ApplyExp aexp = new ApplyExp(slotProc, xargs);
        aexp.setLine((Expression) exp);
        return visitor.visitApplyOnly(aexp, required);
    }

    static {
        typeHasNamedParts = ClassType.make("gnu.mapping.HasNamedParts");
    }

    public static Expression makeGetNamedInstancePartExp(Expression member) {
        if (member instanceof QuoteExp) {
            Object val = ((QuoteExp) member).getValue();
            if (val instanceof SimpleSymbol) {
                return QuoteExp.getInstance(new GetNamedInstancePart(val.toString()));
            }
        }
        return new ApplyExp(Invoke.make, new Expression[]{new QuoteExp(ClassType.make("gnu.kawa.functions.GetNamedInstancePart")), member});
    }

    public static Expression validateGetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Expression[] xargs;
        Procedure property;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        GetNamedInstancePart gproc = (GetNamedInstancePart) proc;
        if (gproc.isField) {
            xargs = new Expression[]{args[0], new QuoteExp(gproc.pname)};
            property = SlotGet.field;
        } else {
            int nargs = args.length;
            xargs = new Expression[(nargs + 1)];
            xargs[0] = args[0];
            xargs[1] = new QuoteExp(gproc.pname);
            System.arraycopy(args, 1, xargs, 2, nargs - 1);
            property = Invoke.invoke;
        }
        return visitor.visitApplyOnly(new ApplyExp(property, xargs), required);
    }

    public static Expression validateSetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        String pname = ((SetNamedInstancePart) proc).pname;
        return visitor.visitApplyOnly(new ApplyExp(SlotSet.set$Mnfield$Ex, new Expression[]{args[0], new QuoteExp(pname), args[1]}), required);
    }
}
