package com.example.cbts1;

public class LocationHelper {

    private double latitude;
    private double longitude;

    public LocationHelper(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
