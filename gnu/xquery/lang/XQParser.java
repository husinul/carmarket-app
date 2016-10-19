package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.reflect.SingletonType;
import gnu.kawa.slib.srfi1;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeWithBaseUri;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.ParentAxis;
import gnu.kawa.xml.ProcessingInstructionType;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.Sequence;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.TtyInPort;
import gnu.mapping.WrappedException;
import gnu.math.DateTime;
import gnu.math.IntNum;
import gnu.text.FilePath;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SourceError;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.text.URIPath;
import gnu.xml.NamespaceBinding;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.util.CastableAs;
import gnu.xquery.util.NamedCollator;
import gnu.xquery.util.QNameUtils;
import gnu.xquery.util.RelativeStep;
import gnu.xquery.util.ValuesFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Stack;
import java.util.Vector;

public class XQParser extends Lexer {
    static final int ARROW_TOKEN = 82;
    static final int ATTRIBUTE_TOKEN = 252;
    static final int AXIS_ANCESTOR = 0;
    static final int AXIS_ANCESTOR_OR_SELF = 1;
    static final int AXIS_ATTRIBUTE = 2;
    static final int AXIS_CHILD = 3;
    static final int AXIS_DESCENDANT = 4;
    static final int AXIS_DESCENDANT_OR_SELF = 5;
    static final int AXIS_FOLLOWING = 6;
    static final int AXIS_FOLLOWING_SIBLING = 7;
    static final int AXIS_NAMESPACE = 8;
    static final int AXIS_PARENT = 9;
    static final int AXIS_PRECEDING = 10;
    static final int AXIS_PRECEDING_SIBLING = 11;
    static final int AXIS_SELF = 12;
    static final int CASE_DOLLAR_TOKEN = 247;
    static final int COLON_COLON_TOKEN = 88;
    static final int COLON_EQUAL_TOKEN = 76;
    static final int COMMENT_TOKEN = 254;
    static final int COUNT_OP_AXIS = 13;
    static final char DECIMAL_TOKEN = '1';
    static final int DECLARE_BASE_URI_TOKEN = 66;
    static final int DECLARE_BOUNDARY_SPACE_TOKEN = 83;
    static final int DECLARE_CONSTRUCTION_TOKEN = 75;
    static final int DECLARE_COPY_NAMESPACES_TOKEN = 76;
    static final int DECLARE_FUNCTION_TOKEN = 80;
    static final int DECLARE_NAMESPACE_TOKEN = 78;
    static final int DECLARE_OPTION_TOKEN = 111;
    static final int DECLARE_ORDERING_TOKEN = 85;
    static final int DECLARE_VARIABLE_TOKEN = 86;
    static final int DEFAULT_COLLATION_TOKEN = 71;
    static final int DEFAULT_ELEMENT_TOKEN = 69;
    static final int DEFAULT_FUNCTION_TOKEN = 79;
    static final int DEFAULT_ORDER_TOKEN = 72;
    static final int DEFINE_QNAME_TOKEN = 87;
    static final int DOCUMENT_TOKEN = 256;
    static final int DOTDOT_TOKEN = 51;
    static final Symbol DOT_VARNAME;
    static final char DOUBLE_TOKEN = '2';
    static final int ELEMENT_TOKEN = 251;
    static final int EOF_TOKEN = -1;
    static final int EOL_TOKEN = 10;
    static final int EVERY_DOLLAR_TOKEN = 246;
    static final int FNAME_TOKEN = 70;
    static final int FOR_DOLLAR_TOKEN = 243;
    static final int IF_LPAREN_TOKEN = 241;
    static final int IMPORT_MODULE_TOKEN = 73;
    static final int IMPORT_SCHEMA_TOKEN = 84;
    static final char INTEGER_TOKEN = '0';
    static final Symbol LAST_VARNAME;
    static final int LET_DOLLAR_TOKEN = 244;
    static final int MODULE_NAMESPACE_TOKEN = 77;
    static final int NCNAME_COLON_TOKEN = 67;
    static final int NCNAME_TOKEN = 65;
    static final int OP_ADD = 413;
    static final int OP_AND = 401;
    static final int OP_ATTRIBUTE = 236;
    static final int OP_AXIS_FIRST = 100;
    static final int OP_BASE = 400;
    static final int OP_CASTABLE_AS = 424;
    static final int OP_CAST_AS = 425;
    static final int OP_COMMENT = 232;
    static final int OP_DIV = 416;
    static final int OP_DOCUMENT = 234;
    static final int OP_ELEMENT = 235;
    static final int OP_EMPTY_SEQUENCE = 238;
    static final int OP_EQ = 426;
    static final int OP_EQU = 402;
    static final int OP_EXCEPT = 421;
    static final int OP_GE = 431;
    static final int OP_GEQ = 407;
    static final int OP_GRT = 405;
    static final int OP_GRTGRT = 410;
    static final int OP_GT = 430;
    static final int OP_IDIV = 417;
    static final int OP_INSTANCEOF = 422;
    static final int OP_INTERSECT = 420;
    static final int OP_IS = 408;
    static final int OP_ISNOT = 409;
    static final int OP_ITEM = 237;
    static final int OP_LE = 429;
    static final int OP_LEQ = 406;
    static final int OP_LSS = 404;
    static final int OP_LSSLSS = 411;
    static final int OP_LT = 428;
    static final int OP_MOD = 418;
    static final int OP_MUL = 415;
    static final int OP_NE = 427;
    static final int OP_NEQ = 403;
    static final int OP_NODE = 230;
    static final int OP_OR = 400;
    static final int OP_PI = 233;
    static final int OP_RANGE_TO = 412;
    static final int OP_SCHEMA_ATTRIBUTE = 239;
    static final int OP_SCHEMA_ELEMENT = 240;
    static final int OP_SUB = 414;
    static final int OP_TEXT = 231;
    static final int OP_TREAT_AS = 423;
    static final int OP_UNION = 419;
    static final int OP_WHERE = 196;
    static final int ORDERED_LBRACE_TOKEN = 249;
    static final int PI_TOKEN = 255;
    static final Symbol POSITION_VARNAME;
    static final int PRAGMA_START_TOKEN = 197;
    static final int QNAME_TOKEN = 81;
    static final int SLASHSLASH_TOKEN = 68;
    static final int SOME_DOLLAR_TOKEN = 245;
    static final int STRING_TOKEN = 34;
    static final int TEXT_TOKEN = 253;
    static final int TYPESWITCH_LPAREN_TOKEN = 242;
    static final int UNORDERED_LBRACE_TOKEN = 250;
    static final int VALIDATE_LBRACE_TOKEN = 248;
    static final int XQUERY_VERSION_TOKEN = 89;
    public static final String[] axisNames;
    static NamespaceBinding builtinNamespaces;
    public static final CastableAs castableAs;
    public static final QuoteExp getExternalFunction;
    public static final InstanceOf instanceOf;
    static final Expression makeCDATA;
    public static QuoteExp makeChildAxisStep;
    public static QuoteExp makeDescendantAxisStep;
    public static Expression makeText;
    static PrimProcedure proc_OccurrenceType_getInstance;
    public static final Convert treatAs;
    public static boolean warnHidePreviousDeclaration;
    public static boolean warnOldVersion;
    Path baseURI;
    boolean baseURIDeclarationSeen;
    boolean boundarySpaceDeclarationSeen;
    boolean boundarySpacePreserve;
    int commentCount;
    Compilation comp;
    boolean constructionModeDeclarationSeen;
    boolean constructionModeStrip;
    NamespaceBinding constructorNamespaces;
    boolean copyNamespacesDeclarationSeen;
    int copyNamespacesMode;
    int curColumn;
    int curLine;
    int curToken;
    Object curValue;
    NamedCollator defaultCollator;
    String defaultElementNamespace;
    char defaultEmptyOrder;
    boolean emptyOrderDeclarationSeen;
    int enclosedExpressionsSeen;
    String errorIfComment;
    Declaration[] flworDecls;
    int flworDeclsCount;
    int flworDeclsFirst;
    public Namespace[] functionNamespacePath;
    XQuery interpreter;
    String libraryModuleNamespace;
    boolean orderingModeSeen;
    boolean orderingModeUnordered;
    int parseContext;
    int parseCount;
    NamespaceBinding prologNamespaces;
    private int saveToken;
    private Object saveValue;
    boolean seenDeclaration;
    int seenLast;
    int seenPosition;
    private boolean warnedOldStyleKindTest;

    static {
        warnOldVersion = true;
        warnHidePreviousDeclaration = false;
        DOT_VARNAME = Symbol.makeUninterned("$dot$");
        POSITION_VARNAME = Symbol.makeUninterned("$position$");
        LAST_VARNAME = Symbol.makeUninterned("$last$");
        instanceOf = new InstanceOf(XQuery.getInstance(), "instance");
        castableAs = CastableAs.castableAs;
        treatAs = Convert.as;
        proc_OccurrenceType_getInstance = new PrimProcedure(ClassType.make("gnu.kawa.reflect.OccurrenceType").getDeclaredMethod("getInstance", (int) AXIS_CHILD));
        makeChildAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.ChildAxis", "make", (int) AXIS_ANCESTOR_OR_SELF));
        makeDescendantAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml.DescendantAxis", "make", (int) AXIS_ANCESTOR_OR_SELF));
        makeText = makeFunctionExp("gnu.kawa.xml.MakeText", "makeText");
        makeCDATA = makeFunctionExp("gnu.kawa.xml.MakeCDATA", "makeCDATA");
        builtinNamespaces = new NamespaceBinding("local", XQuery.LOCAL_NAMESPACE, new NamespaceBinding("qexo", XQuery.QEXO_FUNCTION_NAMESPACE, new NamespaceBinding("kawa", XQuery.KAWA_FUNCTION_NAMESPACE, new NamespaceBinding("html", XmlNamespace.XHTML_NAMESPACE, new NamespaceBinding("fn", XQuery.XQUERY_FUNCTION_NAMESPACE, new NamespaceBinding("xsi", XQuery.SCHEMA_INSTANCE_NAMESPACE, new NamespaceBinding("xs", XQuery.SCHEMA_NAMESPACE, new NamespaceBinding("xml", NamespaceBinding.XML_NAMESPACE, NamespaceBinding.predefinedXML))))))));
        getExternalFunction = QuoteExp.getInstance(new PrimProcedure("gnu.xquery.lang.XQuery", "getExternal", (int) AXIS_ATTRIBUTE));
        axisNames = new String[COUNT_OP_AXIS];
        axisNames[AXIS_ANCESTOR] = "ancestor";
        axisNames[AXIS_ANCESTOR_OR_SELF] = "ancestor-or-self";
        axisNames[AXIS_ATTRIBUTE] = "attribute";
        axisNames[AXIS_CHILD] = "child";
        axisNames[AXIS_DESCENDANT] = "descendant";
        axisNames[AXIS_DESCENDANT_OR_SELF] = "descendant-or-self";
        axisNames[AXIS_FOLLOWING] = "following";
        axisNames[AXIS_FOLLOWING_SIBLING] = "following-sibling";
        axisNames[AXIS_NAMESPACE] = "namespace";
        axisNames[AXIS_PARENT] = "parent";
        axisNames[EOL_TOKEN] = "preceding";
        axisNames[AXIS_PRECEDING_SIBLING] = "preceding-sibling";
        axisNames[AXIS_SELF] = "self";
    }

    public void setStaticBaseUri(String uri) {
        try {
            this.baseURI = fixupStaticBaseUri(URIPath.valueOf(uri));
        } catch (Throwable th) {
            Throwable ex = th;
            if (ex instanceof WrappedException) {
                ex = ((WrappedException) ex).getCause();
            }
            error('e', "invalid URI: " + ex.getMessage());
        }
    }

    static Path fixupStaticBaseUri(Path path) {
        path = path.getAbsolute();
        if (path instanceof FilePath) {
            return URIPath.valueOf(path.toURI());
        }
        return path;
    }

    public String getStaticBaseUri() {
        Path path = this.baseURI;
        if (path == null) {
            Object value = Environment.getCurrent().get(Symbol.make(ElementType.MATCH_ANY_LOCALNAME, "base-uri"), null, null);
            if (!(value == null || (value instanceof Path))) {
                path = URIPath.valueOf(value.toString());
            }
            if (path == null) {
                LineBufferedReader port = getPort();
                if (port != null) {
                    path = port.getPath();
                    if ((path instanceof FilePath) && (!path.exists() || (port instanceof TtyInPort) || (port instanceof CharArrayInPort))) {
                        path = null;
                    }
                }
            }
            if (path == null) {
                path = Path.currentPath();
            }
            path = fixupStaticBaseUri(path);
            this.baseURI = path;
        }
        return path.toString();
    }

    public String resolveAgainstBaseUri(String uri) {
        return Path.uriSchemeSpecified(uri) ? uri : Path.valueOf(getStaticBaseUri()).resolve(uri).toString();
    }

    final int skipSpace() throws IOException, SyntaxException {
        return skipSpace(true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final int skipSpace(boolean r6) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r5 = this;
        r1 = 40;
        r2 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r4 = 45;
    L_0x0006:
        r0 = r5.read();
        if (r0 != r1) goto L_0x0019;
    L_0x000c:
        r3 = 58;
        r3 = r5.checkNext(r3);
        if (r3 != 0) goto L_0x0015;
    L_0x0014:
        return r1;
    L_0x0015:
        r5.skipComment();
        goto L_0x0006;
    L_0x0019:
        if (r0 != r2) goto L_0x0038;
    L_0x001b:
        r0 = r5.read();
        if (r0 == r4) goto L_0x0026;
    L_0x0021:
        r5.unread(r0);
        r1 = r2;
        goto L_0x0014;
    L_0x0026:
        r0 = r5.read();
        if (r0 == r4) goto L_0x0034;
    L_0x002c:
        r5.unread(r0);
        r5.unread(r4);
        r1 = r2;
        goto L_0x0014;
    L_0x0034:
        r5.skipOldComment();
        goto L_0x0006;
    L_0x0038:
        if (r6 == 0) goto L_0x0045;
    L_0x003a:
        if (r0 < 0) goto L_0x0043;
    L_0x003c:
        r3 = (char) r0;
        r3 = java.lang.Character.isWhitespace(r3);
        if (r3 != 0) goto L_0x0006;
    L_0x0043:
        r1 = r0;
        goto L_0x0014;
    L_0x0045:
        r3 = 32;
        if (r0 == r3) goto L_0x0006;
    L_0x0049:
        r3 = 9;
        if (r0 == r3) goto L_0x0006;
    L_0x004d:
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.skipSpace(boolean):int");
    }

    final void skipToSemicolon() throws IOException {
        int next;
        do {
            next = read();
            if (next < 0) {
                return;
            }
        } while (next != 59);
    }

    final void skipOldComment() throws IOException, SyntaxException {
        int seenDashes = AXIS_ANCESTOR;
        int startLine = getLineNumber() + AXIS_ANCESTOR_OR_SELF;
        int startColumn = getColumnNumber() - 2;
        warnOldVersion("use (: :) instead of old-style comment {-- --}");
        while (true) {
            int ch = read();
            if (ch == 45) {
                seenDashes += AXIS_ANCESTOR_OR_SELF;
            } else if (ch == 125 && seenDashes >= AXIS_ATTRIBUTE) {
                return;
            } else {
                if (ch < 0) {
                    this.curLine = startLine;
                    this.curColumn = startColumn;
                    eofError("non-terminated comment starting here");
                } else {
                    seenDashes = AXIS_ANCESTOR;
                }
            }
        }
    }

    final void skipComment() throws IOException, SyntaxException {
        this.commentCount += AXIS_ANCESTOR_OR_SELF;
        int startLine = getLineNumber() + AXIS_ANCESTOR_OR_SELF;
        int startColumn = getColumnNumber() + EOF_TOKEN;
        if (this.errorIfComment != null) {
            this.curLine = startLine;
            this.curColumn = startColumn;
            error('e', this.errorIfComment);
        }
        int prev = AXIS_ANCESTOR;
        int commentNesting = AXIS_ANCESTOR;
        char saveReadState = pushNesting(':');
        while (true) {
            int ch = read();
            if (ch == 58) {
                if (prev == 40) {
                    commentNesting += AXIS_ANCESTOR_OR_SELF;
                    ch = AXIS_ANCESTOR;
                }
            } else if (ch == 41 && prev == 58) {
                if (commentNesting == 0) {
                    popNesting(saveReadState);
                    return;
                }
                commentNesting += EOF_TOKEN;
            } else if (ch < 0) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                eofError("non-terminated comment starting here");
            }
            prev = ch;
        }
    }

    final int peekNonSpace(String message) throws IOException, SyntaxException {
        int ch = skipSpace();
        if (ch < 0) {
            eofError(message);
        }
        unread(ch);
        return ch;
    }

    public void mark() throws IOException {
        super.mark();
        this.saveToken = this.curToken;
        this.saveValue = this.curValue;
    }

    public void reset() throws IOException {
        this.curToken = this.saveToken;
        this.curValue = this.saveValue;
        super.reset();
    }

    private int setToken(int token, int width) {
        this.curToken = token;
        this.curLine = this.port.getLineNumber() + AXIS_ANCESTOR_OR_SELF;
        this.curColumn = (this.port.getColumnNumber() + AXIS_ANCESTOR_OR_SELF) - width;
        return token;
    }

    void checkSeparator(char ch) {
        if (XName.isNameStart(ch)) {
            error('e', "missing separator", "XPST0003");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    int getRawToken() throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r10 = this;
        r9 = 58;
        r4 = 1;
        r5 = 0;
        r8 = 61;
        r7 = 46;
    L_0x0008:
        r2 = r10.readUnicodeChar();
        if (r2 >= 0) goto L_0x0014;
    L_0x000e:
        r6 = -1;
        r0 = r10.setToken(r6, r5);
    L_0x0013:
        return r0;
    L_0x0014:
        r6 = 10;
        if (r2 == r6) goto L_0x001c;
    L_0x0018:
        r6 = 13;
        if (r2 != r6) goto L_0x0027;
    L_0x001c:
        r6 = r10.nesting;
        if (r6 > 0) goto L_0x0008;
    L_0x0020:
        r6 = 10;
        r0 = r10.setToken(r6, r5);
        goto L_0x0013;
    L_0x0027:
        r6 = 40;
        if (r2 != r6) goto L_0x004c;
    L_0x002b:
        r6 = r10.checkNext(r9);
        if (r6 == 0) goto L_0x0035;
    L_0x0031:
        r10.skipComment();
        goto L_0x0008;
    L_0x0035:
        r5 = 35;
        r5 = r10.checkNext(r5);
        if (r5 == 0) goto L_0x0045;
    L_0x003d:
        r5 = 197; // 0xc5 float:2.76E-43 double:9.73E-322;
        r6 = 2;
        r0 = r10.setToken(r5, r6);
        goto L_0x0013;
    L_0x0045:
        r5 = 40;
        r0 = r10.setToken(r5, r4);
        goto L_0x0013;
    L_0x004c:
        r6 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        if (r2 != r6) goto L_0x0078;
    L_0x0050:
        r6 = 45;
        r6 = r10.checkNext(r6);
        if (r6 != 0) goto L_0x005f;
    L_0x0058:
        r5 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r0 = r10.setToken(r5, r4);
        goto L_0x0013;
    L_0x005f:
        r2 = r10.read();
        r6 = 45;
        if (r2 == r6) goto L_0x0074;
    L_0x0067:
        r10.unread();
        r10.unread();
        r5 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r0 = r10.setToken(r5, r4);
        goto L_0x0013;
    L_0x0074:
        r10.skipOldComment();
        goto L_0x0008;
    L_0x0078:
        r6 = 32;
        if (r2 == r6) goto L_0x0008;
    L_0x007c:
        r6 = 9;
        if (r2 == r6) goto L_0x0008;
    L_0x0080:
        r10.tokenBufferLength = r5;
        r6 = r10.port;
        r6 = r6.getLineNumber();
        r6 = r6 + 1;
        r10.curLine = r6;
        r6 = r10.port;
        r6 = r6.getColumnNumber();
        r10.curColumn = r6;
        r0 = (char) r2;
        switch(r0) {
            case 33: goto L_0x0107;
            case 34: goto L_0x0156;
            case 36: goto L_0x00e5;
            case 39: goto L_0x0156;
            case 41: goto L_0x00e5;
            case 42: goto L_0x00fe;
            case 43: goto L_0x0101;
            case 44: goto L_0x00e5;
            case 45: goto L_0x0104;
            case 47: goto L_0x0110;
            case 58: goto L_0x00e9;
            case 59: goto L_0x00e5;
            case 60: goto L_0x013f;
            case 61: goto L_0x011b;
            case 62: goto L_0x0128;
            case 63: goto L_0x00e5;
            case 64: goto L_0x00e5;
            case 91: goto L_0x00e5;
            case 93: goto L_0x00e5;
            case 124: goto L_0x00fb;
            case 125: goto L_0x00e5;
            default: goto L_0x0098;
        };
    L_0x0098:
        r6 = java.lang.Character.isDigit(r0);
        if (r6 != 0) goto L_0x00ab;
    L_0x009e:
        if (r0 != r7) goto L_0x01c4;
    L_0x00a0:
        r6 = r10.peek();
        r6 = (char) r6;
        r6 = java.lang.Character.isDigit(r6);
        if (r6 == 0) goto L_0x01c4;
    L_0x00ab:
        if (r0 != r7) goto L_0x0185;
    L_0x00ad:
        r10.tokenBufferAppend(r0);
        r2 = r10.read();
        if (r2 >= 0) goto L_0x0188;
    L_0x00b6:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r2 == r5) goto L_0x00be;
    L_0x00ba:
        r5 = 69;
        if (r2 != r5) goto L_0x01b2;
    L_0x00be:
        r5 = (char) r2;
        r10.tokenBufferAppend(r5);
        r2 = r10.read();
        r5 = 43;
        if (r2 == r5) goto L_0x00ce;
    L_0x00ca:
        r5 = 45;
        if (r2 != r5) goto L_0x00d5;
    L_0x00ce:
        r10.tokenBufferAppend(r2);
        r2 = r10.read();
    L_0x00d5:
        r1 = 0;
    L_0x00d6:
        if (r2 >= 0) goto L_0x0198;
    L_0x00d8:
        if (r1 != 0) goto L_0x00e3;
    L_0x00da:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r6 = "no digits following exponent";
        r7 = "XPST0003";
        r10.error(r5, r6, r7);
    L_0x00e3:
        r0 = 50;
    L_0x00e5:
        r10.curToken = r0;
        goto L_0x0013;
    L_0x00e9:
        r5 = r10.checkNext(r8);
        if (r5 == 0) goto L_0x00f2;
    L_0x00ef:
        r0 = 76;
        goto L_0x00e5;
    L_0x00f2:
        r5 = r10.checkNext(r9);
        if (r5 == 0) goto L_0x00e5;
    L_0x00f8:
        r0 = 88;
        goto L_0x00e5;
    L_0x00fb:
        r0 = 419; // 0x1a3 float:5.87E-43 double:2.07E-321;
        goto L_0x00e5;
    L_0x00fe:
        r0 = 415; // 0x19f float:5.82E-43 double:2.05E-321;
        goto L_0x00e5;
    L_0x0101:
        r0 = 413; // 0x19d float:5.79E-43 double:2.04E-321;
        goto L_0x00e5;
    L_0x0104:
        r0 = 414; // 0x19e float:5.8E-43 double:2.045E-321;
        goto L_0x00e5;
    L_0x0107:
        r5 = r10.checkNext(r8);
        if (r5 == 0) goto L_0x00e5;
    L_0x010d:
        r0 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        goto L_0x00e5;
    L_0x0110:
        r5 = 47;
        r5 = r10.checkNext(r5);
        if (r5 == 0) goto L_0x00e5;
    L_0x0118:
        r0 = 68;
        goto L_0x00e5;
    L_0x011b:
        r5 = 62;
        r5 = r10.checkNext(r5);
        if (r5 == 0) goto L_0x0125;
    L_0x0123:
        r0 = 82;
    L_0x0125:
        r0 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        goto L_0x00e5;
    L_0x0128:
        r5 = r10.checkNext(r8);
        if (r5 == 0) goto L_0x0131;
    L_0x012e:
        r0 = 407; // 0x197 float:5.7E-43 double:2.01E-321;
    L_0x0130:
        goto L_0x00e5;
    L_0x0131:
        r5 = 62;
        r5 = r10.checkNext(r5);
        if (r5 == 0) goto L_0x013c;
    L_0x0139:
        r0 = 410; // 0x19a float:5.75E-43 double:2.026E-321;
        goto L_0x0130;
    L_0x013c:
        r0 = 405; // 0x195 float:5.68E-43 double:2.0E-321;
        goto L_0x0130;
    L_0x013f:
        r5 = r10.checkNext(r8);
        if (r5 == 0) goto L_0x0148;
    L_0x0145:
        r0 = 406; // 0x196 float:5.69E-43 double:2.006E-321;
    L_0x0147:
        goto L_0x00e5;
    L_0x0148:
        r5 = 60;
        r5 = r10.checkNext(r5);
        if (r5 == 0) goto L_0x0153;
    L_0x0150:
        r0 = 411; // 0x19b float:5.76E-43 double:2.03E-321;
        goto L_0x0147;
    L_0x0153:
        r0 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
        goto L_0x0147;
    L_0x0156:
        r5 = (char) r2;
        r3 = r10.pushNesting(r5);
    L_0x015b:
        r2 = r10.readUnicodeChar();
        if (r2 >= 0) goto L_0x0166;
    L_0x0161:
        r5 = "unexpected end-of-file in string starting here";
        r10.eofError(r5);
    L_0x0166:
        r5 = 38;
        if (r2 != r5) goto L_0x016e;
    L_0x016a:
        r10.parseEntityOrCharRef();
        goto L_0x015b;
    L_0x016e:
        if (r0 != r2) goto L_0x0181;
    L_0x0170:
        r2 = r10.peek();
        if (r0 == r2) goto L_0x017d;
    L_0x0176:
        r10.popNesting(r3);
        r0 = 34;
        goto L_0x00e5;
    L_0x017d:
        r2 = r10.read();
    L_0x0181:
        r10.tokenBufferAppend(r2);
        goto L_0x015b;
    L_0x0185:
        r4 = r5;
        goto L_0x00ad;
    L_0x0188:
        r0 = (char) r2;
        if (r0 != r7) goto L_0x0190;
    L_0x018b:
        if (r4 != 0) goto L_0x00b6;
    L_0x018d:
        r4 = 1;
        goto L_0x00ad;
    L_0x0190:
        r5 = java.lang.Character.isDigit(r0);
        if (r5 != 0) goto L_0x00ad;
    L_0x0196:
        goto L_0x00b6;
    L_0x0198:
        r0 = (char) r2;
        r5 = java.lang.Character.isDigit(r0);
        if (r5 != 0) goto L_0x01a7;
    L_0x019f:
        r10.checkSeparator(r0);
        r10.unread();
        goto L_0x00d8;
    L_0x01a7:
        r10.tokenBufferAppend(r0);
        r2 = r10.read();
        r1 = r1 + 1;
        goto L_0x00d6;
    L_0x01b2:
        if (r4 == 0) goto L_0x01c1;
    L_0x01b4:
        r0 = 49;
    L_0x01b6:
        if (r2 < 0) goto L_0x00e5;
    L_0x01b8:
        r5 = (char) r2;
        r10.checkSeparator(r5);
        r10.unread(r2);
        goto L_0x00e5;
    L_0x01c1:
        r0 = 48;
        goto L_0x01b6;
    L_0x01c4:
        if (r0 != r7) goto L_0x01d0;
    L_0x01c6:
        r5 = r10.checkNext(r7);
        if (r5 == 0) goto L_0x00e5;
    L_0x01cc:
        r0 = 51;
        goto L_0x00e5;
    L_0x01d0:
        r5 = gnu.xml.XName.isNameStart(r0);
        if (r5 == 0) goto L_0x0224;
    L_0x01d6:
        r10.tokenBufferAppend(r0);
        r2 = r10.read();
        r0 = (char) r2;
        r5 = gnu.xml.XName.isNamePart(r0);
        if (r5 != 0) goto L_0x01d6;
    L_0x01e4:
        if (r2 >= 0) goto L_0x01ea;
    L_0x01e6:
        r0 = 65;
        goto L_0x00e5;
    L_0x01ea:
        if (r2 == r9) goto L_0x01f3;
    L_0x01ec:
        r0 = 65;
    L_0x01ee:
        r10.unread(r2);
        goto L_0x00e5;
    L_0x01f3:
        r2 = r10.read();
        if (r2 >= 0) goto L_0x01fe;
    L_0x01f9:
        r5 = "unexpected end-of-file after NAME ':'";
        r10.eofError(r5);
    L_0x01fe:
        r0 = (char) r2;
        r5 = gnu.xml.XName.isNameStart(r0);
        if (r5 == 0) goto L_0x0219;
    L_0x0205:
        r10.tokenBufferAppend(r9);
    L_0x0208:
        r10.tokenBufferAppend(r0);
        r2 = r10.read();
        r0 = (char) r2;
        r5 = gnu.xml.XName.isNamePart(r0);
        if (r5 != 0) goto L_0x0208;
    L_0x0216:
        r0 = 81;
        goto L_0x01ee;
    L_0x0219:
        if (r0 != r8) goto L_0x0221;
    L_0x021b:
        r10.unread(r0);
        r0 = 65;
        goto L_0x01ee;
    L_0x0221:
        r0 = 67;
        goto L_0x01ee;
    L_0x0224:
        r5 = 32;
        if (r0 < r5) goto L_0x024a;
    L_0x0228:
        r5 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        if (r0 >= r5) goto L_0x024a;
    L_0x022c:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "invalid character '";
        r5 = r5.append(r6);
        r5 = r5.append(r0);
        r6 = 39;
        r5 = r5.append(r6);
        r5 = r5.toString();
        r10.syntaxError(r5);
        goto L_0x00e5;
    L_0x024a:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "invalid character '\\u";
        r5 = r5.append(r6);
        r6 = java.lang.Integer.toHexString(r0);
        r5 = r5.append(r6);
        r6 = 39;
        r5 = r5.append(r6);
        r5 = r5.toString();
        r10.syntaxError(r5);
        goto L_0x00e5;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.getRawToken():int");
    }

    public void getDelimited(String delimiter) throws IOException, SyntaxException {
        if (!readDelimited(delimiter)) {
            eofError("unexpected end-of-file looking for '" + delimiter + '\'');
        }
    }

    public void appendNamedEntity(String name) {
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
            error("unknown enity reference: '" + name + "'");
        }
        tokenBufferAppend(ch);
    }

    boolean match(String word1, String word2, boolean force) throws IOException, SyntaxException {
        if (match(word1)) {
            mark();
            getRawToken();
            if (match(word2)) {
                reset();
                getRawToken();
                return true;
            }
            reset();
            if (force) {
                error('e', "'" + word1 + "' must be followed by '" + word2 + "'", "XPST0003");
                return true;
            }
        }
        return false;
    }

    int peekOperator() throws IOException, SyntaxException {
        while (this.curToken == EOL_TOKEN) {
            if (this.nesting == 0) {
                return EOL_TOKEN;
            }
            getRawToken();
        }
        if (this.curToken == NCNAME_TOKEN) {
            char c1;
            char c2;
            switch (this.tokenBufferLength) {
                case AXIS_ATTRIBUTE /*2*/:
                    c1 = this.tokenBuffer[AXIS_ANCESTOR];
                    c2 = this.tokenBuffer[AXIS_ANCESTOR_OR_SELF];
                    if (c1 != 'o' || c2 != 'r') {
                        if (c1 != 't' || c2 != 'o') {
                            if (c1 != 'i' || c2 != 's') {
                                if (c1 != 'e' || c2 != 'q') {
                                    if (c1 != 'n' || c2 != 'e') {
                                        if (c1 != 'g') {
                                            if (c1 == 'l') {
                                                if (c2 != 'e') {
                                                    if (c2 == 't') {
                                                        this.curToken = OP_LT;
                                                        break;
                                                    }
                                                }
                                                this.curToken = OP_LE;
                                                break;
                                            }
                                        } else if (c2 != 'e') {
                                            if (c2 == 't') {
                                                this.curToken = OP_GT;
                                                break;
                                            }
                                        } else {
                                            this.curToken = OP_GE;
                                            break;
                                        }
                                    }
                                    this.curToken = OP_NE;
                                    break;
                                }
                                this.curToken = OP_EQ;
                                break;
                            }
                            this.curToken = OP_IS;
                            break;
                        }
                        this.curToken = OP_RANGE_TO;
                        break;
                    }
                    this.curToken = OP_OR;
                    break;
                    break;
                case AXIS_CHILD /*3*/:
                    c1 = this.tokenBuffer[AXIS_ANCESTOR];
                    c2 = this.tokenBuffer[AXIS_ANCESTOR_OR_SELF];
                    char c3 = this.tokenBuffer[AXIS_ATTRIBUTE];
                    if (c1 != 'a') {
                        if (c1 != 'm') {
                            if (c1 == 'd' && c2 == 'i' && c3 == 'v') {
                                this.curToken = OP_DIV;
                                break;
                            }
                        }
                        if (c2 == 'u' && c3 == 'l') {
                            this.curToken = OP_MUL;
                        }
                        if (c2 == 'o' && c3 == 'd') {
                            this.curToken = OP_MOD;
                            break;
                        }
                    } else if (c2 == 'n' && c3 == 'd') {
                        this.curToken = OP_AND;
                        break;
                    }
                case AXIS_DESCENDANT /*4*/:
                    if (!match("idiv")) {
                        if (match("cast", "as", true)) {
                            this.curToken = OP_CAST_AS;
                            break;
                        }
                    }
                    this.curToken = OP_IDIV;
                    break;
                    break;
                case AXIS_DESCENDANT_OR_SELF /*5*/:
                    if (!match("where")) {
                        if (!match("isnot")) {
                            if (!match("union")) {
                                if (match("treat", "as", true)) {
                                    this.curToken = OP_TREAT_AS;
                                    break;
                                }
                            }
                            this.curToken = OP_UNION;
                            break;
                        }
                        this.curToken = OP_ISNOT;
                        break;
                    }
                    this.curToken = OP_WHERE;
                    break;
                    break;
                case AXIS_FOLLOWING /*6*/:
                    if (match("except")) {
                        this.curToken = OP_EXCEPT;
                        break;
                    }
                    break;
                case AXIS_NAMESPACE /*8*/:
                    if (!match("instance", "of", true)) {
                        if (match("castable", "as", true)) {
                            this.curToken = OP_CASTABLE_AS;
                            break;
                        }
                    }
                    this.curToken = OP_INSTANCEOF;
                    break;
                    break;
                case AXIS_PARENT /*9*/:
                    if (match("intersect")) {
                        this.curToken = OP_INTERSECT;
                        break;
                    }
                    break;
                case EOL_TOKEN /*10*/:
                    if (match("instanceof")) {
                        warnOldVersion("use 'instanceof of' (two words) instead of 'instanceof'");
                        this.curToken = OP_INSTANCEOF;
                        break;
                    }
                    break;
            }
        }
        return this.curToken;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean lookingAt(java.lang.String r8, java.lang.String r9) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r7 = this;
        r5 = 1;
        r4 = 0;
        r6 = r7.curValue;
        r6 = r8.equals(r6);
        if (r6 != 0) goto L_0x000b;
    L_0x000a:
        return r4;
    L_0x000b:
        r1 = 0;
        r3 = r9.length();
    L_0x0010:
        r0 = r7.read();
        if (r1 != r3) goto L_0x002f;
    L_0x0016:
        if (r0 >= 0) goto L_0x001a;
    L_0x0018:
        r4 = r5;
        goto L_0x000a;
    L_0x001a:
        r6 = (char) r0;
        r6 = gnu.xml.XName.isNamePart(r6);
        if (r6 != 0) goto L_0x0026;
    L_0x0021:
        r7.unread();
        r4 = r5;
        goto L_0x000a;
    L_0x0026:
        r1 = r1 + 1;
    L_0x0028:
        r5 = r7.port;
        r6 = -r1;
        r5.skip(r6);
        goto L_0x000a;
    L_0x002f:
        if (r0 < 0) goto L_0x0028;
    L_0x0031:
        r2 = r1 + 1;
        r6 = r9.charAt(r1);
        if (r0 == r6) goto L_0x003b;
    L_0x0039:
        r1 = r2;
        goto L_0x0028;
    L_0x003b:
        r1 = r2;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.lookingAt(java.lang.String, java.lang.String):boolean");
    }

    int getAxis() {
        String name = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength).intern();
        int i = COUNT_OP_AXIS;
        do {
            i += EOF_TOKEN;
            if (i < 0) {
                break;
            }
        } while (axisNames[i] != name);
        if (i < 0 || i == AXIS_NAMESPACE) {
            error('e', "unknown axis name '" + name + '\'', "XPST0003");
            i = AXIS_CHILD;
        }
        return (char) (i + OP_AXIS_FIRST);
    }

    int peekOperand() throws IOException, SyntaxException {
        while (this.curToken == EOL_TOKEN) {
            getRawToken();
        }
        int next;
        if (this.curToken == NCNAME_TOKEN || this.curToken == QNAME_TOKEN) {
            next = skipSpace(this.nesting != 0);
            switch (this.tokenBuffer[AXIS_ANCESTOR]) {
                case 'a':
                    if (match("attribute")) {
                        if (next == 40) {
                            this.curToken = OP_ATTRIBUTE;
                            return OP_ATTRIBUTE;
                        } else if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = ATTRIBUTE_TOKEN;
                            return ATTRIBUTE_TOKEN;
                        }
                    }
                    break;
                case 'c':
                    if (match("comment")) {
                        if (next == 40) {
                            this.curToken = OP_COMMENT;
                            return OP_COMMENT;
                        } else if (next == srfi1.$Pcprovide$Pcsrfi$Mn1) {
                            unread();
                            this.curToken = COMMENT_TOKEN;
                            return COMMENT_TOKEN;
                        }
                    }
                    break;
                case OP_AXIS_FIRST /*100*/:
                    if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 && match("document")) {
                        unread();
                        this.curToken = DOCUMENT_TOKEN;
                        return DOCUMENT_TOKEN;
                    } else if (next == 40 && match("document-node")) {
                        this.curToken = OP_DOCUMENT;
                        return OP_DOCUMENT;
                    }
                    break;
                case ErrorMessages.ERROR_LOCATION_SENSOR_LATITUDE_NOT_FOUND /*101*/:
                    if (match("element")) {
                        if (next == 40) {
                            this.curToken = OP_ELEMENT;
                            return OP_ELEMENT;
                        } else if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = ELEMENT_TOKEN;
                            return ELEMENT_TOKEN;
                        }
                    } else if (next == 40 && match("empty-sequence")) {
                        this.curToken = OP_EMPTY_SEQUENCE;
                        return OP_EMPTY_SEQUENCE;
                    } else if (next == 36 && match("every")) {
                        this.curToken = EVERY_DOLLAR_TOKEN;
                        return EVERY_DOLLAR_TOKEN;
                    }
                    break;
                case ErrorMessages.ERROR_LOCATION_SENSOR_LONGITUDE_NOT_FOUND /*102*/:
                    if (next == 36 && match("for")) {
                        this.curToken = FOR_DOLLAR_TOKEN;
                        return FOR_DOLLAR_TOKEN;
                    }
                case 'i':
                    if (next == 40 && match("if")) {
                        this.curToken = IF_LPAREN_TOKEN;
                        return IF_LPAREN_TOKEN;
                    } else if (next == 40 && match("item")) {
                        this.curToken = OP_ITEM;
                        return OP_ITEM;
                    }
                    break;
                case 'l':
                    if (next == 36 && match("let")) {
                        this.curToken = LET_DOLLAR_TOKEN;
                        return LET_DOLLAR_TOKEN;
                    }
                case 'n':
                    if (next == 40 && match("node")) {
                        this.curToken = OP_NODE;
                        return OP_NODE;
                    }
                case DECLARE_OPTION_TOKEN /*111*/:
                    if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 && match("ordered")) {
                        this.curToken = ORDERED_LBRACE_TOKEN;
                        return ORDERED_LBRACE_TOKEN;
                    }
                case DateTime.TIME_MASK /*112*/:
                    if (match("processing-instruction")) {
                        if (next == 40) {
                            this.curToken = OP_PI;
                            return OP_PI;
                        } else if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 || XName.isNameStart((char) next)) {
                            unread();
                            this.curToken = PI_TOKEN;
                            return PI_TOKEN;
                        }
                    }
                    break;
                case 's':
                    if (next == 36 && match("some")) {
                        this.curToken = SOME_DOLLAR_TOKEN;
                        return SOME_DOLLAR_TOKEN;
                    } else if (next == 40 && match("schema-attribute")) {
                        this.curToken = OP_SCHEMA_ATTRIBUTE;
                        return OP_SCHEMA_ATTRIBUTE;
                    } else if (next == 40 && match("schema-element")) {
                        this.curToken = OP_SCHEMA_ELEMENT;
                        return OP_SCHEMA_ELEMENT;
                    }
                    break;
                case 't':
                    if (match(PropertyTypeConstants.PROPERTY_TYPE_TEXT)) {
                        if (next == 40) {
                            this.curToken = OP_TEXT;
                            return OP_TEXT;
                        } else if (next == srfi1.$Pcprovide$Pcsrfi$Mn1) {
                            unread();
                            this.curToken = TEXT_TOKEN;
                            return TEXT_TOKEN;
                        }
                    }
                    if (next == 40 && match("typeswitch")) {
                        this.curToken = TYPESWITCH_LPAREN_TOKEN;
                        return TYPESWITCH_LPAREN_TOKEN;
                    }
                case 'u':
                    if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 && match("unordered")) {
                        this.curToken = UNORDERED_LBRACE_TOKEN;
                        return UNORDERED_LBRACE_TOKEN;
                    }
                case 'v':
                    if (next == srfi1.$Pcprovide$Pcsrfi$Mn1 && match("validate")) {
                        this.curToken = VALIDATE_LBRACE_TOKEN;
                        return VALIDATE_LBRACE_TOKEN;
                    }
            }
            if (next == 40 && peek() != 58) {
                this.curToken = FNAME_TOKEN;
                return FNAME_TOKEN;
            } else if (next == 58 && peek() == 58) {
                int axis = getAxis();
                this.curToken = axis;
                return axis;
            } else {
                this.curValue = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
                switch (next) {
                    case 98:
                        if (lookingAt("declare", "ase-uri")) {
                            this.curToken = DECLARE_BASE_URI_TOKEN;
                            return DECLARE_BASE_URI_TOKEN;
                        } else if (lookingAt("declare", "oundary-space")) {
                            this.curToken = DECLARE_BOUNDARY_SPACE_TOKEN;
                            return DECLARE_BOUNDARY_SPACE_TOKEN;
                        }
                        break;
                    case 99:
                        if (lookingAt("declare", "onstruction")) {
                            this.curToken = DECLARE_CONSTRUCTION_TOKEN;
                            return DECLARE_CONSTRUCTION_TOKEN;
                        } else if (lookingAt("declare", "opy-namespaces")) {
                            this.curToken = DECLARE_COPY_NAMESPACES_TOKEN;
                            return DECLARE_COPY_NAMESPACES_TOKEN;
                        }
                        break;
                    case OP_AXIS_FIRST /*100*/:
                        if (lookingAt("declare", "efault")) {
                            getRawToken();
                            if (match("function")) {
                                this.curToken = DEFAULT_FUNCTION_TOKEN;
                                return DEFAULT_FUNCTION_TOKEN;
                            } else if (match("element")) {
                                this.curToken = DEFAULT_ELEMENT_TOKEN;
                                return DEFAULT_ELEMENT_TOKEN;
                            } else if (match("collation")) {
                                this.curToken = DEFAULT_COLLATION_TOKEN;
                                return DEFAULT_COLLATION_TOKEN;
                            } else if (match("order")) {
                                this.curToken = DEFAULT_ORDER_TOKEN;
                                return DEFAULT_ORDER_TOKEN;
                            } else {
                                error("unrecognized/unimplemented 'declare default'");
                                skipToSemicolon();
                                return peekOperand();
                            }
                        }
                        break;
                    case ErrorMessages.ERROR_LOCATION_SENSOR_LATITUDE_NOT_FOUND /*101*/:
                        break;
                    case ErrorMessages.ERROR_LOCATION_SENSOR_LONGITUDE_NOT_FOUND /*102*/:
                        if (lookingAt("declare", "unction")) {
                            this.curToken = DECLARE_FUNCTION_TOKEN;
                            return DECLARE_FUNCTION_TOKEN;
                        } else if (lookingAt("define", "unction")) {
                            warnOldVersion("replace 'define function' by 'declare function'");
                            this.curToken = DECLARE_FUNCTION_TOKEN;
                            return DECLARE_FUNCTION_TOKEN;
                        } else if (lookingAt("default", "unction")) {
                            warnOldVersion("replace 'default function' by 'declare default function namespace'");
                            this.curToken = DEFAULT_FUNCTION_TOKEN;
                            return DEFAULT_FUNCTION_TOKEN;
                        }
                        break;
                    case 109:
                        if (lookingAt("import", "odule")) {
                            this.curToken = IMPORT_MODULE_TOKEN;
                            return IMPORT_MODULE_TOKEN;
                        }
                        break;
                    case 110:
                        if (lookingAt("declare", "amespace")) {
                            this.curToken = DECLARE_NAMESPACE_TOKEN;
                            return DECLARE_NAMESPACE_TOKEN;
                        } else if (lookingAt("default", "amespace")) {
                            warnOldVersion("replace 'default namespace' by 'declare default element namespace'");
                            this.curToken = DEFAULT_ELEMENT_TOKEN;
                            return DEFAULT_ELEMENT_TOKEN;
                        } else if (lookingAt("module", "amespace")) {
                            this.curToken = MODULE_NAMESPACE_TOKEN;
                            return MODULE_NAMESPACE_TOKEN;
                        }
                        break;
                    case DECLARE_OPTION_TOKEN /*111*/:
                        if (lookingAt("declare", "rdering")) {
                            this.curToken = DECLARE_ORDERING_TOKEN;
                            return DECLARE_ORDERING_TOKEN;
                        } else if (lookingAt("declare", "ption")) {
                            this.curToken = DECLARE_OPTION_TOKEN;
                            return DECLARE_OPTION_TOKEN;
                        }
                        break;
                    case 115:
                        if (lookingAt("import", "chema")) {
                            this.curToken = IMPORT_SCHEMA_TOKEN;
                            return IMPORT_SCHEMA_TOKEN;
                        }
                        break;
                    case 118:
                        if (lookingAt("declare", "ariable")) {
                            this.curToken = DECLARE_VARIABLE_TOKEN;
                            return DECLARE_VARIABLE_TOKEN;
                        } else if (lookingAt("define", "ariable")) {
                            warnOldVersion("replace 'define variable' by 'declare variable'");
                            this.curToken = DECLARE_VARIABLE_TOKEN;
                            return DECLARE_VARIABLE_TOKEN;
                        } else if (lookingAt("xquery", "ersion")) {
                            this.curToken = XQUERY_VERSION_TOKEN;
                            return XQUERY_VERSION_TOKEN;
                        }
                        break;
                    case 120:
                        if (lookingAt("declare", "mlspace")) {
                            warnOldVersion("replace 'define xmlspace' by 'declare boundary-space'");
                            this.curToken = DECLARE_BOUNDARY_SPACE_TOKEN;
                            return DECLARE_BOUNDARY_SPACE_TOKEN;
                        }
                        break;
                }
                if (lookingAt("default", "lement")) {
                    warnOldVersion("replace 'default element' by 'declare default element namespace'");
                    this.curToken = DEFAULT_ELEMENT_TOKEN;
                    return DEFAULT_ELEMENT_TOKEN;
                }
                if (next >= 0) {
                    unread();
                    if (XName.isNameStart((char) next) && this.curValue.equals("define")) {
                        getRawToken();
                        this.curToken = DEFINE_QNAME_TOKEN;
                    }
                }
                return this.curToken;
            }
        }
        if (this.curToken == NCNAME_COLON_TOKEN) {
            next = read();
            if (next == 58) {
                this.curToken = getAxis();
            } else {
                unread(next);
            }
        }
        return this.curToken;
    }

    void checkAllowedNamespaceDeclaration(String prefix, String uri, boolean inConstructor) {
        boolean xmlPrefix = "xml".equals(prefix);
        if (NamespaceBinding.XML_NAMESPACE.equals(uri)) {
            if (!xmlPrefix || !inConstructor) {
                error('e', "namespace uri cannot be the same as the prefined xml namespace", "XQST0070");
            }
        } else if (xmlPrefix || "xmlns".equals(prefix)) {
            error('e', "namespace prefix cannot be 'xml' or 'xmlns'", "XQST0070");
        }
    }

    void pushNamespace(String prefix, String uri) {
        if (uri.length() == 0) {
            uri = null;
        }
        this.prologNamespaces = new NamespaceBinding(prefix, uri, this.prologNamespaces);
    }

    public XQParser(InPort port, SourceMessages messages, XQuery interp) {
        super(port, messages);
        this.defaultCollator = null;
        this.defaultEmptyOrder = 'L';
        this.baseURI = null;
        this.copyNamespacesMode = AXIS_CHILD;
        this.functionNamespacePath = XQuery.defaultFunctionNamespacePath;
        this.defaultElementNamespace = ElementType.MATCH_ANY_LOCALNAME;
        this.constructorNamespaces = NamespaceBinding.predefinedXML;
        this.interpreter = interp;
        this.nesting = AXIS_ANCESTOR_OR_SELF;
        this.prologNamespaces = builtinNamespaces;
    }

    public void setInteractive(boolean v) {
        if (this.interactive != v) {
            if (v) {
                this.nesting += EOF_TOKEN;
            } else {
                this.nesting += AXIS_ANCESTOR_OR_SELF;
            }
        }
        this.interactive = v;
    }

    private static final int priority(int opcode) {
        switch (opcode) {
            case OP_OR /*400*/:
                return AXIS_ANCESTOR_OR_SELF;
            case OP_AND /*401*/:
                return AXIS_ATTRIBUTE;
            case OP_EQU /*402*/:
            case OP_NEQ /*403*/:
            case OP_LSS /*404*/:
            case OP_GRT /*405*/:
            case OP_LEQ /*406*/:
            case OP_GEQ /*407*/:
            case OP_IS /*408*/:
            case OP_ISNOT /*409*/:
            case OP_GRTGRT /*410*/:
            case OP_LSSLSS /*411*/:
            case OP_EQ /*426*/:
            case OP_NE /*427*/:
            case OP_LT /*428*/:
            case OP_LE /*429*/:
            case OP_GT /*430*/:
            case OP_GE /*431*/:
                return AXIS_CHILD;
            case OP_RANGE_TO /*412*/:
                return AXIS_DESCENDANT;
            case OP_ADD /*413*/:
            case OP_SUB /*414*/:
                return AXIS_DESCENDANT_OR_SELF;
            case OP_MUL /*415*/:
            case OP_DIV /*416*/:
            case OP_IDIV /*417*/:
            case OP_MOD /*418*/:
                return AXIS_FOLLOWING;
            case OP_UNION /*419*/:
                return AXIS_FOLLOWING_SIBLING;
            case OP_INTERSECT /*420*/:
            case OP_EXCEPT /*421*/:
                return AXIS_NAMESPACE;
            case OP_INSTANCEOF /*422*/:
                return AXIS_PARENT;
            case OP_TREAT_AS /*423*/:
                return EOL_TOKEN;
            case OP_CASTABLE_AS /*424*/:
                return AXIS_PRECEDING_SIBLING;
            case OP_CAST_AS /*425*/:
                return AXIS_SELF;
            default:
                return AXIS_ANCESTOR;
        }
    }

    static Expression makeBinary(Expression func, Expression exp1, Expression exp2) {
        Expression[] args = new Expression[AXIS_ATTRIBUTE];
        args[AXIS_ANCESTOR] = exp1;
        args[AXIS_ANCESTOR_OR_SELF] = exp2;
        return new ApplyExp(func, args);
    }

    static Expression makeExprSequence(Expression exp1, Expression exp2) {
        return makeBinary(makeFunctionExp("gnu.kawa.functions.AppendValues", "appendValues"), exp1, exp2);
    }

    Expression makeBinary(int op, Expression exp1, Expression exp2) throws IOException, SyntaxException {
        Expression func;
        switch (op) {
            case OP_EQU /*402*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "=");
                break;
            case OP_NEQ /*403*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "!=");
                break;
            case OP_LSS /*404*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<");
                break;
            case OP_GRT /*405*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">");
                break;
            case OP_LEQ /*406*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "<=");
                break;
            case OP_GEQ /*407*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", ">=");
                break;
            case OP_IS /*408*/:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Eq", "is");
                break;
            case OP_ISNOT /*409*/:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ne", "isnot");
                break;
            case OP_GRTGRT /*410*/:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Gr", ">>");
                break;
            case OP_LSSLSS /*411*/:
                func = makeFunctionExp("gnu.kawa.xml.NodeCompare", "$Ls", "<<");
                break;
            case OP_RANGE_TO /*412*/:
                func = makeFunctionExp("gnu.xquery.util.IntegerRange", "integerRange");
                break;
            case OP_ADD /*413*/:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "add", "+");
                break;
            case OP_SUB /*414*/:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "sub", "-");
                break;
            case OP_MUL /*415*/:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mul", "*");
                break;
            case OP_DIV /*416*/:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "div", "div");
                break;
            case OP_IDIV /*417*/:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "idiv", "idiv");
                break;
            case OP_MOD /*418*/:
                func = makeFunctionExp("gnu.xquery.util.ArithOp", "mod", "mod");
                break;
            case OP_UNION /*419*/:
                func = makeFunctionExp("gnu.kawa.xml.UnionNodes", "unionNodes");
                break;
            case OP_INTERSECT /*420*/:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "intersectNodes");
                break;
            case OP_EXCEPT /*421*/:
                func = makeFunctionExp("gnu.kawa.xml.IntersectNodes", "exceptNodes");
                break;
            case OP_EQ /*426*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valEq", "eq");
                break;
            case OP_NE /*427*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valNe", "ne");
                break;
            case OP_LT /*428*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLt", "lt");
                break;
            case OP_LE /*429*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valLe", "le");
                break;
            case OP_GT /*430*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGt", "gt");
                break;
            case OP_GE /*431*/:
                func = makeFunctionExp("gnu.xquery.util.Compare", "valGe", "ge");
                break;
            default:
                return syntaxError("unimplemented binary op: " + op);
        }
        return makeBinary(func, exp1, exp2);
    }

    private void parseSimpleKindType() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken == 41) {
            getRawToken();
        } else {
            error("expected ')'");
        }
    }

    public Expression parseNamedNodeType(boolean attribute) throws IOException, SyntaxException {
        Expression qname;
        Expression tname = null;
        getRawToken();
        if (this.curToken == 41) {
            qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            getRawToken();
        } else {
            if (this.curToken == QNAME_TOKEN || this.curToken == NCNAME_TOKEN) {
                qname = parseNameTest(attribute);
            } else {
                if (this.curToken != OP_MUL) {
                    syntaxError("expected QName or *");
                }
                qname = QuoteExp.getInstance(ElementType.MATCH_ANY_QNAME);
            }
            getRawToken();
            if (this.curToken == 44) {
                getRawToken();
                if (this.curToken == QNAME_TOKEN || this.curToken == NCNAME_TOKEN) {
                    tname = parseDataType();
                } else {
                    syntaxError("expected QName");
                }
            }
            if (this.curToken == 41) {
                getRawToken();
            } else {
                error("expected ')' after element");
            }
        }
        return makeNamedNodeType(attribute, qname, tname);
    }

    static Expression makeNamedNodeType(boolean attribute, Expression qname, Expression tname) {
        Expression[] name = new Expression[AXIS_ATTRIBUTE];
        Method declaredMethod = ClassType.make(attribute ? "gnu.kawa.xml.AttributeType" : "gnu.kawa.xml.ElementType").getDeclaredMethod("make", (int) AXIS_ANCESTOR_OR_SELF);
        Expression[] expressionArr = new Expression[AXIS_ANCESTOR_OR_SELF];
        expressionArr[AXIS_ANCESTOR] = qname;
        ApplyExp elt = new ApplyExp(declaredMethod, expressionArr);
        elt.setFlag(AXIS_DESCENDANT);
        return tname == null ? elt : new BeginExp(tname, elt);
    }

    private void warnOldStyleKindTest() {
        if (!this.warnedOldStyleKindTest) {
            error('w', "old-style KindTest - first one here");
            this.warnedOldStyleKindTest = true;
        }
    }

    public Expression parseOptionalTypeDeclaration() throws IOException, SyntaxException {
        if (!match("as")) {
            return null;
        }
        getRawToken();
        return parseDataType();
    }

    public Expression parseDataType() throws IOException, SyntaxException {
        int min;
        int max;
        Expression etype = parseItemType();
        if (etype == null) {
            if (this.curToken != OP_EMPTY_SEQUENCE) {
                return syntaxError("bad syntax - expected DataType");
            }
            parseSimpleKindType();
            if (this.curToken == 63 || this.curToken == OP_ADD || this.curToken == OP_MUL) {
                getRawToken();
                return syntaxError("occurrence-indicator meaningless after empty-sequence()");
            }
            etype = QuoteExp.getInstance(OccurrenceType.emptySequenceType);
            min = AXIS_ANCESTOR;
            max = AXIS_ANCESTOR;
        } else if (this.curToken == 63) {
            min = AXIS_ANCESTOR;
            max = AXIS_ANCESTOR_OR_SELF;
        } else if (this.curToken == OP_ADD) {
            min = AXIS_ANCESTOR_OR_SELF;
            max = EOF_TOKEN;
        } else if (this.curToken == OP_MUL) {
            min = AXIS_ANCESTOR;
            max = EOF_TOKEN;
        } else {
            min = AXIS_ANCESTOR_OR_SELF;
            max = AXIS_ANCESTOR_OR_SELF;
        }
        if (this.parseContext == NCNAME_COLON_TOKEN && max != AXIS_ANCESTOR_OR_SELF) {
            return syntaxError("type to 'cast as' or 'castable as' must be a 'SingleType'");
        }
        if (min == max) {
            return etype;
        }
        getRawToken();
        Expression[] args = new Expression[AXIS_CHILD];
        args[AXIS_ANCESTOR] = etype;
        args[AXIS_ANCESTOR_OR_SELF] = QuoteExp.getInstance(IntNum.make(min));
        args[AXIS_ATTRIBUTE] = QuoteExp.getInstance(IntNum.make(max));
        Expression otype = new ApplyExp(proc_OccurrenceType_getInstance, args);
        otype.setFlag(AXIS_DESCENDANT);
        return otype;
    }

    public Expression parseMaybeKindTest() throws IOException, SyntaxException {
        Type type;
        boolean z = false;
        switch (this.curToken) {
            case OP_NODE /*230*/:
                parseSimpleKindType();
                type = NodeType.anyNodeTest;
                break;
            case OP_TEXT /*231*/:
                parseSimpleKindType();
                type = NodeType.textNodeTest;
                break;
            case OP_COMMENT /*232*/:
                parseSimpleKindType();
                type = NodeType.commentNodeTest;
                break;
            case OP_PI /*233*/:
                getRawToken();
                String piTarget = null;
                if (this.curToken == NCNAME_TOKEN || this.curToken == STRING_TOKEN) {
                    piTarget = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
                    getRawToken();
                }
                if (this.curToken == 41) {
                    getRawToken();
                } else {
                    error("expected ')'");
                }
                type = ProcessingInstructionType.getInstance(piTarget);
                break;
            case OP_DOCUMENT /*234*/:
                parseSimpleKindType();
                type = NodeType.documentNodeTest;
                break;
            case OP_ELEMENT /*235*/:
            case OP_ATTRIBUTE /*236*/:
                if (this.curToken == OP_ATTRIBUTE) {
                    z = true;
                }
                return parseNamedNodeType(z);
            default:
                return null;
        }
        return QuoteExp.getInstance(type);
    }

    public Expression parseItemType() throws IOException, SyntaxException {
        Type type;
        peekOperand();
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            if (this.parseContext != NCNAME_COLON_TOKEN) {
                return etype;
            }
            type = XDataType.anyAtomicType;
        } else if (this.curToken == OP_ITEM) {
            parseSimpleKindType();
            type = SingletonType.getInstance();
        } else if (this.curToken != NCNAME_TOKEN && this.curToken != QNAME_TOKEN) {
            return null;
        } else {
            Expression rexp = new ReferenceExp(new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength));
            rexp.setFlag(16);
            maybeSetLine(rexp, this.curLine, this.curColumn);
            getRawToken();
            return rexp;
        }
        return QuoteExp.getInstance(type);
    }

    Object parseURILiteral() throws IOException, SyntaxException {
        getRawToken();
        if (this.curToken != STRING_TOKEN) {
            return declError("expected a URILiteral");
        }
        return TextUtils.replaceWhitespace(new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength), true);
    }

    Expression parseExpr() throws IOException, SyntaxException {
        return parseExprSingle();
    }

    final Expression parseExprSingle() throws IOException, SyntaxException {
        int startLine = this.curLine;
        int startColumn = this.curColumn;
        peekOperand();
        switch (this.curToken) {
            case IF_LPAREN_TOKEN /*241*/:
                return parseIfExpr();
            case TYPESWITCH_LPAREN_TOKEN /*242*/:
                return parseTypeSwitch();
            case FOR_DOLLAR_TOKEN /*243*/:
                return parseFLWRExpression(true);
            case LET_DOLLAR_TOKEN /*244*/:
                return parseFLWRExpression(false);
            case SOME_DOLLAR_TOKEN /*245*/:
                return parseQuantifiedExpr(false);
            case EVERY_DOLLAR_TOKEN /*246*/:
                return parseQuantifiedExpr(true);
            default:
                return parseBinaryExpr(priority(OP_OR));
        }
    }

    Expression parseBinaryExpr(int prio) throws IOException, SyntaxException {
        Expression exp = parseUnaryExpr();
        while (true) {
            int token = peekOperator();
            if (!(token == EOL_TOKEN || (token == OP_LSS && peek() == 47))) {
                int tokPriority = priority(token);
                if (tokPriority >= prio) {
                    char saveReadState = pushNesting('%');
                    getRawToken();
                    popNesting(saveReadState);
                    Expression[] args;
                    if (token >= OP_INSTANCEOF && token <= OP_CAST_AS) {
                        Expression func;
                        if (token == OP_CAST_AS || token == OP_CASTABLE_AS) {
                            this.parseContext = NCNAME_COLON_TOKEN;
                        }
                        Expression type = parseDataType();
                        this.parseContext = AXIS_ANCESTOR;
                        args = new Expression[AXIS_ATTRIBUTE];
                        switch (token) {
                            case OP_INSTANCEOF /*422*/:
                                args[AXIS_ANCESTOR] = exp;
                                args[AXIS_ANCESTOR_OR_SELF] = type;
                                func = makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf");
                                break;
                            case OP_TREAT_AS /*423*/:
                                args[AXIS_ANCESTOR] = type;
                                args[AXIS_ANCESTOR_OR_SELF] = exp;
                                func = makeFunctionExp("gnu.xquery.lang.XQParser", "treatAs");
                                break;
                            case OP_CASTABLE_AS /*424*/:
                                args[AXIS_ANCESTOR] = exp;
                                args[AXIS_ANCESTOR_OR_SELF] = type;
                                func = new ReferenceExp(XQResolveNames.castableAsDecl);
                                break;
                            default:
                                args[AXIS_ANCESTOR] = type;
                                args[AXIS_ANCESTOR_OR_SELF] = exp;
                                func = new ReferenceExp(XQResolveNames.castAsDecl);
                                break;
                        }
                        exp = new ApplyExp(func, args);
                    } else if (token == OP_INSTANCEOF) {
                        args = new Expression[AXIS_ATTRIBUTE];
                        args[AXIS_ANCESTOR] = exp;
                        args[AXIS_ANCESTOR_OR_SELF] = parseDataType();
                        exp = new ApplyExp(makeFunctionExp("gnu.xquery.lang.XQParser", "instanceOf"), args);
                    } else {
                        Expression exp2 = parseBinaryExpr(tokPriority + AXIS_ANCESTOR_OR_SELF);
                        if (token == OP_AND) {
                            exp = new IfExp(booleanValue(exp), booleanValue(exp2), XQuery.falseExp);
                        } else if (token == OP_OR) {
                            exp = new IfExp(booleanValue(exp), XQuery.trueExp, booleanValue(exp2));
                        } else {
                            exp = makeBinary(token, exp, exp2);
                        }
                    }
                }
            }
            return exp;
        }
    }

    Expression parseUnaryExpr() throws IOException, SyntaxException {
        if (this.curToken != OP_SUB && this.curToken != OP_ADD) {
            return parseUnionExpr();
        }
        int op = this.curToken;
        getRawToken();
        Expression exp = parseUnaryExpr();
        Expression func = makeFunctionExp("gnu.xquery.util.ArithOp", op == OP_ADD ? "plus" : "minus", op == OP_ADD ? "+" : "-");
        Expression[] expressionArr = new Expression[AXIS_ANCESTOR_OR_SELF];
        expressionArr[AXIS_ANCESTOR] = exp;
        return new ApplyExp(func, expressionArr);
    }

    Expression parseUnionExpr() throws IOException, SyntaxException {
        Expression exp = parseIntersectExceptExpr();
        while (true) {
            int op = peekOperator();
            if (op != OP_UNION) {
                return exp;
            }
            getRawToken();
            exp = makeBinary(op, exp, parseIntersectExceptExpr());
        }
    }

    Expression parseIntersectExceptExpr() throws IOException, SyntaxException {
        Expression exp = parsePathExpr();
        while (true) {
            int op = peekOperator();
            if (op != OP_INTERSECT && op != OP_EXCEPT) {
                return exp;
            }
            getRawToken();
            exp = makeBinary(op, exp, parsePathExpr());
        }
    }

    Expression parsePathExpr() throws IOException, SyntaxException {
        Expression step1;
        boolean z = true;
        if (this.curToken == 47 || this.curToken == SLASHSLASH_TOKEN) {
            Expression dot;
            Declaration dotDecl = this.comp.lexical.lookup(DOT_VARNAME, false);
            if (dotDecl == null) {
                dot = syntaxError("context item is undefined", "XPDY0002");
            } else {
                dot = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            Method declaredMethod = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("rootDocument", (int) AXIS_ANCESTOR_OR_SELF);
            Expression[] expressionArr = new Expression[AXIS_ANCESTOR_OR_SELF];
            expressionArr[AXIS_ANCESTOR] = dot;
            step1 = new ApplyExp(declaredMethod, expressionArr);
            if (this.nesting == 0) {
                z = false;
            }
            int next = skipSpace(z);
            unread(next);
            if (next < 0 || next == 41 || next == 125) {
                getRawToken();
                return step1;
            }
        }
        step1 = parseStepExpr();
        return parseRelativePathExpr(step1);
    }

    Expression parseNameTest(boolean attribute) throws IOException, SyntaxException {
        Object obj;
        String local = null;
        String prefix = null;
        if (this.curToken == QNAME_TOKEN) {
            int colon = this.tokenBufferLength;
            do {
                colon += EOF_TOKEN;
            } while (this.tokenBuffer[colon] != ':');
            prefix = new String(this.tokenBuffer, AXIS_ANCESTOR, colon);
            colon += AXIS_ANCESTOR_OR_SELF;
            local = new String(this.tokenBuffer, colon, this.tokenBufferLength - colon);
        } else if (this.curToken == OP_MUL) {
            int next = read();
            local = ElementType.MATCH_ANY_LOCALNAME;
            if (next != 58) {
                unread(next);
            } else {
                next = read();
                if (next < 0) {
                    eofError("unexpected end-of-file after '*:'");
                }
                if (XName.isNameStart((char) next)) {
                    unread();
                    getRawToken();
                    if (this.curToken != NCNAME_TOKEN) {
                        syntaxError("invalid name test");
                    } else {
                        local = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength).intern();
                    }
                } else if (next != 42) {
                    syntaxError("missing local-name after '*:'");
                }
            }
            return QuoteExp.getInstance(new Symbol(null, local));
        } else if (this.curToken == NCNAME_TOKEN) {
            local = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
            if (attribute) {
                return new QuoteExp(Namespace.EmptyNamespace.getSymbol(local.intern()));
            }
            prefix = null;
        } else if (this.curToken == NCNAME_COLON_TOKEN) {
            prefix = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
            if (read() != 42) {
                syntaxError("invalid characters after 'NCName:'");
            }
            local = ElementType.MATCH_ANY_LOCALNAME;
        }
        if (prefix != null) {
            prefix = prefix.intern();
        }
        Expression[] args = new Expression[AXIS_CHILD];
        Expression referenceExp = new ReferenceExp(XQResolveNames.resolvePrefixDecl);
        Expression[] expressionArr = new Expression[AXIS_ANCESTOR_OR_SELF];
        expressionArr[AXIS_ANCESTOR] = QuoteExp.getInstance(prefix);
        args[AXIS_ANCESTOR] = new ApplyExp(referenceExp, expressionArr);
        if (local == null) {
            obj = ElementType.MATCH_ANY_LOCALNAME;
        } else {
            String str = local;
        }
        args[AXIS_ANCESTOR_OR_SELF] = new QuoteExp(obj);
        args[AXIS_ATTRIBUTE] = new QuoteExp(prefix);
        ApplyExp make = new ApplyExp(Compilation.typeSymbol.getDeclaredMethod("make", (int) AXIS_CHILD), args);
        make.setFlag(AXIS_DESCENDANT);
        return make;
    }

    Expression parseNodeTest(int axis) throws IOException, SyntaxException {
        Expression dot;
        Expression makeAxisStep;
        int token = peekOperand();
        Expression[] args = new Expression[AXIS_ANCESTOR_OR_SELF];
        Expression etype = parseMaybeKindTest();
        if (etype != null) {
            args[AXIS_ANCESTOR] = etype;
        } else if (this.curToken == NCNAME_TOKEN || this.curToken == QNAME_TOKEN || this.curToken == NCNAME_COLON_TOKEN || this.curToken == OP_MUL) {
            args[AXIS_ANCESTOR] = makeNamedNodeType(axis == AXIS_ATTRIBUTE, parseNameTest(axis == AXIS_ATTRIBUTE), null);
        } else if (axis >= 0) {
            return syntaxError("unsupported axis '" + axisNames[axis] + "::'");
        } else {
            return null;
        }
        Declaration dotDecl = this.comp.lexical.lookup(DOT_VARNAME, false);
        if (dotDecl == null) {
            dot = syntaxError("node test when context item is undefined", "XPDY0002");
        } else {
            dot = new ReferenceExp(DOT_VARNAME, dotDecl);
        }
        if (etype == null) {
            getRawToken();
        }
        if (axis == AXIS_CHILD || axis == EOF_TOKEN) {
            makeAxisStep = makeChildAxisStep;
        } else if (axis == AXIS_DESCENDANT) {
            makeAxisStep = makeDescendantAxisStep;
        } else {
            String axisName;
            switch (axis) {
                case AXIS_ANCESTOR /*0*/:
                    axisName = "Ancestor";
                    break;
                case AXIS_ANCESTOR_OR_SELF /*1*/:
                    axisName = "AncestorOrSelf";
                    break;
                case AXIS_ATTRIBUTE /*2*/:
                    axisName = "Attribute";
                    break;
                case AXIS_DESCENDANT_OR_SELF /*5*/:
                    axisName = "DescendantOrSelf";
                    break;
                case AXIS_FOLLOWING /*6*/:
                    axisName = "Following";
                    break;
                case AXIS_FOLLOWING_SIBLING /*7*/:
                    axisName = "FollowingSibling";
                    break;
                case AXIS_PARENT /*9*/:
                    axisName = "Parent";
                    break;
                case EOL_TOKEN /*10*/:
                    axisName = "Preceding";
                    break;
                case AXIS_PRECEDING_SIBLING /*11*/:
                    axisName = "PrecedingSibling";
                    break;
                case AXIS_SELF /*12*/:
                    axisName = "Self";
                    break;
                default:
                    throw new Error();
            }
            makeAxisStep = QuoteExp.getInstance(new PrimProcedure("gnu.kawa.xml." + axisName + "Axis", "make", (int) AXIS_ANCESTOR_OR_SELF));
        }
        Expression mkAxis = new ApplyExp(makeAxisStep, args);
        mkAxis.setFlag(AXIS_DESCENDANT);
        Expression[] expressionArr = new Expression[AXIS_ANCESTOR_OR_SELF];
        expressionArr[AXIS_ANCESTOR] = dot;
        return new ApplyExp(mkAxis, expressionArr);
    }

    Expression parseRelativePathExpr(Expression exp) throws IOException, SyntaxException {
        Expression beforeSlashSlash = null;
        while (true) {
            if (this.curToken != 47 && this.curToken != SLASHSLASH_TOKEN) {
                return exp;
            }
            Expression[] args;
            boolean descendants = this.curToken == SLASHSLASH_TOKEN;
            ScopeExp lexp = new LambdaExp((int) AXIS_CHILD);
            Declaration dotDecl = lexp.addDeclaration((Object) DOT_VARNAME);
            dotDecl.setFlag(262144);
            dotDecl.setType(NodeType.anyNodeTest);
            dotDecl.noteValue(null);
            lexp.addDeclaration(POSITION_VARNAME, LangPrimType.intType);
            lexp.addDeclaration(LAST_VARNAME, LangPrimType.intType);
            this.comp.push(lexp);
            if (descendants) {
                this.curToken = 47;
                args = new Expression[AXIS_ANCESTOR_OR_SELF];
                args[AXIS_ANCESTOR] = new ReferenceExp(DOT_VARNAME, dotDecl);
                lexp.body = new ApplyExp(DescendantOrSelfAxis.anyNode, args);
                beforeSlashSlash = exp;
            } else {
                getRawToken();
                Expression exp2 = parseStepExpr();
                if (beforeSlashSlash != null && (exp2 instanceof ApplyExp)) {
                    Expression func = ((ApplyExp) exp2).getFunction();
                    if (func instanceof ApplyExp) {
                        ApplyExp aexp = (ApplyExp) func;
                        if (aexp.getFunction() == makeChildAxisStep) {
                            aexp.setFunction(makeDescendantAxisStep);
                            exp = beforeSlashSlash;
                        }
                    }
                }
                lexp.body = exp2;
                beforeSlashSlash = null;
            }
            this.comp.pop(lexp);
            args = new Expression[AXIS_ATTRIBUTE];
            args[AXIS_ANCESTOR] = exp;
            args[AXIS_ANCESTOR_OR_SELF] = lexp;
            exp = new ApplyExp(RelativeStep.relativeStep, args);
        }
    }

    Expression parseStepExpr() throws IOException, SyntaxException {
        int axis;
        if (this.curToken == 46 || this.curToken == DOTDOT_TOKEN) {
            Expression exp;
            int i;
            if (this.curToken == 46) {
                axis = AXIS_SELF;
            } else {
                axis = AXIS_PARENT;
            }
            getRawToken();
            Declaration dotDecl = this.comp.lexical.lookup(DOT_VARNAME, false);
            if (dotDecl == null) {
                exp = syntaxError("context item is undefined", "XPDY0002");
            } else {
                exp = new ReferenceExp(DOT_VARNAME, dotDecl);
            }
            if (axis == AXIS_PARENT) {
                Expression[] args = new Expression[AXIS_ANCESTOR_OR_SELF];
                args[AXIS_ANCESTOR] = exp;
                exp = new ApplyExp(ParentAxis.make(NodeType.anyNodeTest), args);
            }
            if (axis == AXIS_SELF) {
                i = EOF_TOKEN;
            } else {
                i = axis;
            }
            return parseStepQualifiers(exp, i);
        }
        Expression unqualifiedStep;
        axis = peekOperand() - 100;
        if (axis >= 0 && axis < COUNT_OP_AXIS) {
            getRawToken();
            unqualifiedStep = parseNodeTest(axis);
        } else if (this.curToken == 64) {
            getRawToken();
            axis = AXIS_ATTRIBUTE;
            unqualifiedStep = parseNodeTest(AXIS_ATTRIBUTE);
        } else if (this.curToken == OP_ATTRIBUTE) {
            axis = AXIS_ATTRIBUTE;
            unqualifiedStep = parseNodeTest(AXIS_ATTRIBUTE);
        } else {
            unqualifiedStep = parseNodeTest(EOF_TOKEN);
            if (unqualifiedStep != null) {
                axis = AXIS_CHILD;
            } else {
                axis = EOF_TOKEN;
                unqualifiedStep = parsePrimaryExpr();
            }
        }
        return parseStepQualifiers(unqualifiedStep, axis);
    }

    Expression parseStepQualifiers(Expression exp, int axis) throws IOException, SyntaxException {
        while (this.curToken == 91) {
            Procedure valuesFilter;
            int startLine = getLineNumber() + AXIS_ANCESTOR_OR_SELF;
            int startColumn = getColumnNumber() + AXIS_ANCESTOR_OR_SELF;
            int saveSeenPosition = this.seenPosition;
            int saveSawLast = this.seenLast;
            getRawToken();
            ScopeExp lexp = new LambdaExp((int) AXIS_CHILD);
            maybeSetLine((Expression) lexp, startLine, startColumn);
            Declaration dot = lexp.addDeclaration((Object) DOT_VARNAME);
            if (axis >= 0) {
                dot.setType(NodeType.anyNodeTest);
            } else {
                dot.setType(SingletonType.getInstance());
            }
            lexp.addDeclaration(POSITION_VARNAME, Type.intType);
            lexp.addDeclaration(LAST_VARNAME, Type.intType);
            this.comp.push(lexp);
            dot.noteValue(null);
            Expression cond = parseExprSequence(93, false);
            if (this.curToken == EOF_TOKEN) {
                eofError("missing ']' - unexpected end-of-file");
            }
            if (axis < 0) {
                valuesFilter = ValuesFilter.exprFilter;
            } else if (axis == 0 || axis == AXIS_ANCESTOR_OR_SELF || axis == AXIS_PARENT || axis == EOL_TOKEN || axis == AXIS_PRECEDING_SIBLING) {
                valuesFilter = ValuesFilter.reverseFilter;
            } else {
                valuesFilter = ValuesFilter.forwardFilter;
            }
            maybeSetLine(cond, startLine, startColumn);
            this.comp.pop(lexp);
            lexp.body = cond;
            getRawToken();
            Expression[] args = new Expression[AXIS_ATTRIBUTE];
            args[AXIS_ANCESTOR] = exp;
            args[AXIS_ANCESTOR_OR_SELF] = lexp;
            exp = new ApplyExp(valuesFilter, args);
        }
        return exp;
    }

    Expression parsePrimaryExpr() throws IOException, SyntaxException {
        Expression exp = parseMaybePrimaryExpr();
        if (exp != null) {
            return exp;
        }
        exp = syntaxError("missing expression");
        if (this.curToken != EOF_TOKEN) {
            getRawToken();
        }
        return exp;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void parseEntityOrCharRef() throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r10 = this;
        r8 = 59;
        r3 = r10.read();
        r7 = 35;
        if (r3 != r7) goto L_0x0074;
    L_0x000a:
        r3 = r10.read();
        r7 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r3 != r7) goto L_0x002d;
    L_0x0012:
        r0 = 16;
        r3 = r10.read();
    L_0x0018:
        r6 = 0;
    L_0x0019:
        if (r3 < 0) goto L_0x0022;
    L_0x001b:
        r1 = (char) r3;
        r2 = java.lang.Character.digit(r1, r0);
        if (r2 >= 0) goto L_0x0030;
    L_0x0022:
        if (r3 == r8) goto L_0x003b;
    L_0x0024:
        r10.unread();
        r7 = "invalid character reference";
        r10.error(r7);
    L_0x002c:
        return;
    L_0x002d:
        r0 = 10;
        goto L_0x0018;
    L_0x0030:
        r7 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        if (r6 >= r7) goto L_0x0022;
    L_0x0034:
        r6 = r6 * r0;
        r6 = r6 + r2;
        r3 = r10.read();
        goto L_0x0019;
    L_0x003b:
        if (r6 <= 0) goto L_0x0042;
    L_0x003d:
        r7 = 55295; // 0xd7ff float:7.7485E-41 double:2.73194E-319;
        if (r6 <= r7) goto L_0x0055;
    L_0x0042:
        r7 = 57344; // 0xe000 float:8.0356E-41 double:2.83317E-319;
        if (r6 < r7) goto L_0x004c;
    L_0x0047:
        r7 = 65533; // 0xfffd float:9.1831E-41 double:3.23776E-319;
        if (r6 <= r7) goto L_0x0055;
    L_0x004c:
        r7 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r6 < r7) goto L_0x0059;
    L_0x0050:
        r7 = 1114111; // 0x10ffff float:1.561202E-39 double:5.50444E-318;
        if (r6 > r7) goto L_0x0059;
    L_0x0055:
        r10.tokenBufferAppend(r6);
        goto L_0x002c;
    L_0x0059:
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "invalid character value ";
        r8 = r8.append(r9);
        r8 = r8.append(r6);
        r8 = r8.toString();
        r9 = "XQST0090";
        r10.error(r7, r8, r9);
        goto L_0x002c;
    L_0x0074:
        r5 = r10.tokenBufferLength;
    L_0x0076:
        if (r3 < 0) goto L_0x007f;
    L_0x0078:
        r1 = (char) r3;
        r7 = gnu.xml.XName.isNamePart(r1);
        if (r7 != 0) goto L_0x008a;
    L_0x007f:
        if (r3 == r8) goto L_0x0092;
    L_0x0081:
        r10.unread();
        r7 = "invalid entity reference";
        r10.error(r7);
        goto L_0x002c;
    L_0x008a:
        r10.tokenBufferAppend(r1);
        r3 = r10.read();
        goto L_0x0076;
    L_0x0092:
        r4 = new java.lang.String;
        r7 = r10.tokenBuffer;
        r8 = r10.tokenBufferLength;
        r8 = r8 - r5;
        r4.<init>(r7, r5, r8);
        r10.tokenBufferLength = r5;
        r10.appendNamedEntity(r4);
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseEntityOrCharRef():void");
    }

    void parseContent(char delimiter, Vector result) throws IOException, SyntaxException {
        Expression text;
        this.tokenBufferLength = AXIS_ANCESTOR;
        int prevEnclosed = result.size() + EOF_TOKEN;
        boolean skipBoundarySpace = !this.boundarySpacePreserve && delimiter == '<';
        boolean skippable = skipBoundarySpace;
        while (true) {
            int next = read();
            if (next == delimiter) {
                if (delimiter == '<') {
                    next = read();
                    text = null;
                    if (this.tokenBufferLength > 0) {
                        Expression[] args = new Expression[AXIS_ANCESTOR_OR_SELF];
                        args[AXIS_ANCESTOR] = new QuoteExp(new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength));
                        Expression applyExp = new ApplyExp(makeText, args);
                    }
                    this.tokenBufferLength = AXIS_ANCESTOR;
                    if (next == 47) {
                        break;
                    }
                    Expression content = parseXMLConstructor(next, true);
                    boolean isCDATA = false;
                    boolean emptyCDATA = false;
                    if (content instanceof ApplyExp) {
                        ApplyExp aexp = (ApplyExp) content;
                        if (aexp.getFunction() == makeCDATA) {
                            isCDATA = true;
                            String str = (String) aexp.getArg(AXIS_ANCESTOR).valueIfConstant();
                            emptyCDATA = str != null && str.length() == 0;
                        }
                    }
                    if (text != null && (!skippable || isCDATA)) {
                        result.addElement(text);
                    }
                    if (isCDATA) {
                        skippable = false;
                    } else {
                        skippable = skipBoundarySpace;
                    }
                    if (!emptyCDATA) {
                        result.addElement(content);
                    }
                    this.tokenBufferLength = AXIS_ANCESTOR;
                } else if (checkNext(delimiter)) {
                    tokenBufferAppend(delimiter);
                }
            }
            if (next == delimiter || next < '\u0000' || next == '{') {
                int postBrace = next == '{' ? read() : EOF_TOKEN;
                if (postBrace == 123) {
                    tokenBufferAppend(srfi1.$Pcprovide$Pcsrfi$Mn1);
                    skippable = false;
                } else {
                    String text2;
                    if (this.tokenBufferLength <= 0 || skippable) {
                        if (next == '{' && prevEnclosed == result.size()) {
                            text2 = ElementType.MATCH_ANY_LOCALNAME;
                        }
                        this.tokenBufferLength = AXIS_ANCESTOR;
                        if (next == delimiter) {
                            return;
                        }
                        if (next >= '\u0000') {
                            eofError("unexpected end-of-file");
                        } else {
                            unread(postBrace);
                            this.enclosedExpressionsSeen += AXIS_ANCESTOR_OR_SELF;
                            result.addElement(parseEnclosedExpr());
                            this.tokenBufferLength = AXIS_ANCESTOR;
                            prevEnclosed = result.size();
                            skippable = skipBoundarySpace;
                        }
                    } else {
                        text2 = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
                    }
                    args = new Expression[AXIS_ANCESTOR_OR_SELF];
                    args[AXIS_ANCESTOR] = new QuoteExp(text2);
                    result.addElement(new ApplyExp(makeText, args));
                    this.tokenBufferLength = AXIS_ANCESTOR;
                    if (next == delimiter) {
                        if (next >= '\u0000') {
                            unread(postBrace);
                            this.enclosedExpressionsSeen += AXIS_ANCESTOR_OR_SELF;
                            result.addElement(parseEnclosedExpr());
                            this.tokenBufferLength = AXIS_ANCESTOR;
                            prevEnclosed = result.size();
                            skippable = skipBoundarySpace;
                        } else {
                            eofError("unexpected end-of-file");
                        }
                    } else {
                        return;
                    }
                }
            } else if (next == '}') {
                next = read();
                if (next == 125) {
                    tokenBufferAppend(125);
                    skippable = false;
                } else {
                    error("unexpected '}' in element content");
                    unread(next);
                }
            } else if (next == '&') {
                parseEntityOrCharRef();
                skippable = false;
            } else {
                if (delimiter != '<' && (next == AXIS_PARENT || next == EOL_TOKEN || next == COUNT_OP_AXIS)) {
                    next = 32;
                }
                if (next == 60) {
                    error('e', "'<' must be quoted in a direct attribute value");
                }
                if (skippable) {
                    skippable = Character.isWhitespace((char) next);
                }
                tokenBufferAppend((char) next);
            }
        }
        if (text != null && !skippable) {
            result.addElement(text);
        }
    }

    Expression parseEnclosedExpr() throws IOException, SyntaxException {
        String saveErrorIfComment = this.errorIfComment;
        this.errorIfComment = null;
        char saveReadState = pushNesting('{');
        peekNonSpace("unexpected end-of-file after '{'");
        int startLine = getLineNumber() + AXIS_ANCESTOR_OR_SELF;
        int startColumn = getColumnNumber() + AXIS_ANCESTOR_OR_SELF;
        getRawToken();
        Expression exp = parseExpr();
        while (this.curToken != 125) {
            if (this.curToken == EOF_TOKEN || this.curToken == 41 || this.curToken == 93) {
                exp = syntaxError("missing '}'");
                break;
            }
            if (this.curToken != 44) {
                exp = syntaxError("missing '}' or ','");
            } else {
                getRawToken();
            }
            exp = makeExprSequence(exp, parseExpr());
        }
        maybeSetLine(exp, startLine, startColumn);
        popNesting(saveReadState);
        this.errorIfComment = saveErrorIfComment;
        return exp;
    }

    public static Expression booleanValue(Expression exp) {
        Expression[] args = new Expression[AXIS_ANCESTOR_OR_SELF];
        args[AXIS_ANCESTOR] = exp;
        return new ApplyExp(makeFunctionExp("gnu.xquery.util.BooleanValue", "booleanValue"), args);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    gnu.expr.Expression parseXMLConstructor(int r19, boolean r20) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r18 = this;
        r12 = 33;
        r0 = r19;
        if (r0 != r12) goto L_0x00ec;
    L_0x0006:
        r19 = r18.read();
        r12 = 45;
        r0 = r19;
        if (r0 != r12) goto L_0x0075;
    L_0x0010:
        r12 = r18.peek();
        r13 = 45;
        if (r12 != r13) goto L_0x0075;
    L_0x0018:
        r18.skip();
        r12 = "-->";
        r0 = r18;
        r0.getDelimited(r12);
        r2 = 0;
        r0 = r18;
        r7 = r0.tokenBufferLength;
        r10 = 1;
    L_0x0028:
        r7 = r7 + -1;
        if (r7 < 0) goto L_0x003c;
    L_0x002c:
        r0 = r18;
        r12 = r0.tokenBuffer;
        r12 = r12[r7];
        r13 = 45;
        if (r12 != r13) goto L_0x0047;
    L_0x0036:
        r5 = 1;
    L_0x0037:
        if (r10 == 0) goto L_0x0049;
    L_0x0039:
        if (r5 == 0) goto L_0x0049;
    L_0x003b:
        r2 = 1;
    L_0x003c:
        if (r2 == 0) goto L_0x004b;
    L_0x003e:
        r12 = "consecutive or final hyphen in XML comment";
        r0 = r18;
        r6 = r0.syntaxError(r12);
    L_0x0046:
        return r6;
    L_0x0047:
        r5 = 0;
        goto L_0x0037;
    L_0x0049:
        r10 = r5;
        goto L_0x0028;
    L_0x004b:
        r12 = 1;
        r1 = new gnu.expr.Expression[r12];
        r12 = 0;
        r13 = new gnu.expr.QuoteExp;
        r14 = new java.lang.String;
        r0 = r18;
        r15 = r0.tokenBuffer;
        r16 = 0;
        r0 = r18;
        r0 = r0.tokenBufferLength;
        r17 = r0;
        r14.<init>(r15, r16, r17);
        r13.<init>(r14);
        r1[r12] = r13;
        r6 = new gnu.expr.ApplyExp;
        r12 = "gnu.kawa.xml.CommentConstructor";
        r13 = "commentConstructor";
        r12 = makeFunctionExp(r12, r13);
        r6.<init>(r12, r1);
        goto L_0x0046;
    L_0x0075:
        r12 = 91;
        r0 = r19;
        if (r0 != r12) goto L_0x00e2;
    L_0x007b:
        r12 = r18.read();
        r13 = 67;
        if (r12 != r13) goto L_0x00e2;
    L_0x0083:
        r12 = r18.read();
        r13 = 68;
        if (r12 != r13) goto L_0x00e2;
    L_0x008b:
        r12 = r18.read();
        r13 = 65;
        if (r12 != r13) goto L_0x00e2;
    L_0x0093:
        r12 = r18.read();
        r13 = 84;
        if (r12 != r13) goto L_0x00e2;
    L_0x009b:
        r12 = r18.read();
        r13 = 65;
        if (r12 != r13) goto L_0x00e2;
    L_0x00a3:
        r12 = r18.read();
        r13 = 91;
        if (r12 != r13) goto L_0x00e2;
    L_0x00ab:
        if (r20 != 0) goto L_0x00b6;
    L_0x00ad:
        r12 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r13 = "CDATA section must be in element content";
        r0 = r18;
        r0.error(r12, r13);
    L_0x00b6:
        r12 = "]]>";
        r0 = r18;
        r0.getDelimited(r12);
        r12 = 1;
        r1 = new gnu.expr.Expression[r12];
        r12 = 0;
        r13 = new gnu.expr.QuoteExp;
        r14 = new java.lang.String;
        r0 = r18;
        r15 = r0.tokenBuffer;
        r16 = 0;
        r0 = r18;
        r0 = r0.tokenBufferLength;
        r17 = r0;
        r14.<init>(r15, r16, r17);
        r13.<init>(r14);
        r1[r12] = r13;
        r6 = new gnu.expr.ApplyExp;
        r12 = makeCDATA;
        r6.<init>(r12, r1);
        goto L_0x0046;
    L_0x00e2:
        r12 = "'<!' must be followed by '--' or '[CDATA['";
        r0 = r18;
        r6 = r0.syntaxError(r12);
        goto L_0x0046;
    L_0x00ec:
        r12 = 63;
        r0 = r19;
        if (r0 != r12) goto L_0x0179;
    L_0x00f2:
        r19 = r18.peek();
        if (r19 < 0) goto L_0x0109;
    L_0x00f8:
        r0 = r19;
        r12 = (char) r0;
        r12 = gnu.xml.XName.isNameStart(r12);
        if (r12 == 0) goto L_0x0109;
    L_0x0101:
        r12 = r18.getRawToken();
        r13 = 65;
        if (r12 == r13) goto L_0x0110;
    L_0x0109:
        r12 = "missing target after '<?'";
        r0 = r18;
        r0.syntaxError(r12);
    L_0x0110:
        r11 = new java.lang.String;
        r0 = r18;
        r12 = r0.tokenBuffer;
        r13 = 0;
        r0 = r18;
        r14 = r0.tokenBufferLength;
        r11.<init>(r12, r13, r14);
        r8 = 0;
    L_0x011f:
        r3 = r18.read();
        if (r3 >= 0) goto L_0x016b;
    L_0x0125:
        r12 = "?>";
        r0 = r18;
        r0.getDelimited(r12);
        if (r8 != 0) goto L_0x013b;
    L_0x012e:
        r0 = r18;
        r12 = r0.tokenBufferLength;
        if (r12 <= 0) goto L_0x013b;
    L_0x0134:
        r12 = "target must be followed by space or '?>'";
        r0 = r18;
        r0.syntaxError(r12);
    L_0x013b:
        r4 = new java.lang.String;
        r0 = r18;
        r12 = r0.tokenBuffer;
        r13 = 0;
        r0 = r18;
        r14 = r0.tokenBufferLength;
        r4.<init>(r12, r13, r14);
        r12 = 2;
        r1 = new gnu.expr.Expression[r12];
        r12 = 0;
        r13 = new gnu.expr.QuoteExp;
        r13.<init>(r11);
        r1[r12] = r13;
        r12 = 1;
        r13 = new gnu.expr.QuoteExp;
        r13.<init>(r4);
        r1[r12] = r13;
        r6 = new gnu.expr.ApplyExp;
        r12 = "gnu.kawa.xml.MakeProcInst";
        r13 = "makeProcInst";
        r12 = makeFunctionExp(r12, r13);
        r6.<init>(r12, r1);
        goto L_0x0046;
    L_0x016b:
        r12 = (char) r3;
        r12 = java.lang.Character.isWhitespace(r12);
        if (r12 != 0) goto L_0x0176;
    L_0x0172:
        r18.unread();
        goto L_0x0125;
    L_0x0176:
        r8 = r8 + 1;
        goto L_0x011f;
    L_0x0179:
        if (r19 < 0) goto L_0x0184;
    L_0x017b:
        r0 = r19;
        r12 = (char) r0;
        r12 = gnu.xml.XName.isNameStart(r12);
        if (r12 != 0) goto L_0x018e;
    L_0x0184:
        r12 = "expected QName after '<'";
        r0 = r18;
        r6 = r0.syntaxError(r12);
        goto L_0x0046;
    L_0x018e:
        r18.unread(r19);
        r18.getRawToken();
        r12 = 60;
        r0 = r18;
        r9 = r0.pushNesting(r12);
        r6 = r18.parseElementConstructor();
        if (r20 != 0) goto L_0x01a8;
    L_0x01a2:
        r0 = r18;
        r6 = r0.wrapWithBaseUri(r6);
    L_0x01a8:
        r0 = r18;
        r0.popNesting(r9);
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseXMLConstructor(int, boolean):gnu.expr.Expression");
    }

    static ApplyExp castQName(Expression value, boolean element) {
        Expression referenceExp = new ReferenceExp(element ? XQResolveNames.xsQNameDecl : XQResolveNames.xsQNameIgnoreDefaultDecl);
        Expression[] expressionArr = new Expression[AXIS_ANCESTOR_OR_SELF];
        expressionArr[AXIS_ANCESTOR] = value;
        return new ApplyExp(referenceExp, expressionArr);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    gnu.expr.Expression parseElementConstructor() throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r32 = this;
        r24 = new java.lang.String;
        r0 = r32;
        r0 = r0.tokenBuffer;
        r29 = r0;
        r30 = 0;
        r0 = r32;
        r0 = r0.tokenBufferLength;
        r31 = r0;
        r0 = r24;
        r1 = r29;
        r2 = r30;
        r3 = r31;
        r0.<init>(r1, r2, r3);
        r26 = new java.util.Vector;
        r26.<init>();
        r29 = new gnu.expr.QuoteExp;
        r0 = r29;
        r1 = r24;
        r0.<init>(r1);
        r30 = 1;
        r29 = castQName(r29, r30);
        r0 = r26;
        r1 = r29;
        r0.addElement(r1);
        r29 = "comment not allowed in element start tag";
        r0 = r29;
        r1 = r32;
        r1.errorIfComment = r0;
        r17 = 0;
    L_0x0040:
        r21 = 0;
        r8 = r32.read();
    L_0x0046:
        if (r8 < 0) goto L_0x0058;
    L_0x0048:
        r0 = (char) r8;
        r29 = r0;
        r29 = java.lang.Character.isWhitespace(r29);
        if (r29 == 0) goto L_0x0058;
    L_0x0051:
        r8 = r32.read();
        r21 = 1;
        goto L_0x0046;
    L_0x0058:
        if (r8 < 0) goto L_0x0066;
    L_0x005a:
        r29 = 62;
        r0 = r29;
        if (r8 == r0) goto L_0x0066;
    L_0x0060:
        r29 = 47;
        r0 = r29;
        if (r8 != r0) goto L_0x0093;
    L_0x0066:
        r29 = 0;
        r0 = r29;
        r1 = r32;
        r1.errorIfComment = r0;
        r10 = 0;
        r29 = 47;
        r0 = r29;
        if (r8 != r0) goto L_0x0080;
    L_0x0075:
        r8 = r32.read();
        r29 = 62;
        r0 = r29;
        if (r8 != r0) goto L_0x0292;
    L_0x007f:
        r10 = 1;
    L_0x0080:
        if (r10 != 0) goto L_0x0342;
    L_0x0082:
        r29 = 62;
        r0 = r29;
        if (r8 == r0) goto L_0x0299;
    L_0x0088:
        r29 = "missing '>' after start element";
        r0 = r32;
        r1 = r29;
        r20 = r0.syntaxError(r1);
    L_0x0092:
        return r20;
    L_0x0093:
        if (r21 != 0) goto L_0x009e;
    L_0x0095:
        r29 = "missing space before attribute";
        r0 = r32;
        r1 = r29;
        r0.syntaxError(r1);
    L_0x009e:
        r0 = r32;
        r0.unread(r8);
        r32.getRawToken();
        r27 = r26.size();
        r0 = r32;
        r0 = r0.curToken;
        r29 = r0;
        r30 = 65;
        r0 = r29;
        r1 = r30;
        if (r0 == r1) goto L_0x00c6;
    L_0x00b8:
        r0 = r32;
        r0 = r0.curToken;
        r29 = r0;
        r30 = 81;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x0066;
    L_0x00c6:
        r6 = new java.lang.String;
        r0 = r32;
        r0 = r0.tokenBuffer;
        r29 = r0;
        r30 = 0;
        r0 = r32;
        r0 = r0.tokenBufferLength;
        r31 = r0;
        r0 = r29;
        r1 = r30;
        r2 = r31;
        r6.<init>(r0, r1, r2);
        r29 = r32.getLineNumber();
        r23 = r29 + 1;
        r29 = r32.getColumnNumber();
        r29 = r29 + 1;
        r0 = r32;
        r0 = r0.tokenBufferLength;
        r30 = r0;
        r22 = r29 - r30;
        r9 = 0;
        r0 = r32;
        r0 = r0.curToken;
        r29 = r0;
        r30 = 65;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x0143;
    L_0x0102:
        r29 = "xmlns";
        r0 = r29;
        r29 = r6.equals(r0);
        if (r29 == 0) goto L_0x010e;
    L_0x010c:
        r9 = "";
    L_0x010e:
        if (r9 == 0) goto L_0x015a;
    L_0x0110:
        r13 = 0;
    L_0x0111:
        r29 = new gnu.expr.QuoteExp;
        r0 = r29;
        r0.<init>(r6);
        r30 = 0;
        r29 = castQName(r29, r30);
        r0 = r26;
        r1 = r29;
        r0.addElement(r1);
        r8 = r32.skipSpace();
        r29 = 61;
        r0 = r29;
        if (r8 == r0) goto L_0x015d;
    L_0x012f:
        r29 = 0;
        r0 = r29;
        r1 = r32;
        r1.errorIfComment = r0;
        r29 = "missing '=' after attribute";
        r0 = r32;
        r1 = r29;
        r20 = r0.syntaxError(r1);
        goto L_0x0092;
    L_0x0143:
        r29 = "xmlns:";
        r0 = r29;
        r29 = r6.startsWith(r0);
        if (r29 == 0) goto L_0x010e;
    L_0x014d:
        r29 = 6;
        r0 = r29;
        r29 = r6.substring(r0);
        r9 = r29.intern();
        goto L_0x010e;
    L_0x015a:
        r13 = gnu.kawa.xml.MakeAttribute.makeAttributeExp;
        goto L_0x0111;
    L_0x015d:
        r8 = r32.skipSpace();
        r0 = r32;
        r11 = r0.enclosedExpressionsSeen;
        r29 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r0 = r29;
        if (r8 != r0) goto L_0x01de;
    L_0x016b:
        r29 = "enclosed attribute value expression should be quoted";
        r0 = r32;
        r1 = r29;
        r0.warnOldVersion(r1);
        r29 = r32.parseEnclosedExpr();
        r0 = r26;
        r1 = r29;
        r0.addElement(r1);
    L_0x017f:
        r29 = r26.size();
        r15 = r29 - r27;
        if (r9 == 0) goto L_0x0264;
    L_0x0187:
        r16 = "";
        r29 = 1;
        r0 = r29;
        if (r15 != r0) goto L_0x01eb;
    L_0x018f:
        r16 = "";
    L_0x0191:
        r26.setSize(r27);
        r29 = 1;
        r0 = r32;
        r1 = r16;
        r2 = r29;
        r0.checkAllowedNamespaceDeclaration(r9, r1, r2);
        r29 = "";
        r0 = r29;
        if (r9 != r0) goto L_0x01a6;
    L_0x01a5:
        r9 = 0;
    L_0x01a6:
        r19 = r17;
    L_0x01a8:
        if (r19 == 0) goto L_0x01c5;
    L_0x01aa:
        r29 = r19.getPrefix();
        r0 = r29;
        if (r0 != r9) goto L_0x025e;
    L_0x01b2:
        r30 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r9 != 0) goto L_0x0239;
    L_0x01b6:
        r29 = "duplicate default namespace declaration";
    L_0x01b8:
        r31 = "XQST0071";
        r0 = r32;
        r1 = r30;
        r2 = r29;
        r3 = r31;
        r0.error(r1, r2, r3);
    L_0x01c5:
        r18 = new gnu.xml.NamespaceBinding;
        r29 = "";
        r0 = r16;
        r1 = r29;
        if (r0 != r1) goto L_0x01d1;
    L_0x01cf:
        r16 = 0;
    L_0x01d1:
        r0 = r18;
        r1 = r16;
        r2 = r17;
        r0.<init>(r9, r1, r2);
        r17 = r18;
        goto L_0x0040;
    L_0x01de:
        r0 = (char) r8;
        r29 = r0;
        r0 = r32;
        r1 = r29;
        r2 = r26;
        r0.parseContent(r1, r2);
        goto L_0x017f;
    L_0x01eb:
        r0 = r32;
        r0 = r0.enclosedExpressionsSeen;
        r29 = r0;
        r0 = r29;
        if (r0 <= r11) goto L_0x01ff;
    L_0x01f5:
        r29 = "enclosed expression not allowed in namespace declaration";
        r0 = r32;
        r1 = r29;
        r0.syntaxError(r1);
        goto L_0x0191;
    L_0x01ff:
        r29 = r27 + 1;
        r0 = r26;
        r1 = r29;
        r28 = r0.elementAt(r1);
        r0 = r28;
        r0 = r0 instanceof gnu.expr.ApplyExp;
        r29 = r0;
        if (r29 == 0) goto L_0x0229;
    L_0x0211:
        r7 = r28;
        r7 = (gnu.expr.ApplyExp) r7;
        r29 = r7.getFunction();
        r30 = makeText;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x0229;
    L_0x0221:
        r29 = 0;
        r0 = r29;
        r28 = r7.getArg(r0);
    L_0x0229:
        r28 = (gnu.expr.QuoteExp) r28;
        r29 = r28.getValue();
        r29 = r29.toString();
        r16 = r29.intern();
        goto L_0x0191;
    L_0x0239:
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r31 = "duplicate namespace prefix '";
        r0 = r29;
        r1 = r31;
        r29 = r0.append(r1);
        r0 = r29;
        r29 = r0.append(r9);
        r31 = 39;
        r0 = r29;
        r1 = r31;
        r29 = r0.append(r1);
        r29 = r29.toString();
        goto L_0x01b8;
    L_0x025e:
        r19 = r19.getNext();
        goto L_0x01a8;
    L_0x0264:
        r5 = new gnu.expr.Expression[r15];
        r12 = r15;
    L_0x0267:
        r12 = r12 + -1;
        if (r12 < 0) goto L_0x027a;
    L_0x026b:
        r29 = r27 + r12;
        r0 = r26;
        r1 = r29;
        r29 = r0.elementAt(r1);
        r29 = (gnu.expr.Expression) r29;
        r5[r12] = r29;
        goto L_0x0267;
    L_0x027a:
        r26.setSize(r27);
        r4 = new gnu.expr.ApplyExp;
        r4.<init>(r13, r5);
        r0 = r32;
        r1 = r23;
        r2 = r22;
        r0.maybeSetLine(r4, r1, r2);
        r0 = r26;
        r0.addElement(r4);
        goto L_0x0040;
    L_0x0292:
        r0 = r32;
        r0.unread(r8);
        goto L_0x0080;
    L_0x0299:
        r29 = 60;
        r0 = r32;
        r1 = r29;
        r2 = r26;
        r0.parseContent(r1, r2);
        r8 = r32.peek();
        if (r8 < 0) goto L_0x0330;
    L_0x02aa:
        r0 = (char) r8;
        r29 = r0;
        r29 = gnu.xml.XName.isNameStart(r29);
        if (r29 != 0) goto L_0x02bf;
    L_0x02b3:
        r29 = "invalid tag syntax after '</'";
        r0 = r32;
        r1 = r29;
        r20 = r0.syntaxError(r1);
        goto L_0x0092;
    L_0x02bf:
        r32.getRawToken();
        r25 = new java.lang.String;
        r0 = r32;
        r0 = r0.tokenBuffer;
        r29 = r0;
        r30 = 0;
        r0 = r32;
        r0 = r0.tokenBufferLength;
        r31 = r0;
        r0 = r25;
        r1 = r29;
        r2 = r30;
        r3 = r31;
        r0.<init>(r1, r2, r3);
        r0 = r25;
        r1 = r24;
        r29 = r0.equals(r1);
        if (r29 != 0) goto L_0x031c;
    L_0x02e7:
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "'<";
        r29 = r29.append(r30);
        r0 = r29;
        r1 = r24;
        r29 = r0.append(r1);
        r30 = ">' closed by '</";
        r29 = r29.append(r30);
        r0 = r29;
        r1 = r25;
        r29 = r0.append(r1);
        r30 = ">'";
        r29 = r29.append(r30);
        r29 = r29.toString();
        r0 = r32;
        r1 = r29;
        r20 = r0.syntaxError(r1);
        goto L_0x0092;
    L_0x031c:
        r29 = "comment not allowed in element end tag";
        r0 = r29;
        r1 = r32;
        r1.errorIfComment = r0;
        r8 = r32.skipSpace();
        r29 = 0;
        r0 = r29;
        r1 = r32;
        r1.errorIfComment = r0;
    L_0x0330:
        r29 = 62;
        r0 = r29;
        if (r8 == r0) goto L_0x0342;
    L_0x0336:
        r29 = "missing '>' after end element";
        r0 = r32;
        r1 = r29;
        r20 = r0.syntaxError(r1);
        goto L_0x0092;
    L_0x0342:
        r29 = r26.size();
        r0 = r29;
        r5 = new gnu.expr.Expression[r0];
        r0 = r26;
        r0.copyInto(r5);
        r14 = new gnu.kawa.xml.MakeElement;
        r14.<init>();
        r0 = r32;
        r0 = r0.copyNamespacesMode;
        r29 = r0;
        r0 = r29;
        r14.copyNamespacesMode = r0;
        r0 = r17;
        r14.setNamespaceNodes(r0);
        r20 = new gnu.expr.ApplyExp;
        r29 = new gnu.expr.QuoteExp;
        r0 = r29;
        r0.<init>(r14);
        r0 = r20;
        r1 = r29;
        r0.<init>(r1, r5);
        goto L_0x0092;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseElementConstructor():gnu.expr.Expression");
    }

    Expression wrapWithBaseUri(Expression exp) {
        if (getStaticBaseUri() == null) {
            return exp;
        }
        Procedure procedure = MakeWithBaseUri.makeWithBaseUri;
        Expression[] expressionArr = new Expression[AXIS_ATTRIBUTE];
        expressionArr[AXIS_ANCESTOR] = new ApplyExp(new ReferenceExp(XQResolveNames.staticBaseUriDecl), Expression.noExpressions);
        expressionArr[AXIS_ANCESTOR_OR_SELF] = exp;
        return new ApplyExp(procedure, expressionArr).setLine(exp);
    }

    Expression parseParenExpr() throws IOException, SyntaxException {
        getRawToken();
        char saveReadState = pushNesting('(');
        Expression exp = parseExprSequence(41, true);
        popNesting(saveReadState);
        if (this.curToken == EOF_TOKEN) {
            eofError("missing ')' - unexpected end-of-file");
        }
        return exp;
    }

    Expression parseExprSequence(int rightToken, boolean optional) throws IOException, SyntaxException {
        if (this.curToken == rightToken || this.curToken == EOF_TOKEN) {
            if (!optional) {
                syntaxError("missing expression");
            }
            return QuoteExp.voidExp;
        }
        String str;
        Expression exp = null;
        while (true) {
            Expression exp1 = parseExprSingle();
            exp = exp == null ? exp1 : makeExprSequence(exp, exp1);
            if (this.curToken == rightToken || this.curToken == EOF_TOKEN) {
                return exp;
            }
            if (this.nesting == 0 && this.curToken == EOL_TOKEN) {
                return exp;
            }
            if (this.curToken != 44) {
                break;
            }
            getRawToken();
        }
        if (rightToken == 41) {
            str = "expected ')'";
        } else {
            str = "confused by syntax error";
        }
        return syntaxError(str);
    }

    Expression parseTypeSwitch() throws IOException, SyntaxException {
        Declaration decl;
        char c = 'e';
        char save = pushNesting('t');
        Expression selector = parseParenExpr();
        getRawToken();
        Vector vec = new Vector();
        vec.addElement(selector);
        while (match("case")) {
            pushNesting('c');
            getRawToken();
            if (this.curToken == 36) {
                decl = parseVariableDeclaration();
                if (decl == null) {
                    return syntaxError("missing Variable after '$'");
                }
                getRawToken();
                if (match("as")) {
                    getRawToken();
                } else {
                    error('e', "missing 'as'");
                }
            } else {
                decl = new Declaration((Object) "(arg)");
            }
            decl.setTypeExp(parseDataType());
            popNesting('t');
            ScopeExp lexp = new LambdaExp((int) AXIS_ANCESTOR_OR_SELF);
            lexp.addDeclaration(decl);
            if (match("return")) {
                getRawToken();
            } else {
                error("missing 'return' after 'case'");
            }
            this.comp.push(lexp);
            pushNesting('r');
            lexp.body = parseExpr();
            popNesting('t');
            this.comp.pop(lexp);
            vec.addElement(lexp);
        }
        if (match("default")) {
            lexp = new LambdaExp((int) AXIS_ANCESTOR_OR_SELF);
            getRawToken();
            if (this.curToken == 36) {
                decl = parseVariableDeclaration();
                if (decl == null) {
                    return syntaxError("missing Variable after '$'");
                }
                getRawToken();
            } else {
                decl = new Declaration((Object) "(arg)");
            }
            lexp.addDeclaration(decl);
            if (match("return")) {
                getRawToken();
            } else {
                error("missing 'return' after 'default'");
            }
            this.comp.push(lexp);
            lexp.body = parseExpr();
            this.comp.pop(lexp);
            vec.addElement(lexp);
        } else {
            if (!this.comp.isPedantic()) {
                c = 'w';
            }
            error(c, "no 'default' clause in 'typeswitch'", "XPST0003");
        }
        popNesting(save);
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        return new ApplyExp(makeFunctionExp("gnu.kawa.reflect.TypeSwitch", "typeSwitch"), args);
    }

    Expression parseMaybePrimaryExpr() throws IOException, SyntaxException {
        Expression quoteExp;
        int startLine = this.curLine;
        int startColumn = this.curColumn;
        int token = peekOperand();
        Vector vec;
        int i;
        Expression[] args;
        Expression referenceExp;
        char saveReadState;
        switch (token) {
            case STRING_TOKEN /*34*/:
                quoteExp = new QuoteExp(new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength).intern());
                break;
            case Sequence.COMMENT_VALUE /*36*/:
                Object name = parseVariable();
                if (name != null) {
                    quoteExp = new ReferenceExp(name);
                    maybeSetLine(quoteExp, this.curLine, this.curColumn);
                    break;
                }
                return syntaxError("missing Variable");
            case XDataType.TOKEN_TYPE_CODE /*40*/:
                quoteExp = parseParenExpr();
                break;
            case XDataType.UNTYPED_TYPE_CODE /*48*/:
                quoteExp = new QuoteExp(IntNum.valueOf(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength, EOL_TOKEN, false));
                break;
            case 49:
            case 50:
                String str = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
                if (token == 49) {
                    try {
                        BigDecimal bigDecimal = new BigDecimal(str);
                    } catch (Throwable th) {
                        quoteExp = syntaxError("invalid decimal literal: '" + str + "'");
                        break;
                    }
                }
                Number d = new Double(str);
                quoteExp = new QuoteExp(r27);
                break;
            case FNAME_TOKEN /*70*/:
                String name2 = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
                char save = pushNesting('(');
                getRawToken();
                vec = new Vector(EOL_TOKEN);
                i = this.curToken;
                if (r0 != 41) {
                    while (true) {
                        vec.addElement(parseExpr());
                        i = this.curToken;
                        if (r0 != 41) {
                            i = this.curToken;
                            if (r0 != 44) {
                                return syntaxError("missing ')' after function call");
                            }
                            getRawToken();
                        }
                    }
                }
                args = new Expression[vec.size()];
                vec.copyInto(args);
                referenceExp = new ReferenceExp(name2, null);
                referenceExp.setProcedureName(true);
                quoteExp = new ApplyExp(referenceExp, args);
                maybeSetLine(quoteExp, startLine, startColumn);
                popNesting(save);
                break;
            case srfi1.$Pcprovide$Pcsrfi$Mn1:
                quoteExp = syntaxError("saw unexpected '{' - assume you meant '('");
                parseEnclosedExpr();
                break;
            case PRAGMA_START_TOKEN /*197*/:
                Stack extArgs = new Stack();
                do {
                    Expression qname;
                    StringBuffer sbuf;
                    int spaces;
                    int ch;
                    getRawToken();
                    i = this.curToken;
                    if (r0 != QNAME_TOKEN) {
                        i = this.curToken;
                        if (r0 != NCNAME_TOKEN) {
                            qname = syntaxError("missing pragma name");
                            extArgs.push(qname);
                            sbuf = new StringBuffer();
                            spaces = EOF_TOKEN;
                            do {
                                ch = read();
                                spaces += AXIS_ANCESTOR_OR_SELF;
                                if (ch < 0) {
                                }
                                while (true) {
                                    if (ch == 35 || peek() != 41) {
                                        if (ch < 0) {
                                            eofError("pragma ended by end-of-file");
                                        }
                                        if (spaces != 0) {
                                            error("missing space between pragma and extension content");
                                        }
                                        spaces = AXIS_ANCESTOR_OR_SELF;
                                        sbuf.append((char) ch);
                                        ch = read();
                                    } else {
                                        read();
                                        extArgs.push(QuoteExp.getInstance(sbuf.toString()));
                                        getRawToken();
                                        i = this.curToken;
                                    }
                                }
                            } while (Character.isWhitespace((char) ch));
                            while (true) {
                                if (ch == 35) {
                                    break;
                                }
                                if (ch < 0) {
                                    eofError("pragma ended by end-of-file");
                                }
                                if (spaces != 0) {
                                    error("missing space between pragma and extension content");
                                }
                                spaces = AXIS_ANCESTOR_OR_SELF;
                                sbuf.append((char) ch);
                                ch = read();
                            }
                        }
                    }
                    qname = QuoteExp.getInstance(new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength));
                    extArgs.push(qname);
                    sbuf = new StringBuffer();
                    spaces = EOF_TOKEN;
                    do {
                        ch = read();
                        spaces += AXIS_ANCESTOR_OR_SELF;
                        if (ch < 0) {
                        }
                        while (true) {
                            if (ch == 35) {
                            }
                            if (ch < 0) {
                                eofError("pragma ended by end-of-file");
                            }
                            if (spaces != 0) {
                                error("missing space between pragma and extension content");
                            }
                            spaces = AXIS_ANCESTOR_OR_SELF;
                            sbuf.append((char) ch);
                            ch = read();
                        }
                    } while (Character.isWhitespace((char) ch));
                    while (true) {
                        if (ch == 35) {
                        }
                        if (ch < 0) {
                            eofError("pragma ended by end-of-file");
                        }
                        if (spaces != 0) {
                            error("missing space between pragma and extension content");
                        }
                        spaces = AXIS_ANCESTOR_OR_SELF;
                        sbuf.append((char) ch);
                        ch = read();
                    }
                } while (r0 == PRAGMA_START_TOKEN);
                i = this.curToken;
                if (r0 != 123) {
                    quoteExp = syntaxError("missing '{' after pragma");
                    break;
                }
                getRawToken();
                i = this.curToken;
                if (r0 != 125) {
                    saveReadState = pushNesting('{');
                    extArgs.push(parseExprSequence(125, false));
                    popNesting(saveReadState);
                    i = this.curToken;
                    if (r0 == EOF_TOKEN) {
                        eofError("missing '}' - unexpected end-of-file");
                    }
                }
                args = new Expression[extArgs.size()];
                extArgs.copyInto(args);
                quoteExp = new ApplyExp(new ReferenceExp(XQResolveNames.handleExtensionDecl), args);
                break;
            case ORDERED_LBRACE_TOKEN /*249*/:
            case UNORDERED_LBRACE_TOKEN /*250*/:
                getRawToken();
                quoteExp = parseExprSequence(125, false);
                break;
            case ELEMENT_TOKEN /*251*/:
            case ATTRIBUTE_TOKEN /*252*/:
            case TEXT_TOKEN /*253*/:
            case COMMENT_TOKEN /*254*/:
            case PI_TOKEN /*255*/:
            case DOCUMENT_TOKEN /*256*/:
                Expression func;
                getRawToken();
                vec = new Vector();
                if (token == ELEMENT_TOKEN || token == ATTRIBUTE_TOKEN) {
                    Expression element;
                    MakeElement mk;
                    i = this.curToken;
                    if (r0 != NCNAME_TOKEN) {
                        i = this.curToken;
                        if (r0 != QNAME_TOKEN) {
                            i = this.curToken;
                            if (r0 == 123) {
                                element = parseEnclosedExpr();
                                vec.addElement(castQName(element, token != ELEMENT_TOKEN));
                                if (token != ELEMENT_TOKEN) {
                                    mk = new MakeElement();
                                    mk.copyNamespacesMode = this.copyNamespacesMode;
                                    func = new QuoteExp(mk);
                                } else {
                                    func = MakeAttribute.makeAttributeExp;
                                }
                                getRawToken();
                            } else {
                                return syntaxError("missing element/attribute name");
                            }
                        }
                    }
                    element = parseNameTest(token != ELEMENT_TOKEN);
                    if (token != ELEMENT_TOKEN) {
                    }
                    vec.addElement(castQName(element, token != ELEMENT_TOKEN));
                    if (token != ELEMENT_TOKEN) {
                        func = MakeAttribute.makeAttributeExp;
                    } else {
                        mk = new MakeElement();
                        mk.copyNamespacesMode = this.copyNamespacesMode;
                        func = new QuoteExp(mk);
                    }
                    getRawToken();
                } else if (token == DOCUMENT_TOKEN) {
                    func = makeFunctionExp("gnu.kawa.xml.DocumentConstructor", "documentConstructor");
                } else if (token == COMMENT_TOKEN) {
                    func = makeFunctionExp("gnu.kawa.xml.CommentConstructor", "commentConstructor");
                } else if (token == PI_TOKEN) {
                    Expression target;
                    i = this.curToken;
                    if (r0 == NCNAME_TOKEN) {
                        referenceExp = new QuoteExp(new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength).intern());
                    } else {
                        i = this.curToken;
                        if (r0 == 123) {
                            target = parseEnclosedExpr();
                        } else {
                            target = syntaxError("expected NCName or '{' after 'processing-instruction'");
                            i = this.curToken;
                            if (r0 != QNAME_TOKEN) {
                                return target;
                            }
                        }
                    }
                    vec.addElement(target);
                    func = makeFunctionExp("gnu.kawa.xml.MakeProcInst", "makeProcInst");
                    getRawToken();
                } else {
                    func = makeFunctionExp("gnu.kawa.xml.MakeText", "makeText");
                }
                saveReadState = pushNesting('{');
                peekNonSpace("unexpected end-of-file after '{'");
                i = this.curToken;
                if (r0 == 123) {
                    getRawToken();
                    if (token == TEXT_TOKEN || token == COMMENT_TOKEN || token == PI_TOKEN) {
                        vec.addElement(parseExprSequence(125, token == PI_TOKEN));
                    } else {
                        i = this.curToken;
                        if (r0 != 125) {
                            vec.addElement(parseExpr());
                            while (true) {
                                i = this.curToken;
                                if (r0 == 44) {
                                    getRawToken();
                                    vec.addElement(parseExpr());
                                }
                            }
                        }
                    }
                    popNesting(saveReadState);
                    i = this.curToken;
                    if (r0 == 125) {
                        args = new Expression[vec.size()];
                        vec.copyInto(args);
                        quoteExp = new ApplyExp(func, args);
                        maybeSetLine(quoteExp, startLine, startColumn);
                        if (token == DOCUMENT_TOKEN || token == ELEMENT_TOKEN) {
                            quoteExp = wrapWithBaseUri(quoteExp);
                            break;
                        }
                    }
                    return syntaxError("missing '}'");
                }
                return syntaxError("missing '{'");
                break;
            case OP_LSS /*404*/:
                int next = read();
                if (next != 47) {
                    quoteExp = parseXMLConstructor(next, false);
                    maybeSetLine(quoteExp, startLine, startColumn);
                    break;
                }
                String msg;
                getRawToken();
                i = this.curToken;
                if (r0 != NCNAME_TOKEN) {
                    i = this.curToken;
                    if (r0 != QNAME_TOKEN) {
                        i = this.curToken;
                        if (r0 != NCNAME_COLON_TOKEN) {
                            msg = "saw end tag '</' not in an element constructor";
                            this.curLine = startLine;
                            this.curColumn = startColumn;
                            quoteExp = syntaxError(msg);
                            while (true) {
                                i = this.curToken;
                                if (r0 != OP_GRT) {
                                    return quoteExp;
                                }
                                i = this.curToken;
                                if (r0 != EOF_TOKEN) {
                                    return quoteExp;
                                }
                                i = this.curToken;
                                if (r0 != EOL_TOKEN) {
                                    return quoteExp;
                                }
                                getRawToken();
                            }
                        }
                    }
                }
                msg = "saw end tag '</" + new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength) + ">' not in an element constructor";
                this.curLine = startLine;
                this.curColumn = startColumn;
                quoteExp = syntaxError(msg);
                while (true) {
                    i = this.curToken;
                    if (r0 != OP_GRT) {
                        return quoteExp;
                    }
                    i = this.curToken;
                    if (r0 != EOF_TOKEN) {
                        return quoteExp;
                    }
                    i = this.curToken;
                    if (r0 != EOL_TOKEN) {
                        return quoteExp;
                    }
                    getRawToken();
                }
            default:
                return null;
        }
        getRawToken();
        return quoteExp;
    }

    public Expression parseIfExpr() throws IOException, SyntaxException {
        char saveReadState1 = pushNesting('i');
        getRawToken();
        char saveReadState2 = pushNesting('(');
        Expression cond = parseExprSequence(41, false);
        popNesting(saveReadState2);
        if (this.curToken == EOF_TOKEN) {
            eofError("missing ')' - unexpected end-of-file");
        }
        getRawToken();
        if (match("then")) {
            getRawToken();
        } else {
            syntaxError("missing 'then'");
        }
        Expression thenPart = parseExpr();
        if (match("else")) {
            getRawToken();
        } else {
            syntaxError("missing 'else'");
        }
        popNesting(saveReadState1);
        return new IfExp(booleanValue(cond), thenPart, parseExpr());
    }

    public boolean match(String word) {
        if (this.curToken != NCNAME_TOKEN) {
            return false;
        }
        int len = word.length();
        if (this.tokenBufferLength != len) {
            return false;
        }
        int i = len;
        do {
            i += EOF_TOKEN;
            if (i < 0) {
                return true;
            }
        } while (word.charAt(i) == this.tokenBuffer[i]);
        return false;
    }

    public Object parseVariable() throws IOException, SyntaxException {
        if (this.curToken == 36) {
            getRawToken();
        } else {
            syntaxError("missing '$' before variable name");
        }
        String str = new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
        if (this.curToken == QNAME_TOKEN) {
            return str;
        }
        if (this.curToken == NCNAME_TOKEN) {
            return Namespace.EmptyNamespace.getSymbol(str.intern());
        }
        return null;
    }

    public Declaration parseVariableDeclaration() throws IOException, SyntaxException {
        Object name = parseVariable();
        if (name == null) {
            return null;
        }
        Declaration decl = new Declaration(name);
        maybeSetLine(decl, getLineNumber() + AXIS_ANCESTOR_OR_SELF, (getColumnNumber() + AXIS_ANCESTOR_OR_SELF) - this.tokenBufferLength);
        return decl;
    }

    public Expression parseFLWRExpression(boolean isFor) throws IOException, SyntaxException {
        int flworDeclsSave = this.flworDeclsFirst;
        this.flworDeclsFirst = this.flworDeclsCount;
        Expression exp = parseFLWRInner(isFor);
        if (match("order")) {
            ScopeExp lexp;
            int i;
            int i2;
            char saveNesting = pushNesting(isFor ? 'f' : 'l');
            getRawToken();
            if (match("by")) {
                getRawToken();
            } else {
                error("missing 'by' following 'order'");
            }
            Stack specs = new Stack();
            while (true) {
                boolean descending = false;
                char emptyOrder = this.defaultEmptyOrder;
                lexp = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
                i = this.flworDeclsFirst;
                while (true) {
                    i2 = this.flworDeclsCount;
                    if (i >= r0) {
                        break;
                    }
                    lexp.addDeclaration(this.flworDecls[i].getSymbol());
                    i += AXIS_ANCESTOR_OR_SELF;
                }
                this.comp.push(lexp);
                lexp.body = parseExprSingle();
                this.comp.pop(lexp);
                specs.push(lexp);
                if (match("ascending")) {
                    getRawToken();
                } else {
                    if (match("descending")) {
                        getRawToken();
                        descending = true;
                    }
                }
                if (match("empty")) {
                    getRawToken();
                    if (match("greatest")) {
                        getRawToken();
                        emptyOrder = 'G';
                    } else {
                        if (match("least")) {
                            getRawToken();
                            emptyOrder = 'L';
                        } else {
                            error("'empty' sequence order must be 'greatest' or 'least'");
                        }
                    }
                }
                specs.push(new QuoteExp((descending ? "D" : "A") + emptyOrder));
                NamedCollator collation = this.defaultCollator;
                if (match("collation")) {
                    Object uri = parseURILiteral();
                    if (uri instanceof String) {
                        try {
                            collation = NamedCollator.make(resolveAgainstBaseUri((String) uri));
                        } catch (Exception e) {
                            error('e', "unknown collation '" + uri + "'", "XQST0076");
                        }
                    }
                    getRawToken();
                }
                specs.push(new QuoteExp(collation));
                i2 = this.curToken;
                if (r0 != 44) {
                    break;
                }
                getRawToken();
            }
            if (match("return")) {
                getRawToken();
                lexp = new LambdaExp(this.flworDeclsCount - this.flworDeclsFirst);
                i = this.flworDeclsFirst;
                while (true) {
                    i2 = this.flworDeclsCount;
                    if (i >= r0) {
                        break;
                    }
                    lexp.addDeclaration(this.flworDecls[i].getSymbol());
                    i += AXIS_ANCESTOR_OR_SELF;
                }
                popNesting(saveNesting);
                this.comp.push(lexp);
                lexp.body = parseExprSingle();
                this.comp.pop(lexp);
                int nspecs = specs.size();
                Expression[] args = new Expression[(nspecs + AXIS_ATTRIBUTE)];
                args[AXIS_ANCESTOR] = exp;
                args[AXIS_ANCESTOR_OR_SELF] = lexp;
                for (i = AXIS_ANCESTOR; i < nspecs; i += AXIS_ANCESTOR_OR_SELF) {
                    args[i + AXIS_ATTRIBUTE] = (Expression) specs.elementAt(i);
                }
                return new ApplyExp(makeFunctionExp("gnu.xquery.util.OrderedMap", "orderedMap"), args);
            }
            return syntaxError("expected 'return' clause");
        }
        this.flworDeclsCount = this.flworDeclsFirst;
        this.flworDeclsFirst = flworDeclsSave;
        return exp;
    }

    public Expression parseFLWRInner(boolean isFor) throws IOException, SyntaxException {
        char saveNesting = pushNesting(isFor ? 'f' : 'l');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable - saw " + tokenString());
        }
        int i;
        Expression sc;
        Expression body;
        Expression[] args;
        if (this.flworDecls == null) {
            this.flworDecls = new Declaration[AXIS_NAMESPACE];
        } else {
            if (this.flworDeclsCount >= this.flworDecls.length) {
                Object tmp = new Declaration[(this.flworDeclsCount * AXIS_ATTRIBUTE)];
                System.arraycopy(this.flworDecls, AXIS_ANCESTOR, tmp, AXIS_ANCESTOR, this.flworDeclsCount);
                this.flworDecls = tmp;
            }
        }
        Declaration[] declarationArr = this.flworDecls;
        int i2 = this.flworDeclsCount;
        this.flworDeclsCount = i2 + AXIS_ANCESTOR_OR_SELF;
        declarationArr[i2] = decl;
        getRawToken();
        Expression type = parseOptionalTypeDeclaration();
        Expression[] inits = new Expression[AXIS_ANCESTOR_OR_SELF];
        Declaration posDecl = null;
        if (isFor) {
            boolean sawAt = match("at");
            Expression lambdaExp = new LambdaExp(sawAt ? AXIS_ATTRIBUTE : AXIS_ANCESTOR_OR_SELF);
            if (sawAt) {
                getRawToken();
                i = this.curToken;
                if (r0 == 36) {
                    posDecl = parseVariableDeclaration();
                    getRawToken();
                }
                if (posDecl == null) {
                    syntaxError("missing Variable after 'at'");
                }
            }
            sc = lambdaExp;
            if (match("in")) {
                getRawToken();
            } else {
                i = this.curToken;
                if (r0 == DECLARE_COPY_NAMESPACES_TOKEN) {
                    getRawToken();
                }
                syntaxError("missing 'in' in 'for' clause");
            }
        } else {
            i = this.curToken;
            if (r0 == DECLARE_COPY_NAMESPACES_TOKEN) {
                getRawToken();
            } else {
                if (match("in")) {
                    getRawToken();
                }
                syntaxError("missing ':=' in 'let' clause");
            }
            sc = new LetExp(inits);
        }
        inits[AXIS_ANCESTOR] = parseExprSingle();
        if (!(type == null || isFor)) {
            inits[AXIS_ANCESTOR] = Compilation.makeCoercion(inits[AXIS_ANCESTOR], type);
        }
        popNesting(saveNesting);
        this.comp.push((ScopeExp) sc);
        sc.addDeclaration(decl);
        if (type != null) {
            decl.setTypeExp(type);
        }
        if (isFor) {
            decl.noteValue(null);
            decl.setFlag(262144);
        }
        if (posDecl != null) {
            sc.addDeclaration(posDecl);
            posDecl.setType(LangPrimType.intType);
            posDecl.noteValue(null);
            posDecl.setFlag(262144);
        }
        i = this.curToken;
        if (r0 == 44) {
            getRawToken();
            i = this.curToken;
            if (r0 != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body = parseFLWRInner(isFor);
        } else {
            if (match("for")) {
                getRawToken();
                i = this.curToken;
                if (r0 != 36) {
                    return syntaxError("missing $NAME after 'for'");
                }
                body = parseFLWRInner(true);
            } else {
                if (match("let")) {
                    getRawToken();
                    i = this.curToken;
                    if (r0 != 36) {
                        return syntaxError("missing $NAME after 'let'");
                    }
                    body = parseFLWRInner(false);
                } else {
                    Expression cond;
                    Expression body2;
                    char save = pushNesting('w');
                    i = this.curToken;
                    if (r0 == OP_WHERE) {
                        getRawToken();
                        cond = parseExprSingle();
                    } else {
                        if (match("where")) {
                            cond = parseExprSingle();
                        } else {
                            cond = null;
                        }
                    }
                    popNesting(save);
                    if (match("stable")) {
                        getRawToken();
                    }
                    boolean sawReturn = match("return");
                    boolean sawOrder = match("order");
                    if (!(sawReturn || sawOrder)) {
                        if (!match("let")) {
                            if (!match("for")) {
                                return syntaxError("missing 'return' clause");
                            }
                        }
                    }
                    if (!sawOrder) {
                        peekNonSpace("unexpected eof-of-file after 'return'");
                    }
                    int bodyLine = getLineNumber() + AXIS_ANCESTOR_OR_SELF;
                    int bodyColumn = getColumnNumber() + AXIS_ANCESTOR_OR_SELF;
                    if (sawReturn) {
                        getRawToken();
                    }
                    if (sawOrder) {
                        int ndecls = this.flworDeclsCount - this.flworDeclsFirst;
                        args = new Expression[ndecls];
                        for (int i3 = AXIS_ANCESTOR; i3 < ndecls; i3 += AXIS_ANCESTOR_OR_SELF) {
                            args[i3] = new ReferenceExp(this.flworDecls[this.flworDeclsFirst + i3]);
                        }
                        body2 = new ApplyExp(new PrimProcedure("gnu.xquery.util.OrderedMap", "makeTuple$V", (int) AXIS_ANCESTOR_OR_SELF), args);
                    } else {
                        body2 = parseExprSingle();
                    }
                    if (cond != null) {
                        body = new IfExp(booleanValue(cond), body2, QuoteExp.voidExp);
                    } else {
                        body = body2;
                    }
                    maybeSetLine(body, bodyLine, bodyColumn);
                }
            }
        }
        this.comp.pop(sc);
        if (isFor) {
            String str;
            LambdaExp lexp = (LambdaExp) sc;
            lexp.body = body;
            args = new Expression[AXIS_ATTRIBUTE];
            args[AXIS_ANCESTOR] = sc;
            args[AXIS_ANCESTOR_OR_SELF] = inits[AXIS_ANCESTOR];
            String str2 = "gnu.kawa.functions.ValuesMap";
            i = lexp.min_args;
            if (r0 == AXIS_ANCESTOR_OR_SELF) {
                str = "valuesMap";
            } else {
                str = "valuesMapWithPos";
            }
            return new ApplyExp(makeFunctionExp(str2, str), args);
        }
        ((LetExp) sc).setBody(body);
        return sc;
    }

    public Expression parseQuantifiedExpr(boolean isEvery) throws IOException, SyntaxException {
        char saveNesting = pushNesting(isEvery ? 'e' : 's');
        this.curToken = 36;
        Declaration decl = parseVariableDeclaration();
        if (decl == null) {
            return syntaxError("missing Variable token:" + this.curToken);
        }
        Expression body;
        String str;
        getRawToken();
        ScopeExp lexp = new LambdaExp((int) AXIS_ANCESTOR_OR_SELF);
        lexp.addDeclaration(decl);
        decl.noteValue(null);
        decl.setFlag(262144);
        decl.setTypeExp(parseOptionalTypeDeclaration());
        if (match("in")) {
            getRawToken();
        } else {
            if (this.curToken == DECLARE_COPY_NAMESPACES_TOKEN) {
                getRawToken();
            }
            syntaxError("missing 'in' in QuantifiedExpr");
        }
        Expression[] inits = new Expression[AXIS_ANCESTOR_OR_SELF];
        inits[AXIS_ANCESTOR] = parseExprSingle();
        popNesting(saveNesting);
        this.comp.push(lexp);
        if (this.curToken == 44) {
            getRawToken();
            if (this.curToken != 36) {
                return syntaxError("missing $NAME after ','");
            }
            body = parseQuantifiedExpr(isEvery);
        } else {
            boolean sawSatisfies = match("satisfies");
            if (!sawSatisfies && !match("every") && !match("some")) {
                return syntaxError("missing 'satisfies' clause");
            }
            peekNonSpace("unexpected eof-of-file after 'satisfies'");
            int bodyLine = getLineNumber() + AXIS_ANCESTOR_OR_SELF;
            int bodyColumn = getColumnNumber() + AXIS_ANCESTOR_OR_SELF;
            if (sawSatisfies) {
                getRawToken();
            }
            body = parseExprSingle();
            maybeSetLine(body, bodyLine, bodyColumn);
        }
        this.comp.pop(lexp);
        lexp.body = body;
        Expression[] args = new Expression[AXIS_ATTRIBUTE];
        args[AXIS_ANCESTOR] = lexp;
        args[AXIS_ANCESTOR_OR_SELF] = inits[AXIS_ANCESTOR];
        String str2 = "gnu.xquery.util.ValuesEvery";
        if (isEvery) {
            str = "every";
        } else {
            str = "some";
        }
        return new ApplyExp(makeFunctionExp(str2, str), args);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression parseFunctionDefinition(int r14, int r15) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r13 = this;
        r9 = r13.curToken;
        r10 = 81;
        if (r9 == r10) goto L_0x0013;
    L_0x0006:
        r9 = r13.curToken;
        r10 = 65;
        if (r9 == r10) goto L_0x0013;
    L_0x000c:
        r9 = "missing function name";
        r6 = r13.syntaxError(r9);
    L_0x0012:
        return r6;
    L_0x0013:
        r3 = new java.lang.String;
        r9 = r13.tokenBuffer;
        r10 = 0;
        r11 = r13.tokenBufferLength;
        r3.<init>(r9, r10, r11);
        r9 = r13.getMessages();
        r10 = r13.port;
        r10 = r10.getName();
        r11 = r13.curLine;
        r12 = r13.curColumn;
        r9.setLine(r10, r11, r12);
        r9 = 1;
        r7 = r13.namespaceResolve(r3, r9);
        r8 = r7.getNamespaceURI();
        r9 = "http://www.w3.org/XML/1998/namespace";
        if (r8 == r9) goto L_0x0047;
    L_0x003b:
        r9 = "http://www.w3.org/2001/XMLSchema";
        if (r8 == r9) goto L_0x0047;
    L_0x003f:
        r9 = "http://www.w3.org/2001/XMLSchema-instance";
        if (r8 == r9) goto L_0x0047;
    L_0x0043:
        r9 = "http://www.w3.org/2005/xpath-functions";
        if (r8 != r9) goto L_0x008a;
    L_0x0047:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "cannot declare function in standard namespace '";
        r10 = r10.append(r11);
        r10 = r10.append(r8);
        r11 = 39;
        r10 = r10.append(r11);
        r10 = r10.toString();
        r11 = "XQST0045";
        r13.error(r9, r10, r11);
    L_0x0067:
        r13.getRawToken();
        r9 = r13.curToken;
        r10 = 40;
        if (r9 == r10) goto L_0x00c5;
    L_0x0070:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "missing parameter list:";
        r9 = r9.append(r10);
        r10 = r13.curToken;
        r9 = r9.append(r10);
        r9 = r9.toString();
        r6 = r13.syntaxError(r9);
        goto L_0x0012;
    L_0x008a:
        r9 = "";
        if (r8 != r9) goto L_0x00a3;
    L_0x008e:
        r9 = r13.comp;
        r9 = r9.isPedantic();
        if (r9 == 0) goto L_0x00a0;
    L_0x0096:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0098:
        r10 = "cannot declare function in empty namespace";
        r11 = "XQST0060";
        r13.error(r9, r10, r11);
        goto L_0x0067;
    L_0x00a0:
        r9 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x0098;
    L_0x00a3:
        r9 = r13.libraryModuleNamespace;
        if (r9 == 0) goto L_0x0067;
    L_0x00a7:
        r9 = r13.libraryModuleNamespace;
        if (r8 == r9) goto L_0x0067;
    L_0x00ab:
        r9 = "http://www.w3.org/2005/xquery-local-functions";
        r9 = r9.equals(r8);
        if (r9 == 0) goto L_0x00bb;
    L_0x00b3:
        r9 = r13.comp;
        r9 = r9.isPedantic();
        if (r9 == 0) goto L_0x0067;
    L_0x00bb:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r10 = "function not in namespace of library module";
        r11 = "XQST0048";
        r13.error(r9, r10, r11);
        goto L_0x0067;
    L_0x00c5:
        r13.getRawToken();
        r2 = new gnu.expr.LambdaExp;
        r2.<init>();
        r13.maybeSetLine(r2, r14, r15);
        r2.setName(r3);
        r9 = r13.comp;
        r9 = r9.currentScope();
        r0 = r9.addDeclaration(r7);
        r9 = r13.comp;
        r9 = r9.isStatic();
        if (r9 == 0) goto L_0x00ea;
    L_0x00e5:
        r10 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r0.setFlag(r10);
    L_0x00ea:
        r9 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r2.setFlag(r9);
        r9 = 1;
        r0.setCanRead(r9);
        r9 = 1;
        r0.setProcedureDecl(r9);
        r13.maybeSetLine(r0, r14, r15);
        r9 = r13.comp;
        r9.push(r2);
        r9 = r13.curToken;
        r10 = 41;
        if (r9 == r10) goto L_0x0116;
    L_0x0105:
        r4 = r13.parseVariableDeclaration();
        if (r4 != 0) goto L_0x013d;
    L_0x010b:
        r9 = "missing parameter name";
        r13.error(r9);
    L_0x0110:
        r9 = r13.curToken;
        r10 = 41;
        if (r9 != r10) goto L_0x0157;
    L_0x0116:
        r13.getRawToken();
        r5 = r13.parseOptionalTypeDeclaration();
        r9 = r13.parseEnclosedExpr();
        r2.body = r9;
        r9 = r13.comp;
        r9.pop(r2);
        if (r5 == 0) goto L_0x012f;
    L_0x012a:
        r9 = r13.interpreter;
        r2.setCoercedReturnValue(r5, r9);
    L_0x012f:
        r6 = new gnu.expr.SetExp;
        r6.<init>(r0, r2);
        r9 = 1;
        r6.setDefining(r9);
        r0.noteValue(r2);
        goto L_0x0012;
    L_0x013d:
        r2.addDeclaration(r4);
        r13.getRawToken();
        r9 = r2.min_args;
        r9 = r9 + 1;
        r2.min_args = r9;
        r9 = r2.max_args;
        r9 = r9 + 1;
        r2.max_args = r9;
        r9 = r13.parseOptionalTypeDeclaration();
        r4.setTypeExp(r9);
        goto L_0x0110;
    L_0x0157:
        r9 = r13.curToken;
        r10 = 44;
        if (r9 == r10) goto L_0x0186;
    L_0x015d:
        r9 = "missing ',' in parameter list";
        r1 = r13.syntaxError(r9);
    L_0x0163:
        r13.getRawToken();
        r9 = r13.curToken;
        if (r9 < 0) goto L_0x0176;
    L_0x016a:
        r9 = r13.curToken;
        r10 = 59;
        if (r9 == r10) goto L_0x0176;
    L_0x0170:
        r9 = r13.curToken;
        r10 = 59;
        if (r9 != r10) goto L_0x0179;
    L_0x0176:
        r6 = r1;
        goto L_0x0012;
    L_0x0179:
        r9 = r13.curToken;
        r10 = 41;
        if (r9 == r10) goto L_0x0116;
    L_0x017f:
        r9 = r13.curToken;
        r10 = 44;
        if (r9 != r10) goto L_0x0163;
    L_0x0185:
        goto L_0x0105;
    L_0x0186:
        r13.getRawToken();
        goto L_0x0105;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parseFunctionDefinition(int, int):gnu.expr.Expression");
    }

    public Object readObject() throws IOException, SyntaxException {
        return parse(null);
    }

    protected Symbol namespaceResolve(String name, boolean function) {
        int colon = name.indexOf(58);
        String prefix = colon >= 0 ? name.substring(AXIS_ANCESTOR, colon).intern() : function ? XQuery.DEFAULT_FUNCTION_PREFIX : XQuery.DEFAULT_ELEMENT_PREFIX;
        String uri = QNameUtils.lookupPrefix(prefix, this.constructorNamespaces, this.prologNamespaces);
        if (uri == null) {
            if (colon < 0) {
                uri = ElementType.MATCH_ANY_LOCALNAME;
            } else if (!this.comp.isPedantic()) {
                try {
                    Class cl = Class.forName(prefix);
                    uri = "class:" + prefix;
                } catch (Exception e) {
                    uri = null;
                }
            }
            if (uri == null) {
                getMessages().error('e', "unknown namespace prefix '" + prefix + "'", "XPST0081");
                uri = "(unknown namespace)";
            }
        }
        return Symbol.make(uri, colon < 0 ? name : name.substring(colon + AXIS_ANCESTOR_OR_SELF), prefix);
    }

    void parseSeparator() throws IOException, SyntaxException {
        int startLine = this.port.getLineNumber() + AXIS_ANCESTOR_OR_SELF;
        int startColumn = this.port.getColumnNumber() + AXIS_ANCESTOR_OR_SELF;
        int next = skipSpace(this.nesting != 0);
        if (next != 59) {
            if (warnOldVersion && next != EOL_TOKEN) {
                this.curLine = startLine;
                this.curColumn = startColumn;
                error('w', "missing ';' after declaration");
            }
            if (next >= 0) {
                unread(next);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression parse(gnu.expr.Compilation r52) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r51 = this;
        r0 = r52;
        r1 = r51;
        r1.comp = r0;
        r18 = r51.skipSpace();
        if (r18 >= 0) goto L_0x000f;
    L_0x000c:
        r27 = 0;
    L_0x000e:
        return r27;
    L_0x000f:
        r0 = r51;
        r6 = r0.parseCount;
        r6 = r6 + 1;
        r0 = r51;
        r0.parseCount = r6;
        r0 = r51;
        r1 = r18;
        r0.unread(r1);
        r6 = r51.getLineNumber();
        r44 = r6 + 1;
        r6 = r51.getColumnNumber();
        r43 = r6 + 1;
        r6 = 35;
        r0 = r18;
        if (r0 != r6) goto L_0x006d;
    L_0x0032:
        r6 = 1;
        r0 = r44;
        if (r0 != r6) goto L_0x006d;
    L_0x0037:
        r6 = 1;
        r0 = r43;
        if (r0 != r6) goto L_0x006d;
    L_0x003c:
        r51.read();
        r18 = r51.read();
        r6 = 33;
        r0 = r18;
        if (r0 != r6) goto L_0x0053;
    L_0x0049:
        r18 = r51.read();
        r6 = 47;
        r0 = r18;
        if (r0 == r6) goto L_0x005a;
    L_0x0053:
        r6 = "'#' is only allowed in initial '#!/PROGRAM'";
        r0 = r51;
        r0.error(r6);
    L_0x005a:
        r6 = 13;
        r0 = r18;
        if (r0 == r6) goto L_0x006d;
    L_0x0060:
        r6 = 10;
        r0 = r18;
        if (r0 == r6) goto L_0x006d;
    L_0x0066:
        if (r18 < 0) goto L_0x006d;
    L_0x0068:
        r18 = r51.read();
        goto L_0x005a;
    L_0x006d:
        r6 = r51.getRawToken();
        r9 = -1;
        if (r6 != r9) goto L_0x0077;
    L_0x0074:
        r27 = 0;
        goto L_0x000e;
    L_0x0077:
        r51.peekOperand();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 65;
        if (r6 != r9) goto L_0x00a3;
    L_0x0082:
        r9 = "namespace";
        r0 = r51;
        r6 = r0.curValue;
        r6 = (java.lang.String) r6;
        r6 = r9.equals(r6);
        if (r6 == 0) goto L_0x00a3;
    L_0x0090:
        r6 = warnOldVersion;
        if (r6 == 0) goto L_0x009d;
    L_0x0094:
        r6 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r9 = "use 'declare namespace' instead of 'namespace'";
        r0 = r51;
        r0.error(r6, r9);
    L_0x009d:
        r6 = 78;
        r0 = r51;
        r0.curToken = r6;
    L_0x00a3:
        r0 = r51;
        r6 = r0.curToken;
        switch(r6) {
            case 66: goto L_0x0b67;
            case 69: goto L_0x06c1;
            case 71: goto L_0x0653;
            case 72: goto L_0x0910;
            case 73: goto L_0x043d;
            case 75: goto L_0x0821;
            case 76: goto L_0x086e;
            case 77: goto L_0x02b9;
            case 78: goto L_0x02b9;
            case 79: goto L_0x06c1;
            case 80: goto L_0x011c;
            case 83: goto L_0x07ab;
            case 84: goto L_0x0434;
            case 85: goto L_0x0a04;
            case 86: goto L_0x0160;
            case 87: goto L_0x00df;
            case 89: goto L_0x0a51;
            case 111: goto L_0x0974;
            default: goto L_0x00aa;
        };
    L_0x00aa:
        r6 = -1;
        r9 = 1;
        r0 = r51;
        r27 = r0.parseExprSequence(r6, r9);
        r0 = r51;
        r6 = r0.curToken;
        r9 = 10;
        if (r6 != r9) goto L_0x00c1;
    L_0x00ba:
        r6 = 10;
        r0 = r51;
        r0.unread(r6);
    L_0x00c1:
        r0 = r51;
        r1 = r27;
        r2 = r44;
        r3 = r43;
        r0.maybeSetLine(r1, r2, r3);
        r0 = r51;
        r6 = r0.libraryModuleNamespace;
        if (r6 == 0) goto L_0x000e;
    L_0x00d2:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "query body in a library module";
        r10 = "XPST0003";
        r0 = r51;
        r0.error(r6, r9, r10);
        goto L_0x000e;
    L_0x00df:
        r6 = r51.getLineNumber();
        r23 = r6 + 1;
        r6 = r51.getColumnNumber();
        r22 = r6 + 1;
        r6 = "unexpected end-of-file after 'define QName'";
        r0 = r51;
        r36 = r0.peekNonSpace(r6);
        r6 = 40;
        r0 = r36;
        if (r0 != r6) goto L_0x0112;
    L_0x00f9:
        r6 = "'missing 'function' after 'define'";
        r0 = r51;
        r0.syntaxError(r6);
        r6 = 65;
        r0 = r51;
        r0.curToken = r6;
        r0 = r51;
        r1 = r23;
        r2 = r22;
        r27 = r0.parseFunctionDefinition(r1, r2);
        goto L_0x000e;
    L_0x0112:
        r6 = "missing keyword after 'define'";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x011c:
        r6 = r51.getLineNumber();
        r23 = r6 + 1;
        r6 = r51.getColumnNumber();
        r22 = r6 + 1;
        r51.getRawToken();
        r6 = "unexpected end-of-file after 'define function'";
        r0 = r51;
        r0.peekNonSpace(r6);
        r6 = 100;
        r0 = r51;
        r40 = r0.pushNesting(r6);
        r0 = r51;
        r1 = r23;
        r2 = r22;
        r27 = r0.parseFunctionDefinition(r1, r2);
        r0 = r51;
        r1 = r40;
        r0.popNesting(r1);
        r51.parseSeparator();
        r0 = r51;
        r1 = r27;
        r2 = r44;
        r3 = r43;
        r0.maybeSetLine(r1, r2, r3);
        r6 = 1;
        r0 = r51;
        r0.seenDeclaration = r6;
        goto L_0x000e;
    L_0x0160:
        r51.getRawToken();
        r21 = r51.parseVariableDeclaration();
        if (r21 != 0) goto L_0x0173;
    L_0x0169:
        r6 = "missing Variable";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0173:
        r35 = r21.getSymbol();
        r0 = r35;
        r6 = r0 instanceof java.lang.String;
        if (r6 == 0) goto L_0x01a4;
    L_0x017d:
        r6 = r51.getMessages();
        r0 = r51;
        r9 = r0.port;
        r9 = r9.getName();
        r0 = r51;
        r10 = r0.curLine;
        r0 = r51;
        r11 = r0.curColumn;
        r6.setLine(r9, r10, r11);
        r35 = (java.lang.String) r35;
        r6 = 0;
        r0 = r51;
        r1 = r35;
        r6 = r0.namespaceResolve(r1, r6);
        r0 = r21;
        r0.setSymbol(r6);
    L_0x01a4:
        r0 = r51;
        r6 = r0.libraryModuleNamespace;
        if (r6 == 0) goto L_0x01d7;
    L_0x01aa:
        r6 = r21.getSymbol();
        r6 = (gnu.mapping.Symbol) r6;
        r48 = r6.getNamespaceURI();
        r0 = r51;
        r6 = r0.libraryModuleNamespace;
        r0 = r48;
        if (r0 == r6) goto L_0x01d7;
    L_0x01bc:
        r6 = "http://www.w3.org/2005/xquery-local-functions";
        r0 = r48;
        r6 = r6.equals(r0);
        if (r6 == 0) goto L_0x01cc;
    L_0x01c6:
        r6 = r52.isPedantic();
        if (r6 == 0) goto L_0x01d7;
    L_0x01cc:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "variable not in namespace of library module";
        r10 = "XQST0048";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x01d7:
        r6 = r52.currentScope();
        r0 = r21;
        r6.addDeclaration(r0);
        r51.getRawToken();
        r47 = r51.parseOptionalTypeDeclaration();
        r6 = 1;
        r0 = r21;
        r0.setCanRead(r6);
        r10 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
        r0 = r21;
        r0.setFlag(r10);
        r30 = 0;
        r42 = 0;
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 == r9) goto L_0x0208;
    L_0x0200:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 76;
        if (r6 != r9) goto L_0x021c;
    L_0x0208:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 != r9) goto L_0x0217;
    L_0x0210:
        r6 = "declare variable contains '=' instead of ':='";
        r0 = r51;
        r0.error(r6);
    L_0x0217:
        r51.getRawToken();
        r42 = 1;
    L_0x021c:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        if (r6 != r9) goto L_0x025d;
    L_0x0224:
        r6 = "obsolete '{' in variable declaration";
        r0 = r51;
        r0.warnOldVersion(r6);
        r30 = r51.parseEnclosedExpr();
        r51.parseSeparator();
    L_0x0232:
        if (r47 == 0) goto L_0x023c;
    L_0x0234:
        r0 = r30;
        r1 = r47;
        r30 = gnu.expr.Compilation.makeCoercion(r0, r1);
    L_0x023c:
        r0 = r21;
        r1 = r30;
        r0.noteValue(r1);
        r0 = r21;
        r1 = r30;
        r27 = gnu.expr.SetExp.makeDefinition(r0, r1);
        r0 = r51;
        r1 = r27;
        r2 = r44;
        r3 = r43;
        r0.maybeSetLine(r1, r2, r3);
        r6 = 1;
        r0 = r51;
        r0.seenDeclaration = r6;
        goto L_0x000e;
    L_0x025d:
        r6 = "external";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x02a1;
    L_0x0267:
        r6 = 2;
        r15 = new gnu.expr.Expression[r6];
        r6 = 0;
        r9 = new gnu.expr.QuoteExp;
        r10 = r21.getSymbol();
        r9.<init>(r10);
        r10 = 0;
        r9 = castQName(r9, r10);
        r15[r6] = r9;
        r9 = 1;
        if (r47 != 0) goto L_0x029e;
    L_0x027e:
        r6 = gnu.expr.QuoteExp.nullExp;
    L_0x0280:
        r15[r9] = r6;
        r30 = new gnu.expr.ApplyExp;
        r6 = getExternalFunction;
        r0 = r30;
        r0.<init>(r6, r15);
        r0 = r51;
        r6 = r0.curLine;
        r0 = r51;
        r9 = r0.curColumn;
        r0 = r51;
        r1 = r30;
        r0.maybeSetLine(r1, r6, r9);
        r51.getRawToken();
        goto L_0x0232;
    L_0x029e:
        r6 = r47;
        goto L_0x0280;
    L_0x02a1:
        r30 = r51.parseExpr();
        r25 = 0;
        if (r42 == 0) goto L_0x02ab;
    L_0x02a9:
        if (r30 != 0) goto L_0x02b3;
    L_0x02ab:
        r6 = "expected ':= init' or 'external'";
        r0 = r51;
        r25 = r0.syntaxError(r6);
    L_0x02b3:
        if (r30 != 0) goto L_0x0232;
    L_0x02b5:
        r30 = r25;
        goto L_0x0232;
    L_0x02b9:
        r0 = r51;
        r0 = r0.curToken;
        r20 = r0;
        r6 = 77;
        r0 = r20;
        if (r0 != r6) goto L_0x0304;
    L_0x02c5:
        r0 = r51;
        r6 = r0.libraryModuleNamespace;
        if (r6 == 0) goto L_0x0304;
    L_0x02cb:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "duplicate module declaration";
        r0 = r51;
        r0.error(r6, r9);
    L_0x02d4:
        r0 = r51;
        r6 = r0.nesting;
        if (r6 == 0) goto L_0x031a;
    L_0x02da:
        r6 = 1;
    L_0x02db:
        r0 = r51;
        r36 = r0.skipSpace(r6);
        if (r36 < 0) goto L_0x0434;
    L_0x02e3:
        r51.unread();
        r0 = r36;
        r6 = (char) r0;
        r6 = gnu.xml.XName.isNameStart(r6);
        if (r6 == 0) goto L_0x0434;
    L_0x02ef:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 65;
        if (r6 == r9) goto L_0x031c;
    L_0x02fa:
        r6 = "missing namespace prefix";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0304:
        r0 = r51;
        r6 = r0.seenDeclaration;
        if (r6 == 0) goto L_0x02d4;
    L_0x030a:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x02d4;
    L_0x0310:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "namespace declared after function/variable/option";
        r0 = r51;
        r0.error(r6, r9);
        goto L_0x02d4;
    L_0x031a:
        r6 = 0;
        goto L_0x02db;
    L_0x031c:
        r39 = new java.lang.String;
        r0 = r51;
        r6 = r0.tokenBuffer;
        r9 = 0;
        r0 = r51;
        r10 = r0.tokenBufferLength;
        r0 = r39;
        r0.<init>(r6, r9, r10);
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 == r9) goto L_0x0341;
    L_0x0337:
        r6 = "missing '=' in namespace declaration";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0341:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x0356;
    L_0x034c:
        r6 = "missing uri in namespace declaration";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0356:
        r6 = new java.lang.String;
        r0 = r51;
        r9 = r0.tokenBuffer;
        r10 = 0;
        r0 = r51;
        r11 = r0.tokenBufferLength;
        r6.<init>(r9, r10, r11);
        r48 = r6.intern();
        r39 = r39.intern();
        r0 = r51;
        r0 = r0.prologNamespaces;
        r37 = r0;
    L_0x0372:
        r6 = builtinNamespaces;
        r0 = r37;
        if (r0 == r6) goto L_0x03a4;
    L_0x0378:
        r6 = r37.getPrefix();
        r0 = r39;
        if (r6 != r0) goto L_0x0424;
    L_0x0380:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "duplicate declarations for the same namespace prefix '";
        r9 = r9.append(r10);
        r0 = r39;
        r9 = r9.append(r0);
        r10 = "'";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r10 = "XQST0033";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x03a4:
        r0 = r51;
        r1 = r39;
        r2 = r48;
        r0.pushNamespace(r1, r2);
        r6 = 0;
        r0 = r51;
        r1 = r39;
        r2 = r48;
        r0.checkAllowedNamespaceDeclaration(r1, r2, r6);
        r51.parseSeparator();
        r6 = 77;
        r0 = r20;
        if (r0 != r6) goto L_0x0430;
    L_0x03c0:
        r8 = r52.getModule();
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r9 = gnu.expr.Compilation.mangleURI(r48);
        r6 = r6.append(r9);
        r9 = 46;
        r6 = r6.append(r9);
        r9 = r8.getFileName();
        r9 = gnu.xquery.lang.XQuery.makeClassName(r9);
        r6 = r6.append(r9);
        r4 = r6.toString();
        r8.setName(r4);
        r6 = new gnu.bytecode.ClassType;
        r6.<init>(r4);
        r0 = r52;
        r0.mainClass = r6;
        r0 = r52;
        r6 = r0.mainClass;
        r8.setType(r6);
        r32 = gnu.expr.ModuleManager.getInstance();
        r0 = r32;
        r1 = r52;
        r5 = r0.find(r1);
        r0 = r48;
        r5.setNamespaceUri(r0);
        r0 = r52;
        r6 = r0.mainClass;
        r8.setType(r6);
        r6 = r48.length();
        if (r6 != 0) goto L_0x042a;
    L_0x0418:
        r6 = "zero-length module namespace";
        r9 = "XQST0088";
        r0 = r51;
        r27 = r0.syntaxError(r6, r9);
        goto L_0x000e;
    L_0x0424:
        r37 = r37.getNext();
        goto L_0x0372;
    L_0x042a:
        r0 = r48;
        r1 = r51;
        r1.libraryModuleNamespace = r0;
    L_0x0430:
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0434:
        r6 = "'import schema' not implemented";
        r9 = "XQST0009";
        r0 = r51;
        r0.fatal(r6, r9);
    L_0x043d:
        r51.getRawToken();
        r39 = 0;
        r6 = "namespace";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0489;
    L_0x044c:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 65;
        if (r6 == r9) goto L_0x0461;
    L_0x0457:
        r6 = "missing namespace prefix";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0461:
        r39 = new java.lang.String;
        r0 = r51;
        r6 = r0.tokenBuffer;
        r9 = 0;
        r0 = r51;
        r10 = r0.tokenBufferLength;
        r0 = r39;
        r0.<init>(r6, r9, r10);
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 == r9) goto L_0x0486;
    L_0x047c:
        r6 = "missing '=' in namespace declaration";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0486:
        r51.getRawToken();
    L_0x0489:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x049b;
    L_0x0491:
        r6 = "missing uri in namespace declaration";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x049b:
        r0 = r51;
        r6 = r0.tokenBufferLength;
        if (r6 != 0) goto L_0x04ad;
    L_0x04a1:
        r6 = "zero-length target namespace";
        r9 = "XQST0088";
        r0 = r51;
        r27 = r0.syntaxError(r6, r9);
        goto L_0x000e;
    L_0x04ad:
        r6 = new java.lang.String;
        r0 = r51;
        r9 = r0.tokenBuffer;
        r10 = 0;
        r0 = r51;
        r11 = r0.tokenBufferLength;
        r6.<init>(r9, r10, r11);
        r48 = r6.intern();
        if (r39 == 0) goto L_0x04d6;
    L_0x04c1:
        r6 = 0;
        r0 = r51;
        r1 = r39;
        r2 = r48;
        r0.checkAllowedNamespaceDeclaration(r1, r2, r6);
        r6 = r39.intern();
        r0 = r51;
        r1 = r48;
        r0.pushNamespace(r6, r1);
    L_0x04d6:
        r51.getRawToken();
        r6 = gnu.expr.ModuleManager.getInstance();
        r0 = r52;
        r6.find(r0);
        r8 = r52.getModule();
        r7 = new java.util.Vector;
        r7.<init>();
        r38 = gnu.expr.Compilation.mangleURI(r48);
        r0 = r51;
        r6 = r0.port;
        r6 = r6.getName();
        r0 = r52;
        r1 = r44;
        r2 = r43;
        r0.setLine(r6, r1, r2);
        r6 = "at";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x05c5;
    L_0x050a:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x051f;
    L_0x0515:
        r6 = "missing module location";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x051f:
        r16 = new java.lang.String;
        r0 = r51;
        r6 = r0.tokenBuffer;
        r9 = 0;
        r0 = r51;
        r10 = r0.tokenBufferLength;
        r0 = r16;
        r0.<init>(r6, r9, r10);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r9 = gnu.expr.Compilation.mangleURI(r48);
        r6 = r6.append(r9);
        r9 = 46;
        r6 = r6.append(r9);
        r9 = gnu.xquery.lang.XQuery.makeClassName(r16);
        r6 = r6.append(r9);
        r4 = r6.toString();
        r0 = r16;
        r5 = kawa.standard.require.lookupModuleFromSourcePath(r0, r8);
        if (r5 != 0) goto L_0x0572;
    L_0x0556:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "malformed URL: ";
        r9 = r9.append(r10);
        r0 = r16;
        r9 = r9.append(r0);
        r9 = r9.toString();
        r0 = r52;
        r0.error(r6, r9);
    L_0x0572:
        r6 = 0;
        r9 = r52;
        kawa.standard.require.importDefinitions(r4, r5, r6, r7, r8, r9);
        r0 = r51;
        r6 = r0.nesting;
        if (r6 == 0) goto L_0x05c3;
    L_0x057e:
        r6 = 1;
    L_0x057f:
        r0 = r51;
        r36 = r0.skipSpace(r6);
        r6 = 44;
        r0 = r36;
        if (r0 == r6) goto L_0x050a;
    L_0x058b:
        r0 = r51;
        r1 = r36;
        r0.unread(r1);
        r51.parseSeparator();
    L_0x0595:
        r0 = r52;
        r6 = r0.pendingImports;
        if (r6 == 0) goto L_0x05b0;
    L_0x059b:
        r0 = r52;
        r6 = r0.pendingImports;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x05b0;
    L_0x05a5:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "module import forms a cycle";
        r10 = "XQST0073";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x05b0:
        r6 = r7.size();
        r0 = new gnu.expr.Expression[r6];
        r31 = r0;
        r0 = r31;
        r7.toArray(r0);
        r27 = gnu.expr.BeginExp.canonicalize(r31);
        goto L_0x000e;
    L_0x05c3:
        r6 = 0;
        goto L_0x057f;
    L_0x05c5:
        r32 = gnu.expr.ModuleManager.getInstance();
        r34 = 0;
        r0 = r32;
        r1 = r38;
        r0.loadPackageInfo(r1);	 Catch:{ ClassNotFoundException -> 0x0ba1, Throwable -> 0x060a }
    L_0x05d2:
        r29 = 0;
    L_0x05d4:
        r0 = r32;
        r1 = r29;
        r5 = r0.getModule(r1);
        if (r5 != 0) goto L_0x0634;
    L_0x05de:
        if (r34 != 0) goto L_0x05fc;
    L_0x05e0:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "no module found for ";
        r9 = r9.append(r10);
        r0 = r48;
        r9 = r9.append(r0);
        r9 = r9.toString();
        r0 = r51;
        r0.error(r6, r9);
    L_0x05fc:
        r16 = 0;
        r0 = r51;
        r6 = r0.curToken;
        r9 = 59;
        if (r6 == r9) goto L_0x0595;
    L_0x0606:
        r51.parseSeparator();
        goto L_0x0595;
    L_0x060a:
        r26 = move-exception;
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "error loading map for ";
        r9 = r9.append(r10);
        r0 = r48;
        r9 = r9.append(r0);
        r10 = " - ";
        r9 = r9.append(r10);
        r0 = r26;
        r9 = r9.append(r0);
        r9 = r9.toString();
        r0 = r51;
        r0.error(r6, r9);
        goto L_0x05d2;
    L_0x0634:
        r6 = r5.getNamespaceUri();
        r0 = r48;
        r6 = r0.equals(r6);
        if (r6 != 0) goto L_0x0643;
    L_0x0640:
        r29 = r29 + 1;
        goto L_0x05d4;
    L_0x0643:
        r34 = r34 + 1;
        r9 = r5.getClassName();
        r11 = 0;
        r10 = r5;
        r12 = r7;
        r13 = r8;
        r14 = r52;
        kawa.standard.require.importDefinitions(r9, r10, r11, r12, r13, r14);
        goto L_0x0640;
    L_0x0653:
        r0 = r51;
        r6 = r0.defaultCollator;
        if (r6 == 0) goto L_0x066a;
    L_0x0659:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x066a;
    L_0x065f:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "duplicate default collation declaration";
        r10 = "XQST0038";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x066a:
        r49 = r51.parseURILiteral();
        r0 = r49;
        r6 = r0 instanceof gnu.expr.Expression;
        if (r6 == 0) goto L_0x067a;
    L_0x0674:
        r49 = (gnu.expr.Expression) r49;
        r27 = r49;
        goto L_0x000e;
    L_0x067a:
        r19 = r49;
        r19 = (java.lang.String) r19;
        r0 = r51;
        r1 = r19;
        r19 = r0.resolveAgainstBaseUri(r1);	 Catch:{ Exception -> 0x0695 }
        r6 = gnu.xquery.util.NamedCollator.make(r19);	 Catch:{ Exception -> 0x0695 }
        r0 = r51;
        r0.defaultCollator = r6;	 Catch:{ Exception -> 0x0695 }
    L_0x068e:
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0695:
        r26 = move-exception;
        r6 = gnu.xquery.util.NamedCollator.codepointCollation;
        r0 = r51;
        r0.defaultCollator = r6;
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "unknown collation '";
        r9 = r9.append(r10);
        r0 = r19;
        r9 = r9.append(r0);
        r10 = "'";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r10 = "XQST0038";
        r0 = r51;
        r0.error(r6, r9, r10);
        goto L_0x068e;
    L_0x06c1:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 79;
        if (r6 != r9) goto L_0x0724;
    L_0x06c9:
        r28 = 1;
    L_0x06cb:
        if (r28 == 0) goto L_0x0727;
    L_0x06cd:
        r39 = "(functions)";
    L_0x06cf:
        r0 = r51;
        r6 = r0.prologNamespaces;
        r9 = builtinNamespaces;
        r0 = r39;
        r6 = r6.resolve(r0, r9);
        if (r6 == 0) goto L_0x072a;
    L_0x06dd:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "duplicate default namespace declaration";
        r10 = "XQST0066";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x06e8:
        r51.getRawToken();
        r6 = "namespace";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0740;
    L_0x06f5:
        r51.getRawToken();
    L_0x06f8:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 == r9) goto L_0x0708;
    L_0x0700:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 76;
        if (r6 != r9) goto L_0x0712;
    L_0x0708:
        r6 = "extra '=' in default namespace declaration";
        r0 = r51;
        r0.warnOldVersion(r6);
        r51.getRawToken();
    L_0x0712:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x0764;
    L_0x071a:
        r6 = "missing namespace uri";
        r0 = r51;
        r27 = r0.declError(r6);
        goto L_0x000e;
    L_0x0724:
        r28 = 0;
        goto L_0x06cb;
    L_0x0727:
        r39 = gnu.xquery.lang.XQuery.DEFAULT_ELEMENT_PREFIX;
        goto L_0x06cf;
    L_0x072a:
        r0 = r51;
        r6 = r0.seenDeclaration;
        if (r6 == 0) goto L_0x06e8;
    L_0x0730:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x06e8;
    L_0x0736:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "default namespace declared after function/variable/option";
        r0 = r51;
        r0.error(r6, r9);
        goto L_0x06e8;
    L_0x0740:
        r33 = "expected 'namespace' keyword";
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x075c;
    L_0x074a:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 == r9) goto L_0x075c;
    L_0x0752:
        r0 = r51;
        r1 = r33;
        r27 = r0.declError(r1);
        goto L_0x000e;
    L_0x075c:
        r0 = r51;
        r1 = r33;
        r0.warnOldVersion(r1);
        goto L_0x06f8;
    L_0x0764:
        r6 = new java.lang.String;
        r0 = r51;
        r9 = r0.tokenBuffer;
        r10 = 0;
        r0 = r51;
        r11 = r0.tokenBufferLength;
        r6.<init>(r9, r10, r11);
        r48 = r6.intern();
        if (r28 == 0) goto L_0x07a4;
    L_0x0778:
        r6 = 1;
        r6 = new gnu.mapping.Namespace[r6];
        r0 = r51;
        r0.functionNamespacePath = r6;
        r0 = r51;
        r6 = r0.functionNamespacePath;
        r9 = 0;
        r10 = gnu.mapping.Namespace.valueOf(r48);
        r6[r9] = r10;
    L_0x078a:
        r0 = r51;
        r1 = r39;
        r2 = r48;
        r0.pushNamespace(r1, r2);
        r6 = 0;
        r0 = r51;
        r1 = r39;
        r2 = r48;
        r0.checkAllowedNamespaceDeclaration(r1, r2, r6);
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x07a4:
        r0 = r48;
        r1 = r51;
        r1.defaultElementNamespace = r0;
        goto L_0x078a;
    L_0x07ab:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 402; // 0x192 float:5.63E-43 double:1.986E-321;
        if (r6 != r9) goto L_0x07c0;
    L_0x07b6:
        r6 = "obsolate '=' in boundary-space declaration";
        r0 = r51;
        r0.warnOldVersion(r6);
        r51.getRawToken();
    L_0x07c0:
        r0 = r51;
        r6 = r0.boundarySpaceDeclarationSeen;
        if (r6 == 0) goto L_0x07d5;
    L_0x07c6:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x07d5;
    L_0x07cc:
        r6 = "duplicate 'declare boundary-space' seen";
        r9 = "XQST0068";
        r0 = r51;
        r0.syntaxError(r6, r9);
    L_0x07d5:
        r6 = 1;
        r0 = r51;
        r0.boundarySpaceDeclarationSeen = r6;
        r6 = "preserve";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x07f0;
    L_0x07e4:
        r6 = 1;
        r0 = r51;
        r0.boundarySpacePreserve = r6;
    L_0x07e9:
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x07f0:
        r6 = "strip";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0800;
    L_0x07fa:
        r6 = 0;
        r0 = r51;
        r0.boundarySpacePreserve = r6;
        goto L_0x07e9;
    L_0x0800:
        r6 = "skip";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0817;
    L_0x080a:
        r6 = "update: declare boundary-space skip -> strip";
        r0 = r51;
        r0.warnOldVersion(r6);
        r6 = 0;
        r0 = r51;
        r0.boundarySpacePreserve = r6;
        goto L_0x07e9;
    L_0x0817:
        r6 = "boundary-space declaration must be preserve or strip";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0821:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.constructionModeDeclarationSeen;
        if (r6 == 0) goto L_0x0839;
    L_0x082a:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x0839;
    L_0x0830:
        r6 = "duplicate 'declare construction' seen";
        r9 = "XQST0067";
        r0 = r51;
        r0.syntaxError(r6, r9);
    L_0x0839:
        r6 = 1;
        r0 = r51;
        r0.constructionModeDeclarationSeen = r6;
        r6 = "strip";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0854;
    L_0x0848:
        r6 = 1;
        r0 = r51;
        r0.constructionModeStrip = r6;
    L_0x084d:
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0854:
        r6 = "preserve";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0864;
    L_0x085e:
        r6 = 0;
        r0 = r51;
        r0.constructionModeStrip = r6;
        goto L_0x084d;
    L_0x0864:
        r6 = "construction declaration must be strip or preserve";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x086e:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.copyNamespacesDeclarationSeen;
        if (r6 == 0) goto L_0x0886;
    L_0x0877:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x0886;
    L_0x087d:
        r6 = "duplicate 'declare copy-namespaces' seen";
        r9 = "XQST0055";
        r0 = r51;
        r0.syntaxError(r6, r9);
    L_0x0886:
        r6 = 1;
        r0 = r51;
        r0.copyNamespacesDeclarationSeen = r6;
        r6 = "preserve";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x08b4;
    L_0x0895:
        r0 = r51;
        r6 = r0.copyNamespacesMode;
        r6 = r6 | 1;
        r0 = r51;
        r0.copyNamespacesMode = r6;
    L_0x089f:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 44;
        if (r6 == r9) goto L_0x08d3;
    L_0x08aa:
        r6 = "missing ',' in copy-namespaces declaration";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x08b4:
        r6 = "no-preserve";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x08c9;
    L_0x08be:
        r0 = r51;
        r6 = r0.copyNamespacesMode;
        r6 = r6 & -2;
        r0 = r51;
        r0.copyNamespacesMode = r6;
        goto L_0x089f;
    L_0x08c9:
        r6 = "expected 'preserve' or 'no-preserve' after 'declare copy-namespaces'";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x08d3:
        r51.getRawToken();
        r6 = "inherit";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x08f1;
    L_0x08e0:
        r0 = r51;
        r6 = r0.copyNamespacesMode;
        r6 = r6 | 2;
        r0 = r51;
        r0.copyNamespacesMode = r6;
    L_0x08ea:
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x08f1:
        r6 = "no-inherit";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0906;
    L_0x08fb:
        r0 = r51;
        r6 = r0.copyNamespacesMode;
        r6 = r6 & -3;
        r0 = r51;
        r0.copyNamespacesMode = r6;
        goto L_0x08ea;
    L_0x0906:
        r6 = "expected 'inherit' or 'no-inherit' in copy-namespaces declaration";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0910:
        r51.getRawToken();
        r6 = "empty";
        r0 = r51;
        r41 = r0.match(r6);
        r0 = r51;
        r6 = r0.emptyOrderDeclarationSeen;
        if (r6 == 0) goto L_0x0930;
    L_0x0921:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x0930;
    L_0x0927:
        r6 = "duplicate 'declare default empty order' seen";
        r9 = "XQST0069";
        r0 = r51;
        r0.syntaxError(r6, r9);
    L_0x0930:
        r6 = 1;
        r0 = r51;
        r0.emptyOrderDeclarationSeen = r6;
        if (r41 == 0) goto L_0x0951;
    L_0x0937:
        r51.getRawToken();
    L_0x093a:
        r6 = "greatest";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0959;
    L_0x0944:
        r6 = 71;
        r0 = r51;
        r0.defaultEmptyOrder = r6;
    L_0x094a:
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0951:
        r6 = "expected 'empty greatest' or 'empty least'";
        r0 = r51;
        r0.syntaxError(r6);
        goto L_0x093a;
    L_0x0959:
        r6 = "least";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x096a;
    L_0x0963:
        r6 = 76;
        r0 = r51;
        r0.defaultEmptyOrder = r6;
        goto L_0x094a;
    L_0x096a:
        r6 = "expected 'empty greatest' or 'empty least'";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0974:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 81;
        if (r6 == r9) goto L_0x0992;
    L_0x097f:
        r6 = "expected QName after 'declare option'";
        r0 = r51;
        r0.syntaxError(r6);
    L_0x0986:
        r51.parseSeparator();
        r6 = 1;
        r0 = r51;
        r0.seenDeclaration = r6;
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0992:
        r45 = new java.lang.String;
        r0 = r51;
        r6 = r0.tokenBuffer;
        r9 = 0;
        r0 = r51;
        r10 = r0.tokenBufferLength;
        r0 = r45;
        r0.<init>(r6, r9, r10);
        r6 = r51.getMessages();
        r0 = r51;
        r9 = r0.port;
        r9 = r9.getName();
        r0 = r51;
        r10 = r0.curLine;
        r0 = r51;
        r11 = r0.curColumn;
        r6.setLine(r9, r10, r11);
        r6 = 0;
        r0 = r51;
        r1 = r45;
        r46 = r0.namespaceResolve(r1, r6);
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x09ee;
    L_0x09cd:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r9 = "expected string literal after 'declare option ";
        r6 = r6.append(r9);
        r0 = r45;
        r6 = r6.append(r0);
        r9 = 39;
        r6 = r6.append(r9);
        r6 = r6.toString();
        r0 = r51;
        r0.syntaxError(r6);
        goto L_0x0986;
    L_0x09ee:
        r6 = new java.lang.String;
        r0 = r51;
        r9 = r0.tokenBuffer;
        r10 = 0;
        r0 = r51;
        r11 = r0.tokenBufferLength;
        r6.<init>(r9, r10, r11);
        r0 = r51;
        r1 = r46;
        r0.handleOption(r1, r6);
        goto L_0x0986;
    L_0x0a04:
        r0 = r51;
        r6 = r0.orderingModeSeen;
        if (r6 == 0) goto L_0x0a19;
    L_0x0a0a:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x0a19;
    L_0x0a10:
        r6 = "duplicate 'declare ordering' seen";
        r9 = "XQST0065";
        r0 = r51;
        r0.syntaxError(r6, r9);
    L_0x0a19:
        r6 = 1;
        r0 = r51;
        r0.orderingModeSeen = r6;
        r51.getRawToken();
        r6 = "ordered";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0a37;
    L_0x0a2b:
        r6 = 0;
        r0 = r51;
        r0.orderingModeUnordered = r6;
    L_0x0a30:
        r51.parseSeparator();
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0a37:
        r6 = "unordered";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0a47;
    L_0x0a41:
        r6 = 1;
        r0 = r51;
        r0.orderingModeUnordered = r6;
        goto L_0x0a30;
    L_0x0a47:
        r6 = "ordering declaration must be ordered or unordered";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0a51:
        r0 = r51;
        r6 = r0.parseCount;
        r9 = 1;
        if (r6 == r9) goto L_0x0ac6;
    L_0x0a58:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "'xquery version' does not start module";
        r0 = r51;
        r0.error(r6, r9);
    L_0x0a61:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 != r9) goto L_0x0ad6;
    L_0x0a6c:
        r50 = new java.lang.String;
        r0 = r51;
        r6 = r0.tokenBuffer;
        r9 = 0;
        r0 = r51;
        r10 = r0.tokenBufferLength;
        r0 = r50;
        r0.<init>(r6, r9, r10);
        r6 = "1.0";
        r0 = r50;
        r6 = r0.equals(r6);
        if (r6 != 0) goto L_0x0aa4;
    L_0x0a86:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "unrecognized xquery version ";
        r9 = r9.append(r10);
        r0 = r50;
        r9 = r9.append(r0);
        r9 = r9.toString();
        r10 = "XQST0031";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x0aa4:
        r51.getRawToken();
        r6 = "encoding";
        r0 = r51;
        r6 = r0.match(r6);
        if (r6 == 0) goto L_0x0b54;
    L_0x0ab1:
        r51.getRawToken();
        r0 = r51;
        r6 = r0.curToken;
        r9 = 34;
        if (r6 == r9) goto L_0x0ae0;
    L_0x0abc:
        r6 = "invalid encoding specification";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0ac6:
        r0 = r51;
        r6 = r0.commentCount;
        if (r6 <= 0) goto L_0x0a61;
    L_0x0acc:
        r6 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r9 = "comments should not precede 'xquery version'";
        r0 = r51;
        r0.error(r6, r9);
        goto L_0x0a61;
    L_0x0ad6:
        r6 = "missing version string after 'xquery version'";
        r0 = r51;
        r27 = r0.syntaxError(r6);
        goto L_0x000e;
    L_0x0ae0:
        r24 = new java.lang.String;
        r0 = r51;
        r6 = r0.tokenBuffer;
        r9 = 0;
        r0 = r51;
        r10 = r0.tokenBufferLength;
        r0 = r24;
        r0.<init>(r6, r9, r10);
        r0 = r51;
        r0 = r0.tokenBufferLength;
        r29 = r0;
        if (r29 != 0) goto L_0x0b41;
    L_0x0af8:
        r17 = 1;
    L_0x0afa:
        r29 = r29 + -1;
        if (r29 < 0) goto L_0x0b44;
    L_0x0afe:
        if (r17 != 0) goto L_0x0b44;
    L_0x0b00:
        r0 = r51;
        r6 = r0.tokenBuffer;
        r18 = r6[r29];
        r6 = 65;
        r0 = r18;
        if (r0 < r6) goto L_0x0b12;
    L_0x0b0c:
        r6 = 90;
        r0 = r18;
        if (r0 <= r6) goto L_0x0afa;
    L_0x0b12:
        r6 = 97;
        r0 = r18;
        if (r0 < r6) goto L_0x0b1e;
    L_0x0b18:
        r6 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        r0 = r18;
        if (r0 <= r6) goto L_0x0afa;
    L_0x0b1e:
        if (r29 == 0) goto L_0x0b3e;
    L_0x0b20:
        r6 = 48;
        r0 = r18;
        if (r0 < r6) goto L_0x0b2c;
    L_0x0b26:
        r6 = 57;
        r0 = r18;
        if (r0 <= r6) goto L_0x0afa;
    L_0x0b2c:
        r6 = 46;
        r0 = r18;
        if (r0 == r6) goto L_0x0afa;
    L_0x0b32:
        r6 = 95;
        r0 = r18;
        if (r0 == r6) goto L_0x0afa;
    L_0x0b38:
        r6 = 45;
        r0 = r18;
        if (r0 == r6) goto L_0x0afa;
    L_0x0b3e:
        r17 = 1;
        goto L_0x0afa;
    L_0x0b41:
        r17 = 0;
        goto L_0x0afa;
    L_0x0b44:
        if (r17 == 0) goto L_0x0b51;
    L_0x0b46:
        r6 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r9 = "invalid encoding name syntax";
        r10 = "XQST0087";
        r0 = r51;
        r0.error(r6, r9, r10);
    L_0x0b51:
        r51.getRawToken();
    L_0x0b54:
        r0 = r51;
        r6 = r0.curToken;
        r9 = 59;
        if (r6 == r9) goto L_0x0b63;
    L_0x0b5c:
        r6 = "missing ';'";
        r0 = r51;
        r0.syntaxError(r6);
    L_0x0b63:
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0b67:
        r0 = r51;
        r6 = r0.baseURIDeclarationSeen;
        if (r6 == 0) goto L_0x0b7c;
    L_0x0b6d:
        r0 = r51;
        r6 = r0.interactive;
        if (r6 != 0) goto L_0x0b7c;
    L_0x0b73:
        r6 = "duplicate 'declare base-uri' seen";
        r9 = "XQST0032";
        r0 = r51;
        r0.syntaxError(r6, r9);
    L_0x0b7c:
        r6 = 1;
        r0 = r51;
        r0.baseURIDeclarationSeen = r6;
        r49 = r51.parseURILiteral();
        r0 = r49;
        r6 = r0 instanceof gnu.expr.Expression;
        if (r6 == 0) goto L_0x0b91;
    L_0x0b8b:
        r49 = (gnu.expr.Expression) r49;
        r27 = r49;
        goto L_0x000e;
    L_0x0b91:
        r51.parseSeparator();
        r49 = (java.lang.String) r49;
        r0 = r51;
        r1 = r49;
        r0.setStaticBaseUri(r1);
        r27 = gnu.expr.QuoteExp.voidExp;
        goto L_0x000e;
    L_0x0ba1:
        r6 = move-exception;
        goto L_0x05d2;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQParser.parse(gnu.expr.Compilation):gnu.expr.Expression");
    }

    public void handleOption(Symbol name, String value) {
    }

    public static Expression makeFunctionExp(String className, String name) {
        return makeFunctionExp(className, Compilation.mangleNameIfNeeded(name), name);
    }

    public static Expression makeFunctionExp(String className, String fieldName, String name) {
        return new ReferenceExp(name, Declaration.getDeclarationValueFromStatic(className, fieldName, name));
    }

    String tokenString() {
        switch (this.curToken) {
            case EOF_TOKEN /*-1*/:
                return "<EOF>";
            case STRING_TOKEN /*34*/:
                StringBuffer sbuf = new StringBuffer();
                sbuf.append('\"');
                for (int i = AXIS_ANCESTOR; i < this.tokenBufferLength; i += AXIS_ANCESTOR_OR_SELF) {
                    char ch = this.tokenBuffer[i];
                    if (ch == '\"') {
                        sbuf.append('\"');
                    }
                    sbuf.append(ch);
                }
                sbuf.append('\"');
                return sbuf.toString();
            case NCNAME_TOKEN /*65*/:
            case QNAME_TOKEN /*81*/:
                return new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength);
            case FNAME_TOKEN /*70*/:
                return new String(this.tokenBuffer, AXIS_ANCESTOR, this.tokenBufferLength) + " + '('";
            default:
                if (this.curToken >= OP_AXIS_FIRST && this.curToken - 100 < COUNT_OP_AXIS) {
                    return axisNames[this.curToken - 100] + "::-axis(" + this.curToken + ")";
                }
                if (this.curToken < 32 || this.curToken >= 127) {
                    return Integer.toString(this.curToken);
                }
                return Integer.toString(this.curToken) + "='" + ((char) this.curToken) + "'";
        }
    }

    public void error(char severity, String message, String code) {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError(severity, this.port.getName(), this.curLine, this.curColumn, message);
        err.code = code;
        messages.error(err);
    }

    public void error(char severity, String message) {
        error(severity, message, null);
    }

    public Expression declError(String message) throws IOException, SyntaxException {
        if (this.interactive) {
            return syntaxError(message);
        }
        error(message);
        while (this.curToken != 59 && this.curToken != EOF_TOKEN) {
            getRawToken();
        }
        return new ErrorExp(message);
    }

    public Expression syntaxError(String message, String code) throws IOException, SyntaxException {
        error('e', message, code);
        if (!this.interactive) {
            return new ErrorExp(message);
        }
        int ch;
        this.curToken = AXIS_ANCESTOR;
        this.curValue = null;
        this.nesting = AXIS_ANCESTOR;
        ((InPort) getPort()).readState = '\n';
        do {
            ch = read();
            if (ch >= 0) {
                if (ch == COUNT_OP_AXIS) {
                    break;
                }
            } else {
                break;
            }
        } while (ch != EOL_TOKEN);
        unread(ch);
        throw new SyntaxException(getMessages());
    }

    public Expression syntaxError(String message) throws IOException, SyntaxException {
        return syntaxError(message, "XPST0003");
    }

    public void eofError(String msg) throws SyntaxException {
        fatal(msg, "XPST0003");
    }

    public void fatal(String msg, String code) throws SyntaxException {
        SourceMessages messages = getMessages();
        SourceError err = new SourceError('f', this.port.getName(), this.curLine, this.curColumn, msg);
        err.code = code;
        messages.error(err);
        throw new SyntaxException(messages);
    }

    void warnOldVersion(String message) {
        if (warnOldVersion || this.comp.isPedantic()) {
            error(this.comp.isPedantic() ? 'e' : 'w', message);
        }
    }

    public void maybeSetLine(Expression exp, int line, int column) {
        String file = getName();
        if (file != null && exp.getFileName() == null && !(exp instanceof QuoteExp)) {
            exp.setFile(file);
            exp.setLine(line, column);
        }
    }

    public void maybeSetLine(Declaration decl, int line, int column) {
        String file = getName();
        if (file != null) {
            decl.setFile(file);
            decl.setLine(line, column);
        }
    }
}
