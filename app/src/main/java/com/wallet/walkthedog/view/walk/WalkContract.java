package com.wallet.walkthedog.view.walk;

import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.view.email.EmailContract;

import tim.com.libnetwork.base.Contract;

public class WalkContract {
    interface WalkView extends Contract.BaseView<WalkContract.WalkPresenter> {

        void getFail(Integer code, String toastMessage);

    }

    interface WalkPresenter extends Contract.BasePresenter {

        void startWalkDog(SwitchWalkRequest request);

        void stopWalkDog(SwitchWalkRequest request);
    }
}
