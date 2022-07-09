package com.wallet.walkthedog.bus_event;

public class GpsSateliteEvent {
    private int connSatellite;
    private int showSatellite;

    public int getConnSatellite() {
        return connSatellite;
    }

    public void setConnSatellite(int connSatellite) {
        this.connSatellite = connSatellite;
    }

    public int getShowSatellite() {
        return showSatellite;
    }

    public void setShowSatellite(int showSatellite) {
        this.showSatellite = showSatellite;
    }
}
