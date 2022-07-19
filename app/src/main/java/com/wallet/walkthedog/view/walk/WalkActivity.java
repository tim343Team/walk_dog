package com.wallet.walkthedog.view.walk;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.WalkDogAdapter;
import com.wallet.walkthedog.bus_event.GpsLocationEvent;
import com.wallet.walkthedog.bus_event.GpsSateliteEvent;
import com.wallet.walkthedog.dao.EquipmentDao;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NoticeDialog;
import com.wallet.walkthedog.dialog.WalkNoticeDialog;
import com.wallet.walkthedog.service.GpsService;
import com.wallet.walkthedog.view.home.HomeActivity1;
import com.wallet.walkthedog.view.login.CreateActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.DateTimeUtil;

public class WalkActivity extends BaseActivity {
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.txt_speed)
    TextView txtSpeed;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_spend_2)
    TextView txtSpeed2;
    @BindView(R.id.txt_notice)
    TextView txtNotice;
    @BindView(R.id.img_gps_status)
    ImageView imgGpsStatus;
    @BindView(R.id.txt_gps_status)
    TextView txtGpsStatus;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public static final int LOCATION_CODE = 1000;
    public static final int OPEN_GPS_CODE = 1001;
    private GpsService gpsService;
    private WalkDogAdapter adapter;
    private List<EquipmentDao> data = new ArrayList<>();
    private boolean isWalking = false;
    private WalkNoticeDialog dialog;
    private boolean gpsEnable = false;
    private CountDownTimer timer;
    private long walkTime = 0;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.ll_status)
    void switchButton() {
        if (isWalking) {
            //停止遛狗
            unbindService(serviceConnection);
            isWalking = false;
            txtStatus.setText(R.string.walk_start);
            stopWalkTime();
        } else {
            //开始遛狗
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("GPS-Info", "申请权限");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_CODE);//自定义的code 随便填一个数
            } else {
                // 用户以授权定位信息
                Intent intentBind = new Intent(WalkActivity.this, GpsService.class);
                bindService(intentBind, serviceConnection, Context.BIND_AUTO_CREATE);
                isWalking = true;
                txtStatus.setText(R.string.walk_stop);
                saveWalkTime();
            }
        }
        //TODO 测试倒计时
//        showWarning("2","1.11");
//        showWarning("1","12.5");
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, WalkActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_walk;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initRv();
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
        if (gpsEnable) {
            txtSpeed.setText(location.getSpeed());
        } else {
            txtSpeed.setText("0.0");
        }
//        txtSpeed2.setText("Latitude:" + location.getLatitude() + "\n" + "Longitude:" + location.getLongitude() + "\n" + "Speed:" + location.getSpeed());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsSatelite(GpsSateliteEvent message) {
        if (message.getConnSatellite() < 3) {
            txtGpsStatus.setText(R.string.gps_week);
            gpsEnable = true;
        } else {
            txtGpsStatus.setText(R.string.gps);
            gpsEnable = false;
        }
//        txtSpeed.setText("showSatelliteList.size:" + message.getShowSatellite() + "\n" + "connSatelliteList.size:" + message.getConnSatellite());
    }

    void initRv() {
        //TODO 測試數據
        for (int i = 0; i < 12; i++) {
            EquipmentDao equipmentDao = new EquipmentDao();
            data.add(equipmentDao);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new WalkDogAdapter(R.layout.adapter_walk_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.OnclickListenerItem(new WalkDogAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                NormalDialog dialog = NormalDialog.newInstance(R.string.collect_notice, R.mipmap.icon_normal);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
            }
        });
        adapter.setEnableLoadMore(false);
    }

    void showWarning(String type, String speed) {
        if (!(dialog == null || !dialog.isShown())) {
            dialog.dismiss();
        }
        dialog = WalkNoticeDialog.newInstance(type, speed);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new WalkNoticeDialog.OperateCallback() {
            @Override
            public void callback() {
                dialog.dismiss();
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getClass().getName(), "onServiceConnected");
            gpsService = ((GpsService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getClass().getName(), "onServiceDisconnected");
            gpsService = null;
        }
    };

    private void stopWalkTime() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void saveWalkTime() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                walkTime = walkTime + 1;
                txtTime.setText(walkTime + "");
            }

            @Override
            public void onFinish() {
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
    }
}
