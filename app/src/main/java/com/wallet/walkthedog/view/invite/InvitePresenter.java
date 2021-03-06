package com.wallet.walkthedog.view.invite;

import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.props.ChoicePropsContract;

import java.util.List;

public class InvitePresenter  implements InviteContract.InvitePresenter{
    private InviteContract.InviteView view;
    private DataSource dataRepository;

    public InvitePresenter(DataSource dataRepository,InviteContract.InviteView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }


    @Override
    public void getFriendList(int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getFriendList(pageNo,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getFriendpSuccess((List<FriendInfoDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void sendFriendInvited(String friendEmail) {
        view.displayLoadingPopup();//显示loading
        dataRepository.friendEmail(friendEmail,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.sendFriendInvitedSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
