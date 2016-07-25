package com.epicodus.android_bike_accidents.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.android_bike_accidents.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.summaryTextView) TextView mSummaryTextView;
    @Bind(R.id.accidentListButton) Button mAccidentListButton;
    @Bind(R.id.createReportButton) Button mCreateReportButton;
    @Bind(R.id.accidentMapButton) Button mAccidentMapButton;
    @Bind(R.id.bikeImageView) ImageView mBikeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Picasso.with(this).load(R.drawable.bike).into(mBikeImageView);
    }
}
