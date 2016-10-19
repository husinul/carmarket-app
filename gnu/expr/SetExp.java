package gnu.expr;

import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.AddOp;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.math.IntNum;

public class SetExp extends AccessExp {
    public static final int BAD_SHORT = 65536;
    public static final int DEFINING_FLAG = 2;
    public static final int GLOBAL_FLAG = 4;
    public static final int HAS_VALUE = 64;
    public static final int PREFER_BINDING2 = 8;
    public static final int PROCEDURE = 16;
    public static final int SET_IF_UNBOUND = 32;
    Expression new_value;

    public SetExp(Object symbol, Expression val) {
        this.symbol = symbol;
        this.new_value = val;
    }

    public SetExp(Declaration decl, Expression val) {
        this.binding = decl;
        this.symbol = decl.getSymbol();
        this.new_value = val;
    }

    public static SetExp makeDefinition(Object symbol, Expression val) {
        SetExp sexp = new SetExp(symbol, val);
        sexp.setDefining(true);
        return sexp;
    }

    public static SetExp makeDefinition(Declaration decl, Expression val) {
        SetExp sexp = new SetExp(decl, val);
        sexp.setDefining(true);
        return sexp;
    }

    public final Expression getNewValue() {
        return this.new_value;
    }

    public final boolean isDefining() {
        return (this.flags & DEFINING_FLAG) != 0;
    }

    public final void setDefining(boolean value) {
        if (value) {
            this.flags |= DEFINING_FLAG;
        } else {
            this.flags &= -3;
        }
    }

    public final boolean getHasValue() {
        return (this.flags & HAS_VALUE) != 0;
    }

    public final void setHasValue(boolean value) {
        if (value) {
            this.flags |= HAS_VALUE;
        } else {
            this.flags &= -65;
        }
    }

    public final boolean isFuncDef() {
        return (this.flags & PROCEDURE) != 0;
    }

    public final void setFuncDef(boolean value) {
        if (value) {
            this.flags |= PROCEDURE;
        } else {
            this.flags &= -17;
        }
    }

    public final boolean isSetIfUnbound() {
        return (this.flags & SET_IF_UNBOUND) != 0;
    }

    public final void setSetIfUnbound(boolean value) {
        if (value) {
            this.flags |= SET_IF_UNBOUND;
        } else {
            this.flags &= -33;
        }
    }

    protected boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Symbol sym;
        Environment env = Environment.getCurrent();
        if (this.symbol instanceof Symbol) {
            sym = (Symbol) this.symbol;
        } else {
            sym = env.getSymbol(this.symbol.toString());
        }
        Object property = null;
        Language language = Language.getDefaultLanguage();
        if (isFuncDef() && language.hasSeparateFunctionNamespace()) {
            property = EnvironmentKey.FUNCTION;
        }
        if (isSetIfUnbound()) {
            Location loc = env.getLocation(sym, property);
            if (!loc.isBound()) {
                loc.set(this.new_value.eval(env));
            }
            if (getHasValue()) {
                ctx.writeValue(loc);
                return;
            }
            return;
        }
        Object new_val = this.new_value.eval(env);
        if (this.binding != null && !(this.binding.context instanceof ModuleExp)) {
            Object[] evalFrame = ctx.evalFrames[ScopeExp.nesting(this.binding.context)];
            if (this.binding.isIndirectBinding()) {
                if (isDefining()) {
                    evalFrame[this.binding.evalIndex] = Location.make(sym);
                }
                ((Location) evalFrame[this.binding.evalIndex]).set(this.new_value);
            } else {
                evalFrame[this.binding.evalIndex] = new_val;
            }
        } else if (isDefining()) {
            env.define(sym, property, new_val);
        } else {
            env.put(sym, property, new_val);
        }
        if (getHasValue()) {
            ctx.writeValue(new_val);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compile(gnu.expr.Compilation r28, gnu.expr.Target r29) {
        /*
        r27 = this;
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        r0 = r0 instanceof gnu.expr.LambdaExp;
        r24 = r0;
        if (r24 == 0) goto L_0x0025;
    L_0x000e:
        r0 = r29;
        r0 = r0 instanceof gnu.expr.IgnoreTarget;
        r24 = r0;
        if (r24 == 0) goto L_0x0025;
    L_0x0016:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r24 = (gnu.expr.LambdaExp) r24;
        r24 = r24.getInlineOnly();
        if (r24 == 0) goto L_0x0025;
    L_0x0024:
        return;
    L_0x0025:
        r6 = r28.getCode();
        r24 = r27.getHasValue();
        if (r24 == 0) goto L_0x0089;
    L_0x002f:
        r0 = r29;
        r0 = r0 instanceof gnu.expr.IgnoreTarget;
        r24 = r0;
        if (r24 != 0) goto L_0x0089;
    L_0x0037:
        r13 = 1;
    L_0x0038:
        r22 = 0;
        r0 = r27;
        r7 = r0.binding;
        r8 = r7.getValue();
        r0 = r8 instanceof gnu.expr.LambdaExp;
        r24 = r0;
        if (r24 == 0) goto L_0x008b;
    L_0x0048:
        r0 = r7.context;
        r24 = r0;
        r0 = r24;
        r0 = r0 instanceof gnu.expr.ModuleExp;
        r24 = r0;
        if (r24 == 0) goto L_0x008b;
    L_0x0054:
        r24 = r7.ignorable();
        if (r24 != 0) goto L_0x008b;
    L_0x005a:
        r24 = r8;
        r24 = (gnu.expr.LambdaExp) r24;
        r24 = r24.getName();
        if (r24 == 0) goto L_0x008b;
    L_0x0064:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        if (r8 != r0) goto L_0x008b;
    L_0x006e:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r24 = (gnu.expr.LambdaExp) r24;
        r0 = r24;
        r1 = r28;
        r0.compileSetField(r1);
    L_0x007d:
        if (r13 == 0) goto L_0x0307;
    L_0x007f:
        if (r22 != 0) goto L_0x0307;
    L_0x0081:
        r24 = new java.lang.Error;
        r25 = "SetExp.compile: not implemented - return value";
        r24.<init>(r25);
        throw r24;
    L_0x0089:
        r13 = 0;
        goto L_0x0038;
    L_0x008b:
        r24 = r7.shouldEarlyInit();
        if (r24 != 0) goto L_0x0097;
    L_0x0091:
        r24 = r7.isAlias();
        if (r24 == 0) goto L_0x00d6;
    L_0x0097:
        r0 = r7.context;
        r24 = r0;
        r0 = r24;
        r0 = r0 instanceof gnu.expr.ModuleExp;
        r24 = r0;
        if (r24 == 0) goto L_0x00d6;
    L_0x00a3:
        r24 = r27.isDefining();
        if (r24 == 0) goto L_0x00d6;
    L_0x00a9:
        r24 = r7.ignorable();
        if (r24 != 0) goto L_0x00d6;
    L_0x00af:
        r24 = r7.shouldEarlyInit();
        if (r24 == 0) goto L_0x00c2;
    L_0x00b5:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        r1 = r28;
        gnu.expr.BindingInitializer.create(r7, r0, r1);
    L_0x00c2:
        if (r13 == 0) goto L_0x007d;
    L_0x00c4:
        r24 = 0;
        r25 = gnu.expr.Target.pushObject;
        r0 = r27;
        r1 = r24;
        r2 = r28;
        r3 = r25;
        r7.load(r0, r1, r2, r3);
        r22 = 1;
        goto L_0x007d;
    L_0x00d6:
        r4 = r27;
        r15 = r27.contextDecl();
        r24 = r27.isDefining();
        if (r24 != 0) goto L_0x00f4;
    L_0x00e2:
        if (r7 == 0) goto L_0x00f4;
    L_0x00e4:
        r24 = r7.isAlias();
        if (r24 == 0) goto L_0x00f4;
    L_0x00ea:
        r8 = r7.getValue();
        r0 = r8 instanceof gnu.expr.ReferenceExp;
        r24 = r0;
        if (r24 != 0) goto L_0x010d;
    L_0x00f4:
        r24 = r7.ignorable();
        if (r24 == 0) goto L_0x0127;
    L_0x00fa:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r25 = gnu.expr.Target.Ignore;
        r0 = r24;
        r1 = r28;
        r2 = r25;
        r0.compile(r1, r2);
        goto L_0x007d;
    L_0x010d:
        r16 = r8;
        r16 = (gnu.expr.ReferenceExp) r16;
        r0 = r16;
        r14 = r0.binding;
        if (r14 == 0) goto L_0x00f4;
    L_0x0117:
        if (r15 == 0) goto L_0x011f;
    L_0x0119:
        r24 = r14.needsContext();
        if (r24 != 0) goto L_0x00f4;
    L_0x011f:
        r15 = r16.contextDecl();
        r4 = r16;
        r7 = r14;
        goto L_0x00e2;
    L_0x0127:
        r24 = r7.isAlias();
        if (r24 == 0) goto L_0x016d;
    L_0x012d:
        r24 = r27.isDefining();
        if (r24 == 0) goto L_0x016d;
    L_0x0133:
        r24 = 2;
        r25 = gnu.expr.Target.pushObject;
        r0 = r27;
        r1 = r24;
        r2 = r28;
        r3 = r25;
        r7.load(r0, r1, r2, r3);
        r24 = "gnu.mapping.IndirectableLocation";
        r11 = gnu.bytecode.ClassType.make(r24);
        r6.emitCheckcast(r11);
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r25 = gnu.expr.Target.pushObject;
        r0 = r24;
        r1 = r28;
        r2 = r25;
        r0.compile(r1, r2);
        r24 = "setAlias";
        r25 = 1;
        r0 = r24;
        r1 = r25;
        r12 = r11.getDeclaredMethod(r0, r1);
        r6.emitInvokeVirtual(r12);
        goto L_0x007d;
    L_0x016d:
        r24 = r7.isIndirectBinding();
        if (r24 == 0) goto L_0x01f8;
    L_0x0173:
        r24 = 2;
        r25 = gnu.expr.Target.pushObject;
        r0 = r24;
        r1 = r28;
        r2 = r25;
        r7.load(r4, r0, r1, r2);
        r24 = r27.isSetIfUnbound();
        if (r24 == 0) goto L_0x01b7;
    L_0x0186:
        if (r13 == 0) goto L_0x018d;
    L_0x0188:
        r6.emitDup();
        r22 = 1;
    L_0x018d:
        r6.pushScope();
        r6.emitDup();
        r24 = gnu.expr.Compilation.typeLocation;
        r0 = r24;
        r20 = r6.addLocal(r0);
        r0 = r20;
        r6.emitStore(r0);
        r24 = gnu.expr.Compilation.typeLocation;
        r25 = "isBound";
        r26 = 0;
        r24 = r24.getDeclaredMethod(r25, r26);
        r0 = r24;
        r6.emitInvokeVirtual(r0);
        r6.emitIfIntEqZero();
        r0 = r20;
        r6.emitLoad(r0);
    L_0x01b7:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r25 = gnu.expr.Target.pushObject;
        r0 = r24;
        r1 = r28;
        r2 = r25;
        r0.compile(r1, r2);
        if (r13 == 0) goto L_0x01d5;
    L_0x01ca:
        r24 = r27.isSetIfUnbound();
        if (r24 != 0) goto L_0x01d5;
    L_0x01d0:
        r6.emitDupX();
        r22 = 1;
    L_0x01d5:
        r19 = "set";
        r24 = gnu.expr.Compilation.typeLocation;
        r25 = 1;
        r0 = r24;
        r1 = r19;
        r2 = r25;
        r24 = r0.getDeclaredMethod(r1, r2);
        r0 = r24;
        r6.emitInvokeVirtual(r0);
        r24 = r27.isSetIfUnbound();
        if (r24 == 0) goto L_0x007d;
    L_0x01f0:
        r6.emitFi();
        r6.popScope();
        goto L_0x007d;
    L_0x01f8:
        r24 = r7.isSimple();
        if (r24 == 0) goto L_0x0256;
    L_0x01fe:
        r21 = r7.getType();
        r23 = r7.getVariable();
        if (r23 != 0) goto L_0x020c;
    L_0x0208:
        r23 = r7.allocateVariable(r6);
    L_0x020c:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        r9 = canUseInc(r0, r7);
        r24 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r24;
        if (r9 == r0) goto L_0x0239;
    L_0x021e:
        r24 = r28.getCode();
        r0 = (short) r9;
        r25 = r0;
        r0 = r24;
        r1 = r23;
        r2 = r25;
        r0.emitInc(r1, r2);
        if (r13 == 0) goto L_0x007d;
    L_0x0230:
        r0 = r23;
        r6.emitLoad(r0);
        r22 = 1;
        goto L_0x007d;
    L_0x0239:
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        r1 = r28;
        r0.compile(r1, r7);
        if (r13 == 0) goto L_0x024f;
    L_0x0248:
        r0 = r21;
        r6.emitDup(r0);
        r22 = 1;
    L_0x024f:
        r0 = r23;
        r6.emitStore(r0);
        goto L_0x007d;
    L_0x0256:
        r0 = r7.context;
        r24 = r0;
        r0 = r24;
        r0 = r0 instanceof gnu.expr.ClassExp;
        r24 = r0;
        if (r24 == 0) goto L_0x02be;
    L_0x0262:
        r0 = r7.field;
        r24 = r0;
        if (r24 != 0) goto L_0x02be;
    L_0x0268:
        r24 = 16;
        r0 = r27;
        r1 = r24;
        r24 = r0.getFlag(r1);
        if (r24 != 0) goto L_0x02be;
    L_0x0274:
        r0 = r7.context;
        r24 = r0;
        r24 = (gnu.expr.ClassExp) r24;
        r24 = r24.isMakingClassPair();
        if (r24 == 0) goto L_0x02be;
    L_0x0280:
        r24 = "set";
        r25 = r7.getName();
        r17 = gnu.expr.ClassExp.slotToMethodName(r24, r25);
        r5 = r7.context;
        r5 = (gnu.expr.ClassExp) r5;
        r0 = r5.type;
        r24 = r0;
        r25 = 1;
        r0 = r24;
        r1 = r17;
        r2 = r25;
        r18 = r0.getDeclaredMethod(r1, r2);
        r0 = r28;
        r5.loadHeapFrame(r0);
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        r1 = r28;
        r0.compile(r1, r7);
        if (r13 == 0) goto L_0x02b7;
    L_0x02b2:
        r6.emitDupX();
        r22 = 1;
    L_0x02b7:
        r0 = r18;
        r6.emitInvoke(r0);
        goto L_0x007d;
    L_0x02be:
        r10 = r7.field;
        r24 = r10.getStaticFlag();
        if (r24 != 0) goto L_0x02cb;
    L_0x02c6:
        r0 = r28;
        r7.loadOwningObject(r15, r0);
    L_0x02cb:
        r21 = r10.getType();
        r0 = r27;
        r0 = r0.new_value;
        r24 = r0;
        r0 = r24;
        r1 = r28;
        r0.compile(r1, r7);
        r24 = r10.getDeclaringClass();
        r0 = r28;
        r1 = r24;
        r0.usedClass(r1);
        r24 = r10.getStaticFlag();
        if (r24 == 0) goto L_0x02fb;
    L_0x02ed:
        if (r13 == 0) goto L_0x02f6;
    L_0x02ef:
        r0 = r21;
        r6.emitDup(r0);
        r22 = 1;
    L_0x02f6:
        r6.emitPutStatic(r10);
        goto L_0x007d;
    L_0x02fb:
        if (r13 == 0) goto L_0x0302;
    L_0x02fd:
        r6.emitDupX();
        r22 = 1;
    L_0x0302:
        r6.emitPutField(r10);
        goto L_0x007d;
    L_0x0307:
        if (r13 == 0) goto L_0x0318;
    L_0x0309:
        r24 = r27.getType();
        r0 = r29;
        r1 = r28;
        r2 = r24;
        r0.compileFromStack(r1, r2);
        goto L_0x0024;
    L_0x0318:
        r24 = gnu.mapping.Values.empty;
        r0 = r28;
        r1 = r24;
        r2 = r29;
        r0.compileConstant(r1, r2);
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.SetExp.compile(gnu.expr.Compilation, gnu.expr.Target):void");
    }

    public static int canUseInc(Expression rhs, Declaration target) {
        Variable var = target.getVariable();
        if (target.isSimple() && var.getType().getImplementationType().promote() == Type.intType && (rhs instanceof ApplyExp)) {
            ApplyExp aexp = (ApplyExp) rhs;
            if (aexp.getArgCount() == DEFINING_FLAG) {
                int sign;
                AddOp func = aexp.getFunction().valueIfConstant();
                if (func == AddOp.$Pl) {
                    sign = 1;
                } else if (func == AddOp.$Mn) {
                    sign = -1;
                }
                Expression arg0 = aexp.getArg(0);
                Expression arg1 = aexp.getArg(1);
                if ((arg0 instanceof QuoteExp) && sign > 0) {
                    Expression tmp = arg1;
                    arg1 = arg0;
                    arg0 = tmp;
                }
                if (arg0 instanceof ReferenceExp) {
                    ReferenceExp ref0 = (ReferenceExp) arg0;
                    if (ref0.getBinding() == target && !ref0.getDontDereference()) {
                        IntNum value1 = arg1.valueIfConstant();
                        if (value1 instanceof Integer) {
                            short val1 = ((Integer) value1).intValue();
                            if (sign < 0) {
                                val1 = -val1;
                            }
                            short s = (short) val1;
                            if (r0 == val1) {
                                return val1;
                            }
                        } else if (value1 instanceof IntNum) {
                            IntNum int1 = value1;
                            int hi = 32767;
                            int lo = -32767;
                            if (sign > 0) {
                                lo--;
                            } else {
                                hi = 32767 + 1;
                            }
                            if (IntNum.compare(int1, (long) lo) >= 0) {
                                if (IntNum.compare(int1, (long) hi) <= 0) {
                                    return sign * int1.intValue();
                                }
                            }
                        }
                    }
                }
            }
        }
        return BAD_SHORT;
    }

    public final Type getType() {
        if (getHasValue()) {
            return this.binding == null ? Type.pointer_type : this.binding.getType();
        } else {
            return Type.voidType;
        }
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitSetExp(this, d);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.new_value = visitor.visitAndUpdate(this.new_value, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock(isDefining() ? "(Define" : "(Set", ")", (int) DEFINING_FLAG);
        out.writeSpaceFill();
        printLineColumn(out);
        if (this.binding == null || this.symbol.toString() != this.binding.getName()) {
            out.print('/');
            out.print(this.symbol);
        }
        if (this.binding != null) {
            out.print('/');
            out.print(this.binding);
        }
        out.writeSpaceLinear();
        this.new_value.print(out);
        out.endLogicalBlock(")");
    }

    public String toString() {
        return "SetExp[" + this.symbol + ":=" + this.new_value + ']';
    }
}
