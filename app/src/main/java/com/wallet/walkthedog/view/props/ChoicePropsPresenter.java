package com.wallet.walkthedog.view.props;

import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.view.walk.WalkContract;

import java.util.List;

public class ChoicePropsPresenter implements ChoicePropsContract.ChoicePropsPresenter{
    private ChoicePropsContract.ChoicePropsView view;
    private DataSource dataRepository;

    public ChoicePropsPresenter(DataSource dataRepository,ChoicePropsContract.ChoicePropsView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void getUserProp(int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getUserProp(pageNo,new DataSource.DataCallback() {
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
    public void getDogProp(String dogId,int pageNo) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getDogProp(dogId,pageNo,new DataSource.DataCallback() {
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
    public void getRemoveProp(OpreationPropRequest request,int position) {
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
    public void getAddProp(OpreationPropRequest request,int position) {
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
                view.getFail(code, toastMessage);
            }
        });
    }

    @Override
    public void getPropDetailInfo(OpreationPropRequest request) {
        view.displayLoadingPopup();//显示loading
        dataRepository.getPropDetailInfo(request,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getPropDetailSuccess((PropDetailDao) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getFail(code, toastMessage);
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
}
