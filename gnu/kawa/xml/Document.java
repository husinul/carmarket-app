package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.ThreadLocation;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xml.NodeTree;
import gnu.xml.XMLParser;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public class Document {
    private static HashMap cache;
    private static ThreadLocation docMapLocation;
    public static final Document document;

    private static class DocReference extends SoftReference {
        static ReferenceQueue queue;
        Path key;

        static {
            queue = new ReferenceQueue();
        }

        public DocReference(Path key, KDocument doc) {
            super(doc, queue);
            this.key = key;
        }
    }

    static {
        document = new Document();
        docMapLocation = new ThreadLocation("document-map");
        cache = new HashMap();
    }

    public static void parse(Object name, Consumer out) throws Throwable {
        SourceMessages messages = new SourceMessages();
        if (out instanceof XConsumer) {
            ((XConsumer) out).beginEntity(name);
        }
        XMLParser.parse(name, messages, out);
        if (messages.seenErrors()) {
            throw new SyntaxException("document function read invalid XML", messages);
        } else if (out instanceof XConsumer) {
            ((XConsumer) out).endEntity();
        }
    }

    public static KDocument parse(Object uri) throws Throwable {
        NodeTree tree = new NodeTree();
        parse(uri, tree);
        return new KDocument(tree, 10);
    }

    public static void clearLocalCache() {
        docMapLocation.getLocation().set(null);
    }

    public static void clearSoftCache() {
        cache = new HashMap();
    }

    public static KDocument parseCached(Object uri) throws Throwable {
        return parseCached(Path.valueOf(uri));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized gnu.kawa.xml.KDocument parseCached(gnu.text.Path r9) throws java.lang.Throwable {
        /*
        r7 = gnu.kawa.xml.Document.class;
        monitor-enter(r7);
    L_0x0003:
        r6 = gnu.kawa.xml.Document.DocReference.queue;	 Catch:{ all -> 0x0037 }
        r4 = r6.poll();	 Catch:{ all -> 0x0037 }
        r4 = (gnu.kawa.xml.Document.DocReference) r4;	 Catch:{ all -> 0x0037 }
        if (r4 != 0) goto L_0x002f;
    L_0x000d:
        r6 = docMapLocation;	 Catch:{ all -> 0x0037 }
        r2 = r6.getLocation();	 Catch:{ all -> 0x0037 }
        r6 = 0;
        r3 = r2.get(r6);	 Catch:{ all -> 0x0037 }
        r3 = (java.util.Hashtable) r3;	 Catch:{ all -> 0x0037 }
        if (r3 != 0) goto L_0x0024;
    L_0x001c:
        r3 = new java.util.Hashtable;	 Catch:{ all -> 0x0037 }
        r3.<init>();	 Catch:{ all -> 0x0037 }
        r2.set(r3);	 Catch:{ all -> 0x0037 }
    L_0x0024:
        r0 = r3.get(r9);	 Catch:{ all -> 0x0037 }
        r0 = (gnu.kawa.xml.KDocument) r0;	 Catch:{ all -> 0x0037 }
        if (r0 == 0) goto L_0x003a;
    L_0x002c:
        r1 = r0;
    L_0x002d:
        monitor-exit(r7);
        return r1;
    L_0x002f:
        r6 = cache;	 Catch:{ all -> 0x0037 }
        r8 = r4.key;	 Catch:{ all -> 0x0037 }
        r6.remove(r8);	 Catch:{ all -> 0x0037 }
        goto L_0x0003;
    L_0x0037:
        r6 = move-exception;
        monitor-exit(r7);
        throw r6;
    L_0x003a:
        r6 = cache;	 Catch:{ all -> 0x0037 }
        r5 = r6.get(r9);	 Catch:{ all -> 0x0037 }
        r5 = (gnu.kawa.xml.Document.DocReference) r5;	 Catch:{ all -> 0x0037 }
        if (r5 == 0) goto L_0x0051;
    L_0x0044:
        r0 = r5.get();	 Catch:{ all -> 0x0037 }
        r0 = (gnu.kawa.xml.KDocument) r0;	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x0064;
    L_0x004c:
        r6 = cache;	 Catch:{ all -> 0x0037 }
        r6.remove(r9);	 Catch:{ all -> 0x0037 }
    L_0x0051:
        r0 = parse(r9);	 Catch:{ all -> 0x0037 }
        r3.put(r9, r0);	 Catch:{ all -> 0x0037 }
        r6 = cache;	 Catch:{ all -> 0x0037 }
        r8 = new gnu.kawa.xml.Document$DocReference;	 Catch:{ all -> 0x0037 }
        r8.<init>(r9, r0);	 Catch:{ all -> 0x0037 }
        r6.put(r9, r8);	 Catch:{ all -> 0x0037 }
        r1 = r0;
        goto L_0x002d;
    L_0x0064:
        r3.put(r9, r0);	 Catch:{ all -> 0x0037 }
        r1 = r0;
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.xml.Document.parseCached(gnu.text.Path):gnu.kawa.xml.KDocument");
    }
}
