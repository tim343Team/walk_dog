package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class AvatarDialog extends BaseDialogFragment {

    @OnClick(R.id.txt_photograph)
    void photograph() {
        callback.callback(0);
        dismiss();
    }

    @OnClick(R.id.txt_album)
    void album() {
        callback.callback(1);
        dismiss();
    }

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    public static AvatarDialog newInstance() {
        AvatarDialog fragment = new AvatarDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_avatar;
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
        void callback(int type);
    }
}
