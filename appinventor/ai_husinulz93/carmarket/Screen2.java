package appinventor.ai_husinulz93.carmarket;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.ActivityStarter;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.HorizontalScrollArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.Sequence;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;

/* compiled from: Screen2.yail */
public class Screen2 extends Form implements Runnable {
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit100;
    static final SimpleSymbol Lit101;
    static final IntNum Lit11;
    static final SimpleSymbol Lit12;
    static final IntNum Lit13;
    static final FString Lit14;
    static final FString Lit15;
    static final SimpleSymbol Lit16;
    static final IntNum Lit17;
    static final IntNum Lit18;
    static final IntNum Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final FString Lit21;
    static final PairWithPosition Lit22;
    static final SimpleSymbol Lit23;
    static final SimpleSymbol Lit24;
    static final FString Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final IntNum Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final IntNum Lit30;
    static final IntNum Lit31;
    static final FString Lit32;
    static final FString Lit33;
    static final SimpleSymbol Lit34;
    static final IntNum Lit35;
    static final FString Lit36;
    static final FString Lit37;
    static final SimpleSymbol Lit38;
    static final SimpleSymbol Lit39;
    static final SimpleSymbol Lit4;
    static final DFloNum Lit40;
    static final SimpleSymbol Lit41;
    static final IntNum Lit42;
    static final SimpleSymbol Lit43;
    static final FString Lit44;
    static final FString Lit45;
    static final SimpleSymbol Lit46;
    static final IntNum Lit47;
    static final FString Lit48;
    static final FString Lit49;
    static final FString Lit5;
    static final SimpleSymbol Lit50;
    static final FString Lit51;
    static final FString Lit52;
    static final SimpleSymbol Lit53;
    static final IntNum Lit54;
    static final IntNum Lit55;
    static final FString Lit56;
    static final PairWithPosition Lit57;
    static final SimpleSymbol Lit58;
    static final FString Lit59;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit60;
    static final IntNum Lit61;
    static final FString Lit62;
    static final FString Lit63;
    static final SimpleSymbol Lit64;
    static final SimpleSymbol Lit65;
    static final IntNum Lit66;
    static final FString Lit67;
    static final FString Lit68;
    static final SimpleSymbol Lit69;
    static final SimpleSymbol Lit7;
    static final IntNum Lit70;
    static final FString Lit71;
    static final FString Lit72;
    static final SimpleSymbol Lit73;
    static final IntNum Lit74;
    static final FString Lit75;
    static final FString Lit76;
    static final SimpleSymbol Lit77;
    static final IntNum Lit78;
    static final IntNum Lit79;
    static final IntNum Lit8;
    static final FString Lit80;
    static final PairWithPosition Lit81;
    static final SimpleSymbol Lit82;
    static final FString Lit83;
    static final SimpleSymbol Lit84;
    static final IntNum Lit85;
    static final FString Lit86;
    static final FString Lit87;
    static final SimpleSymbol Lit88;
    static final FString Lit89;
    static final SimpleSymbol Lit9;
    static final SimpleSymbol Lit90;
    static final SimpleSymbol Lit91;
    static final SimpleSymbol Lit92;
    static final SimpleSymbol Lit93;
    static final SimpleSymbol Lit94;
    static final SimpleSymbol Lit95;
    static final SimpleSymbol Lit96;
    static final SimpleSymbol Lit97;
    static final SimpleSymbol Lit98;
    static final SimpleSymbol Lit99;
    public static Screen2 Screen2;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public ActivityStarter ActivityStarter1;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public Button Button4;
    public final ModuleMethod Button4$Click;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public HorizontalArrangement HorizontalArrangement4;
    public HorizontalArrangement HorizontalArrangement5;
    public HorizontalScrollArrangement HorizontalScrollArrangement1;
    public Label Label1;
    public Label Label2;
    public Label Label3;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement2;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    /* compiled from: Screen2.yail */
    public class frame extends ModuleBody {
        Screen2 $main;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case ParseFormat.SEEN_MINUS /*1*/:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case ArithOp.DIVIDE_INEXACT /*5*/:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case ArithOp.ASHIFT_LEFT /*10*/:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case ArithOp.ASHIFT_RIGHT /*11*/:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case ArithOp.LSHIFT_RIGHT /*12*/:
                    if (!(obj instanceof Screen2)) {
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

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case SetExp.DEFINING_FLAG /*2*/:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case ArithOp.QUOTIENT /*6*/:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case ArithOp.QUOTIENT_EXACT /*7*/:
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
                case ArithOp.IOR /*14*/:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case SetExp.PREFER_BINDING2 /*8*/:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case ArithOp.AND /*13*/:
                    if (!(obj instanceof Screen2)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    if (!(obj4 instanceof String)) {
                        return -786428;
                    }
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case ParseFormat.SEEN_MINUS /*1*/:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "lookup-in-form-environment", 1, obj);
                    }
                case ArithOp.DIVIDE_INEXACT /*5*/:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "is-bound-in-form-environment", 1, obj);
                    }
                case ArithOp.ASHIFT_LEFT /*10*/:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case ArithOp.ASHIFT_RIGHT /*11*/:
                    this.$main.sendError(obj);
                    return Values.empty;
                case ArithOp.LSHIFT_RIGHT /*12*/:
                    this.$main.processException(obj);
                    return Values.empty;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            switch (moduleMethod.selector) {
                case SetExp.PREFER_BINDING2 /*8*/:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case ArithOp.AND /*13*/:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "dispatchEvent", 1, obj);
                    }
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case SetExp.DEFINING_FLAG /*2*/:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case ArithOp.QUOTIENT /*6*/:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "add-to-global-var-environment", 1, obj);
                    }
                case ArithOp.QUOTIENT_EXACT /*7*/:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case ArithOp.ASHIFT_GENERAL /*9*/:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case ArithOp.IOR /*14*/:
                    return this.$main.lookupHandler(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case ArithOp.XOR /*15*/:
                    return Screen2.lambda2();
                case SetExp.PROCEDURE /*16*/:
                    this.$main.$define();
                    return Values.empty;
                case Sequence.INT_U8_VALUE /*17*/:
                    return Screen2.lambda3();
                case Sequence.INT_S8_VALUE /*18*/:
                    return Screen2.lambda4();
                case Sequence.INT_U16_VALUE /*19*/:
                    return Screen2.lambda5();
                case Sequence.INT_S16_VALUE /*20*/:
                    return Screen2.lambda6();
                case Sequence.INT_U32_VALUE /*21*/:
                    return Screen2.lambda7();
                case Sequence.INT_S32_VALUE /*22*/:
                    return this.$main.Button2$Click();
                case Sequence.INT_U64_VALUE /*23*/:
                    return Screen2.lambda8();
                case Sequence.INT_S64_VALUE /*24*/:
                    return Screen2.lambda9();
                case Sequence.FLOAT_VALUE /*25*/:
                    return Screen2.lambda10();
                case Sequence.DOUBLE_VALUE /*26*/:
                    return Screen2.lambda11();
                case Sequence.BOOLEAN_VALUE /*27*/:
                    return Screen2.lambda12();
                case Sequence.TEXT_BYTE_VALUE /*28*/:
                    return Screen2.lambda13();
                case Sequence.CHAR_VALUE /*29*/:
                    return Screen2.lambda14();
                case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                    return Screen2.lambda15();
                case Sequence.CDATA_VALUE /*31*/:
                    return Screen2.lambda16();
                case SetExp.SET_IF_UNBOUND /*32*/:
                    return Screen2.lambda17();
                case Sequence.ELEMENT_VALUE /*33*/:
                    return this.$main.Button1$Click();
                case Sequence.DOCUMENT_VALUE /*34*/:
                    return Screen2.lambda18();
                case Sequence.ATTRIBUTE_VALUE /*35*/:
                    return Screen2.lambda19();
                case Sequence.COMMENT_VALUE /*36*/:
                    return Screen2.lambda20();
                case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                    return Screen2.lambda21();
                case XDataType.STRING_TYPE_CODE /*38*/:
                    return Screen2.lambda22();
                case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
                    return Screen2.lambda23();
                case XDataType.TOKEN_TYPE_CODE /*40*/:
                    return Screen2.lambda24();
                case XDataType.LANGUAGE_TYPE_CODE /*41*/:
                    return Screen2.lambda25();
                case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                    return Screen2.lambda26();
                case XDataType.NAME_TYPE_CODE /*43*/:
                    return Screen2.lambda27();
                case XDataType.NCNAME_TYPE_CODE /*44*/:
                    return this.$main.Button4$Click();
                case XDataType.ID_TYPE_CODE /*45*/:
                    return Screen2.lambda28();
                case XDataType.IDREF_TYPE_CODE /*46*/:
                    return Screen2.lambda29();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case ArithOp.XOR /*15*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case SetExp.PROCEDURE /*16*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_U8_VALUE /*17*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_S8_VALUE /*18*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_U16_VALUE /*19*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_S16_VALUE /*20*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_U32_VALUE /*21*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_S32_VALUE /*22*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_U64_VALUE /*23*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.INT_S64_VALUE /*24*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.FLOAT_VALUE /*25*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.DOUBLE_VALUE /*26*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.BOOLEAN_VALUE /*27*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.TEXT_BYTE_VALUE /*28*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.CHAR_VALUE /*29*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.CDATA_VALUE /*31*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case SetExp.SET_IF_UNBOUND /*32*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.ELEMENT_VALUE /*33*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.DOCUMENT_VALUE /*34*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.ATTRIBUTE_VALUE /*35*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.COMMENT_VALUE /*36*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.STRING_TYPE_CODE /*38*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.TOKEN_TYPE_CODE /*40*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.LANGUAGE_TYPE_CODE /*41*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.NAME_TYPE_CODE /*43*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.NCNAME_TYPE_CODE /*44*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.ID_TYPE_CODE /*45*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.IDREF_TYPE_CODE /*46*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static {
        Lit101 = (SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve();
        Lit100 = (SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve();
        Lit99 = (SimpleSymbol) new SimpleSymbol("send-error").readResolve();
        Lit98 = (SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve();
        Lit97 = (SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve();
        Lit96 = (SimpleSymbol) new SimpleSymbol("add-to-components").readResolve();
        Lit95 = (SimpleSymbol) new SimpleSymbol("add-to-events").readResolve();
        Lit94 = (SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve();
        Lit93 = (SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve();
        Lit92 = (SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve();
        Lit91 = (SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve();
        Lit90 = (SimpleSymbol) new SimpleSymbol("android-log-form").readResolve();
        Lit89 = new FString("com.google.appinventor.components.runtime.ActivityStarter");
        Lit88 = (SimpleSymbol) new SimpleSymbol("ActivityStarter1").readResolve();
        Lit87 = new FString("com.google.appinventor.components.runtime.ActivityStarter");
        Lit86 = new FString("com.google.appinventor.components.runtime.Label");
        int[] iArr = new int[2];
        iArr[0] = Component.COLOR_RED;
        Lit85 = IntNum.make(iArr);
        Lit84 = (SimpleSymbol) new SimpleSymbol("Label3").readResolve();
        Lit83 = new FString("com.google.appinventor.components.runtime.Label");
        Lit82 = (SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve();
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit4 = simpleSymbol;
        Lit81 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen2.yail", 634958);
        Lit80 = new FString("com.google.appinventor.components.runtime.Button");
        Lit79 = IntNum.make(-1050);
        Lit78 = IntNum.make(-1025);
        Lit77 = (SimpleSymbol) new SimpleSymbol("Button4").readResolve();
        Lit76 = new FString("com.google.appinventor.components.runtime.Button");
        Lit75 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
        Lit74 = IntNum.make(-1020);
        Lit73 = (SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement1").readResolve();
        Lit72 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
        Lit71 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit70 = IntNum.make(-1050);
        Lit69 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement5").readResolve();
        Lit68 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit67 = new FString("com.google.appinventor.components.runtime.Label");
        iArr = new int[2];
        iArr[0] = Component.COLOR_RED;
        Lit66 = IntNum.make(iArr);
        Lit65 = (SimpleSymbol) new SimpleSymbol("TextColor").readResolve();
        Lit64 = (SimpleSymbol) new SimpleSymbol("Label2").readResolve();
        Lit63 = new FString("com.google.appinventor.components.runtime.Label");
        Lit62 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit61 = IntNum.make(-1020);
        Lit60 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve();
        Lit59 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit58 = (SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve();
        Lit57 = PairWithPosition.make(Lit4, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen2.yail", 417870);
        Lit56 = new FString("com.google.appinventor.components.runtime.Button");
        Lit55 = IntNum.make(-1050);
        Lit54 = IntNum.make(-1025);
        Lit53 = (SimpleSymbol) new SimpleSymbol("Button1").readResolve();
        Lit52 = new FString("com.google.appinventor.components.runtime.Button");
        Lit51 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit50 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve();
        Lit49 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit48 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit47 = IntNum.make(-1060);
        Lit46 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve();
        Lit45 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit44 = new FString("com.google.appinventor.components.runtime.Label");
        Lit43 = (SimpleSymbol) new SimpleSymbol("Text").readResolve();
        Lit42 = IntNum.make(-1010);
        Lit41 = (SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve();
        Lit40 = DFloNum.make((double) 24);
        Lit39 = (SimpleSymbol) new SimpleSymbol("FontSize").readResolve();
        Lit38 = (SimpleSymbol) new SimpleSymbol("Label1").readResolve();
        Lit37 = new FString("com.google.appinventor.components.runtime.Label");
        Lit36 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit35 = IntNum.make(-1040);
        Lit34 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve();
        Lit33 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit32 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit31 = IntNum.make(-1050);
        Lit30 = IntNum.make(2);
        Lit29 = (SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve();
        Lit28 = IntNum.make(3);
        Lit27 = (SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve();
        Lit26 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve();
        Lit25 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit24 = (SimpleSymbol) new SimpleSymbol("Click").readResolve();
        Lit23 = (SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve();
        Lit22 = PairWithPosition.make(Lit4, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen2.yail", 155726);
        Lit21 = new FString("com.google.appinventor.components.runtime.Button");
        Lit20 = (SimpleSymbol) new SimpleSymbol("Image").readResolve();
        Lit19 = IntNum.make(-1020);
        Lit18 = IntNum.make(-1010);
        Lit17 = IntNum.make((int) Component.COLOR_NONE);
        Lit16 = (SimpleSymbol) new SimpleSymbol("Button2").readResolve();
        Lit15 = new FString("com.google.appinventor.components.runtime.Button");
        Lit14 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit13 = IntNum.make(-2);
        Lit12 = (SimpleSymbol) new SimpleSymbol("Width").readResolve();
        Lit11 = IntNum.make(-1008);
        Lit10 = (SimpleSymbol) new SimpleSymbol("Height").readResolve();
        Lit9 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit8 = IntNum.make((int) Component.COLOR_NONE);
        Lit7 = (SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve();
        Lit5 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit3 = (SimpleSymbol) new SimpleSymbol("AppName").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("getMessage").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("Screen2").readResolve();
    }

    public Screen2() {
        ModuleInfo.register(this);
        ModuleBody appinventor_ai_husinulz93_carmarket_Screen2_frame = new frame();
        appinventor_ai_husinulz93_carmarket_Screen2_frame.$main = this;
        this.android$Mnlog$Mnform = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 1, Lit90, 4097);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 2, Lit91, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 3, Lit92, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 5, Lit93, 4097);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 6, Lit94, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 7, Lit95, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 8, Lit96, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 9, Lit97, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 10, Lit98, 4097);
        this.send$Mnerror = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 11, Lit99, 4097);
        this.process$Mnexception = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 12, "process-exception", 4097);
        this.dispatchEvent = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 13, Lit100, 16388);
        this.lookup$Mnhandler = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 14, Lit101, 8194);
        PropertySet moduleMethod = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 15, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime8872982264086122834.scm:547");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 16, "$define", 0);
        lambda$Fn2 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 17, null, 0);
        lambda$Fn3 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 18, null, 0);
        lambda$Fn4 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 19, null, 0);
        lambda$Fn5 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 20, null, 0);
        lambda$Fn6 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 21, null, 0);
        this.Button2$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 22, Lit23, 0);
        lambda$Fn7 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 23, null, 0);
        lambda$Fn8 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 24, null, 0);
        lambda$Fn9 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 25, null, 0);
        lambda$Fn10 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 26, null, 0);
        lambda$Fn11 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 27, null, 0);
        lambda$Fn12 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 28, null, 0);
        lambda$Fn13 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 29, null, 0);
        lambda$Fn14 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 30, null, 0);
        lambda$Fn15 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 31, null, 0);
        lambda$Fn16 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 32, null, 0);
        this.Button1$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 33, Lit58, 0);
        lambda$Fn17 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 34, null, 0);
        lambda$Fn18 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 35, null, 0);
        lambda$Fn19 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 36, null, 0);
        lambda$Fn20 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 37, null, 0);
        lambda$Fn21 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 38, null, 0);
        lambda$Fn22 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 39, null, 0);
        lambda$Fn23 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 40, null, 0);
        lambda$Fn24 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 41, null, 0);
        lambda$Fn25 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 42, null, 0);
        lambda$Fn26 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 43, null, 0);
        this.Button4$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 44, Lit82, 0);
        lambda$Fn27 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 45, null, 0);
        lambda$Fn28 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen2_frame, 46, null, 0);
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        Throwable th;
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th2) {
            th = th2;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            String str;
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
            FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
            if (stringAppend == null) {
                str = null;
            } else {
                str = stringAppend.toString();
            }
            this.global$Mnvar$Mnenvironment = Environment.make(str);
            Screen2 = null;
            this.form$Mnname$Mnsymbol = Lit0;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            find = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find).run();
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit3, "carmarket", Lit4), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn2));
                }
                this.HorizontalArrangement4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit5, Lit6, lambda$Fn3), $result);
                } else {
                    addToComponents(Lit0, Lit14, Lit6, lambda$Fn4);
                }
                this.Button2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit15, Lit16, lambda$Fn5), $result);
                } else {
                    addToComponents(Lit6, Lit21, Lit16, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit23, this.Button2$Click);
                } else {
                    addToFormEnvironment(Lit23, this.Button2$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
                } else {
                    addToEvents(Lit16, Lit24);
                }
                this.VerticalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit25, Lit26, lambda$Fn7), $result);
                } else {
                    addToComponents(Lit0, Lit32, Lit26, lambda$Fn8);
                }
                this.VerticalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit26, Lit33, Lit34, lambda$Fn9), $result);
                } else {
                    addToComponents(Lit26, Lit36, Lit34, lambda$Fn10);
                }
                this.Label1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit34, Lit37, Lit38, lambda$Fn11), $result);
                } else {
                    addToComponents(Lit34, Lit44, Lit38, lambda$Fn12);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit34, Lit45, Lit46, lambda$Fn13), $result);
                } else {
                    addToComponents(Lit34, Lit48, Lit46, lambda$Fn14);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit46, Lit49, Lit50, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit46, Lit51, Lit50, Boolean.FALSE);
                }
                this.Button1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit50, Lit52, Lit53, lambda$Fn15), $result);
                } else {
                    addToComponents(Lit50, Lit56, Lit53, lambda$Fn16);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit58, this.Button1$Click);
                } else {
                    addToFormEnvironment(Lit58, this.Button1$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
                } else {
                    addToEvents(Lit53, Lit24);
                }
                this.HorizontalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit46, Lit59, Lit60, lambda$Fn17), $result);
                } else {
                    addToComponents(Lit46, Lit62, Lit60, lambda$Fn18);
                }
                this.Label2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit60, Lit63, Lit64, lambda$Fn19), $result);
                } else {
                    addToComponents(Lit60, Lit67, Lit64, lambda$Fn20);
                }
                this.HorizontalArrangement5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit68, Lit69, lambda$Fn21), $result);
                } else {
                    addToComponents(Lit0, Lit71, Lit69, lambda$Fn22);
                }
                this.HorizontalScrollArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit69, Lit72, Lit73, lambda$Fn23), $result);
                } else {
                    addToComponents(Lit69, Lit75, Lit73, lambda$Fn24);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit73, Lit76, Lit77, lambda$Fn25), $result);
                } else {
                    addToComponents(Lit73, Lit80, Lit77, lambda$Fn26);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit82, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit82, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit77, Lit24);
                }
                this.Label3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit69, Lit83, Lit84, lambda$Fn27), $result);
                } else {
                    addToComponents(Lit69, Lit86, Lit84, lambda$Fn28);
                }
                this.ActivityStarter1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit87, Lit88, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit89, Lit88, Boolean.FALSE);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static Object lambda3() {
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit3, "carmarket", Lit4);
    }

    static Object lambda4() {
        runtime.setAndCoerceProperty$Ex(Lit6, Lit7, Lit8, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit6, Lit10, Lit11, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit6, Lit12, Lit13, Lit9);
    }

    static Object lambda5() {
        runtime.setAndCoerceProperty$Ex(Lit6, Lit7, Lit8, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit6, Lit10, Lit11, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit6, Lit12, Lit13, Lit9);
    }

    static Object lambda6() {
        runtime.setAndCoerceProperty$Ex(Lit16, Lit7, Lit17, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit10, Lit18, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit12, Lit19, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit16, Lit20, "images.png", Lit4);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit16, Lit7, Lit17, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit10, Lit18, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit12, Lit19, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit16, Lit20, "images.png", Lit4);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit22, "open another screen");
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit26, Lit27, Lit28, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit26, Lit29, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit26, Lit10, Lit31, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit26, Lit12, Lit13, Lit9);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit26, Lit27, Lit28, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit26, Lit29, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit26, Lit10, Lit31, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit26, Lit12, Lit13, Lit9);
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit34, Lit10, Lit35, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit34, Lit12, Lit13, Lit9);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit34, Lit10, Lit35, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit34, Lit12, Lit13, Lit9);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit38, Lit39, Lit40, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit41, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit10, Lit42, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit12, Lit13, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit38, Lit43, "Find a new car", Lit4);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit38, Lit39, Lit40, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit41, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit10, Lit42, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit12, Lit13, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit38, Lit43, "Find a new car", Lit4);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit46, Lit10, Lit47, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit46, Lit12, Lit13, Lit9);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit46, Lit10, Lit47, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit46, Lit12, Lit13, Lit9);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit53, Lit10, Lit54, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit53, Lit12, Lit55, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit53, Lit20, "images(1).png", Lit4);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit53, Lit10, Lit54, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit53, Lit12, Lit55, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit53, Lit20, "images(1).png", Lit4);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen3"), Lit57, "open another screen");
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit60, Lit29, Lit30, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit10, Lit61, Lit9);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit60, Lit29, Lit30, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit10, Lit61, Lit9);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit64, Lit41, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit64, Lit12, Lit13, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit64, Lit43, "by brand", Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit64, Lit65, Lit66, Lit9);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit64, Lit41, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit64, Lit12, Lit13, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit64, Lit43, "by brand", Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit64, Lit65, Lit66, Lit9);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit69, Lit10, Lit70, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit69, Lit12, Lit13, Lit9);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit69, Lit10, Lit70, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit69, Lit12, Lit13, Lit9);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit73, Lit29, Lit28, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit73, Lit10, Lit74, Lit9);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit73, Lit29, Lit28, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit73, Lit10, Lit74, Lit9);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit10, Lit78, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit12, Lit79, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit20, "images(2).png", Lit4);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit10, Lit78, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit12, Lit79, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit20, "images(2).png", Lit4);
    }

    public Object Button4$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen4"), Lit81, "open another screen");
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit84, Lit41, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit84, Lit43, "by Category", Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit84, Lit65, Lit85, Lit9);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit84, Lit41, Lit30, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit84, Lit43, "by Category", Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit84, Lit65, Lit85, Lit9);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        boolean x = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & 1;
        if (x) {
            if (!this.form$Mnenvironment.isBound(name)) {
                return default$Mnvalue;
            }
        } else if (!x) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = lists.cons(lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public void processException(Object ex) {
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            return false;
        } else if (lookupInFormEnvironment(registeredObject) != componentObject) {
            return false;
        } else {
            try {
                Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                return true;
            } catch (Throwable exception) {
                androidLogForm(exception.getMessage());
                exception.printStackTrace();
                processException(exception);
                return false;
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        String str = null;
        String obj = componentName == null ? null : componentName.toString();
        if (eventName != null) {
            str = eventName.toString();
        }
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj, str)));
    }

    public void $define() {
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            androidLogForm(exception.getMessage());
            processException(exception);
        }
        Screen2 = this;
        addToFormEnvironment(Lit0, this);
        Object obj = this.events$Mnto$Mnregister;
        while (obj != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj;
                Object event$Mninfo = arg0.getCar();
                Object apply1 = lists.car.apply1(event$Mninfo);
                String obj2 = apply1 == null ? null : apply1.toString();
                Object apply12 = lists.cdr.apply1(event$Mninfo);
                EventDispatcher.registerEventForDelegation(this, obj2, apply12 == null ? null : apply12.toString());
                obj = arg0.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, obj);
            }
        }
        addToGlobalVars(Lit2, lambda$Fn1);
        Screen2 closureEnv = this;
        obj = lists.reverse(this.global$Mnvars$Mnto$Mncreate);
        while (obj != LList.Empty) {
            try {
                arg0 = (Pair) obj;
                Object var$Mnval = arg0.getCar();
                Object var = lists.car.apply1(var$Mnval);
                addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(lists.cadr.apply1(var$Mnval)));
                obj = arg0.getCdr();
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, obj);
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "arg0", -2, obj);
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "add-to-form-environment", 0, component$Mnname);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "lookup-in-form-environment", 0, apply1);
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "arg0", -2, obj);
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "arg0", -2, obj);
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "add-to-global-var-environment", 0, var);
            } catch (ClassCastException e2222222) {
                throw new WrongType(e2222222, "arg0", -2, obj);
            } catch (YailRuntimeError exception2) {
                processException(exception2);
                return;
            }
        }
        obj = lists.reverse(this.form$Mndo$Mnafter$Mncreation);
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            misc.force(arg0.getCar());
            obj = arg0.getCdr();
        }
        LList component$Mndescriptors = lists.reverse(this.components$Mnto$Mncreate);
        closureEnv = this;
        obj = component$Mndescriptors;
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            Object component$Mninfo = arg0.getCar();
            Object component$Mnname = lists.caddr.apply1(component$Mninfo);
            lists.cadddr.apply1(component$Mninfo);
            Object component$Mnobject = Invoke.make.apply2(lists.cadr.apply1(component$Mninfo), lookupInFormEnvironment((Symbol) lists.car.apply1(component$Mninfo)));
            SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
            addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
            obj = arg0.getCdr();
        }
        obj = component$Mndescriptors;
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            component$Mninfo = arg0.getCar();
            lists.caddr.apply1(component$Mninfo);
            Boolean init$Mnthunk = lists.cadddr.apply1(component$Mninfo);
            if (init$Mnthunk != Boolean.FALSE) {
                Scheme.applyToArgs.apply1(init$Mnthunk);
            }
            obj = arg0.getCdr();
        }
        obj = component$Mndescriptors;
        while (obj != LList.Empty) {
            arg0 = (Pair) obj;
            component$Mninfo = arg0.getCar();
            component$Mnname = lists.caddr.apply1(component$Mninfo);
            lists.cadddr.apply1(component$Mninfo);
            callInitialize(SlotGet.field.apply2(this, component$Mnname));
            obj = arg0.getCdr();
        }
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        Object car;
        LList symbols = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Pair result = LList.Empty;
        Object arg0 = symbols;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                car = arg02.getCar();
                try {
                    result = Pair.make(misc.symbol$To$String((Symbol) car), result);
                    arg0 = arg03;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        car = procedure.apply2(moduleMethod, LList.reverseInPlace(result));
        try {
            return misc.string$To$Symbol((CharSequence) car);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, car);
        }
    }

    static Object lambda2() {
        return null;
    }
}
