package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class TrainDogDialog extends BaseDialogFragment {
    @BindView(R.id.img_train_type)
    ImageView imgTrainType;
    @BindView(R.id.txt_train_type)
    TextView txtTrainType;
    @BindView(R.id.txt_train_introduce)
    TextView txtTrainIntroduce;
    @BindView(R.id.txt_consume)
    TextView txtConsume;
    @BindView(R.id.txt_balance)
    TextView txtBalance;

    @OnClick(R.id.txt_back)
    void feeding(){
        callback.callback();
    }

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    public static TrainDogDialog newInstance(int status) {
        TrainDogDialog fragment = new TrainDogDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("status",status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_train_dog;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        int status = bundle.getInt("status");
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
        void callback();
    }
}
