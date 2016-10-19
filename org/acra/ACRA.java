package org.acra;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import gnu.expr.SetExp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.XDataType;
import org.acra.annotation.ReportsCrashes;
import org.acra.log.ACRALog;
import org.acra.log.AndroidLogDelegate;

public class ACRA {
    public static final ReportField[] DEFAULT_MAIL_REPORT_FIELDS;
    public static final ReportField[] DEFAULT_REPORT_FIELDS;
    public static final boolean DEV_LOGGING = false;
    public static final String LOG_TAG;
    public static final String PREF_ALWAYS_ACCEPT = "acra.alwaysaccept";
    public static final String PREF_DISABLE_ACRA = "acra.disable";
    public static final String PREF_ENABLE_ACRA = "acra.enable";
    public static final String PREF_ENABLE_DEVICE_ID = "acra.deviceid.enable";
    public static final String PREF_ENABLE_SYSTEM_LOGS = "acra.syslog.enable";
    public static final String PREF_LAST_VERSION_NR = "acra.lastVersionNr";
    public static final String PREF_USER_EMAIL_ADDRESS = "acra.user.email";
    private static ACRAConfiguration configProxy;
    private static ErrorReporter errorReporterSingleton;
    public static ACRALog log;
    private static Application mApplication;
    private static OnSharedPreferenceChangeListener mPrefListener;
    private static ReportsCrashes mReportsCrashes;

    /* renamed from: org.acra.ACRA.1 */
    static class C01621 implements OnSharedPreferenceChangeListener {
        C01621() {
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (ACRA.PREF_DISABLE_ACRA.equals(key) || ACRA.PREF_ENABLE_ACRA.equals(key)) {
                ACRA.getErrorReporter().setEnabled(!ACRA.shouldDisableACRA(sharedPreferences) ? true : ACRA.DEV_LOGGING);
            }
        }
    }

    /* renamed from: org.acra.ACRA.2 */
    static /* synthetic */ class C01632 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$ReportingInteractionMode;

        static {
            $SwitchMap$org$acra$ReportingInteractionMode = new int[ReportingInteractionMode.values().length];
            try {
                $SwitchMap$org$acra$ReportingInteractionMode[ReportingInteractionMode.TOAST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$acra$ReportingInteractionMode[ReportingInteractionMode.NOTIFICATION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$acra$ReportingInteractionMode[ReportingInteractionMode.DIALOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static {
        LOG_TAG = ACRA.class.getSimpleName();
        log = new AndroidLogDelegate();
        DEFAULT_MAIL_REPORT_FIELDS = new ReportField[]{ReportField.USER_COMMENT, ReportField.ANDROID_VERSION, ReportField.APP_VERSION_NAME, ReportField.BRAND, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE};
        DEFAULT_REPORT_FIELDS = new ReportField[]{ReportField.REPORT_ID, ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.PACKAGE_NAME, ReportField.FILE_PATH, ReportField.PHONE_MODEL, ReportField.BRAND, ReportField.PRODUCT, ReportField.ANDROID_VERSION, ReportField.BUILD, ReportField.TOTAL_MEM_SIZE, ReportField.AVAILABLE_MEM_SIZE, ReportField.CUSTOM_DATA, ReportField.IS_SILENT, ReportField.STACK_TRACE, ReportField.INITIAL_CONFIGURATION, ReportField.CRASH_CONFIGURATION, ReportField.DISPLAY, ReportField.USER_COMMENT, ReportField.USER_EMAIL, ReportField.USER_APP_START_DATE, ReportField.USER_CRASH_DATE, ReportField.DUMPSYS_MEMINFO, ReportField.LOGCAT, ReportField.INSTALLATION_ID, ReportField.DEVICE_FEATURES, ReportField.ENVIRONMENT, ReportField.SHARED_PREFERENCES, ReportField.SETTINGS_SYSTEM, ReportField.SETTINGS_SECURE};
    }

    public static void init(Application app) {
        if (mApplication != null) {
            throw new IllegalStateException("ACRA#init called more than once");
        }
        mApplication = app;
        mReportsCrashes = (ReportsCrashes) mApplication.getClass().getAnnotation(ReportsCrashes.class);
        if (mReportsCrashes == null) {
            log.m2e(LOG_TAG, "ACRA#init called but no ReportsCrashes annotation on Application " + mApplication.getPackageName());
            return;
        }
        SharedPreferences prefs = getACRASharedPreferences();
        try {
            checkCrashResources();
            log.m0d(LOG_TAG, "ACRA is enabled for " + mApplication.getPackageName() + ", intializing...");
            ErrorReporter errorReporter = new ErrorReporter(mApplication.getApplicationContext(), prefs, !shouldDisableACRA(prefs) ? true : DEV_LOGGING);
            errorReporter.setDefaultReportSenders();
            errorReporterSingleton = errorReporter;
        } catch (ACRAConfigurationException e) {
            log.m9w(LOG_TAG, "Error : ", e);
        }
        mPrefListener = new C01621();
        prefs.registerOnSharedPreferenceChangeListener(mPrefListener);
    }

    public static ErrorReporter getErrorReporter() {
        if (errorReporterSingleton != null) {
            return errorReporterSingleton;
        }
        throw new IllegalStateException("Cannot access ErrorReporter before ACRA#init");
    }

    private static boolean shouldDisableACRA(SharedPreferences prefs) {
        boolean z = true;
        boolean disableAcra = DEV_LOGGING;
        try {
            boolean enableAcra = prefs.getBoolean(PREF_ENABLE_ACRA, true);
            String str = PREF_DISABLE_ACRA;
            if (enableAcra) {
                z = DEV_LOGGING;
            }
            disableAcra = prefs.getBoolean(str, z);
        } catch (Exception e) {
        }
        return disableAcra;
    }

    static void checkCrashResources() throws ACRAConfigurationException {
        ReportsCrashes conf = getConfig();
        switch (C01632.$SwitchMap$org$acra$ReportingInteractionMode[conf.mode().ordinal()]) {
            case ParseFormat.SEEN_MINUS /*1*/:
                if (conf.resToastText() == 0) {
                    throw new ACRAConfigurationException("TOAST mode: you have to define the resToastText parameter in your application @ReportsCrashes() annotation.");
                }
            case SetExp.DEFINING_FLAG /*2*/:
                if (conf.resNotifTickerText() == 0 || conf.resNotifTitle() == 0 || conf.resNotifText() == 0 || conf.resDialogText() == 0) {
                    throw new ACRAConfigurationException("NOTIFICATION mode: you have to define at least the resNotifTickerText, resNotifTitle, resNotifText, resDialogText parameters in your application @ReportsCrashes() annotation.");
                }
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                if (conf.resDialogText() == 0) {
                    throw new ACRAConfigurationException("DIALOG mode: you have to define at least the resDialogText parameters in your application @ReportsCrashes() annotation.");
                }
            default:
        }
    }

    public static SharedPreferences getACRASharedPreferences() {
        ReportsCrashes conf = getConfig();
        if (ElementType.MATCH_ANY_LOCALNAME.equals(conf.sharedPreferencesName())) {
            return PreferenceManager.getDefaultSharedPreferences(mApplication);
        }
        return mApplication.getSharedPreferences(conf.sharedPreferencesName(), conf.sharedPreferencesMode());
    }

    public static ACRAConfiguration getConfig() {
        if (configProxy == null) {
            if (mApplication == null) {
                log.m8w(LOG_TAG, "Calling ACRA.getConfig() before ACRA.init() gives you an empty configuration instance. You might prefer calling ACRA.getNewDefaultConfig(Application) to get an instance with default values taken from a @ReportsCrashes annotation.");
            }
            configProxy = getNewDefaultConfig(mApplication);
        }
        return configProxy;
    }

    public static void setConfig(ACRAConfiguration conf) {
        configProxy = conf;
    }

    public static ACRAConfiguration getNewDefaultConfig(Application app) {
        if (app != null) {
            return new ACRAConfiguration((ReportsCrashes) app.getClass().getAnnotation(ReportsCrashes.class));
        }
        return new ACRAConfiguration(null);
    }

    static boolean isDebuggable() {
        try {
            if ((mApplication.getPackageManager().getApplicationInfo(mApplication.getPackageName(), 0).flags & 2) > 0) {
                return true;
            }
            return DEV_LOGGING;
        } catch (NameNotFoundException e) {
            return DEV_LOGGING;
        }
    }

    static Application getApplication() {
        return mApplication;
    }

    public static void setLog(ACRALog log) {
        log = log;
    }
}
