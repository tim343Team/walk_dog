package com.wallet.walkthedog.data;

import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;

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
    public void emailRegister(EmailRegisterRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailRegister(request,dataCallback);
        else mRemoteDataSource.emailRegister(request,dataCallback);
    }

    @Override
    public void emailLogin(EmailLoginRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailLogin(request,dataCallback);
        else mRemoteDataSource.emailLogin(request,dataCallback);
    }
}
