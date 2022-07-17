package com.wallet.walkthedog.view.home;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.view.login.SettingPasswordContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class HomeContract {
    interface HomeView extends Contract.BaseView<HomeContract.HomePresenter> {

        void getFail(Integer code, String toastMessage);

        void getMyDogSuccess(List<DogInfoDao> data);

    }

    interface HomePresenter extends Contract.BasePresenter {
        void getUserDog();
    }
}
