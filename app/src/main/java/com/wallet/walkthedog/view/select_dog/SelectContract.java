package com.wallet.walkthedog.view.select_dog;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.view.home.HomeContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class SelectContract {

    interface SelectView extends Contract.BaseView<SelectContract.SelectPresenter> {

        void getFail(Integer code, String toastMessage);

        void getMyDogSuccess(List<DogInfoDao> data);

    }

    interface SelectPresenter extends Contract.BasePresenter {
        void getUserDog();

    }
}
