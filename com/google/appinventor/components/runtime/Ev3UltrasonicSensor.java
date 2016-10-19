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
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.kawa.xml.ElementType;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to an ultrasonic sensor on a LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
public class Ev3UltrasonicSensor extends LegoMindstormsEv3Sensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 30;
    private static final String DEFAULT_SENSOR_MODE_STRING = "cm";
    private static final int DEFAULT_TOP_OF_RANGE = 90;
    private static final int DELAY_MILLISECONDS = 50;
    private static final int SENSOR_MODE_CM = 0;
    private static final String SENSOR_MODE_CM_STRING = "cm";
    private static final int SENSOR_MODE_INCH = 1;
    private static final String SENSOR_MODE_INCH_STRING = "inch";
    private static final int SENSOR_TYPE = 30;
    private boolean aboveRangeEventEnabled;
    private boolean belowRangeEventEnabled;
    private int bottomOfRange;
    private Handler eventHandler;
    private int mode;
    private String modeString;
    private double previousDistance;
    private final Runnable sensorValueChecker;
    private int topOfRange;
    private boolean withinRangeEventEnabled;

    /* renamed from: com.google.appinventor.components.runtime.Ev3UltrasonicSensor.1 */
    class C00091 implements Runnable {
        C00091() {
        }

        public void run() {
            String functionName = ElementType.MATCH_ANY_LOCALNAME;
            if (Ev3UltrasonicSensor.this.bluetooth != null && Ev3UltrasonicSensor.this.bluetooth.IsConnected()) {
                double currentDistance = Ev3UltrasonicSensor.this.getDistance(functionName);
                if (Ev3UltrasonicSensor.this.previousDistance < 0.0d) {
                    Ev3UltrasonicSensor.this.previousDistance = currentDistance;
                    Ev3UltrasonicSensor.this.eventHandler.postDelayed(this, 50);
                    return;
                }
                if (currentDistance < ((double) Ev3UltrasonicSensor.this.bottomOfRange)) {
                    if (Ev3UltrasonicSensor.this.belowRangeEventEnabled && Ev3UltrasonicSensor.this.previousDistance >= ((double) Ev3UltrasonicSensor.this.bottomOfRange)) {
                        Ev3UltrasonicSensor.this.BelowRange();
                    }
                } else if (currentDistance > ((double) Ev3UltrasonicSensor.this.topOfRange)) {
                    if (Ev3UltrasonicSensor.this.aboveRangeEventEnabled && Ev3UltrasonicSensor.this.previousDistance <= ((double) Ev3UltrasonicSensor.this.topOfRange)) {
                        Ev3UltrasonicSensor.this.AboveRange();
                    }
                } else if (Ev3UltrasonicSensor.this.withinRangeEventEnabled && (Ev3UltrasonicSensor.this.previousDistance < ((double) Ev3UltrasonicSensor.this.bottomOfRange) || Ev3UltrasonicSensor.this.previousDistance > ((double) Ev3UltrasonicSensor.this.topOfRange))) {
                    Ev3UltrasonicSensor.this.WithinRange();
                }
                Ev3UltrasonicSensor.this.previousDistance = currentDistance;
            }
            Ev3UltrasonicSensor.this.eventHandler.postDelayed(this, 50);
        }
    }

    public Ev3UltrasonicSensor(ComponentContainer container) {
        super(container, "Ev3UltrasonicSensor");
        this.modeString = SENSOR_MODE_CM_STRING;
        this.mode = SENSOR_MODE_CM;
        this.previousDistance = -1.0d;
        this.eventHandler = new Handler();
        this.sensorValueChecker = new C00091();
        this.eventHandler.post(this.sensorValueChecker);
        TopOfRange(DEFAULT_TOP_OF_RANGE);
        BottomOfRange(SENSOR_TYPE);
        BelowRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        Unit(SENSOR_MODE_CM_STRING);
    }

    @SimpleFunction(description = "Returns the current distance in centimeters as a value between 0 and 254, or -1 if the distance can not be read.")
    public double GetDistance() {
        return getDistance("GetDistance");
    }

    private double getDistance(String functionName) {
        double distance = readInputSI(functionName, SENSOR_MODE_CM, this.sensorPortNumber, SENSOR_TYPE, this.mode);
        return distance == 255.0d ? -1.0d : distance;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int BottomOfRange() {
        return this.bottomOfRange;
    }

    @DesignerProperty(defaultValue = "30", editorType = "non_negative_integer")
    @SimpleProperty
    public void BottomOfRange(int bottomOfRange) {
        this.bottomOfRange = bottomOfRange;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int TopOfRange() {
        return this.topOfRange;
    }

    @DesignerProperty(defaultValue = "90", editorType = "non_negative_integer")
    @SimpleProperty
    public void TopOfRange(int topOfRange) {
        this.topOfRange = topOfRange;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the distance goes below the BottomOfRange.")
    public boolean BelowRangeEventEnabled() {
        return this.belowRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void BelowRangeEventEnabled(boolean enabled) {
        this.belowRangeEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called when the detected distance has gone below the range.")
    public void BelowRange() {
        EventDispatcher.dispatchEvent(this, "BelowRange", new Object[SENSOR_MODE_CM]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the WithinRange event should fire when the distance goes between the BottomOfRange and the TopOfRange.")
    public boolean WithinRangeEventEnabled() {
        return this.withinRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void WithinRangeEventEnabled(boolean enabled) {
        this.withinRangeEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called when the detected distance has gone within the range.")
    public void WithinRange() {
        EventDispatcher.dispatchEvent(this, "WithinRange", new Object[SENSOR_MODE_CM]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the AboveRange event should fire when the distance goes above the TopOfRange.")
    public boolean AboveRangeEventEnabled() {
        return this.aboveRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void AboveRangeEventEnabled(boolean enabled) {
        this.aboveRangeEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called when the detected distance has gone above the range.")
    public void AboveRange() {
        EventDispatcher.dispatchEvent(this, "AboveRange", new Object[SENSOR_MODE_CM]);
    }

    @DesignerProperty(defaultValue = "cm", editorType = "lego_ev3_ultrasonic_sensor_mode")
    @SimpleProperty
    public void Unit(String unitName) {
        String functionName = "Unit";
        try {
            setMode(unitName);
        } catch (IllegalArgumentException e) {
            Form form = this.form;
            Object[] objArr = new Object[SENSOR_MODE_INCH];
            objArr[SENSOR_MODE_CM] = functionName;
            form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, objArr);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The distance unit, which can be either \"cm\" or \"inch\".")
    public String Unit() {
        return this.modeString;
    }

    @SimpleFunction(description = "Measure the distance in centimeters.")
    public void SetCmUnit() {
        String functionName = "SetCmUnit";
        try {
            setMode(SENSOR_MODE_CM_STRING);
        } catch (IllegalArgumentException e) {
            Form form = this.form;
            Object[] objArr = new Object[SENSOR_MODE_INCH];
            objArr[SENSOR_MODE_CM] = functionName;
            form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, objArr);
        }
    }

    @SimpleFunction(description = "Measure the distance in inches.")
    public void SetInchUnit() {
        String functionName = "SetInchUnit";
        try {
            setMode(SENSOR_MODE_INCH_STRING);
        } catch (IllegalArgumentException e) {
            Form form = this.form;
            Object[] objArr = new Object[SENSOR_MODE_INCH];
            objArr[SENSOR_MODE_CM] = functionName;
            form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, objArr);
        }
    }

    private void setMode(String newModeString) {
        this.previousDistance = -1.0d;
        if (SENSOR_MODE_CM_STRING.equals(newModeString)) {
            this.mode = SENSOR_MODE_CM;
        } else if (SENSOR_MODE_INCH_STRING.equals(newModeString)) {
            this.mode = SENSOR_MODE_INCH;
        } else {
            throw new IllegalArgumentException();
        }
        this.modeString = newModeString;
    }

    public void onDelete() {
        this.eventHandler.removeCallbacks(this.sensorValueChecker);
        super.onDelete();
    }
}
