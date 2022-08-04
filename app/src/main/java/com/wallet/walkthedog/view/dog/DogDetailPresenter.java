package com.wallet.walkthedog.view.dog;

import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.props.ChoicePropsContract;

public class DogDetailPresenter implements DogDetailContract.DogDetailPresenter{
    private DogDetailContract.DogDetailView view;
    private DataSource dataRepository;

    public DogDetailPresenter(DataSource dataRepository,DogDetailContract.DogDetailView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void sellDog(SellRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.sellDog(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.sellDog((String) obj);
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
                view.getFeedDogInfo((FeedDogFoodDao) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
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

}
