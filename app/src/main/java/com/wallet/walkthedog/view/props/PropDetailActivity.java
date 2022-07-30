package com.wallet.walkthedog.view.props;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.dialog.MoreOperationDialog;
import com.wallet.walkthedog.dialog.MorePropOperationDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.OpenindDialog;
import com.wallet.walkthedog.dialog.SellPropDialog;
import com.wallet.walkthedog.dialog.SellPropNoticeDialog;
import com.wallet.walkthedog.even.UpdateHomeData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class PropDetailActivity extends BaseActivity implements ChoicePropsContract.ChoicePropsView {
    @BindView(R.id.ll_box)
    View boxView;
    @BindView(R.id.ll_dog_food)
    View foodView;
    @BindView(R.id.ll_normal)
    View normalView;
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.txt_title)
    TextView TextTitle;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_time)
    TextView txtTimee;
    @BindView(R.id.txt_charm)
    TextView txtCharm;
    @BindView(R.id.txt_id)
    TextView txtPropId;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_access)
    TextView txtAccess;
    //
    @BindView(R.id.txt_dog_name)
    TextView txtDogname;
    @BindView(R.id.txt_dog_id)
    TextView txtDogId;
    @BindView(R.id.txt_user_id)
    TextView txtUserId;
    //
    @BindView(R.id.txt_introduce)
    TextView txtIntroduc;
    @BindView(R.id.tv_submit)
    ShadowTextView tvSubmit;

    private PropDao propDao;
    private String currentDogId;
    private String type;
    private String id;
    private boolean isChange = false;
    private ChoicePropsContract.ChoicePropsPresenter presenter;

    @OnClick(R.id.img_more)
    void more() {
        MorePropOperationDialog dialog = MorePropOperationDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setFeedCallback(new MorePropOperationDialog.OperateFeedCallback() {
            @Override
            public void sell() {
                //售卖
                if (propDao.getType() == 1) {
                    //判断是否装备
                    SellPropNoticeDialog noticeDialog = SellPropNoticeDialog.newInstance(R.string.release_prop,R.string.release_prop_notice);
                    noticeDialog.setTheme(R.style.PaddingScreen);
                    noticeDialog.setGravity(Gravity.CENTER);
                    noticeDialog.show(getSupportFragmentManager(), "edit");
                } else {
                    //输入价格
                    SellPropDialog sellPropDialog = SellPropDialog.newInstance(R.string.sell_prop);
                    sellPropDialog.setTheme(R.style.PaddingScreen);
                    sellPropDialog.setGravity(Gravity.CENTER);
                    sellPropDialog.show(getSupportFragmentManager(), "edit");
                    sellPropDialog.setCallback(new SellPropDialog.OperateCallback() {
                        @Override
                        public void callback(String price) {
                            presenter.sellProp(new SellRequest(propDao.getId(), price));
                            sellPropDialog.dismiss();
                        }
                    });
                }
                //售卖
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.tv_submit)
    void open() {
        if (type.equals(Constant.PROP_BOX)) {
            //打开宝箱接口
            presenter.openBox(new OpreationPropRequest(propDao.getId()),0);
        } else if (type.equals(Constant.PROP_FOOD)) {
            //打开狗粮接口
            presenter.useDogFood(new OpreationPropRequest(propDao.getId()),0);
        } else if (type.equals(Constant.PROP_NORMAL)) {
            if (propDao.getType() == 1) {
                //取消装备
                presenter.getRemoveProp(new OpreationPropRequest(propDao.getId(), String.valueOf(propDao.getDecorateDogId())), 0);
            } else {
                //装备
                presenter.getAddProp(new OpreationPropRequest(propDao.getId(), currentDogId), 0);
            }
        }

    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, String id, String type, PropDao propDao, String currentDogId) {
        Intent intent = new Intent(activity, PropDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("propDao", propDao);
        intent.putExtra("currentDogId", currentDogId);
        activity.startActivity(intent);
    }

    @Override
    public void finish() {
        if (isChange) {
            EventBus.getDefault().post(new UpdateHomeData());
            EventBus.getDefault().post(new UpdatePropsEvent());
        }
        super.finish();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_prop_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new ChoicePropsPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        currentDogId = getIntent().getStringExtra("currentDogId");
        propDao = (PropDao) getIntent().getSerializableExtra("propDao");
        updateUi();

    }

    private void updateUi() {
        if (type.equals(Constant.PROP_BOX)) {
            tvSubmit.setText(R.string.open);
        } else if (type.equals(Constant.PROP_FOOD)) {
            tvSubmit.setText(R.string.open);
        } else if (type.equals(Constant.PROP_NORMAL)) {
            //装备，取消装备
            if (propDao.getType() == 1) {
                tvSubmit.setText(R.string.deselect);
            } else if(propDao.getType() == 2){
                tvSubmit.setText(R.string.select);
            }else if(propDao.getType() == 3){
                tvSubmit.setText(R.string.cancle_sale);
            } else {
                tvSubmit.setText(R.string.select);
            }
        }
        switchView();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        OpreationPropRequest request = new OpreationPropRequest(id);
        presenter.getPropDetailInfo(request);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalDialog dialog = NormalDialog.newInstance(toastMessage, R.mipmap.icon_normal_no,R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void getAddFail(Integer code, String toastMessage) {
        NormalDialog dialog = NormalDialog.newInstance(R.string.cancle_props, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void getPropSuccess(List<PropDao> data) {

    }

    @Override
    public void getRemovePropSuccess(int dao) {
        propDao.setType(2);
        updateUi();
        isChange = true;
    }

    @Override
    public void getAddPropSuccess(int dao) {
        propDao.setType(1);
        updateUi();
        isChange = true;
    }

    @Override
    public void getPropDetailSuccess(PropDetailDao data) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(this).load(data.getImg()).apply(options).into(imgDog);
        TextTitle.setText(data.getName());
        txtPrice.setText(String.format(getString(R.string.price_s), data.getPrice() + ""));
        txtName.setText(data.getName());
        txtTimee.setText(data.getCreateTime());
        txtCharm.setText(data.getUsercp());
        txtPropId.setText(data.getId() + "");
        txtDogname.setText(data.getDogName());
        txtDogId.setText(data.getDogId() + "");
        txtUserId.setText(data.getDogUserId() + "");
        txtIntroduc.setText(data.getDescribeData());
        //
        txtWeight.setText(String.format(getString(R.string.g), data.getWeight()));
    }

    @Override
    public void useDogFoodSuccess(String data,int postion) {
        //打開狗糧
        isChange = true;
        OpenindDialog openindDialog=OpenindDialog.newInstance(Constant.PROP_FOOD);
        openindDialog.setTheme(R.style.PaddingScreen);
        openindDialog.setGravity(Gravity.CENTER);
        openindDialog.show(getSupportFragmentManager(), "edit");
        openindDialog.setCallback(new OpenindDialog.OperateCallback() {
            @Override
            public void callback(String name) {
                NormalDialog dialog = NormalDialog.newInstance(String.format(getString(R.string.input_prop_success), getResources().getString(R.string.dog_food)), R.mipmap.icon_normal);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
                openindDialog.dismiss();
            }
        });
        finish();
    }

    @Override
    public void sellProp(String data) {
        isChange = true;
        finish();
    }

    @Override
    public void openBoxSuccess(BoxDao dao,int postion) {
        //打开宝箱成功
        isChange = true;
        OpenindDialog openindDialog=OpenindDialog.newInstance(Constant.PROP_NORMAL,dao);
        openindDialog.setTheme(R.style.PaddingScreen);
        openindDialog.setGravity(Gravity.CENTER);
        openindDialog.show(getSupportFragmentManager(), "edit");
        openindDialog.setCallback(new OpenindDialog.OperateCallback() {
            @Override
            public void callback(String name) {
                NormalDialog dialog = NormalDialog.newInstance(String.format(getString(R.string.input_prop_success), name), R.mipmap.icon_normal);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
                openindDialog.dismiss();
            }
        });
        finish();
    }

    @Override
    public void setPresenter(ChoicePropsContract.ChoicePropsPresenter presenter) {
        this.presenter = presenter;
    }

    void switchView() {
        if (type.equals(Constant.PROP_NORMAL)) {
            boxView.setVisibility(View.GONE);
            foodView.setVisibility(View.GONE);
            normalView.setVisibility(View.VISIBLE);
        } else if (type.equals(Constant.PROP_FOOD)) {
            boxView.setVisibility(View.GONE);
            foodView.setVisibility(View.VISIBLE);
            normalView.setVisibility(View.GONE);
        } else if (type.equals(Constant.PROP_BOX)) {
            boxView.setVisibility(View.VISIBLE);
            foodView.setVisibility(View.GONE);
            normalView.setVisibility(View.GONE);
        }
    }
}
