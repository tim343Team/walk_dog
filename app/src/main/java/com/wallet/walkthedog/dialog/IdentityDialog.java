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

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class IdentityDialog extends BaseDialogFragment {
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_level)
    TextView txtLevel;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_muscle)
    TextView txtMucle;
    @BindView(R.id.txt_perspmality)
    TextView txtPerspmality;
    @BindView(R.id.txt_id)
    TextView txtId;

    private DogInfoDao mDefultDogInfo;

    @OnClick(R.id.txt_cancle)
    void back(){
        dismiss();
    }

    public static IdentityDialog newInstance(DogInfoDao mDefultDogInfo) {
        IdentityDialog fragment = new IdentityDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mDefultDogInfo",mDefultDogInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_identity;
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
        if (mDefultDogInfo.getSex() == 0) {
            txtGender.setText(R.string.female);
        } else {
            txtGender.setText(R.string.male);
        }
        txtName.setText(mDefultDogInfo.getName()==null?mDefultDogInfo.getDogName():mDefultDogInfo.getName());
        txtLevel.setText(mDefultDogInfo.getLevel()+"");
        txtWeight.setText(mDefultDogInfo.getWeight()+"Kg");
        txtMucle.setText(mDefultDogInfo.getDecimalDog()+"Kg");
        txtPerspmality.setText("????有這個值嗎");
        txtId.setText(mDefultDogInfo.getId());
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
}
