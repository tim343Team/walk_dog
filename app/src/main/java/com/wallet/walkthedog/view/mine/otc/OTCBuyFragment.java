package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class OTCBuyFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otc_buy, container, false);
    }

    private TabLayout tablayout;
    private ViewPager viewPager;
    private List<String> title = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tablayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        title.add("USDT");
        title.add("SUZU");
        title.add("SUZU");
        title.add("USDT");

        tablayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new PageAdapter(getChildFragmentManager(), title));
    }


    static class PageAdapter extends FragmentStatePagerAdapter {

        private final List<String> titles;

        public PageAdapter(@NonNull FragmentManager fm, List<String> titles) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.titles = titles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return OTCBuyListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
