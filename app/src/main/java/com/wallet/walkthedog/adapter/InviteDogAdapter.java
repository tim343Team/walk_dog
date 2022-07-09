package com.wallet.walkthedog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dialog.BuyFoodDialog;

import java.util.List;

public class InviteDogAdapter extends BaseQuickAdapter<DogInfoDao, BaseViewHolder> {

    public InviteDogAdapter(int layoutResId, @Nullable List<DogInfoDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DogInfoDao item) {
        helper.getView(R.id.txt_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callback(item);
            }
        });
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(DogInfoDao dao);
    }
}
