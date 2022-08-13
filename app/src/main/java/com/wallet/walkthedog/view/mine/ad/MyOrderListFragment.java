package com.wallet.walkthedog.view.mine.ad;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.OTCOrderItem;
import com.wallet.walkthedog.dao.OTCOrderItemWrapper;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import tim.com.libnetwork.network.okhttp.RequestBuilder;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MyOrderListFragment extends Fragment {
    private OTCOrderItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OTCOrderItemAdapter();
        recyclerView.setAdapter(adapter);
        request();
    }

    private void request() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        Integer state = null;
        //status:0已取消 1 未付款 2已付款 3已完成 4 申述中（非必传） orderSn:订单号（非必传
        if (position != 0) {
            state = position;
        }
        if (position == 4) {
            state = 0;
        }
        RequestBuilder builder = WonderfulOkhttpUtils.post().url(UrlFactory.orderSelf())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("pageNo", "1")
                .addParams("pageSize", "1000")
                .addParams("direction", "ASC");
        if (state != null) {
            builder.addParams("status", String.valueOf(state));
        }
        builder.build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<OTCOrderItemWrapper>>() {

                    @Override
                    protected void onRes(RemoteData<OTCOrderItemWrapper> data) {
                        adapter.setNewData(data.getNotNullData().getContent());
                    }
                });
    }

    class OTCOrderItemAdapter extends BaseQuickAdapter<OTCOrderItem, BaseViewHolder> {

        public OTCOrderItemAdapter() {
            super(R.layout.item_ad_order);
        }

        @Override
        protected void convert(BaseViewHolder helper, OTCOrderItem item) {
            helper.setText(R.id.tv_1, item.getOrderSn());
            helper.setText(R.id.tv_2, item.getCreateTime());
            helper.setText(R.id.tv_3, item.getUnit());
            String type = getString(R.string.buy);
            if (item.getType() == 1) {
                type = getString(R.string.sell);
            }
            helper.setText(R.id.tv_4, type);
            String name = item.getRealName();
            if (TextUtils.isEmpty(name)) {
                name = item.getName();
            }
            helper.setText(R.id.tv_5, name);
            helper.setText(R.id.tv_6, item.getAmount());
            helper.setText(R.id.tv_7, item.getMoney());
            helper.setText(R.id.tv_8, item.getCommission());
        }
    }
}
