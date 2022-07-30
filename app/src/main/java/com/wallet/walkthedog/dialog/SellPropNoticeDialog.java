package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class SellPropNoticeDialog extends BaseDialogFragment {
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_message)
    TextView txtMessage;

    private int titleId;
    private int messageId;

    @OnClick(R.id.txt_confirm)
    void confirm() {
        dismiss();
    }

    @OnClick({R.id.txt_cancle,R.id.back})
    void cancle() {
        dismiss();
    }

    public static SellPropNoticeDialog newInstance(int titleId,int messageId) {
        SellPropNoticeDialog fragment = new SellPropNoticeDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("titleId",titleId);
        bundle.putInt("messageId",messageId);
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
        Bundle bundle = getArguments();
        titleId  =  bundle.getInt("titleId");
        messageId  =  bundle.getInt("messageId");
        txtTitle.setText(titleId);
        txtMessage.setText(messageId);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }
}
