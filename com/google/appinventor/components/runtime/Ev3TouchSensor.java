package com.google.appinventor.components.runtime;

import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import gnu.kawa.xml.ElementType;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a touch sensor on a LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
public class Ev3TouchSensor extends LegoMindstormsEv3Sensor implements Deleteable {
    private static final int DELAY_MILLISECONDS = 50;
    private static final int SENSOR_MODE_TOUCH = 0;
    private static final String SENSOR_MODE_TOUCH_STRING = "touch";
    private static final int SENSOR_TYPE = 16;
    private static final int SENSOR_VALUE_THRESHOLD = 50;
    private Handler eventHandler;
    private int mode;
    private String modeString;
    private boolean pressedEventEnabled;
    private boolean releasedEventEnabled;
    private int savedPressedValue;
    private final Runnable sensorValueChecker;

    /* renamed from: com.google.appinventor.components.runtime.Ev3TouchSensor.1 */
    class C00081 implements Runnable {
        C00081() {
        }

        public void run() {
            String functionName = ElementType.MATCH_ANY_LOCALNAME;
            if (Ev3TouchSensor.this.bluetooth != null && Ev3TouchSensor.this.bluetooth.IsConnected()) {
                int currentPressedValue = Ev3TouchSensor.this.getPressedValue(functionName);
                if (Ev3TouchSensor.this.savedPressedValue < 0) {
                    Ev3TouchSensor.this.savedPressedValue = currentPressedValue;
                    Ev3TouchSensor.this.eventHandler.postDelayed(this, 50);
                    return;
                }
                if (Ev3TouchSensor.this.savedPressedValue < Ev3TouchSensor.SENSOR_VALUE_THRESHOLD) {
                    if (Ev3TouchSensor.this.releasedEventEnabled && currentPressedValue >= Ev3TouchSensor.SENSOR_VALUE_THRESHOLD) {
                        Ev3TouchSensor.this.Pressed();
                    }
                } else if (Ev3TouchSensor.this.pressedEventEnabled && currentPressedValue < Ev3TouchSensor.SENSOR_VALUE_THRESHOLD) {
                    Ev3TouchSensor.this.Released();
                }
                Ev3TouchSensor.this.savedPressedValue = currentPressedValue;
            }
            Ev3TouchSensor.this.eventHandler.postDelayed(this, 50);
        }
    }

    public Ev3TouchSensor(ComponentContainer container) {
        super(container, "Ev3TouchSensor");
        this.modeString = SENSOR_MODE_TOUCH_STRING;
        this.mode = SENSOR_MODE_TOUCH;
        this.savedPressedValue = -1;
        this.eventHandler = new Handler();
        this.sensorValueChecker = new C00081();
        this.eventHandler.post(this.sensorValueChecker);
        PressedEventEnabled(false);
        ReleasedEventEnabled(false);
    }

    @SimpleFunction(description = "Returns true if the touch sensor is pressed.")
    public boolean IsPressed() {
        return getPressedValue("IsPressed") >= SENSOR_VALUE_THRESHOLD;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void PressedEventEnabled(boolean enabled) {
        this.pressedEventEnabled = enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the Released event should fire when the touch sensor is pressed.")
    public boolean PressedEventEnabled() {
        return this.pressedEventEnabled;
    }

    @SimpleEvent(description = "Called when the touch sensor is pressed.")
    public void Pressed() {
        EventDispatcher.dispatchEvent(this, "Pressed", new Object[SENSOR_MODE_TOUCH]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the Released event should fire when the touch sensor is released.")
    public boolean ReleasedEventEnabled() {
        return this.releasedEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void ReleasedEventEnabled(boolean enabled) {
        this.releasedEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called when the touch sensor is pressed.")
    public void Released() {
        EventDispatcher.dispatchEvent(this, "Released", new Object[SENSOR_MODE_TOUCH]);
    }

    private int getPressedValue(String functionName) {
        return readInputPercentage(functionName, SENSOR_MODE_TOUCH, this.sensorPortNumber, SENSOR_TYPE, this.mode);
    }

    public void onDelete() {
        this.eventHandler.removeCallbacks(this.sensorValueChecker);
        super.onDelete();
    }
}
