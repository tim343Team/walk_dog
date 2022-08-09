package com.wallet.walkthedog.view.props;

import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.SellRecordDao;
import com.wallet.walkthedog.data.DataSource;

import java.util.List;

public class SellRecordPresenter implements SellRecordContract.SellRecordPresenter{
    private SellRecordContract.SellRecordView view;
    private DataSource dataRepository;

    public SellRecordPresenter(DataSource dataRepository,SellRecordContract.SellRecordView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getShoppLog(int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getShoppLog(pageNo,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getSuccess((List<SellRecordDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
