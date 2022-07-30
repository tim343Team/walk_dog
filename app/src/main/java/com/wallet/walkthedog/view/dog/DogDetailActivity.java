package com.wallet.walkthedog.view.dog;

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
import com.wallet.walkthedog.bus_event.UpdateDogEvent;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.dialog.FeedingDialog;
import com.wallet.walkthedog.dialog.IdentityDialog;
import com.wallet.walkthedog.dialog.MoreDogOperationDialog;
import com.wallet.walkthedog.dialog.MoreOperationDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.SellPropDialog;
import com.wallet.walkthedog.dialog.SellPropNoticeDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.props.ChoicePropsPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class DogDetailActivity extends BaseActivity implements DogDetailContract.DogDetailView {
    @BindView(R.id.txt_level_2)
    TextView txtDogLevel2;
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_status)
    TextView txtState;
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindViews({R.id.img_equipment_1, R.id.img_equipment_2, R.id.img_equipment_3})
    ImageView[] imgEquipments;

    private boolean isChange = false;
    private String totalFood = "0";
    private DogInfoDao dogInfoDao;
    private DogDetailContract.DogDetailPresenter presenter;

    @OnClick(R.id.img_more)
    void startMore() {
        MoreDogOperationDialog dialog = MoreDogOperationDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setFeedCallback(new MoreDogOperationDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                presenter.getFeedDog(dogInfoDao.getId());
            }
        });
        dialog.setSellCallback(new MoreDogOperationDialog.OperateSellCallback() {
            @Override
            public void callback() {
                //售卖
                if (dogInfoDao.getType() == 3) {
                    //判断是否選中狗狗
                    SellPropNoticeDialog noticeDialog = SellPropNoticeDialog.newInstance(R.string.release_dog, R.string.release_dog_notice);
                    noticeDialog.setTheme(R.style.PaddingScreen);
                    noticeDialog.setGravity(Gravity.CENTER);
                    noticeDialog.show(getSupportFragmentManager(), "edit");
                } else {
                    //输入价格
                    SellPropDialog sellPropDialog = SellPropDialog.newInstance(R.string.sell_dog);
                    sellPropDialog.setTheme(R.style.PaddingScreen);
                    sellPropDialog.setGravity(Gravity.CENTER);
                    sellPropDialog.show(getSupportFragmentManager(), "edit");
                    sellPropDialog.setCallback(new SellPropDialog.OperateCallback() {
                        @Override
                        public void callback(String price) {
                            //出售狗狗
                            presenter.sellDog(new SellRequest(dogInfoDao.getId(), price));
                            sellPropDialog.dismiss();
                        }
                    });
                }
                //售卖
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.img_identity)
    void startIdentity() {
        if (dogInfoDao == null) {
            return;
        }
        IdentityDialog dialog = IdentityDialog.newInstance(dogInfoDao);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @OnClick(R.id.tv_submit)
    void submit() {
        //选择使用
        presenter.useDog(dogInfoDao.getId());
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, DogInfoDao dogInfoDao) {
        Intent intent = new Intent(activity, DogDetailActivity.class);
        intent.putExtra("dogInfoDao", dogInfoDao);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_dog_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new DogDetailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        dogInfoDao = (DogInfoDao) getIntent().getSerializableExtra("dogInfoDao");
        if (dogInfoDao.getStarvation() == 2) {
            txtState.setText(R.string.full_of_energy);
            txtState.setTextColor(getResources().getColor(R.color.white));
        } else if (dogInfoDao.getStarvation() == 1) {
            txtState.setText(R.string.full_of_hunger);
            txtState.setTextColor(getResources().getColor(R.color.color_ffbe0d));
        }
        txtTitle.setText(dogInfoDao.getName());
        txtDogLevel2.setText("LEVEL " + dogInfoDao.getLevel());
        setProgress(progressBg, progressBar, progressTxt, dogInfoDao.getRateOfProgress() / 100.00);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(this).load(dogInfoDao.getImg()).apply(options).into(imgDog);
        initEquipment();
        presenter.getWallet("2");//获取狗粮总数
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

    @Override
    public void finish() {
        if (isChange) {
            EventBus.getDefault().post(new UpdateHomeData());
            EventBus.getDefault().post(new UpdateDogEvent());
        }
        super.finish();
    }

    void initEquipment() {
        if (dogInfoDao.getPropList() == null) {
            return;
        }
        for (int i = 0; i < dogInfoDao.getPropList().size(); i++) {
            if (i > 2) {
                break;
            }
            if (dogInfoDao.getPropList().get(i).getId() == null) {
                continue;
            }
            imgEquipments[i].setVisibility(View.VISIBLE);
            RequestOptions optionsEq = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_bell)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
            Glide.with(this).load(dogInfoDao.getPropList().get(i).getImg()).apply(optionsEq).into(imgEquipments[i]);
        }
    }

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
        NormalDialog dialog = NormalDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void sellDog(String data) {
        isChange = true;
        finish();
    }

    @Override
    public void feedSuccessful(String data) {
        //喂食成功,更新数据
        NormalDialog dialog = NormalDialog.newInstance(R.string.feeding_su, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        presenter.getWallet("2");//获取狗粮总数
        isChange = true;
    }

    @Override
    public void feedFail(Integer code, String toastMessage) {
        //喂食失败
        if (code == 1) {
            //获取狗粮数据
            NormalDialog dialog = NormalDialog.newInstance(R.string.feeding_error, R.mipmap.icon_normal_no, R.color.color_E12828);
            dialog.setTheme(R.style.PaddingScreen);
            dialog.setGravity(Gravity.CENTER);
            dialog.show(getSupportFragmentManager(), "edit");
        }

    }

    @Override
    public void getFeedDogInfo(String data) {
        //喂食弹唱
        FeedingDialog dialog = FeedingDialog.newInstance(dogInfoDao, data, totalFood);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setFeedCallback(new FeedingDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                //調用餵食接口
                presenter.feedDog(dogInfoDao.getId());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void getWalletInfo(String data, String type) {
        if (type.equals("2")) {
            totalFood = data;
        }
    }

    @Override
    public void useDogSuccess(String data, String dogId) {
        //选择成功弹窗
        SharedPrefsHelper.getInstance().saveDogId(dogId);
        NormalDialog dialog = NormalDialog.newInstance(R.string.choose_select, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //更新主页
        isChange=true;
    }


    @Override
    public void setPresenter(DogDetailContract.DogDetailPresenter presenter) {
        this.presenter = presenter;
    }
}
