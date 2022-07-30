package com.wallet.walkthedog.service;

import com.wallet.walkthedog.dao.CoordDao;
import com.wallet.walkthedog.dao.StartWalkDao;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.data.DataSource;

public class WalkPresenter implements WalkContract.WalkPresenter{
    private WalkContract.WalkView view;
    private DataSource dataRepository;

    public WalkPresenter(DataSource dataRepository, WalkContract.WalkView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void startWalkDog(SwitchWalkRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.startWalkDog(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.startSuccess((StartWalkDao) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void stopWalkDog(SwitchWalkRequest request) {
        dataRepository.stopWalkDog(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.stopSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void addCoord(SwitchWalkRequest request) {
        dataRepository.addCoord(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.coordSuccess((CoordDao) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
