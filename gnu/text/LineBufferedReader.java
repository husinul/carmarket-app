package gnu.text;

import gnu.bytecode.Access;
import gnu.kawa.xml.ElementType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LineBufferedReader extends Reader {
    public static final int BUFFER_SIZE = 8192;
    private static final int CONVERT_CR = 1;
    private static final int DONT_KEEP_FULL_LINES = 8;
    private static final int PREV_WAS_CR = 4;
    private static final int USER_BUFFER = 2;
    public char[] buffer;
    private int flags;
    int highestPos;
    protected Reader in;
    public int limit;
    protected int lineNumber;
    private int lineStartPos;
    protected int markPos;
    Path path;
    public int pos;
    protected int readAheadLimit;
    public char readState;

    public void close() throws IOException {
        this.in.close();
    }

    public char getReadState() {
        return this.readState;
    }

    public void setKeepFullLines(boolean keep) {
        if (keep) {
            this.flags &= -9;
        } else {
            this.flags |= DONT_KEEP_FULL_LINES;
        }
    }

    public final boolean getConvertCR() {
        return (this.flags & CONVERT_CR) != 0;
    }

    public final void setConvertCR(boolean convertCR) {
        if (convertCR) {
            this.flags |= CONVERT_CR;
        } else {
            this.flags &= -2;
        }
    }

    public LineBufferedReader(InputStream in) {
        this.readState = '\n';
        this.readAheadLimit = 0;
        this.in = new InputStreamReader(in);
    }

    public LineBufferedReader(Reader in) {
        this.readState = '\n';
        this.readAheadLimit = 0;
        this.in = in;
    }

    public void lineStart(boolean revisited) throws IOException {
    }

    public int fill(int len) throws IOException {
        return this.in.read(this.buffer, this.pos, len);
    }

    private void clearMark() {
        int i = 0;
        this.readAheadLimit = 0;
        if (this.lineStartPos >= 0) {
            i = this.lineStartPos;
        }
        while (true) {
            i += CONVERT_CR;
            if (i < this.pos) {
                char ch = this.buffer[i - 1];
                if (ch == '\n' || (ch == '\r' && !(getConvertCR() && this.buffer[i] == '\n'))) {
                    this.lineNumber += CONVERT_CR;
                    this.lineStartPos = i;
                }
            } else {
                return;
            }
        }
    }

    public void setBuffer(char[] buffer) throws IOException {
        if (buffer == null) {
            if (this.buffer != null) {
                buffer = new char[this.buffer.length];
                System.arraycopy(this.buffer, 0, buffer, 0, this.buffer.length);
                this.buffer = buffer;
            }
            this.flags &= -3;
        } else if (this.limit - this.pos > buffer.length) {
            throw new IOException("setBuffer - too short");
        } else {
            this.flags |= USER_BUFFER;
            reserve(buffer, 0);
        }
    }

    private void reserve(char[] buffer, int reserve) throws IOException {
        int saveStart;
        reserve += this.limit;
        if (reserve <= buffer.length) {
            saveStart = 0;
        } else {
            saveStart = this.pos;
            if (this.readAheadLimit > 0 && this.markPos < this.pos) {
                if (this.pos - this.markPos > this.readAheadLimit || ((this.flags & USER_BUFFER) != 0 && reserve - this.markPos > buffer.length)) {
                    clearMark();
                } else {
                    saveStart = this.markPos;
                }
            }
            reserve -= buffer.length;
            if (reserve > saveStart || (saveStart > this.lineStartPos && (this.flags & DONT_KEEP_FULL_LINES) == 0)) {
                if (reserve <= this.lineStartPos && saveStart > this.lineStartPos) {
                    saveStart = this.lineStartPos;
                } else if ((this.flags & USER_BUFFER) != 0) {
                    saveStart -= (saveStart - reserve) >> USER_BUFFER;
                } else {
                    if (this.lineStartPos >= 0) {
                        saveStart = this.lineStartPos;
                    }
                    buffer = new char[(buffer.length * USER_BUFFER)];
                }
            }
            this.lineStartPos -= saveStart;
            this.limit -= saveStart;
            this.markPos -= saveStart;
            this.pos -= saveStart;
            this.highestPos -= saveStart;
        }
        if (this.limit > 0) {
            System.arraycopy(this.buffer, saveStart, buffer, 0, this.limit);
        }
        this.buffer = buffer;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read() throws java.io.IOException {
        /*
        r9 = this;
        r5 = 1;
        r4 = 10;
        r8 = 13;
        r6 = r9.pos;
        if (r6 <= 0) goto L_0x0077;
    L_0x0009:
        r6 = r9.buffer;
        r7 = r9.pos;
        r7 = r7 + -1;
        r1 = r6[r7];
    L_0x0011:
        if (r1 == r8) goto L_0x0015;
    L_0x0013:
        if (r1 != r4) goto L_0x004d;
    L_0x0015:
        r6 = r9.lineStartPos;
        r7 = r9.pos;
        if (r6 >= r7) goto L_0x002f;
    L_0x001b:
        r6 = r9.readAheadLimit;
        if (r6 == 0) goto L_0x0025;
    L_0x001f:
        r6 = r9.pos;
        r7 = r9.markPos;
        if (r6 > r7) goto L_0x002f;
    L_0x0025:
        r6 = r9.pos;
        r9.lineStartPos = r6;
        r6 = r9.lineNumber;
        r6 = r6 + 1;
        r9.lineNumber = r6;
    L_0x002f:
        r6 = r9.pos;
        r7 = r9.highestPos;
        if (r6 >= r7) goto L_0x0089;
    L_0x0035:
        r3 = r5;
    L_0x0036:
        if (r1 != r4) goto L_0x0042;
    L_0x0038:
        r6 = r9.pos;
        if (r6 > r5) goto L_0x008b;
    L_0x003c:
        r6 = r9.flags;
        r6 = r6 & 4;
        if (r6 != 0) goto L_0x0045;
    L_0x0042:
        r9.lineStart(r3);
    L_0x0045:
        if (r3 != 0) goto L_0x004d;
    L_0x0047:
        r6 = r9.pos;
        r6 = r6 + 1;
        r9.highestPos = r6;
    L_0x004d:
        r6 = r9.pos;
        r7 = r9.limit;
        if (r6 < r7) goto L_0x00af;
    L_0x0053:
        r6 = r9.buffer;
        if (r6 != 0) goto L_0x0096;
    L_0x0057:
        r5 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r5 = new char[r5];
        r9.buffer = r5;
    L_0x005d:
        r5 = r9.pos;
        if (r5 != 0) goto L_0x0069;
    L_0x0061:
        if (r1 != r8) goto L_0x00a3;
    L_0x0063:
        r5 = r9.flags;
        r5 = r5 | 4;
        r9.flags = r5;
    L_0x0069:
        r5 = r9.buffer;
        r5 = r5.length;
        r6 = r9.pos;
        r5 = r5 - r6;
        r2 = r9.fill(r5);
        if (r2 > 0) goto L_0x00aa;
    L_0x0075:
        r0 = -1;
    L_0x0076:
        return r0;
    L_0x0077:
        r6 = r9.flags;
        r6 = r6 & 4;
        if (r6 == 0) goto L_0x0080;
    L_0x007d:
        r1 = 13;
        goto L_0x0011;
    L_0x0080:
        r6 = r9.lineStartPos;
        if (r6 < 0) goto L_0x0087;
    L_0x0084:
        r1 = 10;
        goto L_0x0011;
    L_0x0087:
        r1 = 0;
        goto L_0x0011;
    L_0x0089:
        r3 = 0;
        goto L_0x0036;
    L_0x008b:
        r6 = r9.buffer;
        r7 = r9.pos;
        r7 = r7 + -2;
        r6 = r6[r7];
        if (r6 == r8) goto L_0x0045;
    L_0x0095:
        goto L_0x0042;
    L_0x0096:
        r6 = r9.limit;
        r7 = r9.buffer;
        r7 = r7.length;
        if (r6 != r7) goto L_0x005d;
    L_0x009d:
        r6 = r9.buffer;
        r9.reserve(r6, r5);
        goto L_0x005d;
    L_0x00a3:
        r5 = r9.flags;
        r5 = r5 & -5;
        r9.flags = r5;
        goto L_0x0069;
    L_0x00aa:
        r5 = r9.limit;
        r5 = r5 + r2;
        r9.limit = r5;
    L_0x00af:
        r5 = r9.buffer;
        r6 = r9.pos;
        r7 = r6 + 1;
        r9.pos = r7;
        r0 = r5[r6];
        if (r0 != r4) goto L_0x00dc;
    L_0x00bb:
        if (r1 != r8) goto L_0x0076;
    L_0x00bd:
        r4 = r9.lineStartPos;
        r5 = r9.pos;
        r5 = r5 + -1;
        if (r4 != r5) goto L_0x00d1;
    L_0x00c5:
        r4 = r9.lineNumber;
        r4 = r4 + -1;
        r9.lineNumber = r4;
        r4 = r9.lineStartPos;
        r4 = r4 + -1;
        r9.lineStartPos = r4;
    L_0x00d1:
        r4 = r9.getConvertCR();
        if (r4 == 0) goto L_0x0076;
    L_0x00d7:
        r0 = r9.read();
        goto L_0x0076;
    L_0x00dc:
        if (r0 != r8) goto L_0x0076;
    L_0x00de:
        r5 = r9.getConvertCR();
        if (r5 == 0) goto L_0x0076;
    L_0x00e4:
        r0 = r4;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.read():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(char[] r10, int r11, int r12) throws java.io.IOException {
        /*
        r9 = this;
        r8 = 13;
        r7 = 10;
        r5 = r9.pos;
        r6 = r9.limit;
        if (r5 < r6) goto L_0x0024;
    L_0x000a:
        r0 = 0;
    L_0x000b:
        r4 = r12;
        r2 = r11;
    L_0x000d:
        if (r4 <= 0) goto L_0x007b;
    L_0x000f:
        r5 = r9.pos;
        r6 = r9.limit;
        if (r5 >= r6) goto L_0x0019;
    L_0x0015:
        if (r0 == r7) goto L_0x0019;
    L_0x0017:
        if (r0 != r8) goto L_0x0056;
    L_0x0019:
        r5 = r9.pos;
        r6 = r9.limit;
        if (r5 < r6) goto L_0x0040;
    L_0x001f:
        if (r4 >= r12) goto L_0x0040;
    L_0x0021:
        r5 = r12 - r4;
    L_0x0023:
        return r5;
    L_0x0024:
        r5 = r9.pos;
        if (r5 <= 0) goto L_0x0031;
    L_0x0028:
        r5 = r9.buffer;
        r6 = r9.pos;
        r6 = r6 + -1;
        r0 = r5[r6];
        goto L_0x000b;
    L_0x0031:
        r5 = r9.flags;
        r5 = r5 & 4;
        if (r5 != 0) goto L_0x003b;
    L_0x0037:
        r5 = r9.lineStartPos;
        if (r5 < 0) goto L_0x003e;
    L_0x003b:
        r0 = 10;
        goto L_0x000b;
    L_0x003e:
        r0 = 0;
        goto L_0x000b;
    L_0x0040:
        r0 = r9.read();
        if (r0 >= 0) goto L_0x004d;
    L_0x0046:
        r12 = r12 - r4;
        if (r12 > 0) goto L_0x004b;
    L_0x0049:
        r5 = -1;
        goto L_0x0023;
    L_0x004b:
        r5 = r12;
        goto L_0x0023;
    L_0x004d:
        r11 = r2 + 1;
        r5 = (char) r0;
        r10[r2] = r5;
        r4 = r4 + -1;
        r2 = r11;
        goto L_0x000d;
    L_0x0056:
        r3 = r9.pos;
        r1 = r9.limit;
        r5 = r1 - r3;
        if (r4 >= r5) goto L_0x0060;
    L_0x005e:
        r1 = r3 + r4;
    L_0x0060:
        if (r3 >= r1) goto L_0x006a;
    L_0x0062:
        r5 = r9.buffer;
        r0 = r5[r3];
        if (r0 == r7) goto L_0x006a;
    L_0x0068:
        if (r0 != r8) goto L_0x0072;
    L_0x006a:
        r5 = r9.pos;
        r5 = r3 - r5;
        r4 = r4 - r5;
        r9.pos = r3;
        goto L_0x000d;
    L_0x0072:
        r11 = r2 + 1;
        r5 = (char) r0;
        r10[r2] = r5;
        r3 = r3 + 1;
        r2 = r11;
        goto L_0x0060;
    L_0x007b:
        r5 = r12;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.read(char[], int, int):int");
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return this.path == null ? null : this.path.toString();
    }

    public void setName(Object name) {
        setPath(Path.valueOf(name));
    }

    public int getLineNumber() {
        int lineno = this.lineNumber;
        if (this.readAheadLimit != 0) {
            return lineno + countLines(this.buffer, this.lineStartPos < 0 ? 0 : this.lineStartPos, this.pos);
        } else if (this.pos <= 0 || this.pos <= this.lineStartPos) {
            return lineno;
        } else {
            char prev = this.buffer[this.pos - 1];
            if (prev == '\n' || prev == '\r') {
                return lineno + CONVERT_CR;
            }
            return lineno;
        }
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber += lineNumber - getLineNumber();
    }

    public void incrLineNumber(int lineDelta, int lineStartPos) {
        this.lineNumber += lineDelta;
        this.lineStartPos = lineStartPos;
    }

    public int getColumnNumber() {
        int start = 0;
        if (this.pos > 0) {
            char prev = this.buffer[this.pos - 1];
            if (prev == '\n' || prev == '\r') {
                return 0;
            }
        }
        if (this.readAheadLimit <= 0) {
            return this.pos - this.lineStartPos;
        }
        if (this.lineStartPos >= 0) {
            start = this.lineStartPos;
        }
        int i = start;
        while (i < this.pos) {
            int i2 = i + CONVERT_CR;
            char ch = this.buffer[i];
            if (ch == '\n' || ch == '\r') {
                start = i2;
            }
            i = i2;
        }
        int col = this.pos - start;
        if (this.lineStartPos < 0) {
            col -= this.lineStartPos;
        }
        return col;
    }

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readAheadLimit) {
        if (this.readAheadLimit > 0) {
            clearMark();
        }
        this.readAheadLimit = readAheadLimit;
        this.markPos = this.pos;
    }

    public void reset() throws IOException {
        if (this.readAheadLimit <= 0) {
            throw new IOException("mark invalid");
        }
        if (this.pos > this.highestPos) {
            this.highestPos = this.pos;
        }
        this.pos = this.markPos;
        this.readAheadLimit = 0;
    }

    public void readLine(StringBuffer sbuf, char mode) throws IOException {
        while (read() >= 0) {
            int start = this.pos - 1;
            this.pos = start;
            while (this.pos < this.limit) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + CONVERT_CR;
                int ch = cArr[i];
                if (ch != 13) {
                    if (ch == 10) {
                    }
                }
                sbuf.append(this.buffer, start, (this.pos - 1) - start);
                if (mode == 'P') {
                    this.pos--;
                    return;
                } else if (!getConvertCR() && ch != 10) {
                    if (mode != Access.INNERCLASS_CONTEXT) {
                        sbuf.append('\r');
                    }
                    ch = read();
                    if (ch == 10) {
                        if (mode != Access.INNERCLASS_CONTEXT) {
                            sbuf.append('\n');
                            return;
                        }
                        return;
                    } else if (ch >= 0) {
                        unread_quick();
                        return;
                    } else {
                        return;
                    }
                } else if (mode != Access.INNERCLASS_CONTEXT) {
                    sbuf.append('\n');
                    return;
                } else {
                    return;
                }
            }
            sbuf.append(this.buffer, start, this.pos - start);
        }
    }

    public String readLine() throws IOException {
        int ch = read();
        if (ch < 0) {
            return null;
        }
        if (ch == 13 || ch == 10) {
            return ElementType.MATCH_ANY_LOCALNAME;
        }
        StringBuffer sbuf;
        int start = this.pos - 1;
        while (this.pos < this.limit) {
            char[] cArr = this.buffer;
            int i = this.pos;
            this.pos = i + CONVERT_CR;
            ch = cArr[i];
            if (ch != 13) {
                if (ch == 10) {
                }
            }
            int end = this.pos - 1;
            if (!(ch == 10 || getConvertCR())) {
                if (this.pos >= this.limit) {
                    this.pos--;
                    sbuf = new StringBuffer(100);
                    sbuf.append(this.buffer, start, this.pos - start);
                    readLine(sbuf, Access.INNERCLASS_CONTEXT);
                    return sbuf.toString();
                } else if (this.buffer[this.pos] == '\n') {
                    this.pos += CONVERT_CR;
                }
            }
            return new String(this.buffer, start, end - start);
        }
        sbuf = new StringBuffer(100);
        sbuf.append(this.buffer, start, this.pos - start);
        readLine(sbuf, Access.INNERCLASS_CONTEXT);
        return sbuf.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int skip(int r9) throws java.io.IOException {
        /*
        r8 = this;
        r7 = 13;
        r6 = 10;
        if (r9 >= 0) goto L_0x0015;
    L_0x0006:
        r3 = -r9;
    L_0x0007:
        if (r3 <= 0) goto L_0x0013;
    L_0x0009:
        r4 = r8.pos;
        if (r4 <= 0) goto L_0x0013;
    L_0x000d:
        r8.unread();
        r3 = r3 + -1;
        goto L_0x0007;
    L_0x0013:
        r9 = r9 + r3;
    L_0x0014:
        return r9;
    L_0x0015:
        r3 = r9;
        r4 = r8.pos;
        r5 = r8.limit;
        if (r4 < r5) goto L_0x0031;
    L_0x001c:
        r0 = 0;
    L_0x001d:
        if (r3 <= 0) goto L_0x0014;
    L_0x001f:
        if (r0 == r6) goto L_0x0029;
    L_0x0021:
        if (r0 == r7) goto L_0x0029;
    L_0x0023:
        r4 = r8.pos;
        r5 = r8.limit;
        if (r4 < r5) goto L_0x0050;
    L_0x0029:
        r0 = r8.read();
        if (r0 >= 0) goto L_0x004d;
    L_0x002f:
        r9 = r9 - r3;
        goto L_0x0014;
    L_0x0031:
        r4 = r8.pos;
        if (r4 <= 0) goto L_0x003e;
    L_0x0035:
        r4 = r8.buffer;
        r5 = r8.pos;
        r5 = r5 + -1;
        r0 = r4[r5];
        goto L_0x001d;
    L_0x003e:
        r4 = r8.flags;
        r4 = r4 & 4;
        if (r4 != 0) goto L_0x0048;
    L_0x0044:
        r4 = r8.lineStartPos;
        if (r4 < 0) goto L_0x004b;
    L_0x0048:
        r0 = 10;
        goto L_0x001d;
    L_0x004b:
        r0 = 0;
        goto L_0x001d;
    L_0x004d:
        r3 = r3 + -1;
        goto L_0x001d;
    L_0x0050:
        r2 = r8.pos;
        r1 = r8.limit;
        r4 = r1 - r2;
        if (r3 >= r4) goto L_0x005a;
    L_0x0058:
        r1 = r2 + r3;
    L_0x005a:
        if (r2 >= r1) goto L_0x0064;
    L_0x005c:
        r4 = r8.buffer;
        r0 = r4[r2];
        if (r0 == r6) goto L_0x0064;
    L_0x0062:
        if (r0 != r7) goto L_0x006c;
    L_0x0064:
        r4 = r8.pos;
        r4 = r2 - r4;
        r3 = r3 - r4;
        r8.pos = r2;
        goto L_0x001d;
    L_0x006c:
        r2 = r2 + 1;
        goto L_0x005a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.skip(int):int");
    }

    public boolean ready() throws IOException {
        return this.pos < this.limit || this.in.ready();
    }

    public final void skip_quick() throws IOException {
        this.pos += CONVERT_CR;
    }

    public void skip() throws IOException {
        read();
    }

    static int countLines(char[] buffer, int start, int limit) {
        int count = 0;
        char prev = '\u0000';
        for (int i = start; i < limit; i += CONVERT_CR) {
            char ch = buffer[i];
            if ((ch == '\n' && prev != '\r') || ch == '\r') {
                count += CONVERT_CR;
            }
            prev = ch;
        }
        return count;
    }

    public void skipRestOfLine() throws IOException {
        int c;
        do {
            c = read();
            if (c >= 0) {
                if (c == 13) {
                    c = read();
                    if (c >= 0 && c != 10) {
                        unread();
                        return;
                    }
                    return;
                }
            }
            return;
        } while (c != 10);
    }

    public void unread() throws IOException {
        if (this.pos == 0) {
            throw new IOException("unread too much");
        }
        this.pos--;
        char ch = this.buffer[this.pos];
        if (ch == '\n' || ch == '\r') {
            if (this.pos > 0 && ch == '\n' && getConvertCR() && this.buffer[this.pos - 1] == '\r') {
                this.pos--;
            }
            if (this.pos < this.lineStartPos) {
                this.lineNumber--;
                int i = this.pos;
                while (i > 0) {
                    i--;
                    ch = this.buffer[i];
                    if (ch != '\r') {
                        if (ch == '\n') {
                        }
                    }
                    i += CONVERT_CR;
                    break;
                }
                this.lineStartPos = i;
            }
        }
    }

    public void unread_quick() {
        this.pos--;
    }

    public int peek() throws IOException {
        if (this.pos < this.limit && this.pos > 0) {
            char ch = this.buffer[this.pos - 1];
            if (!(ch == '\n' || ch == '\r')) {
                ch = this.buffer[this.pos];
                if (ch == '\r' && getConvertCR()) {
                    return '\n';
                }
                return ch;
            }
        }
        char c = read();
        if (c >= '\u0000') {
            unread_quick();
        }
        return c;
    }
}
