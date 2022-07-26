package com.wallet.walkthedog.view.mail.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dialog.PasswordDialog;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class TransactionPropActivity extends BaseActivity {

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.txt_operation)
    void operation() {
        PasswordDialog passwordDialog = PasswordDialog.newInstance();
        passwordDialog.setTheme(R.style.PaddingScreen);
        passwordDialog.setGravity(Gravity.CENTER);
        passwordDialog.show(getSupportFragmentManager(), "edit");
        passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
            @Override
            public void callback(String password) {
                passwordDialog.dismiss();
            }
        });
        passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
            @Override
            public void callback() {

            }
        });
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TransactionPropActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_transaction_prop;
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
