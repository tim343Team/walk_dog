package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.merchant.MerchantActivity;

import tim.com.libnetwork.base.BaseActivity;

public class AgreementActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_argeement;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        CheckBox checkBox = findViewById(R.id.checkbox);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()){
                    ToastUtils.shortToast(getString(R.string.please_agree_to_freeze_the_deposit));
                    return;
                }
                MerchantActivity.actionStart(AgreementActivity.this);
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
