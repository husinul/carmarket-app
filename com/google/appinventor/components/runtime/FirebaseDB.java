package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Config;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.Firebase.AuthStateListener;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Result;
import com.firebase.client.ValueEventListener;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.kawa.xml.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;

@DesignerComponent(category = ComponentCategory.EXPERIMENTAL, description = "Non-visible component that communicates with Firebase to store and retrieve information.", designerHelpDescription = "Non-visible component that communicates with a Firebase to store and retrieve information.", iconName = "images/firebaseDB.png", nonVisible = true, version = 3)
@UsesLibraries(libraries = "firebase.jar")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class FirebaseDB extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "Firebase";
    private static boolean isInitialized;
    private static boolean persist;
    private final Activity activity;
    private Handler androidUIHandler;
    private AuthStateListener authListener;
    private ChildEventListener childListener;
    private String defaultURL;
    private String developerBucket;
    private String firebaseToken;
    private String firebaseURL;
    private Firebase myFirebase;
    private String projectBucket;
    private boolean useDefault;

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.1 */
    class C00181 implements ChildEventListener {

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.1.1 */
        class C00151 implements Runnable {
            final /* synthetic */ DataSnapshot val$snapshot;

            C00151(DataSnapshot dataSnapshot) {
                this.val$snapshot = dataSnapshot;
            }

            public void run() {
                FirebaseDB.this.DataChanged(this.val$snapshot.getKey(), this.val$snapshot.getValue());
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.1.2 */
        class C00162 implements Runnable {
            final /* synthetic */ FirebaseError val$error;

            C00162(FirebaseError firebaseError) {
                this.val$error = firebaseError;
            }

            public void run() {
                FirebaseDB.this.FirebaseError(this.val$error.getMessage());
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.1.3 */
        class C00173 implements Runnable {
            final /* synthetic */ DataSnapshot val$snapshot;

            C00173(DataSnapshot dataSnapshot) {
                this.val$snapshot = dataSnapshot;
            }

            public void run() {
                FirebaseDB.this.DataChanged(this.val$snapshot.getKey(), this.val$snapshot.getValue());
            }
        }

        C00181() {
        }

        public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
            FirebaseDB.this.androidUIHandler.post(new C00151(snapshot));
        }

        public void onCancelled(FirebaseError error) {
            FirebaseDB.this.androidUIHandler.post(new C00162(error));
        }

        public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
            FirebaseDB.this.androidUIHandler.post(new C00173(snapshot));
        }

        public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
        }

        public void onChildRemoved(DataSnapshot snapshot) {
            Log.i(FirebaseDB.LOG_TAG, "onChildRemoved: " + snapshot.getKey() + " removed.");
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.2 */
    class C00202 implements AuthStateListener {

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.2.1 */
        class C00191 implements AuthResultHandler {
            C00191() {
            }

            public void onAuthenticated(AuthData authData) {
                Log.i(FirebaseDB.LOG_TAG, "Auth Successful.");
            }

            public void onAuthenticationError(FirebaseError error) {
                Log.e(FirebaseDB.LOG_TAG, "Auth Failed: " + error.getMessage());
            }
        }

        C00202() {
        }

        public void onAuthStateChanged(AuthData data) {
            Log.i(FirebaseDB.LOG_TAG, "onAuthStateChanged: data = " + data);
            if (data == null) {
                FirebaseDB.this.myFirebase.authWithCustomToken(FirebaseDB.this.firebaseToken, new C00191());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.3 */
    class C00233 implements ValueEventListener {
        final /* synthetic */ String val$tag;
        final /* synthetic */ Object val$valueIfTagNotThere;

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.3.1 */
        class C00211 implements Runnable {
            final /* synthetic */ AtomicReference val$value;

            C00211(AtomicReference atomicReference) {
                this.val$value = atomicReference;
            }

            public void run() {
                FirebaseDB.this.GotValue(C00233.this.val$tag, this.val$value.get());
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.3.2 */
        class C00222 implements Runnable {
            final /* synthetic */ FirebaseError val$error;

            C00222(FirebaseError firebaseError) {
                this.val$error = firebaseError;
            }

            public void run() {
                FirebaseDB.this.FirebaseError(this.val$error.getMessage());
            }
        }

        C00233(Object obj, String str) {
            this.val$valueIfTagNotThere = obj;
            this.val$tag = str;
        }

        public void onDataChange(DataSnapshot snapshot) {
            AtomicReference<Object> value = new AtomicReference();
            try {
                if (snapshot.exists()) {
                    value.set(snapshot.getValue());
                } else {
                    value.set(JsonUtil.getJsonRepresentation(this.val$valueIfTagNotThere));
                }
                FirebaseDB.this.androidUIHandler.post(new C00211(value));
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        }

        public void onCancelled(FirebaseError error) {
            FirebaseDB.this.androidUIHandler.post(new C00222(error));
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.5 */
    class C00245 implements Runnable {
        final /* synthetic */ ReturnVal val$result;

        C00245(ReturnVal returnVal) {
            this.val$result = returnVal;
        }

        public void run() {
            FirebaseDB.this.FirstRemoved(this.val$result.getRetval());
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.6 */
    class C00266 implements ValueEventListener {

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.6.1 */
        class C00251 implements Runnable {
            final /* synthetic */ List val$listValue;

            C00251(List list) {
                this.val$listValue = list;
            }

            public void run() {
                FirebaseDB.this.TagList(this.val$listValue);
            }
        }

        C00266() {
        }

        public void onDataChange(DataSnapshot data) {
            Object value = data.getValue();
            if (value instanceof HashMap) {
                FirebaseDB.this.androidUIHandler.post(new C00251(new ArrayList(((HashMap) value).keySet())));
            }
        }

        public void onCancelled(FirebaseError error) {
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.8 */
    class C00298 implements Transaction.Handler {
        final /* synthetic */ ReturnVal val$result;
        final /* synthetic */ Transactional val$toRun;
        final /* synthetic */ Runnable val$whenDone;

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.8.1 */
        class C00271 implements Runnable {
            final /* synthetic */ FirebaseError val$firebaseError;

            C00271(FirebaseError firebaseError) {
                this.val$firebaseError = firebaseError;
            }

            public void run() {
                Log.i(FirebaseDB.LOG_TAG, "AppendValue(onComplete): firebase: " + this.val$firebaseError.getMessage());
                Log.i(FirebaseDB.LOG_TAG, "AppendValue(onComplete): result.err: " + C00298.this.val$result.err);
                FirebaseDB.this.FirebaseError(this.val$firebaseError.getMessage());
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.8.2 */
        class C00282 implements Runnable {
            C00282() {
            }

            public void run() {
                Log.i(FirebaseDB.LOG_TAG, "AppendValue(!committed): result.err: " + C00298.this.val$result.err);
                FirebaseDB.this.FirebaseError(C00298.this.val$result.err);
            }
        }

        C00298(Transactional transactional, ReturnVal returnVal, Runnable runnable) {
            this.val$toRun = transactional;
            this.val$result = returnVal;
            this.val$whenDone = runnable;
        }

        public Result doTransaction(MutableData currentData) {
            return this.val$toRun.run(currentData);
        }

        public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
            if (firebaseError != null) {
                FirebaseDB.this.androidUIHandler.post(new C00271(firebaseError));
            } else if (!committed) {
                FirebaseDB.this.androidUIHandler.post(new C00282());
            } else if (this.val$whenDone != null) {
                FirebaseDB.this.androidUIHandler.post(this.val$whenDone);
            }
        }
    }

    private static class ReturnVal {
        String err;
        Object retval;

        private ReturnVal() {
        }

        Object getRetval() {
            return this.retval;
        }
    }

    private static abstract class Transactional {
        final Object arg1;
        final Object arg2;
        final ReturnVal retv;

        abstract Result run(MutableData mutableData);

        Transactional(Object arg1, Object arg2, ReturnVal retv) {
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.retv = retv;
        }

        ReturnVal getResult() {
            return this.retv;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.4 */
    class C01744 extends Transactional {
        final /* synthetic */ ReturnVal val$result;

        C01744(Object x0, Object x1, ReturnVal x2, ReturnVal returnVal) {
            this.val$result = returnVal;
            super(x0, x1, x2);
        }

        Result run(MutableData currentData) {
            Object value = currentData.getValue();
            if (value == null) {
                this.val$result.err = "Previous value was empty.";
                return Transaction.abort();
            }
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value);
                    if (!(value instanceof List)) {
                        this.val$result.err = "You can only remove elements from a list.";
                        return Transaction.abort();
                    } else if (((List) value).isEmpty()) {
                        this.val$result.err = "The list was empty";
                        return Transaction.abort();
                    } else {
                        this.val$result.retval = ((List) value).remove(0);
                        try {
                            currentData.setValue(JsonUtil.getJsonRepresentation(YailList.makeList((List) value)));
                            return Transaction.success(currentData);
                        } catch (JSONException e) {
                            this.val$result.err = "Could not convert value to JSON.";
                            return Transaction.abort();
                        }
                    }
                }
                this.val$result.err = "Invalid JSON object in database (shouldn't happen!)";
                return Transaction.abort();
            } catch (JSONException e2) {
                this.val$result.err = "Invalid JSON object in database (shouldn't happen!)";
                return Transaction.abort();
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.FirebaseDB.7 */
    class C01757 extends Transactional {
        final /* synthetic */ ReturnVal val$result;
        final /* synthetic */ Object val$valueToAdd;

        C01757(Object x0, Object x1, ReturnVal x2, ReturnVal returnVal, Object obj) {
            this.val$result = returnVal;
            this.val$valueToAdd = obj;
            super(x0, x1, x2);
        }

        Result run(MutableData currentData) {
            Object value = currentData.getValue();
            if (value == null) {
                this.val$result.err = "Previous value was empty.";
                return Transaction.abort();
            }
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value);
                    if (value instanceof List) {
                        ((List) value).add(this.val$valueToAdd);
                        try {
                            currentData.setValue(JsonUtil.getJsonRepresentation(YailList.makeList((List) value)));
                            return Transaction.success(currentData);
                        } catch (JSONException e) {
                            this.val$result.err = "Could not convert value to JSON.";
                            return Transaction.abort();
                        }
                    }
                    this.val$result.err = "You can only append to a list.";
                    return Transaction.abort();
                }
                this.val$result.err = "Invalid JSON object in database (shouldn't happen!)";
                return Transaction.abort();
            } catch (JSONException e2) {
                this.val$result.err = "Invalid JSON object in database (shouldn't happen!)";
                return Transaction.abort();
            }
        }
    }

    static {
        isInitialized = false;
        persist = false;
    }

    public FirebaseDB(ComponentContainer container) {
        super(container.$form());
        this.firebaseURL = null;
        this.defaultURL = null;
        this.useDefault = true;
        this.androidUIHandler = new Handler();
        this.activity = container.$context();
        Firebase.setAndroidContext(this.activity);
        this.developerBucket = ElementType.MATCH_ANY_LOCALNAME;
        this.projectBucket = ElementType.MATCH_ANY_LOCALNAME;
        this.firebaseToken = ElementType.MATCH_ANY_LOCALNAME;
        this.childListener = new C00181();
        this.authListener = new C00202();
    }

    public void Initialize() {
        Log.i(LOG_TAG, "Initalize called!");
        isInitialized = true;
        resetListener();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets the URL for this FirebaseDB.", userVisible = false)
    public String FirebaseURL() {
        if (this.useDefault) {
            return "DEFAULT";
        }
        return this.firebaseURL;
    }

    @DesignerProperty(defaultValue = "DEFAULT", editorType = "FirbaseURL")
    @SimpleProperty(description = "Sets the URL for this FirebaseDB.")
    public void FirebaseURL(String url) {
        if (!url.equals("DEFAULT")) {
            this.useDefault = false;
            url = url + (url.endsWith("/") ? ElementType.MATCH_ANY_LOCALNAME : "/");
            if (!this.firebaseURL.equals(url)) {
                this.firebaseURL = url;
                this.useDefault = false;
                resetListener();
            }
        } else if (this.useDefault) {
            this.firebaseURL = this.defaultURL;
        } else {
            this.useDefault = true;
            if (this.defaultURL == null) {
                Log.d(LOG_TAG, "FirebaseURL called before DefaultURL (should not happen!)");
                return;
            }
            this.firebaseURL = this.defaultURL;
            resetListener();
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public String DeveloperBucket() {
        return this.developerBucket;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty
    public void DeveloperBucket(String bucket) {
        this.developerBucket = bucket;
        resetListener();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets the ProjectBucket for this FirebaseDB.")
    public String ProjectBucket() {
        return this.projectBucket;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "Sets the ProjectBucket for this FirebaseDB.")
    public void ProjectBucket(String bucket) {
        if (!this.projectBucket.equals(bucket)) {
            this.projectBucket = bucket;
            resetListener();
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public String FirebaseToken() {
        return this.firebaseToken;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty
    public void FirebaseToken(String JWT) {
        this.firebaseToken = JWT;
        resetListener();
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(description = "If true, variables will retain their values when off-line and the App exits. Values will be uploaded to Firebase the next time the App is run while connected to the network. This is useful for applications which will gather data while not connected to the network. Note: AppendValue and RemoveFirst will not work correctly when off-line, they require a network connection.<br/><br/> <i>Note</i>: If you set Persist on any Firebase component, on any screen, it makes all Firebase components on all screens persistent. This is a limitation of the low level Firebase library. Also be aware that if you want to set persist to true, you should do so before connecting the Companion for incremental development.", userVisible = false)
    public void Persist(boolean value) {
        Log.i(LOG_TAG, "Persist Called: Value = " + value);
        if (persist == value) {
            return;
        }
        if (isInitialized) {
            throw new RuntimeException("You cannot change the Persist value of Firebase after Application Initialization, this includes the Companion");
        }
        Config config = Firebase.getDefaultConfig();
        config.setPersistenceEnabled(value);
        Firebase.setDefaultConfig(config);
        persist = value;
        resetListener();
    }

    private void resetListener() {
        if (isInitialized) {
            if (this.myFirebase != null) {
                this.myFirebase.removeEventListener(this.childListener);
                this.myFirebase.removeAuthStateListener(this.authListener);
            }
            this.myFirebase = null;
            connectFirebase();
        }
    }

    @SimpleFunction(description = "Remove the tag from Firebase")
    public void ClearTag(String tag) {
        this.myFirebase.child(tag).removeValue();
    }

    @SimpleFunction
    public void StoreValue(String tag, Object valueToStore) {
        if (valueToStore != null) {
            try {
                valueToStore = JsonUtil.getJsonRepresentation(valueToStore);
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        }
        this.myFirebase.child(tag).setValue(valueToStore);
    }

    @SimpleFunction
    public void GetValue(String tag, Object valueIfTagNotThere) {
        this.myFirebase.child(tag).addListenerForSingleValueEvent(new C00233(valueIfTagNotThere, tag));
    }

    @SimpleEvent
    public void GotValue(String tag, Object value) {
        if (value != null) {
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value);
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Retrieval Error.");
            }
        }
        EventDispatcher.dispatchEvent(this, "GotValue", tag, value);
    }

    @SimpleEvent
    public void DataChanged(String tag, Object value) {
        if (value != null) {
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value);
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Retrieval Error.");
            }
        }
        EventDispatcher.dispatchEvent(this, "DataChanged", tag, value);
    }

    @SimpleEvent
    public void FirebaseError(String message) {
        Log.e(LOG_TAG, message);
        if (!EventDispatcher.dispatchEvent(this, "FirebaseError", message)) {
            Notifier.oneButtonAlert(this.form, message, "FirebaseError", "Continue");
        }
    }

    private void connectFirebase() {
        if (SdkLevel.getLevel() < 10) {
            Notifier.oneButtonAlert(this.activity, "The version of Android on this device is too old to use Firebase.", "Android Too Old", "OK");
            return;
        }
        if (this.useDefault) {
            this.myFirebase = new Firebase(this.firebaseURL + "developers/" + this.developerBucket + this.projectBucket);
        } else {
            this.myFirebase = new Firebase(this.firebaseURL + this.projectBucket);
        }
        this.myFirebase.addChildEventListener(this.childListener);
        this.myFirebase.addAuthStateListener(this.authListener);
    }

    @SimpleFunction(description = "If you are having difficulty with the Companion and you are switching between different Firebase accounts, you may need to use this function to clear internal Firebase caches. You can just use the \"Do It\" function on this block in the blocks editor. Note: You should not normally need to use this block as part of an application.")
    public void Unauthenticate() {
        if (this.myFirebase == null) {
            connectFirebase();
        }
        this.myFirebase.unauth();
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void DefaultURL(String url) {
        this.defaultURL = url;
        if (this.useDefault) {
            this.firebaseURL = this.defaultURL;
            resetListener();
        }
    }

    @SimpleFunction(description = "Return the first element of a list and atomically remove it. If two devices use this function simultaneously, one will get the first element and the the other will get the second element, or an error if there is no available element. When the element is available, the \"FirstRemoved\" event will be triggered.")
    public void RemoveFirst(String tag) {
        ReturnVal result = new ReturnVal();
        firebaseTransaction(new C01744(null, null, result, result), this.myFirebase.child(tag), new C00245(result));
    }

    @SimpleFunction(description = "Get the list of tags for this application. When complete a \"TagList\" event will be triggered with the list of known tags.")
    public void GetTagList() {
        this.myFirebase.child(ElementType.MATCH_ANY_LOCALNAME).addListenerForSingleValueEvent(new C00266());
    }

    @SimpleEvent(description = "Event triggered when we have received the list of known tags. Used with the \"GetTagList\" Function.")
    public void TagList(List value) {
        EventDispatcher.dispatchEvent(this, "TagList", value);
    }

    @SimpleEvent(description = "Event triggered by the \"RemoveFirst\" function. The argument \"value\" is the object that was the first in the list, and which is now removed.")
    public void FirstRemoved(Object value) {
        EventDispatcher.dispatchEvent(this, "FirstRemoved", value);
    }

    @SimpleFunction(description = "Append a value to the end of a list atomically. If two devices use this function simultaneously, both will be appended and no data lost.")
    public void AppendValue(String tag, Object valueToAdd) {
        ReturnVal result = new ReturnVal();
        firebaseTransaction(new C01757(null, null, result, result, valueToAdd), this.myFirebase.child(tag), null);
    }

    private void firebaseTransaction(Transactional toRun, Firebase firebase, Runnable whenDone) {
        firebase.runTransaction(new C00298(toRun, toRun.getResult(), whenDone));
    }
}
