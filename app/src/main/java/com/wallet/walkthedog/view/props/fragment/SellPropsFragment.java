package com.wallet.walkthedog.view.props.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdatePropsEvent;
import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.dialog.OpenindDialog;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.view.home.HomeActivity;
import com.wallet.walkthedog.view.props.PropDetailActivity;
import com.wallet.walkthedog.view.props.SellRecordActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseLazyFragment;

public class SellPropsFragment extends BaseLazyFragment implements PropsContract.PropsView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @OnClick(R.id.ll_sell_record)
    void gotoRecord(){
        //售卖记录
        SellRecordActivity.actionStart(getmActivity());
    }

    private PropsContract.PropsPresenter presenter;
    private int pageNo = 1;
    private int type = 2;
    private String currentDogId;
    private List<PropDao> data = new ArrayList<>();
    private MyPropsAdapter adapter;

    public static SellPropsFragment getInstance() {
        SellPropsFragment fragment = new SellPropsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sell_props;
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
        LayoutInflater layoutInflater = getLayoutInflater();//获得layoutInflater对象
        View emptyView = layoutInflater.inflate(R.layout.empty_sell_prop, null);//获得view对象
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(HomeActivity.instance!=null){
                    HomeActivity.instance.type=1;
                    HomeActivity.actionStart(getActivity(),1);
                }
            }
        });
        adapter.setEmptyView(emptyView);
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
                PropDetailActivity.actionStart(getmActivity(), data.get(position).getId(), type, dao, currentDogId,true);
            }
        });
        adapter.OnclickListenerItem(new MyPropsAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                //移除售賣
                presenter.cancelSellProp(new BuyRequest(data.get(position).getId()), position);
            }
        });
        adapter.OnclickListenerItem(new MyPropsAdapter.OnOpenListenerItem() {
            @Override
            public void click(int position, int type) {
                if (type == 0) {
                    //售卖狗粮
                    presenter.cancelSellProp(new BuyRequest(data.get(position).getId()), position);
                } else if (type == 1) {
                    //售卖宝箱
                    presenter.cancelSellProp(new BuyRequest(data.get(position).getId()), position);
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
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @Override
    public void getAddFail(Integer code, String toastMessage) {

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
    public void getRemovePropSuccess(int dao) {

    }

    @Override
    public void getAddPropSuccess(int dao) {

    }

    @Override
    public void useDogFoodSuccess(String dao, int position) {
        //打開狗糧
        data.remove(position);
        adapter.notifyItemRemoved(position);
        OpenindDialog openindDialog = OpenindDialog.newInstance(Constant.PROP_FOOD);
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
    public void cancelSellSuccess(String message, int position) {
        //取消售卖成功刷新页面
        data.remove(position);
        if (data.size() > 0) {
            adapter.notifyItemRemoved(position);
        } else {
            adapter.notifyDataSetChanged();
        }
        EventBus.getDefault().post(new UpdatePropsEvent());
    }

    @Override
    public void openBoxSuccess(BoxDao dao, int position) {
        //打开宝箱成功
        data.remove(position);
        adapter.notifyItemRemoved(position);
        OpenindDialog openindDialog = OpenindDialog.newInstance(Constant.PROP_NORMAL, dao);
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
