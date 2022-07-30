package com.wallet.walkthedog.bus_event;

public class GpsStartEvent {
    private int logId;

    public GpsStartEvent(int logId) {
        this.logId = logId;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }
}
