package kawa.standard;

import gnu.mapping.ProcedureN;

public class vector_append extends ProcedureN {
    public static final vector_append vectorAppend;

    static {
        vectorAppend = new vector_append("vector-append");
    }

    public vector_append(String name) {
        super(name);
    }

    public Object applyN(Object[] args) {
        return apply$V(args);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.lists.FVector apply$V(java.lang.Object[] r15) {
        /*
        r4 = 0;
        r1 = r15.length;
        r2 = r1;
    L_0x0003:
        r2 = r2 + -1;
        if (r2 < 0) goto L_0x0028;
    L_0x0007:
        r0 = r15[r2];
        r12 = r0 instanceof gnu.lists.FVector;
        if (r12 == 0) goto L_0x0015;
    L_0x000d:
        r0 = (gnu.lists.FVector) r0;
        r12 = r0.size();
        r4 = r4 + r12;
        goto L_0x0003;
    L_0x0015:
        r12 = 0;
        r5 = gnu.lists.LList.listLength(r0, r12);
        if (r5 >= 0) goto L_0x0026;
    L_0x001c:
        r12 = new gnu.mapping.WrongType;
        r13 = vectorAppend;
        r14 = "list or vector";
        r12.<init>(r13, r2, r0, r14);
        throw r12;
    L_0x0026:
        r4 = r4 + r5;
        goto L_0x0003;
    L_0x0028:
        r9 = new java.lang.Object[r4];
        r7 = 0;
        r2 = 0;
    L_0x002c:
        if (r2 >= r1) goto L_0x0068;
    L_0x002e:
        r0 = r15[r2];
        r12 = r0 instanceof gnu.lists.FVector;
        if (r12 == 0) goto L_0x004f;
    L_0x0034:
        r10 = r0;
        r10 = (gnu.lists.FVector) r10;
        r11 = r10.size();
        r3 = 0;
        r8 = r7;
    L_0x003d:
        if (r3 >= r11) goto L_0x004b;
    L_0x003f:
        r7 = r8 + 1;
        r12 = r10.get(r3);
        r9[r8] = r12;
        r3 = r3 + 1;
        r8 = r7;
        goto L_0x003d;
    L_0x004b:
        r7 = r8;
    L_0x004c:
        r2 = r2 + 1;
        goto L_0x002c;
    L_0x004f:
        r12 = r0 instanceof gnu.lists.Pair;
        if (r12 == 0) goto L_0x004c;
    L_0x0053:
        r12 = gnu.lists.LList.Empty;
        if (r0 == r12) goto L_0x004c;
    L_0x0057:
        r6 = r0;
        r6 = (gnu.lists.Pair) r6;
        r8 = r7 + 1;
        r12 = r6.getCar();
        r9[r7] = r12;
        r0 = r6.getCdr();
        r7 = r8;
        goto L_0x0053;
    L_0x0068:
        r12 = new gnu.lists.FVector;
        r12.<init>(r9);
        return r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.vector_append.apply$V(java.lang.Object[]):gnu.lists.FVector");
    }
}
