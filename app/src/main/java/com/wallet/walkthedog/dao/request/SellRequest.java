package com.wallet.walkthedog.dao.request;

public class SellRequest {
    private String id;
    private String price;

    public SellRequest(String id, String price) {
        this.id = id;
        this.price = price;
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
}
