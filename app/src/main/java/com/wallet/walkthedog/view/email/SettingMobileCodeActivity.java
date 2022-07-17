package com.wallet.walkthedog.view.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.custom_view.PasswordView;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.login.ImportActivity;
import com.wallet.walkthedog.view.login.SettingPassWordActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class SettingMobileCodeActivity extends BaseActivity  implements EmailContract.EmailView{
    public static SettingMobileCodeActivity instance = null;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_mailbox)
    TextView txtMailbox;
    @BindView(R.id.ll_send)
    View llSend;
    @BindView(R.id.password_edit)
    PasswordView passwordView;

    @OnClick(R.id.ll_send)
    void resendCode() {
        presenter.sendMailboxCode(new SendMailboxCodeRequest(email));//发起请求
    }

    @OnClick(R.id.txt_cancle)
    void clear() {
        passwordView.setText("");
    }

    private EmailContract.EmailPresenter presenter;
    private CountDownTimer timer;
    private String type;
    private String code;
    private String email;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Context context,String code,String email, String type) {
        Intent intent = new Intent(context, SettingMobileCodeActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("email", email);
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
        presenter = new EmailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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
                    //判断郵箱验证码是否正确
                    if(code.equals(content)){
                        if(type.equals(Constant.LOGIN_MAIL_LOGIN)){
                            //登錄
                            //TODO 調用驗證碼登錄
                            ToastUtils.shortToast("暫無驗證碼登錄接口");
                        }else if(type.equals(Constant.LOGIN_MAIL_REGISTER)){
                            //註冊
                            SettingPassWordActivity.actionStart(SettingMobileCodeActivity.this, type,email,code);
                            finish();
                        }
                    }else {
                        //验证码错误
                        NormalDialog dialog = NormalDialog.newInstance(R.string.mailbox_code_error, R.mipmap.icon_normal_no,R.color.color_E12828);
                        dialog.setTheme(R.style.PaddingScreen);
                        dialog.setGravity(Gravity.CENTER);
                        dialog.show(getSupportFragmentManager(), "edit");
                        passwordView.setText("");
                    }
                }
            }
        });
    }

    @Override
    protected void obtainData() {
        type = getIntent().getStringExtra("type");
        code = getIntent().getStringExtra("code");
        email = getIntent().getStringExtra("email");
        txtMailbox.setText(String.format(getString(R.string.mailbox_code_notice), email));
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
        txtTime.setVisibility(View.VISIBLE);
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

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(this,R.string.mailbox_send_error);
    }

    @Override
    public void getSuccessCodeData(String dao,String email) {
        //发送验证码接口的返回
        ToastUtils.shortToast(this,R.string.mailbox_send_succeed);
        fillCodeView(90 * 1000);
    }

    @Override
    public void setPresenter(EmailContract.EmailPresenter presenter) {
        this.presenter=presenter;
    }
}
