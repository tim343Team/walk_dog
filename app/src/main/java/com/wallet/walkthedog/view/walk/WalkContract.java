package com.wallet.walkthedog.view.walk;

import com.wallet.walkthedog.dao.AwardDao;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.InvitedFriendDao;
import com.wallet.walkthedog.dao.request.AwardRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.view.email.EmailContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class WalkContract {
    interface WalkView extends Contract.BaseView<WalkContract.WalkPresenter> {

        void getFail(Integer code, String toastMessage);

        void getAwardFail(Integer code, String toastMessage);

        void stopSuccess(String message);

        void getAwardPageSuccess(List<AwardDao> daos);

        void getAwardSuccess(String message,int position);

        void getWalkTheDogFriendSuccessful(InvitedFriendDao data);

    }

    interface WalkPresenter extends Contract.BasePresenter {

        void stopWalkDog(SwitchWalkRequest request);

        void getAwardPage(AwardRequest request);

        void getAward(AwardRequest request,int position);//领取奖励

        void getWalkTheDogFriend();//获取一起遛狗的好友
    }
}
