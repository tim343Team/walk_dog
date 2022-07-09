package com.wallet.walkthedog.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.CreateeMnemoricAdapter;
import com.wallet.walkthedog.dao.MnemonicDao;
import com.wallet.walkthedog.dialog.VerifyDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class CreateActivity extends BaseActivity {
    public static CreateActivity instance = null;
    private boolean isBind=false;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @OnClick({R.id.txt_copy,R.id.txt_skip})
    void verify(){
        VerifyDialog verifyDialog = VerifyDialog.newInstance();
        verifyDialog.setTheme(R.style.PaddingScreen);
        verifyDialog.setGravity(Gravity.CENTER);
        verifyDialog.show(getSupportFragmentManager(), "edit");
        verifyDialog.setCallback(new VerifyDialog.OperateCallback() {
            @Override
            public void callback() {
                VerificationActivity.actionStart(CreateActivity.this,data,isBind);
                verifyDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    private CreateeMnemoricAdapter adapter;
    private List<MnemonicDao> data = new ArrayList<>();

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CreateActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void befSuperonCreate() {
        super.befSuperonCreate();
        //禁止截屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_create;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        instance = this;
        initRecyclerView();
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

    private void initRecyclerView() {
        //TODO 测试数据
        for (int i = 0; i < 12; i++) {
            MnemonicDao mnemonicDao = new MnemonicDao();
            mnemonicDao.setWords("swords"+i);
            data.add(mnemonicDao);
        }
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(CreateActivity.this, 3));
        adapter = new CreateeMnemoricAdapter(CreateActivity.this, data, 1);
        recyclerview.setAdapter(adapter);
    }
}
