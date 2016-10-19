package com.google.appinventor.components.runtime;

import android.app.Activity;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import com.google.appinventor.components.runtime.util.Ev3Constants.SystemCommand;
import com.google.appinventor.components.runtime.util.Ev3Constants.SystemCommandType;
import com.google.appinventor.components.runtime.util.Ev3Constants.UIDrawSubcode;
import com.google.appinventor.components.runtime.util.Ev3Constants.UIReadSubcode;
import com.google.appinventor.components.runtime.util.Ev3Constants.UIWriteSubcode;
import gnu.bytecode.ConstantPool;
import gnu.expr.LambdaExp;
import gnu.kawa.xml.ElementType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MEDIA, description = "Use this component to translate words and sentences between different languages. This component needs Internet access, as it will request translations to the Yandex.Translate service. Specify the source and target language in the form source-target using two letter language codes. So\"en-es\" will translate from English to Spanish while \"es-ru\" will translate from Spanish to Russian. If you leave out the source language, the service will attempt to detect the source language. So providing just \"es\" will attempt to detect the source language and translate it to Spanish.<p /> This component is powered by the Yandex translation service.  See http://api.yandex.com/translate/ for more information, including the list of available languages and the meanings of the language codes and status codes. <p />Note: Translation happens asynchronously in the background. When the translation is complete, the \"GotTranslation\" event is triggered.", iconName = "images/yandex.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class YandexTranslate extends AndroidNonvisibleComponent {
    public static final String YANDEX_TRANSLATE_SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    private final Activity activity;
    private final byte[] key1;
    private final byte[] key2;
    private final String yandexKey;

    /* renamed from: com.google.appinventor.components.runtime.YandexTranslate.1 */
    class C01321 implements Runnable {
        final /* synthetic */ String val$languageToTranslateTo;
        final /* synthetic */ String val$textToTranslate;

        C01321(String str, String str2) {
            this.val$languageToTranslateTo = str;
            this.val$textToTranslate = str2;
        }

        public void run() {
            try {
                YandexTranslate.this.performRequest(this.val$languageToTranslateTo, this.val$textToTranslate);
            } catch (IOException e) {
                YandexTranslate.this.form.dispatchErrorOccurredEvent(YandexTranslate.this, "RequestTranslation", ErrorMessages.ERROR_TRANSLATE_SERVICE_NOT_AVAILABLE, new Object[0]);
            } catch (JSONException e2) {
                YandexTranslate.this.form.dispatchErrorOccurredEvent(YandexTranslate.this, "RequestTranslation", ErrorMessages.ERROR_TRANSLATE_JSON_RESPONSE, new Object[0]);
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.YandexTranslate.2 */
    class C01332 implements Runnable {
        final /* synthetic */ String val$responseCode;
        final /* synthetic */ String val$translation;

        C01332(String str, String str2) {
            this.val$responseCode = str;
            this.val$translation = str2;
        }

        public void run() {
            YandexTranslate.this.GotTranslation(this.val$responseCode, this.val$translation);
        }
    }

    public YandexTranslate(ComponentContainer container) {
        super(container.$form());
        this.key1 = new byte[]{SystemCommandType.SYSTEM_COMMAND_NO_REPLY, Opcode.OUTPUT_READ, Opcode.CP_EQF, Opcode.CP_NEQ8, Opcode.JR_FALSE, Opcode.JR_NEQ8, Opcode.OUTPUT_STEP_SYNC, Opcode.CP_LTEQF, Opcode.ARRAY_WRITE, Opcode.MEMORY_WRITE, Opcode.UI_BUTTON, (byte) -25, (byte) -31, Opcode.MOVE16_F, Opcode.JR_GTF, Opcode.COM_REMOVE, Opcode.ARRAY, Opcode.ARRAY_WRITE, Opcode.OR16, Opcode.TIMER_READY, (byte) 1, Opcode.CP_GTEQ16, (byte) -33, UIDrawSubcode.VIEW_UNIT, (byte) -19, UIReadSubcode.GET_LBATT, Opcode.OUTPUT_TIME_SPEED, Opcode.AND16, (byte) -67, Opcode.JR_NEQ32, Opcode.SELECT8, Opcode.ARRAY_APPEND, Opcode.OUTPUT_PRG_STOP, Opcode.WRITE32, Opcode.MEMORY_USAGE, Opcode.WRITEF, Opcode.RANDOM, Opcode.FILE, SystemCommand.ENTERFWUPDATE, (byte) -75, Opcode.JR_LTEQ16, Opcode.BP_SET, Opcode.MOVE16_16, (byte) -8, Opcode.RL8, Opcode.JR_EQF, Opcode.JR_GTEQ8, Opcode.MOVE8_8, Opcode.XOR16, UIWriteSubcode.GRAPH_SAMPLE, Opcode.CP_LTEQ16, Opcode.BP_SET, (byte) -31, UIReadSubcode.GET_WARNING, Opcode.CP_LTEQF, Opcode.OUTPUT_POLARITY, Opcode.WRITEF, Opcode.WRITE16, Opcode.INIT_BYTES, Opcode.SELECT8, Opcode.JR_GTEQ16, Opcode.FILENAME, Opcode.OUTPUT_STEP_SYNC, (byte) -25, Opcode.CP_LTEQ32, Opcode.JR_GTEQF, Opcode.MAILBOX_READY, (byte) -9, Opcode.JR_LT16, Opcode.KEEP_ALIVE, (byte) -22, (byte) -28, (byte) -29, (byte) -14, Opcode.UI_BUTTON, Opcode.RL32, SystemCommand.LIST_FILES, Opcode.MAILBOX_READY, Opcode.STRINGS, Opcode.JR_NEQ32, (byte) 35, (byte) -31, (byte) 1, Opcode.JR_GTEQF};
        this.key2 = new byte[]{(byte) -11, Opcode.MAILBOX_READ, Opcode.OR16, (byte) 35, Opcode.RL16, Opcode.SELECT32, SystemCommandType.SYSTEM_COMMAND_NO_REPLY, Opcode.JR_GTEQ16, (byte) -13, Opcode.CP_NEQ8, Opcode.OUTPUT_TIME_SYNC, Opcode.COM_WRITEFILE, Opcode.COM_READY, (byte) 3, Opcode.CP_GTEQF, (byte) -29, (byte) -15, (byte) -9, Opcode.JR_LTEQ16, (byte) -74, Opcode.MOVE8_16, Opcode.JR_GT16, (byte) -26, Opcode.OR32, Opcode.MAILBOX_CLOSE, Opcode.CP_GT8, SystemCommandType.SYSTEM_COMMAND_NO_REPLY, Opcode.JR, Opcode.BP_SET, Opcode.CP_LT16, Opcode.JR_EQF, (byte) -12, Opcode.COM_READY, Opcode.OUTPUT_TIME_SPEED, (byte) -11, Opcode.OUTPUT_TIME_POWER, (byte) -69, (byte) -12, SystemCommand.BEGIN_UPLOAD, Opcode.COM_REMOVE, Opcode.JR_FALSE, (byte) -72, Opcode.CP_LTEQ32, Opcode.COM_REMOVE, UIWriteSubcode.LED, ConstantPool.NAME_AND_TYPE, UIWriteSubcode.UPDATE_RUN, (byte) 2, UIDrawSubcode.BMPFILE, Opcode.JR_GTEQ32, Opcode.MOVE8_F, (byte) -24, Opcode.COM_GET, Opcode.AND8, Opcode.MOVE16_32, SystemCommand.BEGIN_GETFILE, Opcode.OUTPUT_TEST, (byte) -3, UIWriteSubcode.LED, Opcode.MOVEF_32, Opcode.JR_FALSE, (byte) -16, Opcode.UI_WRITE, Opcode.COM_REMOVE, Opcode.NOTE_TO_FREQ, Opcode.CP_EQ16, (byte) -70, Opcode.WRITEF, Opcode.CP_NEQF, (byte) -12, Opcode.RANDOM, Opcode.MAILBOX_CLOSE, Opcode.COM_SET, SystemCommand.CONTINUE_DOWNLOAD, Opcode.OUTPUT_GET_COUNT, UIDrawSubcode.BMPFILE, Opcode.OUTPUT_STEP_POWER, (byte) -66, Opcode.CP_GT8, UIWriteSubcode.SET_BUSY, UIReadSubcode.GET_LBATT, Opcode.UI_WRITE, Opcode.MOVE8_32, Opcode.CP_EQ32};
        this.form.setYandexTranslateTagline();
        this.yandexKey = gk();
        this.activity = container.$context();
    }

    @SimpleFunction(description = "By providing a target language to translate to (for instance, 'es' for Spanish, 'en' for English, or 'ru' for Russian), and a word or sentence to translate, this method will request a translation to the Yandex.Translate service.\nOnce the text is translated by the external service, the event GotTranslation will be executed.\nNote: Yandex.Translate will attempt to detect the source language. You can also specify prepending it to the language translation. I.e., es-ru will specify Spanish to Russian translation.")
    public void RequestTranslation(String languageToTranslateTo, String textToTranslate) {
        if (this.yandexKey.equals(ElementType.MATCH_ANY_LOCALNAME)) {
            this.form.dispatchErrorOccurredEvent(this, "RequestTranslation", ErrorMessages.ERROR_TRANSLATE_NO_KEY_FOUND, new Object[0]);
        } else {
            AsynchUtil.runAsynchronously(new C01321(languageToTranslateTo, textToTranslate));
        }
    }

    private void performRequest(String languageToTranslateTo, String textToTranslate) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(YANDEX_TRANSLATE_SERVICE_URL + this.yandexKey + "&lang=" + languageToTranslateTo + "&text=" + URLEncoder.encode(textToTranslate, "UTF-8")).openConnection();
        if (connection != null) {
            try {
                JSONObject jsonResponse = new JSONObject(getResponseContent(connection));
                this.activity.runOnUiThread(new C01332(jsonResponse.getString("code"), (String) jsonResponse.getJSONArray(PropertyTypeConstants.PROPERTY_TYPE_TEXT).get(0)));
            } finally {
                connection.disconnect();
            }
        }
    }

    private static String getResponseContent(HttpURLConnection connection) throws IOException {
        String encoding = connection.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), encoding);
        try {
            int contentLength = connection.getContentLength();
            StringBuilder sb = contentLength != -1 ? new StringBuilder(contentLength) : new StringBuilder();
            char[] buf = new char[LambdaExp.SEQUENCE_RESULT];
            while (true) {
                int read = reader.read(buf);
                if (read == -1) {
                    break;
                }
                sb.append(buf, 0, read);
            }
            String stringBuilder = sb.toString();
            return stringBuilder;
        } finally {
            reader.close();
        }
    }

    @SimpleEvent(description = "Event triggered when the Yandex.Translate service returns the translated text. This event also provides a response code for error handling. If the responseCode is not 200, then something went wrong with the call, and the translation will not be available.")
    public void GotTranslation(String responseCode, String translation) {
        EventDispatcher.dispatchEvent(this, "GotTranslation", responseCode, translation);
    }

    private String gk() {
        byte[] retval = new byte[this.key1.length];
        for (int i = 0; i < this.key1.length; i++) {
            retval[i] = (byte) (this.key1[i] ^ this.key2[i]);
        }
        return new String(retval);
    }
}
