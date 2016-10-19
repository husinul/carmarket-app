package gnu.kawa.lispexpr;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.functions.MakeList;
import gnu.kawa.reflect.InstanceOf;
import gnu.mapping.Procedure;
import gnu.mapping.WrongType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.URIPath;
import java.util.List;

public class LangObjType extends ObjectType implements TypeValue {
    private static final int CLASSTYPE_TYPE_CODE = 6;
    private static final int CLASS_TYPE_CODE = 4;
    private static final int DFLONUM_TYPE_CODE = 15;
    private static final int FILEPATH_TYPE_CODE = 2;
    private static final int INTEGER_TYPE_CODE = 7;
    private static final int LIST_TYPE_CODE = 11;
    private static final int NUMERIC_TYPE_CODE = 10;
    private static final int PATH_TYPE_CODE = 1;
    private static final int RATIONAL_TYPE_CODE = 8;
    private static final int REAL_TYPE_CODE = 9;
    private static final int REGEX_TYPE_CODE = 14;
    private static final int STRING_TYPE_CODE = 13;
    private static final int TYPE_TYPE_CODE = 5;
    public static final LangObjType URIType;
    private static final int URI_TYPE_CODE = 3;
    static final String VARARGS_SUFFIX = "";
    private static final int VECTOR_TYPE_CODE = 12;
    public static final LangObjType dflonumType;
    public static final LangObjType filepathType;
    public static final LangObjType integerType;
    public static final LangObjType listType;
    static PrimProcedure makeFilepathProc;
    static PrimProcedure makePathProc;
    static PrimProcedure makeURIProc;
    public static final LangObjType numericType;
    public static final LangObjType pathType;
    public static final LangObjType rationalType;
    public static final LangObjType realType;
    public static final LangObjType regexType;
    public static final LangObjType stringType;
    static final ClassType typeArithmetic;
    public static final LangObjType typeClass;
    public static final LangObjType typeClassType;
    public static final ClassType typeLangObjType;
    public static final LangObjType typeType;
    public static final LangObjType vectorType;
    ClassType implementationType;
    final int typeCode;

    static {
        pathType = new LangObjType("path", "gnu.text.Path", PATH_TYPE_CODE);
        filepathType = new LangObjType("filepath", "gnu.text.FilePath", FILEPATH_TYPE_CODE);
        URIType = new LangObjType("URI", "gnu.text.URIPath", URI_TYPE_CODE);
        typeClass = new LangObjType("class", "java.lang.Class", CLASS_TYPE_CODE);
        typeType = new LangObjType("type", "gnu.bytecode.Type", TYPE_TYPE_CODE);
        typeClassType = new LangObjType("class-type", "gnu.bytecode.ClassType", CLASSTYPE_TYPE_CODE);
        numericType = new LangObjType("number", "gnu.math.Numeric", NUMERIC_TYPE_CODE);
        realType = new LangObjType("real", "gnu.math.RealNum", REAL_TYPE_CODE);
        rationalType = new LangObjType("rational", "gnu.math.RatNum", RATIONAL_TYPE_CODE);
        integerType = new LangObjType(PropertyTypeConstants.PROPERTY_TYPE_INTEGER, "gnu.math.IntNum", INTEGER_TYPE_CODE);
        dflonumType = new LangObjType("DFloNum", "gnu.math.DFloNum", DFLONUM_TYPE_CODE);
        vectorType = new LangObjType("vector", "gnu.lists.FVector", VECTOR_TYPE_CODE);
        regexType = new LangObjType("regex", "java.util.regex.Pattern", REGEX_TYPE_CODE);
        stringType = new LangObjType(PropertyTypeConstants.PROPERTY_TYPE_STRING, "java.lang.CharSequence", STRING_TYPE_CODE);
        listType = new LangObjType("list", "gnu.lists.LList", LIST_TYPE_CODE);
        typeArithmetic = ClassType.make("gnu.kawa.functions.Arithmetic");
        makePathProc = new PrimProcedure("gnu.text.Path", "valueOf", (int) PATH_TYPE_CODE);
        makeFilepathProc = new PrimProcedure("gnu.text.FilePath", "makeFilePath", (int) PATH_TYPE_CODE);
        makeURIProc = new PrimProcedure("gnu.text.URIPath", "makeURI", (int) PATH_TYPE_CODE);
        typeLangObjType = ClassType.make("gnu.kawa.lispexpr.LangObjType");
    }

    LangObjType(String name, String implClass, int typeCode) {
        super(name);
        this.implementationType = ClassType.make(implClass);
        this.typeCode = typeCode;
        setSignature(this.implementationType.getSignature());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compare(gnu.bytecode.Type r6) {
        /*
        r5 = this;
        r4 = 0;
        r1 = -1;
        r2 = 1;
        r3 = r5.typeCode;
        switch(r3) {
            case 4: goto L_0x0015;
            case 5: goto L_0x002a;
            case 6: goto L_0x0040;
            case 7: goto L_0x0057;
            case 8: goto L_0x0008;
            case 9: goto L_0x0066;
            case 10: goto L_0x0008;
            case 11: goto L_0x0008;
            case 12: goto L_0x0008;
            case 13: goto L_0x0008;
            case 14: goto L_0x0008;
            case 15: goto L_0x0066;
            default: goto L_0x0008;
        };
    L_0x0008:
        r1 = r5.getImplementationType();
        r2 = r6.getImplementationType();
        r1 = r1.compare(r2);
    L_0x0014:
        return r1;
    L_0x0015:
        r2 = typeType;
        if (r6 == r2) goto L_0x0014;
    L_0x0019:
        r2 = typeClassType;
        if (r6 == r2) goto L_0x0014;
    L_0x001d:
        r2 = typeType;
        r2 = r2.implementationType;
        if (r6 == r2) goto L_0x0014;
    L_0x0023:
        r2 = typeClassType;
        r2 = r2.implementationType;
        if (r6 != r2) goto L_0x0008;
    L_0x0029:
        goto L_0x0014;
    L_0x002a:
        r3 = typeClass;
        if (r6 == r3) goto L_0x003e;
    L_0x002e:
        r3 = typeClassType;
        if (r6 == r3) goto L_0x003e;
    L_0x0032:
        r3 = typeClass;
        r3 = r3.implementationType;
        if (r6 == r3) goto L_0x003e;
    L_0x0038:
        r3 = typeClassType;
        r3 = r3.implementationType;
        if (r6 != r3) goto L_0x0040;
    L_0x003e:
        r1 = r2;
        goto L_0x0014;
    L_0x0040:
        r3 = typeClass;
        if (r6 == r3) goto L_0x004a;
    L_0x0044:
        r3 = typeClass;
        r3 = r3.implementationType;
        if (r6 != r3) goto L_0x004c;
    L_0x004a:
        r1 = r2;
        goto L_0x0014;
    L_0x004c:
        r2 = typeType;
        if (r6 == r2) goto L_0x0014;
    L_0x0050:
        r2 = typeClass;
        r2 = r2.implementationType;
        if (r6 != r2) goto L_0x0008;
    L_0x0056:
        goto L_0x0014;
    L_0x0057:
        r1 = r6 instanceof gnu.bytecode.PrimType;
        if (r1 == 0) goto L_0x0066;
    L_0x005b:
        r1 = r6.getSignature();
        r0 = r1.charAt(r4);
        switch(r0) {
            case 66: goto L_0x0078;
            case 73: goto L_0x0078;
            case 74: goto L_0x0078;
            case 83: goto L_0x0078;
            default: goto L_0x0066;
        };
    L_0x0066:
        r1 = r6 instanceof gnu.bytecode.PrimType;
        if (r1 == 0) goto L_0x0008;
    L_0x006a:
        r1 = r6.getSignature();
        r0 = r1.charAt(r4);
        switch(r0) {
            case 68: goto L_0x0076;
            case 69: goto L_0x0075;
            case 70: goto L_0x0076;
            default: goto L_0x0075;
        };
    L_0x0075:
        goto L_0x0008;
    L_0x0076:
        r1 = r2;
        goto L_0x0014;
    L_0x0078:
        r1 = r2;
        goto L_0x0014;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LangObjType.compare(gnu.bytecode.Type):int");
    }

    public Field getField(String name, int mask) {
        return this.implementationType.getField(name, mask);
    }

    public Method getMethod(String name, Type[] arg_types) {
        return this.implementationType.getMethod(name, arg_types);
    }

    public Method getDeclaredMethod(String name, int argCount) {
        return this.implementationType.getDeclaredMethod(name, argCount);
    }

    public int getMethods(Filter filter, int searchSupers, List<Method> result) {
        return this.implementationType.getMethods(filter, searchSupers, result);
    }

    public Class getReflectClass() {
        return this.implementationType.getReflectClass();
    }

    public Type getRealType() {
        return this.implementationType;
    }

    public Type getImplementationType() {
        return this.implementationType;
    }

    public void emitIsInstance(Variable incoming, Compilation comp, Target target) {
        switch (this.typeCode) {
            case LIST_TYPE_CODE /*11*/:
            case VECTOR_TYPE_CODE /*12*/:
            case STRING_TYPE_CODE /*13*/:
            case REGEX_TYPE_CODE /*14*/:
                this.implementationType.emitIsInstance(comp.getCode());
                target.compileFromStack(comp, comp.getLanguage().getTypeFor(Boolean.TYPE));
            default:
                InstanceOf.emitIsInstance(this, incoming, comp, target);
        }
    }

    public static Numeric coerceNumeric(Object value) {
        Numeric rval = Numeric.asNumericOrNull(value);
        if (rval != null || value == null) {
            return rval;
        }
        throw new WrongType(-4, value, numericType);
    }

    public static RealNum coerceRealNum(Object value) {
        RealNum rval = RealNum.asRealNumOrNull(value);
        if (rval != null || value == null) {
            return rval;
        }
        throw new WrongType(-4, value, realType);
    }

    public static DFloNum coerceDFloNum(Object value) {
        DFloNum rval = DFloNum.asDFloNumOrNull(value);
        if (rval != null || value == null) {
            return rval;
        }
        throw new WrongType(-4, value, dflonumType);
    }

    public static RatNum coerceRatNum(Object value) {
        RatNum rval = RatNum.asRatNumOrNull(value);
        if (rval != null || value == null) {
            return rval;
        }
        throw new WrongType(-4, value, rationalType);
    }

    public static IntNum coerceIntNum(Object value) {
        IntNum ival = IntNum.asIntNumOrNull(value);
        if (ival != null || value == null) {
            return ival;
        }
        throw new WrongType(-4, value, integerType);
    }

    public static Class coerceToClassOrNull(Object type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if ((type instanceof Type) && (type instanceof ClassType) && !(type instanceof PairClassType)) {
            return ((ClassType) type).getReflectClass();
        }
        return null;
    }

    public static Class coerceToClass(Object obj) {
        Class coerced = coerceToClassOrNull(obj);
        if (coerced != null || obj == null) {
            return coerced;
        }
        throw new ClassCastException("cannot cast " + obj + " to type");
    }

    public static ClassType coerceToClassTypeOrNull(Object type) {
        if (type instanceof ClassType) {
            return (ClassType) type;
        }
        if (type instanceof Class) {
            Type t = Language.getDefaultLanguage().getTypeFor((Class) type);
            if (t instanceof ClassType) {
                return (ClassType) t;
            }
        }
        return null;
    }

    public static ClassType coerceToClassType(Object obj) {
        ClassType coerced = coerceToClassTypeOrNull(obj);
        if (coerced != null || obj == null) {
            return coerced;
        }
        throw new ClassCastException("cannot cast " + obj + " to class-type");
    }

    public static Type coerceToTypeOrNull(Object type) {
        if (type instanceof Type) {
            return (Type) type;
        }
        if (type instanceof Class) {
            return Language.getDefaultLanguage().getTypeFor((Class) type);
        }
        return null;
    }

    public static Type coerceToType(Object obj) {
        Type coerced = coerceToTypeOrNull(obj);
        if (coerced != null || obj == null) {
            return coerced;
        }
        throw new ClassCastException("cannot cast " + obj + " to type");
    }

    Method coercionMethod() {
        switch (this.typeCode) {
            case CLASS_TYPE_CODE /*4*/:
                return typeLangObjType.getDeclaredMethod("coerceToClass", (int) PATH_TYPE_CODE);
            case TYPE_TYPE_CODE /*5*/:
                return typeLangObjType.getDeclaredMethod("coerceToType", (int) PATH_TYPE_CODE);
            case CLASSTYPE_TYPE_CODE /*6*/:
                return typeLangObjType.getDeclaredMethod("coerceToClassType", (int) PATH_TYPE_CODE);
            case INTEGER_TYPE_CODE /*7*/:
                return typeLangObjType.getDeclaredMethod("coerceIntNum", (int) PATH_TYPE_CODE);
            case RATIONAL_TYPE_CODE /*8*/:
                return typeLangObjType.getDeclaredMethod("coerceRatNum", (int) PATH_TYPE_CODE);
            case REAL_TYPE_CODE /*9*/:
                return typeLangObjType.getDeclaredMethod("coerceRealNum", (int) PATH_TYPE_CODE);
            case NUMERIC_TYPE_CODE /*10*/:
                return typeLangObjType.getDeclaredMethod("coerceNumeric", (int) PATH_TYPE_CODE);
            case LIST_TYPE_CODE /*11*/:
            case VECTOR_TYPE_CODE /*12*/:
            case STRING_TYPE_CODE /*13*/:
            case REGEX_TYPE_CODE /*14*/:
                return null;
            case DFLONUM_TYPE_CODE /*15*/:
                return typeLangObjType.getDeclaredMethod("coerceDFloNum", (int) PATH_TYPE_CODE);
            default:
                return ((PrimProcedure) getConstructor()).getMethod();
        }
    }

    Method coercionOrNullMethod() {
        String mname;
        ClassType methodDeclaringClass = this.implementationType;
        switch (this.typeCode) {
            case PATH_TYPE_CODE /*1*/:
                mname = "coerceToPathOrNull";
                break;
            case FILEPATH_TYPE_CODE /*2*/:
                mname = "coerceToFilePathOrNull";
                break;
            case URI_TYPE_CODE /*3*/:
                mname = "coerceToURIPathOrNull";
                break;
            case CLASS_TYPE_CODE /*4*/:
                methodDeclaringClass = typeLangObjType;
                mname = "coerceToClassOrNull";
                break;
            case TYPE_TYPE_CODE /*5*/:
                methodDeclaringClass = typeLangObjType;
                mname = "coerceToTypeOrNull";
                break;
            case CLASSTYPE_TYPE_CODE /*6*/:
                methodDeclaringClass = typeLangObjType;
                mname = "coerceToClassTypeOrNull";
                break;
            case INTEGER_TYPE_CODE /*7*/:
                methodDeclaringClass = this.implementationType;
                mname = "asIntNumOrNull";
                break;
            case RATIONAL_TYPE_CODE /*8*/:
                methodDeclaringClass = this.implementationType;
                mname = "asRatNumOrNull";
                break;
            case REAL_TYPE_CODE /*9*/:
                methodDeclaringClass = this.implementationType;
                mname = "asRealNumOrNull";
                break;
            case NUMERIC_TYPE_CODE /*10*/:
                methodDeclaringClass = this.implementationType;
                mname = "asNumericOrNull";
                break;
            case DFLONUM_TYPE_CODE /*15*/:
                methodDeclaringClass = this.implementationType;
                mname = "asDFloNumOrNull";
                break;
            default:
                return null;
        }
        return methodDeclaringClass.getDeclaredMethod(mname, (int) PATH_TYPE_CODE);
    }

    public void emitTestIf(Variable incoming, Declaration decl, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (incoming != null) {
            code.emitLoad(incoming);
        }
        Method method = coercionOrNullMethod();
        if (method != null) {
            code.emitInvokeStatic(method);
        }
        if (decl != null) {
            code.emitDup();
            decl.compileStore(comp);
        }
        if (method != null) {
            code.emitIfNotNull();
            return;
        }
        this.implementationType.emitIsInstance(code);
        code.emitIfIntNotZero();
    }

    public Object coerceFromObject(Object obj) {
        switch (this.typeCode) {
            case PATH_TYPE_CODE /*1*/:
                return Path.valueOf(obj);
            case FILEPATH_TYPE_CODE /*2*/:
                return FilePath.makeFilePath(obj);
            case URI_TYPE_CODE /*3*/:
                return URIPath.makeURI(obj);
            case CLASS_TYPE_CODE /*4*/:
                return coerceToClass(obj);
            case TYPE_TYPE_CODE /*5*/:
                return coerceToType(obj);
            case CLASSTYPE_TYPE_CODE /*6*/:
                return coerceToClassType(obj);
            case INTEGER_TYPE_CODE /*7*/:
                return coerceIntNum(obj);
            case RATIONAL_TYPE_CODE /*8*/:
                return coerceRatNum(obj);
            case REAL_TYPE_CODE /*9*/:
                return coerceRealNum(obj);
            case NUMERIC_TYPE_CODE /*10*/:
                return coerceNumeric(obj);
            case DFLONUM_TYPE_CODE /*15*/:
                return coerceDFloNum(obj);
            default:
                return super.coerceFromObject(obj);
        }
    }

    public void emitConvertFromPrimitive(Type stackType, CodeAttr code) {
        Type argType = null;
        String cname = null;
        switch (this.typeCode) {
            case INTEGER_TYPE_CODE /*7*/:
            case RATIONAL_TYPE_CODE /*8*/:
            case REAL_TYPE_CODE /*9*/:
            case NUMERIC_TYPE_CODE /*10*/:
                if (stackType instanceof PrimType) {
                    if (stackType != Type.intType && stackType != Type.byteType && stackType != Type.shortType) {
                        if (stackType != Type.longType) {
                            if (this.typeCode == REAL_TYPE_CODE || this.typeCode == NUMERIC_TYPE_CODE) {
                                if (stackType == Type.floatType) {
                                    code.emitConvert(Type.float_type, Type.double_type);
                                    stackType = Type.doubleType;
                                }
                                if (stackType == Type.doubleType) {
                                    cname = "gnu.math.DFloNum";
                                    argType = Type.doubleType;
                                    break;
                                }
                            }
                        }
                        cname = "gnu.math.IntNum";
                        argType = Type.long_type;
                        break;
                    }
                    cname = "gnu.math.IntNum";
                    argType = Type.int_type;
                    break;
                }
                break;
            case DFLONUM_TYPE_CODE /*15*/:
                if (stackType instanceof PrimType) {
                    if (stackType == Type.intType || stackType == Type.byteType || stackType == Type.shortType || stackType == Type.longType || stackType == Type.floatType) {
                        code.emitConvert(stackType, Type.doubleType);
                        stackType = Type.doubleType;
                    }
                    if (stackType == Type.doubleType) {
                        cname = "gnu.math.DFloNum";
                        argType = stackType;
                        break;
                    }
                }
                break;
        }
        if (cname != null) {
            ClassType clas = ClassType.make(cname);
            Type[] args = new Type[PATH_TYPE_CODE];
            args[0] = argType;
            code.emitInvokeStatic(clas.getDeclaredMethod("make", args));
            return;
        }
        super.emitConvertFromPrimitive(stackType, code);
    }

    public Expression convertValue(Expression value) {
        if (this.typeCode == INTEGER_TYPE_CODE || this.typeCode == NUMERIC_TYPE_CODE || this.typeCode == REAL_TYPE_CODE || this.typeCode == RATIONAL_TYPE_CODE || this.typeCode == DFLONUM_TYPE_CODE) {
            return null;
        }
        Method method = coercionMethod();
        if (method == null) {
            return null;
        }
        Expression[] expressionArr = new Expression[PATH_TYPE_CODE];
        expressionArr[0] = value;
        Expression aexp = new ApplyExp(method, expressionArr);
        aexp.setType(this);
        return aexp;
    }

    public void emitCoerceFromObject(CodeAttr code) {
        switch (this.typeCode) {
            case LIST_TYPE_CODE /*11*/:
            case VECTOR_TYPE_CODE /*12*/:
            case STRING_TYPE_CODE /*13*/:
            case REGEX_TYPE_CODE /*14*/:
                code.emitCheckcast(this.implementationType);
            default:
                code.emitInvoke(coercionMethod());
        }
    }

    public Procedure getConstructor() {
        switch (this.typeCode) {
            case PATH_TYPE_CODE /*1*/:
                return makePathProc;
            case FILEPATH_TYPE_CODE /*2*/:
                return makeFilepathProc;
            case URI_TYPE_CODE /*3*/:
                return makeURIProc;
            case LIST_TYPE_CODE /*11*/:
                return MakeList.list;
            case VECTOR_TYPE_CODE /*12*/:
                return new PrimProcedure("gnu.lists.FVector", "make", (int) PATH_TYPE_CODE);
            case STRING_TYPE_CODE /*13*/:
                return new PrimProcedure("kawa.lib.strings", "$make$string$", (int) PATH_TYPE_CODE);
            case REGEX_TYPE_CODE /*14*/:
                return new PrimProcedure("java.util.regex.Pattern", "compile", (int) PATH_TYPE_CODE);
            default:
                return null;
        }
    }
}
