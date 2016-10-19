package gnu.commonlisp.lisp;

import gnu.commonlisp.lang.CommonLisp;
import gnu.commonlisp.lang.Lisp2;
import gnu.commonlisp.lang.Symbols;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.PropertyLocation;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;

/* compiled from: PrimOps.scm */
public class PrimOps extends ModuleBody {
    public static final PrimOps $instance;
    static final SimpleSymbol Lit0;
    static final IntNum Lit1;
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final SimpleSymbol Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final SimpleSymbol Lit17;
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final SimpleSymbol Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit30;
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;
    public static final ModuleMethod apply;
    public static final ModuleMethod aref;
    public static final ModuleMethod arrayp;
    public static final ModuleMethod aset;
    public static final ModuleMethod boundp;
    public static final ModuleMethod car;
    public static final ModuleMethod cdr;
    public static final ModuleMethod char$Mnto$Mnstring;
    public static final ModuleMethod fillarray;
    public static final ModuleMethod fset;
    public static final ModuleMethod functionp;
    public static final ModuleMethod get;
    public static final ModuleMethod length;
    public static final ModuleMethod make$Mnstring;
    public static final ModuleMethod plist$Mnget;
    public static final ModuleMethod plist$Mnmember;
    public static final ModuleMethod plist$Mnput;
    public static final ModuleMethod plist$Mnremprop;
    public static final ModuleMethod put;
    public static final ModuleMethod set;
    public static final ModuleMethod setcar;
    public static final ModuleMethod setcdr;
    public static final ModuleMethod setplist;
    public static final ModuleMethod stringp;
    public static final ModuleMethod substring;
    public static final ModuleMethod symbol$Mnfunction;
    public static final ModuleMethod symbol$Mnname;
    public static final ModuleMethod symbol$Mnplist;
    public static final ModuleMethod symbol$Mnvalue;
    public static final ModuleMethod symbolp;

    static {
        Lit31 = (SimpleSymbol) new SimpleSymbol("functionp").readResolve();
        Lit30 = (SimpleSymbol) new SimpleSymbol("char-to-string").readResolve();
        Lit29 = (SimpleSymbol) new SimpleSymbol("substring").readResolve();
        Lit28 = (SimpleSymbol) new SimpleSymbol("make-string").readResolve();
        Lit27 = (SimpleSymbol) new SimpleSymbol("stringp").readResolve();
        Lit26 = (SimpleSymbol) new SimpleSymbol("fillarray").readResolve();
        Lit25 = (SimpleSymbol) new SimpleSymbol("aset").readResolve();
        Lit24 = (SimpleSymbol) new SimpleSymbol("aref").readResolve();
        Lit23 = (SimpleSymbol) new SimpleSymbol("arrayp").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("length").readResolve();
        Lit21 = (SimpleSymbol) new SimpleSymbol("apply").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol("fset").readResolve();
        Lit19 = (SimpleSymbol) new SimpleSymbol("symbol-function").readResolve();
        Lit18 = (SimpleSymbol) new SimpleSymbol("set").readResolve();
        Lit17 = (SimpleSymbol) new SimpleSymbol("symbol-value").readResolve();
        Lit16 = (SimpleSymbol) new SimpleSymbol("put").readResolve();
        Lit15 = (SimpleSymbol) new SimpleSymbol("get").readResolve();
        Lit14 = (SimpleSymbol) new SimpleSymbol("plist-member").readResolve();
        Lit13 = (SimpleSymbol) new SimpleSymbol("plist-remprop").readResolve();
        Lit12 = (SimpleSymbol) new SimpleSymbol("plist-put").readResolve();
        Lit11 = (SimpleSymbol) new SimpleSymbol("plist-get").readResolve();
        Lit10 = (SimpleSymbol) new SimpleSymbol("setplist").readResolve();
        Lit9 = (SimpleSymbol) new SimpleSymbol("symbol-plist").readResolve();
        Lit8 = (SimpleSymbol) new SimpleSymbol("symbol-name").readResolve();
        Lit7 = (SimpleSymbol) new SimpleSymbol("symbolp").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("boundp").readResolve();
        Lit5 = (SimpleSymbol) new SimpleSymbol("setcdr").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol("setcar").readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("cdr").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("car").readResolve();
        Lit1 = IntNum.make(0);
        Lit0 = (SimpleSymbol) new SimpleSymbol("t").readResolve();
        $instance = new PrimOps();
        ModuleBody moduleBody = $instance;
        car = new ModuleMethod(moduleBody, 1, Lit2, 4097);
        cdr = new ModuleMethod(moduleBody, 2, Lit3, 4097);
        setcar = new ModuleMethod(moduleBody, 3, Lit4, 8194);
        setcdr = new ModuleMethod(moduleBody, 4, Lit5, 8194);
        boundp = new ModuleMethod(moduleBody, 5, Lit6, 4097);
        symbolp = new ModuleMethod(moduleBody, 6, Lit7, 4097);
        symbol$Mnname = new ModuleMethod(moduleBody, 7, Lit8, 4097);
        symbol$Mnplist = new ModuleMethod(moduleBody, 8, Lit9, 4097);
        setplist = new ModuleMethod(moduleBody, 9, Lit10, 8194);
        plist$Mnget = new ModuleMethod(moduleBody, 10, Lit11, 12290);
        plist$Mnput = new ModuleMethod(moduleBody, 12, Lit12, 12291);
        plist$Mnremprop = new ModuleMethod(moduleBody, 13, Lit13, 8194);
        plist$Mnmember = new ModuleMethod(moduleBody, 14, Lit14, 8194);
        get = new ModuleMethod(moduleBody, 15, Lit15, 12290);
        put = new ModuleMethod(moduleBody, 17, Lit16, 12291);
        symbol$Mnvalue = new ModuleMethod(moduleBody, 18, Lit17, 4097);
        set = new ModuleMethod(moduleBody, 19, Lit18, 8194);
        symbol$Mnfunction = new ModuleMethod(moduleBody, 20, Lit19, 4097);
        fset = new ModuleMethod(moduleBody, 21, Lit20, 8194);
        apply = new ModuleMethod(moduleBody, 22, Lit21, -4095);
        length = new ModuleMethod(moduleBody, 23, Lit22, 4097);
        arrayp = new ModuleMethod(moduleBody, 24, Lit23, 4097);
        aref = new ModuleMethod(moduleBody, 25, Lit24, 8194);
        aset = new ModuleMethod(moduleBody, 26, Lit25, 12291);
        fillarray = new ModuleMethod(moduleBody, 27, Lit26, 8194);
        stringp = new ModuleMethod(moduleBody, 28, Lit27, 4097);
        make$Mnstring = new ModuleMethod(moduleBody, 29, Lit28, 8194);
        substring = new ModuleMethod(moduleBody, 30, Lit29, 12290);
        char$Mnto$Mnstring = new ModuleMethod(moduleBody, 32, Lit30, 4097);
        functionp = new ModuleMethod(moduleBody, 33, Lit31, 4097);
        $instance.run();
    }

    public PrimOps() {
        ModuleInfo.register(this);
    }

    public static Object get(Symbol symbol, Object obj) {
        return get(symbol, obj, LList.Empty);
    }

    public static Object plistGet(Object obj, Object obj2) {
        return plistGet(obj, obj2, Boolean.FALSE);
    }

    public static FString substring(CharSequence charSequence, Object obj) {
        return substring(charSequence, obj, LList.Empty);
    }

    public static Object car(Object x) {
        return x == LList.Empty ? x : ((Pair) x).getCar();
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
            case ArithOp.DIVIDE_INEXACT /*5*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.QUOTIENT /*6*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.QUOTIENT_EXACT /*7*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.PREFER_BINDING2 /*8*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S8_VALUE /*18*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S16_VALUE /*20*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_U64_VALUE /*23*/:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S64_VALUE /*24*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.SET_IF_UNBOUND /*32*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.ELEMENT_VALUE /*33*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object cdr(Object x) {
        return x == LList.Empty ? x : ((Pair) x).getCdr();
    }

    public static void setcar(Pair p, Object x) {
        lists.setCar$Ex(p, x);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case SetExp.GLOBAL_FLAG /*4*/:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.ASHIFT_GENERAL /*9*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.ASHIFT_LEFT /*10*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.AND /*13*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.IOR /*14*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.XOR /*15*/:
                if (!(obj instanceof Symbol)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.INT_U16_VALUE /*19*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.INT_U32_VALUE /*21*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.FLOAT_VALUE /*25*/:
                if (!(obj instanceof SimpleVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.BOOLEAN_VALUE /*27*/:
                if (!(obj instanceof SimpleVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.CHAR_VALUE /*29*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                if (!(obj instanceof CharSequence)) {
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

    public static void setcdr(Pair p, Object x) {
        lists.setCdr$Ex(p, x);
    }

    public static boolean boundp(Object symbol) {
        return Symbols.isBound(symbol);
    }

    public static boolean symbolp(Object x) {
        return Symbols.isSymbol(x);
    }

    public static Object symbolName(Object symbol) {
        return Symbols.getPrintName(symbol);
    }

    public static Object symbolPlist(Object symbol) {
        return PropertyLocation.getPropertyList(symbol);
    }

    public static Object setplist(Object symbol, Object plist) {
        PropertyLocation.setPropertyList(symbol, plist);
        return plist;
    }

    public static Object plistGet(Object plist, Object prop, Object default_) {
        return PropertyLocation.plistGet(plist, prop, default_);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ArithOp.ASHIFT_LEFT /*10*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case ArithOp.LSHIFT_RIGHT /*12*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case ArithOp.XOR /*15*/:
                if (!(obj instanceof Symbol)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case Sequence.INT_U8_VALUE /*17*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case Sequence.DOUBLE_VALUE /*26*/:
                if (!(obj instanceof SimpleVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object plistPut(Object plist, Object prop, Object value) {
        return PropertyLocation.plistPut(plist, prop, value);
    }

    public static Object plistRemprop(Object plist, Object prop) {
        return PropertyLocation.plistRemove(plist, prop);
    }

    public static Object plistMember(Object plist, Object prop) {
        return PropertyLocation.plistGet(plist, prop, Values.empty) == Values.empty ? LList.Empty : Lit0;
    }

    public static Object get(Symbol symbol, Object property, Object default_) {
        return PropertyLocation.getProperty(symbol, property, default_);
    }

    public static void put(Object symbol, Object property, Object value) {
        PropertyLocation.putProperty(symbol, property, value);
    }

    public static Object symbolValue(Object sym) {
        return Environment.getCurrent().get(Symbols.getSymbol(sym));
    }

    public static void set(Object symbol, Object value) {
        Environment.getCurrent().put(Symbols.getSymbol(symbol), value);
    }

    public static Object symbolFunction(Object symbol) {
        return Symbols.getFunctionBinding(symbol);
    }

    public static void fset(Object symbol, Object object) {
        Symbols.setFunctionBinding(Environment.getCurrent(), symbol, object);
    }

    public static Object apply(Object func, Object... args) {
        return (misc.isSymbol(func) ? (Procedure) symbolFunction(func) : (Procedure) func).applyN(Apply.getArguments(args, 0, apply));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 22) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return apply(obj, objArr2);
            }
            objArr2[length] = objArr[length + 1];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 22) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }

    public static int length(Sequence x) {
        return x.size();
    }

    public static boolean arrayp(Object x) {
        return x instanceof SimpleVector;
    }

    public static Object aref(SimpleVector array, int k) {
        return array.get(k);
    }

    public static Object aset(SimpleVector array, int k, Object obj) {
        array.set(k, obj);
        return obj;
    }

    public static Object fillarray(SimpleVector array, Object obj) {
        array.fill(obj);
        return obj;
    }

    public static boolean stringp(Object x) {
        return x instanceof CharSequence;
    }

    public static FString makeString(int count, Object ch) {
        return new FString(count, CommonLisp.asChar(ch));
    }

    public static FString substring(CharSequence str, Object from, Object to) {
        if (to == LList.Empty) {
            to = Integer.valueOf(strings.stringLength(str));
        }
        if (Scheme.numLss.apply2(to, Lit1) != Boolean.FALSE) {
            to = AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength(str)), to);
        }
        if (Scheme.numLss.apply2(from, Lit1) != Boolean.FALSE) {
            from = AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength(str)), from);
        }
        return new FString(str, ((Number) from).intValue(), ((Number) AddOp.$Mn.apply2(to, from)).intValue());
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                try {
                    setcar((Pair) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "setcar", 1, obj);
                }
            case SetExp.GLOBAL_FLAG /*4*/:
                try {
                    setcdr((Pair) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "setcdr", 1, obj);
                }
            case ArithOp.ASHIFT_GENERAL /*9*/:
                return setplist(obj, obj2);
            case ArithOp.ASHIFT_LEFT /*10*/:
                return plistGet(obj, obj2);
            case ArithOp.AND /*13*/:
                return plistRemprop(obj, obj2);
            case ArithOp.IOR /*14*/:
                return plistMember(obj, obj2);
            case ArithOp.XOR /*15*/:
                try {
                    return get((Symbol) obj, obj2);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "get", 1, obj);
                }
            case Sequence.INT_U16_VALUE /*19*/:
                set(obj, obj2);
                return Values.empty;
            case Sequence.INT_U32_VALUE /*21*/:
                fset(obj, obj2);
                return Values.empty;
            case Sequence.FLOAT_VALUE /*25*/:
                try {
                    try {
                        return aref((SimpleVector) obj, ((Number) obj2).intValue());
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "aref", 2, obj2);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "aref", 1, obj);
                }
            case Sequence.BOOLEAN_VALUE /*27*/:
                try {
                    return fillarray((SimpleVector) obj, obj2);
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "fillarray", 1, obj);
                }
            case Sequence.CHAR_VALUE /*29*/:
                try {
                    return makeString(((Number) obj).intValue(), obj2);
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "make-string", 1, obj);
                }
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                try {
                    return substring((CharSequence) obj, obj2);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "substring", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case ArithOp.ASHIFT_LEFT /*10*/:
                return plistGet(obj, obj2, obj3);
            case ArithOp.LSHIFT_RIGHT /*12*/:
                return plistPut(obj, obj2, obj3);
            case ArithOp.XOR /*15*/:
                try {
                    return get((Symbol) obj, obj2, obj3);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "get", 1, obj);
                }
            case Sequence.INT_U8_VALUE /*17*/:
                put(obj, obj2, obj3);
                return Values.empty;
            case Sequence.DOUBLE_VALUE /*26*/:
                try {
                    try {
                        return aset((SimpleVector) obj, ((Number) obj2).intValue(), obj3);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "aset", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "aset", 1, obj);
                }
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                try {
                    return substring((CharSequence) obj, obj2, obj3);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "substring", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static FString charToString(Object ch) {
        return new FString(1, CommonLisp.asChar(ch));
    }

    public static boolean functionp(Object x) {
        return x instanceof Procedure;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return car(obj);
            case SetExp.DEFINING_FLAG /*2*/:
                return cdr(obj);
            case ArithOp.DIVIDE_INEXACT /*5*/:
                return boundp(obj) ? Lisp2.TRUE : LList.Empty;
            case ArithOp.QUOTIENT /*6*/:
                return symbolp(obj) ? Lisp2.TRUE : LList.Empty;
            case ArithOp.QUOTIENT_EXACT /*7*/:
                return symbolName(obj);
            case SetExp.PREFER_BINDING2 /*8*/:
                return symbolPlist(obj);
            case Sequence.INT_S8_VALUE /*18*/:
                return symbolValue(obj);
            case Sequence.INT_S16_VALUE /*20*/:
                return symbolFunction(obj);
            case Sequence.INT_U64_VALUE /*23*/:
                try {
                    return Integer.valueOf(length((Sequence) obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "length", 1, obj);
                }
            case Sequence.INT_S64_VALUE /*24*/:
                return arrayp(obj) ? Lisp2.TRUE : LList.Empty;
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                return stringp(obj) ? Lisp2.TRUE : LList.Empty;
            case SetExp.SET_IF_UNBOUND /*32*/:
                return charToString(obj);
            case Sequence.ELEMENT_VALUE /*33*/:
                return functionp(obj) ? Lisp2.TRUE : LList.Empty;
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
