package com.csc354cde335.kuselftour;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Steven Gantz
 * Main menu class, handles activity transfer to one of three main activities.
 * Camera based AR, 2D top down map view, and a help screen.
 */

public class MainMenu extends ActionBarActivity {

    /**
     * Override oncreate of activity
     * (Runs when the MainMenu class is initially created)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Runs when the options menu is created
     * (Top right corner)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    // Completely developer created methods below.
    // Automated methods created above

    public void openHelp(View view) {
        Intent intent = new Intent(this, HelpPage.class);
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
}
