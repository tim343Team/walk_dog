package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.OtherAssetDao;

import java.io.Serializable;

import tim.com.libnetwork.base.BaseActivity;

public class OTCExchangeActivity extends BaseActivity {
    private LinearLayout layout_0, layout_1;
    private TextView tv_1_hint, tv_2_hint;
    private boolean fromfund = true;
    private OtherAssetDao otherAssetDao;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_otc_account;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        otherAssetDao = (OtherAssetDao) getIntent().getSerializableExtra("otherAssetDao");
        layout_0 = findViewById(R.id.layout_0);
        layout_1 = findViewById(R.id.layout_1);
        tv_1_hint = findViewById(R.id.tv_1_hint);
        tv_2_hint = findViewById(R.id.tv_2_hint);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChangeAnim();
            }
        });
    }

    private int exchangeH = 0;
    private boolean isAnimation = false;

    private void startChangeAnim() {
        if (isAnimation) {
            return;
        }
        if (exchangeH == 0) {
            int top = layout_0.getTop();
            int top1 = layout_1.getTop();
            exchangeH = top1 - top;
        }
        int exchange = exchangeH;
        if (!fromfund) {
            exchange = -exchangeH;
        }
        ViewCompat.animate(layout_0).translationYBy(exchange)
                .setDuration(200)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isAnimation = false;
                        fromfund = !fromfund;
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                });
        ViewCompat.animate(layout_1).translationYBy(-exchange)
                .setDuration(200)
                .start();
        isAnimation = true;
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
