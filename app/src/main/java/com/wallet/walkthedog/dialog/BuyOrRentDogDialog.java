package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class BuyOrRentDogDialog extends BaseDialogFragment {
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_accept)
    TextView txtAccept;
    @BindView(R.id.ll_rental_time)
    View llRentalTime;
    private int type = 1;//1:购买 2:租赁

    @OnClick({R.id.back, R.id.txt_refuse})
    void back() {
        dismiss();
    }

    @OnClick(R.id.txt_accept)
    void buyDog() {
        callback.callback();
    }

    public static BuyOrRentDogDialog newInstance(int type) {
        BuyOrRentDogDialog fragment = new BuyOrRentDogDialog();
        Bundle bundle = new Bundle();
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
