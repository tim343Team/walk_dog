package com.wallet.walkthedog.view.mine.ad;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.ADCoinItem;
import com.wallet.walkthedog.dao.ContentItem;
import com.wallet.walkthedog.dao.ContryItem;
import com.wallet.walkthedog.dialog.MutSelectCoinDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.dialog.SelectCoinDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.view.switchbuttom.SwitchButton;

public class PlaceADActivity extends BaseActivity {
    private EditText edit_premium;
    private TextView tv_token;
    private TextView tv_contry_select;
    private TextView tv_max_c;
    private TextView tv_min_c;
    private TextView tv_country_name;
    private TextView tv_conutry_to_coin;
    private TextView tv_pay_info;
    private SwitchButton settings_right_toogle;
    private EditText edit_max, edit_min, edit_quantity, edit_trade_period;

    private final List<ADCoinItem> adCoinItems = new ArrayList<>();
    private final List<ContryItem> contryItems = new ArrayList<>();

    private Integer selectCoinPostion = -1;
    private Integer selectContryPostion = -1;
    private int advertiseType = 0; // 0是买，1是卖
    private ContentItem contentItem;
    private final List<Integer> selectPayInfo = new ArrayList<>();
    private final List<PayInfo> payInfos = new ArrayList<>();
    private boolean ignoreCoinNextReset = false;
    private boolean ignoreCountryNextReset = false;

    @Override
    protected int getActivityLayoutId() {
        payInfos.add(new PayInfo("Bank Paypal", getString(R.string.bank_card)));
        payInfos.add(new PayInfo("Swift", getString(R.string.swift)));
        return R.layout.activity_place_ad;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        TextView tv_hint_quantity = findViewById(R.id.tv_hint_quantity);

        settings_right_toogle = findViewById(R.id.settings_right_toogle);
        tv_pay_info = findViewById(R.id.tv_pay_info);
        edit_max = findViewById(R.id.edit_max);
        edit_min = findViewById(R.id.edit_min);
        edit_quantity = findViewById(R.id.edit_quantity);
        edit_trade_period = findViewById(R.id.edit_trade_period);

        tv_max_c = findViewById(R.id.tv_max_c);
        tv_min_c = findViewById(R.id.tv_min_c);
        tv_conutry_to_coin = findViewById(R.id.tv_conutry_to_coin);
        tv_country_name = findViewById(R.id.tv_country_name);
        ImageView iv_token = findViewById(R.id.iv_token);
        ImageView iv_country = findViewById(R.id.iv_country);

        tv_contry_select = findViewById(R.id.tv_contry_select);
        edit_premium = findViewById(R.id.edit_premium);
        tv_token = findViewById(R.id.tv_token);
        MinMaxInputFilter.minMaxLimit(edit_premium, 0, 100);
        ScaleInputFilter.scale(edit_premium, 2);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //选择币
        iv_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCoin();
            }
        });
        //选择国家
        iv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectContry();
            }
        });
        //支付方式
        findViewById(R.id.iv_pay_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPayInfo();
            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        contentItem = (ContentItem) getIntent().getSerializableExtra("contentItem");
        advertiseType = getIntent().getIntExtra("advertiseType", -1);
        if (contentItem != null && advertiseType == -1) {
            advertiseType = contentItem.getAdvertiseType();
        }

        if (advertiseType == 0) {
            tv_hint_quantity.setText(getString(R.string.buy_quantity));
        } else {
            tv_hint_quantity.setText(getString(R.string.sell_quantity));
        }

        if (contentItem != null) {
            fillUI(contentItem);
        }
    }

    static class PayInfo implements SelectCoinDialog.SimpleText {
        String payName;
        String payNametxt;

        public PayInfo(String payName, String payNametxt) {
            this.payName = payName;
            this.payNametxt = payNametxt;
        }

        @Override
        public String getShowText() {
            return payNametxt;
        }
    }

    private String getPayInfoTxt() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < selectPayInfo.size(); i++) {
            result.append(payInfos.get(selectPayInfo.get(i)).getShowText()).append(",");
        }
        if (result.length() == 0) {
            return "";
        }
        return result.replace(result.length() - 1, result.length(), "").toString();
    }

    private void showSelectPayInfo() {

        MutSelectCoinDialog<PayInfo> selectCoinDialog = new MutSelectCoinDialog<PayInfo>();
        selectCoinDialog.callBack = select -> {
            selectPayInfo.clear();
            selectPayInfo.addAll(select);
            tv_pay_info.setText(getPayInfoTxt());
        };
        selectCoinDialog.lists = payInfos;
        selectCoinDialog.show(getSupportFragmentManager(), "");
    }

    private void fillUI(ContentItem contentItem) {
        //double premium = Utils.toDouble(edit_premium.getText().toString());
        //        double quantity = Utils.toDouble(edit_quantity.getText().toString());
        //        double tradePeriod = Utils.toDouble(edit_trade_period.getText().toString());
        //        double min = Utils.toDouble(edit_min.getText().toString());
        //        double max = Utils.toDouble(edit_max.getText().toString());
        //hashMap.put("advertiseType", advertiseType);
        //                hashMap.put("coinId", adCoinItems.get(selectCoinPostion).getId());
        //                hashMap.put("countryName", contryItems.get(selectContryPostion).getZhName());
        //                hashMap.put("minLimit", min);
        //                hashMap.put("maxLimit", max);
        //                hashMap.put("remark", "");
        //                hashMap.put("timeLimit", (int) tradePeriod);
        //                hashMap.put("premiseRate", premium / 100);
        //                String priceType = "REGULAR";
        //                if (settings_right_toogle.isEnabled()) {
        //                    priceType = "MUTATIVE";
        //                }
        //                hashMap.put("priceType", priceType);
        //                hashMap.put("number", quantity);
        //                hashMap.put("pay[]", "Bank");
        //String priceType = "REGULAR";
        //                if (settings_right_toogle.isEnabled()) {
        //                    priceType = "MUTATIVE";
        //                }

        edit_premium.setText(Utils.getFormat("%.2f", contentItem.getPremiseRate() * 100));
        edit_quantity.setText(String.valueOf((int) contentItem.getNumber()));
        edit_trade_period.setText(String.valueOf(contentItem.getTimeLimit()));
        edit_min.setText(String.valueOf(contentItem.getMinLimit()));
        edit_max.setText(String.valueOf(contentItem.getMaxLimit()));
        settings_right_toogle.setChecked(contentItem.getPriceType() == 1);

        String payMode = contentItem.getPayMode();
        if (!TextUtils.isEmpty(payMode)){
            selectPayInfo.clear();
            String[] split = payMode.split(",");
            for (String s : split) {
                for (int j = 0; j < payInfos.size(); j++) {
                    if (payInfos.get(j).getShowText().equals(s)) {
                        selectPayInfo.add(j);
                    }
                }
            }
            tv_pay_info.setText(getPayInfoTxt());
        }
    }

    private void submit() {
        if (selectCoinPostion == -1) {
            ToastUtils.shortToast(getString(R.string.select_coin));
            return;
        }

        if (selectContryPostion == -1) {
            ToastUtils.shortToast(getString(R.string.select_country));
            return;
        }
        double premium = Utils.toDouble(edit_premium.getText().toString());
        double quantity = Utils.toDouble(edit_quantity.getText().toString());
        double tradePeriod = Utils.toDouble(edit_trade_period.getText().toString());
        double min = Utils.toDouble(edit_min.getText().toString());
        double max = Utils.toDouble(edit_max.getText().toString());
        if (quantity == 0) {
            ToastUtils.shortToast(getString(R.string.input_quantity));
            return;
        }
        if (tradePeriod == 0) {
            ToastUtils.shortToast(getString(R.string.input_trading_period));
            return;
        }
        if (min == 0) {
            ToastUtils.shortToast(getString(R.string.input_minimum_transaction_amount));
            return;
        }
        if (max == 0 || max <= min) {
            ToastUtils.shortToast(getString(R.string.input_maximum_transaction_amount));
            return;
        }
        if (selectPayInfo.isEmpty()) {
            ToastUtils.shortToast(getString(R.string.please_choose_a_payment_method));
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (contentItem != null) {
                    hashMap.put("id", contentItem.getId());
                }
                hashMap.put("advertiseType", advertiseType);
                hashMap.put("coinId", adCoinItems.get(selectCoinPostion).getId());
                hashMap.put("countryName", contryItems.get(selectContryPostion).getZhName());
                hashMap.put("minLimit", min);
                hashMap.put("maxLimit", max);
                hashMap.put("remark", "");
                hashMap.put("timeLimit", (int) tradePeriod);
                hashMap.put("premiseRate", premium / 100);
                String priceType = "REGULAR";
                if (settings_right_toogle.isChecked()) {
                    priceType = "MUTATIVE";
                }
                hashMap.put("priceType", priceType);
                hashMap.put("number", quantity);
                hashMap.put("pay[]", tv_pay_info.getText().toString());
                String params = Utils.toGetUri(hashMap);

                String url = UrlFactory.advertiseCreate();
                if (contentItem != null) {
                    url = UrlFactory.advertiseUpdate();
                }

                WonderfulOkhttpUtils.get().url(url + "?" + params)
                        .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                        .build()
                        .getCall()
                        .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {

                            @Override
                            protected void onRes(RemoteData<Object> data) {
                                ToastUtils.shortToast(getString(R.string.place_ad_success));
                                getWindow().getDecorView().postDelayed(PlaceADActivity.this::finish, 500);
                            }
                        });
            }
        };

        PasswordDialog dialog = new PasswordDialog();
        dialog.setGravity(Gravity.CENTER);
        dialog.setCallback(new PasswordDialog.OperateCallback() {
            @Override
            public void callback(String password) {
                hashMap.put("jyPassword", password);
                runnable.run();
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), "");

    }

    private void showSelectContry() {
        if (!contryItems.isEmpty()) {
            SelectCoinDialog<ContryItem> selectCoinDialog = new SelectCoinDialog<>();
            selectCoinDialog.callBack = integer -> {
                this.selectContryPostion = integer;
                onSelectContry();
            };
            selectCoinDialog.lists = contryItems;
            selectCoinDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void showSelectCoin() {
        if (!adCoinItems.isEmpty()) {
            SelectCoinDialog<ADCoinItem> selectCoinDialog = new SelectCoinDialog<>();
            selectCoinDialog.callBack = integer -> {
                this.selectCoinPostion = integer;
                onSelectCoin();
            };
            selectCoinDialog.lists = adCoinItems;
            selectCoinDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void onSelectCoin() {
        if (selectCoinPostion != -1) {
            ADCoinItem adCoinItem = adCoinItems.get(selectCoinPostion);
            tv_token.setText(adCoinItem.getName());
            if (!ignoreCoinNextReset) {
                edit_max.setText("");
                edit_min.setText("");
            }
            ignoreCoinNextReset = false;

        }
    }

    private void onSelectContry() {
        if (selectContryPostion != -1) {
            ContryItem contryItem = contryItems.get(selectContryPostion);
            tv_contry_select.setText(contryItem.getLocalCurrency());
            tv_max_c.setText(contryItem.getLocalCurrency());
            tv_min_c.setText(contryItem.getLocalCurrency());
            tv_country_name.setText(contryItem.getEnName());
            if (!ignoreCountryNextReset) {
                edit_max.setText("");
                edit_min.setText("");
            }
            ignoreCountryNextReset = false;
        }
    }

    private void getAllCoin() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getAllCoin())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<ADCoinItem>>>() {

                    @Override
                    protected void onRes(RemoteData<List<ADCoinItem>> data) {
                        adCoinItems.addAll(data.getNotNullData());
                        if (adCoinItems.isEmpty()) {
                            return;
                        }
                        if (contentItem != null) {
                            selectCoinPostion = findCoinPostion(adCoinItems, contentItem);
                            ignoreCoinNextReset = true;
                        } else {
                            selectCoinPostion = 0;
                        }
                        onSelectCoin();
                    }
                });
    }

    private int findCoinPostion(List<ADCoinItem> adCoinItems, ContentItem contentItem) {
        int id = contentItem.getCoin().getId();
        for (int i = 0; i < adCoinItems.size(); i++) {
            if (adCoinItems.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }

    private int findCountry(List<ContryItem> contryItems, ContentItem contentItem) {
        for (int i = 0; i < contryItems.size(); i++) {
            if (Objects.equals(contryItems.get(i).getAreaCode(), contentItem.getCountry().getAreaCode())) {
                return i;
            }
        }
        return 0;
    }

    private void requestCountryList() {
        WonderfulOkhttpUtils.get().url(UrlFactory.countryList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<ContryItem>>>() {
                    @Override
                    protected void onRes(RemoteData<List<ContryItem>> testRemoteData) {
                        contryItems.addAll(testRemoteData.getNotNullData());
                        if (contryItems.isEmpty()) {
                            return;
                        }
                        if (contentItem != null) {
                            selectContryPostion = findCountry(contryItems, contentItem);
                            ignoreCountryNextReset = true;
                        } else {
                            selectContryPostion = 0;
                        }
                        onSelectContry();
                    }
                });
    }


    @Override
    protected void obtainData() {
        getAllCoin();
        requestCountryList();
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }
    //private edit_premium

}