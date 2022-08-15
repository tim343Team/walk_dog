package com.wallet.walkthedog.view.email;

import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.SendMailboxCodeDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;

import tim.com.libnetwork.base.Contract;

public class EmailContract {
    interface EmailView extends Contract.BaseView<EmailContract.EmailPresenter> {

        void getFail(Integer code, String toastMessage);

        void getSuccessCodeData(String dao,String email);

        void getSuccessMobileLogin(EmailLoginDao dao);

        void getSuccessInvited(String dao,String code);

    }

    interface EmailPresenter extends Contract.BasePresenter {

        void checkInvitedCode(SendMailboxCodeRequest request);

        void sendMailboxCode(SendMailboxCodeRequest request);

        void emailCheckCode(SendMailboxCodeRequest request);
    }
}
