package com.wallet.walkthedog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.SellRecordDao;

import java.util.List;

public class SellRecordAdapter extends BaseQuickAdapter<SellRecordDao, BaseViewHolder> {

    public SellRecordAdapter(int layoutResId, @Nullable List<SellRecordDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SellRecordDao item) {
        helper.setText(R.id.txt_name,item.getNftTypeName());
        helper.setText(R.id.txt_prop_id,item.getDogNumberChain());
        helper.setText(R.id.txt_price,item.getSum()+"");
        helper.setText(R.id.txt_time,item.getCreateTime());
        helper.setText(R.id.txt_orider_id,String.format(mContext.getString(R.string.order_number_), item.getId()+""));
    }
}
