package com.wallet.walkthedog.dao.request;

public class MailRequest {
    private String priceSort;//传asc 升序  desc降序
    private String nftCatagoryId;

    public MailRequest(String priceSort, String nftCatagoryId) {
        this.priceSort = priceSort;
        this.nftCatagoryId = nftCatagoryId;
    }

    public String getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(String priceSort) {
        this.priceSort = priceSort;
    }

    public String getNftCatagoryId() {
        return nftCatagoryId;
    }

    public void setNftCatagoryId(String nftCatagoryId) {
        this.nftCatagoryId = nftCatagoryId;
    }
}
