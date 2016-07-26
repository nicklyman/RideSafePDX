package com.epicodus.android_bike_accidents.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.Accident;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccidentDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private AccidentPagerAdapter adapterViewPager;
    ArrayList<Accident> mAccidents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_detail);
        ButterKnife.bind(this);

        mAccidents = Parcels.unwrap(getIntent().getParcelableExtra("accidents"));

        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new AccidentPagerAdapter(getSupportFragmentManager(), mAccidents);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
