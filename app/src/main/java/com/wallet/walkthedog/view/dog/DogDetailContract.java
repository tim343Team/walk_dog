package com.wallet.walkthedog.view.dog;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.view.dog.fragment.DogContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class DogDetailContract {

    interface DogDetailView extends Contract.BaseView<DogDetailContract.DogDetailPresenter> {
        void getFail(Integer code, String toastMessage);

        void sellDog(String data);

        void getFeedDogInfo(String data);

        void feedSuccessful(String data);

        void feedFail(Integer code, String toastMessage);

        void getWalletInfo(String data,String type);

        void useDogSuccess(String data,String dogId);

    }

    interface DogDetailPresenter extends Contract.BasePresenter {
        void sellDog(SellRequest request);

        void getFeedDog(String dogId);//查询喂食消耗

        void feedDog(String dogId);//喂食

        void getWallet(String type);//获取钱包余额 1是代币 2狗粮

        void useDog(String dogId);

    }
}
