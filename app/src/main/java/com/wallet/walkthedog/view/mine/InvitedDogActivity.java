package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;
import com.wallet.walkthedog.dao.InviteInfoDao;
import com.wallet.walkthedog.dao.InviteInfoItem;
import com.wallet.walkthedog.dao.TotalWalkDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import java.util.Collections;
import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

public class InvitedDogActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvfriends;
    private TextView tvInviteOthers;
    private TextView tvBeInvited;
    private RecyclerView recyclerview;
    private InviteAdapter adapter1;
    private InviteAdapter adapter2;


    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invited_dog;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        recyclerview = findViewById(R.id.recyclerview);
        tvfriends = findViewById(R.id.tv_friends);
        tvInviteOthers = findViewById(R.id.tv_invite_others);
        tvBeInvited = findViewById(R.id.tv_be_invited);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        tvfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 0) {
                    selectPosition = 0;
                    onSelect();
                }
            }
        });

        tvInviteOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 1) {
                    selectPosition = 1;
                    onSelect();
                }
            }
        });
        tvBeInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 2) {
                    selectPosition = 2;
                    onSelect();
                }
            }
        });
        onSelect();
    }

    private void onSelect() {
        if (selectPosition == 0) {
            selectTextView(tvfriends, tvInviteOthers, tvBeInvited);


        } else if (selectPosition == 1) {
            selectTextView(tvInviteOthers, tvfriends, tvBeInvited);
            if (adapter1 == null) {
                adapter1 = new InviteAdapter(false);
            }
            recyclerview.setAdapter(adapter1);
        } else {
            selectTextView(tvBeInvited, tvfriends, tvInviteOthers);
            if (adapter2 == null) {
                adapter2 = new InviteAdapter(true);
            }
            recyclerview.setAdapter(adapter2);
        }


        requestData();
    }

    private void selectTextView(TextView select, TextView unselect, TextView unSelect1) {
        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#56758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardBackgroundColor(Color.parseColor("#2950A6"))
                .setCardCornerRadius(ScreenUtils.dip2px(this, 10))
                .create()
                .bindView(select);
        select.setTextColor(Color.WHITE);

        unselect.setBackground(null);
        unselect.setTextColor(Color.parseColor("#ADAEB3"));

        unSelect1.setBackground(null);
        unSelect1.setTextColor(Color.parseColor("#ADAEB3"));
    }

    private void requestData() {
        if (selectPosition == 1) {
            getTogetherPage(0);
        } else if (selectPosition == 2) {
            getTogetherPage(1);
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


    private void getTogetherPage(int type) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getTogetherPage())
                .addParams("type",String.valueOf(type))
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<InviteInfoDao>>() {
                    @Override
                    protected void onRes(RemoteData<InviteInfoDao> testRemoteData) {
                        onSuccessGetTogetherPage(testRemoteData.getNotNullData());
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    private void onSuccessGetTogetherPage(InviteInfoDao dao) {

    }


    //class FriendsListAdapter extends BaseQuickAdapter<>


    static class InviteAdapter extends BaseQuickAdapter<InviteInfoItem, BaseViewHolder> {

        private final boolean showAction;

        public InviteAdapter(boolean showAction) {
            super(R.layout.item_invite_info, Collections.emptyList());
            this.showAction = showAction;
        }


        @Override
        protected void convert(BaseViewHolder helper, InviteInfoItem item) {
            helper.setText(R.id.tv_dog_name, item.getFriendName());
            helper.setText(R.id.tv_state, item.getStatus());
            helper.setText(R.id.tv_time, item.getCreateTime());

            if (showAction) {
                helper.setGone(R.id.tv_refuse, true);
                helper.setGone(R.id.tv_accept, true);
            } else {
                //根据状态来
            }
        }
    }
}
