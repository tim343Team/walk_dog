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
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.data.Constant;

import java.util.ArrayList;
import java.util.List;

public class MyPropsAdapter extends BaseQuickAdapter<PropDao, BaseViewHolder> {
    private List<PropDao> selectDao = new ArrayList<>();

    public MyPropsAdapter(int layoutResId, @Nullable List<PropDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PropDao item) {
        String type;
//        int nftTypeCatagoryId = item.getNftTypeCatagoryId();
        int nftTypeCatagoryId = item.getKindType();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_bell)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.img_prop));
        helper.setText(R.id.txt_name, item.getName());
        helper.setText(R.id.txt_id, item.getPropNumberChain());
        helper.setText(R.id.txt_create_time, item.getCreateTime());
        if(item.getDecorateDogId() == 0){
            helper.setText(R.id.txt_equipment_id,R.string.nothing);
        }else {
            helper.setText(R.id.txt_equipment_id,String.format(mContext.getString(R.string.equipment_dog_), item.getDecorateDogId() + ""));
        }
        if (nftTypeCatagoryId == 1) {
            type = Constant.PROP_FOOD;
            helper.setText(R.id.txt_choice, R.string.open);
            helper.getView(R.id.txt_choice).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOpen.click(helper.getLayoutPosition(), 0);
                }
            });
        } else if (nftTypeCatagoryId == 2) {
            type = Constant.PROP_BOX;
            helper.setText(R.id.txt_choice, R.string.open);
            helper.getView(R.id.txt_choice).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOpen.click(helper.getLayoutPosition(), 1);
                }
            });
        } else {
            type = Constant.PROP_NORMAL;
            helper.getView(R.id.txt_choice).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.click(helper.getLayoutPosition());
                }
            });
            if (item.getType() == 1) {
                helper.setText(R.id.txt_choice, R.string.deselect);
            } else if (item.getType() == 2) {
                helper.setText(R.id.txt_choice, R.string.select);
            } else if (item.getType() == 3) {
                helper.setText(R.id.txt_choice, R.string.cancle_sale);
            } else {
                helper.setText(R.id.txt_choice, R.string.select);
            }
        }
        if (item.getType() == 1) {
            helper.setTextColor(R.id.txt_name, mContext.getColor(R.color.white));
            helper.setTextColor(R.id.txt_id, mContext.getColor(R.color.white));
            helper.setTextColor(R.id.txt_time, mContext.getColor(R.color.white));
            helper.setTextColor(R.id.txt_create_time, mContext.getColor(R.color.white));
            helper.setTextColor(R.id.txt_equipment_id, mContext.getColor(R.color.white));
            helper.getView(R.id.root_view).setBackgroundResource(R.drawable.bg_gradual_select);
        } else {
            helper.setTextColor(R.id.txt_name, mContext.getColor(R.color.color_4D67C1));
            helper.setTextColor(R.id.txt_id, mContext.getColor(R.color.color_4D67C1));
            helper.setTextColor(R.id.txt_time, mContext.getColor(R.color.color_4D67C1));
            helper.setTextColor(R.id.txt_create_time, mContext.getColor(R.color.color_4D67C1));
            helper.setTextColor(R.id.txt_equipment_id, mContext.getColor(R.color.color_4D67C1));
            helper.getView(R.id.root_view).setBackgroundResource(R.drawable.rectangle_white);
        }

        helper.getView(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootClick.click(helper.getLayoutPosition(), type);
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

    OnOpenListenerItem itemOpen;

    public void OnclickListenerItem(OnOpenListenerItem itemOpen) {
        this.itemOpen = itemOpen;
    }

    public interface OnOpenListenerItem {
        void click(int position, int type);//0:狗糧 1：箱子
    }

    OnclickListenerRoot rootClick;

    public void OnclickListenerRoot(OnclickListenerRoot rootClick) {
        this.rootClick = rootClick;
    }

    public interface OnclickListenerRoot {
        void click(int position, String type);
    }
}
