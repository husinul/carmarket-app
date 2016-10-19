package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.collect.Sets;
import com.google.appinventor.components.runtime.multidex.MultiDex;
import com.google.appinventor.components.runtime.util.AlignmentUtil;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.ScreenDensityUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.ViewUtil;
import gnu.expr.LambdaExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;

@DesignerComponent(category = ComponentCategory.LAYOUT, description = "Top-level component containing all other components in the program", showOnPalette = false, version = 19)
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.ACCESS_WIFI_STATE,android.permission.ACCESS_NETWORK_STATE")
public class Form extends Activity implements Component, ComponentContainer, HandlesEventDispatching, OnGlobalLayoutListener {
    public static final String APPINVENTOR_URL_SCHEME = "appinventor";
    private static final String ARGUMENT_NAME = "APP_INVENTOR_START";
    private static final String LOG_TAG = "Form";
    private static final String RESULT_NAME = "APP_INVENTOR_RESULT";
    private static final int SWITCH_FORM_REQUEST_CODE = 1;
    private static boolean _initialized;
    protected static Form activeForm;
    private static boolean applicationIsBeingClosed;
    private static long minimumToastWait;
    private static int nextRequestCode;
    private static boolean sCompatibilityMode;
    private String aboutScreen;
    private final HashMap<Integer, ActivityResultListener> activityResultMap;
    private AlignmentUtil alignmentSetter;
    private final Handler androidUIHandler;
    private int backgroundColor;
    private Drawable backgroundDrawable;
    private String backgroundImagePath;
    private String closeAnimType;
    private float compatScalingFactor;
    private float deviceDensity;
    private ArrayList<PercentStorageRecord> dimChanges;
    private int formHeight;
    protected String formName;
    private int formWidth;
    private FrameLayout frameLayout;
    private FullScreenVideoUtil fullScreenVideoUtil;
    private int horizontalAlignment;
    private boolean keyboardShown;
    private long lastToastTime;
    private String nextFormName;
    private final Set<OnCreateOptionsMenuListener> onCreateOptionsMenuListeners;
    private final Set<OnDestroyListener> onDestroyListeners;
    private final Set<OnInitializeListener> onInitializeListeners;
    private final Set<OnNewIntentListener> onNewIntentListeners;
    private final Set<OnOptionsItemSelectedListener> onOptionsItemSelectedListeners;
    private final Set<OnPauseListener> onPauseListeners;
    private final Set<OnResumeListener> onResumeListeners;
    private final Set<OnStopListener> onStopListeners;
    private String openAnimType;
    private ProgressDialog progress;
    private ScaledFrameLayout scaleLayout;
    private boolean screenInitialized;
    private boolean scrollable;
    private boolean showStatusBar;
    private boolean showTitle;
    protected String startupValue;
    private int verticalAlignment;
    private LinearLayout viewLayout;
    private String yandexTranslateTagline;

    /* renamed from: com.google.appinventor.components.runtime.Form.1 */
    class C00311 implements Runnable {
        final /* synthetic */ int val$newOrientation;

        /* renamed from: com.google.appinventor.components.runtime.Form.1.1 */
        class C00301 implements Runnable {
            C00301() {
            }

            public void run() {
                if (Form.this.frameLayout != null) {
                    Form.this.frameLayout.invalidate();
                }
            }
        }

        C00311(int i) {
            this.val$newOrientation = i;
        }

        public void run() {
            boolean dispatchEventNow = false;
            if (Form.this.frameLayout != null) {
                if (this.val$newOrientation == 2) {
                    if (Form.this.frameLayout.getWidth() >= Form.this.frameLayout.getHeight()) {
                        dispatchEventNow = true;
                    }
                } else if (Form.this.frameLayout.getHeight() >= Form.this.frameLayout.getWidth()) {
                    dispatchEventNow = true;
                }
            }
            if (dispatchEventNow) {
                Form.this.recomputeLayout();
                FrameLayout savedLayout = Form.this.frameLayout;
                Form.this.androidUIHandler.postDelayed(new C00301(), 100);
                Form.this.ScreenOrientationChanged();
                return;
            }
            Form.this.androidUIHandler.post(this);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.2 */
    class C00322 implements Runnable {
        C00322() {
        }

        public void run() {
            if (Form.this.frameLayout == null || Form.this.frameLayout.getWidth() == 0 || Form.this.frameLayout.getHeight() == 0) {
                Form.this.androidUIHandler.post(this);
                return;
            }
            EventDispatcher.dispatchEvent(Form.this, "Initialize", new Object[0]);
            if (Form.sCompatibilityMode) {
                Form.this.Sizing("Fixed");
            } else {
                Form.this.Sizing("Responsive");
            }
            Form.this.screenInitialized = true;
            for (OnInitializeListener onInitializeListener : Form.this.onInitializeListeners) {
                onInitializeListener.onInitialize();
            }
            if (Form.activeForm instanceof ReplForm) {
                ((ReplForm) Form.activeForm).HandleReturnValues();
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.3 */
    class C00333 implements Runnable {
        final /* synthetic */ Component val$component;
        final /* synthetic */ int val$errorNumber;
        final /* synthetic */ String val$functionName;
        final /* synthetic */ Object[] val$messageArgs;

        C00333(int i, Object[] objArr, Component component, String str) {
            this.val$errorNumber = i;
            this.val$messageArgs = objArr;
            this.val$component = component;
            this.val$functionName = str;
        }

        public void run() {
            Form.this.ErrorOccurred(this.val$component, this.val$functionName, this.val$errorNumber, ErrorMessages.formatMessage(this.val$errorNumber, this.val$messageArgs));
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.4 */
    class C00344 implements Runnable {
        final /* synthetic */ Component val$component;
        final /* synthetic */ int val$errorNumber;
        final /* synthetic */ String val$functionName;
        final /* synthetic */ Object[] val$messageArgs;

        C00344(int i, Object[] objArr, Component component, String str) {
            this.val$errorNumber = i;
            this.val$messageArgs = objArr;
            this.val$component = component;
            this.val$functionName = str;
        }

        public void run() {
            Form.this.ErrorOccurredDialog(this.val$component, this.val$functionName, this.val$errorNumber, ErrorMessages.formatMessage(this.val$errorNumber, this.val$messageArgs), "Error in " + this.val$functionName, "Dismiss");
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.5 */
    class C00355 implements Runnable {
        C00355() {
        }

        public void run() {
            if (Form.this.frameLayout == null || Form.this.frameLayout.getWidth() == 0 || Form.this.frameLayout.getHeight() == 0) {
                Form.this.androidUIHandler.post(this);
                return;
            }
            if (Form.sCompatibilityMode) {
                Form.this.Sizing("Fixed");
            } else {
                Form.this.Sizing("Responsive");
            }
            Form.this.ReplayFormOrientation();
            Form.this.frameLayout.requestLayout();
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.6 */
    class C00366 implements Runnable {
        final /* synthetic */ AndroidViewComponent val$component;
        final /* synthetic */ int val$fWidth;

        C00366(AndroidViewComponent androidViewComponent, int i) {
            this.val$component = androidViewComponent;
            this.val$fWidth = i;
        }

        public void run() {
            System.err.println("(Form)Width not stable yet... trying again");
            Form.this.setChildWidth(this.val$component, this.val$fWidth);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.7 */
    class C00377 implements Runnable {
        final /* synthetic */ AndroidViewComponent val$component;
        final /* synthetic */ int val$fHeight;

        C00377(AndroidViewComponent androidViewComponent, int i) {
            this.val$component = androidViewComponent;
            this.val$fHeight = i;
        }

        public void run() {
            System.err.println("(Form)Height not stable yet... trying again");
            Form.this.setChildHeight(this.val$component, this.val$fHeight);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.8 */
    class C00388 implements OnMenuItemClickListener {
        C00388() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            Form.this.showExitApplicationNotification();
            return true;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Form.9 */
    class C00399 implements OnMenuItemClickListener {
        C00399() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            Form.this.showAboutApplicationNotification();
            return true;
        }
    }

    private static class MultiDexInstaller extends AsyncTask<Form, Void, Boolean> {
        Form ourForm;

        private MultiDexInstaller() {
        }

        protected Boolean doInBackground(Form... form) {
            this.ourForm = form[0];
            Log.d(Form.LOG_TAG, "Doing Full MultiDex Install");
            MultiDex.install(this.ourForm, true);
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean v) {
            this.ourForm.onCreateFinish();
        }
    }

    public static class PercentStorageRecord {
        AndroidViewComponent component;
        Dim dim;
        int length;

        public enum Dim {
            HEIGHT,
            WIDTH
        }

        public PercentStorageRecord(AndroidViewComponent component, int length, Dim dim) {
            this.component = component;
            this.length = length;
            this.dim = dim;
        }
    }

    public Form() {
        this.androidUIHandler = new Handler();
        this.showStatusBar = true;
        this.showTitle = true;
        this.backgroundImagePath = ElementType.MATCH_ANY_LOCALNAME;
        this.activityResultMap = Maps.newHashMap();
        this.onStopListeners = Sets.newHashSet();
        this.onNewIntentListeners = Sets.newHashSet();
        this.onResumeListeners = Sets.newHashSet();
        this.onPauseListeners = Sets.newHashSet();
        this.onDestroyListeners = Sets.newHashSet();
        this.onInitializeListeners = Sets.newHashSet();
        this.onCreateOptionsMenuListeners = Sets.newHashSet();
        this.onOptionsItemSelectedListeners = Sets.newHashSet();
        this.startupValue = ElementType.MATCH_ANY_LOCALNAME;
        this.lastToastTime = System.nanoTime() - minimumToastWait;
        this.keyboardShown = false;
        this.dimChanges = new ArrayList();
        this.yandexTranslateTagline = ElementType.MATCH_ANY_LOCALNAME;
    }

    static {
        nextRequestCode = 2;
        minimumToastWait = 10000000000L;
        _initialized = false;
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String className = getClass().getName();
        this.formName = className.substring(className.lastIndexOf(46) + SWITCH_FORM_REQUEST_CODE);
        Log.d(LOG_TAG, "Form " + this.formName + " got onCreate");
        activeForm = this;
        Log.i(LOG_TAG, "activeForm is now " + activeForm.formName);
        this.deviceDensity = getResources().getDisplayMetrics().density;
        Log.d(LOG_TAG, "deviceDensity = " + this.deviceDensity);
        this.compatScalingFactor = ScreenDensityUtil.computeCompatibleScaling(this);
        Log.i(LOG_TAG, "compatScalingFactor = " + this.compatScalingFactor);
        this.viewLayout = new LinearLayout(this, SWITCH_FORM_REQUEST_CODE);
        this.alignmentSetter = new AlignmentUtil(this.viewLayout);
        this.progress = null;
        if (_initialized || !this.formName.equals("Screen1")) {
            Log.d(LOG_TAG, "NO MULTI: _initialized = " + _initialized + " formName = " + this.formName);
            _initialized = true;
            onCreateFinish();
            return;
        }
        Log.d(LOG_TAG, "MULTI: _initialized = " + _initialized + " formName = " + this.formName);
        _initialized = true;
        if (ReplApplication.installed) {
            Log.d(LOG_TAG, "MultiDex already installed.");
            onCreateFinish();
            return;
        }
        this.progress = ProgressDialog.show(this, "Please Wait...", "Installation Finishing");
        this.progress.show();
        MultiDexInstaller multiDexInstaller = new MultiDexInstaller();
        Form[] formArr = new Form[SWITCH_FORM_REQUEST_CODE];
        formArr[0] = this;
        multiDexInstaller.execute(formArr);
    }

    void onCreateFinish() {
        Log.d(LOG_TAG, "onCreateFinish called " + System.currentTimeMillis());
        if (this.progress != null) {
            this.progress.dismiss();
        }
        defaultPropertyValues();
        Intent startIntent = getIntent();
        if (startIntent != null && startIntent.hasExtra(ARGUMENT_NAME)) {
            this.startupValue = startIntent.getStringExtra(ARGUMENT_NAME);
        }
        this.fullScreenVideoUtil = new FullScreenVideoUtil(this, this.androidUIHandler);
        getWindow().setSoftInputMode(getWindow().getAttributes().softInputMode | 16);
        $define();
        Initialize();
    }

    private void defaultPropertyValues() {
        Scrollable(false);
        Sizing("Fixed");
        BackgroundImage(ElementType.MATCH_ANY_LOCALNAME);
        AboutScreen(ElementType.MATCH_ANY_LOCALNAME);
        BackgroundImage(ElementType.MATCH_ANY_LOCALNAME);
        BackgroundColor(-1);
        AlignHorizontal(SWITCH_FORM_REQUEST_CODE);
        AlignVertical(SWITCH_FORM_REQUEST_CODE);
        Title(ElementType.MATCH_ANY_LOCALNAME);
        ShowStatusBar(true);
        TitleVisible(true);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(LOG_TAG, "onConfigurationChanged() called");
        int newOrientation = newConfig.orientation;
        if (newOrientation == 2 || newOrientation == SWITCH_FORM_REQUEST_CODE) {
            this.androidUIHandler.post(new C00311(newOrientation));
        }
    }

    public void onGlobalLayout() {
        int heightDiff = this.scaleLayout.getRootView().getHeight() - this.scaleLayout.getHeight();
        int contentViewTop = getWindow().findViewById(16908290).getTop();
        Log.d(LOG_TAG, "onGlobalLayout(): heightdiff = " + heightDiff + " contentViewTop = " + contentViewTop);
        if (heightDiff <= contentViewTop) {
            Log.d(LOG_TAG, "keyboard hidden!");
            if (this.keyboardShown) {
                this.keyboardShown = false;
                if (sCompatibilityMode) {
                    this.scaleLayout.setScale(this.compatScalingFactor);
                    this.scaleLayout.invalidate();
                    return;
                }
                return;
            }
            return;
        }
        int keyboardHeight = heightDiff - contentViewTop;
        Log.d(LOG_TAG, "keyboard shown!");
        this.keyboardShown = true;
        if (this.scaleLayout != null) {
            this.scaleLayout.setScale(1.0f);
            this.scaleLayout.invalidate();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (BackPressed()) {
            return true;
        }
        boolean handled = super.onKeyDown(keyCode, event);
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
        return handled;
    }

    @SimpleEvent(description = "Device back button pressed.")
    public boolean BackPressed() {
        return EventDispatcher.dispatchEvent(this, "BackPressed", new Object[0]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG_TAG, "Form " + this.formName + " got onActivityResult, requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == SWITCH_FORM_REQUEST_CODE) {
            String resultString;
            if (data == null || !data.hasExtra(RESULT_NAME)) {
                resultString = ElementType.MATCH_ANY_LOCALNAME;
            } else {
                resultString = data.getStringExtra(RESULT_NAME);
            }
            OtherScreenClosed(this.nextFormName, decodeJSONStringForForm(resultString, "other screen closed"));
            return;
        }
        ActivityResultListener component = (ActivityResultListener) this.activityResultMap.get(Integer.valueOf(requestCode));
        if (component != null) {
            component.resultReturned(requestCode, resultCode, data);
        }
    }

    private static Object decodeJSONStringForForm(String jsonString, String functionName) {
        Log.i(LOG_TAG, "decodeJSONStringForForm -- decoding JSON representation:" + jsonString);
        Object obj = ElementType.MATCH_ANY_LOCALNAME;
        try {
            obj = JsonUtil.getObjectFromJson(jsonString);
            Log.i(LOG_TAG, "decodeJSONStringForForm -- got decoded JSON:" + obj.toString());
            return obj;
        } catch (JSONException e) {
            Form form = activeForm;
            Component component = activeForm;
            Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
            objArr[0] = jsonString;
            form.dispatchErrorOccurredEvent(component, functionName, ErrorMessages.ERROR_SCREEN_BAD_VALUE_RECEIVED, objArr);
            return obj;
        }
    }

    public int registerForActivityResult(ActivityResultListener listener) {
        int requestCode = generateNewRequestCode();
        this.activityResultMap.put(Integer.valueOf(requestCode), listener);
        return requestCode;
    }

    public void unregisterForActivityResult(ActivityResultListener listener) {
        List<Integer> keysToDelete = Lists.newArrayList();
        for (Entry<Integer, ActivityResultListener> mapEntry : this.activityResultMap.entrySet()) {
            if (listener.equals(mapEntry.getValue())) {
                keysToDelete.add(mapEntry.getKey());
            }
        }
        for (Integer key : keysToDelete) {
            this.activityResultMap.remove(key);
        }
    }

    void ReplayFormOrientation() {
        Log.d(LOG_TAG, "ReplayFormOrientation()");
        ArrayList<PercentStorageRecord> temp = (ArrayList) this.dimChanges.clone();
        this.dimChanges.clear();
        for (int i = 0; i < temp.size(); i += SWITCH_FORM_REQUEST_CODE) {
            PercentStorageRecord r = (PercentStorageRecord) temp.get(i);
            if (r.dim == Dim.HEIGHT) {
                r.component.Height(r.length);
            } else {
                r.component.Width(r.length);
            }
        }
    }

    public void registerPercentLength(AndroidViewComponent component, int length, Dim dim) {
        this.dimChanges.add(new PercentStorageRecord(component, length, dim));
    }

    private static int generateNewRequestCode() {
        int i = nextRequestCode;
        nextRequestCode = i + SWITCH_FORM_REQUEST_CODE;
        return i;
    }

    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "Form " + this.formName + " got onResume");
        activeForm = this;
        if (applicationIsBeingClosed) {
            closeApplication();
            return;
        }
        for (OnResumeListener onResumeListener : this.onResumeListeners) {
            onResumeListener.onResume();
        }
    }

    public void registerForOnResume(OnResumeListener component) {
        this.onResumeListeners.add(component);
    }

    public void registerForOnInitialize(OnInitializeListener component) {
        this.onInitializeListeners.add(component);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, "Form " + this.formName + " got onNewIntent " + intent);
        for (OnNewIntentListener onNewIntentListener : this.onNewIntentListeners) {
            onNewIntentListener.onNewIntent(intent);
        }
    }

    public void registerForOnNewIntent(OnNewIntentListener component) {
        this.onNewIntentListeners.add(component);
    }

    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "Form " + this.formName + " got onPause");
        for (OnPauseListener onPauseListener : this.onPauseListeners) {
            onPauseListener.onPause();
        }
    }

    public void registerForOnPause(OnPauseListener component) {
        this.onPauseListeners.add(component);
    }

    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "Form " + this.formName + " got onStop");
        for (OnStopListener onStopListener : this.onStopListeners) {
            onStopListener.onStop();
        }
    }

    public void registerForOnStop(OnStopListener component) {
        this.onStopListeners.add(component);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "Form " + this.formName + " got onDestroy");
        EventDispatcher.removeDispatchDelegate(this);
        for (OnDestroyListener onDestroyListener : this.onDestroyListeners) {
            onDestroyListener.onDestroy();
        }
    }

    public void registerForOnDestroy(OnDestroyListener component) {
        this.onDestroyListeners.add(component);
    }

    public void registerForOnCreateOptionsMenu(OnCreateOptionsMenuListener component) {
        this.onCreateOptionsMenuListeners.add(component);
    }

    public void registerForOnOptionsItemSelected(OnOptionsItemSelectedListener component) {
        this.onOptionsItemSelectedListeners.add(component);
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG /*189*/:
                return this.fullScreenVideoUtil.createFullScreenVideoDialog();
            default:
                return super.onCreateDialog(id);
        }
    }

    public void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG /*189*/:
                this.fullScreenVideoUtil.prepareFullScreenVideoDialog(dialog);
            default:
                super.onPrepareDialog(id, dialog);
        }
    }

    protected void $define() {
        throw new UnsupportedOperationException();
    }

    public boolean canDispatchEvent(Component component, String eventName) {
        boolean canDispatch = this.screenInitialized || (component == this && eventName.equals("Initialize"));
        if (canDispatch) {
            activeForm = this;
        }
        return canDispatch;
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] args) {
        throw new UnsupportedOperationException();
    }

    @SimpleEvent(description = "Screen starting")
    public void Initialize() {
        this.androidUIHandler.post(new C00322());
    }

    @SimpleEvent(description = "Screen orientation changed")
    public void ScreenOrientationChanged() {
        EventDispatcher.dispatchEvent(this, "ScreenOrientationChanged", new Object[0]);
    }

    @SimpleEvent(description = "Event raised when an error occurs. Only some errors will raise this condition.  For those errors, the system will show a notification by default.  You can use this event handler to prescribe an error behavior different than the default.")
    public void ErrorOccurred(Component component, String functionName, int errorNumber, String message) {
        String componentType = component.getClass().getName();
        Log.e(LOG_TAG, "Form " + this.formName + " ErrorOccurred, errorNumber = " + errorNumber + ", componentType = " + componentType.substring(componentType.lastIndexOf(".") + SWITCH_FORM_REQUEST_CODE) + ", functionName = " + functionName + ", messages = " + message);
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", component, functionName, Integer.valueOf(errorNumber), message) && this.screenInitialized) {
            new Notifier(this).ShowAlert("Error " + errorNumber + ": " + message);
        }
    }

    public void ErrorOccurredDialog(Component component, String functionName, int errorNumber, String message, String title, String buttonText) {
        String componentType = component.getClass().getName();
        Log.e(LOG_TAG, "Form " + this.formName + " ErrorOccurred, errorNumber = " + errorNumber + ", componentType = " + componentType.substring(componentType.lastIndexOf(".") + SWITCH_FORM_REQUEST_CODE) + ", functionName = " + functionName + ", messages = " + message);
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", component, functionName, Integer.valueOf(errorNumber), message) && this.screenInitialized) {
            new Notifier(this).ShowMessageDialog("Error " + errorNumber + ": " + message, title, buttonText);
        }
    }

    public void dispatchErrorOccurredEvent(Component component, String functionName, int errorNumber, Object... messageArgs) {
        runOnUiThread(new C00333(errorNumber, messageArgs, component, functionName));
    }

    public void dispatchErrorOccurredEventDialog(Component component, String functionName, int errorNumber, Object... messageArgs) {
        runOnUiThread(new C00344(errorNumber, messageArgs, component, functionName));
    }

    public void runtimeFormErrorOccurredEvent(String functionName, int errorNumber, String message) {
        Log.d("FORM_RUNTIME_ERROR", "functionName is " + functionName);
        Log.d("FORM_RUNTIME_ERROR", "errorNumber is " + errorNumber);
        Log.d("FORM_RUNTIME_ERROR", "message is " + message);
        Component component = activeForm;
        Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
        objArr[0] = message;
        dispatchErrorOccurredEvent(component, functionName, errorNumber, objArr);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "When checked, there will be a vertical scrollbar on the screen, and the height of the application can exceed the physical height of the device. When unchecked, the application height is constrained to the height of the device.")
    public boolean Scrollable() {
        return this.scrollable;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void Scrollable(boolean scrollable) {
        if (this.scrollable != scrollable || this.frameLayout == null) {
            this.scrollable = scrollable;
            recomputeLayout();
        }
    }

    private void recomputeLayout() {
        Log.d(LOG_TAG, "recomputeLayout called");
        if (this.frameLayout != null) {
            this.frameLayout.removeAllViews();
        }
        this.frameLayout = this.scrollable ? new ScrollView(this) : new FrameLayout(this);
        this.frameLayout.addView(this.viewLayout.getLayoutManager(), new LayoutParams(-1, -1));
        setBackground(this.frameLayout);
        Log.d(LOG_TAG, "About to create a new ScaledFrameLayout");
        this.scaleLayout = new ScaledFrameLayout(this);
        this.scaleLayout.addView(this.frameLayout, new LayoutParams(-1, -1));
        setContentView(this.scaleLayout);
        this.frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.scaleLayout.requestLayout();
        this.androidUIHandler.post(new C00355());
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&HFFFFFFFF", editorType = "color")
    @SimpleProperty
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        setBackground(this.frameLayout);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The screen background image.")
    public String BackgroundImage() {
        return this.backgroundImagePath;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The screen background image.")
    public void BackgroundImage(String path) {
        if (path == null) {
            path = ElementType.MATCH_ANY_LOCALNAME;
        }
        this.backgroundImagePath = path;
        try {
            this.backgroundDrawable = MediaUtil.getBitmapDrawable(this, this.backgroundImagePath);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to load " + this.backgroundImagePath);
            this.backgroundDrawable = null;
        }
        setBackground(this.frameLayout);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The caption for the form, which apears in the title bar")
    public String Title() {
        return getTitle().toString();
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Title(String title) {
        setTitle(title);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Information about the screen.  It appears when \"About this Application\" is selected from the system menu. Use it to inform people about your app.  In multiple screen apps, each screen has its own AboutScreen info.")
    public String AboutScreen() {
        return this.aboutScreen;
    }

    @DesignerProperty(defaultValue = "", editorType = "textArea")
    @SimpleProperty
    public void AboutScreen(String aboutScreen) {
        this.aboutScreen = aboutScreen;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The title bar is the top gray bar on the screen. This property reports whether the title bar is visible.")
    public boolean TitleVisible() {
        return this.showTitle;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void TitleVisible(boolean show) {
        if (show != this.showTitle) {
            View v = (View) findViewById(16908310).getParent();
            if (v != null) {
                if (show) {
                    v.setVisibility(0);
                } else {
                    v.setVisibility(8);
                }
                this.showTitle = show;
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The status bar is the topmost bar on the screen. This property reports whether the status bar is visible.")
    public boolean ShowStatusBar() {
        return this.showStatusBar;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ShowStatusBar(boolean show) {
        if (show != this.showStatusBar) {
            if (show) {
                getWindow().addFlags(LambdaExp.OVERLOADABLE_FIELD);
                getWindow().clearFlags(LambdaExp.SEQUENCE_RESULT);
            } else {
                getWindow().addFlags(LambdaExp.SEQUENCE_RESULT);
                getWindow().clearFlags(LambdaExp.OVERLOADABLE_FIELD);
            }
            this.showStatusBar = show;
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The requested screen orientation, specified as a text value.  Commonly used values are landscape, portrait, sensor, user and unspecified.  See the Android developer documentation for ActivityInfo.Screen_Orientation for the complete list of possible settings.")
    public String ScreenOrientation() {
        switch (getRequestedOrientation()) {
            case ListPicker.DEFAULT_ITEM_TEXT_COLOR /*-1*/:
                return "unspecified";
            case Sequence.EOF_VALUE /*0*/:
                return "landscape";
            case SWITCH_FORM_REQUEST_CODE /*1*/:
                return "portrait";
            case SetExp.DEFINING_FLAG /*2*/:
                return "user";
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                return "behind";
            case SetExp.GLOBAL_FLAG /*4*/:
                return "sensor";
            case ArithOp.DIVIDE_INEXACT /*5*/:
                return "nosensor";
            case ArithOp.QUOTIENT /*6*/:
                return "sensorLandscape";
            case ArithOp.QUOTIENT_EXACT /*7*/:
                return "sensorPortrait";
            case SetExp.PREFER_BINDING2 /*8*/:
                return "reverseLandscape";
            case ArithOp.ASHIFT_GENERAL /*9*/:
                return "reversePortrait";
            case ArithOp.ASHIFT_LEFT /*10*/:
                return "fullSensor";
            default:
                return "unspecified";
        }
    }

    @DesignerProperty(defaultValue = "unspecified", editorType = "screen_orientation")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void ScreenOrientation(String screenOrientation) {
        if (screenOrientation.equalsIgnoreCase("behind")) {
            setRequestedOrientation(3);
        } else if (screenOrientation.equalsIgnoreCase("landscape")) {
            setRequestedOrientation(0);
        } else if (screenOrientation.equalsIgnoreCase("nosensor")) {
            setRequestedOrientation(5);
        } else if (screenOrientation.equalsIgnoreCase("portrait")) {
            setRequestedOrientation(SWITCH_FORM_REQUEST_CODE);
        } else if (screenOrientation.equalsIgnoreCase("sensor")) {
            setRequestedOrientation(4);
        } else if (screenOrientation.equalsIgnoreCase("unspecified")) {
            setRequestedOrientation(-1);
        } else if (screenOrientation.equalsIgnoreCase("user")) {
            setRequestedOrientation(2);
        } else if (SdkLevel.getLevel() < 9) {
            r1 = new Object[SWITCH_FORM_REQUEST_CODE];
            r1[0] = screenOrientation;
            dispatchErrorOccurredEvent(this, "ScreenOrientation", ErrorMessages.ERROR_INVALID_SCREEN_ORIENTATION, r1);
        } else if (screenOrientation.equalsIgnoreCase("fullSensor")) {
            setRequestedOrientation(10);
        } else if (screenOrientation.equalsIgnoreCase("reverseLandscape")) {
            setRequestedOrientation(8);
        } else if (screenOrientation.equalsIgnoreCase("reversePortrait")) {
            setRequestedOrientation(9);
        } else if (screenOrientation.equalsIgnoreCase("sensorLandscape")) {
            setRequestedOrientation(6);
        } else if (screenOrientation.equalsIgnoreCase("sensorPortrait")) {
            setRequestedOrientation(7);
        } else {
            r1 = new Object[SWITCH_FORM_REQUEST_CODE];
            r1[0] = screenOrientation;
            dispatchErrorOccurredEvent(this, "ScreenOrientation", ErrorMessages.ERROR_INVALID_SCREEN_ORIENTATION, r1);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how contents of the screen are aligned  horizontally. The choices are: 1 = left aligned, 2 = horizontally centered,  3 = right aligned.")
    public int AlignHorizontal() {
        return this.horizontalAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "horizontal_alignment")
    @SimpleProperty
    public void AlignHorizontal(int alignment) {
        try {
            this.alignmentSetter.setHorizontalAlignment(alignment);
            this.horizontalAlignment = alignment;
        } catch (IllegalArgumentException e) {
            Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
            objArr[0] = Integer.valueOf(alignment);
            dispatchErrorOccurredEvent(this, "HorizontalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_HORIZONTAL_ALIGNMENT, objArr);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how the contents of the arrangement are aligned vertically. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom. Vertical alignment has no effect if the screen is scrollable.")
    public int AlignVertical() {
        return this.verticalAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "vertical_alignment")
    @SimpleProperty
    public void AlignVertical(int alignment) {
        try {
            this.alignmentSetter.setVerticalAlignment(alignment);
            this.verticalAlignment = alignment;
        } catch (IllegalArgumentException e) {
            Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
            objArr[0] = Integer.valueOf(alignment);
            dispatchErrorOccurredEvent(this, "VerticalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT, objArr);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The animation for switching to another screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none")
    public String OpenScreenAnimation() {
        return this.openAnimType;
    }

    @DesignerProperty(defaultValue = "default", editorType = "screen_animation")
    @SimpleProperty
    public void OpenScreenAnimation(String animType) {
        if (animType == "default" || animType == "fade" || animType == "zoom" || animType == "slidehorizontal" || animType == "slidevertical" || animType == "none") {
            this.openAnimType = animType;
            return;
        }
        Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
        objArr[0] = animType;
        dispatchErrorOccurredEvent(this, "Screen", ErrorMessages.ERROR_SCREEN_INVALID_ANIMATION, objArr);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The animation for closing current screen and returning  to the previous screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none")
    public String CloseScreenAnimation() {
        return this.closeAnimType;
    }

    @DesignerProperty(defaultValue = "default", editorType = "screen_animation")
    @SimpleProperty
    public void CloseScreenAnimation(String animType) {
        if (animType == "default" || animType == "fade" || animType == "zoom" || animType == "slidehorizontal" || animType == "slidevertical" || animType == "none") {
            this.closeAnimType = animType;
            return;
        }
        Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
        objArr[0] = animType;
        dispatchErrorOccurredEvent(this, "Screen", ErrorMessages.ERROR_SCREEN_INVALID_ANIMATION, objArr);
    }

    public String getOpenAnimType() {
        return this.openAnimType;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty(userVisible = false)
    public void Icon(String name) {
    }

    @DesignerProperty(defaultValue = "1", editorType = "non_negative_integer")
    @SimpleProperty(description = "An integer value which must be incremented each time a new Android Application Package File (APK) is created for the Google Play Store.", userVisible = false)
    public void VersionCode(int vCode) {
    }

    @DesignerProperty(defaultValue = "1.0", editorType = "string")
    @SimpleProperty(description = "A string which can be changed to allow Google Play Store users to distinguish between different versions of the App.", userVisible = false)
    public void VersionName(String vName) {
    }

    @DesignerProperty(defaultValue = "Fixed", editorType = "sizing")
    @SimpleProperty(description = "If set to fixed,  screen layouts will be created for a single fixed-size screen and autoscaled. If set to responsive, screen layouts will use the actual resolution of the device.  See the documentation on responsive design in App Inventor for more information. This property appears on Screen1 only and controls the sizing for all screens in the app.", userVisible = false)
    public void Sizing(String value) {
        Log.d(LOG_TAG, "Sizing(" + value + ")");
        this.formWidth = (int) (((float) getResources().getDisplayMetrics().widthPixels) / this.deviceDensity);
        this.formHeight = (int) (((float) getResources().getDisplayMetrics().heightPixels) / this.deviceDensity);
        if (value.equals("Fixed")) {
            sCompatibilityMode = true;
            this.formWidth = (int) (((float) this.formWidth) / this.compatScalingFactor);
            this.formHeight = (int) (((float) this.formHeight) / this.compatScalingFactor);
        } else {
            sCompatibilityMode = false;
        }
        this.scaleLayout.setScale(sCompatibilityMode ? this.compatScalingFactor : 1.0f);
        if (this.frameLayout != null) {
            this.frameLayout.invalidate();
        }
        Log.d(LOG_TAG, "formWidth = " + this.formWidth + " formHeight = " + this.formHeight);
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "This is the display name of the installed application in the phone.If the AppName is blank, it will be set to the name of the project when the project is built.", userVisible = false)
    public void AppName(String aName) {
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Screen width (x-size).")
    public int Width() {
        Log.d(LOG_TAG, "Form.Width = " + this.formWidth);
        return this.formWidth;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Screen height (y-size).")
    public int Height() {
        Log.d(LOG_TAG, "Form.Height = " + this.formHeight);
        return this.formHeight;
    }

    public static void switchForm(String nextFormName) {
        if (activeForm != null) {
            activeForm.startNewForm(nextFormName, null);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void switchFormWithStartValue(String nextFormName, Object startValue) {
        Log.i(LOG_TAG, "Open another screen with start value:" + nextFormName);
        if (activeForm != null) {
            activeForm.startNewForm(nextFormName, startValue);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void startNewForm(String nextFormName, Object startupValue) {
        String jValue;
        Log.i(LOG_TAG, "startNewForm:" + nextFormName);
        Intent activityIntent = new Intent();
        activityIntent.setClassName(this, getPackageName() + "." + nextFormName);
        String functionName = startupValue == null ? "open another screen" : "open another screen with start value";
        if (startupValue != null) {
            Log.i(LOG_TAG, "StartNewForm about to JSON encode:" + startupValue);
            jValue = jsonEncodeForForm(startupValue, functionName);
            Log.i(LOG_TAG, "StartNewForm got JSON encoding:" + jValue);
        } else {
            jValue = ElementType.MATCH_ANY_LOCALNAME;
        }
        activityIntent.putExtra(ARGUMENT_NAME, jValue);
        this.nextFormName = nextFormName;
        Log.i(LOG_TAG, "about to start new form" + nextFormName);
        try {
            Log.i(LOG_TAG, "startNewForm starting activity:" + activityIntent);
            startActivityForResult(activityIntent, SWITCH_FORM_REQUEST_CODE);
            AnimationUtil.ApplyOpenScreenAnimation(this, this.openAnimType);
        } catch (ActivityNotFoundException e) {
            Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
            objArr[0] = nextFormName;
            dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_SCREEN_NOT_FOUND, objArr);
        }
    }

    protected static String jsonEncodeForForm(Object value, String functionName) {
        String jsonResult = ElementType.MATCH_ANY_LOCALNAME;
        Log.i(LOG_TAG, "jsonEncodeForForm -- creating JSON representation:" + value.toString());
        try {
            jsonResult = JsonUtil.getJsonRepresentation(value);
            Log.i(LOG_TAG, "jsonEncodeForForm -- got JSON representation:" + jsonResult);
            return jsonResult;
        } catch (JSONException e) {
            Form form = activeForm;
            Component component = activeForm;
            Object[] objArr = new Object[SWITCH_FORM_REQUEST_CODE];
            objArr[0] = value.toString();
            form.dispatchErrorOccurredEvent(component, functionName, ErrorMessages.ERROR_SCREEN_BAD_VALUE_FOR_SENDING, objArr);
            return jsonResult;
        }
    }

    @SimpleEvent(description = "Event raised when another screen has closed and control has returned to this screen.")
    public void OtherScreenClosed(String otherScreenName, Object result) {
        Log.i(LOG_TAG, "Form " + this.formName + " OtherScreenClosed, otherScreenName = " + otherScreenName + ", result = " + result.toString());
        EventDispatcher.dispatchEvent(this, "OtherScreenClosed", otherScreenName, result);
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this;
    }

    public Activity $context() {
        return this;
    }

    public Form $form() {
        return this;
    }

    public void $add(AndroidViewComponent component) {
        this.viewLayout.add(component);
    }

    public float deviceDensity() {
        return this.deviceDensity;
    }

    public float compatScalingFactor() {
        return this.compatScalingFactor;
    }

    public void setChildWidth(AndroidViewComponent component, int width) {
        int cWidth = Width();
        if (cWidth == 0) {
            this.androidUIHandler.postDelayed(new C00366(component, width), 100);
        }
        System.err.println("Form.setChildWidth(): width = " + width + " parent Width = " + cWidth + " child = " + component);
        if (width <= Component.LENGTH_PERCENT_TAG) {
            width = ((-(width + 1000)) * cWidth) / 100;
        }
        component.setLastWidth(width);
        ViewUtil.setChildWidthForVerticalLayout(component.getView(), width);
    }

    public void setChildHeight(AndroidViewComponent component, int height) {
        if (Height() == 0) {
            this.androidUIHandler.postDelayed(new C00377(component, height), 100);
        }
        if (height <= Component.LENGTH_PERCENT_TAG) {
            height = (Height() * (-(height + 1000))) / 100;
        }
        component.setLastHeight(height);
        ViewUtil.setChildHeightForVerticalLayout(component.getView(), height);
    }

    public static Form getActiveForm() {
        return activeForm;
    }

    public static String getStartText() {
        if (activeForm != null) {
            return activeForm.startupValue;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static Object getStartValue() {
        if (activeForm != null) {
            return decodeJSONStringForForm(activeForm.startupValue, "get start value");
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivity() {
        if (activeForm != null) {
            activeForm.closeForm(null);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivityWithResult(Object result) {
        if (activeForm == null) {
            throw new IllegalStateException("activeForm is null");
        } else if (activeForm instanceof ReplForm) {
            ((ReplForm) activeForm).setResult(result);
            activeForm.closeForm(null);
        } else {
            String jString = jsonEncodeForForm(result, "close screen with value");
            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_NAME, jString);
            activeForm.closeForm(resultIntent);
        }
    }

    public static void finishActivityWithTextResult(String result) {
        if (activeForm != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_NAME, result);
            activeForm.closeForm(resultIntent);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void closeForm(Intent resultIntent) {
        if (resultIntent != null) {
            setResult(-1, resultIntent);
        }
        finish();
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
    }

    public static void finishApplication() {
        if (activeForm != null) {
            activeForm.closeApplicationFromBlocks();
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void closeApplicationFromBlocks() {
        closeApplication();
    }

    private void closeApplicationFromMenu() {
        closeApplication();
    }

    private void closeApplication() {
        applicationIsBeingClosed = true;
        finish();
        if (this.formName.equals("Screen1")) {
            System.exit(0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        addExitButtonToMenu(menu);
        addAboutInfoToMenu(menu);
        for (OnCreateOptionsMenuListener onCreateOptionsMenuListener : this.onCreateOptionsMenuListeners) {
            onCreateOptionsMenuListener.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public void addExitButtonToMenu(Menu menu) {
        menu.add(0, 0, SWITCH_FORM_REQUEST_CODE, "Stop this application").setOnMenuItemClickListener(new C00388()).setIcon(17301594);
    }

    public void addAboutInfoToMenu(Menu menu) {
        menu.add(0, 0, 2, "About this application").setOnMenuItemClickListener(new C00399()).setIcon(17301651);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        for (OnOptionsItemSelectedListener onOptionsItemSelectedListener : this.onOptionsItemSelectedListeners) {
            if (onOptionsItemSelectedListener.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    private void showExitApplicationNotification() {
        Runnable stopApplication = new Runnable() {
            public void run() {
                Form.this.closeApplicationFromMenu();
            }
        };
        Runnable doNothing = new Runnable() {
            public void run() {
            }
        };
        Notifier.twoButtonDialog(this, "Stop this application and exit? You'll need to relaunch the application to use it again.", "Stop application?", "Stop and exit", "Don't stop", false, stopApplication, doNothing, doNothing);
    }

    void setYandexTranslateTagline() {
        this.yandexTranslateTagline = "<p><small>Language translation powered by Yandex.Translate</small></p>";
    }

    private void showAboutApplicationNotification() {
        Notifier.oneButtonAlert(this, (this.aboutScreen + "<p><small><em>Invented with MIT App Inventor<br>appinventor.mit.edu</em></small></p>" + this.yandexTranslateTagline).replaceAll("\\n", "<br>"), "About this app", "Got it");
    }

    public void clear() {
        this.viewLayout.getLayoutManager().removeAllViews();
        this.frameLayout.removeAllViews();
        this.frameLayout = null;
        defaultPropertyValues();
        this.onStopListeners.clear();
        this.onNewIntentListeners.clear();
        this.onResumeListeners.clear();
        this.onPauseListeners.clear();
        this.onDestroyListeners.clear();
        this.onInitializeListeners.clear();
        this.onCreateOptionsMenuListeners.clear();
        this.onOptionsItemSelectedListeners.clear();
        this.screenInitialized = false;
        System.err.println("Form.clear() About to do moby GC!");
        System.gc();
        this.dimChanges.clear();
    }

    public void deleteComponent(Object component) {
        if (component instanceof OnStopListener) {
            OnStopListener onStopListener = (OnStopListener) component;
            if (this.onStopListeners.contains(onStopListener)) {
                this.onStopListeners.remove(onStopListener);
            }
        }
        if (component instanceof OnNewIntentListener) {
            OnNewIntentListener onNewIntentListener = (OnNewIntentListener) component;
            if (this.onNewIntentListeners.contains(onNewIntentListener)) {
                this.onNewIntentListeners.remove(onNewIntentListener);
            }
        }
        if (component instanceof OnResumeListener) {
            OnResumeListener onResumeListener = (OnResumeListener) component;
            if (this.onResumeListeners.contains(onResumeListener)) {
                this.onResumeListeners.remove(onResumeListener);
            }
        }
        if (component instanceof OnPauseListener) {
            OnPauseListener onPauseListener = (OnPauseListener) component;
            if (this.onPauseListeners.contains(onPauseListener)) {
                this.onPauseListeners.remove(onPauseListener);
            }
        }
        if (component instanceof OnDestroyListener) {
            OnDestroyListener onDestroyListener = (OnDestroyListener) component;
            if (this.onDestroyListeners.contains(onDestroyListener)) {
                this.onDestroyListeners.remove(onDestroyListener);
            }
        }
        if (component instanceof OnInitializeListener) {
            OnInitializeListener onInitializeListener = (OnInitializeListener) component;
            if (this.onInitializeListeners.contains(onInitializeListener)) {
                this.onInitializeListeners.remove(onInitializeListener);
            }
        }
        if (component instanceof OnCreateOptionsMenuListener) {
            OnCreateOptionsMenuListener onCreateOptionsMenuListener = (OnCreateOptionsMenuListener) component;
            if (this.onCreateOptionsMenuListeners.contains(onCreateOptionsMenuListener)) {
                this.onCreateOptionsMenuListeners.remove(onCreateOptionsMenuListener);
            }
        }
        if (component instanceof OnOptionsItemSelectedListener) {
            OnOptionsItemSelectedListener onOptionsItemSelectedListener = (OnOptionsItemSelectedListener) component;
            if (this.onOptionsItemSelectedListeners.contains(onOptionsItemSelectedListener)) {
                this.onOptionsItemSelectedListeners.remove(onOptionsItemSelectedListener);
            }
        }
        if (component instanceof Deleteable) {
            ((Deleteable) component).onDelete();
        }
    }

    public void dontGrabTouchEventsForComponent() {
        this.frameLayout.requestDisallowInterceptTouchEvent(true);
    }

    protected boolean toastAllowed() {
        long now = System.nanoTime();
        if (now <= this.lastToastTime + minimumToastWait) {
            return false;
        }
        this.lastToastTime = now;
        return true;
    }

    public void callInitialize(Object component) throws Throwable {
        try {
            Method method = component.getClass().getMethod("Initialize", (Class[]) null);
            try {
                Log.i(LOG_TAG, "calling Initialize method for Object " + component.toString());
                method.invoke(component, (Object[]) null);
            } catch (InvocationTargetException e) {
                Log.i(LOG_TAG, "invoke exception: " + e.getMessage());
                throw e.getTargetException();
            }
        } catch (SecurityException e2) {
            Log.i(LOG_TAG, "Security exception " + e2.getMessage());
        } catch (NoSuchMethodException e3) {
        }
    }

    public synchronized Bundle fullScreenVideoAction(int action, VideoPlayer source, Object data) {
        return this.fullScreenVideoUtil.performAction(action, source, data);
    }

    private void setBackground(View bgview) {
        int i = -1;
        Drawable setDraw = this.backgroundDrawable;
        if (this.backgroundImagePath == ElementType.MATCH_ANY_LOCALNAME || setDraw == null) {
            if (this.backgroundColor != 0) {
                i = this.backgroundColor;
            }
            setDraw = new ColorDrawable(i);
        } else {
            setDraw = this.backgroundDrawable.getConstantState().newDrawable();
            if (this.backgroundColor != 0) {
                i = this.backgroundColor;
            }
            setDraw.setColorFilter(i, Mode.DST_OVER);
        }
        ViewUtil.setBackgroundImage(bgview, setDraw);
        bgview.invalidate();
    }

    public static boolean getCompatibilityMode() {
        return sCompatibilityMode;
    }

    @SimpleFunction(description = "Hide the onscreen soft keyboard.")
    public void HideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            dispatchErrorOccurredEvent(this, "HideKeyboard", ErrorMessages.ERROR_NO_FOCUSABLE_VIEW_FOUND, new Object[0]);
        }
    }
}
