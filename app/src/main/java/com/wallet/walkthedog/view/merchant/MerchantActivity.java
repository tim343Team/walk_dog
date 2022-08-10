package com.wallet.walkthedog.view.merchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.card.CardVerifyActivity;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class MerchantActivity extends BaseActivity {

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MerchantActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_merchant;
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
