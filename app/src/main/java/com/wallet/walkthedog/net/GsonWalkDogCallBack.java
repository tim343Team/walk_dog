package com.wallet.walkthedog.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public abstract class GsonWalkDogCallBack<T> extends AbsWalkDogCallBack<RemoteData<T>> {

    static Gson gson = new Gson();

    @Override
    RemoteData<T> conver(String string) {
        Type type = new TypeToken<RemoteData<T>>() {
        }.getType();
        return gson.fromJson(string, type);
    }


    @Override
    void onRes(RemoteData<T> tRemoteData) {
        if (tRemoteData.getCode() != 0) {
            if (TextUtils.isEmpty(tRemoteData.getMessage())) {
                tRemoteData.setMessage("unknown err");
            }
            fail(converException(new ApiException(tRemoteData.getMessage())));
        } else {
            onSuccess(tRemoteData.getData());
        }
    }

    static class ApiException extends Exception {
        public ApiException(String messgae) {
            super(messgae);
        }
    }

    protected abstract void onSuccess(T t);
}
