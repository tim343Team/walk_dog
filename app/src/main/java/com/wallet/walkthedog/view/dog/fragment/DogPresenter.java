package com.wallet.walkthedog.view.dog.fragment;

import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.props.fragment.PropsContract;

import java.util.List;

public class DogPresenter implements DogContract.DogPresenter {
    private DogContract.DogView view;
    private DataSource dataRepository;

    public DogPresenter(DataSource dataRepository, DogContract.DogView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getUserDog(int type, int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getUserDog(type, pageNo, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                if (type == 1)
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
        dataRepository.useDog(dogId, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.useDogSuccess((String) obj, dogId);//接受RemoteDataSource里sendMailboxCode方法的返回
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
        dataRepository.removeDog(dogId, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.removeDogSuccess((String) obj,dogId);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void cancelSellDog(BuyRequest request, int position) {
        dataRepository.cancelSellDog(request, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.cancelSellSuccess((String) obj, position);//接受RemoteDataSource里sendMailboxCode方法的返回
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
    public void getFeedDog(String dogId,DogInfoDao mDefultDogInfo) {
        dataRepository.getFeedDog(dogId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getFeedDogInfo((FeedDogFoodDao) obj,mDefultDogInfo);//接受RemoteDataSource里sendMailboxCode方法的返回
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
    public void buyShopDogFood(int dogFoodId, int number,String password) {
        view.displayLoadingPopup();
        dataRepository.ShopDogFood(dogFoodId,number,password,new DataSource.DataCallback() {
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
}
