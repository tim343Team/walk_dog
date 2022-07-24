package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.email.LoginTypeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class AddFriendDialog extends BaseDialogFragment {
    @BindView(R.id.tv_email)
    EditText editText;

    @OnClick(R.id.txt_cancle)
    void cancle(){
        dismiss();
    }

    @OnClick(R.id.txt_confirm)
    void add(){
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),R.string.mailbox_address,Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkoutEmail(editText.getText().toString())){
            Toast.makeText(getActivity(),R.string.mailbox_address_notice,Toast.LENGTH_SHORT).show();
            return;
        }
        callback.callback(editText.getText().toString());
    }

    public static AddFriendDialog newInstance() {
        AddFriendDialog fragment = new AddFriendDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_friend;
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

    public boolean checkoutEmail(String email) {
        boolean flag=false;
        try {
            String emailMatcher="[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
            Pattern regex= Pattern.compile(emailMatcher);
            Matcher matcher=regex.matcher(email);
            flag=matcher.matches();
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(String email);
    }
}
