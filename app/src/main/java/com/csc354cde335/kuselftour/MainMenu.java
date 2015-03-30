package com.csc354cde335.kuselftour;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

/**
 * Steven Gantz
 * Main menu class, handles activity transfer to one of three main activities.
 * Camera based AR, 2D top down map view, and a help screen.
 */

public class MainMenu extends ActionBarActivity {

    public static boolean isDebugOn;
    public boolean allowDebug;

    public static int FieldOfView;

    /**
     * Override oncreate of activity
     * (Runs when the MainMenu class is initially created)
     * @param savedInstanceState - last instance of this class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // CHANGE THIS TO ALLOW FOR DEBUGGING OR NOT
        allowDebug = true;

        isDebugOn = false;
        FieldOfView = 15;
        // If debug is on
        Button infoButton = (Button) findViewById(R.id.Information);
        if(!isDebugOn){
            infoButton.setVisibility(View.GONE);
        }
        else{
            infoButton.setVisibility(View.VISIBLE);
        }
        // Deny or allow debugging
        if(!allowDebug){
            Switch debugSwitch = (Switch) findViewById(R.id.DebugSwitch);
            debugSwitch.setVisibility(View.GONE);
        }
    }

    /**
     * onStart runs when the activity begins
     */
    @Override
    protected void onStart(){
        super.onStart();
    }

    /**
     * OnResume runs each time this activity is brought back (also runs on first startup)
     */
    @Override
    protected void onResume(){
        super.onResume();
        // If debug is on
        Button infoButton = (Button) findViewById(R.id.Information);
        if(!isDebugOn){
            infoButton.setVisibility(View.GONE);
        }
        else{
            infoButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Runs when the options menu is created
     * (Top right corner)
     * @param menu - pass the menu
     * @return - returns options successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return false;
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

    // Completely developer created methods below.
    // Automated methods created above

    public void openInformation(View view) {
        Intent intent = new Intent(this, Information.class);
        intent.putExtra("selected", "Sharadin");
        startActivity(intent);
    }

    public void openAR(View view) {
        Intent intent = new Intent(this, ARCamera.class);
        startActivity(intent);
    }

    public void openMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void onDebugClick(View view){
        Switch debugSwitch = (Switch) findViewById(R.id.DebugSwitch);
        if(debugSwitch.isChecked()){
            Log.e("DEBUG", "debug is true");
            isDebugOn = true;
        }
        else{
            Log.e("DEBUG", "debug is false");
            isDebugOn = false;
        }
        // If debug is on, show the button, else hide the button
        Button infoButton = (Button) findViewById(R.id.Information);
        if(!isDebugOn){
            infoButton.setVisibility(View.GONE);
        }
        else{
            infoButton.setVisibility(View.VISIBLE);
        }
    }
}
