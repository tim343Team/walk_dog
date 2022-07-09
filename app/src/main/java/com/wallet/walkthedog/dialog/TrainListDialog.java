package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class TrainListDialog extends BaseDialogFragment {
    @OnClick({R.id.txt_sit, R.id.txt_lay, R.id.txt_come, R.id.txt_leave, R.id.txt_name, R.id.txt_walk, R.id.txt_back})
    void train(View v) {
        switch (v.getId()) {
            case R.id.txt_sit:
                callback.callback(0);
                break;
            case R.id.txt_lay:
                callback.callback(1);
                break;
            case R.id.txt_come:
                callback.callback(2);
                break;
            case R.id.txt_leave:
                callback.callback(3);
                break;
            case R.id.txt_name:
                callback.callback(4);
                break;
            case R.id.txt_walk:
                callback.callback(5);
                break;
            case R.id.txt_back:
                callback.callback(6);
                break;
        }
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

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(int status);
    }
}
