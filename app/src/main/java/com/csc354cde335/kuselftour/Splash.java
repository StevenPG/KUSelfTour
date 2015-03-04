package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Steven on 3/4/2015.
 * This class serves the purpose of acting
 * as a pseudo loading screen
 * Error: Seems only to load once...
 */
public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("test", "Resuming the loading screen");
        try {
            Thread.sleep(2000);
            Log.v("test", "Waited 2 seconds to start main menu");
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
