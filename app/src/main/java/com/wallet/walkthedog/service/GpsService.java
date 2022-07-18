package com.wallet.walkthedog.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.wallet.walkthedog.untils.GpsUtils;

public class GpsService extends Service {
    //声明IBinder接口的一个接口变量mBinder
    public final IBinder mBinder = new LocalBinder();
    private NotificationManager mNM;
    private GpsUtils gpsUtils;

    //LocalBinder是继承Binder的一个内部类
    public class LocalBinder extends Binder {

        public GpsService getService() {
            return GpsService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.e(getClass().getName(), "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e(getClass().getName(), "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getName(), "onBind");
        gpsUtils = new GpsUtils(this);
        gpsUtils.startLocation();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(getClass().getName(), "onUnbind");
        if (gpsUtils != null) {
            gpsUtils.stopLocation();
        }
        return super.onUnbind(intent);
    }
}
