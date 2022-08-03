package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowFrameLayout;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseDialogFragment;

public class NormalErrorDialog extends BaseDialogFragment {
    @BindView(R.id.root_view)
    ShadowFrameLayout rootView;
    @BindView(R.id.img_notice)
    ImageView imgNotice;
    @BindView(R.id.txt_notice)
    TextView txtNotice;

    public static NormalErrorDialog newInstance(int resMessageId, int resImgId, int resColorId) {
        NormalErrorDialog fragment = new NormalErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("message", resMessageId);
        bundle.putInt("img", resImgId);
        bundle.putInt("color", resColorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static NormalErrorDialog newInstance(String messageString, int resImgId, int resColorId) {
        NormalErrorDialog fragment = new NormalErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putString("messageString", messageString);
        bundle.putInt("img", resImgId);
        bundle.putInt("color", resColorId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_normal_error;
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
        int message = bundle.getInt("message", 0);
        int img = bundle.getInt("img");
        int color = bundle.getInt("color", 0);
        String messageString = bundle.getString("messageString");
        imgNotice.setBackgroundResource(img);
        if (message != 0) {
            txtNotice.setText(getResources().getString(message));
        }else {
            txtNotice.setText(messageString);
        }
        if (color != 0) {
            txtNotice.setTextColor(ContextCompat.getColor(getContext(), color));
        }
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }
}
