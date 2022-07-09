package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class InvitedStopDialog extends BaseDialogFragment {
    @BindView(R.id.view_root)
    LinearLayout rootView;

    @OnClick(R.id.txt_cancle)
    void back(){
        dismiss();
    }

    public static InvitedStopDialog newInstance() {
        InvitedStopDialog fragment = new InvitedStopDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_invited_stop;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        addView();
        addView();
        addView();
        addView();
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private void addView(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_prop, null);
        rootView.addView(view);
    }
}
