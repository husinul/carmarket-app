package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import gnu.expr.ModuleExp;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PackageInstaller {
    private static final String LOG_TAG = "PackageInstaller(AppInventor)";
    private static final String REPL_ASSET_DIR = "/sdcard/AppInventor/assets/";

    /* renamed from: com.google.appinventor.components.runtime.util.PackageInstaller.1 */
    static class C01551 implements Runnable {
        final /* synthetic */ Form val$form;
        final /* synthetic */ String val$inurl;

        C01551(String str, Form form) {
            this.val$inurl = str;
            this.val$form = form;
        }

        public void run() {
            try {
                URLConnection conn = new URL(this.val$inurl).openConnection();
                File rootDir = new File(PackageInstaller.REPL_ASSET_DIR);
                InputStream instream = new BufferedInputStream(conn.getInputStream());
                FileOutputStream apkOut = new FileOutputStream(new File(rootDir + "/package.apk"));
                byte[] buffer = new byte[ModuleExp.STATIC_SPECIFIED];
                while (true) {
                    int len = instream.read(buffer, 0, ModuleExp.STATIC_SPECIFIED);
                    if (len > 0) {
                        apkOut.write(buffer, 0, len);
                    } else {
                        instream.close();
                        apkOut.close();
                        Log.d(PackageInstaller.LOG_TAG, "About to Install package from " + this.val$inurl);
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.fromFile(new File(rootDir + "/package.apk")), "application/vnd.android.package-archive");
                        this.val$form.startActivity(intent);
                        return;
                    }
                }
            } catch (Exception e) {
                Object[] objArr = new Object[1];
                objArr[0] = this.val$inurl;
                this.val$form.dispatchErrorOccurredEvent(this.val$form, "PackageInstaller", ErrorMessages.ERROR_WEB_UNABLE_TO_GET, objArr);
            }
        }
    }

    private PackageInstaller() {
    }

    public static void doPackageInstall(Form form, String inurl) {
        AsynchUtil.runAsynchronously(new C01551(inurl, form));
    }
}
