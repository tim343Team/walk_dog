package com.wallet.walkthedog.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.Calendar;

import tim.com.libnetwork.app.MyApplication;

public class RootApplication extends MyApplication {
    private static RootApplication instance;

    /**
     * 获取程序的Application对象
     */
    public static RootApplication getApp() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
        /*二维码识别*///
//        ZXingLibrary.initDisplayOpinion(this);
    }


    private void initApplication() {
        instance = this;
    }

    public static RootApplication getInstance() {
        return instance;
    }
}
