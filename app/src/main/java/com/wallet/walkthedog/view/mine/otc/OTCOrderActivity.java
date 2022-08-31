package com.wallet.walkthedog.view.mine.otc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.merchant.MerchantActivity;
import com.wallet.walkthedog.view.merchant.MerchantApplyActivity;
import com.wallet.walkthedog.view.mine.ad.ADAssetActivity;
import com.wallet.walkthedog.view.mine.ad.PlaceADActivity;

import java.util.ArrayList;
import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

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
        findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectWindow(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusMerchant();
    }

    private PopupWindow popupWindow;

    private void showSelectWindow(View v) {
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.simple_item_textview) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv, item);
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    onMenuSelect(position);
                }
            }
        });
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.buy_ads));
        items.add(getString(R.string.sell_ads));
        items.add(getString(R.string.my_ads));
        items.add(getString(R.string.my_order));

        if (isMerchant){
            items.add(getString(R.string.merchant_settle_out));
        }else {
            items.add(getString(R.string.how_to_pay));
        }

        adapter.setNewData(items);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_money_select, null, false);
        view.measure(View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenWidth(this), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenHeight(this), View.MeasureSpec.AT_MOST)
        );
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        popupWindow = new PopupWindow(view, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(false);
        int measuredWidth = view.getMeasuredWidth();
        int[] outLocation = new int[2];
        int screenWidth = ScreenUtils.getScreenWidth(this);
        v.getLocationInWindow(outLocation);
        popupWindow.showAsDropDown(v, -measuredWidth + (screenWidth - outLocation[0]), 0, Gravity.BOTTOM);
    }

    private void onMenuSelect(int position) {
        if (position == 0) {
            if (canTouch) {
                Intent intent = new Intent(this, PlaceADActivity.class);
                intent.putExtra("advertiseType", 0);
                startActivity(intent);
            } else {
                MerchantActivity.actionStart(this);
            }
        } else if (position == 1) {
            if (canTouch) {
                Intent intent = new Intent(this, PlaceADActivity.class);
                intent.putExtra("advertiseType", 1);
                startActivity(intent);
            } else {
                MerchantActivity.actionStart(this);
            }

        } else if (position == 2) {
            Intent intent = new Intent(this, ADAssetActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(this, ADAssetActivity.class);
            intent.putExtra("position",1);
            startActivity(intent);
        } else {
            MerchantActivity.actionStart(this);
        }
    }

    private boolean canTouch = true;
    private boolean isMerchant = false;

    private void statusMerchant() {
        WonderfulOkhttpUtils.get().url(UrlFactory.statusMerchantUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<MerchantStatusDao>>() {
                    @Override
                    protected void onRes(RemoteData<MerchantStatusDao> testRemoteData) {
                        int certifiedBusinessStatus = testRemoteData.getNotNullData().getCertifiedBusinessStatus();
                        canTouch = certifiedBusinessStatus == 2 || certifiedBusinessStatus == 5 || certifiedBusinessStatus == 6;
                        isMerchant = certifiedBusinessStatus == 2;
                    }
                });
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), this);
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

            Bundle bundle = new Bundle();
            if (position == 0) {
                bundle.putInt("advertiseType", 1);
            } else {
                bundle.putInt("advertiseType", 0);
            }
            OTCFragment otcFragment = new OTCFragment();
            otcFragment.setArguments(bundle);
            return otcFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
