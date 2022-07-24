package com.wallet.walkthedog.view.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.TrainRecordItem;
import com.wallet.walkthedog.dao.TrainRecordDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import java.util.Collections;
import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

/**
 * 训练记录
 */
public class TrainRecordActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_train_record;
    }

    TrainRecordAdapter adapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        RecyclerView recyclerview = findViewById(R.id.recyclerview);

        adapter = new TrainRecordAdapter();

        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getTrainRecord();
    }


    private void onSuccess(TrainRecordDao data) {
        List<TrainRecordItem> records = data.getRecords();
        adapter.addData(records);
    }

    private void onFail() {

    }


    private void getTrainRecord() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getTrainingPage())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<TrainRecordDao>>() {
                    @Override
                    protected void onRes(RemoteData<TrainRecordDao> testRemoteData) {
                        TrainRecordDao notNullData = testRemoteData.getNotNullData();
                        onSuccess(notNullData);
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                        TrainRecordActivity.this.onFail();
                    }
                });
    }


    static class TrainRecordAdapter extends BaseQuickAdapter<TrainRecordItem, BaseViewHolder> {

        public TrainRecordAdapter() {
            super(R.layout.item_train_record, Collections.emptyList());
        }

        @Override
        protected void convert(BaseViewHolder helper, TrainRecordItem item) {
            helper.setText(R.id.tv_dog_species, item.getDogTypeName());
            helper.setText(R.id.tv_number, item.getDogNumberChain());
            helper.setText(R.id.tv_times, String.valueOf(item.getCount()));
            helper.setText(R.id.tv_food, item.getDogNumberChain());//TODO
            helper.setText(R.id.tv_muscle, item.getDogNumberChain());
            helper.setText(R.id.tv_create_time, item.getCreateTime());
        }
    }
}
