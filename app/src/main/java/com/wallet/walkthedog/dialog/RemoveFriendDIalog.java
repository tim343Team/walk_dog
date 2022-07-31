package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.TrainDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.utils.DateTimeUtil;

public class RemoveFriendDIalog extends BaseDialogFragment {
    @BindView(R.id.txt_notice)
    TextView txtNotice;
    @BindView(R.id.txt_number)
    TextView txtNumber;
    @BindView(R.id.txt_time)
    TextView txtTime;

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    @OnClick(R.id.txt_remove)
    void remove() {
        callback.callback();
    }

    public static RemoveFriendDIalog newInstance(String friendName,String dogName,String friendWalkTheDogCount,
                                                 String friendWalkTheDogTime) {
        RemoveFriendDIalog fragment = new RemoveFriendDIalog();
        Bundle bundle = new Bundle();
        bundle.putString("friendName",friendName);
        bundle.putString("dogName",dogName);
        bundle.putString("friendWalkTheDogCount",friendWalkTheDogCount);
        bundle.putString("friendWalkTheDogTime",friendWalkTheDogTime);
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
        String friendWalkTheDogCount =  bundle.getString("friendWalkTheDogCount");
        String friendWalkTheDogTime =  bundle.getString("friendWalkTheDogTime");
        txtNotice.setText(String.format(getString(R.string.remove_friend_notice), friendName,dogName));
        txtNumber.setText(String.format(getString(R.string._time), friendWalkTheDogCount));
        txtTime.setText( DateTimeUtil.second2Time(Long.valueOf(friendWalkTheDogTime)));
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
