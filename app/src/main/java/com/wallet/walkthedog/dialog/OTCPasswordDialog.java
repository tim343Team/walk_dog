package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.PasswordView;
import com.wallet.walkthedog.untils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class OTCPasswordDialog extends BaseDialogFragment {
    @BindView(R.id.password_edit)
    EditText passwordView;
    private CheckBox checkbox;

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    public static OTCPasswordDialog newInstance() {
        OTCPasswordDialog fragment = new OTCPasswordDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.otc_dialog_password;
    }

    @Override
    protected void prepareView(View view) {
        checkbox = view.findViewById(R.id.checkbox);
        view.findViewById(R.id.txt_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.txt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox.isChecked()) {
                    ToastUtils.shortToast(getString(R.string.please_confirm_received_payment));
                    return;
                }
                if (passwordView.getText().toString().isEmpty()) {
                    ToastUtils.shortToast(getString(R.string.please_enter_the_funding_password));
                    return;
                }
                if (callback != null) {
                    callback.callback(passwordView.getText().toString());
                }
            }
        });
    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dismissCallback != null) {
            dismissCallback.call();
        }
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private OperateCallback callback;
    private DismissCallback dismissCallback;

    public void setDismissCallback(DismissCallback dismissCallback) {
        this.dismissCallback = dismissCallback;
    }

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(String password);
    }

    public interface DismissCallback {
        void call();
    }

    private OperateErrorCallback callbackError;

    public void
    setCallback(OperateErrorCallback callbackError) {
        this.callbackError = callbackError;
    }

    public interface OperateErrorCallback {
        void callback();
    }
}
