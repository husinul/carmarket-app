package kawa.lib.rnrs;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ParseFormat;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.numbers;

/* compiled from: programs.scm */
public class programs extends ModuleBody {
    public static final programs $instance;
    static final IntNum Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit2;
    public static final ModuleMethod command$Mnline;
    public static final ModuleMethod exit;

    static {
        Lit2 = (SimpleSymbol) new SimpleSymbol("exit").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("command-line").readResolve();
        Lit0 = IntNum.make(0);
        $instance = new programs();
        ModuleBody moduleBody = $instance;
        command$Mnline = new ModuleMethod(moduleBody, 1, Lit1, 0);
        exit = new ModuleMethod(moduleBody, 2, Lit2, LambdaExp.ATTEMPT_INLINE);
        $instance.run();
    }

    public programs() {
        ModuleInfo.register(this);
    }

    public static void exit() {
        exit(Lit0);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static LList commandLine() {
        return lists.cons("kawa", LList.makeList(ApplicationMainSupport.commandLineArgArray, 0));
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case SetExp.DEFINING_FLAG /*2*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public static void exit(Object code) {
        int status;
        OutPort.runCleanups();
        if (numbers.isInteger(code)) {
            try {
                status = ((Number) code).intValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "status", -2, code);
            }
        }
        status = code != Boolean.FALSE ? 0 : -1;
        System.exit(status);
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return commandLine();
            case SetExp.DEFINING_FLAG /*2*/:
                exit();
                return Values.empty;
            default:
                return super.apply0(moduleMethod);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        if (moduleMethod.selector != 2) {
            return super.apply1(moduleMethod, obj);
        }
        exit(obj);
        return Values.empty;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 2) {
            return super.match1(moduleMethod, obj, callContext);
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.pc = 1;
        return 0;
    }
}
