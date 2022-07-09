package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class IdentityDialog extends BaseDialogFragment {
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_level)
    TextView txtLevel;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_muscle)
    TextView txtMucle;
    @BindView(R.id.txt_perspmality)
    TextView txtPerspmality;
    @BindView(R.id.txt_id)
    TextView txtId;


    @OnClick(R.id.txt_cancle)
    void back(){
        dismiss();
    }

    public static IdentityDialog newInstance() {
        IdentityDialog fragment = new IdentityDialog();
        Bundle bundle = new Bundle();
//        bundle.putString("message",message);
//        bundle.putString("type","0");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_identity;
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
//        String message = bundle.getString("message");
//        String type = bundle.getString("type","0");
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }
}
