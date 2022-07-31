package com.wallet.walkthedog.view.mine.otc;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wallet.walkthedog.R;

import java.util.ArrayList;
import java.util.List;

import tim.com.libnetwork.base.BaseActivity;

public class OTCOrderActivity extends BaseActivity {
    TabLayout tablayout;
    ViewPager viewpager;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_otc_order;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),this);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(pageAdapter);
    }

    @Override
    protected void loadData() {

    }

    static class PageAdapter extends FragmentStatePagerAdapter {
        List<String> title = new ArrayList<>();

        public PageAdapter(@NonNull FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            title.add(context.getString(R.string.buy));
            title.add(context.getString(R.string.sell));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new OTCBuyFragment();
            }
            return new OTCSellFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
