package com.wallet.walkthedog.adapter;

import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.LanguageDao;

import java.util.List;


public class PopupAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    int itemWidth;
    public PopupAdapter(int layoutResId, @Nullable List<T> data, int width) {
        super(layoutResId, data);
        itemWidth=width;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if(item instanceof LanguageDao){
            LanguageDao str= (LanguageDao) item;
            helper.setText(R.id.tv_popup_text,str.getLanguage());
        }else if(item instanceof String){
            helper.setText(R.id.tv_popup_text,(String) item);
        }

        if(itemWidth!=0){
            ViewGroup.LayoutParams lp = helper.getConvertView().getLayoutParams();
            lp.width=itemWidth;
            helper.getConvertView().setLayoutParams(lp);
        }
    }
}
