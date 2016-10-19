package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public final class RuntimeErrorAlert {

    /* renamed from: com.google.appinventor.components.runtime.util.RuntimeErrorAlert.1 */
    static class C01561 implements OnClickListener {
        final /* synthetic */ Object val$context;

        C01561(Object obj) {
            this.val$context = obj;
        }

        public void onClick(DialogInterface dialog, int which) {
            ((Activity) this.val$context).finish();
        }
    }

    public static void alert(Object context, String message, String title, String buttonText) {
        Log.i("RuntimeErrorAlert", "in alert");
        AlertDialog alertDialog = new Builder((Context) context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(buttonText, new C01561(context));
        if (message == null) {
            Log.e(RuntimeErrorAlert.class.getName(), "No error message available");
        } else {
            Log.e(RuntimeErrorAlert.class.getName(), message);
        }
        alertDialog.show();
    }
}
