package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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

        camera = (Button)findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ARCamera.class);
                startActivity(i);
            }
        });

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
}
/*
WebView myWebView = (WebView) findViewById(R.id.webview);
WebSettings webSettings = myWebView.getSettings();
webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://kucdinteractive.com/kuvt");*/
