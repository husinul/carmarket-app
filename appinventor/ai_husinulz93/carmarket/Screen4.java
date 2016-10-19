package appinventor.ai_husinulz93.carmarket;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.ActivityStarter;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
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
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;

/* compiled from: Screen4.yail */
public class Screen4 extends Form implements Runnable {
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10;
    static final FString Lit100;
    static final SimpleSymbol Lit101;
    static final IntNum Lit102;
    static final IntNum Lit103;
    static final FString Lit104;
    static final SimpleSymbol Lit105;
    static final FString Lit106;
    static final SimpleSymbol Lit107;
    static final IntNum Lit108;
    static final FString Lit109;
    static final IntNum Lit11;
    static final FString Lit110;
    static final SimpleSymbol Lit111;
    static final FString Lit112;
    static final FString Lit113;
    static final SimpleSymbol Lit114;
    static final FString Lit115;
    static final FString Lit116;
    static final SimpleSymbol Lit117;
    static final FString Lit118;
    static final FString Lit119;
    static final SimpleSymbol Lit12;
    static final SimpleSymbol Lit120;
    static final IntNum Lit121;
    static final IntNum Lit122;
    static final FString Lit123;
    static final SimpleSymbol Lit124;
    static final FString Lit125;
    static final SimpleSymbol Lit126;
    static final FString Lit127;
    static final FString Lit128;
    static final SimpleSymbol Lit129;
    static final IntNum Lit13;
    static final SimpleSymbol Lit130;
    static final IntNum Lit131;
    static final SimpleSymbol Lit132;
    static final FString Lit133;
    static final FString Lit134;
    static final SimpleSymbol Lit135;
    static final IntNum Lit136;
    static final FString Lit137;
    static final FString Lit138;
    static final SimpleSymbol Lit139;
    static final FString Lit14;
    static final IntNum Lit140;
    static final IntNum Lit141;
    static final FString Lit142;
    static final SimpleSymbol Lit143;
    static final FString Lit144;
    static final FString Lit145;
    static final SimpleSymbol Lit146;
    static final SimpleSymbol Lit147;
    static final SimpleSymbol Lit148;
    static final SimpleSymbol Lit149;
    static final FString Lit15;
    static final SimpleSymbol Lit150;
    static final SimpleSymbol Lit151;
    static final SimpleSymbol Lit152;
    static final SimpleSymbol Lit153;
    static final SimpleSymbol Lit154;
    static final SimpleSymbol Lit155;
    static final SimpleSymbol Lit156;
    static final SimpleSymbol Lit157;
    static final SimpleSymbol Lit16;
    static final IntNum Lit17;
    static final IntNum Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2;
    static final FString Lit20;
    static final PairWithPosition Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final FString Lit24;
    static final SimpleSymbol Lit25;
    static final FString Lit26;
    static final FString Lit27;
    static final SimpleSymbol Lit28;
    static final IntNum Lit29;
    static final SimpleSymbol Lit3;
    static final FString Lit30;
    static final FString Lit31;
    static final SimpleSymbol Lit32;
    static final IntNum Lit33;
    static final IntNum Lit34;
    static final FString Lit35;
    static final PairWithPosition Lit36;
    static final SimpleSymbol Lit37;
    static final FString Lit38;
    static final SimpleSymbol Lit39;
    static final SimpleSymbol Lit4;
    static final IntNum Lit40;
    static final FString Lit41;
    static final FString Lit42;
    static final SimpleSymbol Lit43;
    static final FString Lit44;
    static final FString Lit45;
    static final SimpleSymbol Lit46;
    static final FString Lit47;
    static final FString Lit48;
    static final SimpleSymbol Lit49;
    static final FString Lit5;
    static final SimpleSymbol Lit50;
    static final SimpleSymbol Lit51;
    static final SimpleSymbol Lit52;
    static final IntNum Lit53;
    static final SimpleSymbol Lit54;
    static final FString Lit55;
    static final FString Lit56;
    static final SimpleSymbol Lit57;
    static final IntNum Lit58;
    static final IntNum Lit59;
    static final SimpleSymbol Lit6;
    static final FString Lit60;
    static final SimpleSymbol Lit61;
    static final SimpleSymbol Lit62;
    static final SimpleSymbol Lit63;
    static final SimpleSymbol Lit64;
    static final SimpleSymbol Lit65;
    static final FString Lit66;
    static final SimpleSymbol Lit67;
    static final IntNum Lit68;
    static final FString Lit69;
    static final SimpleSymbol Lit7;
    static final FString Lit70;
    static final SimpleSymbol Lit71;
    static final FString Lit72;
    static final FString Lit73;
    static final SimpleSymbol Lit74;
    static final FString Lit75;
    static final FString Lit76;
    static final SimpleSymbol Lit77;
    static final IntNum Lit78;
    static final SimpleSymbol Lit79;
    static final IntNum Lit8;
    static final FString Lit80;
    static final FString Lit81;
    static final SimpleSymbol Lit82;
    static final IntNum Lit83;
    static final IntNum Lit84;
    static final FString Lit85;
    static final SimpleSymbol Lit86;
    static final FString Lit87;
    static final SimpleSymbol Lit88;
    static final IntNum Lit89;
    static final SimpleSymbol Lit9;
    static final FString Lit90;
    static final FString Lit91;
    static final SimpleSymbol Lit92;
    static final FString Lit93;
    static final FString Lit94;
    static final SimpleSymbol Lit95;
    static final FString Lit96;
    static final FString Lit97;
    static final SimpleSymbol Lit98;
    static final FString Lit99;
    public static Screen4 Screen4;
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
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn36 = null;
    static final ModuleMethod lambda$Fn37 = null;
    static final ModuleMethod lambda$Fn38 = null;
    static final ModuleMethod lambda$Fn39 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn40 = null;
    static final ModuleMethod lambda$Fn41 = null;
    static final ModuleMethod lambda$Fn42 = null;
    static final ModuleMethod lambda$Fn43 = null;
    static final ModuleMethod lambda$Fn44 = null;
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
    public Button Button3;
    public final ModuleMethod Button3$Click;
    public Button Button4;
    public final ModuleMethod Button4$Click;
    public Button Button5;
    public final ModuleMethod Button5$Click;
    public Button Button6;
    public final ModuleMethod Button6$Click;
    public Button Button7;
    public final ModuleMethod Button7$Click;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement10;
    public HorizontalArrangement HorizontalArrangement11;
    public HorizontalArrangement HorizontalArrangement12;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public HorizontalArrangement HorizontalArrangement4;
    public HorizontalArrangement HorizontalArrangement5;
    public HorizontalArrangement HorizontalArrangement6;
    public HorizontalArrangement HorizontalArrangement7;
    public HorizontalArrangement HorizontalArrangement8;
    public HorizontalArrangement HorizontalArrangement9;
    public Label Label1;
    public Label Label2;
    public Label Label3;
    public Label Label4;
    public Label Label5;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement2;
    public VerticalArrangement VerticalArrangement3;
    public VerticalArrangement VerticalArrangement4;
    public VerticalArrangement VerticalArrangement5;
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

    /* compiled from: Screen4.yail */
    public class frame extends ModuleBody {
        Screen4 $main;

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
                    if (!(obj instanceof Screen4)) {
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
                    if (!(obj instanceof Screen4)) {
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
                    return Screen4.lambda2();
                case SetExp.PROCEDURE /*16*/:
                    this.$main.$define();
                    return Values.empty;
                case Sequence.INT_U8_VALUE /*17*/:
                    return Screen4.lambda3();
                case Sequence.INT_S8_VALUE /*18*/:
                    return Screen4.lambda4();
                case Sequence.INT_U16_VALUE /*19*/:
                    return Screen4.lambda5();
                case Sequence.INT_S16_VALUE /*20*/:
                    return Screen4.lambda6();
                case Sequence.INT_U32_VALUE /*21*/:
                    return Screen4.lambda7();
                case Sequence.INT_S32_VALUE /*22*/:
                    return this.$main.Button6$Click();
                case Sequence.INT_U64_VALUE /*23*/:
                    return Screen4.lambda8();
                case Sequence.INT_S64_VALUE /*24*/:
                    return Screen4.lambda9();
                case Sequence.FLOAT_VALUE /*25*/:
                    return Screen4.lambda10();
                case Sequence.DOUBLE_VALUE /*26*/:
                    return Screen4.lambda11();
                case Sequence.BOOLEAN_VALUE /*27*/:
                    return this.$main.Button7$Click();
                case Sequence.TEXT_BYTE_VALUE /*28*/:
                    return Screen4.lambda12();
                case Sequence.CHAR_VALUE /*29*/:
                    return Screen4.lambda13();
                case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                    return Screen4.lambda14();
                case Sequence.CDATA_VALUE /*31*/:
                    return Screen4.lambda15();
                case SetExp.SET_IF_UNBOUND /*32*/:
                    return Screen4.lambda16();
                case Sequence.ELEMENT_VALUE /*33*/:
                    return Screen4.lambda17();
                case Sequence.DOCUMENT_VALUE /*34*/:
                    return this.$main.Button1$Click();
                case Sequence.ATTRIBUTE_VALUE /*35*/:
                    return Screen4.lambda18();
                case Sequence.COMMENT_VALUE /*36*/:
                    return Screen4.lambda19();
                case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                    return Screen4.lambda20();
                case XDataType.STRING_TYPE_CODE /*38*/:
                    return Screen4.lambda21();
                case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
                    return Screen4.lambda22();
                case XDataType.TOKEN_TYPE_CODE /*40*/:
                    return Screen4.lambda23();
                case XDataType.LANGUAGE_TYPE_CODE /*41*/:
                    return Screen4.lambda24();
                case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                    return Screen4.lambda25();
                case XDataType.NAME_TYPE_CODE /*43*/:
                    return this.$main.Button2$Click();
                case XDataType.NCNAME_TYPE_CODE /*44*/:
                    return Screen4.lambda26();
                case XDataType.ID_TYPE_CODE /*45*/:
                    return Screen4.lambda27();
                case XDataType.IDREF_TYPE_CODE /*46*/:
                    return Screen4.lambda28();
                case XDataType.ENTITY_TYPE_CODE /*47*/:
                    return Screen4.lambda29();
                case XDataType.UNTYPED_TYPE_CODE /*48*/:
                    return Screen4.lambda30();
                case 49:
                    return Screen4.lambda31();
                case 50:
                    return this.$main.Button3$Click();
                case 51:
                    return Screen4.lambda32();
                case 52:
                    return Screen4.lambda33();
                case 53:
                    return Screen4.lambda34();
                case 54:
                    return Screen4.lambda35();
                case 55:
                    return Screen4.lambda36();
                case 56:
                    return Screen4.lambda37();
                case 57:
                    return this.$main.Button4$Click();
                case 58:
                    return Screen4.lambda38();
                case 59:
                    return Screen4.lambda39();
                case 60:
                    return Screen4.lambda40();
                case 61:
                    return Screen4.lambda41();
                case 62:
                    return Screen4.lambda42();
                case 63:
                    return Screen4.lambda43();
                case SetExp.HAS_VALUE /*64*/:
                    return Screen4.lambda44();
                case 65:
                    return Screen4.lambda45();
                case 66:
                    return this.$main.Button5$Click();
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
                case XDataType.ENTITY_TYPE_CODE /*47*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case XDataType.UNTYPED_TYPE_CODE /*48*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 49:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 50:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 51:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 52:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 53:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 54:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 55:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 56:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 57:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 58:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 59:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 60:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 61:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 62:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 63:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case SetExp.HAS_VALUE /*64*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 65:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 66:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static {
        Lit157 = (SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve();
        Lit156 = (SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve();
        Lit155 = (SimpleSymbol) new SimpleSymbol("send-error").readResolve();
        Lit154 = (SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve();
        Lit153 = (SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve();
        Lit152 = (SimpleSymbol) new SimpleSymbol("add-to-components").readResolve();
        Lit151 = (SimpleSymbol) new SimpleSymbol("add-to-events").readResolve();
        Lit150 = (SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve();
        Lit149 = (SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve();
        Lit148 = (SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve();
        Lit147 = (SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve();
        Lit146 = (SimpleSymbol) new SimpleSymbol("android-log-form").readResolve();
        Lit145 = new FString("com.google.appinventor.components.runtime.ActivityStarter");
        Lit144 = new FString("com.google.appinventor.components.runtime.ActivityStarter");
        Lit143 = (SimpleSymbol) new SimpleSymbol("Button5$Click").readResolve();
        Lit142 = new FString("com.google.appinventor.components.runtime.Button");
        Lit141 = IntNum.make(-1050);
        Lit140 = IntNum.make(-1011);
        Lit139 = (SimpleSymbol) new SimpleSymbol("Button5").readResolve();
        Lit138 = new FString("com.google.appinventor.components.runtime.Button");
        Lit137 = new FString("com.google.appinventor.components.runtime.Label");
        Lit136 = IntNum.make(-1010);
        Lit135 = (SimpleSymbol) new SimpleSymbol("Label5").readResolve();
        Lit134 = new FString("com.google.appinventor.components.runtime.Label");
        Lit133 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit132 = (SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve();
        Lit131 = IntNum.make(3);
        Lit130 = (SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve();
        Lit129 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement5").readResolve();
        Lit128 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit127 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit126 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement10").readResolve();
        Lit125 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit124 = (SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve();
        Lit123 = new FString("com.google.appinventor.components.runtime.Button");
        Lit122 = IntNum.make(-1045);
        Lit121 = IntNum.make(-1023);
        Lit120 = (SimpleSymbol) new SimpleSymbol("Button4").readResolve();
        Lit119 = new FString("com.google.appinventor.components.runtime.Button");
        Lit118 = new FString("com.google.appinventor.components.runtime.Label");
        Lit117 = (SimpleSymbol) new SimpleSymbol("Label4").readResolve();
        Lit116 = new FString("com.google.appinventor.components.runtime.Label");
        Lit115 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit114 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement4").readResolve();
        Lit113 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit112 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit111 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement9").readResolve();
        Lit110 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit109 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit108 = IntNum.make(-1010);
        Lit107 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement8").readResolve();
        Lit106 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit105 = (SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve();
        Lit104 = new FString("com.google.appinventor.components.runtime.Button");
        Lit103 = IntNum.make(-1040);
        Lit102 = IntNum.make(-1026);
        Lit101 = (SimpleSymbol) new SimpleSymbol("Button3").readResolve();
        Lit100 = new FString("com.google.appinventor.components.runtime.Button");
        Lit99 = new FString("com.google.appinventor.components.runtime.Label");
        Lit98 = (SimpleSymbol) new SimpleSymbol("Label3").readResolve();
        Lit97 = new FString("com.google.appinventor.components.runtime.Label");
        Lit96 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit95 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement3").readResolve();
        Lit94 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit93 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit92 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement7").readResolve();
        Lit91 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit90 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit89 = IntNum.make(-1030);
        Lit88 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement6").readResolve();
        Lit87 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit86 = (SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve();
        Lit85 = new FString("com.google.appinventor.components.runtime.Button");
        Lit84 = IntNum.make(-1055);
        Lit83 = IntNum.make(-1018);
        Lit82 = (SimpleSymbol) new SimpleSymbol("Button2").readResolve();
        Lit81 = new FString("com.google.appinventor.components.runtime.Button");
        Lit80 = new FString("com.google.appinventor.components.runtime.Label");
        Lit79 = (SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve();
        Lit78 = IntNum.make(-1025);
        Lit77 = (SimpleSymbol) new SimpleSymbol("Label2").readResolve();
        Lit76 = new FString("com.google.appinventor.components.runtime.Label");
        Lit75 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit74 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve();
        Lit73 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit72 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit71 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve();
        Lit70 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit69 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit68 = IntNum.make(-1010);
        Lit67 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement5").readResolve();
        Lit66 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit65 = (SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve();
        Lit64 = (SimpleSymbol) new SimpleSymbol("StartActivity").readResolve();
        Lit63 = (SimpleSymbol) new SimpleSymbol("Action").readResolve();
        Lit62 = (SimpleSymbol) new SimpleSymbol("DataUri").readResolve();
        Lit61 = (SimpleSymbol) new SimpleSymbol("ActivityStarter1").readResolve();
        Lit60 = new FString("com.google.appinventor.components.runtime.Button");
        Lit59 = IntNum.make(-1040);
        Lit58 = IntNum.make(-1020);
        Lit57 = (SimpleSymbol) new SimpleSymbol("Button1").readResolve();
        Lit56 = new FString("com.google.appinventor.components.runtime.Button");
        Lit55 = new FString("com.google.appinventor.components.runtime.Label");
        Lit54 = (SimpleSymbol) new SimpleSymbol("Text").readResolve();
        Lit53 = IntNum.make(2);
        Lit52 = (SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve();
        Lit51 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit50 = (SimpleSymbol) new SimpleSymbol("FontBold").readResolve();
        Lit49 = (SimpleSymbol) new SimpleSymbol("Label1").readResolve();
        Lit48 = new FString("com.google.appinventor.components.runtime.Label");
        Lit47 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit46 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve();
        Lit45 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit44 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit43 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve();
        Lit42 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit41 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit40 = IntNum.make(-1030);
        Lit39 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve();
        Lit38 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit37 = (SimpleSymbol) new SimpleSymbol("Button7$Click").readResolve();
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit4 = simpleSymbol;
        Lit36 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen4.yail", 249934);
        Lit35 = new FString("com.google.appinventor.components.runtime.Button");
        Lit34 = IntNum.make(-1015);
        Lit33 = IntNum.make(-1010);
        Lit32 = (SimpleSymbol) new SimpleSymbol("Button7").readResolve();
        Lit31 = new FString("com.google.appinventor.components.runtime.Button");
        Lit30 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit29 = IntNum.make(-1060);
        Lit28 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement12").readResolve();
        Lit27 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit26 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit25 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement11").readResolve();
        Lit24 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit23 = (SimpleSymbol) new SimpleSymbol("Click").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("Button6$Click").readResolve();
        Lit21 = PairWithPosition.make(Lit4, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen4.yail", 147534);
        Lit20 = new FString("com.google.appinventor.components.runtime.Button");
        Lit19 = (SimpleSymbol) new SimpleSymbol("Image").readResolve();
        Lit18 = IntNum.make(-1010);
        Lit17 = IntNum.make(-1010);
        Lit16 = (SimpleSymbol) new SimpleSymbol("Button6").readResolve();
        Lit15 = new FString("com.google.appinventor.components.runtime.Button");
        Lit14 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit13 = IntNum.make(-2);
        Lit12 = (SimpleSymbol) new SimpleSymbol("Width").readResolve();
        Lit11 = IntNum.make(-1010);
        Lit10 = (SimpleSymbol) new SimpleSymbol("Height").readResolve();
        Lit9 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit8 = IntNum.make((int) Component.COLOR_NONE);
        Lit7 = (SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve();
        Lit5 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit3 = (SimpleSymbol) new SimpleSymbol("AppName").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("getMessage").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("Screen4").readResolve();
    }

    public Screen4() {
        ModuleInfo.register(this);
        ModuleBody appinventor_ai_husinulz93_carmarket_Screen4_frame = new frame();
        appinventor_ai_husinulz93_carmarket_Screen4_frame.$main = this;
        this.android$Mnlog$Mnform = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 1, Lit146, 4097);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 2, Lit147, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 3, Lit148, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 5, Lit149, 4097);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 6, Lit150, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 7, Lit151, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 8, Lit152, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 9, Lit153, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 10, Lit154, 4097);
        this.send$Mnerror = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 11, Lit155, 4097);
        this.process$Mnexception = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 12, "process-exception", 4097);
        this.dispatchEvent = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 13, Lit156, 16388);
        this.lookup$Mnhandler = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 14, Lit157, 8194);
        PropertySet moduleMethod = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 15, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime8872982264086122834.scm:547");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 16, "$define", 0);
        lambda$Fn2 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 17, null, 0);
        lambda$Fn3 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 18, null, 0);
        lambda$Fn4 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 19, null, 0);
        lambda$Fn5 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 20, null, 0);
        lambda$Fn6 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 21, null, 0);
        this.Button6$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 22, Lit22, 0);
        lambda$Fn7 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 23, null, 0);
        lambda$Fn8 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 24, null, 0);
        lambda$Fn9 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 25, null, 0);
        lambda$Fn10 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 26, null, 0);
        this.Button7$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 27, Lit37, 0);
        lambda$Fn11 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 28, null, 0);
        lambda$Fn12 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 29, null, 0);
        lambda$Fn13 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 30, null, 0);
        lambda$Fn14 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 31, null, 0);
        lambda$Fn15 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 32, null, 0);
        lambda$Fn16 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 33, null, 0);
        this.Button1$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 34, Lit65, 0);
        lambda$Fn17 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 35, null, 0);
        lambda$Fn18 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 36, null, 0);
        lambda$Fn19 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 37, null, 0);
        lambda$Fn20 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 38, null, 0);
        lambda$Fn21 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 39, null, 0);
        lambda$Fn22 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 40, null, 0);
        lambda$Fn23 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 41, null, 0);
        lambda$Fn24 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 42, null, 0);
        this.Button2$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 43, Lit86, 0);
        lambda$Fn25 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 44, null, 0);
        lambda$Fn26 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 45, null, 0);
        lambda$Fn27 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 46, null, 0);
        lambda$Fn28 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 47, null, 0);
        lambda$Fn29 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 48, null, 0);
        lambda$Fn30 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 49, null, 0);
        this.Button3$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 50, Lit105, 0);
        lambda$Fn31 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 51, null, 0);
        lambda$Fn32 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 52, null, 0);
        lambda$Fn33 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 53, null, 0);
        lambda$Fn34 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 54, null, 0);
        lambda$Fn35 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 55, null, 0);
        lambda$Fn36 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 56, null, 0);
        this.Button4$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 57, Lit124, 0);
        lambda$Fn37 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 58, null, 0);
        lambda$Fn38 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 59, null, 0);
        lambda$Fn39 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 60, null, 0);
        lambda$Fn40 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 61, null, 0);
        lambda$Fn41 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 62, null, 0);
        lambda$Fn42 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 63, null, 0);
        lambda$Fn43 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 64, null, 0);
        lambda$Fn44 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 65, null, 0);
        this.Button5$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen4_frame, 66, Lit143, 0);
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
            Screen4 = null;
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
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit5, Lit6, lambda$Fn3), $result);
                } else {
                    addToComponents(Lit0, Lit14, Lit6, lambda$Fn4);
                }
                this.Button6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit15, Lit16, lambda$Fn5), $result);
                } else {
                    addToComponents(Lit6, Lit20, Lit16, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit22, this.Button6$Click);
                } else {
                    addToFormEnvironment(Lit22, this.Button6$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button6", "Click");
                } else {
                    addToEvents(Lit16, Lit23);
                }
                this.HorizontalArrangement11 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit24, Lit25, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit6, Lit26, Lit25, Boolean.FALSE);
                }
                this.HorizontalArrangement12 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit27, Lit28, lambda$Fn7), $result);
                } else {
                    addToComponents(Lit6, Lit30, Lit28, lambda$Fn8);
                }
                this.Button7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit31, Lit32, lambda$Fn9), $result);
                } else {
                    addToComponents(Lit6, Lit35, Lit32, lambda$Fn10);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit37, this.Button7$Click);
                } else {
                    addToFormEnvironment(Lit37, this.Button7$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button7", "Click");
                } else {
                    addToEvents(Lit32, Lit23);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit38, Lit39, lambda$Fn11), $result);
                } else {
                    addToComponents(Lit0, Lit41, Lit39, lambda$Fn12);
                }
                this.HorizontalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit39, Lit42, Lit43, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit39, Lit44, Lit43, Boolean.FALSE);
                }
                this.VerticalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit43, Lit45, Lit46, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit43, Lit47, Lit46, Boolean.FALSE);
                }
                this.Label1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit46, Lit48, Lit49, lambda$Fn13), $result);
                } else {
                    addToComponents(Lit46, Lit55, Lit49, lambda$Fn14);
                }
                this.Button1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit46, Lit56, Lit57, lambda$Fn15), $result);
                } else {
                    addToComponents(Lit46, Lit60, Lit57, lambda$Fn16);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit65, this.Button1$Click);
                } else {
                    addToFormEnvironment(Lit65, this.Button1$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
                } else {
                    addToEvents(Lit57, Lit23);
                }
                this.HorizontalArrangement5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit39, Lit66, Lit67, lambda$Fn17), $result);
                } else {
                    addToComponents(Lit39, Lit69, Lit67, lambda$Fn18);
                }
                this.HorizontalArrangement4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit39, Lit70, Lit71, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit39, Lit72, Lit71, Boolean.FALSE);
                }
                this.VerticalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit71, Lit73, Lit74, lambda$Fn19), $result);
                } else {
                    addToComponents(Lit71, Lit75, Lit74, lambda$Fn20);
                }
                this.Label2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit74, Lit76, Lit77, lambda$Fn21), $result);
                } else {
                    addToComponents(Lit74, Lit80, Lit77, lambda$Fn22);
                }
                this.Button2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit74, Lit81, Lit82, lambda$Fn23), $result);
                } else {
                    addToComponents(Lit74, Lit85, Lit82, lambda$Fn24);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit86, this.Button2$Click);
                } else {
                    addToFormEnvironment(Lit86, this.Button2$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
                } else {
                    addToEvents(Lit82, Lit23);
                }
                this.HorizontalArrangement6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit87, Lit88, lambda$Fn25), $result);
                } else {
                    addToComponents(Lit0, Lit90, Lit88, lambda$Fn26);
                }
                this.HorizontalArrangement7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit88, Lit91, Lit92, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit88, Lit93, Lit92, Boolean.FALSE);
                }
                this.VerticalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit92, Lit94, Lit95, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit92, Lit96, Lit95, Boolean.FALSE);
                }
                this.Label3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit95, Lit97, Lit98, lambda$Fn27), $result);
                } else {
                    addToComponents(Lit95, Lit99, Lit98, lambda$Fn28);
                }
                this.Button3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit95, Lit100, Lit101, lambda$Fn29), $result);
                } else {
                    addToComponents(Lit95, Lit104, Lit101, lambda$Fn30);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit105, this.Button3$Click);
                } else {
                    addToFormEnvironment(Lit105, this.Button3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
                } else {
                    addToEvents(Lit101, Lit23);
                }
                this.HorizontalArrangement8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit88, Lit106, Lit107, lambda$Fn31), $result);
                } else {
                    addToComponents(Lit88, Lit109, Lit107, lambda$Fn32);
                }
                this.HorizontalArrangement9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit88, Lit110, Lit111, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit88, Lit112, Lit111, Boolean.FALSE);
                }
                this.VerticalArrangement4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit111, Lit113, Lit114, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit111, Lit115, Lit114, Boolean.FALSE);
                }
                this.Label4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit114, Lit116, Lit117, lambda$Fn33), $result);
                } else {
                    addToComponents(Lit114, Lit118, Lit117, lambda$Fn34);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit114, Lit119, Lit120, lambda$Fn35), $result);
                } else {
                    addToComponents(Lit114, Lit123, Lit120, lambda$Fn36);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit124, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit124, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit120, Lit23);
                }
                this.HorizontalArrangement10 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit125, Lit126, lambda$Fn37), $result);
                } else {
                    addToComponents(Lit0, Lit127, Lit126, lambda$Fn38);
                }
                this.VerticalArrangement5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit126, Lit128, Lit129, lambda$Fn39), $result);
                } else {
                    addToComponents(Lit126, Lit133, Lit129, lambda$Fn40);
                }
                this.Label5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit129, Lit134, Lit135, lambda$Fn41), $result);
                } else {
                    addToComponents(Lit129, Lit137, Lit135, lambda$Fn42);
                }
                this.Button5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit129, Lit138, Lit139, lambda$Fn43), $result);
                } else {
                    addToComponents(Lit129, Lit142, Lit139, lambda$Fn44);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit143, this.Button5$Click);
                } else {
                    addToFormEnvironment(Lit143, this.Button5$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button5", "Click");
                } else {
                    addToEvents(Lit139, Lit23);
                }
                this.ActivityStarter1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit144, Lit61, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit145, Lit61, Boolean.FALSE);
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
        runtime.setAndCoerceProperty$Ex(Lit16, Lit10, Lit17, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit12, Lit18, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit16, Lit19, "images(4).png", Lit4);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit16, Lit10, Lit17, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit12, Lit18, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit16, Lit19, "images(4).png", Lit4);
    }

    public Object Button6$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen2"), Lit21, "open another screen");
    }

    static Object lambda8() {
        return runtime.setAndCoerceProperty$Ex(Lit28, Lit12, Lit29, Lit9);
    }

    static Object lambda9() {
        return runtime.setAndCoerceProperty$Ex(Lit28, Lit12, Lit29, Lit9);
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit32, Lit10, Lit33, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit32, Lit12, Lit34, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit32, Lit19, "images.png", Lit4);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit32, Lit10, Lit33, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit32, Lit12, Lit34, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit32, Lit19, "images.png", Lit4);
    }

    public Object Button7$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit36, "open another screen");
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit39, Lit10, Lit40, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit39, Lit12, Lit13, Lit9);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit39, Lit10, Lit40, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit39, Lit12, Lit13, Lit9);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit49, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit52, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit49, Lit54, "Hatchback", Lit4);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit49, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit52, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit49, Lit54, "Hatchback", Lit4);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit57, Lit10, Lit58, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit12, Lit59, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit57, Lit19, "download.jpg", Lit4);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit57, Lit10, Lit58, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit12, Lit59, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit57, Lit19, "download.jpg", Lit4);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit61, Lit62, "https://autoportal.com/newcars/hatchback-cars-in-india-cf/", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit63, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit61, Lit64, LList.Empty, LList.Empty);
    }

    static Object lambda18() {
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit12, Lit68, Lit9);
    }

    static Object lambda19() {
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit12, Lit68, Lit9);
    }

    static Object lambda20() {
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit12, Lit13, Lit9);
    }

    static Object lambda21() {
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit12, Lit13, Lit9);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit52, Lit53, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit12, Lit78, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit54, "Sedan", Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit79, Lit53, Lit9);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit52, Lit53, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit12, Lit78, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit54, "Sedan", Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit79, Lit53, Lit9);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit82, Lit10, Lit83, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit82, Lit12, Lit84, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit82, Lit19, "download(3).jpg", Lit4);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit82, Lit10, Lit83, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit82, Lit12, Lit84, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit82, Lit19, "download(3).jpg", Lit4);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit61, Lit62, "https://www.cardekho.com/new-diesel+sedans+cars", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit63, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit61, Lit64, LList.Empty, LList.Empty);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit10, Lit89, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit12, Lit13, Lit9);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit10, Lit89, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit12, Lit13, Lit9);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit98, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit52, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit54, "Suv", Lit4);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit98, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit52, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit54, "Suv", Lit4);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit101, Lit10, Lit102, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit101, Lit12, Lit103, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit19, "download(2).jpg", Lit4);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit101, Lit10, Lit102, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit101, Lit12, Lit103, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit19, "download(2).jpg", Lit4);
    }

    public Object Button3$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit61, Lit62, "https://autoportal.com/newcars/suv-cars-in-india-cf/", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit63, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit61, Lit64, LList.Empty, LList.Empty);
    }

    static Object lambda32() {
        return runtime.setAndCoerceProperty$Ex(Lit107, Lit12, Lit108, Lit9);
    }

    static Object lambda33() {
        return runtime.setAndCoerceProperty$Ex(Lit107, Lit12, Lit108, Lit9);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit117, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit117, Lit52, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit117, Lit54, "Muv", Lit4);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit117, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit117, Lit52, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit117, Lit54, "Muv", Lit4);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit120, Lit10, Lit121, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit120, Lit12, Lit122, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit120, Lit19, "images(7).jpg", Lit4);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit120, Lit10, Lit121, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit120, Lit12, Lit122, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit120, Lit19, "images(7).jpg", Lit4);
    }

    public Object Button4$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit61, Lit62, "https://www.cardekho.com/new-muv+cars", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit63, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit61, Lit64, LList.Empty, LList.Empty);
    }

    static Object lambda38() {
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit12, Lit13, Lit9);
    }

    static Object lambda39() {
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit12, Lit13, Lit9);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit129, Lit130, Lit131, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit132, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit12, Lit13, Lit9);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit129, Lit130, Lit131, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit132, Lit53, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit12, Lit13, Lit9);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit52, Lit53, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit10, Lit136, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit54, "Luxury", Lit4);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit50, Boolean.TRUE, Lit51);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit52, Lit53, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit10, Lit136, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit54, "Luxury", Lit4);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit139, Lit10, Lit140, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit12, Lit141, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit139, Lit19, "images(8).jpg", Lit4);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit139, Lit10, Lit140, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit12, Lit141, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit139, Lit19, "images(8).jpg", Lit4);
    }

    public Object Button5$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit61, Lit62, "https://www.cardekho.com/new-luxury+cars", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit63, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit61, Lit64, LList.Empty, LList.Empty);
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
        Screen4 = this;
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
        Screen4 closureEnv = this;
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
