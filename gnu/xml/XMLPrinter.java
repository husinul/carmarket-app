package gnu.xml;

import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumable;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.math.DFloNum;
import gnu.math.RealNum;
import gnu.text.Char;
import gnu.text.Path;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class XMLPrinter extends OutPort implements PositionConsumer, XConsumer {
    private static final int COMMENT = -5;
    private static final int ELEMENT_END = -4;
    private static final int ELEMENT_START = -3;
    static final String HtmlEmptyTags = "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/";
    private static final int KEYWORD = -6;
    private static final int PROC_INST = -7;
    private static final int WORD = -2;
    public static final ThreadLocation doctypePublic;
    public static final ThreadLocation doctypeSystem;
    public static final ThreadLocation indentLoc;
    boolean canonicalize;
    public boolean canonicalizeCDATA;
    Object[] elementNameStack;
    int elementNesting;
    public boolean escapeNonAscii;
    public boolean escapeText;
    boolean inAttribute;
    int inComment;
    boolean inDocument;
    boolean inStartTag;
    public boolean indentAttributes;
    boolean isHtml;
    boolean isHtmlOrXhtml;
    NamespaceBinding namespaceBindings;
    NamespaceBinding[] namespaceSaveStack;
    boolean needXMLdecl;
    int prev;
    public int printIndent;
    boolean printXMLdecl;
    char savedHighSurrogate;
    public boolean strict;
    Object style;
    boolean undeclareNamespaces;
    public int useEmptyElementTag;

    public void setPrintXMLdecl(boolean value) {
        this.printXMLdecl = value;
    }

    static {
        doctypeSystem = new ThreadLocation("doctype-system");
        doctypePublic = new ThreadLocation("doctype-public");
        indentLoc = new ThreadLocation("xml-indent");
    }

    public XMLPrinter(OutPort out, boolean autoFlush) {
        super(out, autoFlush);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(OutputStream out, boolean autoFlush) {
        super(new OutputStreamWriter(out), true, autoFlush);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(Writer out) {
        super(out);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(OutputStream out) {
        super(new OutputStreamWriter(out), false, false);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(OutputStream out, Path path) {
        super(new OutputStreamWriter(out), true, false, path);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public static XMLPrinter make(OutPort out, Object style) {
        XMLPrinter xout = new XMLPrinter(out, true);
        xout.setStyle(style);
        return xout;
    }

    public static String toString(Object value) {
        Writer stringWriter = new StringWriter();
        new XMLPrinter(stringWriter).writeObject(value);
        return stringWriter.toString();
    }

    public void setStyle(Object style) {
        this.style = style;
        this.useEmptyElementTag = this.canonicalize ? 0 : 1;
        if ("html".equals(style)) {
            this.isHtml = true;
            this.isHtmlOrXhtml = true;
            this.useEmptyElementTag = 2;
            if (this.namespaceBindings == NamespaceBinding.predefinedXML) {
                this.namespaceBindings = XmlNamespace.HTML_BINDINGS;
            }
        } else if (this.namespaceBindings == XmlNamespace.HTML_BINDINGS) {
            this.namespaceBindings = NamespaceBinding.predefinedXML;
        }
        if ("xhtml".equals(style)) {
            this.isHtmlOrXhtml = true;
            this.useEmptyElementTag = 2;
        }
        if ("plain".equals(style)) {
            this.escapeText = false;
        }
    }

    boolean mustHexEscape(int v) {
        return (v >= 127 && (v <= 159 || this.escapeNonAscii)) || v == 8232 || (v < 32 && (this.inAttribute || !(v == 9 || v == 10)));
    }

    public void write(int v) {
        closeTag();
        if (this.printIndent >= 0 && (v == 13 || v == 10)) {
            if (!(v == 10 && this.prev == 13)) {
                writeBreak(82);
            }
            if (this.inComment > 0) {
                this.inComment = 1;
            }
        } else if (!this.escapeText) {
            this.bout.write(v);
            this.prev = v;
        } else if (this.inComment > 0) {
            if (v != 45) {
                this.inComment = 1;
            } else if (this.inComment == 1) {
                this.inComment = 2;
            } else {
                this.bout.write(32);
            }
            super.write(v);
        } else {
            this.prev = 59;
            if (v == 60 && (!this.isHtml || !this.inAttribute)) {
                this.bout.write("&lt;");
            } else if (v == 62) {
                this.bout.write("&gt;");
            } else if (v == 38) {
                this.bout.write("&amp;");
            } else if (v == 34 && this.inAttribute) {
                this.bout.write("&quot;");
            } else if (mustHexEscape(v)) {
                int i = v;
                if (v >= 55296) {
                    if (v < 56320) {
                        this.savedHighSurrogate = (char) v;
                        return;
                    } else if (v < 57344) {
                        i = (((this.savedHighSurrogate - 55296) * LambdaExp.SEQUENCE_RESULT) + (i - 56320)) + ModuleExp.NONSTATIC_SPECIFIED;
                        this.savedHighSurrogate = '\u0000';
                    }
                }
                this.bout.write("&#x" + Integer.toHexString(i).toUpperCase() + ";");
            } else {
                this.bout.write(v);
                this.prev = v;
            }
        }
    }

    private void startWord() {
        closeTag();
        writeWordStart();
    }

    public void writeBoolean(boolean v) {
        startWord();
        super.print(v);
        writeWordEnd();
    }

    protected void startNumber() {
        startWord();
    }

    protected void endNumber() {
        writeWordEnd();
    }

    public void closeTag() {
        if (this.inStartTag && !this.inAttribute) {
            if (this.printIndent >= 0 && this.indentAttributes) {
                endLogicalBlock(ElementType.MATCH_ANY_LOCALNAME);
            }
            this.bout.write(62);
            this.inStartTag = false;
            this.prev = ELEMENT_START;
        } else if (this.needXMLdecl) {
            this.bout.write("<?xml version=\"1.0\"?>\n");
            if (this.printIndent >= 0) {
                startLogicalBlock(ElementType.MATCH_ANY_LOCALNAME, ElementType.MATCH_ANY_LOCALNAME, 2);
            }
            this.needXMLdecl = false;
            this.prev = 62;
        }
    }

    void setIndentMode() {
        String indent = null;
        Object xmlIndent = indentLoc.get(null);
        if (xmlIndent != null) {
            indent = xmlIndent.toString();
        }
        if (indent == null) {
            this.printIndent = -1;
        } else if (indent.equals("pretty")) {
            this.printIndent = 0;
        } else if (indent.equals("always") || indent.equals("yes")) {
            this.printIndent = 1;
        } else {
            this.printIndent = -1;
        }
    }

    public void startDocument() {
        if (this.printXMLdecl) {
            this.needXMLdecl = true;
        }
        setIndentMode();
        this.inDocument = true;
        if (this.printIndent >= 0 && !this.needXMLdecl) {
            startLogicalBlock(ElementType.MATCH_ANY_LOCALNAME, ElementType.MATCH_ANY_LOCALNAME, 2);
        }
    }

    public void endDocument() {
        this.inDocument = false;
        if (this.printIndent >= 0) {
            endLogicalBlock(ElementType.MATCH_ANY_LOCALNAME);
        }
        freshLine();
    }

    public void beginEntity(Object base) {
    }

    public void endEntity() {
    }

    protected void writeQName(Object name) {
        if (name instanceof Symbol) {
            Symbol sname = (Symbol) name;
            String prefix = sname.getPrefix();
            if (prefix != null && prefix.length() > 0) {
                this.bout.write(prefix);
                this.bout.write(58);
            }
            this.bout.write(sname.getLocalPart());
            return;
        }
        this.bout.write(name == null ? "{null name}" : (String) name);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startElement(java.lang.Object r29) {
        /*
        r28 = this;
        r28.closeTag();
        r0 = r28;
        r0 = r0.elementNesting;
        r24 = r0;
        if (r24 != 0) goto L_0x00a8;
    L_0x000b:
        r0 = r28;
        r0 = r0.inDocument;
        r24 = r0;
        if (r24 != 0) goto L_0x0016;
    L_0x0013:
        r28.setIndentMode();
    L_0x0016:
        r0 = r28;
        r0 = r0.prev;
        r24 = r0;
        r25 = -7;
        r0 = r24;
        r1 = r25;
        if (r0 != r1) goto L_0x002d;
    L_0x0024:
        r24 = 10;
        r0 = r28;
        r1 = r24;
        r0.write(r1);
    L_0x002d:
        r24 = doctypeSystem;
        r25 = 0;
        r21 = r24.get(r25);
        if (r21 == 0) goto L_0x00a8;
    L_0x0037:
        r20 = r21.toString();
        r24 = r20.length();
        if (r24 <= 0) goto L_0x00a8;
    L_0x0041:
        r24 = doctypePublic;
        r25 = 0;
        r16 = r24.get(r25);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "<!DOCTYPE ";
        r24.write(r25);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = r29.toString();
        r24.write(r25);
        if (r16 != 0) goto L_0x019d;
    L_0x0063:
        r15 = 0;
    L_0x0064:
        if (r15 == 0) goto L_0x01a3;
    L_0x0066:
        r24 = r15.length();
        if (r24 <= 0) goto L_0x01a3;
    L_0x006c:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = " PUBLIC \"";
        r24.write(r25);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r0 = r24;
        r0.write(r15);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "\" \"";
        r24.write(r25);
    L_0x008d:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r0 = r24;
        r1 = r20;
        r0.write(r1);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "\">";
        r24.write(r25);
        r28.println();
    L_0x00a8:
        r0 = r28;
        r0 = r0.printIndent;
        r24 = r0;
        if (r24 < 0) goto L_0x00fc;
    L_0x00b0:
        r0 = r28;
        r0 = r0.prev;
        r24 = r0;
        r25 = -3;
        r0 = r24;
        r1 = r25;
        if (r0 == r1) goto L_0x00da;
    L_0x00be:
        r0 = r28;
        r0 = r0.prev;
        r24 = r0;
        r25 = -4;
        r0 = r24;
        r1 = r25;
        if (r0 == r1) goto L_0x00da;
    L_0x00cc:
        r0 = r28;
        r0 = r0.prev;
        r24 = r0;
        r25 = -5;
        r0 = r24;
        r1 = r25;
        if (r0 != r1) goto L_0x00eb;
    L_0x00da:
        r0 = r28;
        r0 = r0.printIndent;
        r24 = r0;
        if (r24 <= 0) goto L_0x01b0;
    L_0x00e2:
        r24 = 82;
    L_0x00e4:
        r0 = r28;
        r1 = r24;
        r0.writeBreak(r1);
    L_0x00eb:
        r24 = "";
        r25 = "";
        r26 = 2;
        r0 = r28;
        r1 = r24;
        r2 = r25;
        r3 = r26;
        r0.startLogicalBlock(r1, r2, r3);
    L_0x00fc:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = 60;
        r24.write(r25);
        r28.writeQName(r29);
        r0 = r28;
        r0 = r0.printIndent;
        r24 = r0;
        if (r24 < 0) goto L_0x012b;
    L_0x0112:
        r0 = r28;
        r0 = r0.indentAttributes;
        r24 = r0;
        if (r24 == 0) goto L_0x012b;
    L_0x011a:
        r24 = "";
        r25 = "";
        r26 = 2;
        r0 = r28;
        r1 = r24;
        r2 = r25;
        r3 = r26;
        r0.startLogicalBlock(r1, r2, r3);
    L_0x012b:
        r0 = r28;
        r0 = r0.elementNameStack;
        r24 = r0;
        r0 = r28;
        r0 = r0.elementNesting;
        r25 = r0;
        r24[r25] = r29;
        r4 = 0;
        r0 = r28;
        r0 = r0.namespaceSaveStack;
        r24 = r0;
        r0 = r28;
        r0 = r0.elementNesting;
        r25 = r0;
        r26 = r25 + 1;
        r0 = r26;
        r1 = r28;
        r1.elementNesting = r0;
        r0 = r28;
        r0 = r0.namespaceBindings;
        r26 = r0;
        r24[r25] = r26;
        r0 = r29;
        r0 = r0 instanceof gnu.xml.XName;
        r24 = r0;
        if (r24 == 0) goto L_0x02bd;
    L_0x015e:
        r24 = r29;
        r24 = (gnu.xml.XName) r24;
        r0 = r24;
        r4 = r0.namespaceNodes;
        r0 = r28;
        r0 = r0.namespaceBindings;
        r24 = r0;
        r0 = r24;
        r7 = gnu.xml.NamespaceBinding.commonAncestor(r4, r0);
        if (r4 != 0) goto L_0x01b4;
    L_0x0174:
        r12 = 0;
    L_0x0175:
        r0 = new gnu.xml.NamespaceBinding[r12];
        r19 = r0;
        r5 = 0;
        r0 = r28;
        r0 = r0.canonicalize;
        r18 = r0;
        r9 = r4;
    L_0x0181:
        if (r9 == r7) goto L_0x01d5;
    L_0x0183:
        r6 = r5;
        r17 = 0;
        r23 = r9.getUri();
        r13 = r9.getPrefix();
    L_0x018e:
        r6 = r6 + -1;
        if (r6 < 0) goto L_0x01bd;
    L_0x0192:
        r10 = r19[r6];
        r14 = r10.getPrefix();
        if (r13 != r14) goto L_0x01b9;
    L_0x019a:
        r9 = r9.next;
        goto L_0x0181;
    L_0x019d:
        r15 = r16.toString();
        goto L_0x0064;
    L_0x01a3:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = " SYSTEM \"";
        r24.write(r25);
        goto L_0x008d;
    L_0x01b0:
        r24 = 78;
        goto L_0x00e4;
    L_0x01b4:
        r12 = r4.count(r7);
        goto L_0x0175;
    L_0x01b9:
        if (r18 == 0) goto L_0x018e;
    L_0x01bb:
        if (r13 != 0) goto L_0x01c6;
    L_0x01bd:
        if (r18 == 0) goto L_0x01d3;
    L_0x01bf:
        r6 = r6 + 1;
    L_0x01c1:
        r19[r6] = r9;
        r5 = r5 + 1;
        goto L_0x019a;
    L_0x01c6:
        if (r14 == 0) goto L_0x01ce;
    L_0x01c8:
        r24 = r13.compareTo(r14);
        if (r24 <= 0) goto L_0x01bd;
    L_0x01ce:
        r24 = r6 + 1;
        r19[r24] = r10;
        goto L_0x018e;
    L_0x01d3:
        r6 = r5;
        goto L_0x01c1;
    L_0x01d5:
        r12 = r5;
        r5 = r12;
    L_0x01d7:
        r5 = r5 + -1;
        if (r5 < 0) goto L_0x0260;
    L_0x01db:
        r9 = r19[r5];
        r13 = r9.prefix;
        r0 = r9.uri;
        r23 = r0;
        r0 = r28;
        r0 = r0.namespaceBindings;
        r24 = r0;
        r0 = r24;
        r24 = r0.resolve(r13);
        r0 = r23;
        r1 = r24;
        if (r0 == r1) goto L_0x01d7;
    L_0x01f5:
        if (r23 != 0) goto L_0x0201;
    L_0x01f7:
        if (r13 == 0) goto L_0x0201;
    L_0x01f9:
        r0 = r28;
        r0 = r0.undeclareNamespaces;
        r24 = r0;
        if (r24 == 0) goto L_0x01d7;
    L_0x0201:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = 32;
        r24.write(r25);
        if (r13 != 0) goto L_0x0249;
    L_0x020e:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "xmlns";
        r24.write(r25);
    L_0x0219:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "=\"";
        r24.write(r25);
        r24 = 1;
        r0 = r24;
        r1 = r28;
        r1.inAttribute = r0;
        if (r23 == 0) goto L_0x0235;
    L_0x022e:
        r0 = r28;
        r1 = r23;
        r0.write(r1);
    L_0x0235:
        r24 = 0;
        r0 = r24;
        r1 = r28;
        r1.inAttribute = r0;
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = 34;
        r24.write(r25);
        goto L_0x01d7;
    L_0x0249:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "xmlns:";
        r24.write(r25);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r0 = r24;
        r0.write(r13);
        goto L_0x0219;
    L_0x0260:
        r0 = r28;
        r0 = r0.undeclareNamespaces;
        r24 = r0;
        if (r24 == 0) goto L_0x02b9;
    L_0x0268:
        r0 = r28;
        r9 = r0.namespaceBindings;
    L_0x026c:
        if (r9 == r7) goto L_0x02b9;
    L_0x026e:
        r13 = r9.prefix;
        r0 = r9.uri;
        r24 = r0;
        if (r24 == 0) goto L_0x029f;
    L_0x0276:
        r24 = r4.resolve(r13);
        if (r24 != 0) goto L_0x029f;
    L_0x027c:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = 32;
        r24.write(r25);
        if (r13 != 0) goto L_0x02a2;
    L_0x0289:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "xmlns";
        r24.write(r25);
    L_0x0294:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "=\"\"";
        r24.write(r25);
    L_0x029f:
        r9 = r9.next;
        goto L_0x026c;
    L_0x02a2:
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r25 = "xmlns:";
        r24.write(r25);
        r0 = r28;
        r0 = r0.bout;
        r24 = r0;
        r0 = r24;
        r0.write(r13);
        goto L_0x0294;
    L_0x02b9:
        r0 = r28;
        r0.namespaceBindings = r4;
    L_0x02bd:
        r0 = r28;
        r0 = r0.elementNesting;
        r24 = r0;
        r0 = r28;
        r0 = r0.namespaceSaveStack;
        r25 = r0;
        r0 = r25;
        r0 = r0.length;
        r25 = r0;
        r0 = r24;
        r1 = r25;
        if (r0 < r1) goto L_0x032a;
    L_0x02d4:
        r0 = r28;
        r0 = r0.elementNesting;
        r24 = r0;
        r24 = r24 * 2;
        r0 = r24;
        r11 = new gnu.xml.NamespaceBinding[r0];
        r0 = r28;
        r0 = r0.namespaceSaveStack;
        r24 = r0;
        r25 = 0;
        r26 = 0;
        r0 = r28;
        r0 = r0.elementNesting;
        r27 = r0;
        r0 = r24;
        r1 = r25;
        r2 = r26;
        r3 = r27;
        java.lang.System.arraycopy(r0, r1, r11, r2, r3);
        r0 = r28;
        r0.namespaceSaveStack = r11;
        r0 = r28;
        r0 = r0.elementNesting;
        r24 = r0;
        r24 = r24 * 2;
        r0 = r24;
        r8 = new java.lang.Object[r0];
        r0 = r28;
        r0 = r0.elementNameStack;
        r24 = r0;
        r25 = 0;
        r26 = 0;
        r0 = r28;
        r0 = r0.elementNesting;
        r27 = r0;
        r0 = r24;
        r1 = r25;
        r2 = r26;
        r3 = r27;
        java.lang.System.arraycopy(r0, r1, r8, r2, r3);
        r0 = r28;
        r0.elementNameStack = r8;
    L_0x032a:
        r24 = 1;
        r0 = r24;
        r1 = r28;
        r1.inStartTag = r0;
        r22 = r28.getHtmlTag(r29);
        r24 = "script";
        r0 = r24;
        r1 = r22;
        r24 = r0.equals(r1);
        if (r24 != 0) goto L_0x034e;
    L_0x0342:
        r24 = "style";
        r0 = r24;
        r1 = r22;
        r24 = r0.equals(r1);
        if (r24 == 0) goto L_0x0356;
    L_0x034e:
        r24 = 0;
        r0 = r24;
        r1 = r28;
        r1.escapeText = r0;
    L_0x0356:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLPrinter.startElement(java.lang.Object):void");
    }

    public static boolean isHtmlEmptyElementTag(String name) {
        int index = HtmlEmptyTags.indexOf(name);
        return index > 0 && HtmlEmptyTags.charAt(index - 1) == '/' && HtmlEmptyTags.charAt(name.length() + index) == '/';
    }

    protected String getHtmlTag(Object type) {
        if (type instanceof Symbol) {
            Symbol sym = (Symbol) type;
            String uri = sym.getNamespaceURI();
            if (uri == XmlNamespace.XHTML_NAMESPACE || (this.isHtmlOrXhtml && uri == ElementType.MATCH_ANY_LOCALNAME)) {
                return sym.getLocalPart();
            }
        } else if (this.isHtmlOrXhtml) {
            return type.toString();
        }
        return null;
    }

    public void endElement() {
        if (this.useEmptyElementTag == 0) {
            closeTag();
        }
        Symbol type = this.elementNameStack[this.elementNesting - 1];
        String typeName = getHtmlTag(type);
        if (this.inStartTag) {
            boolean isEmpty;
            if (this.printIndent >= 0 && this.indentAttributes) {
                endLogicalBlock(ElementType.MATCH_ANY_LOCALNAME);
            }
            String end = null;
            if (typeName == null || !isHtmlEmptyElementTag(typeName)) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
            if ((this.useEmptyElementTag == 0 || !(typeName == null || isEmpty)) && (type instanceof Symbol)) {
                Symbol sym = type;
                String prefix = sym.getPrefix();
                String uri = sym.getNamespaceURI();
                String local = sym.getLocalName();
                if (prefix != ElementType.MATCH_ANY_LOCALNAME) {
                    end = "></" + prefix + ":" + local + ">";
                } else if (uri == ElementType.MATCH_ANY_LOCALNAME || uri == null) {
                    end = "></" + local + ">";
                }
            }
            if (end == null) {
                end = (isEmpty && this.isHtml) ? ">" : this.useEmptyElementTag == 2 ? " />" : "/>";
            }
            this.bout.write(end);
            this.inStartTag = false;
        } else {
            if (this.printIndent >= 0) {
                setIndentation(0, false);
                if (this.prev == ELEMENT_END) {
                    writeBreak(this.printIndent > 0 ? 82 : 78);
                }
            }
            this.bout.write("</");
            writeQName(type);
            this.bout.write(">");
        }
        if (this.printIndent >= 0) {
            endLogicalBlock(ElementType.MATCH_ANY_LOCALNAME);
        }
        this.prev = ELEMENT_END;
        if (!(typeName == null || this.escapeText || (!"script".equals(typeName) && !"style".equals(typeName)))) {
            this.escapeText = true;
        }
        NamespaceBinding[] namespaceBindingArr = this.namespaceSaveStack;
        int i = this.elementNesting - 1;
        this.elementNesting = i;
        this.namespaceBindings = namespaceBindingArr[i];
        this.namespaceSaveStack[this.elementNesting] = null;
        this.elementNameStack[this.elementNesting] = null;
    }

    public void startAttribute(Object attrType) {
        if (!this.inStartTag && this.strict) {
            error("attribute not in element", "SENR0001");
        }
        if (this.inAttribute) {
            this.bout.write(34);
        }
        this.inAttribute = true;
        this.bout.write(32);
        if (this.printIndent >= 0) {
            writeBreakFill();
        }
        this.bout.write(attrType.toString());
        this.bout.write("=\"");
        this.prev = 32;
    }

    public void endAttribute() {
        if (this.inAttribute) {
            if (this.prev != KEYWORD) {
                this.bout.write(34);
                this.inAttribute = false;
            }
            this.prev = 32;
        }
    }

    public void writeDouble(double d) {
        startWord();
        this.bout.write(formatDouble(d));
    }

    public void writeFloat(float f) {
        startWord();
        this.bout.write(formatFloat(f));
    }

    public static String formatDouble(double d) {
        if (Double.isNaN(d)) {
            return "NaN";
        }
        boolean neg = d < 0.0d;
        if (Double.isInfinite(d)) {
            return neg ? "-INF" : "INF";
        } else {
            double dabs;
            if (neg) {
                dabs = -d;
            } else {
                dabs = d;
            }
            String dstr = Double.toString(d);
            if ((dabs >= 1000000.0d || dabs < 1.0E-6d) && dabs != 0.0d) {
                return RealNum.toStringScientific(dstr);
            }
            return formatDecimal(RealNum.toStringDecimal(dstr));
        }
    }

    public static String formatFloat(float f) {
        if (Float.isNaN(f)) {
            return "NaN";
        }
        boolean neg = f < 0.0f;
        if (Float.isInfinite(f)) {
            return neg ? "-INF" : "INF";
        } else {
            float fabs;
            if (neg) {
                fabs = -f;
            } else {
                fabs = f;
            }
            String fstr = Float.toString(f);
            if ((fabs >= 1000000.0f || ((double) fabs) < 1.0E-6d) && ((double) fabs) != 0.0d) {
                return RealNum.toStringScientific(fstr);
            }
            return formatDecimal(RealNum.toStringDecimal(fstr));
        }
    }

    public static String formatDecimal(BigDecimal dec) {
        return formatDecimal(dec.toPlainString());
    }

    static String formatDecimal(String str) {
        if (str.indexOf(46) < 0) {
            return str;
        }
        char ch;
        int len = str.length();
        int pos = len;
        do {
            pos--;
            ch = str.charAt(pos);
        } while (ch == '0');
        if (ch != '.') {
            pos++;
        }
        if (pos == len) {
            return str;
        }
        return str.substring(0, pos);
    }

    public void print(Object v) {
        String v2;
        if (v instanceof BigDecimal) {
            v2 = formatDecimal((BigDecimal) v);
        } else if ((v instanceof Double) || (v instanceof DFloNum)) {
            v2 = formatDouble(((Number) v).doubleValue());
        } else if (v instanceof Float) {
            v2 = formatFloat(((Float) v).floatValue());
        }
        write(v2 == null ? "(null)" : v2.toString());
    }

    public void writeObject(Object v) {
        if (v instanceof SeqPosition) {
            this.bout.clearWordEnd();
            SeqPosition pos = (SeqPosition) v;
            pos.sequence.consumeNext(pos.ipos, this);
            if (pos.sequence instanceof NodeTree) {
                this.prev = 45;
            }
        } else if ((v instanceof Consumable) && !(v instanceof UnescapedData)) {
            ((Consumable) v).consume(this);
        } else if (v instanceof Keyword) {
            startAttribute(((Keyword) v).getName());
            this.prev = KEYWORD;
        } else {
            closeTag();
            if (v instanceof UnescapedData) {
                this.bout.clearWordEnd();
                this.bout.write(((UnescapedData) v).getData());
                this.prev = 45;
            } else if (v instanceof Char) {
                Char.print(((Char) v).intValue(), this);
            } else {
                startWord();
                this.prev = 32;
                print(v);
                writeWordEnd();
                this.prev = WORD;
            }
        }
    }

    public boolean ignoring() {
        return false;
    }

    public void write(String str, int start, int length) {
        if (length > 0) {
            closeTag();
            int limit = start + length;
            int count = 0;
            int start2 = start;
            while (start2 < limit) {
                start = start2 + 1;
                char c = str.charAt(start2);
                if (mustHexEscape(c) || (this.inComment <= 0 ? c == '<' || c == '>' || c == '&' || (this.inAttribute && (c == '\"' || c < ' ')) : c == '-' || this.inComment == 2)) {
                    if (count > 0) {
                        this.bout.write(str, (start - 1) - count, count);
                    }
                    write(c);
                    count = 0;
                } else {
                    count++;
                }
                start2 = start;
            }
            if (count > 0) {
                this.bout.write(str, limit - count, count);
            }
            start = start2;
        }
        this.prev = 45;
    }

    public void write(char[] buf, int off, int len) {
        if (len > 0) {
            closeTag();
            int limit = off + len;
            int count = 0;
            int off2 = off;
            while (off2 < limit) {
                off = off2 + 1;
                char c = buf[off2];
                if (mustHexEscape(c) || (this.inComment <= 0 ? c == '<' || c == '>' || c == '&' || (this.inAttribute && (c == '\"' || c < ' ')) : c == '-' || this.inComment == 2)) {
                    if (count > 0) {
                        this.bout.write(buf, (off - 1) - count, count);
                    }
                    write(c);
                    count = 0;
                } else {
                    count++;
                }
                off2 = off;
            }
            if (count > 0) {
                this.bout.write(buf, limit - count, count);
            }
            off = off2;
        }
        this.prev = 45;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        seq.consumeNext(ipos, this);
    }

    public void writeBaseUri(Object uri) {
    }

    public void beginComment() {
        closeTag();
        if (this.printIndent >= 0 && (this.prev == ELEMENT_START || this.prev == ELEMENT_END || this.prev == COMMENT)) {
            writeBreak(this.printIndent > 0 ? 82 : 78);
        }
        this.bout.write("<!--");
        this.inComment = 1;
    }

    public void endComment() {
        this.bout.write("-->");
        this.prev = COMMENT;
        this.inComment = 0;
    }

    public void writeComment(String chars) {
        beginComment();
        write(chars);
        endComment();
    }

    public void writeComment(char[] chars, int offset, int length) {
        beginComment();
        write(chars, offset, length);
        endComment();
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        if (this.canonicalizeCDATA) {
            write(chars, offset, length);
            return;
        }
        closeTag();
        this.bout.write("<![CDATA[");
        int limit = offset + length;
        int i = offset;
        while (i < limit + WORD) {
            if (chars[i] == ']' && chars[i + 1] == ']' && chars[i + 2] == '>') {
                if (i > offset) {
                    this.bout.write(chars, offset, i - offset);
                }
                print("]]]><![CDATA[]>");
                offset = i + 3;
                length = limit - offset;
                i += 2;
            }
            i++;
        }
        this.bout.write(chars, offset, length);
        this.bout.write("]]>");
        this.prev = 62;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        if ("xml".equals(target)) {
            this.needXMLdecl = false;
        }
        closeTag();
        this.bout.write("<?");
        print(target);
        print(' ');
        this.bout.write(content, offset, length);
        this.bout.write("?>");
        this.prev = PROC_INST;
    }

    public void consume(SeqPosition position) {
        position.sequence.consumeNext(position.ipos, this);
    }

    public void error(String msg, String code) {
        throw new RuntimeException("serialization error: " + msg + " [" + code + ']');
    }
}
