package com.wallet.walkthedog.view.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.PasswordView;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.login.ImportActivity;
import com.wallet.walkthedog.view.login.SettingPassWordActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class SettingMobileCodeActivity extends BaseActivity {
    public static SettingMobileCodeActivity instance = null;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.ll_send)
    View llSend;
    @BindView(R.id.password_edit)
    PasswordView passwordView;

    @OnClick(R.id.ll_send)
    void resendCode() {
        fillCodeView(90 * 1000);
    }

    private CountDownTimer timer;
    private String type;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Context context, String type) {
        Intent intent = new Intent(context, SettingMobileCodeActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_setting_mobile;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
        fillCodeView(90 * 1000);
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
                    //TODO 判断郵箱验证码是否正确
                    SettingPassWordActivity.actionStart(SettingMobileCodeActivity.this, type);
                    finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void fillCodeView(long time) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        llSend.setVisibility(View.GONE);
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText((millisUntilFinished / 1000) + "S");
            }

            @Override
            public void onFinish() {
                txtTime.setVisibility(View.GONE);
                llSend.setVisibility(View.VISIBLE);
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
    }
}
