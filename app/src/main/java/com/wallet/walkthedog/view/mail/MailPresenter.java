package com.wallet.walkthedog.view.mail;

import com.wallet.walkthedog.dao.DogBoxDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.home.HomeContract;

import java.util.List;

public class MailPresenter implements  MailContract.MailPresenter{
    private MailContract.MailView view;
    private DataSource dataRepository;

    public  MailPresenter(DataSource dataRepository, MailContract. MailView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getDogList(MailRequest request, int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getDogList(request,pageNo,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getDogListSuccess((List<DogMailDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getPropList(MailRequest request, int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getPropList(request,pageNo,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getPropListSuccess((List<PropMailDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getDogDownBox() {
        dataRepository.getDogDownBox(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getBoxtSuccess((List<DogBoxDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getPropDownBox() {
        dataRepository.getPropDownBox(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getBoxtSuccess((List<DogBoxDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
