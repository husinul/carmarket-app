package gnu.text;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.FieldPosition;

public class IntegerFormat extends ReportFormat {
    public static final int MIN_DIGITS = 64;
    public static final int PAD_RIGHT = 16;
    public static final int SHOW_BASE = 8;
    public static final int SHOW_GROUPS = 1;
    public static final int SHOW_PLUS = 2;
    public static final int SHOW_SPACE = 4;
    public static final int UPPERCASE = 32;
    public int base;
    public int commaChar;
    public int commaInterval;
    public int flags;
    public int minWidth;
    public int padChar;

    public IntegerFormat() {
        this.base = 10;
        this.minWidth = SHOW_GROUPS;
        this.padChar = UPPERCASE;
        this.commaChar = 44;
        this.commaInterval = 3;
        this.flags = 0;
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        return format((Object) args, start, dst, fpos);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int format(java.lang.Object r25, int r26, java.io.Writer r27, java.text.FieldPosition r28) throws java.io.IOException {
        /*
        r24 = this;
        r0 = r25;
        r0 = r0 instanceof java.lang.Object[];
        r22 = r0;
        if (r22 == 0) goto L_0x00cb;
    L_0x0008:
        r22 = r25;
        r22 = (java.lang.Object[]) r22;
        r22 = (java.lang.Object[]) r22;
        r3 = r22;
    L_0x0010:
        r0 = r24;
        r0 = r0.minWidth;
        r22 = r0;
        r23 = 1;
        r0 = r22;
        r1 = r23;
        r2 = r26;
        r9 = gnu.text.ReportFormat.getParam(r0, r1, r3, r2);
        r0 = r24;
        r0 = r0.minWidth;
        r22 = r0;
        r23 = -1610612736; // 0xffffffffa0000000 float:-1.0842022E-19 double:NaN;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0032;
    L_0x0030:
        r26 = r26 + 1;
    L_0x0032:
        r0 = r24;
        r0 = r0.padChar;
        r22 = r0;
        r23 = 32;
        r0 = r22;
        r1 = r23;
        r2 = r26;
        r13 = gnu.text.ReportFormat.getParam(r0, r1, r3, r2);
        r0 = r24;
        r0 = r0.padChar;
        r22 = r0;
        r23 = -1610612736; // 0xffffffffa0000000 float:-1.0842022E-19 double:NaN;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0054;
    L_0x0052:
        r26 = r26 + 1;
    L_0x0054:
        r0 = r24;
        r0 = r0.commaChar;
        r22 = r0;
        r23 = 44;
        r0 = r22;
        r1 = r23;
        r2 = r26;
        r5 = gnu.text.ReportFormat.getParam(r0, r1, r3, r2);
        r0 = r24;
        r0 = r0.commaChar;
        r22 = r0;
        r23 = -1610612736; // 0xffffffffa0000000 float:-1.0842022E-19 double:NaN;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0076;
    L_0x0074:
        r26 = r26 + 1;
    L_0x0076:
        r0 = r24;
        r0 = r0.commaInterval;
        r22 = r0;
        r23 = 3;
        r0 = r22;
        r1 = r23;
        r2 = r26;
        r6 = gnu.text.ReportFormat.getParam(r0, r1, r3, r2);
        r0 = r24;
        r0 = r0.commaInterval;
        r22 = r0;
        r23 = -1610612736; // 0xffffffffa0000000 float:-1.0842022E-19 double:NaN;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0098;
    L_0x0096:
        r26 = r26 + 1;
    L_0x0098:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 1;
        if (r22 == 0) goto L_0x00ce;
    L_0x00a2:
        r16 = 1;
    L_0x00a4:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 16;
        if (r22 == 0) goto L_0x00d1;
    L_0x00ae:
        r15 = 1;
    L_0x00af:
        r22 = 48;
        r0 = r22;
        if (r13 != r0) goto L_0x00d3;
    L_0x00b5:
        r14 = 1;
    L_0x00b6:
        if (r3 == 0) goto L_0x00d7;
    L_0x00b8:
        r0 = r3.length;
        r22 = r0;
        r0 = r26;
        r1 = r22;
        if (r0 < r1) goto L_0x00d5;
    L_0x00c1:
        r22 = "#<missing format argument>";
        r0 = r27;
        r1 = r22;
        r0.write(r1);
    L_0x00ca:
        return r26;
    L_0x00cb:
        r3 = 0;
        goto L_0x0010;
    L_0x00ce:
        r16 = 0;
        goto L_0x00a4;
    L_0x00d1:
        r15 = 0;
        goto L_0x00af;
    L_0x00d3:
        r14 = 0;
        goto L_0x00b6;
    L_0x00d5:
        r25 = r3[r26];
    L_0x00d7:
        r0 = r24;
        r0 = r0.base;
        r22 = r0;
        r0 = r24;
        r1 = r25;
        r2 = r22;
        r17 = r0.convertToIntegerString(r1, r2);
        if (r17 == 0) goto L_0x0269;
    L_0x00e9:
        r22 = 0;
        r0 = r17;
        r1 = r22;
        r18 = r0.charAt(r1);
        r22 = 45;
        r0 = r18;
        r1 = r22;
        if (r0 != r1) goto L_0x0164;
    L_0x00fb:
        r11 = 1;
    L_0x00fc:
        r19 = r17.length();
        if (r11 == 0) goto L_0x0166;
    L_0x0102:
        r10 = r19 + -1;
    L_0x0104:
        if (r16 == 0) goto L_0x0169;
    L_0x0106:
        r22 = r10 + -1;
        r12 = r22 / r6;
    L_0x010a:
        r20 = r10 + r12;
        if (r11 != 0) goto L_0x0118;
    L_0x010e:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 6;
        if (r22 == 0) goto L_0x011a;
    L_0x0118:
        r20 = r20 + 1;
    L_0x011a:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 8;
        if (r22 == 0) goto L_0x0134;
    L_0x0124:
        r0 = r24;
        r0 = r0.base;
        r22 = r0;
        r23 = 16;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x016b;
    L_0x0132:
        r20 = r20 + 2;
    L_0x0134:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 64;
        if (r22 == 0) goto L_0x0154;
    L_0x013e:
        r20 = r10;
        r22 = 1;
        r0 = r19;
        r1 = r22;
        if (r0 != r1) goto L_0x0154;
    L_0x0148:
        r22 = 48;
        r0 = r18;
        r1 = r22;
        if (r0 != r1) goto L_0x0154;
    L_0x0150:
        if (r9 != 0) goto L_0x0154;
    L_0x0152:
        r19 = 0;
    L_0x0154:
        if (r15 != 0) goto L_0x0184;
    L_0x0156:
        if (r14 != 0) goto L_0x0184;
    L_0x0158:
        r0 = r20;
        if (r9 <= r0) goto L_0x0184;
    L_0x015c:
        r0 = r27;
        r0.write(r13);
        r9 = r9 + -1;
        goto L_0x0158;
    L_0x0164:
        r11 = 0;
        goto L_0x00fc;
    L_0x0166:
        r10 = r19;
        goto L_0x0104;
    L_0x0169:
        r12 = 0;
        goto L_0x010a;
    L_0x016b:
        r0 = r24;
        r0 = r0.base;
        r22 = r0;
        r23 = 8;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0134;
    L_0x0179:
        r22 = 48;
        r0 = r18;
        r1 = r22;
        if (r0 == r1) goto L_0x0134;
    L_0x0181:
        r20 = r20 + 1;
        goto L_0x0134;
    L_0x0184:
        r7 = 0;
        if (r11 == 0) goto L_0x01e8;
    L_0x0187:
        r22 = 45;
        r0 = r27;
        r1 = r22;
        r0.write(r1);
        r7 = r7 + 1;
        r19 = r19 + -1;
    L_0x0194:
        r0 = r24;
        r0 = r0.base;
        r22 = r0;
        r23 = 10;
        r0 = r22;
        r1 = r23;
        if (r0 <= r1) goto L_0x0210;
    L_0x01a2:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 32;
        if (r22 == 0) goto L_0x0210;
    L_0x01ac:
        r21 = 1;
    L_0x01ae:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 8;
        if (r22 == 0) goto L_0x01da;
    L_0x01b8:
        r0 = r24;
        r0 = r0.base;
        r22 = r0;
        r23 = 16;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0216;
    L_0x01c6:
        r22 = 48;
        r0 = r27;
        r1 = r22;
        r0.write(r1);
        if (r21 == 0) goto L_0x0213;
    L_0x01d1:
        r22 = 88;
    L_0x01d3:
        r0 = r27;
        r1 = r22;
        r0.write(r1);
    L_0x01da:
        if (r14 == 0) goto L_0x0278;
    L_0x01dc:
        r0 = r20;
        if (r9 <= r0) goto L_0x0278;
    L_0x01e0:
        r0 = r27;
        r0.write(r13);
        r9 = r9 + -1;
        goto L_0x01dc;
    L_0x01e8:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 2;
        if (r22 == 0) goto L_0x01fc;
    L_0x01f2:
        r22 = 43;
        r0 = r27;
        r1 = r22;
        r0.write(r1);
        goto L_0x0194;
    L_0x01fc:
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r22 = r22 & 4;
        if (r22 == 0) goto L_0x0194;
    L_0x0206:
        r22 = 32;
        r0 = r27;
        r1 = r22;
        r0.write(r1);
        goto L_0x0194;
    L_0x0210:
        r21 = 0;
        goto L_0x01ae;
    L_0x0213:
        r22 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x01d3;
    L_0x0216:
        r0 = r24;
        r0 = r0.base;
        r22 = r0;
        r23 = 8;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x01da;
    L_0x0224:
        r22 = 48;
        r0 = r18;
        r1 = r22;
        if (r0 == r1) goto L_0x01da;
    L_0x022c:
        r22 = 48;
        r0 = r27;
        r1 = r22;
        r0.write(r1);
        goto L_0x01da;
    L_0x0236:
        r7 = r8 + 1;
        r0 = r17;
        r4 = r0.charAt(r8);
        if (r21 == 0) goto L_0x0244;
    L_0x0240:
        r4 = java.lang.Character.toUpperCase(r4);
    L_0x0244:
        r0 = r27;
        r0.write(r4);
        r19 = r19 + -1;
        if (r16 == 0) goto L_0x0258;
    L_0x024d:
        if (r19 <= 0) goto L_0x0258;
    L_0x024f:
        r22 = r19 % r6;
        if (r22 != 0) goto L_0x0258;
    L_0x0253:
        r0 = r27;
        r0.write(r5);
    L_0x0258:
        r8 = r7;
    L_0x0259:
        if (r19 != 0) goto L_0x0236;
    L_0x025b:
        if (r15 == 0) goto L_0x0274;
    L_0x025d:
        r0 = r20;
        if (r9 <= r0) goto L_0x0274;
    L_0x0261:
        r0 = r27;
        r0.write(r13);
        r9 = r9 + -1;
        goto L_0x025d;
    L_0x0269:
        r22 = r25.toString();
        r0 = r27;
        r1 = r22;
        gnu.text.ReportFormat.print(r0, r1);
    L_0x0274:
        r26 = r26 + 1;
        goto L_0x00ca;
    L_0x0278:
        r8 = r7;
        goto L_0x0259;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.IntegerFormat.format(java.lang.Object, int, java.io.Writer, java.text.FieldPosition):int");
    }

    public String convertToIntegerString(Object x, int radix) {
        if (!(x instanceof Number)) {
            return null;
        }
        if (x instanceof BigInteger) {
            return ((BigInteger) x).toString(radix);
        }
        return Long.toString(((Number) x).longValue(), radix);
    }
}
