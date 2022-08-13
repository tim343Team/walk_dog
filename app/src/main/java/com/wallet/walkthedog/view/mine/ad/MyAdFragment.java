package com.wallet.walkthedog.view.mine.ad;

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
import com.wallet.walkthedog.dao.ADRecordDao;
import com.wallet.walkthedog.dao.ContentItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import java.util.HashMap;
import java.util.Map;

import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MyAdFragment extends Fragment {

    AdRecordAdapter adapter = new AdRecordAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemChildClickListener((x, view1, position) -> {
            if (view1.getId() == R.id.btn_1) {
                revise(adapter.getData().get(position));
            } else if (view1.getId() == R.id.btn_2) {
                takeOffOn(adapter.getData().get(position).getId(), adapter.getData().get(position).getStatus());
            } else if (view1.getId() == R.id.btn_3) {
                delect(adapter.getData().get(position).getId());
            }
        });
    }

    private void delect(int id) {
        WonderfulOkhttpUtils.get().url(UrlFactory.advertiseDelect() + "?" + "id=" + id)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> data) {
                        ToastUtils.shortToast(getString(R.string.delete));
                        load();
                    }
                });
    }

    //0 上架  1下架 2关闭
    private void takeOffOn(int id, int status) {
        String url;
        if (status == 0) {
            url = UrlFactory.advertiseOFF() + "?" + "id=" + id;
        } else {
            url = UrlFactory.advertiseON() + "?" + "id=" + id;
        }
        WonderfulOkhttpUtils.get().url(url)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> data) {
                        load();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void revise(ContentItem contentItem) {
        Intent intent = new Intent(requireActivity(), PlaceADActivity.class);
        intent.putExtra("contentItem", contentItem);
        startActivity(intent);
    }

    private void load() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", 1);
        map.put("pageSize", 1000);
        map.put("direction", "ASC");
        String params = Utils.toGetUri(map);
        request(params);
    }

    private void request(String params) {
        WonderfulOkhttpUtils.get().url(UrlFactory.advertiseAll() + "?" + params)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<ADRecordDao>>() {
                    @Override
                    protected void onRes(RemoteData<ADRecordDao> data) {
                        adapter.setNewData(data.getNotNullData().getContent());
                    }
                });
    }


    class AdRecordAdapter extends BaseQuickAdapter<ContentItem, BaseViewHolder> {

        public AdRecordAdapter() {
            super(R.layout.item_my_ad);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder baseViewHolder = super.onCreateDefViewHolder(parent, viewType);
            baseViewHolder.addOnClickListener(R.id.btn_1);
            baseViewHolder.addOnClickListener(R.id.btn_3);
            baseViewHolder.addOnClickListener(R.id.btn_2);
            return baseViewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, ContentItem item) {
            if (item.getAdvertiseType() == 0) {
                helper.setText(R.id.tv_1, getString(R.string.buy));
            } else {
                helper.setText(R.id.tv_1, getString(R.string.sell));
            }
            helper.setText(R.id.tv_2, item.getMinLimit() + "~" + item.getMaxLimit());
            helper.setText(R.id.tv_3, String.valueOf(item.getNumber()));
            helper.setText(R.id.tv_4, String.valueOf(item.getCoin().getName()));
            helper.setText(R.id.tv_5, String.valueOf(item.getUpdateTime()));
            //0 上架  1下架 2关闭
            helper.setVisible(R.id.btn_2, true);
            if (item.getStatus() == 0) {
                helper.setText(R.id.btn_2, getString(R.string.take_off));
            } else if (item.getStatus() == 1) {
                helper.setText(R.id.btn_2, getString(R.string.take_on));
            } else {
                helper.setVisible(R.id.btn_2, false);
            }
        }
    }

}
