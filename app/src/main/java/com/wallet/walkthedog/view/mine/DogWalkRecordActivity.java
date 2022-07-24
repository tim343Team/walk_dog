package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.ScreenUtils;

public class DogWalkRecordActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvData;
    private TextView tvWalkRecord;


    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_dog_walk_record;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvData = findViewById(R.id.tv_data);
        tvWalkRecord = findViewById(R.id.tv_walk_record);
        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 0) {
                    selectPosition = 0;
                    onSelect();
                }
            }
        });
        tvWalkRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 1) {
                    selectPosition = 1;
                    onSelect();
                }
            }
        });
        onSelect();
    }

    private void onSelect() {
        if (selectPosition == 0) {
            selectTextView(tvData, tvWalkRecord);
        } else {
            selectTextView(tvWalkRecord, tvData);
        }
        requestData();
    }

    private void requestData() {

    }

    private void selectTextView(TextView select, TextView unselect) {
        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#56758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardBackgroundColor(Color.parseColor("#2950A6"))
                .setCardCornerRadius(ScreenUtils.dip2px(this, 10))
                .create()
                .bindView(select);
        select.setTextColor(Color.WHITE);

        unselect.setBackground(null);
        unselect.setTextColor(Color.parseColor("#ADAEB3"));
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
}
