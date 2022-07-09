package com.wallet.walkthedog.view.mail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.PagerAdapter;
import com.wallet.walkthedog.custom_view.NoScrollViewPager;
import com.wallet.walkthedog.view.home.HomeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseTransFragment;

public class MailFragment extends BaseTransFragment {
    public static final String TAG = MailFragment.class.getSimpleName();
    @BindViews({R.id.txt_dog, R.id.txt_prop})
    TextView[] tvTabs;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;

    private PagerAdapter adapter;
    private DogMailFragment subFragment1;
    private PropMailFragment subFragment2;
    private ArrayList<String> tabs = new ArrayList<>();

    @OnClick(R.id.txt_dog)
    void clickDog(){
        showTab(0);
    }

    @OnClick(R.id.txt_prop)
    void clickProp(){
        showTab(1);
    }

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mail;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setView();
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

    private void setView() {
        tabs.clear();
        fragments.clear();
        for (TextView textView : tvTabs) {
            tabs.add("");
        }
        fragments.add(subFragment1 = DogMailFragment.getInstance());
        fragments.add(subFragment2 = PropMailFragment.getInstance());
        if (adapter == null) {
            adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabs);
            viewpager.setAdapter(adapter);
            viewpager.setOffscreenPageLimit(fragments.size() - 1);
            viewpager.setCurrentItem(0);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    void showTab(int position) {
        for (int i = 0; i < tvTabs.length; i++) {
            if (i == position) {
                tvTabs[i].setBackgroundResource(R.drawable.button_gradual_background_round);
                tvTabs[i].setTextColor(ContextCompat.getColor(getmActivity(), R.color.white));
            } else {
                tvTabs[i].setBackgroundColor(ContextCompat.getColor(getmActivity(), R.color.white));
                tvTabs[i].setTextColor(ContextCompat.getColor(getmActivity(), R.color.color_ADAEB3));
            }
        }
        viewpager.setCurrentItem(position);
    }
}
