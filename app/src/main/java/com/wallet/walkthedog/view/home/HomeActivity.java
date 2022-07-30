package com.wallet.walkthedog.view.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.login.LoginActivity;
import com.wallet.walkthedog.view.mail.MailFragment;
import com.wallet.walkthedog.view.mine.MineFragment;
import com.wallet.walkthedog.view.rental.RentalFragment;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragmentActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

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
    /*权限请求Code*/
    private final static int PERMISSION_REQUEST_CODE = 1234;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[2]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[3]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void loadData() {
        WonderfulOkhttpUtils.get().url(UrlFactory.userinfo())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<UserInfoDao>>() {
                    @Override
                    protected void onRes(RemoteData<UserInfoDao> testRemoteData) {
                        onSuccessUserInfo(testRemoteData.getNotNullData());
                    }
                });
    }

    private void onSuccessUserInfo(UserInfoDao userinfo) {
        SharedPrefsHelper.getInstance().saveUserInfo(userinfo);
    }

    public void selecte(View v, int page) {
        currentPage = page;
        llOne.setSelected(false);
        llTwo.setSelected(false);
        llThree.setSelected(false);
        llFour.setSelected(false);
        v.setSelected(true);

        for (int i = 0; i < fragments.size(); i++) {
            fragments.get(i).onHiddenChanged(true);
        }
        fragments.get(page).onHiddenChanged(false);
        showFragment(fragments.get(page));
    }

    //权限反馈
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                /*PackageManager.PERMISSION_GRANTED  权限被许可*/
                /*PackageManager.PERMISSION_DENIED  没有权限；拒绝访问*/
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeActivity.this, "无法使用相机！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeActivity.this, "无法录制音频！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
