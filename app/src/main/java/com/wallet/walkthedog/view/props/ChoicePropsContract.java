package com.wallet.walkthedog.view.props;

import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.view.walk.WalkContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class ChoicePropsContract {
    interface ChoicePropsView extends Contract.BaseView<ChoicePropsContract.ChoicePropsPresenter> {

        void getFail(Integer code, String toastMessage);

        void getPropSuccess(List<PropDao> data);

        void getRemovePropSuccess(int dao);

        void getAddPropSuccess(int dao);

    }

    interface ChoicePropsPresenter extends Contract.BasePresenter {
        void getUserProp(int pageNo);

        void getRemoveProp(OpreationPropRequest request,int position);

        void getAddProp(OpreationPropRequest request,int position);
    }
}