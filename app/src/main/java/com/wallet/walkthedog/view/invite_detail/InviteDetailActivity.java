package com.wallet.walkthedog.view.invite_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateFriendEvent;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dao.request.InviteRequest;
import com.wallet.walkthedog.dialog.IdentityDialog;
import com.wallet.walkthedog.dialog.InviteMoreDialog;
import com.wallet.walkthedog.dialog.NicknameDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.dialog.RemoveFriendDIalog;
import com.wallet.walkthedog.dialog.SettingInviteDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.DateTimeUtil;

public class InviteDetailActivity extends BaseActivity implements InviteDetailContract.InviteDetailView {
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;
    @BindView(R.id.txt_dog_name)
    TextView txtDogName;
    @BindView(R.id.img_gender)
    ImageView imgGender;
    @BindView(R.id.txt_level)
    TextView txtDogLevel;
    @BindView(R.id.txt_level_2)
    TextView txtDogLevel2;
    @BindView(R.id.txt_state)
    TextView txtState;//体力状态
    @BindView(R.id.txt_status)
    TextView txtStatus;//？？
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.txt_km)
    TextView txtKm;
    @BindView(R.id.txt_number)
    TextView txtTrip;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_number_t)
    TextView txtFrequency;
    @BindView(R.id.txt_number_tt)
    TextView txtFrequencyT;
    @BindView(R.id.txt_time_t)
    TextView txtTimeT;
    @BindView(R.id.txt_walk_t)
    TextView txtKmt;

    private InviteDetailContract.InviteDetailPresenter presenter;
    private FriendInfoDao friendInfoDao;
    private DogInfoDao mDefultDogInfo;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.img_identity)
    void startIdentity() {
        if (mDefultDogInfo == null) {
            return;
        }
        IdentityDialog dialog = IdentityDialog.newInstance(mDefultDogInfo);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @OnClick(R.id.txt_remove)
    void remove() {
        if (mDefultDogInfo == null) {
            return;
        }
        RemoveFriendDIalog dialog = RemoveFriendDIalog.newInstance(mDefultDogInfo.getFriendName(), mDefultDogInfo.getDogName(),
                mDefultDogInfo.getFriendWalkTheDogCount(), mDefultDogInfo.getFriendWalkTheDogTime());
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new RemoveFriendDIalog.OperateCallback() {
            @Override
            public void callback() {
                presenter.delFriend(new FriendRequest(friendInfoDao.getFriendMemberId() + ""));
//                PasswordDialog passwordDialog = PasswordDialog.newInstance();
//                passwordDialog.setTheme(R.style.PaddingScreen);
//                passwordDialog.setGravity(Gravity.CENTER);
//                passwordDialog.show(getSupportFragmentManager(), "edit");
//                passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
//                    @Override
//                    public void callback(String password) {
//                        //删除好友
//                        presenter.delFriend(new FriendRequest(friendInfoDao.getFriendMemberId()+""));
//                        dialog.dismiss();
//                        passwordDialog.dismiss();
//                    }
//                });
//                passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
//                    @Override
//                    public void callback() {
//                        ToastUtils.shortToast("错误");
//                    }
//                });
            }
        });
    }

    @OnClick(R.id.view_bottom)
    void settingInvited() {
        if (mDefultDogInfo == null) {
            return;
        }
        SettingInviteDialog dialog = SettingInviteDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new SettingInviteDialog.OperateCallback() {
            @Override
            public void callback(String startTime, String endTime) {
                //发起邀请接口
                //2022-07-26    2022-07-28
                presenter.addTogether(new InviteRequest(mDefultDogInfo.getFriendMemberId(), startTime, endTime));
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.img_more)
    void more() {
        InviteMoreDialog moreDialog = InviteMoreDialog.newInstance();
        moreDialog.setTheme(R.style.PaddingScreen);
        moreDialog.setGravity(Gravity.CENTER);
        moreDialog.show(getSupportFragmentManager(), "edit");
        moreDialog.setCallback(new InviteMoreDialog.OperateCallback() {
            @Override
            public void callback() {
                NicknameDialog nicknameDialog = NicknameDialog.newInstance();
                nicknameDialog.setTheme(R.style.PaddingScreen);
                nicknameDialog.setGravity(Gravity.CENTER);
                nicknameDialog.show(getSupportFragmentManager(), "edit");
                nicknameDialog.setCallback(new NicknameDialog.OperateCallback() {
                    @Override
                    public void callback(String name) {
                        FriendRequest request = new FriendRequest(friendInfoDao.getFriendListId() + "", name);
                        presenter.setNote(request);
                        nicknameDialog.dismiss();
                    }
                });
                moreDialog.dismiss();
            }
        });
    }

    public static void actionStart(Activity activity, FriendInfoDao friendInfoDao) {
        Intent intent = new Intent(activity, InviteDetailActivity.class);
        intent.putExtra("friendInfoDao", friendInfoDao);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invite_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new InviteDetailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        friendInfoDao = (FriendInfoDao) getIntent().getSerializableExtra("friendInfoDao");
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        if (friendInfoDao == null) {
            return;
        }
        presenter.getFriendDogDetail(friendInfoDao.getId() + "");
    }

    //设置进度条
    private int progressAll = 0;

    //设置进度条
    private void setProgress(View progressBg, View progressBar, TextView progressTxt, double percentage) {
        progressBg.post(new Runnable() {
            @Override
            public void run() {
                int progressAll = progressBg.getWidth();
                int progress = (int) (progressAll * percentage);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
                params.width = progress;
                progressBar.setLayoutParams(params);
                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
                if (percentage < 0.2) {
                    params2.leftMargin = 0;
                } else if (percentage > 0.9) {
                    params2.leftMargin = (int) (progress - progressAll * 0.3);
                } else {
                    params2.leftMargin = (int) (progress - progressAll * 0.18);
                }
                progressTxt.setLayoutParams(params2);
                progressTxt.setText(percentage * 100 + "%");
            }
        });
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getSuccess(DogInfoDao data) {
        mDefultDogInfo = data;
        if (mDefultDogInfo.getSex() == 0) {
            imgGender.setBackgroundResource(R.mipmap.icon_female);
        } else {
            imgGender.setBackgroundResource(R.mipmap.icon_male);
        }
        if (mDefultDogInfo.getStarvation() == 2) {
            txtState.setText(R.string.full_of_energy);
            txtState.setTextColor(getResources().getColor(R.color.white));
        } else if (mDefultDogInfo.getStarvation() == 1) {
            txtState.setText(R.string.full_of_hunger);
            txtState.setTextColor(getResources().getColor(R.color.color_ffbe0d));
        } else {
            txtState.setText("状态字段返回错误为：" + mDefultDogInfo.getStarvation());
        }
        txtDogLevel2.setText("LEVEL " + mDefultDogInfo.getLevel());
        txtDogLevel.setText("Lv. " + mDefultDogInfo.getLevel());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(this).load(mDefultDogInfo.getImg()).apply(options).into(imgDog);
        setProgress(progressBg, progressBar, progressTxt, mDefultDogInfo.getRateOfProgress() / 100.00);
        //
        txtKm.setText(mDefultDogInfo.getWalkTheDogKm() + "Km");
        txtTrip.setText(mDefultDogInfo.getWalkTheDogTime() + "");
        txtTrip.setText(DateTimeUtil.second2Time(Long.valueOf(mDefultDogInfo.getWalkTheDogTime())));
        txtTime.setText(mDefultDogInfo.getDayLimit() + "/2");
        txtFrequencyT.setText(String.format(getString(R.string._time), mDefultDogInfo.getWalkTheDogCount() + ""));
        //
        txtFrequency.setText(String.format(getString(R.string._time), mDefultDogInfo.getFriendWalkTheDogCount()));
        txtTimeT.setText(DateTimeUtil.second2Time(Long.valueOf(mDefultDogInfo.getFriendWalkTheDogTime())));
        txtKmt.setText(mDefultDogInfo.getFriendWalkTheDogTime() + "Km");

    }

    @Override
    public void setNoteSuccess(String data) {
        //
        NormalDialog dialog = NormalDialog.newInstance(R.string.operation_successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void delSuccess(String data) {
        //
        NormalDialog dialog = NormalDialog.newInstance(R.string.operation_successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //刷新好友列表
        EventBus.getDefault().post(new UpdateFriendEvent());
        EventBus.getDefault().post(new UpdateHomeData());
        finish();
    }

    @Override
    public void addTogetherSuccess(String data) {
        NormalDialog dialog = NormalDialog.newInstance(R.string.successful_invite, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void addTogetherFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void setPresenter(InviteDetailContract.InviteDetailPresenter presenter) {
        this.presenter = presenter;
    }
}
