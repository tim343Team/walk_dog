package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class NoticeDialog extends BaseDialogFragment {
    @BindView(R.id.txt_cancle)
    TextView txtCancle;
    @BindView(R.id.text_message_body)
    TextView txtMessage;

    @OnClick(R.id.txt_cancle)
    void back(){
        dismiss();
    }

    @OnClick(R.id.txt_search)
    void search(){
        callback.callback();
    }

    public static NoticeDialog newInstance(String message) {
        NoticeDialog fragment = new NoticeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message",message);
        bundle.putString("type","0");
        fragment.setArguments(bundle);
        return fragment;
    }

    public static NoticeDialog newInstance(String message,String type) {
        NoticeDialog fragment = new NoticeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message",message);
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_notice;
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
        String message = bundle.getString("message");
        String type = bundle.getString("type","0");
        txtMessage.setText(message);
        if(!type.equals("0")){
            txtCancle.setVisibility(View.GONE);
        }
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
