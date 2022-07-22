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
    public void getDogInfo(String dogId) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getDogInfo(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getCurrentDogInfo((DogInfoDao) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getUseDog(String dogId) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getUseDog(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
//                view.getMyDogSuccess((List<DogInfoDao>) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getWalkTheDogFriend(String dogId) {
        dataRepository.getWalkTheDogFriend(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
//                view.getMyDogSuccess((List<DogInfoDao>) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getFeedDog(String dogId) {
        dataRepository.getFeedDog(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getFeedDogInfo((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
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
    public void feedDog(String dogId) {
        view.displayLoadingPopup();
        dataRepository.feedDog(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.feedSuccessful((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.feedFail(code, toastMessage);
            }
        });
    }
}
