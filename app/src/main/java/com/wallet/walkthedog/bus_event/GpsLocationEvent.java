package com.wallet.walkthedog.bus_event;

public class GpsLocationEvent {
    private double latitude;
    private double longitude;
    private String Speed;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }
}
