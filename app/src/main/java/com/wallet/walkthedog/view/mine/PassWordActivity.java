package com.wallet.walkthedog.view.mine;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseActivity;

public class PassWordActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
