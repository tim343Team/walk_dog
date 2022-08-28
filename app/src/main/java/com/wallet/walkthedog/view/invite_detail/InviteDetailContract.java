package com.wallet.walkthedog.view.invite_detail;

import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dao.request.InviteRequest;

import tim.com.libnetwork.base.Contract;

public class InviteDetailContract {
    interface InviteDetailView extends Contract.BaseView<InviteDetailContract.InviteDetailPresenter> {

        void getFail(Integer code, String toastMessage);

        void getSuccess(DogInfoDao data);

        void setNoteSuccess(String data);

        void delSuccess(String data);

        void addTogetherSuccess(String data);

        void deleteTogetherSuccess(String data);

        void ideaTogetherSuccessful(String data,int status);

        void addTogetherFail(Integer code, String toastMessage);

    }

    interface InviteDetailPresenter extends Contract.BasePresenter {
        void getFriendDogDetail(String id);

        void setNote(FriendRequest request);

        void delFriend(FriendRequest request);

        void addTogether(InviteRequest request);

        void deleteTogether(String togetherId);

        void ideaTogether(String togetherId,int status);//1同意 3拒绝 4取消邀请

    }
}
