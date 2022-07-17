package com.wallet.walkthedog.view.login;

import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.view.email.EmailContract;

import tim.com.libnetwork.base.Contract;

public class SettingPasswordContract {
    interface SettingPasswordView extends Contract.BaseView<SettingPasswordContract.SettingPasswordPresenter> {

        void getFail(Integer code, String toastMessage);

        void getSuccess(EmailLoginDao dao,String password);
    }

    interface SettingPasswordPresenter extends Contract.BasePresenter {
        void emailRegister(EmailRegisterRequest request,String password);

        void emailLogin(EmailLoginRequest request,String password);

        void passwordLogin(EmailLoginRequest request,String password);

    }
}
