package com.wallet.walkthedog.view.props;

import com.wallet.walkthedog.dao.SellRecordDao;


import java.util.List;

import tim.com.libnetwork.base.Contract;

public class SellRecordContract {
    interface SellRecordView extends Contract.BaseView<SellRecordContract.SellRecordPresenter> {

        void getFail(Integer code, String toastMessage);

        void getSuccess(List<SellRecordDao> obj);

    }

    interface SellRecordPresenter extends Contract.BasePresenter {

        void getShoppLog(int pageNo);

    }
}
