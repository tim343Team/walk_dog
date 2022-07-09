package com.wallet.walkthedog.view.invite_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dialog.IdentityDialog;
import com.wallet.walkthedog.dialog.InviteMoreDialog;
import com.wallet.walkthedog.dialog.NicknameDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.dialog.RemoveFriendDIalog;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.login.ImportActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class InviteDetailActivity extends BaseActivity {
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;

    private String id;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.txt_remove)
    void remove() {
        RemoveFriendDIalog dialog = RemoveFriendDIalog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new RemoveFriendDIalog.OperateCallback() {
            @Override
            public void callback() {
                PasswordDialog passwordDialog = PasswordDialog.newInstance();
                passwordDialog.setTheme(R.style.PaddingScreen);
                passwordDialog.setGravity(Gravity.CENTER);
                passwordDialog.show(getSupportFragmentManager(), "edit");
                passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                    @Override
                    public void callback() {
                        dialog.dismiss();
                        passwordDialog.dismiss();
                    }
                });
                passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                    @Override
                    public void callback() {
                        ToastUtils.shortToast("错误");
                    }
                });
            }
        });
    }

    @OnClick(R.id.img_identity)
    void startIdentity() {
        IdentityDialog dialog = IdentityDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @OnClick(R.id.img_more)
    void more() {
        InviteMoreDialog moreDialog = InviteMoreDialog.newInstance();
        moreDialog.setTheme(R.style.PaddingScreen);
        moreDialog.setGravity(Gravity.CENTER);
        moreDialog.show(getSupportFragmentManager(), "edit");
        moreDialog.setCallback(new InviteMoreDialog.OperateCallback() {
            @Override
            public void callback() {
                NicknameDialog nicknameDialog = NicknameDialog.newInstance();
                nicknameDialog.setTheme(R.style.PaddingScreen);
                nicknameDialog.setGravity(Gravity.CENTER);
                nicknameDialog.show(getSupportFragmentManager(), "edit");
                nicknameDialog.setCallback(new NicknameDialog.OperateCallback() {
                    @Override
                    public void callback(String name) {
                        nicknameDialog.dismiss();
                    }
                });
                moreDialog.dismiss();
            }
        });
    }

    public static void actionStart(Activity activity,String id) {
        Intent intent = new Intent(activity, InviteDetailActivity.class);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invite_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
         id = getIntent().getStringExtra("id");
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

    //设置进度条
    private int progressAll = 0;
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
