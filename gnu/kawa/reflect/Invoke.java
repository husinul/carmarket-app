package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;

public class Invoke extends ProcedureN {
    public static final Invoke invoke;
    public static final Invoke invokeSpecial;
    public static final Invoke invokeStatic;
    public static final Invoke make;
    char kind;
    Language language;

    static {
        invoke = new Invoke("invoke", '*');
        invokeStatic = new Invoke("invoke-static", 'S');
        invokeSpecial = new Invoke("invoke-special", 'P');
        make = new Invoke("make", 'N');
    }

    public Invoke(String name, char kind) {
        this(name, kind, Language.getDefaultLanguage());
    }

    public Invoke(String name, char kind, Language language) {
        super(name);
        this.kind = kind;
        this.language = language;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileInvoke:validateApplyInvoke");
    }

    public static Object invoke$V(Object[] args) throws Throwable {
        return invoke.applyN(args);
    }

    public static Object invokeStatic$V(Object[] args) throws Throwable {
        return invokeStatic.applyN(args);
    }

    public static Object make$V(Object[] args) throws Throwable {
        return make.applyN(args);
    }

    private static ObjectType typeFrom(Object arg, Invoke thisProc) {
        if (arg instanceof Class) {
            arg = Type.make((Class) arg);
        }
        if (arg instanceof ObjectType) {
            return (ObjectType) arg;
        }
        if ((arg instanceof String) || (arg instanceof FString)) {
            return ClassType.make(arg.toString());
        }
        if (arg instanceof Symbol) {
            return ClassType.make(((Symbol) arg).getName());
        }
        if (arg instanceof ClassNamespace) {
            return ((ClassNamespace) arg).getClassType();
        }
        throw new WrongType((Procedure) thisProc, 0, arg, "class-specifier");
    }

    public void apply(CallContext ctx) throws Throwable {
        Object[] args = ctx.getArgs();
        if (this.kind == 'S' || this.kind == 'V' || this.kind == 's' || this.kind == '*') {
            int nargs = args.length;
            Procedure.checkArgCount(this, nargs);
            Object arg0 = args[0];
            Type typeFrom = (this.kind == 'S' || this.kind == 's') ? typeFrom(arg0, this) : Type.make(arg0.getClass());
            Procedure proc = lookupMethods((ObjectType) typeFrom, args[1]);
            Object[] margs = new Object[(nargs - (this.kind == 'S' ? 2 : 1))];
            int i = 0;
            if (this.kind == 'V' || this.kind == '*') {
                int i2 = 0 + 1;
                margs[0] = args[0];
                i = i2;
            }
            System.arraycopy(args, 2, margs, i, nargs - 2);
            proc.checkN(margs, ctx);
            return;
        }
        ctx.writeValue(applyN(args));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object applyN(java.lang.Object[] r39) throws java.lang.Throwable {
        /*
        r38 = this;
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 80;
        r0 = r34;
        r1 = r35;
        if (r0 != r1) goto L_0x002b;
    L_0x000e:
        r34 = new java.lang.RuntimeException;
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r36 = r38.getName();
        r35 = r35.append(r36);
        r36 = ": invoke-special not allowed at run time";
        r35 = r35.append(r36);
        r35 = r35.toString();
        r34.<init>(r35);
        throw r34;
    L_0x002b:
        r0 = r39;
        r0 = r0.length;
        r27 = r0;
        r0 = r38;
        r1 = r27;
        gnu.mapping.Procedure.checkArgCount(r0, r1);
        r34 = 0;
        r7 = r39[r34];
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 86;
        r0 = r34;
        r1 = r35;
        if (r0 == r1) goto L_0x009d;
    L_0x0049:
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 42;
        r0 = r34;
        r1 = r35;
        if (r0 == r1) goto L_0x009d;
    L_0x0057:
        r0 = r38;
        r11 = typeFrom(r7, r0);
    L_0x005d:
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 78;
        r0 = r34;
        r1 = r35;
        if (r0 != r1) goto L_0x0178;
    L_0x006b:
        r25 = 0;
        r0 = r11 instanceof gnu.expr.TypeValue;
        r34 = r0;
        if (r34 == 0) goto L_0x00aa;
    L_0x0073:
        r34 = r11;
        r34 = (gnu.expr.TypeValue) r34;
        r10 = r34.getConstructor();
        if (r10 == 0) goto L_0x00aa;
    L_0x007d:
        r27 = r27 + -1;
        r0 = r27;
        r0 = new java.lang.Object[r0];
        r33 = r0;
        r34 = 1;
        r35 = 0;
        r0 = r39;
        r1 = r34;
        r2 = r33;
        r3 = r35;
        r4 = r27;
        java.lang.System.arraycopy(r0, r1, r2, r3, r4);
        r0 = r33;
        r8 = r10.applyN(r0);
    L_0x009c:
        return r8;
    L_0x009d:
        r34 = r7.getClass();
        r34 = gnu.bytecode.Type.make(r34);
        r34 = (gnu.bytecode.ObjectType) r34;
        r11 = r34;
        goto L_0x005d;
    L_0x00aa:
        r0 = r11 instanceof gnu.expr.PairClassType;
        r34 = r0;
        if (r34 == 0) goto L_0x00b8;
    L_0x00b0:
        r29 = r11;
        r29 = (gnu.expr.PairClassType) r29;
        r0 = r29;
        r11 = r0.instanceType;
    L_0x00b8:
        r0 = r11 instanceof gnu.bytecode.ArrayType;
        r34 = r0;
        if (r34 == 0) goto L_0x017c;
    L_0x00be:
        r34 = r11;
        r34 = (gnu.bytecode.ArrayType) r34;
        r12 = r34.getComponentType();
        r0 = r39;
        r0 = r0.length;
        r34 = r0;
        r21 = r34 + -1;
        r34 = 2;
        r0 = r21;
        r1 = r34;
        if (r0 < r1) goto L_0x014e;
    L_0x00d5:
        r34 = 1;
        r34 = r39[r34];
        r0 = r34;
        r0 = r0 instanceof gnu.expr.Keyword;
        r34 = r0;
        if (r34 == 0) goto L_0x014e;
    L_0x00e1:
        r35 = "length";
        r34 = 1;
        r34 = r39[r34];
        r34 = (gnu.expr.Keyword) r34;
        r26 = r34.getName();
        r0 = r35;
        r1 = r26;
        r34 = r0.equals(r1);
        if (r34 != 0) goto L_0x0103;
    L_0x00f7:
        r34 = "size";
        r0 = r34;
        r1 = r26;
        r34 = r0.equals(r1);
        if (r34 == 0) goto L_0x014e;
    L_0x0103:
        r34 = 2;
        r34 = r39[r34];
        r34 = (java.lang.Number) r34;
        r22 = r34.intValue();
        r15 = 3;
        r23 = 1;
    L_0x0110:
        r34 = r12.getReflectClass();
        r0 = r34;
        r1 = r22;
        r8 = java.lang.reflect.Array.newInstance(r0, r1);
        r17 = 0;
    L_0x011e:
        r0 = r21;
        if (r15 > r0) goto L_0x009c;
    L_0x0122:
        r6 = r39[r15];
        if (r23 == 0) goto L_0x013e;
    L_0x0126:
        r0 = r6 instanceof gnu.expr.Keyword;
        r34 = r0;
        if (r34 == 0) goto L_0x013e;
    L_0x012c:
        r0 = r21;
        if (r15 >= r0) goto L_0x013e;
    L_0x0130:
        r6 = (gnu.expr.Keyword) r6;
        r20 = r6.getName();
        r17 = java.lang.Integer.parseInt(r20);	 Catch:{ Throwable -> 0x0154 }
        r15 = r15 + 1;
        r6 = r39[r15];
    L_0x013e:
        r34 = r12.coerceFromObject(r6);
        r0 = r17;
        r1 = r34;
        java.lang.reflect.Array.set(r8, r0, r1);
        r17 = r17 + 1;
        r15 = r15 + 1;
        goto L_0x011e;
    L_0x014e:
        r22 = r21;
        r15 = 1;
        r23 = 0;
        goto L_0x0110;
    L_0x0154:
        r14 = move-exception;
        r34 = new java.lang.RuntimeException;
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r36 = "non-integer keyword '";
        r35 = r35.append(r36);
        r0 = r35;
        r1 = r20;
        r35 = r0.append(r1);
        r36 = "' in array constructor";
        r35 = r35.append(r36);
        r35 = r35.toString();
        r34.<init>(r35);
        throw r34;
    L_0x0178:
        r34 = 1;
        r25 = r39[r34];
    L_0x017c:
        r0 = r38;
        r1 = r25;
        r28 = r0.lookupMethods(r11, r1);
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 78;
        r0 = r34;
        r1 = r35;
        if (r0 == r1) goto L_0x01fb;
    L_0x0192:
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 83;
        r0 = r34;
        r1 = r35;
        if (r0 == r1) goto L_0x01ae;
    L_0x01a0:
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        r0 = r34;
        r1 = r35;
        if (r0 != r1) goto L_0x01f8;
    L_0x01ae:
        r34 = 2;
    L_0x01b0:
        r34 = r27 - r34;
        r0 = r34;
        r0 = new java.lang.Object[r0];
        r24 = r0;
        r15 = 0;
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 86;
        r0 = r34;
        r1 = r35;
        if (r0 == r1) goto L_0x01d5;
    L_0x01c7:
        r0 = r38;
        r0 = r0.kind;
        r34 = r0;
        r35 = 42;
        r0 = r34;
        r1 = r35;
        if (r0 != r1) goto L_0x01df;
    L_0x01d5:
        r16 = r15 + 1;
        r34 = 0;
        r34 = r39[r34];
        r24[r15] = r34;
        r15 = r16;
    L_0x01df:
        r34 = 2;
        r35 = r27 + -2;
        r0 = r39;
        r1 = r34;
        r2 = r24;
        r3 = r35;
        java.lang.System.arraycopy(r0, r1, r2, r15, r3);
        r0 = r28;
        r1 = r24;
        r8 = r0.applyN(r1);
        goto L_0x009c;
    L_0x01f8:
        r34 = 1;
        goto L_0x01b0;
    L_0x01fb:
        r31 = gnu.mapping.CallContext.getInstance();
        r19 = 0;
    L_0x0201:
        r0 = r39;
        r0 = r0.length;
        r34 = r0;
        r0 = r19;
        r1 = r34;
        if (r0 >= r1) goto L_0x0219;
    L_0x020c:
        r34 = r39[r19];
        r0 = r34;
        r0 = r0 instanceof gnu.expr.Keyword;
        r34 = r0;
        if (r34 != 0) goto L_0x0219;
    L_0x0216:
        r19 = r19 + 1;
        goto L_0x0201;
    L_0x0219:
        r13 = -1;
        r0 = r39;
        r0 = r0.length;
        r34 = r0;
        r0 = r19;
        r1 = r34;
        if (r0 != r1) goto L_0x02cd;
    L_0x0225:
        r0 = r28;
        r1 = r39;
        r2 = r31;
        r13 = r0.matchN(r1, r2);
        if (r13 != 0) goto L_0x0237;
    L_0x0231:
        r8 = r31.runUntilValue();
        goto L_0x009c;
    L_0x0237:
        r34 = r11;
        r34 = (gnu.bytecode.ClassType) r34;
        r35 = "valueOf";
        r36 = 0;
        r0 = r38;
        r0 = r0.language;
        r37 = r0;
        r32 = gnu.kawa.reflect.ClassMethods.apply(r34, r35, r36, r37);
        if (r32 == 0) goto L_0x0278;
    L_0x024b:
        r34 = r27 + -1;
        r0 = r34;
        r0 = new java.lang.Object[r0];
        r24 = r0;
        r34 = 1;
        r35 = 0;
        r36 = r27 + -1;
        r0 = r39;
        r1 = r34;
        r2 = r24;
        r3 = r35;
        r4 = r36;
        java.lang.System.arraycopy(r0, r1, r2, r3, r4);
        r0 = r32;
        r1 = r24;
        r2 = r31;
        r13 = r0.matchN(r1, r2);
        if (r13 != 0) goto L_0x0278;
    L_0x0272:
        r8 = r31.runUntilValue();
        goto L_0x009c;
    L_0x0278:
        r34 = 0;
        r34 = r39[r34];
        r0 = r28;
        r1 = r34;
        r30 = r0.apply1(r1);
    L_0x0284:
        r15 = r19;
    L_0x0286:
        r34 = r15 + 1;
        r0 = r39;
        r0 = r0.length;
        r35 = r0;
        r0 = r34;
        r1 = r35;
        if (r0 >= r1) goto L_0x029b;
    L_0x0293:
        r6 = r39[r15];
        r0 = r6 instanceof gnu.expr.Keyword;
        r34 = r0;
        if (r34 != 0) goto L_0x02e7;
    L_0x029b:
        r0 = r39;
        r0 = r0.length;
        r34 = r0;
        r0 = r19;
        r1 = r34;
        if (r0 != r1) goto L_0x02a7;
    L_0x02a6:
        r15 = 1;
    L_0x02a7:
        r0 = r39;
        r0 = r0.length;
        r34 = r0;
        r0 = r34;
        if (r15 == r0) goto L_0x0318;
    L_0x02b0:
        r34 = r11;
        r34 = (gnu.bytecode.ClassType) r34;
        r35 = "add";
        r36 = 0;
        r0 = r38;
        r0 = r0.language;
        r37 = r0;
        r5 = gnu.kawa.reflect.ClassMethods.apply(r34, r35, r36, r37);
        if (r5 != 0) goto L_0x0301;
    L_0x02c4:
        r0 = r28;
        r1 = r39;
        r34 = gnu.mapping.MethodProc.matchFailAsException(r13, r0, r1);
        throw r34;
    L_0x02cd:
        r0 = r19;
        r9 = new java.lang.Object[r0];
        r34 = 0;
        r35 = 0;
        r0 = r39;
        r1 = r34;
        r2 = r35;
        r3 = r19;
        java.lang.System.arraycopy(r0, r1, r9, r2, r3);
        r0 = r28;
        r30 = r0.applyN(r9);
        goto L_0x0284;
    L_0x02e7:
        r18 = r6;
        r18 = (gnu.expr.Keyword) r18;
        r34 = r15 + 1;
        r6 = r39[r34];
        r34 = 0;
        r35 = r18.getName();
        r0 = r34;
        r1 = r30;
        r2 = r35;
        gnu.kawa.reflect.SlotSet.apply(r0, r1, r2, r6);
        r15 = r15 + 2;
        goto L_0x0286;
    L_0x0301:
        r0 = r39;
        r0 = r0.length;
        r34 = r0;
        r0 = r34;
        if (r15 >= r0) goto L_0x0318;
    L_0x030a:
        r16 = r15 + 1;
        r34 = r39[r15];
        r0 = r30;
        r1 = r34;
        r5.apply2(r0, r1);
        r15 = r16;
        goto L_0x0301;
    L_0x0318:
        r8 = r30;
        goto L_0x009c;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.Invoke.applyN(java.lang.Object[]):java.lang.Object");
    }

    public int numArgs() {
        return (this.kind == 'N' ? 1 : 2) | -4096;
    }

    protected MethodProc lookupMethods(ObjectType dtype, Object name) {
        String mname;
        char c = 'P';
        if (this.kind == 'N') {
            mname = "<init>";
        } else {
            if ((name instanceof String) || (name instanceof FString)) {
                mname = name.toString();
            } else if (name instanceof Symbol) {
                mname = ((Symbol) name).getName();
            } else {
                throw new WrongType((Procedure) this, 1, null);
            }
            mname = Compilation.mangleName(mname);
        }
        if (this.kind != 'P') {
            c = (this.kind == '*' || this.kind == 'V') ? 'V' : '\u0000';
        }
        MethodProc proc = ClassMethods.apply(dtype, mname, c, this.language);
        if (proc != null) {
            return proc;
        }
        throw new RuntimeException(getName() + ": no method named `" + mname + "' in class " + dtype.getName());
    }

    public static synchronized ApplyExp makeInvokeStatic(ClassType type, String name, Expression[] args) {
        ApplyExp applyExp;
        synchronized (Invoke.class) {
            Procedure method = getStaticMethod(type, name, args);
            if (method == null) {
                throw new RuntimeException("missing or ambiguous method `" + name + "' in " + type.getName());
            }
            applyExp = new ApplyExp(method, args);
        }
        return applyExp;
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        PrimProcedure staticMethod;
        synchronized (Invoke.class) {
            staticMethod = CompileInvoke.getStaticMethod(type, name, args);
        }
        return staticMethod;
    }
}
