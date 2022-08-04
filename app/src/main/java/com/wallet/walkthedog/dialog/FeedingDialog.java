package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class FeedingDialog extends BaseDialogFragment {
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_level)
    TextView txtLevel;
    @BindView(R.id.txt_add_weight)
    TextView txtAddWeight;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_muscle)
    TextView txtMucle;
    @BindView(R.id.txt_perspmality)
    TextView txtPerspmality;
    @BindView(R.id.txt_id)
    TextView txtId;
    @BindView(R.id.txt_consumption)
    TextView txtConsumption;
    @BindView(R.id.txt_surplus)
    TextView txtSurplus;
//    @BindView(R.id.txt_add_weight)
//    TextView txtAddWeight;

    private DogInfoDao mDefultDogInfo;
    private FeedDogFoodDao feedDog;
    private String totalFood;

    @OnClick(R.id.txt_feeding)
    void back(){
        feedCallback.callback();
    }

    public static FeedingDialog newInstance(DogInfoDao mDefultDogInfo, FeedDogFoodDao feedDog, String totalFood) {
        FeedingDialog fragment = new FeedingDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mDefultDogInfo",mDefultDogInfo);
        bundle.putSerializable("feedDog",feedDog);
        bundle.putString("totalFood",totalFood);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_feeding;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mDefultDogInfo = (DogInfoDao) bundle.getSerializable("mDefultDogInfo");
        feedDog = (FeedDogFoodDao) bundle.getSerializable("feedDog");
        totalFood = bundle.getString("totalFood");
        txtConsumption.setText(feedDog.getFood()+"g");
        txtAddWeight.setText("+"+feedDog.getAddWeight()+"g");
        txtSurplus.setText(totalFood+"g");
        if (mDefultDogInfo.getSex() == 0) {
            txtGender.setText(R.string.female);
        } else {
            txtGender.setText(R.string.male);
        }
        txtName.setText(mDefultDogInfo.getName());
        txtLevel.setText(mDefultDogInfo.getLevel()+"");
        txtWeight.setText(mDefultDogInfo.getWeight()+"Kg");
        txtMucle.setText(mDefultDogInfo.getDecimalDog()+"Kg");
        txtPerspmality.setText("????有這個值嗎");
        txtId.setText(mDefultDogInfo.getDogNumberChain());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(getContext()).load(mDefultDogInfo.getImg()).apply(options).into(imgDog);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private OperateFeedCallback feedCallback;

    public void setFeedCallback(OperateFeedCallback feedCallback) {
        this.feedCallback = feedCallback;
    }

    public interface OperateFeedCallback {
        void callback();
    }
}
