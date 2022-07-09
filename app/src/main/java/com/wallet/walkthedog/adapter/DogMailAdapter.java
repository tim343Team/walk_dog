package com.wallet.walkthedog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropDao;

import java.util.ArrayList;
import java.util.List;

public class DogMailAdapter extends BaseQuickAdapter<DogMailDao, BaseViewHolder> {
    private List<PropDao> selectDao = new ArrayList<>();

    public DogMailAdapter(int layoutResId, @Nullable List<DogMailDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DogMailDao item) {
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
        void click(DogMailDao item);
    }
}
