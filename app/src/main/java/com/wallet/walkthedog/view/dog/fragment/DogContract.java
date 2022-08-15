package com.wallet.walkthedog.view.dog.fragment;

import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.view.props.fragment.PropsContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class DogContract {

    interface DogView extends Contract.BaseView<DogContract.DogPresenter> {
        void getFail(Integer code, String toastMessage);

        void getMyDogSuccess(List<DogInfoDao> data);

        void useDogSuccess(String data,String dogId);

        void cancelSellSuccess(String data,int position);

        void getFeedDogInfo(FeedDogFoodDao data, DogInfoDao mDefultDogInfo);

        void feedSuccessful(String data);

        void feedFail(Integer code, String toastMessage);

        void getShopDogFoodSuccessful(DogFoodDao data);

        void buyShopDogFoodSuccessful(String data);

        void getWalletInfo(String data,String type);
    }

    interface DogPresenter extends Contract.BasePresenter {
        void getUserDog(int type,int pageNo);

        void useDog(String dogId);

        void removeDog(String dogId);

        void cancelSellDog(BuyRequest request,int position);//取消售卖狗狗

        void getWallet(String type);//获取钱包余额 1是代币 2狗粮

        void getFeedDog(String dogId,DogInfoDao mDefultDogInfo);//查询喂食消耗

        void feedDog(String dogId);//喂食

        void getShopDogFood();// 获取商城售卖狗粮详情

        void buyShopDogFood(int dogFoodId,int number);//购买商城出售的狗粮

    }
}
