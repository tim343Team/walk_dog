package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class SellPropDialog extends BaseDialogFragment {
    @BindView(R.id.tv_price)
    EditText editText;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private int titleId;

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    @OnClick(R.id.txt_confirm)
    void add() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.enter_sell_price, Toast.LENGTH_SHORT).show();
            return;
        }
        callback.callback(editText.getText().toString());
    }

    public static SellPropDialog newInstance(int titleId) {
        SellPropDialog fragment = new SellPropDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("titleId", titleId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sell_prop;
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
        titleId = bundle.getInt("titleId");
        txtTitle.setText(titleId);
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
        void callback(String price);
    }
}
