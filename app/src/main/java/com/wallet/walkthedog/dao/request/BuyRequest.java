package com.wallet.walkthedog.dao.request;

public class BuyRequest {
    private String id;
    private String password;

    public BuyRequest(String id) {
        this.id = id;
    }

    public BuyRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
