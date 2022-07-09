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
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.HungryDialog;
import com.wallet.walkthedog.dialog.InviteMoreDialog;
import com.wallet.walkthedog.dialog.InvitedInforDialog;
import com.wallet.walkthedog.dialog.NicknameDialog;
import com.wallet.walkthedog.view.invite_detail.InviteDetailActivity;
import com.wallet.walkthedog.view.props.ChoicePropsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class InviteActivity extends BaseActivity {
    @BindView(R.id.root_empty)
    View rootEmpty;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private InviteDogAdapter adapter;
    private List<DogInfoDao> data = new ArrayList<>();

    @OnClick(R.id.root_empty)
    void invitedFriends(){

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

    }

    private void initRecyclerView() {
        //TODO 測試數據
        for (int i = 0; i < 10; i++) {
            DogInfoDao dogInfoDao = new DogInfoDao();
            data.add(dogInfoDao);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new InviteDogAdapter(R.layout.adapter_invite_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.setCallback(new InviteDogAdapter.OperateCallback() {
            @Override
            public void callback(DogInfoDao dao) {
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
}
