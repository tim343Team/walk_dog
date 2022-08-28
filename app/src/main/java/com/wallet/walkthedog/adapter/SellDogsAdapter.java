package com.wallet.walkthedog.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.DogMailDao;

import java.util.List;

import tim.com.libnetwork.utils.DateTimeUtil;

public class SellDogsAdapter extends BaseQuickAdapter<DogInfoDao, BaseViewHolder> {

    public SellDogsAdapter(int layoutResId, @Nullable List<DogInfoDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DogInfoDao item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        ImageView imgGender = (ImageView) helper.getView(R.id.img_gender);
        helper.getView(R.id.group_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(item,helper.getLayoutPosition());
            }
        });
        if (item.getSex() == 0) {
            Glide.with(mContext).load(R.mipmap.icon_blue_female).apply(options).into(imgGender);
        } else {
            Glide.with(mContext).load(R.mipmap.icon_blue_male).apply(options).into(imgGender);
        }
        //升级栏目
        setProgress(helper,item.getRateOfProgress()/100.00);
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.img_dog));
        helper.setText(R.id.txt_level, "Lv." + item.getLevel());
        helper.setText(R.id.txt_name,item.getName());
        helper.setText(R.id.txt_price,String.valueOf(item.getPrice()));
        helper.setText(R.id.txt_cancle,R.string.cancle_buy);
        helper.setText(R.id.txt_count,String.format(mContext.getString(R.string._time), item.getWalkTheDogCount() + "") );//遛狗次数
        helper.setText(R.id.txt_time, DateTimeUtil.second2Time(item.getWalkTheDogTime()));//遛狗时间
        helper.setText(R.id.txt_id,item.getDogNumberChain());//id

    }

    //设置进度条
    private void setProgress(BaseViewHolder helper, double percentage) {
        View progressBg = helper.getView(R.id.progress_bg);
        View progressBar = helper.getView(R.id.progress_bar);
        TextView progressTxt = helper.getView(R.id.progress_txt);
        progressBg.post(new Runnable() {
            @Override
            public void run() {
                int progressAll =  progressBg.getWidth();
                int progress = (int) (progressAll * percentage);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
                params.width = progress;
                progressBar.setLayoutParams(params);
                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
                if (percentage < 0.2) {
                    params2.leftMargin = 0;
                }else if(percentage >0.9){
                    params2.leftMargin = (int) (progress - progressAll * 0.4);
                } else {
                    params2.leftMargin = (int) (progress - progressAll * 0.18);
                }
                progressTxt.setLayoutParams(params2);
                progressTxt.setText(percentage * 100 + "%");
            }
        });
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(DogInfoDao item,int position);
    }
}
