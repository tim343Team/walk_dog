package com.wallet.walkthedog.view.merchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.card.CardVerifyActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class MerchantActivity extends BaseActivity {
    @BindView(R.id.tv_confirm)
    ShadowTextView txtConfirm;
    @BindView(R.id.img_select)
    ImageView imgSelect;

    private int type;//0:申請  1：退出
    private boolean isSelect = false;

    @OnClick(R.id.img_select)
    void select() {
        if (isSelect) {
            imgSelect.setBackgroundResource(R.mipmap.icon_unselect_gray);
            isSelect = false;
        } else {
            imgSelect.setBackgroundResource(R.mipmap.icon_select_blue);
            isSelect = true;
        }
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if(!isSelect){
            ToastUtils.shortToast(R.string.merchant_agreement_notice);
            return;
        }
        MerchantAgreementActivity.actionStart(this, type);
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, int type) {
        Intent intent = new Intent(activity, MerchantActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_merchant;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            txtConfirm.setText(R.string.merchant_apply_imme);
        } else {
            txtConfirm.setText(R.string.sign_out);
        }
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
