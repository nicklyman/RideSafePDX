package com.epicodus.android_bike_accidents.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.epicodus.android_bike_accidents.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.createReportButton) Button mReportButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mReportButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mReportButton) {
            Intent intent = new Intent(MainActivity.this, CreateReportActivity.class);
            startActivity(intent);
        }
    }
}

