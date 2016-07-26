package com.epicodus.android_bike_accidents.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/26/16.
 */

@Parcel
public class Accident {
    String collision;
    String description;
    int severity;
    String location;
    String casenumber;
    String date;
    String time;

    public Accident() {}

public Accident (String collision, String description, int severity, String date, String time, String location, String casenumber) {
    this.collision = collision;
    this.description = description;
    this.severity = severity;
    this.location = location;
    this.casenumber = casenumber;
    this.date = date;
    this.time = time;
    }

    public String getCollision() {
        return collision;
    }
    public String getDescription() {
        return description;
    }
    public int getSeverity() {
        return severity;
    }
    public String getLocation() {
        return location;
    }
    public String getCasenumber() {
        return casenumber;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
}
