package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.untils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class NicknameDialog extends BaseDialogFragment {
    @BindView(R.id.edit_nickname)
    EditText editText;

    @OnClick({R.id.back, R.id.txt_refuse})
    void back() {
        dismiss();
    }

    @OnClick(R.id.txt_accept)
    void reNickname() {
        String nickname = editText.getText().toString();
        if (nickname.isEmpty()) {
            ToastUtils.shortToast(R.string.note_nickname);
            return;
        }
        callback.callback(nickname);
    }

    public static NicknameDialog newInstance() {
        NicknameDialog fragment = new NicknameDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_nickname;
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

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(String name);
    }
}
