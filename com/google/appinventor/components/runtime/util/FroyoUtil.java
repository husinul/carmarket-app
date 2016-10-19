package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.http.SslError;
import android.view.Display;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.Player;
import com.google.appinventor.components.runtime.Player.State;
import gnu.kawa.functions.ParseFormat;
import gnu.xquery.lang.XQResolveNames;

public class FroyoUtil {

    /* renamed from: com.google.appinventor.components.runtime.util.FroyoUtil.1 */
    static class C01421 implements OnAudioFocusChangeListener {
        private boolean playbackFlag;
        final /* synthetic */ Player val$player;

        C01421(Player player) {
            this.val$player = player;
            this.playbackFlag = false;
        }

        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case XQResolveNames.HANDLE_EXTENSION_BUILTIN /*-3*/:
                case XQResolveNames.POSITION_BUILTIN /*-2*/:
                    if (this.val$player != null && this.val$player.playerState == State.PLAYING) {
                        this.val$player.pause();
                        this.playbackFlag = true;
                    }
                case ListPicker.DEFAULT_ITEM_TEXT_COLOR /*-1*/:
                    this.playbackFlag = false;
                    this.val$player.OtherPlayerStarted();
                case ParseFormat.SEEN_MINUS /*1*/:
                    if (this.val$player != null && this.playbackFlag && this.val$player.playerState == State.PAUSED_BY_EVENT) {
                        this.val$player.Start();
                        this.playbackFlag = false;
                    }
                default:
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.FroyoUtil.2 */
    static class C01432 extends WebViewClient {
        final /* synthetic */ Component val$component;
        final /* synthetic */ boolean val$followLinks;
        final /* synthetic */ Form val$form;
        final /* synthetic */ boolean val$ignoreErrors;

        C01432(boolean z, boolean z2, Form form, Component component) {
            this.val$followLinks = z;
            this.val$ignoreErrors = z2;
            this.val$form = form;
            this.val$component = component;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return !this.val$followLinks;
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (this.val$ignoreErrors) {
                handler.proceed();
                return;
            }
            handler.cancel();
            this.val$form.dispatchErrorOccurredEvent(this.val$component, "WebView", ErrorMessages.ERROR_WEBVIEW_SSL_ERROR, new Object[0]);
        }
    }

    private FroyoUtil() {
    }

    public static int getRotation(Display display) {
        return display.getRotation();
    }

    public static AudioManager setAudioManager(Activity activity) {
        return (AudioManager) activity.getSystemService("audio");
    }

    public static Object setAudioFocusChangeListener(Player player) {
        return new C01421(player);
    }

    public static boolean focusRequestGranted(AudioManager am, Object afChangeListener) {
        if (am.requestAudioFocus((OnAudioFocusChangeListener) afChangeListener, 3, 1) == 1) {
            return true;
        }
        return false;
    }

    public static void abandonFocus(AudioManager am, Object afChangeListener) {
        am.abandonAudioFocus((OnAudioFocusChangeListener) afChangeListener);
    }

    public static WebViewClient getWebViewClient(boolean ignoreErrors, boolean followLinks, Form form, Component component) {
        return new C01432(followLinks, ignoreErrors, form, component);
    }
}
