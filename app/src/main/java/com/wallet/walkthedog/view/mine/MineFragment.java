package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dialog.QrDialog;
import com.wallet.walkthedog.view.home.HomeFragment;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragment;

public class MineFragment extends BaseTransFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.avatar)
    ImageView ivHeader;

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void init() {
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),AvatarActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.iv_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO url地址
                QrDialog qrDialog =  QrDialog.newInstance("xxxxx");
                qrDialog.show(getChildFragmentManager(),"");

            }
        });
        rootView.findViewById(R.id.layout_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),MyAssetActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_walk_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),DogWalkRecordActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_invited_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),InvitedDogActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_train_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //训练记录
                Intent intent = new Intent(requireContext(),TrainRecordActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),LanguageSetActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_security).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),SecuritySetActivity.class);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.ll_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

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
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }
}
