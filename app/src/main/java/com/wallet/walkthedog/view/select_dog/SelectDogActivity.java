package com.wallet.walkthedog.view.select_dog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.adapter.RentDogsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.db.UserDao;
import com.wallet.walkthedog.db.dao.UserCache;
import com.wallet.walkthedog.dialog.BuyFoodDialog;
import com.wallet.walkthedog.dialog.FeedingDialog;
import com.wallet.walkthedog.dialog.FeedofRemindDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class SelectDogActivity extends BaseActivity implements SelectContract.SelectView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerview_rent)
    RecyclerView recyclerViewRent;
    @BindView(R.id.txt_rent_dog)
    TextView txtRentDog;
    @BindView(R.id.txt_my_dog)
    TextView txtMyDog;

    private SelectContract.SelectPresenter presenter;
    private int status = 0;//0:我的狗狗 1:租赁的狗狗
    private String totalFood = "0";
    private String totalProperty = "0";

    @OnClick(R.id.txt_my_dog)
    void choiceMyDog() {

    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick({R.id.txt_rent_dog, R.id.txt_my_dog})
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
        presenter = new SelectPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
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
        updateData();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyDogsAdapter(R.layout.adapter_my_dog, data, SharedPrefsHelper.getInstance().getDogId());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.setCallback(new MyDogsAdapter.OperateCallback() {
            @Override
            public void callback(DogInfoDao dao, int selectPosition, int oldSelectPosition) {
                adapter.notifyItemChanged(selectPosition);
                adapter.notifyItemChanged(oldSelectPosition);
                if(selectPosition==oldSelectPosition){
                    //取消选择
                    presenter.removeDog(dao.getId());
                }else {
                    //选择使用
                    presenter.useDog(dao.getId());
                }
            }
        });
        adapter.setFeedCallback(new MyDogsAdapter.FeedCallback() {
            @Override
            public void callback(DogInfoDao dao) {
                //feed
                showFeeding(dao);
            }
        });
        adapter.setItemCallback(new MyDogsAdapter.ItemCallback() {
            @Override
            public void callback(DogInfoDao dao) {

            }
        });
    }

    private void initRecyclerViewRent() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewRent.setLayoutManager(manager);
        adapterRent = new RentDogsAdapter(R.layout.adapter_rent_dog, dataRent);
        adapterRent.bindToRecyclerView(recyclerViewRent);
        adapterRent.setEnableLoadMore(false);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getMyDogSuccess(List<DogInfoDao> dogInfoDaos) {
        data.clear();
        if (dogInfoDaos.size() > 0) {
            data.addAll(dogInfoDaos);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void useDogSuccess(String data, String currentDogId) {
        //选择成功弹窗
        SharedPrefsHelper.getInstance().saveDogId(currentDogId);
        NormalDialog dialog = NormalDialog.newInstance(R.string.choose_select, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //更新主页
        EventBus.getDefault().post(new UpdateHomeData());
    }

    @Override
    public void removeDogSuccess(String data, String dogId) {
        //选择成功弹窗
        SharedPrefsHelper.getInstance().saveDogId("");
        NormalDialog dialog = NormalDialog.newInstance(R.string.deselect, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //更新主页
        EventBus.getDefault().post(new UpdateHomeData());
    }

    @Override
    public void getFeedDogInfo(FeedDogFoodDao data,DogInfoDao mDefultDogInfo) {
        //喂食弹唱
        FeedingDialog dialog = FeedingDialog.newInstance(mDefultDogInfo, data, totalFood);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setFeedCallback(new FeedingDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                //調用餵食接口
                presenter.feedDog(mDefultDogInfo.getId());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void setPresenter(SelectContract.SelectPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void feedSuccessful(String data) {
        //喂食成功,更新数据
        NormalDialog dialog = NormalDialog.newInstance(R.string.feeding_su, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //刷新列表
        updateData();
    }

    @Override
    public void feedFail(Integer code, String toastMessage) {
        //喂食失败
        presenter.getShopDogFood();
    }

    @Override
    public void getShopDogFoodSuccessful(DogFoodDao data) {
        //新增是否购买页面
//        FeedofRemindDialog dialog = FeedofRemindDialog.newInstance();
//        dialog.setTheme(R.style.PaddingScreen);
//        dialog.setGravity(Gravity.BOTTOM);
//        dialog.show(getSupportFragmentManager(), "edit");
//        dialog.setCallback(new FeedofRemindDialog.OperateCallback() {
//
//            @Override
//            public void callback() {
//                BuyFoodDialog buyDialog = BuyFoodDialog.newInstance(totalProperty, data);
//                buyDialog.setTheme(R.style.PaddingScreen);
//                buyDialog.setGravity(Gravity.CENTER);
//                buyDialog.show(getSupportFragmentManager(), "edit");
//                buyDialog.setCallback(new BuyFoodDialog.OperateCallback() {
//                    @Override
//                    public void callback(int number) {
//                        PasswordDialog passwordDialog = PasswordDialog.newInstance();
//                        passwordDialog.setTheme(R.style.PaddingScreen);
//                        passwordDialog.setGravity(Gravity.CENTER);
//                        passwordDialog.show(getSupportFragmentManager(), "edit");
//                        passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
//                            @Override
//                            public void callback(String password) {
//                                //購買狗糧
//                                presenter.buyShopDogFood(data.getId(), number,password);
//                                passwordDialog.dismiss();
//                                buyDialog.dismiss();
//                            }
//                        });
//                        passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
//                            @Override
//                            public void callback() {
//                                ToastUtils.shortToast("错误");
//                            }
//                        });
//                    }
//                });
//                dialog.dismiss();
//            }
//        });
        BuyFoodDialog buyDialog = BuyFoodDialog.newInstance(totalProperty, data);
        buyDialog.setTheme(R.style.PaddingScreen);
        buyDialog.setGravity(Gravity.CENTER);
        buyDialog.show(getSupportFragmentManager(), "edit");
        buyDialog.setCallback(new BuyFoodDialog.OperateCallback() {
            @Override
            public void callback(int number) {
                PasswordDialog passwordDialog = PasswordDialog.newInstance();
                passwordDialog.setTheme(R.style.PaddingScreen);
                passwordDialog.setGravity(Gravity.CENTER);
                passwordDialog.show(getSupportFragmentManager(), "edit");
                passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                    @Override
                    public void callback(String password) {
                        //購買狗糧
                        presenter.buyShopDogFood(data.getId(), number,password);
                        passwordDialog.dismiss();
                        buyDialog.dismiss();
                    }
                });
                passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                    @Override
                    public void callback() {
                        ToastUtils.shortToast("错误");
                    }
                });
            }
        });
    }

    @Override
    public void buyShopDogFoodSuccessful(String data) {
        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //更新
        updateData();
    }

    @Override
    public void getWalletInfo(String data, String type) {
        if (type.equals("1")) {
            totalProperty = data;
        } else if (type.equals("2")) {
            totalFood = data;
        }
    }

    private void showFeeding(DogInfoDao mDefultDogInfo) {
        presenter.getFeedDog(mDefultDogInfo.getId(),mDefultDogInfo);
    }

    private void updateData(){
        presenter.getWallet("1");//获取代币总数
        presenter.getWallet("2");//获取狗粮总数
        presenter.getUserDog(1,1);
    }
}
