package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Pair extends LList implements Externalizable {
    protected Object car;
    protected Object cdr;

    public Pair(Object carval, Object cdrval) {
        this.car = carval;
        this.cdr = cdrval;
    }

    public Object getCar() {
        return this.car;
    }

    public Object getCdr() {
        return this.cdr;
    }

    public void setCar(Object car) {
        this.car = car;
    }

    public void setCdr(Object cdr) {
        this.cdr = cdr;
    }

    public void setCdrBackdoor(Object cdr) {
        this.cdr = cdr;
    }

    public int size() {
        int n = LList.listLength(this, true);
        if (n >= 0) {
            return n;
        }
        if (n == -1) {
            return Integer.MAX_VALUE;
        }
        throw new RuntimeException("not a true list");
    }

    public boolean isEmpty() {
        return false;
    }

    public int length() {
        int n = 0;
        LList fast = this;
        Pair pair = this;
        while (fast != Empty) {
            if (fast instanceof Pair) {
                Pair fast_pair = (Pair) fast;
                if (fast_pair.cdr == Empty) {
                    return n + 1;
                }
                if (fast == pair && n > 0) {
                    return -1;
                }
                if (!(fast_pair.cdr instanceof Pair)) {
                    n++;
                    fast = fast_pair.cdr;
                } else if (!(pair instanceof Pair)) {
                    return -2;
                } else {
                    Pair slow = pair.cdr;
                    fast = ((Pair) fast_pair.cdr).cdr;
                    n += 2;
                    pair = slow;
                }
            } else if (!(fast instanceof Sequence)) {
                return -2;
            } else {
                int j = fast.size();
                if (j >= 0) {
                    j += n;
                }
                return j;
            }
        }
        return n;
    }

    public boolean hasNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0;
        } else {
            return PositionManager.getPositionObject(ipos).hasNext();
        }
    }

    public int nextPos(int ipos) {
        if (ipos > 0) {
            if (!((LListPosition) PositionManager.getPositionObject(ipos)).gotoNext()) {
                ipos = 0;
            }
            return ipos;
        } else if (ipos < 0) {
            return 0;
        } else {
            return PositionManager.manager.register(new LListPosition(this, 1, true));
        }
    }

    public Object getPosNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? this.car : eofValue;
        } else {
            return PositionManager.getPositionObject(ipos).getNext();
        }
    }

    public Object getPosPrevious(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? eofValue : lastPair().car;
        } else {
            return PositionManager.getPositionObject(ipos).getPrevious();
        }
    }

    public final Pair lastPair() {
        Pair pair = this;
        while (true) {
            Pair next = pair.cdr;
            if (!(next instanceof Pair)) {
                return pair;
            }
            pair = next;
        }
    }

    public int hashCode() {
        int hash = 1;
        LList lList = this;
        while (lList instanceof Pair) {
            Pair pair = (Pair) lList;
            Object obj = pair.car;
            hash = (hash * 31) + (obj == null ? 0 : obj.hashCode());
            lList = pair.cdr;
        }
        if (lList == LList.Empty || lList == null) {
            return hash;
        }
        return hash ^ lList.hashCode();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean equals(gnu.lists.Pair r5, gnu.lists.Pair r6) {
        /*
        r2 = 1;
        r3 = 0;
        if (r5 != r6) goto L_0x0005;
    L_0x0004:
        return r2;
    L_0x0005:
        if (r5 == 0) goto L_0x0009;
    L_0x0007:
        if (r6 != 0) goto L_0x0011;
    L_0x0009:
        r2 = r3;
        goto L_0x0004;
    L_0x000b:
        r5 = r0;
        r5 = (gnu.lists.Pair) r5;
        r6 = r1;
        r6 = (gnu.lists.Pair) r6;
    L_0x0011:
        r0 = r5.car;
        r1 = r6.car;
        if (r0 == r1) goto L_0x0021;
    L_0x0017:
        if (r0 == 0) goto L_0x001f;
    L_0x0019:
        r4 = r0.equals(r1);
        if (r4 != 0) goto L_0x0021;
    L_0x001f:
        r2 = r3;
        goto L_0x0004;
    L_0x0021:
        r0 = r5.cdr;
        r1 = r6.cdr;
        if (r0 == r1) goto L_0x0004;
    L_0x0027:
        if (r0 == 0) goto L_0x002b;
    L_0x0029:
        if (r1 != 0) goto L_0x002d;
    L_0x002b:
        r2 = r3;
        goto L_0x0004;
    L_0x002d:
        r4 = r0 instanceof gnu.lists.Pair;
        if (r4 == 0) goto L_0x0035;
    L_0x0031:
        r4 = r1 instanceof gnu.lists.Pair;
        if (r4 != 0) goto L_0x000b;
    L_0x0035:
        r2 = r0.equals(r1);
        goto L_0x0004;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.equals(gnu.lists.Pair, gnu.lists.Pair):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int compareTo(gnu.lists.Pair r7, gnu.lists.Pair r8) {
        /*
        r5 = 1;
        r3 = 0;
        r4 = -1;
        if (r7 != r8) goto L_0x0007;
    L_0x0005:
        r0 = r3;
    L_0x0006:
        return r0;
    L_0x0007:
        if (r7 != 0) goto L_0x000b;
    L_0x0009:
        r0 = r4;
        goto L_0x0006;
    L_0x000b:
        if (r8 != 0) goto L_0x0015;
    L_0x000d:
        r0 = r5;
        goto L_0x0006;
    L_0x000f:
        r7 = r1;
        r7 = (gnu.lists.Pair) r7;
        r8 = r2;
        r8 = (gnu.lists.Pair) r8;
    L_0x0015:
        r1 = r7.car;
        r2 = r8.car;
        r1 = (java.lang.Comparable) r1;
        r2 = (java.lang.Comparable) r2;
        r0 = r1.compareTo(r2);
        if (r0 != 0) goto L_0x0006;
    L_0x0023:
        r1 = r7.cdr;
        r2 = r8.cdr;
        if (r1 != r2) goto L_0x002b;
    L_0x0029:
        r0 = r3;
        goto L_0x0006;
    L_0x002b:
        if (r1 != 0) goto L_0x002f;
    L_0x002d:
        r0 = r4;
        goto L_0x0006;
    L_0x002f:
        if (r2 != 0) goto L_0x0033;
    L_0x0031:
        r0 = r5;
        goto L_0x0006;
    L_0x0033:
        r6 = r1 instanceof gnu.lists.Pair;
        if (r6 == 0) goto L_0x003b;
    L_0x0037:
        r6 = r2 instanceof gnu.lists.Pair;
        if (r6 != 0) goto L_0x000f;
    L_0x003b:
        r1 = (java.lang.Comparable) r1;
        r2 = (java.lang.Comparable) r2;
        r0 = r1.compareTo(r2);
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.compareTo(gnu.lists.Pair, gnu.lists.Pair):int");
    }

    public int compareTo(Object obj) {
        if (obj == Empty) {
            return 1;
        }
        return compareTo(this, (Pair) obj);
    }

    public Object get(int index) {
        Pair pair = this;
        int i = index;
        while (i > 0) {
            i--;
            if (pair.cdr instanceof Pair) {
                pair = pair.cdr;
            } else {
                if (pair.cdr instanceof Sequence) {
                    return ((Sequence) pair.cdr).get(i);
                }
                if (i == 0) {
                    return pair.car;
                }
                throw new IndexOutOfBoundsException();
            }
        }
        if (i == 0) {
            return pair.car;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }
        return equals(this, (Pair) obj);
    }

    public static Pair make(Object car, Object cdr) {
        return new Pair(car, cdr);
    }

    public Object[] toArray() {
        int len = size();
        Object[] arr = new Object[len];
        int i = 0;
        Sequence rest = this;
        while (i < len && (rest instanceof Pair)) {
            Pair pair = (Pair) rest;
            arr[i] = pair.car;
            rest = pair.cdr;
            i++;
        }
        int prefix = i;
        while (i < len) {
            arr[i] = rest.get(i - prefix);
            i++;
        }
        return arr;
    }

    public Object[] toArray(Object[] arr) {
        int alen = arr.length;
        int len = length();
        if (len > alen) {
            arr = new Object[len];
            alen = len;
        }
        int i = 0;
        Sequence rest = this;
        while (i < len && (rest instanceof Pair)) {
            Pair pair = (Pair) rest;
            arr[i] = pair.car;
            rest = pair.cdr;
            i++;
        }
        int prefix = i;
        while (i < len) {
            arr[i] = rest.get(i - prefix);
            i++;
        }
        if (len < alen) {
            arr[len] = null;
        }
        return arr;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.car);
        out.writeObject(this.cdr);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.car = in.readObject();
        this.cdr = in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        return this;
    }
}
