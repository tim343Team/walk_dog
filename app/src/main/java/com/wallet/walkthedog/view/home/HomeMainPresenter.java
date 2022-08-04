package com.wallet.walkthedog.view.home;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.InviteNoticeDao;
import com.wallet.walkthedog.data.DataSource;

import java.util.List;

public class HomeMainPresenter implements HomeMainContract.HomeMainPresenter{
    private HomeMainContract.HomeMainView view;
    private DataSource dataRepository;

    public HomeMainPresenter(DataSource dataRepository,HomeMainContract.HomeMainView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void ideaTogether(String togetherId, int status) {
        view.displayLoadingPopup();//显示loading
        dataRepository.ideaTogether(togetherId,status,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.ideaTogetherSuccessful((String) obj,status);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getNewTogethersUrl() {
        dataRepository.getNewTogethersUrl(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.getNewTogethersSuccessful((List<InviteNoticeDao>) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
            }
        });
    }
}
