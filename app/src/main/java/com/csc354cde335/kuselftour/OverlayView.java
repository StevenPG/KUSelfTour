package com.csc354cde335.kuselftour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.util.Log;
import android.view.View;

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
     * This draw method is what draws data to the screen
     * @param canvas - what surface to draw onto
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        debugDraw(canvas);

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(28);
        contentPaint.setColor(Color.WHITE);

        // Primary Map
        /*
        // use roll for screen rotation
        float[] orientation = getOrientation();
        canvas.rotate((float)(0.0f - Math.toDegrees(orientation[2])));

        // Translate, but normalize for the FOV of the camera
        float curBearingToMW = lastLocation.bearingTo(StevesHouse);
        float dx = (float) ( (canvas.getWidth()/ ArDisplayView.horizontalFOV) * (Math.toDegrees(orientation[0])-curBearingToMW));
        float dy = (float) ( (canvas.getHeight()/ ArDisplayView.verticalFOV) * Math.toDegrees(orientation[1])) ;

        // wait to translate the dx so the horizon doesn't get pushed off
        canvas.translate(0.0f, 0.0f-dy);

        // Create a line big enough to draw regardless of rotation and translation
        canvas.drawLine(0f - canvas.getHeight(),
                canvas.getHeight()/2,
                canvas.getWidth()+canvas.getHeight(),
                canvas.getHeight()/2,
                contentPaint);

        // now translate the dx
        canvas.translate(0.0f-dx, 0.0f);

        // draw a point
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, 8.0f, contentPaint);
        */
        this.invalidate();
    }
}
