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
    String address;
    String casenumber;
    char date;
    char time;

    public Accident() {}

public Accident (String collision, String description, int severity, String address, String casenumber, char date, char time) {
    this.collision = collision;
    this.description = description;
    this.severity = severity;
    this.address = address;
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
    public String getAddress() {
        return address;
    }
    public String getCasenumber() {
        return casenumber;
    }
    public char getDate() {
        return date;
    }
    public char getTime() {
        return time;
    }
}
