package com.wallet.walkthedog.view.card;

import com.wallet.walkthedog.dao.CardInfoDao;
import com.wallet.walkthedog.dao.request.CardEditRequset;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.dog.DogDetailContract;

import java.util.List;

public class CardPresenter implements CardContract.CardPresenter{
    private CardContract.CardView view;
    private DataSource dataRepository;

    public CardPresenter(DataSource dataRepository,CardContract.CardView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getBankAccount() {
        view.displayLoadingPopup();//显示loading
        dataRepository.getBankAccount(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getBankAccountSuccess((List<CardInfoDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getApproveBank(CardEditRequset requset) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getApproveBank(requset,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getEditSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getApproveSwift(CardEditRequset requset) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getApproveSwift(requset,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getEditSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
