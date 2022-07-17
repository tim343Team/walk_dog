package com.wallet.walkthedog.adapter;

import android.util.TimeUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.EquipmentDao;

import java.util.List;

import tim.com.libnetwork.utils.DateTimeUtil;

public class MyDogsAdapter extends BaseQuickAdapter<DogInfoDao, BaseViewHolder> {
    private String currentDogId;

    public MyDogsAdapter(int layoutResId, @Nullable List<DogInfoDao> data, String currentDogId) {
        super(layoutResId, data);
        this.currentDogId = currentDogId;
    }

    @Override
    protected void convert(BaseViewHolder helper, DogInfoDao item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.img_dog));
        helper.setText(R.id.txt_dog_name, item.getName());
        helper.setText(R.id.txt_level, "Lv." + item.getLevel());
        ImageView imgGender = (ImageView) helper.getView(R.id.img_gender);
        if (item.getSex() == 0) {
            imgGender.setBackgroundResource(R.mipmap.icon_black_male);
        } else {
            imgGender.setBackgroundResource(R.mipmap.icon_black_female);
        }
        //TODO 缺少升级栏目
        //TODO 缺少精力状态
        helper.setText(R.id.txt_number, item.getDayLimit() + "/2");
        helper.setText(R.id.txt_time, String.format(mContext.getString(R.string._time), item.getWalkTheDogCount() + ""));
        helper.setText(R.id.txt_trip, DateTimeUtil.second2Time(Long.parseLong(item.getWalkTheDogTime())));//总次数
//        currentDogId.equals(item.getId())?:

    }
}
