package com.wallet.walkthedog.view.props;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.adapter.SellRecordAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.SellRecordDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class SellRecordActivity extends BaseActivity implements SellRecordContract.SellRecordView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int pageNo = 1;
    private SellRecordContract.SellRecordPresenter presenter;
    private SellRecordAdapter adapter;
    private List<SellRecordDao> data = new ArrayList<>();

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SellRecordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_sell_record;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new SellRecordPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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
        presenter.getShoppLog(pageNo);
    }

    @Override
    public void getSuccess(List<SellRecordDao> obj) {
        adapter.setEnableLoadMore(true);
        if (pageNo == 1) {
            data.clear();
            if (obj.size() == 0) {
                adapter.loadMoreEnd();
            } else {
                this.data.addAll(obj);
            }
        } else {
            if (obj.size() != 0) {
                this.data.addAll(obj);
            } else {
                adapter.loadMoreEnd();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new SellRecordAdapter(R.layout.adapter_sell_record, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
        presenter.getShoppLog(pageNo);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void setPresenter(SellRecordContract.SellRecordPresenter presenter) {
        this.presenter = presenter;
    }
}
