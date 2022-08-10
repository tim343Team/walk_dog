package com.wallet.walkthedog.view.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.CardAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.CardInfoDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class CardActivity extends BaseActivity implements CardContract.CardView{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private CardAdapter adapter;
    private CardContract.CardPresenter presenter;
    private int pageNo = 1;
    private List<CardInfoDao> data = new ArrayList<>();

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.img_add)
    void add() {
        EditCardActivity.actionStart(this,0);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CardActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_layout_card;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new CardPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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

    private void initRecyclerView() {
        //TODO 测试数据
        for (int i = 0; i < 12; i++) {
            CardInfoDao mnemonicDao = new CardInfoDao();
            data.add(mnemonicDao);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new CardAdapter(R.layout.adapter_card, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.OnclickListenerItem(new CardAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {

            }
        });
        adapter.OnEditListenerItem(new CardAdapter.OnEditListenerItem() {
            @Override
            public void click(int position) {
                EditCardActivity.actionStart(CardActivity.this,1);
            }
        });
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
//        presenter.getUserDog(type, pageNo);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void setPresenter(CardContract.CardPresenter presenter) {
        this.presenter=presenter;
    }
}
