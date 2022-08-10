package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class DeleteCardDialog extends BaseDialogFragment {
    @OnClick(R.id.txt_cancle)
    void cancle(){
        dismiss();
    }

    @OnClick(R.id.txt_confirm)
    void confirm(){
        callback.callback();
    }

    public static DeleteCardDialog newInstance() {
        DeleteCardDialog fragment = new DeleteCardDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_delete_card;
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
