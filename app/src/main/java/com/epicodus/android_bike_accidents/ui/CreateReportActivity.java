package com.epicodus.android_bike_accidents.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.epicodus.android_bike_accidents.Constants;
import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.models.Accident;
import com.epicodus.android_bike_accidents.models.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateReportActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.selectDateButton) Button mSelectDateButton;
    @Bind(R.id.dateOutput) TextView mDateOutput;
    @Bind(R.id.selectTimeButton) Button mSelectTimeButton;
    @Bind(R.id.timeOutput) TextView mTimeOutput;
    @Bind(R.id.typeSpinner) Spinner mTypeSpinner;
    @Bind(R.id.severitySpinner) Spinner mSeveritySpinner;
    @Bind(R.id.submitButton) Button mSubmitButton;
    @Bind(R.id.descriptionEditText) EditText mDescriptionEditText;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.caseNumberEditText) EditText mCaseNumberEditText;
    @Bind(R.id.latitudeEditText) EditText mLatitudeEditText;
    @Bind(R.id.longitudeEditText) EditText mLongitudeEditText;
    @Bind(R.id.getLocationButton) Button mGetLocationButton;

    private LocationManager locationManager;
    public static Double userLong;
    public static Double userLat;
    public static LatLng userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> collisionAdapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        collisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(collisionAdapter);

        ArrayAdapter<CharSequence> severityAdapter = ArrayAdapter.createFromResource(this, R.array.severities_array, android.R.layout.simple_spinner_item);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSeveritySpinner.setAdapter(severityAdapter);

        mSelectDateButton.setOnClickListener(this);
        mSelectTimeButton.setOnClickListener(this);
        mGetLocationButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mDateOutput.setVisibility(View.INVISIBLE);
        mTimeOutput.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == mSelectDateButton) {
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(CreateReportActivity.this, new mDateSetListener(), mYear, mMonth, mDay);
            dialog.show();
        } else if (v == mSelectTimeButton) {
            TimePickerDialog dialog = new TimePickerDialog(CreateReportActivity.this, new mTimeSetListener(), 12, 0, false);
            dialog.show();
        }

        if (v == mSubmitButton) {

            if(mLatitudeEditText.getText().toString().length() != 0 && mLongitudeEditText.getText().toString().length() != 0) {
                String type = mTypeSpinner.getSelectedItem().toString();
                String description = mDescriptionEditText.getText().toString().trim();
                String severityString = mSeveritySpinner.getSelectedItem().toString();
                int severityInt = Integer.parseInt(severityString.substring(0,1));
                String date = mDateOutput.getText().toString().trim();
                String time = mTimeOutput.getText().toString().trim();
                String location = mLocationEditText.getText().toString().trim();
                String policeCaseNumber = mCaseNumberEditText.getText().toString().trim();
                double latitude = Double.parseDouble(mLatitudeEditText.getText().toString());
                double longitude = Double.parseDouble(mLongitudeEditText.getText().toString());

                Accident userInput = new Accident(type, description, severityInt, date, time, location, latitude, longitude, policeCaseNumber);

                DatabaseReference accidentRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ACCIDENTS);
                accidentRef.push().setValue(userInput);
                Toast.makeText(CreateReportActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateReportActivity.this, AccidentListActivity.class);
                startActivity(intent);
            } else {
                mLongitudeEditText.setError("please enter a longitude");
                mLatitudeEditText.setError("pleade enter a latitude");
            }

        }

        if(v == mGetLocationButton) {
            ///FIND USER LOCATION WITH PERMISSIONS
            refreshLocation();
            Log.d("latitude", String.valueOf(userLat));
            Log.d("longitude", String.valueOf(userLong));
        }
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            mDateOutput.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            System.out.println(mDateOutput.getText().toString());
            mDateOutput.setVisibility(View.VISIBLE);
        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int mHourOfDay = hourOfDay;
            int mMinute = minute;
            if(mHourOfDay > 12) {
                mTimeOutput.setText(new StringBuilder().append(mHourOfDay - 12).append(":").append(String.format("%02d", mMinute)).append("pm"));
            } else if (mHourOfDay == 12) {
                mTimeOutput.setText(new StringBuilder().append(mHourOfDay).append(":").append(String.format("%02d", mMinute)).append("pm"));
            } else if(mHourOfDay == 0)
                mTimeOutput.setText(new StringBuilder().append(mHourOfDay + 12).append(":").append(String.format("%02d", mMinute)).append("am"));
            else {
                mTimeOutput.setText(new StringBuilder().append(mHourOfDay).append(":").append(String.format("%02d", mMinute)).append("am"));
            }
            System.out.println(mTimeOutput.getText().toString());
            mTimeOutput.setVisibility(View.VISIBLE);
        }
    }

    private void refreshLocation() {
        ///SETS CRITERIA FOR WANTED CONNECTION
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        ///SETS REFRESH ON USER LOCATION TO EVERY SECOND.
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}, 10);
                }
                return;
            }
            locationManager.requestLocationUpdates(provider, 1000, 0, listener);
        } else {
            Log.d("provider", "NO PROVIDER FOUND!");
        }
    }


    ///LISTENING FOR CHANGE IN LOCATION, SETTING USER LOCATION.
    private final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {
            userLong = location.getLongitude();
            userLat = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }

        @Override
        public void onProviderEnabled(String s) {
            userLocation = new LatLng(userLat, userLong);
        }

        @Override
        public void onProviderDisabled(String s) { }
    };
}




