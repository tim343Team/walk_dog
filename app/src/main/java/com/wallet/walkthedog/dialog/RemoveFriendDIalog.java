package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.TrainDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class RemoveFriendDIalog extends BaseDialogFragment {
    @BindView(R.id.txt_notice)
    TextView txtNotice;

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    @OnClick(R.id.txt_remove)
    void remove() {
        callback.callback();
    }

    public static RemoveFriendDIalog newInstance(String friendName,String dogName) {
        RemoveFriendDIalog fragment = new RemoveFriendDIalog();
        Bundle bundle = new Bundle();
        bundle.putString("friendName",friendName);
        bundle.putString("dogName",dogName);
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
        Bundle bundle = getArguments();
        String friendName  =  bundle.getString("friendName");
        String dogName =  bundle.getString("dogName");
        txtNotice.setText(String.format(getString(R.string.remove_friend_notice), friendName,dogName));
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
