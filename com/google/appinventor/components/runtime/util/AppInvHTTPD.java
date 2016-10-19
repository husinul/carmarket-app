package com.google.appinventor.components.runtime.util;

import android.os.Handler;
import com.google.appinventor.components.runtime.ReplForm;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppInvHTTPD extends NanoHTTPD {
    private static final String LOG_TAG = "AppInvHTTPD";
    private static final String MIME_JSON = "application/json";
    private static final int YAV_SKEW_BACKWARD = 4;
    private static final int YAV_SKEW_FORWARD = 1;
    private static byte[] hmacKey;
    private static int seq;
    private final Handler androidUIHandler;
    private ReplForm form;
    private File rootDir;
    private Language scheme;
    private boolean secure;

    /* renamed from: com.google.appinventor.components.runtime.util.AppInvHTTPD.1 */
    class C01361 implements Runnable {
        C01361() {
        }

        public void run() {
            AppInvHTTPD.this.form.clear();
        }
    }

    public AppInvHTTPD(int port, File wwwroot, boolean secure, ReplForm form) throws IOException {
        super(port, wwwroot);
        this.androidUIHandler = new Handler();
        this.rootDir = wwwroot;
        this.scheme = Language.getInstance("scheme");
        this.form = form;
        this.secure = secure;
        ModuleExp.mustNeverCompile();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.appinventor.components.runtime.util.NanoHTTPD.Response serve(java.lang.String r52, java.lang.String r53, java.util.Properties r54, java.util.Properties r55, java.util.Properties r56, java.net.Socket r57) {
        /*
        r51 = this;
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r0 = r45;
        r1 = r53;
        r45 = r0.append(r1);
        r46 = " '";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r52;
        r45 = r0.append(r1);
        r46 = "' ";
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r0 = r51;
        r0 = r0.secure;
        r44 = r0;
        if (r44 == 0) goto L_0x00cd;
    L_0x0032:
        r29 = r57.getInetAddress();
        r19 = r29.getHostAddress();
        r44 = "127.0.0.1";
        r0 = r19;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 != 0) goto L_0x00cd;
    L_0x0046:
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Debug: hostAddress = ";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r19;
        r45 = r0.append(r1);
        r46 = " while in secure mode, closing connection.";
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = new java.lang.StringBuilder;
        r46.<init>();
        r47 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Source Location ";
        r46 = r46.append(r47);
        r0 = r46;
        r1 = r19;
        r46 = r0.append(r1);
        r47 = "\"}";
        r46 = r46.append(r47);
        r46 = r46.toString();
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
    L_0x00cc:
        return r36;
    L_0x00cd:
        r44 = "OPTIONS";
        r0 = r53;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x0169;
    L_0x00d9:
        r10 = r54.propertyNames();
    L_0x00dd:
        r44 = r10.hasMoreElements();
        if (r44 == 0) goto L_0x011e;
    L_0x00e3:
        r42 = r10.nextElement();
        r42 = (java.lang.String) r42;
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "  HDR: '";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r42;
        r45 = r0.append(r1);
        r46 = "' = '";
        r45 = r45.append(r46);
        r0 = r54;
        r1 = r42;
        r46 = r0.getProperty(r1);
        r45 = r45.append(r46);
        r46 = "'";
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        goto L_0x00dd;
    L_0x011e:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "OK";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0169:
        r44 = "/_newblocks";
        r0 = r52;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x04e3;
    L_0x0175:
        r44 = "seq";
        r45 = "0";
        r0 = r55;
        r1 = r44;
        r2 = r45;
        r22 = r0.getProperty(r1, r2);
        r26 = java.lang.Integer.parseInt(r22);
        r44 = "blockid";
        r0 = r55;
        r1 = r44;
        r7 = r0.getProperty(r1);
        r44 = "code";
        r0 = r55;
        r1 = r44;
        r8 = r0.getProperty(r1);
        r44 = "mac";
        r45 = "no key provided";
        r0 = r55;
        r1 = r44;
        r2 = r45;
        r21 = r0.getProperty(r1, r2);
        r9 = "";
        r23 = r8;
        r44 = hmacKey;
        if (r44 == 0) goto L_0x0463;
    L_0x01b1:
        r44 = "HmacSHA1";
        r18 = javax.crypto.Mac.getInstance(r44);	 Catch:{ Exception -> 0x030d }
        r27 = new javax.crypto.spec.SecretKeySpec;	 Catch:{ Exception -> 0x030d }
        r44 = hmacKey;	 Catch:{ Exception -> 0x030d }
        r45 = "RAW";
        r0 = r27;
        r1 = r44;
        r2 = r45;
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x030d }
        r0 = r18;
        r1 = r27;
        r0.init(r1);	 Catch:{ Exception -> 0x030d }
        r44 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x030d }
        r44.<init>();	 Catch:{ Exception -> 0x030d }
        r0 = r44;
        r44 = r0.append(r8);	 Catch:{ Exception -> 0x030d }
        r0 = r44;
        r1 = r22;
        r44 = r0.append(r1);	 Catch:{ Exception -> 0x030d }
        r0 = r44;
        r44 = r0.append(r7);	 Catch:{ Exception -> 0x030d }
        r44 = r44.toString();	 Catch:{ Exception -> 0x030d }
        r44 = r44.getBytes();	 Catch:{ Exception -> 0x030d }
        r0 = r18;
        r1 = r44;
        r40 = r0.doFinal(r1);	 Catch:{ Exception -> 0x030d }
        r37 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x030d }
        r0 = r40;
        r0 = r0.length;	 Catch:{ Exception -> 0x030d }
        r44 = r0;
        r44 = r44 * 2;
        r0 = r37;
        r1 = r44;
        r0.<init>(r1);	 Catch:{ Exception -> 0x030d }
        r17 = new java.util.Formatter;	 Catch:{ Exception -> 0x030d }
        r0 = r17;
        r1 = r37;
        r0.<init>(r1);	 Catch:{ Exception -> 0x030d }
        r5 = r40;
        r0 = r5.length;	 Catch:{ Exception -> 0x030d }
        r28 = r0;
        r20 = 0;
    L_0x0216:
        r0 = r20;
        r1 = r28;
        if (r0 >= r1) goto L_0x023c;
    L_0x021c:
        r6 = r5[r20];	 Catch:{ Exception -> 0x030d }
        r44 = "%02x";
        r45 = 1;
        r0 = r45;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x030d }
        r45 = r0;
        r46 = 0;
        r47 = java.lang.Byte.valueOf(r6);	 Catch:{ Exception -> 0x030d }
        r45[r46] = r47;	 Catch:{ Exception -> 0x030d }
        r0 = r17;
        r1 = r44;
        r2 = r45;
        r0.format(r1, r2);	 Catch:{ Exception -> 0x030d }
        r20 = r20 + 1;
        goto L_0x0216;
    L_0x023c:
        r9 = r37.toString();	 Catch:{ Exception -> 0x030d }
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Incoming Mac = ";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r21;
        r45 = r0.append(r1);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Computed Mac = ";
        r45 = r45.append(r46);
        r0 = r45;
        r45 = r0.append(r9);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Incoming seq = ";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r22;
        r45 = r0.append(r1);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Computed seq = ";
        r45 = r45.append(r46);
        r46 = seq;
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "blockid = ";
        r45 = r45.append(r46);
        r0 = r45;
        r45 = r0.append(r7);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r0 = r21;
        r44 = r0.equals(r9);
        if (r44 != 0) goto L_0x0351;
    L_0x02ce:
        r44 = "AppInvHTTPD";
        r45 = "Hmac does not match";
        android.util.Log.e(r44, r45);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r51;
        r0 = r0.form;
        r45 = r0;
        r46 = "AppInvHTTPD";
        r47 = 1801; // 0x709 float:2.524E-42 double:8.9E-321;
        r48 = 1;
        r0 = r48;
        r0 = new java.lang.Object[r0];
        r48 = r0;
        r49 = 0;
        r50 = "Invalid HMAC";
        r48[r49] = r50;
        r44.dispatchErrorOccurredEvent(r45, r46, r47, r48);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid MAC\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x00cc;
    L_0x030d:
        r10 = move-exception;
        r44 = "AppInvHTTPD";
        r45 = "Error working with hmac";
        r0 = r44;
        r1 = r45;
        android.util.Log.e(r0, r1, r10);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r51;
        r0 = r0.form;
        r45 = r0;
        r46 = "AppInvHTTPD";
        r47 = 1801; // 0x709 float:2.524E-42 double:8.9E-321;
        r48 = 1;
        r0 = r48;
        r0 = new java.lang.Object[r0];
        r48 = r0;
        r49 = 0;
        r50 = "Exception working on HMAC";
        r48[r49] = r50;
        r44.dispatchErrorOccurredEvent(r45, r46, r47, r48);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "NOT";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x00cc;
    L_0x0351:
        r44 = seq;
        r0 = r44;
        r1 = r26;
        if (r0 == r1) goto L_0x03a2;
    L_0x0359:
        r44 = seq;
        r45 = r26 + 1;
        r0 = r44;
        r1 = r45;
        if (r0 == r1) goto L_0x03a2;
    L_0x0363:
        r44 = "AppInvHTTPD";
        r45 = "Seq does not match";
        android.util.Log.e(r44, r45);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r51;
        r0 = r0.form;
        r45 = r0;
        r46 = "AppInvHTTPD";
        r47 = 1801; // 0x709 float:2.524E-42 double:8.9E-321;
        r48 = 1;
        r0 = r48;
        r0 = new java.lang.Object[r0];
        r48 = r0;
        r49 = 0;
        r50 = "Invalid Seq";
        r48[r49] = r50;
        r44.dispatchErrorOccurredEvent(r45, r46, r47, r48);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Seq\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x00cc;
    L_0x03a2:
        r44 = seq;
        r45 = r26 + 1;
        r0 = r44;
        r1 = r45;
        if (r0 != r1) goto L_0x03b3;
    L_0x03ac:
        r44 = "AppInvHTTPD";
        r45 = "Seq Fixup Invoked";
        android.util.Log.e(r44, r45);
    L_0x03b3:
        r44 = r26 + 1;
        seq = r44;
        r44 = new java.lang.StringBuilder;
        r44.<init>();
        r45 = "(begin (require <com.google.youngandroid.runtime>) (process-repl-input ";
        r44 = r44.append(r45);
        r0 = r44;
        r44 = r0.append(r7);
        r45 = " (begin ";
        r44 = r44.append(r45);
        r0 = r44;
        r44 = r0.append(r8);
        r45 = " )))";
        r44 = r44.append(r45);
        r8 = r44.toString();
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "To Eval: ";
        r45 = r45.append(r46);
        r0 = r45;
        r45 = r0.append(r8);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r44.loadComponents();
        r44 = "#f";
        r0 = r23;
        r1 = r44;
        r44 = r0.equals(r1);	 Catch:{ Throwable -> 0x04af }
        if (r44 == 0) goto L_0x04a2;
    L_0x040d:
        r44 = "AppInvHTTPD";
        r45 = "Skipping evaluation of #f";
        android.util.Log.e(r44, r45);	 Catch:{ Throwable -> 0x04af }
    L_0x0414:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;	 Catch:{ Throwable -> 0x04af }
        r44 = "200 OK";
        r45 = "application/json";
        r46 = 0;
        r46 = com.google.appinventor.components.runtime.util.RetValManager.fetch(r46);	 Catch:{ Throwable -> 0x04af }
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);	 Catch:{ Throwable -> 0x04af }
    L_0x042d:
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0463:
        r44 = "AppInvHTTPD";
        r45 = "No HMAC Key";
        android.util.Log.e(r44, r45);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r51;
        r0 = r0.form;
        r45 = r0;
        r46 = "AppInvHTTPD";
        r47 = 1801; // 0x709 float:2.524E-42 double:8.9E-321;
        r48 = 1;
        r0 = r48;
        r0 = new java.lang.Object[r0];
        r48 = r0;
        r49 = 0;
        r50 = "No HMAC Key";
        r48[r49] = r50;
        r44.dispatchErrorOccurredEvent(r45, r46, r47, r48);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: No HMAC Key\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x00cc;
    L_0x04a2:
        r0 = r51;
        r0 = r0.scheme;	 Catch:{ Throwable -> 0x04af }
        r44 = r0;
        r0 = r44;
        r0.eval(r8);	 Catch:{ Throwable -> 0x04af }
        goto L_0x0414;
    L_0x04af:
        r12 = move-exception;
        r44 = "AppInvHTTPD";
        r45 = "newblocks: Scheme Failure";
        r0 = r44;
        r1 = r45;
        android.util.Log.e(r0, r1, r12);
        r44 = "BAD";
        r45 = r12.toString();
        r0 = r44;
        r1 = r45;
        com.google.appinventor.components.runtime.util.RetValManager.appendReturnValue(r7, r0, r1);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = 0;
        r46 = com.google.appinventor.components.runtime.util.RetValManager.fetch(r46);
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x042d;
    L_0x04e3:
        r44 = "/_values";
        r0 = r52;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x053e;
    L_0x04ef:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = 1;
        r46 = com.google.appinventor.components.runtime.util.RetValManager.fetch(r46);
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x053e:
        r44 = "/_getversion";
        r0 = r52;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x065c;
    L_0x054a:
        r0 = r51;
        r0 = r0.form;	 Catch:{ NameNotFoundException -> 0x0642 }
        r44 = r0;
        r32 = r44.getPackageName();	 Catch:{ NameNotFoundException -> 0x0642 }
        r0 = r51;
        r0 = r0.form;	 Catch:{ NameNotFoundException -> 0x0642 }
        r44 = r0;
        r44 = r44.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0642 }
        r45 = 0;
        r0 = r44;
        r1 = r32;
        r2 = r45;
        r31 = r0.getPackageInfo(r1, r2);	 Catch:{ NameNotFoundException -> 0x0642 }
        r44 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel();	 Catch:{ NameNotFoundException -> 0x0642 }
        r45 = 5;
        r0 = r44;
        r1 = r45;
        if (r0 < r1) goto L_0x063e;
    L_0x0576:
        r44 = "edu.mit.appinventor.aicompanion3";
        r0 = r51;
        r0 = r0.form;	 Catch:{ NameNotFoundException -> 0x0642 }
        r45 = r0;
        r24 = com.google.appinventor.components.runtime.util.EclairUtil.getInstallerPackageName(r44, r45);	 Catch:{ NameNotFoundException -> 0x0642 }
    L_0x0582:
        r0 = r31;
        r0 = r0.versionName;	 Catch:{ NameNotFoundException -> 0x0642 }
        r43 = r0;
        if (r24 != 0) goto L_0x058c;
    L_0x058a:
        r24 = "Not Known";
    L_0x058c:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;	 Catch:{ NameNotFoundException -> 0x0642 }
        r44 = "200 OK";
        r45 = "application/json";
        r46 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0642 }
        r46.<init>();	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = "{\"version\" : \"";
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r0 = r46;
        r1 = r43;
        r46 = r0.append(r1);	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = "\", \"fingerprint\" : \"";
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = android.os.Build.FINGERPRINT;	 Catch:{ NameNotFoundException -> 0x0642 }
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = "\",";
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = " \"installer\" : \"";
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r0 = r46;
        r1 = r24;
        r46 = r0.append(r1);	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = "\", \"package\" : \"";
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r0 = r46;
        r1 = r32;
        r46 = r0.append(r1);	 Catch:{ NameNotFoundException -> 0x0642 }
        r47 = "\", \"fqcn\" : true }";
        r46 = r46.append(r47);	 Catch:{ NameNotFoundException -> 0x0642 }
        r46 = r46.toString();	 Catch:{ NameNotFoundException -> 0x0642 }
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);	 Catch:{ NameNotFoundException -> 0x0642 }
    L_0x05ea:
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r0 = r51;
        r0 = r0.secure;
        r44 = r0;
        if (r44 == 0) goto L_0x00cc;
    L_0x0626:
        r44 = 1;
        seq = r44;
        r0 = r51;
        r0 = r0.androidUIHandler;
        r44 = r0;
        r45 = new com.google.appinventor.components.runtime.util.AppInvHTTPD$1;
        r0 = r45;
        r1 = r51;
        r0.<init>();
        r44.post(r45);
        goto L_0x00cc;
    L_0x063e:
        r24 = "Not Known";
        goto L_0x0582;
    L_0x0642:
        r30 = move-exception;
        r30.printStackTrace();
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"verison\" : \"Unknown\"";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x05ea;
    L_0x065c:
        r44 = "/_update";
        r0 = r52;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 != 0) goto L_0x0674;
    L_0x0668:
        r44 = "/_install";
        r0 = r52;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x08e4;
    L_0x0674:
        r44 = "url";
        r45 = "";
        r0 = r55;
        r1 = r44;
        r2 = r45;
        r41 = r0.getProperty(r1, r2);
        r44 = "mac";
        r45 = "";
        r0 = r55;
        r1 = r44;
        r2 = r45;
        r21 = r0.getProperty(r1, r2);
        r44 = "";
        r0 = r41;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 != 0) goto L_0x0899;
    L_0x069c:
        r44 = hmacKey;
        if (r44 == 0) goto L_0x0899;
    L_0x06a0:
        r44 = "";
        r0 = r21;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 != 0) goto L_0x0899;
    L_0x06ac:
        r27 = new javax.crypto.spec.SecretKeySpec;	 Catch:{ Exception -> 0x07cf }
        r44 = hmacKey;	 Catch:{ Exception -> 0x07cf }
        r45 = "RAW";
        r0 = r27;
        r1 = r44;
        r2 = r45;
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x07cf }
        r44 = "HmacSHA1";
        r18 = javax.crypto.Mac.getInstance(r44);	 Catch:{ Exception -> 0x07cf }
        r0 = r18;
        r1 = r27;
        r0.init(r1);	 Catch:{ Exception -> 0x07cf }
        r44 = r41.getBytes();	 Catch:{ Exception -> 0x07cf }
        r0 = r18;
        r1 = r44;
        r40 = r0.doFinal(r1);	 Catch:{ Exception -> 0x07cf }
        r37 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x07cf }
        r0 = r40;
        r0 = r0.length;	 Catch:{ Exception -> 0x07cf }
        r44 = r0;
        r44 = r44 * 2;
        r0 = r37;
        r1 = r44;
        r0.<init>(r1);	 Catch:{ Exception -> 0x07cf }
        r17 = new java.util.Formatter;	 Catch:{ Exception -> 0x07cf }
        r0 = r17;
        r1 = r37;
        r0.<init>(r1);	 Catch:{ Exception -> 0x07cf }
        r5 = r40;
        r0 = r5.length;	 Catch:{ Exception -> 0x07cf }
        r28 = r0;
        r20 = 0;
    L_0x06f4:
        r0 = r20;
        r1 = r28;
        if (r0 >= r1) goto L_0x071a;
    L_0x06fa:
        r6 = r5[r20];	 Catch:{ Exception -> 0x07cf }
        r44 = "%02x";
        r45 = 1;
        r0 = r45;
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x07cf }
        r45 = r0;
        r46 = 0;
        r47 = java.lang.Byte.valueOf(r6);	 Catch:{ Exception -> 0x07cf }
        r45[r46] = r47;	 Catch:{ Exception -> 0x07cf }
        r0 = r17;
        r1 = r44;
        r2 = r45;
        r0.format(r1, r2);	 Catch:{ Exception -> 0x07cf }
        r20 = r20 + 1;
        goto L_0x06f4;
    L_0x071a:
        r9 = r37.toString();	 Catch:{ Exception -> 0x07cf }
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Incoming Mac (update) = ";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r21;
        r45 = r0.append(r1);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "Computed Mac (update) = ";
        r45 = r45.append(r46);
        r0 = r45;
        r45 = r0.append(r9);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r0 = r21;
        r44 = r0.equals(r9);
        if (r44 != 0) goto L_0x0847;
    L_0x075c:
        r44 = "AppInvHTTPD";
        r45 = "Hmac does not match";
        android.util.Log.e(r44, r45);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r51;
        r0 = r0.form;
        r45 = r0;
        r46 = "AppInvHTTPD";
        r47 = 1801; // 0x709 float:2.524E-42 double:8.9E-321;
        r48 = 1;
        r0 = r48;
        r0 = new java.lang.Object[r0];
        r48 = r0;
        r49 = 0;
        r50 = "Invalid HMAC (update)";
        r48[r49] = r50;
        r44.dispatchErrorOccurredEvent(r45, r46, r47, r48);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid MAC\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x07cf:
        r10 = move-exception;
        r44 = "AppInvHTTPD";
        r45 = "Error verifying update";
        r0 = r44;
        r1 = r45;
        android.util.Log.e(r0, r1, r10);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r51;
        r0 = r0.form;
        r45 = r0;
        r46 = "AppInvHTTPD";
        r47 = 1801; // 0x709 float:2.524E-42 double:8.9E-321;
        r48 = 1;
        r0 = r48;
        r0 = new java.lang.Object[r0];
        r48 = r0;
        r49 = 0;
        r50 = "Exception working on HMAC for update";
        r48[r49] = r50;
        r44.dispatchErrorOccurredEvent(r45, r46, r47, r48);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"BAD\", \"message\" : \"Security Error: Exception processing MAC\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0847:
        r0 = r51;
        r1 = r41;
        r0.doPackageUpdate(r1);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"OK\", \"message\" : \"Update Should Happen\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0899:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "application/json";
        r46 = "{\"status\" : \"BAD\", \"message\" : \"Missing Parameters\"}";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x08e4:
        r44 = "/_package";
        r0 = r52;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x09d5;
    L_0x08f0:
        r44 = "package";
        r45 = 0;
        r0 = r55;
        r1 = r44;
        r2 = r45;
        r33 = r0.getProperty(r1, r2);
        if (r33 != 0) goto L_0x0917;
    L_0x0900:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "NOT OK";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        goto L_0x00cc;
    L_0x0917:
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r0 = r51;
        r0 = r0.rootDir;
        r46 = r0;
        r45 = r45.append(r46);
        r46 = "/";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r33;
        r45 = r0.append(r1);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r25 = new android.content.Intent;
        r44 = "android.intent.action.VIEW";
        r0 = r25;
        r1 = r44;
        r0.<init>(r1);
        r44 = new java.io.File;
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r0 = r51;
        r0 = r0.rootDir;
        r46 = r0;
        r45 = r45.append(r46);
        r46 = "/";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r33;
        r45 = r0.append(r1);
        r45 = r45.toString();
        r44.<init>(r45);
        r34 = android.net.Uri.fromFile(r44);
        r44 = "application/vnd.android.package-archive";
        r0 = r25;
        r1 = r34;
        r2 = r44;
        r0.setDataAndType(r1, r2);
        r0 = r51;
        r0 = r0.form;
        r44 = r0;
        r0 = r44;
        r1 = r25;
        r0.startActivity(r1);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "OK";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x09d5:
        r44 = "PUT";
        r0 = r53;
        r1 = r44;
        r44 = r0.equals(r1);
        if (r44 == 0) goto L_0x0b50;
    L_0x09e1:
        r44 = 0;
        r11 = java.lang.Boolean.valueOf(r44);
        r44 = "content";
        r45 = 0;
        r0 = r56;
        r1 = r44;
        r2 = r45;
        r39 = r0.getProperty(r1, r2);
        if (r39 == 0) goto L_0x0af7;
    L_0x09f7:
        r14 = new java.io.File;
        r0 = r39;
        r14.<init>(r0);
        r44 = "filename";
        r45 = 0;
        r0 = r55;
        r1 = r44;
        r2 = r45;
        r16 = r0.getProperty(r1, r2);
        if (r16 == 0) goto L_0x0a50;
    L_0x0a0e:
        r44 = "..";
        r0 = r16;
        r1 = r44;
        r44 = r0.startsWith(r1);
        if (r44 != 0) goto L_0x0a32;
    L_0x0a1a:
        r44 = "..";
        r0 = r16;
        r1 = r44;
        r44 = r0.endsWith(r1);
        if (r44 != 0) goto L_0x0a32;
    L_0x0a26:
        r44 = "../";
        r0 = r16;
        r1 = r44;
        r44 = r0.indexOf(r1);
        if (r44 < 0) goto L_0x0a50;
    L_0x0a32:
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = " Ignoring invalid filename: ";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r16;
        r45 = r0.append(r1);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r16 = 0;
    L_0x0a50:
        if (r16 == 0) goto L_0x0ae6;
    L_0x0a52:
        r15 = new java.io.File;
        r44 = new java.lang.StringBuilder;
        r44.<init>();
        r0 = r51;
        r0 = r0.rootDir;
        r45 = r0;
        r44 = r44.append(r45);
        r45 = "/";
        r44 = r44.append(r45);
        r0 = r44;
        r1 = r16;
        r44 = r0.append(r1);
        r44 = r44.toString();
        r0 = r44;
        r15.<init>(r0);
        r35 = r15.getParentFile();
        r44 = r35.exists();
        if (r44 != 0) goto L_0x0a87;
    L_0x0a84:
        r35.mkdirs();
    L_0x0a87:
        r44 = r14.renameTo(r15);
        if (r44 != 0) goto L_0x0a95;
    L_0x0a8d:
        r0 = r51;
        r0.copyFile(r14, r15);
        r14.delete();
    L_0x0a95:
        r44 = r11.booleanValue();
        if (r44 == 0) goto L_0x0b05;
    L_0x0a9b:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "NOTOK";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0ae6:
        r14.delete();
        r44 = "AppInvHTTPD";
        r45 = "Received content without a file name!";
        android.util.Log.e(r44, r45);
        r44 = 1;
        r11 = java.lang.Boolean.valueOf(r44);
        goto L_0x0a95;
    L_0x0af7:
        r44 = "AppInvHTTPD";
        r45 = "Received PUT without content.";
        android.util.Log.e(r44, r45);
        r44 = 1;
        r11 = java.lang.Boolean.valueOf(r44);
        goto L_0x0a95;
    L_0x0b05:
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "OK";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0b50:
        r10 = r54.propertyNames();
    L_0x0b54:
        r44 = r10.hasMoreElements();
        if (r44 == 0) goto L_0x0b95;
    L_0x0b5a:
        r42 = r10.nextElement();
        r42 = (java.lang.String) r42;
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "  HDR: '";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r42;
        r45 = r0.append(r1);
        r46 = "' = '";
        r45 = r45.append(r46);
        r0 = r54;
        r1 = r42;
        r46 = r0.getProperty(r1);
        r45 = r45.append(r46);
        r46 = "'";
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        goto L_0x0b54;
    L_0x0b95:
        r10 = r55.propertyNames();
    L_0x0b99:
        r44 = r10.hasMoreElements();
        if (r44 == 0) goto L_0x0bda;
    L_0x0b9f:
        r42 = r10.nextElement();
        r42 = (java.lang.String) r42;
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = "  PRM: '";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r42;
        r45 = r0.append(r1);
        r46 = "' = '";
        r45 = r45.append(r46);
        r0 = r55;
        r1 = r42;
        r46 = r0.getProperty(r1);
        r45 = r45.append(r46);
        r46 = "'";
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        goto L_0x0b99;
    L_0x0bda:
        r10 = r56.propertyNames();
        r44 = r10.hasMoreElements();
        if (r44 == 0) goto L_0x0cf7;
    L_0x0be4:
        r13 = r10.nextElement();
        r13 = (java.lang.String) r13;
        r0 = r56;
        r38 = r0.getProperty(r13);
        r0 = r55;
        r16 = r0.getProperty(r13);
        r44 = "..";
        r0 = r16;
        r1 = r44;
        r44 = r0.startsWith(r1);
        if (r44 != 0) goto L_0x0c1a;
    L_0x0c02:
        r44 = "..";
        r0 = r16;
        r1 = r44;
        r44 = r0.endsWith(r1);
        if (r44 != 0) goto L_0x0c1a;
    L_0x0c0e:
        r44 = "../";
        r0 = r16;
        r1 = r44;
        r44 = r0.indexOf(r1);
        if (r44 < 0) goto L_0x0c38;
    L_0x0c1a:
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = " Ignoring invalid filename: ";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r16;
        r45 = r0.append(r1);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r16 = 0;
    L_0x0c38:
        r14 = new java.io.File;
        r0 = r38;
        r14.<init>(r0);
        if (r16 != 0) goto L_0x0cbf;
    L_0x0c41:
        r14.delete();
    L_0x0c44:
        r44 = "AppInvHTTPD";
        r45 = new java.lang.StringBuilder;
        r45.<init>();
        r46 = " UPLOADED: '";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r16;
        r45 = r0.append(r1);
        r46 = "' was at '";
        r45 = r45.append(r46);
        r0 = r45;
        r1 = r38;
        r45 = r0.append(r1);
        r46 = "'";
        r45 = r45.append(r46);
        r45 = r45.toString();
        android.util.Log.d(r44, r45);
        r36 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r44 = "200 OK";
        r45 = "text/plain";
        r46 = "OK";
        r0 = r36;
        r1 = r51;
        r2 = r44;
        r3 = r45;
        r4 = r46;
        r0.<init>(r2, r3, r4);
        r44 = "Access-Control-Allow-Origin";
        r45 = "*";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Headers";
        r45 = "origin, content-type";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Access-Control-Allow-Methods";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        r44 = "Allow";
        r45 = "POST,OPTIONS,GET,HEAD,PUT";
        r0 = r36;
        r1 = r44;
        r2 = r45;
        r0.addHeader(r1, r2);
        goto L_0x00cc;
    L_0x0cbf:
        r15 = new java.io.File;
        r44 = new java.lang.StringBuilder;
        r44.<init>();
        r0 = r51;
        r0 = r0.rootDir;
        r45 = r0;
        r44 = r44.append(r45);
        r45 = "/";
        r44 = r44.append(r45);
        r0 = r44;
        r1 = r16;
        r44 = r0.append(r1);
        r44 = r44.toString();
        r0 = r44;
        r15.<init>(r0);
        r44 = r14.renameTo(r15);
        if (r44 != 0) goto L_0x0c44;
    L_0x0ced:
        r0 = r51;
        r0.copyFile(r14, r15);
        r14.delete();
        goto L_0x0c44;
    L_0x0cf7:
        r0 = r51;
        r0 = r0.rootDir;
        r44 = r0;
        r45 = 1;
        r0 = r51;
        r1 = r52;
        r2 = r54;
        r3 = r44;
        r4 = r45;
        r36 = r0.serveFile(r1, r2, r3, r4);
        goto L_0x00cc;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.AppInvHTTPD.serve(java.lang.String, java.lang.String, java.util.Properties, java.util.Properties, java.util.Properties, java.net.Socket):com.google.appinventor.components.runtime.util.NanoHTTPD$Response");
    }

    private void copyFile(File infile, File outfile) {
        try {
            FileInputStream in = new FileInputStream(infile);
            FileOutputStream out = new FileOutputStream(outfile);
            byte[] buffer = new byte[ModuleExp.STATIC_SPECIFIED];
            while (true) {
                int len = in.read(buffer);
                if (len > 0) {
                    out.write(buffer, 0, len);
                } else {
                    in.close();
                    out.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setHmacKey(String inputKey) {
        hmacKey = inputKey.getBytes();
        seq = YAV_SKEW_FORWARD;
    }

    private void doPackageUpdate(String inurl) {
        PackageInstaller.doPackageInstall(this.form, inurl);
    }

    public void resetSeq() {
        seq = YAV_SKEW_FORWARD;
    }
}
