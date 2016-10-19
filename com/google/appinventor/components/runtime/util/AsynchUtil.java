package com.google.appinventor.components.runtime.util;

import android.os.Handler;

public class AsynchUtil {

    /* renamed from: com.google.appinventor.components.runtime.util.AsynchUtil.1 */
    static class C01381 implements Runnable {
        final /* synthetic */ Handler val$androidUIHandler;
        final /* synthetic */ Runnable val$call;
        final /* synthetic */ Runnable val$callback;

        /* renamed from: com.google.appinventor.components.runtime.util.AsynchUtil.1.1 */
        class C01371 implements Runnable {
            C01371() {
            }

            public void run() {
                C01381.this.val$callback.run();
            }
        }

        C01381(Runnable runnable, Runnable runnable2, Handler handler) {
            this.val$call = runnable;
            this.val$callback = runnable2;
            this.val$androidUIHandler = handler;
        }

        public void run() {
            this.val$call.run();
            if (this.val$callback != null) {
                this.val$androidUIHandler.post(new C01371());
            }
        }
    }

    public static void runAsynchronously(Runnable call) {
        new Thread(call).start();
    }

    public static void runAsynchronously(Handler androidUIHandler, Runnable call, Runnable callback) {
        new Thread(new C01381(call, callback, androidUIHandler)).start();
    }
}
