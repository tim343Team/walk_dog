package com.wallet.walkthedog.view.mine.ad;

import android.os.Bundle;
import android.widget.EditText;

import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseActivity;

public class PlaceADActivity extends BaseActivity {
    private EditText edit_premium;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_place_ad;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        edit_premium = findViewById(R.id.edit_premium);
        MinMaxInputFilter.minMaxLimit(edit_premium, 0, 100);
        ScaleInputFilter.scale(edit_premium, 2);

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
    //private edit_premium

}