package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class FeedingDialog extends BaseDialogFragment {
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
    @BindView(R.id.txt_consumption)
    TextView txtConsumption;
    @BindView(R.id.txt_surplus)
    TextView txtSurplus;
//    @BindView(R.id.txt_add_weight)
//    TextView txtAddWeight;

    @OnClick(R.id.txt_feeding)
    void back(){
        feedCallback.callback();
    }

    public static FeedingDialog newInstance() {
        FeedingDialog fragment = new FeedingDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_feeding;
    }

    @Override
    protected void prepareView(View view) {

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

    private OperateFeedCallback feedCallback;

    public void setFeedCallback(OperateFeedCallback feedCallback) {
        this.feedCallback = feedCallback;
    }

    public interface OperateFeedCallback {
        void callback();
    }
}
