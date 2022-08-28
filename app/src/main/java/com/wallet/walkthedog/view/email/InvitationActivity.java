package com.wallet.walkthedog.view.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.untils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class InvitationActivity extends BaseActivity implements EmailContract.EmailView {
    public static InvitationActivity instance = null;
    @BindView(R.id.txt_title)
    TextView txtTile;
    @BindView(R.id.edit)
    EditText editText;

    private EmailContract.EmailPresenter presenter;
    private int status = 0;//0:输入邀請碼 1：輸入郵箱
    private String invitCode;

    @OnClick({R.id.view_bottom, R.id.img_ok})
    void gotocheckInvitation() {
        actionNext();
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

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, InvitationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invite_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
        presenter = new EmailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //执行对应的操作
                    actionNext();
                    return true;
                }
                return false;
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

    private void switchToStatus() {
        status = 1;
        invitCode = editText.getText().toString();
        txtTile.setText(R.string.mailbox_sign_up);
        editText.setHint(R.string.mailbox_address);
        editText.setText("");
    }

    public boolean checkoutEmail(String email) {
        boolean flag = false;
        try {
            String emailMatcher = "[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
            Pattern regex = Pattern.compile(emailMatcher);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(this, R.string.mailbox_send_error);
    }

    @Override
    public void getSuccessCodeData(String dao, String email) {
        //发送验证码接口的返回
        ToastUtils.shortToast(this, R.string.mailbox_send_succeed);
        SettingMobileCodeActivity.actionStart(this, email, Constant.LOGIN_MAIL_REGISTER,invitCode);
    }

    @Override
    public void getSuccessMobileLogin(EmailLoginDao dao) {

    }

    @Override
    public void getSuccessInvited(String dao, String code) {

    }

    @Override
    public void setPresenter(EmailContract.EmailPresenter presenter) {
        this.presenter = presenter;
    }

    private void actionNext(){
        if (status == 0) {
            if (editText.getText().toString().isEmpty()) {
                Toast.makeText(InvitationActivity.this, R.string.invitation_code_notice, Toast.LENGTH_SHORT).show();
                return;
            }
            //保存邀请码
            switchToStatus();
        } else if (status == 1) {
            if (editText.getText().toString().isEmpty()) {
                Toast.makeText(InvitationActivity.this, R.string.mailbox_address, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!checkoutEmail(editText.getText().toString())) {
                Toast.makeText(InvitationActivity.this, R.string.mailbox_address_notice, Toast.LENGTH_SHORT).show();
                return;
            }
            presenter.sendMailboxCode(new SendMailboxCodeRequest(editText.getText().toString()));//发起请求
        }
    }
}
