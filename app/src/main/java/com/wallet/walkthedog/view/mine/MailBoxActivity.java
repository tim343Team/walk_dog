package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wallet.walkthedog.MainActivity;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.net.SimpleCallBack;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.login.LoginActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.StringCallBack;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MailBoxActivity extends BaseActivity {

    TextView tv_email;
    EditText editText_0;
    EditText editText;
    TextView tv_submit;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_mail_box;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_email = findViewById(R.id.tv_email);
        editText_0 = findViewById(R.id.editText_0);
        editText = findViewById(R.id.editText);
        tv_submit = findViewById(R.id.tv_submit);
        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() != 6) {
                    ToastUtils.shortToast(R.string.please_enter_email_code);
                    return;
                }

                if (tv_email.getVisibility() == View.VISIBLE) {
                    checkEmail(editText.getText().toString());
                } else {
                    if (editText_0.getText().toString().isEmpty()) {
                        ToastUtils.shortToast(R.string.please_enter_email);
                        return;
                    }
                    updateEmail(editText_0.getText().toString(), editText.getText().toString());
                }
            }
        });
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private void onCheckEmail(int p) {
        tv_email.setVisibility(View.GONE);
        editText_0.setVisibility(View.VISIBLE);
        editText_0.setText("");
        editText.setText("");
        ToastUtils.shortToast(p);
    }

    private void onUpdateEmail() {
        ToastUtils.shortToast(R.string.update_email_success);
        //去登录页
        SharedPrefsHelper.getInstance().saveToken("");
        Intent intent = new Intent(MailBoxActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void checkEmail(String code) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getCheckEmailUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("checkCode", code)
                .build()
                .getCall()
                .enqueue(new SimpleCallBack<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        onCheckEmail(R.string.check_email_success);
                    }
                });
    }


    private void updateEmail(String email, String code) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getUpdateEmailUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("checkCode", code)
                .addParams("email", email)
                .build()
                .getCall()
                .enqueue(new SimpleCallBack<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        onUpdateEmail();
                    }
                });
    }


}
