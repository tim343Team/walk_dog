package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FriendInfoDao;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class InvitedInforDialog extends BaseDialogFragment {
    private FriendInfoDao dogInfoDao;

    @OnClick(R.id.img_more)
    void more(){
        moreCallback.callback();
    }

    @OnClick(R.id.txt_invite)
    void invited(){
        callback.callback();
    }

    public static InvitedInforDialog newInstance(FriendInfoDao dogInfoDao) {
        InvitedInforDialog fragment = new InvitedInforDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dao",dogInfoDao);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_invited_info_2;
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
        dogInfoDao= (FriendInfoDao) bundle.getSerializable("dao");
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

    private MoreCallback moreCallback;

    public void setMoreCallback(MoreCallback moreCallback) {
        this.moreCallback = moreCallback;
    }

    public interface MoreCallback {
        void callback();
    }
}
