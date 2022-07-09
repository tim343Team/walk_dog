package com.wallet.walkthedog.view.walk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.WalkDogAdapter;
import com.wallet.walkthedog.dao.EquipmentDao;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NoticeDialog;
import com.wallet.walkthedog.dialog.WalkNoticeDialog;
import com.wallet.walkthedog.view.login.CreateActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

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
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private WalkDogAdapter adapter;
    private List<EquipmentDao> data = new ArrayList<>();
    private boolean isWalking=false;
    private WalkNoticeDialog dialog;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.ll_status)
    void switchButton() {
        if(isWalking){
            //停止遛狗
        }else {
            //开始遛狗
        }
        //TODO 测试倒计时
        showWarning("2","1.11");
        showWarning("1","12.5");
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

    void showWarning(String type,String speed){
        if(!(dialog==null || !dialog.isShown())){
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
}
