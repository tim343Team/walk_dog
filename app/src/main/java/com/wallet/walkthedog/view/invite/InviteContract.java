package com.wallet.walkthedog.view.invite;

import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.view.props.ChoicePropsContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class InviteContract {
    interface InviteView extends Contract.BaseView<InviteContract.InvitePresenter> {

        void getFail(Integer code, String toastMessage);

        void getFriendpSuccess(List<FriendInfoDao> data);

        void sendFriendInvitedSuccess(String data);


    }

    interface InvitePresenter extends Contract.BasePresenter {
        void getFriendList(int pageNo);

        void sendFriendInvited(String friendEmail);

    }

}
