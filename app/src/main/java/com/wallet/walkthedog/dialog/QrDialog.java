package com.wallet.walkthedog.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.WriterException;
import com.uuzuche.lib_zxing.encoding.EncodingHandler;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.mine.CollectionActivity;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.utils.ScreenUtils;
import tim.com.libnetwork.utils.WonderfulCommonUtils;

public class QrDialog extends BaseDialogFragment {

    @BindView(R.id.iv_qr)
    ImageView iv;
    private Bitmap qrCode;

    public static QrDialog newInstance(String url) {
        QrDialog fragment = new QrDialog();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_qr;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        setGravity(Gravity.CENTER);
        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao userinfo) {
                String email = userinfo.getEmail();
                try {
                    qrCode = EncodingHandler.createQRCode(email, ScreenUtils.dip2px(requireContext(), 350f));
                    iv.setImageBitmap(qrCode);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrCode != null) {
                    Utils.saveImage29(requireContext(),qrCode,"WalkPersionEmail");
                }
            }
        });
        rootView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
