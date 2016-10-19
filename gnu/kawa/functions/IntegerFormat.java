package gnu.kawa.functions;

import gnu.math.RealNum;
import gnu.text.EnglishIntegerFormat;
import gnu.text.RomanIntegerFormat;
import java.text.Format;

public class IntegerFormat extends gnu.text.IntegerFormat {
    private static IntegerFormat plainDecimalFormat;

    public static IntegerFormat getInstance() {
        if (plainDecimalFormat == null) {
            plainDecimalFormat = new IntegerFormat();
        }
        return plainDecimalFormat;
    }

    public static Format getInstance(int base, int minWidth, int padChar, int commaChar, int commaInterval, int flags) {
        boolean seenColon = true;
        if (base == ParseFormat.PARAM_UNSPECIFIED) {
            if (padChar == ParseFormat.PARAM_UNSPECIFIED && padChar == ParseFormat.PARAM_UNSPECIFIED && commaChar == ParseFormat.PARAM_UNSPECIFIED && commaInterval == ParseFormat.PARAM_UNSPECIFIED) {
                if ((flags & 1) == 0) {
                    seenColon = false;
                }
                if ((flags & 2) != 0) {
                    return RomanIntegerFormat.getInstance(seenColon);
                }
                return EnglishIntegerFormat.getInstance(seenColon);
            }
            base = 10;
        }
        if (minWidth == ParseFormat.PARAM_UNSPECIFIED) {
            minWidth = 1;
        }
        if (padChar == ParseFormat.PARAM_UNSPECIFIED) {
            padChar = 32;
        }
        if (commaChar == ParseFormat.PARAM_UNSPECIFIED) {
            commaChar = 44;
        }
        if (commaInterval == ParseFormat.PARAM_UNSPECIFIED) {
            commaInterval = 3;
        }
        if (base == 10 && minWidth == 1 && padChar == 32 && commaChar == 44 && commaInterval == 3 && flags == 0) {
            return getInstance();
        }
        Format fmt = new IntegerFormat();
        fmt.base = base;
        fmt.minWidth = minWidth;
        fmt.padChar = padChar;
        fmt.commaChar = commaChar;
        fmt.commaInterval = commaInterval;
        fmt.flags = flags;
        return fmt;
    }

    public String convertToIntegerString(Object arg, int radix) {
        if (arg instanceof RealNum) {
            return ((RealNum) arg).toExactInt(4).toString(radix);
        }
        return super.convertToIntegerString(arg, radix);
    }
}
