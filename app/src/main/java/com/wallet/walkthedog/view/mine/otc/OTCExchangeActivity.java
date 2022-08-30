package com.wallet.walkthedog.view.mine.otc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.ActivityLifecycleManager;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.OtherAssetDao;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.dao.WalletsItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.mine.ad.MinMaxInputFilter;
import com.wallet.walkthedog.view.mine.ad.PlaceADActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

/**
 * OTC划转
 */
public class OTCExchangeActivity extends BaseActivity {
    private LinearLayout layout_0, layout_1;
    private TextView tv_1_hint, tv_2_hint;
    private boolean fromfund = true;
    private OtherAssetDao otherAssetDao;
    private EditText edit_1, edit_2;
    private int exchangeH = 0;
    private boolean isAnimation = false;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_otc_account;
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void initViews(Bundle savedInstanceState) {
        otherAssetDao = (OtherAssetDao) getIntent().getSerializableExtra("otherAssetDao");
        layout_0 = findViewById(R.id.layout_0);
        edit_1 = findViewById(R.id.edit_1);
        edit_2 = findViewById(R.id.edit_2);
        layout_1 = findViewById(R.id.layout_1);
        tv_1_hint = findViewById(R.id.tv_1_hint);
        tv_2_hint = findViewById(R.id.tv_2_hint);
        TextView tv_money = findViewById(R.id.tv_money);
        TextView tv_otc_money = findViewById(R.id.tv_otc_money);
        findViewById(R.id.tv_confim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao dao) {
                List<WalletsItem> wallets = dao.getWallets();
                for (int i = 0; i < wallets.size(); i++) {
                    WalletsItem item = wallets.get(i);
                    if (item.getType() == 1) {
                        String asset = Utils.getFormat("%.2f", item.getDogFood());
                        tv_money.setText(getString(R.string.balance_) + asset);
                        MinMaxInputFilter.minMaxLimit(edit_1, 0, item.getDogFood());
                    }
                }
            }
        });
        tv_otc_money.setText(getString(R.string.balance_) + otherAssetDao.getBalance());
        try {
            double max = otherAssetDao.getBalance();
            MinMaxInputFilter.minMaxLimit(edit_2, 0, max);
        } catch (Exception ignored) {

        }
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChangeAnim();
            }
        });
    }

    private void submit() {
        double v1 = Utils.toDouble(edit_1.getText().toString());
        double v2 = Utils.toDouble(edit_2.getText().toString());
        if (fromfund && v1 <= 0) {
            ToastUtils.shortToast(getString(R.string.please_input_funding));
            return;
        }
        if (!fromfund && v2 <= 0) {
            ToastUtils.shortToast(getString(R.string.please_input_otc));
            return;
        }
        double amount = 0.0;
        if (fromfund) {
            amount = v1;
        }
        int direction = 2;
        if (!fromfund) {
            direction = 1;
        }
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("coinName", otherAssetDao.getCoin().getName());
        hashmap.put("amount", String.valueOf(amount));
        hashmap.put("direction", String.valueOf(direction));
        String s = Utils.toGetUri(hashmap);

        WonderfulOkhttpUtils.get().url(UrlFactory.otcExchange() + "?" + s)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("coinName", otherAssetDao.getCoin().getName())
                .addParams("amount", String.valueOf(amount))
                .addParams("direction", String.valueOf(direction))
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> data) {
                        ToastUtils.shortToast(getString(R.string.exchange_success));
                        finish();
                        ActivityLifecycleManager.get().finishs(MyOTCAssetActivity.class, MyOtherAssetActivity.class);
                    }
                });
    }


    private void startChangeAnim() {
        if (isAnimation) {
            return;
        }
        if (exchangeH == 0) {
            int top = layout_0.getTop();
            int top1 = layout_1.getTop();
            exchangeH = top1 - top;
        }
        int exchange = exchangeH;
        if (!fromfund) {
            exchange = -exchangeH;
        }
        ViewCompat.animate(layout_0).translationYBy(exchange)
                .setDuration(200)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isAnimation = false;
                        fromfund = !fromfund;
                        if (fromfund) {
                            edit_2.setText("");
                        } else {
                            edit_1.setText("");
                        }
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                });
        ViewCompat.animate(layout_1).translationYBy(-exchange)
                .setDuration(200)
                .start();
        isAnimation = true;
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
}
