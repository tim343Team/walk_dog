package com.wallet.walkthedog.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.wallet.walkthedog.MainActivity;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.bus_event.GpsConnectTImeEvent;
import com.wallet.walkthedog.untils.GpsUtils;
import com.wallet.walkthedog.view.home.HomeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class GpsService extends Service {
    //声明IBinder接口的一个接口变量mBinder
    private static int foregroundId = 110;
    private Notification notification;
    private Timer timer = null;

    //    public final IBinder mBinder = new LocalBinder();
    private NotificationManager mNM;
    private GpsUtils gpsUtils;
    private long timeSecond;

    //LocalBinder是继承Binder的一个内部类
//    public class LocalBinder extends Binder {
//
//        public GpsService getService() {
//            return GpsService.this;
//        }
//    }

    @Override
    public void onCreate() {
        Log.e(getClass().getName(), "onCreate");
        timeSecond = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeSecond = timeSecond + 1;
                EventBus.getDefault().post(new GpsConnectTImeEvent(timeSecond));
                Log.e(getClass().getName(), timeSecond + "s");
            }
        }, 1000, 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(getClass().getName(), "onStartCommand");
        //启动通知栏提示
        String CHANNEL_ONE_ID = "com.primedu.cn";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID)
                .setTicker("Nature")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText("提示内容")
                .setContentIntent(pendingIntent)
                .getNotification();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(1, notification);
        //开始定位服务
        gpsUtils = new GpsUtils(this);
        gpsUtils.startLocation();
//        fillCodeView(Long.MAX_VALUE);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(getClass().getName(), "onDestroy");
        if (gpsUtils != null) {
            gpsUtils.stopLocation();
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        Log.e(getClass().getName(), "onBind");
//        gpsUtils = new GpsUtils(this);
//        gpsUtils.startLocation();
//        return mBinder;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        Log.e(getClass().getName(), "onUnbind");
//        if (gpsUtils != null) {
//            gpsUtils.stopLocation();
//        }
//        return super.onUnbind(intent);
//    }
}
