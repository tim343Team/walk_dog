package com.wallet.walkthedog.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.AwardDao;
import com.wallet.walkthedog.dao.EquipmentDao;

import java.util.List;

public class WalkDogAdapter extends BaseQuickAdapter<AwardDao, BaseViewHolder> {

    public WalkDogAdapter(int layoutResId, @Nullable List<AwardDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardDao item) {
        if(item.getStatus()==1) {
            helper.getView(R.id.txt_collect).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.txt_collect).setVisibility(View.INVISIBLE);
        }
        helper.getView(R.id.txt_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getStatus()==1){
                    itemClick.click(helper.getLayoutPosition());
                }else {
                    itemClick.click(-1);
                }
            }
        });
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_bell)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(mContext).load(item.getCatImg()).apply(options).into((ImageView) helper.getView(R.id.img_equipment));
        helper.setText(R.id.txt_time,item.getCreateTime());
        helper.setText(R.id.txt_name,String.format(mContext.getString(R.string._time), item.getCatName()));
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(int position);

    }
}
