package com.wallet.walkthedog.view.home;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.email.EmailContract;

import java.util.List;

public class HomePresenter implements HomeContract.HomePresenter{
    private HomeContract.HomeView view;
    private DataSource dataRepository;

    public HomePresenter(DataSource dataRepository,HomeContract.HomeView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getUserDog() {
        view.displayLoadingPopup();//显示loading
        dataRepository.getUserDog(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getMyDogSuccess((List<DogInfoDao>) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
