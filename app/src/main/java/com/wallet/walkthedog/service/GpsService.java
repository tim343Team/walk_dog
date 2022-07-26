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
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.GpsConnectTImeEvent;
import com.wallet.walkthedog.bus_event.GpsLocationEvent;
import com.wallet.walkthedog.bus_event.GpsSateliteEvent;
import com.wallet.walkthedog.bus_event.GpsStopEvent;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.untils.GpsUtils;
import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.props.ChoicePropsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class GpsService extends Service implements WalkContract.WalkView {
    //声明IBinder接口的一个接口变量mBinder
    private WalkContract.WalkPresenter presenter;
    private int notifyId;
    private Notification notification;
    private Timer timer = null;
    private NotificationManager mNM;
    private GpsUtils gpsUtils;
    private long timeSecond;
    private String dogId;
    private boolean gpsEnable = false;
    private boolean isFirst = true;
    private double lan;//维度
    private double lon;//经度
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
        isFirst = true;
        presenter = new WalkPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        EventBus.getDefault().register(this);
        timeSecond = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(getClass().getName(), "onStartCommand");
        //获取doigId
        dogId = intent.getStringExtra("dogId");
        //启动通知栏提示
        String CHANNEL_ONE_ID = "com.walkdog.cn";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notifyId = (int) System.currentTimeMillis();
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            notification = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID)
                    .setTicker("Nature")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getResources().getString(R.string.notification_title))
                    .setContentText("提示内容")
                    .setContentIntent(pendingIntent)
                    .getNotification();
            startForeground(notifyId, notification);
        }
        //开始定位服务
        gpsUtils = new GpsUtils(this);
        gpsUtils.startLocation();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeSecond = timeSecond + 1;
                EventBus.getDefault().post(new GpsConnectTImeEvent(timeSecond));
                Log.e(getClass().getName(), timeSecond + "s");
            }
        }, 1000, 1000);
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            stopForeground(notifyId);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getName(), "onBind");
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsLocation(GpsLocationEvent location) {
        if (gpsEnable) {
            lon = location.getLongitude();
            lan = location.getLatitude();
            if (isFirst) {
                isFirst = false;
                //开始遛狗
                presenter.startWalkDog(new SwitchWalkRequest(dogId,String.valueOf(lan),String.valueOf(lon)));
            }
            //TODO 发送数据
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsSatelite(GpsSateliteEvent message) {
        if (message.getConnSatellite() == 0) {
            gpsEnable = false;
        } else {
            gpsEnable = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsStop(GpsStopEvent location) {
        Log.e(getClass().getName(), "stopSelf");
        //停止遛狗接口
        presenter.stopWalkDog(new SwitchWalkRequest(dogId,String.valueOf(lan),String.valueOf(lon)));
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void stopSuccess(String message) {
        //关闭service
        stopSelf();
    }

    @Override
    public void setPresenter(WalkContract.WalkPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void hideLoadingPopup() {

    }

    @Override
    public void displayLoadingPopup() {

    }

}
