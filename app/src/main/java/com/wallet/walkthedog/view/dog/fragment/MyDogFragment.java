package com.wallet.walkthedog.view.dog.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateDogEvent;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.BuyFoodDialog;
import com.wallet.walkthedog.dialog.FeedingDialog;
import com.wallet.walkthedog.dialog.FeedofRemindDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.dog.DogDetailActivity;
import com.wallet.walkthedog.view.dog.MyDogActivity;
import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.props.MyPropsActivity;
import com.wallet.walkthedog.view.props.fragment.MyPropsFragment;
import com.wallet.walkthedog.view.props.fragment.PropsContract;
import com.wallet.walkthedog.view.props.fragment.PropsPresenter;
import com.wallet.walkthedog.view.select_dog.SelectContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseLazyFragment;

public class MyDogFragment extends BaseLazyFragment implements DogContract.DogView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private DogContract.DogPresenter presenter;
    private int pageNo = 1;
    private int type = 1;
    private List<DogInfoDao> data = new ArrayList<>();
    private MyDogsAdapter adapter;
    private String totalFood = "0";
    private String totalProperty = "0";

    public static MyDogFragment getInstance() {
        MyDogFragment fragment = new MyDogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_dog;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new DogPresenter(Injection.provideTasksRepository(getmActivity()), this);//初始化presenter
        initRecyclerView();
        updateData();
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
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(UpdateDogEvent event) {
        adapter.setEnableLoadMore(true);
        adapter.loadMoreEnd(false);
        pageNo = 1;
        presenter.getUserDog(type, pageNo);
        if (adapter != null) {
            adapter.setCurrentDogId(SharedPrefsHelper.getInstance().getDogId());
        }
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
        presenter.getUserDog(type, pageNo);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getmActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyDogsAdapter(R.layout.adapter_my_dog, data, SharedPrefsHelper.getInstance().getDogId());
        adapter.bindToRecyclerView(recyclerView);
        LayoutInflater layoutInflater = getLayoutInflater();//获得layoutInflater对象
        View emptyView = layoutInflater.inflate(R.layout.empty_sell_dog, null);//获得view对象
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if (HomeActivity.instance != null) {
                    HomeActivity.instance.type = 0;
                    HomeActivity.actionStart(getActivity(), 1);
                }
            }
        });
        adapter.setEmptyView(emptyView);
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
                DogDetailActivity.actionStart(getActivity(), dao);
            }
        });

    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @Override
    public void getMyDogSuccess(List<DogInfoDao> obj) {
        adapter.setEnableLoadMore(true);
        if (pageNo == 1) {
            data.clear();
            if (obj.size() == 0) {
                adapter.loadMoreEnd();
            } else {
                this.data.addAll(obj);
            }
        } else {
            if (obj.size() != 0) {
                this.data.addAll(obj);
            } else {
                adapter.loadMoreEnd(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void useDogSuccess(String data, String currentDogId) {
        //选择成功弹窗
        SharedPrefsHelper.getInstance().saveDogId(currentDogId);
        NormalDialog dialog = NormalDialog.newInstance(R.string.choose_select, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        //更新主页
        ((MyDogActivity) Objects.requireNonNull(getActivity())).isChange = true;
    }

    @Override
    public void removeDogSuccess(String data, String dogId) {
        //选择成功弹窗
        SharedPrefsHelper.getInstance().saveDogId("");
        NormalDialog dialog = NormalDialog.newInstance(R.string.deselect, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        //更新主页
        ((MyDogActivity) Objects.requireNonNull(getActivity())).isChange = true;
    }

    @Override
    public void cancelSellSuccess(String data, int position) {

    }

    @Override
    public void setPresenter(DogContract.DogPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getFeedDogInfo(FeedDogFoodDao data, DogInfoDao mDefultDogInfo) {
        //喂食弹唱
        FeedingDialog dialog = FeedingDialog.newInstance(mDefultDogInfo, data, totalFood);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
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
    public void feedSuccessful(String data) {
        //喂食成功,更新数据
        NormalDialog dialog = NormalDialog.newInstance(R.string.feeding_su, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
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
//        dialog.show(getFragmentManager(), "edit");
//        dialog.setCallback(new FeedofRemindDialog.OperateCallback() {
//
//            @Override
//            public void callback() {
//                BuyFoodDialog buyDialog = BuyFoodDialog.newInstance(totalProperty, data);
//                buyDialog.setTheme(R.style.PaddingScreen);
//                buyDialog.setGravity(Gravity.CENTER);
//                buyDialog.show(getFragmentManager(), "edit");
//                buyDialog.setCallback(new BuyFoodDialog.OperateCallback() {
//                    @Override
//                    public void callback(int number) {
//                        PasswordDialog passwordDialog = PasswordDialog.newInstance();
//                        passwordDialog.setTheme(R.style.PaddingScreen);
//                        passwordDialog.setGravity(Gravity.CENTER);
//                        passwordDialog.show(getFragmentManager(), "edit");
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
        buyDialog.show(getFragmentManager(), "edit");
        buyDialog.setCallback(new BuyFoodDialog.OperateCallback() {
            @Override
            public void callback(int number) {
                PasswordDialog passwordDialog = PasswordDialog.newInstance();
                passwordDialog.setTheme(R.style.PaddingScreen);
                passwordDialog.setGravity(Gravity.CENTER);
                passwordDialog.show(getFragmentManager(), "edit");
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
        dialog.show(getFragmentManager(), "edit");
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
        presenter.getFeedDog(mDefultDogInfo.getId(), mDefultDogInfo);
    }

    private void updateData() {
        presenter.getWallet("1");//获取代币总数
        presenter.getWallet("2");//获取狗粮总数
        presenter.getUserDog(1, 1);
    }
}
