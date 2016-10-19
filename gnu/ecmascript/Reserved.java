package gnu.ecmascript;

import gnu.mapping.Procedure;

public class Reserved {
    public static final int BREAK_TOKEN = 35;
    public static final int CONTINUE_TOKEN = 34;
    public static final int ELSE_TOKEN = 38;
    public static final int FOR_TOKEN = 33;
    public static final int FUNCTION_TOKEN = 41;
    public static final int IF_TOKEN = 31;
    public static final int LESS_OP = 5;
    public static final int LSHIFT_OP = 4;
    public static final int MINUS_OP = 2;
    public static final int NEW_TOKEN = 39;
    public static final int PLUS_OP = 1;
    public static final int RETURN_TOKEN = 36;
    public static final int THIS_TOKEN = 40;
    public static final int TIMES_OP = 3;
    public static final int VAR_TOKEN = 30;
    public static final int WHILE_TOKEN = 32;
    public static final int WITH_TOKEN = 37;
    static final Reserved opBitAnd;
    static final Reserved opBitOr;
    static final Reserved opBitXor;
    static final Reserved opBoolAnd;
    static final Reserved opBoolOr;
    static final Reserved opDivide;
    static final Reserved opEqual;
    static final Reserved opGreater;
    static final Reserved opGreaterEqual;
    static final Reserved opLess;
    static final Reserved opLessEqual;
    static final Reserved opLshift;
    static final Reserved opMinus;
    static Reserved opMinusMinus;
    static final Reserved opNotEqual;
    static final Reserved opPlus;
    static Reserved opPlusPlus;
    static final Reserved opRemainder;
    static final Reserved opRshiftSigned;
    static final Reserved opRshiftUnsigned;
    static final Reserved opTimes;
    String name;
    int prio;
    Procedure proc;

    public Reserved(String name, int prio, Procedure proc) {
        this.name = name;
        this.prio = prio;
        this.proc = proc;
    }

    public Reserved(String name, int prio) {
        this.name = name;
        this.prio = prio;
    }

    public Reserved(String name, int prio, int op) {
        this.name = name;
        this.prio = prio;
        this.proc = new BinaryOp(name, op);
    }

    static {
        opBoolOr = new Reserved("||", (int) PLUS_OP, 0);
        opBoolAnd = new Reserved("&&", (int) MINUS_OP, 0);
        opBitOr = new Reserved("|", (int) TIMES_OP, 0);
        opBitXor = new Reserved("^", (int) LSHIFT_OP, 0);
        opBitAnd = new Reserved("&", (int) LESS_OP, 0);
        opEqual = new Reserved("=", 6, 0);
        opNotEqual = new Reserved("!=", 6, 0);
        opLess = new Reserved("<", 7, (int) LESS_OP);
        opGreater = new Reserved(">", 7, 0);
        opLessEqual = new Reserved("<=", 7, 0);
        opGreaterEqual = new Reserved(">=", 7, 0);
        opLshift = new Reserved("<<", 8, (int) LSHIFT_OP);
        opRshiftSigned = new Reserved(">>", 8, 0);
        opRshiftUnsigned = new Reserved(">>>", 8, 0);
        opPlus = new Reserved("+", 9, (int) PLUS_OP);
        opMinus = new Reserved("-", 9, (int) MINUS_OP);
        opTimes = new Reserved("*", 10, (int) TIMES_OP);
        opDivide = new Reserved("/", 10, 0);
        opRemainder = new Reserved("%", 10, 0);
    }

    public String toString() {
        return "[Reserved \"" + this.name + "\" prio:" + this.prio + "]";
    }

    public boolean isAssignmentOp() {
        return false;
    }
}
