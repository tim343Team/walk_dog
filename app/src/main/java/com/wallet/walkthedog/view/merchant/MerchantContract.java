package com.wallet.walkthedog.view.merchant;

import com.wallet.walkthedog.dao.CardInfoDao;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.dao.request.CardEditRequset;
import com.wallet.walkthedog.dao.request.MerchantRequest;
import com.wallet.walkthedog.view.card.CardContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class MerchantContract {
    interface MerchantView extends Contract.BaseView<MerchantContract.MerchantPresenter> {
        void getFail(Integer code, String toastMessage);

        void cancleSuccess(String toastMessage);

        void applySuccess(String toastMessage);

        void statusSuccess(MerchantStatusDao merchantStatusDao);

    }

    interface MerchantPresenter extends Contract.BasePresenter {
        void merchantStatus();

        void cancleMerchant();

        void applyMerchant(MerchantRequest request);
    }
}
