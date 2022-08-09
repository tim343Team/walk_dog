package com.wallet.walkthedog.view.mine.otc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.AdvertiseUnitItem;
import com.wallet.walkthedog.dao.CoinNameItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.network.okhttp.post.PostFormBuilder;

/**
 * 买卖生成订单
 */
public class PurchaseSellOTCActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_purchase_otc;
    }

    private EditText editTextNum;
    private EditText editTextAmount;
    private TextView tv_limit_hint;
    private TextView tv_buy;
    private int advertiseType;//0是买，1是卖

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        advertiseType = getIntent().getIntExtra("advertiseType", 0);
        editTextNum = findViewById(R.id.edt_q);
        editTextAmount = findViewById(R.id.edit_amount);
        tv_limit_hint = findViewById(R.id.tv_limit_hint);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_all_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(advertiseUnitItem.getRemainQuantity());
                editTextNum.setText(s);
            }
        });
        findViewById(R.id.tv_all_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(advertiseUnitItem.getMaxLimit());
                editTextAmount.setText(s);
            }
        });
        editTextNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double num = Double.parseDouble(s.toString());
                    editTextAmount.setText(Utils.getFormat("%.2f", num * advertiseUnitItem.getPrice()));
                } catch (Exception e) {
                    editTextAmount.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextAmount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (limit(s.toString())) {
                    tv_limit_hint.setVisibility(View.VISIBLE);
                } else {
                    tv_limit_hint.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_buy = findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (advertiseType==0){
                    submitBuy();
                }else {
                    submitSell();
                }
            }
        });

    }
    //卖币流程
    private void submitSell() {
        try {
            String num = editTextNum.getText().toString();
            String amount = editTextAmount.getText().toString();
            if (num.isEmpty()) {
                ToastUtils.shortToast(getString(R.string.please_enter_the_quantity));
                return;
            }
            double numd = Double.parseDouble(num);
            if (numd > advertiseUnitItem.getRemainQuantity()) {
                ToastUtils.shortToast(getString(R.string.max_quantity_is_s, advertiseUnitItem.getRemainQuantity()));
                return;
            }

            if (limit(amount)) {
                ToastUtils.shortToast(getString(R.string.the_otc_order_hint));
                return;
            }
            requestSell(num);
        } catch (Exception ignored) {

        }
    }

    //买的流程
    private void submitBuy() {
        try {
            String num = editTextNum.getText().toString();
            String amount = editTextAmount.getText().toString();
            if (num.isEmpty()) {
                ToastUtils.shortToast(getString(R.string.please_enter_the_quantity));
                return;
            }
            double numd = Double.parseDouble(num);
            if (numd > advertiseUnitItem.getRemainQuantity()) {
                ToastUtils.shortToast(getString(R.string.max_quantity_is_s, advertiseUnitItem.getRemainQuantity()));
                return;
            }

            if (limit(amount)) {
                ToastUtils.shortToast(getString(R.string.the_otc_order_hint));
                return;
            }
            requestBuy(num);
        } catch (Exception ignored) {

        }
    }
    private void requestSell(String num) {
        PostFormBuilder builder = WonderfulOkhttpUtils.post().url(UrlFactory.orderSell());
        builder.addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("id", String.valueOf(advertiseUnitItem.getAdvertiseId()))
                .addParams("coinId", String.valueOf(coinNameItem.getId()))
                .addParams("price", String.valueOf(advertiseUnitItem.getPrice()))
                .addParams("remark", "")
                .addParams("mode", String.valueOf(0));
        builder.addParams("amount", num);
        builder.addParams("money", String.valueOf(Double.parseDouble(num) * advertiseUnitItem.getPrice()));
        builder.build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<String>>() {
                    @Override
                    protected void onRes(RemoteData<String> testRemoteData) {
                        String notNullData = testRemoteData.getNotNullData();
                        if (!notNullData.isEmpty()) {
                            Intent intent = new Intent(PurchaseSellOTCActivity.this, SellDetailActivity.class);
                            intent.putExtra("CoinNameItemKEY", coinNameItem);
                            intent.putExtra("AdvertiseUnitItemKEY", advertiseUnitItem);
                            intent.putExtra("orderID", testRemoteData.getNotNullData());
                            startActivity(intent);
                        }
                    }
                });
    }
    private void requestBuy(String num) {
        PostFormBuilder builder = WonderfulOkhttpUtils.post().url(UrlFactory.orderBuy());
        builder.addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("id", String.valueOf(advertiseUnitItem.getAdvertiseId()))
                .addParams("coinId", String.valueOf(coinNameItem.getId()))
                .addParams("price", String.valueOf(advertiseUnitItem.getPrice()))
                .addParams("remark", "")
                .addParams("mode", String.valueOf(0));
        builder.addParams("amount", num);
        builder.addParams("money", String.valueOf(Double.parseDouble(num) * advertiseUnitItem.getPrice()));
        builder.build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<String>>() {
                    @Override
                    protected void onRes(RemoteData<String> testRemoteData) {
                        String notNullData = testRemoteData.getNotNullData();
                        if (!notNullData.isEmpty()) {
                            Intent intent = new Intent(PurchaseSellOTCActivity.this, PurchaseDetailActivity.class);
                            intent.putExtra("CoinNameItemKEY", coinNameItem);
                            intent.putExtra("AdvertiseUnitItemKEY", advertiseUnitItem);
                            intent.putExtra("orderID", testRemoteData.getNotNullData());
                            startActivity(intent);
                        }
                    }
                });
    }

    private boolean limit(CharSequence amount) {
        try {
            if (!amount.toString().isEmpty()) {
                double a = Double.parseDouble(amount.toString());
                if (a > advertiseUnitItem.getMaxLimit() || a < advertiseUnitItem.getMinLimit()) {
                    return true;
                }
            }

        } catch (Exception ignored) {

        }
        return false;
    }

    private CoinNameItem coinNameItem;
    private AdvertiseUnitItem advertiseUnitItem;

    @Override
    protected void obtainData() {
        coinNameItem = (CoinNameItem) getIntent().getSerializableExtra("CoinNameItemKEY");
        advertiseUnitItem = (AdvertiseUnitItem) getIntent().getSerializableExtra("AdvertiseUnitItemKEY");

    }

    @Override
    protected void fillWidget() {
        AdvertiseUnitItem item = advertiseUnitItem;
        Hepler helper = new Hepler(getWindow().getDecorView());
        ImageView view = helper.getView(R.id.iv_header);
        if (!TextUtils.isEmpty(item.getAvatar())) {
            Glide.with(view)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(item.getAvatar())
                    .into(view);
        }
        helper.setText(R.id.tv_name, item.getMemberName());
        helper.setText(R.id.tv_price, Utils.getFormat("￥：%.2f", item.getPrice()));
        helper.setText(R.id.tv_volume, Utils.getFormat("%.2f", item.getRemainQuantity()));
        helper.setText(R.id.tv_unit, item.getCoinName() + "/" + item.getLegalCurrency());
        helper.setText(R.id.tv_qu, Utils.getFormat("Quantity：%.2f" + item.getCoinName(), item.getRemainQuantity()));
        helper.setText(R.id.tv_limit, Utils.getFormat("Limit：%.2f" + item.getLegalCurrency(), item.getMinLimit()));
        if (advertiseType == 1) {
            tv_buy.setText(getString(R.string.sell));
        }
    }

    public static class Hepler {
        View itemView;

        public Hepler(View itemView) {
            this.itemView = itemView;
        }

        public void setText(int id, CharSequence value) {
            if (value == null) {
                value = "";
            }
            TextView tv = itemView.findViewById(id);
            tv.setText(value);
        }

        public <T extends View> T getView(int id) {
            return itemView.findViewById(id);
        }
    }

    @Override
    protected void loadData() {

    }
}
