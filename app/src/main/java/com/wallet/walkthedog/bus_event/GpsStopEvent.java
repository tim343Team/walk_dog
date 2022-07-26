package com.wallet.walkthedog.bus_event;

public class GpsStopEvent {
    private String lan;
    private String lon;

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
