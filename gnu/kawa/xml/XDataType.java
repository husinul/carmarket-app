package gnu.kawa.xml;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.lists.Consumer;
import gnu.lists.SeqPosition;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.DateTime;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.math.RealNum;
import gnu.math.Unit;
import gnu.text.Path;
import gnu.text.Printable;
import gnu.text.URIPath;
import gnu.xml.TextUtils;
import java.math.BigDecimal;

public class XDataType extends Type implements TypeValue {
    public static final int ANY_ATOMIC_TYPE_CODE = 3;
    public static final int ANY_SIMPLE_TYPE_CODE = 2;
    public static final int ANY_URI_TYPE_CODE = 33;
    public static final int BASE64_BINARY_TYPE_CODE = 34;
    public static final int BOOLEAN_TYPE_CODE = 31;
    public static final int BYTE_TYPE_CODE = 11;
    public static final int DATE_TIME_TYPE_CODE = 20;
    public static final int DATE_TYPE_CODE = 21;
    public static final int DAY_TIME_DURATION_TYPE_CODE = 30;
    public static final BigDecimal DECIMAL_ONE;
    public static final int DECIMAL_TYPE_CODE = 4;
    public static final Double DOUBLE_ONE;
    public static final int DOUBLE_TYPE_CODE = 19;
    public static final Double DOUBLE_ZERO;
    public static final int DURATION_TYPE_CODE = 28;
    public static final int ENTITY_TYPE_CODE = 47;
    public static final Float FLOAT_ONE;
    public static final int FLOAT_TYPE_CODE = 18;
    public static final Float FLOAT_ZERO;
    public static final int G_DAY_TYPE_CODE = 26;
    public static final int G_MONTH_DAY_TYPE_CODE = 25;
    public static final int G_MONTH_TYPE_CODE = 27;
    public static final int G_YEAR_MONTH_TYPE_CODE = 23;
    public static final int G_YEAR_TYPE_CODE = 24;
    public static final int HEX_BINARY_TYPE_CODE = 35;
    public static final int IDREF_TYPE_CODE = 46;
    public static final int ID_TYPE_CODE = 45;
    public static final int INTEGER_TYPE_CODE = 5;
    public static final int INT_TYPE_CODE = 9;
    public static final int LANGUAGE_TYPE_CODE = 41;
    public static final int LONG_TYPE_CODE = 8;
    public static final int NAME_TYPE_CODE = 43;
    public static final int NCNAME_TYPE_CODE = 44;
    public static final int NEGATIVE_INTEGER_TYPE_CODE = 7;
    public static final int NMTOKEN_TYPE_CODE = 42;
    public static final int NONNEGATIVE_INTEGER_TYPE_CODE = 12;
    public static final int NON_POSITIVE_INTEGER_TYPE_CODE = 6;
    public static final int NORMALIZED_STRING_TYPE_CODE = 39;
    public static final int NOTATION_TYPE_CODE = 36;
    public static final XDataType NotationType;
    public static final int POSITIVE_INTEGER_TYPE_CODE = 17;
    public static final int QNAME_TYPE_CODE = 32;
    public static final int SHORT_TYPE_CODE = 10;
    public static final int STRING_TYPE_CODE = 38;
    public static final int TIME_TYPE_CODE = 22;
    public static final int TOKEN_TYPE_CODE = 40;
    public static final int UNSIGNED_BYTE_TYPE_CODE = 16;
    public static final int UNSIGNED_INT_TYPE_CODE = 14;
    public static final int UNSIGNED_LONG_TYPE_CODE = 13;
    public static final int UNSIGNED_SHORT_TYPE_CODE = 15;
    public static final int UNTYPED_ATOMIC_TYPE_CODE = 37;
    public static final int UNTYPED_TYPE_CODE = 48;
    public static final int YEAR_MONTH_DURATION_TYPE_CODE = 29;
    public static final XDataType anyAtomicType;
    public static final XDataType anySimpleType;
    public static final XDataType anyURIType;
    public static final XDataType base64BinaryType;
    public static final XDataType booleanType;
    public static final XDataType dayTimeDurationType;
    public static final XDataType decimalType;
    public static final XDataType doubleType;
    public static final XDataType durationType;
    public static final XDataType floatType;
    public static final XDataType hexBinaryType;
    public static final XDataType stringStringType;
    public static final XDataType stringType;
    public static final XDataType untypedAtomicType;
    public static final XDataType untypedType;
    public static final XDataType yearMonthDurationType;
    XDataType baseType;
    Type implementationType;
    Object name;
    int typeCode;

    public XDataType(Object name, Type implementationType, int typeCode) {
        super(implementationType);
        this.name = name;
        if (name != null) {
            setName(name.toString());
        }
        this.implementationType = implementationType;
        this.typeCode = typeCode;
    }

    static {
        anySimpleType = new XDataType("anySimpleType", Type.objectType, ANY_SIMPLE_TYPE_CODE);
        anyAtomicType = new XDataType("anyAtomicType", Type.objectType, ANY_ATOMIC_TYPE_CODE);
        stringType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_STRING, ClassType.make("java.lang.CharSequence"), STRING_TYPE_CODE);
        stringStringType = new XDataType("String", ClassType.make("java.lang.String"), STRING_TYPE_CODE);
        untypedAtomicType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_STRING, ClassType.make("gnu.kawa.xml.UntypedAtomic"), UNTYPED_ATOMIC_TYPE_CODE);
        base64BinaryType = new XDataType("base64Binary", ClassType.make("gnu.kawa.xml.Base64Binary"), BASE64_BINARY_TYPE_CODE);
        hexBinaryType = new XDataType("hexBinary", ClassType.make("gnu.kawa.xml.HexBinary"), HEX_BINARY_TYPE_CODE);
        booleanType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, Type.booleanType, BOOLEAN_TYPE_CODE);
        anyURIType = new XDataType("anyURI", ClassType.make("gnu.text.Path"), ANY_URI_TYPE_CODE);
        NotationType = new XDataType("NOTATION", ClassType.make("gnu.kawa.xml.Notation"), NOTATION_TYPE_CODE);
        decimalType = new XDataType("decimal", ClassType.make("java.lang.Number"), DECIMAL_TYPE_CODE);
        floatType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_FLOAT, ClassType.make("java.lang.Float"), FLOAT_TYPE_CODE);
        doubleType = new XDataType("double", ClassType.make("java.lang.Double"), DOUBLE_TYPE_CODE);
        durationType = new XDataType("duration", ClassType.make("gnu.math.Duration"), DURATION_TYPE_CODE);
        yearMonthDurationType = new XDataType("yearMonthDuration", ClassType.make("gnu.math.Duration"), YEAR_MONTH_DURATION_TYPE_CODE);
        dayTimeDurationType = new XDataType("dayTimeDuration", ClassType.make("gnu.math.Duration"), DAY_TIME_DURATION_TYPE_CODE);
        untypedType = new XDataType("untyped", Type.objectType, UNTYPED_TYPE_CODE);
        DOUBLE_ZERO = makeDouble(0.0d);
        DOUBLE_ONE = makeDouble(1.0d);
        FLOAT_ZERO = makeFloat(0.0f);
        FLOAT_ONE = makeFloat(1.0f);
        DECIMAL_ONE = BigDecimal.valueOf(1);
    }

    public Class getReflectClass() {
        return this.implementationType.getReflectClass();
    }

    public Type getImplementationType() {
        return this.implementationType;
    }

    public void emitCoerceFromObject(CodeAttr code) {
        Compilation.getCurrent().compileConstant(this, Target.pushObject);
        Method meth = ClassType.make("gnu.kawa.xml.XDataType").getDeclaredMethod("coerceFromObject", 1);
        code.emitSwap();
        code.emitInvokeVirtual(meth);
        this.implementationType.emitCoerceFromObject(code);
    }

    public void emitCoerceToObject(CodeAttr code) {
        if (this.typeCode == BOOLEAN_TYPE_CODE) {
            this.implementationType.emitCoerceToObject(code);
        } else {
            super.emitCoerceToObject(code);
        }
    }

    public void emitTestIf(Variable incoming, Declaration decl, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (this.typeCode == BOOLEAN_TYPE_CODE) {
            if (incoming != null) {
                code.emitLoad(incoming);
            }
            Type.javalangBooleanType.emitIsInstance(code);
            code.emitIfIntNotZero();
            if (decl != null) {
                code.emitLoad(incoming);
                Type.booleanType.emitCoerceFromObject(code);
                decl.compileStore(comp);
                return;
            }
            return;
        }
        comp.compileConstant(this, Target.pushObject);
        if (incoming == null) {
            code.emitSwap();
        } else {
            code.emitLoad(incoming);
        }
        code.emitInvokeVirtual(Compilation.typeType.getDeclaredMethod("isInstance", 1));
        code.emitIfIntNotZero();
        if (decl != null) {
            code.emitLoad(incoming);
            emitCoerceFromObject(code);
            decl.compileStore(comp);
        }
    }

    public Expression convertValue(Expression value) {
        return null;
    }

    public boolean isInstance(Object obj) {
        boolean z = false;
        switch (this.typeCode) {
            case ANY_SIMPLE_TYPE_CODE /*2*/:
                if ((obj instanceof SeqPosition) || (obj instanceof Nodes)) {
                    return false;
                }
                return true;
            case ANY_ATOMIC_TYPE_CODE /*3*/:
                if ((obj instanceof Values) || (obj instanceof SeqPosition)) {
                    return false;
                }
                return true;
            case DECIMAL_TYPE_CODE /*4*/:
                if ((obj instanceof BigDecimal) || (obj instanceof IntNum)) {
                    z = true;
                }
                return z;
            case FLOAT_TYPE_CODE /*18*/:
                return obj instanceof Float;
            case DOUBLE_TYPE_CODE /*19*/:
                return obj instanceof Double;
            case DURATION_TYPE_CODE /*28*/:
                return obj instanceof Duration;
            case YEAR_MONTH_DURATION_TYPE_CODE /*29*/:
                if ((obj instanceof Duration) && ((Duration) obj).unit() == Unit.month) {
                    return true;
                }
                return false;
            case DAY_TIME_DURATION_TYPE_CODE /*30*/:
                return (obj instanceof Duration) && ((Duration) obj).unit() == Unit.second;
            case BOOLEAN_TYPE_CODE /*31*/:
                return obj instanceof Boolean;
            case ANY_URI_TYPE_CODE /*33*/:
                return obj instanceof Path;
            case UNTYPED_ATOMIC_TYPE_CODE /*37*/:
                return obj instanceof UntypedAtomic;
            case STRING_TYPE_CODE /*38*/:
                return obj instanceof CharSequence;
            case UNTYPED_TYPE_CODE /*48*/:
                return true;
            default:
                return super.isInstance(obj);
        }
    }

    public void emitIsInstance(Variable incoming, Compilation comp, Target target) {
        InstanceOf.emitIsInstance(this, incoming, comp, target);
    }

    public String toString(Object value) {
        return value.toString();
    }

    public void print(Object value, Consumer out) {
        if (value instanceof Printable) {
            ((Printable) value).print(out);
        } else {
            out.write(toString(value));
        }
    }

    public boolean castable(Object value) {
        try {
            cast(value);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public Object cast(Object value) {
        DateTime value2 = KNode.atomicValue(value);
        if (value2 instanceof UntypedAtomic) {
            if (this.typeCode == UNTYPED_ATOMIC_TYPE_CODE) {
                return value2;
            }
            return valueOf(value2.toString());
        } else if (value2 instanceof String) {
            return valueOf(value2.toString());
        } else {
            switch (this.typeCode) {
                case DECIMAL_TYPE_CODE /*4*/:
                    if (value2 instanceof BigDecimal) {
                        return value2;
                    }
                    if (value2 instanceof RealNum) {
                        return ((RealNum) value2).asBigDecimal();
                    }
                    if ((value2 instanceof Float) || (value2 instanceof Double)) {
                        return BigDecimal.valueOf(value2.doubleValue());
                    }
                    if (value2 instanceof Boolean) {
                        return cast(((Boolean) value2).booleanValue() ? IntNum.one() : IntNum.zero());
                    }
                    break;
                case FLOAT_TYPE_CODE /*18*/:
                    if (value2 instanceof Float) {
                        return value2;
                    }
                    if (value2 instanceof Number) {
                        return makeFloat(value2.floatValue());
                    }
                    if (value2 instanceof Boolean) {
                        return ((Boolean) value2).booleanValue() ? FLOAT_ONE : FLOAT_ZERO;
                    }
                    break;
                case DOUBLE_TYPE_CODE /*19*/:
                    if (value2 instanceof Double) {
                        return value2;
                    }
                    if (value2 instanceof Number) {
                        return makeDouble(value2.doubleValue());
                    }
                    if (value2 instanceof Boolean) {
                        return ((Boolean) value2).booleanValue() ? DOUBLE_ONE : DOUBLE_ZERO;
                    }
                    break;
                case DATE_TIME_TYPE_CODE /*20*/:
                case DATE_TYPE_CODE /*21*/:
                case TIME_TYPE_CODE /*22*/:
                    if (value2 instanceof DateTime) {
                        return value2.cast(XTimeType.components(((XTimeType) this).typeCode));
                    }
                    break;
                case G_YEAR_MONTH_TYPE_CODE /*23*/:
                case G_YEAR_TYPE_CODE /*24*/:
                case G_MONTH_DAY_TYPE_CODE /*25*/:
                case G_DAY_TYPE_CODE /*26*/:
                case G_MONTH_TYPE_CODE /*27*/:
                    if (value2 instanceof DateTime) {
                        int dstMask = XTimeType.components(((XTimeType) this).typeCode);
                        DateTime dt = value2;
                        int srcMask = dt.components();
                        if (dstMask == srcMask || (srcMask & UNSIGNED_INT_TYPE_CODE) == UNSIGNED_INT_TYPE_CODE) {
                            return dt.cast(dstMask);
                        }
                        throw new ClassCastException();
                    }
                    break;
                case DURATION_TYPE_CODE /*28*/:
                    return castToDuration(value2, Unit.duration);
                case YEAR_MONTH_DURATION_TYPE_CODE /*29*/:
                    return castToDuration(value2, Unit.month);
                case DAY_TIME_DURATION_TYPE_CODE /*30*/:
                    return castToDuration(value2, Unit.second);
                case BOOLEAN_TYPE_CODE /*31*/:
                    if (value2 instanceof Boolean) {
                        return ((Boolean) value2).booleanValue() ? Boolean.TRUE : Boolean.FALSE;
                    } else if (value2 instanceof Number) {
                        double d = value2.doubleValue();
                        Boolean bool = (d == 0.0d || Double.isNaN(d)) ? Boolean.FALSE : Boolean.TRUE;
                        return bool;
                    }
                    break;
                case ANY_URI_TYPE_CODE /*33*/:
                    return URIPath.makeURI(value2);
                case BASE64_BINARY_TYPE_CODE /*34*/:
                    if (value2 instanceof BinaryObject) {
                        return new Base64Binary(((BinaryObject) value2).getBytes());
                    }
                    break;
                case HEX_BINARY_TYPE_CODE /*35*/:
                    break;
                case UNTYPED_ATOMIC_TYPE_CODE /*37*/:
                    return new UntypedAtomic(TextUtils.stringValue(value2));
                case STRING_TYPE_CODE /*38*/:
                    return TextUtils.asString(value2);
            }
            if (value2 instanceof BinaryObject) {
                return new HexBinary(((BinaryObject) value2).getBytes());
            }
            return coerceFromObject(value2);
        }
    }

    Duration castToDuration(Object value, Unit unit) {
        if (!(value instanceof Duration)) {
            return (Duration) coerceFromObject(value);
        }
        Duration dur = (Duration) value;
        if (dur.unit() == unit) {
            return dur;
        }
        int months = dur.getTotalMonths();
        long seconds = dur.getTotalSeconds();
        int nanos = dur.getNanoSecondsOnly();
        if (unit == Unit.second) {
            months = 0;
        }
        if (unit == Unit.month) {
            seconds = 0;
            nanos = 0;
        }
        return Duration.make(months, seconds, nanos, unit);
    }

    public Object coerceFromObject(Object obj) {
        if (isInstance(obj)) {
            return obj;
        }
        throw new ClassCastException("cannot cast " + obj + " to " + this.name);
    }

    public int compare(Type other) {
        if (this == other || ((this == stringStringType && other == stringType) || (this == stringType && other == stringStringType))) {
            return 0;
        }
        return this.implementationType.compare(other);
    }

    public Object valueOf(String value) {
        switch (this.typeCode) {
            case DECIMAL_TYPE_CODE /*4*/:
                value = value.trim();
                int i = value.length();
                char ch;
                do {
                    i--;
                    if (i < 0) {
                        return new BigDecimal(value);
                    }
                    ch = value.charAt(i);
                    if (ch != 'e') {
                    }
                    throw new IllegalArgumentException("not a valid decimal: '" + value + "'");
                } while (ch != 'E');
                throw new IllegalArgumentException("not a valid decimal: '" + value + "'");
            case FLOAT_TYPE_CODE /*18*/:
            case DOUBLE_TYPE_CODE /*19*/:
                value = value.trim();
                if ("INF".equals(value)) {
                    value = "Infinity";
                } else if ("-INF".equals(value)) {
                    value = "-Infinity";
                }
                return this.typeCode == FLOAT_TYPE_CODE ? Float.valueOf(value) : Double.valueOf(value);
            case DURATION_TYPE_CODE /*28*/:
                return Duration.parseDuration(value);
            case YEAR_MONTH_DURATION_TYPE_CODE /*29*/:
                return Duration.parseYearMonthDuration(value);
            case DAY_TIME_DURATION_TYPE_CODE /*30*/:
                return Duration.parseDayTimeDuration(value);
            case BOOLEAN_TYPE_CODE /*31*/:
                value = value.trim();
                if (value.equals("true") || value.equals("1")) {
                    return Boolean.TRUE;
                }
                if (value.equals("false") || value.equals("0")) {
                    return Boolean.FALSE;
                }
                throw new IllegalArgumentException("not a valid boolean: '" + value + "'");
            case ANY_URI_TYPE_CODE /*33*/:
                return URIPath.makeURI(TextUtils.replaceWhitespace(value, true));
            case BASE64_BINARY_TYPE_CODE /*34*/:
                return Base64Binary.valueOf(value);
            case HEX_BINARY_TYPE_CODE /*35*/:
                return HexBinary.valueOf(value);
            case UNTYPED_ATOMIC_TYPE_CODE /*37*/:
                return new UntypedAtomic(value);
            case STRING_TYPE_CODE /*38*/:
                return value;
            default:
                throw new RuntimeException("valueOf not implemented for " + this.name);
        }
    }

    public static Float makeFloat(float value) {
        return Float.valueOf(value);
    }

    public static Double makeDouble(double value) {
        return Double.valueOf(value);
    }

    public Procedure getConstructor() {
        return null;
    }
}
