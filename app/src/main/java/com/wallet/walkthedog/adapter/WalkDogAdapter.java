package com.wallet.walkthedog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.EquipmentDao;

import java.util.List;

public class WalkDogAdapter extends BaseQuickAdapter<EquipmentDao, BaseViewHolder> {

    public WalkDogAdapter(int layoutResId, @Nullable List<EquipmentDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EquipmentDao item) {
        helper.getView(R.id.txt_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(helper.getLayoutPosition());
            }
        });
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(int position);

    }
}
