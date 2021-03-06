package com.wallet.walkthedog.view.home;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.TrainRequest;
import com.wallet.walkthedog.view.login.SettingPasswordContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class HomeContract {
    interface HomeView extends Contract.BaseView<HomeContract.HomePresenter> {

        void getFail(Integer code, String toastMessage);

        void getCurrentDogInfo(DogInfoDao dogInfoDao);

        void getFeedDogInfo(String data);

        void getWalletInfo(String data,String type);

        void feedSuccessful(String data);

        void feedFail(Integer code, String toastMessage);
    }

    interface HomePresenter extends Contract.BasePresenter {
        void getDogInfo(String dogId);

        void getUseDog(String dogId);

        void getWalkTheDogFriend(String dogId);

        void getFeedDog(String dogId);//查询喂食消耗

        void getWallet(String type);//获取钱包余额 1是代币 2狗粮

        void feedDog(String dogId);//喂食

        void getAllTrain();//获取所有训练项目

        void trainDog(TrainRequest request);//训练狗狗
    }
}
