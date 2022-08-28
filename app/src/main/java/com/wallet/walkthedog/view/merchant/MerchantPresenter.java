package com.wallet.walkthedog.view.merchant;

import com.wallet.walkthedog.dao.BusinessAuthDao;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.dao.request.MerchantRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.card.CardContract;

import java.util.List;

public class MerchantPresenter implements MerchantContract.MerchantPresenter{
    private MerchantContract.MerchantView view;
    private DataSource dataRepository;

    public MerchantPresenter(DataSource dataRepository,MerchantContract.MerchantView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void merchantStatus() {
        view.displayLoadingPopup();//显示loading
        dataRepository.merchantStatus(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.statusSuccess((MerchantStatusDao) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void cancleMerchant() {
        view.displayLoadingPopup();//显示loading
        dataRepository.cancleMerchant(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.cancleSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void applyMerchant(MerchantRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.applyMerchant(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.applySuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getApproveBusiness() {
        view.displayLoadingPopup();//显示loading
        dataRepository.getApproveBusiness(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.approveSuccess((List<BusinessAuthDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
