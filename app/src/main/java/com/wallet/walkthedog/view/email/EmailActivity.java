package com.wallet.walkthedog.view.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.SendMailboxCodeDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.untils.ToastUtils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class EmailActivity extends BaseActivity implements EmailContract.EmailView {
    public static EmailActivity instance = null;
    private EmailContract.EmailPresenter presenter;
    @BindView(R.id.edit)
    EditText editText;

    @OnClick(R.id.view_bottom)
    void login(){
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(EmailActivity.this,R.string.mailbox_address,Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkoutEmail(editText.getText().toString())){
            Toast.makeText(EmailActivity.this,R.string.mailbox_address_notice,Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.sendMailboxCode(new SendMailboxCodeRequest(editText.getText().toString()));//发起请求
    }

    @OnClick(R.id.ll_edit)
    void clickEdit() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.txt_register)
    void register(){
        InvitationActivity.actionStart(this);
    }

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EmailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
        presenter = new EmailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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

    public boolean checkoutEmail(String email) {
        boolean flag=false;
        try {
            String emailMatcher="[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
            Pattern regex= Pattern.compile(emailMatcher);
            Matcher matcher=regex.matcher(email);
            flag=matcher.matches();
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(EmailActivity.this,R.string.mailbox_send_error);
    }

    @Override
    public void getSuccessCodeData(String dao,String email) {
        //发送验证码接口的返回
        ToastUtils.shortToast(EmailActivity.this,R.string.mailbox_send_succeed);
        SettingMobileCodeActivity.actionStart(this,dao,email, Constant.LOGIN_MAIL_LOGIN);
    }

    @Override
    public void setPresenter(EmailContract.EmailPresenter presenter) {
        this.presenter=presenter;
    }
}
