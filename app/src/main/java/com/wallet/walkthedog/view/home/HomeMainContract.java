package com.wallet.walkthedog.view.home;


import com.wallet.walkthedog.dao.InviteNoticeDao;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class HomeMainContract {
    interface HomeMainView extends Contract.BaseView<HomeMainContract.HomeMainPresenter> {

        void getFail(Integer code, String toastMessage);

        void ideaTogetherSuccessful(String data,int status);

        void getNewTogethersSuccessful(List<InviteNoticeDao> noticeDaoList);

    }

    interface HomeMainPresenter extends Contract.BasePresenter {
        void ideaTogether(String togetherId,int status);//1同意 3拒绝 4取消邀请

        void getNewTogethersUrl();//1同意 3拒绝 4取消邀请
    }
}
