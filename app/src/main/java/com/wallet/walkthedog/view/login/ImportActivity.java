package com.wallet.walkthedog.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dialog.NoticeDialog;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class ImportActivity extends BaseActivity {
    public static ImportActivity instance = null;
    @BindView(R.id.edit)
    EditText editText;
    @BindView(R.id.txt_log)
    TextView txtLog;
    @BindView(R.id.img_ok)
    ImageView imgOk;

    @OnClick(R.id.ll_edit)
    void clickEdit() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick({R.id.txt_create,R.id.img_ok})
    void confirmNmnemonic() {
        showLog(-1);
        StringBuilder confirm = new StringBuilder();
        String mnemonics = editText.getText().toString();
        String[] mnemonicArray = mnemonics.split(" ");
        if (mnemonicArray.length < 12) {
            showLog(1);
            showErrorDialog();
        }
        for (String confirDao : mnemonicArray) {
            confirm.append(confirDao).append(",");
        }
        SettingPassWordActivity.actionStart(ImportActivity.this,"1");
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ImportActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_import;
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

    private void showLog(int type) {
        if (type == 0) {
            txtLog.setTextColor(ContextCompat.getColor(this, R.color.color_848484));
            txtLog.setText(getResources().getString(R.string.wait_import));
            txtLog.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            txtLog.setTextColor(ContextCompat.getColor(this, R.color.color_C93148));
            txtLog.setText(getResources().getString(R.string.error_import));
            txtLog.setVisibility(View.VISIBLE);
        } else {
            txtLog.setVisibility(View.GONE);
        }
    }

    private void showErrorDialog() {
        NoticeDialog dialog = NoticeDialog.newInstance(getResources().getString(R.string.dialog_error_import), "1");
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new NoticeDialog.OperateCallback() {
            @Override
            public void callback() {
                dialog.dismiss();
            }
        });
    }
}
