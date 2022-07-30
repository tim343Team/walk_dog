package com.wallet.walkthedog.view.props.fragment;

import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.view.props.ChoicePropsContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class PropsContract {

    interface PropsView extends Contract.BaseView<PropsContract.PropsPresenter> {
        void getFail(Integer code, String toastMessage);

        void getAddFail(Integer code, String toastMessage);

        void getPropSuccess(List<PropDao> data);

        void getRemovePropSuccess(int dao);

        void getAddPropSuccess(int dao);

        void useDogFoodSuccess(String data);

        void cancelSellSuccess(String data,int position);

        void openBoxSuccess(BoxDao dao, int position);

    }

    interface PropsPresenter extends Contract.BasePresenter {
        void getUserProp(int type,int pageNo);

        void getRemoveProp(OpreationPropRequest request,int position);

        void getAddProp(OpreationPropRequest request,int position);

        void useDogFood(OpreationPropRequest request);

        void cancelSellProp(BuyRequest request,int position);

        void openBox(OpreationPropRequest request,int position);
    }
}
