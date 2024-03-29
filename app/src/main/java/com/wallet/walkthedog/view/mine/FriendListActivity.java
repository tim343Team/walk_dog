package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.FriendInfoListDao;
import com.wallet.walkthedog.dao.InviteFriendDao;
import com.wallet.walkthedog.dao.InviteFriendItem;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.data.DataRepository;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.dialog.AddFriendDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.SettingInviteDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.invite_detail.InviteDetailActivity;

import java.util.Objects;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

/**
 * 好友列表及邀请记录
 */
public class FriendListActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvfriends;
    private TextView tvBeInvited;
    private RecyclerView recyclerview;

    private FrindListAdapter adapter0;
    private InviteAdapter adapter1;
    private View emptyView;


    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_friend_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        recyclerview = findViewById(R.id.recyclerview);
        emptyView = findViewById(R.id.layout_add);

        tvfriends = findViewById(R.id.tv_friends);
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
        tvBeInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != 1) {
                    selectPosition = 1;
                    onSelect();
                }
            }
        });

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        findViewById(R.id.img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        onSelect();
    }

    DataRepository repository;


    //添加好友
    private void showAddDialog() {
        if (repository == null) {
            repository = Injection.provideTasksRepository(getApplicationContext());
        }
        AddFriendDialog addFriendDialog = AddFriendDialog.newInstance();
        addFriendDialog.setGravity(Gravity.CENTER);
        addFriendDialog.setCallback(new AddFriendDialog.OperateCallback() {
            @Override
            public void callback(String email) {
                displayLoadingPopup();
                repository.friendEmail(email, new DataSource.DataCallback() {
                    @Override
                    public void onDataLoaded(Object obj) {
                        hideLoadingPopup();
                        addFriendDialog.dismiss();

                        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
                        dialog.setTheme(R.style.PaddingScreen);
                        dialog.setGravity(Gravity.CENTER);
                        dialog.show(getSupportFragmentManager(), "edit");
                        onSelect();
                    }

                    @Override
                    public void onDataNotAvailable(Integer code, String toastMessage) {
                        hideLoadingPopup();
                        ToastUtils.shortToast(toastMessage);
                    }
                });
            }
        });
        addFriendDialog.show(getSupportFragmentManager(), "");
    }

    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;

    private void onSelect() {
        emptyView.setVisibility(View.GONE);
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(this);
            gridLayoutManager = new GridLayoutManager(this, 2);
        }
        if (selectPosition == 0) {
            selectTextView(tvfriends, tvBeInvited);
            if (adapter0 == null) {
                adapter0 = new FrindListAdapter();
                setclickListenner();
            }
            recyclerview.setLayoutManager(gridLayoutManager);
            recyclerview.setAdapter(adapter0);
        } else if (selectPosition == 1) {
            selectTextView(tvBeInvited, tvfriends);
            if (adapter1 == null) {
                adapter1 = new InviteAdapter();
                setclickListenner();
            }
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(adapter1);
        }

        requestData();
    }

    private void setclickListenner() {
        if (adapter1 != null) {
            adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_accept) {
                        //同意
                        accept(adapter1.getData().get(position).getId() + "", true);
                    } else if (view.getId() == R.id.tv_refuse) {
                        //拒绝
                        accept(adapter1.getData().get(position).getId() + "", false);
                    }
                }
            });
        }
        if (adapter0 != null) {
            adapter0.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    SettingInviteDialog dialog = SettingInviteDialog.newInstance();
                    dialog.setTheme(R.style.PaddingScreen);
                    dialog.setGravity(Gravity.BOTTOM);
                    dialog.show(getSupportFragmentManager(), "edit");
                    dialog.setCallback(new SettingInviteDialog.OperateCallback() {
                        @Override
                        public void callback(String startTime, String endTime) {

                        }
                    });
                }
            });
            adapter0.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    InviteDetailActivity.actionStart(FriendListActivity.this, adapter0.getItem(position));
                }
            });
        }

    }


    private void selectTextView(TextView select, TextView unSelect1) {
        new ShadowDrawable.ShadowBuilder()
                .setStartColor(Color.parseColor("#56758BD4"))
                .setEndColor(Color.parseColor("#10E6E8EF"))
                .setmGradientColor(new int[]{Color.parseColor("#548FEB"),Color.parseColor("#4D67C1")})
                .setCardElevation(ScreenUtils.dip2px(this, 8))
                .setCardCornerRadius(ScreenUtils.dip2px(this, 16))
                .create()
                .bindView(select);
        select.setTextColor(Color.WHITE);

        unSelect1.setBackground(null);
        unSelect1.setTextColor(Color.parseColor("#ADAEB3"));
    }

    private void requestData() {
        if (selectPosition == 0) {
            if (adapter0.getData().isEmpty()) {
                getFriendList();
            }
        } else if (selectPosition == 1) {
            getBeInviteList();
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
        if (notNullData.getRecords().isEmpty() && selectPosition == 0) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    private void onSuccessInviteFriendList(InviteFriendDao notNullData) {
        adapter1.setNewData(notNullData.getRecords());
    }

    private void onSuccessChangeList() {
        getBeInviteList();
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

    private void accept(String friendId, boolean accpet) {
        String url = "";
        if (accpet) {
            url = UrlFactory.getAcceptTheInvitation();
        } else {
            url = UrlFactory.getDeclineTheInvitation();
        }
        WonderfulOkhttpUtils.get().url(url + "?id=" + friendId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> testRemoteData) {
                        onSuccessChangeList();
                    }

                });
    }


    private void getBeInviteList() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getInviteFriendList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<InviteFriendDao>>() {
                    @Override
                    protected void onRes(RemoteData<InviteFriendDao> testRemoteData) {
                        onSuccessInviteFriendList(testRemoteData.getNotNullData());
                    }

                });
    }


    private static class InviteAdapter extends BaseQuickAdapter<InviteFriendItem, BaseViewHolder> {

        int id = 0;

        public InviteAdapter() {
            super(R.layout.item_friend_invite_info);
            SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
                @Override
                public void call(UserInfoDao dao) {
                    id = dao.getId();
                }
            });
        }

        @Override
        protected BaseViewHolder createBaseViewHolder(View view) {
            BaseViewHolder baseViewHolder = super.createBaseViewHolder(view);
            baseViewHolder.addOnClickListener(R.id.tv_refuse);
            baseViewHolder.addOnClickListener(R.id.tv_accept);
            return baseViewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, InviteFriendItem item) {
            //如果自己的id=memberId  那么就显示friendNote或者friend Email
            //如果自己的id=friendId  那么就显示memberNote或者memberEmail
            String friendName = "";
            if (id == item.getMemberId()) {
                friendName = item.getFriendNote();
                if (TextUtils.isEmpty(friendName)) {
                    friendName = item.getFriendEmail();
                }
            } else if (Objects.equals(item.getFriendId(), String.valueOf(id))) {
                friendName = item.getMemberNote();
                if (TextUtils.isEmpty(friendName)) {
                    friendName = item.getMemberEmail();
                }
            }
            helper.setText(R.id.tv_dog_name, friendName);
            helper.setText(R.id.tv_time, item.getCreateTime());
            int type = item.getType();
            if (type == 1) {
                helper.setGone(R.id.tv_refuse, false);
                helper.setGone(R.id.tv_accept, false);
                helper.setText(R.id.tv_state, mContext.getString(R.string.friends));
            } else if (type == 4) {
                helper.setGone(R.id.tv_refuse, false);
                helper.setGone(R.id.tv_accept, false);
                helper.setText(R.id.tv_state, mContext.getString(R.string.refuse));
            } else if (type == 2) {
                helper.setGone(R.id.tv_refuse, true);
                helper.setGone(R.id.tv_accept, true);
                helper.setText(R.id.tv_state, mContext.getString(R.string.wait_accept));
            } else if (type == 3) {
                helper.setGone(R.id.tv_refuse, false);
                helper.setGone(R.id.tv_accept, false);
                helper.setText(R.id.tv_state, mContext.getString(R.string.wait_friend_accept));
            }
        }
    }


    private static class FrindListAdapter extends BaseQuickAdapter<
            FriendInfoDao, BaseViewHolder> {

        public FrindListAdapter() {
            super(R.layout.item_invite_frinds);
        }

        @Override
        protected BaseViewHolder createBaseViewHolder(View view) {
            BaseViewHolder baseViewHolder = super.createBaseViewHolder(view);
            baseViewHolder.addOnClickListener(R.id.tv_invite);
            return baseViewHolder;
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
            helper.setText(R.id.iv_lv, Utils.getFormat("Lv.%d",(int) (item.getLevel())));
            helper.setText(R.id.iv_name1, item.getFriendName());
            helper.setText(R.id.tv_times, String.format(mContext.getString(R.string._time), item.getWalkTheDogCount() + ""));
            helper.setText(R.id.tv_all_time, Utils.getTime(item.getWalkTheDogTime(),"%02dH%02dmin"));
            helper.setText(R.id.tv_today, Utils.getFormat("%d/2", item.getDayLimit()));

            if (item.getStarvation() == 1) {
                helper.setTextColor(R.id.txt_status, Color.parseColor("#E51616"));
                helper.setText(R.id.txt_status, R.string.full_of_hunger);
            } else {
                helper.setTextColor(R.id.txt_status, Color.parseColor("#30B226"));
                helper.setText(R.id.txt_status, R.string.full_of_energy);
            }
            setProgress(helper, item.getRateOfProgress() / 100.00);
        }

        //设置进度条
        private void setProgress(BaseViewHolder helper, double percentage) {
            View progressBg = helper.getView(R.id.progress_bg);
            View progressBar = helper.getView(R.id.progress_bar);
            TextView progressTxt = helper.getView(R.id.progress_txt);
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
                        params2.leftMargin = (int) (progress - progressAll * 0.4);
                    } else {
                        params2.leftMargin = (int) (progress - progressAll * 0.18);
                    }
                    progressTxt.setLayoutParams(params2);
                    progressTxt.setText(percentage * 100 + "%");
                }
            });
        }
    }
}
