package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.SdkLevel;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "The Notifier component displays alert dialogs, messages, and temporary alerts, and creates Android log entries through the following methods: <ul><li> ShowMessageDialog: displays a message which the user must dismiss by pressing a button.</li><li> ShowChooseDialog: displays a message two buttons to let the user choose one of two responses, for example, yes or no, after which the AfterChoosing event is raised.</li><li> ShowTextDialog: lets the user enter text in response to the message, after which the AfterTextInput event is raised. <li> ShowAlert: displays a temporary  alert that goes away by itself after a short time.</li><li> ShowProgressDialog: displays an alert with a loading spinner that cannot be dismissed by the user. It can only be dismissed by using the DismissProgressDialog block.</li><li> DismissProgressDialog: Dismisses the progress dialog displayed by ShowProgressDialog.</li><li> LogError: logs an error message to the Android log. </li><li> LogInfo: logs an info message to the Android log.</li><li> LogWarning: logs a warning message to the Android log.</li><li>The messages in the dialogs (but not the alert) can be formatted using the following HTML tags:&lt;b&gt;, &lt;big&gt;, &lt;blockquote&gt;, &lt;br&gt;, &lt;cite&gt;, &lt;dfn&gt;, &lt;div&gt;, &lt;em&gt;, &lt;small&gt;, &lt;strong&gt;, &lt;sub&gt;, &lt;sup&gt;, &lt;tt&gt;. &lt;u&gt;</li><li>You can also use the font tag to specify color, for example, &lt;font color=\"blue\"&gt;.  Some of the available color names are aqua, black, blue, fuchsia, green, grey, lime, maroon, navy, olive, purple, red, silver, teal, white, and yellow</li></ul>", iconName = "images/notifier.png", nonVisible = true, version = 4)
public final class Notifier extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "Notifier";
    private final Activity activity;
    private int backgroundColor;
    private final Handler handler;
    private int notifierLength;
    private ProgressDialog progressDialog;
    private int textColor;

    /* renamed from: com.google.appinventor.components.runtime.Notifier.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ String val$notice;

        AnonymousClass10(String str) {
            this.val$notice = str;
        }

        public void run() {
            Notifier.this.toastNow(this.val$notice);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.1 */
    static class C00611 implements OnClickListener {
        C00611() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.2 */
    class C00622 implements Runnable {
        final /* synthetic */ String val$button1Text;

        C00622(String str) {
            this.val$button1Text = str;
        }

        public void run() {
            Notifier.this.AfterChoosing(this.val$button1Text);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.3 */
    class C00633 implements Runnable {
        final /* synthetic */ String val$button2Text;

        C00633(String str) {
            this.val$button2Text = str;
        }

        public void run() {
            Notifier.this.AfterChoosing(this.val$button2Text);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.4 */
    class C00644 implements Runnable {
        C00644() {
        }

        public void run() {
            Notifier.this.AfterChoosing(Notifier.this.activity.getString(17039360));
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.5 */
    static class C00655 implements OnClickListener {
        final /* synthetic */ Runnable val$positiveAction;

        C00655(Runnable runnable) {
            this.val$positiveAction = runnable;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.val$positiveAction.run();
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.6 */
    static class C00666 implements OnClickListener {
        final /* synthetic */ Runnable val$negativeAction;

        C00666(Runnable runnable) {
            this.val$negativeAction = runnable;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.val$negativeAction.run();
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.7 */
    static class C00677 implements OnClickListener {
        final /* synthetic */ Runnable val$cancelAction;

        C00677(Runnable runnable) {
            this.val$cancelAction = runnable;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.val$cancelAction.run();
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.8 */
    class C00688 implements OnClickListener {
        final /* synthetic */ EditText val$input;

        C00688(EditText editText) {
            this.val$input = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            Notifier.this.HideKeyboard(this.val$input);
            Notifier.this.AfterTextInput(this.val$input.getText().toString());
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Notifier.9 */
    class C00699 implements OnClickListener {
        final /* synthetic */ String val$cancelButtonText;
        final /* synthetic */ EditText val$input;

        C00699(EditText editText, String str) {
            this.val$input = editText;
            this.val$cancelButtonText = str;
        }

        public void onClick(DialogInterface dialog, int which) {
            Notifier.this.HideKeyboard(this.val$input);
            Notifier.this.AfterTextInput(this.val$cancelButtonText);
        }
    }

    public Notifier(ComponentContainer container) {
        super(container.$form());
        this.notifierLength = 1;
        this.backgroundColor = Component.COLOR_DKGRAY;
        this.textColor = -1;
        this.activity = container.$context();
        this.handler = new Handler();
        this.progressDialog = null;
    }

    @SimpleFunction(description = "Shows a dialog box with an optional title and message (use empty strings if they are not wanted). This dialog box contains a spinning artifact to indicate that the program is working. It cannot be canceled by the user but must be dismissed by the App Inventor Program by using the DismissProgressDialog block.")
    public void ShowProgressDialog(String message, String title) {
        progressDialog(message, title);
    }

    @SimpleFunction(description = "Dismiss a previously displayed ProgressDialog box")
    public void DismissProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
            this.progressDialog = null;
        }
    }

    public void progressDialog(String message, String title) {
        if (this.progressDialog != null) {
            DismissProgressDialog();
        }
        this.progressDialog = ProgressDialog.show(this.activity, title, message);
        this.progressDialog.setCancelable(false);
    }

    @SimpleFunction
    public void ShowMessageDialog(String message, String title, String buttonText) {
        oneButtonAlert(this.activity, message, title, buttonText);
    }

    public static void oneButtonAlert(Activity activity, String message, String title, String buttonText) {
        Log.i(LOG_TAG, "One button alert " + message);
        AlertDialog alertDialog = new Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(stringToHTML(message));
        alertDialog.setButton(-3, buttonText, new C00611());
        alertDialog.show();
    }

    private static SpannableString stringToHTML(String message) {
        return new SpannableString(Html.fromHtml(message));
    }

    @SimpleFunction(description = "Shows a dialog box with two buttons, from which the user can choose.  If cancelable is true there will be an additional CANCEL button. Pressing a button will raise the AfterChoosing event.  The \"choice\" parameter to AfterChoosing will be the text on the button that was pressed, or \"Cancel\" if the  CANCEL button was pressed.")
    public void ShowChooseDialog(String message, String title, String button1Text, String button2Text, boolean cancelable) {
        twoButtonDialog(this.activity, message, title, button1Text, button2Text, cancelable, new C00622(button1Text), new C00633(button2Text), new C00644());
    }

    public static void twoButtonDialog(Activity activity, String message, String title, String button1Text, String button2Text, boolean cancelable, Runnable positiveAction, Runnable negativeAction, Runnable cancelAction) {
        Log.i(LOG_TAG, "ShowChooseDialog: " + message);
        AlertDialog alertDialog = new Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(stringToHTML(message));
        alertDialog.setButton(-1, button1Text, new C00655(positiveAction));
        alertDialog.setButton(-3, button2Text, new C00666(negativeAction));
        if (cancelable) {
            alertDialog.setButton(-2, activity.getString(17039360), new C00677(cancelAction));
        }
        alertDialog.show();
    }

    @SimpleEvent
    public void AfterChoosing(String choice) {
        EventDispatcher.dispatchEvent(this, "AfterChoosing", choice);
    }

    @SimpleFunction(description = "Shows a dialog box where the user can enter text, after which the AfterTextInput event will be raised.  If cancelable is true there will be an additional CANCEL button. Entering text will raise the AfterTextInput event.  The \"response\" parameter to AfterTextInput will be the text that was entered, or \"Cancel\" if the CANCEL button was pressed.")
    public void ShowTextDialog(String message, String title, boolean cancelable) {
        textInputDialog(message, title, cancelable);
    }

    private void textInputDialog(String message, String title, boolean cancelable) {
        AlertDialog alertDialog = new Builder(this.activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(stringToHTML(message));
        EditText input = new EditText(this.activity);
        alertDialog.setView(input);
        alertDialog.setCancelable(false);
        alertDialog.setButton(-1, "OK", new C00688(input));
        if (cancelable) {
            String cancelButtonText = this.activity.getString(17039360);
            alertDialog.setButton(-2, cancelButtonText, new C00699(input, cancelButtonText));
        }
        alertDialog.show();
    }

    public void HideKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) this.activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @SimpleEvent
    public void AfterTextInput(String response) {
        EventDispatcher.dispatchEvent(this, "AfterTextInput", response);
    }

    @SimpleFunction
    public void ShowAlert(String notice) {
        this.handler.post(new AnonymousClass10(notice));
    }

    @DesignerProperty(defaultValue = "1", editorType = "toast_length")
    @SimpleProperty(userVisible = false)
    public void NotifierLength(int length) {
        this.notifierLength = length;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "specifies the length of time that the alert is shown -- either \"short\" or \"long\".")
    public int NotifierLength() {
        return this.notifierLength;
    }

    @DesignerProperty(defaultValue = "&HFF444444", editorType = "color")
    @SimpleProperty(description = "Specifies the background color for alerts (not dialogs).")
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Specifies the text color for alerts (not dialogs).")
    public int TextColor() {
        return this.textColor;
    }

    @DesignerProperty(defaultValue = "&HFFFFFFFF", editorType = "color")
    @SimpleProperty
    public void TextColor(int argb) {
        this.textColor = argb;
    }

    private void toastNow(String message) {
        int fontsize = SdkLevel.getLevel() >= 14 ? 22 : 15;
        Toast toast = Toast.makeText(this.activity, message, this.notifierLength);
        toast.setGravity(17, toast.getXOffset() / 2, toast.getYOffset() / 2);
        TextView textView = new TextView(this.activity);
        textView.setBackgroundColor(this.backgroundColor);
        textView.setTextColor(this.textColor);
        textView.setTextSize((float) fontsize);
        textView.setTypeface(Typeface.create(Typeface.SANS_SERIF, 0));
        textView.setPadding(10, 10, 10, 10);
        textView.setText(message + " ");
        toast.setView(textView);
        toast.show();
    }

    @SimpleFunction(description = "Writes an error message to the Android system log. See the Google Android documentation for how to access the log.")
    public void LogError(String message) {
        Log.e(LOG_TAG, message);
    }

    @SimpleFunction(description = "Writes a warning message to the Android log. See the Google Android documentation for how to access the log.")
    public void LogWarning(String message) {
        Log.w(LOG_TAG, message);
    }

    @SimpleFunction(description = "Writes an information message to the Android log.")
    public void LogInfo(String message) {
        Log.i(LOG_TAG, message);
    }
}
