package com.wallet.walkthedog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.EquipmentDao;

import java.util.List;

public class MyDogsAdapter extends BaseQuickAdapter<DogInfoDao, BaseViewHolder> {

    public MyDogsAdapter(int layoutResId, @Nullable List<DogInfoDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DogInfoDao item) {

    }
}
