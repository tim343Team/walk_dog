package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class MoreDogOperationDialog extends BaseDialogFragment {
    @OnClick(R.id.txt_feeding)
    void feeding(){
        feedCallback.callback();
    }

    @OnClick(R.id.txt_sell)
    void selling(){
        sellCallback.callback();
    }

    @OnClick(R.id.back)
    void back(){
        dismiss();
    }

    public static MoreDogOperationDialog newInstance() {
        MoreDogOperationDialog fragment = new MoreDogOperationDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_more_dog_operation;
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

    private OperateFeedCallback feedCallback;

    public void setFeedCallback(OperateFeedCallback feedCallback) {
        this.feedCallback = feedCallback;
    }

    public interface OperateFeedCallback {
        void callback();
    }

    private OperateSellCallback sellCallback;

    public void setSellCallback(OperateSellCallback sellCallback) {
        this.sellCallback = sellCallback;
    }

    public interface OperateSellCallback {
        void callback();
    }
}
