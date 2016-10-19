package gnu.kawa.lispexpr;

import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public abstract class ReadTableEntry {
    public static final ReadTableEntry brace;
    public static final ReadTableEntry constituent;
    public static final ReadTableEntry illegal;
    public static final ReadTableEntry multipleEscape;
    public static final ReadTableEntry singleEscape;
    public static final ReadTableEntry whitespace;

    static {
        illegal = new ReaderMisc(0);
        whitespace = new ReaderMisc(1);
        singleEscape = new ReaderMisc(3);
        multipleEscape = new ReaderMisc(4);
        constituent = new ReaderMisc(2);
        brace = new ReaderMisc(2);
    }

    public static ReadTableEntry getIllegalInstance() {
        return illegal;
    }

    public static ReadTableEntry getWhitespaceInstance() {
        return whitespace;
    }

    public static ReadTableEntry getSingleEscapeInstance() {
        return singleEscape;
    }

    public static ReadTableEntry getMultipleEscapeInstance() {
        return multipleEscape;
    }

    public static ReadTableEntry getDigitInstance() {
        return constituent;
    }

    public static ReadTableEntry getConstituentInstance() {
        return constituent;
    }

    public int getKind() {
        return 5;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        throw new Error("invalid character");
    }
}
