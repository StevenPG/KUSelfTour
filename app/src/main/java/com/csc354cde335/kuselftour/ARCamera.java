package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.io.IOException;

/**
 * The ARCamera uses location based augmented reality to showcase information
 * and media in real time using hardware sensors and internet features of a device.
 */
public class ARCamera extends Activity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcamera);

       // Add custom views to the FrameLayout control
       FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

       ArDisplayView arDisplay = new ArDisplayView(this,this);
       arViewPane.addView(arDisplay);

       // Add the custom overlay to the ARCamera activity and view pane
       OverlayView arContent = new OverlayView(getApplicationContext());
       arViewPane.addView(arContent);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arcamera, menu);
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
