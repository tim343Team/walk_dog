package com.wallet.walkthedog.view.merchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.dao.BusinessAuthDao;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.untils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class MerchantAgreementActivity extends BaseActivity implements MerchantContract.MerchantView {
    @BindView(R.id.tv_confirm)
    ShadowTextView txtConfirm;
    @BindView(R.id.txt_notice_1)
    TextView txtNotice1;
    @BindView(R.id.txt_notice_2)
    TextView txtNotice2;
    @BindView(R.id.txt_notice_3)
    TextView txtNotice3;
    @BindView(R.id.txt_notice_4)
    TextView txtNotice4;
    @BindView(R.id.txt_notice_5)
    TextView txtNotice5;
    @BindView(R.id.txt_notice_6)
    TextView txtNotice6;
    @BindView(R.id.txt_notice_7)
    TextView txtNotice7;
    @BindView(R.id.txt_notice_8)
    TextView txtNotice8;
    @BindView(R.id.txt_notice_9)
    TextView txtNotice9;
    @BindView(R.id.img_select)
    ImageView imgSelect;

    private int type;//0:申請  2：退出
    private boolean isSelect = false;
    private MerchantContract.MerchantPresenter presenter;

    @OnClick(R.id.img_select)
    void select() {
        if (isSelect) {
            imgSelect.setBackgroundResource(R.mipmap.icon_unselect_gray);
            isSelect = false;
        } else {
            imgSelect.setBackgroundResource(R.mipmap.icon_select_blue);
            isSelect = true;
        }
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if (!isSelect) {
            ToastUtils.shortToast(MerchantAgreementActivity.this,R.string.merchant_agreement_notice);
            return;
        }
        if (type == 0) {
            MerchantApplyActivity.actionStart(this);
            finish();
        } else {
            //調用退出接口
            presenter.cancleMerchant();
        }
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, int type) {
        Intent intent = new Intent(activity, MerchantAgreementActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_merchant_agreement;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new MerchantPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            txtConfirm.setText(R.string.merchant_apply_imme);
            txtNotice8.setVisibility(View.VISIBLE);
            txtNotice1.setText(R.string.merchant_agreement_notice_1);
            txtNotice2.setText(R.string.merchant_agreement_notice_2);
            txtNotice3.setText(R.string.merchant_agreement_notice_3);
            txtNotice4.setText(R.string.merchant_agreement_notice_4);
            txtNotice5.setText(R.string.merchant_agreement_notice_5);
            txtNotice6.setText(R.string.merchant_agreement_notice_6);
            txtNotice7.setText(R.string.merchant_agreement_notice_7);
            txtNotice9.setText(R.string.merchant_agreement_notice_9);
        } else {
            txtConfirm.setText(R.string.sign_out);
            txtNotice8.setVisibility(View.GONE);
            txtNotice1.setText(R.string.merchant_agreement_notice_s_1);
            txtNotice2.setText(R.string.merchant_agreement_notice_s_2);
            txtNotice3.setText(R.string.merchant_agreement_notice_s_3);
            txtNotice4.setText(R.string.merchant_agreement_notice_s_4);
            txtNotice5.setText(R.string.merchant_agreement_notice_s_5);
            txtNotice6.setText(R.string.merchant_agreement_notice_s_6);
            txtNotice7.setText(R.string.merchant_agreement_notice_s_7);
            txtNotice9.setText(R.string.merchant_agreement_notice_s_9);
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
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void cancleSuccess(String toastMessage) {
        NormalDialog dialog = NormalDialog.newInstance(toastMessage, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        finish();
    }

    @Override
    public void applySuccess(String toastMessage) {

    }

    @Override
    public void statusSuccess(MerchantStatusDao merchantStatusDao) {

    }

    @Override
    public void approveSuccess(List<BusinessAuthDao> daos) {

    }

    @Override
    public void setPresenter(MerchantContract.MerchantPresenter presenter) {
        this.presenter = presenter;
    }
}
