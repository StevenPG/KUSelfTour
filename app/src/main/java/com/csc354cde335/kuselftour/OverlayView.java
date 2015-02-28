package com.csc354cde335.kuselftour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

/**
 * Created by Steven on 2/28/2015.
 * This class performs displaying of overlaid content
 * using the onDraw method of our view.
 */
public class OverlayView extends View implements SensorEventListener{

    /**
     * The Log.e's Debug tag
     */
    public static final String DEBUG_TAG = "OverlayView Log";

    /**
     * String containing last known value of device Accelerometer.
     */
    String accelData = "Accelerometer Data";

    /**
     * String containing last known value of device Compass.
     */
    String compassData = "Compass Data";

    /**
     * String containing last known value of device Gyroscope.
     */
    String gyroData = "Gyro Data";

    /**
     * These objects represent each hardware object and their software manager
     */
    SensorManager sensors; // Sensor Manager
    Sensor accelSensor; // measured in m/s^2 ( ex. Earth's gravity is 9.81 m/s^2 )
    Sensor compassSensor; // measured in micro-Teslas (x, y, z) Determine magnetic north
    Sensor gyroSensor; // measured in rotations around each axis in radians per second

    /**
     * Availability tests for each hardware object
     */
    boolean isAccelAvailable;
    boolean isCompassAvailable;
    boolean isGyroAvailable;

    /**
     * Standard Constructor
     * Assign values to some initial fields
     * Register listeners with sensor objects
     * @param context
     */
    public OverlayView(Context context) {
        super(context);

        // Assign values to the Sensor objects
        sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        isAccelAvailable = sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        isCompassAvailable = sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
        isGyroAvailable = sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * This draw method is what draws data to the screen
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.CENTER);
        contentPaint.setTextSize(20);
        contentPaint.setColor(Color.RED);

        // Write text data to the overlay
        canvas.drawText(accelData, canvas.getWidth()/2, canvas.getHeight()/4, contentPaint);
        canvas.drawText(compassData, canvas.getWidth()/2, canvas.getHeight()/2, contentPaint);
        canvas.drawText(gyroData, canvas.getWidth()/2, (canvas.getHeight()*3)/4, contentPaint);
    }

    /**
     * Here we have the view react to sensor changes.
     * The class is registered for several different changes
     * so this method may be called for any sensor change.
     *
     * This class is saving the sensor data that changed and forcing
     * the view to update its content by invalidating the view and
     * forcing it to be redrawn.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        // Define text values
        StringBuilder msg = new StringBuilder(event.sensor.getName()).append(" ");
        for(float value: event.values){
            msg.append("[").append(value).append("]");
        }

        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                accelData = msg.toString();
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroData = msg.toString();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                compassData = msg.toString();
                break;
        }

        // Force a redraw of the current view
        this.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
