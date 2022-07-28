package com.wallet.walkthedog.view.mail.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateMaiPropEvent;
import com.wallet.walkthedog.bus_event.UpdateMailDogEvent;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.db.UserDao;
import com.wallet.walkthedog.db.dao.UserCache;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class TransactionPropActivity extends BaseActivity implements TransactionContract.TransactionView {
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_prop)
    ImageView imgProp;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_charm)
    TextView txtCharm;
    @BindView(R.id.txt_id)
    TextView txtId;
    @BindView(R.id.txt_introduce)
    TextView txtIntroduce;
    @BindView(R.id.txt_operation)
    TextView txtOperation;

    private TransactionContract.TransactionPresenter presenter;
    private PropMailDao propMailDao;
    private PasswordDialog passwordDialog;
    private int status = 0;//0：購買 1：取消售賣
    private String uid = "0";

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.txt_operation)
    void operation() {
        if (status == 0) {
            passwordDialog = PasswordDialog.newInstance();
            passwordDialog.setTheme(R.style.PaddingScreen);
            passwordDialog.setGravity(Gravity.CENTER);
            passwordDialog.show(getSupportFragmentManager(), "edit");
            passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                @Override
                public void callback(String password) {
                    //购买接口
                    presenter.buyProp(new BuyRequest(propMailDao.getId()+"",password));
                    passwordDialog.dismiss();
                }
            });
            passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                @Override
                public void callback() {

                }
            });
        } else if (status == 1) {
            //取消购买接口
            presenter.cancelSellProp(new BuyRequest(propMailDao.getId()+""));
        }

    }

    public static void actionStart(Activity activity, PropMailDao propMailDao) {
        Intent intent = new Intent(activity, TransactionPropActivity.class);
        intent.putExtra("propMailDao", propMailDao);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_transaction_prop;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new TransactionPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        propMailDao = (PropMailDao) getIntent().getSerializableExtra("propMailDao");
        if (propMailDao == null) {
            return;
        }
        List<UserCache> userCaches = UserDao.query(this, null, null);
        if (userCaches.size() > 0) {
            uid = userCaches.get(0).getUid();
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(this).load(propMailDao.getImg()).apply(options).into(imgProp);
        txtTitle.setText(propMailDao.getName());
        txtName.setText(propMailDao.getName());
        txtCharm.setText(propMailDao.getUsercp());
        txtId.setText(propMailDao.getId() + "");
        txtIntroduce.setText(propMailDao.getDescribeData());
        if (propMailDao.getMemberId().equals(uid)) {
            status = 1;
            txtOperation.setText(R.string.cancle_buy);
        } else {
            status = 0;
            txtOperation.setText(R.string.buy);
        }
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
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getWalletInfo(String data, String type) {

    }

    @Override
    public void bugSuccess(String data) {
        passwordDialog.dismiss();
        NormalDialog normalDialog = NormalDialog.newInstance(R.string.buy_success, R.mipmap.icon_normal);
        normalDialog.setTheme(R.style.PaddingScreen);
        normalDialog.setGravity(Gravity.CENTER);
        normalDialog.show(getSupportFragmentManager(), "edit");
        //刷新列表
        EventBus.getDefault().post(new UpdateMaiPropEvent());
        finish();
    }

    @Override
    public void cancelSellSuccess(String data) {
        NormalDialog normalDialog = NormalDialog.newInstance(R.string.cancle_sale, R.mipmap.icon_normal);
        normalDialog.setTheme(R.style.PaddingScreen);
        normalDialog.setGravity(Gravity.CENTER);
        normalDialog.show(getSupportFragmentManager(), "edit");
        //刷新列表
        EventBus.getDefault().post(new UpdateMaiPropEvent());
        finish();
    }

    @Override
    public void setPresenter(TransactionContract.TransactionPresenter presenter) {
        this.presenter = presenter;
    }
}
