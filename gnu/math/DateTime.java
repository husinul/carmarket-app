package gnu.math;

import gnu.kawa.functions.ArithOp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTime extends Quantity implements Cloneable {
    public static final int DATE_MASK = 14;
    static final int DAY_COMPONENT = 3;
    public static final int DAY_MASK = 8;
    public static TimeZone GMT = null;
    static final int HOURS_COMPONENT = 4;
    public static final int HOURS_MASK = 16;
    static final int MINUTES_COMPONENT = 5;
    public static final int MINUTES_MASK = 32;
    static final int MONTH_COMPONENT = 2;
    public static final int MONTH_MASK = 4;
    static final int SECONDS_COMPONENT = 6;
    public static final int SECONDS_MASK = 64;
    static final int TIMEZONE_COMPONENT = 7;
    public static final int TIMEZONE_MASK = 128;
    public static final int TIME_MASK = 112;
    static final int YEAR_COMPONENT = 1;
    public static final int YEAR_MASK = 2;
    private static final Date minDate;
    GregorianCalendar calendar;
    int mask;
    int nanoSeconds;
    Unit unit;

    public int components() {
        return this.mask & -129;
    }

    public DateTime cast(int newComponents) {
        int oldComponents = this.mask & -129;
        if (newComponents == oldComponents) {
            return this;
        }
        DateTime copy = new DateTime(newComponents, (GregorianCalendar) this.calendar.clone());
        if (((oldComponents ^ -1) & newComponents) == 0 || (oldComponents == DATE_MASK && newComponents == 126)) {
            if (isZoneUnspecified()) {
                copy.mask &= -129;
            } else {
                copy.mask |= TIMEZONE_MASK;
            }
            int extraComponents = oldComponents & (newComponents ^ -1);
            if ((extraComponents & TIME_MASK) != 0) {
                copy.calendar.clear(11);
                copy.calendar.clear(12);
                copy.calendar.clear(13);
            } else {
                copy.nanoSeconds = this.nanoSeconds;
            }
            if ((extraComponents & YEAR_MASK) != 0) {
                copy.calendar.clear(YEAR_COMPONENT);
                copy.calendar.clear(0);
            }
            if ((extraComponents & MONTH_MASK) != 0) {
                copy.calendar.clear(YEAR_MASK);
            }
            if ((extraComponents & DAY_MASK) != 0) {
                copy.calendar.clear(MINUTES_COMPONENT);
            }
            return copy;
        }
        throw new ClassCastException("cannot cast DateTime - missing conponents");
    }

    static {
        minDate = new Date(Long.MIN_VALUE);
        GMT = TimeZone.getTimeZone("GMT");
    }

    public DateTime(int mask) {
        this.unit = Unit.date;
        this.calendar = new GregorianCalendar();
        this.calendar.setGregorianChange(minDate);
        this.calendar.clear();
        this.mask = mask;
    }

    public DateTime(int mask, GregorianCalendar calendar) {
        this.unit = Unit.date;
        this.calendar = calendar;
        this.mask = mask;
    }

    public static DateTime parse(String value, int mask) {
        boolean wantDate;
        boolean wantTime = true;
        DateTime result = new DateTime(mask);
        value = value.trim();
        int len = value.length();
        int pos = 0;
        if ((mask & DATE_MASK) != 0) {
            wantDate = true;
        } else {
            wantDate = false;
        }
        if ((mask & TIME_MASK) == 0) {
            wantTime = false;
        }
        if (wantDate) {
            pos = result.parseDate(value, 0, mask);
            if (wantTime) {
                if (pos < 0 || pos >= len || value.charAt(pos) != 'T') {
                    pos = -1;
                } else {
                    pos += YEAR_COMPONENT;
                }
            }
        }
        if (wantTime) {
            pos = result.parseTime(value, pos);
        }
        if (result.parseZone(value, pos) == len) {
            return result;
        }
        throw new NumberFormatException("Unrecognized date/time '" + value + '\'');
    }

    int parseDate(String str, int start, int mask) {
        if (start < 0) {
            return start;
        }
        int year;
        int len = str.length();
        boolean negYear = false;
        if (start < len && str.charAt(start) == '-') {
            start += YEAR_COMPONENT;
            negYear = true;
        }
        int pos = start;
        if ((mask & YEAR_MASK) != 0) {
            int part = parseDigits(str, pos);
            year = part >> HOURS_MASK;
            pos = part & 65535;
            if (pos != start + MONTH_MASK && (pos <= start + MONTH_MASK || str.charAt(start) == '0')) {
                return -1;
            }
            if (negYear || year == 0) {
                this.calendar.set(0, 0);
                this.calendar.set(YEAR_COMPONENT, year + YEAR_COMPONENT);
            } else {
                this.calendar.set(YEAR_COMPONENT, year);
            }
        } else if (!negYear) {
            return -1;
        } else {
            year = -1;
        }
        if ((mask & 12) == 0) {
            return pos;
        }
        if (pos >= len || str.charAt(pos) != '-') {
            return -1;
        }
        pos += YEAR_COMPONENT;
        start = pos;
        int month;
        if ((mask & MONTH_MASK) != 0) {
            part = parseDigits(str, start);
            month = part >> HOURS_MASK;
            pos = part & 65535;
            if (month <= 0 || month > 12 || pos != start + YEAR_MASK) {
                return -1;
            }
            this.calendar.set(YEAR_MASK, month - 1);
            if ((mask & DAY_MASK) == 0) {
                return pos;
            }
        }
        month = -1;
        if (pos >= len || str.charAt(pos) != '-') {
            return -1;
        }
        start = pos + YEAR_COMPONENT;
        part = parseDigits(str, start);
        int day = part >> HOURS_MASK;
        pos = part & 65535;
        if (day > 0 && pos == start + YEAR_MASK) {
            int maxDay;
            if ((mask & MONTH_MASK) == 0) {
                maxDay = 31;
            } else {
                int i = month - 1;
                if ((mask & YEAR_MASK) == 0) {
                    year = 2000;
                }
                maxDay = daysInMonth(i, year);
            }
            if (day <= maxDay) {
                this.calendar.set(MINUTES_COMPONENT, day);
                return pos;
            }
        }
        return -1;
    }

    public static boolean isLeapYear(int year) {
        return year % MONTH_MASK == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static int daysInMonth(int month, int year) {
        switch (month) {
            case YEAR_COMPONENT /*1*/:
                return isLeapYear(year) ? 29 : 28;
            case DAY_COMPONENT /*3*/:
            case MINUTES_COMPONENT /*5*/:
            case DAY_MASK /*8*/:
            case ArithOp.ASHIFT_LEFT /*10*/:
                return 30;
            default:
                return 31;
        }
    }

    int parseZone(String str, int start) {
        if (start < 0) {
            return start;
        }
        int part = parseZoneMinutes(str, start);
        if (part == 0) {
            return -1;
        }
        if (part == start) {
            return start;
        }
        TimeZone zone;
        int pos = part & 65535;
        if ((part >> HOURS_MASK) == 0) {
            zone = GMT;
        } else {
            zone = TimeZone.getTimeZone("GMT" + str.substring(start, pos));
        }
        this.calendar.setTimeZone(zone);
        this.mask |= TIMEZONE_MASK;
        return pos;
    }

    int parseZoneMinutes(String str, int start) {
        int len = str.length();
        if (start == len || start < 0) {
            return start;
        }
        char ch = str.charAt(start);
        if (ch == 'Z') {
            return start + YEAR_COMPONENT;
        }
        if (ch != '+' && ch != '-') {
            return start;
        }
        start += YEAR_COMPONENT;
        int part = parseDigits(str, start);
        int hour = part >> HOURS_MASK;
        if (hour > DATE_MASK) {
            return 0;
        }
        int minute = hour * 60;
        int pos = part & 65535;
        if (pos != start + YEAR_MASK || pos >= len) {
            return 0;
        }
        if (str.charAt(pos) == ':') {
            start = pos + YEAR_COMPONENT;
            part = parseDigits(str, start);
            pos = part & 65535;
            part >>= HOURS_MASK;
            if (part > 0 && (part >= 60 || hour == DATE_MASK)) {
                return 0;
            }
            minute += part;
            if (pos != start + YEAR_MASK) {
                return 0;
            }
        }
        if (minute > 840) {
            return 0;
        }
        if (ch == '-') {
            minute = -minute;
        }
        return (minute << HOURS_MASK) | pos;
    }

    int parseTime(String str, int start) {
        if (start < 0) {
            return start;
        }
        int len = str.length();
        int pos = start;
        int part = parseDigits(str, start);
        int hour = part >> HOURS_MASK;
        pos = part & 65535;
        if (hour <= 24 && pos == start + YEAR_MASK && pos != len && str.charAt(pos) == ':') {
            start = pos + YEAR_COMPONENT;
            part = parseDigits(str, start);
            int minute = part >> HOURS_MASK;
            pos = part & 65535;
            if (minute < 60 && pos == start + YEAR_MASK && pos != len && str.charAt(pos) == ':') {
                start = pos + YEAR_COMPONENT;
                part = parseDigits(str, start);
                int second = part >> HOURS_MASK;
                pos = part & 65535;
                if (second < 60 && pos == start + YEAR_MASK) {
                    if (pos + YEAR_COMPONENT < len && str.charAt(pos) == '.' && Character.digit(str.charAt(pos + YEAR_COMPONENT), 10) >= 0) {
                        int i;
                        pos += YEAR_COMPONENT;
                        int nanos = 0;
                        int nfrac = 0;
                        while (pos < len) {
                            int dig = Character.digit(str.charAt(pos), 10);
                            if (dig < 0) {
                                i = nfrac;
                                break;
                            }
                            if (nfrac < 9) {
                                nanos = (nanos * 10) + dig;
                            } else if (nfrac == 9 && dig >= MINUTES_COMPONENT) {
                                nanos += YEAR_COMPONENT;
                            }
                            nfrac += YEAR_COMPONENT;
                            pos += YEAR_COMPONENT;
                        }
                        i = nfrac;
                        while (true) {
                            nfrac = i + YEAR_COMPONENT;
                            if (i >= 9) {
                                break;
                            }
                            nanos *= 10;
                            i = nfrac;
                        }
                        this.nanoSeconds = nanos;
                    }
                    if (hour == 24 && (minute != 0 || second != 0 || this.nanoSeconds != 0)) {
                        return -1;
                    }
                    this.calendar.set(11, hour);
                    this.calendar.set(12, minute);
                    this.calendar.set(13, second);
                    return pos;
                }
            }
        }
        return -1;
    }

    private static int parseDigits(String str, int start) {
        int i = start;
        int val = -1;
        int len = str.length();
        while (i < len) {
            int dig = Character.digit(str.charAt(i), 10);
            if (dig < 0) {
                break;
            } else if (val > 20000) {
                return 0;
            } else {
                val = val < 0 ? dig : (val * 10) + dig;
                i += YEAR_COMPONENT;
            }
        }
        return val < 0 ? i : i | (val << HOURS_MASK);
    }

    public int getYear() {
        int year = this.calendar.get(YEAR_COMPONENT);
        if (this.calendar.get(0) == 0) {
            return 1 - year;
        }
        return year;
    }

    public int getMonth() {
        return this.calendar.get(YEAR_MASK) + YEAR_COMPONENT;
    }

    public int getDay() {
        return this.calendar.get(MINUTES_COMPONENT);
    }

    public int getHours() {
        return this.calendar.get(11);
    }

    public int getMinutes() {
        return this.calendar.get(12);
    }

    public int getSecondsOnly() {
        return this.calendar.get(13);
    }

    public int getWholeSeconds() {
        return this.calendar.get(13);
    }

    public int getNanoSecondsOnly() {
        return this.nanoSeconds;
    }

    public static int compare(DateTime date1, DateTime date2) {
        long millis1 = date1.calendar.getTimeInMillis();
        long millis2 = date2.calendar.getTimeInMillis();
        if (((date1.mask | date2.mask) & DATE_MASK) == 0) {
            if (millis1 < 0) {
                millis1 += 86400000;
            }
            if (millis2 < 0) {
                millis2 += 86400000;
            }
        }
        int nanos1 = date1.nanoSeconds;
        int nanos2 = date2.nanoSeconds;
        millis1 += (long) (nanos1 / 1000000);
        millis2 += (long) (nanos2 / 1000000);
        nanos1 %= 1000000;
        nanos2 %= 1000000;
        if (millis1 < millis2) {
            return -1;
        }
        if (millis1 > millis2) {
            return YEAR_COMPONENT;
        }
        if (nanos1 < nanos2) {
            return -1;
        }
        return nanos1 > nanos2 ? YEAR_COMPONENT : 0;
    }

    public int compare(Object obj) {
        if (obj instanceof DateTime) {
            return compare(this, (DateTime) obj);
        }
        return ((Numeric) obj).compareReversed(this);
    }

    public static Duration sub(DateTime date1, DateTime date2) {
        long millis1 = date1.calendar.getTimeInMillis();
        long millis2 = date2.calendar.getTimeInMillis();
        int nanos1 = date1.nanoSeconds;
        int nanos2 = date2.nanoSeconds;
        nanos1 %= 1000000;
        nanos2 %= 1000000;
        long millis = (millis1 + ((long) (nanos1 / 1000000))) - (millis2 + ((long) (nanos2 / 1000000)));
        long j = (long) nanos2;
        int nanos = (int) ((((millis % 1000) * 1000000) + ((long) nanos2)) - r0);
        return Duration.make(0, (millis / 1000) + ((long) (nanos / 1000000000)), nanos % 1000000000, Unit.second);
    }

    public DateTime withZoneUnspecified() {
        if (isZoneUnspecified()) {
            return this;
        }
        DateTime r = new DateTime(this.mask, (GregorianCalendar) this.calendar.clone());
        r.calendar.setTimeZone(TimeZone.getDefault());
        r.mask &= -129;
        return r;
    }

    public DateTime adjustTimezone(int newOffset) {
        TimeZone zone;
        DateTime r = new DateTime(this.mask, (GregorianCalendar) this.calendar.clone());
        if (newOffset == 0) {
            zone = GMT;
        } else {
            StringBuffer sbuf = new StringBuffer("GMT");
            toStringZone(newOffset, sbuf);
            zone = TimeZone.getTimeZone(sbuf.toString());
        }
        r.calendar.setTimeZone(zone);
        if ((r.mask & TIMEZONE_MASK) != 0) {
            r.calendar.setTimeInMillis(this.calendar.getTimeInMillis());
            if ((this.mask & TIME_MASK) == 0) {
                r.calendar.set(11, 0);
                r.calendar.set(12, 0);
                r.calendar.set(13, 0);
                r.nanoSeconds = 0;
            }
        } else {
            r.mask |= TIMEZONE_MASK;
        }
        return r;
    }

    public static DateTime add(DateTime x, Duration y, int k) {
        if (y.unit == Unit.duration || (y.unit == Unit.month && (x.mask & DATE_MASK) != DATE_MASK)) {
            throw new IllegalArgumentException("invalid date/time +/- duration combinatuion");
        }
        int i = x.mask;
        DateTime r = new DateTime(r16, (GregorianCalendar) x.calendar.clone());
        if (y.months != 0) {
            int year;
            int daysInMonth;
            int month = ((r.getYear() * 12) + r.calendar.get(YEAR_MASK)) + (y.months * k);
            int day = r.calendar.get(MINUTES_COMPONENT);
            if (month >= 12) {
                year = month / 12;
                month %= 12;
                r.calendar.set(0, YEAR_COMPONENT);
                daysInMonth = daysInMonth(month, year);
            } else {
                month = 11 - month;
                r.calendar.set(0, 0);
                year = (month / 12) + YEAR_COMPONENT;
                month = 11 - (month % 12);
                daysInMonth = daysInMonth(month, YEAR_COMPONENT);
            }
            if (day > daysInMonth) {
                day = daysInMonth;
            }
            r.calendar.set(year, month, day);
        }
        long j = (long) y.nanos;
        long nanos = ((long) x.nanoSeconds) + (((long) k) * ((y.seconds * 1000000000) + r0));
        if (nanos != 0) {
            if ((x.mask & TIME_MASK) == 0) {
                long mod = nanos % 86400000000000L;
                if (mod < 0) {
                    mod += 86400000000000L;
                }
                nanos -= mod;
            }
            r.calendar.setTimeInMillis(r.calendar.getTimeInMillis() + ((nanos / 1000000000) * 1000));
            r.nanoSeconds = (int) (nanos % 1000000000);
        }
        return r;
    }

    public static DateTime addMinutes(DateTime x, int y) {
        return addSeconds(x, y * 60);
    }

    public static DateTime addSeconds(DateTime x, int y) {
        DateTime r = new DateTime(x.mask, (GregorianCalendar) x.calendar.clone());
        long nanos = ((long) y) * 1000000000;
        if (nanos != 0) {
            nanos += (long) x.nanoSeconds;
            r.calendar.setTimeInMillis(x.calendar.getTimeInMillis() + (nanos / 1000000));
            r.nanoSeconds = (int) (nanos % 1000000);
        }
        return r;
    }

    public Numeric add(Object y, int k) {
        if (y instanceof Duration) {
            return add(this, (Duration) y, k);
        }
        if ((y instanceof DateTime) && k == -1) {
            return sub(this, (DateTime) y);
        }
        throw new IllegalArgumentException();
    }

    public Numeric addReversed(Numeric x, int k) {
        if ((x instanceof Duration) && k == YEAR_COMPONENT) {
            return add(this, (Duration) x, k);
        }
        throw new IllegalArgumentException();
    }

    private static void append(int value, StringBuffer sbuf, int minWidth) {
        int start = sbuf.length();
        sbuf.append(value);
        int padding = (start + minWidth) - sbuf.length();
        while (true) {
            padding--;
            if (padding >= 0) {
                sbuf.insert(start, '0');
            } else {
                return;
            }
        }
    }

    public void toStringDate(StringBuffer sbuf) {
        int mask = components();
        if ((mask & YEAR_MASK) != 0) {
            int year = this.calendar.get(YEAR_COMPONENT);
            if (this.calendar.get(0) == 0) {
                year--;
                if (year != 0) {
                    sbuf.append('-');
                }
            }
            append(year, sbuf, MONTH_MASK);
        } else {
            sbuf.append('-');
        }
        if ((mask & 12) != 0) {
            sbuf.append('-');
            if ((mask & MONTH_MASK) != 0) {
                append(getMonth(), sbuf, YEAR_MASK);
            }
            if ((mask & DAY_MASK) != 0) {
                sbuf.append('-');
                append(getDay(), sbuf, YEAR_MASK);
            }
        }
    }

    public void toStringTime(StringBuffer sbuf) {
        append(getHours(), sbuf, YEAR_MASK);
        sbuf.append(':');
        append(getMinutes(), sbuf, YEAR_MASK);
        sbuf.append(':');
        append(getWholeSeconds(), sbuf, YEAR_MASK);
        Duration.appendNanoSeconds(this.nanoSeconds, sbuf);
    }

    public boolean isZoneUnspecified() {
        return (this.mask & TIMEZONE_MASK) == 0;
    }

    public int getZoneMinutes() {
        return this.calendar.getTimeZone().getRawOffset() / 60000;
    }

    public static TimeZone minutesToTimeZone(int minutes) {
        if (minutes == 0) {
            return GMT;
        }
        StringBuffer sbuf = new StringBuffer("GMT");
        toStringZone(minutes, sbuf);
        return TimeZone.getTimeZone(sbuf.toString());
    }

    public void setTimeZone(TimeZone timeZone) {
        this.calendar.setTimeZone(timeZone);
    }

    public void toStringZone(StringBuffer sbuf) {
        if (!isZoneUnspecified()) {
            toStringZone(getZoneMinutes(), sbuf);
        }
    }

    public static void toStringZone(int minutes, StringBuffer sbuf) {
        if (minutes == 0) {
            sbuf.append('Z');
            return;
        }
        if (minutes < 0) {
            sbuf.append('-');
            minutes = -minutes;
        } else {
            sbuf.append('+');
        }
        append(minutes / 60, sbuf, YEAR_MASK);
        sbuf.append(':');
        append(minutes % 60, sbuf, YEAR_MASK);
    }

    public void toString(StringBuffer sbuf) {
        boolean hasDate;
        boolean hasTime = true;
        int mask = components();
        if ((mask & DATE_MASK) != 0) {
            hasDate = true;
        } else {
            hasDate = false;
        }
        if ((mask & TIME_MASK) == 0) {
            hasTime = false;
        }
        if (hasDate) {
            toStringDate(sbuf);
            if (hasTime) {
                sbuf.append('T');
            }
        }
        if (hasTime) {
            toStringTime(sbuf);
        }
        toStringZone(sbuf);
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        toString(sbuf);
        return sbuf.toString();
    }

    public boolean isExact() {
        return (this.mask & TIME_MASK) == 0;
    }

    public boolean isZero() {
        throw new Error("DateTime.isZero not meaningful!");
    }

    public Unit unit() {
        return this.unit;
    }

    public Complex number() {
        throw new Error("number needs to be implemented!");
    }
}
