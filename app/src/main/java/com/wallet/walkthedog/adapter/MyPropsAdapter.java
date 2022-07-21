package com.wallet.walkthedog.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;

import java.util.ArrayList;
import java.util.List;

public class MyPropsAdapter extends BaseQuickAdapter<PropDao, BaseViewHolder> {
    private List<PropDao> selectDao = new ArrayList<>();

    public MyPropsAdapter(int layoutResId, @Nullable List<PropDao> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, PropDao item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_bell)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.img_prop));
        helper.setText(R.id.txt_name,item.getName());
        helper.setText(R.id.txt_id,item.getId());
        if (item.getType()==1) {
            helper.setText(R.id.txt_choice, R.string.deselect);
            helper.setTextColor(R.id.txt_name,mContext.getColor(R.color.white));
            helper.setTextColor(R.id.txt_id,mContext.getColor(R.color.white));
            helper.getView(R.id.root_view).setBackgroundResource(R.drawable.bg_gradual_select);
        } else {
            helper.setText(R.id.txt_choice, R.string.select);
            helper.setTextColor(R.id.txt_name,mContext.getColor(R.color.color_4D67C1));
            helper.setTextColor(R.id.txt_id,mContext.getColor(R.color.color_4D67C1));
            helper.getView(R.id.root_view).setBackgroundResource(R.drawable.rectangle_white);
        }
        helper.getView(R.id.txt_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(helper.getLayoutPosition());
            }
        });
    }

    public int getSelectCount() {
        return selectDao.size();
    }

    public void addSelect(PropDao dao){
        selectDao.add(dao);
    }

    public void removeSelect(PropDao dao){
        selectDao.remove(dao);
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(int position);
    }
}
