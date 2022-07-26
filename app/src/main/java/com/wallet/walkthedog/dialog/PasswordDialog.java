package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.PasswordView;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class PasswordDialog extends BaseDialogFragment {
    @BindView(R.id.password_edit)
    PasswordView passwordView;

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    public static PasswordDialog newInstance() {
        PasswordDialog fragment = new PasswordDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_password;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        passwordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                if (content.length() == 6) {
                    callback.callback(content);
//                    String signPassword = SharedPrefsHelper.getInstance().getSignPassword();
//                    if(signPassword.equals(content)){
//                        //密码正确
//                        callback.callback();
//                    }else {
//                        //密码错误
//                        callbackError.callback();
//                        passwordView.setText("");
//                        ToastUtils.shortToast(R.string.password_error);
//                    }
                }
            }
        });
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
        void callback(String password);
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
