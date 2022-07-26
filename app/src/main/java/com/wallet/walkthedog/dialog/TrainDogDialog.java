package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.TrainDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class TrainDogDialog extends BaseDialogFragment {
    @BindView(R.id.img_train_type)
    ImageView imgTrainType;
    @BindView(R.id.txt_train_type)
    TextView txtTrainType;
    @BindView(R.id.txt_train_introduce)
    TextView txtTrainIntroduce;
    @BindView(R.id.txt_consume)
    TextView txtConsume;
    @BindView(R.id.txt_balance)
    TextView txtBalance;

    private TrainDao item;
    private String totalFood;

    @OnClick(R.id.txt_back)
    void feeding(){
        callback.callback(item.getId());
    }

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    public static TrainDogDialog newInstance(TrainDao item,String totalFood) {
        TrainDogDialog fragment = new TrainDogDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("TrainDao",item);
        bundle.putString("totalFood",totalFood);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_train_dog;
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
        item = (TrainDao) bundle.getSerializable("TrainDao");
        totalFood = bundle.getString("totalFood");
        txtTrainType.setText(item.getName());
        txtTrainIntroduce.setText(item.getDescribe());
        txtConsume.setText(String.format(getString(R.string.g), String.valueOf(item.getConsume())));
        txtBalance.setText(String.format(getString(R.string.g), String.valueOf(totalFood)));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(getContext()).load(item.getImg()).apply(options).into(imgTrainType);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(int trainId);
    }
}
