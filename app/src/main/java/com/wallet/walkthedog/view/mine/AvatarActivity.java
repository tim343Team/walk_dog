package com.wallet.walkthedog.view.mine;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseActivity;

public class AvatarActivity extends BaseActivity {
    @BindView(R.id.tv_edit)
    TextView tvEdit;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_avatar;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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
