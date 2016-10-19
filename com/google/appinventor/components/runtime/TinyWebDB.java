package com.google.appinventor.components.runtime;

import android.os.Handler;
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
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.WebServiceUtil;
import gnu.kawa.xml.ElementType;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

@SimpleObject
@DesignerComponent(category = ComponentCategory.STORAGE, description = "Non-visible component that communicates with a Web service to store and retrieve information.", iconName = "images/tinyWebDB.png", nonVisible = true, version = 2)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class TinyWebDB extends AndroidNonvisibleComponent implements Component {
    private static final String GETVALUE_COMMAND = "getvalue";
    private static final String LOG_TAG = "TinyWebDB";
    private static final String STOREAVALUE_COMMAND = "storeavalue";
    private static final String TAG_PARAMETER = "tag";
    private static final String VALUE_PARAMETER = "value";
    private Handler androidUIHandler;
    private String serviceURL;

    /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.1 */
    class C00861 implements Runnable {
        final /* synthetic */ String val$tag;
        final /* synthetic */ Object val$valueToStore;

        C00861(String str, Object obj) {
            this.val$tag = str;
            this.val$valueToStore = obj;
        }

        public void run() {
            TinyWebDB.this.postStoreValue(this.val$tag, this.val$valueToStore);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.3 */
    class C00893 implements Runnable {
        final /* synthetic */ String val$tag;

        C00893(String str) {
            this.val$tag = str;
        }

        public void run() {
            TinyWebDB.this.postGetValue(this.val$tag);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.2 */
    class C01782 implements AsyncCallbackPair<String> {

        /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.2.1 */
        class C00871 implements Runnable {
            C00871() {
            }

            public void run() {
                TinyWebDB.this.ValueStored();
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.2.2 */
        class C00882 implements Runnable {
            final /* synthetic */ String val$message;

            C00882(String str) {
                this.val$message = str;
            }

            public void run() {
                TinyWebDB.this.WebServiceError(this.val$message);
            }
        }

        C01782() {
        }

        public void onSuccess(String response) {
            TinyWebDB.this.androidUIHandler.post(new C00871());
        }

        public void onFailure(String message) {
            TinyWebDB.this.androidUIHandler.post(new C00882(message));
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.4 */
    class C01794 implements AsyncCallbackPair<JSONArray> {
        final /* synthetic */ String val$tag;

        /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.4.1 */
        class C00901 implements Runnable {
            C00901() {
            }

            public void run() {
                TinyWebDB.this.WebServiceError("The Web server did not respond to the get value request for the tag " + C01794.this.val$tag + ".");
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.4.2 */
        class C00912 implements Runnable {
            final /* synthetic */ String val$tagFromWebDB;
            final /* synthetic */ Object val$valueFromWebDB;

            C00912(String str, Object obj) {
                this.val$tagFromWebDB = str;
                this.val$valueFromWebDB = obj;
            }

            public void run() {
                TinyWebDB.this.GotValue(this.val$tagFromWebDB, this.val$valueFromWebDB);
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.4.3 */
        class C00923 implements Runnable {
            C00923() {
            }

            public void run() {
                TinyWebDB.this.WebServiceError("The Web server returned a garbled value for the tag " + C01794.this.val$tag + ".");
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.TinyWebDB.4.4 */
        class C00934 implements Runnable {
            final /* synthetic */ String val$message;

            C00934(String str) {
                this.val$message = str;
            }

            public void run() {
                TinyWebDB.this.WebServiceError(this.val$message);
            }
        }

        C01794(String str) {
            this.val$tag = str;
        }

        public void onSuccess(JSONArray result) {
            if (result == null) {
                TinyWebDB.this.androidUIHandler.post(new C00901());
                return;
            }
            try {
                String tagFromWebDB = result.getString(1);
                String value = result.getString(2);
                TinyWebDB.this.androidUIHandler.post(new C00912(tagFromWebDB, value.length() == 0 ? ElementType.MATCH_ANY_LOCALNAME : JsonUtil.getObjectFromJson(value)));
            } catch (JSONException e) {
                TinyWebDB.this.androidUIHandler.post(new C00923());
            }
        }

        public void onFailure(String message) {
            TinyWebDB.this.androidUIHandler.post(new C00934(message));
        }
    }

    public TinyWebDB(ComponentContainer container) {
        super(container.$form());
        this.androidUIHandler = new Handler();
        this.serviceURL = "http://appinvtinywebdb.appspot.com/";
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ServiceURL() {
        return this.serviceURL;
    }

    @DesignerProperty(defaultValue = "http://appinvtinywebdb.appspot.com", editorType = "string")
    @SimpleProperty
    public void ServiceURL(String url) {
        this.serviceURL = url;
    }

    @SimpleFunction
    public void StoreValue(String tag, Object valueToStore) {
        AsynchUtil.runAsynchronously(new C00861(tag, valueToStore));
    }

    private void postStoreValue(String tag, Object valueToStore) {
        AsyncCallbackPair<String> myCallback = new C01782();
        try {
            WebServiceUtil.getInstance().postCommand(this.serviceURL, STOREAVALUE_COMMAND, Lists.newArrayList(new BasicNameValuePair(TAG_PARAMETER, tag), new BasicNameValuePair(VALUE_PARAMETER, JsonUtil.getJsonRepresentation(valueToStore))), myCallback);
        } catch (JSONException e) {
            throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
        }
    }

    @SimpleEvent
    public void ValueStored() {
        EventDispatcher.dispatchEvent(this, "ValueStored", new Object[0]);
    }

    @SimpleFunction
    public void GetValue(String tag) {
        AsynchUtil.runAsynchronously(new C00893(tag));
    }

    private void postGetValue(String tag) {
        AsyncCallbackPair<JSONArray> myCallback = new C01794(tag);
        WebServiceUtil.getInstance().postCommandReturningArray(this.serviceURL, GETVALUE_COMMAND, Lists.newArrayList(new BasicNameValuePair(TAG_PARAMETER, tag)), myCallback);
    }

    @SimpleEvent
    public void GotValue(String tagFromWebDB, Object valueFromWebDB) {
        EventDispatcher.dispatchEvent(this, "GotValue", tagFromWebDB, valueFromWebDB);
    }

    @SimpleEvent
    public void WebServiceError(String message) {
        EventDispatcher.dispatchEvent(this, "WebServiceError", message);
    }
}
