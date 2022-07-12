package com.wallet.walkthedog.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.PasswordView;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.email.SettingMobileCodeActivity;
import com.wallet.walkthedog.view.home.HomeActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class SettingPassWordActivity extends BaseActivity {
    @BindView(R.id.password_edit)
    PasswordView passwordView;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_match)
    TextView txtMatch;

    private String type;//1:导入助记词 2：创建助记词 3:邮箱登录 4:郵箱註冊
    private String enterPassword;
    private String password;
    private int status = 0;//0:输入密码 1：确认密码

    @OnClick(R.id.txt_cancle)
    void clearText() {
        passwordView.setText("");
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, String type) {
        Intent intent = new Intent(activity, SettingPassWordActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_setting_pass;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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
                if (content.isEmpty()) {
                    return;
                }
                if (content.length() == 6) {
                    if (status == 0) {
                        password = content;
                        status = 1;
                        passwordView.setText("");
                        txtTitle.setText(getResources().getString(R.string.enter_your_password));
                    } else if (status == 1) {
                        if (content.equals(password)) {
                            //两次密码相同.保存密码，进入主页面
                            //保存密碼
                            //設置登陸狀態
                            SharedPrefsHelper.getInstance().saveSignPassword(password);
                            SharedPrefsHelper.getInstance().setLogin();
                            txtMatch.setVisibility(View.GONE);
                            if (ImportActivity.instance != null) {
                                ImportActivity.instance.finish();
                            }
                            if (LoginActivity.instance != null) {
                                LoginActivity.instance.finish();
                            }
                            if (CreateActivity.instance != null) {
                                CreateActivity.instance.finish();
                            }
                            if (SettingMobileCodeActivity.instance != null) {
                                SettingMobileCodeActivity.instance.finish();
                            }
                            if (EmailActivity.instance != null) {
                                EmailActivity.instance.finish();
                            }
                            HomeActivity.actionStart(SettingPassWordActivity.this);
                        } else {
                            //两次密码不相符
                            txtMatch.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void obtainData() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }
}
