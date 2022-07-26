package com.wallet.walkthedog.service;

import com.wallet.walkthedog.dao.request.SwitchWalkRequest;

import tim.com.libnetwork.base.Contract;

public class WalkContract {
    interface WalkView extends Contract.BaseView<WalkPresenter> {

        void getFail(Integer code, String toastMessage);

        void stopSuccess(String message);

    }

    interface WalkPresenter extends Contract.BasePresenter {

        void startWalkDog(SwitchWalkRequest request);

        void stopWalkDog(SwitchWalkRequest request);
    }
}
