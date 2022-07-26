package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogMailDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.utils.DateTimeUtil;

public class BuyOrRentDogDialog extends BaseDialogFragment {
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_accept)
    TextView txtAccept;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_level)
    TextView txtLevel;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_muscle)
    TextView txtMuscle;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_id)
    TextView txtId;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_fee)
    TextView txtFee;
    @BindView(R.id.txt_balance)
    TextView txtBalance;
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.ll_rental_time)
    View llRentalTime;
    private int type = 1;//1:购买 2:租赁
    private String totalProperty = "0";
    private DogMailDao dogMailDao;

    @OnClick({R.id.back, R.id.txt_refuse})
    void back() {
        dismiss();
    }

    @OnClick(R.id.txt_accept)
    void buyDog() {
        callback.callback();
    }

    public static BuyOrRentDogDialog newInstance(DogMailDao dogMailDao,String totalProperty,int type) {
        BuyOrRentDogDialog fragment = new BuyOrRentDogDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogMailDao", dogMailDao);
        bundle.putString("totalProperty", totalProperty);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_buy_or_rental_dog;
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
        type = bundle.getInt("type", 1);
        totalProperty = bundle.getString("totalProperty" );
        dogMailDao = (DogMailDao) bundle.getSerializable("dogMailDao");
        if (type == 1) {
            //购买
            txtTitle.setText(R.string.buy);
            txtAccept.setText(R.string.buy);
            llRentalTime.setVisibility(View.GONE);
        } else if (type == 2) {
            //租赁
            txtTitle.setText(R.string.rental);
            txtAccept.setText(R.string.rental);
            llRentalTime.setVisibility(View.VISIBLE);
        }

        if (dogMailDao.getSex() == 0) {
            txtGender.setText(R.string.male);
        } else {
            txtGender.setText(R.string.female);
        }
        txtName.setText(dogMailDao.getName());
        txtLevel.setText(dogMailDao.getLevel()+"");
        txtWeight.setText(dogMailDao.getWeight()+"Kg");
        txtMuscle.setText(dogMailDao.getDecimalDog()+"Kg");
        txtTime.setText(DateTimeUtil.second2Time(Long.valueOf(dogMailDao.getWalkTheDogTime())));
        txtId.setText(dogMailDao.getId());
        txtPrice.setText(dogMailDao.getPrice());
        txtBalance.setText(totalProperty);
        txtFee.setText("缺少字段");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(getContext()).load(dogMailDao.getImg()).apply(options).into(imgDog);
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
        void callback();
    }
}
