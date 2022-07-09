package com.wallet.walkthedog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.dao.DogInfoDao;

import java.util.List;

public class RentDogsAdapter extends BaseQuickAdapter<DogInfoDao, BaseViewHolder> {

    public RentDogsAdapter(int layoutResId, @Nullable List<DogInfoDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DogInfoDao item) {

    }
}
