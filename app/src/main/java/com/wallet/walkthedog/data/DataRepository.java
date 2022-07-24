package com.wallet.walkthedog.data;

import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.dao.request.TrainRequest;

public class DataRepository implements DataSource {
    private static DataRepository INSTANCE = null;
    private final DataSource mRemoteDataSource;
    private final DataSource mLocalDataSource;
    private boolean isLocal = false;

    private DataRepository(DataSource mRemoteDataSource, DataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static DataRepository getInstance(DataSource mRemoteDataSource, DataSource mLocalDataSource) {
        if (INSTANCE == null) INSTANCE = new DataRepository(mRemoteDataSource, mLocalDataSource);
        return INSTANCE;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    @Override
    public void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.sendMailboxCode(request,dataCallback);
        else mRemoteDataSource.sendMailboxCode(request,dataCallback);
    }

    @Override
    public void emailCheckCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailCheckCode(request,dataCallback);
        else mRemoteDataSource.emailCheckCode(request,dataCallback);
    }

    @Override
    public void emailRegister(EmailRegisterRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailRegister(request,dataCallback);
        else mRemoteDataSource.emailRegister(request,dataCallback);
    }

    @Override
    public void emailLogin(EmailLoginRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailLogin(request,dataCallback);
        else mRemoteDataSource.emailLogin(request,dataCallback);
    }

    @Override
    public void passwordLogin(EmailLoginRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.passwordLogin(request,dataCallback);
        else mRemoteDataSource.passwordLogin(request,dataCallback);
    }

    @Override
    public void getUserDog(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getUserDog(dataCallback);
        else mRemoteDataSource.getUserDog(dataCallback);
    }

    @Override
    public void getDogInfo(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getDogInfo(dogId,dataCallback);
        else mRemoteDataSource.getDogInfo(dogId,dataCallback);
    }

    @Override
    public void getUseDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getUseDog(dogId,dataCallback);
        else mRemoteDataSource.getUseDog(dogId,dataCallback);
    }

    @Override
    public void getFeedDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getFeedDog(dogId,dataCallback);
        else mRemoteDataSource.getFeedDog(dogId,dataCallback);
    }

    @Override
    public void feedDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.feedDog(dogId,dataCallback);
        else mRemoteDataSource.feedDog(dogId,dataCallback);
    }

    @Override
    public void getWallet(String type, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getWallet(type,dataCallback);
        else mRemoteDataSource.getWallet(type,dataCallback);
    }

    @Override
    public void getWalkTheDogFriend(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getWalkTheDogFriend(dataCallback);
        else mRemoteDataSource.getWalkTheDogFriend(dataCallback);
    }

    @Override
    public void startWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.startWalkDog(request,dataCallback);
        else mRemoteDataSource.startWalkDog(request,dataCallback);
    }

    @Override
    public void stopWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.stopWalkDog(request,dataCallback);
        else mRemoteDataSource.stopWalkDog(request,dataCallback);
    }

    @Override
    public void getUserProp(int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getUserProp(pageNo,dataCallback);
        else mRemoteDataSource.getUserProp(pageNo,dataCallback);
    }

    @Override
    public void getDogProp(String dogId,int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getDogProp(dogId,pageNo,dataCallback);
        else mRemoteDataSource.getDogProp(dogId,pageNo,dataCallback);
    }

    @Override
    public void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getRemoveProp(request,dataCallback);
        else mRemoteDataSource.getRemoveProp(request,dataCallback);
    }

    @Override
    public void getAddProp(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getAddProp(request,dataCallback);
        else mRemoteDataSource.getAddProp(request,dataCallback);
    }

    @Override
    public void getPropDetailInfo(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getPropDetailInfo(request,dataCallback);
        else mRemoteDataSource.getPropDetailInfo(request,dataCallback);
    }

    @Override
    public void useDogFood(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.useDogFood(request,dataCallback);
        else mRemoteDataSource.useDogFood(request,dataCallback);
    }

    @Override
    public void getAllTrain(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getAllTrain(dataCallback);
        else mRemoteDataSource.getAllTrain(dataCallback);
    }

    @Override
    public void trainDog(TrainRequest request,DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.trainDog(request,dataCallback);
        else mRemoteDataSource.trainDog(request,dataCallback);
    }

    @Override
    public void getFriendList(int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getFriendList(pageNo,dataCallback);
        else mRemoteDataSource.getFriendList(pageNo,dataCallback);
    }

    @Override
    public void friendEmail(String friendEmail, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.friendEmail(friendEmail,dataCallback);
        else mRemoteDataSource.friendEmail(friendEmail,dataCallback);
    }

}
