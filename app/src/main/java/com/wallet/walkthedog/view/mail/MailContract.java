package com.wallet.walkthedog.view.mail;

import com.wallet.walkthedog.dao.DogBoxDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.TrainDao;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.view.home.HomeContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class MailContract {
    interface MailView extends Contract.BaseView<MailContract.MailPresenter> {

        void getFail(Integer code, String toastMessage);

        void getDogListSuccess(List<DogMailDao> data);

        void getPropListSuccess(List<PropMailDao> data);

        void getBoxtSuccess(List<DogBoxDao> data);
    }

    interface MailPresenter extends Contract.BasePresenter {
        void getDogList(MailRequest request,int pageNo);

        void getPropList(MailRequest request,int pageNo);

        void getDogDownBox();

        void getPropDownBox();
    }
}
