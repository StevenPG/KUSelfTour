package com.csc354cde335.kuselftour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static com.csc354cde335.kuselftour.ARCamera.mLastUpdateTime;

/**
 * Created by Steven on 2/28/2015.
 * This class performs displaying of overlaid content
 * using the onDraw method of our view.
 */
public class OverlayView extends View{

    /**
     * The mapping that holds the necessary data from properties file
     */
    Map dictionary = new HashMap();

    /**
     * The Log.e's Debug tag
     */
    public static final String DEBUG_TAG = "Debug";

    /**
     * The sensor data encapsulation object that contains updated sensor data
     */
    public static SensorData sensorsEventListener;

    /**
     * This object is created to update the sensors in the background, unbeknownst
     * to the API user.
     */
    private class SensorUpdater implements Runnable {

        /**
         * Save the context for sensor access
         */
        private Context thread_context;

        /**
         * Timer to recreate the sensor object
         * @param context - activity context
         */

        // Save the context in the constructor
        public SensorUpdater(Context context){
            thread_context = context;
        }

        // Updater function
        public void run() {
            Log.v(DEBUG_TAG, "Thread loop beginning for every 100ms");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sensorsEventListener = new SensorData(thread_context);
                }
            }, 100);
        }
    }

    /**
     * Standard Constructor
     * Assign values to some initial fields
     * Register listeners with sensor objects
     * @param context - current context
     */
    public OverlayView(Context context) {
        super(context);
        // Begin sensor updates
        Runnable sensor_updater = new SensorUpdater(context);
        new Thread(sensor_updater).start();
    }

    /**
     * This draw method overlays important debug information
     * and can be easily commented out to not be displayed.
     * Values can be understood better:
     * http://developer.android.com/reference/android/hardware/SensorEvent.html#values
     * @param canvas - drawing canvas for output
     */
    protected void debugDraw(Canvas canvas){
        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Easy margin alteration
        final int left_margin = canvas.getWidth()/10;
        final int text_size = canvas.getHeight()/43;

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(text_size);
        contentPaint.setColor(Color.WHITE);

        // Display screen resolution available
        // Measured in number of pixels horizontally and vertically
        canvas.drawText("DEBUG " +
                        "Width:" + canvas.getWidth() + " " +
                        "Height:" + canvas.getHeight(),
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30,
                contentPaint);

        // Display accelerometer data
        String[] accelData = sensorsEventListener.getAccelData();
        canvas.drawText("Accelerometer Data: m/s^2 along axis",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*2,
                contentPaint);
        canvas.drawText("x: " + accelData[0] + " " +
                "y: " + accelData[1] + " " +
                "z: " + accelData[2] + " ",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*3,
                contentPaint);

        // Display Gravity data
        String[] gravityData = sensorsEventListener.getGravityData();
        canvas.drawText("Gravity Data: m/s^2 along axis",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*5,
                contentPaint);
        canvas.drawText("x: " + gravityData[0] + " " +
                        "y: " + gravityData[1] + " " +
                        "z: " + gravityData[2] + " ",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*6,
                contentPaint);

        // Display Linear Acceleration data
        String[] linAccelData = sensorsEventListener.getLinAccelData();
        canvas.drawText("Linear Accelerometer Data: Acceleration - Gravity",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*8,
                contentPaint);
        canvas.drawText("x: " + linAccelData[0] + " " +
                        "y: " + linAccelData[1] + " " +
                        "z: " + linAccelData[2] + " ",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*9,
                contentPaint);

        // Display gyroscope data
        String[] gyroData = sensorsEventListener.getGyroData();
        canvas.drawText("Gyroscope Data: Angular Speed around axis",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*11,
                contentPaint);
        canvas.drawText("x: " + gyroData[0] + " " +
                        "y: " + gyroData[1] + " " +
                        "z: " + gyroData[2] + " ",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*12,
                contentPaint);

        // Display compass data
        String[] compassData = sensorsEventListener.getCompassData();
        canvas.drawText("Compass Data: Ambient magnetic field around x",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*14,
                contentPaint);
        canvas.drawText("x: " + compassData[0] + " " +
                        "y: " + compassData[1] + " " +
                        "z: " + compassData[2] + " ",
                canvas.getWidth()/left_margin,
                canvas.getHeight()/30 + text_size*15,
                contentPaint);

        Location currentLocation = ARCamera.mCurrentLocation;

        if(currentLocation != null) {
            //Log.v(DEBUG_TAG, String.valueOf(ARCamera.mCurrentLocation.getLatitude()));
            //Log.v(DEBUG_TAG, String.valueOf(ARCamera.mCurrentLocation.getLongitude()));
            canvas.drawText("GPS Coordinates:",
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size*17,
                    contentPaint);
            canvas.drawText("Latitude: " + currentLocation.getLatitude(),
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size*18,
                    contentPaint);
            canvas.drawText("Longitude: " + currentLocation.getLongitude(),
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size*19,
                    contentPaint);
            canvas.drawText("Provider: " + currentLocation.getProvider(),
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size*20,
                    contentPaint);
            canvas.drawText("Accuracy: " + currentLocation.getAccuracy() + " meters",
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size*21,
                    contentPaint);
            canvas.drawText("Last Update @ " + mLastUpdateTime,
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size*22,
                    contentPaint);
        }
    }

    /**
     * This method display the distance via gps coordinates from each building
     */
    protected void debugBuildingDistance(Canvas canvas){
        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Easy margin alteration
        final int left_margin = canvas.getWidth()/10;
        final int text_size = canvas.getHeight()/43;

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(text_size);
        contentPaint.setColor(Color.WHITE);

        Location currentLocation = ARCamera.mCurrentLocation;
        if(currentLocation != null){

            Location[] buildings = new Location[11];

            Location AF = new Location("AF");
            AF.setLatitude(40.512208); AF.setLongitude(-75.786278);
            buildings[0] = AF;

            Location Beekey = new Location("Beekey");
            Beekey.setLatitude(40.515028); Beekey.setLongitude(-75.785800);
            buildings[1] = Beekey;

            Location Boehm = new Location("Boehm");
            Boehm.setLatitude(40.511950); Boehm.setLongitude(-75.784617);
            buildings[2] = Boehm;

            Location deFran = new Location("deFran");
            deFran.setLatitude(40.514181); deFran.setLongitude(-75.785814);
            buildings[3] = deFran;

            Location Grim = new Location("Grim");
            Grim.setLatitude(40.511314); Grim.setLongitude(-75.785797);
            buildings[4] = Grim;

            Location Lytle = new Location("Lytle");
            Lytle.setLatitude(40.513233); Lytle.setLongitude(-75.787525);
            buildings[5] = Lytle;

            Location Rickenbach = new Location("Rickenbach");
            Rickenbach.setLatitude(40.514400); Rickenbach.setLongitude(-75.784614);
            buildings[6] = Rickenbach;

            Location Rohrbach = new Location("Rohrbach");
            Rohrbach.setLatitude(40.513147); Rohrbach.setLongitude(-75.785419);
            buildings[7] = Rohrbach;

            Location Schaeffer = new Location("Schaeffer");
            Schaeffer.setLatitude(40.511842); Schaeffer.setLongitude(-75.783544);
            buildings[8] = Schaeffer;

            Location Sheridan = new Location("Sheridan");
            Sheridan.setLatitude(40.512644); Sheridan.setLongitude(-75.783053);
            buildings[9] = Sheridan;

            Location Old = new Location("Old");
            Old.setLatitude(40.510250); Old.setLongitude(-75.783061);
            buildings[10] = Old;

            canvas.drawText("Accuracy: " + currentLocation.getAccuracy() + " meters",
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30,
                    contentPaint);

            canvas.drawText("Last Update @ " + mLastUpdateTime,
                    canvas.getWidth()/left_margin,
                    canvas.getHeight()/30 + text_size,
                    contentPaint);

            // Print all locations and distances
            for(int i = 0; i < buildings.length; i++) {
                //canvas.drawText("Distance to " + buildings[i].getProvider(),
                canvas.drawText("Distance to " + buildings[i].getProvider(),
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 30 + text_size * (i + 3),
                        contentPaint);

                canvas.drawText(Float.toString(buildings[i].distanceTo(currentLocation)),
                        canvas.getWidth() / 2,
                        canvas.getHeight() / 30 + text_size * (i + 3),
                        contentPaint);

                // Bearing east of true magnetic north
                canvas.drawText("Bearing toward " + buildings[i].getProvider() + ": " + (360 - Math.abs(currentLocation.bearingTo(buildings[i]))),
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 2 + text_size * (i + 4),
                        contentPaint);

                // Pitch when phone is lying flat
                canvas.drawText("Pitch: " + Float.toString(SensorData.deviceXBearing),
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 2 + text_size * (16),
                        contentPaint);

                // Roll when device is lying flat
                canvas.drawText("Roll: " + Float.toString(SensorData.deviceYBearing),
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 2 + text_size * (17),
                        contentPaint);

                // Degree direction when device is not lying flat
                canvas.drawText("Direction: " + Float.toString(SensorData.deviceZBearing),
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 2 + text_size * (18),
                        contentPaint);
            }
        }
        else{
            canvas.drawText("Current location has not yet been found",
                    canvas.getWidth() / left_margin,
                    canvas.getHeight() / 2 + text_size,
                    contentPaint);
            Log.v(DEBUG_TAG, "Current location has not yet been found");
        }
    }

    /**
     * This draw method is what draws data to the screen
     * @param canvas - what surface to draw onto
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //debugDraw(canvas);
        //debugBuildingDistance(canvas);

        // Design logic for showing text on screen at what building you are pointing at within some distance

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(28);
        contentPaint.setColor(Color.WHITE);

        this.invalidate();
    }
}
