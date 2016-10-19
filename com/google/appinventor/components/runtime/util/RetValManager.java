package com.google.appinventor.components.runtime.util;

import android.util.Log;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class RetValManager {
    private static final String LOG_TAG = "RetValManager";
    private static final long TENSECONDS = 10000;
    private static ArrayList<JSONObject> currentArray;
    private static final Object semaphore;

    static {
        semaphore = new Object();
        currentArray = new ArrayList(10);
    }

    private RetValManager() {
    }

    public static void appendReturnValue(String blockid, String ok, String item) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put("status", ok);
                retval.put("type", "return");
                retval.put("value", item);
                retval.put("blockid", blockid);
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    public static void sendError(String error) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put("status", "OK");
                retval.put("type", "error");
                retval.put("value", error);
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    public static void pushScreen(String screenName, Object value) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put("status", "OK");
                retval.put("type", "pushScreen");
                retval.put("screen", screenName);
                if (value != null) {
                    retval.put("value", value.toString());
                }
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    public static void popScreen(String value) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put("status", "OK");
                retval.put("type", "popScreen");
                if (value != null) {
                    retval.put("value", value.toString());
                }
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String fetch(boolean r14) {
        /*
        r4 = java.lang.System.currentTimeMillis();
        r8 = semaphore;
        monitor-enter(r8);
    L_0x0007:
        r3 = currentArray;	 Catch:{ all -> 0x0056 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0056 }
        if (r3 == 0) goto L_0x001d;
    L_0x000f:
        if (r14 == 0) goto L_0x001d;
    L_0x0011:
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x0056 }
        r10 = r6 - r4;
        r12 = 9900; // 0x26ac float:1.3873E-41 double:4.8912E-320;
        r3 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r3 <= 0) goto L_0x0040;
    L_0x001d:
        r0 = new org.json.JSONArray;	 Catch:{ all -> 0x0056 }
        r3 = currentArray;	 Catch:{ all -> 0x0056 }
        r0.<init>(r3);	 Catch:{ all -> 0x0056 }
        r2 = new org.json.JSONObject;	 Catch:{ all -> 0x0056 }
        r2.<init>();	 Catch:{ all -> 0x0056 }
        r3 = "status";
        r9 = "OK";
        r2.put(r3, r9);	 Catch:{ JSONException -> 0x004a }
        r3 = "values";
        r2.put(r3, r0);	 Catch:{ JSONException -> 0x004a }
        r3 = currentArray;	 Catch:{ all -> 0x0056 }
        r3.clear();	 Catch:{ all -> 0x0056 }
        r3 = r2.toString();	 Catch:{ all -> 0x0056 }
        monitor-exit(r8);	 Catch:{ all -> 0x0056 }
    L_0x003f:
        return r3;
    L_0x0040:
        r3 = semaphore;	 Catch:{ InterruptedException -> 0x0048 }
        r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r3.wait(r10);	 Catch:{ InterruptedException -> 0x0048 }
        goto L_0x0007;
    L_0x0048:
        r3 = move-exception;
        goto L_0x0007;
    L_0x004a:
        r1 = move-exception;
        r3 = "RetValManager";
        r9 = "Error fetching retvals";
        android.util.Log.e(r3, r9, r1);	 Catch:{ all -> 0x0056 }
        r3 = "{\"status\" : \"BAD\", \"message\" : \"Failure in RetValManager\"}";
        monitor-exit(r8);	 Catch:{ all -> 0x0056 }
        goto L_0x003f;
    L_0x0056:
        r3 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0056 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.RetValManager.fetch(boolean):java.lang.String");
    }
}
