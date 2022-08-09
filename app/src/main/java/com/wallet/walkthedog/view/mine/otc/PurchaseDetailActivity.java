package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.OTCOrderDetailDao;
import com.wallet.walkthedog.dao.PayInfo;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.network.okhttp.post.PostFormBuilder;

public class PurchaseDetailActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_purchase_detail;
    }

    private ViewGroup layout_content;
    private TextView tv_time_limit;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long timeLimit = -1L;
    private long sysTime = System.currentTimeMillis();
    private final Runnable timeRun = new Runnable() {
        @Override
        public void run() {
            long timeSpacing = System.currentTimeMillis() - sysTime;
            long curTime = timeLimit - timeSpacing;
            String time = Utils.getTime(curTime);
            tv_time_limit.setText(getString(R.string.remaining_to_close_s, time));
            if (curTime < 0) {
                finish();
                return;
            }
            handler.postDelayed(this, 900);
        }
    };

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_time_limit = findViewById(R.id.tv_time_limit);
        layout_content = findViewById(R.id.layout_content);
        layout_content.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        String orderID = getIntent().getStringExtra("orderID");
        orderDetail(orderID);
    }

    private void orderDetail(String orderSn) {
        WonderfulOkhttpUtils.post().url(UrlFactory.orderDetail())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("orderSn", orderSn)
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<OTCOrderDetailDao>>() {
                    @Override
                    protected void onRes(RemoteData<OTCOrderDetailDao> otcOrderDetailDaoRemoteData) {
                        OTCOrderDetailDao notNullData = otcOrderDetailDaoRemoteData.getNotNullData();
                        onSuccess(notNullData);
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timeRun);
    }

    private void onSuccess(OTCOrderDetailDao dao) {
        layout_content.setVisibility(View.VISIBLE);
        PurchaseOTCActivity.Hepler helper = new PurchaseOTCActivity.Hepler(getWindow().getDecorView());
        handler.postDelayed(timeRun, 900);
        timeLimit = dao.getTimeLimit() * 60L * 1000;
        sysTime = System.currentTimeMillis();
        helper.setText(R.id.tv_amount, getString(R.string.amount_of_the_transaction_s, Utils.getFormat("￥%.2f", dao.getMoney())));
        helper.setText(R.id.tv_number, getString(R.string.number_of_transactions_s, Utils.getFormat("%.8f" + dao.getUnit(), dao.getAmount())));
        helper.setText(R.id.tv_price, getString(R.string.number_of_transactions_s, Utils.getFormat("￥%.2f", dao.getPrice())));
        helper.setText(R.id.tv_merchant, getString(R.string.merchant_s, dao.getPayInfo().getRealName()));
        helper.setText(R.id.tv_create_time, getString(R.string.order_time_s, dao.getCreateTime()));
        helper.setText(R.id.tv_order_id, getString(R.string.order_number_s, dao.getOrderSn()));
        helper.getView(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = helper.getView(R.id.tv_order_id);
                Utils.copyText(v.getContext(), tv.getText().toString());
                ToastUtils.shortToast(getString(R.string.copy_success));
            }
        });
        PayInfo payInfo = dao.getPayInfo();
        if (payInfo != null && payInfo.getBankInfo() != null) {
//            StringBuffer buffer = new StringBuffer();
//            buffer.append(getString(R.string.payer))
//                    .append(payInfo.getRealName()).append("\n")
//                    .append("Pay by:")
//                    .append("Perfect Money").append("\n")
//                    .append("Country:")
//                    .append("J")
        }


      //  helper.setText(R.id.tv_pay_info, "");
    }
}
