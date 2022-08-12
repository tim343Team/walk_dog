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
    private int type;

    public CardAdapter(int layoutResId, @Nullable List<CardInfoDao> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, CardInfoDao item) {
        helper.getView(R.id.img_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = item.getType();
                itemClick.click(helper.getLayoutPosition());
            }
        });
        helper.getView(R.id.img_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemEdit.click(helper.getLayoutPosition());
            }
        });
        helper.setText(R.id.txt_name, item.getRealName());
        helper.setText(R.id.txt_card_number, item.getAccount());
        helper.setText(R.id.txt_bank, item.getBankName());
        helper.setText(R.id.txt_type, item.getTypeText());
        if (type == item.getType()) {
            helper.getView(R.id.img_select).setBackgroundResource(R.mipmap.icon_select_white);
        } else {
            helper.getView(R.id.img_select).setBackgroundResource(R.mipmap.icon_unselect_white);
        }
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
