package com.wallet.walkthedog.view.login;

import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.email.EmailContract;

public class SettingPasswordPresenter implements SettingPasswordContract.SettingPasswordPresenter{
    private SettingPasswordContract.SettingPasswordView view;
    private DataSource dataRepository;

    public SettingPasswordPresenter(DataSource dataRepository,SettingPasswordContract.SettingPasswordView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void emailRegister(EmailRegisterRequest request,String password) {
        view.displayLoadingPopup();//显示loading
        dataRepository.emailRegister(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSuccess((EmailLoginDao) obj,password);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void emailLogin(EmailLoginRequest request,String password) {
        view.displayLoadingPopup();//显示loading
        dataRepository.emailLogin(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSuccess((EmailLoginDao) obj,password);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void passwordLogin(EmailLoginRequest request, String password) {
        view.displayLoadingPopup();//显示loading
        dataRepository.passwordLogin(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSuccess((EmailLoginDao) obj,password);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
