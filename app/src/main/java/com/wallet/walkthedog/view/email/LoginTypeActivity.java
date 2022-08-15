package com.wallet.walkthedog.view.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.login.SettingPassWordActivity;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class LoginTypeActivity extends BaseActivity implements EmailContract.EmailView{
    public static LoginTypeActivity instance = null;
    private EmailContract.EmailPresenter presenter;
    private String mailbox;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    @OnClick(R.id.ll_password_login)
    void gotoPasswordLogin(){
        SettingPassWordActivity.actionStart(this, Constant.LOGIN_MAIL_LOGIN,mailbox);
    }

    @OnClick(R.id.ll_code_login)
    void gotoCodeLogin(){
        presenter.sendMailboxCode(new SendMailboxCodeRequest(mailbox));//发起请求
    }

    public static void actionStart(Activity activity,String mailbox) {
        Intent intent = new Intent(activity, LoginTypeActivity.class);
        intent.putExtra("mailbox",mailbox);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_login_type;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
        presenter = new EmailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
    }

    @Override
    protected void obtainData() {
        mailbox=getIntent().getStringExtra("mailbox");
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(LoginTypeActivity.this,R.string.mailbox_send_error);
    }

    @Override
    public void getSuccessCodeData(String dao,String email) {
        //发送验证码接口的返回
        ToastUtils.shortToast(LoginTypeActivity.this,R.string.mailbox_send_succeed);
        SettingMobileCodeActivity.actionStart(this,email, Constant.LOGIN_MAIL_LOGIN);
    }

    @Override
    public void getSuccessMobileLogin(EmailLoginDao dao) {

    }

    @Override
    public void getSuccessInvited(String dao, String code) {

    }

    @Override
    public void setPresenter(EmailContract.EmailPresenter presenter) {
        this.presenter=presenter;
    }
}
