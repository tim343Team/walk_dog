package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.utils.CharUtil;
import tim.com.libnetwork.utils.WonderfulStringUtils;

public class SellPropDialog extends BaseDialogFragment {
    @BindView(R.id.tv_price)
    EditText editText;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private int titleId;

    @OnClick(R.id.txt_cancle)
    void cancle() {
        dismiss();
    }

    @OnClick(R.id.txt_confirm)
    void add() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.enter_sell_price, Toast.LENGTH_SHORT).show();
            return;
        }
        callback.callback(editText.getText().toString());
    }

    public static SellPropDialog newInstance(int titleId) {
        SellPropDialog fragment = new SellPropDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("titleId", titleId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sell_prop;
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
        titleId = bundle.getInt("titleId");
        txtTitle.setText(titleId);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    return;
                }
                if (!checkoutString(editable.toString())) {
                    editText.setText(editable.toString().substring(0,editable.toString().length()-1));
                }
            }
        });
    }

    public boolean checkoutString(String text) {
        Pattern EMAIL = Pattern.compile("^\\d+(\\.\\d{0,4})?$");
        boolean flag = false;
        try {
            Matcher matcher = EMAIL.matcher(text);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
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
        void callback(String price);
    }
}
