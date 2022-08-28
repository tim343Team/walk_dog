package com.wallet.walkthedog.view.select_dog;

import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.view.home.HomeContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class SelectContract {

    interface SelectView extends Contract.BaseView<SelectContract.SelectPresenter> {

        void getFail(Integer code, String toastMessage);

        void getMyDogSuccess(List<DogInfoDao> data);

        void useDogSuccess(String data,String dogId);

        void removeDogSuccess(String data,String dogId);

        void getFeedDogInfo(FeedDogFoodDao data,DogInfoDao mDefultDogInfo);

        void feedSuccessful(String data);

        void feedFail(Integer code, String toastMessage);

        void getShopDogFoodSuccessful(DogFoodDao data);

        void buyShopDogFoodSuccessful(String data);

        void getWalletInfo(String data,String type);

    }

    interface SelectPresenter extends Contract.BasePresenter {
        void getUserDog(int type,int pageNo);

        void useDog(String dogId);

        void removeDog(String dogId);

        void getWallet(String type);//获取钱包余额 1是代币 2狗粮

        void getFeedDog(String dogId,DogInfoDao mDefultDogInfo);//查询喂食消耗

        void feedDog(String dogId);//喂食

        void getShopDogFood();// 获取商城售卖狗粮详情

        void buyShopDogFood(int dogFoodId,int number,String password);//购买商城出售的狗粮

    }
}
