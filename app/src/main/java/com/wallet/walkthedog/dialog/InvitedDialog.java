package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class InvitedDialog extends BaseDialogFragment {

    @OnClick(R.id.back)
    void back(){
        dismiss();
    }

    @OnClick(R.id.txt_refuse)
    void refuse(){
        refuseCallback.callback();
    }

    @OnClick(R.id.txt_accept)
    void accept(){
        acceptCallback.callback();
    }

    public static InvitedDialog newInstance() {
        InvitedDialog fragment = new InvitedDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_invited;
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

    private OperateRefuseCallback refuseCallback;

    public void setRefuseCallback(OperateRefuseCallback refuseCallback) {
        this.refuseCallback = refuseCallback;
    }

    public interface OperateRefuseCallback {
        void callback();
    }

    private OperateAcceptCallback acceptCallback;

    public void setAcceptCallback(OperateAcceptCallback acceptCallback) {
        this.acceptCallback = acceptCallback;
    }

    public interface OperateAcceptCallback {
        void callback();
    }
}
