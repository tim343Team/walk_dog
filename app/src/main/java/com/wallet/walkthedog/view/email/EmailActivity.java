package com.wallet.walkthedog.view.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.home.HomeActivity;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class EmailActivity extends BaseActivity {

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
