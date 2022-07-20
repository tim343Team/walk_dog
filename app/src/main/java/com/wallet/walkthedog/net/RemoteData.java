package com.wallet.walkthedog.net;

import androidx.annotation.Nullable;

public class RemoteData<T> {
    @Nullable
    private T data;
    private int code;
    private String message = "";
    private int total;

    @Nullable
    public T getData() {
        return data;
    }

    public T getNotNullData() {
        if (data == null) {
            throw new NullPointerException("data is null");
        }
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
