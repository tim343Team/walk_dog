package com.wallet.walkthedog.view.props.fragment;

import android.os.Bundle;
import android.view.Gravity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.ChoicePropsAdapter;
import com.wallet.walkthedog.adapter.MyDogsAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.OpenindDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.props.ChoicePropsActivity;
import com.wallet.walkthedog.view.props.MyPropsActivity;
import com.wallet.walkthedog.view.props.PropDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseLazyFragment;

public class MyPropsFragment extends BaseLazyFragment implements PropsContract.PropsView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private PropsContract.PropsPresenter presenter;
    private int pageNo = 1;
    private int type = 1;
    private String currentDogId;
    private List<PropDao> data = new ArrayList<>();
    private MyPropsAdapter adapter;

    public static MyPropsFragment getInstance() {
        MyPropsFragment fragment = new MyPropsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_props;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new PropsPresenter(Injection.provideTasksRepository(getmActivity()), this);//初始化presenter
        currentDogId = SharedPrefsHelper.getInstance().getDogId();
        initRecyclerView();
        presenter.getUserProp(type, pageNo);
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

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getmActivity(), 2);
        recyclerView.setLayoutManager(manager);
        adapter = new MyPropsAdapter(R.layout.adapter_my_props, data);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setEnableLoadMore(false);
        adapter.OnclickListenerRoot(new MyPropsAdapter.OnclickListenerRoot() {
            @Override
            public void click(int position, String type) {
                PropDao dao = data.get(position);
                PropDetailActivity.actionStart(getmActivity(), data.get(position).getId(), type, dao, currentDogId);
            }
        });
        adapter.OnclickListenerItem(new MyPropsAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                if (data.get(position).getType() == 1) {
                    //移除装备
                    PropDao dao = data.get(position);
                    presenter.getRemoveProp(new OpreationPropRequest(dao.getId(), String.valueOf(dao.getDecorateDogId())), position);
                } else {
                    //安装装备
                    PropDao dao = data.get(position);
                    presenter.getAddProp(new OpreationPropRequest(dao.getId(), currentDogId), position);
                }
            }
        });
        adapter.OnclickListenerItem(new MyPropsAdapter.OnOpenListenerItem() {
            @Override
            public void click(int position, int type) {
                if (type == 0) {
                    //打開狗粮接口
                    presenter.useDogFood(new OpreationPropRequest(data.get(position).getId()), position);
                } else if (type == 1) {
                    //打開寶箱接口
                    presenter.openBox(new OpreationPropRequest(data.get(position).getId()), position);
                }
            }
        });
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
        presenter.getUserProp(type, pageNo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(UpdatePropsEvent event) {
        pageNo = 1;
        presenter.getUserProp(type, pageNo);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no,R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @Override
    public void getAddFail(Integer code, String toastMessage) {
        //提示
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(R.string.cancle_props, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        return;
    }

    @Override
    public void getPropSuccess(List<PropDao> obj) {
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
    public void getRemovePropSuccess(int position) {
        data.get(position).setType(2);
        data.get(position).setDecorateDogId(0);
        adapter.notifyItemChanged(position);
        ((MyPropsActivity) Objects.requireNonNull(getActivity())).isChange = true;
    }

    @Override
    public void getAddPropSuccess(int position) {
        data.get(position).setType(1);
        data.get(position).setDecorateDogId(Integer.parseInt(currentDogId));
        adapter.notifyItemChanged(position);
        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        ((MyPropsActivity) Objects.requireNonNull(getActivity())).isChange = true;
    }

    @Override
    public void useDogFoodSuccess(String dap,int position) {
        //打開狗糧
        data.remove(position);
        adapter.notifyItemRemoved(position);
        OpenindDialog openindDialog=OpenindDialog.newInstance(Constant.PROP_FOOD);
        openindDialog.setTheme(R.style.PaddingScreen);
        openindDialog.setGravity(Gravity.CENTER);
        openindDialog.show(getFragmentManager(), "edit");
        openindDialog.setCallback(new OpenindDialog.OperateCallback() {
            @Override
            public void callback(String name) {
                NormalDialog dialog = NormalDialog.newInstance(String.format(getString(R.string.input_prop_success), getResources().getString(R.string.dog_food)), R.mipmap.icon_normal);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getFragmentManager(), "edit");
                openindDialog.dismiss();
            }
        });
    }

    @Override
    public void cancelSellSuccess(String data, int position) {

    }

    @Override
    public void openBoxSuccess(BoxDao dao, int position) {
        //打开宝箱成功
        data.remove(position);
        adapter.notifyItemRemoved(position);
        OpenindDialog openindDialog=OpenindDialog.newInstance(Constant.PROP_NORMAL,dao);
        openindDialog.setTheme(R.style.PaddingScreen);
        openindDialog.setGravity(Gravity.CENTER);
        openindDialog.show(getFragmentManager(), "edit");
        openindDialog.setCallback(new OpenindDialog.OperateCallback() {
            @Override
            public void callback(String name) {
                NormalDialog dialog = NormalDialog.newInstance(String.format(getString(R.string.input_prop_success), name), R.mipmap.icon_normal);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getFragmentManager(), "edit");
                openindDialog.dismiss();
            }
        });
    }

    @Override
    public void setPresenter(PropsContract.PropsPresenter presenter) {
        this.presenter = presenter;
    }
}
