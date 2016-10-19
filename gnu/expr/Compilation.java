package gnu.expr;

import gnu.bytecode.Access;
import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.SwitchState;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.slib.srfi1;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.MethodProc;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.text.Lexer;
import gnu.text.Options;
import gnu.text.Options.OptionInfo;
import gnu.text.Path;
import gnu.text.PrettyWriter;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kawa.Shell;

public class Compilation implements SourceLocator {
    public static final int BODY_PARSED = 4;
    public static final int CALL_WITH_CONSUMER = 2;
    public static final int CALL_WITH_CONTINUATIONS = 4;
    public static final int CALL_WITH_RETURN = 1;
    public static final int CALL_WITH_TAILCALLS = 3;
    public static final int CALL_WITH_UNSPECIFIED = 0;
    public static final int CLASS_WRITTEN = 14;
    public static final int COMPILED = 12;
    public static final int COMPILE_SETUP = 10;
    public static final int ERROR_SEEN = 100;
    public static final int MODULE_NONSTATIC = -1;
    public static final int MODULE_STATIC = 1;
    public static final int MODULE_STATIC_DEFAULT = 0;
    public static final int MODULE_STATIC_RUN = 2;
    public static final int PROLOG_PARSED = 2;
    public static final int PROLOG_PARSING = 1;
    public static final int RESOLVED = 6;
    public static final int WALKED = 8;
    public static Type[] apply0args;
    public static Method apply0method;
    public static Type[] apply1args;
    public static Method apply1method;
    public static Type[] apply2args;
    public static Method apply2method;
    public static Method apply3method;
    public static Method apply4method;
    private static Type[] applyCpsArgs;
    public static Method applyCpsMethod;
    public static Type[] applyNargs;
    public static Method applyNmethod;
    public static Method[] applymethods;
    public static Field argsCallContextField;
    private static Compilation chainUninitialized;
    static Method checkArgCountMethod;
    public static String classPrefixDefault;
    private static final ThreadLocal<Compilation> current;
    public static boolean debugPrintExpr;
    public static boolean debugPrintFinalExpr;
    public static int defaultCallConvention;
    public static int defaultClassFileVersion;
    public static boolean emitSourceDebugExtAttr;
    public static final Field falseConstant;
    public static boolean generateMainDefault;
    public static Method getCallContextInstanceMethod;
    public static Method getCurrentEnvironmentMethod;
    public static final Method getLocation1EnvironmentMethod;
    public static final Method getLocation2EnvironmentMethod;
    public static final Method getLocationMethod;
    public static final Method getProcedureBindingMethod;
    public static final Method getSymbolProcedureMethod;
    public static final Method getSymbolValueMethod;
    public static boolean inlineOk;
    public static final Type[] int1Args;
    public static ClassType javaStringType;
    static Method makeListMethod;
    public static int moduleStatic;
    public static Field noArgsField;
    public static final ArrayType objArrayType;
    public static Options options;
    public static Field pcCallContextField;
    public static Field procCallContextField;
    public static ClassType scmBooleanType;
    public static ClassType scmKeywordType;
    public static ClassType scmListType;
    public static ClassType scmSequenceType;
    static final Method setNameMethod;
    public static final Type[] string1Arg;
    public static final Type[] sym1Arg;
    public static final Field trueConstant;
    public static ClassType typeApplet;
    public static ClassType typeCallContext;
    public static ClassType typeClass;
    public static ClassType typeClassType;
    public static final ClassType typeConsumer;
    public static ClassType typeEnvironment;
    public static ClassType typeLanguage;
    public static ClassType typeLocation;
    public static ClassType typeMethodProc;
    public static ClassType typeModuleBody;
    public static ClassType typeModuleMethod;
    public static ClassType typeModuleWithContext;
    public static ClassType typeObject;
    public static ClassType typeObjectType;
    public static ClassType typePair;
    public static ClassType typeProcedure;
    public static ClassType typeProcedure0;
    public static ClassType typeProcedure1;
    public static ClassType typeProcedure2;
    public static ClassType typeProcedure3;
    public static ClassType typeProcedure4;
    public static ClassType[] typeProcedureArray;
    public static ClassType typeProcedureN;
    public static ClassType typeRunnable;
    public static ClassType typeServlet;
    public static ClassType typeString;
    public static ClassType typeSymbol;
    public static ClassType typeType;
    public static ClassType typeValues;
    public static OptionInfo warnAsError;
    public static OptionInfo warnInvokeUnknownMethod;
    public static OptionInfo warnUndefinedVariable;
    public static OptionInfo warnUnknownMember;
    Variable callContextVar;
    Variable callContextVarForInit;
    public String classPrefix;
    ClassType[] classes;
    Initializer clinitChain;
    Method clinitMethod;
    public ClassType curClass;
    public LambdaExp curLambda;
    public Options currentOptions;
    protected ScopeExp current_scope;
    public boolean explicit;
    public Stack<Expression> exprStack;
    Method forNameHelper;
    SwitchState fswitch;
    Field fswitchIndex;
    public boolean generateMain;
    public boolean immediate;
    private int keyUninitialized;
    int langOptions;
    protected Language language;
    public Lexer lexer;
    public NameLookup lexical;
    LitTable litTable;
    ArrayClassLoader loader;
    int localFieldIndex;
    public ClassType mainClass;
    public ModuleExp mainLambda;
    int maxSelectorValue;
    protected SourceMessages messages;
    public Method method;
    int method_counter;
    public ModuleInfo minfo;
    public ClassType moduleClass;
    Field moduleInstanceMainField;
    Variable moduleInstanceVar;
    public boolean mustCompile;
    private Compilation nextUninitialized;
    int numClasses;
    boolean pedantic;
    public Stack<Object> pendingImports;
    private int state;
    public Variable thisDecl;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isPedantic() {
        return this.pedantic;
    }

    public void pushPendingImport(ModuleInfo info, ScopeExp defs, int formSize) {
        if (this.pendingImports == null) {
            this.pendingImports = new Stack();
        }
        this.pendingImports.push(info);
        this.pendingImports.push(defs);
        Expression posExp = new ReferenceExp(null);
        posExp.setLine(this);
        this.pendingImports.push(posExp);
        this.pendingImports.push(Integer.valueOf(formSize));
    }

    static {
        debugPrintExpr = false;
        options = new Options();
        warnUndefinedVariable = options.add("warn-undefined-variable", PROLOG_PARSING, Boolean.TRUE, "warn if no compiler-visible binding for a variable");
        warnUnknownMember = options.add("warn-unknown-member", PROLOG_PARSING, Boolean.TRUE, "warn if referencing an unknown method or field");
        warnInvokeUnknownMethod = options.add("warn-invoke-unknown-method", PROLOG_PARSING, warnUnknownMember, "warn if invoke calls an unknown method (subsumed by warn-unknown-member)");
        warnAsError = options.add("warn-as-error", PROLOG_PARSING, Boolean.FALSE, "Make all warnings into errors");
        defaultClassFileVersion = ClassType.JDK_1_5_VERSION;
        moduleStatic = MODULE_STATIC_DEFAULT;
        typeObject = Type.objectType;
        scmBooleanType = ClassType.make("java.lang.Boolean");
        typeString = ClassType.make("java.lang.String");
        javaStringType = typeString;
        scmKeywordType = ClassType.make("gnu.expr.Keyword");
        scmSequenceType = ClassType.make("gnu.lists.Sequence");
        scmListType = ClassType.make("gnu.lists.LList");
        typePair = ClassType.make("gnu.lists.Pair");
        objArrayType = ArrayType.make(typeObject);
        typeRunnable = ClassType.make("java.lang.Runnable");
        typeType = ClassType.make("gnu.bytecode.Type");
        typeObjectType = ClassType.make("gnu.bytecode.ObjectType");
        typeClass = Type.javalangClassType;
        typeClassType = ClassType.make("gnu.bytecode.ClassType");
        typeProcedure = ClassType.make("gnu.mapping.Procedure");
        typeLanguage = ClassType.make("gnu.expr.Language");
        typeEnvironment = ClassType.make("gnu.mapping.Environment");
        typeLocation = ClassType.make("gnu.mapping.Location");
        typeSymbol = ClassType.make("gnu.mapping.Symbol");
        getSymbolValueMethod = typeLanguage.getDeclaredMethod("getSymbolValue", (int) PROLOG_PARSING);
        getSymbolProcedureMethod = typeLanguage.getDeclaredMethod("getSymbolProcedure", (int) PROLOG_PARSING);
        getLocationMethod = typeLocation.addMethod("get", Type.typeArray0, Type.objectType, (int) PROLOG_PARSING);
        getProcedureBindingMethod = typeSymbol.addMethod("getProcedure", Type.typeArray0, typeProcedure, (int) PROLOG_PARSING);
        trueConstant = scmBooleanType.getDeclaredField("TRUE");
        falseConstant = scmBooleanType.getDeclaredField("FALSE");
        setNameMethod = typeProcedure.getDeclaredMethod("setName", (int) PROLOG_PARSING);
        Type[] typeArr = new Type[PROLOG_PARSING];
        typeArr[MODULE_STATIC_DEFAULT] = Type.intType;
        int1Args = typeArr;
        typeArr = new Type[PROLOG_PARSING];
        typeArr[MODULE_STATIC_DEFAULT] = javaStringType;
        string1Arg = typeArr;
        sym1Arg = string1Arg;
        getLocation1EnvironmentMethod = typeEnvironment.getDeclaredMethod("getLocation", (int) PROLOG_PARSING);
        Type[] args = new Type[PROLOG_PARSED];
        args[MODULE_STATIC_DEFAULT] = typeSymbol;
        args[PROLOG_PARSING] = Type.objectType;
        getLocation2EnvironmentMethod = typeEnvironment.addMethod("getLocation", args, typeLocation, 17);
        Type[] makeListArgs = new Type[PROLOG_PARSED];
        makeListArgs[MODULE_STATIC_DEFAULT] = objArrayType;
        makeListArgs[PROLOG_PARSING] = Type.intType;
        makeListMethod = scmListType.addMethod("makeList", makeListArgs, scmListType, 9);
        getCurrentEnvironmentMethod = typeEnvironment.addMethod("getCurrent", Type.typeArray0, typeEnvironment, 9);
        apply0args = Type.typeArray0;
        typeArr = new Type[PROLOG_PARSING];
        typeArr[MODULE_STATIC_DEFAULT] = typeObject;
        apply1args = typeArr;
        typeArr = new Type[PROLOG_PARSED];
        typeArr[MODULE_STATIC_DEFAULT] = typeObject;
        typeArr[PROLOG_PARSING] = typeObject;
        apply2args = typeArr;
        typeArr = new Type[PROLOG_PARSING];
        typeArr[MODULE_STATIC_DEFAULT] = objArrayType;
        applyNargs = typeArr;
        apply0method = typeProcedure.addMethod("apply0", apply0args, typeObject, 17);
        apply1method = typeProcedure.addMethod("apply1", apply1args, typeObject, (int) PROLOG_PARSING);
        apply2method = typeProcedure.addMethod("apply2", apply2args, typeObject, (int) PROLOG_PARSING);
        Type[] apply3args = new Type[CALL_WITH_TAILCALLS];
        apply3args[MODULE_STATIC_DEFAULT] = typeObject;
        apply3args[PROLOG_PARSING] = typeObject;
        apply3args[PROLOG_PARSED] = typeObject;
        apply3method = typeProcedure.addMethod("apply3", apply3args, typeObject, (int) PROLOG_PARSING);
        Type[] apply4args = new Type[CALL_WITH_CONTINUATIONS];
        apply4args[MODULE_STATIC_DEFAULT] = typeObject;
        apply4args[PROLOG_PARSING] = typeObject;
        apply4args[PROLOG_PARSED] = typeObject;
        apply4args[CALL_WITH_TAILCALLS] = typeObject;
        apply4method = typeProcedure.addMethod("apply4", apply4args, typeObject, (int) PROLOG_PARSING);
        applyNmethod = typeProcedure.addMethod("applyN", applyNargs, typeObject, (int) PROLOG_PARSING);
        args = new Type[PROLOG_PARSED];
        args[MODULE_STATIC_DEFAULT] = typeProcedure;
        args[PROLOG_PARSING] = Type.intType;
        checkArgCountMethod = typeProcedure.addMethod("checkArgCount", args, Type.voidType, 9);
        Method[] methodArr = new Method[RESOLVED];
        methodArr[MODULE_STATIC_DEFAULT] = apply0method;
        methodArr[PROLOG_PARSING] = apply1method;
        methodArr[PROLOG_PARSED] = apply2method;
        methodArr[CALL_WITH_TAILCALLS] = apply3method;
        methodArr[CALL_WITH_CONTINUATIONS] = apply4method;
        methodArr[5] = applyNmethod;
        applymethods = methodArr;
        typeProcedure0 = ClassType.make("gnu.mapping.Procedure0");
        typeProcedure1 = ClassType.make("gnu.mapping.Procedure1");
        typeProcedure2 = ClassType.make("gnu.mapping.Procedure2");
        typeProcedure3 = ClassType.make("gnu.mapping.Procedure3");
        typeProcedure4 = ClassType.make("gnu.mapping.Procedure4");
        typeProcedureN = ClassType.make("gnu.mapping.ProcedureN");
        typeModuleBody = ClassType.make("gnu.expr.ModuleBody");
        typeModuleWithContext = ClassType.make("gnu.expr.ModuleWithContext");
        typeApplet = ClassType.make("java.applet.Applet");
        typeServlet = ClassType.make("gnu.kawa.servlet.KawaServlet");
        typeCallContext = ClassType.make("gnu.mapping.CallContext");
        typeConsumer = ClassType.make("gnu.lists.Consumer");
        getCallContextInstanceMethod = typeCallContext.getDeclaredMethod("getInstance", (int) MODULE_STATIC_DEFAULT);
        typeValues = ClassType.make("gnu.mapping.Values");
        noArgsField = typeValues.getDeclaredField("noArgs");
        pcCallContextField = typeCallContext.getDeclaredField("pc");
        typeMethodProc = ClassType.make("gnu.mapping.MethodProc");
        typeModuleMethod = ClassType.make("gnu.expr.ModuleMethod");
        argsCallContextField = typeCallContext.getDeclaredField("values");
        procCallContextField = typeCallContext.getDeclaredField("proc");
        typeArr = new Type[PROLOG_PARSING];
        typeArr[MODULE_STATIC_DEFAULT] = typeCallContext;
        applyCpsArgs = typeArr;
        applyCpsMethod = typeProcedure.addMethod("apply", applyCpsArgs, Type.voidType, (int) PROLOG_PARSING);
        typeProcedureArray = new ClassType[]{typeProcedure0, typeProcedure1, typeProcedure2, typeProcedure3, typeProcedure4};
        generateMainDefault = false;
        inlineOk = true;
        classPrefixDefault = ElementType.MATCH_ANY_LOCALNAME;
        emitSourceDebugExtAttr = true;
        current = new InheritableThreadLocal();
    }

    public boolean warnUndefinedVariable() {
        return this.currentOptions.getBoolean(warnUndefinedVariable);
    }

    public boolean warnUnknownMember() {
        return this.currentOptions.getBoolean(warnUnknownMember);
    }

    public boolean warnInvokeUnknownMethod() {
        return this.currentOptions.getBoolean(warnInvokeUnknownMethod);
    }

    public boolean warnAsError() {
        return this.currentOptions.getBoolean(warnAsError);
    }

    public final boolean getBooleanOption(String key, boolean defaultValue) {
        return this.currentOptions.getBoolean(key, defaultValue);
    }

    public final boolean getBooleanOption(String key) {
        return this.currentOptions.getBoolean(key);
    }

    public boolean usingCPStyle() {
        return defaultCallConvention == CALL_WITH_CONTINUATIONS;
    }

    public boolean usingTailCalls() {
        return defaultCallConvention >= CALL_WITH_TAILCALLS;
    }

    public final CodeAttr getCode() {
        return this.method.getCode();
    }

    public boolean generatingApplet() {
        return (this.langOptions & 16) != 0;
    }

    public boolean generatingServlet() {
        return (this.langOptions & 32) != 0;
    }

    public boolean sharedModuleDefs() {
        return (this.langOptions & PROLOG_PARSED) != 0;
    }

    public void setSharedModuleDefs(boolean shared) {
        if (shared) {
            this.langOptions |= PROLOG_PARSED;
        } else {
            this.langOptions &= -3;
        }
    }

    public final ClassType getModuleType() {
        return defaultCallConvention >= PROLOG_PARSED ? typeModuleWithContext : typeModuleBody;
    }

    public void compileConstant(Object value) {
        CodeAttr code = getCode();
        if (value == null) {
            code.emitPushNull();
        } else if (!(value instanceof String) || this.immediate) {
            code.emitGetStatic(compileConstantToField(value));
        } else {
            code.emitPushString((String) value);
        }
    }

    public Field compileConstantToField(Object value) {
        Literal literal = this.litTable.findLiteral(value);
        if (literal.field == null) {
            literal.assign(this.litTable);
        }
        return literal.field;
    }

    public boolean inlineOk(Expression proc) {
        if (proc instanceof LambdaExp) {
            LambdaExp lproc = (LambdaExp) proc;
            Declaration nameDecl = lproc.nameDecl;
            if (nameDecl == null || nameDecl.getSymbol() == null || !(nameDecl.context instanceof ModuleExp)) {
                return true;
            }
            if (this.immediate && nameDecl.isPublic() && !lproc.getFlag(LambdaExp.OVERLOADABLE_FIELD) && (this.curLambda == null || lproc.topLevel() != this.curLambda.topLevel())) {
                return false;
            }
        }
        return inlineOk;
    }

    public boolean inlineOk(Procedure proc) {
        if (this.immediate && (proc instanceof ModuleMethod) && (((ModuleMethod) proc).module.getClass().getClassLoader() instanceof ArrayClassLoader)) {
            return false;
        }
        return inlineOk;
    }

    public void compileConstant(Object value, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            if (value instanceof Values) {
                Object[] values = ((Values) value).getValues();
                int len = values.length;
                if (target instanceof ConsumerTarget) {
                    for (int i = MODULE_STATIC_DEFAULT; i < len; i += PROLOG_PARSING) {
                        compileConstant(values[i], target);
                    }
                    return;
                }
            }
            if (target instanceof ConditionalTarget) {
                ConditionalTarget ctarg = (ConditionalTarget) target;
                getCode().emitGoto(getLanguage().isTrue(value) ? ctarg.ifTrue : ctarg.ifFalse);
                return;
            }
            Type type;
            if (target instanceof StackTarget) {
                Type type2 = ((StackTarget) target).getType();
                if (type2 instanceof PrimType) {
                    try {
                        String signature = type2.getSignature();
                        CodeAttr code = getCode();
                        char sig1 = (signature == null || signature.length() != PROLOG_PARSING) ? ' ' : signature.charAt(MODULE_STATIC_DEFAULT);
                        if (value instanceof Number) {
                            Number num = (Number) value;
                            switch (sig1) {
                                case 'B':
                                    code.emitPushInt(num.byteValue());
                                    return;
                                case 'D':
                                    code.emitPushDouble(num.doubleValue());
                                    return;
                                case PrettyWriter.NEWLINE_FILL /*70*/:
                                    code.emitPushFloat(num.floatValue());
                                    return;
                                case 'I':
                                    code.emitPushInt(num.intValue());
                                    return;
                                case 'J':
                                    code.emitPushLong(num.longValue());
                                    return;
                                case PrettyWriter.NEWLINE_SPACE /*83*/:
                                    code.emitPushInt(num.shortValue());
                                    return;
                            }
                        }
                        if (sig1 == Access.CLASS_CONTEXT) {
                            code.emitPushInt(((PrimType) type2).charValue(value));
                            return;
                        } else if (sig1 == 'Z') {
                            code.emitPushInt(PrimType.booleanValue(value) ? PROLOG_PARSING : MODULE_STATIC_DEFAULT);
                            return;
                        }
                    } catch (ClassCastException e) {
                    }
                }
                if (type2 == typeClass && (value instanceof ClassType)) {
                    loadClassRef((ClassType) value);
                    return;
                }
                try {
                    value = type2.coerceFromObject(value);
                } catch (Exception e2) {
                    StringBuffer sbuf = new StringBuffer();
                    if (value == Values.empty) {
                        sbuf.append("cannot convert void to ");
                    } else {
                        sbuf.append("cannot convert literal (of type ");
                        if (value == null) {
                            sbuf.append("<null>");
                        } else {
                            sbuf.append(value.getClass().getName());
                        }
                        sbuf.append(") to ");
                    }
                    sbuf.append(type2.getName());
                    error('w', sbuf.toString());
                }
            }
            compileConstant(value);
            if (value == null) {
                type = target.getType();
            } else {
                type = Type.make(value.getClass());
            }
            target.compileFromStack(this, type);
        }
    }

    private void dumpInitializers(Initializer inits) {
        for (Initializer init = Initializer.reverse(inits); init != null; init = init.next) {
            init.emit(this);
        }
    }

    public ClassType findNamedClass(String name) {
        for (int i = MODULE_STATIC_DEFAULT; i < this.numClasses; i += PROLOG_PARSING) {
            if (name.equals(this.classes[i].getName())) {
                return this.classes[i];
            }
        }
        return null;
    }

    private static void putURLWords(String name, StringBuffer sbuf) {
        int dot = name.indexOf(46);
        if (dot > 0) {
            putURLWords(name.substring(dot + PROLOG_PARSING), sbuf);
            sbuf.append('.');
            name = name.substring(MODULE_STATIC_DEFAULT, dot);
        }
        sbuf.append(name);
    }

    public static String mangleURI(String name) {
        boolean hasSlash = name.indexOf(47) >= 0;
        int len = name.length();
        if (len > RESOLVED && name.startsWith("class:")) {
            return name.substring(RESOLVED);
        }
        if (len > 5 && name.charAt(CALL_WITH_CONTINUATIONS) == ':' && name.substring(MODULE_STATIC_DEFAULT, CALL_WITH_CONTINUATIONS).equalsIgnoreCase("http")) {
            name = name.substring(5);
            len -= 5;
            hasSlash = true;
        } else if (len > CALL_WITH_CONTINUATIONS && name.charAt(CALL_WITH_TAILCALLS) == ':' && name.substring(MODULE_STATIC_DEFAULT, CALL_WITH_TAILCALLS).equalsIgnoreCase("uri")) {
            name = name.substring(CALL_WITH_CONTINUATIONS);
            len -= 4;
        }
        int start = MODULE_STATIC_DEFAULT;
        StringBuffer sbuf = new StringBuffer();
        while (true) {
            int end;
            int slash = name.indexOf(47, start);
            if (slash < 0) {
                end = len;
            } else {
                end = slash;
            }
            boolean first = sbuf.length() == 0;
            if (first && hasSlash) {
                String host = name.substring(start, end);
                if (end - start > CALL_WITH_CONTINUATIONS && host.startsWith("www.")) {
                    host = host.substring(CALL_WITH_CONTINUATIONS);
                }
                putURLWords(host, sbuf);
            } else if (start != end) {
                if (!first) {
                    sbuf.append('.');
                }
                if (end == len) {
                    int dot = name.lastIndexOf(46, len);
                    if (dot > start + PROLOG_PARSING && !first) {
                        int extLen = len - dot;
                        if (extLen <= CALL_WITH_CONTINUATIONS || (extLen == 5 && name.endsWith("html"))) {
                            len -= extLen;
                            end = len;
                            name = name.substring(MODULE_STATIC_DEFAULT, len);
                        }
                    }
                }
                sbuf.append(name.substring(start, end));
            }
            if (slash < 0) {
                return sbuf.toString();
            }
            start = slash + PROLOG_PARSING;
        }
    }

    public static String mangleName(String name) {
        return mangleName(name, (int) MODULE_NONSTATIC);
    }

    public static String mangleNameIfNeeded(String name) {
        return (name == null || isValidJavaName(name)) ? name : mangleName(name, (int) MODULE_STATIC_DEFAULT);
    }

    public static boolean isValidJavaName(String name) {
        int len = name.length();
        if (len == 0 || !Character.isJavaIdentifierStart(name.charAt(MODULE_STATIC_DEFAULT))) {
            return false;
        }
        int i = len;
        do {
            i += MODULE_NONSTATIC;
            if (i <= 0) {
                return true;
            }
        } while (Character.isJavaIdentifierPart(name.charAt(i)));
        return false;
    }

    public static String mangleName(String name, boolean reversible) {
        return mangleName(name, reversible ? PROLOG_PARSING : MODULE_NONSTATIC);
    }

    public static String mangleName(String name, int kind) {
        boolean reversible;
        if (kind >= 0) {
            reversible = true;
        } else {
            reversible = false;
        }
        int len = name.length();
        if (len == RESOLVED && name.equals("*init*")) {
            return "<init>";
        }
        StringBuffer mangled = new StringBuffer(len);
        boolean upcaseNext = false;
        int i = MODULE_STATIC_DEFAULT;
        while (i < len) {
            char ch = name.charAt(i);
            if (upcaseNext) {
                ch = Character.toTitleCase(ch);
                upcaseNext = false;
            }
            if (Character.isDigit(ch)) {
                if (i == 0) {
                    mangled.append("$N");
                }
                mangled.append(ch);
            } else if (Character.isLetter(ch) || ch == '_') {
                mangled.append(ch);
            } else if (ch == '$') {
                mangled.append(kind > PROLOG_PARSING ? "$$" : "$");
            } else {
                switch (ch) {
                    case Sequence.ELEMENT_VALUE /*33*/:
                        mangled.append("$Ex");
                        break;
                    case Sequence.DOCUMENT_VALUE /*34*/:
                        mangled.append("$Dq");
                        break;
                    case Sequence.ATTRIBUTE_VALUE /*35*/:
                        mangled.append("$Nm");
                        break;
                    case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                        mangled.append("$Pc");
                        break;
                    case XDataType.STRING_TYPE_CODE /*38*/:
                        mangled.append("$Am");
                        break;
                    case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
                        mangled.append("$Sq");
                        break;
                    case XDataType.TOKEN_TYPE_CODE /*40*/:
                        mangled.append("$LP");
                        break;
                    case XDataType.LANGUAGE_TYPE_CODE /*41*/:
                        mangled.append("$RP");
                        break;
                    case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                        mangled.append("$St");
                        break;
                    case XDataType.NAME_TYPE_CODE /*43*/:
                        mangled.append("$Pl");
                        break;
                    case XDataType.NCNAME_TYPE_CODE /*44*/:
                        mangled.append("$Cm");
                        break;
                    case XDataType.ID_TYPE_CODE /*45*/:
                        if (!reversible) {
                            char next;
                            if (i + PROLOG_PARSING < len) {
                                next = name.charAt(i + PROLOG_PARSING);
                            } else {
                                next = '\u0000';
                            }
                            if (next != '>') {
                                if (!Character.isLowerCase(next)) {
                                    mangled.append("$Mn");
                                    break;
                                }
                            }
                            mangled.append("$To$");
                            i += PROLOG_PARSING;
                            break;
                        }
                        mangled.append("$Mn");
                        break;
                        break;
                    case XDataType.IDREF_TYPE_CODE /*46*/:
                        mangled.append("$Dt");
                        break;
                    case XDataType.ENTITY_TYPE_CODE /*47*/:
                        mangled.append("$Sl");
                        break;
                    case ':':
                        mangled.append("$Cl");
                        break;
                    case ';':
                        mangled.append("$SC");
                        break;
                    case '<':
                        mangled.append("$Ls");
                        break;
                    case '=':
                        mangled.append("$Eq");
                        break;
                    case '>':
                        mangled.append("$Gr");
                        break;
                    case '?':
                        char first;
                        if (mangled.length() > 0) {
                            first = mangled.charAt(MODULE_STATIC_DEFAULT);
                        } else {
                            first = '\u0000';
                        }
                        if (reversible || i + PROLOG_PARSING != len || !Character.isLowerCase(first)) {
                            mangled.append("$Qu");
                            break;
                        }
                        mangled.setCharAt(MODULE_STATIC_DEFAULT, Character.toTitleCase(first));
                        mangled.insert(MODULE_STATIC_DEFAULT, "is");
                        break;
                    case SetExp.HAS_VALUE /*64*/:
                        mangled.append("$At");
                        break;
                    case '[':
                        mangled.append("$LB");
                        break;
                    case ']':
                        mangled.append("$RB");
                        break;
                    case '^':
                        mangled.append("$Up");
                        break;
                    case srfi1.$Pcprovide$Pcsrfi$Mn1 /*123*/:
                        mangled.append("$LC");
                        break;
                    case '|':
                        mangled.append("$VB");
                        break;
                    case '}':
                        mangled.append("$RC");
                        break;
                    case '~':
                        mangled.append("$Tl");
                        break;
                    default:
                        mangled.append('$');
                        mangled.append(Character.forDigit((ch >> COMPILED) & 15, 16));
                        mangled.append(Character.forDigit((ch >> WALKED) & 15, 16));
                        mangled.append(Character.forDigit((ch >> CALL_WITH_CONTINUATIONS) & 15, 16));
                        mangled.append(Character.forDigit(ch & 15, 16));
                        break;
                }
                if (!reversible) {
                    upcaseNext = true;
                }
            }
            i += PROLOG_PARSING;
        }
        String mname = mangled.toString();
        if (mname.equals(name)) {
            return name;
        }
        return mname;
    }

    public static char demangle2(char char1, char char2) {
        switch ((char1 << 16) | char2) {
            case 4259949:
                return '&';
            case 4259956:
                return '@';
            case 4391020:
                return ':';
            case 4391021:
                return ',';
            case 4456561:
                return '\"';
            case 4456564:
                return '.';
            case 4522097:
                return '=';
            case 4522104:
                return '!';
            case 4653170:
                return '>';
            case 4980802:
                return '[';
            case 4980803:
                return '{';
            case 4980816:
                return '(';
            case 4980851:
                return '<';
            case 5046371:
            case 5242979:
                return '%';
            case 5046382:
                return '-';
            case 5111917:
                return '#';
            case 5242988:
                return '+';
            case 5308533:
                return '?';
            case 5374018:
                return ']';
            case 5374019:
                return '}';
            case 5374032:
                return ')';
            case 5439555:
                return ';';
            case 5439596:
                return '/';
            case 5439601:
                return '\\';
            case 5439604:
                return '*';
            case 5505132:
                return '~';
            case 5570672:
                return '^';
            case 5636162:
                return '|';
            default:
                return LispReader.TOKEN_ESCAPE_CHAR;
        }
    }

    public static String demangleName(String name) {
        return demangleName(name, false);
    }

    public static String demangleName(String name, boolean reversible) {
        StringBuffer sbuf = new StringBuffer();
        int len = name.length();
        boolean mangled = false;
        boolean predicate = false;
        boolean downCaseNext = false;
        int i = MODULE_STATIC_DEFAULT;
        while (i < len) {
            char d;
            char ch = name.charAt(i);
            if (downCaseNext && !reversible) {
                ch = Character.toLowerCase(ch);
                downCaseNext = false;
            }
            if (!reversible && ch == 'i' && i == 0 && len > PROLOG_PARSED && name.charAt(i + PROLOG_PARSING) == 's') {
                d = name.charAt(i + PROLOG_PARSED);
                if (!Character.isLowerCase(d)) {
                    mangled = true;
                    predicate = true;
                    i += PROLOG_PARSING;
                    if (Character.isUpperCase(d) || Character.isTitleCase(d)) {
                        sbuf.append(Character.toLowerCase(d));
                        i += PROLOG_PARSING;
                        i += PROLOG_PARSING;
                    } else {
                        i += PROLOG_PARSING;
                    }
                }
            }
            if (ch == '$' && i + PROLOG_PARSED < len) {
                char c1 = name.charAt(i + PROLOG_PARSING);
                char c2 = name.charAt(i + PROLOG_PARSED);
                d = demangle2(c1, c2);
                if (d != LispReader.TOKEN_ESCAPE_CHAR) {
                    sbuf.append(d);
                    i += PROLOG_PARSED;
                    mangled = true;
                    downCaseNext = true;
                } else if (c1 == 'T' && c2 == 'o' && i + CALL_WITH_TAILCALLS < len && name.charAt(i + CALL_WITH_TAILCALLS) == '$') {
                    sbuf.append("->");
                    i += CALL_WITH_TAILCALLS;
                    mangled = true;
                    downCaseNext = true;
                }
                i += PROLOG_PARSING;
            } else if (!reversible && i > PROLOG_PARSING && ((Character.isUpperCase(ch) || Character.isTitleCase(ch)) && Character.isLowerCase(name.charAt(i + MODULE_NONSTATIC)))) {
                sbuf.append('-');
                mangled = true;
                ch = Character.toLowerCase(ch);
            }
            sbuf.append(ch);
            i += PROLOG_PARSING;
        }
        if (predicate) {
            sbuf.append('?');
        }
        return mangled ? sbuf.toString() : name;
    }

    public String generateClassName(String hint) {
        hint = mangleName(hint, true);
        if (this.mainClass != null) {
            hint = this.mainClass.getName() + '$' + hint;
        } else if (this.classPrefix != null) {
            hint = this.classPrefix + hint;
        }
        if (findNamedClass(hint) == null) {
            return hint;
        }
        int i = MODULE_STATIC_DEFAULT;
        while (true) {
            String new_hint = hint + i;
            if (findNamedClass(new_hint) == null) {
                return new_hint;
            }
            i += PROLOG_PARSING;
        }
    }

    public Compilation(Language language, SourceMessages messages, NameLookup lexical) {
        this.mustCompile = ModuleExp.alwaysCompile;
        this.currentOptions = new Options(options);
        this.generateMain = generateMainDefault;
        this.classPrefix = classPrefixDefault;
        this.language = language;
        this.messages = messages;
        this.lexical = lexical;
    }

    public void walkModule(ModuleExp mexp) {
        if (debugPrintExpr) {
            OutPort dout = OutPort.errDefault();
            dout.println("[Module:" + mexp.getName());
            mexp.print(dout);
            dout.println(']');
            dout.flush();
        }
        InlineCalls.inlineCalls(mexp, this);
        PushApply.pushApply(mexp);
        ChainLambdas.chainLambdas(mexp, this);
        FindTailCalls.findTailCalls(mexp, this);
    }

    public void outputClass(String directory) throws IOException {
        char dirSep = File.separatorChar;
        for (int iClass = MODULE_STATIC_DEFAULT; iClass < this.numClasses; iClass += PROLOG_PARSING) {
            ClassType clas = this.classes[iClass];
            String out_name = directory + clas.getName().replace('.', dirSep) + ".class";
            String parent = new File(out_name).getParent();
            if (parent != null) {
                new File(parent).mkdirs();
            }
            clas.writeToFile(out_name);
        }
        this.minfo.cleanupAfterCompilation();
    }

    public void cleanupAfterCompilation() {
        for (int iClass = MODULE_STATIC_DEFAULT; iClass < this.numClasses; iClass += PROLOG_PARSING) {
            this.classes[iClass].cleanupAfterCompilation();
        }
        this.classes = null;
        this.minfo.comp = null;
        if (this.minfo.exp != null) {
            this.minfo.exp.body = null;
        }
        this.mainLambda.body = null;
        this.mainLambda = null;
        if (!this.immediate) {
            this.litTable = null;
        }
    }

    public void compileToArchive(ModuleExp mexp, String fname) throws IOException {
        boolean makeJar;
        ZipOutputStream zout;
        if (fname.endsWith(".zip")) {
            makeJar = false;
        } else if (fname.endsWith(".jar")) {
            makeJar = true;
        } else {
            fname = fname + ".zip";
            makeJar = false;
        }
        process(COMPILED);
        File zar_file = new File(fname);
        if (zar_file.exists()) {
            zar_file.delete();
        }
        if (makeJar) {
            zout = new JarOutputStream(new FileOutputStream(zar_file));
        } else {
            zout = new ZipOutputStream(new FileOutputStream(zar_file));
        }
        byte[][] classBytes = new byte[this.numClasses][];
        CRC32 zcrc = new CRC32();
        for (int iClass = MODULE_STATIC_DEFAULT; iClass < this.numClasses; iClass += PROLOG_PARSING) {
            ClassType clas = this.classes[iClass];
            classBytes[iClass] = clas.writeToArray();
            ZipEntry zent = new ZipEntry(clas.getName().replace('.', '/') + ".class");
            zent.setSize((long) classBytes[iClass].length);
            zcrc.reset();
            zcrc.update(classBytes[iClass], MODULE_STATIC_DEFAULT, classBytes[iClass].length);
            zent.setCrc(zcrc.getValue());
            zout.putNextEntry(zent);
            zout.write(classBytes[iClass]);
        }
        zout.close();
    }

    private void registerClass(ClassType new_class) {
        if (this.classes == null) {
            this.classes = new ClassType[20];
        } else if (this.numClasses >= this.classes.length) {
            ClassType[] new_classes = new ClassType[(this.classes.length * PROLOG_PARSED)];
            System.arraycopy(this.classes, MODULE_STATIC_DEFAULT, new_classes, MODULE_STATIC_DEFAULT, this.numClasses);
            this.classes = new_classes;
        }
        new_class.addModifiers(new_class.isInterface() ? PROLOG_PARSING : 33);
        if (new_class == this.mainClass && this.numClasses > 0) {
            new_class = this.classes[MODULE_STATIC_DEFAULT];
            this.classes[MODULE_STATIC_DEFAULT] = this.mainClass;
        }
        ClassType[] classTypeArr = this.classes;
        int i = this.numClasses;
        this.numClasses = i + PROLOG_PARSING;
        classTypeArr[i] = new_class;
    }

    public void addClass(ClassType new_class) {
        if (this.mainLambda.filename != null) {
            if (emitSourceDebugExtAttr) {
                new_class.setStratum(getLanguage().getName());
            }
            new_class.setSourceFile(this.mainLambda.filename);
        }
        registerClass(new_class);
        new_class.setClassfileVersion(defaultClassFileVersion);
    }

    public boolean makeRunnable() {
        return (generatingServlet() || generatingApplet() || getModule().staticInitRun()) ? false : true;
    }

    public void addMainClass(ModuleExp module) {
        this.mainClass = module.classFor(this);
        ClassType type = this.mainClass;
        ClassType[] interfaces = module.getInterfaces();
        if (interfaces != null) {
            type.setInterfaces(interfaces);
        }
        ClassType sup = module.getSuperType();
        if (sup == null) {
            if (generatingApplet()) {
                sup = typeApplet;
            } else if (generatingServlet()) {
                sup = typeServlet;
            } else {
                sup = getModuleType();
            }
        }
        if (makeRunnable()) {
            type.addInterface(typeRunnable);
        }
        type.setSuper(sup);
        module.type = type;
        addClass(type);
        getConstructor(this.mainClass, module);
    }

    public final Method getConstructor(LambdaExp lexp) {
        return getConstructor(lexp.getHeapFrameType(), lexp);
    }

    public static final Method getConstructor(ClassType clas, LambdaExp lexp) {
        Method meth = clas.getDeclaredMethod("<init>", (int) MODULE_STATIC_DEFAULT);
        if (meth != null) {
            return meth;
        }
        Type[] args;
        if (!(lexp instanceof ClassExp) || lexp.staticLinkField == null) {
            args = apply0args;
        } else {
            args = new Type[PROLOG_PARSING];
            args[MODULE_STATIC_DEFAULT] = lexp.staticLinkField.getType();
        }
        return clas.addMethod("<init>", (int) PROLOG_PARSING, args, Type.voidType);
    }

    public final void generateConstructor(LambdaExp lexp) {
        generateConstructor(lexp.getHeapFrameType(), lexp);
    }

    public final void generateConstructor(ClassType clas, LambdaExp lexp) {
        Method save_method = this.method;
        Variable callContextSave = this.callContextVar;
        this.callContextVar = null;
        ClassType save_class = this.curClass;
        this.curClass = clas;
        Method constructor_method = getConstructor(clas, lexp);
        clas.constructor = constructor_method;
        this.method = constructor_method;
        CodeAttr code = constructor_method.startCode();
        if ((lexp instanceof ClassExp) && lexp.staticLinkField != null) {
            code.emitPushThis();
            code.emitLoad(code.getCurrentScope().getVariable(PROLOG_PARSING));
            code.emitPutField(lexp.staticLinkField);
        }
        ClassExp.invokeDefaultSuperConstructor(clas.getSuperclass(), this, lexp);
        if (!(this.curClass != this.mainClass || this.minfo == null || this.minfo.sourcePath == null)) {
            code.emitPushThis();
            code.emitInvokeStatic(ClassType.make("gnu.expr.ModuleInfo").getDeclaredMethod("register", (int) PROLOG_PARSING));
        }
        if (!(lexp == null || lexp.initChain == null)) {
            LambdaExp save = this.curLambda;
            this.curLambda = new LambdaExp();
            this.curLambda.closureEnv = code.getArg(MODULE_STATIC_DEFAULT);
            this.curLambda.outer = save;
            while (true) {
                Initializer init = lexp.initChain;
                if (init == null) {
                    break;
                }
                lexp.initChain = null;
                dumpInitializers(init);
            }
            this.curLambda = save;
        }
        if (lexp instanceof ClassExp) {
            callInitMethods(((ClassExp) lexp).getCompiledClassType(this), new Vector(COMPILE_SETUP));
        }
        code.emitReturn();
        this.method = save_method;
        this.curClass = save_class;
        this.callContextVar = callContextSave;
    }

    void callInitMethods(ClassType clas, Vector<ClassType> seen) {
        if (clas != null) {
            String name = clas.getName();
            if (!"java.lang.Object".equals(name)) {
                int i = seen.size();
                do {
                    i += MODULE_NONSTATIC;
                    if (i < 0) {
                        seen.addElement(clas);
                        ClassType[] interfaces = clas.getInterfaces();
                        if (interfaces != null) {
                            int n = interfaces.length;
                            for (i = MODULE_STATIC_DEFAULT; i < n; i += PROLOG_PARSING) {
                                callInitMethods(interfaces[i], seen);
                            }
                        }
                        int clEnvArgs = PROLOG_PARSING;
                        if (!clas.isInterface()) {
                            clEnvArgs = MODULE_STATIC_DEFAULT;
                        } else if (clas instanceof PairClassType) {
                            clas = ((PairClassType) clas).instanceType;
                        } else {
                            try {
                                clas = (ClassType) Type.make(Class.forName(clas.getName() + "$class"));
                            } catch (Throwable th) {
                                return;
                            }
                        }
                        Method meth = clas.getDeclaredMethod("$finit$", clEnvArgs);
                        if (meth != null) {
                            CodeAttr code = getCode();
                            code.emitPushThis();
                            code.emitInvoke(meth);
                            return;
                        }
                        return;
                    }
                } while (((ClassType) seen.elementAt(i)).getName() != name);
            }
        }
    }

    public void generateMatchMethods(LambdaExp lexp) {
        int numApplyMethods = lexp.applyMethods == null ? MODULE_STATIC_DEFAULT : lexp.applyMethods.size();
        if (numApplyMethods != 0) {
            Method save_method = this.method;
            ClassType save_class = this.curClass;
            ClassType procType = typeModuleMethod;
            this.curClass = lexp.getHeapFrameType();
            if (!this.curClass.getSuperclass().isSubtype(typeModuleBody)) {
                this.curClass = this.moduleClass;
            }
            CodeAttr code = null;
            int i = MODULE_STATIC_DEFAULT;
            while (i <= 5) {
                int k;
                boolean needThisMatch = false;
                SwitchState aswitch = null;
                String mname = null;
                Type[] matchArgs = null;
                int j = numApplyMethods;
                while (true) {
                    j += MODULE_NONSTATIC;
                    if (j < 0) {
                        break;
                    }
                    boolean varArgs;
                    int methodIndex;
                    int line;
                    Variable ctxVar;
                    Declaration var;
                    Type ptype;
                    Label label;
                    Label falseLabel;
                    ConditionalTarget ctarget;
                    LambdaExp source = (LambdaExp) lexp.applyMethods.elementAt(j);
                    int numMethods = source.primMethods.length;
                    if (source.max_args >= 0) {
                        if (source.max_args < source.min_args + numMethods) {
                            varArgs = false;
                            if (i >= 5) {
                                methodIndex = i - source.min_args;
                                if (methodIndex >= 0 && methodIndex < numMethods) {
                                    if (methodIndex == numMethods + MODULE_NONSTATIC || !varArgs) {
                                    }
                                }
                            } else {
                                methodIndex = 5 - source.min_args;
                                if (methodIndex > 0 || numMethods > methodIndex || varArgs) {
                                    methodIndex = numMethods + MODULE_NONSTATIC;
                                }
                            }
                            if (!needThisMatch) {
                                if (i >= 5) {
                                    mname = "match" + i;
                                    matchArgs = new Type[(i + PROLOG_PARSED)];
                                    for (k = i; k >= 0; k += MODULE_NONSTATIC) {
                                        matchArgs[k + PROLOG_PARSING] = typeObject;
                                    }
                                    matchArgs[i + PROLOG_PARSING] = typeCallContext;
                                } else {
                                    mname = "matchN";
                                    matchArgs = new Type[CALL_WITH_TAILCALLS];
                                    matchArgs[PROLOG_PARSING] = objArrayType;
                                    matchArgs[PROLOG_PARSED] = typeCallContext;
                                }
                                matchArgs[MODULE_STATIC_DEFAULT] = procType;
                                this.method = this.curClass.addMethod(mname, matchArgs, Type.intType, PROLOG_PARSING);
                                code = this.method.startCode();
                                code.emitLoad(code.getArg(PROLOG_PARSING));
                                code.emitGetField(procType.getField("selector"));
                                aswitch = code.startSwitch();
                                needThisMatch = true;
                            }
                            aswitch.addCase(source.getSelectorValue(this), code);
                            line = source.getLineNumber();
                            if (line > 0) {
                                code.putLineNumber(source.getFileName(), line);
                            }
                            ctxVar = code.getArg(i != 5 ? CALL_WITH_TAILCALLS : i + PROLOG_PARSED);
                            if (i >= 5) {
                                var = source.firstDecl();
                                for (k = PROLOG_PARSING; k <= i; k += PROLOG_PARSING) {
                                    code.emitLoad(ctxVar);
                                    code.emitLoad(code.getArg(k + PROLOG_PARSING));
                                    ptype = var.getType();
                                    if (ptype == Type.objectType) {
                                        if (ptype instanceof TypeValue) {
                                            label = new Label(code);
                                            falseLabel = new Label(code);
                                            ctarget = new ConditionalTarget(label, falseLabel, getLanguage());
                                            code.emitDup();
                                            ((TypeValue) ptype).emitIsInstance(null, this, ctarget);
                                            falseLabel.define(code);
                                            code.emitPushInt(MethodProc.NO_MATCH_BAD_TYPE | k);
                                            code.emitReturn();
                                            label.define(code);
                                        } else if (!(!(ptype instanceof ClassType) || ptype == Type.objectType || ptype == Type.toStringType)) {
                                            code.emitDup();
                                            ptype.emitIsInstance(code);
                                            code.emitIfIntEqZero();
                                            code.emitPushInt(MethodProc.NO_MATCH_BAD_TYPE | k);
                                            code.emitReturn();
                                            code.emitFi();
                                        }
                                    }
                                    code.emitPutField(typeCallContext.getField("value" + k));
                                    var = var.nextDecl();
                                }
                            } else {
                                code.emitLoad(ctxVar);
                                code.emitLoad(code.getArg(PROLOG_PARSED));
                                code.emitPutField(typeCallContext.getField("values"));
                            }
                            code.emitLoad(ctxVar);
                            if (defaultCallConvention >= PROLOG_PARSED) {
                                code.emitLoad(code.getArg(PROLOG_PARSING));
                            } else {
                                code.emitLoad(code.getArg(MODULE_STATIC_DEFAULT));
                            }
                            code.emitPutField(procCallContextField);
                            code.emitLoad(ctxVar);
                            if (defaultCallConvention < PROLOG_PARSED) {
                                code.emitPushInt(source.getSelectorValue(this) + methodIndex);
                            } else {
                                code.emitPushInt(i);
                            }
                            code.emitPutField(pcCallContextField);
                            code.emitPushInt(MODULE_STATIC_DEFAULT);
                            code.emitReturn();
                        }
                    }
                    varArgs = true;
                    if (i >= 5) {
                        methodIndex = 5 - source.min_args;
                        if (methodIndex > 0) {
                        }
                        methodIndex = numMethods + MODULE_NONSTATIC;
                    } else {
                        methodIndex = i - source.min_args;
                        if (methodIndex == numMethods + MODULE_NONSTATIC) {
                        }
                    }
                    if (needThisMatch) {
                        if (i >= 5) {
                            mname = "matchN";
                            matchArgs = new Type[CALL_WITH_TAILCALLS];
                            matchArgs[PROLOG_PARSING] = objArrayType;
                            matchArgs[PROLOG_PARSED] = typeCallContext;
                        } else {
                            mname = "match" + i;
                            matchArgs = new Type[(i + PROLOG_PARSED)];
                            for (k = i; k >= 0; k += MODULE_NONSTATIC) {
                                matchArgs[k + PROLOG_PARSING] = typeObject;
                            }
                            matchArgs[i + PROLOG_PARSING] = typeCallContext;
                        }
                        matchArgs[MODULE_STATIC_DEFAULT] = procType;
                        this.method = this.curClass.addMethod(mname, matchArgs, Type.intType, PROLOG_PARSING);
                        code = this.method.startCode();
                        code.emitLoad(code.getArg(PROLOG_PARSING));
                        code.emitGetField(procType.getField("selector"));
                        aswitch = code.startSwitch();
                        needThisMatch = true;
                    }
                    aswitch.addCase(source.getSelectorValue(this), code);
                    line = source.getLineNumber();
                    if (line > 0) {
                        code.putLineNumber(source.getFileName(), line);
                    }
                    if (i != 5) {
                    }
                    ctxVar = code.getArg(i != 5 ? CALL_WITH_TAILCALLS : i + PROLOG_PARSED);
                    if (i >= 5) {
                        code.emitLoad(ctxVar);
                        code.emitLoad(code.getArg(PROLOG_PARSED));
                        code.emitPutField(typeCallContext.getField("values"));
                    } else {
                        var = source.firstDecl();
                        for (k = PROLOG_PARSING; k <= i; k += PROLOG_PARSING) {
                            code.emitLoad(ctxVar);
                            code.emitLoad(code.getArg(k + PROLOG_PARSING));
                            ptype = var.getType();
                            if (ptype == Type.objectType) {
                                if (ptype instanceof TypeValue) {
                                    label = new Label(code);
                                    falseLabel = new Label(code);
                                    ctarget = new ConditionalTarget(label, falseLabel, getLanguage());
                                    code.emitDup();
                                    ((TypeValue) ptype).emitIsInstance(null, this, ctarget);
                                    falseLabel.define(code);
                                    code.emitPushInt(MethodProc.NO_MATCH_BAD_TYPE | k);
                                    code.emitReturn();
                                    label.define(code);
                                } else {
                                    code.emitDup();
                                    ptype.emitIsInstance(code);
                                    code.emitIfIntEqZero();
                                    code.emitPushInt(MethodProc.NO_MATCH_BAD_TYPE | k);
                                    code.emitReturn();
                                    code.emitFi();
                                }
                            }
                            code.emitPutField(typeCallContext.getField("value" + k));
                            var = var.nextDecl();
                        }
                    }
                    code.emitLoad(ctxVar);
                    if (defaultCallConvention >= PROLOG_PARSED) {
                        code.emitLoad(code.getArg(MODULE_STATIC_DEFAULT));
                    } else {
                        code.emitLoad(code.getArg(PROLOG_PARSING));
                    }
                    code.emitPutField(procCallContextField);
                    code.emitLoad(ctxVar);
                    if (defaultCallConvention < PROLOG_PARSED) {
                        code.emitPushInt(i);
                    } else {
                        code.emitPushInt(source.getSelectorValue(this) + methodIndex);
                    }
                    code.emitPutField(pcCallContextField);
                    code.emitPushInt(MODULE_STATIC_DEFAULT);
                    code.emitReturn();
                }
                if (needThisMatch) {
                    aswitch.addDefault(code);
                    int nargs = (i > CALL_WITH_CONTINUATIONS ? PROLOG_PARSED : i + PROLOG_PARSING) + PROLOG_PARSING;
                    for (k = MODULE_STATIC_DEFAULT; k <= nargs; k += PROLOG_PARSING) {
                        code.emitLoad(code.getArg(k));
                    }
                    code.emitInvokeSpecial(typeModuleBody.getDeclaredMethod(mname, matchArgs.length));
                    code.emitReturn();
                    aswitch.finish(code);
                }
                i += PROLOG_PARSING;
            }
            this.method = save_method;
            this.curClass = save_class;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void generateApplyMethodsWithContext(gnu.expr.LambdaExp r38) {
        /*
        r37 = this;
        r0 = r38;
        r3 = r0.applyMethods;
        if (r3 != 0) goto L_0x000b;
    L_0x0006:
        r20 = 0;
    L_0x0008:
        if (r20 != 0) goto L_0x0014;
    L_0x000a:
        return;
    L_0x000b:
        r0 = r38;
        r3 = r0.applyMethods;
        r20 = r3.size();
        goto L_0x0008;
    L_0x0014:
        r0 = r37;
        r0 = r0.curClass;
        r30 = r0;
        r3 = r38.getHeapFrameType();
        r0 = r37;
        r0.curClass = r3;
        r0 = r37;
        r3 = r0.curClass;
        r3 = r3.getSuperclass();
        r34 = typeModuleWithContext;
        r0 = r34;
        r3 = r3.isSubtype(r0);
        if (r3 != 0) goto L_0x003c;
    L_0x0034:
        r0 = r37;
        r3 = r0.moduleClass;
        r0 = r37;
        r0.curClass = r3;
    L_0x003c:
        r26 = typeModuleMethod;
        r0 = r37;
        r0 = r0.method;
        r31 = r0;
        r11 = 0;
        r3 = 1;
        r9 = new gnu.bytecode.Type[r3];
        r3 = 0;
        r34 = typeCallContext;
        r9[r3] = r34;
        r0 = r37;
        r3 = r0.curClass;
        r34 = "apply";
        r35 = gnu.bytecode.Type.voidType;
        r36 = 1;
        r0 = r34;
        r1 = r35;
        r2 = r36;
        r3 = r3.addMethod(r0, r9, r1, r2);
        r0 = r37;
        r0.method = r3;
        r0 = r37;
        r3 = r0.method;
        r11 = r3.startCode();
        r3 = 1;
        r8 = r11.getArg(r3);
        r11.emitLoad(r8);
        r3 = pcCallContextField;
        r11.emitGetField(r3);
        r10 = r11.startSwitch();
        r15 = 0;
    L_0x007f:
        r0 = r20;
        if (r15 >= r0) goto L_0x02a6;
    L_0x0083:
        r0 = r38;
        r3 = r0.applyMethods;
        r4 = r3.elementAt(r15);
        r4 = (gnu.expr.LambdaExp) r4;
        r0 = r4.primMethods;
        r25 = r0;
        r0 = r25;
        r0 = r0.length;
        r21 = r0;
        r14 = 0;
    L_0x0097:
        r0 = r21;
        if (r14 >= r0) goto L_0x02a2;
    L_0x009b:
        r3 = r21 + -1;
        if (r14 != r3) goto L_0x01ed;
    L_0x009f:
        r3 = r4.max_args;
        if (r3 < 0) goto L_0x00af;
    L_0x00a3:
        r3 = r4.max_args;
        r0 = r4.min_args;
        r34 = r0;
        r34 = r34 + r21;
        r0 = r34;
        if (r3 < r0) goto L_0x01ed;
    L_0x00af:
        r33 = 1;
    L_0x00b1:
        r18 = r14;
        r0 = r37;
        r3 = r4.getSelectorValue(r0);
        r3 = r3 + r14;
        r10.addCase(r3, r11);
        r0 = r37;
        r3 = r0.messages;
        r28 = r3.swapSourceLocator(r4);
        r17 = r4.getLineNumber();
        if (r17 <= 0) goto L_0x00d4;
    L_0x00cb:
        r3 = r4.getFileName();
        r0 = r17;
        r11.putLineNumber(r3, r0);
    L_0x00d4:
        r24 = r25[r18];
        r23 = r24.getParameterTypes();
        r3 = r4.min_args;
        r5 = r3 + r18;
        r6 = 0;
        r22 = 0;
        r3 = 4;
        if (r14 <= r3) goto L_0x0114;
    L_0x00e4:
        r3 = 1;
        r0 = r21;
        if (r0 <= r3) goto L_0x0114;
    L_0x00e9:
        r3 = gnu.bytecode.Type.intType;
        r6 = r11.addLocal(r3);
        r11.emitLoad(r8);
        r3 = typeCallContext;
        r34 = "getArgCount";
        r35 = 0;
        r0 = r34;
        r1 = r35;
        r3 = r3.getDeclaredMethod(r0, r1);
        r11.emitInvoke(r3);
        r3 = r4.min_args;
        if (r3 == 0) goto L_0x0111;
    L_0x0107:
        r3 = r4.min_args;
        r11.emitPushInt(r3);
        r3 = gnu.bytecode.Type.intType;
        r11.emitSub(r3);
    L_0x0111:
        r11.emitStore(r6);
    L_0x0114:
        r3 = r24.getStaticFlag();
        if (r3 == 0) goto L_0x01f1;
    L_0x011a:
        r19 = 0;
    L_0x011c:
        if (r33 == 0) goto L_0x01f5;
    L_0x011e:
        r3 = 2;
    L_0x011f:
        r3 = r3 + r5;
        r0 = r23;
        r0 = r0.length;
        r34 = r0;
        r0 = r34;
        if (r3 >= r0) goto L_0x01f8;
    L_0x0129:
        r13 = 1;
    L_0x012a:
        r3 = r19 + r13;
        if (r3 <= 0) goto L_0x0154;
    L_0x012e:
        r11.emitPushThis();
        r0 = r37;
        r3 = r0.curClass;
        r0 = r37;
        r0 = r0.moduleClass;
        r34 = r0;
        r0 = r34;
        if (r3 != r0) goto L_0x0154;
    L_0x013f:
        r0 = r37;
        r3 = r0.mainClass;
        r0 = r37;
        r0 = r0.moduleClass;
        r34 = r0;
        r0 = r34;
        if (r3 == r0) goto L_0x0154;
    L_0x014d:
        r0 = r37;
        r3 = r0.moduleInstanceMainField;
        r11.emitGetField(r3);
    L_0x0154:
        r32 = r4.firstDecl();
        if (r32 == 0) goto L_0x0164;
    L_0x015a:
        r3 = r32.isThisParameter();
        if (r3 == 0) goto L_0x0164;
    L_0x0160:
        r32 = r32.nextDecl();
    L_0x0164:
        r16 = 0;
    L_0x0166:
        r0 = r16;
        if (r0 >= r5) goto L_0x0213;
    L_0x016a:
        if (r6 == 0) goto L_0x018d;
    L_0x016c:
        r3 = r4.min_args;
        r0 = r16;
        if (r0 < r3) goto L_0x018d;
    L_0x0172:
        r11.emitLoad(r6);
        r11.emitIfIntLEqZero();
        r11.emitLoad(r8);
        r3 = r4.min_args;
        r3 = r16 - r3;
        r3 = r25[r3];
        r11.emitInvoke(r3);
        r11.emitElse();
        r22 = r22 + 1;
        r3 = -1;
        r11.emitInc(r6, r3);
    L_0x018d:
        r11.emitLoad(r8);
        r3 = 4;
        r0 = r16;
        if (r0 > r3) goto L_0x01fb;
    L_0x0195:
        if (r33 != 0) goto L_0x01fb;
    L_0x0197:
        r3 = r4.max_args;
        r34 = 4;
        r0 = r34;
        if (r3 > r0) goto L_0x01fb;
    L_0x019f:
        r3 = typeCallContext;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "value";
        r34 = r34.append(r35);
        r35 = r16 + 1;
        r34 = r34.append(r35);
        r34 = r34.toString();
        r0 = r34;
        r3 = r3.getDeclaredField(r0);
        r11.emitGetField(r3);
    L_0x01bf:
        r27 = r32.getType();
        r3 = gnu.bytecode.Type.objectType;
        r0 = r27;
        if (r0 == r3) goto L_0x01e5;
    L_0x01c9:
        r0 = r37;
        r3 = r0.messages;
        r0 = r32;
        r29 = r3.swapSourceLocator(r0);
        r3 = r16 + 1;
        r0 = r37;
        r1 = r27;
        gnu.expr.CheckedTarget.emitCheckedCoerce(r0, r4, r3, r1);
        r0 = r37;
        r3 = r0.messages;
        r0 = r29;
        r3.swapSourceLocator(r0);
    L_0x01e5:
        r32 = r32.nextDecl();
        r16 = r16 + 1;
        goto L_0x0166;
    L_0x01ed:
        r33 = 0;
        goto L_0x00b1;
    L_0x01f1:
        r19 = 1;
        goto L_0x011c;
    L_0x01f5:
        r3 = 1;
        goto L_0x011f;
    L_0x01f8:
        r13 = 0;
        goto L_0x012a;
    L_0x01fb:
        r3 = typeCallContext;
        r34 = "values";
        r0 = r34;
        r3 = r3.getDeclaredField(r0);
        r11.emitGetField(r3);
        r0 = r16;
        r11.emitPushInt(r0);
        r3 = gnu.bytecode.Type.objectType;
        r11.emitArrayLoad(r3);
        goto L_0x01bf;
    L_0x0213:
        if (r33 == 0) goto L_0x0222;
    L_0x0215:
        r3 = r13 + r5;
        r7 = r23[r3];
        r3 = r7 instanceof gnu.bytecode.ArrayType;
        if (r3 == 0) goto L_0x0232;
    L_0x021d:
        r3 = r37;
        r3.varArgsToArray(r4, r5, r6, r7, r8);
    L_0x0222:
        r11.emitLoad(r8);
        r0 = r24;
        r11.emitInvoke(r0);
    L_0x022a:
        r22 = r22 + -1;
        if (r22 < 0) goto L_0x027d;
    L_0x022e:
        r11.emitFi();
        goto L_0x022a;
    L_0x0232:
        r3 = "gnu.lists.LList";
        r34 = r7.getName();
        r0 = r34;
        r3 = r3.equals(r0);
        if (r3 == 0) goto L_0x0258;
    L_0x0240:
        r11.emitLoad(r8);
        r11.emitPushInt(r5);
        r3 = typeCallContext;
        r34 = "getRestArgsList";
        r35 = 1;
        r0 = r34;
        r1 = r35;
        r3 = r3.getDeclaredMethod(r0, r1);
        r11.emitInvokeVirtual(r3);
        goto L_0x0222;
    L_0x0258:
        r3 = typeCallContext;
        if (r7 != r3) goto L_0x0260;
    L_0x025c:
        r11.emitLoad(r8);
        goto L_0x0222;
    L_0x0260:
        r3 = new java.lang.RuntimeException;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "unsupported #!rest type:";
        r34 = r34.append(r35);
        r0 = r34;
        r34 = r0.append(r7);
        r34 = r34.toString();
        r0 = r34;
        r3.<init>(r0);
        throw r3;
    L_0x027d:
        r3 = defaultCallConvention;
        r34 = 2;
        r0 = r34;
        if (r3 >= r0) goto L_0x0292;
    L_0x0285:
        r3 = gnu.expr.Target.pushObject;
        r34 = r4.getReturnType();
        r0 = r37;
        r1 = r34;
        r3.compileFromStack(r0, r1);
    L_0x0292:
        r0 = r37;
        r3 = r0.messages;
        r0 = r28;
        r3.swapSourceLocator(r0);
        r11.emitReturn();
        r14 = r14 + 1;
        goto L_0x0097;
    L_0x02a2:
        r15 = r15 + 1;
        goto L_0x007f;
    L_0x02a6:
        r10.addDefault(r11);
        r3 = typeModuleMethod;
        r34 = "applyError";
        r35 = 0;
        r0 = r34;
        r1 = r35;
        r12 = r3.getDeclaredMethod(r0, r1);
        r11.emitInvokeStatic(r12);
        r11.emitReturn();
        r10.finish(r11);
        r0 = r31;
        r1 = r37;
        r1.method = r0;
        r0 = r30;
        r1 = r37;
        r1.curClass = r0;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Compilation.generateApplyMethodsWithContext(gnu.expr.LambdaExp):void");
    }

    public void generateApplyMethodsWithoutContext(LambdaExp lexp) {
        int numApplyMethods = lexp.applyMethods == null ? MODULE_STATIC_DEFAULT : lexp.applyMethods.size();
        if (numApplyMethods != 0) {
            ClassType save_class = this.curClass;
            this.curClass = lexp.getHeapFrameType();
            ClassType procType = typeModuleMethod;
            if (!this.curClass.getSuperclass().isSubtype(typeModuleBody)) {
                this.curClass = this.moduleClass;
            }
            Method save_method = this.method;
            CodeAttr code = null;
            int i = defaultCallConvention >= PROLOG_PARSED ? 5 : MODULE_STATIC_DEFAULT;
            while (i < RESOLVED) {
                int k;
                boolean needThisApply = false;
                SwitchState aswitch = null;
                String mname = null;
                Type[] applyArgs = null;
                for (int j = MODULE_STATIC_DEFAULT; j < numApplyMethods; j += PROLOG_PARSING) {
                    int methodIndex;
                    LambdaExp source = (LambdaExp) lexp.applyMethods.elementAt(j);
                    Method[] primMethods = source.primMethods;
                    int numMethods = primMethods.length;
                    boolean varArgs = source.max_args < 0 || source.max_args >= source.min_args + numMethods;
                    boolean skipThisProc = false;
                    if (i < 5) {
                        methodIndex = i - source.min_args;
                        if (methodIndex < 0 || methodIndex >= numMethods || (methodIndex == numMethods + MODULE_NONSTATIC && varArgs)) {
                            skipThisProc = true;
                        }
                        numMethods = PROLOG_PARSING;
                        varArgs = false;
                    } else {
                        methodIndex = 5 - source.min_args;
                        if (methodIndex > 0 && numMethods <= methodIndex && !varArgs) {
                            skipThisProc = true;
                        }
                        methodIndex = numMethods + MODULE_NONSTATIC;
                    }
                    if (!skipThisProc) {
                        int explicitFrameArg;
                        if (!needThisApply) {
                            if (i < 5) {
                                mname = "apply" + i;
                                applyArgs = new Type[(i + PROLOG_PARSING)];
                                for (k = i; k > 0; k += MODULE_NONSTATIC) {
                                    applyArgs[k] = typeObject;
                                }
                            } else {
                                mname = "applyN";
                                applyArgs = new Type[PROLOG_PARSED];
                                applyArgs[PROLOG_PARSING] = objArrayType;
                            }
                            applyArgs[MODULE_STATIC_DEFAULT] = procType;
                            this.method = this.curClass.addMethod(mname, applyArgs, defaultCallConvention >= PROLOG_PARSED ? Type.voidType : Type.objectType, PROLOG_PARSING);
                            code = this.method.startCode();
                            code.emitLoad(code.getArg(PROLOG_PARSING));
                            code.emitGetField(procType.getField("selector"));
                            aswitch = code.startSwitch();
                            needThisApply = true;
                        }
                        aswitch.addCase(source.getSelectorValue(this), code);
                        SourceLocator saveLoc1 = this.messages.swapSourceLocator(source);
                        int line = source.getLineNumber();
                        if (line > 0) {
                            code.putLineNumber(source.getFileName(), line);
                        }
                        Method primMethod = primMethods[methodIndex];
                        Type[] primArgTypes = primMethod.getParameterTypes();
                        int singleArgs = source.min_args + methodIndex;
                        Variable counter = null;
                        int pendingIfEnds = MODULE_STATIC_DEFAULT;
                        if (i > CALL_WITH_CONTINUATIONS && numMethods > PROLOG_PARSING) {
                            counter = code.addLocal(Type.intType);
                            code.emitLoad(code.getArg(PROLOG_PARSED));
                            code.emitArrayLength();
                            if (source.min_args != 0) {
                                code.emitPushInt(source.min_args);
                                code.emitSub(Type.intType);
                            }
                            code.emitStore(counter);
                        }
                        int needsThis = primMethod.getStaticFlag() ? MODULE_STATIC_DEFAULT : PROLOG_PARSING;
                        if ((varArgs ? PROLOG_PARSING : MODULE_STATIC_DEFAULT) + singleArgs < primArgTypes.length) {
                            explicitFrameArg = PROLOG_PARSING;
                        } else {
                            explicitFrameArg = MODULE_STATIC_DEFAULT;
                        }
                        if (needsThis + explicitFrameArg > 0) {
                            code.emitPushThis();
                            if (this.curClass == this.moduleClass && this.mainClass != this.moduleClass) {
                                code.emitGetField(this.moduleInstanceMainField);
                            }
                        }
                        Declaration var = source.firstDecl();
                        if (var != null && var.isThisParameter()) {
                            var = var.nextDecl();
                        }
                        k = MODULE_STATIC_DEFAULT;
                        while (k < singleArgs) {
                            if (counter != null && k >= source.min_args) {
                                code.emitLoad(counter);
                                code.emitIfIntLEqZero();
                                code.emitInvoke(primMethods[k - source.min_args]);
                                code.emitElse();
                                pendingIfEnds += PROLOG_PARSING;
                                code.emitInc(counter, (short) -1);
                            }
                            Variable pvar = null;
                            if (i <= CALL_WITH_CONTINUATIONS) {
                                pvar = code.getArg(k + PROLOG_PARSED);
                                code.emitLoad(pvar);
                            } else {
                                code.emitLoad(code.getArg(PROLOG_PARSED));
                                code.emitPushInt(k);
                                code.emitArrayLoad(Type.objectType);
                            }
                            Type ptype = var.getType();
                            if (ptype != Type.objectType) {
                                SourceLocator saveLoc2 = this.messages.swapSourceLocator(var);
                                CheckedTarget.emitCheckedCoerce(this, source, k + PROLOG_PARSING, ptype, pvar);
                                this.messages.swapSourceLocator(saveLoc2);
                            }
                            var = var.nextDecl();
                            k += PROLOG_PARSING;
                        }
                        if (varArgs) {
                            Type lastArgType = primArgTypes[explicitFrameArg + singleArgs];
                            if (lastArgType instanceof ArrayType) {
                                varArgsToArray(source, singleArgs, counter, lastArgType, null);
                            } else if ("gnu.lists.LList".equals(lastArgType.getName())) {
                                code.emitLoad(code.getArg(PROLOG_PARSED));
                                code.emitPushInt(singleArgs);
                                code.emitInvokeStatic(makeListMethod);
                            } else if (lastArgType == typeCallContext) {
                                code.emitLoad(code.getArg(PROLOG_PARSED));
                            } else {
                                throw new RuntimeException("unsupported #!rest type:" + lastArgType);
                            }
                        }
                        code.emitInvoke(primMethod);
                        while (true) {
                            pendingIfEnds += MODULE_NONSTATIC;
                            if (pendingIfEnds < 0) {
                                break;
                            }
                            code.emitFi();
                        }
                        if (defaultCallConvention < PROLOG_PARSED) {
                            Target.pushObject.compileFromStack(this, source.getReturnType());
                        }
                        this.messages.swapSourceLocator(saveLoc1);
                        code.emitReturn();
                    }
                }
                if (needThisApply) {
                    aswitch.addDefault(code);
                    if (defaultCallConvention >= PROLOG_PARSED) {
                        code.emitInvokeStatic(typeModuleMethod.getDeclaredMethod("applyError", MODULE_STATIC_DEFAULT));
                    } else {
                        int nargs = (i > CALL_WITH_CONTINUATIONS ? PROLOG_PARSED : i + PROLOG_PARSING) + PROLOG_PARSING;
                        for (k = MODULE_STATIC_DEFAULT; k < nargs; k += PROLOG_PARSING) {
                            code.emitLoad(code.getArg(k));
                        }
                        code.emitInvokeSpecial(typeModuleBody.getDeclaredMethod(mname, applyArgs));
                    }
                    code.emitReturn();
                    aswitch.finish(code);
                }
                i += PROLOG_PARSING;
            }
            this.method = save_method;
            this.curClass = save_class;
        }
    }

    private void varArgsToArray(LambdaExp source, int singleArgs, Variable counter, Type lastArgType, Variable ctxVar) {
        boolean mustConvert;
        CodeAttr code = getCode();
        Type elType = ((ArrayType) lastArgType).getComponentType();
        if ("java.lang.Object".equals(elType.getName())) {
            mustConvert = false;
        } else {
            mustConvert = true;
        }
        if (ctxVar != null && !mustConvert) {
            code.emitLoad(ctxVar);
            code.emitPushInt(singleArgs);
            code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getRestArgsArray", (int) PROLOG_PARSING));
        } else if (singleArgs != 0 || mustConvert) {
            code.pushScope();
            if (counter == null) {
                counter = code.addLocal(Type.intType);
                if (ctxVar != null) {
                    code.emitLoad(ctxVar);
                    code.emitInvoke(typeCallContext.getDeclaredMethod("getArgCount", (int) MODULE_STATIC_DEFAULT));
                } else {
                    code.emitLoad(code.getArg(PROLOG_PARSED));
                    code.emitArrayLength();
                }
                if (singleArgs != 0) {
                    code.emitPushInt(singleArgs);
                    code.emitSub(Type.intType);
                }
                code.emitStore(counter);
            }
            code.emitLoad(counter);
            code.emitNewArray(elType.getImplementationType());
            Label testLabel = new Label(code);
            Label loopTopLabel = new Label(code);
            loopTopLabel.setTypes(code);
            code.emitGoto(testLabel);
            loopTopLabel.define(code);
            code.emitDup((int) PROLOG_PARSING);
            code.emitLoad(counter);
            if (ctxVar != null) {
                code.emitLoad(ctxVar);
            } else {
                code.emitLoad(code.getArg(PROLOG_PARSED));
            }
            code.emitLoad(counter);
            if (singleArgs != 0) {
                code.emitPushInt(singleArgs);
                code.emitAdd(Type.intType);
            }
            if (ctxVar != null) {
                code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getArgAsObject", (int) PROLOG_PARSING));
            } else {
                code.emitArrayLoad(Type.objectType);
            }
            if (mustConvert) {
                CheckedTarget.emitCheckedCoerce(this, source, source.getName(), MODULE_STATIC_DEFAULT, elType, null);
            }
            code.emitArrayStore(elType);
            testLabel.define(code);
            code.emitInc(counter, (short) -1);
            code.emitLoad(counter);
            code.emitGotoIfIntGeZero(loopTopLabel);
            code.popScope();
        } else {
            code.emitLoad(code.getArg(PROLOG_PARSED));
        }
    }

    private Method startClassInit() {
        this.method = this.curClass.addMethod("<clinit>", apply0args, Type.voidType, 9);
        CodeAttr code = this.method.startCode();
        if (this.generateMain || generatingApplet() || generatingServlet()) {
            Method registerMethod = ((ClassType) Type.make(getLanguage().getClass())).getDeclaredMethod("registerEnvironment", (int) MODULE_STATIC_DEFAULT);
            if (registerMethod != null) {
                code.emitInvokeStatic(registerMethod);
            }
        }
        return this.method;
    }

    public void process(int wantedState) {
        int i = COMPILE_SETUP;
        int i2 = WALKED;
        int i3 = RESOLVED;
        int i4 = ERROR_SEEN;
        Compilation saveCompilation = setSaveCurrent(this);
        try {
            ModuleExp mexp = getModule();
            if (wantedState >= CALL_WITH_CONTINUATIONS && getState() < CALL_WITH_TAILCALLS) {
                setState(CALL_WITH_TAILCALLS);
                this.language.parse(this, MODULE_STATIC_DEFAULT);
                this.lexer.close();
                this.lexer = null;
                setState(this.messages.seenErrors() ? ERROR_SEEN : CALL_WITH_CONTINUATIONS);
                if (this.pendingImports != null) {
                    return;
                }
            }
            if (wantedState >= RESOLVED) {
                if (getState() < RESOLVED) {
                    addMainClass(mexp);
                    this.language.resolve(this);
                    if (this.messages.seenErrors()) {
                        i3 = ERROR_SEEN;
                    }
                    setState(i3);
                }
            }
            if (!(this.explicit || this.immediate || !this.minfo.checkCurrent(ModuleManager.getInstance(), System.currentTimeMillis()))) {
                this.minfo.cleanupAfterCompilation();
                setState(CLASS_WRITTEN);
            }
            if (wantedState >= WALKED && getState() < WALKED) {
                walkModule(mexp);
                if (this.messages.seenErrors()) {
                    i2 = ERROR_SEEN;
                }
                setState(i2);
            }
            if (wantedState >= COMPILE_SETUP && getState() < COMPILE_SETUP) {
                this.litTable = new LitTable(this);
                mexp.setCanRead(true);
                FindCapturedVars.findCapturedVars(mexp, this);
                mexp.allocFields(this);
                mexp.allocChildMethods(this);
                if (this.messages.seenErrors()) {
                    i = ERROR_SEEN;
                }
                setState(i);
            }
            if (wantedState >= COMPILED && getState() < COMPILED) {
                if (this.immediate) {
                    this.loader = new ArrayClassLoader(ObjectType.getContextClassLoader());
                }
                generateBytecode();
                if (!this.messages.seenErrors()) {
                    i4 = COMPILED;
                }
                setState(i4);
            }
            if (wantedState >= CLASS_WRITTEN && getState() < CLASS_WRITTEN) {
                outputClass(ModuleManager.getInstance().getCompilationDirectory());
                setState(CLASS_WRITTEN);
            }
            restoreCurrent(saveCompilation);
        } catch (SyntaxException ex) {
            setState(ERROR_SEEN);
            if (ex.getMessages() != getMessages()) {
                throw new RuntimeException("confussing syntax error: " + ex);
            }
        } catch (IOException ex2) {
            ex2.printStackTrace();
            error('f', "caught " + ex2);
            setState(ERROR_SEEN);
        } finally {
            restoreCurrent(saveCompilation);
        }
    }

    void generateBytecode() {
        Type[] arg_types;
        int i;
        Variable variable;
        LambdaExp module = getModule();
        if (debugPrintFinalExpr) {
            OutPort dout = OutPort.errDefault();
            dout.println("[Compiling final " + module.getName() + " to " + this.mainClass.getName() + ":");
            module.print(dout);
            dout.println(']');
            dout.flush();
        }
        Type neededSuper = getModuleType();
        if (this.mainClass.getSuperclass().isSubtype(neededSuper)) {
            this.moduleClass = this.mainClass;
        } else {
            this.moduleClass = new ClassType(generateClassName("frame"));
            this.moduleClass.setSuper((ClassType) neededSuper);
            addClass(this.moduleClass);
            generateConstructor(this.moduleClass, null);
        }
        this.curClass = module.type;
        LambdaExp saveLambda = this.curLambda;
        this.curLambda = module;
        if (module.isHandlingTailCalls()) {
            arg_types = new Type[PROLOG_PARSING];
            arg_types[MODULE_STATIC_DEFAULT] = typeCallContext;
        } else {
            if (module.min_args == module.max_args) {
                int i2 = module.min_args;
                if (r0 <= CALL_WITH_CONTINUATIONS) {
                    int arg_count = module.min_args;
                    arg_types = new Type[arg_count];
                    i = arg_count;
                    while (true) {
                        i += MODULE_NONSTATIC;
                        if (i < 0) {
                            break;
                        }
                        arg_types[i] = typeObject;
                    }
                }
            }
            arg_types = new Type[PROLOG_PARSING];
            arg_types[MODULE_STATIC_DEFAULT] = new ArrayType(typeObject);
        }
        Variable heapFrame = module.heapFrame;
        boolean staticModule = module.isStatic();
        this.method = this.curClass.addMethod("run", arg_types, Type.voidType, 17);
        this.method.initCode();
        CodeAttr code = getCode();
        if (this.method.getStaticFlag()) {
            variable = null;
        } else {
            variable = module.declareThis(module.type);
        }
        this.thisDecl = variable;
        module.closureEnv = module.thisVariable;
        if (module.isStatic()) {
            variable = null;
        } else {
            variable = module.thisVariable;
        }
        module.heapFrame = variable;
        module.allocChildClasses(this);
        if (module.isHandlingTailCalls() || usingCPStyle()) {
            this.callContextVar = new Variable("$ctx", typeCallContext);
            module.getVarScope().addVariableAfter(this.thisDecl, this.callContextVar);
            this.callContextVar.setParameter(true);
        }
        int line = module.getLineNumber();
        if (line > 0) {
            code.putLineNumber(module.getFileName(), line);
        }
        module.allocParameters(this);
        module.enterFunction(this);
        if (usingCPStyle()) {
            loadCallContext();
            code.emitGetField(pcCallContextField);
            this.fswitch = code.startSwitch();
            this.fswitch.addCase(MODULE_STATIC_DEFAULT, code);
        }
        module.compileBody(this);
        module.compileEnd(this);
        Label startLiterals = null;
        Label afterLiterals = null;
        Method initMethod = null;
        if (this.curClass == this.mainClass) {
            Method save_method = this.method;
            Variable callContextSave = this.callContextVar;
            this.callContextVar = null;
            initMethod = startClassInit();
            this.clinitMethod = initMethod;
            code = getCode();
            Label label = new Label(code);
            afterLiterals = new Label(code);
            code.fixupChain(afterLiterals, label);
            if (staticModule) {
                generateConstructor(module);
                code.emitNew(this.moduleClass);
                code.emitDup(this.moduleClass);
                code.emitInvokeSpecial(this.moduleClass.constructor);
                this.moduleInstanceMainField = this.moduleClass.addField("$instance", this.moduleClass, 25);
                code.emitPutStatic(this.moduleInstanceMainField);
            }
            while (true) {
                Initializer init = this.clinitChain;
                if (init == null) {
                    break;
                }
                this.clinitChain = null;
                dumpInitializers(init);
            }
            if (module.staticInitRun()) {
                code.emitGetStatic(this.moduleInstanceMainField);
                code.emitInvoke(typeModuleBody.getDeclaredMethod("run", (int) MODULE_STATIC_DEFAULT));
            }
            code.emitReturn();
            if (!(this.moduleClass == this.mainClass || staticModule || this.generateMain || this.immediate)) {
                this.method = this.curClass.addMethod("run", (int) PROLOG_PARSING, Type.typeArray0, Type.voidType);
                code = this.method.startCode();
                Variable ctxVar = code.addLocal(typeCallContext);
                Variable saveVar = code.addLocal(typeConsumer);
                Variable exceptionVar = code.addLocal(Type.javalangThrowableType);
                code.emitInvokeStatic(getCallContextInstanceMethod);
                code.emitStore(ctxVar);
                Field consumerFld = typeCallContext.getDeclaredField("consumer");
                code.emitLoad(ctxVar);
                code.emitGetField(consumerFld);
                code.emitStore(saveVar);
                code.emitLoad(ctxVar);
                code.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
                code.emitPutField(consumerFld);
                code.emitTryStart(false, Type.voidType);
                code.emitPushThis();
                code.emitLoad(ctxVar);
                code.emitInvokeVirtual(save_method);
                code.emitPushNull();
                code.emitStore(exceptionVar);
                code.emitTryEnd();
                code.emitCatchStart(exceptionVar);
                code.emitCatchEnd();
                code.emitTryCatchEnd();
                code.emitLoad(ctxVar);
                code.emitLoad(exceptionVar);
                code.emitLoad(saveVar);
                code.emitInvokeStatic(typeModuleBody.getDeclaredMethod("runCleanup", (int) CALL_WITH_TAILCALLS));
                code.emitReturn();
            }
            this.method = save_method;
            this.callContextVar = callContextSave;
        }
        module.generateApplyMethods(this);
        this.curLambda = saveLambda;
        module.heapFrame = heapFrame;
        if (usingCPStyle()) {
            code = getCode();
            this.fswitch.finish(code);
        }
        if (!(startLiterals == null && this.callContextVar == null)) {
            this.method = initMethod;
            code = getCode();
            label = new Label(code);
            code.fixupChain(startLiterals, label);
            if (this.callContextVarForInit != null) {
                code.emitInvokeStatic(getCallContextInstanceMethod);
                code.emitStore(this.callContextVarForInit);
            }
            try {
                if (this.immediate) {
                    code.emitPushInt(registerForImmediateLiterals(this));
                    code.emitInvokeStatic(ClassType.make("gnu.expr.Compilation").getDeclaredMethod("setupLiterals", (int) PROLOG_PARSING));
                } else {
                    this.litTable.emit();
                }
            } catch (Throwable ex) {
                error('e', "Literals: Internal error:" + ex);
            }
            code.fixupChain(label, afterLiterals);
        }
        if (this.generateMain && this.curClass == this.mainClass) {
            Type[] args = new Type[PROLOG_PARSING];
            args[MODULE_STATIC_DEFAULT] = new ArrayType(javaStringType);
            this.method = this.curClass.addMethod("main", 9, args, Type.voidType);
            code = this.method.startCode();
            if (Shell.defaultFormatName != null) {
                code.emitPushString(Shell.defaultFormatName);
                code.emitInvokeStatic(ClassType.make("kawa.Shell").getDeclaredMethod("setDefaultFormat", (int) PROLOG_PARSING));
            }
            code.emitLoad(code.getArg(MODULE_STATIC_DEFAULT));
            code.emitInvokeStatic(ClassType.make("gnu.expr.ApplicationMainSupport").getDeclaredMethod("processArgs", (int) PROLOG_PARSING));
            if (this.moduleInstanceMainField != null) {
                code.emitGetStatic(this.moduleInstanceMainField);
            } else {
                code.emitNew(this.curClass);
                code.emitDup(this.curClass);
                code.emitInvokeSpecial(this.curClass.constructor);
            }
            code.emitInvokeVirtual(typeModuleBody.getDeclaredMethod("runAsMain", (int) MODULE_STATIC_DEFAULT));
            code.emitReturn();
        }
        if (this.minfo != null) {
            if (this.minfo.getNamespaceUri() != null) {
                ModuleManager manager = ModuleManager.getInstance();
                String mainPrefix = this.mainClass.getName();
                int dot = mainPrefix.lastIndexOf(46);
                if (dot < 0) {
                    mainPrefix = ElementType.MATCH_ANY_LOCALNAME;
                } else {
                    String mainPackage = mainPrefix.substring(MODULE_STATIC_DEFAULT, dot);
                    try {
                        manager.loadPackageInfo(mainPackage);
                    } catch (ClassNotFoundException e) {
                    } catch (Throwable ex2) {
                        error('e', "error loading map for " + mainPackage + " - " + ex2);
                    }
                    mainPrefix = mainPrefix.substring(MODULE_STATIC_DEFAULT, dot + PROLOG_PARSING);
                }
                ClassType classType = new ClassType(mainPrefix + ModuleSet.MODULES_MAP);
                ClassType typeModuleSet = ClassType.make("gnu.expr.ModuleSet");
                classType.setSuper(typeModuleSet);
                registerClass(classType);
                String str = "<init>";
                this.method = classType.addMethod(r48, PROLOG_PARSING, apply0args, Type.voidType);
                str = "<init>";
                Method superConstructor = typeModuleSet.addMethod(r48, PROLOG_PARSING, apply0args, Type.voidType);
                code = this.method.startCode();
                code.emitPushThis();
                code.emitInvokeSpecial(superConstructor);
                code.emitReturn();
                ClassType typeModuleManager = ClassType.make("gnu.expr.ModuleManager");
                Type[] margs = new Type[PROLOG_PARSING];
                margs[MODULE_STATIC_DEFAULT] = typeModuleManager;
                this.method = classType.addMethod("register", margs, Type.voidType, PROLOG_PARSING);
                code = this.method.startCode();
                Method reg = typeModuleManager.getDeclaredMethod("register", CALL_WITH_TAILCALLS);
                i = manager.numModules;
                while (true) {
                    i += MODULE_NONSTATIC;
                    if (i >= 0) {
                        ModuleInfo mi = manager.modules[i];
                        String miClassName = mi.getClassName();
                        if (miClassName != null && miClassName.startsWith(mainPrefix)) {
                            String moduleSource = mi.sourcePath;
                            String moduleUri = mi.getNamespaceUri();
                            code.emitLoad(code.getArg(PROLOG_PARSING));
                            compileConstant(miClassName);
                            if (!Path.valueOf(moduleSource).isAbsolute()) {
                                try {
                                    char sep = File.separatorChar;
                                    String path = manager.getCompilationDirectory();
                                    path = Path.toURL(path + mainPrefix.replace('.', sep)).toString();
                                    int plen = path.length();
                                    if (plen > 0) {
                                        if (path.charAt(plen + MODULE_NONSTATIC) != sep) {
                                            path = path + sep;
                                        }
                                    }
                                    moduleSource = Path.relativize(mi.getSourceAbsPathname(), path);
                                } catch (Throwable ex22) {
                                    WrappedException wrappedException = new WrappedException("exception while fixing up '" + moduleSource + '\'', ex22);
                                }
                            }
                            compileConstant(moduleSource);
                            compileConstant(moduleUri);
                            code.emitInvokeVirtual(reg);
                        }
                    } else {
                        code.emitReturn();
                        return;
                    }
                }
            }
        }
    }

    public Field allocLocalField(Type type, String name) {
        if (name == null) {
            StringBuilder append = new StringBuilder().append("tmp_");
            int i = this.localFieldIndex + PROLOG_PARSING;
            this.localFieldIndex = i;
            name = append.append(i).toString();
        }
        return this.curClass.addField(name, type, MODULE_STATIC_DEFAULT);
    }

    public final void loadCallContext() {
        CodeAttr code = getCode();
        if (this.callContextVar != null && !this.callContextVar.dead()) {
            code.emitLoad(this.callContextVar);
        } else if (this.method == this.clinitMethod) {
            this.callContextVar = new Variable("$ctx", typeCallContext);
            this.callContextVar.reserveLocal(code.getMaxLocals(), code);
            code.emitLoad(this.callContextVar);
            this.callContextVarForInit = this.callContextVar;
        } else {
            code.emitInvokeStatic(getCallContextInstanceMethod);
            code.emitDup();
            this.callContextVar = new Variable("$ctx", typeCallContext);
            code.getCurrentScope().addVariable(code, this.callContextVar);
            code.emitStore(this.callContextVar);
        }
    }

    public void freeLocalField(Field field) {
    }

    public Expression parse(Object input) {
        throw new Error("unimeplemented parse");
    }

    public Language getLanguage() {
        return this.language;
    }

    public LambdaExp currentLambda() {
        return this.current_scope.currentLambda();
    }

    public final ModuleExp getModule() {
        return this.mainLambda;
    }

    public void setModule(ModuleExp mexp) {
        this.mainLambda = mexp;
    }

    public boolean isStatic() {
        return this.mainLambda.isStatic();
    }

    public ModuleExp currentModule() {
        return this.current_scope.currentModule();
    }

    public void mustCompileHere() {
        if (this.mustCompile || ModuleExp.compilerAvailable) {
            this.mustCompile = true;
        } else {
            error('w', "this expression claimed that it must be compiled, but compiler is unavailable");
        }
    }

    public ScopeExp currentScope() {
        return this.current_scope;
    }

    public void setCurrentScope(ScopeExp scope) {
        int scope_nesting = ScopeExp.nesting(scope);
        int current_nesting = ScopeExp.nesting(this.current_scope);
        while (current_nesting > scope_nesting) {
            pop(this.current_scope);
            current_nesting += MODULE_NONSTATIC;
        }
        ScopeExp sc = scope;
        while (scope_nesting > current_nesting) {
            sc = sc.outer;
            scope_nesting += MODULE_NONSTATIC;
        }
        while (sc != this.current_scope) {
            pop(this.current_scope);
            sc = sc.outer;
        }
        pushChain(scope, sc);
    }

    void pushChain(ScopeExp scope, ScopeExp limit) {
        if (scope != limit) {
            pushChain(scope.outer, limit);
            pushScope(scope);
            this.lexical.push(scope);
        }
    }

    public ModuleExp pushNewModule(Lexer lexer) {
        this.lexer = lexer;
        return pushNewModule(lexer.getName());
    }

    public ModuleExp pushNewModule(String filename) {
        ScopeExp module = new ModuleExp();
        if (filename != null) {
            module.setFile(filename);
        }
        if (generatingApplet() || generatingServlet()) {
            module.setFlag(ModuleExp.SUPERTYPE_SPECIFIED);
        }
        if (this.immediate) {
            module.setFlag(ModuleExp.IMMEDIATE);
            new ModuleInfo().setCompilation(this);
        }
        this.mainLambda = module;
        push(module);
        return module;
    }

    public void push(ScopeExp scope) {
        pushScope(scope);
        this.lexical.push(scope);
    }

    public final void pushScope(ScopeExp scope) {
        if (!this.mustCompile && (scope.mustCompile() || (ModuleExp.compilerAvailable && (scope instanceof LambdaExp) && !(scope instanceof ModuleExp)))) {
            mustCompileHere();
        }
        scope.outer = this.current_scope;
        this.current_scope = scope;
    }

    public void pop(ScopeExp scope) {
        this.lexical.pop(scope);
        this.current_scope = scope.outer;
    }

    public final void pop() {
        pop(this.current_scope);
    }

    public void push(Declaration decl) {
        this.lexical.push(decl);
    }

    public Declaration lookup(Object name, int namespace) {
        return this.lexical.lookup(name, namespace);
    }

    public void usedClass(Type type) {
        while (type instanceof ArrayType) {
            type = ((ArrayType) type).getComponentType();
        }
        if (this.immediate && (type instanceof ClassType)) {
            this.loader.addClass((ClassType) type);
        }
    }

    public SourceMessages getMessages() {
        return this.messages;
    }

    public void setMessages(SourceMessages messages) {
        this.messages = messages;
    }

    public void error(char severity, String message, SourceLocator location) {
        String file = location.getFileName();
        int line = location.getLineNumber();
        int column = location.getColumnNumber();
        if (file == null || line <= 0) {
            file = getFileName();
            line = getLineNumber();
            column = getColumnNumber();
        }
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        this.messages.error(severity, file, line, column, message);
    }

    public void error(char severity, String message) {
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        this.messages.error(severity, (SourceLocator) this, message);
    }

    public void error(char severity, Declaration decl, String msg1, String msg2) {
        error(severity, msg1 + decl.getName() + msg2, null, decl);
    }

    public void error(char severity, String message, String code, Declaration decl) {
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        String filename = getFileName();
        int line = getLineNumber();
        int column = getColumnNumber();
        int decl_line = decl.getLineNumber();
        if (decl_line > 0) {
            filename = decl.getFileName();
            line = decl_line;
            column = decl.getColumnNumber();
        }
        this.messages.error(severity, filename, line, column, message, code);
    }

    public Expression syntaxError(String message) {
        error('e', message);
        return new ErrorExp(message);
    }

    public final int getLineNumber() {
        return this.messages.getLineNumber();
    }

    public final int getColumnNumber() {
        return this.messages.getColumnNumber();
    }

    public final String getFileName() {
        return this.messages.getFileName();
    }

    public String getPublicId() {
        return this.messages.getPublicId();
    }

    public String getSystemId() {
        return this.messages.getSystemId();
    }

    public boolean isStableSourceLocation() {
        return false;
    }

    public void setFile(String filename) {
        this.messages.setFile(filename);
    }

    public void setLine(int line) {
        this.messages.setLine(line);
    }

    public void setColumn(int column) {
        this.messages.setColumn(column);
    }

    public final void setLine(Expression position) {
        this.messages.setLocation(position);
    }

    public void setLine(Object location) {
        if (location instanceof SourceLocator) {
            this.messages.setLocation((SourceLocator) location);
        }
    }

    public final void setLocation(SourceLocator position) {
        this.messages.setLocation(position);
    }

    public void setLine(String filename, int line, int column) {
        this.messages.setLine(filename, line, column);
    }

    public void letStart() {
        pushScope(new LetExp(null));
    }

    public Declaration letVariable(Object name, Type type, Expression init) {
        Declaration decl = this.current_scope.addDeclaration(name, type);
        decl.noteValue(init);
        return decl;
    }

    public void letEnter() {
        ScopeExp let = this.current_scope;
        Expression[] inits = new Expression[let.countDecls()];
        Declaration decl = let.firstDecl();
        int i = MODULE_STATIC_DEFAULT;
        while (decl != null) {
            int i2 = i + PROLOG_PARSING;
            inits[i] = decl.getValue();
            decl = decl.nextDecl();
            i = i2;
        }
        let.inits = inits;
        this.lexical.push(let);
    }

    public LetExp letDone(Expression body) {
        LetExp let = this.current_scope;
        let.body = body;
        pop(let);
        return let;
    }

    private void checkLoop() {
        if (((LambdaExp) this.current_scope).getName() != "%do%loop") {
            throw new Error("internal error - bad loop state");
        }
    }

    public void loopStart() {
        LambdaExp loopLambda = new LambdaExp();
        Expression[] inits = new Expression[PROLOG_PARSING];
        inits[MODULE_STATIC_DEFAULT] = loopLambda;
        LetExp let = new LetExp(inits);
        String fname = "%do%loop";
        let.addDeclaration((Object) fname).noteValue(loopLambda);
        loopLambda.setName(fname);
        let.outer = this.current_scope;
        loopLambda.outer = let;
        this.current_scope = loopLambda;
    }

    public Declaration loopVariable(Object name, Type type, Expression init) {
        checkLoop();
        LambdaExp loopLambda = this.current_scope;
        Declaration decl = loopLambda.addDeclaration(name, type);
        if (this.exprStack == null) {
            this.exprStack = new Stack();
        }
        this.exprStack.push(init);
        loopLambda.min_args += PROLOG_PARSING;
        return decl;
    }

    public void loopEnter() {
        checkLoop();
        ScopeExp loopLambda = this.current_scope;
        int ninits = loopLambda.min_args;
        loopLambda.max_args = ninits;
        Expression[] inits = new Expression[ninits];
        int i = ninits;
        while (true) {
            i += MODULE_NONSTATIC;
            if (i >= 0) {
                inits[i] = (Expression) this.exprStack.pop();
            } else {
                LetExp let = loopLambda.outer;
                let.setBody(new ApplyExp(new ReferenceExp(let.firstDecl()), inits));
                this.lexical.push(loopLambda);
                return;
            }
        }
    }

    public void loopCond(Expression cond) {
        checkLoop();
        this.exprStack.push(cond);
    }

    public void loopBody(Expression body) {
        this.current_scope.body = body;
    }

    public Expression loopRepeat(Expression[] exps) {
        ScopeExp loopLambda = this.current_scope;
        ScopeExp let = loopLambda.outer;
        loopLambda.body = new IfExp((Expression) this.exprStack.pop(), new BeginExp(loopLambda.body, new ApplyExp(new ReferenceExp(let.firstDecl()), exps)), QuoteExp.voidExp);
        this.lexical.pop(loopLambda);
        this.current_scope = let.outer;
        return let;
    }

    public Expression loopRepeat() {
        return loopRepeat(Expression.noExpressions);
    }

    public Expression loopRepeat(Expression exp) {
        Expression[] args = new Expression[PROLOG_PARSING];
        args[MODULE_STATIC_DEFAULT] = exp;
        return loopRepeat(args);
    }

    public static ApplyExp makeCoercion(Expression value, Expression type) {
        Expression[] exps = new Expression[PROLOG_PARSED];
        exps[MODULE_STATIC_DEFAULT] = type;
        exps[PROLOG_PARSING] = value;
        return new ApplyExp(new QuoteExp(Convert.getInstance()), exps);
    }

    public static Expression makeCoercion(Expression value, Type type) {
        return makeCoercion(value, new QuoteExp(type));
    }

    public void loadClassRef(ObjectType clas) {
        CodeAttr code = getCode();
        if (this.curClass.getClassfileVersion() >= ClassType.JDK_1_5_VERSION) {
            code.emitPushClass(clas);
        } else if (clas == this.mainClass && this.mainLambda.isStatic() && this.moduleInstanceMainField != null) {
            code.emitGetStatic(this.moduleInstanceMainField);
            code.emitInvokeVirtual(Type.objectType.getDeclaredMethod("getClass", (int) MODULE_STATIC_DEFAULT));
        } else {
            code.emitPushString(clas instanceof ClassType ? clas.getName() : clas.getInternalName().replace('/', '.'));
            code.emitInvokeStatic(getForNameHelper());
        }
    }

    public Method getForNameHelper() {
        if (this.forNameHelper == null) {
            Method save_method = this.method;
            this.method = this.curClass.addMethod("class$", 9, string1Arg, typeClass);
            this.forNameHelper = this.method;
            CodeAttr code = this.method.startCode();
            code.emitLoad(code.getArg(MODULE_STATIC_DEFAULT));
            code.emitPushInt(MODULE_STATIC_DEFAULT);
            code.emitPushString(this.mainClass.getName());
            code.emitInvokeStatic(typeClass.getDeclaredMethod("forName", (int) PROLOG_PARSING));
            code.emitInvokeVirtual(typeClass.getDeclaredMethod("getClassLoader", (int) MODULE_STATIC_DEFAULT));
            code.emitInvokeStatic(typeClass.getDeclaredMethod("forName", (int) CALL_WITH_TAILCALLS));
            code.emitReturn();
            this.method = save_method;
        }
        return this.forNameHelper;
    }

    public Object resolve(Object name, boolean function) {
        Environment env = Environment.getCurrent();
        if (name instanceof String) {
            EnvironmentKey symbol = env.defaultNamespace().lookup((String) name);
        } else {
            Symbol symbol2 = (Symbol) name;
        }
        if (symbol == null) {
            return null;
        }
        if (function && getLanguage().hasSeparateFunctionNamespace()) {
            return env.getFunction(symbol, null);
        }
        return env.get(symbol, null);
    }

    public static void setupLiterals(int key) {
        Compilation comp = findForImmediateLiterals(key);
        try {
            Class clas = comp.loader.loadClass(comp.mainClass.getName());
            for (Literal init = comp.litTable.literalsChain; init != null; init = init.next) {
                clas.getDeclaredField(init.field.getName()).set(null, init.value);
            }
            comp.litTable = null;
        } catch (Throwable ex) {
            WrappedException wrappedException = new WrappedException("internal error", ex);
        }
    }

    public static synchronized int registerForImmediateLiterals(Compilation comp) {
        int i;
        synchronized (Compilation.class) {
            i = MODULE_STATIC_DEFAULT;
            for (Compilation c = chainUninitialized; c != null; c = c.nextUninitialized) {
                if (i <= c.keyUninitialized) {
                    i = c.keyUninitialized + PROLOG_PARSING;
                }
            }
            comp.keyUninitialized = i;
            comp.nextUninitialized = chainUninitialized;
            chainUninitialized = comp;
        }
        return i;
    }

    public static synchronized Compilation findForImmediateLiterals(int key) {
        Compilation comp;
        synchronized (Compilation.class) {
            Compilation prev = null;
            comp = chainUninitialized;
            while (true) {
                Compilation next = comp.nextUninitialized;
                if (comp.keyUninitialized == key) {
                    break;
                }
                prev = comp;
                comp = next;
            }
            if (prev == null) {
                chainUninitialized = next;
            } else {
                prev.nextUninitialized = next;
            }
            comp.nextUninitialized = null;
        }
        return comp;
    }

    public static Compilation getCurrent() {
        return (Compilation) current.get();
    }

    public static void setCurrent(Compilation comp) {
        current.set(comp);
    }

    public static Compilation setSaveCurrent(Compilation comp) {
        Compilation save = (Compilation) current.get();
        current.set(comp);
        return save;
    }

    public static void restoreCurrent(Compilation saved) {
        current.set(saved);
    }

    public String toString() {
        return "<compilation " + this.mainLambda + ">";
    }
}
