package com.wallet.walkthedog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.EquipmentDao;
import com.wallet.walkthedog.dao.TrainDao;

import java.util.List;

public class TrainListAdapter extends BaseQuickAdapter<TrainDao, BaseViewHolder> {

    public TrainListAdapter(int layoutResId, @Nullable List<TrainDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainDao item) {
        helper.getView(R.id.txt_train).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(item);
            }
        });
        helper.setText(R.id.txt_train,item.getName());
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(TrainDao item);

    }
}
