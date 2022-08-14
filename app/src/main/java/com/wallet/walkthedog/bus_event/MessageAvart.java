package com.wallet.walkthedog.bus_event;

/**
 * ${description}
 *
 * @author weiqiliu
 * @version 1.0 2020/5/14
 */
public class MessageAvart {
    private String path;

    public MessageAvart(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
