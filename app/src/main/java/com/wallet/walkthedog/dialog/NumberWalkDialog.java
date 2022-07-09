package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;

import com.wallet.walkthedog.R;

import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class NumberWalkDialog extends BaseDialogFragment {

    @OnClick(R.id.txt_back)
    void back(){
        dismiss();
    }

    public static NumberWalkDialog newInstance() {
        NumberWalkDialog fragment = new NumberWalkDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_number_walk;
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
}
