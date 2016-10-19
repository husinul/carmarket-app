package org.acra.log;

public interface ACRALog {
    int m0d(String str, String str2);

    int m1d(String str, String str2, Throwable th);

    int m2e(String str, String str2);

    int m3e(String str, String str2, Throwable th);

    String getStackTraceString(Throwable th);

    int m4i(String str, String str2);

    int m5i(String str, String str2, Throwable th);

    int m6v(String str, String str2);

    int m7v(String str, String str2, Throwable th);

    int m8w(String str, String str2);

    int m9w(String str, String str2, Throwable th);

    int m10w(String str, Throwable th);
}
