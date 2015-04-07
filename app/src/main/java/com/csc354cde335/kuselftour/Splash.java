package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * Created by Steven on 3/4/2015.
 * This class serves the purpose of acting
 * as a pseudo loading screen
 * Error: Seems only to load once...
 */
public class Splash extends Activity {

    /**
     * Static this for waiting
     */
    Object splash = this;

    private int waitTime;

    /**
     * Progress Dialog
     * @param savedInstanceState
     */
    ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        waitTime = 4000;
        // Debug time below
        //waitTime = 100;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("test", "Resuming the loading screen");

        progress = progress.show(this, "Starting Components...",
                "Loading...", true);

        // Wait 4 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Intent intent = new Intent((Activity)splash, MainMenu.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }, waitTime);
    }
}
