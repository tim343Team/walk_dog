package com.wallet.walkthedog.view.select_dog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.adapter.RentDogsAdapter;
import com.wallet.walkthedog.dao.DogInfoDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class SelectDogActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerview_rent)
    RecyclerView recyclerViewRent;
    @BindView(R.id.txt_rent_dog)
    TextView txtRentDog;
    @BindView(R.id.txt_my_dog)
    TextView txtMyDog;

    private int status = 0;//0:我的狗狗 1:租赁的狗狗

    @OnClick(R.id.txt_my_dog)
    void choiceMyDog() {

    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick({R.id.txt_rent_dog,R.id.txt_my_dog})
    void choiceRentDog(View v) {
        switch (v.getId()) {
            case R.id.txt_rent_dog:
                status = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerViewRent.setVisibility(View.VISIBLE);
                txtRentDog.setTextColor(getResources().getColor(R.color.color_4D67C1));
                txtMyDog.setTextColor(getResources().getColor(R.color.color_ADAEB3));
                break;
            case R.id.txt_my_dog:
                status = 0;
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewRent.setVisibility(View.GONE);
                txtRentDog.setTextColor(getResources().getColor(R.color.color_ADAEB3));
                txtMyDog.setTextColor(getResources().getColor(R.color.color_4D67C1));
                break;
        }
    }

    private MyDogsAdapter adapter;
    private RentDogsAdapter adapterRent;
    private List<DogInfoDao> data = new ArrayList<>();
    private List<DogInfoDao> dataRent = new ArrayList<>();

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SelectDogActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_select_dog;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        initRecyclerViewRent();
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
            DogInfoDao dogInfoDao = new DogInfoDao();
            data.add(dogInfoDao);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyDogsAdapter(R.layout.adapter_my_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEnableLoadMore(false);
    }

    private void initRecyclerViewRent() {
        //TODO 測試數據
        for (int i = 0; i < 10; i++) {
            DogInfoDao dogInfoDao = new DogInfoDao();
            dataRent.add(dogInfoDao);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewRent.setLayoutManager(manager);
        adapterRent = new RentDogsAdapter(R.layout.adapter_rent_dog, dataRent);
        adapterRent.bindToRecyclerView(recyclerViewRent);
        adapterRent.setEnableLoadMore(false);
    }
}
