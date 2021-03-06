package com.epicodus.android_bike_accidents.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.epicodus.android_bike_accidents.models.CustomLatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    public AlertDialog mErrorDialog;
    AlertDialog.Builder alertDialogBuilder;

    private LocationManager locationManager;
    public static Double userLong;
    public static Double userLat;
    public static CustomLatLng userLocation;

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
        mSubmitButton.setOnClickListener(this);
        mDateOutput.setVisibility(View.INVISIBLE);
        mTimeOutput.setVisibility(View.INVISIBLE);

        createErrorDialog();
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
            String type = mTypeSpinner.getSelectedItem().toString();
            TextView errorText = (TextView)mTypeSpinner.getSelectedView();
            if (type.equals("")) {
                errorText.setText("Select incident type");
                errorText.setTextColor(Color.RED);
                return;
            }
            String description = mDescriptionEditText.getText().toString().trim();
            String severityString = mSeveritySpinner.getSelectedItem().toString();
            TextView severityErrorText = (TextView)mSeveritySpinner.getSelectedView();
            if (severityString.equals("")) {
                severityErrorText.setText("Select incident severity");
                severityErrorText.setTextColor(Color.RED);
                return;
            }
            int severityInt = Integer.parseInt(severityString.substring(0,1));
            String date = mDateOutput.getText().toString().trim();
            if (date.equals("")) {
                mErrorDialog = alertDialogBuilder.show();
                return;
            }
            String time = mTimeOutput.getText().toString().trim();
            if (time.equals("")) {
                mErrorDialog = alertDialogBuilder.show();
                return;
            }
            String location = mLocationEditText.getText().toString().trim();
            String policeCaseNumber = mCaseNumberEditText.getText().toString().trim();

            if(!mLocationEditText.getText().toString().equals("")) {
                CustomLatLng newCoordinates = getLocationFromAddress(mLocationEditText.getText().toString().trim());
                if(newCoordinates == null) {
                    mLocationEditText.setError("Couldn't find coordinates for this address, try a different address");
                    return;
                } else {
                    //do stuff (latitude, longitude)
                    Log.v("Coordinates: ", newCoordinates.toString());

                    Accident userInput = new Accident(type, description, severityInt, date, time, location, newCoordinates, policeCaseNumber);

                    DatabaseReference accidentRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ACCIDENTS);
                    accidentRef.push().setValue(userInput);
                    Toast.makeText(CreateReportActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                }

            } else {
                mLocationEditText.setError("Please enter an address to use the button");
                return;
            }
            Intent intent = new Intent(CreateReportActivity.this, MainActivity.class);
            startActivity(intent);
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


    //geocode latitude and longitude from address
    public CustomLatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(CreateReportActivity.this, Locale.getDefault());
        List<Address> address;
        CustomLatLng coordinates = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            coordinates = new CustomLatLng(location.getLatitude(), location.getLongitude());

            return coordinates;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinates;
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
        Intent intent = new Intent(CreateReportActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void createErrorDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this)
            .setTitle("Date & Time Required")
            .setMessage("Please choose a date and time before submitting")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });



//        mErrorDialog = new AlertDialog(this);
//        mErrorDialog.setTitle("Loading...");
//        mErrorDialog.set
//        mErrorDialog.setCancelable(true);
    }
}




