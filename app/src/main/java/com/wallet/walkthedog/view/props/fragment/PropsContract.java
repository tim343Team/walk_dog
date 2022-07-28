package com.wallet.walkthedog.view.props.fragment;

import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.view.props.ChoicePropsContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class PropsContract {

    interface PropsView extends Contract.BaseView<PropsContract.PropsPresenter> {
        void getFail(Integer code, String toastMessage);

        void getPropSuccess(List<PropDao> data);
    }

    interface PropsPresenter extends Contract.BasePresenter {
        void getUserProp(int type,int pageNo);
    }
}
