package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class SoldQuantityDialog extends BaseDialogFragment {


    public DialogConfimCall call;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sold_quantity;
    }


    public static SoldQuantityDialog newInstance(double dogFood) {
        Bundle args = new Bundle();
        args.putDouble("dogFood", dogFood);
        SoldQuantityDialog fragment = new SoldQuantityDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void prepareView(View view) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            double dogFood = getArguments().getDouble("dogFood");
            view.<TextView>findViewById(R.id.tv_all).setText(getString(R.string.my_all_dog_food_s, dogFood));
        }

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_confim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    showPWDialog();
                }
            }
        });
    }

    private void showPWDialog() {
        PasswordDialog dialog = new PasswordDialog();
        requireView().setVisibility(View.INVISIBLE);
        dialog.setCallback(new PasswordDialog.OperateCallback() {
            @Override
            public void callback(String password) {
                sellDoogFood(10, new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.shortToast(getString(R.string.sell_success));
                        if (call != null) {
                            call.call(SoldQuantityDialog.this);
                        }
                    }
                });
            }
        });
        dialog.setDismissCallback(new PasswordDialog.DismissCallback() {
            @Override
            public void call() {
                requireView().setVisibility(View.VISIBLE);
            }
        });
        dialog.show(getChildFragmentManager(), "");
    }

    private void sellDoogFood(double price, Runnable runnable) {
        WonderfulOkhttpUtils.get().url(UrlFactory.sellDoogFood() + "?catID=2" + "&price=" + price)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Double>>() {
                    @Override
                    protected void onRes(RemoteData<Double> testRemoteData) {
                        runnable.run();
                    }
                });
    }

    private boolean check() {

        return true;
    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    public interface DialogConfimCall {
        void call(DialogFragment dialog);
    }
}
