package com.wallet.walkthedog.view.walk;

import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.email.EmailContract;

public class WalkPresenter implements WalkContract.WalkPresenter{
    private WalkContract.WalkView view;
    private DataSource dataRepository;

    public WalkPresenter(DataSource dataRepository,WalkContract.WalkView view) {
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
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
