package com.epicodus.android_bike_accidents.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageView;
import android.widget.TextView;
import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.CustomLatLng;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface CaviarDreams = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");
        mSummaryTextView.setTypeface(CaviarDreams);
        mAccidentText.setTypeface(CaviarDreams);

        mCreateReportButton.setOnClickListener(this);
        mAccidentListButton.setOnClickListener(this);
        mAccidentMapButton.setOnClickListener(this);
        Picasso.with(this).load(R.drawable.bike).into(mBikeImageView);
    }

    @Override
    public void onClick(View v) {
        if(v == mCreateReportButton) {
            Intent intent = new Intent(MainActivity.this, CreateReportActivity.class);
            startActivity(intent);
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
}

