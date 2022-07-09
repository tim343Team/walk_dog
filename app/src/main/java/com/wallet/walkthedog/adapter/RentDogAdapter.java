package com.wallet.walkthedog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.RentDogDao;

import java.util.List;

public class RentDogAdapter extends BaseQuickAdapter<RentDogDao, BaseViewHolder> {

    public RentDogAdapter(int layoutResId, @Nullable List<RentDogDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RentDogDao item) {
        helper.getView(R.id.group_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(item);
            }
        });
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(RentDogDao item);
    }
}
