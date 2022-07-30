package com.wallet.walkthedog.view.props.fragment;

import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.props.ChoicePropsContract;

import java.util.List;

public class PropsPresenter implements PropsContract.PropsPresenter{
    private PropsContract.PropsView view;
    private DataSource dataRepository;

    public PropsPresenter(DataSource dataRepository,PropsContract.PropsView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getUserProp(int type, int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getUserProp(type,pageNo,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getPropSuccess((List<PropDao>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getRemoveProp(OpreationPropRequest request, int position) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getRemoveProp(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getRemovePropSuccess(position);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getAddProp(OpreationPropRequest request, int position) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getAddProp(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getAddPropSuccess(position);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getAddFail(code, toastMessage);
            }
        });
    }

    @Override
    public void useDogFood(OpreationPropRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.useDogFood(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.useDogFoodSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void cancelSellProp(BuyRequest request,int position) {
        view.displayLoadingPopup();//显示loading
        dataRepository.cancelSellProp(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.cancelSellSuccess((String) obj,position);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void openBox(OpreationPropRequest request, int position) {
        view.displayLoadingPopup();//显示loading
        dataRepository.openBox(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.openBoxSuccess((BoxDao) obj,position);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
            }
        });
    }
}
