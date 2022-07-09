package com.wallet.walkthedog.view.mail;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.DogMailAdapter;
import com.wallet.walkthedog.adapter.PropMailAdapter;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.view.mail.transaction.TransactionPropActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseLazyFragment;

public class PropMailFragment  extends BaseLazyFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int page = 1;
    private PropMailAdapter adapter;
    private List<PropMailDao> data = new ArrayList<>();

    @OnClick(R.id.ll_price)
    void selectPrice(){

    }

    @OnClick(R.id.ll_variety)
    void selectVariety(){

    }

    public static PropMailFragment getInstance() {
        PropMailFragment fragment = new PropMailFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prop_mail;
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
            PropMailDao propDao = new PropMailDao();
            data.add(propDao);
        }
        GridLayoutManager manager=new GridLayoutManager(getmActivity(),2);
        recyclerView.setLayoutManager(manager);
        adapter = new PropMailAdapter(R.layout.adapter_prop_mail, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.OnclickListenerItem(new PropMailAdapter.OnclickListenerItem() {
            @Override
            public void click(PropMailDao item) {
                TransactionPropActivity.actionStart(getActivity());
            }
        });
        adapter.setEnableLoadMore(false);
    }

    private void loadMore() {
        page = page + 1;
    }
}
