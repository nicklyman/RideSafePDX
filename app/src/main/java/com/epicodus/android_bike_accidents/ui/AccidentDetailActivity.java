package com.epicodus.android_bike_accidents.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.Accident;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccidentDetailActivity extends AppCompatActivity {
    @Bind(R.id.accidentReportTextView) TextView mAccidentReportTextView;
    @Bind(R.id.typeTextView) TextView mCollisionTypeTextView;
    @Bind(R.id.descriptionTextView) TextView mDescriptionTextView;
    @Bind(R.id.severityTextView) TextView mSeverityTextView;
    @Bind(R.id.dateOutput) TextView mDateOutput;
    @Bind(R.id.timeOutput) TextView mTimeOutput;
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.caseNumberTextView) TextView mCaseNumberTextView;

    ArrayList<Accident> mAccidents = new ArrayList<>();
    private Accident accident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_detail);
        ButterKnife.bind(this);

        mAccidents = Parcels.unwrap(getIntent().getParcelableExtra("accidents"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        accident = mAccidents.get(startingPosition);

        mCollisionTypeTextView.setText("Collision Type: " + accident.getCollision());
        mDescriptionTextView.setText("Description: " + accident.getDescription());
        mSeverityTextView.setText("Severity: " + accident.getSeverity());
        mDateOutput.setText("Date: " + accident.getDate());
        mTimeOutput.setText("Time: " + accident.getTime());
        mLocationTextView.setText("Location: " + accident.getLocation());
        mCaseNumberTextView.setText("Case#: " + accident.getCasenumber());
    }
}