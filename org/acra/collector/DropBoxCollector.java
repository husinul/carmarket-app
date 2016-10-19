package org.acra.collector;

final class DropBoxCollector {
    private static final String NO_RESULT = "N/A";
    private static final String[] SYSTEM_TAGS;

    DropBoxCollector() {
    }

    static {
        SYSTEM_TAGS = new String[]{"system_app_anr", "system_app_wtf", "system_app_crash", "system_server_anr", "system_server_wtf", "system_server_crash", "BATTERY_DISCHARGE_INFO", "SYSTEM_RECOVERY_LOG", "SYSTEM_BOOT", "SYSTEM_LAST_KMSG", "APANIC_CONSOLE", "APANIC_THREADS", "SYSTEM_RESTART", "SYSTEM_TOMBSTONE", "data_app_strictmode"};
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String read(android.content.Context r27, java.lang.String[] r28) {
        /*
        r13 = org.acra.collector.Compatibility.getDropBoxServiceName();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r13 != 0) goto L_0x0009;
    L_0x0006:
        r22 = "N/A";
    L_0x0008:
        return r22;
    L_0x0009:
        r0 = r27;
        r5 = r0.getSystemService(r13);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r5.getClass();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = "getNextEntry";
        r24 = 2;
        r0 = r24;
        r0 = new java.lang.Class[r0];	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r24 = r0;
        r25 = 0;
        r26 = java.lang.String.class;
        r24[r25] = r26;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r25 = 1;
        r26 = java.lang.Long.TYPE;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r24[r25] = r26;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r9 = r22.getMethod(r23, r24);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r9 != 0) goto L_0x0032;
    L_0x002f:
        r22 = "";
        goto L_0x0008;
    L_0x0032:
        r19 = new android.text.format.Time;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r19.<init>();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r19.setToNow();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r19;
        r0 = r0.minute;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r0;
        r23 = org.acra.ACRA.getConfig();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = r23.dropboxCollectionMinutes();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r22 - r23;
        r0 = r22;
        r1 = r19;
        r1.minute = r0;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = 0;
        r0 = r19;
        r1 = r22;
        r0.normalize(r1);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = 0;
        r0 = r19;
        r1 = r22;
        r20 = r0.toMillis(r1);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r17 = new java.util.ArrayList;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r17.<init>();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = org.acra.ACRA.getConfig();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r22.includeDropBoxSystemTags();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r22 == 0) goto L_0x007f;
    L_0x0072:
        r22 = SYSTEM_TAGS;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = java.util.Arrays.asList(r22);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r17;
        r1 = r22;
        r0.addAll(r1);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
    L_0x007f:
        if (r28 == 0) goto L_0x0093;
    L_0x0081:
        r0 = r28;
        r0 = r0.length;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r0;
        if (r22 <= 0) goto L_0x0093;
    L_0x0088:
        r22 = java.util.Arrays.asList(r28);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r17;
        r1 = r22;
        r0.addAll(r1);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
    L_0x0093:
        r22 = r17.isEmpty();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r22 == 0) goto L_0x009d;
    L_0x0099:
        r22 = "No tag configured for collection.";
        goto L_0x0008;
    L_0x009d:
        r6 = new java.lang.StringBuilder;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r6.<init>();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r12 = r17.iterator();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
    L_0x00a6:
        r22 = r12.hasNext();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r22 == 0) goto L_0x01d6;
    L_0x00ac:
        r16 = r12.next();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r16 = (java.lang.String) r16;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = "Tag: ";
        r0 = r22;
        r22 = r6.append(r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r1 = r16;
        r22 = r0.append(r1);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 10;
        r22.append(r23);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = 2;
        r0 = r22;
        r0 = new java.lang.Object[r0];	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r0;
        r23 = 0;
        r22[r23] = r16;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 1;
        r24 = java.lang.Long.valueOf(r20);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22[r23] = r24;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r8 = r9.invoke(r5, r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r8 != 0) goto L_0x00fd;
    L_0x00e3:
        r22 = "Nothing.";
        r0 = r22;
        r22 = r6.append(r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 10;
        r22.append(r23);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        goto L_0x00a6;
    L_0x00f1:
        r7 = move-exception;
        r22 = org.acra.ACRA.LOG_TAG;
        r23 = "DropBoxManager not available.";
        android.util.Log.i(r22, r23);
    L_0x00f9:
        r22 = "N/A";
        goto L_0x0008;
    L_0x00fd:
        r22 = r8.getClass();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = "getText";
        r24 = 1;
        r0 = r24;
        r0 = new java.lang.Class[r0];	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r24 = r0;
        r25 = 0;
        r26 = java.lang.Integer.TYPE;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r24[r25] = r26;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r10 = r22.getMethod(r23, r24);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = r8.getClass();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r24 = "getTimeMillis";
        r22 = 0;
        r22 = (java.lang.Class[]) r22;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r23;
        r1 = r24;
        r2 = r22;
        r11 = r0.getMethod(r1, r2);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = r8.getClass();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r24 = "close";
        r22 = 0;
        r22 = (java.lang.Class[]) r22;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r23;
        r1 = r24;
        r2 = r22;
        r4 = r0.getMethod(r1, r2);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
    L_0x013d:
        if (r8 == 0) goto L_0x00a6;
    L_0x013f:
        r22 = 0;
        r22 = (java.lang.Object[]) r22;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r22 = r11.invoke(r8, r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = (java.lang.Long) r22;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r14 = r22.longValue();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r19;
        r0.set(r14);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = "@";
        r0 = r22;
        r22 = r6.append(r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = r19.format2445();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r22.append(r23);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 10;
        r22.append(r23);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = 1;
        r0 = r22;
        r0 = new java.lang.Object[r0];	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r0;
        r23 = 0;
        r24 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r24 = java.lang.Integer.valueOf(r24);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22[r23] = r24;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r18 = r10.invoke(r8, r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r18 = (java.lang.String) r18;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        if (r18 == 0) goto L_0x01be;
    L_0x0185:
        r22 = "Text: ";
        r0 = r22;
        r22 = r6.append(r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r1 = r18;
        r22 = r0.append(r1);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 10;
        r22.append(r23);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
    L_0x019a:
        r22 = 0;
        r22 = (java.lang.Object[]) r22;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r4.invoke(r8, r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = 2;
        r0 = r22;
        r0 = new java.lang.Object[r0];	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22 = r0;
        r23 = 0;
        r22[r23] = r16;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 1;
        r24 = java.lang.Long.valueOf(r14);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r22[r23] = r24;	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r0 = r22;
        r8 = r9.invoke(r5, r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        goto L_0x013d;
    L_0x01be:
        r22 = "Not Text!";
        r0 = r22;
        r22 = r6.append(r0);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        r23 = 10;
        r22.append(r23);	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        goto L_0x019a;
    L_0x01cc:
        r7 = move-exception;
        r22 = org.acra.ACRA.LOG_TAG;
        r23 = "DropBoxManager not available.";
        android.util.Log.i(r22, r23);
        goto L_0x00f9;
    L_0x01d6:
        r22 = r6.toString();	 Catch:{ SecurityException -> 0x00f1, NoSuchMethodException -> 0x01cc, IllegalArgumentException -> 0x01dc, IllegalAccessException -> 0x01e6, InvocationTargetException -> 0x01f0, NoSuchFieldException -> 0x01fa }
        goto L_0x0008;
    L_0x01dc:
        r7 = move-exception;
        r22 = org.acra.ACRA.LOG_TAG;
        r23 = "DropBoxManager not available.";
        android.util.Log.i(r22, r23);
        goto L_0x00f9;
    L_0x01e6:
        r7 = move-exception;
        r22 = org.acra.ACRA.LOG_TAG;
        r23 = "DropBoxManager not available.";
        android.util.Log.i(r22, r23);
        goto L_0x00f9;
    L_0x01f0:
        r7 = move-exception;
        r22 = org.acra.ACRA.LOG_TAG;
        r23 = "DropBoxManager not available.";
        android.util.Log.i(r22, r23);
        goto L_0x00f9;
    L_0x01fa:
        r7 = move-exception;
        r22 = org.acra.ACRA.LOG_TAG;
        r23 = "DropBoxManager not available.";
        android.util.Log.i(r22, r23);
        goto L_0x00f9;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.collector.DropBoxCollector.read(android.content.Context, java.lang.String[]):java.lang.String");
    }
}
