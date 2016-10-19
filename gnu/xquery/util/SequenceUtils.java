package gnu.xquery.util;

import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.NodeType;
import gnu.lists.Consumer;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.xml.NodeTree;

public class SequenceUtils {
    public static final NodeType textOrElement;

    public static boolean isZeroOrOne(Object arg) {
        return !(arg instanceof Values) || ((Values) arg).isEmpty();
    }

    static Object coerceToZeroOrOne(Object arg, String functionName, int iarg) {
        if (isZeroOrOne(arg)) {
            return arg;
        }
        throw new WrongType(functionName, iarg, arg, "xs:item()?");
    }

    public static Object zeroOrOne(Object arg) {
        return coerceToZeroOrOne(arg, "zero-or-one", 1);
    }

    public static Object oneOrMore(Object arg) {
        if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
            return arg;
        }
        throw new IllegalArgumentException();
    }

    public static Object exactlyOne(Object arg) {
        if (!(arg instanceof Values)) {
            return arg;
        }
        throw new IllegalArgumentException();
    }

    public static boolean isEmptySequence(Object arg) {
        return (arg instanceof Values) && ((Values) arg).isEmpty();
    }

    public static boolean exists(Object arg) {
        return ((arg instanceof Values) && ((Values) arg).isEmpty()) ? false : true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void insertBefore$X(java.lang.Object r11, long r12, java.lang.Object r14, gnu.mapping.CallContext r15) {
        /*
        r4 = r15.consumer;
        r6 = 0;
        r8 = 0;
        r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r7 > 0) goto L_0x000b;
    L_0x0009:
        r12 = 1;
    L_0x000b:
        r7 = r11 instanceof gnu.mapping.Values;
        if (r7 == 0) goto L_0x0030;
    L_0x000f:
        r5 = r11;
        r5 = (gnu.mapping.Values) r5;
        r2 = 0;
        r0 = 0;
    L_0x0015:
        r3 = r5.nextPos(r2);
        if (r3 != 0) goto L_0x001d;
    L_0x001b:
        if (r6 == 0) goto L_0x0024;
    L_0x001d:
        r8 = 1;
        r0 = r0 + r8;
        r7 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1));
        if (r7 != 0) goto L_0x0028;
    L_0x0024:
        gnu.mapping.Values.writeValues(r14, r4);
        r6 = 1;
    L_0x0028:
        if (r3 != 0) goto L_0x002b;
    L_0x002a:
        return;
    L_0x002b:
        r5.consumePosRange(r2, r3, r4);
        r2 = r3;
        goto L_0x0015;
    L_0x0030:
        r8 = 1;
        r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r7 > 0) goto L_0x0039;
    L_0x0036:
        gnu.mapping.Values.writeValues(r14, r4);
    L_0x0039:
        r4.writeObject(r11);
        r8 = 1;
        r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r7 <= 0) goto L_0x002a;
    L_0x0042:
        gnu.mapping.Values.writeValues(r14, r4);
        goto L_0x002a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.insertBefore$X(java.lang.Object, long, java.lang.Object, gnu.mapping.CallContext):void");
    }

    public static void remove$X(Object arg, long position, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values values = (Values) arg;
            int ipos = 0;
            long i = 0;
            while (true) {
                int next = values.nextPos(ipos);
                if (next != 0) {
                    i++;
                    if (i != position) {
                        values.consumePosRange(ipos, next, out);
                    }
                    ipos = next;
                } else {
                    return;
                }
            }
        } else if (position != 1) {
            out.writeObject(arg);
        }
    }

    public static void reverse$X(Object arg, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            int n;
            Values vals = (Values) arg;
            int ipos = 0;
            int[] poses = new int[100];
            int n2 = 0;
            while (true) {
                if (n2 >= poses.length) {
                    int[] t = new int[(n2 * 2)];
                    System.arraycopy(poses, 0, t, 0, n2);
                    poses = t;
                }
                n = n2 + 1;
                poses[n2] = ipos;
                ipos = vals.nextPos(ipos);
                if (ipos == 0) {
                    break;
                }
                n2 = n;
            }
            int i = n - 1;
            while (true) {
                i--;
                if (i >= 0) {
                    vals.consumePosRange(poses[i], poses[i + 1], out);
                } else {
                    return;
                }
            }
        }
        out.writeObject(arg);
    }

    public static void indexOf$X(Object seqParam, Object srchParam, NamedCollator collator, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (seqParam instanceof Values) {
            Values vals = (Values) seqParam;
            int ipos = vals.startPos();
            int i = 1;
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos != 0) {
                    if (Compare.apply(72, vals.getPosPrevious(ipos), srchParam, collator)) {
                        out.writeInt(i);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else if (Compare.apply(72, seqParam, srchParam, collator)) {
            out.writeInt(1);
        }
    }

    static {
        textOrElement = new NodeType("element-or-text", 3);
    }

    public static boolean deepEqualChildren(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        NodeType filter = textOrElement;
        int child1 = seq1.firstChildPos(ipos1, filter);
        int child2 = seq2.firstChildPos(ipos2, filter);
        while (child1 != 0 && child2 != 0) {
            if (!deepEqual(seq1, child1, seq2, child2, collator)) {
                return false;
            }
            child1 = seq1.nextMatching(child1, filter, -1, false);
            child2 = seq2.nextMatching(child2, filter, -1, false);
        }
        if (child1 == child2) {
            return true;
        }
        return false;
    }

    public static boolean deepEqual(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        int kind1 = seq1.getNextKind(ipos1);
        int kind2 = seq2.getNextKind(ipos2);
        switch (kind1) {
            case Sequence.ELEMENT_VALUE /*33*/:
                if (kind1 != kind2) {
                    return false;
                }
                if (seq1.posLocalName(ipos1) != seq2.posLocalName(ipos2)) {
                    return false;
                }
                if (seq1.posNamespaceURI(ipos1) != seq2.posNamespaceURI(ipos2)) {
                    return false;
                }
                int attr1 = seq1.firstAttributePos(ipos1);
                int nattr1 = 0;
                while (attr1 != 0 && seq1.getNextKind(attr1) == 35) {
                    nattr1++;
                    String local = seq1.posLocalName(attr1);
                    int attr2 = seq2.getAttributeI(ipos2, seq1.posNamespaceURI(attr1), local);
                    if (attr2 == 0) {
                        return false;
                    }
                    if (!deepEqualItems(KNode.getNodeValue(seq1, attr1), KNode.getNodeValue(seq2, attr2), collator)) {
                        return false;
                    }
                    attr1 = seq1.nextPos(attr1);
                }
                if (nattr1 != seq2.getAttributeCount(ipos2)) {
                    return false;
                }
                break;
            case Sequence.DOCUMENT_VALUE /*34*/:
                break;
            case Sequence.ATTRIBUTE_VALUE /*35*/:
                if (seq1.posLocalName(ipos1) != seq2.posLocalName(ipos2) || seq1.posNamespaceURI(ipos1) != seq2.posNamespaceURI(ipos2)) {
                    return false;
                }
                return deepEqualItems(KAttr.getObjectValue(seq1, ipos1), KAttr.getObjectValue(seq2, ipos2), collator);
            case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                if (seq1.posTarget(ipos1).equals(seq2.posTarget(ipos2))) {
                    return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
                }
                return false;
            default:
                if (kind1 != kind2) {
                    return false;
                }
                return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
        }
        return deepEqualChildren(seq1, ipos1, seq2, ipos2, collator);
    }

    public static boolean deepEqualItems(Object arg1, Object arg2, NamedCollator collator) {
        if (NumberValue.isNaN(arg1) && NumberValue.isNaN(arg2)) {
            return true;
        }
        return Compare.atomicCompare(8, arg1, arg2, collator);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean deepEqual(java.lang.Object r21, java.lang.Object r22, gnu.xquery.util.NamedCollator r23) {
        /*
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x0009;
    L_0x0006:
        r17 = 1;
    L_0x0008:
        return r17;
    L_0x0009:
        if (r21 == 0) goto L_0x0013;
    L_0x000b:
        r17 = gnu.mapping.Values.empty;
        r0 = r21;
        r1 = r17;
        if (r0 != r1) goto L_0x0023;
    L_0x0013:
        if (r22 == 0) goto L_0x001d;
    L_0x0015:
        r17 = gnu.mapping.Values.empty;
        r0 = r22;
        r1 = r17;
        if (r0 != r1) goto L_0x0020;
    L_0x001d:
        r17 = 1;
        goto L_0x0008;
    L_0x0020:
        r17 = 0;
        goto L_0x0008;
    L_0x0023:
        if (r22 == 0) goto L_0x002d;
    L_0x0025:
        r17 = gnu.mapping.Values.empty;
        r0 = r22;
        r1 = r17;
        if (r0 != r1) goto L_0x0030;
    L_0x002d:
        r17 = 0;
        goto L_0x0008;
    L_0x0030:
        r7 = 1;
        r8 = 1;
        r0 = r21;
        r9 = r0 instanceof gnu.mapping.Values;
        r0 = r22;
        r10 = r0 instanceof gnu.mapping.Values;
        if (r9 == 0) goto L_0x006e;
    L_0x003c:
        r17 = r21;
        r17 = (gnu.mapping.Values) r17;
        r15 = r17;
    L_0x0042:
        if (r10 == 0) goto L_0x0070;
    L_0x0044:
        r17 = r22;
        r17 = (gnu.mapping.Values) r17;
        r16 = r17;
    L_0x004a:
        r6 = 1;
    L_0x004b:
        if (r9 == 0) goto L_0x0057;
    L_0x004d:
        if (r6 == 0) goto L_0x0053;
    L_0x004f:
        r7 = r15.startPos();
    L_0x0053:
        r7 = r15.nextPos(r7);
    L_0x0057:
        if (r10 == 0) goto L_0x0065;
    L_0x0059:
        if (r6 == 0) goto L_0x005f;
    L_0x005b:
        r8 = r16.startPos();
    L_0x005f:
        r0 = r16;
        r8 = r0.nextPos(r8);
    L_0x0065:
        if (r7 == 0) goto L_0x0069;
    L_0x0067:
        if (r8 != 0) goto L_0x0076;
    L_0x0069:
        if (r7 != r8) goto L_0x0073;
    L_0x006b:
        r17 = 1;
        goto L_0x0008;
    L_0x006e:
        r15 = 0;
        goto L_0x0042;
    L_0x0070:
        r16 = 0;
        goto L_0x004a;
    L_0x0073:
        r17 = 0;
        goto L_0x0008;
    L_0x0076:
        if (r9 == 0) goto L_0x009a;
    L_0x0078:
        r11 = r15.getPosPrevious(r7);
    L_0x007c:
        if (r10 == 0) goto L_0x009d;
    L_0x007e:
        r0 = r16;
        r12 = r0.getPosPrevious(r8);
    L_0x0084:
        r0 = r11 instanceof gnu.kawa.xml.KNode;
        r17 = r0;
        if (r17 != 0) goto L_0x00a5;
    L_0x008a:
        r0 = r12 instanceof gnu.kawa.xml.KNode;
        r17 = r0;
        if (r17 != 0) goto L_0x00a5;
    L_0x0090:
        r17 = deepEqualItems(r21, r22, r23);	 Catch:{ Throwable -> 0x00a0 }
        if (r17 != 0) goto L_0x00e3;
    L_0x0096:
        r17 = 0;
        goto L_0x0008;
    L_0x009a:
        r11 = r21;
        goto L_0x007c;
    L_0x009d:
        r12 = r22;
        goto L_0x0084;
    L_0x00a0:
        r5 = move-exception;
        r17 = 0;
        goto L_0x0008;
    L_0x00a5:
        r0 = r11 instanceof gnu.kawa.xml.KNode;
        r17 = r0;
        if (r17 == 0) goto L_0x00df;
    L_0x00ab:
        r0 = r12 instanceof gnu.kawa.xml.KNode;
        r17 = r0;
        if (r17 == 0) goto L_0x00df;
    L_0x00b1:
        r13 = r11;
        r13 = (gnu.kawa.xml.KNode) r13;
        r14 = r12;
        r14 = (gnu.kawa.xml.KNode) r14;
        r0 = r13.sequence;
        r17 = r0;
        r17 = (gnu.xml.NodeTree) r17;
        r0 = r13.ipos;
        r19 = r0;
        r0 = r14.sequence;
        r18 = r0;
        r18 = (gnu.xml.NodeTree) r18;
        r0 = r14.ipos;
        r20 = r0;
        r0 = r17;
        r1 = r19;
        r2 = r18;
        r3 = r20;
        r4 = r23;
        r17 = deepEqual(r0, r1, r2, r3, r4);
        if (r17 != 0) goto L_0x00e3;
    L_0x00db:
        r17 = 0;
        goto L_0x0008;
    L_0x00df:
        r17 = 0;
        goto L_0x0008;
    L_0x00e3:
        if (r6 == 0) goto L_0x004b;
    L_0x00e5:
        r6 = 0;
        if (r9 != 0) goto L_0x00e9;
    L_0x00e8:
        r7 = 0;
    L_0x00e9:
        if (r10 != 0) goto L_0x004b;
    L_0x00eb:
        r8 = 0;
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.deepEqual(java.lang.Object, java.lang.Object, gnu.xquery.util.NamedCollator):boolean");
    }
}
