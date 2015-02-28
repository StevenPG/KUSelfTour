package com.csc354cde335.kuselftour;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is the base point for the two-dimensional
 * map using location services defined in the android manifest.
 * Map shows a top-down view of the Kutztown campus and allows
 * the user to easily find their location and select more
 * information and/or a destination.
 */
public class Map extends ActionBarActivity {

    /**
     * For tests
     */
    GpsTracker gps;
    String lat;
    String mylong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gps = new GpsTracker(Map.this);
                                lat = Double.toString(gps.getLatitude());
                                mylong = Double.toString(gps.getLongitude());
                                Log.e("Lat",lat);
                                Log.e("Long",mylong);
                                TextView Loc = (TextView)findViewById(R.id.myLocation);
                                Loc.setText("Lat:" + lat + " " + "Long:" + mylong);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
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
}
