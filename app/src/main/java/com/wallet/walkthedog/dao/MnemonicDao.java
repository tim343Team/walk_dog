package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class MnemonicDao implements Serializable {
    private boolean isSelect=false;
    private String words;

    public MnemonicDao() {
    }

    public MnemonicDao(String words) {
        this.words = words;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
