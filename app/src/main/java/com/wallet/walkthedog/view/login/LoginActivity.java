package com.wallet.walkthedog.view.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.MainActivity;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.untils.PopupUtils;
import com.wallet.walkthedog.view.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    public static LoginActivity instance = null;
    /*权限请求Code*/
    private final static int PERMISSION_REQUEST_CODE = 1234;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };
    public boolean isShowLock = true;

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

    @OnClick(R.id.group_quick_entrance)
    void gotoHome() {
        HomeActivity.actionStart(this);
        finish();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[2]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[3]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void loadData() {

    }

    //权限反馈
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                /*PackageManager.PERMISSION_GRANTED  权限被许可*/
                /*PackageManager.PERMISSION_DENIED  没有权限；拒绝访问*/
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "无法使用相机！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "无法录制音频！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
