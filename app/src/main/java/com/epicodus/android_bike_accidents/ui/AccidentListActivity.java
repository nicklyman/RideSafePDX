package com.epicodus.android_bike_accidents.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.android_bike_accidents.Constants;
import com.epicodus.android_bike_accidents.R;
import com.epicodus.android_bike_accidents.adapters.FirebaseAccidentViewHolder;
import com.epicodus.android_bike_accidents.models.Accident;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccidentListActivity extends AppCompatActivity {
    private DatabaseReference mAccidentReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
//    private FirebaseAccidentViewHolder mAdapter;
//
    public ArrayList<Accident> mAccidents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        mAccidentReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ACCIDENTS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Accident, FirebaseAccidentViewHolder>
                (Accident.class, R.layout.accident_list_item, FirebaseAccidentViewHolder.class,
                        mAccidentReference) {

            @Override
            protected void populateViewHolder(FirebaseAccidentViewHolder viewHolder,
                                              Accident model, int position) {
                viewHolder.bindAccident(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

//
//    private void getAccidents() {
//        AccidentListActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter = new FirebaseAccidentViewHolder(getApplicationContext(), mAccidents);
//                mRecyclerView.setAdapter(mAdapter);
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AccidentListActivity.this);
//                mRecyclerView.setLayoutManager(layoutManager);
//                mRecyclerView.setHasFixedSize(true);
//            }
//        });
//    }
}
