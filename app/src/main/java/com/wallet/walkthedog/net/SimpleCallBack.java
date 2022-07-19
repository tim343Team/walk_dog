package com.wallet.walkthedog.net;

import com.wallet.walkthedog.untils.ToastUtils;

public abstract class SimpleCallBack<T> extends GsonWalkDogCallBack<T> {

    @Override
    void onFail(Exception e) {
        ToastUtils.shortToast(e.getMessage());
    }
}
