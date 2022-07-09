package com.wallet.walkthedog.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.untils.PopupUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    public static LoginActivity instance = null;
    @BindView(R.id.txt_language)
    TextView txtLanguage;
    @BindView(R.id.view_select_language)
    View viewLanguage;

    @OnClick(R.id.view_select_language)
    void selectLanguage() {
        List<String> data=new ArrayList<>();
        data.add(getResources().getString(R.string.english));
        data.add(getResources().getString(R.string.japanese));
        data.add(getResources().getString(R.string.chinese));
        PopupUtils.showListBottomRight(this, data, viewLanguage, viewLanguage.getMeasuredWidth(), new PopupUtils.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                txtLanguage.setText(data.get(position));
            }
        });
    }

    @OnClick(R.id.txt_create)
    void gotoCreateImportView() {
        CreateActivity.actionStart(this);
    }

    @OnClick(R.id.txt_import)
    void gotoImportView() {
        ImportActivity.actionStart(this);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
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
