package com.wallet.walkthedog.view.mine.ad;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.ADCoinItem;
import com.wallet.walkthedog.dao.ContryItem;
import com.wallet.walkthedog.dialog.SelectCoinDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class PlaceADActivity extends BaseActivity {
    private EditText edit_premium;
    private TextView tv_token;
    private TextView tv_contry_select;
    private TextView tv_max_c;
    private TextView tv_min_c;
    private EditText edit_max,edit_min;

    private List<ADCoinItem> adCoinItems = new ArrayList<>();
    private List<ContryItem> contryItems = new ArrayList<>();

    private Integer selectCoinPostion = -1;
    private Integer selectContryPostion = -1;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_place_ad;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        edit_max = findViewById(R.id.edit_max);
        edit_min = findViewById(R.id.edit_min);
        tv_max_c = findViewById(R.id.tv_max_c);
        tv_min_c = findViewById(R.id.tv_min_c);
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
        tv_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCoin();
            }
        });
        //选择国家
        tv_contry_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectContry();
            }
        });

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
            edit_max.setText("");
            edit_min.setText("");
        }
    }

    private void onSelectContry() {
        if (selectContryPostion != -1) {
            ContryItem contryItem = contryItems.get(selectContryPostion);
            tv_contry_select.setText(contryItem.getEnName());
            tv_max_c.setText(contryItem.getEnName());
            tv_min_c.setText(contryItem.getEnName());
            edit_max.setText("");
            edit_min.setText("");
        }
    }

    private void getAllCoin() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getIdeaTogether())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<ADCoinItem>>>() {

                    @Override
                    protected void onRes(RemoteData<List<ADCoinItem>> data) {
                        adCoinItems.addAll(data.getNotNullData());
                    }
                });
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