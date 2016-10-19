package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.text.SourceMessages;

public class ApplyExp extends Expression {
    public static final int INLINE_IF_CONSTANT = 4;
    public static final int MAY_CONTAIN_BACK_JUMP = 8;
    public static final int TAILCALL = 2;
    Expression[] args;
    LambdaExp context;
    Expression func;
    public ApplyExp nextCall;
    protected Type type;

    public final Expression getFunction() {
        return this.func;
    }

    public final Expression[] getArgs() {
        return this.args;
    }

    public final int getArgCount() {
        return this.args.length;
    }

    public void setFunction(Expression func) {
        this.func = func;
    }

    public void setArgs(Expression[] args) {
        this.args = args;
    }

    public Expression getArg(int i) {
        return this.args[i];
    }

    public void setArg(int i, Expression arg) {
        this.args[i] = arg;
    }

    public final boolean isTailCall() {
        return getFlag(TAILCALL);
    }

    public final void setTailCall(boolean tailCall) {
        setFlag(tailCall, TAILCALL);
    }

    public final Object getFunctionValue() {
        return this.func instanceof QuoteExp ? ((QuoteExp) this.func).getValue() : null;
    }

    public ApplyExp(Expression f, Expression[] a) {
        this.func = f;
        this.args = a;
    }

    public ApplyExp(Procedure p, Expression[] a) {
        this.func = new QuoteExp(p);
        this.args = a;
    }

    public ApplyExp(Method m, Expression[] a) {
        this.func = new QuoteExp(new PrimProcedure(m));
        this.args = a;
    }

    protected boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object proc = this.func.eval(ctx);
        int n = this.args.length;
        Object[] vals = new Object[n];
        for (int i = 0; i < n; i++) {
            vals[i] = this.args[i].eval(ctx);
        }
        ((Procedure) proc).checkN(vals, ctx);
    }

    public static void compileToArray(Expression[] args, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (args.length == 0) {
            code.emitGetStatic(Compilation.noArgsField);
            return;
        }
        code.emitPushInt(args.length);
        code.emitNewArray(Type.pointer_type);
        for (int i = 0; i < args.length; i++) {
            Expression arg = args[i];
            if (!comp.usingCPStyle() || (arg instanceof QuoteExp) || (arg instanceof ReferenceExp)) {
                code.emitDup(Compilation.objArrayType);
                code.emitPushInt(i);
                arg.compileWithPosition(comp, Target.pushObject);
            } else {
                arg.compileWithPosition(comp, Target.pushObject);
                code.emitSwap();
                code.emitDup(1, 1);
                code.emitSwap();
                code.emitPushInt(i);
                code.emitSwap();
            }
            code.emitArrayStore(Type.pointer_type);
        }
    }

    public void compile(Compilation comp, Target target) {
        compile(this, comp, target, true);
    }

    public static void compile(ApplyExp exp, Compilation comp, Target target) {
        compile(exp, comp, target, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void compile(gnu.expr.ApplyExp r34, gnu.expr.Compilation r35, gnu.expr.Target r36, boolean r37) {
        /*
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r0 = r29;
        r6 = r0.length;
        r0 = r34;
        r10 = r0.func;
        r13 = 0;
        r14 = 0;
        r20 = 0;
        r23 = 0;
        r0 = r10 instanceof gnu.expr.LambdaExp;
        r29 = r0;
        if (r29 == 0) goto L_0x005c;
    L_0x0019:
        r13 = r10;
        r13 = (gnu.expr.LambdaExp) r13;
        r14 = r13.getName();
        if (r14 != 0) goto L_0x045e;
    L_0x0022:
        r14 = "<lambda>";
        r29 = r23;
    L_0x0026:
        if (r37 == 0) goto L_0x0126;
    L_0x0028:
        r0 = r29;
        r0 = r0 instanceof gnu.mapping.Procedure;
        r30 = r0;
        if (r30 == 0) goto L_0x0126;
    L_0x0030:
        r22 = r29;
        r22 = (gnu.mapping.Procedure) r22;
        r0 = r36;
        r0 = r0 instanceof gnu.expr.IgnoreTarget;
        r30 = r0;
        if (r30 == 0) goto L_0x00df;
    L_0x003c:
        r30 = r22.isSideEffectFree();
        if (r30 == 0) goto L_0x00df;
    L_0x0042:
        r16 = 0;
    L_0x0044:
        r0 = r16;
        if (r0 >= r6) goto L_0x00ed;
    L_0x0048:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r29 = r29[r16];
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.compile(r1, r2);
        r16 = r16 + 1;
        goto L_0x0044;
    L_0x005c:
        r0 = r10 instanceof gnu.expr.ReferenceExp;
        r29 = r0;
        if (r29 == 0) goto L_0x00cd;
    L_0x0062:
        r15 = r10;
        r15 = (gnu.expr.ReferenceExp) r15;
        r20 = r15.contextDecl();
        r12 = r15.binding;
    L_0x006b:
        if (r12 == 0) goto L_0x0091;
    L_0x006d:
        r29 = r12.isAlias();
        if (r29 == 0) goto L_0x0091;
    L_0x0073:
        r0 = r12.value;
        r29 = r0;
        r0 = r29;
        r0 = r0 instanceof gnu.expr.ReferenceExp;
        r29 = r0;
        if (r29 == 0) goto L_0x0091;
    L_0x007f:
        r15 = r12.value;
        r15 = (gnu.expr.ReferenceExp) r15;
        if (r20 != 0) goto L_0x0091;
    L_0x0085:
        r29 = r12.needsContext();
        if (r29 != 0) goto L_0x0091;
    L_0x008b:
        r0 = r15.binding;
        r29 = r0;
        if (r29 != 0) goto L_0x00c6;
    L_0x0091:
        r30 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r30;
        r29 = r12.getFlag(r0);
        if (r29 != 0) goto L_0x00c2;
    L_0x009c:
        r28 = r12.getValue();
        r14 = r12.getName();
        if (r28 == 0) goto L_0x00b2;
    L_0x00a6:
        r0 = r28;
        r0 = r0 instanceof gnu.expr.LambdaExp;
        r29 = r0;
        if (r29 == 0) goto L_0x00b2;
    L_0x00ae:
        r13 = r28;
        r13 = (gnu.expr.LambdaExp) r13;
    L_0x00b2:
        if (r28 == 0) goto L_0x00c2;
    L_0x00b4:
        r0 = r28;
        r0 = r0 instanceof gnu.expr.QuoteExp;
        r29 = r0;
        if (r29 == 0) goto L_0x00c2;
    L_0x00bc:
        r28 = (gnu.expr.QuoteExp) r28;
        r23 = r28.getValue();
    L_0x00c2:
        r29 = r23;
        goto L_0x0026;
    L_0x00c6:
        r12 = r15.binding;
        r20 = r15.contextDecl();
        goto L_0x006b;
    L_0x00cd:
        r0 = r10 instanceof gnu.expr.QuoteExp;
        r29 = r0;
        if (r29 == 0) goto L_0x045e;
    L_0x00d3:
        r29 = r10;
        r29 = (gnu.expr.QuoteExp) r29;
        r23 = r29.getValue();
        r29 = r23;
        goto L_0x0026;
    L_0x00df:
        r0 = r22;
        r1 = r34;
        r2 = r35;
        r3 = r36;
        r29 = inlineCompile(r0, r1, r2, r3);	 Catch:{ Throwable -> 0x00ee }
        if (r29 == 0) goto L_0x0126;
    L_0x00ed:
        return;
    L_0x00ee:
        r9 = move-exception;
        r30 = r35.getMessages();
        r31 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "caught exception in inline-compiler for ";
        r32 = r32.append(r33);
        r0 = r32;
        r1 = r29;
        r29 = r0.append(r1);
        r32 = " - ";
        r0 = r29;
        r1 = r32;
        r29 = r0.append(r1);
        r0 = r29;
        r29 = r0.append(r9);
        r29 = r29.toString();
        r0 = r30;
        r1 = r31;
        r2 = r29;
        r0.error(r1, r2, r9);
        goto L_0x00ed;
    L_0x0126:
        r7 = r35.getCode();
        if (r13 == 0) goto L_0x01f0;
    L_0x012c:
        r0 = r13.max_args;
        r29 = r0;
        if (r29 < 0) goto L_0x013a;
    L_0x0132:
        r0 = r13.max_args;
        r29 = r0;
        r0 = r29;
        if (r6 > r0) goto L_0x0142;
    L_0x013a:
        r0 = r13.min_args;
        r29 = r0;
        r0 = r29;
        if (r6 >= r0) goto L_0x015d;
    L_0x0142:
        r29 = new java.lang.Error;
        r30 = new java.lang.StringBuilder;
        r30.<init>();
        r31 = "internal error - wrong number of parameters for ";
        r30 = r30.append(r31);
        r0 = r30;
        r30 = r0.append(r13);
        r30 = r30.toString();
        r29.<init>(r30);
        throw r29;
    L_0x015d:
        r8 = r13.getCallConvention();
        r0 = r35;
        r29 = r0.inlineOk(r13);
        if (r29 == 0) goto L_0x01f0;
    L_0x0169:
        r29 = 2;
        r0 = r29;
        if (r8 <= r0) goto L_0x017b;
    L_0x016f:
        r29 = 3;
        r0 = r29;
        if (r8 != r0) goto L_0x01f0;
    L_0x0175:
        r29 = r34.isTailCall();
        if (r29 != 0) goto L_0x01f0;
    L_0x017b:
        r19 = r13.getMethod(r6);
        if (r19 == 0) goto L_0x01f0;
    L_0x0181:
        r21 = new gnu.expr.PrimProcedure;
        r0 = r21;
        r1 = r19;
        r0.<init>(r1, r13);
        r18 = r19.getStaticFlag();
        r11 = 0;
        if (r18 == 0) goto L_0x0197;
    L_0x0191:
        r29 = r13.declareClosureEnv();
        if (r29 == 0) goto L_0x01b3;
    L_0x0197:
        if (r18 == 0) goto L_0x019a;
    L_0x0199:
        r11 = 1;
    L_0x019a:
        r0 = r35;
        r0 = r0.curLambda;
        r29 = r0;
        r0 = r29;
        if (r0 != r13) goto L_0x01cb;
    L_0x01a4:
        r0 = r13.closureEnv;
        r29 = r0;
        if (r29 == 0) goto L_0x01c6;
    L_0x01aa:
        r0 = r13.closureEnv;
        r29 = r0;
    L_0x01ae:
        r0 = r29;
        r7.emitLoad(r0);
    L_0x01b3:
        if (r11 == 0) goto L_0x01ed;
    L_0x01b5:
        r29 = gnu.bytecode.Type.voidType;
    L_0x01b7:
        r0 = r21;
        r1 = r29;
        r2 = r34;
        r3 = r35;
        r4 = r36;
        r0.compile(r1, r2, r3, r4);
        goto L_0x00ed;
    L_0x01c6:
        r0 = r13.thisVariable;
        r29 = r0;
        goto L_0x01ae;
    L_0x01cb:
        if (r20 == 0) goto L_0x01e1;
    L_0x01cd:
        r29 = 0;
        r30 = 0;
        r31 = gnu.expr.Target.pushObject;
        r0 = r20;
        r1 = r29;
        r2 = r30;
        r3 = r35;
        r4 = r31;
        r0.load(r1, r2, r3, r4);
        goto L_0x01b3;
    L_0x01e1:
        r29 = r13.getOwningLambda();
        r0 = r29;
        r1 = r35;
        r0.loadHeapFrame(r1);
        goto L_0x01b3;
    L_0x01ed:
        r29 = 0;
        goto L_0x01b7;
    L_0x01f0:
        r29 = r34.isTailCall();
        if (r29 == 0) goto L_0x024b;
    L_0x01f6:
        if (r13 == 0) goto L_0x024b;
    L_0x01f8:
        r0 = r35;
        r0 = r0.curLambda;
        r29 = r0;
        r0 = r29;
        if (r13 != r0) goto L_0x024b;
    L_0x0202:
        r25 = 1;
    L_0x0204:
        if (r13 == 0) goto L_0x02a2;
    L_0x0206:
        r29 = r13.getInlineOnly();
        if (r29 == 0) goto L_0x02a2;
    L_0x020c:
        if (r25 != 0) goto L_0x02a2;
    L_0x020e:
        r0 = r13.min_args;
        r29 = r0;
        r0 = r29;
        if (r0 != r6) goto L_0x02a2;
    L_0x0216:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r30 = 0;
        r0 = r29;
        r1 = r30;
        r2 = r35;
        pushArgs(r13, r0, r1, r2);
        r29 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r0 = r29;
        r29 = r13.getFlag(r0);
        if (r29 == 0) goto L_0x024e;
    L_0x0231:
        r29 = 0;
        r30 = 0;
        r0 = r29;
        r1 = r30;
        popParams(r7, r13, r0, r1);
        r29 = 0;
        r30 = r13.getVarScope();
        r0 = r29;
        r1 = r30;
        r7.emitTailCall(r0, r1);
        goto L_0x00ed;
    L_0x024b:
        r25 = 0;
        goto L_0x0204;
    L_0x024e:
        r0 = r13.flags;
        r29 = r0;
        r0 = r29;
        r0 = r0 | 128;
        r29 = r0;
        r0 = r29;
        r13.flags = r0;
        r0 = r35;
        r0 = r0.curLambda;
        r24 = r0;
        r0 = r35;
        r0.curLambda = r13;
        r0 = r35;
        r13.allocChildClasses(r0);
        r0 = r35;
        r13.allocParameters(r0);
        r29 = 0;
        r30 = 0;
        r0 = r29;
        r1 = r30;
        popParams(r7, r13, r0, r1);
        r0 = r35;
        r13.enterFunction(r0);
        r0 = r13.body;
        r29 = r0;
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.compileWithPosition(r1, r2);
        r0 = r35;
        r13.compileEnd(r0);
        r0 = r35;
        r13.generateApplyMethods(r0);
        r7.popScope();
        r0 = r24;
        r1 = r35;
        r1.curLambda = r0;
        goto L_0x00ed;
    L_0x02a2:
        r0 = r35;
        r0 = r0.curLambda;
        r29 = r0;
        r29 = r29.isHandlingTailCalls();
        if (r29 == 0) goto L_0x038f;
    L_0x02ae:
        r29 = r34.isTailCall();
        if (r29 != 0) goto L_0x02bc;
    L_0x02b4:
        r0 = r36;
        r0 = r0 instanceof gnu.expr.ConsumerTarget;
        r29 = r0;
        if (r29 == 0) goto L_0x038f;
    L_0x02bc:
        r0 = r35;
        r0 = r0.curLambda;
        r29 = r0;
        r29 = r29.getInlineOnly();
        if (r29 != 0) goto L_0x038f;
    L_0x02c8:
        r27 = gnu.expr.Compilation.typeCallContext;
        r29 = new gnu.expr.StackTarget;
        r30 = gnu.expr.Compilation.typeProcedure;
        r29.<init>(r30);
        r0 = r35;
        r1 = r29;
        r10.compile(r0, r1);
        r29 = 4;
        r0 = r29;
        if (r6 > r0) goto L_0x032a;
    L_0x02de:
        r16 = 0;
    L_0x02e0:
        r0 = r16;
        if (r0 >= r6) goto L_0x02fa;
    L_0x02e4:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r29 = r29[r16];
        r30 = gnu.expr.Target.pushObject;
        r0 = r29;
        r1 = r35;
        r2 = r30;
        r0.compileWithPosition(r1, r2);
        r16 = r16 + 1;
        goto L_0x02e0;
    L_0x02fa:
        r35.loadCallContext();
        r29 = gnu.expr.Compilation.typeProcedure;
        r30 = new java.lang.StringBuilder;
        r30.<init>();
        r31 = "check";
        r30 = r30.append(r31);
        r0 = r30;
        r30 = r0.append(r6);
        r30 = r30.toString();
        r31 = r6 + 1;
        r29 = r29.getDeclaredMethod(r30, r31);
        r0 = r29;
        r7.emitInvoke(r0);
    L_0x031f:
        r29 = r34.isTailCall();
        if (r29 == 0) goto L_0x034a;
    L_0x0325:
        r7.emitReturn();
        goto L_0x00ed;
    L_0x032a:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r0 = r29;
        r1 = r35;
        compileToArray(r0, r1);
        r35.loadCallContext();
        r29 = gnu.expr.Compilation.typeProcedure;
        r30 = "checkN";
        r31 = 2;
        r29 = r29.getDeclaredMethod(r30, r31);
        r0 = r29;
        r7.emitInvoke(r0);
        goto L_0x031f;
    L_0x034a:
        r29 = r36;
        r29 = (gnu.expr.ConsumerTarget) r29;
        r29 = r29.isContextTarget();
        if (r29 == 0) goto L_0x036c;
    L_0x0354:
        r35.loadCallContext();
        r29 = "runUntilDone";
        r30 = 0;
        r0 = r27;
        r1 = r29;
        r2 = r30;
        r29 = r0.getDeclaredMethod(r1, r2);
        r0 = r29;
        r7.emitInvoke(r0);
        goto L_0x00ed;
    L_0x036c:
        r35.loadCallContext();
        r36 = (gnu.expr.ConsumerTarget) r36;
        r29 = r36.getConsumerVariable();
        r0 = r29;
        r7.emitLoad(r0);
        r29 = "runUntilValue";
        r30 = 1;
        r0 = r27;
        r1 = r29;
        r2 = r30;
        r29 = r0.getDeclaredMethod(r1, r2);
        r0 = r29;
        r7.emitInvoke(r0);
        goto L_0x00ed;
    L_0x038f:
        if (r25 != 0) goto L_0x039f;
    L_0x0391:
        r29 = new gnu.expr.StackTarget;
        r30 = gnu.expr.Compilation.typeProcedure;
        r29.<init>(r30);
        r0 = r35;
        r1 = r29;
        r10.compile(r0, r1);
    L_0x039f:
        if (r25 == 0) goto L_0x03dc;
    L_0x03a1:
        r0 = r13.min_args;
        r29 = r0;
        r0 = r13.max_args;
        r30 = r0;
        r0 = r29;
        r1 = r30;
        if (r0 == r1) goto L_0x03d9;
    L_0x03af:
        r26 = 1;
    L_0x03b1:
        r17 = 0;
        if (r26 == 0) goto L_0x03e8;
    L_0x03b5:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r0 = r29;
        r1 = r35;
        compileToArray(r0, r1);
        r19 = gnu.expr.Compilation.applyNmethod;
    L_0x03c4:
        r29 = r7.reachableHere();
        if (r29 != 0) goto L_0x0434;
    L_0x03ca:
        r29 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r30 = "unreachable code";
        r0 = r35;
        r1 = r29;
        r2 = r30;
        r0.error(r1, r2);
        goto L_0x00ed;
    L_0x03d9:
        r26 = 0;
        goto L_0x03b1;
    L_0x03dc:
        r29 = 4;
        r0 = r29;
        if (r6 <= r0) goto L_0x03e5;
    L_0x03e2:
        r26 = 1;
        goto L_0x03b1;
    L_0x03e5:
        r26 = 0;
        goto L_0x03b1;
    L_0x03e8:
        if (r25 == 0) goto L_0x040d;
    L_0x03ea:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r0 = r29;
        r0 = r0.length;
        r29 = r0;
        r0 = r29;
        r0 = new int[r0];
        r17 = r0;
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r0 = r29;
        r1 = r17;
        r2 = r35;
        pushArgs(r13, r0, r1, r2);
        r19 = 0;
        goto L_0x03c4;
    L_0x040d:
        r16 = 0;
    L_0x040f:
        r0 = r16;
        if (r0 >= r6) goto L_0x042c;
    L_0x0413:
        r0 = r34;
        r0 = r0.args;
        r29 = r0;
        r29 = r29[r16];
        r30 = gnu.expr.Target.pushObject;
        r0 = r29;
        r1 = r35;
        r2 = r30;
        r0.compileWithPosition(r1, r2);
        r29 = r7.reachableHere();
        if (r29 != 0) goto L_0x0431;
    L_0x042c:
        r29 = gnu.expr.Compilation.applymethods;
        r19 = r29[r6];
        goto L_0x03c4;
    L_0x0431:
        r16 = r16 + 1;
        goto L_0x040f;
    L_0x0434:
        if (r25 == 0) goto L_0x044c;
    L_0x0436:
        r0 = r17;
        r1 = r26;
        popParams(r7, r13, r0, r1);
        r29 = 0;
        r30 = r13.getVarScope();
        r0 = r29;
        r1 = r30;
        r7.emitTailCall(r0, r1);
        goto L_0x00ed;
    L_0x044c:
        r0 = r19;
        r7.emitInvokeVirtual(r0);
        r29 = gnu.bytecode.Type.pointer_type;
        r0 = r36;
        r1 = r35;
        r2 = r29;
        r0.compileFromStack(r1, r2);
        goto L_0x00ed;
    L_0x045e:
        r29 = r23;
        goto L_0x0026;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ApplyExp.compile(gnu.expr.ApplyExp, gnu.expr.Compilation, gnu.expr.Target, boolean):void");
    }

    public Expression deepCopy(IdentityHashTable mapper) {
        Expression f = Expression.deepCopy(this.func, mapper);
        Expression[] a = Expression.deepCopy(this.args, mapper);
        if ((f == null && this.func != null) || (a == null && this.args != null)) {
            return null;
        }
        Expression copy = new ApplyExp(f, a);
        copy.flags = getFlags();
        return copy;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitApplyExp(this, d);
    }

    public void visitArgs(InlineCalls visitor) {
        this.args = visitor.visitExps(this.args, this.args.length, null);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.func = visitor.visitAndUpdate(this.func, d);
        if (visitor.exitValue == null) {
            this.args = visitor.visitExps(this.args, this.args.length, d);
        }
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Apply", ")", (int) TAILCALL);
        if (isTailCall()) {
            out.print(" [tailcall]");
        }
        if (!(this.type == null || this.type == Type.pointer_type)) {
            out.print(" => ");
            out.print(this.type);
        }
        out.writeSpaceFill();
        printLineColumn(out);
        this.func.print(out);
        for (Expression print : this.args) {
            out.writeSpaceLinear();
            print.print(out);
        }
        out.endLogicalBlock(")");
    }

    private static void pushArgs(LambdaExp lexp, Expression[] args, int[] incValues, Compilation comp) {
        Declaration param = lexp.firstDecl();
        int args_length = args.length;
        for (int i = 0; i < args_length; i++) {
            Expression arg = args[i];
            if (param.ignorable()) {
                arg.compile(comp, Target.Ignore);
            } else {
                if (incValues != null) {
                    int canUseInc = SetExp.canUseInc(arg, param);
                    incValues[i] = canUseInc;
                    if (canUseInc != ModuleExp.NONSTATIC_SPECIFIED) {
                    }
                }
                arg.compileWithPosition(comp, StackTarget.getInstance(param.getType()));
            }
            param = param.nextDecl();
        }
    }

    private static void popParams(CodeAttr code, LambdaExp lexp, int[] incValues, boolean toArray) {
        Variable vars = lexp.getVarScope().firstVar();
        Declaration decls = lexp.firstDecl();
        if (vars != null && vars.getName() == "this") {
            vars = vars.nextVar();
        }
        if (vars != null && vars.getName() == "$ctx") {
            vars = vars.nextVar();
        }
        if (vars != null && vars.getName() == "argsArray") {
            if (toArray) {
                popParams(code, 0, 1, null, decls, vars);
                return;
            }
            vars = vars.nextVar();
        }
        popParams(code, 0, lexp.min_args, incValues, decls, vars);
    }

    private static void popParams(CodeAttr code, int paramNo, int count, int[] incValues, Declaration decl, Variable vars) {
        if (count > 0) {
            popParams(code, paramNo + 1, count - 1, incValues, decl.nextDecl(), decl.getVariable() == null ? vars : vars.nextVar());
            if (!decl.ignorable()) {
                if (incValues == null || incValues[paramNo] == 65536) {
                    code.emitStore(vars);
                } else {
                    code.emitInc(vars, (short) incValues[paramNo]);
                }
            }
        }
    }

    public final Type getTypeRaw() {
        return this.type;
    }

    public final void setType(Type type) {
        this.type = type;
    }

    public boolean side_effects() {
        Object value = derefFunc(this.func).valueIfConstant();
        if (!(value instanceof Procedure) || !((Procedure) value).isSideEffectFree()) {
            return true;
        }
        for (Expression side_effects : this.args) {
            if (side_effects.side_effects()) {
                return true;
            }
        }
        return false;
    }

    static Expression derefFunc(Expression afunc) {
        if (!(afunc instanceof ReferenceExp)) {
            return afunc;
        }
        Declaration func_decl = Declaration.followAliases(((ReferenceExp) afunc).binding);
        if (func_decl == null || func_decl.getFlag(65536)) {
            return afunc;
        }
        return func_decl.getValue();
    }

    public final Type getType() {
        if (this.type != null) {
            return this.type;
        }
        Expression afunc = derefFunc(this.func);
        this.type = Type.objectType;
        if (afunc instanceof QuoteExp) {
            Object value = ((QuoteExp) afunc).getValue();
            if (value instanceof Procedure) {
                this.type = ((Procedure) value).getReturnType(this.args);
            }
        } else if (afunc instanceof LambdaExp) {
            this.type = ((LambdaExp) afunc).getReturnType();
        }
        return this.type;
    }

    public static Inlineable asInlineable(Procedure proc) {
        if (proc instanceof Inlineable) {
            return (Inlineable) proc;
        }
        return (Inlineable) Procedure.compilerKey.get(proc);
    }

    static boolean inlineCompile(Procedure proc, ApplyExp exp, Compilation comp, Target target) throws Throwable {
        Inlineable compiler = asInlineable(proc);
        if (compiler == null) {
            return false;
        }
        compiler.compile(exp, comp, target);
        return true;
    }

    public final Expression inlineIfConstant(Procedure proc, InlineCalls visitor) {
        return inlineIfConstant(proc, visitor.getMessages());
    }

    public final Expression inlineIfConstant(Procedure proc, SourceMessages messages) {
        int len = this.args.length;
        Object[] vals = new Object[len];
        int i = len;
        while (true) {
            i--;
            if (i >= 0) {
                Expression arg = this.args[i];
                if (arg instanceof ReferenceExp) {
                    Declaration decl = ((ReferenceExp) arg).getBinding();
                    if (decl != null) {
                        arg = decl.getValue();
                        if (arg == QuoteExp.undefined_exp) {
                            return this;
                        }
                    }
                }
                if (!(arg instanceof QuoteExp)) {
                    return this;
                }
                vals[i] = ((QuoteExp) arg).getValue();
            } else {
                try {
                    return new QuoteExp(proc.applyN(vals), this.type);
                } catch (Throwable ex) {
                    if (messages == null) {
                        return this;
                    }
                    messages.error('w', "call to " + proc + " throws " + ex);
                    return this;
                }
            }
        }
    }

    public String toString() {
        if (this == LambdaExp.unknownContinuation) {
            return "ApplyExp[unknownContinuation]";
        }
        return "ApplyExp/" + (this.args == null ? 0 : this.args.length) + '[' + this.func + ']';
    }
}
