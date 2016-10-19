package kawa.lib;

import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.expr.Special;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.ParseFormat;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import java.util.List;

/* compiled from: vectors.scm */
public class vectors extends ModuleBody {
    public static final vectors $instance;
    static final Keyword Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    public static final ModuleMethod list$Mn$Grvector;
    public static final ModuleMethod make$Mnvector;
    public static final ModuleMethod vector$Mn$Grlist;
    public static final ModuleMethod vector$Mnfill$Ex;
    public static final ModuleMethod vector$Mnlength;
    public static final GenericProc vector$Mnref = null;
    static final ModuleMethod vector$Mnref$Fn1;
    public static final ModuleMethod vector$Mnset$Ex;
    public static final ModuleMethod vector$Qu;

    static {
        Lit8 = (SimpleSymbol) new SimpleSymbol("vector-fill!").readResolve();
        Lit7 = (SimpleSymbol) new SimpleSymbol("list->vector").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("vector->list").readResolve();
        Lit5 = (SimpleSymbol) new SimpleSymbol("vector-ref").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol("vector-set!").readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("vector-length").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("make-vector").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("vector?").readResolve();
        Lit0 = Keyword.make("setter");
        $instance = new vectors();
        ModuleBody moduleBody = $instance;
        vector$Qu = new ModuleMethod(moduleBody, 1, Lit1, 4097);
        make$Mnvector = new ModuleMethod(moduleBody, 2, Lit2, 8193);
        vector$Mnlength = new ModuleMethod(moduleBody, 4, Lit3, 4097);
        vector$Mnset$Ex = new ModuleMethod(moduleBody, 5, Lit4, 12291);
        vector$Mnref$Fn1 = new ModuleMethod(moduleBody, 6, Lit5, 8194);
        vector$Mn$Grlist = new ModuleMethod(moduleBody, 7, Lit6, 4097);
        list$Mn$Grvector = new ModuleMethod(moduleBody, 8, Lit7, 4097);
        vector$Mnfill$Ex = new ModuleMethod(moduleBody, 9, Lit8, 8194);
        $instance.run();
    }

    public vectors() {
        ModuleInfo.register(this);
    }

    public static FVector makeVector(int i) {
        return makeVector(i, Special.undefined);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        vector$Mnref = new GenericProc("vector-ref");
        GenericProc genericProc = vector$Mnref;
        Object[] objArr = new Object[3];
        objArr[0] = Lit0;
        objArr[1] = vector$Mnset$Ex;
        Procedure vector$Mnref = vector$Mnref$Fn1;
        objArr[2] = vector$Mnref$Fn1;
        genericProc.setProperties(objArr);
    }

    public static boolean isVector(Object x) {
        return x instanceof FVector;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.DEFINING_FLAG /*2*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.GLOBAL_FLAG /*4*/:
                if (!(obj instanceof FVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.QUOTIENT_EXACT /*7*/:
                if (!(obj instanceof FVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.PREFER_BINDING2 /*8*/:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static FVector makeVector(int k, Object fill) {
        return new FVector(k, fill);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case SetExp.DEFINING_FLAG /*2*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.QUOTIENT /*6*/:
                if (!(obj instanceof FVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.ASHIFT_GENERAL /*9*/:
                if (!(obj instanceof FVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static int vectorLength(FVector x) {
        return x.size();
    }

    public static void vectorSet$Ex(FVector vector, int k, Object obj) {
        vector.set(k, obj);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector != 5) {
            return super.apply3(moduleMethod, obj, obj2, obj3);
        }
        try {
            try {
                vectorSet$Ex((FVector) obj, ((Number) obj2).intValue(), obj3);
                return Values.empty;
            } catch (ClassCastException e) {
                throw new WrongType(e, "vector-set!", 2, obj2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "vector-set!", 1, obj);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 5) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        if (!(obj instanceof FVector)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    public static Object vectorRef(FVector vector, int k) {
        return vector.get(k);
    }

    public static LList vector$To$List(FVector vec) {
        LList result = LList.Empty;
        int i = vectorLength(vec);
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            result = lists.cons(vector$Mnref.apply2(vec, Integer.valueOf(i)), result);
        }
    }

    public static FVector list$To$Vector(LList x) {
        return new FVector((List) x);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return isVector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case SetExp.DEFINING_FLAG /*2*/:
                try {
                    return makeVector(((Number) obj).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-vector", 1, obj);
                }
            case SetExp.GLOBAL_FLAG /*4*/:
                try {
                    return Integer.valueOf(vectorLength((FVector) obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "vector-length", 1, obj);
                }
            case ArithOp.QUOTIENT_EXACT /*7*/:
                try {
                    return vector$To$List((FVector) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "vector->list", 1, obj);
                }
            case SetExp.PREFER_BINDING2 /*8*/:
                try {
                    return list$To$Vector((LList) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "list->vector", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static void vectorFill$Ex(FVector vec, Object fill) {
        vec.setAll(fill);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case SetExp.DEFINING_FLAG /*2*/:
                try {
                    return makeVector(((Number) obj).intValue(), obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-vector", 1, obj);
                }
            case ArithOp.QUOTIENT /*6*/:
                try {
                    try {
                        return vectorRef((FVector) obj, ((Number) obj2).intValue());
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "vector-ref", 1, obj);
                }
            case ArithOp.ASHIFT_GENERAL /*9*/:
                try {
                    vectorFill$Ex((FVector) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "vector-fill!", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }
}
