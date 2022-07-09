package com.wallet.walkthedog.view.rental;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.RentDogAdapter;
import com.wallet.walkthedog.dao.RentDogDao;
import com.wallet.walkthedog.view.rental.transaction.TrasactionDogActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragment;

public class RentalFragment extends BaseTransFragment{
    public static final String TAG = RentalFragment.class.getSimpleName();

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int page = 1;
    private RentDogAdapter adapter;
    private List<RentDogDao> data = new ArrayList<>();
    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rental;
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
            RentDogDao rentDogDao = new RentDogDao();
            data.add(rentDogDao);
        }
        GridLayoutManager manager=new GridLayoutManager(getmActivity(),2);
        recyclerView.setLayoutManager(manager);
        adapter = new RentDogAdapter(R.layout.adapter_rental_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.OnclickListenerItem(new RentDogAdapter.OnclickListenerItem() {
            @Override
            public void click(RentDogDao item) {
                TrasactionDogActivity.actionStart(getActivity());
            }
        });
        adapter.setEnableLoadMore(false);
    }

    private void loadMore() {
        page = page + 1;
    }

}
