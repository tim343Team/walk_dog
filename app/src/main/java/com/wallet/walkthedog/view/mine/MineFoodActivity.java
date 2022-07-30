package com.wallet.walkthedog.view.mine;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseActivity;

public class MineFoodActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_mine_food;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.layout_sell).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
