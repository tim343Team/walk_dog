package com.wallet.walkthedog.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.mail.MailFragment;
import com.wallet.walkthedog.view.mine.MineFragment;
import com.wallet.walkthedog.view.rental.RentalFragment;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragmentActivity;

public class HomeActivity extends BaseTransFragmentActivity {
    public static HomeActivity instance = null;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.llOne)
    LinearLayout llOne;
    @BindView(R.id.llTwo)
    LinearLayout llTwo;
    @BindView(R.id.llThree)
    LinearLayout llThree;
    @BindView(R.id.llFour)
    LinearLayout llFour;

    private LinearLayout[] lls;
    private int currentPage;
    private HomeFragment oneFragment;
    private MailFragment twoFragment;
    private RentalFragment threeFragment;
    private MineFragment fourFragment;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void recoverFragment() {
        oneFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        twoFragment = (MailFragment) getSupportFragmentManager().findFragmentByTag(MailFragment.TAG);
        threeFragment = (RentalFragment) getSupportFragmentManager().findFragmentByTag(RentalFragment.TAG);
        fourFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag(MineFragment.TAG);

        if (oneFragment == null) {
            fragments.add(oneFragment = new HomeFragment());
        } else {
            fragments.add(oneFragment);
        }
        if (twoFragment == null) {
            fragments.add(twoFragment = new MailFragment());
        } else {
            fragments.add(twoFragment);
        }
        if (threeFragment == null) {
            fragments.add(threeFragment = new RentalFragment());
        } else {
            fragments.add(threeFragment);
        }
        if (fourFragment == null) {
            fragments.add(fourFragment = new MineFragment());
        } else {
            fragments.add(fourFragment);
        }
    }

    @Override
    protected void initFragments() {
        if (oneFragment == null) {
            fragments.add(oneFragment = new HomeFragment());
        }
        if (twoFragment == null) {
            fragments.add(twoFragment = new MailFragment());
        }
        if (threeFragment == null) {
            fragments.add(threeFragment = new RentalFragment());
        }
        if (fourFragment == null) {
            fragments.add(fourFragment = new MineFragment());
        }
    }

    @Override
    public int getContainerId() {
        return R.id.flContainer;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        int page = savedInstanceState.getInt("page");
        selecte(lls[page], page);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activiy_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
        lls = new LinearLayout[]{llOne, llTwo, llThree, llFour};
        llOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 0);
            }
        });
        llTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 1);
            }
        });
        llThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 2);
            }
        });
        llFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 3);
            }
        });
        if (fragments.size() == 0) {
            recoverFragment();
        }
        if (savedInstanceState == null) {
            hideFragment(oneFragment);
            selecte(llOne, 0);
        }
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

    public void selecte(View v, int page) {
        currentPage = page;
        llOne.setSelected(false);
        llTwo.setSelected(false);
        llThree.setSelected(false);
        llFour.setSelected(false);
        v.setSelected(true);
        showFragment(fragments.get(page));
    }
}
