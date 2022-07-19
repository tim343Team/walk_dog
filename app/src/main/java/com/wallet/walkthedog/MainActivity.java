package com.wallet.walkthedog;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startService(new Intent(StartActivity.this, MyTextService.class));
        if (!isTaskRoot()) {
            finish();
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new MyTask(),2000);
    }


    class MyTask extends TimerTask {
        @Override
        public void run() {
            //判断是否第一次进入应用
            if (SharedPrefsHelper.getInstance().getFirst()) {
//            SplashActivity.actionStart(this);
                EmailActivity.actionStart(MainActivity.this);
                SharedPrefsHelper.getInstance().saveFirst();
            }else {
                if(SharedPrefsHelper.getInstance().getToken().isEmpty()){
                    EmailActivity.actionStart(MainActivity.this);
                }else {
                    HomeActivity.actionStart(MainActivity.this);
                }
            }
            finish();
        }
    }
}
