package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by Justin on 3/25/2015.
 */
public class Map extends Activity implements OnMapReadyCallback {

    private HashMap<String, ArrayList<Double>> af = new HashMap();
    private HashMap<String, ArrayList<Double>> beekey = new HashMap();
    private HashMap<String, ArrayList<Double>> boehm = new HashMap();
    private HashMap<String, ArrayList<Double>> defran = new HashMap();
    private HashMap<String, ArrayList<Double>> gradcenter = new HashMap();
    private HashMap<String, ArrayList<Double>> grim = new HashMap();
    private HashMap<String, ArrayList<Double>> lytle = new HashMap();
    private HashMap<String, ArrayList<Double>> rickenbach = new HashMap();
    private HashMap<String, ArrayList<Double>> rhorbach = new HashMap();
    private HashMap<String, ArrayList<Double>> schaeffer = new HashMap();
    private HashMap<String, ArrayList<Double>> sheridan = new HashMap();
    private HashMap<String, ArrayList<Double>> oldmain = new HashMap();
    private HashMap<String, ArrayList<Double>> msu = new HashMap();

=======
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Map extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    protected static final String DEBUG = "Debug";
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Boolean mRequestingLocationUpdates;

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public static final ArrayList<Double> topLeft = new ArrayList<Double>(Arrays.asList(40.516956,-75.790327));
    public static final ArrayList<Double> topRight = new ArrayList<Double>(Arrays.asList(40.516956,-75.781648));
    public static final ArrayList<Double> bottomLeft = new ArrayList<Double>(Arrays.asList(40.509559,-75.790327));
    public static final ArrayList<Double> bottomRight = new ArrayList<Double>(Arrays.asList(40.509559,-75.781648));
    public static Location mCurrentLocation;
    public static String mLastUpdateTime;

    private boolean onCampus = false;
    private Display display;
    private Double longitude;
    private Double latitude;
    private Double xDifference;
    private Double yDifference;
    private float scale = 1f;
    private ImageView user;
    private ImageView map;
    private int screenHeight;
    private int screenWidth;
    private int userHeight;
    private int userWidth;
    //private Matrix matrix = new Matrix();
    private Point size;
    //private ScaleGestureDetector SGD;
    private TextView longLat;

    /**
     * Runs when the activity is first instantiated
     * @param savedInstanceState - saved instance
     */
>>>>>>> master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

<<<<<<< HEAD
        Button home;
        Button camera;

        home = (Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(i);
            }
        });
=======
        xDifference = topRight.get(1) - topLeft.get(1);
        yDifference=  topRight.get(0) - bottomRight.get(0);

        // Location Updates
        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
        buildGoogleApiClient();

        longLat = (TextView)findViewById(R.id.myLocation);
       // user = (ImageView)findViewById(R.id.user);
        //map = (ImageView)findViewById(R.id.northcampus);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();

        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;
        // SGD = new ScaleGestureDetector(this, new ScaleListener());
        userHeight = user.getHeight();
        userWidth = user.getWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //SGD.onTouchEvent(ev);
        return true;
    }

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(DEBUG, "Updating values from bundle");
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(DEBUG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.v(DEBUG, "Starting location updates");
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.v(DEBUG, "Stopping location updates");
    }

    /**
     * When the activity begins, connect with the GoogleApiClient
     */
    @Override
    protected void onStart() {
       super.onStart();
       mGoogleApiClient.connect();
    }

    /**
     * Runs when the activity resumes
     */
    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        Log.v(DEBUG, "Resume: Sensor listeners registered");

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    /**
     * Runs when the activity is temporarily suspended
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    /**
     * Runs when the activity is closed
     */
    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(DEBUG, "Connected to GoogleApiClient");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            Log.v(DEBUG, "Retrieving updated location at  " + mLastUpdateTime);
        }

        // Make sure we are connected, then set to true
        mRequestingLocationUpdates = true;

        // If the user presses the Start Updates button before GoogleApiClient connects, we set
        // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
        // the value of mRequestingLocationUpdates and if it is true, we start location updates.
        if (mRequestingLocationUpdates){
            startLocationUpdates();
        }
    }
>>>>>>> master

        camera = (Button)findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ARCamera.class);
                startActivity(i);
            }
        });

<<<<<<< HEAD
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        af.put("ne", new ArrayList<Double>(Arrays.asList(40.512530, -75.785973)));
        af.put("sw", new ArrayList<Double>(Arrays.asList(40.511943, -75.786777)));
        beekey.put("ne", new ArrayList<Double>(Arrays.asList(40.515348, -75.785399)));
        beekey.put("sw", new ArrayList<Double>(Arrays.asList(40.514720, -75.786289)));
        boehm.put("ne", new ArrayList<Double>(Arrays.asList(40.512412, -75.784261)));
        boehm.put("sw", new ArrayList<Double>(Arrays.asList(40.511482, -75.785270)));
        defran.put("ne", new ArrayList<Double>(Arrays.asList(40.514418, -75.785495)));
        defran.put("sw", new ArrayList<Double>(Arrays.asList(40.513945, -75.786268)));
        gradcenter.put("ne", new ArrayList<Double>(Arrays.asList(40.511947, -75.782631)));
        gradcenter.put("sw", new ArrayList<Double>(Arrays.asList(40.511702, -75.782942)));
        grim.put("ne", new ArrayList<Double>(Arrays.asList(40.511433, -75.785635)));
        grim.put("sw", new ArrayList<Double>(Arrays.asList(40.511172, -75.786000)));
        lytle.put("ne", new ArrayList<Double>(Arrays.asList(40.513456, -75.787223)));
        lytle.put("sw", new ArrayList<Double>(Arrays.asList(40.512926, -75.787974)));
        rhorbach.put("ne", new ArrayList<Double>(Arrays.asList(40.513688, -75.784878)));
        rhorbach.put("sw", new ArrayList<Double>(Arrays.asList(40.512685, -75.786016)));
        rickenbach.put("ne", new ArrayList<Double>(Arrays.asList(40.514647, -75.784208)));
        rickenbach.put("sw", new ArrayList<Double>(Arrays.asList(40.514149, -75.785163)));
        schaeffer.put("ne", new ArrayList<Double>(Arrays.asList(40.512118, -75.783285)));
        schaeffer.put("sw", new ArrayList<Double>(Arrays.asList(40.511596, -75.783854)));
        sheridan.put("ne", new ArrayList<Double>(Arrays.asList(40.512836, -75.782577)));
        sheridan.put("sw", new ArrayList<Double>(Arrays.asList(40.512257, -75.783897)));
        oldmain.put("ne", new ArrayList<Double>(Arrays.asList(40.511005, -75.782110)));
        oldmain.put("sw", new ArrayList<Double>(Arrays.asList(40.509520, -75.783655)));
        msu.put("ne", new ArrayList<Double>(Arrays.asList(40.514063, -75.783612)));
        msu.put("sw", new ArrayList<Double>(Arrays.asList(40.513093, -75.784535)));
=======
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arcamera, menu);
        return true;
>>>>>>> master
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng kutztown = new LatLng(40.513207, -75.785383);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kutztown, 17));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(getApplicationContext(), MainMenu.class);

                Log.d("TEST", "click: " + latLng.latitude + ", " + latLng.longitude);

                if (latLng.latitude > af.get("sw").get(0) && latLng.latitude < af.get("ne").get(0)) {
                    if (latLng.longitude > af.get("sw").get(1) &&  latLng.longitude < af.get("ne").get(1)) {
                        Log.d("TEST", "click af");
                        i.putExtra("selected", "AF");
                    }
                }
                if (latLng.latitude > beekey.get("sw").get(0) && latLng.latitude < beekey.get("ne").get(0)) {
                    if (latLng.longitude > beekey.get("sw").get(1) &&  latLng.longitude < beekey.get("ne").get(1)) {
                        Log.d("TEST", "click beekey");
                        i.putExtra("selected", "Beekey");
                    }
                }
                if (latLng.latitude > boehm.get("sw").get(0) && latLng.latitude < boehm.get("ne").get(0)) {
                    if (latLng.longitude > boehm.get("sw").get(1) &&  latLng.longitude < boehm.get("ne").get(1)) {
                        Log.d("TEST", "click boehm");
                        i.putExtra("selected", "Boehm");
                    }
                }
                if (latLng.latitude > defran.get("sw").get(0) && latLng.latitude < defran.get("ne").get(0)) {
                    if (latLng.longitude > defran.get("sw").get(1) &&  latLng.longitude < defran.get("ne").get(1)) {
                        Log.d("TEST", "click defran");
                        i.putExtra("selected", "deFrancesco");
                    }
                }
                if (latLng.latitude > gradcenter.get("sw").get(0) && latLng.latitude < gradcenter.get("ne").get(0)) {
                    if (latLng.longitude > gradcenter.get("sw").get(1) &&  latLng.longitude < gradcenter.get("ne").get(1)) {
                        Log.d("TEST", "click gradcenter");
                        i.putExtra("selected", "Grad Center");
                    }
                }
                if (latLng.latitude > grim.get("sw").get(0) && latLng.latitude < grim.get("ne").get(0)) {
                    if (latLng.longitude > grim.get("sw").get(1) &&  latLng.longitude < grim.get("ne").get(1)) {
                        Log.d("TEST", "click grim");
                        i.putExtra("selected", "Grim");
                    }
                }
                if (latLng.latitude > lytle.get("sw").get(0) && latLng.latitude < lytle.get("ne").get(0)) {
                    if (latLng.longitude > lytle.get("sw").get(1) &&  latLng.longitude < lytle.get("ne").get(1)) {
                        Log.d("TEST", "click lytle");
                        i.putExtra("selected", "Lytle");
                    }
                }
                if (latLng.latitude > rickenbach.get("sw").get(0) && latLng.latitude < rickenbach.get("ne").get(0)) {
                    if (latLng.longitude > rickenbach.get("sw").get(1) &&  latLng.longitude < rickenbach.get("ne").get(1)) {
                        Log.d("TEST", "click rickenbach");
                        i.putExtra("selected", "Rickenbach");
                    }
                }
                if (latLng.latitude > rhorbach.get("sw").get(0) && latLng.latitude < rhorbach.get("ne").get(0)) {
                    if (latLng.longitude > rhorbach.get("sw").get(1) &&  latLng.longitude < rhorbach.get("ne").get(1)) {
                        Log.d("TEST", "click rhorbach");
                        i.putExtra("selected", "Rhorbach");
                    }
                }
                if (latLng.latitude > schaeffer.get("sw").get(0) && latLng.latitude < schaeffer.get("ne").get(0)) {
                    if (latLng.longitude > schaeffer.get("sw").get(1) &&  latLng.longitude < schaeffer.get("ne").get(1)) {
                        Log.d("TEST", "click schaeffer");
                        i.putExtra("selected", "Schaeffer Auditorium");
                    }
                }
                if (latLng.latitude > sheridan.get("sw").get(0) && latLng.latitude < sheridan.get("ne").get(0)) {
                    if (latLng.longitude > sheridan.get("sw").get(1) &&  latLng.longitude < sheridan.get("ne").get(1)) {
                        Log.d("TEST", "click sheridan");
                        i.putExtra("selected", "Sharadan Art Studio");
                    }
                }
                if (latLng.latitude > oldmain.get("sw").get(0) && latLng.latitude < oldmain.get("ne").get(0)) {
                    if (latLng.longitude > oldmain.get("sw").get(1) &&  latLng.longitude < oldmain.get("ne").get(1)) {
                        Log.d("TEST", "click oldmain");
                        i.putExtra("selected", "Old Main");
                    }
                }
                if (latLng.latitude > msu.get("sw").get(0) && latLng.latitude < msu.get("ne").get(0)) {
                    if (latLng.longitude > msu.get("sw").get(1) &&  latLng.longitude < msu.get("ne").get(1)) {
                        Log.d("TEST", "click msu");
                        i.putExtra("selected", "SUB");
                    }
                }
                startActivity(i);
            }
        });
    }


    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        Log.v(DEBUG, "Printing Location at" + mLastUpdateTime);

        latitude= mCurrentLocation.getLatitude();
        longitude = mCurrentLocation.getLongitude();
        Log.v(DEBUG, latitude + ", " + longitude);

        longLat.setText(latitude + " & " + longitude);
        if (isOnCampus() == true) {
            onCampus = true;
        } else {
            onCampus = false;
        }

        if (onCampus == false) {
            longLat.setText("You are currently not on campus.");
        }

        user.setTranslationX((float)calcXLoc());
        user.setTranslationY((float)calcYLoc());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(DEBUG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(DEBUG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    /**
     * Stores activity data in the Bundle.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    public boolean isOnCampus() {
        if (longitude > topRight.get(0) || longitude < bottomRight.get(0) || latitude < topLeft.get(1) || latitude > topRight.get(1)) {
            return false;
        }
        return true;
    }

    public double calcXLoc(){
       double userXDifference = longitude - topLeft.get(1);
       double offset = userXDifference / xDifference;
       return (screenWidth * offset) - userWidth;
    }

    public double calcYLoc() {
        double userYDifference = latitude - bottomLeft.get(0);
        double offset = userYDifference / yDifference;
        return (screenHeight * (.95 - offset)) + userHeight + 5;
    }
   /* private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);
            map.setImageMatrix(matrix);
            return true;
        }
    }*/
}
/*
WebView myWebView = (WebView) findViewById(R.id.webview);
WebSettings webSettings = myWebView.getSettings();
webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://kucdinteractive.com/kuvt");*/



