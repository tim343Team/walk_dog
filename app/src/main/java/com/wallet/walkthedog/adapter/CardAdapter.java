package com.wallet.walkthedog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.CardInfoDao;
import com.wallet.walkthedog.dao.PropDao;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends BaseQuickAdapter<CardInfoDao, BaseViewHolder> {
    private List<PropDao> selectDao = new ArrayList<>();

    public CardAdapter(int layoutResId, @Nullable List<CardInfoDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardInfoDao item) {
        helper.getView(R.id.img_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(helper.getLayoutPosition());
            }
        });
        helper.getView(R.id.img_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemEdit.click(helper.getLayoutPosition());
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

    OnEditListenerItem itemEdit;

    public void OnEditListenerItem(OnEditListenerItem itemEdit) {
        this.itemEdit = itemEdit;
    }

    public interface OnEditListenerItem {
        void click(int position);
    }
}
