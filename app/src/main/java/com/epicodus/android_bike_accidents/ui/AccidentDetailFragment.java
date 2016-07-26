package com.epicodus.android_bike_accidents.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.android_bike_accidents.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccidentDetailFragment extends Fragment {


    public AccidentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accident_detail, container, false);
    }

}
