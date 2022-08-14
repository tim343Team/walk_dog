package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseActivity;

public class MyOtherAssetActivity extends BaseActivity {

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_other_asset;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.llTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewPager viewpager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return MyAssetFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position==0){
                    return getString(R.string.asset_account);
                }
                return getString(R.string.otc_account);
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
