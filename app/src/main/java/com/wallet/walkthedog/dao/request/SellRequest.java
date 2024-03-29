package com.wallet.walkthedog.dao.request;

public class SellRequest {
    private String id;
    private String price;
    private String password;

    public SellRequest(String id, String price,String password) {
        this.id = id;
        this.price = price;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
