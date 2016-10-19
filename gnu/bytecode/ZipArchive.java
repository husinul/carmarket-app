package gnu.bytecode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZipArchive {
    private static void usage() {
        System.err.println("zipfile [ptxq] archive [file ...]");
        System.exit(-1);
    }

    public static long copy(InputStream in, OutputStream out, byte[] buffer) throws IOException {
        long total = 0;
        while (true) {
            int count = in.read(buffer);
            if (count <= 0) {
                return total;
            }
            out.write(buffer, 0, count);
            total += (long) count;
        }
    }

    public static void copy(InputStream in, String name, byte[] buffer) throws IOException {
        File f = new File(name);
        String dir_name = f.getParent();
        if (dir_name != null) {
            File dir = new File(dir_name);
            if (!dir.exists()) {
                System.err.println("mkdirs:" + dir.mkdirs());
            }
        }
        if (name.charAt(name.length() - 1) != '/') {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
            copy(in, out, buffer);
            out.close();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void main(java.lang.String[] r20) throws java.io.IOException {
        /*
        r0 = r20;
        r0 = r0.length;
        r17 = r0;
        r18 = 2;
        r0 = r17;
        r1 = r18;
        if (r0 >= r1) goto L_0x0010;
    L_0x000d:
        usage();
    L_0x0010:
        r17 = 0;
        r4 = r20[r17];
        r17 = 1;
        r2 = r20[r17];
        r17 = "t";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 != 0) goto L_0x0036;
    L_0x0022:
        r17 = "p";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 != 0) goto L_0x0036;
    L_0x002c:
        r17 = "x";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 == 0) goto L_0x0141;
    L_0x0036:
        r11 = java.lang.System.out;	 Catch:{ IOException -> 0x0088 }
        r17 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r17;
        r3 = new byte[r0];	 Catch:{ IOException -> 0x0088 }
        r0 = r20;
        r0 = r0.length;	 Catch:{ IOException -> 0x0088 }
        r17 = r0;
        r18 = 2;
        r0 = r17;
        r1 = r18;
        if (r0 != r1) goto L_0x00ba;
    L_0x004b:
        r9 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x0088 }
        r17 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        r0.<init>(r2);	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        r9.<init>(r0);	 Catch:{ IOException -> 0x0088 }
        r16 = new java.util.zip.ZipInputStream;	 Catch:{ IOException -> 0x0088 }
        r0 = r16;
        r0.<init>(r9);	 Catch:{ IOException -> 0x0088 }
    L_0x0060:
        r15 = r16.getNextEntry();	 Catch:{ IOException -> 0x0088 }
        if (r15 == 0) goto L_0x00a3;
    L_0x0066:
        r10 = r15.getName();	 Catch:{ IOException -> 0x0088 }
        r17 = "t";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 == 0) goto L_0x00a4;
    L_0x0074:
        r11.print(r10);	 Catch:{ IOException -> 0x0088 }
        r17 = " size: ";
        r0 = r17;
        r11.print(r0);	 Catch:{ IOException -> 0x0088 }
        r18 = r15.getSize();	 Catch:{ IOException -> 0x0088 }
        r0 = r18;
        r11.println(r0);	 Catch:{ IOException -> 0x0088 }
        goto L_0x0060;
    L_0x0088:
        r6 = move-exception;
        r17 = java.lang.System.err;
        r18 = new java.lang.StringBuilder;
        r18.<init>();
        r19 = "I/O Exception:  ";
        r18 = r18.append(r19);
        r0 = r18;
        r18 = r0.append(r6);
        r18 = r18.toString();
        r17.println(r18);
    L_0x00a3:
        return;
    L_0x00a4:
        r17 = "p";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 == 0) goto L_0x00b4;
    L_0x00ae:
        r0 = r16;
        copy(r0, r11, r3);	 Catch:{ IOException -> 0x0088 }
        goto L_0x0060;
    L_0x00b4:
        r0 = r16;
        copy(r0, r10, r3);	 Catch:{ IOException -> 0x0088 }
        goto L_0x0060;
    L_0x00ba:
        r13 = new java.util.zip.ZipFile;	 Catch:{ IOException -> 0x0088 }
        r13.<init>(r2);	 Catch:{ IOException -> 0x0088 }
        r8 = 2;
    L_0x00c0:
        r0 = r20;
        r0 = r0.length;	 Catch:{ IOException -> 0x0088 }
        r17 = r0;
        r0 = r17;
        if (r8 >= r0) goto L_0x00a3;
    L_0x00c9:
        r10 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r15 = r13.getEntry(r10);	 Catch:{ IOException -> 0x0088 }
        if (r15 != 0) goto L_0x0105;
    L_0x00d1:
        r17 = java.lang.System.err;	 Catch:{ IOException -> 0x0088 }
        r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0088 }
        r18.<init>();	 Catch:{ IOException -> 0x0088 }
        r19 = "zipfile ";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r0 = r18;
        r18 = r0.append(r2);	 Catch:{ IOException -> 0x0088 }
        r19 = ":";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r19 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r19 = " - not found";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r18 = r18.toString();	 Catch:{ IOException -> 0x0088 }
        r17.println(r18);	 Catch:{ IOException -> 0x0088 }
        r17 = -1;
        java.lang.System.exit(r17);	 Catch:{ IOException -> 0x0088 }
    L_0x0102:
        r8 = r8 + 1;
        goto L_0x00c0;
    L_0x0105:
        r17 = "t";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 == 0) goto L_0x0123;
    L_0x010f:
        r11.print(r10);	 Catch:{ IOException -> 0x0088 }
        r17 = " size: ";
        r0 = r17;
        r11.print(r0);	 Catch:{ IOException -> 0x0088 }
        r18 = r15.getSize();	 Catch:{ IOException -> 0x0088 }
        r0 = r18;
        r11.println(r0);	 Catch:{ IOException -> 0x0088 }
        goto L_0x0102;
    L_0x0123:
        r17 = "p";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 == 0) goto L_0x0137;
    L_0x012d:
        r17 = r13.getInputStream(r15);	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        copy(r0, r11, r3);	 Catch:{ IOException -> 0x0088 }
        goto L_0x0102;
    L_0x0137:
        r17 = r13.getInputStream(r15);	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        copy(r0, r10, r3);	 Catch:{ IOException -> 0x0088 }
        goto L_0x0102;
    L_0x0141:
        r17 = "q";
        r0 = r17;
        r17 = r4.equals(r0);	 Catch:{ IOException -> 0x0088 }
        if (r17 == 0) goto L_0x020f;
    L_0x014b:
        r13 = new java.util.zip.ZipOutputStream;	 Catch:{ IOException -> 0x0088 }
        r17 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        r0.<init>(r2);	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        r13.<init>(r0);	 Catch:{ IOException -> 0x0088 }
        r8 = 2;
    L_0x015a:
        r0 = r20;
        r0 = r0.length;	 Catch:{ IOException -> 0x0088 }
        r17 = r0;
        r0 = r17;
        if (r8 >= r0) goto L_0x020a;
    L_0x0163:
        r9 = new java.io.File;	 Catch:{ IOException -> 0x0088 }
        r17 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        r9.<init>(r0);	 Catch:{ IOException -> 0x0088 }
        r17 = r9.exists();	 Catch:{ IOException -> 0x0088 }
        if (r17 != 0) goto L_0x018d;
    L_0x0172:
        r17 = new java.io.IOException;	 Catch:{ IOException -> 0x0088 }
        r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0088 }
        r18.<init>();	 Catch:{ IOException -> 0x0088 }
        r19 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r19 = " - not found";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r18 = r18.toString();	 Catch:{ IOException -> 0x0088 }
        r17.<init>(r18);	 Catch:{ IOException -> 0x0088 }
        throw r17;	 Catch:{ IOException -> 0x0088 }
    L_0x018d:
        r17 = r9.canRead();	 Catch:{ IOException -> 0x0088 }
        if (r17 != 0) goto L_0x01ae;
    L_0x0193:
        r17 = new java.io.IOException;	 Catch:{ IOException -> 0x0088 }
        r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0088 }
        r18.<init>();	 Catch:{ IOException -> 0x0088 }
        r19 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r19 = " - not readable";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r18 = r18.toString();	 Catch:{ IOException -> 0x0088 }
        r17.<init>(r18);	 Catch:{ IOException -> 0x0088 }
        throw r17;	 Catch:{ IOException -> 0x0088 }
    L_0x01ae:
        r18 = r9.length();	 Catch:{ IOException -> 0x0088 }
        r0 = r18;
        r12 = (int) r0;	 Catch:{ IOException -> 0x0088 }
        r7 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0088 }
        r7.<init>(r9);	 Catch:{ IOException -> 0x0088 }
        r5 = new byte[r12];	 Catch:{ IOException -> 0x0088 }
        r17 = r7.read(r5);	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        if (r0 == r12) goto L_0x01df;
    L_0x01c4:
        r17 = new java.io.IOException;	 Catch:{ IOException -> 0x0088 }
        r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0088 }
        r18.<init>();	 Catch:{ IOException -> 0x0088 }
        r19 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r19 = " - read error";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0088 }
        r18 = r18.toString();	 Catch:{ IOException -> 0x0088 }
        r17.<init>(r18);	 Catch:{ IOException -> 0x0088 }
        throw r17;	 Catch:{ IOException -> 0x0088 }
    L_0x01df:
        r7.close();	 Catch:{ IOException -> 0x0088 }
        r14 = new java.util.zip.ZipEntry;	 Catch:{ IOException -> 0x0088 }
        r17 = r20[r8];	 Catch:{ IOException -> 0x0088 }
        r0 = r17;
        r14.<init>(r0);	 Catch:{ IOException -> 0x0088 }
        r0 = (long) r12;	 Catch:{ IOException -> 0x0088 }
        r18 = r0;
        r0 = r18;
        r14.setSize(r0);	 Catch:{ IOException -> 0x0088 }
        r18 = r9.lastModified();	 Catch:{ IOException -> 0x0088 }
        r0 = r18;
        r14.setTime(r0);	 Catch:{ IOException -> 0x0088 }
        r13.putNextEntry(r14);	 Catch:{ IOException -> 0x0088 }
        r17 = 0;
        r0 = r17;
        r13.write(r5, r0, r12);	 Catch:{ IOException -> 0x0088 }
        r8 = r8 + 1;
        goto L_0x015a;
    L_0x020a:
        r13.close();	 Catch:{ IOException -> 0x0088 }
        goto L_0x00a3;
    L_0x020f:
        usage();	 Catch:{ IOException -> 0x0088 }
        goto L_0x00a3;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.ZipArchive.main(java.lang.String[]):void");
    }
}
