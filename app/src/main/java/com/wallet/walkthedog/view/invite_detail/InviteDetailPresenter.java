package com.wallet.walkthedog.view.invite_detail;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dao.request.InviteRequest;
import com.wallet.walkthedog.data.DataSource;

public class InviteDetailPresenter implements InviteDetailContract.InviteDetailPresenter{
    private InviteDetailContract.InviteDetailView view;
    private DataSource dataRepository;

    public InviteDetailPresenter(DataSource dataRepository,InviteDetailContract.InviteDetailView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getFriendDogDetail(String id) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getFriendDogDetail(id,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSuccess((DogInfoDao) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void setNote(FriendRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.setNote(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.setNoteSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void delFriend(FriendRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.delFriend(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.delSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void addTogether(InviteRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.addTogether(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.addTogetherSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.addTogetherFail(code, toastMessage);
            }
        });
    }

    @Override
    public void deleteTogether(String togetherId) {
        view.displayLoadingPopup();//显示loading
        dataRepository.deleteTogether(togetherId,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.deleteTogetherSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.addTogetherFail(code, toastMessage);
            }
        });
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
                view.addTogetherFail(code, toastMessage);
            }
        });
    }
}
