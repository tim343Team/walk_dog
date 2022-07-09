package com.wallet.walkthedog.dao;

public class PropDao {
    private boolean isSelect=false;
    private String id;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
