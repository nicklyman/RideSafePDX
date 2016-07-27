package com.epicodus.android_bike_accidents.models;


import com.epicodus.android_bike_accidents.models.LatLng;
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
    LatLng coordinates;
    String casenumber;
    String date;
    String time;

    public Accident() {}

public Accident (String collision, String description, int severity, String date, String time, String location, LatLng coordinates, String casenumber) {
    this.collision = collision;
    this.description = description;
    this.severity = severity;
    this.location = location;
    this.coordinates = coordinates;
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
    public LatLng getCoordinates() {return coordinates; }
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
