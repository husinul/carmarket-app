package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.text.Char;

/* compiled from: characters.scm */
public class characters extends ModuleBody {
    public static final characters $instance;
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    public static final ModuleMethod char$Eq$Qu;
    public static final ModuleMethod char$Gr$Eq$Qu;
    public static final ModuleMethod char$Gr$Qu;
    public static final ModuleMethod char$Ls$Eq$Qu;
    public static final ModuleMethod char$Ls$Qu;
    public static final ModuleMethod char$Mn$Grinteger;
    public static final ModuleMethod char$Qu;
    public static final ModuleMethod integer$Mn$Grchar;

    static {
        Lit7 = (SimpleSymbol) new SimpleSymbol("char>=?").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("char<=?").readResolve();
        Lit5 = (SimpleSymbol) new SimpleSymbol("char>?").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol("char<?").readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("char=?").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("integer->char").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("char->integer").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("char?").readResolve();
        $instance = new characters();
        ModuleBody moduleBody = $instance;
        char$Qu = new ModuleMethod(moduleBody, 1, Lit0, 4097);
        char$Mn$Grinteger = new ModuleMethod(moduleBody, 2, Lit1, 4097);
        integer$Mn$Grchar = new ModuleMethod(moduleBody, 3, Lit2, 4097);
        char$Eq$Qu = new ModuleMethod(moduleBody, 4, Lit3, 8194);
        char$Ls$Qu = new ModuleMethod(moduleBody, 5, Lit4, 8194);
        char$Gr$Qu = new ModuleMethod(moduleBody, 6, Lit5, 8194);
        char$Ls$Eq$Qu = new ModuleMethod(moduleBody, 7, Lit6, 8194);
        char$Gr$Eq$Qu = new ModuleMethod(moduleBody, 8, Lit7, 8194);
        $instance.run();
    }

    public characters() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static boolean isChar(Object x) {
        return x instanceof Char;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.DEFINING_FLAG /*2*/:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static int char$To$Integer(Char char_) {
        return char_.intValue();
    }

    public static Char integer$To$Char(int n) {
        return Char.make(n);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return isChar(obj) ? Boolean.TRUE : Boolean.FALSE;
            case SetExp.DEFINING_FLAG /*2*/:
                try {
                    return Integer.valueOf(char$To$Integer((Char) obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char->integer", 1, obj);
                }
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                try {
                    return integer$To$Char(((Number) obj).intValue());
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "integer->char", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static boolean isChar$Eq(Char c1, Char c2) {
        return c1.intValue() == c2.intValue();
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case SetExp.GLOBAL_FLAG /*4*/:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.DIVIDE_INEXACT /*5*/:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.QUOTIENT /*6*/:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.QUOTIENT_EXACT /*7*/:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case SetExp.PREFER_BINDING2 /*8*/:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static boolean isChar$Ls(Char c1, Char c2) {
        return c1.intValue() < c2.intValue();
    }

    public static boolean isChar$Gr(Char c1, Char c2) {
        return c1.intValue() > c2.intValue();
    }

    public static boolean isChar$Ls$Eq(Char c1, Char c2) {
        return c1.intValue() <= c2.intValue();
    }

    public static boolean isChar$Gr$Eq(Char c1, Char c2) {
        return c1.intValue() >= c2.intValue();
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case SetExp.GLOBAL_FLAG /*4*/:
                try {
                    try {
                        return isChar$Eq((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "char=?", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "char=?", 1, obj);
                }
            case ArithOp.DIVIDE_INEXACT /*5*/:
                try {
                    try {
                        return isChar$Ls((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "char<?", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "char<?", 1, obj);
                }
            case ArithOp.QUOTIENT /*6*/:
                try {
                    try {
                        return isChar$Gr((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "char>?", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "char>?", 1, obj);
                }
            case ArithOp.QUOTIENT_EXACT /*7*/:
                try {
                    try {
                        return isChar$Ls$Eq((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "char<=?", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "char<=?", 1, obj);
                }
            case SetExp.PREFER_BINDING2 /*8*/:
                try {
                    try {
                        return isChar$Gr$Eq((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "char>=?", 2, obj2);
                    }
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "char>=?", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }
}
