package com.epicodus.android_bike_accidents.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.epicodus.android_bike_accidents.R;

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
}


