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
    private HashMap<String, ArrayList<Double>> rohrbach = new HashMap();
    private HashMap<String, ArrayList<Double>> schaeffer = new HashMap();
    private HashMap<String, ArrayList<Double>> sheridan = new HashMap();
    private HashMap<String, ArrayList<Double>> oldmain = new HashMap();
    private HashMap<String, ArrayList<Double>> msu = new HashMap();
    private LatLngBounds kubounds = new LatLngBounds(new LatLng(40.509491, -75.790372), new LatLng(40.516961, -75.781558));
    private Float MAX_ZOOM = 16f;
    private Float MIN_ZOOM = 18f;
    private Float CAMERA_BORDER = .0001f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button home = (Button)findViewById(R.id.home);
        Button camera = (Button)findViewById(R.id.camera);

        initializeBuildingLocations();
        initializeButtonListeners(camera, home);

        //Initialize map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng kutztown = new LatLng(40.513266, -75.785965);

        final GroundOverlayOptions kuMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.northcampus))
                .positionFromBounds(kubounds);
        map.addGroundOverlay(kuMap);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kutztown, 17));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(getApplicationContext(), Information.class);
                Log.d("TEST", "click: " + latLng.latitude + ", " + latLng.longitude);

                String selected = testBuildingClicked(latLng.latitude, latLng.longitude);
                if (!selected.equals("")) {
                    i.putExtra("selected", selected);
                    startActivity(i);
                }
            }
        });

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("TEST", "CHANGE: " + cameraPosition.zoom);
                boolean zoomChange = false;
                boolean locChange = false;
                Float zoom = cameraPosition.zoom;
                if (cameraPosition.zoom > MIN_ZOOM) {
                    zoom = MIN_ZOOM;
                    zoomChange = true;
                } else if (cameraPosition.zoom < MAX_ZOOM) {
                    zoom = MAX_ZOOM;
                    zoomChange = true;
                }

                double lon = cameraPosition.target.longitude;
                double lat = cameraPosition.target.latitude;

                if (cameraPosition.target.longitude > kubounds.northeast.longitude) {
                    lon = kubounds.northeast.longitude - CAMERA_BORDER;
                    locChange = true;
                } else if (cameraPosition.target.longitude < kubounds.southwest.longitude) {
                    lon = kubounds.southwest.longitude + CAMERA_BORDER;
                    locChange = true;
                }
                if (cameraPosition.target.latitude > kubounds.northeast.latitude) {
                    lat = kubounds.northeast.latitude - CAMERA_BORDER;
                    locChange = true;
                } else if (cameraPosition.target.latitude < kubounds.southwest.latitude) {
                    lat = kubounds.southwest.latitude + CAMERA_BORDER;
                    locChange = true;
                }
                LatLng camLoc = new LatLng(lat, lon);
                if (locChange) {
                    map.animateCamera(CameraUpdateFactory.newLatLng(camLoc));
                }
                if (zoomChange) {
                    map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                }
            }
        });
    }

    private void initializeBuildingLocations() {
        af.put("ne", new ArrayList<>(Arrays.asList(40.512530, -75.785973)));
        af.put("sw", new ArrayList<>(Arrays.asList(40.511943, -75.786777)));
        beekey.put("ne", new ArrayList<>(Arrays.asList(40.515348, -75.785399)));
        beekey.put("sw", new ArrayList<>(Arrays.asList(40.514720, -75.786289)));
        boehm.put("ne", new ArrayList<>(Arrays.asList(40.512412, -75.784261)));
        boehm.put("sw", new ArrayList<>(Arrays.asList(40.511482, -75.785270)));
        defran.put("ne", new ArrayList<>(Arrays.asList(40.514418, -75.785495)));
        defran.put("sw", new ArrayList<>(Arrays.asList(40.513945, -75.786268)));
        gradcenter.put("ne", new ArrayList<>(Arrays.asList(40.511947, -75.782631)));
        gradcenter.put("sw", new ArrayList<>(Arrays.asList(40.511702, -75.782942)));
        grim.put("ne", new ArrayList<>(Arrays.asList(40.511433, -75.785635)));
        grim.put("sw", new ArrayList<>(Arrays.asList(40.511172, -75.786000)));
        lytle.put("ne", new ArrayList<>(Arrays.asList(40.513456, -75.787223)));
        lytle.put("sw", new ArrayList<>(Arrays.asList(40.512926, -75.787974)));
        rohrbach.put("ne", new ArrayList<>(Arrays.asList(40.513688, -75.784878)));
        rohrbach.put("sw", new ArrayList<>(Arrays.asList(40.512685, -75.786016)));
        rickenbach.put("ne", new ArrayList<>(Arrays.asList(40.514647, -75.784208)));
        rickenbach.put("sw", new ArrayList<>(Arrays.asList(40.514149, -75.785163)));
        schaeffer.put("ne", new ArrayList<>(Arrays.asList(40.512118, -75.783285)));
        schaeffer.put("sw", new ArrayList<>(Arrays.asList(40.511596, -75.783854)));
        sheridan.put("ne", new ArrayList<>(Arrays.asList(40.512836, -75.782577)));
        sheridan.put("sw", new ArrayList<>(Arrays.asList(40.512257, -75.783897)));
        oldmain.put("ne", new ArrayList<>(Arrays.asList(40.511005, -75.782110)));
        oldmain.put("sw", new ArrayList<>(Arrays.asList(40.509520, -75.783655)));
        msu.put("ne", new ArrayList<>(Arrays.asList(40.514063, -75.783612)));
        msu.put("sw", new ArrayList<>(Arrays.asList(40.513093, -75.784535)));
    }

    private void initializeButtonListeners(Button camera, Button home) {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(i);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ARCamera.class);
                startActivity(i);
            }
        });
    }

    private String testBuildingClicked(Double latitude, Double longitude) {
        String selected = "";
        if (latitude > af.get("sw").get(0) && latitude < af.get("ne").get(0)) {
            if (longitude > af.get("sw").get(1) && longitude < af.get("ne").get(1)) {
                Log.d("TEST", "click af");
                selected = "AF";
            }
        }
        if (latitude > beekey.get("sw").get(0) && latitude < beekey.get("ne").get(0)) {
            if (longitude > beekey.get("sw").get(1) && longitude < beekey.get("ne").get(1)) {
                Log.d("TEST", "click beekey");
                selected = "Beekey";
            }
        }
        if (latitude > boehm.get("sw").get(0) && latitude < boehm.get("ne").get(0)) {
            if (longitude > boehm.get("sw").get(1) && longitude < boehm.get("ne").get(1)) {
                Log.d("TEST", "click boehm");
                selected = "Boehm";
            }
        }
        if (latitude > defran.get("sw").get(0) && latitude < defran.get("ne").get(0)) {
            if (longitude > defran.get("sw").get(1) && longitude < defran.get("ne").get(1)) {
                Log.d("TEST", "click defran");
                selected = "deFrancesco";
            }
        }
        if (latitude > gradcenter.get("sw").get(0) && latitude < gradcenter.get("ne").get(0)) {
            if (longitude > gradcenter.get("sw").get(1) && longitude < gradcenter.get("ne").get(1)) {
                Log.d("TEST", "click gradcenter");
                selected = "Grad Center";
            }
        }
        if (latitude > grim.get("sw").get(0) && latitude < grim.get("ne").get(0)) {
            if (longitude > grim.get("sw").get(1) && longitude < grim.get("ne").get(1)) {
                Log.d("TEST", "click grim");
                selected = "Grim";
            }
        }
        if (latitude > lytle.get("sw").get(0) && latitude < lytle.get("ne").get(0)) {
            if (longitude > lytle.get("sw").get(1) && longitude < lytle.get("ne").get(1)) {
                Log.d("TEST", "click lytle");
                selected = "Lytle";
            }
        }
        if (latitude > rickenbach.get("sw").get(0) && latitude < rickenbach.get("ne").get(0)) {
            if (longitude > rickenbach.get("sw").get(1) && longitude < rickenbach.get("ne").get(1)) {
                Log.d("TEST", "click rickenbach");
                selected = "Rickenbach";
            }
        }
        if (latitude > rohrbach.get("sw").get(0) && latitude < rohrbach.get("ne").get(0)) {
            if (longitude > rohrbach.get("sw").get(1) && longitude < rohrbach.get("ne").get(1)) {
                Log.d("TEST", "click rhorbach");
                selected = "Rohrbach";
            }
        }
        if (latitude > schaeffer.get("sw").get(0) && latitude < schaeffer.get("ne").get(0)) {
            if (longitude > schaeffer.get("sw").get(1) && longitude < schaeffer.get("ne").get(1)) {
                Log.d("TEST", "click schaeffer");
                selected = "Schaeffer Auditorium";
            }
        }
        if (latitude > sheridan.get("sw").get(0) && latitude < sheridan.get("ne").get(0)) {
            if (longitude > sheridan.get("sw").get(1) && longitude < sheridan.get("ne").get(1)) {
                Log.d("TEST", "click sheridan");
                selected = "Sharadan Art Studio";
            }
        }
        if (latitude > oldmain.get("sw").get(0) && latitude < oldmain.get("ne").get(0)) {
            if (longitude > oldmain.get("sw").get(1) && longitude < oldmain.get("ne").get(1)) {
                Log.d("TEST", "click oldmain");
                selected = "Old Main";
            }
        }
        if (latitude > msu.get("sw").get(0) && latitude < msu.get("ne").get(0)) {
            if (longitude > msu.get("sw").get(1) && longitude < msu.get("ne").get(1)) {
                Log.d("TEST", "click msu");
                selected = "SUB";
            }
        }
        return selected;
    }
}
