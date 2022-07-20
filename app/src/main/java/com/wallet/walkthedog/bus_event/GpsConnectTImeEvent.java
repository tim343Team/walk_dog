package com.wallet.walkthedog.bus_event;

public class GpsConnectTImeEvent {
    private long seconed;

    public GpsConnectTImeEvent(long seconed) {
        this.seconed = seconed;
    }

    public long getSeconed() {
        return seconed;
    }

    public void setSeconed(long seconed) {
        this.seconed = seconed;
    }
}
