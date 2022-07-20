package com.wallet.walkthedog.view.home;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.bus_event.GpsLocationEvent;
import com.wallet.walkthedog.bus_event.GpsSateliteEvent;
import com.wallet.walkthedog.service.GpsService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class HomeActivity1 extends BaseActivity {
    private GpsService gpsService;
    public static final int LOCATION_CODE = 1000;
    public static final int OPEN_GPS_CODE = 1001;
    private boolean isBind = false;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.gps_info)
    TextView infoTextView;
    @BindView(R.id.open_gps)
    TextView textOpenGps;

    @OnClick(R.id.open_gps)
    void openGpsService() {
        if (isBind) {
//            unbindService(serviceConnection);
            isBind = false;
            textOpenGps.setText("打开GPS");
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e("GPS-Info", "申请权限");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_CODE);//自定义的code 随便填一个数
            } else {
                // 用户以授权定位信息
//                Intent intentBind = new Intent(HomeActivity1.this, GpsService.class);
//                bindService(intentBind, serviceConnection, Context.BIND_AUTO_CREATE);
//                isBind = true;
//                textOpenGps.setText("关闭GPS");
            }
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity1.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activiy_home_1;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsLocation(GpsLocationEvent location) {
        infoTextView.setText("Latitude:" + location.getLatitude() + "\n" + "Longitude:" + location.getLongitude() + "\n" + "Speed:" + location.getSpeed());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsSatelite(GpsSateliteEvent message) {
        textView.setText("showSatelliteList.size:" + message.getShowSatellite() + "\n" + "connSatelliteList.size:" + message.getConnSatellite());
    }

    //  授权回调。询问是否同意授权的时候，系统会弹出对话框，我们选择之后，会进行回调。在回调里面进行判断。
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    // 执形我们想要的操作
//                    Intent intentBind = new Intent(HomeActivity1.this, GpsService.class);
//                    bindService(intentBind, serviceConnection, Context.BIND_AUTO_CREATE);
//                    isBind = true;
//                    textOpenGps.setText("关闭GPS");
                } else {
                    Log.e("GPS-Info", "未授权定位权限");
                    // 用户授权定位信息
                }
            }
            case OPEN_GPS_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    // 执形我们想要的操作
//                    Intent intentBind = new Intent(HomeActivity1.this, GpsService.class);
//                    bindService(intentBind, serviceConnection, Context.BIND_AUTO_CREATE);
//                    isBind = true;
//                    textOpenGps.setText("关闭GPS");
                } else {
                    Log.e("GPS-Info", "未授权定位权限");
                    // 用户授权定位信息
                }
            }
        }
    }

//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.e(getClass().getName(), "onServiceConnected");
//            gpsService = ((GpsService.LocalBinder) service).getService();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.e(getClass().getName(), "onServiceDisconnected");
//            gpsService = null;
//        }
//    };
}
