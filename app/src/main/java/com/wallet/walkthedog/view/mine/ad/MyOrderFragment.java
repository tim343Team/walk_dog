package com.wallet.walkthedog.view.mine.ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wallet.walkthedog.R;

public class MyOrderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_ad_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewpager = view.findViewById(R.id.viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                MyOrderListFragment myOrderListFragment = new MyOrderListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                myOrderListFragment.setArguments(bundle);
                return myOrderListFragment;
            }

            @Override
            public int getCount() {
                return 5;
            }


            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                   return getString(R.string.all);
                } else if (position==1){
                    return getString(R.string.unpaid);
                } else if (position==2){
                    return getString(R.string.paid);
                } else if (position==3){
                    return getString(R.string.completed);
                }
                return getString(R.string.cancle);
            }
        });
    }
}
