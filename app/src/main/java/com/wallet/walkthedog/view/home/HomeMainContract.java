package com.wallet.walkthedog.view.home;


import tim.com.libnetwork.base.Contract;

public class HomeMainContract {
    interface HomeMainView extends Contract.BaseView<HomeMainContract.HomeMainPresenter> {

        void getFail(Integer code, String toastMessage);

        void ideaTogetherSuccessful(String data,int status);

    }

    interface HomeMainPresenter extends Contract.BasePresenter {
        void ideaTogether(String togetherId,int status);//1同意 3拒绝 4取消邀请
    }
}
