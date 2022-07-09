package com.wallet.walkthedog;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.Toast;

import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.login.LoginActivity;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bg)
    ImageView bg;
    public static MainActivity instance = null;
    /*权限请求Code*/
    private final static int PERMISSION_REQUEST_CODE = 1234;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };
    public boolean isShowLock = true;

    //权限反馈
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                /*PackageManager.PERMISSION_GRANTED  权限被许可*/
                /*PackageManager.PERMISSION_DENIED  没有权限；拒绝访问*/
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "无法使用相机！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "无法录制音频！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
//        RequestOptions options = new RequestOptions()
//                .centerCrop();
//        Glide.with(this).load(R.mipmap.bg_homepage).apply(options).into(bg);
        //判断是否第一次进入应用
//        if (SharedPrefsHelper.getInstance().getFirst()) {
//            SplashActivity.actionStart(this);
//            finish();
//        }else {
//            LoginActivity.actionStart(MainActivity.this);
//        }
//        LoginActivity.actionStart(MainActivity.this);
        HomeActivity.actionStart(MainActivity.this);
        finish();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED &&
//                checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED &&
//                checkSelfPermission(permissions[2]) != PackageManager.PERMISSION_GRANTED &&
//                checkSelfPermission(permissions[3]) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
//        }
    }

    @Override
    protected void loadData() {

    }

}
