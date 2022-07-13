package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseActivity;

public class MyAssetActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_asset;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.layout_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAssetActivity.this,CollectionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.layout_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAssetActivity.this,TransferActivity.class);
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
