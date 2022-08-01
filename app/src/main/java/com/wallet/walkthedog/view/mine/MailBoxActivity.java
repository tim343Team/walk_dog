package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.login.LoginActivity;

import tim.com.libnetwork.base.BaseActivity;
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

    private void onCheckEmail() {
        tv_email.setVisibility(View.GONE);
        editText_0.setVisibility(View.VISIBLE);
        editText_0.setText("");
        editText.setText("");
        ToastUtils.shortToast(R.string.check_email_success);
    }

    private void onUpdateEmail() {
        ToastUtils.shortToast(R.string.update_email_success);
        //去登录页
        SharedPrefsHelper.getInstance().saveToken("");
        Intent intent = new Intent(MailBoxActivity.this, EmailActivity.class);
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
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> testRemoteData)  {
                        onCheckEmail();
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
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> testRemoteData) {
                       onUpdateEmail();
                    }
                });
    }


}
