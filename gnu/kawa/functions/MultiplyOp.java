package gnu.kawa.functions;

import gnu.expr.SetExp;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;

public class MultiplyOp extends ArithOp {
    public static final MultiplyOp $St;

    static {
        $St = new MultiplyOp("*");
    }

    public MultiplyOp(String name) {
        super(name, 3);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forMul");
    }

    public Object defaultResult() {
        return IntNum.one();
    }

    public static Object apply(Object arg1, Object arg2) {
        return ((Numeric) arg1).mul(arg2);
    }

    public Object applyN(Object[] args) {
        int len = args.length;
        if (len == 0) {
            return IntNum.one();
        }
        Object result = args[0];
        int code = Arithmetic.classifyValue(result);
        for (int i = 1; i < len; i++) {
            Object arg2 = args[i];
            int code2 = Arithmetic.classifyValue(arg2);
            if (code < code2) {
                code = code2;
            }
            switch (code) {
                case ParseFormat.SEEN_MINUS /*1*/:
                    result = new Integer(Arithmetic.asInt(result) * Arithmetic.asInt(arg2));
                    break;
                case SetExp.DEFINING_FLAG /*2*/:
                    result = new Long(Arithmetic.asLong(result) * Arithmetic.asLong(arg2));
                    break;
                case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                    result = Arithmetic.asBigInteger(result).multiply(Arithmetic.asBigInteger(arg2));
                    break;
                case SetExp.GLOBAL_FLAG /*4*/:
                    result = IntNum.times(Arithmetic.asIntNum(result), Arithmetic.asIntNum(arg2));
                    break;
                case ArithOp.DIVIDE_INEXACT /*5*/:
                    result = Arithmetic.asBigDecimal(result).multiply(Arithmetic.asBigDecimal(arg2));
                    break;
                case ArithOp.QUOTIENT /*6*/:
                    result = RatNum.times(Arithmetic.asRatNum(result), Arithmetic.asRatNum(arg2));
                    break;
                case ArithOp.QUOTIENT_EXACT /*7*/:
                    result = new Float(Arithmetic.asFloat(result) * Arithmetic.asFloat(arg2));
                    break;
                case SetExp.PREFER_BINDING2 /*8*/:
                    result = new Double(Arithmetic.asDouble(result) * Arithmetic.asDouble(arg2));
                    break;
                case ArithOp.ASHIFT_GENERAL /*9*/:
                    result = new DFloNum(Arithmetic.asDouble(result) * Arithmetic.asDouble(arg2));
                    break;
                default:
                    result = Arithmetic.asNumeric(result).mul(Arithmetic.asNumeric(arg2));
                    break;
            }
        }
        return result;
    }
}
