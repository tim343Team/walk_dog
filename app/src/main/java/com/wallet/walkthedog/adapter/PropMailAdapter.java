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
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropMailDao;

import java.util.List;

public class PropMailAdapter extends BaseQuickAdapter<PropMailDao, BaseViewHolder> {
    private String uid;

    public PropMailAdapter(int layoutResId, @Nullable List<PropMailDao> data, String uid) {
        super(layoutResId, data);
        this.uid = uid;
    }

    @Override
    protected void convert(BaseViewHolder helper, PropMailDao item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        helper.getView(R.id.group_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(item);
            }
        });
        helper.setText(R.id.txt_name, item.getName());
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.img_prop));
        helper.setText(R.id.txt_id, item.getPropNumberChain() + "");
        helper.setText(R.id.txt_price, String.valueOf(item.getPrice())+" suzu");
        if(item.getMemberId().equals(uid)){
            helper.setText(R.id.txt_cancle,R.string.cancle_buy);
        }else {
            helper.setText(R.id.txt_cancle,R.string.buy);
        }
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(PropMailDao item);
    }

}
