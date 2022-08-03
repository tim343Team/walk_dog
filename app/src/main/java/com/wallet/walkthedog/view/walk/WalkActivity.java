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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.WalkDogAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.GpsConnectTImeEvent;
import com.wallet.walkthedog.bus_event.GpsLocationEvent;
import com.wallet.walkthedog.bus_event.GpsSateliteEvent;
import com.wallet.walkthedog.bus_event.GpsStartEvent;
import com.wallet.walkthedog.bus_event.GpsStopEvent;
import com.wallet.walkthedog.bus_event.UpdateFriendEvent;
import com.wallet.walkthedog.dao.AwardDao;
import com.wallet.walkthedog.dao.CoordDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.EquipmentDao;
import com.wallet.walkthedog.dao.InvitedFriendDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.RentDogDao;
import com.wallet.walkthedog.dao.request.AwardRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.NoticeDialog;
import com.wallet.walkthedog.dialog.StopWalkDIalog;
import com.wallet.walkthedog.dialog.WalkNoticeDialog;
import com.wallet.walkthedog.service.GpsService;
import com.wallet.walkthedog.untils.AutoUtils;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.home.HomeActivity1;
import com.wallet.walkthedog.view.invite.InviteActivity;
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

public class WalkActivity extends BaseActivity implements WalkContract.WalkView {
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
    @BindView(R.id.img_invate)
    ImageView imgInvate;
    @BindView(R.id.ll_gps)
    View llGPS;

    public static final int LOCATION_CODE = 1000;
    public static final int OPEN_GPS_CODE = 1001;
    private WalkContract.WalkPresenter presenter;
    private DogInfoDao mDefultDogInfo;
    private Intent gpsService;
    private int logId = 0;//遛狗记录id
    private double lan;//维度
    private double lon;//经度
    private WalkDogAdapter adapter;
    private List<AwardDao> data = new ArrayList<>();
    private boolean isWalking = false;
    private boolean isServicesWalking = false;
    private boolean isUpdateAward = false;
    private WalkNoticeDialog dialog;
    private boolean gpsEnable = false;
    private int speedErrorCount = 0;//记录不匹配速度的次数，达到三次后弹窗提示
    private long walkTime = 0;
    private Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //定时检查是否获取有新道具
//            ToastUtils.shortToast("WalkActivity：" + "定时器是否获取到道具："+isUpdateAward+logId);
            try {
                if (isUpdateAward) {
                    presenter.getAwardPage(new AwardRequest(logId, 0));
//                    presenter.getAwardPage(new AwardRequest(200,0));
                }
                mHandler.postDelayed(this, 15000);
            } catch (Exception e) {
                mHandler.postDelayed(this, 15000);
            }
        }
    };

    @OnClick(R.id.img_invate)
    void addInvate() {
        InviteActivity.actionStart(this);
    }

    @OnClick(R.id.img_back)
    void back() {
        if (isWalking) {
            //增加提示弹窗
            StopWalkDIalog dialog = StopWalkDIalog.newInstance();
            dialog.setTheme(R.style.PaddingScreen);
            dialog.setGravity(Gravity.CENTER);
            dialog.show(getSupportFragmentManager(), "edit");
            dialog.setCallback(new StopWalkDIalog.OperateCallback() {
                @Override
                public void callback() {
                    //停止遛狗
                    stopService(gpsService);
                    switchButton(false);
                    //停止遛狗接口
                    if (isServicesWalking)
                        presenter.stopWalkDog(new SwitchWalkRequest(mDefultDogInfo.getId(), String.valueOf(lan), String.valueOf(lon)));
                    dialog.dismiss();
                }
            });
        } else {
            finish();
        }
    }

    @OnClick(R.id.ll_status)
    void switchButton() {
        if (isWalking) {
            //停止遛狗
            stopService(gpsService);
            switchButton(false);
            //停止遛狗接口
            if (isServicesWalking)
                presenter.stopWalkDog(new SwitchWalkRequest(mDefultDogInfo.getId(), String.valueOf(lan), String.valueOf(lon)));
        } else {
            //开始遛狗
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("GPS-Info", "申请权限");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_CODE);//自定义的code 随便填一个数
            } else {
                // 用户以授权定位信息
                gpsService = new Intent(getApplicationContext(), GpsService.class);
                gpsService.putExtra("dogId", mDefultDogInfo.getId());
                // 开启服务
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //适配8.0机制
                    startForegroundService(gpsService);
                } else {
                    startService(gpsService);
                }
                switchButton(true);
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
        presenter = new WalkPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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
        presenter.getWalkTheDogFriend();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void finish() {
        if (isWalking) {
            return;
        }
        super.finish();
    }

    //gps 定位实时经纬度
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsLocation(GpsLocationEvent location) {
        double speend = 0.0;
        if (gpsEnable) {
            lon = location.getLongitude();
            lan = location.getLatitude();
            BigDecimal bigDecimal = new BigDecimal(location.getSpeed());
            speend = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            txtSpeed.setText(String.valueOf(speend));
            txtSpeed2.setText(location.getSpeedStatus());
            if (location.getSpeedStatus().equals("1")) {
                //彈出彈窗
                showWarning("1", String.valueOf(speend));
            } else if (location.getSpeedStatus().equals("2")) {
                //彈出彈窗
                showWarning("2", String.valueOf(speend));
            } else if (location.getSpeedStatus().equals("0")) {
                //关闭彈窗
                stopWarning();
            }
        } else {
            speend = 0.0;
            txtSpeed.setText(speend + "");
            showWarning("1", String.valueOf(speend));
        }
    }

    //gps卫星状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsSatelite(GpsSateliteEvent message) {
        if (txtGpsStatus == null) {
            return;
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

    //遛狗时间
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsConnectTime(GpsConnectTImeEvent message) {
        if (txtTime == null) {
            return;
        }
        txtTime.setText(DateTimeUtil.second2Minute(message.getSeconed()));
    }

    //遛狗停止
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsStop(GpsStopEvent location) {
        if (gpsService == null) {
            return;
        }
        stopService(gpsService);
        switchButton(false);
    }

    //gps状态正常遛狗真正开始
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetGpsStart(GpsStartEvent location) {
        logId = location.getLogId();
        isServicesWalking = true;
    }

    //gps状态正常遛狗真正开始
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addCoord(CoordDao coordDao) {
        logId = coordDao.getLogId();
        if (coordDao.getArea() == 1) {
            txtNotice.setText(R.string.walk_notice_2);
            txtNotice.setTextColor(getResources().getColor(R.color.color_f02828));
        } else if (coordDao.getArea() == 2) {
            txtNotice.setText(R.string.walk_notice_1);
            txtNotice.setTextColor(getResources().getColor(R.color.white));
        } else if (coordDao.getArea() == 3) {
            txtNotice.setText(R.string.walk_notice_1);
            txtNotice.setTextColor(getResources().getColor(R.color.white));
        }
        isUpdateAward = true;
    }

    //更新好友
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UpdateFriendEvent location) {
        presenter.getWalkTheDogFriend();
    }

    void initEquipment() {
        if (mDefultDogInfo.getPropList() == null) {
            return;
        }
        for (int i = 0; i < mDefultDogInfo.getPropList().size(); i++) {
            if (i > 2) {
                break;
            }
            if (mDefultDogInfo.getPropList().get(i).getId() == null) {
                continue;
            }
            imgEquipments[i].setVisibility(View.VISIBLE);
            RequestOptions optionsEq = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_bell)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
            Glide.with(this).load(mDefultDogInfo.getPropList().get(i).getImg()).apply(optionsEq).into(imgEquipments[i]);

        }
    }

    void initRv() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        adapter = new WalkDogAdapter(R.layout.adapter_walk_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.OnclickListenerItem(new WalkDogAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                if (position >= 0) {
                    //打开宝箱
                    presenter.getAward(new AwardRequest(0, data.get(position).getId()), position);
                }
//                NormalDialog dialog = NormalDialog.newInstance(R.string.collect_notice, R.mipmap.icon_normal);
//                dialog.setTheme(R.style.PaddingScreen);
//                dialog.setGravity(Gravity.CENTER);
//                dialog.show(getSupportFragmentManager(), "edit");
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
            //开始遛狗
            txtStatus.setText(R.string.walk_stop);
            imgStatus.setBackgroundResource(R.mipmap.icon_walk_stop);
            llGPS.setVisibility(View.VISIBLE);
        } else {
            //停止遛狗
            txtStatus.setText(R.string.walk_start);
            imgStatus.setBackgroundResource(R.mipmap.icon_walking);
            llGPS.setVisibility(View.INVISIBLE);
        }
    }

    void showWarning(String type, String speed) {
        if (dialog != null && dialog.isShown()) {
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
                //TODO 调用结束接口
            }
        });
    }

    void stopWarning() {
        if (dialog != null && dialog.isShown()) {
            dialog.dismiss();
        }
    }

    /**
     * 判断我们的应用是否在白名单中
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
     * 申请加入白名单
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

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(toastMessage);
    }

    @Override
    public void getAwardFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void stopSuccess(String message) {

    }

    @Override
    public void getAwardPageSuccess(List<AwardDao> daos) {
        isUpdateAward = false;
        ToastUtils.shortToast("获取道具：" + daos.size());
        //获取奖励
        data.clear();
        data.addAll(daos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAwardSuccess(String message, int position) {
        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        data.get(position).setStatus(3);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void getWalkTheDogFriendSuccessful(InvitedFriendDao data) {
        //展示遛狗好友狗狗头像
        if (data != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_null_dog)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
            Glide.with(this).load(data.getDog().getImg()).apply(options).into(imgInvate);
        }
    }

    @Override
    public void setPresenter(WalkContract.WalkPresenter presenter) {
        this.presenter = presenter;
    }
}
