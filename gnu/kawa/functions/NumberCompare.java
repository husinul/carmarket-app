package gnu.kawa.functions;

import com.google.appinventor.components.common.YaVersion;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.math.IntNum;
import gnu.math.RatNum;

public class NumberCompare extends ProcedureN implements Inlineable {
    static final int RESULT_EQU = 0;
    static final int RESULT_GRT = 1;
    static final int RESULT_LSS = -1;
    static final int RESULT_NAN = -2;
    static final int RESULT_NEQ = -3;
    public static final int TRUE_IF_EQU = 8;
    public static final int TRUE_IF_GRT = 16;
    public static final int TRUE_IF_LSS = 4;
    public static final int TRUE_IF_NAN = 2;
    public static final int TRUE_IF_NEQ = 1;
    int flags;
    Language language;

    public int numArgs() {
        return -4094;
    }

    public static boolean $Eq(Object arg1, Object arg2) {
        return apply2(TRUE_IF_EQU, arg1, arg2);
    }

    public static boolean $Gr(Object arg1, Object arg2) {
        return apply2(TRUE_IF_GRT, arg1, arg2);
    }

    public static boolean $Gr$Eq(Object arg1, Object arg2) {
        return apply2(24, arg1, arg2);
    }

    public static boolean $Ls(Object arg1, Object arg2) {
        return apply2(TRUE_IF_LSS, arg1, arg2);
    }

    public static boolean $Ls$Eq(Object arg1, Object arg2) {
        return apply2(12, arg1, arg2);
    }

    public static boolean $Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Eq(arg1, arg2) && $Eq(arg2, arg3)) {
            return rest.length == 0 || ($Eq(arg3, rest[RESULT_EQU]) && applyN(TRUE_IF_EQU, rest));
        } else {
            return false;
        }
    }

    public static boolean $Gr$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Gr(arg1, arg2) && $Gr(arg2, arg3)) {
            return rest.length == 0 || ($Gr(arg3, rest[RESULT_EQU]) && applyN(TRUE_IF_GRT, rest));
        } else {
            return false;
        }
    }

    public static boolean $Gr$Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Gr$Eq(arg1, arg2) && $Gr$Eq(arg2, arg3)) {
            return rest.length == 0 || ($Gr$Eq(arg3, rest[RESULT_EQU]) && applyN(24, rest));
        } else {
            return false;
        }
    }

    public static boolean $Ls$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Ls(arg1, arg2) && $Ls(arg2, arg3)) {
            return rest.length == 0 || ($Ls(arg3, rest[RESULT_EQU]) && applyN(TRUE_IF_LSS, rest));
        } else {
            return false;
        }
    }

    public static boolean $Ls$Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Ls$Eq(arg1, arg2) && $Ls$Eq(arg2, arg3)) {
            return rest.length == 0 || ($Ls$Eq(arg3, rest[RESULT_EQU]) && applyN(12, rest));
        } else {
            return false;
        }
    }

    public static NumberCompare make(Language language, String name, int flags) {
        NumberCompare proc = new NumberCompare();
        proc.language = language;
        proc.setName(name);
        proc.flags = flags;
        proc.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyNumberCompare");
        return proc;
    }

    protected final Language getLanguage() {
        return this.language;
    }

    public Object apply2(Object arg1, Object arg2) {
        return getLanguage().booleanObject(apply2(this.flags, arg1, arg2));
    }

    public static boolean apply2(int flags, Object arg1, Object arg2) {
        return ((TRUE_IF_NEQ << (compare(arg1, arg2, true) + 3)) & flags) != 0;
    }

    public static boolean checkCompareCode(int code, int flags) {
        return ((TRUE_IF_NEQ << (code + 3)) & flags) != 0;
    }

    public static boolean applyWithPromotion(int flags, Object arg1, Object arg2) {
        return checkCompareCode(compare(arg1, arg2, false), flags);
    }

    public static int compare(Object arg1, Object arg2, boolean exact) {
        return compare(arg1, Arithmetic.classifyValue(arg1), arg2, Arithmetic.classifyValue(arg2), exact);
    }

    public static int compare(Object arg1, int code1, Object arg2, int code2, boolean exact) {
        if (code1 < 0 || code2 < 0) {
            return RESULT_NEQ;
        }
        int code;
        int comp;
        if (code1 < code2) {
            code = code2;
        } else {
            code = code1;
        }
        switch (code) {
            case TRUE_IF_NEQ /*1*/:
                int i1 = Arithmetic.asInt(arg1);
                int i2 = Arithmetic.asInt(arg2);
                comp = i1 < i2 ? RESULT_LSS : i1 > i2 ? TRUE_IF_NEQ : RESULT_EQU;
                return comp;
            case TRUE_IF_NAN /*2*/:
                long l1 = Arithmetic.asLong(arg1);
                long l2 = Arithmetic.asLong(arg2);
                comp = l1 < l2 ? RESULT_LSS : l1 > l2 ? TRUE_IF_NEQ : RESULT_EQU;
                return comp;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                return Arithmetic.asBigInteger(arg1).compareTo(Arithmetic.asBigInteger(arg2));
            case TRUE_IF_LSS /*4*/:
                return IntNum.compare(Arithmetic.asIntNum(arg1), Arithmetic.asIntNum(arg2));
            case ArithOp.DIVIDE_INEXACT /*5*/:
                return Arithmetic.asBigDecimal(arg1).compareTo(Arithmetic.asBigDecimal(arg2));
            case ArithOp.QUOTIENT /*6*/:
                return RatNum.compare(Arithmetic.asRatNum(arg1), Arithmetic.asRatNum(arg2));
            case ArithOp.QUOTIENT_EXACT /*7*/:
                if (!exact || (code1 > 6 && code2 > 6)) {
                    float f1 = Arithmetic.asFloat(arg1);
                    float f2 = Arithmetic.asFloat(arg2);
                    comp = f1 > f2 ? TRUE_IF_NEQ : f1 < f2 ? RESULT_LSS : f1 == f2 ? RESULT_EQU : RESULT_NAN;
                    return comp;
                }
            case TRUE_IF_EQU /*8*/:
            case ArithOp.ASHIFT_GENERAL /*9*/:
                break;
        }
        if (!exact || (code1 > 6 && code2 > 6)) {
            double d1 = Arithmetic.asDouble(arg1);
            double d2 = Arithmetic.asDouble(arg2);
            comp = d1 > d2 ? TRUE_IF_NEQ : d1 < d2 ? RESULT_LSS : d1 == d2 ? RESULT_EQU : RESULT_NAN;
            return comp;
        }
        return Arithmetic.asNumeric(arg1).compare(Arithmetic.asNumeric(arg2));
    }

    static boolean applyN(int flags, Object[] args) {
        for (int i = RESULT_EQU; i < args.length + RESULT_LSS; i += TRUE_IF_NEQ) {
            if (!apply2(flags, args[i], args[i + TRUE_IF_NEQ])) {
                return false;
            }
        }
        return true;
    }

    public Object applyN(Object[] args) {
        return getLanguage().booleanObject(applyN(this.flags, args));
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        int length = args.length;
        if (r0 == TRUE_IF_NAN) {
            Expression arg0 = args[RESULT_EQU];
            Expression arg1 = args[TRUE_IF_NEQ];
            int kind0 = classify(arg0);
            int kind1 = classify(arg1);
            CodeAttr code = comp.getCode();
            if (kind0 > 0 && kind1 > 0 && kind0 <= 10 && kind1 <= 10 && !(kind0 == 6 && kind1 == 6)) {
                if (target instanceof ConditionalTarget) {
                    Type commonType;
                    int opcode;
                    int mask = this.flags;
                    if (mask == TRUE_IF_NEQ) {
                        mask = 20;
                    }
                    if (kind0 <= TRUE_IF_LSS && kind1 <= TRUE_IF_LSS && (kind0 > TRUE_IF_NAN || kind1 > TRUE_IF_NAN)) {
                        Type[] ctypes = new Type[TRUE_IF_NAN];
                        ctypes[RESULT_EQU] = Arithmetic.typeIntNum;
                        if (kind1 <= TRUE_IF_NAN) {
                            ctypes[TRUE_IF_NEQ] = Type.longType;
                        } else if (kind0 > TRUE_IF_NAN || !((arg0 instanceof QuoteExp) || (arg1 instanceof QuoteExp) || (arg0 instanceof ReferenceExp) || (arg1 instanceof ReferenceExp))) {
                            ctypes[TRUE_IF_NEQ] = Arithmetic.typeIntNum;
                        } else {
                            ctypes[TRUE_IF_NEQ] = Type.longType;
                            args = new Expression[TRUE_IF_NAN];
                            args[RESULT_EQU] = arg1;
                            args[TRUE_IF_NEQ] = arg0;
                            if (!(mask == TRUE_IF_EQU || mask == 20)) {
                                mask ^= 20;
                            }
                        }
                        arg0 = new ApplyExp(new PrimProcedure(Arithmetic.typeIntNum.getMethod("compare", ctypes)), args);
                        arg1 = new QuoteExp(IntNum.zero());
                        kind1 = TRUE_IF_NEQ;
                        kind0 = TRUE_IF_NEQ;
                    }
                    if (kind0 <= TRUE_IF_NEQ && kind1 <= TRUE_IF_NEQ) {
                        commonType = Type.intType;
                    } else if (kind0 > TRUE_IF_NAN || kind1 > TRUE_IF_NAN) {
                        commonType = Type.doubleType;
                    } else {
                        commonType = Type.longType;
                    }
                    Target stackTarget = new StackTarget(commonType);
                    ConditionalTarget ctarget = (ConditionalTarget) target;
                    if ((arg0 instanceof QuoteExp) && !(arg1 instanceof QuoteExp)) {
                        Expression tmp = arg1;
                        arg1 = arg0;
                        arg0 = tmp;
                        if (!(mask == TRUE_IF_EQU || mask == 20)) {
                            mask ^= 20;
                        }
                    }
                    Label label1 = ctarget.trueBranchComesFirst ? ctarget.ifFalse : ctarget.ifTrue;
                    if (ctarget.trueBranchComesFirst) {
                        mask ^= 28;
                    }
                    switch (mask) {
                        case TRUE_IF_LSS /*4*/:
                            opcode = 155;
                            break;
                        case TRUE_IF_EQU /*8*/:
                            opcode = 153;
                            break;
                        case ArithOp.LSHIFT_RIGHT /*12*/:
                            opcode = YaVersion.YOUNG_ANDROID_VERSION;
                            break;
                        case TRUE_IF_GRT /*16*/:
                            opcode = 157;
                            break;
                        case Sequence.INT_S16_VALUE /*20*/:
                            opcode = 154;
                            break;
                        case Sequence.INT_S64_VALUE /*24*/:
                            opcode = 156;
                            break;
                        default:
                            opcode = RESULT_EQU;
                            break;
                    }
                    arg0.compile(comp, stackTarget);
                    if (kind0 <= TRUE_IF_NEQ && kind1 <= TRUE_IF_NEQ && (arg1 instanceof QuoteExp)) {
                        Object value = ((QuoteExp) arg1).getValue();
                        if ((value instanceof IntNum) && ((IntNum) value).isZero()) {
                            code.emitGotoIfCompare1(label1, opcode);
                            ctarget.emitGotoFirstBranch(code);
                            return;
                        }
                    }
                    arg1.compile(comp, stackTarget);
                    code.emitGotoIfCompare2(label1, opcode);
                    ctarget.emitGotoFirstBranch(code);
                    return;
                }
                IfExp.compile(exp, QuoteExp.trueExp, QuoteExp.falseExp, comp, target);
                return;
            }
        }
        ApplyExp.compile(exp, comp, target);
    }

    static int classify(Expression exp) {
        int kind = Arithmetic.classifyType(exp.getType());
        if (kind != TRUE_IF_LSS || !(exp instanceof QuoteExp)) {
            return kind;
        }
        Object value = ((QuoteExp) exp).getValue();
        if (!(value instanceof IntNum)) {
            return kind;
        }
        int ilength = ((IntNum) value).intLength();
        if (ilength < 32) {
            return TRUE_IF_NEQ;
        }
        if (ilength < 64) {
            return TRUE_IF_NAN;
        }
        return kind;
    }

    public Type getReturnType(Expression[] args) {
        return Type.booleanType;
    }
}
