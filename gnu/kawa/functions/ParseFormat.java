package gnu.kawa.functions;

import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.expr.Compilation;
import gnu.expr.SetExp;
import gnu.kawa.xml.XDataType;
import gnu.lists.FString;
import gnu.lists.Sequence;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.InPort;
import gnu.mapping.Procedure1;
import gnu.text.CompoundFormat;
import gnu.text.LineBufferedReader;
import gnu.text.LiteralFormat;
import gnu.text.PadFormat;
import gnu.text.PrettyWriter;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.util.Vector;

public class ParseFormat extends Procedure1 {
    public static final int PARAM_FROM_LIST = -1610612736;
    public static final int PARAM_UNSPECIFIED = -1073741824;
    public static final int SEEN_HASH = 16;
    public static final int SEEN_MINUS = 1;
    public static final int SEEN_PLUS = 2;
    public static final int SEEN_SPACE = 4;
    public static final int SEEN_ZERO = 8;
    public static final ParseFormat parseFormat;
    boolean emacsStyle;

    static {
        parseFormat = new ParseFormat(false);
    }

    public ParseFormat(boolean emacsStyle) {
        this.emacsStyle = true;
        this.emacsStyle = emacsStyle;
    }

    public ReportFormat parseFormat(LineBufferedReader fmt) throws ParseException, IOException {
        return parseFormat(fmt, this.emacsStyle ? '?' : '~');
    }

    public static ReportFormat parseFormat(LineBufferedReader fmt, char magic) throws ParseException, IOException {
        StringBuffer stringBuffer = new StringBuffer(100);
        int position = 0;
        Vector formats = new Vector();
        while (true) {
            int ch = fmt.read();
            if (ch >= 0) {
                if (ch != magic) {
                    stringBuffer.append((char) ch);
                } else {
                    ch = fmt.read();
                    if (ch == magic) {
                        stringBuffer.append((char) ch);
                    }
                }
            }
            int len = stringBuffer.length();
            if (len > 0) {
                char[] text = new char[len];
                stringBuffer.getChars(0, len, text, 0);
                stringBuffer.setLength(0);
                formats.addElement(new LiteralFormat(text));
            }
            if (ch < 0) {
                int fcount = formats.size();
                if (fcount == SEEN_MINUS) {
                    Object f = formats.elementAt(0);
                    if (f instanceof ReportFormat) {
                        return (ReportFormat) f;
                    }
                }
                Format[] farray = new Format[fcount];
                formats.copyInto(farray);
                return new CompoundFormat(farray);
            }
            int digit;
            if (ch == 36) {
                position = Character.digit((char) fmt.read(), 10);
                if (position < 0) {
                    throw new ParseException("missing number (position) after '%$'", -1);
                }
                while (true) {
                    ch = fmt.read();
                    digit = Character.digit((char) ch, 10);
                    if (digit < 0) {
                        position--;
                    } else {
                        position = (position * 10) + digit;
                    }
                }
            }
            int flags = 0;
            while (true) {
                switch ((char) ch) {
                    case SetExp.SET_IF_UNBOUND /*32*/:
                        flags |= SEEN_SPACE;
                        break;
                    case Sequence.ATTRIBUTE_VALUE /*35*/:
                        flags |= SEEN_HASH;
                        break;
                    case XDataType.NAME_TYPE_CODE /*43*/:
                        flags |= SEEN_PLUS;
                        break;
                    case XDataType.ID_TYPE_CODE /*45*/:
                        flags |= SEEN_MINUS;
                        break;
                    case XDataType.UNTYPED_TYPE_CODE /*48*/:
                        flags |= SEEN_ZERO;
                        break;
                    default:
                        Format format;
                        char padChar;
                        Format format2;
                        int width = PARAM_UNSPECIFIED;
                        digit = Character.digit((char) ch, 10);
                        if (digit >= 0) {
                            width = digit;
                            while (true) {
                                ch = fmt.read();
                                digit = Character.digit((char) ch, 10);
                                if (digit >= 0) {
                                    width = (width * 10) + digit;
                                }
                            }
                        } else if (ch == 42) {
                            width = PARAM_FROM_LIST;
                        }
                        int precision = PARAM_UNSPECIFIED;
                        if (ch == 46) {
                            if (ch == 42) {
                                precision = PARAM_FROM_LIST;
                            } else {
                                precision = 0;
                                while (true) {
                                    ch = fmt.read();
                                    digit = Character.digit((char) ch, 10);
                                    if (digit >= 0) {
                                        precision = (precision * 10) + digit;
                                    }
                                }
                            }
                        }
                        switch (ch) {
                            case PrettyWriter.NEWLINE_SPACE /*83*/:
                            case 115:
                                format = new ObjectFormat(ch == 83, precision);
                                break;
                            case 88:
                            case Compilation.ERROR_SEEN /*100*/:
                            case 105:
                            case 111:
                            case 120:
                                int base;
                                int fflags = 0;
                                if (ch == 100 || ch == 105) {
                                    base = 10;
                                } else if (ch == 111) {
                                    base = SEEN_ZERO;
                                } else {
                                    base = SEEN_HASH;
                                    if (ch == 88) {
                                        fflags = 32;
                                    }
                                }
                                padChar = (flags & 9) == SEEN_ZERO ? '0' : ' ';
                                if ((flags & SEEN_HASH) != 0) {
                                    fflags |= SEEN_ZERO;
                                }
                                if ((flags & SEEN_PLUS) != 0) {
                                    fflags |= SEEN_PLUS;
                                }
                                if ((flags & SEEN_MINUS) != 0) {
                                    fflags |= SEEN_HASH;
                                }
                                if ((flags & SEEN_SPACE) != 0) {
                                    fflags |= SEEN_SPACE;
                                }
                                if (precision == PARAM_UNSPECIFIED) {
                                    format = IntegerFormat.getInstance(base, width, padChar, PARAM_UNSPECIFIED, PARAM_UNSPECIFIED, fflags);
                                    break;
                                }
                                flags &= -9;
                                format = IntegerFormat.getInstance(base, precision, 48, PARAM_UNSPECIFIED, PARAM_UNSPECIFIED, fflags | 64);
                                break;
                                break;
                            case ErrorMessages.ERROR_LOCATION_SENSOR_LATITUDE_NOT_FOUND /*101*/:
                            case ErrorMessages.ERROR_LOCATION_SENSOR_LONGITUDE_NOT_FOUND /*102*/:
                            case 103:
                                format = new ObjectFormat(false);
                                break;
                            default:
                                throw new ParseException("unknown format character '" + ch + "'", -1);
                        }
                        if (width > 0) {
                            int where;
                            padChar = (flags & SEEN_ZERO) != 0 ? '0' : ' ';
                            if ((flags & SEEN_MINUS) != 0) {
                                where = 100;
                            } else if (padChar == '0') {
                                where = -1;
                            } else {
                                where = 0;
                            }
                            Format padFormat = new PadFormat(format, width, padChar, where);
                        } else {
                            format2 = format;
                        }
                        formats.addElement(format2);
                        position += SEEN_MINUS;
                        continue;
                }
                ch = fmt.read();
            }
        }
    }

    public Object apply1(Object arg) {
        return asFormat(arg, this.emacsStyle ? '?' : '~');
    }

    public static ReportFormat asFormat(Object arg, char style) {
        InPort iport;
        try {
            if (arg instanceof ReportFormat) {
                return (ReportFormat) arg;
            }
            if (style == '~') {
                return new LispFormat(arg.toString());
            }
            if (arg instanceof FString) {
                FString str = (FString) arg;
                iport = new CharArrayInPort(str.data, str.size);
            } else {
                iport = new CharArrayInPort(arg.toString());
            }
            arg = parseFormat(iport, style);
            iport.close();
            return arg;
        } catch (IOException ex) {
            throw new RuntimeException("Error parsing format (" + ex + ")");
        } catch (ParseException ex2) {
            throw new RuntimeException("Invalid format (" + ex2 + ")");
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("End while parsing format");
        } catch (Throwable th) {
            iport.close();
        }
    }
}
