package kawa;

import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.kawa.swingviews.SwingContent;
import gnu.mapping.Environment;
import gnu.mapping.Future;
import gnu.mapping.Values;
import gnu.text.Path;
import gnu.text.QueueReader;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ReplDocument extends DefaultStyledDocument implements DocumentListener, FocusListener {
    static Style blueStyle;
    public static Style defaultStyle;
    public static Style inputStyle;
    static Style promptStyle;
    public static Style redStyle;
    public static StyleContext styles;
    Object closeListeners;
    SwingContent content;
    public int endMark;
    Environment environment;
    final ReplPaneOutPort err_stream;
    final GuiInPort in_p;
    final QueueReader in_r;
    Language language;
    int length;
    final ReplPaneOutPort out_stream;
    public int outputMark;
    JTextPane pane;
    int paneCount;
    Future thread;

    /* renamed from: kawa.ReplDocument.3 */
    class C01603 implements Runnable {
        final /* synthetic */ String val$str;
        final /* synthetic */ AttributeSet val$style;

        C01603(String str, AttributeSet attributeSet) {
            this.val$str = str;
            this.val$style = attributeSet;
        }

        public void run() {
            boolean moveCaret = ReplDocument.this.pane != null && ReplDocument.this.pane.getCaretPosition() == ReplDocument.this.outputMark;
            ReplDocument.this.insertString(ReplDocument.this.outputMark, this.val$str, this.val$style);
            int len = this.val$str.length();
            ReplDocument replDocument = ReplDocument.this;
            replDocument.outputMark += len;
            if (moveCaret) {
                ReplDocument.this.pane.setCaretPosition(ReplDocument.this.outputMark);
            }
        }
    }

    /* renamed from: kawa.ReplDocument.4 */
    class C01614 implements Runnable {
        C01614() {
        }

        public void run() {
            int inputStart = ReplDocument.this.outputMark;
            if (inputStart <= ReplDocument.this.endMark) {
                CharSequence b = ReplDocument.this.content.buffer;
                int lineAfter = b.indexOf(10, inputStart);
                if (lineAfter == ReplDocument.this.endMark) {
                    ReplDocument.this.endMark = -1;
                }
                if (inputStart == ReplDocument.this.outputMark) {
                    ReplDocument.this.outputMark = lineAfter + 1;
                }
                if (ReplDocument.this.in_r != null) {
                    synchronized (ReplDocument.this.in_r) {
                        ReplDocument.this.in_r.append(b, inputStart, lineAfter + 1);
                        ReplDocument.this.in_r.notifyAll();
                    }
                }
            }
        }
    }

    public interface DocumentCloseListener {
        void closed(ReplDocument replDocument);
    }

    /* renamed from: kawa.ReplDocument.1 */
    class C01851 extends QueueReader {
        C01851() {
        }

        public void checkAvailable() {
            ReplDocument.this.checkingPendingInput();
        }
    }

    /* renamed from: kawa.ReplDocument.2 */
    class C01862 extends repl {
        final /* synthetic */ boolean val$shared;

        /* renamed from: kawa.ReplDocument.2.1 */
        class C01591 implements Runnable {
            C01591() {
            }

            public void run() {
                ReplDocument.this.fireDocumentClosed();
            }
        }

        C01862(Language x0, boolean z) {
            this.val$shared = z;
            super(x0);
        }

        public Object apply0() {
            Environment env = Environment.getCurrent();
            if (this.val$shared) {
                env.setIndirectDefines();
            }
            ReplDocument.this.environment = env;
            Shell.run(this.language, env);
            SwingUtilities.invokeLater(new C01591());
            return Values.empty;
        }
    }

    static {
        styles = new StyleContext();
        defaultStyle = styles.addStyle("default", null);
        inputStyle = styles.addStyle("input", null);
        redStyle = styles.addStyle("red", null);
        blueStyle = styles.addStyle("blue", null);
        promptStyle = styles.addStyle("prompt", null);
        StyleConstants.setForeground(redStyle, Color.red);
        StyleConstants.setForeground(blueStyle, Color.blue);
        StyleConstants.setForeground(promptStyle, Color.green);
        StyleConstants.setBold(inputStyle, true);
    }

    public ReplDocument(Language language, Environment penvironment, boolean shared) {
        this(new SwingContent(), language, penvironment, shared);
    }

    private ReplDocument(SwingContent content, Language language, Environment penvironment, boolean shared) {
        super(content, styles);
        this.outputMark = 0;
        this.endMark = -1;
        this.length = 0;
        this.content = content;
        ModuleBody.exitIncrement();
        addDocumentListener(this);
        this.language = language;
        this.in_r = new C01851();
        this.out_stream = new ReplPaneOutPort(this, "/dev/stdout", defaultStyle);
        this.err_stream = new ReplPaneOutPort(this, "/dev/stderr", redStyle);
        this.in_p = new GuiInPort(this.in_r, Path.valueOf("/dev/stdin"), this.out_stream, this);
        this.thread = Future.make(new C01862(language, shared), penvironment, this.in_p, this.out_stream, this.err_stream);
        this.thread.start();
    }

    public synchronized void deleteOldText() {
        int lineBefore = 0;
        synchronized (this) {
            try {
                String str = getText(0, this.outputMark);
                if (this.outputMark > 0) {
                    lineBefore = str.lastIndexOf(10, this.outputMark - 1) + 1;
                }
                remove(0, lineBefore);
            } catch (BadLocationException ex) {
                throw new Error(ex);
            }
        }
    }

    public void insertString(int pos, String str, AttributeSet style) {
        try {
            super.insertString(pos, str, style);
        } catch (BadLocationException ex) {
            throw new Error(ex);
        }
    }

    public void write(String str, AttributeSet style) {
        SwingUtilities.invokeLater(new C01603(str, style));
    }

    public void checkingPendingInput() {
        SwingUtilities.invokeLater(new C01614());
    }

    public void focusGained(FocusEvent e) {
        Object source = e.getSource();
        if (source instanceof ReplPane) {
            this.pane = (ReplPane) source;
        } else {
            this.pane = null;
        }
        if (source instanceof ReplPane) {
            source = (ReplPane) source;
        } else {
            source = null;
        }
        this.pane = source;
    }

    public void focusLost(FocusEvent e) {
        this.pane = null;
    }

    public void changedUpdate(DocumentEvent e) {
        textValueChanged(e);
    }

    public void insertUpdate(DocumentEvent e) {
        textValueChanged(e);
    }

    public void removeUpdate(DocumentEvent e) {
        textValueChanged(e);
    }

    public synchronized void textValueChanged(DocumentEvent e) {
        int pos = e.getOffset();
        int delta = getLength() - this.length;
        this.length += delta;
        if (pos < this.outputMark) {
            this.outputMark += delta;
        } else if (pos - delta < this.outputMark) {
            this.outputMark = pos;
        }
        if (this.endMark >= 0) {
            if (pos < this.endMark) {
                this.endMark += delta;
            } else if (pos - delta < this.endMark) {
                this.endMark = pos;
            }
        }
    }

    void close() {
        this.in_r.appendEOF();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        this.thread.stop();
        fireDocumentClosed();
        ModuleBody.exitDecrement();
    }

    public void addDocumentCloseListener(DocumentCloseListener listener) {
        if (this.closeListeners == null) {
            this.closeListeners = listener;
            return;
        }
        ArrayList vec;
        if (this.closeListeners instanceof ArrayList) {
            vec = (ArrayList) this.closeListeners;
        } else {
            vec = new ArrayList(10);
            vec.add(this.closeListeners);
            this.closeListeners = vec;
        }
        vec.add(listener);
    }

    public void removeDocumentCloseListener(DocumentCloseListener listener) {
        if (this.closeListeners instanceof DocumentCloseListener) {
            if (this.closeListeners == listener) {
                this.closeListeners = null;
            }
        } else if (this.closeListeners != null) {
            ArrayList vec = this.closeListeners;
            int i = vec.size();
            while (true) {
                i--;
                if (i < 0) {
                    break;
                } else if (vec.get(i) == listener) {
                    vec.remove(i);
                }
            }
            if (vec.size() == 0) {
                this.closeListeners = null;
            }
        }
    }

    void fireDocumentClosed() {
        if (this.closeListeners instanceof DocumentCloseListener) {
            ((DocumentCloseListener) this.closeListeners).closed(this);
        } else if (this.closeListeners != null) {
            ArrayList vec = this.closeListeners;
            int i = vec.size();
            while (true) {
                i--;
                if (i >= 0) {
                    ((DocumentCloseListener) vec.get(i)).closed(this);
                } else {
                    return;
                }
            }
        }
    }
}
