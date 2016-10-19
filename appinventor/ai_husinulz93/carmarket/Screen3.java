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

/* compiled from: Screen3.yail */
public class Screen3 extends Form implements Runnable {
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10;
    static final IntNum Lit100;
    static final IntNum Lit101;
    static final FString Lit102;
    static final SimpleSymbol Lit103;
    static final FString Lit104;
    static final FString Lit105;
    static final SimpleSymbol Lit106;
    static final SimpleSymbol Lit107;
    static final SimpleSymbol Lit108;
    static final SimpleSymbol Lit109;
    static final IntNum Lit11;
    static final SimpleSymbol Lit110;
    static final SimpleSymbol Lit111;
    static final SimpleSymbol Lit112;
    static final SimpleSymbol Lit113;
    static final SimpleSymbol Lit114;
    static final SimpleSymbol Lit115;
    static final SimpleSymbol Lit116;
    static final SimpleSymbol Lit117;
    static final FString Lit12;
    static final FString Lit13;
    static final SimpleSymbol Lit14;
    static final IntNum Lit15;
    static final IntNum Lit16;
    static final SimpleSymbol Lit17;
    static final FString Lit18;
    static final PairWithPosition Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final FString Lit22;
    static final SimpleSymbol Lit23;
    static final IntNum Lit24;
    static final IntNum Lit25;
    static final FString Lit26;
    static final PairWithPosition Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final FString Lit30;
    static final SimpleSymbol Lit31;
    static final IntNum Lit32;
    static final FString Lit33;
    static final FString Lit34;
    static final SimpleSymbol Lit35;
    static final FString Lit36;
    static final FString Lit37;
    static final SimpleSymbol Lit38;
    static final SimpleSymbol Lit39;
    static final SimpleSymbol Lit4;
    static final IntNum Lit40;
    static final SimpleSymbol Lit41;
    static final FString Lit42;
    static final FString Lit43;
    static final SimpleSymbol Lit44;
    static final FString Lit45;
    static final FString Lit46;
    static final SimpleSymbol Lit47;
    static final FString Lit48;
    static final FString Lit49;
    static final FString Lit5;
    static final SimpleSymbol Lit50;
    static final IntNum Lit51;
    static final IntNum Lit52;
    static final FString Lit53;
    static final SimpleSymbol Lit54;
    static final SimpleSymbol Lit55;
    static final SimpleSymbol Lit56;
    static final SimpleSymbol Lit57;
    static final SimpleSymbol Lit58;
    static final FString Lit59;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit60;
    static final IntNum Lit61;
    static final IntNum Lit62;
    static final FString Lit63;
    static final SimpleSymbol Lit64;
    static final FString Lit65;
    static final SimpleSymbol Lit66;
    static final IntNum Lit67;
    static final IntNum Lit68;
    static final FString Lit69;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70;
    static final FString Lit71;
    static final SimpleSymbol Lit72;
    static final IntNum Lit73;
    static final IntNum Lit74;
    static final FString Lit75;
    static final SimpleSymbol Lit76;
    static final FString Lit77;
    static final SimpleSymbol Lit78;
    static final FString Lit79;
    static final IntNum Lit8;
    static final FString Lit80;
    static final SimpleSymbol Lit81;
    static final IntNum Lit82;
    static final IntNum Lit83;
    static final FString Lit84;
    static final SimpleSymbol Lit85;
    static final FString Lit86;
    static final SimpleSymbol Lit87;
    static final IntNum Lit88;
    static final IntNum Lit89;
    static final SimpleSymbol Lit9;
    static final FString Lit90;
    static final SimpleSymbol Lit91;
    static final FString Lit92;
    static final SimpleSymbol Lit93;
    static final IntNum Lit94;
    static final IntNum Lit95;
    static final FString Lit96;
    static final SimpleSymbol Lit97;
    static final FString Lit98;
    static final SimpleSymbol Lit99;
    public static Screen3 Screen3;
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
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public ActivityStarter ActivityStarter1;
    public Button Button10;
    public final ModuleMethod Button10$Click;
    public Button Button11;
    public final ModuleMethod Button11$Click;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public Button Button3;
    public final ModuleMethod Button3$Click;
    public Button Button4;
    public final ModuleMethod Button4$Click;
    public Button Button5;
    public final ModuleMethod Button5$Click;
    public Button Button6;
    public final ModuleMethod Button6$TouchDown;
    public Button Button7;
    public final ModuleMethod Button7$Click;
    public Button Button8;
    public final ModuleMethod Button8$Click;
    public Button Button9;
    public final ModuleMethod Button9$Click;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public HorizontalArrangement HorizontalArrangement4;
    public HorizontalArrangement HorizontalArrangement5;
    public Label Label1;
    public VerticalArrangement VerticalArrangement1;
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

    /* compiled from: Screen3.yail */
    public class frame extends ModuleBody {
        Screen3 $main;

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
                    if (!(obj instanceof Screen3)) {
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
                    if (!(obj instanceof Screen3)) {
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
                    return Screen3.lambda2();
                case SetExp.PROCEDURE /*16*/:
                    this.$main.$define();
                    return Values.empty;
                case Sequence.INT_U8_VALUE /*17*/:
                    return Screen3.lambda3();
                case Sequence.INT_S8_VALUE /*18*/:
                    return Screen3.lambda4();
                case Sequence.INT_U16_VALUE /*19*/:
                    return Screen3.lambda5();
                case Sequence.INT_S16_VALUE /*20*/:
                    return Screen3.lambda6();
                case Sequence.INT_U32_VALUE /*21*/:
                    return Screen3.lambda7();
                case Sequence.INT_S32_VALUE /*22*/:
                    return this.$main.Button6$TouchDown();
                case Sequence.INT_U64_VALUE /*23*/:
                    return Screen3.lambda8();
                case Sequence.INT_S64_VALUE /*24*/:
                    return Screen3.lambda9();
                case Sequence.FLOAT_VALUE /*25*/:
                    return this.$main.Button7$Click();
                case Sequence.DOUBLE_VALUE /*26*/:
                    return Screen3.lambda10();
                case Sequence.BOOLEAN_VALUE /*27*/:
                    return Screen3.lambda11();
                case Sequence.TEXT_BYTE_VALUE /*28*/:
                    return Screen3.lambda12();
                case Sequence.CHAR_VALUE /*29*/:
                    return Screen3.lambda13();
                case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                    return Screen3.lambda14();
                case Sequence.CDATA_VALUE /*31*/:
                    return Screen3.lambda15();
                case SetExp.SET_IF_UNBOUND /*32*/:
                    return Screen3.lambda16();
                case Sequence.ELEMENT_VALUE /*33*/:
                    return Screen3.lambda17();
                case Sequence.DOCUMENT_VALUE /*34*/:
                    return Screen3.lambda18();
                case Sequence.ATTRIBUTE_VALUE /*35*/:
                    return Screen3.lambda19();
                case Sequence.COMMENT_VALUE /*36*/:
                    return this.$main.Button2$Click();
                case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                    return Screen3.lambda20();
                case XDataType.STRING_TYPE_CODE /*38*/:
                    return Screen3.lambda21();
                case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
                    return this.$main.Button3$Click();
                case XDataType.TOKEN_TYPE_CODE /*40*/:
                    return Screen3.lambda22();
                case XDataType.LANGUAGE_TYPE_CODE /*41*/:
                    return Screen3.lambda23();
                case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                    return this.$main.Button4$Click();
                case XDataType.NAME_TYPE_CODE /*43*/:
                    return Screen3.lambda24();
                case XDataType.NCNAME_TYPE_CODE /*44*/:
                    return Screen3.lambda25();
                case XDataType.ID_TYPE_CODE /*45*/:
                    return this.$main.Button5$Click();
                case XDataType.IDREF_TYPE_CODE /*46*/:
                    return Screen3.lambda26();
                case XDataType.ENTITY_TYPE_CODE /*47*/:
                    return Screen3.lambda27();
                case XDataType.UNTYPED_TYPE_CODE /*48*/:
                    return Screen3.lambda28();
                case 49:
                    return Screen3.lambda29();
                case 50:
                    return this.$main.Button8$Click();
                case 51:
                    return Screen3.lambda30();
                case 52:
                    return Screen3.lambda31();
                case 53:
                    return this.$main.Button11$Click();
                case 54:
                    return Screen3.lambda32();
                case 55:
                    return Screen3.lambda33();
                case 56:
                    return this.$main.Button9$Click();
                case 57:
                    return Screen3.lambda34();
                case 58:
                    return Screen3.lambda35();
                case 59:
                    return this.$main.Button10$Click();
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
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static {
        Lit117 = (SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve();
        Lit116 = (SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve();
        Lit115 = (SimpleSymbol) new SimpleSymbol("send-error").readResolve();
        Lit114 = (SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve();
        Lit113 = (SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve();
        Lit112 = (SimpleSymbol) new SimpleSymbol("add-to-components").readResolve();
        Lit111 = (SimpleSymbol) new SimpleSymbol("add-to-events").readResolve();
        Lit110 = (SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve();
        Lit109 = (SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve();
        Lit108 = (SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve();
        Lit107 = (SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve();
        Lit106 = (SimpleSymbol) new SimpleSymbol("android-log-form").readResolve();
        Lit105 = new FString("com.google.appinventor.components.runtime.ActivityStarter");
        Lit104 = new FString("com.google.appinventor.components.runtime.ActivityStarter");
        Lit103 = (SimpleSymbol) new SimpleSymbol("Button10$Click").readResolve();
        Lit102 = new FString("com.google.appinventor.components.runtime.Button");
        Lit101 = IntNum.make(-1025);
        Lit100 = IntNum.make(-1014);
        Lit99 = (SimpleSymbol) new SimpleSymbol("Button10").readResolve();
        Lit98 = new FString("com.google.appinventor.components.runtime.Button");
        Lit97 = (SimpleSymbol) new SimpleSymbol("Button9$Click").readResolve();
        Lit96 = new FString("com.google.appinventor.components.runtime.Button");
        Lit95 = IntNum.make(-1022);
        Lit94 = IntNum.make(-1015);
        Lit93 = (SimpleSymbol) new SimpleSymbol("Button9").readResolve();
        Lit92 = new FString("com.google.appinventor.components.runtime.Button");
        Lit91 = (SimpleSymbol) new SimpleSymbol("Button11$Click").readResolve();
        Lit90 = new FString("com.google.appinventor.components.runtime.Button");
        Lit89 = IntNum.make(-1025);
        Lit88 = IntNum.make(-1017);
        Lit87 = (SimpleSymbol) new SimpleSymbol("Button11").readResolve();
        Lit86 = new FString("com.google.appinventor.components.runtime.Button");
        Lit85 = (SimpleSymbol) new SimpleSymbol("Button8$Click").readResolve();
        Lit84 = new FString("com.google.appinventor.components.runtime.Button");
        Lit83 = IntNum.make(-1025);
        Lit82 = IntNum.make(-1017);
        Lit81 = (SimpleSymbol) new SimpleSymbol("Button8").readResolve();
        Lit80 = new FString("com.google.appinventor.components.runtime.Button");
        Lit79 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit78 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement5").readResolve();
        Lit77 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit76 = (SimpleSymbol) new SimpleSymbol("Button5$Click").readResolve();
        Lit75 = new FString("com.google.appinventor.components.runtime.Button");
        Lit74 = IntNum.make(-1025);
        Lit73 = IntNum.make(-1010);
        Lit72 = (SimpleSymbol) new SimpleSymbol("Button5").readResolve();
        Lit71 = new FString("com.google.appinventor.components.runtime.Button");
        Lit70 = (SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve();
        Lit69 = new FString("com.google.appinventor.components.runtime.Button");
        Lit68 = IntNum.make(-1025);
        Lit67 = IntNum.make(-1010);
        Lit66 = (SimpleSymbol) new SimpleSymbol("Button4").readResolve();
        Lit65 = new FString("com.google.appinventor.components.runtime.Button");
        Lit64 = (SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve();
        Lit63 = new FString("com.google.appinventor.components.runtime.Button");
        Lit62 = IntNum.make(-1025);
        Lit61 = IntNum.make(-1010);
        Lit60 = (SimpleSymbol) new SimpleSymbol("Button3").readResolve();
        Lit59 = new FString("com.google.appinventor.components.runtime.Button");
        Lit58 = (SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve();
        Lit57 = (SimpleSymbol) new SimpleSymbol("StartActivity").readResolve();
        Lit56 = (SimpleSymbol) new SimpleSymbol("Action").readResolve();
        Lit55 = (SimpleSymbol) new SimpleSymbol("DataUri").readResolve();
        Lit54 = (SimpleSymbol) new SimpleSymbol("ActivityStarter1").readResolve();
        Lit53 = new FString("com.google.appinventor.components.runtime.Button");
        Lit52 = IntNum.make(-1025);
        Lit51 = IntNum.make(-1010);
        Lit50 = (SimpleSymbol) new SimpleSymbol("Button2").readResolve();
        Lit49 = new FString("com.google.appinventor.components.runtime.Button");
        Lit48 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit47 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve();
        Lit46 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit45 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit44 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve();
        Lit43 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit42 = new FString("com.google.appinventor.components.runtime.Label");
        Lit41 = (SimpleSymbol) new SimpleSymbol("Text").readResolve();
        Lit40 = IntNum.make(18);
        Lit39 = (SimpleSymbol) new SimpleSymbol("FontSize").readResolve();
        Lit38 = (SimpleSymbol) new SimpleSymbol("Label1").readResolve();
        Lit37 = new FString("com.google.appinventor.components.runtime.Label");
        Lit36 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit35 = (SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve();
        Lit34 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit33 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit32 = IntNum.make(-1050);
        Lit31 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve();
        Lit30 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit29 = (SimpleSymbol) new SimpleSymbol("Click").readResolve();
        Lit28 = (SimpleSymbol) new SimpleSymbol("Button7$Click").readResolve();
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit4 = simpleSymbol;
        Lit27 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen3.yail", 192590);
        Lit26 = new FString("com.google.appinventor.components.runtime.Button");
        Lit25 = IntNum.make(-1020);
        Lit24 = IntNum.make(-1010);
        Lit23 = (SimpleSymbol) new SimpleSymbol("Button7").readResolve();
        Lit22 = new FString("com.google.appinventor.components.runtime.Button");
        Lit21 = (SimpleSymbol) new SimpleSymbol("TouchDown").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol("Button6$TouchDown").readResolve();
        Lit19 = PairWithPosition.make(Lit4, LList.Empty, "/tmp/1476823049413_0.8628316352842967-0/youngandroidproject/../src/appinventor/ai_husinulz93/carmarket/Screen3.yail", 139342);
        Lit18 = new FString("com.google.appinventor.components.runtime.Button");
        Lit17 = (SimpleSymbol) new SimpleSymbol("Image").readResolve();
        Lit16 = IntNum.make(-1020);
        Lit15 = IntNum.make(-1010);
        Lit14 = (SimpleSymbol) new SimpleSymbol("Button6").readResolve();
        Lit13 = new FString("com.google.appinventor.components.runtime.Button");
        Lit12 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit11 = IntNum.make(-2);
        Lit10 = (SimpleSymbol) new SimpleSymbol("Width").readResolve();
        Lit9 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit8 = IntNum.make(-1010);
        Lit7 = (SimpleSymbol) new SimpleSymbol("Height").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve();
        Lit5 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit3 = (SimpleSymbol) new SimpleSymbol("AppName").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("getMessage").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("Screen3").readResolve();
    }

    public Screen3() {
        ModuleInfo.register(this);
        ModuleBody appinventor_ai_husinulz93_carmarket_Screen3_frame = new frame();
        appinventor_ai_husinulz93_carmarket_Screen3_frame.$main = this;
        this.android$Mnlog$Mnform = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 1, Lit106, 4097);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 2, Lit107, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 3, Lit108, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 5, Lit109, 4097);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 6, Lit110, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 7, Lit111, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 8, Lit112, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 9, Lit113, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 10, Lit114, 4097);
        this.send$Mnerror = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 11, Lit115, 4097);
        this.process$Mnexception = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 12, "process-exception", 4097);
        this.dispatchEvent = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 13, Lit116, 16388);
        this.lookup$Mnhandler = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 14, Lit117, 8194);
        PropertySet moduleMethod = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 15, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime8872982264086122834.scm:547");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 16, "$define", 0);
        lambda$Fn2 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 17, null, 0);
        lambda$Fn3 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 18, null, 0);
        lambda$Fn4 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 19, null, 0);
        lambda$Fn5 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 20, null, 0);
        lambda$Fn6 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 21, null, 0);
        this.Button6$TouchDown = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 22, Lit20, 0);
        lambda$Fn7 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 23, null, 0);
        lambda$Fn8 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 24, null, 0);
        this.Button7$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 25, Lit28, 0);
        lambda$Fn9 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 26, null, 0);
        lambda$Fn10 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 27, null, 0);
        lambda$Fn11 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 28, null, 0);
        lambda$Fn12 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 29, null, 0);
        lambda$Fn13 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 30, null, 0);
        lambda$Fn14 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 31, null, 0);
        lambda$Fn15 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 32, null, 0);
        lambda$Fn16 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 33, null, 0);
        lambda$Fn17 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 34, null, 0);
        lambda$Fn18 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 35, null, 0);
        this.Button2$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 36, Lit58, 0);
        lambda$Fn19 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 37, null, 0);
        lambda$Fn20 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 38, null, 0);
        this.Button3$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 39, Lit64, 0);
        lambda$Fn21 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 40, null, 0);
        lambda$Fn22 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 41, null, 0);
        this.Button4$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 42, Lit70, 0);
        lambda$Fn23 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 43, null, 0);
        lambda$Fn24 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 44, null, 0);
        this.Button5$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 45, Lit76, 0);
        lambda$Fn25 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 46, null, 0);
        lambda$Fn26 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 47, null, 0);
        lambda$Fn27 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 48, null, 0);
        lambda$Fn28 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 49, null, 0);
        this.Button8$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 50, Lit85, 0);
        lambda$Fn29 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 51, null, 0);
        lambda$Fn30 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 52, null, 0);
        this.Button11$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 53, Lit91, 0);
        lambda$Fn31 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 54, null, 0);
        lambda$Fn32 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 55, null, 0);
        this.Button9$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 56, Lit97, 0);
        lambda$Fn33 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 57, null, 0);
        lambda$Fn34 = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 58, null, 0);
        this.Button10$Click = new ModuleMethod(appinventor_ai_husinulz93_carmarket_Screen3_frame, 59, Lit103, 0);
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
            Screen3 = null;
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
                    addToComponents(Lit0, Lit12, Lit6, lambda$Fn4);
                }
                this.Button6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit13, Lit14, lambda$Fn5), $result);
                } else {
                    addToComponents(Lit6, Lit18, Lit14, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit20, this.Button6$TouchDown);
                } else {
                    addToFormEnvironment(Lit20, this.Button6$TouchDown);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button6", "TouchDown");
                } else {
                    addToEvents(Lit14, Lit21);
                }
                this.Button7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit6, Lit22, Lit23, lambda$Fn7), $result);
                } else {
                    addToComponents(Lit6, Lit26, Lit23, lambda$Fn8);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit28, this.Button7$Click);
                } else {
                    addToFormEnvironment(Lit28, this.Button7$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button7", "Click");
                } else {
                    addToEvents(Lit23, Lit29);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit30, Lit31, lambda$Fn9), $result);
                } else {
                    addToComponents(Lit0, Lit33, Lit31, lambda$Fn10);
                }
                this.VerticalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit34, Lit35, lambda$Fn11), $result);
                } else {
                    addToComponents(Lit0, Lit36, Lit35, lambda$Fn12);
                }
                this.Label1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit35, Lit37, Lit38, lambda$Fn13), $result);
                } else {
                    addToComponents(Lit35, Lit42, Lit38, lambda$Fn14);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit35, Lit43, Lit44, lambda$Fn15), $result);
                } else {
                    addToComponents(Lit35, Lit45, Lit44, lambda$Fn16);
                }
                this.HorizontalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit44, Lit46, Lit47, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit44, Lit48, Lit47, Boolean.FALSE);
                }
                this.Button2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit47, Lit49, Lit50, lambda$Fn17), $result);
                } else {
                    addToComponents(Lit47, Lit53, Lit50, lambda$Fn18);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit58, this.Button2$Click);
                } else {
                    addToFormEnvironment(Lit58, this.Button2$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
                } else {
                    addToEvents(Lit50, Lit29);
                }
                this.Button3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit44, Lit59, Lit60, lambda$Fn19), $result);
                } else {
                    addToComponents(Lit44, Lit63, Lit60, lambda$Fn20);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit64, this.Button3$Click);
                } else {
                    addToFormEnvironment(Lit64, this.Button3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
                } else {
                    addToEvents(Lit60, Lit29);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit44, Lit65, Lit66, lambda$Fn21), $result);
                } else {
                    addToComponents(Lit44, Lit69, Lit66, lambda$Fn22);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit70, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit70, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit66, Lit29);
                }
                this.Button5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit44, Lit71, Lit72, lambda$Fn23), $result);
                } else {
                    addToComponents(Lit44, Lit75, Lit72, lambda$Fn24);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit76, this.Button5$Click);
                } else {
                    addToFormEnvironment(Lit76, this.Button5$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button5", "Click");
                } else {
                    addToEvents(Lit72, Lit29);
                }
                this.HorizontalArrangement5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit35, Lit77, Lit78, lambda$Fn25), $result);
                } else {
                    addToComponents(Lit35, Lit79, Lit78, lambda$Fn26);
                }
                this.Button8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit78, Lit80, Lit81, lambda$Fn27), $result);
                } else {
                    addToComponents(Lit78, Lit84, Lit81, lambda$Fn28);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit85, this.Button8$Click);
                } else {
                    addToFormEnvironment(Lit85, this.Button8$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button8", "Click");
                } else {
                    addToEvents(Lit81, Lit29);
                }
                this.Button11 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit78, Lit86, Lit87, lambda$Fn29), $result);
                } else {
                    addToComponents(Lit78, Lit90, Lit87, lambda$Fn30);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit91, this.Button11$Click);
                } else {
                    addToFormEnvironment(Lit91, this.Button11$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button11", "Click");
                } else {
                    addToEvents(Lit87, Lit29);
                }
                this.Button9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit78, Lit92, Lit93, lambda$Fn31), $result);
                } else {
                    addToComponents(Lit78, Lit96, Lit93, lambda$Fn32);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit97, this.Button9$Click);
                } else {
                    addToFormEnvironment(Lit97, this.Button9$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button9", "Click");
                } else {
                    addToEvents(Lit93, Lit29);
                }
                this.Button10 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit78, Lit98, Lit99, lambda$Fn33), $result);
                } else {
                    addToComponents(Lit78, Lit102, Lit99, lambda$Fn34);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit103, this.Button10$Click);
                } else {
                    addToFormEnvironment(Lit103, this.Button10$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button10", "Click");
                } else {
                    addToEvents(Lit99, Lit29);
                }
                this.ActivityStarter1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit104, Lit54, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit105, Lit54, Boolean.FALSE);
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
        return runtime.setAndCoerceProperty$Ex(Lit6, Lit10, Lit11, Lit9);
    }

    static Object lambda5() {
        runtime.setAndCoerceProperty$Ex(Lit6, Lit7, Lit8, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit6, Lit10, Lit11, Lit9);
    }

    static Object lambda6() {
        runtime.setAndCoerceProperty$Ex(Lit14, Lit7, Lit15, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit14, Lit10, Lit16, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit14, Lit17, "images.png", Lit4);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit14, Lit7, Lit15, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit14, Lit10, Lit16, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit14, Lit17, "images.png", Lit4);
    }

    public Object Button6$TouchDown() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit19, "open another screen");
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit23, Lit7, Lit24, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit23, Lit10, Lit25, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit23, Lit17, "54f0b93e1ccd6c2e19daf922_car-icon.png", Lit4);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit23, Lit7, Lit24, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit23, Lit10, Lit25, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit23, Lit17, "54f0b93e1ccd6c2e19daf922_car-icon.png", Lit4);
    }

    public Object Button7$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen2"), Lit27, "open another screen");
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit31, Lit7, Lit32, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit31, Lit10, Lit11, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit31, Lit17, "1bmwcarwallpaper.jpg", Lit4);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit31, Lit7, Lit32, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit31, Lit10, Lit11, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit31, Lit17, "1bmwcarwallpaper.jpg", Lit4);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit35, Lit7, Lit11, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit10, Lit11, Lit9);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit35, Lit7, Lit11, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit10, Lit11, Lit9);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit38, Lit39, Lit40, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit38, Lit41, "All Brands", Lit4);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit38, Lit39, Lit40, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit38, Lit41, "All Brands", Lit4);
    }

    static Object lambda16() {
        return runtime.setAndCoerceProperty$Ex(Lit44, Lit10, Lit11, Lit9);
    }

    static Object lambda17() {
        return runtime.setAndCoerceProperty$Ex(Lit44, Lit10, Lit11, Lit9);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit50, Lit7, Lit51, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit10, Lit52, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit17, "images(3).jpg", Lit4);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit50, Lit7, Lit51, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit10, Lit52, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit17, "images(3).jpg", Lit4);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.gaadi.com/new-cars/tata?utm_campaign=Tata&utm_device=c&utm_term=on%20road%20price%20tata%20car&utm_content=search&utm_medium=cpc&utm_source=google&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxd_hE61eFzuTwUcfik8pTRRXGt55OfW-MYkwYcBdufHxoC_5Pw_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit60, Lit7, Lit61, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit60, Lit10, Lit62, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit17, "1354164298_suzuki-logo_0_0_0.jpg", Lit4);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit60, Lit7, Lit61, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit60, Lit10, Lit62, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit17, "1354164298_suzuki-logo_0_0_0.jpg", Lit4);
    }

    public Object Button3$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.cardekho.com/cars/Maruti?utm_campaign=ser-nonm-maruti&utm_device=c&utm_term=maruti%20car%20price&utm_content=search&utm_medium=cpc&utm_source=google&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxdK8U7RHQuh_eKSYrt9L9IxxnSTrzBrqJDHTbWGsPEVhoCFxLw_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit7, Lit67, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit10, Lit68, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit17, "images(3).png", Lit4);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit7, Lit67, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit10, Lit68, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit17, "images(3).png", Lit4);
    }

    public Object Button4$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.zigwheels.com/newcars/Mahindra", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit72, Lit7, Lit73, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit72, Lit10, Lit74, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit72, Lit17, "images(4).jpg", Lit4);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit72, Lit7, Lit73, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit72, Lit10, Lit74, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit72, Lit17, "images(4).jpg", Lit4);
    }

    public Object Button5$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.cardekho.com/cars/Ford?utm_campaign=ford-gen&utm_device=c&utm_term=ford&utm_source=google_adwords&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxdTVeIAoqq2NT5BPpfjEmkf0QbMR5HY3uzW5iW8tcEmRoCkq7w_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda26() {
        return runtime.setAndCoerceProperty$Ex(Lit78, Lit10, Lit11, Lit9);
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit78, Lit10, Lit11, Lit9);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit81, Lit7, Lit82, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit10, Lit83, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit17, "images(5).png", Lit4);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit81, Lit7, Lit82, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit10, Lit83, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit17, "images(5).png", Lit4);
    }

    public Object Button8$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.cardekho.com/cars/Honda?utm_campaign=ser-nonm-honda&utm_device=c&utm_term=honda%20car%20price&utm_content=search&utm_medium=cpc&utm_source=google&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxd70Iym_7ERuzL4tUiaB9-OB-GBorYijR2OEnk-WBpOxoCVUDw_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit87, Lit7, Lit88, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit10, Lit89, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit17, "download(4).jpg", Lit4);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit87, Lit7, Lit88, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit10, Lit89, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit17, "download(4).jpg", Lit4);
    }

    public Object Button11$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.cardekho.com/cars/Hyundai?utm_campaign=SER-Nonm-Hyundai&utm_source=google_adwords&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxd4tGXOSpzElCcwEKyplUODGnhjt5tVj_umQcHZOpPQBoCA6nw_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit7, Lit94, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit10, Lit95, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit17, "images(5).jpg", Lit4);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit7, Lit94, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit10, Lit95, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit17, "images(5).jpg", Lit4);
    }

    public Object Button9$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.gaadi.com/new-cars/nissan?utm_campaign=Nissan&utm_device=c&utm_term=models%20of%20nissan%20cars%20in%20india&utm_content=search&utm_medium=cpc&utm_source=google&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxd1yvj6xknC_eYq88YI-VsIYAcchssenVktfoCl8GBvxoCS3Tw_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit99, Lit7, Lit100, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit10, Lit101, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit17, "toyota.jpg", Lit4);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit99, Lit7, Lit100, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit10, Lit101, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit17, "toyota.jpg", Lit4);
    }

    public Object Button10$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit54, Lit55, "https://www.gaadi.com/new-cars/toyota?utm_campaign=Toyota-Cars-Branded&utm_device=c&utm_term=toyota%20car%20price%20in%20india&utm_content=search&utm_medium=cpc&utm_source=google&gclid=CjwKEAjwkJfABRDnhbPlx6WI4ncSJADMQqxdDtG7LylnUEYSvE-rbDCUUocBvho0j1AtHrEqv1U-XRoCIHXw_wcB", Lit4);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit56, "android.intent.action.VIEW", Lit4);
        return runtime.callComponentMethod(Lit54, Lit57, LList.Empty, LList.Empty);
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
        Screen3 = this;
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
        Screen3 closureEnv = this;
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
