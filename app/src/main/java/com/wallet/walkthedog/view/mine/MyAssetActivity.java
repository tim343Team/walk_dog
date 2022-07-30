package com.wallet.walkthedog.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.AssetLogDao;
import com.wallet.walkthedog.dao.AssetLogItem;
import com.wallet.walkthedog.dao.DogFoodItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MyAssetActivity extends BaseActivity {
    TextView tv_all_asset;
    private double all_asset = 0.0;
    private final static String xxxx ="****";
    private AssetListAdapter adapter = new AssetListAdapter();
    private int pageNo = 1;
    boolean see = true;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_asset;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_all_asset = findViewById(R.id.tv_all_asset);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView iv = findViewById(R.id.tv_hint);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                see = !see;
                if (see) {
                    iv.setImageResource(R.mipmap.icon_eye_close);
                    tv_all_asset.setText(Utils.getFormat("%.2f", all_asset));
                } else {
                    iv.setImageResource(R.mipmap.icon_eye_see);
                    tv_all_asset.setText(xxxx);

                }
            }
        });
        iv.callOnClick();

        findViewById(R.id.layout_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAssetActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.layout_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAssetActivity.this, TransferActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getTokenLog();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getAsset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        getTokenLog();
    }

    private void getAsset() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getWallet() + "?type=1")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Double>>() {
                    @Override
                    protected void onRes(RemoteData<Double> testRemoteData) {
                        onSuccessGetAsset(testRemoteData.getNotNullData());
                    }
                });
    }

    String nowTime = Utils.timeFormat(System.currentTimeMillis() + 3600_000, "yyyy-MM-dd HH:mm:ss");

    private void getTokenLog() {
        WonderfulOkhttpUtils.post().url(UrlFactory.getTokenLog())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("pageNo", String.valueOf(pageNo))
                .addParams("pageSize", "50")
                .addParams("startTime", "1970-01-01 00:00:00")
                .addParams("endTime", nowTime)
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<AssetLogDao>>() {
                    @Override
                    protected void onRes(RemoteData<AssetLogDao> testRemoteData) {
                        onSuccessGetAssetLog(testRemoteData.getNotNullData().getRecords());
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                        onFailGetAssetLog();
                    }
                });
    }

    private void onFailGetAssetLog() {
        adapter.loadMoreFail();
    }

    private void onSuccessGetAssetLog(List<AssetLogItem> records) {
        if (records.size() < 50) {
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

    private void onSuccessGetAsset(double notNullData) {
        this.all_asset = notNullData;
        if (see){
            tv_all_asset.setText(Utils.getFormat("%.2f", notNullData));
        }else {
            tv_all_asset.setText(xxxx);
        }
    }


    static class AssetListAdapter extends BaseQuickAdapter<AssetLogItem, BaseViewHolder> {

        public AssetListAdapter() {
            super(R.layout.item_my_asset);
        }

        @Override
        protected void convert(BaseViewHolder helper, AssetLogItem item) {
            int status = item.getStatus();
            int type = item.getType();
            //1充值 2提现 3购买 4遛狗奖励 5售卖 6宝箱获得
            Context context = helper.itemView.getContext();
            if (type == 1) {
                helper.setText(R.id.tv_type, context.getString(R.string.asset_type_1));
            } else if (type == 2) {
                helper.setText(R.id.tv_type, context.getString(R.string.asset_type_2));
            } else if (type == 3) {
                helper.setText(R.id.tv_type, context.getString(R.string.asset_type_3));
            } else if (type == 4) {
                helper.setText(R.id.tv_type, context.getString(R.string.asset_type_4));
            } else if (type == 5) {
                helper.setText(R.id.tv_type, context.getString(R.string.asset_type_5));
            } else {
                helper.setText(R.id.tv_type, context.getString(R.string.asset_type_6));
            }
            helper.setText(R.id.tv_q, Utils.getFormat("%+.2f", item.getAmount()));
            helper.setText(R.id.tv_time, item.getCreateTime());
            // 0待审核 1 成功 2失败 4已审核
            if (status == 0) {
                helper.setText(R.id.tv_state, context.getString(R.string.asset_state_0));
            } else if (status == 1) {
                helper.setText(R.id.tv_state, context.getString(R.string.successful));
            } else if (status == 2) {
                helper.setText(R.id.tv_state, context.getString(R.string.fail));
            } else if (status == 4) {
                helper.setText(R.id.tv_state, context.getString(R.string.asset_state_4));
            }
        }
    }
}
