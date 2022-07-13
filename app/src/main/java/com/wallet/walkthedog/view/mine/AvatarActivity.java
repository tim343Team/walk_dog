package com.wallet.walkthedog.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

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
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
