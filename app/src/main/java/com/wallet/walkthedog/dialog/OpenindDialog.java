package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class OpenindDialog extends BaseDialogFragment {
    @BindView(R.id.img_prop)
    ImageView imgProp;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_name)
    TextView txtName;

    @OnClick(R.id.back)
    void back(){
        dismiss();
    }

    @OnClick(R.id.txt_next)
    void confirm(){
        callback.callback();
    }

    public static OpenindDialog newInstance() {
        OpenindDialog fragment = new OpenindDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_opening;
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

    OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback();
    }
}
