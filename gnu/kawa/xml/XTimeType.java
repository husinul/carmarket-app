package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.lists.Sequence;
import gnu.math.DateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class XTimeType extends XDataType {
    public static final XTimeType dateTimeType;
    public static final XTimeType dateType;
    private static TimeZone fixedTimeZone;
    public static final XTimeType gDayType;
    public static final XTimeType gMonthDayType;
    public static final XTimeType gMonthType;
    public static final XTimeType gYearMonthType;
    public static final XTimeType gYearType;
    public static final XTimeType timeType;
    static ClassType typeDateTime;

    static {
        typeDateTime = ClassType.make("gnu.math.DateTime");
        dateTimeType = new XTimeType("dateTime", 20);
        dateType = new XTimeType("date", 21);
        timeType = new XTimeType("time", 22);
        gYearMonthType = new XTimeType("gYearMonth", 23);
        gYearType = new XTimeType("gYear", 24);
        gMonthType = new XTimeType("gMonth", 27);
        gMonthDayType = new XTimeType("gMonthDay", 25);
        gDayType = new XTimeType("gDay", 26);
    }

    XTimeType(String name, int code) {
        super(name, typeDateTime, code);
    }

    static int components(int typeCode) {
        switch (typeCode) {
            case Sequence.INT_S16_VALUE /*20*/:
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                return 126;
            case Sequence.INT_U32_VALUE /*21*/:
                return 14;
            case Sequence.INT_S32_VALUE /*22*/:
                return DateTime.TIME_MASK;
            case Sequence.INT_U64_VALUE /*23*/:
                return 6;
            case Sequence.INT_S64_VALUE /*24*/:
                return 2;
            case Sequence.FLOAT_VALUE /*25*/:
                return 12;
            case Sequence.DOUBLE_VALUE /*26*/:
                return 8;
            case Sequence.BOOLEAN_VALUE /*27*/:
                return 4;
            case Sequence.CHAR_VALUE /*29*/:
                return 6;
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                return 120;
            default:
                return 0;
        }
    }

    public DateTime now() {
        return new DateTime(components(this.typeCode) | DateTime.TIMEZONE_MASK, (GregorianCalendar) Calendar.getInstance(fixedTimeZone()));
    }

    private static synchronized TimeZone fixedTimeZone() {
        TimeZone timeZone;
        synchronized (XTimeType.class) {
            if (fixedTimeZone == null) {
                fixedTimeZone = DateTime.minutesToTimeZone(TimeZone.getDefault().getRawOffset() / 60000);
            }
            timeZone = fixedTimeZone;
        }
        return timeZone;
    }

    public static DateTime parseDateTime(String value, int mask) {
        DateTime time = DateTime.parse(value, mask);
        if (time.isZoneUnspecified()) {
            time.setTimeZone(fixedTimeZone());
        }
        return time;
    }

    public Object valueOf(String value) {
        return parseDateTime(value, components(this.typeCode));
    }

    public boolean isInstance(Object obj) {
        if ((obj instanceof DateTime) && components(this.typeCode) == ((DateTime) obj).components()) {
            return true;
        }
        return false;
    }
}
