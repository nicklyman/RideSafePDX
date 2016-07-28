package com.epicodus.android_bike_accidents.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageView;
import android.widget.TextView;
import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.CustomLatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.summaryTextView) TextView mSummaryTextView;
    @Bind(R.id.accidentListButton) Button mAccidentListButton;
    @Bind(R.id.createReportButton) Button mCreateReportButton;
    @Bind(R.id.accidentMapButton) Button mAccidentMapButton;
    @Bind(R.id.bikeImageView) ImageView mBikeImageView;
    @Bind(R.id.accidentText) TextView mAccidentText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;

    private boolean mIsLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface CaviarDreams = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");
        mSummaryTextView.setTypeface(CaviarDreams);
        mAccidentText.setTypeface(CaviarDreams);
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mIsLoggedIn = true;
                } else {
                    mIsLoggedIn = false;
                }
            }
        };


        mCreateReportButton.setOnClickListener(this);
        mAccidentListButton.setOnClickListener(this);
        mAccidentMapButton.setOnClickListener(this);
        Picasso.with(this).load(R.drawable.bike).into(mBikeImageView);
    }

    @Override
    public void onClick(View v) {
        if(v == mCreateReportButton) {
            if (mIsLoggedIn) {
                Intent intent = new Intent(MainActivity.this, CreateReportActivity.class);
                startActivity(intent);
            } else {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }


        }
        if(v == mAccidentListButton) {
            Intent intent = new Intent(MainActivity.this, AccidentListActivity.class);
            startActivity(intent);
        }
        if(v == mAccidentMapButton) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//            CustomLatLng latLng = new CustomLatLng(45.467894, -122.658661);
//            intent.putExtra("coordinates", Parcels.wrap(latLng));
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

