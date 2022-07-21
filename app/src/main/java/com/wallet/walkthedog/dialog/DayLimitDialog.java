package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class DayLimitDialog extends BaseDialogFragment {
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.text_message_body)
    TextView txtMessage;

    @OnClick(R.id.txt_search)
    void back(){
        dismiss();
    }

    public static DayLimitDialog newInstance(String title,String message) {
        DayLimitDialog fragment = new DayLimitDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("message",message);
        bundle.putString("type","0");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_day_limit;
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
        String message = bundle.getString("message");
        String title = bundle.getString("title");
        String type = bundle.getString("type","0");
        txtTitle.setText(title);
        txtMessage.setText(message);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }
}
