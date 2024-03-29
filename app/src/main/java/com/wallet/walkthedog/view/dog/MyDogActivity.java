package com.wallet.walkthedog.view.dog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.PagerAdapter;
import com.wallet.walkthedog.custom_view.NoScrollViewPager;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.view.dog.fragment.MyDogFragment;
import com.wallet.walkthedog.view.dog.fragment.SellDogFragment;
import com.wallet.walkthedog.view.props.MyPropsActivity;
import com.wallet.walkthedog.view.props.fragment.MyPropsFragment;
import com.wallet.walkthedog.view.props.fragment.SellPropsFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class MyDogActivity extends BaseActivity {
    @BindViews({R.id.txt_dog, R.id.txt_sell})
    TextView[] tvTabs;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;

    public boolean isChange = false;
    private PagerAdapter adapter;
    private MyDogFragment subFragment1;
    private SellDogFragment subFragment2;
    private ArrayList<String> tabs = new ArrayList<>();
    protected List<Fragment> fragments = new ArrayList<>();

    @OnClick(R.id.txt_dog)
    void clickDog() {
        showTab(0);
    }

    @OnClick(R.id.txt_sell)
    void clickProp() {
        showTab(1);
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyDogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_dog;
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
    public void finish() {
        if (isChange)
            EventBus.getDefault().post(new UpdateHomeData());
        super.finish();
    }

    private void setView() {
        tabs.clear();
        fragments.clear();
        for (TextView textView : tvTabs) {
            tabs.add("");
        }
        fragments.add(subFragment1 = MyDogFragment.getInstance());
        fragments.add(subFragment2 = SellDogFragment.getInstance());
        if (adapter == null) {
            adapter = new PagerAdapter(getSupportFragmentManager(), fragments, tabs);
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
                tvTabs[i].setTextColor(ContextCompat.getColor(this, R.color.color_4D67C1));
            } else {
                tvTabs[i].setTextColor(ContextCompat.getColor(this, R.color.color_ADAEB3));
            }
        }
        viewpager.setCurrentItem(position);
    }
}
