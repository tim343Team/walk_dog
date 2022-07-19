package com.wallet.walkthedog.adapter;

import android.util.TimeUtils;
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
import com.wallet.walkthedog.dao.EquipmentDao;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import java.util.List;

import tim.com.libnetwork.utils.DateTimeUtil;

public class MyDogsAdapter extends BaseQuickAdapter<DogInfoDao, BaseViewHolder> {
    private String currentDogId;
    private int selectPosition;

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
        //升级栏目
        setProgress(helper,item.getRateOfProgress()/100.00);
        helper.setText(R.id.txt_number, item.getDayLimit() + "/2");
        helper.setText(R.id.txt_time, String.format(mContext.getString(R.string._time), item.getWalkTheDogCount() + ""));
        helper.setText(R.id.txt_trip, DateTimeUtil.second2Time(Long.parseLong(item.getWalkTheDogTime())));//总次数
        TextView txtSelect = helper.getView(R.id.txt_select);
        View rootView = helper.getView(R.id.root_view);
        if (currentDogId.equals(item.getId())) {
            txtSelect.setText(R.string.deselect);
            txtSelect.setTextColor(mContext.getResources().getColor(R.color.color_4D67C1));
            txtSelect.setBackgroundResource(R.drawable.rectangle_white);
            rootView.setBackgroundResource(R.mipmap.bg_item_select);
            selectPosition = helper.getAdapterPosition();
        } else {
            txtSelect.setText(R.string.select);
            txtSelect.setTextColor(mContext.getResources().getColor(R.color.white));
            txtSelect.setBackgroundResource(R.drawable.button_gradual_background);
            rootView.setBackgroundResource(R.drawable.rectangle_white);
        }
        //精力状态
        if(item.getStarvation()==1){
            helper.setText(R.id.txt_status,R.string.full_of_hunger);
        }else {
            helper.setText(R.id.txt_status,R.string.full_of_energy);
        }
        helper.getView(R.id.txt_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentDogId.equals(item.getId())) {
                    return;
                } else {
                    currentDogId = item.getId();
                    SharedPrefsHelper.getInstance().saveDogId(currentDogId);
                    callback.callback(item, helper.getAdapterPosition(), selectPosition);
                }
            }
        });
        helper.getView(R.id.txt_feeding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedCallback.callback(item);
            }
        });
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
                    params2.leftMargin = (int) (progress - progressAll * 0.3);
                } else {
                    params2.leftMargin = (int) (progress - progressAll * 0.18);
                }
                progressTxt.setLayoutParams(params2);
                progressTxt.setText(percentage * 100 + "%");
            }
        });
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(DogInfoDao dao, int selectPosition, int oldSelectPosition);
    }

    private FeedCallback feedCallback;

    public void setFeedCallback(FeedCallback feedCallback) {
        this.feedCallback = feedCallback;
    }

    public interface FeedCallback {
        void callback(DogInfoDao dao);
    }
}
