package com.wallet.walkthedog.view.mail;

import android.os.Bundle;
import android.view.Gravity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.DogMailAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.view.mail.transaction.TransactionDogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseLazyFragment;

public class DogMailFragment  extends BaseLazyFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int page = 1;
    private DogMailAdapter adapter;
    private List<DogMailDao> data = new ArrayList<>();

    @OnClick(R.id.ll_price)
    void selectPrice(){

    }

    @OnClick(R.id.ll_variety)
    void selectVariety(){

    }

    public static DogMailFragment getInstance() {
        DogMailFragment fragment = new DogMailFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dog_mail;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecyclerView();
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
        //TODO 測試數據
        for (int i = 0; i < 10; i++) {
            DogMailDao propDao = new DogMailDao();
            data.add(propDao);
        }
        GridLayoutManager manager=new GridLayoutManager(getmActivity(),2);
        recyclerView.setLayoutManager(manager);
        adapter = new DogMailAdapter(R.layout.adapter_dog_mail, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.OnclickListenerItem(new DogMailAdapter.OnclickListenerItem() {
            @Override
            public void click(DogMailDao item) {
                TransactionDogActivity.actionStart(getActivity());
            }
        });
        adapter.setEnableLoadMore(false);
    }

    private void loadMore() {
        page = page + 1;
    }

}
