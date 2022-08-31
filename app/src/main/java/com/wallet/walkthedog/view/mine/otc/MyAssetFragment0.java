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
import com.wallet.walkthedog.dao.OtherAssetDao;
import com.wallet.walkthedog.dao.WalletsItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.mine.MyAssetActivity;

import java.util.HashMap;
import java.util.List;

import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MyAssetFragment0 extends Fragment {
    public static MyAssetFragment0 newInstance(int postion) {
        Bundle args = new Bundle();
        args.putInt("postion", postion);
        MyAssetFragment0 fragment = new MyAssetFragment0();
        fragment.setArguments(args);
        return fragment;
    }

    private final CoinAdapter adapter0 = new CoinAdapter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_asset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter0);
        adapter0.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(requireContext(), MyAssetActivity.class);
                intent.putExtra("type", String.valueOf(adapter0.getData().get(position).getType()));
                intent.putExtra("coldWalletAddress", adapter0.getData().get(position).getAddress());
                startActivity(intent);
            }
        });
        requestList();
    }

    private void requestList() {
        WonderfulOkhttpUtils.get().url(UrlFactory.wallentList())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<WalletsItem>>>() {
                    @Override
                    protected void onRes(RemoteData<List<WalletsItem>> testRemoteData) {
                        adapter0.setNewData(testRemoteData.getNotNullData());
                    }
                });
    }


    static class CoinAdapter extends BaseQuickAdapter<WalletsItem, BaseViewHolder> {

        HashMap<String, OtherAssetDao> hashMap = new HashMap<>();

        public CoinAdapter() {
            super(R.layout.item_account_asset);
        }

        @Override
        protected void convert(BaseViewHolder helper, WalletsItem item) {
            int type = item.getType();
            String name = "USDT";
            if (type == 4) {
                name = "ETH";
            }
            helper.setText(R.id.tv_1, name);
            helper.setText(R.id.tv_2, Utils.getFormat("%.4f", item.getDogFood()));
        }
    }


}
