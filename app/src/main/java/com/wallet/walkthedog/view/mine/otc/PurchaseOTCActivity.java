package com.wallet.walkthedog.view.mine.otc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseActivity;

public class PurchaseOTCActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_purchase_otc;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PurchaseOTCActivity.this, PurchaseDetailActivity.class);
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
