package com.wallet.walkthedog.view.props;

import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.view.walk.WalkContract;

import java.util.List;

import tim.com.libnetwork.base.Contract;

public class ChoicePropsContract {
    interface ChoicePropsView extends Contract.BaseView<ChoicePropsContract.ChoicePropsPresenter> {

        void getFail(Integer code, String toastMessage);

        void getAddFail(Integer code, String toastMessage);

        void getPropSuccess(List<PropDao> data);

        void getRemovePropSuccess(int dao);

        void getAddPropSuccess(int dao);

        void getPropDetailSuccess(PropDetailDao data);

        void useDogFoodSuccess(String data,int position);

        void sellProp(String data);

        void openBoxSuccess(BoxDao dao,int position);

        void cancelSellSuccess(String data,int position);
    }

    interface ChoicePropsPresenter extends Contract.BasePresenter {

        void getDogProp(String dogId,int pageNo);

        void getRemoveProp(OpreationPropRequest request,int position);

        void getAddProp(OpreationPropRequest request,int position);

        void getPropDetailInfo(OpreationPropRequest request);

        void useDogFood(OpreationPropRequest request,int position);

        void sellProp(SellRequest request);

        void openBox(OpreationPropRequest request,int position);

        void cancelSellProp(BuyRequest request, int position);

    }
}
