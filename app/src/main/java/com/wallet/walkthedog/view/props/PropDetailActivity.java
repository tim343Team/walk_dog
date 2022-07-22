package com.wallet.walkthedog.view.props;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.data.Constant;

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
    View tvSubmit;


    private String type;
    private String id;
    private boolean isOpening = false;
    private ChoicePropsContract.ChoicePropsPresenter presenter;

    @OnClick(R.id.tv_submit)
    void open() {
        //TODO 打开接口
        finish();
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, String id,String type) {
        Intent intent = new Intent(activity, PropDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        activity.startActivity(intent);
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
        if (isOpening) {
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
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

    }

    @Override
    public void getPropSuccess(List<PropDao> data) {

    }

    @Override
    public void getRemovePropSuccess(int dao) {

    }

    @Override
    public void getAddPropSuccess(int dao) {

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
    }

    @Override
    public void useDogFoodSuccess(String data) {

    }

    @Override
    public void setPresenter(ChoicePropsContract.ChoicePropsPresenter presenter) {
        this.presenter = presenter;
    }

    void switchView(){
        if(type.equals(Constant.PROP_NORMAL)){
            boxView.setVisibility(View.GONE);
            foodView.setVisibility(View.GONE);
            normalView.setVisibility(View.VISIBLE);
        }else if(type.equals(Constant.PROP_FOOD)){
            boxView.setVisibility(View.GONE);
            foodView.setVisibility(View.VISIBLE);
            normalView.setVisibility(View.GONE);
        }else if(type.equals(Constant.PROP_BOX)){
            boxView.setVisibility(View.VISIBLE);
            foodView.setVisibility(View.GONE);
            normalView.setVisibility(View.GONE);
        }
    }
}
