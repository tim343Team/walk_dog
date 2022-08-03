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
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropDao;

import java.util.ArrayList;
import java.util.List;

public class DogMailAdapter extends BaseQuickAdapter<DogMailDao, BaseViewHolder> {
    private String uid;

    public DogMailAdapter(int layoutResId, @Nullable List<DogMailDao> data, String uid) {
        super(layoutResId, data);
        this.uid = uid;
    }

    @Override
    protected void convert(BaseViewHolder helper, DogMailDao item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        ImageView imgGender = (ImageView) helper.getView(R.id.img_gender);
        helper.getView(R.id.group_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.click(item);
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
        helper.setText(R.id.txt_price,String.valueOf(item.getPrice())+" suzu");
        if(item.getMemberId().equals(uid)){
            helper.setText(R.id.txt_cancle,R.string.cancle_buy);
        }else {
            helper.setText(R.id.txt_cancle,R.string.buy);
        }
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
        void click(DogMailDao item);
    }
}
