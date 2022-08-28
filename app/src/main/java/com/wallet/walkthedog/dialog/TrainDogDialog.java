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
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_consume)
    TextView txtConsume;
    @BindView(R.id.txt_back)
    TextView txtBack;
    @BindView(R.id.txt_balance)
    TextView txtBalance;

    private TrainDao item;
    private String totalFood;
    private int status = 0;//0:训练  1：训练完成确认页面

    @OnClick(R.id.txt_back)
    void feeding() {
        if (status == 0) {
            status = 1;
            callback.callback(item,totalFood);
        } else if (status == 1) {
            dismiss();
        }
    }

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    public static TrainDogDialog newInstance(TrainDao item, String totalFood,int status) {
        TrainDogDialog fragment = new TrainDogDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("TrainDao", item);
        bundle.putString("totalFood", totalFood);
        bundle.putInt("status", status);
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
        status = bundle.getInt("status");
        txtTrainType.setText(item.getName());
        txtTrainIntroduce.setText(item.getDescribe());
        txtConsume.setText(String.format(getString(R.string.g), String.valueOf(item.getConsume())));//显示消耗粮食
        txtBalance.setText(String.format(getString(R.string.g), String.valueOf(totalFood)));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(getContext()).load(item.getImg()).apply(options).into(imgTrainType);
        if(status == 1){
            updateUi();
        }
    }

    private void updateUi(){
        txtStatus.setText(R.string.add_reap);
        txtBack.setText(R.string.confirm);
        txtConsume.setText(String.format(getString(R.string.g), String.valueOf(item.getReap())));//显示增加粮食
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
        void callback(TrainDao item, String totalFood);
    }
}
