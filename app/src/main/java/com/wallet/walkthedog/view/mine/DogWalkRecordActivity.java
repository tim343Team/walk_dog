package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;
import com.wallet.walkthedog.dao.DogWalkRecordDao;
import com.wallet.walkthedog.dao.DogWalkRecordItem;
import com.wallet.walkthedog.dao.TotalWalkDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import java.util.Collections;
import java.util.Locale;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

/**
 * 遛狗记录
 */
public class DogWalkRecordActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvData;
    private TextView tvWalkRecord;
    private RecyclerView recyclerview;
    private TotalAdapter totalAdapter;
    private WalkRecordAdapter walkRecordAdapter;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_dog_walk_record;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvData = findViewById(R.id.tv_data);
        tvWalkRecord = findViewById(R.id.tv_walk_record);
        recyclerview = findViewById(R.id.recyclerview);
        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 0) {
                    selectPosition = 0;
                    onSelect();
                }
            }
        });
        tvWalkRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 1) {
                    selectPosition = 1;
                    onSelect();
                }
            }
        });
        onSelect();
    }


    private int pageNo = 1;
    private int pageSize = 50;

    private void onSelect() {
        if (selectPosition == 0) {
            selectTextView(tvData, tvWalkRecord);
            if (totalAdapter == null) {
                totalAdapter = new TotalAdapter();
            }
            recyclerview.setAdapter(totalAdapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
        } else {
            selectTextView(tvWalkRecord, tvData);
            if (walkRecordAdapter == null) {
                walkRecordAdapter = new WalkRecordAdapter();
                walkRecordAdapter.setOnLoadMoreListener(() -> {
                    pageNo++;
                    getWalkDogLog();
                }, recyclerview);
            }
            recyclerview.setAdapter(walkRecordAdapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
        }

        requestData();
    }

    private void requestData() {
        if (selectPosition == 0) {
            if (totalAdapter.getData().isEmpty()) {
                getWalkDogLogCount();
            }
        } else {
            if (walkRecordAdapter.getData().isEmpty()) {
                pageNo = 1;
                getWalkDogLog();
            }
        }
    }

    private void getWalkDogLogCount() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getWalkDogLogCount())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<TotalWalkDao>>() {
                    @Override
                    protected void onRes(RemoteData<TotalWalkDao> testRemoteData) {
                        onSuccessGetWalkDogLogCount(testRemoteData.getNotNullData());
                    }
                });
    }

    private void getWalkDogLog() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getWalkDogLog() +"?pageNo="+pageNo +"&pageSize=" +pageSize)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("pageNo", String.valueOf(pageNo))
                .addParams("pageSize", String.valueOf(pageSize))
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<DogWalkRecordDao>>() {
                    @Override
                    protected void onRes(RemoteData<DogWalkRecordDao> testRemoteData) {
                        onSuccessGetWalkDogLog(testRemoteData.getNotNullData());
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                        walkRecordAdapter.loadMoreFail();
                    }
                });
    }

    private void onSuccessGetWalkDogLogCount(TotalWalkDao totalWalkDao) {
        totalAdapter.setNewData(Collections.singletonList(totalWalkDao));
    }

    private void onSuccessGetWalkDogLog(DogWalkRecordDao totalWalkDao) {
        walkRecordAdapter.addData(totalWalkDao.getRecords());
        walkRecordAdapter.loadMoreComplete();
        if (totalWalkDao.getRecords().size() < 50) {
            walkRecordAdapter.loadMoreEnd();
        }
    }


    private void selectTextView(TextView select, TextView unselect) {
        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#56758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardBackgroundColor(Color.parseColor("#2950A6"))
                .setCardCornerRadius(ScreenUtils.dip2px(this, 10))
                .create()
                .bindView(select);
        select.setTextColor(Color.WHITE);

        unselect.setBackground(null);
        unselect.setTextColor(Color.parseColor("#ADAEB3"));
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

    static class TotalAdapter extends BaseQuickAdapter<TotalWalkDao, BaseViewHolder> {

        public TotalAdapter() {
            super(R.layout.item_dog_total_data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TotalWalkDao item) {
            helper.setText(R.id.tv_total_data, String.valueOf(item.getCount()));
            helper.setText(R.id.tv_total_time, Utils.getTime(item.getWalkTheDogTime()));
            helper.setText(R.id.tv_total_mileage, String.format(Locale.getDefault(),"%.2fkm",(Double.parseDouble(item.getWalkTheDogKm())/1000D)));
            helper.setText(R.id.tv_treasure_chest, String.valueOf(item.getBox()));
           // helper.setText(R.id.tv_food, item.getDogFood() +"g");
           // helper.setText(R.id.tv_other,String.valueOf(item.getProp()));
            helper.setText(R.id.tv_rubbish, String.valueOf(item.getRubbish()));
            helper.setText(R.id.tv_well_know_area, String.format(Locale.getDefault(),"%.2fkm",item.getPopularityArea()/1000D));
            helper.setText(R.id.tv_intermediate_area, String.format(Locale.getDefault(),"%.2fkm",item.getMiddlArea()/1000D));
            helper.setText(R.id.tv_unknow_area, String.format(Locale.getDefault(),"%.2fkm",item.getUnknownArea()/1000D));

            helper.setText(R.id.tv_frends_numbers, String.valueOf(item.getFriendCount()));
            helper.setText(R.id.tv_friends_times, String.valueOf(item.getFriendWalkDogCount()));
            helper.setText(R.id.tv_frends_time, Utils.getTime(item.getFriendTime()));
            helper.setText(R.id.tv_frends_mileage, String.format(Locale.getDefault(),"%.2fkm",item.getFriendKm()/1000D));
            helper.setText(R.id.tv_frends_treasure_chest, String.valueOf(item.getFriendBox()));
            helper.setText(R.id.tv_frends_food, String.valueOf(item.getFriendDogFood()));
            helper.setText(R.id.tv_frends_others,String.valueOf(item.getFriendProp()));
            helper.setText(R.id.tv_frends_rubbish, String.valueOf(item.getFriendRubbish()));

        }
    }

    static class WalkRecordAdapter extends BaseQuickAdapter<DogWalkRecordItem, BaseViewHolder> {

        public WalkRecordAdapter() {
            super(R.layout.item_dog_walk_record);
        }

        @Override
        protected void convert(BaseViewHolder helper, DogWalkRecordItem item) {
            helper.setText(R.id.tv_times, String.valueOf(item.getWalkTheDogCount()));
            helper.setText(R.id.tv_time, item.getCreateTime());
            helper.setText(R.id.tv_walk_time, Utils.getTime(item.getTime()));
            helper.setText(R.id.tv_mileages, String.format(Locale.getDefault(), "%.2fkm", item.getWalkTheDogKm()));
            helper.setText(R.id.tv_top_speed, String.format(Locale.getDefault(), "%.2fkm/h", item.getMaxSpeed()));
            helper.setText(R.id.tv_dog_species, item.getDogTypeName());
            helper.setText(R.id.tv_dog_number, item.getDogNumberChain());
            helper.setText(R.id.tv_dog_rating, String.format(Locale.getDefault(), "Lv.%s", item.getDogLevel()));

            helper.setText(R.id.tv_friends, item.getFriendDogId());
            helper.setText(R.id.tv_friends_mileages, String.format(Locale.getDefault(), "%.2fkm", item.getFriendKm()));
            helper.setText(R.id.tv_friends_spe, item.getFriendDogTypeName());
            helper.setText(R.id.tv_friends_num, item.getFriendDogNumberChain());
            helper.setText(R.id.tv_friends_rating, String.format(Locale.getDefault(), "Lv.%s", item.getFriendDogLevel()));


            helper.setText(R.id.tv_area_know, String.format(Locale.getDefault(),"%.2fkm",item.getPopularityArea()));
            helper.setText(R.id.tv_intermediate_area, String.format(Locale.getDefault(),"%.2fkm",item.getMiddlArea()));
            helper.setText(R.id.tv_unknow_area, String.format(Locale.getDefault(), "%.2fkm",item.getUnknownArea()) );
            helper.setText(R.id.tv_treasure_chest, String.valueOf(item.getBox()));
            helper.setText(R.id.tv_dog_food, String.format(Locale.getDefault(), "%d g", item.getDogFood()));
            helper.setText(R.id.tv_dog_other,String.valueOf(item.getWalkTheDogCount()));
            helper.setText(R.id.tv_rubbish, String.valueOf(item.getRubbish()));

        }
    }
}
