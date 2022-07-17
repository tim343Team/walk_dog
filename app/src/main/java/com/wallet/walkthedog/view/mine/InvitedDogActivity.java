package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.ScreenUtils;

public class InvitedDogActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvfriends;
    private TextView tvInviteOthers;
    private TextView tvBeInvited;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invited_dog;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvfriends = findViewById(R.id.tv_friends);
        tvInviteOthers = findViewById(R.id.tv_invite_others);
        tvBeInvited =  findViewById(R.id.tv_be_invited);
        tvfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 0) {
                    selectPosition = 0;
                    onSelect();
                }
            }
        });

        tvInviteOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 1) {
                    selectPosition = 1;
                    onSelect();
                }
            }
        });
        tvBeInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 2) {
                    selectPosition = 2;
                    onSelect();
                }
            }
        });
        onSelect();
    }
    private void onSelect() {
        if (selectPosition == 0) {
            selectTextView(tvfriends, tvInviteOthers,tvBeInvited);
        } else if (selectPosition==1){
            selectTextView(tvInviteOthers, tvfriends,tvBeInvited);
        } else {
            selectTextView(tvBeInvited, tvfriends,tvInviteOthers);
        }
        requestData();
    }

    private void selectTextView(TextView select, TextView unselect,TextView unSelect1) {
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

        unSelect1.setBackground(null);
        unSelect1.setTextColor(Color.parseColor("#ADAEB3"));
    }
    private void requestData() {

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
