package com.wallet.walkthedog.view.mine.otc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.AdvertiseUnitItem;
import com.wallet.walkthedog.dao.AdvertiseUnitPage;
import com.wallet.walkthedog.dao.CoinNameItem;
import com.wallet.walkthedog.dao.ContryItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class OTCBuyListFragment extends Fragment implements OTCBuyFragment.ISelectFaBi {

    public static OTCBuyListFragment newInstance(CoinNameItem item) {
        Bundle args = new Bundle();
        args.putSerializable("CoinNameItemKEY", item);
        OTCBuyListFragment fragment = new OTCBuyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private OTCListAdapter otcListAdapter;
    private CoinNameItem coinNameItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        coinNameItem = (CoinNameItem) getArguments().getSerializable("CoinNameItemKEY");

        RecyclerView recyclerView = new RecyclerView(requireActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        otcListAdapter = new OTCListAdapter();
        recyclerView.setAdapter(otcListAdapter);
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        otcListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_buy) {
                    Intent intent = new Intent(requireActivity(), PurchaseOTCActivity.class);
                    intent.putExtra("CoinNameItemKEY",coinNameItem);
                    AdvertiseUnitItem item =  otcListAdapter.getData().get(position);
                    intent.putExtra("AdvertiseUnitItemKEY",item);
                    startActivity(intent);
                }
            }
        });
        OTCBuyFragment parentFragment = (OTCBuyFragment) getParentFragment();
        assert parentFragment != null;
        ContryItem country = parentFragment.getCountry();
        if (country != null) {
            String localCurrency = country.getLocalCurrency();
            requestList(coinNameItem.getUnit(), localCurrency);
        }

    }

    private void requestList(String unit, String country) {
        WonderfulOkhttpUtils.get().url(UrlFactory.AdpageByUnit() + "?pageNo=1&pageSize=1000" + "&unit=" + unit + "&legalCurrency=" + country + "&advertiseType=1&isCertified=0&payModes=Bank")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<AdvertiseUnitPage>>() {
                    @Override
                    protected void onRes(RemoteData<AdvertiseUnitPage> testRemoteData) {
                        onRequestList(testRemoteData.getNotNullData().getContext());
                    }
                });
    }

    private void onRequestList(List<AdvertiseUnitItem> notNullData) {
        otcListAdapter.setNewData(notNullData);
    }

    @Override
    public void onSelect(ContryItem item) {
        String localCurrency = item.getLocalCurrency();
        requestList(coinNameItem.getUnit(), localCurrency);
    }

    static class OTCListAdapter extends BaseQuickAdapter<AdvertiseUnitItem, BaseViewHolder> {

        public OTCListAdapter() {
            super(R.layout.item_list_buy);
        }

        @Override
        protected BaseViewHolder createBaseViewHolder(View view) {
            BaseViewHolder baseViewHolder = super.createBaseViewHolder(view);
            baseViewHolder.addOnClickListener(R.id.tv_buy);
            return baseViewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, AdvertiseUnitItem item) {
            ImageView view = helper.getView(R.id.iv_header);
            if (!TextUtils.isEmpty(item.getAvatar())) {
                Glide.with(view)
                        .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                        .load(item.getAvatar())
                        .into(view);
            }
            helper.setText(R.id.tv_name, item.getMemberName());
            helper.setText(R.id.tv_price, Utils.getFormat("￥：%.2f", item.getPrice()));
            helper.setText(R.id.tv_volume, Utils.getFormat("%.2f", item.getRemainQuantity()));
            helper.setText(R.id.tv_unit, item.getCoinName() + "/" + item.getLegalCurrency());
            helper.setText(R.id.tv_qu, Utils.getFormat("Quantity：%.2f" + item.getCoinName(), item.getRemainQuantity()));
            helper.setText(R.id.tv_qu, Utils.getFormat("Limit：%.2f" + item.getLegalCurrency(), item.getMaxLimit()));

        }
    }
}
