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
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateDogEvent;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.dog.DogDetailActivity;
import com.wallet.walkthedog.view.dog.MyDogActivity;
import com.wallet.walkthedog.view.props.MyPropsActivity;
import com.wallet.walkthedog.view.props.fragment.MyPropsFragment;
import com.wallet.walkthedog.view.props.fragment.PropsContract;
import com.wallet.walkthedog.view.props.fragment.PropsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseLazyFragment;

public class MyDogFragment extends BaseLazyFragment implements DogContract.DogView{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private DogContract.DogPresenter presenter;
    private int pageNo = 1;
    private int type = 1;
    private List<DogInfoDao> data = new ArrayList<>();
    private MyDogsAdapter adapter;

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
        if(adapter!=null){
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
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.setCallback(new MyDogsAdapter.OperateCallback() {
            @Override
            public void callback(DogInfoDao dao, int selectPosition, int oldSelectPosition) {
                adapter.notifyItemChanged(selectPosition);
                adapter.notifyItemChanged(oldSelectPosition);
                //选择使用
                presenter.useDog(dao.getId());
            }
        });
        adapter.setFeedCallback(new MyDogsAdapter.FeedCallback() {
            @Override
            public void callback(DogInfoDao dao) {
                //TODO feed
            }
        });
        adapter.setItemCallback(new MyDogsAdapter.ItemCallback() {
            @Override
            public void callback(DogInfoDao dao) {
                DogDetailActivity.actionStart(getActivity(),dao);
            }
        });

    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalDialog dialog = NormalDialog.newInstance(toastMessage, R.mipmap.icon_normal_no,R.color.color_E12828);
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
    public void useDogSuccess(String data, String currentDogId) {
//        if (!SharedPrefsHelper.getInstance().getDogId().isEmpty()) {
//            presenter.removeDog(SharedPrefsHelper.getInstance().getDogId());
//        }
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
    public void cancelSellSuccess(String data, int position) {

    }

    @Override
    public void setPresenter(DogContract.DogPresenter presenter) {
        this.presenter=presenter;
    }
}
