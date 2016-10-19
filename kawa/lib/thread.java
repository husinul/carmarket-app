package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Future;
import gnu.mapping.Procedure;
import gnu.mapping.RunnableClosure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.Quantity;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.standard.sleep;

/* compiled from: thread.scm */
public class thread extends ModuleBody {
    public static final ModuleMethod $Prvt$$Pcmake$Mnfuture;
    public static final thread $instance;
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SyntaxRules Lit2;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    public static final Macro future;
    public static final ModuleMethod runnable;
    public static final ModuleMethod sleep;

    static {
        Lit4 = (SimpleSymbol) new SimpleSymbol("runnable").readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("%make-future").readResolve();
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("future").readResolve();
        Lit1 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\t\u0010\b\u0003", new Object[]{Lit3, (SimpleSymbol) new SimpleSymbol("lambda").readResolve()}, 0);
        Lit2 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        Lit0 = (SimpleSymbol) new SimpleSymbol("sleep").readResolve();
        $instance = new thread();
        ModuleBody moduleBody = $instance;
        sleep = new ModuleMethod(moduleBody, 1, Lit0, 4097);
        future = Macro.make(Lit1, Lit2, $instance);
        $Prvt$$Pcmake$Mnfuture = new ModuleMethod(moduleBody, 2, Lit3, 4097);
        runnable = new ModuleMethod(moduleBody, 3, Lit4, 4097);
        $instance.run();
    }

    public thread() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static void sleep(Quantity time) {
        sleep.sleep(time);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                if (!(obj instanceof Quantity)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.DEFINING_FLAG /*2*/:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                if (!(obj instanceof Procedure)) {
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

    public static Future $PcMakeFuture(Procedure p) {
        Future f = new Future(p);
        f.start();
        return f;
    }

    public static RunnableClosure runnable(Procedure p) {
        return new RunnableClosure(p);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                try {
                    sleep((Quantity) obj);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "sleep", 1, obj);
                }
            case SetExp.DEFINING_FLAG /*2*/:
                try {
                    return $PcMakeFuture((Procedure) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%make-future", 1, obj);
                }
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                try {
                    return runnable((Procedure) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "runnable", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
