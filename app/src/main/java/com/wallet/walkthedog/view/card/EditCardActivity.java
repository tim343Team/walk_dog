package com.wallet.walkthedog.view.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.dao.CardInfoDao;
import com.wallet.walkthedog.dao.request.CardEditRequset;
import com.wallet.walkthedog.dialog.AddFriendDialog;
import com.wallet.walkthedog.dialog.DeleteCardDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class EditCardActivity extends BaseActivity implements CardContract.CardView{
    @BindView(R.id.txt_title)
    TextView textTitle;
    @BindView(R.id.txt_number)
    TextView textNumber;
    @BindView(R.id.txt_enter_number)
    TextView textEnterNumber;
    @BindView(R.id.ll_bank_name)
    View llBankName;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.edit_enter_number)
    EditText editEnterNumber;
    @BindView(R.id.edit_bank_name)
    EditText editBankName;

    private int type;//1:銀行卡  2：swift
    private CardContract.CardPresenter presenter;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if (type == 1) {
            //卡號
            if(editName.getText().toString().isEmpty()){
                Toast.makeText(EditCardActivity.this,R.string.ral_name_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            if(editNumber.getText().toString().isEmpty()){
                Toast.makeText(EditCardActivity.this,R.string.card_numbere_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            if(editBankName.getText().toString().isEmpty()){
                Toast.makeText(EditCardActivity.this,R.string.bank_name_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            if(!editNumber.getText().toString().equals(editEnterNumber.getText().toString())){
                Toast.makeText(EditCardActivity.this,R.string.enter_card_number_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            //密碼框窗口
            PasswordDialog passwordDialog = PasswordDialog.newInstance();
            passwordDialog.setTheme(R.style.PaddingScreen);
            passwordDialog.setGravity(Gravity.CENTER);
            passwordDialog.show(getSupportFragmentManager(), "edit");
            passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                @Override
                public void callback(String password) {
                    //修改银行卡
                    CardEditRequset requset=new CardEditRequset();
                    requset.setRealName(editName.getText().toString());
                    requset.setCardNo(editEnterNumber.getText().toString());
                    requset.setBank(editBankName.getText().toString());
                    requset.setJyPassword(password);
                    presenter.getApproveBank(requset);
                    passwordDialog.dismiss();
                }
            });
            passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                @Override
                public void callback() {
                }
            });
        }else if (type == 2) {
            //swift
            if(editName.getText().toString().isEmpty()){
                Toast.makeText(EditCardActivity.this,R.string.ral_name_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            if(editNumber.getText().toString().isEmpty()){
                Toast.makeText(EditCardActivity.this,R.string.swift_number_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            if(!editNumber.getText().toString().equals(editEnterNumber.getText().toString())){
                Toast.makeText(EditCardActivity.this,R.string.enter_swift_number_notice,Toast.LENGTH_SHORT).show();
                return;
            }
            //密碼框窗口
            PasswordDialog passwordDialog = PasswordDialog.newInstance();
            passwordDialog.setTheme(R.style.PaddingScreen);
            passwordDialog.setGravity(Gravity.CENTER);
            passwordDialog.show(getSupportFragmentManager(), "edit");
            passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                @Override
                public void callback(String password) {
                    //修改swift
                    CardEditRequset requset=new CardEditRequset();
                    requset.setSwiftRealName(editName.getText().toString());
                    requset.setSwiftAddress(editEnterNumber.getText().toString());
                    requset.setPassword(password);
                    presenter.getApproveSwift(requset);
                    passwordDialog.dismiss();
                }
            });
            passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                @Override
                public void callback() {
                }
            });
        }
    }

    public static void actionStart(Activity activity, int type) {
        Intent intent = new Intent(activity, EditCardActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_add_card;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new CardPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            //卡號
            textTitle.setText(R.string.edit_my_card);
            llBankName.setVisibility(View.VISIBLE);
            textNumber.setText(R.string.card_number);
            textEnterNumber.setText(R.string.enter_card_number);
        } else if (type == 2){
            //swift
            textTitle.setText(R.string.swift);
            llBankName.setVisibility(View.GONE);
            textNumber.setText(R.string.swift_number);
            textEnterNumber.setText(R.string.enter_swift_number);
        }
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

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void getBankAccountSuccess(List<CardInfoDao> cardInfoDaos) {

    }

    @Override
    public void getEditSuccess(String message) {
        finish();
    }

    @Override
    public void setPresenter(CardContract.CardPresenter presenter) {
        this.presenter=presenter;
    }
}
