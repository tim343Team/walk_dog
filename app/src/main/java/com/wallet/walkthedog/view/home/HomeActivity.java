package com.wallet.walkthedog.view.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.ActivityLifecycleManager;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.DogFoodWeightItemDao;
import com.wallet.walkthedog.dao.InviteNoticeDao;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.dao.request.AwardRequest;
import com.wallet.walkthedog.dialog.InviteNoticeDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.SettingInviteDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailPresenter;
import com.wallet.walkthedog.view.login.LoginActivity;
import com.wallet.walkthedog.view.mail.MailFragment;
import com.wallet.walkthedog.view.mine.MineFragment;
import com.wallet.walkthedog.view.rental.RentalFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragmentActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class HomeActivity extends BaseTransFragmentActivity implements HomeMainContract.HomeMainView {
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
    private HomeMainContract.HomeMainPresenter presenter;
    private HomeFragment oneFragment;
    private MailFragment twoFragment;
    private RentalFragment threeFragment;
    private MineFragment fourFragment;
    public int type = -1;
    public List<InviteNoticeDao> inviteNoticeDaos = new ArrayList<>();
    /*权限请求Code*/
    private final static int PERMISSION_REQUEST_CODE = 1234;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //定时检查是否获取有邀请信息
            try {
                presenter.getNewTogethersUrl();
                mHandler.postDelayed(this, 15000);
            } catch (Exception e) {
                mHandler.postDelayed(this, 15000);
            }
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("type", -1);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, int type) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("type", type);
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
        type = getIntent().getIntExtra("type", -1);
        presenter = new HomeMainPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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


    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable, 1000);
        if (type == -1) {
            return;//默认值 或是 不需要跳转 就返回
        } else if (type == 0 || type == 1) {
            //跳转到商城页面
            showMailFragment(1);
            selecte(llTwo, 1);
            //TODO 设置跳转到狗狗还是道具页
            if (twoFragment != null) {
                twoFragment.showPosition = type;
                twoFragment.showTab(type);
            }
        }
        type = -1;
        getIntent().putExtra("type", -1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(runnable);
        super.onPause();
    }

    private void onSuccessUserInfo(UserInfoDao userinfo) {
        SharedPrefsHelper.getInstance().saveUserInfo(userinfo);
    }

    private void getInviteNotice(List<InviteNoticeDao> daos) {
        if (daos.size() > 0) {
            inviteNoticeDaos.addAll(daos);
            showInvitedDialog();
        }
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

    public void showMailFragment(int position) {
        currentPage = position;
        llOne.setSelected(false);
        llTwo.setSelected(true);
        llThree.setSelected(false);
        MailFragment fragment = (MailFragment) fragments.get(position);
        showFragment(fragment);
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

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void ideaTogetherSuccessful(String data, int status) {
        if (status == 1) {
            //接受邀请
            NormalDialog dialog = NormalDialog.newInstance(R.string.accept_invitation, R.mipmap.icon_normal);
            dialog.setTheme(R.style.PaddingScreen);
            dialog.setGravity(Gravity.CENTER);
            dialog.show(getSupportFragmentManager(), "edit");
        } else if (status == 3) {
            //取消邀请
            NormalDialog dialog = NormalDialog.newInstance(R.string.refuse_invitation, R.mipmap.icon_normal);
            dialog.setTheme(R.style.PaddingScreen);
            dialog.setGravity(Gravity.CENTER);
            dialog.show(getSupportFragmentManager(), "edit");
        }
    }

    @Override
    public void getNewTogethersSuccessful(List<InviteNoticeDao> noticeDaoList) {
        if (noticeDaoList.size() > 0) {
            getInviteNotice(noticeDaoList);
        }
    }

    @Override
    public void setPresenter(HomeMainContract.HomeMainPresenter presenter) {
        this.presenter = presenter;
    }

    private void showInvitedDialog() {
        //弹出邀请信息
        try {
            for (InviteNoticeDao dao : inviteNoticeDaos) {
                if (!dao.isShow()) {
                    continue;
                }
                InviteNoticeDialog dialog = InviteNoticeDialog.newInstance(dao);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
                dialog.setRefuseCallback(new InviteNoticeDialog.OperateRefuseCallback() {
                    @Override
                    public void callback() {
                        //拒绝邀请
                        presenter.ideaTogether(dao.getId() + "", 3);
                        dialog.dismiss();
                    }
                });
                dialog.setAcceptCallback(new InviteNoticeDialog.OperateAcceptCallback() {
                    @Override
                    public void callback() {
                        //接受邀请
                        presenter.ideaTogether(dao.getId() + "", 1);
                        dialog.dismiss();
                    }
                });
                dao.setShow(false);
            }
//            InviteNoticeDao dao=new InviteNoticeDao();
//            InviteNoticeDialog dialog = InviteNoticeDialog.newInstance(dao);
//            dialog.setTheme(R.style.PaddingScreen);
//            dialog.setGravity(Gravity.CENTER);
//            dialog.show(getSupportFragmentManager(), "edit");
//            dialog.setRefuseCallback(new InviteNoticeDialog.OperateRefuseCallback() {
//                @Override
//                public void callback() {
//                    //拒绝邀请
//                    presenter.ideaTogether(dao.getId() + "", 3);
//                    dialog.dismiss();
//                }
//            });
//            dialog.setAcceptCallback(new InviteNoticeDialog.OperateAcceptCallback() {
//                @Override
//                public void callback() {
//                    //接受邀请
//                    presenter.ideaTogether(dao.getId() + "", 1);
//                    dialog.dismiss();
//                }
//            });
//            dao.setShow(false);
        } catch (Exception e) {

        }
    }
}
