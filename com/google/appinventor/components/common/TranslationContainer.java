package com.google.appinventor.components.common;

import com.google.appinventor.components.runtime.Texting;
import java.util.HashMap;
import java.util.Map;

public class TranslationContainer {
    private Map<String, String> CompTransMap;

    public TranslationContainer() {
        this.CompTransMap = new HashMap();
        this.CompTransMap.put("Basic", "\u57fa\u672c");
        this.CompTransMap.put("Media", "\u5a92\u4f53");
        this.CompTransMap.put("Animation", "\u52a8\u753b");
        this.CompTransMap.put("Social", "\u793e\u4ea4\u7684");
        this.CompTransMap.put("Sensors", "\u4f20\u611f\u5668");
        this.CompTransMap.put("Screen Arrangement", "\u5c4f\u5e55\u5e03\u5c40");
        this.CompTransMap.put("LEGO\u00ae MINDSTORMS\u00ae", "\u4e50\u9ad8\u673a\u5668\u4eba\u5957\u4ef6\u00ae");
        this.CompTransMap.put("Other stuff", "\u5176\u4ed6\u4e1c\u897f");
        this.CompTransMap.put("Not ready for prime time", "\u6d4b\u8bd5\u4e2d\u7684\u5957\u4ef6");
        this.CompTransMap.put("Old stuff", "\u65e7\u4e1c\u897f");
        this.CompTransMap.put("Button", "\u6309\u94ae");
        this.CompTransMap.put("Canvas", "\u753b\u5e03");
        this.CompTransMap.put("CheckBox", "\u590d\u9009\u6846");
        this.CompTransMap.put("Clock", "\u65f6\u949f");
        this.CompTransMap.put("Image", "\u56fe\u50cf");
        this.CompTransMap.put("Label", "\u4fbf\u7b7e");
        this.CompTransMap.put("ListPicker", "\u5217\u8868\u9009\u62e9\u5668");
        this.CompTransMap.put("PasswordTextBox", "\u5bc6\u7801\u6846");
        this.CompTransMap.put("TextBox", "\u6587\u672c\u6846");
        this.CompTransMap.put("TinyDB", "\u7ec6\u5c0f\u6570\u636e\u5e93");
        this.CompTransMap.put("Camcorder", "\u6444\u50cf\u673a");
        this.CompTransMap.put("Camera", "\u76f8\u673a");
        this.CompTransMap.put("ImagePicker", "\u753b\u50cf\u9009\u62e9\u5668");
        this.CompTransMap.put("Player", "\u64ad\u653e\u5668");
        this.CompTransMap.put("Sound", "\u58f0\u97f3");
        this.CompTransMap.put("VideoPlayer", "\u5a92\u4f53\u64ad\u653e\u5668");
        this.CompTransMap.put("Ball", "\u7403");
        this.CompTransMap.put("ImageSprite", "\u56fe\u7247\u7cbe\u7075");
        this.CompTransMap.put("ContactPicker", "\u8054\u7cfb\u4fe1\u606f\u9009\u62e9\u5668");
        this.CompTransMap.put("EmailPicker", "\u90ae\u4ef6\u9009\u62e9\u5668");
        this.CompTransMap.put("PhoneCall", "\u7535\u8bdd");
        this.CompTransMap.put("PhoneNumberPicker", "\u7535\u8bdd\u53f7\u7801\u9009\u62e9\u5668");
        this.CompTransMap.put(Texting.META_DATA_SMS_VALUE, "\u4fe1\u606f");
        this.CompTransMap.put("Twitter", "Twitter");
        this.CompTransMap.put("AccelerometerSensor", "\u52a0\u901f\u5ea6\u4f20\u611f\u5668");
        this.CompTransMap.put("LocationSensor", "\u4f4d\u7f6e\u4f20\u611f\u5668");
        this.CompTransMap.put("OrientationSensor", "\u65b9\u5411\u4f20\u611f\u5668");
        this.CompTransMap.put("HorizontalArrangement", "\u6c34\u5e73\u6392\u5217");
        this.CompTransMap.put("TableArrangement", "\u8868\u5b89\u6392");
        this.CompTransMap.put("VerticalArrangement", "\u7ad6\u5411\u5e03\u7f6e");
        this.CompTransMap.put("NxtColorSensor", "Nxt\u989c\u8272\u4f20\u611f\u5668");
        this.CompTransMap.put("NxtDirectCommands", "Nxt\u76f4\u63a5\u547d\u4ee4");
        this.CompTransMap.put("NxtDrive", "Nxt\u9a71\u52a8");
        this.CompTransMap.put("NxtLightSensor", "Nxt\u5149\u4f20\u611f\u5668");
        this.CompTransMap.put("NxtSoundSensor", "Nxt\u58f0\u97f3\u4f20\u611f\u5668");
        this.CompTransMap.put("NxtTouchSensor", "Nxt\u89e6\u6478\u4f20\u611f\u5668");
        this.CompTransMap.put("NxtUltrasonicSensor", "Nxt\u8d85\u58f0\u6ce2\u4f20\u611f\u5668");
        this.CompTransMap.put("ActivityStarter", "\u6d3b\u52a8\u542f\u52a8");
        this.CompTransMap.put("BarcodeScanner", "\u6761\u7801\u626b\u63cf\u5668");
        this.CompTransMap.put(PropertyTypeConstants.PROPERTY_TYPE_BLUETOOTHCLIENT, "\u84dd\u7259\u5ba2\u6237");
        this.CompTransMap.put("BluetoothServer", "\u84dd\u7259\u670d\u52a1\u5668");
        this.CompTransMap.put("Notifier", "\u901a\u544a\u4eba");
        this.CompTransMap.put("SpeechRecognizer", "\u8bed\u97f3\u8bc6\u522b");
        this.CompTransMap.put("TextToSpeech", "\u6587\u672c\u5230\u8bed\u97f3");
        this.CompTransMap.put("TinyWebDB", "\u7ec6\u5c0f\u7f51\u7edc\u6570\u636e\u5e93");
        this.CompTransMap.put("Web", "\u7f51\u7edc");
        this.CompTransMap.put("FusiontablesControl", "Fusiontables\u63a7\u5236");
        this.CompTransMap.put("GameClient", "\u6e38\u620f\u5ba2\u6237\u7aef");
        this.CompTransMap.put("SoundRecorder", "\u58f0\u97f3\u8bb0\u5f55\u5668");
        this.CompTransMap.put("Voting", "\u6295\u7968");
        this.CompTransMap.put("WebViewer", "\u7f51\u9875\u6d4f\u89c8\u5668");
    }

    public String getCorrespondingString(String key) {
        if (this.CompTransMap.containsKey(key)) {
            return (String) this.CompTransMap.get(key);
        }
        return "Missing name";
    }
}
