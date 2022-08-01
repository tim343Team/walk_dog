package com.wallet.walkthedog.view.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.SystemCodeDao;
import com.wallet.walkthedog.dao.TrainRecordDao;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.dao.WalletsItem;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class TransferActivity extends BaseActivity {
    EditText edit_address;
    EditText edit_q;
    TextView tv_shouxu;
    TextView tv_all;
    TextView tv_confirm;
    TextView tv_all_asset;
    String asset;
    double fee = 0.0;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit_address = findViewById(R.id.edit_address);
        edit_q = findViewById(R.id.edit_q);
        tv_shouxu = findViewById(R.id.tv_shouxu);
        tv_all = findViewById(R.id.tv_all);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_all_asset = findViewById(R.id.tv_all_asset);
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(asset)) {
                    edit_q.setText(asset);
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    String address = edit_address.getText().toString();
                    String p = edit_q.getText().toString();
                    PasswordDialog dialog = new PasswordDialog();
                    dialog.setGravity(Gravity.CENTER);
                    dialog.setCallback(new PasswordDialog.OperateCallback() {
                        @Override
                        public void callback(String password) {
                            tokenAudit(address,p,password);
                            dialog.dismiss();
                        }
                    });
                    dialog.show(getSupportFragmentManager(),"");
                }
            }
        });
        edit_q.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                computeFee();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private boolean check() {
        String address = edit_address.getText().toString();
        if (address.isEmpty()) {
            ToastUtils.shortToast(getString(R.string.please_enter_the_address));
            return false;
        }
        try {
            double p = Double.parseDouble(edit_q.getText().toString());
            if (p <= 0) {
                ToastUtils.shortToast(getString(R.string.please_enter_the_amount));
                return false;
            }
        } catch (Exception ignored) {
        }
        return true;
    }



    private void onSuccess(SystemCodeDao notNullData) {
        try {
            fee = Double.parseDouble(notNullData.getValue());
            computeFee();
        } catch (Exception ignored) {

        }

    }

    private void computeFee() {
        try {
            double q = Double.parseDouble(edit_q.getText().toString());
            tv_shouxu.setText(getString(R.string.fee, Utils.getFormat("%.2f", q * fee)));
        } catch (Exception e) {
            tv_shouxu.setText("");
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
        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void call(UserInfoDao userinfo) {
                List<WalletsItem> wallets = userinfo.getWallets();
                for (int i = 0; i < wallets.size(); i++) {
                    WalletsItem item = wallets.get(i);
                    if (item.getType() == 1) {
                        asset = Utils.getFormat("%.2f", item.getDogFood());
                        tv_all_asset.setText(getString(R.string.balance_) + asset);
                        break;
                    }
                }
            }
        });
        computeyugu();
    }


    private void computeyugu() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getSysDataCode() + "?code=extract_token_fee")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<SystemCodeDao>>() {
                    @Override
                    protected void onRes(RemoteData<SystemCodeDao> testRemoteData) {
                        SystemCodeDao notNullData = testRemoteData.getNotNullData();
                        onSuccess(notNullData);
                    }
                });
    }

    private void tokenAudit(String address, String p, String password) {
        WonderfulOkhttpUtils.post().url(UrlFactory.tokenAudit())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("toAddress",address)
                .addParams("amount",p)
                .addParams("password",password)
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> testRemoteData) {
                        onSuccessTokenAudit();
                    }
                });
    }

    private void onSuccessTokenAudit() {
        ToastUtils.shortToast(getString(R.string.token_audit_success));
        finish();
    }
}
