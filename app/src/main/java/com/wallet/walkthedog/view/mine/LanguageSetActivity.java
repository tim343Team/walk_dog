package com.wallet.walkthedog.view.mine;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.ScreenUtils;

public class LanguageSetActivity extends BaseActivity {

    private int selectIndex = 0;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_language_set;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //TODO GET
        View layout_ch = findViewById(R.id.layout_ch);
        TextView tv_ch = findViewById(R.id.tv_ch);
        ImageView iv_ch = findViewById(R.id.iv_ch);

        View layout_en = findViewById(R.id.layout_en);
        TextView tv_en = findViewById(R.id.tv_en);
        ImageView iv_en = findViewById(R.id.iv_en);

        View layout_japan = findViewById(R.id.layout_japan);
        TextView tv_japan = findViewById(R.id.tv_japan);
        ImageView iv_japan = findViewById(R.id.iv_japan);

        Runnable runnable = new Runnable() {
            final int unselectColor = Color.parseColor("#485EA8");

            @Override
            public void run() {
                if (selectIndex == 0) {
                    select(layout_ch, layout_en, layout_japan);
                    tv_ch.setTextColor(Color.WHITE);
                    iv_ch.setImageTintList(ColorStateList.valueOf(Color.WHITE));

                    tv_en.setTextColor(unselectColor);
                    iv_en.setImageTintList(ColorStateList.valueOf(unselectColor));
                    tv_japan.setTextColor(unselectColor);
                    iv_japan.setImageTintList(ColorStateList.valueOf(unselectColor));

                } else if (selectIndex == 1) {
                    select(layout_en,layout_ch, layout_japan);
                    tv_en.setTextColor(Color.WHITE);
                    iv_en.setImageTintList(ColorStateList.valueOf(Color.WHITE));

                    tv_ch.setTextColor(unselectColor);
                    iv_ch.setImageTintList(ColorStateList.valueOf(unselectColor));
                    tv_japan.setTextColor(unselectColor);
                    iv_japan.setImageTintList(ColorStateList.valueOf(unselectColor));
                } else {
                    select(layout_japan,layout_en,layout_ch);
                    tv_japan.setTextColor(Color.WHITE);
                    iv_japan.setImageTintList(ColorStateList.valueOf(Color.WHITE));

                    tv_en.setTextColor(unselectColor);
                    iv_en.setImageTintList(ColorStateList.valueOf(unselectColor));
                    tv_ch.setTextColor(unselectColor);
                    iv_ch.setImageTintList(ColorStateList.valueOf(unselectColor));
                }

            }
        };
        layout_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIndex = 0;
                runnable.run();
            }
        });
        layout_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIndex = 1;
                runnable.run();
            }
        });
        layout_japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIndex = 2;
                runnable.run();
            }
        });

        if (selectIndex == 0) {
            layout_ch.callOnClick();
        } else if (selectIndex == 1) {
            layout_en.callOnClick();
        } else {
            layout_japan.callOnClick();
        }


    }

    private void select(View select, View unselect, View unSelect1) {
        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#8A758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardBackgroundColor(Color.parseColor("#2950A6"))
                .setCardCornerRadius(ScreenUtils.dip2px(this, 10))
                .create()
                .bindView(select);

        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#56758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardBackgroundColor(Color.WHITE)
                .setCardCornerRadius(ScreenUtils.dip2px(this, 10))
                .create()
                .bindView(unselect);

        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#56758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardBackgroundColor(Color.WHITE)
                .setCardCornerRadius(ScreenUtils.dip2px(this, 10))
                .create()
                .bindView(unSelect1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO SAVE
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
