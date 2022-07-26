package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogFoodDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class BuyFoodDialog extends BaseDialogFragment {
    @BindView(R.id.editText_amount)
    TextView editAmount;
    @BindView(R.id.txt_surplus)
    TextView txtSurplus;
    @BindView(R.id.txt_consumption)
    TextView txtConsumption;
    @BindView(R.id.txt_gender)
    TextView txtWeight;
    @BindView(R.id.txt_level)
    TextView txtPrice;
    @BindView(R.id.txt_balance)
    TextView txtBalance;

    private DogFoodDao data;
    private int amount = 0;
    private double price = 0;
    private String balance;

    @OnClick(R.id.img_adding)
    void addAmount() {
        if (amount > 99) {
            return;
        }
        amount = amount + 1;
        editAmount.setText(String.valueOf(amount));
        updatePrice(price * amount);
    }

    @OnClick(R.id.img_subtract)
    void subAmount() {
        if (amount < 1) {
            return;
        }
        amount = amount - 1;
        editAmount.setText(String.valueOf(amount));
        updatePrice(price * amount);
    }

    @OnClick(R.id.txt_buy)
    void buyFood() {
        callback.callback();
    }

    public static BuyFoodDialog newInstance(String property, DogFoodDao data) {
        BuyFoodDialog fragment = new BuyFoodDialog();
        Bundle bundle = new Bundle();
        bundle.putString("balance",property);
        bundle.putSerializable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_buy_feed;
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
        assert bundle != null;
        balance = bundle.getString("balance");
        data = (DogFoodDao) bundle.getSerializable("data");
        editAmount.setText(String.valueOf(amount));
        txtBalance.setText(getResources().getString(R.string.balance_)+balance);
        txtWeight.setText(String.valueOf(data.getWeight()));
        txtPrice.setText(String.valueOf(data.getPrice()));
        price=data.getPrice();
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    void updatePrice(double totalPrice) {
        txtSurplus.setText(String.valueOf(totalPrice));
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback();
    }
}
