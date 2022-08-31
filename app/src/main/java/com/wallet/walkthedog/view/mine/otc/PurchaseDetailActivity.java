package com.wallet.walkthedog.view.mine.otc;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.ActivityLifecycleManager;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.AdvertiseUnitItem;
import com.wallet.walkthedog.dao.FriendInfoListDao;
import com.wallet.walkthedog.dao.OTCOrderDetailDao;
import com.wallet.walkthedog.dao.PayInfo;
import com.wallet.walkthedog.dialog.PayConfirmDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.merchant.MerchantActivity;
import com.wallet.walkthedog.view.mine.ad.PlaceADActivity;

import java.util.ArrayList;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

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
    private TextView tv_payer;
    private TextView tv_pay_by;
    private TextView tv_name;
    private TextView tv_card_number;
    private TextView tv_bank_name;
    private TextView tv_pay_type;
    private View layout_bank;
    private int status = -1;
    private int type = 1;//1.银行卡 2，swift
    private OTCOrderDetailDao orderDetailDao;

    @Override
    protected void onResume() {
        super.onResume();
        String orderID = getIntent().getStringExtra("orderID");
       // AdvertiseUnitItem advertiseUnitItem = (AdvertiseUnitItem) getIntent().getSerializableExtra("AdvertiseUnitItemKEY");
        orderDetail(orderID);
//        getAdvertiseDetail(advertiseUnitItem.getAdvertiseId());
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
        layout_bank = findViewById(R.id.layout_bank);
        tv_payer = findViewById(R.id.tv_payer);
        tv_pay_by = findViewById(R.id.tv_pay_by);
        tv_name = findViewById(R.id.tv_name);
        tv_card_number = findViewById(R.id.tv_card_number);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_pay_type = findViewById(R.id.tv_pay_type);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCancel(getIntent().getStringExtra("orderID"));
            }
        });
        layout_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 切换显示支付内容
                showSelectWindow(view);
            }
        });

        tv_pay.findViewById(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 1) {
                    showPaySuccessDialog();
                } else {
                    if (ActivityLifecycleManager.get().hasActivity(PurchaseSellOTCActivity.class)) {
                        finish();
                        ActivityLifecycleManager.get().finishs(PurchaseSellOTCActivity.class);
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

    private void getAdvertiseDetail(int advertiseId) {
        WonderfulOkhttpUtils.get().url(UrlFactory.advertiseDetail() + "?id=" + advertiseId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<FriendInfoListDao>>() {
                    @Override
                    protected void onRes(RemoteData<FriendInfoListDao> testRemoteData) {
                        //TODO 显示支付信息
//                        onSuccessGetFriendList(testRemoteData.getNotNullData());
                    }

                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void onAdvertiseSuccess(OTCOrderDetailDao dao) {
        tv_payer.setText(getString(R.string.payer_, dao.getPayInfo().getRealName()));
        tv_pay_by.setText(getString(R.string.pay_by_, SharedPrefsHelper.getInstance().getUserInfo().getName()));
        if (type == 1) {
            //显示银行卡
            tv_pay_type.setText(R.string.bank_card);
            tv_bank_name.setVisibility(View.VISIBLE);
            if (dao.getPayInfo().getBankInfo() != null) {
                tv_card_number.setText(getString(R.string.card_number_, dao.getPayInfo().getBankInfo().getCardNo()));
                tv_name.setText(getString(R.string.pay_name_, dao.getPayInfo().getBankInfo().getRealName()));
                tv_bank_name.setText(getString(R.string.bank_name_, dao.getPayInfo().getBankInfo().getBank()));
            } else {
                tv_card_number.setText(getString(R.string.card_number_, ""));
                tv_name.setText(getString(R.string.pay_name_, ""));
                tv_bank_name.setText(getString(R.string.bank_name_, ""));
            }
        } else {
            //显示swift
            tv_pay_type.setText(R.string.swift);
            tv_bank_name.setVisibility(View.GONE);
            if (dao.getPayInfo().getSwift() != null) {
                tv_card_number.setText(getString(R.string.card_number_, dao.getPayInfo().getSwift().getSwiftAddress()));
                tv_name.setText(getString(R.string.pay_name_, dao.getPayInfo().getSwift().getSwiftRealName()));
            }else {
                tv_card_number.setText(getString(R.string.card_number_, ""));
                tv_name.setText(getString(R.string.pay_name_, ""));
            }
        }

    }

    private void onSuccess(OTCOrderDetailDao dao) {
        layout_content.setVisibility(View.VISIBLE);
        orderDetailDao = dao;
        PurchaseSellOTCActivity.Hepler helper = new PurchaseSellOTCActivity.Hepler(getWindow().getDecorView());
        timeLimit = dao.getTimeLimit() * 60L * 1000;
        sysTime = System.currentTimeMillis();
        helper.setText(R.id.tv_amount, getString(R.string.amount_of_the_transaction_s, Utils.getFormat("￥%.2f", dao.getMoney())));
        helper.setText(R.id.tv_number, getString(R.string.number_of_transactions_s, Utils.getFormat("%.8f" + dao.getUnit(), dao.getAmount())));
        helper.setText(R.id.tv_price, getString(R.string.number_of_transactions_s, Utils.getFormat("￥%.2f", dao.getPrice())));
        helper.setText(R.id.tv_merchant, getString(R.string.merchant_s, dao.getPayInfo().getRealName()));
        helper.setText(R.id.tv_create_time, getString(R.string.order_time_s, dao.getCreateTime()));
        helper.setText(R.id.tv_order_id, getString(R.string.order_number_s, dao.getOrderSn()));
        onAdvertiseSuccess(dao);
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

    private PopupWindow popupWindow;

    private void showSelectWindow(View v) {
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.simple_item_textview) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv, item);
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    onMenuSelect(position);
                }
            }
        });
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.bank_card));
        items.add(getString(R.string.swift));
        adapter.setNewData(items);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_money_select, null, false);
        view.measure(View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenWidth(this), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenHeight(this), View.MeasureSpec.AT_MOST)
        );
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        popupWindow = new PopupWindow(view, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(false);
        int measuredWidth = view.getMeasuredWidth();
        int[] outLocation = new int[2];
        int screenWidth = ScreenUtils.getScreenWidth(this);
        v.getLocationInWindow(outLocation);
        popupWindow.showAsDropDown(v, -measuredWidth + (screenWidth - outLocation[0]), 0, Gravity.BOTTOM);
    }

    private void onMenuSelect(int position) {
        if (position == 0) {
            type = 1;
        } else if (position == 1) {
            type = 2;
        }
        if(orderDetailDao!=null){
            onAdvertiseSuccess(orderDetailDao);
        }
    }
}
