package com.wallet.walkthedog.view.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.custom_view.card.ShadowDrawable;
import com.wallet.walkthedog.dao.InviteFriendTheDogDao;
import com.wallet.walkthedog.dao.InviteFriendTheDogItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.ScreenUtils;

/**
 * 好友列表及邀请记录
 */
public class InvitedDogActivity extends BaseActivity {
    private int selectPosition = 0;
    private TextView tvInviteOthers;
    private TextView tvBeInvited;
    private RecyclerView recyclerview;
    private InviteAdapter adapter0;
    private InviteAdapter adapter1;

    private RequestData data0 = new RequestData();

    private RequestData data1 = new RequestData();

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_invited_dog;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        data0.type = 1;
        data1.type = 2;
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        tvInviteOthers = findViewById(R.id.tv_invite_others);
        tvBeInvited = findViewById(R.id.tv_be_invited);

        tvInviteOthers.setOnClickListener(new View.OnClickListener() {
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
        onSelect();
    }

    private void onSelect() {
        if (selectPosition == 0) {
            selectTextView(tvInviteOthers, tvBeInvited);
            if (adapter0 == null) {
                adapter0 = new InviteAdapter(false);
                adapter0.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        getTogetherPage(data0);
                    }
                }, recyclerview);
            }
            recyclerview.setAdapter(adapter0);
        } else if (selectPosition == 1) {
            selectTextView(tvBeInvited, tvInviteOthers);
            if (adapter1 == null) {
                adapter1 = new InviteAdapter(true);
                setListenner();
                adapter1.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        getTogetherPage(data1);
                    }
                }, recyclerview);
            }
            recyclerview.setAdapter(adapter1);
        }
        requestData();
    }

    private void setListenner() {
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_refuse) {
                    ideaTogether(3);
                } else if (view.getId() == R.id.tv_accept){
                    ideaTogether(1);
                }
            }
        });
    }



    private void selectTextView(TextView select, TextView unselect) {
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

    }

    private void requestData() {
        if (selectPosition == 0) {
            if (adapter0.getData().isEmpty()) {
                data0.pageNo = 1;
                getTogetherPage(data0);
            }
        } else if (selectPosition == 1) {
            if (adapter1.getData().isEmpty()) {
                data1.pageNo = 1;
                getTogetherPage(data1);
            }

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

    private static class RequestData {
        int pageNo = 1;
        int pageSize = 50;
        int type = 1;
    }

    private void ideaTogether(int state) {
        displayLoadingPopup();
        WonderfulOkhttpUtils.get().url(UrlFactory.getIdeaTogether())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("state", String.valueOf(state))
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> testRemoteData) {
                        onSuccessIdeaTogether();
                        hideLoadingPopup();
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                        hideLoadingPopup();
                    }
                });
    }



    private void getTogetherPage(RequestData data) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getTogetherPage()+"?pageNo="+ data.pageNo +"&pageSize=" + data.pageSize + "&type="+ data.type)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<InviteFriendTheDogDao>>() {
                    @Override
                    protected void onRes(RemoteData<InviteFriendTheDogDao> testRemoteData) {
                        onSuccessGetTogetherPage(data, testRemoteData.getNotNullData());
                    }

                    @Override
                    protected void onFail(Exception e) {
                        super.onFail(e);
                        onFailGetTogetherPage(data);
                    }
                });
    }

    private void onSuccessIdeaTogether() {
        data1.pageNo = 1;
        getTogetherPage(data1);
    }

    private void onFailGetTogetherPage(RequestData data) {
        if (data.type == 1) {
            adapter0.loadMoreFail();
        } else {
            adapter1.loadMoreFail();
        }
    }

    private void onSuccessGetTogetherPage(RequestData data, InviteFriendTheDogDao dao) {
        data.pageNo += 1;
        if (data.type == 1) {
            if (dao.getRecords().size() < data.pageSize) {
                adapter0.loadMoreEnd();
            } else {
                adapter0.loadMoreComplete();
            }
            adapter0.addData(dao.getRecords());
        } else {
            if (dao.getRecords().size() < data.pageSize) {
                adapter1.loadMoreEnd();
            } else {
                adapter1.loadMoreComplete();
            }
            adapter1.addData(dao.getRecords());
        }
    }


    static class InviteAdapter extends BaseQuickAdapter<InviteFriendTheDogItem, BaseViewHolder> {

        private final boolean showAction;

        public InviteAdapter(boolean showAction) {
            super(R.layout.item_invite_info);
            this.showAction = showAction;
        }

        @Override
        protected BaseViewHolder createBaseViewHolder(View view) {
            BaseViewHolder baseViewHolder = super.createBaseViewHolder(view);
            baseViewHolder.addOnClickListener(R.id.tv_refuse);
            baseViewHolder.addOnClickListener(R.id.tv_accept);
            return baseViewHolder;
        }


        @Override
        protected void convert(BaseViewHolder helper, InviteFriendTheDogItem item) {
            helper.setText(R.id.tv_dog_name, item.getFriendName());

            //1已同意 2等待同意 3已拒绝 4自动拒绝 5发起人取消
            boolean visible = false;
            int status = item.getStatus();
            if (status == 1) {
                helper.setText(R.id.tv_state, R.string.accept);
            } else if (status == 2) {
                if (showAction) {
                    visible = true;
                    helper.setText(R.id.tv_state, R.string.to_be_comfirmed);
                } else {
                    helper.setText(R.id.tv_state, R.string.to_be_comfirmed);
                }
            } else if (status == 3 || status == 4) {
                helper.setText(R.id.tv_state, R.string.refuse);
            } else if (status == 5) {
                helper.setText(R.id.tv_state, R.string.cancle);

            }
            if (visible){
                helper.setGone(R.id.tv_refuse, true);
                helper.setGone(R.id.tv_accept, true);
            }else {
                helper.setGone(R.id.tv_refuse, false);
                helper.setGone(R.id.tv_accept, false);
            }

            helper.setText(R.id.tv_time, item.getCreateTime());
        }
    }

}
