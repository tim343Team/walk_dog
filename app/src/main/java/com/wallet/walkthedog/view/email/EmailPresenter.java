package com.wallet.walkthedog.view.email;

import com.wallet.walkthedog.dao.SendMailboxCodeDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.data.DataSource;

import java.util.List;

public class EmailPresenter implements EmailContract.EmailPresenter{
    private EmailContract.EmailView view;
    private DataSource dataRepository;

    public EmailPresenter(DataSource dataRepository,EmailContract.EmailView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void sendMailboxCode(SendMailboxCodeRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.sendMailboxCode(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSuccessCodeData((String) obj,request.getEmail());//接受RemoteDataSource里sendMailboxCode方法的返回
//                view.getSuccessData((List<SendMailboxCodeDao>) obj);//接受RemoteDataSource里sendMailboxCode方法的返回
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
