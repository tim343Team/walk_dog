package com.wallet.walkthedog.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;

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
    public void getDogInfo(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getUseDog(String dogId, DataCallback dataCallback) {

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
    public void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback) {

    }

    @Override
    public void getAddProp(OpreationPropRequest request, DataCallback dataCallback) {

    }

}
