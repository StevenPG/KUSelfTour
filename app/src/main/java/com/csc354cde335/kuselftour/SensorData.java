package com.csc354cde335.kuselftour;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by Steven on 3/2/2015.
 * This class is an encapsulation of the sensor data retrieved
 * by another object. This allows for simple calls to this object,
 * which updates data on a separate thread.
 */
public class SensorData implements SensorEventListener {

    private final String SENSORLOG = "Debug";

    /**
     * Array of floats containing the accelerometer data
     */
    public static float[] accelerometerData;

    /**
     * Array of floats containing the linear acceleration data
     */
    public static float[] linearAccelerationData;

    /**
     * Array of floats containing the gravity data
     */
    public static float[] gravityData;

    /**
     * Array of floats containing the compass data
     */
    public static float[] compassData;

    /**
     * Array of floats containing the gyroscope data
     */
    public static float[] gyroData;

    /**
     * These objects represent each hardware object and their software manager
     */
    static SensorManager sensors; // Sensor Manager
    static Sensor accelSensor; // measured in m/s^2 ( ex. Earth's gravity is 9.81 m/s^2 )
    static Sensor compassSensor; // measured in micro-Teslas (x, y, z) Determine magnetic north
    static Sensor gyroSensor; // measured in rotations around each axis in radians per second
    static Sensor gravitySensor; // measured in m/s^2
    static Sensor linearAccelSensor; // acceleration sensor - gravity sensor

    /**
     * Availability tests for each hardware object
     */
    boolean isAccelAvailable;
    boolean isCompassAvailable;
    boolean isGravityAvailable;
    boolean isGyroAvailable;
    boolean isLinAccelAvailable;

    /**
     * It is in the constructor that the sensors are initialized and listeners
     * are registered to them for the lifetime of the object.
     * @param context
     */
    public SensorData(Context context) {
        Log.v("Log", "Creating sensor object");

        // Assign values to the Sensor objects
        sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gravitySensor = sensors.getDefaultSensor(Sensor.TYPE_GRAVITY);
        linearAccelSensor = sensors.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Assign listeners to sensor objects
        // Use SENSOR_DELAY_UI for now to slow down read rate
        isAccelAvailable = sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_UI);
        isCompassAvailable = sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_UI);
        isGyroAvailable = sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_UI);
        isGravityAvailable = sensors.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_UI);
        isLinAccelAvailable = sensors.registerListener(this, linearAccelSensor, SensorManager.SENSOR_DELAY_UI);
    }

    /** Getter methods for important fields as Strings**/
    public String[] getAccelData(){
        String[] AccelData = new String[accelerometerData.length];
        for(int i = 0; i < accelerometerData.length; i++){
            AccelData[i] = Float.toString(accelerometerData[i]);
        }
        return AccelData;
    }
    public String[] getGyroData(){
        String[] GyroData = new String[gyroData.length];
        for(int i = 0; i < gyroData.length; i++){
            GyroData[i] = Float.toString(gyroData[i]);
        }
        return GyroData;
    }
    public String[] getCompassData(){
        String[] CompassData = new String[compassData.length];
        for(int i = 0; i < compassData.length; i++){
            CompassData[i] = Float.toString(compassData[i]);
        }

        return CompassData;
    }
    public String[] getGravityData(){
        String[] GravityData = new String[gravityData.length];
        for(int i = 0; i < gravityData.length; i++){
            GravityData[i] = Float.toString(gravityData[i]);
        }
        return GravityData;
    }
    public String[] getLinAccelData(){
        String[] LinAccelData = new String[linearAccelerationData.length];
        for(int i = 0; i < linearAccelerationData.length; i++){
            LinAccelData[i] = Float.toString(linearAccelerationData[i]);
        }
        return LinAccelData;
    }

    /**
     * This function serves the purpose of making acceleration more usable
     * by defining what exactly is being shown and putting it out for readability.
     * Units are measured in meters per second squared ( m/s^2 )
     * This function will apply a low-pass filter by rounding off the lower values
     * @param accelData
     * @return
     */
    public float[] interpretAccelerometer(float[] accelData){
        float[] roundedValues = new float[accelData.length];
        for(int i = 0; i < accelData.length; i++){
            roundedValues[i] = (float) (Math.round(accelData[i] * 10000.0) / 10000.0);
        }
        return roundedValues;
    }

    /**
     * This function serves the purpose of making acceleration more usable
     * by defining what exactly is being shown and putting it out for readability.
     * Units are measured in meters per second squared ( m/s^2 )
     * This function will apply a low-pass filter by rounding off the lower values
     * @param gravityData
     * @return
     */
    public float[] interpretGravity(float[] gravityData){
        float[] roundedValues = new float[gravityData.length];
        for(int i = 0; i < gravityData.length; i++){
            roundedValues[i] = (float) (Math.round(gravityData[i] * 10000.0) / 10000.0);
        }
        return roundedValues;
    }

    /**
     * This function serves the purpose of making acceleration more usable
     * by defining what exactly is being shown and putting it out for readability.
     * Units are measured in meters per second squared ( m/s^2 )
     * This function will apply a low-pass filter by rounding off the lower values
     * @return
     */
    public float[] interpretLinAccel(float[] linearAccelerationData){
        float[] roundedValues = new float[linearAccelerationData.length];
        for(int i = 0; i < linearAccelerationData.length; i++){
            roundedValues[i] = (float) (Math.round(linearAccelerationData[i] * 10000.0) / 10000.0);
        }
        return roundedValues;
    }

    /**
     * This function serves the purpose of making gyroscope more usable
     * by defining what exactly is being shown and putting it out for readability.
     * Units are measured in Angular speed around an axis
     */
    public float[] interpretGyroscope(float[] gyroData){
        float[] roundedValues = new float[gyroData.length];
        for(int i = 0; i < gyroData.length; i++){
            roundedValues[i] = (float) (Math.round(gyroData[i] * 100000.0) / 100000.0);
        }
        return roundedValues;
    }

    /**
     * This function serves the purpose of making compass more usable
     * by defining what exactly is being shown and putting it out for readability.
     * Units are measured in ambient magnetic field
     */
    public float[] interpretCompass(float[] compassData){
        float[] roundedValues = new float[compassData.length];
        for(int i = 0; i < compassData.length; i++){
            roundedValues[i] = (float) (Math.round(compassData[i] * 100000.0) / 100000.0);
        }
        return roundedValues;
    }

    /**
     * This function manually updates the data fields stored in the class.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        switch(event.sensor.getType()){

            case Sensor.TYPE_ACCELEROMETER:
                // Get new value
                accelerometerData = event.values.clone();

                // Pre-rounded value
                //Log.e(SENSORLOG, Arrays.toString(accelerometerData));

                // Overwrite with rounded value for printing
                accelerometerData = interpretAccelerometer(accelerometerData);

                // Post rounded value
                //Log.e(SENSORLOG, Arrays.toString(accelerometerData));

                break;

            case Sensor.TYPE_GRAVITY:
                // Get new value
                gravityData = event.values.clone();

                // Pre-rounded value
                //Log.e(SENSORLOG, Arrays.toString(gravityData));

                // Overwrite with rounded value for printing
                gravityData = interpretGravity(gravityData);

                // Post rounded value
                //Log.e(SENSORLOG, Arrays.toString(gravityData));
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                // Get new value
                linearAccelerationData = event.values.clone();

                // Pre-rounded value
                //Log.e(SENSORLOG, Arrays.toString(linearAccelerationData));

                // Overwrite with rounded value for printing
                linearAccelerationData = interpretLinAccel(linearAccelerationData);

                // Post rounded value
                //Log.e(SENSORLOG, Arrays.toString(linearAccelerationData));
                break;

            case Sensor.TYPE_GYROSCOPE:
                // Get new value
                gyroData = event.values.clone();

                // Pre-rounded value
                //Log.e(SENSORLOG, Arrays.toString(gyroData));

                // Overwrite with rounded value for printing
                gyroData = interpretGyroscope(gyroData);

                // Post rounded value
                //Log.e(SENSORLOG, Arrays.toString(gyroData));

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                // Get new value
                compassData = event.values.clone();

                // Pre-rounded value
                //Log.e(SENSORLOG, Arrays.toString(compassData));

                // Overwrite with rounded value for printing
                compassData = interpretCompass(compassData);

                // Post rounded value
                //Log.e(SENSORLOG, Arrays.toString(compassData));

                break;
         }
    }

    // Implement onPause and onResume just to save battery life

    /**
     @Override
     protected void onResume() {
     super.onResume();
     // for the system's orientation sensor registered listeners
     mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
     SensorManager.SENSOR_DELAY_GAME);
     }

     @Override
     protected void onPause() {
     super.onPause();
     // to stop the listener and save battery
     mSensorManager.unregisterListener(this);
     }

     */

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method has no bearing on the application
    }
}
