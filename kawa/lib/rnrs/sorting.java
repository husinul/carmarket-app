package kawa.lib.rnrs;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.srfi95;
import kawa.standard.append;

/* compiled from: sorting.scm */
public class sorting extends ModuleBody {
    public static final sorting $instance;
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit2;
    public static final ModuleMethod list$Mnsort;
    public static final ModuleMethod vector$Mnsort;
    public static final ModuleMethod vector$Mnsort$Ex;

    static {
        Lit2 = (SimpleSymbol) new SimpleSymbol("vector-sort!").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("vector-sort").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("list-sort").readResolve();
        $instance = new sorting();
        ModuleBody moduleBody = $instance;
        list$Mnsort = new ModuleMethod(moduleBody, 1, Lit0, 8194);
        vector$Mnsort = new ModuleMethod(moduleBody, 2, Lit1, 8194);
        vector$Mnsort$Ex = new ModuleMethod(moduleBody, 3, Lit2, 8194);
        $instance.run();
    }

    public sorting() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object listSort(Object less$Qu, Object list) {
        return srfi95.$PcSortList(append.append$V(new Object[]{list, LList.Empty}), less$Qu, Boolean.FALSE);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case SetExp.DEFINING_FLAG /*2*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static void vectorSort(Object less$Qu, Object seq) {
        try {
            srfi95.$PcSortVector((Sequence) seq, less$Qu, Boolean.FALSE);
        } catch (ClassCastException e) {
            throw new WrongType(e, "%sort-vector", 0, seq);
        }
    }

    public static void vectorSort$Ex(Object proc, Object vector) {
        try {
            srfi95.$PcVectorSort$Ex((Sequence) vector, proc, Boolean.FALSE);
        } catch (ClassCastException e) {
            throw new WrongType(e, "%vector-sort!", 0, vector);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return listSort(obj, obj2);
            case SetExp.DEFINING_FLAG /*2*/:
                vectorSort(obj, obj2);
                return Values.empty;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                vectorSort$Ex(obj, obj2);
                return Values.empty;
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }
}
