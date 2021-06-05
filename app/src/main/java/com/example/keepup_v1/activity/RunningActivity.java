package com.example.keepup_v1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.keepup_v1.R;
import com.example.keepup_v1.DirectionUtil.FetchURL;
import com.example.keepup_v1.DirectionUtil.TaskLoadedCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class RunningActivity extends AppCompatActivity implements
        OnMapReadyCallback, TaskLoadedCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Polyline polyline;
    private Button gmap;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition cameraPosition;
    private PlacesClient placesClient;
    private MarkerOptions mylocationMaker;
    private MarkerOptions locationMaker;
    private MarkerOptions mylocationMaker1;
    private MarkerOptions mylocationMaker2;
    private LatLng resultat;
    private Geocoder geocoder;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;

    private RadioGroup rg_running;
    private RadioButton rb_home;
    private RadioButton rb_running;
    private RadioButton rb_media;
    private RadioButton rb_agenda;
    private RadioButton rb_profile;
    private Drawable draw_home;
    private Drawable draw_running;
    private Drawable draw_media;
    private Drawable draw_agenda;
    private Drawable draw_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_running);

        rg_running = findViewById(R.id.running_radioGroup);

        rb_home = findViewById(R.id.rb_run_main);
        if(rb_home == null){
            Log.d("error","rb_home null");
        }
//        Drawable draw_home = getResources().getDrawable(R.drawable.bt_home);
//        draw_home.setBounds(0,0,50,50);
//        rb_home.setCompoundDrawables(null,draw_home,null,null);
//
//        rb_running = findViewById(R.id.rb_run_running);
//        Drawable draw_running = getResources().getDrawable(R.drawable.bt_running);
//        draw_running.setBounds(0,0,50,50);
//        rb_running.setCompoundDrawables(null,draw_running,null,null);
//
//        rb_media = findViewById(R.id.rb_run_media);
//        Drawable draw_media = getResources().getDrawable(R.drawable.bt_media);
//        draw_media.setBounds(0,0,50,50);
//        rb_media.setCompoundDrawables(null,draw_media,null,null);
//
//        rb_agenda = findViewById(R.id.rb_run_agenda);
//        Drawable draw_agenda = getResources().getDrawable(R.drawable.bt_agenda);
//        draw_agenda.setBounds(0,0,50,50);
//        rb_agenda.setCompoundDrawables(null,draw_agenda,null,null);
//
//        rb_profile = findViewById(R.id.rb_run_my);
//        Drawable draw_profile = getResources().getDrawable(R.drawable.bt_profile);
//        draw_profile.setBounds(0,0,50,50);
//        rb_profile.setCompoundDrawables(null,draw_profile,null,null);

//        rg_running.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            Intent intentMain = new Intent(RunningActivity.this,MainActivity.class);
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_run_main:
//                        MainActivity.JUMP_FRAGMENT = "main";
//                        startActivity(intentMain);
//                        Log.d("jump","ok");
//                        break;
//                    case R.id.rb_run_running:
//                        break;
//                    case R.id.rb_run_media:
//                        Intent intentMedia = new Intent(RunningActivity.this,MediaActivity.class);
//                        startActivity(intentMedia);
//                        break;
//                    case R.id.rb_run_agenda:
//                        MainActivity.JUMP_FRAGMENT = "agenda";
//                        startActivity(intentMain);
//                        break;
//                    case R.id.rb_run_my:
//                        MainActivity.JUMP_FRAGMENT = "profile";
//                        startActivity(intentMain);
//                        break;
//                }
//            }
//        });


        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));
        placesClient = Places.createClient(this);
        //创建位置信息服务客户端
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_polyline) {
            showMarker();
            mMap.addMarker(mylocationMaker);
            if(locationMaker == null){
                locationMaker = mylocationMaker;
            }
            String url = getUrl(mylocationMaker.getPosition(),locationMaker.getPosition(),"driving");
            new FetchURL(RunningActivity.this).execute(url, "driving");
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap mMap){
        this.mMap = mMap;

        getLocationPermission();

        //showMarker();

        updateLocationUI();

        getDeviceLocation();

        mMap.setOnMapLongClickListener(this);
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        if (requestCode == 44) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }

    private void showMarker() {

        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                resultat = new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude());
                                mylocationMaker = new MarkerOptions()
                                        .position(resultat)
                                        .title("mylocationMaker")
                                        .draggable(true);
                                //mMap.addMarker(mylocationMaker);
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            resultat = defaultLocation;
                            mylocationMaker = new MarkerOptions()
                                    .position(resultat)
                                    .title("Location1")
                                    .draggable(true);
                            mMap.addMarker(mylocationMaker);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                //getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private String getUrl(LatLng origin, LatLng destination, String directionMode){
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_destination = "destination=" + destination.latitude + "," + destination.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_destination + "&" + mode;
        String format = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + format + "?" + parameters + "&key=" + getString(R.string.google_maps_key);

        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (polyline != null)
            polyline.remove();

        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d(TAG, "onMapLongClick" + latLng.toString());
        locationMaker = new MarkerOptions().position(latLng);
        mMap.addMarker(locationMaker);
        /*
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address address = addresses.get(0);
            String street = address.getAddressLine(0);
            locationMaker = new MarkerOptions().position(latLng).title(street);
            mMap.addMarker(locationMaker);
        }catch (IOException e){
            e.printStackTrace();
        }
        */
    }
}