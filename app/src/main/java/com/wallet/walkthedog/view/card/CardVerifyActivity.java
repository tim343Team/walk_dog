package com.wallet.walkthedog.view.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.view.email.EmailActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class CardVerifyActivity extends BaseActivity {
    @BindView(R.id.tv_send)
    ShadowTextView tvSend;
    @BindView(R.id.edit_email)
    EditText tvEmail;
    @BindView(R.id.edit_code)
    EditText tvCode;

    private CountDownTimer timer;

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if(tvEmail.getText().toString().isEmpty()){
            Toast.makeText(CardVerifyActivity.this,R.string.mailbox_address,Toast.LENGTH_SHORT).show();
            return;
        }
        if(tvCode.getText().toString().isEmpty()){
            Toast.makeText(CardVerifyActivity.this,R.string.mailbox_code_notice_2,Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkoutEmail(tvEmail.getText().toString())){
            Toast.makeText(CardVerifyActivity.this,R.string.mailbox_address_notice,Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 添加接口
    }

    @OnClick(R.id.tv_send)
    void sendCode() {
        fillCodeView(90 * 1000);
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CardVerifyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_card_verify;
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
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSend.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvSend.setText(R.string.resend);
                tvSend.setEnabled(true);
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
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
