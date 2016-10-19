package gnu.ecmascript;

import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.expr.QuoteExp;
import gnu.kawa.slib.srfi1;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer extends gnu.text.Lexer {
    public static final Char colonToken;
    public static final Char commaToken;
    public static final Char condToken;
    public static final Char dotToken;
    public static final Reserved elseToken;
    public static final Object eofToken;
    public static final Object eolToken;
    public static final Char equalToken;
    public static final Char lbraceToken;
    public static final Char lbracketToken;
    public static final Char lparenToken;
    public static final Reserved newToken;
    public static final Char notToken;
    public static final Char rbraceToken;
    public static final Char rbracketToken;
    static Hashtable reserved;
    public static final Char rparenToken;
    public static final Char semicolonToken;
    public static final Char tildeToken;
    private boolean prevWasCR;

    public Lexer(InPort port) {
        super(port);
        this.prevWasCR = false;
    }

    static {
        lparenToken = Char.make(40);
        rparenToken = Char.make(41);
        lbraceToken = Char.make(srfi1.$Pcprovide$Pcsrfi$Mn1);
        rbraceToken = Char.make(125);
        lbracketToken = Char.make(91);
        rbracketToken = Char.make(93);
        dotToken = Char.make(46);
        condToken = Char.make(63);
        commaToken = Char.make(44);
        colonToken = Char.make(58);
        equalToken = Char.make(61);
        tildeToken = Char.make(126);
        notToken = Char.make(33);
        semicolonToken = Char.make(59);
        eolToken = Char.make(10);
        eofToken = Sequence.eofValue;
        elseToken = new Reserved("else", 38);
        newToken = new Reserved("new", 39);
    }

    static synchronized void initReserved() {
        synchronized (Lexer.class) {
            if (reserved == null) {
                reserved = new Hashtable(20);
                reserved.put("null", new QuoteExp(null));
                reserved.put("true", new QuoteExp(Boolean.TRUE));
                reserved.put("false", new QuoteExp(Boolean.FALSE));
                reserved.put("var", new Reserved("var", 30));
                reserved.put("if", new Reserved("if", 31));
                reserved.put("while", new Reserved("while", 32));
                reserved.put("for", new Reserved("for", 33));
                reserved.put("continue", new Reserved("continue", 34));
                reserved.put("break", new Reserved("break", 35));
                reserved.put("return", new Reserved("return", 36));
                reserved.put("with", new Reserved("with", 37));
                reserved.put("function", new Reserved("function", 41));
                reserved.put("this", new Reserved("this", 40));
                reserved.put("else", elseToken);
                reserved.put("new", newToken);
            }
        }
    }

    public static Object checkReserved(String name) {
        if (reserved == null) {
            initReserved();
        }
        return reserved.get(name);
    }

    public Double getNumericLiteral(int c) throws IOException {
        int radix = 10;
        if (c == 48) {
            c = read();
            if (c == 120 || c == 88) {
                radix = 16;
                c = read();
            } else if (!(c == 46 || c == 101 || c == 69)) {
                radix = 8;
            }
        }
        int i = this.port.pos;
        if (c >= 0) {
            i--;
        }
        this.port.pos = i;
        long ival = gnu.text.Lexer.readDigitsInBuffer(this.port, radix);
        boolean digit_seen = this.port.pos > i;
        if (digit_seen && this.port.pos < this.port.limit) {
            c = this.port.buffer[this.port.pos];
            if (!(Character.isLetterOrDigit((char) c) || c == 46)) {
                double dval;
                if (ival >= 0) {
                    dval = (double) ival;
                } else {
                    dval = IntNum.valueOf(this.port.buffer, i, this.port.pos - i, radix, false).doubleValue();
                }
                return new Double(dval);
            }
        }
        if (radix != 10) {
            error("invalid character in non-decimal number");
        }
        StringBuffer str = new StringBuffer(20);
        if (digit_seen) {
            str.append(this.port.buffer, i, this.port.pos - i);
        }
        int point_loc = -1;
        int exp = 0;
        while (true) {
            c = this.port.read();
            if (Character.digit((char) c, radix) >= 0) {
                digit_seen = true;
                str.append((char) c);
            } else {
                switch (c) {
                    case XDataType.IDREF_TYPE_CODE /*46*/:
                        if (point_loc < 0) {
                            point_loc = str.length();
                            str.append('.');
                            break;
                        }
                        error("duplicate '.' in number");
                        continue;
                    case 69:
                    case ErrorMessages.ERROR_LOCATION_SENSOR_LATITUDE_NOT_FOUND /*101*/:
                        if (radix == 10) {
                            int next = this.port.peek();
                            if (next == 43 || next == 45 || Character.digit((char) next, 10) >= 0) {
                                if (!digit_seen) {
                                    error("mantissa with no digits");
                                }
                                exp = readOptionalExponent();
                                c = read();
                                break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                if (c >= 0) {
                    this.port.unread();
                }
                if (exp != 0) {
                    str.append('e');
                    str.append(exp);
                }
                return new Double(str.toString());
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getStringLiteral(char r15) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r14 = this;
        r13 = 92;
        r12 = 13;
        r11 = 10;
        r9 = r14.port;
        r4 = r9.pos;
        r7 = r4;
        r9 = r14.port;
        r5 = r9.limit;
        r9 = r14.port;
        r0 = r9.buffer;
    L_0x0013:
        if (r4 >= r5) goto L_0x002d;
    L_0x0015:
        r1 = r0[r4];
        if (r1 != r15) goto L_0x0027;
    L_0x0019:
        r9 = r14.port;
        r10 = r4 + 1;
        r9.pos = r10;
        r9 = new java.lang.String;
        r10 = r4 - r7;
        r9.<init>(r0, r7, r10);
    L_0x0026:
        return r9;
    L_0x0027:
        if (r1 == r13) goto L_0x002d;
    L_0x0029:
        if (r1 == r11) goto L_0x002d;
    L_0x002b:
        if (r1 != r12) goto L_0x0048;
    L_0x002d:
        r9 = r14.port;
        r9.pos = r4;
        r6 = new java.lang.StringBuffer;
        r6.<init>();
        r9 = r4 - r7;
        r6.append(r0, r7, r9);
    L_0x003b:
        r9 = r14.port;
        r2 = r9.read();
        if (r2 != r15) goto L_0x004b;
    L_0x0043:
        r9 = r6.toString();
        goto L_0x0026;
    L_0x0048:
        r4 = r4 + 1;
        goto L_0x0013;
    L_0x004b:
        if (r2 >= 0) goto L_0x0052;
    L_0x004d:
        r9 = "unterminated string literal";
        r14.eofError(r9);
    L_0x0052:
        if (r2 == r11) goto L_0x0056;
    L_0x0054:
        if (r2 != r12) goto L_0x005b;
    L_0x0056:
        r9 = "string literal not terminated before end of line";
        r14.fatal(r9);
    L_0x005b:
        if (r2 != r13) goto L_0x006e;
    L_0x005d:
        r9 = r14.port;
        r2 = r9.read();
        switch(r2) {
            case -1: goto L_0x0073;
            case 10: goto L_0x0078;
            case 13: goto L_0x0078;
            case 34: goto L_0x006e;
            case 39: goto L_0x006e;
            case 92: goto L_0x006e;
            case 98: goto L_0x007e;
            case 102: goto L_0x0087;
            case 110: goto L_0x0084;
            case 114: goto L_0x008a;
            case 116: goto L_0x0081;
            case 117: goto L_0x008d;
            case 120: goto L_0x008d;
            default: goto L_0x0066;
        };
    L_0x0066:
        r9 = 48;
        if (r2 < r9) goto L_0x006e;
    L_0x006a:
        r9 = 55;
        if (r2 <= r9) goto L_0x00ed;
    L_0x006e:
        r9 = (char) r2;
        r6.append(r9);
        goto L_0x003b;
    L_0x0073:
        r9 = "eof following '\\' in string";
        r14.eofError(r9);
    L_0x0078:
        r9 = "line terminator following '\\' in string";
        r14.fatal(r9);
        goto L_0x006e;
    L_0x007e:
        r2 = 8;
        goto L_0x006e;
    L_0x0081:
        r2 = 9;
        goto L_0x006e;
    L_0x0084:
        r2 = 10;
        goto L_0x006e;
    L_0x0087:
        r2 = 12;
        goto L_0x006e;
    L_0x008a:
        r2 = 13;
        goto L_0x006e;
    L_0x008d:
        r8 = 0;
        r9 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r2 != r9) goto L_0x00e6;
    L_0x0092:
        r4 = 2;
    L_0x0093:
        r4 = r4 + -1;
        if (r4 < 0) goto L_0x00e4;
    L_0x0097:
        r9 = r14.port;
        r3 = r9.read();
        if (r3 >= 0) goto L_0x00bc;
    L_0x009f:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "eof following '\\";
        r9 = r9.append(r10);
        r10 = (char) r2;
        r9 = r9.append(r10);
        r10 = "' in string";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r14.eofError(r9);
    L_0x00bc:
        r9 = (char) r3;
        r10 = 16;
        r3 = java.lang.Character.forDigit(r9, r10);
        if (r3 >= 0) goto L_0x00e8;
    L_0x00c5:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "invalid char following '\\";
        r9 = r9.append(r10);
        r10 = (char) r2;
        r9 = r9.append(r10);
        r10 = "' in string";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r14.error(r9);
        r8 = 63;
    L_0x00e4:
        r2 = r8;
        goto L_0x006e;
    L_0x00e6:
        r4 = 4;
        goto L_0x0093;
    L_0x00e8:
        r9 = r8 * 16;
        r8 = r9 + r3;
        goto L_0x0093;
    L_0x00ed:
        r8 = 0;
        r4 = 3;
    L_0x00ef:
        r4 = r4 + -1;
        if (r4 < 0) goto L_0x010e;
    L_0x00f3:
        r9 = r14.port;
        r3 = r9.read();
        if (r3 >= 0) goto L_0x0100;
    L_0x00fb:
        r9 = "eof in octal escape in string literal";
        r14.eofError(r9);
    L_0x0100:
        r9 = (char) r3;
        r10 = 8;
        r3 = java.lang.Character.forDigit(r9, r10);
        if (r3 >= 0) goto L_0x0111;
    L_0x0109:
        r9 = r14.port;
        r9.unread_quick();
    L_0x010e:
        r2 = r8;
        goto L_0x006e;
    L_0x0111:
        r9 = r8 * 8;
        r8 = r9 + r3;
        goto L_0x00ef;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.ecmascript.Lexer.getStringLiteral(char):java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getIdentifier(int r8) throws java.io.IOException {
        /*
        r7 = this;
        r5 = r7.port;
        r1 = r5.pos;
        r4 = r1 + -1;
        r5 = r7.port;
        r2 = r5.limit;
        r5 = r7.port;
        r0 = r5.buffer;
    L_0x000e:
        if (r1 >= r2) goto L_0x001b;
    L_0x0010:
        r5 = r0[r1];
        r5 = java.lang.Character.isJavaIdentifierPart(r5);
        if (r5 == 0) goto L_0x001b;
    L_0x0018:
        r1 = r1 + 1;
        goto L_0x000e;
    L_0x001b:
        r5 = r7.port;
        r5.pos = r1;
        if (r1 >= r2) goto L_0x0029;
    L_0x0021:
        r5 = new java.lang.String;
        r6 = r1 - r4;
        r5.<init>(r0, r4, r6);
    L_0x0028:
        return r5;
    L_0x0029:
        r3 = new java.lang.StringBuffer;
        r3.<init>();
        r5 = r1 - r4;
        r3.append(r0, r4, r5);
    L_0x0033:
        r5 = r7.port;
        r8 = r5.read();
        if (r8 >= 0) goto L_0x0040;
    L_0x003b:
        r5 = r3.toString();
        goto L_0x0028;
    L_0x0040:
        r5 = (char) r8;
        r5 = java.lang.Character.isJavaIdentifierPart(r5);
        if (r5 == 0) goto L_0x004c;
    L_0x0047:
        r5 = (char) r8;
        r3.append(r5);
        goto L_0x0033;
    L_0x004c:
        r5 = r7.port;
        r5.unread_quick();
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.ecmascript.Lexer.getIdentifier(int):java.lang.String");
    }

    public Object maybeAssignment(Object token) throws IOException, SyntaxException {
        int ch = read();
        if (ch == 61) {
            error("assignment operation not implemented");
        }
        if (ch >= 0) {
            this.port.unread_quick();
        }
        return token;
    }

    public Object getToken() throws IOException, SyntaxException {
        int ch = read();
        while (ch >= 0) {
            if (!Character.isWhitespace((char) ch)) {
                switch (ch) {
                    case Sequence.ELEMENT_VALUE /*33*/:
                        if (this.port.peek() != 61) {
                            return notToken;
                        }
                        this.port.skip_quick();
                        return Reserved.opNotEqual;
                    case Sequence.DOCUMENT_VALUE /*34*/:
                    case XDataType.NORMALIZED_STRING_TYPE_CODE /*39*/:
                        return new QuoteExp(getStringLiteral((char) ch));
                    case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                        return maybeAssignment(Reserved.opRemainder);
                    case XDataType.STRING_TYPE_CODE /*38*/:
                        if (this.port.peek() != 38) {
                            return maybeAssignment(Reserved.opBitAnd);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opBoolAnd);
                    case XDataType.TOKEN_TYPE_CODE /*40*/:
                        return lparenToken;
                    case XDataType.LANGUAGE_TYPE_CODE /*41*/:
                        return rparenToken;
                    case XDataType.NMTOKEN_TYPE_CODE /*42*/:
                        return maybeAssignment(Reserved.opTimes);
                    case XDataType.NAME_TYPE_CODE /*43*/:
                        if (this.port.peek() != 43) {
                            return maybeAssignment(Reserved.opPlus);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opPlusPlus);
                    case XDataType.NCNAME_TYPE_CODE /*44*/:
                        return commaToken;
                    case XDataType.ID_TYPE_CODE /*45*/:
                        if (this.port.peek() != 45) {
                            return maybeAssignment(Reserved.opMinus);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opMinusMinus);
                    case XDataType.IDREF_TYPE_CODE /*46*/:
                        ch = this.port.peek();
                        if (ch < 48 || ch > 57) {
                            return dotToken;
                        }
                        return new QuoteExp(getNumericLiteral(46));
                    case XDataType.ENTITY_TYPE_CODE /*47*/:
                        return maybeAssignment(Reserved.opDivide);
                    case XDataType.UNTYPED_TYPE_CODE /*48*/:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        return new QuoteExp(getNumericLiteral(ch));
                    case 58:
                        return colonToken;
                    case 59:
                        return semicolonToken;
                    case 60:
                        switch (this.port.peek()) {
                            case 60:
                                this.port.skip_quick();
                                return maybeAssignment(Reserved.opLshift);
                            case 61:
                                this.port.skip_quick();
                                return Reserved.opLessEqual;
                            default:
                                return Reserved.opLess;
                        }
                    case 61:
                        if (this.port.peek() != 61) {
                            return equalToken;
                        }
                        this.port.skip_quick();
                        return Reserved.opEqual;
                    case 62:
                        switch (this.port.peek()) {
                            case 61:
                                this.port.skip_quick();
                                return Reserved.opGreaterEqual;
                            case 62:
                                this.port.skip_quick();
                                if (this.port.peek() != 62) {
                                    return maybeAssignment(Reserved.opRshiftSigned);
                                }
                                this.port.skip_quick();
                                return maybeAssignment(Reserved.opRshiftUnsigned);
                            default:
                                return Reserved.opGreater;
                        }
                    case 63:
                        return condToken;
                    case 91:
                        return lbracketToken;
                    case 93:
                        return rbracketToken;
                    case 94:
                        return maybeAssignment(Reserved.opBitXor);
                    case srfi1.$Pcprovide$Pcsrfi$Mn1:
                        return lbraceToken;
                    case 124:
                        if (this.port.peek() != 124) {
                            return maybeAssignment(Reserved.opBitOr);
                        }
                        this.port.skip_quick();
                        return maybeAssignment(Reserved.opBoolOr);
                    case 125:
                        return rbraceToken;
                    case 126:
                        return tildeToken;
                    default:
                        if (!Character.isJavaIdentifierStart((char) ch)) {
                            return Char.make((char) ch);
                        }
                        String word = getIdentifier(ch).intern();
                        Object token = checkReserved(word);
                        if (token == null) {
                            return word;
                        }
                        return token;
                }
            } else if (ch == 13) {
                this.prevWasCR = true;
                return eolToken;
            } else if (ch == 10 && !this.prevWasCR) {
                return eolToken;
            } else {
                this.prevWasCR = false;
                ch = read();
            }
        }
        return eofToken;
    }

    public static Object getToken(InPort inp) throws IOException, SyntaxException {
        return new Lexer(inp).getToken();
    }

    public static void main(String[] args) {
        Lexer reader = new Lexer(InPort.inDefault());
        Object token;
        do {
            try {
                token = reader.getToken();
                OutPort out = OutPort.outDefault();
                out.print("token:");
                out.print(token);
                out.println(" [class:" + token.getClass() + "]");
            } catch (Exception ex) {
                System.err.println("caught exception:" + ex);
                return;
            }
        } while (token != Sequence.eofValue);
    }
}
