package com.wallet.walkthedog.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.InviteRegisterDao;
import com.wallet.walkthedog.dao.InviteRegisterItem;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class InviteRegisterActivity extends BaseActivity {
    private InviteListAdapter adapter;
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invite_to_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        View layoutInvideCode = findViewById(R.id.layout_invite_code);
        layoutInvideCode.setVisibility(View.INVISIBLE);
        TextView tv_code = findViewById(R.id.tv_code);
        layoutInvideCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.copyText(v.getContext(), tv_code.getText().toString());
            }
        });
        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao dao) {
                String invitationCode = dao.getInvitationCode();
                if (!TextUtils.isEmpty(invitationCode)) {
                    tv_code.setText(invitationCode);
                    layoutInvideCode.setVisibility(View.VISIBLE);
                }
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InviteListAdapter();
        TextView textView = new TextView(this);
        textView.setText(getString(R.string.no_invite_register_list));
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        adapter.setEmptyView(textView);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getInviteRegisterList();
    }

    private void getInviteRegisterList() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getInviteRegisterList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<InviteRegisterDao>>() {
                    @Override
                    protected void onRes(RemoteData<InviteRegisterDao> testRemoteData) {
                        onSuccessGetInviteRegisterList(testRemoteData.getNotNullData());
                    }

                });
    }

    private void onSuccessGetInviteRegisterList(InviteRegisterDao notNullData) {
        adapter.setNewData(notNullData.getRecords());
    }

    static class InviteListAdapter extends BaseQuickAdapter<InviteRegisterItem,BaseViewHolder> {


        public InviteListAdapter() {
            super(R.layout.item_invite_register);
        }

        @Override
        protected void convert(BaseViewHolder helper, InviteRegisterItem item) {
            helper.setText(R.id.tv_1,item.getMemberName());
            helper.setText(R.id.tv_2,item.getTime());
        }
    }
}
