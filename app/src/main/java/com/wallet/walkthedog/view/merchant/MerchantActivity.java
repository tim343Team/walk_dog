package com.wallet.walkthedog.view.merchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.card.CardVerifyActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class MerchantActivity extends BaseActivity implements MerchantContract.MerchantView {
    @BindView(R.id.tv_confirm)
    ShadowTextView txtConfirm;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_error_message)
    TextView txtErrorMessage;
    @BindView(R.id.img_select)
    ImageView imgSelect;
    @BindView(R.id.ll_type_0)
    View view0;
    @BindView(R.id.ll_type_1)
    View view1;
    @BindView(R.id.ll_type_3)
    View view3;


    private boolean isSelect = false;
    private int type;
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
            ToastUtils.shortToast(R.string.merchant_agreement_notice);
            return;
        }
        MerchantAgreementActivity.actionStart(this, type);
    }

    @OnClick(R.id.tv_re_confirm)
    void reConfirm() {
        MerchantAgreementActivity.actionStart(this, 0);
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MerchantActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_merchant;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new MerchantPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        presenter.merchantStatus();

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
    public void cancleSuccess(String toastMessage) {

    }

    @Override
    public void applySuccess(String toastMessage) {

    }

    @Override
    public void statusSuccess(MerchantStatusDao merchantStatusDao) {
        //0未认证   认证1等待审核  2认证审核成功   3认证审核失败  4保证金不足  5退保待审核 6退保审核失败 7退保审核成功
        type = merchantStatusDao.getCertifiedBusinessStatus();
        if (type == 0 || type == 7) {
            type = 0;
            view0.setVisibility(View.VISIBLE);
            txtConfirm.setText(R.string.merchant_apply_imme);
            txtTitle.setText(R.string.merchant_settle);
        } else if (type == 2 || type == 6) {
            type = 2;
            view0.setVisibility(View.VISIBLE);
            txtConfirm.setText(R.string.sign_out);
            txtTitle.setText(R.string.merchant_settle);
            txtTitle.setText(R.string.merchant_settle);
        } else if (type == 1 || type == 5) {
            view1.setVisibility(View.VISIBLE);
        } else if (type == 3 || type == 4) {
            view3.setVisibility(View.VISIBLE);
            //失败信息
            txtErrorMessage.setText(merchantStatusDao.getDetail());
        }
    }

    @Override
    public void setPresenter(MerchantContract.MerchantPresenter presenter) {
        this.presenter = presenter;
    }
}
