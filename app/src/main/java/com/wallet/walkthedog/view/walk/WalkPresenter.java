package com.wallet.walkthedog.view.walk;

import com.wallet.walkthedog.dao.AwardDao;
import com.wallet.walkthedog.dao.InvitedFriendDao;
import com.wallet.walkthedog.dao.request.AwardRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.email.EmailContract;

import java.util.List;

public class WalkPresenter implements WalkContract.WalkPresenter{
    private WalkContract.WalkView view;
    private DataSource dataRepository;

    public WalkPresenter(DataSource dataRepository,WalkContract.WalkView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
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

    @Override
    public void getAwardPage(AwardRequest request) {
        dataRepository.getAwardPage(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getAwardPageSuccess((List<AwardDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getAward(AwardRequest request,int position) {
        view.displayLoadingPopup();
        dataRepository.getAward(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getAwardSuccess((String) obj,position);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getAwardFail(code, toastMessage);
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
