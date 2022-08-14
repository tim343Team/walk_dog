package com.wallet.walkthedog.view.mine.otc;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.wallet.walkthedog.dao.CoinNameItem;
import com.wallet.walkthedog.dao.ContryItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.List;

import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

/**
 * 买otc
 */
public class OTCFragment extends Fragment {
    private TextView tv_m_select;
    private int countrySelect = 0;
    private List<ContryItem> contryItems = new ArrayList<>();
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otc_buy, container, false);
    }

    private final List<CoinNameItem> title = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tablayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        tv_m_select = view.findViewById(R.id.tv_m_select);
        tv_m_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contryItems.isEmpty()) {
                    showSelectWindow();
                }
            }
        });
        tablayout.setupWithViewPager(viewPager);
        requestCoinList();
        requestCountryList();
    }

    private PopupWindow popupWindow;

    private void showSelectWindow() {
        BaseQuickAdapter<ContryItem, BaseViewHolder> adapter = new BaseQuickAdapter<ContryItem, BaseViewHolder>(R.layout.simple_item_textview) {
            @Override
            protected void convert(BaseViewHolder helper, ContryItem item) {
                String name = item.getLocalCurrency();
                helper.setText(R.id.tv, name);
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    countrySelect = position;
                    onSelectFaBi();
                }
            }
        });
        adapter.setNewData(contryItems);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.pop_money_select, null, false);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        ViewGroup.LayoutParams layoutParams = recyclerview.getLayoutParams();
        layoutParams.width = ScreenUtils.dip2px(requireContext(), 70);

        recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerview.setAdapter(adapter);
        popupWindow = new PopupWindow(view, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(false);
        view.measure(View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenWidth(requireContext()), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenHeight(requireContext()), View.MeasureSpec.AT_MOST)
        );
        int[] outLocation = new int[2];
        int screenWidth = ScreenUtils.getScreenWidth(requireContext());
        tv_m_select.getLocationInWindow(outLocation);
        int measuredWidth = view.getMeasuredWidth();
        popupWindow.showAsDropDown(tv_m_select, -measuredWidth + (screenWidth - outLocation[0]), 0, Gravity.BOTTOM);
    }

    private void onSelectFaBi() {
        tv_m_select.setText(contryItems.get(countrySelect).getLocalCurrency());
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof ISelectFaBi) {
                ((ISelectFaBi) fragment).onSelect(contryItems.get(countrySelect));
            }
        }
    }

    @Nullable
    public ContryItem getCountry() {
        if (contryItems.isEmpty()) {
            return null;
        }
        return contryItems.get(countrySelect);
    }

    private void requestCoinList() {
        WonderfulOkhttpUtils.post().url(UrlFactory.coinList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<CoinNameItem>>>() {
                    @Override
                    protected void onRes(RemoteData<List<CoinNameItem>> testRemoteData) {
                        onRequestCoinList(testRemoteData.getNotNullData());
                    }
                });
    }

    private void requestCountryList() {
        WonderfulOkhttpUtils.get().url(UrlFactory.countryList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<ContryItem>>>() {
                    @Override
                    protected void onRes(RemoteData<List<ContryItem>> testRemoteData) {
                        onRequestFaCoinList(testRemoteData.getNotNullData());
                    }
                });
    }

    private void onRequestFaCoinList(List<ContryItem> notNullData) {
        contryItems.clear();
        this.contryItems = notNullData;
        onSelectFaBi();
    }

    private void onRequestCoinList(List<CoinNameItem> notNullData) {
        title.clear();
        title.addAll(notNullData);
        title.add(new CoinNameItem());
        assert getArguments() != null;
        viewPager.setOffscreenPageLimit(title.size()-1);
        viewPager.setAdapter(new PageAdapter(getChildFragmentManager(), title, getArguments().getInt("advertiseType")));
    }

    interface ISelectFaBi {
        void onSelect(ContryItem item);
    }


    static class PageAdapter extends FragmentStatePagerAdapter {

        private final List<CoinNameItem> titles;
        private final int advertiseType;

        public PageAdapter(@NonNull FragmentManager fm, List<CoinNameItem> titles, int advertiseType) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.advertiseType = advertiseType;
            this.titles = titles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == titles.size() - 1) {
                return SUZUFragment.newInstance(advertiseType);
            }
            return OTCListFragment.newInstance(titles.get(position), advertiseType);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position==titles.size()-1){
                return "SUZU";
            }
            return titles.get(position).getName();
        }
    }

}
