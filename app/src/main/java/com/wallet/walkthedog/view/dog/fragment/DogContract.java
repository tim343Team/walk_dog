package com.wallet.walkthedog.view.dog.fragment;

import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.DogInfoDao;
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

    }

    interface DogPresenter extends Contract.BasePresenter {
        void getUserDog(int type,int pageNo);

        void useDog(String dogId);

        void removeDog(String dogId);

        void cancelSellDog(BuyRequest request,int position);//取消售卖狗狗

    }
}
