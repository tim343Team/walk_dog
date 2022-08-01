package com.wallet.walkthedog.view.home;

import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.InvitedFriendDao;
import com.wallet.walkthedog.dao.TrainDao;
import com.wallet.walkthedog.dao.request.TrainRequest;
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
        dataRepository.feedDog(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.feedSuccessful((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
//                view.feedFail(1,(String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.feedFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getAllTrain() {
        view.displayLoadingPopup();
        dataRepository.getAllTrain(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.trainListSuccessful((List<TrainDao>) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void trainDog(TrainRequest request) {
        dataRepository.trainDog(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.trainSuccessful((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void upDogLevel(String dogId) {
        dataRepository.upDogLevel(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.updateSuccessful((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getShopDogFood() {
        dataRepository.getShopDogFood(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getShopDogFoodSuccessful((DogFoodDao) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void buyShopDogFood(int dogFoodId, int number) {
        view.displayLoadingPopup();
        dataRepository.ShopDogFood(dogFoodId,number,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.buyShopDogFoodSuccessful((String) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getWalkTheDogFriend() {
        dataRepository.getWalkTheDogFriend(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.getWalkTheDogFriendSuccessful((InvitedFriendDao) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
//                view.getFail(code, toastMessage);
            }
        });
    }

}
