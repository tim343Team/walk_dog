package com.wallet.walkthedog.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.email.SettingMobileCodeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class InvitationActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView txtTile;
    @BindView(R.id.edit)
    EditText editText;

    private int status = 0;//0:输入邀請碼 1：輸入郵箱

    @OnClick({R.id.view_bottom, R.id.img_ok})
    void gotocheckInvitation() {
        if (status == 0) {
            if (editText.getText().toString().isEmpty()) {
                Toast.makeText(InvitationActivity.this, R.string.invitation_code_notice, Toast.LENGTH_SHORT).show();
                return;
            }
            //TODO 調用接口驗證邀請碼
            switchToStatus();
        } else if (status == 1) {
            if(editText.getText().toString().isEmpty()){
                Toast.makeText(InvitationActivity.this,R.string.mailbox_address,Toast.LENGTH_SHORT).show();
                return;
            }
            if(!checkoutEmail(editText.getText().toString())){
                Toast.makeText(InvitationActivity.this,R.string.mailbox_address_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            SettingMobileCodeActivity.actionStart(this, Constant.LOGIN_MAIL_REGISTER);
            finish();
        }
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

    private void switchToStatus(){
        status=1;
        txtTile.setText(R.string.mailbox_title);
        editText.setHint(R.string.mailbox_address);
        editText.setText("");
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
}
