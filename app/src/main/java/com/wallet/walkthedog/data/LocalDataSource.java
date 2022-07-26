package com.wallet.walkthedog.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.dao.request.TrainRequest;

public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE;
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());

    public LocalDataSource(Context context) {
        this.context = context;
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback) {

    }

    @Override
    public void emailCheckCode(SendMailboxCodeRequest request, DataCallback dataCallback) {

    }

    @Override
    public void emailRegister(EmailRegisterRequest request, DataCallback dataCallback) {

    }

    @Override
    public void emailLogin(EmailLoginRequest request, DataCallback dataCallback) {

    }

    @Override
    public void passwordLogin(EmailLoginRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getUserDog(DataCallback dataCallback) {

    }

    @Override
    public void useDog(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void removeDog(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getDogInfo(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getUseDog(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getFeedDog(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void feedDog(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getWallet(String type, DataCallback dataCallback) {

    }

    @Override
    public void buyDog(BuyRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getWalkTheDogFriend(DataCallback dataCallback) {

    }

    @Override
    public void startWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {

    }

    @Override
    public void stopWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getUserProp(int pageNo, DataCallback dataCallback) {

    }

    @Override
    public void getDogProp(String dogId,int pageNo, DataCallback dataCallback) {

    }

    @Override
    public void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getAddProp(OpreationPropRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getPropDetailInfo(OpreationPropRequest request, DataCallback dataCallback) {

    }

    @Override
    public void useDogFood(OpreationPropRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getAllTrain(DataCallback dataCallback) {

    }

    @Override
    public void trainDog(TrainRequest request, DataCallback dataCallback) {

    }

    @Override
    public void upDogLevel(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getShopDogFood(DataCallback dataCallback) {

    }

    @Override
    public void getFriendList(int pageNo, DataCallback dataCallback) {

    }

    @Override
    public void friendEmail(String friendEmail, DataCallback dataCallback) {

    }

    @Override
    public void getFriendDogDetail(String id, DataCallback dataCallback) {

    }

    @Override
    public void setNote(FriendRequest request, DataCallback dataCallback) {

    }

    @Override
    public void delFriend(FriendRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getDogList(MailRequest request, int pageNo, DataCallback dataCallback) {

    }

    @Override
    public void getPropList(MailRequest request, int pageNo, DataCallback dataCallback) {

    }

}
