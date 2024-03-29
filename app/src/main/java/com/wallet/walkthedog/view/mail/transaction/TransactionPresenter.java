package com.wallet.walkthedog.view.mail.transaction;

import com.wallet.walkthedog.dao.CodeDataDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.mail.MailContract;

public class TransactionPresenter implements  TransactionContract.TransactionPresenter{
    private TransactionContract.TransactionView view;
    private DataSource dataRepository;

    public  TransactionPresenter(DataSource dataRepository, TransactionContract.TransactionView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getWallet(String type) {
        dataRepository.getWallet(type,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getWalletInfo((String) obj,type);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void buyDog(BuyRequest request) {
        dataRepository.buyDog(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.bugSuccess((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void buyProp(BuyRequest request) {
        dataRepository.buyProp(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.bugSuccess((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void cancelSellDog(BuyRequest request) {
        dataRepository.cancelSellDog(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.cancelSellSuccess((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void cancelSellProp(BuyRequest request) {
        dataRepository.cancelSellProp(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.cancelSellSuccess((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getSysDataCode(String code) {
        dataRepository.getSysDataCode(code,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSysDataCodeSuccess((CodeDataDao) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
