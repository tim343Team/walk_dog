package com.wallet.walkthedog.view.merchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.card.CardVerifyActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class MerchantApplyActivity extends BaseActivity {
    @BindView(R.id.edit_email)
    EditText tvEmail;

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if(!checkoutEmail(tvEmail.getText().toString())){
            Toast.makeText(MerchantApplyActivity.this,R.string.mailbox_address_notice,Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MerchantApplyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_merchant_apply;
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
