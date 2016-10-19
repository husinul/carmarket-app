package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
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
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.kawa.xml.ElementType;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@DesignerComponent(category = ComponentCategory.SOCIAL, description = "A non-visible component that enables communication with <a href=\"http://www.twitter.com\" target=\"_blank\">Twitter</a>. Once a user has logged into their Twitter account (and the authorization has been confirmed successful by the <code>IsAuthorized</code> event), many more operations are available:<ul><li> Searching Twitter for tweets or labels (<code>SearchTwitter</code>)</li>\n<li> Sending a Tweet (<code>Tweet</code>)     </li>\n<li> Sending a Tweet with an Image (<code>TweetWithImage</code>)     </li>\n<li> Directing a message to a specific user      (<code>DirectMessage</code>)</li>\n <li> Receiving the most recent messages directed to the logged-in user      (<code>RequestDirectMessages</code>)</li>\n <li> Following a specific user (<code>Follow</code>)</li>\n<li> Ceasing to follow a specific user (<code>StopFollowing</code>)</li>\n<li> Getting a list of users following the logged-in user      (<code>RequestFollowers</code>)</li>\n <li> Getting the most recent messages of users followed by the      logged-in user (<code>RequestFriendTimeline</code>)</li>\n <li> Getting the most recent mentions of the logged-in user      (<code>RequestMentions</code>)</li></ul></p>\n <p>You must obtain a Consumer Key and Consumer Secret for Twitter authorization  specific to your app from http://twitter.com/oauth_clients/new", iconName = "images/twitter.png", nonVisible = true, version = 4)
@UsesLibraries(libraries = "twitter4j.jar,twitter4jmedia.jar")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class Twitter extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String ACCESS_SECRET_TAG = "TwitterOauthAccessSecret";
    private static final String ACCESS_TOKEN_TAG = "TwitterOauthAccessToken";
    private static final String CALLBACK_URL = "appinventor://twitter";
    private static final String MAX_CHARACTERS = "160";
    private static final String MAX_MENTIONS_RETURNED = "20";
    private static final String URL_HOST = "twitter";
    private static final String WEBVIEW_ACTIVITY_CLASS;
    private String TwitPic_API_Key;
    private AccessToken accessToken;
    private String consumerKey;
    private String consumerSecret;
    private final ComponentContainer container;
    private final List<String> directMessages;
    private final List<String> followers;
    private final Handler handler;
    private final List<String> mentions;
    private final int requestCode;
    private RequestToken requestToken;
    private final List<String> searchResults;
    private final SharedPreferences sharedPreferences;
    private final List<List<String>> timeline;
    private twitter4j.Twitter twitter;
    private String userName;

    /* renamed from: com.google.appinventor.components.runtime.Twitter.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ String val$user;

        AnonymousClass10(String str) {
            this.val$user = str;
        }

        public void run() {
            try {
                Twitter.this.twitter.createFriendship(this.val$user);
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Follow", ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, e.getMessage());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.11 */
    class AnonymousClass11 implements Runnable {
        final /* synthetic */ String val$user;

        AnonymousClass11(String str) {
            this.val$user = str;
        }

        public void run() {
            try {
                Twitter.this.twitter.destroyFriendship(this.val$user);
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "StopFollowing", ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, e.getMessage());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.13 */
    class AnonymousClass13 implements Runnable {
        List<Status> tweets;
        final /* synthetic */ String val$query;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.13.1 */
        class C00971 implements Runnable {
            C00971() {
            }

            public void run() {
                Twitter.this.searchResults.clear();
                for (Status tweet : AnonymousClass13.this.tweets) {
                    Twitter.this.searchResults.add(tweet.getUser().getName() + " " + tweet.getText());
                }
                Twitter.this.SearchSuccessful(Twitter.this.searchResults);
            }
        }

        AnonymousClass13(String str) {
            this.val$query = str;
            this.tweets = Collections.emptyList();
        }

        public void run() {
            try {
                this.tweets = Twitter.this.twitter.search(new Query(this.val$query)).getTweets();
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "SearchTwitter", ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, e.getMessage());
            } finally {
                Twitter.this.handler.post(new C00971());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.1 */
    class C00981 implements Runnable {
        final /* synthetic */ String val$myConsumerKey;
        final /* synthetic */ String val$myConsumerSecret;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.1.1 */
        class C00941 implements Runnable {
            C00941() {
            }

            public void run() {
                Twitter.this.IsAuthorized();
            }
        }

        /* renamed from: com.google.appinventor.components.runtime.Twitter.1.2 */
        class C00952 implements Runnable {
            C00952() {
            }

            public void run() {
                Twitter.this.IsAuthorized();
            }
        }

        C00981(String str, String str2) {
            this.val$myConsumerKey = str;
            this.val$myConsumerSecret = str2;
        }

        public void run() {
            if (Twitter.this.checkAccessToken(this.val$myConsumerKey, this.val$myConsumerSecret)) {
                Twitter.this.handler.post(new C00941());
                return;
            }
            try {
                Twitter.this.twitter.setOAuthConsumer(this.val$myConsumerKey, this.val$myConsumerSecret);
                RequestToken newRequestToken = Twitter.this.twitter.getOAuthRequestToken(Twitter.CALLBACK_URL);
                String authURL = newRequestToken.getAuthorizationURL();
                Twitter.this.requestToken = newRequestToken;
                Intent browserIntent = new Intent("android.intent.action.MAIN", Uri.parse(authURL));
                browserIntent.setClassName(Twitter.this.container.$context(), Twitter.WEBVIEW_ACTIVITY_CLASS);
                Twitter.this.container.$context().startActivityForResult(browserIntent, Twitter.this.requestCode);
            } catch (TwitterException e) {
                Log.i("Twitter", "Got exception: " + e.getMessage());
                e.printStackTrace();
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Authorize", ErrorMessages.ERROR_TWITTER_EXCEPTION, e.getMessage());
                Twitter.this.DeAuthorize();
            } catch (IllegalStateException e2) {
                Log.e("Twitter", "OAuthConsumer was already set: launch IsAuthorized()");
                Twitter.this.handler.post(new C00952());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.2 */
    class C01002 implements Runnable {
        final /* synthetic */ String val$myConsumerKey;
        final /* synthetic */ String val$myConsumerSecret;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.2.1 */
        class C00991 implements Runnable {
            C00991() {
            }

            public void run() {
                Twitter.this.IsAuthorized();
            }
        }

        C01002(String str, String str2) {
            this.val$myConsumerKey = str;
            this.val$myConsumerSecret = str2;
        }

        public void run() {
            if (Twitter.this.checkAccessToken(this.val$myConsumerKey, this.val$myConsumerSecret)) {
                Twitter.this.handler.post(new C00991());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.3 */
    class C01023 implements Runnable {
        final /* synthetic */ String val$oauthVerifier;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.3.1 */
        class C01011 implements Runnable {
            C01011() {
            }

            public void run() {
                Twitter.this.IsAuthorized();
            }
        }

        C01023(String str) {
            this.val$oauthVerifier = str;
        }

        public void run() {
            try {
                AccessToken resultAccessToken = Twitter.this.twitter.getOAuthAccessToken(Twitter.this.requestToken, this.val$oauthVerifier);
                Twitter.this.accessToken = resultAccessToken;
                Twitter.this.userName = Twitter.this.accessToken.getScreenName();
                Twitter.this.saveAccessToken(resultAccessToken);
                Twitter.this.handler.post(new C01011());
            } catch (TwitterException e) {
                Log.e("Twitter", "Got exception: " + e.getMessage());
                e.printStackTrace();
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Authorize", ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, e.getMessage());
                Twitter.this.deAuthorize();
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.4 */
    class C01034 implements Runnable {
        final /* synthetic */ String val$status;

        C01034(String str) {
            this.val$status = str;
        }

        public void run() {
            try {
                Twitter.this.twitter.updateStatus(this.val$status);
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Tweet", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, e.getMessage());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.5 */
    class C01045 implements Runnable {
        final /* synthetic */ String val$imagePath;
        final /* synthetic */ String val$status;

        C01045(String str, String str2) {
            this.val$imagePath = str;
            this.val$status = str2;
        }

        public void run() {
            try {
                String cleanImagePath = this.val$imagePath;
                if (cleanImagePath.startsWith("file://")) {
                    cleanImagePath = this.val$imagePath.replace("file://", ElementType.MATCH_ANY_LOCALNAME);
                }
                File imageFilePath = new File(cleanImagePath);
                if (imageFilePath.exists()) {
                    StatusUpdate theTweet = new StatusUpdate(this.val$status);
                    theTweet.setMedia(imageFilePath);
                    Twitter.this.twitter.updateStatus(theTweet);
                    return;
                }
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_INVALID_IMAGE_PATH, new Object[0]);
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, e.getMessage());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.6 */
    class C01066 implements Runnable {
        List<Status> replies;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.6.1 */
        class C01051 implements Runnable {
            C01051() {
            }

            public void run() {
                Twitter.this.mentions.clear();
                for (Status status : C01066.this.replies) {
                    Twitter.this.mentions.add(status.getUser().getScreenName() + " " + status.getText());
                }
                Twitter.this.MentionsReceived(Twitter.this.mentions);
            }
        }

        C01066() {
            this.replies = Collections.emptyList();
        }

        public void run() {
            try {
                this.replies = Twitter.this.twitter.getMentionsTimeline();
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestMentions", ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, e.getMessage());
            } finally {
                Twitter.this.handler.post(new C01051());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.7 */
    class C01087 implements Runnable {
        List<User> friends;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.7.1 */
        class C01071 implements Runnable {
            C01071() {
            }

            public void run() {
                Twitter.this.followers.clear();
                for (User user : C01087.this.friends) {
                    Twitter.this.followers.add(user.getName());
                }
                Twitter.this.FollowersReceived(Twitter.this.followers);
            }
        }

        C01087() {
            this.friends = new ArrayList();
        }

        public void run() {
            try {
                for (long id : Twitter.this.twitter.getFollowersIDs(-1).getIDs()) {
                    this.friends.add(Twitter.this.twitter.showUser(id));
                }
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestFollowers", ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, e.getMessage());
            } finally {
                Twitter.this.handler.post(new C01071());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.8 */
    class C01108 implements Runnable {
        List<DirectMessage> messages;

        /* renamed from: com.google.appinventor.components.runtime.Twitter.8.1 */
        class C01091 implements Runnable {
            C01091() {
            }

            public void run() {
                Twitter.this.directMessages.clear();
                for (DirectMessage message : C01108.this.messages) {
                    Twitter.this.directMessages.add(message.getSenderScreenName() + " " + message.getText());
                }
                Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
            }
        }

        C01108() {
            this.messages = Collections.emptyList();
        }

        public void run() {
            try {
                this.messages = Twitter.this.twitter.getDirectMessages();
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestDirectMessages", ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, e.getMessage());
            } finally {
                Twitter.this.handler.post(new C01091());
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter.9 */
    class C01119 implements Runnable {
        final /* synthetic */ String val$message;
        final /* synthetic */ String val$user;

        C01119(String str, String str2) {
            this.val$user = str;
            this.val$message = str2;
        }

        public void run() {
            try {
                Twitter.this.twitter.sendDirectMessage(this.val$user, this.val$message);
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "DirectMessage", ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, e.getMessage());
            }
        }
    }

    static {
        WEBVIEW_ACTIVITY_CLASS = WebViewActivity.class.getName();
    }

    public Twitter(ComponentContainer container) {
        super(container.$form());
        this.consumerKey = ElementType.MATCH_ANY_LOCALNAME;
        this.consumerSecret = ElementType.MATCH_ANY_LOCALNAME;
        this.TwitPic_API_Key = ElementType.MATCH_ANY_LOCALNAME;
        this.userName = ElementType.MATCH_ANY_LOCALNAME;
        this.container = container;
        this.handler = new Handler();
        this.mentions = new ArrayList();
        this.followers = new ArrayList();
        this.timeline = new ArrayList();
        this.directMessages = new ArrayList();
        this.searchResults = new ArrayList();
        this.sharedPreferences = container.$context().getSharedPreferences("Twitter", 0);
        this.accessToken = retrieveAccessToken();
        this.requestCode = this.form.registerForActivityResult(this);
    }

    @SimpleFunction(description = "Twitter's API no longer supports login via username and password. Use the Authorize call instead.", userVisible = false)
    public void Login(String username, String password) {
        this.form.dispatchErrorOccurredEvent(this, "Login", ErrorMessages.ERROR_TWITTER_UNSUPPORTED_LOGIN_FUNCTION, new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The user name of the authorized user. Empty if there is no authorized user.")
    public String Username() {
        return this.userName;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ConsumerKey() {
        return this.consumerKey;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The the consumer key to be used when authorizing with Twitter via OAuth.")
    public void ConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ConsumerSecret() {
        return this.consumerSecret;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(description = "The consumer secret to be used when authorizing with Twitter via OAuth")
    public void ConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String TwitPic_API_Key() {
        return this.TwitPic_API_Key;
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The API Key for image uploading, provided by TwitPic.")
    public void TwitPic_API_Key(String TwitPic_API_Key) {
        this.TwitPic_API_Key = TwitPic_API_Key;
    }

    @SimpleEvent(description = "This event is raised after the program calls <code>Authorize</code> if the authorization was successful.  It is also called after a call to <code>CheckAuthorized</code> if we already have a valid access token. After this event has been raised, any other method for this component can be called.")
    public void IsAuthorized() {
        EventDispatcher.dispatchEvent(this, "IsAuthorized", new Object[0]);
    }

    @SimpleFunction(description = "Redirects user to login to Twitter via the Web browser using the OAuth protocol if we don't already have authorization.")
    public void Authorize() {
        if (this.consumerKey.length() == 0 || this.consumerSecret.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, new Object[0]);
            return;
        }
        if (this.twitter == null) {
            this.twitter = new TwitterFactory().getInstance();
        }
        AsynchUtil.runAsynchronously(new C00981(this.consumerKey, this.consumerSecret));
    }

    @SimpleFunction(description = "Checks whether we already have access, and if so, causes IsAuthorized event handler to be called.")
    public void CheckAuthorized() {
        AsynchUtil.runAsynchronously(new C01002(this.consumerKey, this.consumerSecret));
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        Log.i("Twitter", "Got result " + resultCode);
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Log.i("Twitter", "Intent URI: " + uri.toString());
                String oauthVerifier = uri.getQueryParameter("oauth_verifier");
                if (this.twitter == null) {
                    Log.e("Twitter", "twitter field is unexpectedly null");
                    this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, "internal error: can't access Twitter library");
                    new RuntimeException().printStackTrace();
                }
                if (this.requestToken == null || oauthVerifier == null || oauthVerifier.length() == 0) {
                    this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED, new Object[0]);
                    deAuthorize();
                    return;
                }
                AsynchUtil.runAsynchronously(new C01023(oauthVerifier));
                return;
            }
            Log.e("Twitter", "uri returned from WebView activity was unexpectedly null");
            deAuthorize();
            return;
        }
        Log.e("Twitter", "intent returned from WebView activity was unexpectedly null");
        deAuthorize();
    }

    private void saveAccessToken(AccessToken accessToken) {
        Editor sharedPrefsEditor = this.sharedPreferences.edit();
        if (accessToken == null) {
            sharedPrefsEditor.remove(ACCESS_TOKEN_TAG);
            sharedPrefsEditor.remove(ACCESS_SECRET_TAG);
        } else {
            sharedPrefsEditor.putString(ACCESS_TOKEN_TAG, accessToken.getToken());
            sharedPrefsEditor.putString(ACCESS_SECRET_TAG, accessToken.getTokenSecret());
        }
        sharedPrefsEditor.commit();
    }

    private AccessToken retrieveAccessToken() {
        String token = this.sharedPreferences.getString(ACCESS_TOKEN_TAG, ElementType.MATCH_ANY_LOCALNAME);
        String secret = this.sharedPreferences.getString(ACCESS_SECRET_TAG, ElementType.MATCH_ANY_LOCALNAME);
        if (token.length() == 0 || secret.length() == 0) {
            return null;
        }
        return new AccessToken(token, secret);
    }

    @SimpleFunction(description = "Removes Twitter authorization from this running app instance")
    public void DeAuthorize() {
        deAuthorize();
    }

    private void deAuthorize() {
        this.requestToken = null;
        this.accessToken = null;
        this.userName = ElementType.MATCH_ANY_LOCALNAME;
        twitter4j.Twitter oldTwitter = this.twitter;
        this.twitter = null;
        saveAccessToken(this.accessToken);
        if (oldTwitter != null) {
            oldTwitter.setOAuthAccessToken(null);
        }
    }

    @SimpleFunction(description = "This sends a tweet as the logged-in user with the specified Text, which will be trimmed if it exceeds 160 characters. <p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void Tweet(String status) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Tweet", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new C01034(status));
    }

    @SimpleFunction(description = "This sends a tweet as the logged-in user with the specified Text and a path to the image to be uploaded, which will be trimmed if it exceeds 160 characters. If an image is not found or invalid, only the text will be tweeted.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void TweetWithImage(String status, String imagePath) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new C01045(imagePath, status));
    }

    @SimpleFunction(description = "Requests the 20 most recent mentions of the logged-in user.  When the mentions have been retrieved, the system will raise the <code>MentionsReceived</code> event and set the <code>Mentions</code> property to the list of mentions.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void RequestMentions() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestMentions", ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new C01066());
    }

    @SimpleEvent(description = "This event is raised when the mentions of the logged-in user requested through <code>RequestMentions</code> have been retrieved.  A list of the mentions can then be found in the <code>mentions</code> parameter or the <code>Mentions</code> property.")
    public void MentionsReceived(List<String> mentions) {
        EventDispatcher.dispatchEvent(this, "MentionsReceived", mentions);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of mentions of the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Call the <code>RequestMentions</code> method.</li> <li> Wait for the <code>MentionsReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of mentions (and will maintain its value until any subsequent calls to <code>RequestMentions</code>).")
    public List<String> Mentions() {
        return this.mentions;
    }

    @SimpleFunction
    public void RequestFollowers() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestFollowers", ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new C01087());
    }

    @SimpleEvent(description = "This event is raised when all of the followers of the logged-in user requested through <code>RequestFollowers</code> have been retrieved. A list of the followers can then be found in the <code>followers</code> parameter or the <code>Followers</code> property.")
    public void FollowersReceived(List<String> followers2) {
        EventDispatcher.dispatchEvent(this, "FollowersReceived", followers2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of the followers of the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Call the <code>RequestFollowers</code> method.</li> <li> Wait for the <code>FollowersReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of followers (and maintain its value until any subsequent call to <code>RequestFollowers</code>).")
    public List<String> Followers() {
        return this.followers;
    }

    @SimpleFunction(description = "Requests the 20 most recent direct messages sent to the logged-in user.  When the messages have been retrieved, the system will raise the <code>DirectMessagesReceived</code> event and set the <code>DirectMessages</code> property to the list of messages.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void RequestDirectMessages() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestDirectMessages", ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new C01108());
    }

    @SimpleEvent(description = "This event is raised when the recent messages requested through <code>RequestDirectMessages</code> have been retrieved. A list of the messages can then be found in the <code>messages</code> parameter or the <code>Messages</code> property.")
    public void DirectMessagesReceived(List<String> messages) {
        EventDispatcher.dispatchEvent(this, "DirectMessagesReceived", messages);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of the most recent messages mentioning the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>Authorized</code> event.</li> <li> Call the <code>RequestDirectMessages</code> method.</li> <li> Wait for the <code>DirectMessagesReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of direct messages retrieved (and maintain that value until any subsequent call to <code>RequestDirectMessages</code>).")
    public List<String> DirectMessages() {
        return this.directMessages;
    }

    @SimpleFunction(description = "This sends a direct (private) message to the specified user.  The message will be trimmed if it exceeds 160characters. <p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void DirectMessage(String user, String message) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "DirectMessage", ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new C01119(user, message));
    }

    @SimpleFunction
    public void Follow(String user) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Follow", ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new AnonymousClass10(user));
    }

    @SimpleFunction
    public void StopFollowing(String user) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "StopFollowing", ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new AnonymousClass11(user));
    }

    @SimpleFunction
    public void RequestFriendTimeline() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestFriendTimeline", ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new Runnable() {
            List<Status> messages;

            /* renamed from: com.google.appinventor.components.runtime.Twitter.12.1 */
            class C00961 implements Runnable {
                C00961() {
                }

                public void run() {
                    Twitter.this.timeline.clear();
                    for (Status message : AnonymousClass12.this.messages) {
                        List<String> status = new ArrayList();
                        status.add(message.getUser().getScreenName());
                        status.add(message.getText());
                        Twitter.this.timeline.add(status);
                    }
                    Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                }
            }

            {
                this.messages = Collections.emptyList();
            }

            public void run() {
                try {
                    this.messages = Twitter.this.twitter.getHomeTimeline();
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestFriendTimeline", ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, e.getMessage());
                } finally {
                    Twitter.this.handler.post(new C00961());
                }
            }
        });
    }

    @SimpleEvent(description = "This event is raised when the messages requested through <code>RequestFriendTimeline</code> have been retrieved. The <code>timeline</code> parameter and the <code>Timeline</code> property will contain a list of lists, where each sub-list contains a status update of the form (username message)")
    public void FriendTimelineReceived(List<List<String>> timeline) {
        EventDispatcher.dispatchEvent(this, "FriendTimelineReceived", timeline);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains the 20 most recent messages of users being followed.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Specify users to follow with one or more calls to the <code>Follow</code> method.</li> <li> Call the <code>RequestFriendTimeline</code> method.</li> <li> Wait for the <code>FriendTimelineReceived</code> event.</li> </ol>\nThe value of this property will then be set to the list of messages (and maintain its value until any subsequent call to <code>RequestFriendTimeline</code>.")
    public List<List<String>> FriendTimeline() {
        return this.timeline;
    }

    @SimpleFunction(description = "This searches Twitter for the given String query.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void SearchTwitter(String query) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "SearchTwitter", ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, "Need to login?");
            return;
        }
        AsynchUtil.runAsynchronously(new AnonymousClass13(query));
    }

    @SimpleEvent(description = "This event is raised when the results of the search requested through <code>SearchSuccessful</code> have been retrieved. A list of the results can then be found in the <code>results</code> parameter or the <code>Results</code> property.")
    public void SearchSuccessful(List<String> searchResults) {
        EventDispatcher.dispatchEvent(this, "SearchSuccessful", searchResults);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property, which is initially empty, is set to a list of search results after the program: <ol><li>Calls the <code>SearchTwitter</code> method.</li> <li>Waits for the <code>SearchSuccessful</code> event.</li></ol>\nThe value of the property will then be the same as the parameter to <code>SearchSuccessful</code>.  Note that it is not necessary to call the <code>Authorize</code> method before calling <code>SearchTwitter</code>.")
    public List<String> SearchResults() {
        return this.searchResults;
    }

    private boolean checkAccessToken(String myConsumerKey, String myConsumerSecret) {
        this.accessToken = retrieveAccessToken();
        if (this.accessToken == null) {
            return false;
        }
        if (this.twitter == null) {
            this.twitter = new TwitterFactory().getInstance();
        }
        try {
            this.twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
            this.twitter.setOAuthAccessToken(this.accessToken);
        } catch (IllegalStateException e) {
        }
        if (this.userName.trim().length() == 0) {
            try {
                this.userName = this.twitter.verifyCredentials().getScreenName();
            } catch (TwitterException e2) {
                deAuthorize();
                return false;
            }
        }
        return true;
    }
}
