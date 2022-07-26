package com.wallet.walkthedog.view.rental.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dialog.BuyOrRentDogDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class TrasactionDogActivity extends BaseActivity {
    @BindView(R.id.txt_rent_time)
    TextView txtRentTime;
    @BindView(R.id.txt_operation)
    TextView txtOperation;
    @BindView(R.id.ll_character)
    View llCharacter;

    private int status = 0;//0：購買 1：取消售賣

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.txt_operation)
    void operation() {
        if (status == 0) {
//            BuyOrRentDogDialog dialog = BuyOrRentDogDialog.newInstance(2);
//            dialog.setTheme(R.style.PaddingScreen);
//            dialog.setGravity(Gravity.CENTER);
//            dialog.show(getSupportFragmentManager(), "edit");
//            dialog.setCallback(new BuyOrRentDogDialog.OperateCallback() {
//                @Override
//                public void callback() {
//                    PasswordDialog passwordDialog = PasswordDialog.newInstance();
//                    passwordDialog.setTheme(R.style.PaddingScreen);
//                    passwordDialog.setGravity(Gravity.CENTER);
//                    passwordDialog.show(getSupportFragmentManager(), "edit");
//                    passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
//                        @Override
//                        public void callback() {
//                            passwordDialog.dismiss();
//                            dialog.dismiss();
//                        }
//                    });
//                    passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
//                        @Override
//                        public void callback() {
//
//                        }
//                    });
//                }
//            });
        } else if (status == 1) {

        }
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TrasactionDogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_transaction_dog;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        txtRentTime.setVisibility(View.VISIBLE);
        llCharacter.setVisibility(View.VISIBLE);
        txtOperation.setText(R.string.rental);
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
