package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.home.HomeFragment;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragment;

public class MineFragment extends BaseTransFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.avatar)
    ImageView ivHeader;

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void init() {
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),AvatarActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }
}
