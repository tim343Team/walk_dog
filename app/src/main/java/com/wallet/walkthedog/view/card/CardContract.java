package com.wallet.walkthedog.view.card;

import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.view.dog.DogDetailContract;

import tim.com.libnetwork.base.Contract;

public class CardContract {

    interface CardView extends Contract.BaseView<CardContract.CardPresenter> {
        void getFail(Integer code, String toastMessage);

    }

    interface CardPresenter extends Contract.BasePresenter {

    }
}
