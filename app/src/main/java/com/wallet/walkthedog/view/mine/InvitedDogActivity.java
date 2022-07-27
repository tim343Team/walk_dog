package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.FriendInfoListDao;
import com.wallet.walkthedog.dao.InviteInfoDao;
import com.wallet.walkthedog.dao.InviteInfoItem;
import com.wallet.walkthedog.dao.TotalWalkDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import java.util.Collections;
import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

/**
 * 好友列表及邀请记录
 */
public class InvitedDogActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvfriends;
    private TextView tvInviteOthers;
    private TextView tvBeInvited;
    private RecyclerView recyclerview;

    private FrindListAdapter adapter0;
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
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    private void onSelect() {
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(this);
            gridLayoutManager = new GridLayoutManager(this, 2);
        }
        if (selectPosition == 0) {
            selectTextView(tvfriends, tvInviteOthers, tvBeInvited);
            if (adapter0 == null){
                adapter0 = new FrindListAdapter();
            }
            recyclerview.setLayoutManager(gridLayoutManager);
            recyclerview.setAdapter(adapter0);
        } else if (selectPosition == 1) {
            selectTextView(tvInviteOthers, tvfriends, tvBeInvited);
            if (adapter1 == null) {
                adapter1 = new InviteAdapter(false);
            }
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(adapter1);
        } else {
            selectTextView(tvBeInvited, tvfriends, tvInviteOthers);
            if (adapter2 == null) {
                adapter2 = new InviteAdapter(true);
            }
            recyclerview.setLayoutManager(linearLayoutManager);
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
        if (selectPosition == 0) {
            if (adapter0.getData().isEmpty()){
                getFriendList();
            }
        } else if (selectPosition == 1) {
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

    private void onSuccessGetFriendList(FriendInfoListDao notNullData) {
        adapter0.setNewData(notNullData.getRecords());
    }

    private void getFriendList() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getFriendList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<FriendInfoListDao>>() {
                    @Override
                    protected void onRes(RemoteData<FriendInfoListDao> testRemoteData) {
                        onSuccessGetFriendList(testRemoteData.getNotNullData());
                    }

                });
    }

    private void getTogetherPage(int type) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getTogetherPage())
                .addParams("type", String.valueOf(type))
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
            super(R.layout.item_invite_info);
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


    static class FrindListAdapter extends BaseQuickAdapter<FriendInfoDao, BaseViewHolder> {

        public FrindListAdapter() {
            super(R.layout.item_invite_frinds);
        }

        @Override
        protected void convert(BaseViewHolder helper, FriendInfoDao item) {
            helper.setText(R.id.tv_name, item.getDogName());
            ImageView iv = helper.getView(R.id.iv_p);
            iv.setColorFilter(Color.parseColor("#4D67C1"));
            if (item.getSex() == 1) {
                helper.setImageResource(R.id.iv_p, R.mipmap.icon_black_male);
            } else {
                helper.setImageResource(R.id.iv_p, R.mipmap.icon_black_female);
            }
            Glide.with(helper.itemView.getContext())
                    .load(item.getImg())
                    .apply(RequestOptions.placeholderOf(R.mipmap.icon_null_dog))
                    .into((ImageView) helper.getView(R.id.iv_dog_pic));

            helper.setText(R.id.iv_lv, Utils.getFormat((int)(item.getLevel()),"LV.%d"));

            helper.setText(R.id.iv_name1,item.getName());
            helper.setText(R.id.tv_times,String.format(mContext.getString(R.string._time), item.getWalkTheDogCount()+""));
            helper.setText(R.id.tv_all_time,Utils.getTime(item.getWalkTheDogTime()));

            if(item.getStarvation()==1){
                helper.setTextColor(R.id.txt_status,Color.parseColor("#E51616"));
                helper.setText(R.id.txt_status,R.string.full_of_hunger);
            }else {
                helper.setTextColor(R.id.txt_status,Color.parseColor("#30B226"));
                helper.setText(R.id.txt_status,R.string.full_of_energy);
            }
        }
    }
}
