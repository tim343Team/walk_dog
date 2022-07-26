package com.wallet.walkthedog.view.mail.transaction;

import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.view.mail.MailContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class TransactionContract {
    interface TransactionView extends Contract.BaseView<TransactionContract.TransactionPresenter> {

        void getFail(Integer code, String toastMessage);

        void getWalletInfo(String data,String type);

    }

    interface TransactionPresenter extends Contract.BasePresenter {
        void getWallet(String type);//获取钱包余额 1是代币 2狗粮
    }
}
