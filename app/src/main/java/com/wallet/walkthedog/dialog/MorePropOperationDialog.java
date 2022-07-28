package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class MorePropOperationDialog extends BaseDialogFragment {
    @OnClick(R.id.txt_sell)
    void feeding(){
        feedCallback.sell();
    }

    @OnClick(R.id.back)
    void back(){
        dismiss();
    }

    public static MorePropOperationDialog newInstance() {
        MorePropOperationDialog fragment = new MorePropOperationDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_more_prop_operation;
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
        void sell();
    }
}
