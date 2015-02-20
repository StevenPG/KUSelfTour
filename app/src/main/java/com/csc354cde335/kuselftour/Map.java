package com.csc354cde335.kuselftour;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This class is the base point for the two-dimensional
 * map using location services defined in the android manifest.
 * Map shows a top-down view of the Kutztown campus and allows
 * the user to easily find their location and select more
 * information and/or a destination.
 */
public class Map extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Create a Locator and find location via network
        Locator loc = new Locator();
        loc.createFields();
        // End non-IDE code
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // IDE Generated code above (some edits in onCreate() ), Developer code below

    /**
     * This class is the bare minimum necessary to gain the latitude/longitude
     * coordinates of the app user.
     */
    public class Locator implements LocationListener {

        // Location fields
        protected LocationManager locationManager;
        protected LocationListener locationListener;
        protected Context context;
        TextView textLatitude;
        String lat;
        String provider;
        protected String latitude, longitude;
        protected boolean gps_enabled, network_enabled;

        // Location update constants
        private int PROXIMITY_UPDATE_DISTANCE = 20; //(meters)
        private int UPDATE_EVERY_X_TIME = 100; // (milliseconds)

        // Location Methods

        /**
         * Create fields and begin requesting location updates based on Network Provider
         * Other Provider: GPS_PROVIDER
         */
        public void createFields() {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    UPDATE_EVERY_X_TIME, PROXIMITY_UPDATE_DISTANCE, this);
            Log.e("TEST", "Creating fields and requesting location");
            textLatitude = (TextView) findViewById(R.id.Location);
        }

        /**
         * Update location when called
         * @param location
         */
        @Override
        public void onLocationChanged(Location location) {
            textLatitude = (TextView) findViewById(R.id.Location);
            textLatitude.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            Log.e("TEST", "Updating Location");
        }

        /**
         * Run when the provider becomes disabled
         * @param provider
         */
        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Latitude", "disable");
        }

        /**
         * Runs when the Provider is reenabled
         * @param provider
         */
        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Latitude", "enable");
        }

        /**
         * Runs on each status change
         * @param provider
         * @param status
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude", "status");
        }

}

}
