package com.wallet.walkthedog.view.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;
import com.uuzuche.lib_zxing.encoding.EncodingHandler;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.dao.WalletsItem;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import java.util.List;

import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.ScreenUtils;
import tim.com.libnetwork.utils.WonderfulBitmapUtils;
import tim.com.libnetwork.utils.WonderfulCommonUtils;

public class CollectionActivity extends BaseActivity {
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_collection;
    }

    TextView tv_address;
    View iv_copy;
    Bitmap qrCode;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ImageView qr = findViewById(R.id.iv_qr);
        tv_address = findViewById(R.id.tv_address);
        iv_copy = findViewById(R.id.iv_copy);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao userinfo) {
                List<WalletsItem> wallets = userinfo.getWallets();

                for (int i = 0; i < wallets.size(); i++) {
                    WalletsItem item = wallets.get(i);
                    if (item.getType() == 1) {
                        String asset = item.getAddress();
                        try {
                            qrCode = EncodingHandler.createQRCode(asset, ScreenUtils.dip2px(CollectionActivity.this, 350f));
                            qr.setImageBitmap(qrCode);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        tv_address.setText(asset);
                        iv_copy.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
        iv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WonderfulCommonUtils.copyText(CollectionActivity.this, tv_address.getText().toString());
                ToastUtils.shortToast(getString(R.string.copy_success));
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrCode != null) {
                    Utils.saveImage29(CollectionActivity.this,qrCode,"walkdogqr");
                    ToastUtils.shortToast(getString(R.string.save_success));
                }
            }
        });

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
