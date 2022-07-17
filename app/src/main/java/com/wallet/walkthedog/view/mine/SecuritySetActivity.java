package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.login.SettingPassWordActivity;

import tim.com.libnetwork.base.BaseActivity;

public class SecuritySetActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_security_settings;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.layout_mnemonic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.layout_mailbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecuritySetActivity.this, MailBoxActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_pw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecuritySetActivity.this, PassWordActivity.class);
                startActivity(intent);
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
}
