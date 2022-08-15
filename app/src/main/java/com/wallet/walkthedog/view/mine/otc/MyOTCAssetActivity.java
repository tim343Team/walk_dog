package com.wallet.walkthedog.view.mine.otc;

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
import com.wallet.walkthedog.dao.CoinExchangeLogDao;
import com.wallet.walkthedog.dao.CoinExchangeLogItem;
import com.wallet.walkthedog.dao.OtherAssetDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

/**
 * OTC资产点进来的
 */
public class MyOTCAssetActivity extends BaseActivity {
    TextView tv_all_asset;
    private String all_asset = "0.0";
    private final static String xxxx = "****";
    private final AssetListAdapter adapter = new AssetListAdapter();
    boolean see = false;
    private OtherAssetDao otherAssetDao;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_otc_asset;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        otherAssetDao = (OtherAssetDao) getIntent().getSerializableExtra("otherAssetDao");

        TextView tv_syb = findViewById(R.id.tv_syb);
        tv_syb.setText(otherAssetDao.getCoin().getName());
        all_asset = otherAssetDao.getBalance();

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
                    iv.setImageResource(R.mipmap.icon_eye_see);
                    tv_all_asset.setText(all_asset);
                } else {
                    iv.setImageResource(R.mipmap.icon_eye_close);
                    tv_all_asset.setText(xxxx);
                }
            }
        });
        iv.callOnClick();

        findViewById(R.id.layout_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOTCAssetActivity.this, OTCExchangeActivity.class);
                intent.putExtra("otherAssetDao", getIntent().getSerializableExtra("otherAssetDao"));
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        getTokenLog();
    }


    private void getTokenLog() {
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("symbol", otherAssetDao.getCoin().getName());
        hashmap.put("pageNo", "1");
        hashmap.put("pageSize", "1000");
        hashmap.put("direction", "ASC");
        String s = Utils.toGetUri(hashmap);


        WonderfulOkhttpUtils.get().url(UrlFactory.getTransferLog() + "?" + s)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<CoinExchangeLogDao>>() {
                    @Override
                    protected void onRes(RemoteData<CoinExchangeLogDao> testRemoteData) {
                        onSuccessGetAssetLog(testRemoteData.getNotNullData().getContent());
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

    private void onSuccessGetAssetLog(List<CoinExchangeLogItem> records) {
        adapter.setNewData(records);
    }


    static class AssetListAdapter extends BaseQuickAdapter<CoinExchangeLogItem, BaseViewHolder> {

        public AssetListAdapter() {
            super(R.layout.item_my_asset);
        }

        @Override
        protected void convert(BaseViewHolder helper, CoinExchangeLogItem item) {

            //otc交易钱包的交易类型
            //"充值"),//0
            //("提现"),//1
            //("转账"),//2
            //("合约交易"),//3
            //("法币买入"),//4
            //("法币卖出"),//5
            //("活动奖励"),//6
            //("推广奖励"),//7
            //("分红"),//8
            //("投票"),//9
            //("人工充值"),//10
            //("配对"),//11
            //("缴纳商家认证保证金"),//12
            //("退回商家认证保证金"),//13
            //("法币充值"),//14
            //("币币兑换"),//15
            //("渠道推广"),//16
            //("划转入杠杆钱包"),//17
            //("从杠杆钱包划转出"),//18
            //("钱包空投"),//19
            //("锁仓"),//20
            //("解锁"),//21
            //("第三方转入"),//22
            //("第三方转出"),//23
            //("币币转入法币"),//24
            //("法币转入币币"),//25
            //("借贷流水"),//26
            //("还款流水"),//27
            //("币币转入合约"),//28
            //("合约转入币币"),//29
            //("隔夜费"),//35
            //("期货转入法币"),//36
            //("法币转入期货"),//37
            //("期货交易"),//38
            //("OTC人工充值"),//42
            //("退回商家投资款"),//44
            //("释放质押"),//45
            int type = item.getType();
            if (type == 28 || type == 29 || type == 24 || type == 25) {
                helper.setText(R.id.tv_type, helper.itemView.getContext().getString(R.string.otc_exchange));
            } else if (type==15){
                helper.setText(R.id.tv_type, helper.itemView.getContext().getString(R.string.otc_duihuan));
            } else {
                helper.setText(R.id.tv_type, helper.itemView.getContext().getString(R.string.otc_trade));
            }
            try {
                helper.setText(R.id.tv_q, String.valueOf(Integer.valueOf(item.getAmount())));
            } catch (Exception ignored) {
            }
            helper.setText(R.id.tv_time, item.getCreateTime());
            helper.setText(R.id.tv_state, helper.itemView.getContext().getString(R.string.successful));
        }
    }
}
