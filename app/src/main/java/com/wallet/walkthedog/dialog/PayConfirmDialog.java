package com.wallet.walkthedog.dialog;

import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class PayConfirmDialog extends BaseDialogFragment {
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


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_pay_confim;
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
