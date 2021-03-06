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
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dialog.BuyFoodDialog;

import java.util.List;

import tim.com.libnetwork.utils.DateTimeUtil;

public class InviteDogAdapter extends BaseQuickAdapter<FriendInfoDao, BaseViewHolder> {

    public InviteDogAdapter(int layoutResId, @Nullable List<FriendInfoDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendInfoDao item) {
        helper.getView(R.id.txt_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callback(item);
            }
        });

        ImageView imgGender = (ImageView) helper.getView(R.id.img_gender);
        if (item.getSex() == 0) {
            imgGender.setBackgroundResource(R.mipmap.icon_black_male);
        } else {
            imgGender.setBackgroundResource(R.mipmap.icon_black_female);
        }
        helper.setText(R.id.txt_dog_name,item.getNftName());
        helper.setText(R.id.txt_rent_name,item.getName());
        helper.setText(R.id.txt_level,"Lv." + item.getLevel());
        helper.setText(R.id.txt_time, String.format(mContext.getString(R.string._time), item.getWalkTheDogCount() + ""));
        helper.setText(R.id.txt_trip, DateTimeUtil.second2Time(item.getWalkTheDogTime()));//总次数
        helper.setText(R.id.txt_id, item.getId()+"");//总次数
        //TODO 缺少
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.icon_null_dog)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
//        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.img_dog));
//        helper.setText(R.id.txt_number, item.getDayLimit() + "/2");
        //精力状态
//        if(item.getStarvation()==1){
//            helper.setText(R.id.txt_status,R.string.full_of_hunger);
//        }else {
//            helper.setText(R.id.txt_status,R.string.full_of_energy);
//        }
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(FriendInfoDao dao);
    }
}
