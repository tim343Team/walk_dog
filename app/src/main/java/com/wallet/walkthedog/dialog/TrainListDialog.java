package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.TrainListAdapter;
import com.wallet.walkthedog.adapter.WalkDogAdapter;
import com.wallet.walkthedog.dao.TrainDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class TrainListDialog extends BaseDialogFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private TrainListAdapter adapter;
    private List<TrainDao> data=new ArrayList<>();

    public void setData(List<TrainDao> data) {
        this.data = data;
    }

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    public static TrainListDialog newInstance() {
        TrainListDialog fragment = new TrainListDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_train_list;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        initRv();
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new TrainListAdapter(R.layout.adapter_train_item, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.OnclickListenerItem(new TrainListAdapter.OnclickListenerItem() {
            @Override
            public void click(TrainDao item) {
                callback.callback(item);
            }
        });
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(TrainDao ited);
    }
}
