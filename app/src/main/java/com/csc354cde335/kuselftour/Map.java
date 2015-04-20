package com.csc354cde335.kuselftour;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
    private LatLngBounds kubounds = new LatLngBounds(new LatLng(40.508967, -75.789671), new LatLng(40.517011, -75.780476));
    private Float MAX_ZOOM = 16f;
    private Float MIN_ZOOM = 18f;
    private Float CAMERA_BORDER = .0001f;
    public static Double longitude;
    public static Double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize two main buttons on the Map activity
        Button home = (Button)findViewById(R.id.home);
        Button camera = (Button)findViewById(R.id.camera);

        // Initilize the Building locations and the button listeners
        initializeBuildingLocations();
        initializeButtonListeners(camera, home);

        // Initialize map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        // Location of the center of the north side of Kutztown University's north campus
        LatLng kutztown = new LatLng(40.513266, -75.785965);

        // Place the overlay image onto the map
        final GroundOverlayOptions kuMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.northcampus))
                .positionFromBounds(kubounds);
        map.addGroundOverlay(kuMap);

        // Allow the user to find their location
        map.setMyLocationEnabled(true);

        // Put the gogole maps camera over the center of north campus and set its zoom level to 17
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kutztown, 17));

        // Handle when the map is clicked
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Initialize what activity the app should go to in the case that a buliding is clicked
                Intent i = new Intent(getApplicationContext(), Information.class);

                // Test whether a building was clicked
                // If a building was clicked send the building name to the new activity and start the activity
                String selected = testBuildingClicked(latLng.latitude, latLng.longitude);
                if (!selected.equals("")) {
                    i.putExtra("selected", selected);
                    startActivity(i);
                }
            }
        });

        // Handle when the camera moves
        // If the user trys to zoom in or out too far it will put it back tot he closest allowable bounds
        // If the user trys to move the camera too far away from campus it will put it back the the
        //    closest allowable longitude/latitude that are within the set bounds
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                boolean zoomChange = false;
                boolean locChange = false;

                // Test if the zoom is ok
                Float zoom = cameraPosition.zoom;
                if (cameraPosition.zoom > MIN_ZOOM) {
                    zoom = MIN_ZOOM;
                    zoomChange = true;
                } else if (cameraPosition.zoom < MAX_ZOOM) {
                    zoom = MAX_ZOOM;
                    zoomChange = true;
                }
                // End zoom test

                double lon = cameraPosition.target.longitude;
                double lat = cameraPosition.target.latitude;

                // Test if the location is okay
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
                // End location test
            }
        });
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    };
    private void initializeBuildingLocations() {
        // Initilize the bounds of each building to be used to see if the user clicked any buildings
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
        // If the home building is clicked go to the Main Menu activity
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(i);
            }
        });

        // If the camera button is clicked go to the ARCamera activity
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ARCamera.class);
                startActivity(i);
            }
        });
    }

    public void openMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void openAR(View view) {
        Intent intent = new Intent(this, ARCamera.class);
        startActivity(intent);
    }

    // Test whether the user clicked a building or not
    private String testBuildingClicked(Double latitude, Double longitude) {
        String selected = "";
        if (latitude > af.get("sw").get(0) && latitude < af.get("ne").get(0)) {
            if (longitude > af.get("sw").get(1) && longitude < af.get("ne").get(1)) {
                selected = KUSelfTourConstants.AF;
            }
        }
        if (latitude > beekey.get("sw").get(0) && latitude < beekey.get("ne").get(0)) {
            if (longitude > beekey.get("sw").get(1) && longitude < beekey.get("ne").get(1)) {
                selected = KUSelfTourConstants.BEEKEY;
            }
        }
        if (latitude > boehm.get("sw").get(0) && latitude < boehm.get("ne").get(0)) {
            if (longitude > boehm.get("sw").get(1) && longitude < boehm.get("ne").get(1)) {
                selected = KUSelfTourConstants.BOEHM;
            }
        }
        if (latitude > defran.get("sw").get(0) && latitude < defran.get("ne").get(0)) {
            if (longitude > defran.get("sw").get(1) && longitude < defran.get("ne").get(1)) {
                selected = KUSelfTourConstants.DEFRANCESCO;
            }
        }
        if (latitude > gradcenter.get("sw").get(0) && latitude < gradcenter.get("ne").get(0)) {
            if (longitude > gradcenter.get("sw").get(1) && longitude < gradcenter.get("ne").get(1)) {
                selected = KUSelfTourConstants.GRAD_CENTER;
            }
        }
        if (latitude > grim.get("sw").get(0) && latitude < grim.get("ne").get(0)) {
            if (longitude > grim.get("sw").get(1) && longitude < grim.get("ne").get(1)) {
                selected = KUSelfTourConstants.GRIM;
            }
        }
        if (latitude > lytle.get("sw").get(0) && latitude < lytle.get("ne").get(0)) {
            if (longitude > lytle.get("sw").get(1) && longitude < lytle.get("ne").get(1)) {
                selected = KUSelfTourConstants.LYTLE;
            }
        }
        if (latitude > rickenbach.get("sw").get(0) && latitude < rickenbach.get("ne").get(0)) {
            if (longitude > rickenbach.get("sw").get(1) && longitude < rickenbach.get("ne").get(1)) {
                selected = KUSelfTourConstants.RICKENBACH;
            }
        }
        if (latitude > rohrbach.get("sw").get(0) && latitude < rohrbach.get("ne").get(0)) {
            if (longitude > rohrbach.get("sw").get(1) && longitude < rohrbach.get("ne").get(1)) {
                selected = KUSelfTourConstants.ROHRBACH;
            }
        }
        if (latitude > schaeffer.get("sw").get(0) && latitude < schaeffer.get("ne").get(0)) {
            if (longitude > schaeffer.get("sw").get(1) && longitude < schaeffer.get("ne").get(1)) {
                selected = KUSelfTourConstants.SCHAEFFER_AUDITORIUM;
            }
        }
        if (latitude > sheridan.get("sw").get(0) && latitude < sheridan.get("ne").get(0)) {
            if (longitude > sheridan.get("sw").get(1) && longitude < sheridan.get("ne").get(1)) {
                selected = KUSelfTourConstants.SHERIDAN;
            }
        }
        if (latitude > oldmain.get("sw").get(0) && latitude < oldmain.get("ne").get(0)) {
            if (longitude > oldmain.get("sw").get(1) && longitude < oldmain.get("ne").get(1)) {
                selected =  KUSelfTourConstants.OLD_MAIN;
            }
        }
        if (latitude > msu.get("sw").get(0) && latitude < msu.get("ne").get(0)) {
            if (longitude > msu.get("sw").get(1) && longitude < msu.get("ne").get(1)) {
                selected =  KUSelfTourConstants.STUDENT_UNION_BUILDING;
            }
        }
        return selected;
    }
}
