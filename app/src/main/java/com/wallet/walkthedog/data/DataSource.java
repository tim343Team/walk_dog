package com.wallet.walkthedog.data;

import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;

public interface DataSource {

    interface DataCallback<T> {

        void onDataLoaded(T obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }

    void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback);

    void emailCheckCode(SendMailboxCodeRequest request, DataCallback dataCallback);

    void emailRegister(EmailRegisterRequest request, DataCallback dataCallback);

    void emailLogin(EmailLoginRequest request, DataCallback dataCallback);

    void passwordLogin(EmailLoginRequest request, DataCallback dataCallback);

    void getUserDog(DataCallback dataCallback);

    void getDogInfo(String dogId,DataCallback dataCallback);

    void getUseDog(String dogId,DataCallback dataCallback);

    void getFeedDog(String dogId,DataCallback dataCallback);

    void feedDog(String dogId,DataCallback dataCallback);

    void getWallet(String type,DataCallback dataCallback);

    void getWalkTheDogFriend(DataCallback dataCallback);

    void startWalkDog(SwitchWalkRequest request,DataCallback dataCallback);

    void stopWalkDog(SwitchWalkRequest request,DataCallback dataCallback);

    void getUserProp(int pageNo,DataCallback dataCallback);

    void getDogProp(String dogId,int pageNo,DataCallback dataCallback);

    void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback);

    void getAddProp(OpreationPropRequest request,DataCallback dataCallback);

    void getPropDetailInfo(OpreationPropRequest request,DataCallback dataCallback);

    void useDogFood(OpreationPropRequest request,DataCallback dataCallback);


}
