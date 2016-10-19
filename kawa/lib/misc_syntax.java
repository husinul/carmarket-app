package kawa.lib;

import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.GetModuleClass;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.slib.srfi1;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Location;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.standard.syntax_case;

/* compiled from: misc_syntax.scm */
public class misc_syntax extends ModuleBody {
    public static final Location $Prvt$define$Mnconstant;
    public static final misc_syntax $instance;
    static final SimpleSymbol Lit0;
    static final SyntaxPattern Lit1;
    static final SimpleSymbol Lit10;
    static final SyntaxRules Lit11;
    static final SimpleSymbol Lit12;
    static final SyntaxPattern Lit13;
    static final SyntaxTemplate Lit14;
    static final SyntaxTemplate Lit15;
    static final SyntaxPattern Lit16;
    static final SyntaxTemplate Lit17;
    static final SimpleSymbol Lit18;
    static final SyntaxPattern Lit19;
    static final SyntaxTemplate Lit2;
    static final SyntaxTemplate Lit20;
    static final SyntaxTemplate Lit21;
    static final SyntaxTemplate Lit22;
    static final SimpleSymbol Lit23;
    static final SimpleSymbol Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SyntaxTemplate Lit3;
    static final SimpleSymbol Lit30;
    static final SimpleSymbol Lit31;
    static final SyntaxTemplate Lit4;
    static final SyntaxPattern Lit5;
    static final SimpleSymbol Lit6;
    static final SyntaxRules Lit7;
    static final SimpleSymbol Lit8;
    static final SyntaxPattern Lit9;
    public static final Macro include;
    public static final Macro include$Mnrelative;
    public static final Macro module$Mnuri;
    public static final Macro provide;
    public static final Macro resource$Mnurl;
    public static final Macro test$Mnbegin;

    /* compiled from: misc_syntax.scm */
    public class frame extends ModuleBody {
        Object f93k;
        InPort f94p;

        public Object lambda4f() {
            Object x = ports.read(this.f94p);
            if (!ports.isEofObject(x)) {
                return new Pair(std_syntax.datum$To$SyntaxObject(this.f93k, x), lambda4f());
            }
            ports.closeInputPort(this.f94p);
            return LList.Empty;
        }
    }

    static {
        Lit31 = (SimpleSymbol) new SimpleSymbol("%test-begin").readResolve();
        Lit30 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
        Lit29 = (SimpleSymbol) new SimpleSymbol("require").readResolve();
        Lit28 = (SimpleSymbol) new SimpleSymbol("else").readResolve();
        Lit27 = (SimpleSymbol) new SimpleSymbol("cond-expand").readResolve();
        Lit26 = (SimpleSymbol) new SimpleSymbol("srfi-64").readResolve();
        Lit25 = (SimpleSymbol) new SimpleSymbol("begin").readResolve();
        Lit24 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve();
        Lit23 = (SimpleSymbol) new SimpleSymbol("$lookup$").readResolve();
        Lit22 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
        Lit21 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
        Lit20 = new SyntaxTemplate("\u0001\u0001", "\b\u000b", new Object[0], 0);
        Lit19 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
        Lit18 = (SimpleSymbol) new SimpleSymbol("include-relative").readResolve();
        Lit17 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u0004\b\u0015\u0013", new Object[]{Lit25}, 1);
        Lit16 = new SyntaxPattern("\r\u0017\u0010\b\b", new Object[0], 3);
        Lit15 = new SyntaxTemplate("\u0001\u0001", "\u0003", new Object[0], 0);
        Lit14 = new SyntaxTemplate("\u0001\u0001", "\u000b", new Object[0], 0);
        Lit13 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
        Lit12 = (SimpleSymbol) new SimpleSymbol("include").readResolve();
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("resource-url").readResolve();
        Lit10 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        r4 = new Object[6];
        SimpleSymbol simpleSymbol2 = Lit23;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("module-uri").readResolve();
        Lit8 = simpleSymbol3;
        r4[2] = PairWithPosition.make(simpleSymbol2, Pair.make(PairWithPosition.make(simpleSymbol3, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 159755), Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol) new SimpleSymbol("resolve").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 159755);
        r4[3] = Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol) new SimpleSymbol("toURL").readResolve(), LList.Empty)), LList.Empty);
        r4[4] = Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol) new SimpleSymbol("openConnection").readResolve(), LList.Empty)), LList.Empty);
        r4[5] = Pair.make(Pair.make(Lit24, Pair.make((SimpleSymbol) new SimpleSymbol("getURL").readResolve(), LList.Empty)), LList.Empty);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\b\u0011\u0018\f\u0099\b\u0011\u0018\fa\b\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\u0018\u001c\u0018$\u0018,", r4, 0);
        Lit11 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        Lit9 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
        Object[] objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-begin").readResolve();
        Lit6 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        r8 = new SyntaxRule[2];
        r8[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\t\u0003\u0018\u001c", new Object[]{Lit25, PairWithPosition.make(Lit27, PairWithPosition.make(PairWithPosition.make(Lit26, PairWithPosition.make(Values.empty, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86046), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86037), PairWithPosition.make(PairWithPosition.make(Lit28, PairWithPosition.make(PairWithPosition.make(Lit29, PairWithPosition.make(PairWithPosition.make(Lit30, PairWithPosition.make(Lit26, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86070), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86070), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86069), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86060), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86060), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86054), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86054), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86037), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 86024), Lit31, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 90144)}, 0);
        r8[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\t\u0003\b\u000b", new Object[]{Lit25, PairWithPosition.make(Lit27, PairWithPosition.make(PairWithPosition.make(Lit26, PairWithPosition.make(Values.empty, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102430), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102421), PairWithPosition.make(PairWithPosition.make(Lit28, PairWithPosition.make(PairWithPosition.make(Lit29, PairWithPosition.make(PairWithPosition.make(Lit30, PairWithPosition.make(Lit26, LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102454), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102454), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102453), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102444), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102444), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102438), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102438), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102421), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 102408), Lit31}, 0);
        Lit7 = new SyntaxRules(objArr2, r8, 2);
        Lit5 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
        Lit4 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("::").readResolve(), PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<int>").readResolve(), PairWithPosition.make(IntNum.make((int) srfi1.$Pcprovide$Pcsrfi$Mn1), LList.Empty, "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 53270), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 53264), "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm", 53260)}, 0);
        Lit3 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
        Lit2 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("define-constant").readResolve()}, 0);
        Lit1 = new SyntaxPattern("\f\u0007,\f\u000f\f\u0017\b\b", new Object[0], 3);
        Lit0 = (SimpleSymbol) new SimpleSymbol("provide").readResolve();
        $instance = new misc_syntax();
        $Prvt$define$Mnconstant = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnconstant");
        simpleSymbol = Lit0;
        ModuleBody moduleBody = $instance;
        provide = Macro.make(simpleSymbol, new ModuleMethod(moduleBody, 1, null, 4097), $instance);
        test$Mnbegin = Macro.make(Lit6, Lit7, $instance);
        simpleSymbol = Lit8;
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 2, null, 4097);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm:29");
        module$Mnuri = Macro.make(simpleSymbol, moduleMethod, $instance);
        resource$Mnurl = Macro.make(Lit10, Lit11, $instance);
        simpleSymbol = Lit12;
        moduleMethod = new ModuleMethod(moduleBody, 3, null, 4097);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/kawa/lib/misc_syntax.scm:54");
        include = Macro.make(simpleSymbol, moduleMethod, $instance);
        include$Mnrelative = Macro.make(Lit18, new ModuleMethod(moduleBody, 4, null, 4097), $instance);
        $instance.run();
    }

    public misc_syntax() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static Object lambda1(Object form) {
        Object[] allocVars = SyntaxPattern.allocVars(3, null);
        if (Lit1.match(form, allocVars, 0)) {
            Object execute = Lit2.execute(allocVars, TemplateScope.make());
            Object[] objArr = new Object[2];
            objArr[0] = "%provide%";
            Object syntaxObject$To$Datum = std_syntax.syntaxObject$To$Datum(Lit3.execute(allocVars, TemplateScope.make()));
            try {
                objArr[1] = misc.symbol$To$String((Symbol) syntaxObject$To$Datum);
                return lists.cons(execute, lists.cons(std_syntax.datum$To$SyntaxObject(form, misc.string$To$Symbol(strings.stringAppend(objArr))), Lit4.execute(allocVars, TemplateScope.make())));
            } catch (ClassCastException e) {
                throw new WrongType(e, "symbol->string", 1, syntaxObject$To$Datum);
            }
        } else if (!Lit5.match(form, allocVars, 0)) {
            return syntax_case.error("syntax-case", form);
        } else {
            Object[] objArr2;
            String str = "provide requires a quoted feature-name";
            if (str instanceof Object[]) {
                objArr2 = (Object[]) str;
            } else {
                objArr2 = new Object[]{str};
            }
            return prim_syntax.syntaxError(form, objArr2);
        }
    }

    static Object lambda2(Object form) {
        return Lit9.match(form, SyntaxPattern.allocVars(1, null), 0) ? GetModuleClass.getModuleClassURI(Compilation.getCurrent()) : syntax_case.error("syntax-case", form);
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
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.GLOBAL_FLAG /*4*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    static Object lambda3(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(2, null);
        if (!Lit13.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        Object fn = std_syntax.syntaxObject$To$Datum(Lit14.execute(allocVars, TemplateScope.make()));
        Object execute = Lit15.execute(allocVars, TemplateScope.make());
        frame kawa_lib_misc_syntax_frame = new frame();
        kawa_lib_misc_syntax_frame.f93k = execute;
        try {
            kawa_lib_misc_syntax_frame.f94p = ports.openInputFile(Path.valueOf(fn));
            execute = kawa_lib_misc_syntax_frame.lambda4f();
            allocVars = SyntaxPattern.allocVars(3, allocVars);
            if (!Lit16.match(execute, allocVars, 0)) {
                return syntax_case.error("syntax-case", execute);
            }
            return Lit17.execute(allocVars, TemplateScope.make());
        } catch (ClassCastException e) {
            throw new WrongType(e, "open-input-file", 1, fn);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return lambda1(obj);
            case SetExp.DEFINING_FLAG /*2*/:
                return lambda2(obj);
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                return lambda3(obj);
            case SetExp.GLOBAL_FLAG /*4*/:
                return lambda5(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    static Object lambda5(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(2, null);
        if (!Lit19.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        Object syntaxObject$To$Datum = std_syntax.syntaxObject$To$Datum(Lit20.execute(allocVars, TemplateScope.make()));
        try {
            PairWithPosition path$Mnpair = (PairWithPosition) syntaxObject$To$Datum;
            Path base = Path.valueOf(path$Mnpair.getFileName());
            String fname = path$Mnpair.getCar().toString();
            return LList.list2(std_syntax.datum$To$SyntaxObject(Lit21.execute(allocVars, TemplateScope.make()), Lit12), std_syntax.datum$To$SyntaxObject(Lit22.execute(allocVars, TemplateScope.make()), base.resolve(fname).toString()));
        } catch (ClassCastException e) {
            throw new WrongType(e, "path-pair", -2, syntaxObject$To$Datum);
        }
    }
}
