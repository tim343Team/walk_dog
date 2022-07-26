package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class FeedofRemindDialog extends BaseDialogFragment {
    private DogFoodDao data;

    @OnClick(R.id.txt_cancle)
    void back(){
        dismiss();
    }

    @OnClick(R.id.txt_search)
    void next(){
        callback.callback();
    }

    public static FeedofRemindDialog newInstance() {
        FeedofRemindDialog fragment = new FeedofRemindDialog();
        Bundle bundle = new Bundle();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_feed_remain;
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
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback();
    }
}
