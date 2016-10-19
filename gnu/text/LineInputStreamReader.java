package gnu.text;

import gnu.expr.Declaration;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class LineInputStreamReader extends LineBufferedReader {
    byte[] barr;
    ByteBuffer bbuf;
    char[] carr;
    CharBuffer cbuf;
    Charset cset;
    CharsetDecoder decoder;
    InputStream istrm;

    public void setCharset(Charset cset) {
        this.cset = cset;
        this.decoder = cset.newDecoder();
    }

    public void setCharset(String name) {
        Charset cset = Charset.forName(name);
        if (this.cset == null) {
            setCharset(cset);
        } else if (!cset.equals(this.cset)) {
            throw new RuntimeException("encoding " + name + " does not match previous " + this.cset);
        }
    }

    public LineInputStreamReader(InputStream in) {
        super((Reader) null);
        this.barr = new byte[Declaration.TYPE_SPECIFIED];
        this.cbuf = null;
        this.bbuf = ByteBuffer.wrap(this.barr);
        this.bbuf.position(this.barr.length);
        this.istrm = in;
    }

    public void close() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        this.istrm.close();
    }

    private int fillBytes(int remaining) throws IOException {
        int i = 0;
        int n = this.istrm.read(this.barr, remaining, this.barr.length - remaining);
        this.bbuf.position(0);
        ByteBuffer byteBuffer = this.bbuf;
        if (n >= 0) {
            i = n;
        }
        byteBuffer.limit(i + remaining);
        return n;
    }

    public void markStart() throws IOException {
    }

    public void resetStart(int pos) throws IOException {
        this.bbuf.position(pos);
    }

    public int getByte() throws IOException {
        if (this.bbuf.hasRemaining() || fillBytes(0) > 0) {
            return this.bbuf.get() & 255;
        }
        return -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int fill(int r9) throws java.io.IOException {
        /*
        r8 = this;
        r5 = r8.cset;
        if (r5 != 0) goto L_0x0009;
    L_0x0004:
        r5 = "UTF-8";
        r8.setCharset(r5);
    L_0x0009:
        r5 = r8.buffer;
        r6 = r8.carr;
        if (r5 == r6) goto L_0x001b;
    L_0x000f:
        r5 = r8.buffer;
        r5 = java.nio.CharBuffer.wrap(r5);
        r8.cbuf = r5;
        r5 = r8.buffer;
        r8.carr = r5;
    L_0x001b:
        r5 = r8.cbuf;
        r6 = r8.pos;
        r6 = r6 + r9;
        r5.limit(r6);
        r5 = r8.cbuf;
        r6 = r8.pos;
        r5.position(r6);
        r2 = 0;
    L_0x002b:
        r5 = r8.decoder;
        r6 = r8.bbuf;
        r7 = r8.cbuf;
        r1 = r5.decode(r6, r7, r2);
        r5 = r8.cbuf;
        r5 = r5.position();
        r6 = r8.pos;
        r0 = r5 - r6;
        if (r0 > 0) goto L_0x0047;
    L_0x0041:
        r5 = r1.isUnderflow();
        if (r5 != 0) goto L_0x004d;
    L_0x0047:
        if (r0 != 0) goto L_0x004c;
    L_0x0049:
        if (r2 == 0) goto L_0x004c;
    L_0x004b:
        r0 = -1;
    L_0x004c:
        return r0;
    L_0x004d:
        r5 = r8.bbuf;
        r4 = r5.remaining();
        if (r4 <= 0) goto L_0x005a;
    L_0x0055:
        r5 = r8.bbuf;
        r5.compact();
    L_0x005a:
        r3 = r8.fillBytes(r4);
        if (r3 >= 0) goto L_0x002b;
    L_0x0060:
        r2 = 1;
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineInputStreamReader.fill(int):int");
    }

    public boolean ready() throws IOException {
        return this.pos < this.limit || this.bbuf.hasRemaining() || this.istrm.available() > 0;
    }
}
