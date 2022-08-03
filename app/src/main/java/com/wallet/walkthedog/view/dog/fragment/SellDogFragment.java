package com.wallet.walkthedog.view.dog.fragment;

import android.os.Bundle;
import android.view.Gravity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.adapter.SellDogsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateDogEvent;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.dog.DogDetailActivity;
import com.wallet.walkthedog.view.props.fragment.PropsContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseLazyFragment;

public class SellDogFragment extends BaseLazyFragment  implements DogContract.DogView{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private DogContract.DogPresenter presenter;
    private int pageNo = 1;
    private int type = 2;
    private List<DogInfoDao> data = new ArrayList<>();
    private SellDogsAdapter adapter;

    public static SellDogFragment getInstance() {
        SellDogFragment fragment = new SellDogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sell_dog;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new DogPresenter(Injection.provideTasksRepository(getmActivity()), this);//初始化presenter
        initRecyclerView();
        presenter.getUserDog(type,pageNo);
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
        pageNo = 1;
        presenter.getUserDog(type, pageNo);
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
        presenter.getUserDog(type, pageNo);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getmActivity(), 2);
        recyclerView.setLayoutManager(manager);
        adapter = new SellDogsAdapter(R.layout.adapter_sell_dog, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.OnclickListenerItem(new SellDogsAdapter.OnclickListenerItem() {
            @Override
            public void click(DogInfoDao item,int position) {
                //取消售卖
                presenter.cancelSellDog(new BuyRequest(item.getId()),position);
            }
        });
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no,R.color.color_E12828);
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
                adapter.loadMoreEnd();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void useDogSuccess(String data, String dogId) {

    }

    @Override
    public void cancelSellSuccess(String message,int position) {
        data.remove(position);
        adapter.notifyItemRemoved(position);
        NormalDialog normalDialog = NormalDialog.newInstance(R.string.cancle_sale, R.mipmap.icon_normal);
        normalDialog.setTheme(R.style.PaddingScreen);
        normalDialog.setGravity(Gravity.CENTER);
        normalDialog.show(getFragmentManager(), "edit");
    }

    @Override
    public void setPresenter(DogContract.DogPresenter presenter) {
        this.presenter=presenter;
    }
}
