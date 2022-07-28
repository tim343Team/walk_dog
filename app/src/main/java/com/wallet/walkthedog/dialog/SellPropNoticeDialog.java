package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class SellPropNoticeDialog extends BaseDialogFragment {

    @OnClick(R.id.txt_confirm)
    void confirm() {
        dismiss();
    }

    @OnClick({R.id.txt_cancle,R.id.back})
    void cancle() {
        dismiss();
    }

    public static SellPropNoticeDialog newInstance() {
        SellPropNoticeDialog fragment = new SellPropNoticeDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sell_prop_notice;
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
}
