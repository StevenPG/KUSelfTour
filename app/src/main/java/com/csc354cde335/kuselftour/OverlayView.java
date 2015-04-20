package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     *  Save context for adding things programatically
     */
    private Context context;

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
        this.context = context;
        // Begin sensor updates
        Runnable sensor_updater = new SensorUpdater(context);
        new Thread(sensor_updater).start();
    }

    /**
     * This function will act as a universal creator of Location lists for easy access and use
     * @return - returns list of buildings with information
     */
    private Location[] populateBuildings(){
        Location[] buildings = new Location[13];

        Location AF = new Location(KUSelfTourConstants.AF);
        AF.setLatitude(40.512208); AF.setLongitude(-75.786278);
        buildings[0] = AF;

        Location Beekey = new Location(KUSelfTourConstants.BEEKEY);
        Beekey.setLatitude(40.515028); Beekey.setLongitude(-75.785800);
        buildings[1] = Beekey;

        Location Boehm = new Location(KUSelfTourConstants.BOEHM);
        Boehm.setLatitude(40.511950); Boehm.setLongitude(-75.784617);
        buildings[2] = Boehm;

        Location deFran = new Location(KUSelfTourConstants.DEFRANCESCO);
        deFran.setLatitude(40.514181); deFran.setLongitude(-75.785814);
        buildings[3] = deFran;

        Location Grim = new Location(KUSelfTourConstants.GRIM);
        Grim.setLatitude(40.511314); Grim.setLongitude(-75.785797);
        buildings[4] = Grim;

        Location Grad = new Location(KUSelfTourConstants.GRAD_CENTER);
        Grad.setLatitude(40.511164); Grad.setLongitude(-75.783347);
        buildings[5] = Grad;

        Location Lytle = new Location(KUSelfTourConstants.LYTLE);
        Lytle.setLatitude(40.513233); Lytle.setLongitude(-75.787525);
        buildings[6] = Lytle;

        Location Rickenbach = new Location(KUSelfTourConstants.RICKENBACH);
        Rickenbach.setLatitude(40.514400); Rickenbach.setLongitude(-75.784614);
        buildings[7] = Rickenbach;

        Location Rohrbach = new Location(KUSelfTourConstants.ROHRBACH);
        Rohrbach.setLatitude(40.513147); Rohrbach.setLongitude(-75.785419);
        buildings[8] = Rohrbach;

        Location Schaeffer = new Location(KUSelfTourConstants.SCHAEFFER_AUDITORIUM);
        Schaeffer.setLatitude(40.511842); Schaeffer.setLongitude(-75.783544);
        buildings[9] = Schaeffer;

        Location Sheridan = new Location(KUSelfTourConstants.SHERIDAN);
        Sheridan.setLatitude(40.512644); Sheridan.setLongitude(-75.783053);
        buildings[10] = Sheridan;

        Location SUB = new Location(KUSelfTourConstants.STUDENT_UNION_BUILDING);
        SUB.setLatitude(40.513586); SUB.setLongitude(-75.783917);
        buildings[11] = SUB;

        Location Old = new Location(KUSelfTourConstants.OLD_MAIN);
        Old.setLatitude(40.510250); Old.setLongitude(-75.783061);
        buildings[12] = Old;

        return buildings;
    }

    /**
     *  This function returns a string that represents the closest building to the gps coordinates
     */
    private String getClosestBuilding(){

        // Parralel Arrays
        Location[] buildings = populateBuildings();
        float[] distanceToBuildings;
        distanceToBuildings = new float[buildings.length];
        Float minValue = Float.MAX_VALUE;
        int minIndex = 0;

        // Get location
        Location currentLocation = ARCamera.mCurrentLocation;

        if(currentLocation != null) {
            // record minimum distance index value
            for (int i = 0; i < buildings.length; i++) {
                distanceToBuildings[i] = currentLocation.distanceTo(buildings[i]);
                if (distanceToBuildings[i] < minValue) {
                    minValue = distanceToBuildings[i];
                    minIndex = i;
                    // Log.v("Debug", minValue + " " + minIndex);
                }
            }
            //Log.e("Debug", buildings[min].getProvider());
            return buildings[minIndex].getProvider();
        }
        Log.v("Debug", "Failed to find closest building...");
        return "Failing to find closest building";
    }

    /**
     * This getClosestBuilding method takes a list of available buildings
     */
    protected Location getClosestBuilding(Location currentLocation, ArrayList buildingsBeingFaced){
        Log.e("", Float.toString(currentLocation.getAccuracy()));
        Location closestBuilding = new Location("NA");
        if(buildingsBeingFaced.size() > 0) {
            closestBuilding = (Location) buildingsBeingFaced.get(0);
            for (int i = 0; i < buildingsBeingFaced.size(); i++) {
                // Get distance to closest building
                Float closestSoFar = currentLocation.distanceTo(closestBuilding);
                // Get distance to current building and test
                Float maybeCloser = currentLocation.distanceTo((Location) buildingsBeingFaced.get(i));
                if (maybeCloser < closestSoFar) {
                    closestBuilding = (Location) buildingsBeingFaced.get(i);
                }
            }
        }

        return closestBuilding;
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
     * This will return a list of all of the buildings the device is within 10 degrees of looking at
     */
    protected Location getListOfBuildingsDeviceIsFacingAndPrint(Location currentLocation, Location [] buildings, Float currentDirection){
        ArrayList<Location> buildingsBeingFaced = new ArrayList();

        for (Location building : buildings) {
            Float buildingBearing = 360 - Math.abs(currentLocation.bearingTo(building));
            Float facingBuilding = buildingBearing - currentDirection;
            if (facingBuilding <= MainMenu.FieldOfView && facingBuilding >= -(MainMenu.FieldOfView) ) {
                /*Log.e(DEBUG_TAG, "Facing " + buildings[i]);
                Log.e(DEBUG_TAG, Float.toString(currentLocation.bearingTo(buildings[i])));
                Log.e(DEBUG_TAG, Float.toString(currentDirection));
                */
                buildingsBeingFaced.add(building);
                // Log.e(DEBUG_TAG, "Adding building to faced list " + buildings[i].getProvider());
            }
        }
        return getClosestBuilding(currentLocation, buildingsBeingFaced);
    }

    /**
     * This method will draw what building the user is looking at within 15 degrees
     * @param canvas - what surface to draw onto
     * @param displayDebugInfo - whether to display debug information
     */
    protected Location displayBuilding(Canvas canvas, boolean displayDebugInfo){
        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Easy margin alteration
        final int left_margin = canvas.getWidth()/4;
        final int text_size = canvas.getHeight()/43;

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(text_size);
        contentPaint.setColor(Color.WHITE);

        Location currentLocation = ARCamera.mCurrentLocation;
        if(currentLocation != null) {

            Location[] buildings = populateBuildings();

            if (displayDebugInfo) {
                canvas.drawText("Accuracy: " + currentLocation.getAccuracy() + " meters",
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 30,
                        contentPaint);

                canvas.drawText("Last Update @ " + mLastUpdateTime,
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 30 + text_size,
                        contentPaint);

                String currentTime = DateFormat.getTimeInstance().format(new Date());
                canvas.drawText("Current Time: " + currentTime,
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() / 30 + text_size * 2,
                        contentPaint);

                // Print all locations and distances
                for (int i = 0; i < buildings.length; i++) {
                    //canvas.drawText("Distance to " + buildings[i].getProvider(),
                    canvas.drawText("Distance to " + buildings[i].getProvider(),
                            canvas.getWidth() / left_margin,
                            canvas.getHeight() / 28 + text_size * (i + 3),
                            contentPaint);

                    // Change meters into km
                    Double distance = (double) (buildings[i].distanceTo(currentLocation) / 1000);
                    // Change km to mi
                    distance = distance * .621371;
                    // String dist = Double.toString(distance);
                    String dist = String.format("%4.3f", distance);
                    canvas.drawText(dist + " mi",
                            (canvas.getWidth() / 3) * 2,
                            canvas.getHeight() / 30 + text_size * (i + 3),
                            contentPaint);

                    // Bearing east of true magnetic north
                    canvas.drawText("Bearing toward " + buildings[i].getProvider() + ": " + (360 - Math.abs(currentLocation.bearingTo(buildings[i]))),
                            canvas.getWidth() / left_margin,
                            canvas.getHeight() / 2 + text_size * (i + 8),
                            contentPaint);

                    // Degree direction when device is not lying flat
                    canvas.drawText("Direction: " + Float.toString(SensorData.deviceZBearing),
                            canvas.getWidth() / 2,
                            canvas.getHeight() - text_size,
                            contentPaint);

                    String closestBuilding = getClosestBuilding();
                    // Debug Closest building
                    canvas.drawText("Closest building is: " + closestBuilding,
                            canvas.getWidth() / 2,
                            canvas.getHeight() / 30,
                            contentPaint);
                    // End Closest building debug
                }

                // Print GPS info
                canvas.drawText(currentLocation.getLatitude() + ", " + currentLocation.getLongitude(),
                        canvas.getWidth() / left_margin,
                        canvas.getHeight() - text_size,
                        contentPaint);
            }

            // This code runs whether displayDebugInfo is true or not
            // It is based on whether a location has been found
            Location facedBuilding = getListOfBuildingsDeviceIsFacingAndPrint(currentLocation, buildings, SensorData.deviceZBearing);

            // Reset text properties for main text
            contentPaint.setTextAlign(Paint.Align.CENTER);
            contentPaint.setTextSize(100);
            contentPaint.setColor(Color.WHITE);
            // Used Blue
            contentPaint.setARGB(255, 80, 188, 200);

            // Paint for boxes
            Paint paint = new Paint();
            // Specific blue specified as background
            // Used light blue
            paint.setARGB(255, 203 ,233, 237);

            // Gray blue: 176, 203, 207

            // Only run if facing a building
            if (!facedBuilding.getProvider().equals("NA")) {
                // Break into whitespace and print each on new line
                String[] splitText = facedBuilding.getProvider().split("\\s+");
                switch(splitText.length){
                    case 1:{
                        canvas.drawRect(80, 500, 640, 680, paint);
                        canvas.drawText(splitText[0],
                                canvas.getWidth() / 2,
                                (canvas.getHeight() / 10) * 5,
                                contentPaint);
                        break;
                    }

                    case 2:{
                        // Print a box around the text
                        canvas.drawRect(80, 500, 640, 775, paint);
                        canvas.drawText(splitText[0],
                                canvas.getWidth() / 2,
                                (canvas.getHeight() / 10) * 5,
                                contentPaint);
                        canvas.drawText(splitText[1],
                                canvas.getWidth() / 2,
                                (canvas.getHeight() / 10) * 6,
                                contentPaint);
                        break;
                    }
                    case 3:{
                        // Print a box around the text
                        canvas.drawRect(80, 450, 640, 900, paint);
                        canvas.drawText(splitText[0],
                                canvas.getWidth() / 2,
                                (canvas.getHeight() / 10) * 5,
                                contentPaint);
                        canvas.drawText(splitText[1],
                                canvas.getWidth() / 2,
                                (canvas.getHeight() / 10) * 6,
                                contentPaint);
                        canvas.drawText(splitText[2],
                                canvas.getWidth() / 2,
                                (canvas.getHeight() / 10) * 7,
                                contentPaint);
                        break;
                    }
                }

                // After printing, display a clickable button
                return facedBuilding;
            }
        }
        else{
            canvas.drawText("Current location has not yet been found",
                    canvas.getWidth() / left_margin,
                    canvas.getHeight() / 2 + text_size,
                    contentPaint);
            Log.v(DEBUG_TAG, "Current location has not yet been found");
        }
        return new Location("NA");
    }

    /**
     * This draw method is what draws data to the screen
     * @param canvas - what surface to draw onto
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Debug text
        //debugDraw(canvas);
        //debugBuildingDistance(canvas);

        // Pass a boolean as second argument depending on whether debug info should be seen or not
        final Location shownBuilding = displayBuilding(canvas, MainMenu.isDebugOn);

        // Design logic for showing text on screen at what building you are pointing at within some distance

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Set text properties
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(28);
        contentPaint.setColor(Color.WHITE);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("debug", "touched");
                Log.e("debug", shownBuilding.getProvider());
                if(!"NA".equals(shownBuilding.getProvider())) {
                    startInfoActivity(shownBuilding.getProvider());
                }
                return false;
            }
        });

        this.invalidate();
    }

    /**
     * Start Info Activity
     */
    public void startInfoActivity(String buildingName){
        Intent intent = new Intent(context, Information.class);
        intent.putExtra("selected", buildingName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }
}