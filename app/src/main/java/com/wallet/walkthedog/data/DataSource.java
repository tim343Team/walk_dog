package com.wallet.walkthedog.data;

import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;

public interface DataSource {

    interface DataCallback<T> {

        void onDataLoaded(T obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }

    void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback);

    void emailRegister(EmailRegisterRequest request, DataCallback dataCallback);

    void emailLogin(EmailLoginRequest request, DataCallback dataCallback);

    void passwordLogin(EmailLoginRequest request, DataCallback dataCallback);


}
