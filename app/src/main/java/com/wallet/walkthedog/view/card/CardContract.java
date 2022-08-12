package com.wallet.walkthedog.view.card;

import com.wallet.walkthedog.dao.CardInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.request.CardEditRequset;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.view.dog.DogDetailContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class CardContract {

    interface CardView extends Contract.BaseView<CardContract.CardPresenter> {
        void getFail(Integer code, String toastMessage);

        void getBankAccountSuccess(List<CardInfoDao> cardInfoDaos);

        void getEditSuccess(String message);
    }

    interface CardPresenter extends Contract.BasePresenter {
        void getBankAccount();

        void getApproveBank(CardEditRequset requset);

        void getApproveSwift(CardEditRequset requset);
    }
}
