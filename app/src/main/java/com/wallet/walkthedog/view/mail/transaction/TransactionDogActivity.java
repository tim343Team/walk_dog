package com.wallet.walkthedog.view.mail.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateMailDogEvent;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dialog.BuyOrRentDogDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.DateTimeUtil;

public class TransactionDogActivity extends BaseActivity implements TransactionContract.TransactionView{
    @BindView(R.id.txt_operation)
    TextView txtPeration;
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.txt_level_2)
    TextView txtLevel;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_muscle)
    TextView txtMuscle;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_id)
    TextView txtId;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_character)
    TextView txtCharacter;
    @BindView(R.id.txt_fee)
    TextView txtFee;
    @BindView(R.id.txt_price)
    TextView txtPrice;

    private int status = 0;//0：購買 1：取消售賣
    private String totalProperty = "0";
    private DogMailDao dogMailDao;
    private BuyOrRentDogDialog dialog;
    private PasswordDialog passwordDialog;
    private TransactionContract.TransactionPresenter presenter;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.txt_operation)
    void operation() {
        if (status == 0) {
            dialog = BuyOrRentDogDialog.newInstance(dogMailDao,totalProperty,1);
            dialog.setTheme(R.style.PaddingScreen);
            dialog.setGravity(Gravity.CENTER);
            dialog.show(getSupportFragmentManager(), "edit");
            dialog.setCallback(new BuyOrRentDogDialog.OperateCallback() {
                @Override
                public void callback() {
                    passwordDialog = PasswordDialog.newInstance();
                    passwordDialog.setTheme(R.style.PaddingScreen);
                    passwordDialog.setGravity(Gravity.CENTER);
                    passwordDialog.show(getSupportFragmentManager(), "edit");
                    passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                        @Override
                        public void callback(String password) {
                            //购买狗狗接口
                            presenter.buyDog(new BuyRequest(dogMailDao.getId(),password));
                        }
                    });
                    passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                        @Override
                        public void callback() {

                        }
                    });
                }
            });
        } else if (status == 1) {

        }
    }

    public static void actionStart(Activity activity, DogMailDao dogMailDao) {
        Intent intent = new Intent(activity, TransactionDogActivity.class);
        intent.putExtra("dogMailDao", dogMailDao);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_transaction_dog;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new TransactionPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        dogMailDao = (DogMailDao) getIntent().getSerializableExtra("dogMailDao");
        if (dogMailDao == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(this).load(dogMailDao.getImg()).apply(options).into(imgDog);
        if (dogMailDao.getSex() == 0) {
            txtGender.setText(R.string.male);
        } else {
            txtGender.setText(R.string.female);
        }
        txtTitle.setText(dogMailDao.getName());
        txtLevel.setText("LEVEL " + dogMailDao.getLevel());
        txtMuscle.setText(dogMailDao.getDecimalDog()+"Kg");
        txtWeight.setText(dogMailDao.getWeight()+"Kg");
        txtId.setText(dogMailDao.getId()+"");
        txtTime.setText(DateTimeUtil.second2Time(Long.valueOf(dogMailDao.getWalkTheDogTime())));
        txtPrice.setText(dogMailDao.getPrice());
        setProgress(progressBg, progressBar, progressTxt, dogMailDao.getRateOfProgress() / 100.00);
        txtFee.setText("缺少字段");
        txtCharacter.setText("缺少字段");
        presenter.getWallet("1");
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private int progressAll = 0;

    //设置进度条
    private void setProgress(View progressBg, View progressBar, TextView progressTxt, double percentage) {
        progressBg.post(new Runnable() {
            @Override
            public void run() {
                int progressAll = progressBg.getWidth();
                int progress = (int) (progressAll * percentage);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
                params.width = progress;
                progressBar.setLayoutParams(params);
                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
                if (percentage < 0.2) {
                    params2.leftMargin = 0;
                } else if (percentage > 0.9) {
                    params2.leftMargin = (int) (progress - progressAll * 0.3);
                } else {
                    params2.leftMargin = (int) (progress - progressAll * 0.18);
                }
                progressTxt.setLayoutParams(params2);
                progressTxt.setText(percentage * 100 + "%");
            }
        });
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalDialog normalDialog = NormalDialog.newInstance(R.string.password_error, R.mipmap.icon_normal_no,R.color.color_E12828);
        normalDialog.setTheme(R.style.PaddingScreen);
        normalDialog.setGravity(Gravity.CENTER);
        normalDialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void getWalletInfo(String data, String type) {
        if (type.equals("1")) {
            totalProperty = data;
        }
    }

    @Override
    public void bugDogSuccess(String data) {
        passwordDialog.dismiss();
        dialog.dismiss();
        NormalDialog normalDialog = NormalDialog.newInstance(R.string.buy_success, R.mipmap.icon_normal);
        normalDialog.setTheme(R.style.PaddingScreen);
        normalDialog.setGravity(Gravity.CENTER);
        normalDialog.show(getSupportFragmentManager(), "edit");
        //刷新列表
        EventBus.getDefault().post(new UpdateMailDogEvent());
    }

    @Override
    public void setPresenter(TransactionContract.TransactionPresenter presenter) {
        this.presenter=presenter;
    }
}
