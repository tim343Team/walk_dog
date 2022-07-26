package com.wallet.walkthedog.view.invite_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateFriendEvent;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dialog.IdentityDialog;
import com.wallet.walkthedog.dialog.InviteMoreDialog;
import com.wallet.walkthedog.dialog.NicknameDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.dialog.RemoveFriendDIalog;
import com.wallet.walkthedog.dialog.SettingInviteDialog;
import com.wallet.walkthedog.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class InviteDetailActivity extends BaseActivity implements InviteDetailContract.InviteDetailView {
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;

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
        RemoveFriendDIalog dialog = RemoveFriendDIalog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new RemoveFriendDIalog.OperateCallback() {
            @Override
            public void callback() {
                PasswordDialog passwordDialog = PasswordDialog.newInstance();
                passwordDialog.setTheme(R.style.PaddingScreen);
                passwordDialog.setGravity(Gravity.CENTER);
                passwordDialog.show(getSupportFragmentManager(), "edit");
                passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                    @Override
                    public void callback() {
                        //删除好友
                        presenter.delFriend(new FriendRequest(friendInfoDao.getFriendMemberId()+""));
                        dialog.dismiss();
                        passwordDialog.dismiss();
                    }
                });
                passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                    @Override
                    public void callback() {
                        ToastUtils.shortToast("错误");
                    }
                });
            }
        });
    }

    @OnClick(R.id.view_bottom)
    void settingInvited() {
        SettingInviteDialog dialog = SettingInviteDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new SettingInviteDialog.OperateCallback() {
            @Override
            public void callback() {
                //TODO 发起邀请接口
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
                        FriendRequest request = new FriendRequest(friendInfoDao.getFriendListId()+"",name);
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
        if(friendInfoDao==null){
            return;
        }
        presenter.getFriendDogDetail(friendInfoDao.getId()+"");
    }

    //设置进度条
    private int progressAll = 0;

    private void setProgress(double percentage) {
        int progress = (int) (progressAll * percentage);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
        params.width = progress;
        progressBar.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
        if (percentage < 0.2) {
            params2.leftMargin = 0;
        } else {
            params2.leftMargin = (int) (progress - progressAll * 0.18);
        }
        progressTxt.setLayoutParams(params2);
        progressTxt.setText(percentage * 100 + "%");
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getSuccess(DogInfoDao data) {
        mDefultDogInfo = data;
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
        finish();
    }

    @Override
    public void setPresenter(InviteDetailContract.InviteDetailPresenter presenter) {
        this.presenter = presenter;
    }
}
