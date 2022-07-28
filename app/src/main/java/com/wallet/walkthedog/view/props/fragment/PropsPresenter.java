package com.wallet.walkthedog.view.props.fragment;

import com.wallet.walkthedog.dao.PropDao;
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
}
