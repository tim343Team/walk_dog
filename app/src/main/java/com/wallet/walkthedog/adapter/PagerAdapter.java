package com.wallet.walkthedog.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import tim.com.libnetwork.base.BaseFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> tabls;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabls) {
        super(fm);
        this.fragments = fragments;
        this.tabls = tabls;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabls == null || tabls.size() == 0) return super.getPageTitle(position);
        return tabls.get(position);
    }
}
