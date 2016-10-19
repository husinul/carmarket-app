package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.GeolocationPermissions;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.google.appinventor.components.runtime.WebViewer;

public class EclairUtil {

    /* renamed from: com.google.appinventor.components.runtime.util.EclairUtil.1 */
    static class C01411 extends WebChromeClient {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ WebViewer val$caller;

        /* renamed from: com.google.appinventor.components.runtime.util.EclairUtil.1.1 */
        class C01391 implements OnClickListener {
            final /* synthetic */ Callback val$theCallback;
            final /* synthetic */ String val$theOrigin;

            C01391(Callback callback, String str) {
                this.val$theCallback = callback;
                this.val$theOrigin = str;
            }

            public void onClick(DialogInterface dialog, int which) {
                this.val$theCallback.invoke(this.val$theOrigin, true, true);
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.util.EclairUtil.1.2 */
        class C01402 implements OnClickListener {
            final /* synthetic */ Callback val$theCallback;
            final /* synthetic */ String val$theOrigin;

            C01402(Callback callback, String str) {
                this.val$theCallback = callback;
                this.val$theOrigin = str;
            }

            public void onClick(DialogInterface dialog, int which) {
                this.val$theCallback.invoke(this.val$theOrigin, false, true);
            }
        }

        C01411(WebViewer webViewer, Activity activity) {
            this.val$caller = webViewer;
            this.val$activity = activity;
        }

        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
            Callback theCallback = callback;
            String theOrigin = origin;
            if (this.val$caller.PromptforPermission()) {
                AlertDialog alertDialog = new Builder(this.val$activity).create();
                alertDialog.setTitle("Permission Request");
                if (origin.equals("file://")) {
                    origin = "This Application";
                }
                alertDialog.setMessage(origin + " would like to access your location.");
                alertDialog.setButton(-1, "Allow", new C01391(theCallback, theOrigin));
                alertDialog.setButton(-2, "Refuse", new C01402(theCallback, theOrigin));
                alertDialog.show();
                return;
            }
            callback.invoke(origin, true, true);
        }
    }

    private EclairUtil() {
    }

    public static void overridePendingTransitions(Activity activity, int enterAnim, int exitAnim) {
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    public static void setupWebViewGeoLoc(WebViewer caller, WebView webview, Activity activity) {
        webview.getSettings().setGeolocationDatabasePath(activity.getFilesDir().getAbsolutePath());
        webview.getSettings().setDatabaseEnabled(true);
        webview.setWebChromeClient(new C01411(caller, activity));
    }

    public static void clearWebViewGeoLoc() {
        GeolocationPermissions.getInstance().clearAll();
    }

    public static String getInstallerPackageName(String pname, Activity form) {
        return form.getPackageManager().getInstallerPackageName(pname);
    }
}
