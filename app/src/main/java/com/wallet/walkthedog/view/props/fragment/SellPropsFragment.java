package com.wallet.walkthedog.view.props.fragment;

import android.os.Bundle;

import com.wallet.walkthedog.R;

import tim.com.libnetwork.base.BaseLazyFragment;

public class SellPropsFragment extends BaseLazyFragment {

    public static SellPropsFragment getInstance() {
        SellPropsFragment fragment = new SellPropsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sell_props;
    }

    @Override
    protected void init() {

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
