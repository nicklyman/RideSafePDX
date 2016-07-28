package com.epicodus.android_bike_accidents.ui;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.epicodus.android_bike_accidents.Constants;
import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.Accident;
import com.epicodus.android_bike_accidents.models.CustomLatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    CustomLatLng mCoordinates;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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

        final LatLng center = new LatLng(45.520886, -122.677395);

        mMap = googleMap;

        if (Parcels.unwrap(getIntent().getParcelableExtra("coordinates")) == null) {
            //get all coordinates from firebase
            mCoordinates = new CustomLatLng(45.467894, -122.658661);

            FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ACCIDENTS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot coordinatesSnapshot : dataSnapshot.getChildren()) {
                        Accident accident = coordinatesSnapshot.getValue(Accident.class);
                        String coordinates = String.valueOf(accident.getCoordinates().latitude()) +"," + String.valueOf(accident.getCoordinates().longitude()) ;
                        Log.d("Coordinates: ", coordinates);
                        LatLng mapCoordinates = new LatLng(accident.getCoordinates().latitude(), accident.getCoordinates().longitude());
                        if(accident.getSeverity() == 0) {
                            mMap.addMarker(new MarkerOptions().position(mapCoordinates).title("Near Collision: " + accident.getCollision()).snippet("Severity: 0 - Near collision"));
                        } else if (accident.getSeverity() == 1) {
                            mMap.addMarker(new MarkerOptions().position(mapCoordinates).title("Collision: " + accident.getCollision()).snippet("Severity: 1 - Minor scrape & bruise"));
                        } else if (accident.getSeverity() == 2) {
                            mMap.addMarker(new MarkerOptions().position(mapCoordinates).title("Collision: " + accident.getCollision()).snippet("Severity: 2 - Injury"));
                        } else if (accident.getSeverity() == 3) {
                            mMap.addMarker(new MarkerOptions().position(mapCoordinates).title("Collision: " + accident.getCollision()).snippet("Severity: 3 - Hospitalization"));
                        } else if (accident.getSeverity() == 4) {
                            mMap.addMarker(new MarkerOptions().position(mapCoordinates).title("Collision: " + accident.getCollision()).snippet("Severity: 4 - Fatal"));
                        }
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {
            mCoordinates = Parcels.unwrap(getIntent().getParcelableExtra("coordinates"));
            LatLng mapCoordinate = new LatLng(mCoordinates.latitude(), mCoordinates.longitude());
            Log.d("individual latlng", mapCoordinate.toString());
            mMap.addMarker(new MarkerOptions().position(mapCoordinate).title("Accident"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCoordinate, 16));
        }
    }
}
