package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class RemoveFriendDIalog extends BaseDialogFragment {

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    @OnClick(R.id.txt_remove)
    void remove() {
        callback.callback();
    }

    public static RemoveFriendDIalog newInstance() {
        RemoveFriendDIalog fragment = new RemoveFriendDIalog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_remove_friend;
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
        void callback();
    }
}
