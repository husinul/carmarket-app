package com.google.appinventor.components.runtime;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.expr.SetExp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.ElementType;
import gnu.lists.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "Non-visible component providing location information, including longitude, latitude, altitude (if supported by the device), speed (if supported by the device), and address.  This can also perform \"geocoding\", converting a given address (not necessarily the current one) to a latitude (with the <code>LatitudeFromAddress</code> method) and a longitude (with the <code>LongitudeFromAddress</code> method).</p>\n<p>In order to function, the component must have its <code>Enabled</code> property set to True, and the device must have location sensing enabled through wireless networks or GPS satellites (if outdoors).</p>\nLocation information might not be immediately available when an app starts.  You'll have to wait a short time for a location provider to be found and used, or wait for the OnLocationChanged event", iconName = "images/locationSensor.png", nonVisible = true, version = 3)
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.ACCESS_FINE_LOCATION,android.permission.ACCESS_COARSE_LOCATION,android.permission.ACCESS_MOCK_LOCATION,android.permission.ACCESS_LOCATION_EXTRA_COMMANDS")
public class LocationSensor extends AndroidNonvisibleComponent implements Component, OnStopListener, OnResumeListener, Deleteable {
    public static final int UNKNOWN_VALUE = 0;
    private List<String> allProviders;
    private double altitude;
    private final Handler androidUIHandler;
    private int distanceInterval;
    private boolean enabled;
    private Geocoder geocoder;
    private final Handler handler;
    private boolean hasAltitude;
    private boolean hasLocationData;
    private Location lastLocation;
    private double latitude;
    private boolean listening;
    private final Criteria locationCriteria;
    private final LocationManager locationManager;
    private LocationProvider locationProvider;
    private double longitude;
    private MyLocationListener myLocationListener;
    private boolean providerLocked;
    private String providerName;
    private float speed;
    private int timeInterval;

    private class MyLocationListener implements LocationListener {

        /* renamed from: com.google.appinventor.components.runtime.LocationSensor.MyLocationListener.1 */
        class C00581 implements Runnable {
            final /* synthetic */ double val$argAltitude;
            final /* synthetic */ double val$argLatitude;
            final /* synthetic */ double val$argLongitude;
            final /* synthetic */ float val$argSpeed;

            C00581(double d, double d2, double d3, float f) {
                this.val$argLatitude = d;
                this.val$argLongitude = d2;
                this.val$argAltitude = d3;
                this.val$argSpeed = f;
            }

            public void run() {
                LocationSensor.this.LocationChanged(this.val$argLatitude, this.val$argLongitude, this.val$argAltitude, this.val$argSpeed);
            }
        }

        private MyLocationListener() {
        }

        public void onLocationChanged(Location location) {
            LocationSensor.this.lastLocation = location;
            LocationSensor.this.longitude = location.getLongitude();
            LocationSensor.this.latitude = location.getLatitude();
            LocationSensor.this.speed = location.getSpeed();
            if (location.hasAltitude()) {
                LocationSensor.this.hasAltitude = true;
                LocationSensor.this.altitude = location.getAltitude();
            }
            if (LocationSensor.this.longitude != 0.0d || LocationSensor.this.latitude != 0.0d) {
                LocationSensor.this.hasLocationData = true;
                LocationSensor.this.androidUIHandler.post(new C00581(LocationSensor.this.latitude, LocationSensor.this.longitude, LocationSensor.this.altitude, LocationSensor.this.speed));
            }
        }

        public void onProviderDisabled(String provider) {
            LocationSensor.this.StatusChanged(provider, "Disabled");
            LocationSensor.this.stopListening();
            if (LocationSensor.this.enabled) {
                LocationSensor.this.RefreshProvider();
            }
        }

        public void onProviderEnabled(String provider) {
            LocationSensor.this.StatusChanged(provider, "Enabled");
            LocationSensor.this.RefreshProvider();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case Sequence.EOF_VALUE /*0*/:
                    LocationSensor.this.StatusChanged(provider, "OUT_OF_SERVICE");
                    if (provider.equals(LocationSensor.this.providerName)) {
                        LocationSensor.this.stopListening();
                        LocationSensor.this.RefreshProvider();
                    }
                case ParseFormat.SEEN_MINUS /*1*/:
                    LocationSensor.this.StatusChanged(provider, "TEMPORARILY_UNAVAILABLE");
                case SetExp.DEFINING_FLAG /*2*/:
                    LocationSensor.this.StatusChanged(provider, "AVAILABLE");
                    if (!provider.equals(LocationSensor.this.providerName) && !LocationSensor.this.allProviders.contains(provider)) {
                        LocationSensor.this.RefreshProvider();
                    }
                default:
            }
        }
    }

    public LocationSensor(ComponentContainer container) {
        super(container.$form());
        this.providerLocked = false;
        this.listening = false;
        this.longitude = 0.0d;
        this.latitude = 0.0d;
        this.altitude = 0.0d;
        this.speed = 0.0f;
        this.hasLocationData = false;
        this.hasAltitude = false;
        this.androidUIHandler = new Handler();
        this.enabled = true;
        this.handler = new Handler();
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.timeInterval = 60000;
        this.distanceInterval = 5;
        Context context = container.$context();
        this.geocoder = new Geocoder(context);
        this.locationManager = (LocationManager) context.getSystemService("location");
        this.locationCriteria = new Criteria();
        this.myLocationListener = new MyLocationListener();
        this.allProviders = new ArrayList();
        Enabled(this.enabled);
    }

    @SimpleEvent
    public void LocationChanged(double latitude, double longitude, double altitude, float speed) {
        EventDispatcher.dispatchEvent(this, "LocationChanged", Double.valueOf(latitude), Double.valueOf(longitude), Double.valueOf(altitude), Float.valueOf(speed));
    }

    @SimpleEvent
    public void StatusChanged(String provider, String status) {
        if (this.enabled) {
            EventDispatcher.dispatchEvent(this, "StatusChanged", provider, status);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ProviderName() {
        if (this.providerName == null) {
            return "NO PROVIDER";
        }
        return this.providerName;
    }

    @SimpleProperty
    public void ProviderName(String providerName) {
        this.providerName = providerName;
        if (empty(providerName) || !startProvider(providerName)) {
            RefreshProvider();
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean ProviderLocked() {
        return this.providerLocked;
    }

    @SimpleProperty
    public void ProviderLocked(boolean lock) {
        this.providerLocked = lock;
    }

    @DesignerProperty(defaultValue = "60000", editorType = "sensor_time_interval")
    @SimpleProperty
    public void TimeInterval(int interval) {
        if (interval >= 0 && interval <= 1000000) {
            this.timeInterval = interval;
            if (this.enabled) {
                RefreshProvider();
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Determines the minimum time interval, in milliseconds, that the sensor will try to use for sending out location updates. However, location updates will only be received when the location of the phone actually changes, and use of the specified time interval is not guaranteed. For example, if 1000 is used as the time interval, location updates will never be fired sooner than 1000ms, but they may be fired anytime after.")
    public int TimeInterval() {
        return this.timeInterval;
    }

    @DesignerProperty(defaultValue = "5", editorType = "sensor_dist_interval")
    @SimpleProperty
    public void DistanceInterval(int interval) {
        if (interval >= 0 && interval <= 1000) {
            this.distanceInterval = interval;
            if (this.enabled) {
                RefreshProvider();
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Determines the minimum distance interval, in meters, that the sensor will try to use for sending out location updates. For example, if this is set to 5, then the sensor will fire a LocationChanged event only after 5 meters have been traversed. However, the sensor does not guarantee that an update will be received at exactly the distance interval. It may take more than 5 meters to fire an event, for instance.")
    public int DistanceInterval() {
        return this.distanceInterval;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HasLongitudeLatitude() {
        return this.hasLocationData && this.enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HasAltitude() {
        return this.hasAltitude && this.enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HasAccuracy() {
        return Accuracy() != 0.0d && this.enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public double Longitude() {
        return this.longitude;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public double Latitude() {
        return this.latitude;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public double Altitude() {
        return this.altitude;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public double Accuracy() {
        if (this.lastLocation != null && this.lastLocation.hasAccuracy()) {
            return (double) this.lastLocation.getAccuracy();
        }
        if (this.locationProvider != null) {
            return (double) this.locationProvider.getAccuracy();
        }
        return 0.0d;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void Enabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            RefreshProvider();
        } else {
            stopListening();
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String CurrentAddress() {
        if ((this.hasLocationData && this.latitude <= 90.0d && this.latitude >= -90.0d && this.longitude <= 180.0d) || this.longitude >= -180.0d) {
            try {
                List<Address> addresses = this.geocoder.getFromLocation(this.latitude, this.longitude, 1);
                if (addresses != null && addresses.size() == 1) {
                    Address address = (Address) addresses.get(0);
                    if (address != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i));
                            sb.append("\n");
                        }
                        return sb.toString();
                    }
                }
            } catch (Exception e) {
                if ((e instanceof IllegalArgumentException) || (e instanceof IOException) || (e instanceof IndexOutOfBoundsException)) {
                    Log.e("LocationSensor", "Exception thrown by getting current address " + e.getMessage());
                } else {
                    Log.e("LocationSensor", "Unexpected exception thrown by getting current address " + e.getMessage());
                }
            }
        }
        return "No address available";
    }

    @SimpleFunction(description = "Derives latitude of given address")
    public double LatitudeFromAddress(String locationName) {
        try {
            List<Address> addressObjs = this.geocoder.getFromLocationName(locationName, 1);
            Log.i("LocationSensor", "latitude addressObjs size is " + addressObjs.size() + " for " + locationName);
            if (addressObjs != null && addressObjs.size() != 0) {
                return ((Address) addressObjs.get(0)).getLatitude();
            }
            throw new IOException(ElementType.MATCH_ANY_LOCALNAME);
        } catch (IOException e) {
            this.form.dispatchErrorOccurredEvent(this, "LatitudeFromAddress", ErrorMessages.ERROR_LOCATION_SENSOR_LATITUDE_NOT_FOUND, locationName);
            return 0.0d;
        }
    }

    @SimpleFunction(description = "Derives longitude of given address")
    public double LongitudeFromAddress(String locationName) {
        try {
            List<Address> addressObjs = this.geocoder.getFromLocationName(locationName, 1);
            Log.i("LocationSensor", "longitude addressObjs size is " + addressObjs.size() + " for " + locationName);
            if (addressObjs != null && addressObjs.size() != 0) {
                return ((Address) addressObjs.get(0)).getLongitude();
            }
            throw new IOException(ElementType.MATCH_ANY_LOCALNAME);
        } catch (IOException e) {
            this.form.dispatchErrorOccurredEvent(this, "LongitudeFromAddress", ErrorMessages.ERROR_LOCATION_SENSOR_LONGITUDE_NOT_FOUND, locationName);
            return 0.0d;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public List<String> AvailableProviders() {
        return this.allProviders;
    }

    public void RefreshProvider() {
        stopListening();
        if (!this.providerLocked || empty(this.providerName)) {
            this.allProviders = this.locationManager.getProviders(true);
            String bProviderName = this.locationManager.getBestProvider(this.locationCriteria, true);
            if (!(bProviderName == null || bProviderName.equals(this.allProviders.get(0)))) {
                this.allProviders.add(0, bProviderName);
            }
            for (String providerN : this.allProviders) {
                this.listening = startProvider(providerN);
                if (this.listening) {
                    if (!this.providerLocked) {
                        this.providerName = providerN;
                        return;
                    }
                    return;
                }
            }
            return;
        }
        this.listening = startProvider(this.providerName);
    }

    private boolean startProvider(String providerName) {
        this.providerName = providerName;
        LocationProvider tLocationProvider = this.locationManager.getProvider(providerName);
        if (tLocationProvider == null) {
            Log.d("LocationSensor", "getProvider(" + providerName + ") returned null");
            return false;
        }
        stopListening();
        this.locationProvider = tLocationProvider;
        this.locationManager.requestLocationUpdates(providerName, (long) this.timeInterval, (float) this.distanceInterval, this.myLocationListener);
        this.listening = true;
        return true;
    }

    private void stopListening() {
        if (this.listening) {
            this.locationManager.removeUpdates(this.myLocationListener);
            this.locationProvider = null;
            this.listening = false;
        }
    }

    public void onResume() {
        if (this.enabled) {
            RefreshProvider();
        }
    }

    public void onStop() {
        stopListening();
    }

    public void onDelete() {
        stopListening();
    }

    private boolean empty(String s) {
        return s == null || s.length() == 0;
    }
}
