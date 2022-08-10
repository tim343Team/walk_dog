package com.wallet.walkthedog.view.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.custom_view.card.ShadowTextView;
import com.wallet.walkthedog.dialog.AddFriendDialog;
import com.wallet.walkthedog.dialog.DeleteCardDialog;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class EditCardActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView textTitle;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.txt_notice)
    TextView txtNotice;
    @BindView(R.id.tv_confirm)
    ShadowTextView shadowTextView;

    private int type;//0:添加  1：編輯

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if(type==0){
            CardVerifyActivity.actionStart(this);
        }else {

        }
    }

    @OnClick(R.id.img_add)
    void delete() {
        DeleteCardDialog dialog = DeleteCardDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new DeleteCardDialog.OperateCallback() {
            @Override
            public void callback() {
                //TODO 删除接口
//                presenter.sendFriendInvited(email);
                dialog.dismiss();
            }
        });
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
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            textTitle.setText(R.string.add_my_card);
            imgAdd.setVisibility(View.INVISIBLE);
            imgAdd.setEnabled(false);
            txtNotice.setVisibility(View.VISIBLE);
            shadowTextView.setText(R.string.next);
        } else {
            textTitle.setText(R.string.edit_my_card);
            imgAdd.setVisibility(View.VISIBLE);
            imgAdd.setEnabled(true);
            txtNotice.setVisibility(View.GONE);
            shadowTextView.setText(R.string.confirm);
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
}
