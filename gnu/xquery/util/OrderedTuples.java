package gnu.xquery.util;

import gnu.kawa.functions.NumberCompare;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.FilterConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;

public class OrderedTuples extends FilterConsumer {
    Procedure body;
    Object[] comps;
    int first;
    int f5n;
    int[] next;
    Object[] tuples;

    public void writeObject(Object v) {
        if (this.f5n >= this.tuples.length) {
            Object[] tmp = new Object[(this.f5n * 2)];
            System.arraycopy(this.tuples, 0, tmp, 0, this.f5n);
            this.tuples = tmp;
        }
        Object[] objArr = this.tuples;
        int i = this.f5n;
        this.f5n = i + 1;
        objArr[i] = v;
    }

    OrderedTuples() {
        super(null);
        this.tuples = new Object[10];
    }

    public static OrderedTuples make$V(Procedure body, Object[] comps) {
        OrderedTuples tuples = new OrderedTuples();
        tuples.comps = comps;
        tuples.body = body;
        return tuples;
    }

    public void run$X(CallContext ctx) throws Throwable {
        this.first = listsort(0);
        emit(ctx);
    }

    void emit(CallContext ctx) throws Throwable {
        int p = this.first;
        while (p >= 0) {
            emit(p, ctx);
            p = this.next[p];
        }
    }

    void emit(int index, CallContext ctx) throws Throwable {
        this.body.checkN((Object[]) this.tuples[index], ctx);
        ctx.runUntilDone();
    }

    int cmp(int a, int b) throws Throwable {
        for (int i = 0; i < this.comps.length; i += 3) {
            Procedure comparator = this.comps[i];
            String flags = this.comps[i + 1];
            NamedCollator collator = this.comps[i + 2];
            if (collator == null) {
                collator = NamedCollator.codepointCollation;
            }
            Object val1 = comparator.applyN((Object[]) this.tuples[a]);
            Object val2 = comparator.applyN((Object[]) this.tuples[b]);
            val1 = KNode.atomicValue(val1);
            val2 = KNode.atomicValue(val2);
            if (val1 instanceof UntypedAtomic) {
                val1 = val1.toString();
            }
            if (val2 instanceof UntypedAtomic) {
                val2 = val2.toString();
            }
            boolean empty1 = SequenceUtils.isEmptySequence(val1);
            boolean empty2 = SequenceUtils.isEmptySequence(val2);
            if (!empty1 || !empty2) {
                int c;
                if (empty1 || empty2) {
                    boolean z;
                    if (flags.charAt(1) == 'L') {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (empty1 == z) {
                        c = -1;
                    } else {
                        c = 1;
                    }
                } else {
                    boolean isNaN1 = (val1 instanceof Number) && Double.isNaN(((Number) val1).doubleValue());
                    boolean isNaN2 = (val2 instanceof Number) && Double.isNaN(((Number) val2).doubleValue());
                    if (!isNaN1 || !isNaN2) {
                        if (isNaN1 || isNaN2) {
                            c = isNaN1 == (flags.charAt(1) == 'L') ? -1 : 1;
                        } else if ((val1 instanceof Number) && (val2 instanceof Number)) {
                            c = NumberCompare.compare(val1, val2, false);
                        } else {
                            c = collator.compare(val1.toString(), val2.toString());
                        }
                    }
                }
                if (c != 0) {
                    if (flags.charAt(0) == 'A') {
                        return c;
                    }
                    return -c;
                }
            }
        }
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    int listsort(int r13) throws java.lang.Throwable {
        /*
        r12 = this;
        r9 = -1;
        r10 = r12.f5n;
        if (r10 != 0) goto L_0x0006;
    L_0x0005:
        return r9;
    L_0x0006:
        r10 = r12.f5n;
        r10 = new int[r10];
        r12.next = r10;
        r1 = 1;
    L_0x000d:
        r10 = r12.f5n;
        if (r1 != r10) goto L_0x0045;
    L_0x0011:
        r10 = r12.next;
        r11 = r1 + -1;
        r10[r11] = r9;
        r2 = 1;
    L_0x0018:
        r4 = r13;
        r13 = -1;
        r8 = -1;
        r3 = 0;
    L_0x001c:
        if (r4 < 0) goto L_0x0077;
    L_0x001e:
        r3 = r3 + 1;
        r6 = r4;
        r5 = 0;
        r1 = 0;
    L_0x0023:
        if (r1 >= r2) goto L_0x002d;
    L_0x0025:
        r5 = r5 + 1;
        r10 = r12.next;
        r6 = r10[r6];
        if (r6 >= 0) goto L_0x004e;
    L_0x002d:
        r7 = r2;
    L_0x002e:
        if (r5 > 0) goto L_0x0034;
    L_0x0030:
        if (r7 <= 0) goto L_0x0075;
    L_0x0032:
        if (r6 < 0) goto L_0x0075;
    L_0x0034:
        if (r5 != 0) goto L_0x0051;
    L_0x0036:
        r0 = r6;
        r10 = r12.next;
        r6 = r10[r6];
        r7 = r7 + -1;
    L_0x003d:
        if (r8 < 0) goto L_0x0073;
    L_0x003f:
        r10 = r12.next;
        r10[r8] = r0;
    L_0x0043:
        r8 = r0;
        goto L_0x002e;
    L_0x0045:
        r10 = r12.next;
        r11 = r1 + -1;
        r10[r11] = r1;
        r1 = r1 + 1;
        goto L_0x000d;
    L_0x004e:
        r1 = r1 + 1;
        goto L_0x0023;
    L_0x0051:
        if (r7 == 0) goto L_0x0055;
    L_0x0053:
        if (r6 >= 0) goto L_0x005d;
    L_0x0055:
        r0 = r4;
        r10 = r12.next;
        r4 = r10[r4];
        r5 = r5 + -1;
        goto L_0x003d;
    L_0x005d:
        r10 = r12.cmp(r4, r6);
        if (r10 > 0) goto L_0x006b;
    L_0x0063:
        r0 = r4;
        r10 = r12.next;
        r4 = r10[r4];
        r5 = r5 + -1;
        goto L_0x003d;
    L_0x006b:
        r0 = r6;
        r10 = r12.next;
        r6 = r10[r6];
        r7 = r7 + -1;
        goto L_0x003d;
    L_0x0073:
        r13 = r0;
        goto L_0x0043;
    L_0x0075:
        r4 = r6;
        goto L_0x001c;
    L_0x0077:
        r10 = r12.next;
        r10[r8] = r9;
        r10 = 1;
        if (r3 > r10) goto L_0x0080;
    L_0x007e:
        r9 = r13;
        goto L_0x0005;
    L_0x0080:
        r2 = r2 * 2;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.OrderedTuples.listsort(int):int");
    }
}
