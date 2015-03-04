package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Steven on 3/4/2015.
 * This class serves the purpose of acting
 * as a pseudo loading screen
 * Error: Seems only to load once...
 */
public class Splash extends Activity {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 2 seconds
                } catch (Exception e) {

                } finally {
                    context = getApplicationContext();
                    Intent i = new Intent(context, MainMenu.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
