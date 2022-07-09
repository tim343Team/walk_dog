package com.wallet.walkthedog.view.mail.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dialog.BuyOrRentDogDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class TransactionDogActivity extends BaseActivity {
    @BindView(R.id.txt_operation)
    TextView txtPeration;
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;

    private int status = 0;//0：購買 1：取消售賣

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.txt_operation)
    void operation() {
        if (status == 0) {
            BuyOrRentDogDialog dialog = BuyOrRentDogDialog.newInstance(1);
            dialog.setTheme(R.style.PaddingScreen);
            dialog.setGravity(Gravity.CENTER);
            dialog.show(getSupportFragmentManager(), "edit");
            dialog.setCallback(new BuyOrRentDogDialog.OperateCallback() {
                @Override
                public void callback() {
                    PasswordDialog passwordDialog = PasswordDialog.newInstance();
                    passwordDialog.setTheme(R.style.PaddingScreen);
                    passwordDialog.setGravity(Gravity.CENTER);
                    passwordDialog.show(getSupportFragmentManager(), "edit");
                    passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                        @Override
                        public void callback() {
                            passwordDialog.dismiss();
                            dialog.dismiss();
                        }
                    });
                    passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                        @Override
                        public void callback() {

                        }
                    });
                }
            });
        } else if (status == 1) {

        }
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TransactionDogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_transaction_dog;
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

    private int progressAll = 0;

    //设置进度条
    private void setProgress(double percentage) {
        int progress = (int) (progressAll * percentage);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
        params.width = progress;
        progressBar.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
        if (percentage < 0.2) {
            params2.leftMargin = 0;
        } else {
            params2.leftMargin = (int) (progress - progressAll * 0.18);
        }
        progressTxt.setLayoutParams(params2);
        progressTxt.setText(percentage * 100 + "%");
    }
}
