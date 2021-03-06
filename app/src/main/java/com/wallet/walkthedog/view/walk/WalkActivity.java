package com.wallet.walkthedog.view.walk;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.WalkDogAdapter;
import com.wallet.walkthedog.bus_event.GpsConnectTImeEvent;
import com.wallet.walkthedog.bus_event.GpsLocationEvent;
import com.wallet.walkthedog.bus_event.GpsSateliteEvent;
import com.wallet.walkthedog.bus_event.GpsStopEvent;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.EquipmentDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NoticeDialog;
import com.wallet.walkthedog.dialog.WalkNoticeDialog;
import com.wallet.walkthedog.service.GpsService;
import com.wallet.walkthedog.untils.AutoUtils;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.home.HomeActivity1;
import com.wallet.walkthedog.view.login.CreateActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.DateTimeUtil;

public class WalkActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
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
    @BindViews({R.id.img_equipment_1, R.id.img_equipment_2, R.id.img_equipment_3})
    ImageView[] imgEquipments;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.ll_gps)
    View llGPS;

    public static final int LOCATION_CODE = 1000;
    public static final int OPEN_GPS_CODE = 1001;
    private DogInfoDao mDefultDogInfo;
    private Intent gpsService;
    private WalkDogAdapter adapter;
    private List<EquipmentDao> data = new ArrayList<>();
    private boolean isWalking = false;
    private WalkNoticeDialog dialog;
    private boolean gpsEnable = false;
    private int speedErrorCount = 0;//????????????????????????????????????????????????????????????
    private long walkTime = 0;
    private Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //????????????????????????????????????
            try {
                mHandler.postDelayed(this, 10000);
            } catch (Exception e) {
                mHandler.postDelayed(this, 10000);
            }
        }
    };

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.ll_status)
    void switchButton() {
        if (isWalking) {
            //????????????
            stopService(gpsService);
            switchButton(false);
        } else {
            //????????????
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("GPS-Info", "????????????");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_CODE);//????????????code ??????????????????
            } else {
                // ???????????????????????????
                gpsService = new Intent(getApplicationContext(), GpsService.class);
                // ????????????
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //??????8.0??????
                    startForegroundService(gpsService);
                } else {
                    startService(gpsService);
                }
            }
        }
    }

    public static void actionStart(Activity activity, DogInfoDao mDefultDogInfo) {
        Intent intent = new Intent(activity, WalkActivity.class);
        intent.putExtra("mDefultDogInfo", mDefultDogInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_walk;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mDefultDogInfo = (DogInfoDao) getIntent().getSerializableExtra("mDefultDogInfo");
        initEquipment();
        initRv();
        checkPermission();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        if (isWalking) {
            return;
        }
        super.finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsLocation(GpsLocationEvent location) {
        if (gpsEnable) {
            BigDecimal bigDecimal = new BigDecimal(location.getSpeed());
            double speend = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            txtSpeed.setText(String.valueOf(speend));
            txtSpeed2.setText(location.getSpeedStatus());
            if(location.getSpeedStatus().equals("1")){
                //????????????
                showWarning("1", String.valueOf(speend));
            }else if(location.getSpeedStatus().equals("2")){
                //????????????
                showWarning("2", String.valueOf(speend));
            }else if(location.getSpeedStatus().equals("0")){
                //????????????
                stopWarning();
            }
        } else {
            txtSpeed.setText("0.0");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsSatelite(GpsSateliteEvent message) {
        if (txtGpsStatus == null) {
            return;
        }
        if (!isWalking) {
            switchButton(true);
        }

        if (message.getConnSatellite() == 0) {
            txtGpsStatus.setText(R.string.gps_week);
            imgGpsStatus.setBackgroundResource(R.mipmap.icon_gps_weak);
            txtSpeed.setText("0.0");
            gpsEnable = false;
        } else {
            txtGpsStatus.setText(R.string.gps);
            imgGpsStatus.setBackgroundResource(R.mipmap.icon_gps_well);
            gpsEnable = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsConnectTime(GpsConnectTImeEvent message) {
        if (txtTime == null) {
            return;
        }
        txtTime.setText(DateTimeUtil.second2Minute(message.getSeconed()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsStop(GpsStopEvent location) {
        if(gpsService==null){
            return;
        }
        stopService(gpsService);
        switchButton(false);
    }

    void initEquipment(){
        if(mDefultDogInfo.getPropList()==null){
            return;
        }
        for(int i=0;i<mDefultDogInfo.getPropList().size();i++){
            if(i>2){
                break;
            }
            if (mDefultDogInfo.getPropList().get(i).getId() == null) {
                continue;
            }
            imgEquipments[i].setVisibility(View.VISIBLE);
            RequestOptions optionsEq = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_bell)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //??????
            Glide.with(this).load(mDefultDogInfo.getPropList().get(i).getImg()).apply(optionsEq).into(imgEquipments[i]);

        }
    }

    void initRv() {
        //TODO ????????????
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

    void checkPermission() {
        if (!isIgnoringBatteryOptimizations(this)) {
            requestIgnoreBatteryOptimizations(this);
        }
//        AutoUtils.openAutoStart(this);
    }

    void switchButton(boolean b) {
        isWalking = b;
        if (isWalking) {
            //????????????
            txtStatus.setText(R.string.walk_stop);
            imgStatus.setBackgroundResource(R.mipmap.icon_walking);
            llGPS.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.INVISIBLE);
        } else {
            //????????????
            txtStatus.setText(R.string.walk_start);
            imgStatus.setBackgroundResource(R.mipmap.icon_walk_stop);
            llGPS.setVisibility(View.INVISIBLE);
            imgBack.setVisibility(View.VISIBLE);
        }
    }

    void showWarning(String type, String speed) {
        if(dialog!=null && dialog.isShown()){
            return;
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

    void stopWarning(){
        if(dialog!=null && dialog.isShown()){
            dialog.dismiss();
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(Context context) {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        return isIgnoring;
    }

    /**
     * ?????????????????????
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestIgnoreBatteryOptimizations(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
