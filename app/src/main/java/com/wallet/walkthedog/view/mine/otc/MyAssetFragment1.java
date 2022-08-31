package com.wallet.walkthedog.view.mine.otc;

import android.content.Intent;
import android.os.Bundle;
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
import com.wallet.walkthedog.dao.CoinNameItem;
import com.wallet.walkthedog.dao.OtherAssetDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.mine.MyAssetActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MyAssetFragment1 extends Fragment {
    public static MyAssetFragment1 newInstance(int postion) {
        Bundle args = new Bundle();
        args.putInt("postion", postion);
        MyAssetFragment1 fragment = new MyAssetFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    private CoinAdapter adapter0 = new CoinAdapter();
    private int postion = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_asset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        postion = getArguments().getInt("postion");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter0);
        adapter0.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OtherAssetDao otherAssetDao = adapter0.hashMap.get(adapter0.getData().get(position).getName());
                if (otherAssetDao != null) {
                    Intent intent;
                    if (postion == 0) {
                        intent = new Intent(requireContext(), MyAssetActivity.class);
                        if (otherAssetDao.getCoin().getName().equals("USDT")){
                            intent.putExtra("type","3");
                        } else if (otherAssetDao.getCoin().getName().equals("ETH")){
                            intent.putExtra("type","4");
                        }
                        intent.putExtra("allAsset",otherAssetDao.getBalance());
                        intent.putExtra("coldWalletAddress",otherAssetDao.getCoin().getColdWalletAddress());
                    } else {
                        intent = new Intent(requireContext(), MyOTCAssetActivity.class);
                    }
                    intent.putExtra("otherAssetDao", otherAssetDao);
                    startActivity(intent);
                }
            }
        });
        requestCoinList();
    }

    private void requestCoinList() {
        assert getArguments() != null;
        int postion = getArguments().getInt("postion");
        if (postion == 0) {
            WonderfulOkhttpUtils.post().url(UrlFactory.coinList())
                    .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                    .build()
                    .getCall()
                    .enqueue(new GsonWalkDogCallBack<RemoteData<List<CoinNameItem>>>() {
                        @Override
                        protected void onRes(RemoteData<List<CoinNameItem>> testRemoteData) {
                            adapter0.setNewData(testRemoteData.getNotNullData());
                        }
                    });
        } else {
            WonderfulOkhttpUtils.get().url(UrlFactory.otcWalletGet())
                    .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                    .build()
                    .getCall()
                    .enqueue(new GsonWalkDogCallBack<RemoteData<List<OtherAssetDao>>>() {
                        @Override
                        protected void onRes(RemoteData<List<OtherAssetDao>> testRemoteData) {
                            List<CoinNameItem> coinNameItems = new ArrayList<>();
                            List<OtherAssetDao> notNullData = testRemoteData.getNotNullData();
                            for (int i = 0; i < notNullData.size(); i++) {
                                String name = notNullData.get(i).getCoin().getName();
                                CoinNameItem coinNameItem = new CoinNameItem();
                                coinNameItem.setName(name);
                                coinNameItems.add(coinNameItem);
                                adapter0.hashMap.put(name, notNullData.get(i));
                            }
                            adapter0.setNewData(coinNameItems);
                        }
                    });
        }


    }

    static class CoinAdapter extends BaseQuickAdapter<CoinNameItem, BaseViewHolder> {

        HashMap<String, OtherAssetDao> hashMap = new HashMap<>();

        public CoinAdapter() {
            super(R.layout.item_account_asset);
        }

        @Override
        protected void convert(BaseViewHolder helper, CoinNameItem item) {
            helper.setText(R.id.tv_1, item.getName());
            helper.setText(R.id.tv_2, "--");
            OtherAssetDao otherAssetDao = hashMap.get(item.getName());
            if (otherAssetDao == null && !hashMap.containsKey(item.getName())) {
                requestAsset(item.getName(), helper.getAdapterPosition());
            } else if (otherAssetDao != null) {
                helper.setText(R.id.tv_2, Utils.getFormat("%.4f",otherAssetDao.getBalance()));
            }
        }

        private void requestAsset(String coinName, int postion) {
            WonderfulOkhttpUtils.get().url(UrlFactory.otcWallet() + "/" + coinName)
                    .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                    .build()
                    .getCall()
                    .enqueue(new GsonWalkDogCallBack<RemoteData<OtherAssetDao>>() {
                        @Override
                        protected void onRes(RemoteData<OtherAssetDao> testRemoteData) {
                            hashMap.put(coinName, testRemoteData.getData());
                            notifyItemChanged(postion);
                        }
                    });
        }
    }


}
