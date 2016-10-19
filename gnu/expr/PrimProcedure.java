package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassFileInput;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import gnu.math.DateTime;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.net.URL;

public class PrimProcedure extends MethodProc implements Inlineable {
    private static ClassLoader systemClassLoader;
    Type[] argTypes;
    Member member;
    Method method;
    char mode;
    int op_code;
    Type retType;
    boolean sideEffectFree;
    LambdaExp source;

    public final int opcode() {
        return this.op_code;
    }

    public Type getReturnType() {
        return this.retType;
    }

    public void setReturnType(Type retType) {
        this.retType = retType;
    }

    public boolean isSpecial() {
        return this.mode == 'P';
    }

    public Type getReturnType(Expression[] args) {
        return this.retType;
    }

    public Method getMethod() {
        return this.method;
    }

    public boolean isSideEffectFree() {
        return this.sideEffectFree;
    }

    public void setSideEffectFree() {
        this.sideEffectFree = true;
    }

    public boolean takesVarArgs() {
        if (this.method == null) {
            return false;
        }
        if ((this.method.getModifiers() & DateTime.TIMEZONE_MASK) != 0) {
            return true;
        }
        String name = this.method.getName();
        if (name.endsWith("$V") || name.endsWith("$V$X")) {
            return true;
        }
        return false;
    }

    public boolean takesContext() {
        return this.method != null && takesContext(this.method);
    }

    public static boolean takesContext(Method method) {
        return method.getName().endsWith("$X");
    }

    public int isApplicable(Type[] argTypes) {
        int app = super.isApplicable(argTypes);
        int nargs = argTypes.length;
        if (app != -1 || this.method == null || (this.method.getModifiers() & DateTime.TIMEZONE_MASK) == 0 || nargs <= 0 || !(argTypes[nargs - 1] instanceof ArrayType)) {
            return app;
        }
        Type[] tmp = new Type[nargs];
        System.arraycopy(argTypes, 0, tmp, 0, nargs - 1);
        tmp[nargs - 1] = ((ArrayType) argTypes[nargs - 1]).getComponentType();
        return super.isApplicable(tmp);
    }

    public final boolean isConstructor() {
        return opcode() == 183 && this.mode != 'P';
    }

    public boolean takesTarget() {
        return this.mode != '\u0000';
    }

    public int numArgs() {
        int num = this.argTypes.length;
        if (takesTarget()) {
            num++;
        }
        if (takesContext()) {
            num--;
        }
        return takesVarArgs() ? (num - 1) - 4096 : (num << 12) + num;
    }

    public int match0(CallContext ctx) {
        return matchN(ProcedureN.noArgs, ctx);
    }

    public int match1(Object arg1, CallContext ctx) {
        return matchN(new Object[]{arg1}, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    public int matchN(Object[] args, CallContext ctx) {
        int nargs = args.length;
        boolean takesVarArgs = takesVarArgs();
        int fixArgs = minArgs();
        if (nargs < fixArgs) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | fixArgs;
        }
        if (!takesVarArgs && nargs > fixArgs) {
            return MethodProc.NO_MATCH_TOO_MANY_ARGS | fixArgs;
        }
        Object obj;
        int paramCount = this.argTypes.length;
        Type elementType = null;
        Object[] restArray = null;
        int extraCount = (takesTarget() || isConstructor()) ? 1 : 0;
        Object[] rargs = new Object[paramCount];
        if (takesContext()) {
            paramCount--;
            rargs[paramCount] = ctx;
        }
        if (takesVarArgs) {
            Type restType = this.argTypes[paramCount - 1];
            if (restType == Compilation.scmListType || restType == LangObjType.listType) {
                rargs[paramCount - 1] = LList.makeList(args, fixArgs);
                nargs = fixArgs;
                elementType = Type.objectType;
            } else {
                elementType = ((ArrayType) restType).getComponentType();
                restArray = (Object[]) Array.newInstance(elementType.getReflectClass(), nargs - fixArgs);
                rargs[paramCount - 1] = restArray;
            }
        }
        if (isConstructor()) {
            obj = args[0];
        } else if (extraCount != 0) {
            try {
                obj = this.method.getDeclaringClass().coerceFromObject(args[0]);
            } catch (ClassCastException e) {
                return -786431;
            }
        } else {
            obj = null;
        }
        int i = extraCount;
        while (true) {
            int length = args.length;
            if (i < r0) {
                Type type;
                Object arg = args[i];
                if (i < fixArgs) {
                    type = this.argTypes[i - extraCount];
                } else {
                    type = elementType;
                }
                if (type != Type.objectType) {
                    try {
                        arg = type.coerceFromObject(arg);
                    } catch (ClassCastException e2) {
                        return MethodProc.NO_MATCH_BAD_TYPE | (i + 1);
                    }
                }
                if (i < fixArgs) {
                    rargs[i - extraCount] = arg;
                } else if (restArray != null) {
                    restArray[i - fixArgs] = arg;
                }
                i++;
            } else {
                ctx.value1 = obj;
                ctx.values = rargs;
                ctx.proc = this;
                return 0;
            }
        }
    }

    public void apply(CallContext ctx) throws Throwable {
        boolean slink;
        Class clas;
        Class[] paramTypes;
        int i;
        int i2;
        Object result;
        Object[] args;
        int arg_count = this.argTypes.length;
        boolean is_constructor = isConstructor();
        if (is_constructor) {
            if (this.method.getDeclaringClass().hasOuterLink()) {
                slink = true;
                if (this.member == null) {
                    clas = this.method.getDeclaringClass().getReflectClass();
                    paramTypes = new Class[((slink ? 1 : 0) + arg_count)];
                    i = arg_count;
                    while (true) {
                        i--;
                        if (i >= 0) {
                            break;
                        }
                        if (slink) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        paramTypes[i2 + i] = this.argTypes[i].getReflectClass();
                    }
                    if (slink) {
                        paramTypes[0] = this.method.getDeclaringClass().getOuterLinkType().getReflectClass();
                    }
                    if (is_constructor) {
                        if (this.method != Type.clone_method) {
                            this.member = clas.getMethod(this.method.getName(), paramTypes);
                        }
                    } else {
                        this.member = clas.getConstructor(paramTypes);
                    }
                }
                if (is_constructor) {
                    if (this.method != Type.clone_method) {
                        Object arr = ctx.value1;
                        Class elClass = arr.getClass().getComponentType();
                        int n = Array.getLength(arr);
                        result = Array.newInstance(elClass, n);
                        System.arraycopy(arr, 0, result, 0, n);
                    } else {
                        result = this.retType.coerceToObject(((java.lang.reflect.Method) this.member).invoke(ctx.value1, ctx.values));
                    }
                } else {
                    args = ctx.values;
                    if (slink) {
                        int nargs = args.length + 1;
                        Object xargs = new Object[nargs];
                        System.arraycopy(args, 0, xargs, 1, nargs - 1);
                        xargs[0] = ((PairClassType) ctx.value1).staticLink;
                        args = xargs;
                    }
                    result = ((Constructor) this.member).newInstance(args);
                }
                if (!takesContext()) {
                    ctx.consumer.writeObject(result);
                }
            }
        }
        slink = false;
        try {
            if (this.member == null) {
                clas = this.method.getDeclaringClass().getReflectClass();
                if (slink) {
                }
                paramTypes = new Class[((slink ? 1 : 0) + arg_count)];
                i = arg_count;
                while (true) {
                    i--;
                    if (i >= 0) {
                        break;
                        if (slink) {
                            paramTypes[0] = this.method.getDeclaringClass().getOuterLinkType().getReflectClass();
                        }
                        if (is_constructor) {
                            if (this.method != Type.clone_method) {
                                this.member = clas.getMethod(this.method.getName(), paramTypes);
                            }
                        } else {
                            this.member = clas.getConstructor(paramTypes);
                        }
                    } else {
                        if (slink) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        paramTypes[i2 + i] = this.argTypes[i].getReflectClass();
                    }
                }
            }
            if (is_constructor) {
                if (this.method != Type.clone_method) {
                    result = this.retType.coerceToObject(((java.lang.reflect.Method) this.member).invoke(ctx.value1, ctx.values));
                } else {
                    Object arr2 = ctx.value1;
                    Class elClass2 = arr2.getClass().getComponentType();
                    int n2 = Array.getLength(arr2);
                    result = Array.newInstance(elClass2, n2);
                    System.arraycopy(arr2, 0, result, 0, n2);
                }
            } else {
                args = ctx.values;
                if (slink) {
                    int nargs2 = args.length + 1;
                    Object xargs2 = new Object[nargs2];
                    System.arraycopy(args, 0, xargs2, 1, nargs2 - 1);
                    xargs2[0] = ((PairClassType) ctx.value1).staticLink;
                    args = xargs2;
                }
                result = ((Constructor) this.member).newInstance(args);
            }
            if (!takesContext()) {
                ctx.consumer.writeObject(result);
            }
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    public PrimProcedure(String className, String methodName, int numArgs) {
        this(ClassType.make(className).getDeclaredMethod(methodName, numArgs));
    }

    public PrimProcedure(java.lang.reflect.Method method, Language language) {
        this(((ClassType) language.getTypeFor(method.getDeclaringClass())).getMethod(method), language);
    }

    public PrimProcedure(Method method) {
        init(method);
        this.retType = method.getName().endsWith("$X") ? Type.objectType : method.getReturnType();
    }

    public PrimProcedure(Method method, Language language) {
        this(method, '\u0000', language);
    }

    public PrimProcedure(Method method, char mode, Language language) {
        this.mode = mode;
        init(method);
        Type[] pTypes = this.argTypes;
        int nTypes = pTypes.length;
        this.argTypes = null;
        int i = nTypes;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            Type javaType = pTypes[i];
            Type langType = language.getLangTypeFor(javaType);
            if (javaType != langType) {
                if (this.argTypes == null) {
                    this.argTypes = new Type[nTypes];
                    System.arraycopy(pTypes, 0, this.argTypes, 0, nTypes);
                }
                this.argTypes[i] = langType;
            }
        }
        if (this.argTypes == null) {
            this.argTypes = pTypes;
        }
        if (isConstructor()) {
            this.retType = method.getDeclaringClass();
        } else if (method.getName().endsWith("$X")) {
            this.retType = Type.objectType;
        } else {
            this.retType = language.getLangTypeFor(method.getReturnType());
            if (this.retType == Type.toStringType) {
                this.retType = Type.javalangStringType;
            }
        }
    }

    private void init(Method method) {
        this.method = method;
        if ((method.getModifiers() & 8) != 0) {
            this.op_code = 184;
        } else {
            ClassType mclass = method.getDeclaringClass();
            if (this.mode == 'P') {
                this.op_code = 183;
            } else {
                this.mode = 'V';
                if ("<init>".equals(method.getName())) {
                    this.op_code = 183;
                } else if ((mclass.getModifiers() & Declaration.NOT_DEFINING) != 0) {
                    this.op_code = 185;
                } else {
                    this.op_code = 182;
                }
            }
        }
        Type[] mtypes = method.getParameterTypes();
        if (isConstructor() && method.getDeclaringClass().hasOuterLink()) {
            int len = mtypes.length - 1;
            Type[] types = new Type[len];
            System.arraycopy(mtypes, 1, types, 0, len);
            mtypes = types;
        }
        this.argTypes = mtypes;
    }

    public PrimProcedure(Method method, LambdaExp source) {
        this(method);
        this.retType = source.getReturnType();
        this.source = source;
    }

    public PrimProcedure(int opcode, Type retType, Type[] argTypes) {
        this.op_code = opcode;
        this.retType = retType;
        this.argTypes = argTypes;
    }

    public static PrimProcedure makeBuiltinUnary(int opcode, Type type) {
        return new PrimProcedure(opcode, type, new Type[]{type});
    }

    public static PrimProcedure makeBuiltinBinary(int opcode, Type type) {
        return new PrimProcedure(opcode, type, new Type[]{type, type});
    }

    public PrimProcedure(int op_code, ClassType classtype, String name, Type retType, Type[] argTypes) {
        char c = '\u0000';
        this.op_code = op_code;
        this.method = classtype.addMethod(name, op_code == 184 ? 8 : 0, argTypes, retType);
        this.retType = retType;
        this.argTypes = argTypes;
        if (op_code != 184) {
            c = 'V';
        }
        this.mode = c;
    }

    public final boolean getStaticFlag() {
        return this.method == null || this.method.getStaticFlag() || isConstructor();
    }

    public final Type[] getParameterTypes() {
        return this.argTypes;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void compileArgs(gnu.expr.Expression[] r25, int r26, gnu.bytecode.Type r27, gnu.expr.Compilation r28) {
        /*
        r24 = this;
        r21 = r24.takesVarArgs();
        r17 = r24.getName();
        r7 = 0;
        r8 = r28.getCode();
        r22 = gnu.bytecode.Type.voidType;
        r0 = r27;
        r1 = r22;
        if (r0 != r1) goto L_0x00f7;
    L_0x0015:
        r19 = 1;
    L_0x0017:
        r0 = r24;
        r0 = r0.argTypes;
        r22 = r0;
        r0 = r22;
        r0 = r0.length;
        r22 = r0;
        r6 = r22 - r19;
        r22 = r24.takesContext();
        if (r22 == 0) goto L_0x002c;
    L_0x002a:
        r6 = r6 + -1;
    L_0x002c:
        r0 = r25;
        r0 = r0.length;
        r22 = r0;
        r18 = r22 - r26;
        if (r27 == 0) goto L_0x0037;
    L_0x0035:
        if (r19 == 0) goto L_0x00fb;
    L_0x0037:
        r14 = 1;
    L_0x0038:
        r9 = 0;
        if (r21 == 0) goto L_0x00b1;
    L_0x003b:
        r0 = r24;
        r0 = r0.method;
        r22 = r0;
        r22 = r22.getModifiers();
        r0 = r22;
        r0 = r0 & 128;
        r22 = r0;
        if (r22 == 0) goto L_0x00b1;
    L_0x004d:
        if (r18 <= 0) goto L_0x00b1;
    L_0x004f:
        r0 = r24;
        r0 = r0.argTypes;
        r22 = r0;
        r0 = r22;
        r0 = r0.length;
        r22 = r0;
        if (r22 <= 0) goto L_0x00b1;
    L_0x005c:
        if (r14 == 0) goto L_0x00fe;
    L_0x005e:
        r22 = 0;
    L_0x0060:
        r22 = r22 + r6;
        r0 = r18;
        r1 = r22;
        if (r0 != r1) goto L_0x00b1;
    L_0x0068:
        r0 = r25;
        r0 = r0.length;
        r22 = r0;
        r22 = r22 + -1;
        r22 = r25[r22];
        r16 = r22.getType();
        r0 = r24;
        r0 = r0.argTypes;
        r22 = r0;
        r0 = r24;
        r0 = r0.argTypes;
        r23 = r0;
        r0 = r23;
        r0 = r0.length;
        r23 = r0;
        r23 = r23 + -1;
        r15 = r22[r23];
        r0 = r16;
        r0 = r0 instanceof gnu.bytecode.ObjectType;
        r22 = r0;
        if (r22 == 0) goto L_0x00b1;
    L_0x0092:
        r0 = r15 instanceof gnu.bytecode.ArrayType;
        r22 = r0;
        if (r22 == 0) goto L_0x00b1;
    L_0x0098:
        r15 = (gnu.bytecode.ArrayType) r15;
        r22 = r15.getComponentType();
        r0 = r22;
        r0 = r0 instanceof gnu.bytecode.ArrayType;
        r22 = r0;
        if (r22 != 0) goto L_0x00b1;
    L_0x00a6:
        r0 = r16;
        r0 = r0 instanceof gnu.bytecode.ArrayType;
        r22 = r0;
        if (r22 != 0) goto L_0x00af;
    L_0x00ae:
        r9 = 1;
    L_0x00af:
        r21 = 0;
    L_0x00b1:
        if (r21 == 0) goto L_0x0105;
    L_0x00b3:
        if (r14 == 0) goto L_0x0102;
    L_0x00b5:
        r22 = 1;
    L_0x00b7:
        r12 = r6 - r22;
    L_0x00b9:
        r0 = r24;
        r0 = r0.source;
        r22 = r0;
        if (r22 != 0) goto L_0x010d;
    L_0x00c1:
        r4 = 0;
    L_0x00c2:
        if (r4 == 0) goto L_0x00ce;
    L_0x00c4:
        r22 = r4.isThisParameter();
        if (r22 == 0) goto L_0x00ce;
    L_0x00ca:
        r4 = r4.nextDecl();
    L_0x00ce:
        r13 = 0;
    L_0x00cf:
        if (r21 == 0) goto L_0x012f;
    L_0x00d1:
        if (r13 != r12) goto L_0x012f;
    L_0x00d3:
        r0 = r24;
        r0 = r0.argTypes;
        r22 = r0;
        r23 = r6 + -1;
        r23 = r23 + r19;
        r7 = r22[r23];
        r22 = gnu.expr.Compilation.scmListType;
        r0 = r22;
        if (r7 == r0) goto L_0x00eb;
    L_0x00e5:
        r22 = gnu.kawa.lispexpr.LangObjType.listType;
        r0 = r22;
        if (r7 != r0) goto L_0x0118;
    L_0x00eb:
        r22 = r26 + r13;
        r0 = r25;
        r1 = r22;
        r2 = r28;
        gnu.kawa.functions.MakeList.compile(r0, r1, r2);
    L_0x00f6:
        return;
    L_0x00f7:
        r19 = 0;
        goto L_0x0017;
    L_0x00fb:
        r14 = 0;
        goto L_0x0038;
    L_0x00fe:
        r22 = 1;
        goto L_0x0060;
    L_0x0102:
        r22 = 0;
        goto L_0x00b7;
    L_0x0105:
        r0 = r25;
        r0 = r0.length;
        r22 = r0;
        r12 = r22 - r26;
        goto L_0x00b9;
    L_0x010d:
        r0 = r24;
        r0 = r0.source;
        r22 = r0;
        r4 = r22.firstDecl();
        goto L_0x00c2;
    L_0x0118:
        r0 = r25;
        r0 = r0.length;
        r22 = r0;
        r22 = r22 - r26;
        r22 = r22 - r12;
        r0 = r22;
        r8.emitPushInt(r0);
        r7 = (gnu.bytecode.ArrayType) r7;
        r7 = r7.getComponentType();
        r8.emitNewArray(r7);
    L_0x012f:
        r0 = r18;
        if (r13 >= r0) goto L_0x00f6;
    L_0x0133:
        if (r9 == 0) goto L_0x01cb;
    L_0x0135:
        r22 = r13 + 1;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x01cb;
    L_0x013d:
        r10 = 1;
    L_0x013e:
        if (r13 < r12) goto L_0x01ce;
    L_0x0140:
        r22 = 1;
        r0 = r22;
        r8.emitDup(r0);
        r22 = r13 - r12;
        r0 = r22;
        r8.emitPushInt(r0);
    L_0x014e:
        r0 = r28;
        r0.usedClass(r7);
        if (r10 == 0) goto L_0x01f7;
    L_0x0155:
        r5 = gnu.bytecode.Type.objectType;
    L_0x0157:
        r0 = r24;
        r0 = r0.source;
        r22 = r0;
        if (r22 != 0) goto L_0x01fa;
    L_0x015f:
        r22 = r13 + 1;
        r0 = r17;
        r1 = r22;
        r20 = gnu.expr.CheckedTarget.getInstance(r5, r0, r1);
    L_0x0169:
        r22 = r26 + r13;
        r22 = r25[r22];
        r23 = r26 + r13;
        r23 = r25[r23];
        r0 = r22;
        r1 = r28;
        r2 = r20;
        r3 = r23;
        r0.compileNotePosition(r1, r2, r3);
        if (r10 == 0) goto L_0x01b8;
    L_0x017e:
        r22 = r7;
        r22 = (gnu.bytecode.ArrayType) r22;
        r11 = r22.getComponentType();
        r8.emitDup();
        r8.emitInstanceof(r7);
        r8.emitIfIntNotZero();
        r8.emitCheckcast(r7);
        r8.emitElse();
        r22 = 1;
        r0 = r22;
        r8.emitPushInt(r0);
        r8.emitNewArray(r11);
        r8.emitDupX();
        r8.emitSwap();
        r22 = 0;
        r0 = r22;
        r8.emitPushInt(r0);
        r8.emitSwap();
        r11.emitCoerceFromObject(r8);
        r8.emitArrayStore(r7);
        r8.emitFi();
    L_0x01b8:
        if (r13 < r12) goto L_0x01bd;
    L_0x01ba:
        r8.emitArrayStore(r7);
    L_0x01bd:
        if (r4 == 0) goto L_0x01c7;
    L_0x01bf:
        if (r14 != 0) goto L_0x01c3;
    L_0x01c1:
        if (r13 <= 0) goto L_0x01c7;
    L_0x01c3:
        r4 = r4.nextDecl();
    L_0x01c7:
        r13 = r13 + 1;
        goto L_0x00cf;
    L_0x01cb:
        r10 = 0;
        goto L_0x013e;
    L_0x01ce:
        if (r4 == 0) goto L_0x01da;
    L_0x01d0:
        if (r14 != 0) goto L_0x01d4;
    L_0x01d2:
        if (r13 <= 0) goto L_0x01da;
    L_0x01d4:
        r7 = r4.getType();
    L_0x01d8:
        goto L_0x014e;
    L_0x01da:
        if (r14 == 0) goto L_0x01e7;
    L_0x01dc:
        r0 = r24;
        r0 = r0.argTypes;
        r22 = r0;
        r23 = r13 + r19;
        r7 = r22[r23];
        goto L_0x01d8;
    L_0x01e7:
        if (r13 != 0) goto L_0x01ec;
    L_0x01e9:
        r7 = r27;
        goto L_0x01d8;
    L_0x01ec:
        r0 = r24;
        r0 = r0.argTypes;
        r22 = r0;
        r23 = r13 + -1;
        r7 = r22[r23];
        goto L_0x01d8;
    L_0x01f7:
        r5 = r7;
        goto L_0x0157;
    L_0x01fa:
        r0 = r24;
        r0 = r0.source;
        r22 = r0;
        r0 = r22;
        r20 = gnu.expr.CheckedTarget.getInstance(r5, r0, r13);
        goto L_0x0169;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.PrimProcedure.compileArgs(gnu.expr.Expression[], int, gnu.bytecode.Type, gnu.expr.Compilation):void");
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ClassType mclass;
        CodeAttr code = comp.getCode();
        if (this.method == null) {
            mclass = null;
        } else {
            mclass = this.method.getDeclaringClass();
        }
        Expression[] args = exp.getArgs();
        if (isConstructor()) {
            if (exp.getFlag(8)) {
                int nargs = args.length;
                comp.letStart();
                Expression[] xargs = new Expression[nargs];
                xargs[0] = args[0];
                for (int i = 1; i < nargs; i++) {
                    Expression argi = args[i];
                    Declaration d = comp.letVariable(null, argi.getType(), argi);
                    d.setCanRead(true);
                    xargs[i] = new ReferenceExp(d);
                }
                comp.letEnter();
                comp.letDone(new ApplyExp(exp.func, xargs)).compile(comp, target);
                return;
            }
            code.emitNew(mclass);
            code.emitDup((Type) mclass);
        }
        String arg_error = WrongArguments.checkArgCount(this, args.length);
        if (arg_error != null) {
            comp.error('e', arg_error);
        }
        if (getStaticFlag()) {
            mclass = null;
        }
        compile(mclass, exp, comp, target);
    }

    void compile(Type thisType, ApplyExp exp, Compilation comp, Target target) {
        ClassType mclass = null;
        Expression[] args = exp.getArgs();
        CodeAttr code = comp.getCode();
        Type stackType = this.retType;
        int startArg = 0;
        if (isConstructor()) {
            if (this.method != null) {
                mclass = this.method.getDeclaringClass();
            }
            if (mclass.hasOuterLink()) {
                ClassExp.loadSuperStaticLink(args[0], mclass, comp);
            }
            thisType = null;
            startArg = 1;
        } else if (opcode() == 183 && this.mode == 'P' && "<init>".equals(this.method.getName())) {
            if (this.method != null) {
                mclass = this.method.getDeclaringClass();
            }
            if (mclass.hasOuterLink()) {
                code.emitPushThis();
                code.emitLoad(code.getCurrentScope().getVariable(1));
                thisType = null;
                startArg = 1;
            }
        } else if (takesTarget() && this.method.getStaticFlag()) {
            startArg = 1;
        }
        compileArgs(args, startArg, thisType, comp);
        if (this.method == null) {
            code.emitPrimop(opcode(), args.length, this.retType);
            target.compileFromStack(comp, stackType);
            return;
        }
        compileInvoke(comp, this.method, target, exp.isTailCall(), this.op_code, stackType);
    }

    public static void compileInvoke(Compilation comp, Method method, Target target, boolean isTailCall, int op_code, Type stackType) {
        CodeAttr code = comp.getCode();
        comp.usedClass(method.getDeclaringClass());
        comp.usedClass(method.getReturnType());
        if (!takesContext(method)) {
            code.emitInvokeMethod(method, op_code);
        } else if ((target instanceof IgnoreTarget) || ((target instanceof ConsumerTarget) && ((ConsumerTarget) target).isContextTarget())) {
            Field consumerFld = null;
            Variable saveCallContext = null;
            comp.loadCallContext();
            if (target instanceof IgnoreTarget) {
                ClassType typeCallContext = Compilation.typeCallContext;
                consumerFld = typeCallContext.getDeclaredField("consumer");
                code.pushScope();
                saveCallContext = code.addLocal(typeCallContext);
                code.emitDup();
                code.emitGetField(consumerFld);
                code.emitStore(saveCallContext);
                code.emitDup();
                code.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
                code.emitPutField(consumerFld);
            }
            code.emitInvokeMethod(method, op_code);
            if (isTailCall) {
                comp.loadCallContext();
                code.emitInvoke(Compilation.typeCallContext.getDeclaredMethod("runUntilDone", 0));
            }
            if (target instanceof IgnoreTarget) {
                comp.loadCallContext();
                code.emitLoad(saveCallContext);
                code.emitPutField(consumerFld);
                code.popScope();
                return;
            }
            return;
        } else {
            comp.loadCallContext();
            stackType = Type.objectType;
            code.pushScope();
            Variable saveIndex = code.addLocal(Type.intType);
            comp.loadCallContext();
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("startFromContext", 0));
            code.emitStore(saveIndex);
            code.emitWithCleanupStart();
            code.emitInvokeMethod(method, op_code);
            code.emitWithCleanupCatch(null);
            comp.loadCallContext();
            code.emitLoad(saveIndex);
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("cleanupFromContext", 1));
            code.emitWithCleanupDone();
            comp.loadCallContext();
            code.emitLoad(saveIndex);
            code.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("getFromContext", 1));
            code.popScope();
        }
        target.compileFromStack(comp, stackType);
    }

    public Type getParameterType(int index) {
        if (takesTarget()) {
            if (index != 0) {
                index--;
            } else if (isConstructor()) {
                return Type.objectType;
            } else {
                return this.method.getDeclaringClass();
            }
        }
        int lenTypes = this.argTypes.length;
        if (index < lenTypes - 1) {
            return this.argTypes[index];
        }
        boolean varArgs = takesVarArgs();
        if (index < lenTypes && !varArgs) {
            return this.argTypes[index];
        }
        Type restType = this.argTypes[lenTypes - 1];
        if (restType instanceof ArrayType) {
            return ((ArrayType) restType).getComponentType();
        }
        return Type.objectType;
    }

    static {
        systemClassLoader = PrimProcedure.class.getClassLoader();
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Expression[] args) {
        return getMethodFor(pproc, null, args, Language.getDefaultLanguage());
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Declaration decl, Expression[] args, Language language) {
        int nargs = args.length;
        Type[] atypes = new Type[nargs];
        int i = nargs;
        while (true) {
            i--;
            if (i < 0) {
                return getMethodFor(pproc, decl, atypes, language);
            }
            atypes[i] = args[i].getType();
        }
    }

    public static PrimProcedure getMethodFor(Procedure pproc, Declaration decl, Type[] atypes, Language language) {
        if (pproc instanceof GenericProc) {
            GenericProc gproc = (GenericProc) pproc;
            MethodProc[] methods = gproc.methods;
            pproc = null;
            int i = gproc.count;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                } else if (methods[i].isApplicable(atypes) >= 0) {
                    if (pproc != null) {
                        return null;
                    }
                    pproc = methods[i];
                }
            }
            if (pproc == null) {
                return null;
            }
        }
        if (pproc instanceof PrimProcedure) {
            PrimProcedure prproc = (PrimProcedure) pproc;
            if (prproc.isApplicable(atypes) >= 0) {
                return prproc;
            }
        }
        Class pclass = getProcedureClass(pproc);
        if (pclass == null) {
            return null;
        }
        return getMethodFor((ClassType) Type.make(pclass), pproc.getName(), decl, atypes, language);
    }

    public static void disassemble$X(Procedure pproc, CallContext ctx) throws Exception {
        Consumer cons = ctx.consumer;
        disassemble(pproc, cons instanceof Writer ? (Writer) cons : new ConsumerWriter(cons));
    }

    public static void disassemble(Procedure proc, Writer out) throws Exception {
        disassemble(proc, new ClassTypeWriter(null, out, 0));
    }

    public static void disassemble(Procedure proc, ClassTypeWriter cwriter) throws Exception {
        if (proc instanceof GenericProc) {
            GenericProc gproc = (GenericProc) proc;
            int n = gproc.getMethodCount();
            cwriter.print("Generic procedure with ");
            cwriter.print(n);
            cwriter.println(n == 1 ? " method." : "methods.");
            for (int i = 0; i < n; i++) {
                Procedure mproc = gproc.getMethod(i);
                if (mproc != null) {
                    cwriter.println();
                    disassemble(mproc, cwriter);
                }
            }
            return;
        }
        String pname = null;
        Class cl = proc.getClass();
        if (proc instanceof ModuleMethod) {
            cl = ((ModuleMethod) proc).module.getClass();
        } else if (proc instanceof PrimProcedure) {
            Method pmethod = ((PrimProcedure) proc).method;
            if (pmethod != null) {
                cl = pmethod.getDeclaringClass().getReflectClass();
                pname = pmethod.getName();
            }
        }
        ClassLoader loader = cl.getClassLoader();
        String cname = cl.getName();
        String rname = cname.replace('.', '/') + ".class";
        ClassType ctype = new ClassType();
        InputStream rin = loader.getResourceAsStream(rname);
        if (rin == null) {
            throw new RuntimeException("missing resource " + rname);
        }
        ClassFileInput cinput = new ClassFileInput(ctype, rin);
        cwriter.setClass(ctype);
        URL resource = loader.getResource(rname);
        cwriter.print("In class ");
        cwriter.print(cname);
        if (resource != null) {
            cwriter.print(" at ");
            cwriter.print(resource);
        }
        cwriter.println();
        if (pname == null) {
            pname = proc.getName();
            if (pname == null) {
                cwriter.println("Anonymous function - unknown method.");
                return;
            }
            pname = Compilation.mangleName(pname);
        }
        for (Method method = ctype.getMethods(); method != null; method = method.getNext()) {
            if (method.getName().equals(pname)) {
                cwriter.printMethod(method);
            }
        }
        cwriter.flush();
    }

    public static Class getProcedureClass(Object pproc) {
        Class procClass;
        if (pproc instanceof ModuleMethod) {
            procClass = ((ModuleMethod) pproc).module.getClass();
        } else {
            procClass = pproc.getClass();
        }
        try {
            if (procClass.getClassLoader() == systemClassLoader) {
                return procClass;
            }
        } catch (SecurityException e) {
        }
        return null;
    }

    public static PrimProcedure getMethodFor(Class procClass, String name, Declaration decl, Expression[] args, Language language) {
        return getMethodFor((ClassType) Type.make(procClass), name, decl, args, language);
    }

    public static PrimProcedure getMethodFor(ClassType procClass, String name, Declaration decl, Expression[] args, Language language) {
        int nargs = args.length;
        Type[] atypes = new Type[nargs];
        int i = nargs;
        while (true) {
            i--;
            if (i < 0) {
                return getMethodFor(procClass, name, decl, atypes, language);
            }
            atypes[i] = args[i].getType();
        }
    }

    public static PrimProcedure getMethodFor(ClassType procClass, String name, Declaration decl, Type[] atypes, Language language) {
        PrimProcedure best = null;
        int bestCode = -1;
        boolean bestIsApply = false;
        if (name == null) {
            return null;
        }
        try {
            String mangledName = Compilation.mangleName(name);
            String mangledNameV = mangledName + "$V";
            String mangledNameVX = mangledName + "$V$X";
            String mangledNameX = mangledName + "$X";
            boolean applyOk = true;
            for (Method meth = procClass.getDeclaredMethods(); meth != null; meth = meth.getNext()) {
                if ((meth.getModifiers() & 9) == 9 || !(decl == null || decl.base == null)) {
                    boolean isApply;
                    String mname = meth.getName();
                    if (mname.equals(mangledName) || mname.equals(mangledNameV) || mname.equals(mangledNameX) || mname.equals(mangledNameVX)) {
                        isApply = false;
                    } else if (applyOk && (mname.equals("apply") || mname.equals("apply$V"))) {
                        isApply = true;
                    }
                    if (!isApply) {
                        applyOk = false;
                        if (bestIsApply) {
                            best = null;
                            bestCode = -1;
                            bestIsApply = false;
                        }
                    }
                    MethodProc prproc = new PrimProcedure(meth, language);
                    prproc.setName(name);
                    int code = prproc.isApplicable(atypes);
                    if (code >= 0 && code >= bestCode) {
                        if (code > bestCode) {
                            best = prproc;
                        } else if (best != null) {
                            best = (PrimProcedure) MethodProc.mostSpecific(best, prproc);
                            if (best == null && bestCode > 0) {
                                return null;
                            }
                        }
                        bestCode = code;
                        bestIsApply = isApply;
                    }
                }
            }
        } catch (SecurityException e) {
        }
        return best;
    }

    public String getName() {
        String name = super.getName();
        if (name != null) {
            return name;
        }
        name = getVerboseName();
        setName(name);
        return name;
    }

    public String getVerboseName() {
        StringBuffer buf = new StringBuffer(100);
        if (this.method == null) {
            buf.append("<op ");
            buf.append(this.op_code);
            buf.append('>');
        } else {
            buf.append(this.method.getDeclaringClass().getName());
            buf.append('.');
            buf.append(this.method.getName());
        }
        buf.append('(');
        for (int i = 0; i < this.argTypes.length; i++) {
            if (i > 0) {
                buf.append(',');
            }
            buf.append(this.argTypes[i].getName());
        }
        buf.append(')');
        return buf.toString();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(100);
        buf.append(this.retType == null ? "<unknown>" : this.retType.getName());
        buf.append(' ');
        buf.append(getVerboseName());
        return buf.toString();
    }

    public void print(PrintWriter ps) {
        ps.print("#<primitive procedure ");
        ps.print(toString());
        ps.print('>');
    }
}
