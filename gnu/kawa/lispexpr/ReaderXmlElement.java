package gnu.kawa.lispexpr;

import gnu.expr.Compilation;
import gnu.expr.PrimProcedure;
import gnu.expr.Special;
import gnu.kawa.slib.srfi1;
import gnu.kawa.xml.CommentConstructor;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.MakeCDATA;
import gnu.kawa.xml.MakeProcInst;
import gnu.kawa.xml.MakeText;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Namespace;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import gnu.xml.XName;
import java.io.IOException;

public class ReaderXmlElement extends ReadTableEntry {
    static final String DEFAULT_ELEMENT_NAMESPACE = "[default-element-namespace]";

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        return readXMLConstructor(reader, reader.readUnicodeChar(), false);
    }

    public static Pair quote(Object obj) {
        return LList.list2(Namespace.EmptyNamespace.getSymbol(LispLanguage.quote_sym), obj);
    }

    public static Object readQNameExpression(LispReader reader, int ch, boolean forElement) throws IOException, SyntaxException {
        String file = reader.getName();
        int line = reader.getLineNumber() + 1;
        int column = reader.getColumnNumber();
        reader.tokenBufferLength = 0;
        if (XName.isNameStart(ch)) {
            int colon = -1;
            while (true) {
                reader.tokenBufferAppend(ch);
                ch = reader.readUnicodeChar();
                if (ch == 58 && colon < 0) {
                    colon = reader.tokenBufferLength;
                } else if (!XName.isNamePart(ch)) {
                    break;
                }
            }
            reader.unread(ch);
            if (colon < 0 && !forElement) {
                return quote(Namespace.getDefaultSymbol(reader.tokenBufferString().intern()));
            }
            String prefix;
            String local = new String(reader.tokenBuffer, colon + 1, (reader.tokenBufferLength - colon) - 1).intern();
            if (colon < 0) {
                prefix = DEFAULT_ELEMENT_NAMESPACE;
            } else {
                prefix = new String(reader.tokenBuffer, 0, colon).intern();
            }
            return new Pair(ResolveNamespace.resolveQName, PairWithPosition.make(Namespace.EmptyNamespace.getSymbol(prefix), new Pair(local, LList.Empty), reader.getName(), line, column));
        } else if (ch == srfi1.$Pcprovide$Pcsrfi$Mn1 || ch == 40) {
            return readEscapedExpression(reader, ch);
        } else {
            reader.error("missing element name");
            return null;
        }
    }

    static Object readEscapedExpression(LispReader reader, int ch) throws IOException, SyntaxException {
        if (ch == 40) {
            reader.unread(ch);
            return reader.readObject();
        }
        LineBufferedReader port = reader.getPort();
        char saveReadState = reader.pushNesting('{');
        int startLine = port.getLineNumber();
        int startColumn = port.getColumnNumber();
        try {
            Object makePair = reader.makePair(new PrimProcedure(Compilation.typeValues.getDeclaredMethod("values", 1)), startLine, startColumn);
            Pair last = makePair;
            ReadTable readTable = ReadTable.getCurrent();
            while (true) {
                int line = port.getLineNumber();
                int column = port.getColumnNumber();
                ch = port.read();
                if (ch == 125) {
                    break;
                }
                if (ch < 0) {
                    reader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                }
                Values value = reader.readValues(ch, readTable.lookup(ch), readTable);
                if (value != Values.empty) {
                    Pair pair = reader.makePair(reader.handlePostfix(value, readTable, line, column), line, column);
                    reader.setCdr(last, pair);
                    last = pair;
                }
            }
            reader.tokenBufferLength = 0;
            if (last == makePair.getCdr()) {
                makePair = last.getCar();
                return makePair;
            }
            reader.popNesting(saveReadState);
            return makePair;
        } finally {
            reader.popNesting(saveReadState);
        }
    }

    static Object readXMLConstructor(LispReader reader, int next, boolean inElementContent) throws IOException, SyntaxException {
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        if (next == 33) {
            next = reader.read();
            if (next == 45) {
                next = reader.peek();
                if (next == 45) {
                    reader.skip();
                    if (!reader.readDelimited("-->")) {
                        reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in XML comment starting here - expected \"-->\"");
                    }
                    return LList.list2(CommentConstructor.commentConstructor, reader.tokenBufferString());
                }
            }
            if (next == 91) {
                next = reader.read();
                if (next == 67) {
                    next = reader.read();
                    if (next == 68) {
                        next = reader.read();
                        if (next == 65) {
                            next = reader.read();
                            if (next == 84) {
                                next = reader.read();
                                if (next == 65) {
                                    next = reader.read();
                                    if (next == 91) {
                                        if (!reader.readDelimited("]]>")) {
                                            reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in CDATA starting here - expected \"]]>\"");
                                        }
                                        return LList.list2(MakeCDATA.makeCDATA, reader.tokenBufferString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            reader.error('f', reader.getName(), startLine, startColumn, "'<!' must be followed by '--' or '[CDATA['");
            while (next >= 0 && next != 62 && next != 10 && next != 13) {
                next = reader.read();
            }
            return null;
        } else if (next != 63) {
            return readElementConstructor(reader, next);
        } else {
            next = reader.readUnicodeChar();
            if (next < 0 || !XName.isNameStart(next)) {
                reader.error("missing target after '<?'");
            }
            do {
                reader.tokenBufferAppend(next);
                next = reader.readUnicodeChar();
            } while (XName.isNamePart(next));
            String target = reader.tokenBufferString();
            int nspaces = 0;
            while (next >= 0 && Character.isWhitespace(next)) {
                nspaces++;
                next = reader.read();
            }
            reader.unread(next);
            char saveReadState = reader.pushNesting('?');
            try {
                if (!reader.readDelimited("?>")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file looking for \"?>\"");
                }
                reader.popNesting(saveReadState);
                if (nspaces == 0 && reader.tokenBufferLength > 0) {
                    reader.error("target must be followed by space or '?>'");
                }
                return LList.list3(MakeProcInst.makeProcInst, target, reader.tokenBufferString());
            } catch (Throwable th) {
                reader.popNesting(saveReadState);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object readElementConstructor(gnu.kawa.lispexpr.LispReader r29, int r30) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r3 = r29.getLineNumber();
        r25 = r3 + 1;
        r3 = r29.getColumnNumber();
        r24 = r3 + -2;
        r3 = 1;
        r0 = r29;
        r1 = r30;
        r27 = readQNameExpression(r0, r1, r3);
        r0 = r29;
        r3 = r0.tokenBufferLength;
        if (r3 != 0) goto L_0x0048;
    L_0x001b:
        r26 = 0;
    L_0x001d:
        r3 = gnu.lists.LList.Empty;
        r4 = r29.getName();
        r0 = r27;
        r1 = r25;
        r2 = r24;
        r28 = gnu.lists.PairWithPosition.make(r0, r3, r4, r1, r2);
        r22 = r28;
        r18 = gnu.lists.LList.Empty;
        r20 = 0;
    L_0x0033:
        r23 = 0;
        r30 = r29.readUnicodeChar();
    L_0x0039:
        if (r30 < 0) goto L_0x004d;
    L_0x003b:
        r3 = java.lang.Character.isWhitespace(r30);
        if (r3 == 0) goto L_0x004d;
    L_0x0041:
        r30 = r29.read();
        r23 = 1;
        goto L_0x0039;
    L_0x0048:
        r26 = r29.tokenBufferString();
        goto L_0x001d;
    L_0x004d:
        if (r30 < 0) goto L_0x005b;
    L_0x004f:
        r3 = 62;
        r0 = r30;
        if (r0 == r3) goto L_0x005b;
    L_0x0055:
        r3 = 47;
        r0 = r30;
        if (r0 != r3) goto L_0x007f;
    L_0x005b:
        r15 = 0;
        r3 = 47;
        r0 = r30;
        if (r0 != r3) goto L_0x006d;
    L_0x0062:
        r30 = r29.read();
        r3 = 62;
        r0 = r30;
        if (r0 != r3) goto L_0x017f;
    L_0x006c:
        r15 = 1;
    L_0x006d:
        if (r15 != 0) goto L_0x020e;
    L_0x006f:
        r3 = 62;
        r0 = r30;
        if (r0 == r3) goto L_0x0184;
    L_0x0075:
        r3 = "missing '>' after start element";
        r0 = r29;
        r0.error(r3);
        r3 = java.lang.Boolean.FALSE;
    L_0x007e:
        return r3;
    L_0x007f:
        if (r23 != 0) goto L_0x0088;
    L_0x0081:
        r3 = "missing space before attribute";
        r0 = r29;
        r0.error(r3);
    L_0x0088:
        r3 = 0;
        r0 = r29;
        r1 = r30;
        r10 = readQNameExpression(r0, r1, r3);
        r3 = r29.getLineNumber();
        r17 = r3 + 1;
        r3 = r29.getColumnNumber();
        r3 = r3 + 1;
        r0 = r29;
        r4 = r0.tokenBufferLength;
        r13 = r3 - r4;
        r14 = 0;
        r0 = r29;
        r3 = r0.tokenBufferLength;
        r4 = 5;
        if (r3 < r4) goto L_0x00eb;
    L_0x00ab:
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 0;
        r3 = r3[r4];
        r4 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r3 != r4) goto L_0x00eb;
    L_0x00b6:
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 1;
        r3 = r3[r4];
        r4 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        if (r3 != r4) goto L_0x00eb;
    L_0x00c1:
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 2;
        r3 = r3[r4];
        r4 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        if (r3 != r4) goto L_0x00eb;
    L_0x00cc:
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 3;
        r3 = r3[r4];
        r4 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r3 != r4) goto L_0x00eb;
    L_0x00d7:
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 4;
        r3 = r3[r4];
        r4 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        if (r3 != r4) goto L_0x00eb;
    L_0x00e2:
        r0 = r29;
        r3 = r0.tokenBufferLength;
        r4 = 5;
        if (r3 != r4) goto L_0x014d;
    L_0x00e9:
        r14 = "";
    L_0x00eb:
        r3 = 32;
        r0 = r29;
        r30 = skipSpace(r0, r3);
        r3 = 61;
        r0 = r30;
        if (r0 == r3) goto L_0x0100;
    L_0x00f9:
        r3 = "missing '=' after attribute";
        r0 = r29;
        r0.error(r3);
    L_0x0100:
        r3 = 32;
        r0 = r29;
        r30 = skipSpace(r0, r3);
        r3 = gnu.kawa.xml.MakeAttribute.makeAttribute;
        r4 = gnu.lists.LList.Empty;
        r5 = r29.getName();
        r0 = r25;
        r1 = r24;
        r9 = gnu.lists.PairWithPosition.make(r3, r4, r5, r0, r1);
        r12 = r9;
        r3 = gnu.lists.LList.Empty;
        r4 = r29.getName();
        r0 = r25;
        r1 = r24;
        r11 = gnu.lists.PairWithPosition.make(r10, r3, r4, r0, r1);
        r0 = r29;
        r0.setCdr(r12, r11);
        r12 = r11;
        r0 = r30;
        r3 = (char) r0;
        r0 = r29;
        r12 = readContent(r0, r3, r12);
        if (r14 == 0) goto L_0x0169;
    L_0x0138:
        r19 = new gnu.lists.PairWithPosition;
        r3 = r11.getCdr();
        r3 = gnu.lists.Pair.make(r14, r3);
        r0 = r19;
        r1 = r18;
        r0.<init>(r11, r3, r1);
        r18 = r19;
        goto L_0x0033;
    L_0x014d:
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 5;
        r3 = r3[r4];
        r4 = 58;
        if (r3 != r4) goto L_0x00eb;
    L_0x0158:
        r14 = new java.lang.String;
        r0 = r29;
        r3 = r0.tokenBuffer;
        r4 = 6;
        r0 = r29;
        r5 = r0.tokenBufferLength;
        r5 = r5 + -6;
        r14.<init>(r3, r4, r5);
        goto L_0x00eb;
    L_0x0169:
        r3 = r29.makeNil();
        r4 = 0;
        r5 = -1;
        r6 = -1;
        r21 = gnu.lists.PairWithPosition.make(r9, r3, r4, r5, r6);
        r0 = r22;
        r1 = r21;
        r0.setCdrBackdoor(r1);
        r22 = r21;
        goto L_0x0033;
    L_0x017f:
        r29.unread(r30);
        goto L_0x006d;
    L_0x0184:
        r3 = 60;
        r0 = r29;
        r1 = r22;
        r22 = readContent(r0, r3, r1);
        r30 = r29.readUnicodeChar();
        r3 = gnu.xml.XName.isNameStart(r30);
        if (r3 == 0) goto L_0x01fd;
    L_0x0198:
        r3 = 0;
        r0 = r29;
        r0.tokenBufferLength = r3;
    L_0x019d:
        r29.tokenBufferAppend(r30);
        r30 = r29.readUnicodeChar();
        r3 = gnu.xml.XName.isNamePart(r30);
        if (r3 != 0) goto L_0x019d;
    L_0x01aa:
        r3 = 58;
        r0 = r30;
        if (r0 == r3) goto L_0x019d;
    L_0x01b0:
        r16 = r29.tokenBufferString();
        if (r26 == 0) goto L_0x01c0;
    L_0x01b6:
        r0 = r16;
        r1 = r26;
        r3 = r0.equals(r1);
        if (r3 != 0) goto L_0x01f8;
    L_0x01c0:
        r4 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r5 = r29.getName();
        r3 = r29.getLineNumber();
        r6 = r3 + 1;
        r3 = r29.getColumnNumber();
        r0 = r29;
        r7 = r0.tokenBufferLength;
        r7 = r3 - r7;
        if (r26 != 0) goto L_0x022a;
    L_0x01d8:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r8 = "computed start tag closed by '</";
        r3 = r3.append(r8);
        r0 = r16;
        r3 = r3.append(r0);
        r8 = ">'";
        r3 = r3.append(r8);
        r8 = r3.toString();
    L_0x01f3:
        r3 = r29;
        r3.error(r4, r5, r6, r7, r8);
    L_0x01f8:
        r3 = 0;
        r0 = r29;
        r0.tokenBufferLength = r3;
    L_0x01fd:
        r30 = skipSpace(r29, r30);
        r3 = 62;
        r0 = r30;
        if (r0 == r3) goto L_0x020e;
    L_0x0207:
        r3 = "missing '>' after end element";
        r0 = r29;
        r0.error(r3);
    L_0x020e:
        r18 = gnu.lists.LList.reverseInPlace(r18);
        r3 = gnu.kawa.lispexpr.MakeXmlElement.makeXml;
        r0 = r18;
        r1 = r28;
        r4 = gnu.lists.Pair.make(r0, r1);
        r5 = r29.getName();
        r0 = r25;
        r1 = r24;
        r3 = gnu.lists.PairWithPosition.make(r3, r4, r5, r0, r1);
        goto L_0x007e;
    L_0x022a:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r8 = "'<";
        r3 = r3.append(r8);
        r0 = r26;
        r3 = r3.append(r0);
        r8 = ">' closed by '</";
        r3 = r3.append(r8);
        r0 = r16;
        r3 = r3.append(r0);
        r8 = ">'";
        r3 = r3.append(r8);
        r8 = r3.toString();
        goto L_0x01f3;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderXmlElement.readElementConstructor(gnu.kawa.lispexpr.LispReader, int):java.lang.Object");
    }

    public static Pair readContent(LispReader reader, char delimiter, Pair resultTail) throws IOException, SyntaxException {
        reader.tokenBufferLength = 0;
        boolean prevWasEnclosed = false;
        while (true) {
            Object obj = null;
            String text = null;
            int line = reader.getLineNumber() + 1;
            int column = reader.getColumnNumber();
            int next = reader.read();
            if (next < 0) {
                reader.error("unexpected end-of-file");
                obj = Special.eof;
            } else if (next == delimiter) {
                if (delimiter == '<') {
                    if (reader.tokenBufferLength > 0) {
                        text = reader.tokenBufferString();
                        reader.tokenBufferLength = 0;
                    }
                    next = reader.read();
                    if (next == 47) {
                        obj = Special.eof;
                    } else {
                        obj = readXMLConstructor(reader, next, true);
                    }
                } else if (reader.checkNext(delimiter)) {
                    reader.tokenBufferAppend(delimiter);
                } else {
                    obj = Special.eof;
                }
                prevWasEnclosed = false;
            } else if (next == '&') {
                next = reader.read();
                if (next == 35) {
                    readCharRef(reader);
                } else if (next == 40 || next == srfi1.$Pcprovide$Pcsrfi$Mn1) {
                    if (reader.tokenBufferLength > 0 || prevWasEnclosed) {
                        text = reader.tokenBufferString();
                    }
                    reader.tokenBufferLength = 0;
                    obj = readEscapedExpression(reader, next);
                } else {
                    obj = readEntity(reader, next);
                    if (prevWasEnclosed && reader.tokenBufferLength == 0) {
                        text = ElementType.MATCH_ANY_LOCALNAME;
                    }
                }
                prevWasEnclosed = true;
            } else {
                if (delimiter != '<' && (next == '\t' || next == '\n' || next == '\r')) {
                    next = 32;
                }
                if (next == 60) {
                    reader.error('e', "'<' must be quoted in a direct attribute value");
                }
                reader.tokenBufferAppend((char) next);
            }
            if (obj != null && reader.tokenBufferLength > 0) {
                text = reader.tokenBufferString();
                reader.tokenBufferLength = 0;
            }
            if (text != null) {
                Pair pair = PairWithPosition.make(LList.list2(MakeText.makeText, text), reader.makeNil(), null, -1, -1);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
            if (obj == Special.eof) {
                return resultTail;
            }
            if (obj != null) {
                pair = PairWithPosition.make(obj, reader.makeNil(), null, line, column);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void readCharRef(gnu.kawa.lispexpr.LispReader r7) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r3 = r7.read();
        r5 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r3 != r5) goto L_0x0025;
    L_0x0008:
        r0 = 16;
        r3 = r7.read();
    L_0x000e:
        r4 = 0;
    L_0x000f:
        if (r3 < 0) goto L_0x0018;
    L_0x0011:
        r1 = (char) r3;
        r2 = java.lang.Character.digit(r1, r0);
        if (r2 >= 0) goto L_0x0028;
    L_0x0018:
        r5 = 59;
        if (r3 == r5) goto L_0x0033;
    L_0x001c:
        r7.unread(r3);
        r5 = "invalid character reference";
        r7.error(r5);
    L_0x0024:
        return;
    L_0x0025:
        r0 = 10;
        goto L_0x000e;
    L_0x0028:
        r5 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        if (r4 >= r5) goto L_0x0018;
    L_0x002c:
        r4 = r4 * r0;
        r4 = r4 + r2;
        r3 = r7.read();
        goto L_0x000f;
    L_0x0033:
        if (r4 <= 0) goto L_0x003a;
    L_0x0035:
        r5 = 55295; // 0xd7ff float:7.7485E-41 double:2.73194E-319;
        if (r4 <= r5) goto L_0x004d;
    L_0x003a:
        r5 = 57344; // 0xe000 float:8.0356E-41 double:2.83317E-319;
        if (r4 < r5) goto L_0x0044;
    L_0x003f:
        r5 = 65533; // 0xfffd float:9.1831E-41 double:3.23776E-319;
        if (r4 <= r5) goto L_0x004d;
    L_0x0044:
        r5 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r4 < r5) goto L_0x0051;
    L_0x0048:
        r5 = 1114111; // 0x10ffff float:1.561202E-39 double:5.50444E-318;
        if (r4 > r5) goto L_0x0051;
    L_0x004d:
        r7.tokenBufferAppend(r4);
        goto L_0x0024;
    L_0x0051:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "invalid character value ";
        r5 = r5.append(r6);
        r5 = r5.append(r4);
        r5 = r5.toString();
        r7.error(r5);
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderXmlElement.readCharRef(gnu.kawa.lispexpr.LispReader):void");
    }

    static Object readEntity(LispReader reader, int next) throws IOException, SyntaxException {
        String result = "?";
        int saveLength = reader.tokenBufferLength;
        while (next >= 0) {
            char ch = (char) next;
            if (!XName.isNamePart(ch)) {
                break;
            }
            reader.tokenBufferAppend(ch);
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid entity reference");
            return result;
        }
        String ref = new String(reader.tokenBuffer, saveLength, reader.tokenBufferLength - saveLength);
        reader.tokenBufferLength = saveLength;
        namedEntity(reader, ref);
        return null;
    }

    public static void namedEntity(LispReader reader, String name) {
        name = name.intern();
        char ch = '?';
        if (name == "lt") {
            ch = '<';
        } else if (name == "gt") {
            ch = '>';
        } else if (name == "amp") {
            ch = '&';
        } else if (name == "quot") {
            ch = '\"';
        } else if (name == "apos") {
            ch = '\'';
        } else {
            reader.error("unknown enity reference: '" + name + "'");
        }
        reader.tokenBufferAppend(ch);
    }

    static int skipSpace(LispReader reader, int ch) throws IOException, SyntaxException {
        while (ch >= 0 && Character.isWhitespace(ch)) {
            ch = reader.readUnicodeChar();
        }
        return ch;
    }
}
