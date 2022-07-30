package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.data.Constant;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class OpenindDialog extends BaseDialogFragment {
    @BindView(R.id.img_prop)
    ImageView imgProp;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_name)
    TextView txtName;

    private String type;
    private String name;
    private BoxDao boxDao;

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    @OnClick(R.id.txt_next)
    void confirm() {
        callback.callback(name);
    }

    public static OpenindDialog newInstance(String type) {
        OpenindDialog fragment = new OpenindDialog();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static OpenindDialog newInstance(String type, BoxDao dao) {
        OpenindDialog fragment = new OpenindDialog();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("BoxDao", dao);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_opening;
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
        type = bundle.getString("type");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        if (type.equals(Constant.PROP_FOOD)) {
            Glide.with(this).load(R.mipmap.icon_dog_food).apply(options).into(imgProp);
            txtName.setText(String.format(getString(R.string.dog_food_s), "*1"));
            name = getResources().getString(R.string.dog_food);
        } else if (type.equals(Constant.PROP_BOX)) {

        } else {
            boxDao = (BoxDao) bundle.getSerializable("BoxDao");
            if (boxDao == null) {
                return;
            }
            Glide.with(this).load(boxDao.getPropImg()).apply(options).into(imgProp);
            txtName.setText(boxDao.getPropName() + "*" + boxDao.getNumber());
            name = boxDao.getPropName();
        }
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(String name);
    }
}
