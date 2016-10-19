package gnu.xquery.util;

import gnu.mapping.OutPort;
import gnu.mapping.WrappedException;
import gnu.xml.XMLPrinter;
import java.io.FileOutputStream;

public class Debug {
    public static String traceFilename;
    public static OutPort tracePort;
    public static String tracePrefix;
    public static boolean traceShouldAppend;
    public static boolean traceShouldFlush;

    static {
        tracePrefix = "XQuery-trace: ";
        tracePort = null;
        traceFilename = "XQuery-trace.log";
        traceShouldFlush = true;
        traceShouldAppend = false;
    }

    public static synchronized Object trace(Object value, Object message) {
        synchronized (Debug.class) {
            OutPort out = tracePort;
            if (out == null) {
                try {
                    out = new OutPort(new FileOutputStream(traceFilename, traceShouldAppend));
                } catch (Throwable ex) {
                    WrappedException wrappedException = new WrappedException("Could not open '" + traceFilename + "' for fn:trace output", ex);
                }
                tracePort = out;
            }
            out.print(tracePrefix);
            out.print(message);
            out.print(' ');
            new XMLPrinter(out, false).writeObject(value);
            out.println();
            if (traceShouldFlush) {
                out.flush();
            }
        }
        return value;
    }
}
