package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.ActivityLifecycleManager;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.OTCOrderDetailDao;
import com.wallet.walkthedog.dao.PayInfo;
import com.wallet.walkthedog.dialog.PayConfirmDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class PurchaseDetailActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_purchase_detail;
    }

    private ViewGroup layout_content;
    private FrameLayout layout_status;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long timeLimit = -1L;
    private long sysTime = System.currentTimeMillis();
    private TextView tv_pay;
    private TextView tv_cancel;
    private int status = -1;


    @Override
    protected void onResume() {
        super.onResume();
        String orderID = getIntent().getStringExtra("orderID");
        orderDetail(orderID);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_content = findViewById(R.id.layout_content);
        layout_content.setVisibility(View.INVISIBLE);
        layout_status = findViewById(R.id.layout_status);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_pay = findViewById(R.id.tv_pay);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCancel(getIntent().getStringExtra("orderID"));
            }
        });

        tv_pay.findViewById(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 1) {
                    showPaySuccessDialog();
                } else {
                    if (ActivityLifecycleManager.get().hasActivity(PurchaseOTCActivity.class)) {
                        finish();
                        ActivityLifecycleManager.get().finishs(PurchaseOTCActivity.class, OTCOrderActivity.class);
                    } else {
                        finish();
                    }

                }
            }
        });
    }

    private void showPaySuccessDialog() {
        PayConfirmDialog payConfirmDialog = new PayConfirmDialog();
        payConfirmDialog.setGravity(Gravity.CENTER);
        payConfirmDialog.setCallback(() -> {
            orderPay(getIntent().getStringExtra("orderID"));
            payConfirmDialog.dismiss();
        });
        payConfirmDialog.show(getSupportFragmentManager(), "");
    }

    private void orderPay(String orderID) {
        WonderfulOkhttpUtils.post().url(UrlFactory.orderPay())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("orderSn", orderID)
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> otcOrderDetailDaoRemoteData) {
                        orderDetail(orderID);
                    }
                });
    }

    private void orderCancel(String orderID) {
        WonderfulOkhttpUtils.post().url(UrlFactory.orderCancel())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("orderSn", orderID)
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> otcOrderDetailDaoRemoteData) {
                        ToastUtils.shortToast(getString(R.string.cancel_order));
                        orderDetail(orderID);
                    }
                });
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
        handler.removeCallbacksAndMessages(null);
    }

    private void onSuccess(OTCOrderDetailDao dao) {
        layout_content.setVisibility(View.VISIBLE);
        PurchaseOTCActivity.Hepler helper = new PurchaseOTCActivity.Hepler(getWindow().getDecorView());
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

        status = dao.getStatus();//status 0已取消 1 未付款 2已付款 3已完成 4申述中
        layout_status.removeAllViews();
        tv_cancel.setVisibility(View.GONE);
        tv_pay.setText(getString(R.string.confirm));
        if (status == 0) {
            layout_status.addView(LayoutInflater.from(this).inflate(R.layout.view_order_cancel, layout_status, false));
        } else if (status == 1) {
            tv_cancel.setVisibility(View.VISIBLE);
            tv_pay.setText(getString(R.string.payment_is_successful));
            layout_status.addView(createPayingView());
        } else if (status == 2) {
            layout_status.addView(LayoutInflater.from(this).inflate(R.layout.view_order_success_1, layout_status, false));
        } else if (status == 3) {
            layout_status.addView(LayoutInflater.from(this).inflate(R.layout.view_order_success_1, layout_status, false));
        }


        //  helper.setText(R.id.tv_pay_info, "");
    }


    private View createPayingView() {
        View paying = LayoutInflater.from(this).inflate(R.layout.view_order_paying, layout_status, false);
        TextView tv_time_limit = paying.findViewById(R.id.tv_time_limit);
        Runnable timeRun = new Runnable() {
            @Override
            public void run() {
                long timeSpacing = System.currentTimeMillis() - sysTime;
                long curTime = timeLimit - timeSpacing;
                String time = Utils.getTimeSecond(curTime / 1000L);
                tv_time_limit.setText(getString(R.string.remaining_to_close_s, time));
                if (curTime < 0) {
                    finish();
                    return;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(timeRun);
        return paying;
    }
}
