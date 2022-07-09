package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class MoreOperationDialog extends BaseDialogFragment {
    @OnClick(R.id.txt_feeding)
    void feeding(){
        feedCallback.callback();
    }

    @OnClick(R.id.txt_replace)
    void replace(){
        replaceCallback.callback();
    }

    @OnClick(R.id.txt_train)
    void train(){
        trainCallback.callback();
    }

    @OnClick(R.id.back)
    void back(){
        dismiss();
    }

    public static MoreOperationDialog newInstance() {
        MoreOperationDialog fragment = new MoreOperationDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_more_operation;
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

    private OperateReplaceCallback replaceCallback;

    public void setReplaceCallback(OperateReplaceCallback replaceCallback) {
        this.replaceCallback = replaceCallback;
    }

    public interface OperateReplaceCallback {
        void callback();
    }

    private OperateTrainCallback trainCallback;

    public void setTrainCallback(OperateTrainCallback trainCallback) {
        this.trainCallback = trainCallback;
    }

    public interface OperateTrainCallback {
        void callback();
    }
}
