package com.wallet.walkthedog.view.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.DogFoodItem;
import com.wallet.walkthedog.dao.DogFoodLogDao;
import com.wallet.walkthedog.dialog.SoldQuantityDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MineFoodActivity extends BaseActivity {
    TextView tv_dog_food;
    RecyclerView recyclerview;
    DogFoodListAdapter adapter = new DogFoodListAdapter();
    String nowTime = Utils.timeFormat(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
    private double dogFood = 0.0;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_mine_food;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        recyclerview = findViewById(R.id.recyclerview);
        tv_dog_food = findViewById(R.id.tv_dog_food);
        findViewById(R.id.layout_sell).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoldDialog();
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDogFoodLog(pageSize, pageNo);
            }
        }, recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    private void showSoldDialog() {
        if (dogFood == 0) {
            return;
        }
        SoldQuantityDialog dialog = SoldQuantityDialog.newInstance(dogFood);
        dialog.call = dialog1 -> {
            pageNo = 1;
            getDogFoodLog(pageSize, pageNo);
            dialog1.dismiss();
        };
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    int pageNo = 1;
    int pageSize = 50;

    @Override
    protected void loadData() {
        getDogFood();
        pageNo = 1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDogFoodLog(pageSize, pageNo);
    }

    private void getDogFood() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getWallet() + "?type=2")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Double>>() {
                    @Override
                    protected void onRes(RemoteData<Double> testRemoteData) {
                        onSuccessGetDogFood(testRemoteData.getNotNullData());
                    }
                });
    }

    private void getDogFoodLog(int pageSize, int pageNo) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getDogFoodLog() + "?pageSize=" + pageSize + "&startTime=1970-01-01 00:00:00&endTime=" + nowTime + "&pageNo" + pageNo)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<DogFoodLogDao>>() {
                    @Override
                    protected void onRes(RemoteData<DogFoodLogDao> testRemoteData) {
                        onSuccessGetDogLogFood(testRemoteData.getNotNullData());
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                        onFailGetDogFood();
                    }
                });
    }

    private void onFailGetDogFood() {
        adapter.loadMoreFail();
    }

    private void onSuccessGetDogLogFood(DogFoodLogDao notNullData) {
        List<DogFoodItem> records = notNullData.getRecords();
        if (records.size() < pageSize) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
        if (pageNo == 1) {
            adapter.setNewData(records);
        } else {
            adapter.addData(records);
        }

        pageNo++;

    }


    private void onSuccessGetDogFood(Double notNullData) {
        this.dogFood = notNullData;
        tv_dog_food.setText(Utils.getFormat("%.2fg", notNullData));
    }


    static class DogFoodListAdapter extends BaseQuickAdapter<DogFoodItem, BaseViewHolder> {

        public DogFoodListAdapter() {
            super(R.layout.item_my_asset);
        }

        @Override
        protected void convert(BaseViewHolder helper, DogFoodItem item) {
            int status = item.getStatus();
            int type = item.getType();
            //1获得 2卖出 4喂食 5购买
            Context context = helper.itemView.getContext();
            if (type == 1) {
                helper.setText(R.id.tv_type, context.getString(R.string.food_get));
            } else if (type == 2) {
                helper.setText(R.id.tv_type, context.getString(R.string.food_sell));
            } else if (type == 4) {
                helper.setText(R.id.tv_type, context.getString(R.string.food_eat));
            } else {
                helper.setText(R.id.tv_type, context.getString(R.string.food_buy));
            }
            helper.setText(R.id.tv_q, Utils.getFormat("%+.2f", item.getFoodConsume()));
            helper.setText(R.id.tv_time, item.getCreateTime());
            //状态 1成功 2失败 0默认 前端不要关注0这个状态 ,
            if (status == 1) {
                helper.setText(R.id.tv_state, context.getString(R.string.successful));
            } else {
                helper.setText(R.id.tv_state, context.getString(R.string.fail));
            }
        }
    }
}
