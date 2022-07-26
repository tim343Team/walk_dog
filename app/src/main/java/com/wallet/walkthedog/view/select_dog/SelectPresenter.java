package com.wallet.walkthedog.view.select_dog;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.home.HomeContract;

import java.util.List;

public class SelectPresenter implements SelectContract.SelectPresenter{
    private SelectContract.SelectView view;
    private DataSource dataRepository;

    public SelectPresenter(DataSource dataRepository,SelectContract.SelectView view) {
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

    @Override
    public void useDog(String dogId) {
        view.displayLoadingPopup();//显示loading
        dataRepository.useDog(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.useDogSuccess((String) obj,dogId);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void removeDog(String dogId) {
        dataRepository.removeDog(dogId,new DataSource.DataCallback() {
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
