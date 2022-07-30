package com.wallet.walkthedog.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

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

        ImageView iv = findViewById(R.id.imageView);

        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao userinfo) {
                Glide.with(iv).load(userinfo.getChatHead())
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv);
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
