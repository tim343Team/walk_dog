package com.wallet.walkthedog.view.mail.transaction;

import com.wallet.walkthedog.dao.CodeDataDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.view.mail.MailContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class TransactionContract {
    interface TransactionView extends Contract.BaseView<TransactionContract.TransactionPresenter> {

        void getFail(Integer code, String toastMessage);

        void getWalletInfo(String data,String type);

        void bugSuccess(String data);

        void cancelSellSuccess(String data);

        void getSysDataCodeSuccess(CodeDataDao data);

    }

    interface TransactionPresenter extends Contract.BasePresenter {
        void getWallet(String type);//获取钱包余额 1是代币 2狗粮

        void buyDog(BuyRequest request);//买狗

        void buyProp(BuyRequest request);//买道具

        void cancelSellDog(BuyRequest request);//取消售卖狗狗

        void cancelSellProp(BuyRequest request);//取消售卖道具

        void getSysDataCode(String code);//获取交易手续费
    }
}
