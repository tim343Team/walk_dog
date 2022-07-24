package com.wallet.walkthedog.view.invite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.InviteDogAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.AddFriendDialog;
import com.wallet.walkthedog.dialog.BuyOrRentDogDialog;
import com.wallet.walkthedog.dialog.HungryDialog;
import com.wallet.walkthedog.dialog.InviteMoreDialog;
import com.wallet.walkthedog.dialog.InvitedInforDialog;
import com.wallet.walkthedog.dialog.NicknameDialog;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailPresenter;
import com.wallet.walkthedog.view.invite_detail.InviteDetailActivity;
import com.wallet.walkthedog.view.props.ChoicePropsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class InviteActivity extends BaseActivity implements InviteContract.InviteView{
    @BindView(R.id.root_empty)
    View rootEmpty;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private InviteDogAdapter adapter;
    private List<FriendInfoDao> data = new ArrayList<>();
    private InviteContract.InvitePresenter presenter;
    private int pageNo=1;

    @OnClick({R.id.root_empty,R.id.img_add})
    void invitedFriends(){
        AddFriendDialog dialog = AddFriendDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new AddFriendDialog.OperateCallback() {
            @Override
            public void callback(String email) {
                //添加朋友接口
                presenter.sendFriendInvited(email);
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, InviteActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new InvitePresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        initRecyclerView();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        presenter.getFriendList(pageNo);
    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new InviteDogAdapter(R.layout.adapter_invite_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.setCallback(new InviteDogAdapter.OperateCallback() {
            @Override
            public void callback(FriendInfoDao dao) {
                InvitedInforDialog dialog = InvitedInforDialog.newInstance(dao);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
                dialog.setCallback(new InvitedInforDialog.OperateCallback() {
                    @Override
                    public void callback() {
                        //邀請
                    }
                });
                dialog.setMoreCallback(new InvitedInforDialog.MoreCallback() {
                    @Override
                    public void callback() {
                        //更多
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
                                        nicknameDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                });
                                moreDialog.dismiss();
                            }
                        });
                    }
                });
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InviteDetailActivity.actionStart(InviteActivity.this,"");
            }
        });
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getFriendpSuccess(List<FriendInfoDao> obj) {
        adapter.setEnableLoadMore(true);
        rootEmpty.setVisibility(View.GONE);
        if (pageNo == 1) {
            data.clear();
            if (obj.size() == 0) {
                rootEmpty.setVisibility(View.VISIBLE);
                adapter.loadMoreEnd();
            } else {
                this.data.addAll(obj);
            }
        } else {
            if (obj.size() != 0) {
                this.data.addAll(obj);
            } else {
                adapter.loadMoreEnd();
            }
        }
//        //TODO 測試數據
//        for (int i = 0; i < 10; i++) {
//            FriendInfoDao dogInfoDao = new FriendInfoDao();
//            data.add(dogInfoDao);
//        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendFriendInvitedSuccess(String data) {
        ToastUtils.shortToast(data);
    }

    @Override
    public void setPresenter(InviteContract.InvitePresenter presenter) {
        this.presenter=presenter;
    }
}
