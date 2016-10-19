package gnu.math;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ExponentialFormat extends Format {
    static final double LOG10;
    public int expDigits;
    public char exponentChar;
    public boolean exponentShowSign;
    public int fracDigits;
    public boolean general;
    public int intDigits;
    public char overflowChar;
    public char padChar;
    public boolean showPlus;
    public int width;

    public ExponentialFormat() {
        this.fracDigits = -1;
        this.exponentChar = 'E';
    }

    static {
        LOG10 = Math.log(10.0d);
    }

    static boolean addOne(StringBuffer sbuf, int digStart, int digEnd) {
        int j = digEnd;
        while (j != digStart) {
            j--;
            char ch = sbuf.charAt(j);
            if (ch != '9') {
                sbuf.setCharAt(j, (char) (ch + 1));
                return false;
            }
            sbuf.setCharAt(j, '0');
        }
        sbuf.insert(j, '1');
        return true;
    }

    public StringBuffer format(float value, StringBuffer sbuf, FieldPosition fpos) {
        return format((double) value, this.fracDigits < 0 ? Float.toString(value) : null, sbuf, fpos);
    }

    public StringBuffer format(double value, StringBuffer sbuf, FieldPosition fpos) {
        return format(value, this.fracDigits < 0 ? Double.toString(value) : null, sbuf, fpos);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    java.lang.StringBuffer format(double r40, java.lang.String r42, java.lang.StringBuffer r43, java.text.FieldPosition r44) {
        /*
        r39 = this;
        r0 = r39;
        r0 = r0.intDigits;
        r20 = r0;
        r0 = r39;
        r6 = r0.fracDigits;
        r34 = 0;
        r33 = (r40 > r34 ? 1 : (r40 == r34 ? 0 : -1));
        if (r33 >= 0) goto L_0x0093;
    L_0x0010:
        r23 = 1;
    L_0x0012:
        if (r23 == 0) goto L_0x0019;
    L_0x0014:
        r0 = r40;
        r0 = -r0;
        r40 = r0;
    L_0x0019:
        r28 = r43.length();
        r31 = 1;
        if (r23 == 0) goto L_0x0097;
    L_0x0021:
        if (r6 < 0) goto L_0x002c;
    L_0x0023:
        r33 = 45;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
    L_0x002c:
        r9 = r43.length();
        r33 = java.lang.Double.isNaN(r40);
        if (r33 != 0) goto L_0x003c;
    L_0x0036:
        r33 = java.lang.Double.isInfinite(r40);
        if (r33 == 0) goto L_0x00ac;
    L_0x003c:
        r27 = 1;
    L_0x003e:
        if (r6 < 0) goto L_0x0042;
    L_0x0040:
        if (r27 == 0) goto L_0x016c;
    L_0x0042:
        if (r42 != 0) goto L_0x0048;
    L_0x0044:
        r42 = java.lang.Double.toString(r40);
    L_0x0048:
        r33 = 69;
        r0 = r42;
        r1 = r33;
        r18 = r0.indexOf(r1);
        if (r18 < 0) goto L_0x0166;
    L_0x0054:
        r0 = r43;
        r1 = r42;
        r0.append(r1);
        r18 = r18 + r9;
        r33 = r18 + 1;
        r0 = r42;
        r1 = r33;
        r33 = r0.charAt(r1);
        r34 = 45;
        r0 = r33;
        r1 = r34;
        if (r0 != r1) goto L_0x00af;
    L_0x006f:
        r24 = 1;
    L_0x0071:
        r13 = 0;
        if (r24 == 0) goto L_0x00b2;
    L_0x0074:
        r33 = 2;
    L_0x0076:
        r17 = r18 + r33;
    L_0x0078:
        r33 = r43.length();
        r0 = r17;
        r1 = r33;
        if (r0 >= r1) goto L_0x00b5;
    L_0x0082:
        r33 = r13 * 10;
        r0 = r43;
        r1 = r17;
        r34 = r0.charAt(r1);
        r34 = r34 + -48;
        r13 = r33 + r34;
        r17 = r17 + 1;
        goto L_0x0078;
    L_0x0093:
        r23 = 0;
        goto L_0x0012;
    L_0x0097:
        r0 = r39;
        r0 = r0.showPlus;
        r33 = r0;
        if (r33 == 0) goto L_0x00a9;
    L_0x009f:
        r33 = 43;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
        goto L_0x002c;
    L_0x00a9:
        r31 = 0;
        goto L_0x002c;
    L_0x00ac:
        r27 = 0;
        goto L_0x003e;
    L_0x00af:
        r24 = 0;
        goto L_0x0071;
    L_0x00b2:
        r33 = 1;
        goto L_0x0076;
    L_0x00b5:
        if (r24 == 0) goto L_0x00b8;
    L_0x00b7:
        r13 = -r13;
    L_0x00b8:
        r0 = r43;
        r1 = r18;
        r0.setLength(r1);
    L_0x00bf:
        if (r23 == 0) goto L_0x00c3;
    L_0x00c1:
        r9 = r9 + 1;
    L_0x00c3:
        r11 = r9 + 1;
        r0 = r43;
        r0.deleteCharAt(r11);
        r33 = r43.length();
        r10 = r33 - r9;
        r33 = 1;
        r0 = r33;
        if (r10 <= r0) goto L_0x00f5;
    L_0x00d6:
        r33 = r9 + r10;
        r33 = r33 + -1;
        r0 = r43;
        r1 = r33;
        r33 = r0.charAt(r1);
        r34 = 48;
        r0 = r33;
        r1 = r34;
        if (r0 != r1) goto L_0x00f5;
    L_0x00ea:
        r10 = r10 + -1;
        r33 = r9 + r10;
        r0 = r43;
        r1 = r33;
        r0.setLength(r1);
    L_0x00f5:
        r33 = r10 - r13;
        r29 = r33 + -1;
    L_0x00f9:
        r33 = r20 + -1;
        r13 = r13 - r33;
        if (r13 >= 0) goto L_0x01b7;
    L_0x00ff:
        r14 = -r13;
    L_0x0100:
        r33 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r33;
        if (r14 < r0) goto L_0x01ba;
    L_0x0106:
        r15 = 4;
    L_0x0107:
        r0 = r39;
        r0 = r0.expDigits;
        r33 = r0;
        r0 = r33;
        if (r0 <= r15) goto L_0x0115;
    L_0x0111:
        r0 = r39;
        r15 = r0.expDigits;
    L_0x0115:
        r30 = 1;
        r0 = r39;
        r0 = r0.general;
        r33 = r0;
        if (r33 != 0) goto L_0x01cf;
    L_0x011f:
        r12 = 0;
    L_0x0120:
        if (r6 >= 0) goto L_0x01e4;
    L_0x0122:
        r16 = 1;
    L_0x0124:
        r0 = r39;
        r0 = r0.general;
        r33 = r0;
        if (r33 != 0) goto L_0x012e;
    L_0x012c:
        if (r16 == 0) goto L_0x0152;
    L_0x012e:
        r22 = r10 - r29;
        if (r16 == 0) goto L_0x013f;
    L_0x0132:
        r33 = 7;
        r0 = r22;
        r1 = r33;
        if (r0 >= r1) goto L_0x01e8;
    L_0x013a:
        r6 = r22;
    L_0x013c:
        if (r10 <= r6) goto L_0x013f;
    L_0x013e:
        r6 = r10;
    L_0x013f:
        r7 = r6 - r22;
        r0 = r39;
        r0 = r0.general;
        r33 = r0;
        if (r33 == 0) goto L_0x01eb;
    L_0x0149:
        if (r22 < 0) goto L_0x01eb;
    L_0x014b:
        if (r7 < 0) goto L_0x01eb;
    L_0x014d:
        r10 = r6;
        r20 = r22;
        r30 = 0;
    L_0x0152:
        r8 = r9 + r10;
    L_0x0154:
        r33 = r43.length();
        r0 = r33;
        if (r0 >= r8) goto L_0x0210;
    L_0x015c:
        r33 = 48;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
        goto L_0x0154;
    L_0x0166:
        r13 = gnu.math.RealNum.toStringScientific(r42, r43);
        goto L_0x00bf;
    L_0x016c:
        if (r20 <= 0) goto L_0x01ad;
    L_0x016e:
        r33 = 1;
    L_0x0170:
        r10 = r6 + r33;
        r34 = java.lang.Math.log(r40);
        r36 = LOG10;
        r34 = r34 / r36;
        r36 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        r34 = r34 + r36;
        r0 = r34;
        r0 = (int) r0;
        r21 = r0;
        r33 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r21;
        r1 = r33;
        if (r0 != r1) goto L_0x01b0;
    L_0x018e:
        r21 = 0;
    L_0x0190:
        r33 = r10 - r21;
        r29 = r33 + -1;
        r0 = r40;
        r2 = r29;
        r33 = gnu.math.RealNum.toScaledInt(r0, r2);
        r34 = 10;
        r0 = r33;
        r1 = r34;
        r2 = r43;
        r0.format(r1, r2);
        r33 = r10 + -1;
        r13 = r33 - r29;
        goto L_0x00f9;
    L_0x01ad:
        r33 = r20;
        goto L_0x0170;
    L_0x01b0:
        r0 = r21;
        r0 = r0 + -1000;
        r21 = r0;
        goto L_0x0190;
    L_0x01b7:
        r14 = r13;
        goto L_0x0100;
    L_0x01ba:
        r33 = 100;
        r0 = r33;
        if (r14 < r0) goto L_0x01c3;
    L_0x01c0:
        r15 = 3;
        goto L_0x0107;
    L_0x01c3:
        r33 = 10;
        r0 = r33;
        if (r14 < r0) goto L_0x01cc;
    L_0x01c9:
        r15 = 2;
        goto L_0x0107;
    L_0x01cc:
        r15 = 1;
        goto L_0x0107;
    L_0x01cf:
        r0 = r39;
        r0 = r0.expDigits;
        r33 = r0;
        if (r33 <= 0) goto L_0x01e1;
    L_0x01d7:
        r0 = r39;
        r0 = r0.expDigits;
        r33 = r0;
        r12 = r33 + 2;
        goto L_0x0120;
    L_0x01e1:
        r12 = 4;
        goto L_0x0120;
    L_0x01e4:
        r16 = 0;
        goto L_0x0124;
    L_0x01e8:
        r6 = 7;
        goto L_0x013c;
    L_0x01eb:
        if (r16 == 0) goto L_0x0152;
    L_0x01ed:
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        if (r33 > 0) goto L_0x01fb;
    L_0x01f5:
        r10 = r6;
    L_0x01f6:
        if (r10 > 0) goto L_0x0152;
    L_0x01f8:
        r10 = 1;
        goto L_0x0152;
    L_0x01fb:
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        r33 = r33 - r31;
        r33 = r33 - r15;
        r5 = r33 + -3;
        r10 = r5;
        if (r20 >= 0) goto L_0x020c;
    L_0x020a:
        r10 = r10 - r20;
    L_0x020c:
        if (r10 <= r6) goto L_0x01f6;
    L_0x020e:
        r10 = r6;
        goto L_0x01f6;
    L_0x0210:
        r33 = r43.length();
        r0 = r33;
        if (r8 != r0) goto L_0x024f;
    L_0x0218:
        r26 = 48;
    L_0x021a:
        r33 = 53;
        r0 = r26;
        r1 = r33;
        if (r0 < r1) goto L_0x0256;
    L_0x0222:
        r4 = 1;
    L_0x0223:
        if (r4 == 0) goto L_0x022f;
    L_0x0225:
        r0 = r43;
        r33 = addOne(r0, r9, r8);
        if (r33 == 0) goto L_0x022f;
    L_0x022d:
        r29 = r29 + 1;
    L_0x022f:
        r33 = r43.length();
        r33 = r33 - r8;
        r29 = r29 - r33;
        r0 = r43;
        r0.setLength(r8);
        r11 = r9;
        if (r20 >= 0) goto L_0x0258;
    L_0x023f:
        r19 = r20;
    L_0x0241:
        r19 = r19 + 1;
        if (r19 > 0) goto L_0x026c;
    L_0x0245:
        r33 = 48;
        r0 = r43;
        r1 = r33;
        r0.insert(r9, r1);
        goto L_0x0241;
    L_0x024f:
        r0 = r43;
        r26 = r0.charAt(r8);
        goto L_0x021a;
    L_0x0256:
        r4 = 0;
        goto L_0x0223;
    L_0x0258:
        r33 = r9 + r20;
        r0 = r33;
        if (r0 <= r8) goto L_0x026a;
    L_0x025e:
        r33 = 48;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
        r8 = r8 + 1;
        goto L_0x0258;
    L_0x026a:
        r11 = r11 + r20;
    L_0x026c:
        if (r27 == 0) goto L_0x02bf;
    L_0x026e:
        r30 = 0;
    L_0x0270:
        if (r30 == 0) goto L_0x02cc;
    L_0x0272:
        r0 = r39;
        r0 = r0.exponentChar;
        r33 = r0;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
        r0 = r39;
        r0 = r0.exponentShowSign;
        r33 = r0;
        if (r33 != 0) goto L_0x0289;
    L_0x0287:
        if (r13 >= 0) goto L_0x0294;
    L_0x0289:
        if (r13 < 0) goto L_0x02c9;
    L_0x028b:
        r33 = 43;
    L_0x028d:
        r0 = r43;
        r1 = r33;
        r0.append(r1);
    L_0x0294:
        r17 = r43.length();
        r0 = r43;
        r0.append(r14);
        r25 = r43.length();
        r0 = r39;
        r0 = r0.expDigits;
        r33 = r0;
        r34 = r25 - r17;
        r19 = r33 - r34;
        if (r19 <= 0) goto L_0x02cd;
    L_0x02ad:
        r25 = r25 + r19;
    L_0x02af:
        r19 = r19 + -1;
        if (r19 < 0) goto L_0x02cd;
    L_0x02b3:
        r33 = 48;
        r0 = r43;
        r1 = r17;
        r2 = r33;
        r0.insert(r1, r2);
        goto L_0x02af;
    L_0x02bf:
        r33 = 46;
        r0 = r43;
        r1 = r33;
        r0.insert(r11, r1);
        goto L_0x0270;
    L_0x02c9:
        r33 = 45;
        goto L_0x028d;
    L_0x02cc:
        r15 = 0;
    L_0x02cd:
        r25 = r43.length();
        r32 = r25 - r28;
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        r17 = r33 - r32;
        if (r16 == 0) goto L_0x0318;
    L_0x02dd:
        r33 = r11 + 1;
        r34 = r43.length();
        r0 = r33;
        r1 = r34;
        if (r0 == r1) goto L_0x02ff;
    L_0x02e9:
        r33 = r11 + 1;
        r0 = r43;
        r1 = r33;
        r33 = r0.charAt(r1);
        r0 = r39;
        r0 = r0.exponentChar;
        r34 = r0;
        r0 = r33;
        r1 = r34;
        if (r0 != r1) goto L_0x0318;
    L_0x02ff:
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        if (r33 <= 0) goto L_0x0309;
    L_0x0307:
        if (r17 <= 0) goto L_0x0318;
    L_0x0309:
        r17 = r17 + -1;
        r33 = r11 + 1;
        r34 = 48;
        r0 = r43;
        r1 = r33;
        r2 = r34;
        r0.insert(r1, r2);
    L_0x0318:
        if (r17 >= 0) goto L_0x0322;
    L_0x031a:
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        if (r33 > 0) goto L_0x0383;
    L_0x0322:
        if (r30 == 0) goto L_0x033e;
    L_0x0324:
        r0 = r39;
        r0 = r0.expDigits;
        r33 = r0;
        r0 = r33;
        if (r15 <= r0) goto L_0x033e;
    L_0x032e:
        r0 = r39;
        r0 = r0.expDigits;
        r33 = r0;
        if (r33 <= 0) goto L_0x033e;
    L_0x0336:
        r0 = r39;
        r0 = r0.overflowChar;
        r33 = r0;
        if (r33 != 0) goto L_0x0383;
    L_0x033e:
        if (r20 > 0) goto L_0x0355;
    L_0x0340:
        if (r17 > 0) goto L_0x034a;
    L_0x0342:
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        if (r33 > 0) goto L_0x0355;
    L_0x034a:
        r33 = 48;
        r0 = r43;
        r1 = r33;
        r0.insert(r9, r1);
        r17 = r17 + -1;
    L_0x0355:
        if (r30 != 0) goto L_0x036f;
    L_0x0357:
        r0 = r39;
        r0 = r0.width;
        r33 = r0;
        if (r33 <= 0) goto L_0x036f;
    L_0x035f:
        r12 = r12 + -1;
        if (r12 < 0) goto L_0x036f;
    L_0x0363:
        r33 = 32;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
        r17 = r17 + -1;
        goto L_0x035f;
    L_0x036f:
        r17 = r17 + -1;
        if (r17 < 0) goto L_0x03aa;
    L_0x0373:
        r0 = r39;
        r0 = r0.padChar;
        r33 = r0;
        r0 = r43;
        r1 = r28;
        r2 = r33;
        r0.insert(r1, r2);
        goto L_0x036f;
    L_0x0383:
        r0 = r39;
        r0 = r0.overflowChar;
        r33 = r0;
        if (r33 == 0) goto L_0x03aa;
    L_0x038b:
        r0 = r43;
        r1 = r28;
        r0.setLength(r1);
        r0 = r39;
        r0 = r0.width;
        r17 = r0;
    L_0x0398:
        r17 = r17 + -1;
        if (r17 < 0) goto L_0x03aa;
    L_0x039c:
        r0 = r39;
        r0 = r0.overflowChar;
        r33 = r0;
        r0 = r43;
        r1 = r33;
        r0.append(r1);
        goto L_0x0398;
    L_0x03aa:
        return r43;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.math.ExponentialFormat.format(double, java.lang.String, java.lang.StringBuffer, java.text.FieldPosition):java.lang.StringBuffer");
    }

    public StringBuffer format(long num, StringBuffer sbuf, FieldPosition fpos) {
        return format((double) num, sbuf, fpos);
    }

    public StringBuffer format(Object num, StringBuffer sbuf, FieldPosition fpos) {
        return format(((RealNum) num).doubleValue(), sbuf, fpos);
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parse - not implemented");
    }

    public Object parseObject(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parseObject - not implemented");
    }
}
