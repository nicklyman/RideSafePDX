package com.epicodus.android_bike_accidents.models;

import org.parceler.Parcel;

@Parcel
public class LatLng {
    public double latitude;
    public double longitude;

    public LatLng(){};

    public LatLng(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double latitude(){
        return this.latitude;
    }

    public double longitude(){
        return this.longitude;
    }
}