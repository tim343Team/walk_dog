package com.wallet.walkthedog.view.props.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.home.HomePresenter;
import com.wallet.walkthedog.view.mail.DogMailFragment;

import java.util.List;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseLazyFragment;

public class MyPropsFragment extends BaseLazyFragment implements PropsContract.PropsView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private PropsContract.PropsPresenter presenter;
    private int pageNo = 1;
    private int type = 1;


    public static MyPropsFragment getInstance() {
        MyPropsFragment fragment = new MyPropsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_props;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new PropsPresenter(Injection.provideTasksRepository(getmActivity()), this);//初始化presenter
        presenter.getUserProp(type,pageNo);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }

    private void initRecyclerView() {
//        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        adapter = new MyDogsAdapter(R.layout.adapter_my_props, data, SharedPrefsHelper.getInstance().getDogId());
//        adapter.bindToRecyclerView(recyclerView);
//        adapter.setEnableLoadMore(false);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getPropSuccess(List<PropDao> data) {

    }

    @Override
    public void setPresenter(PropsContract.PropsPresenter presenter) {
        this.presenter = presenter;
    }
}
