package com.wallet.walkthedog.view.props;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.NormalDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class ChoicePropsActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int count;//可以選擇的數量
    private MyPropsAdapter adapter;
    private List<PropDao> data = new ArrayList<>();

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ChoicePropsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_choice_props;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        count = getIntent().getIntExtra("count",0);
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
        //TODO 測試數據
        for (int i = 0; i < 10; i++) {
            PropDao propDao = new PropDao();
            propDao.setId(i+"0");
            data.add(propDao);
        }
        GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        adapter = new MyPropsAdapter(R.layout.adapter_my_props, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.OnclickListenerItem(new MyPropsAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                if(data.get(position).isSelect()){
                    adapter.removeSelect(data.get(position));
                    data.get(position).setSelect(false);
                    adapter.notifyItemChanged(position);
                }else {
                    if(adapter.getSelectCount()>2){
                        //TODO 提示
                        NormalDialog dialog = NormalDialog.newInstance(R.string.cancle_props, R.mipmap.icon_normal_no,R.color.color_E12828);
                        dialog.setTheme(R.style.PaddingScreen);
                        dialog.setGravity(Gravity.CENTER);
                        dialog.show(getSupportFragmentManager(), "edit");
                        return;
                    }else {
                        adapter.addSelect(data.get(position));
                        data.get(position).setSelect(true);
                        adapter.notifyItemChanged(position);
                        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
                        dialog.setTheme(R.style.PaddingScreen);
                        dialog.setGravity(Gravity.CENTER);
                        dialog.show(getSupportFragmentManager(), "edit");
                    }
                }
            }
        });
    }
}
