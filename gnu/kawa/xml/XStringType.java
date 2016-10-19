package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.util.regex.Pattern;

public class XStringType extends XDataType {
    public static final XStringType ENTITYType;
    public static final XStringType IDREFType;
    public static final XStringType IDType;
    public static final XStringType NCNameType;
    public static final XStringType NMTOKENType;
    public static final XStringType NameType;
    static ClassType XStringType;
    public static final XStringType languageType;
    public static final XStringType normalizedStringType;
    public static final XStringType tokenType;
    Pattern pattern;

    static {
        XStringType = ClassType.make("gnu.kawa.xml.XString");
        normalizedStringType = new XStringType("normalizedString", stringType, 39, null);
        tokenType = new XStringType("token", normalizedStringType, 40, null);
        languageType = new XStringType("language", tokenType, 41, "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*");
        NMTOKENType = new XStringType("NMTOKEN", tokenType, 42, "\\c+");
        NameType = new XStringType("Name", tokenType, 43, null);
        NCNameType = new XStringType("NCName", NameType, 44, null);
        IDType = new XStringType("ID", NCNameType, 45, null);
        IDREFType = new XStringType("IDREF", NCNameType, 46, null);
        ENTITYType = new XStringType("ENTITY", NCNameType, 47, null);
    }

    public XStringType(String name, XDataType base, int typeCode, String pattern) {
        super(name, XStringType, typeCode);
        this.baseType = base;
        if (pattern != null) {
            this.pattern = Pattern.compile(pattern);
        }
    }

    public boolean isInstance(Object obj) {
        if (!(obj instanceof XString)) {
            return false;
        }
        for (XDataType objType = ((XString) obj).getStringType(); objType != null; objType = objType.baseType) {
            if (objType == this) {
                return true;
            }
        }
        return false;
    }

    public String matches(String value) {
        boolean status;
        switch (this.typeCode) {
            case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
            case XDataType.TOKEN_TYPE_CODE /*40*/:
                boolean collapse;
                if (this.typeCode != 39) {
                    collapse = true;
                } else {
                    collapse = false;
                }
                if (value == TextUtils.replaceWhitespace(value, collapse)) {
                    status = true;
                } else {
                    status = false;
                }
                break;
            case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                status = XName.isNmToken(value);
                break;
            case XDataType.NAME_TYPE_CODE /*43*/:
                status = XName.isName(value);
                break;
            case XDataType.NCNAME_TYPE_CODE /*44*/:
            case XDataType.ID_TYPE_CODE /*45*/:
            case XDataType.IDREF_TYPE_CODE /*46*/:
            case XDataType.ENTITY_TYPE_CODE /*47*/:
                status = XName.isNCName(value);
                break;
            default:
                if (this.pattern != null && !this.pattern.matcher(value).matches()) {
                    status = false;
                    break;
                }
                status = true;
                break;
                break;
        }
        if (status) {
            return null;
        }
        return "not a valid XML " + getName();
    }

    public Object valueOf(String value) {
        value = TextUtils.replaceWhitespace(value, this != normalizedStringType);
        if (matches(value) == null) {
            return new XString(value, this);
        }
        throw new ClassCastException("cannot cast " + value + " to " + this.name);
    }

    public Object cast(Object value) {
        if (value instanceof XString) {
            XString xvalue = (XString) value;
            if (xvalue.getStringType() == this) {
                return xvalue;
            }
        }
        return valueOf((String) stringType.cast(value));
    }

    public static XString makeNCName(String value) {
        return (XString) NCNameType.valueOf(value);
    }
}
