package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.VersionInfoDao;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class VersionDialog extends BaseDialogFragment {

    @OnClick(R.id.txt_cancle)
    void back(){
        logoutCallback.callback();
    }

    @OnClick(R.id.txt_confirm)
    void search(){
        callback.callback();
    }

    public static VersionDialog newInstance() {
        VersionDialog fragment = new VersionDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_version;
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

//    @Override
//    public void dismiss() {
//        return;
//    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback();
    }

    private LogoutCallback logoutCallback;

    public void setCallback(LogoutCallback logoutCallback) {
        this.logoutCallback = logoutCallback;
    }

    public interface LogoutCallback {
        void callback();
    }
}
