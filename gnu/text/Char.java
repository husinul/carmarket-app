package gnu.text;

import gnu.bytecode.Access;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.lists.Consumer;
import gnu.math.DateTime;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Char implements Comparable, Externalizable {
    static Char[] ascii;
    static char[] charNameValues;
    static String[] charNames;
    static CharMap hashTable;
    int value;

    Char(int ch) {
        this.value = ch;
    }

    public void print(Consumer out) {
        print(this.value, out);
    }

    public static void print(int i, Consumer out) {
        if (i >= ModuleExp.NONSTATIC_SPECIFIED) {
            out.write((char) (((i - ModuleExp.NONSTATIC_SPECIFIED) >> 10) + 55296));
            out.write((char) ((i & 1023) + 56320));
            return;
        }
        out.write((char) i);
    }

    public final char charValue() {
        return (char) this.value;
    }

    public final int intValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    static {
        hashTable = new CharMap();
        ascii = new Char[DateTime.TIMEZONE_MASK];
        int i = DateTime.TIMEZONE_MASK;
        while (true) {
            i--;
            if (i >= 0) {
                ascii[i] = new Char(i);
            } else {
                charNameValues = new char[]{' ', '\t', '\n', '\n', '\r', '\f', '\b', '\u001b', '\u007f', '\u007f', '\u007f', '\u0007', '\u0007', '\u000b', '\u0000'};
                charNames = new String[]{"space", "tab", "newline", "linefeed", "return", "page", "backspace", "esc", "delete", "del", "rubout", "alarm", "bel", "vtab", "nul"};
                return;
            }
        }
    }

    public static Char make(int ch) {
        if (ch < DateTime.TIMEZONE_MASK) {
            return ascii[ch];
        }
        Char charR;
        synchronized (hashTable) {
            charR = hashTable.get(ch);
        }
        return charR;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Char) && ((Char) obj).intValue() == this.value;
    }

    public static int nameToChar(String name) {
        int i = charNames.length;
        do {
            i--;
            if (i < 0) {
                i = charNames.length;
                do {
                    i--;
                    if (i < 0) {
                        int len = name.length();
                        if (len > 1 && name.charAt(0) == 'u') {
                            int value = 0;
                            int pos = 1;
                            while (pos != len) {
                                int dig = Character.digit(name.charAt(pos), 16);
                                if (dig >= 0) {
                                    value = (value << 4) + dig;
                                    pos++;
                                }
                            }
                            return value;
                        }
                        if (len == 3 && name.charAt(1) == '-') {
                            char ch = name.charAt(0);
                            if (ch == 'c' || ch == Access.CLASS_CONTEXT) {
                                return name.charAt(2) & 31;
                            }
                        }
                        return -1;
                    }
                } while (!charNames[i].equalsIgnoreCase(name));
                return charNameValues[i];
            }
        } while (!charNames[i].equals(name));
        return charNameValues[i];
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append('\'');
        if (this.value < 32 || this.value >= 127 || this.value == 39) {
            buf.append('\\');
            if (this.value == 39) {
                buf.append('\'');
            } else if (this.value == 10) {
                buf.append('n');
            } else if (this.value == 13) {
                buf.append('r');
            } else if (this.value == 9) {
                buf.append('t');
            } else if (this.value < LambdaExp.NO_FIELD) {
                str = Integer.toOctalString(this.value);
                i = 3 - str.length();
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    buf.append('0');
                }
                buf.append(str);
            } else {
                buf.append('u');
                str = Integer.toHexString(this.value);
                i = 4 - str.length();
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    buf.append('0');
                }
                buf.append(str);
            }
        } else {
            buf.append((char) this.value);
        }
        buf.append('\'');
        return buf.toString();
    }

    public static String toScmReadableString(int ch) {
        StringBuffer sbuf = new StringBuffer(20);
        sbuf.append("#\\");
        for (int i = 0; i < charNameValues.length; i++) {
            if (((char) ch) == charNameValues[i]) {
                sbuf.append(charNames[i]);
                return sbuf.toString();
            }
        }
        if (ch < 32 || ch > 127) {
            sbuf.append('x');
            sbuf.append(Integer.toString(ch, 16));
        } else {
            sbuf.append((char) ch);
        }
        return sbuf.toString();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        if (this.value > 55296) {
            if (this.value > 65535) {
                out.writeChar(((this.value - ModuleExp.NONSTATIC_SPECIFIED) >> 10) + 55296);
                this.value = (this.value & 1023) + 56320;
            } else if (this.value <= 56319) {
                out.writeChar(this.value);
                this.value = 0;
            }
        }
        out.writeChar(this.value);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.value = in.readChar();
        if (this.value >= 55296 && this.value < 56319) {
            char next = in.readChar();
            if (next >= '\udc00' && next <= '\udfff') {
                this.value = (((this.value - 55296) << 10) + (next - 56320)) + ModuleExp.NONSTATIC_SPECIFIED;
            }
        }
    }

    public Object readResolve() throws ObjectStreamException {
        return make(this.value);
    }

    public int compareTo(Object o) {
        return this.value - ((Char) o).value;
    }
}
