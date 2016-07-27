package com.epicodus.android_bike_accidents.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.CustomLatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    CustomLatLng mCoordinates;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mCoordinates = Parcels.unwrap(getIntent().getParcelableExtra("coordinates"));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng mapCoordinates = new LatLng(mCoordinates.latitude(), mCoordinates.longitude());

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        CustomLatLng reportCoordinates = new CustomLatLng(mCoordinates);
        mMap.addMarker(new MarkerOptions().position(mapCoordinates).title("Incident Severity: "));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCoordinates, 16));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
    }
}
