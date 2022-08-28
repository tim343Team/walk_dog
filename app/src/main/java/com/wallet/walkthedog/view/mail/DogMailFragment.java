package com.wallet.walkthedog.view.mail;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.DogMailAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateMailDogEvent;
import com.wallet.walkthedog.dao.DogBoxDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PriceBoxDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.db.UserDao;
import com.wallet.walkthedog.db.dao.UserCache;
import com.wallet.walkthedog.view.mail.transaction.TransactionDogActivity;
import com.wallet.walkthedog.view.merchant.MerchantActivity;
import com.wallet.walkthedog.view.merchant.MerchantApplyActivity;
import com.wallet.walkthedog.view.mine.ad.ADAssetActivity;
import com.wallet.walkthedog.view.mine.ad.PlaceADActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseLazyFragment;
import tim.com.libnetwork.utils.ScreenUtils;

public class DogMailFragment extends BaseLazyFragment implements MailContract.MailView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_variety)
    View llVariety;
    @BindView(R.id.tv_variety)
    TextView tvVariety;
    @BindView(R.id.ll_price)
    View llPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    private int pageNo = 1;
    private DogMailAdapter adapter;
    private List<DogMailDao> data = new ArrayList<>();
    private List<DogBoxDao> boxDaoList = new ArrayList<>();
    private List<PriceBoxDao> priceList = new ArrayList<>();
    private MailContract.MailPresenter presenter;
    private String priceSort = "";
    private String nftCatagoryId = "";

    @OnClick(R.id.ll_price)
    void selectPrice() {
        ArrayList<String> items = new ArrayList<>();
        for (PriceBoxDao dao : priceList) {
            items.add(dao.getName());
        }
        showSelectWindow(llPrice, tvPrice, items, 0);
    }

    @OnClick(R.id.ll_variety)
    void selectVariety() {
        ArrayList<String> items = new ArrayList<>();
        for (DogBoxDao dao : boxDaoList) {
            items.add(dao.getName());
        }
        showSelectWindow(llVariety, tvVariety, items, 1);
    }

    public static DogMailFragment getInstance() {
        DogMailFragment fragment = new DogMailFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dog_mail;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new MailPresenter(Injection.provideTasksRepository(getmActivity()), this);//初始化presenter
        initRecyclerView();
        presenter.getDogList(new MailRequest(priceSort, nftCatagoryId), pageNo);
        presenter.getDogDownBox();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    protected void obtainData() {
        final LinkedList<String> priceTypeList = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.text_price_type)));
        for (int i = 0; i < priceTypeList.size(); i++) {
            String name = priceTypeList.get(i);
            PriceBoxDao boxDao = new PriceBoxDao(name);
            if (i == 0) {
                boxDao.setDirection("");
            } else if (i == 1) {
                boxDao.setDirection("asc");
            } else if (i == 2) {
                boxDao.setDirection("desc");
            }
            priceList.add(boxDao);
        }
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
        String uid = "0";
        List<UserCache> userCaches = UserDao.query(getActivity(), null, null);
        if (userCaches.size() > 0) {
            uid = userCaches.get(0).getUid();
        }
        GridLayoutManager manager = new GridLayoutManager(getmActivity(), 2);
        recyclerView.setLayoutManager(manager);
        adapter = new DogMailAdapter(R.layout.adapter_dog_mail, data, uid);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.OnclickListenerItem(new DogMailAdapter.OnclickListenerItem() {
            @Override
            public void click(DogMailDao item) {
                TransactionDogActivity.actionStart(getActivity(), item);
            }
        });
        adapter.setEnableLoadMore(false);
    }

    private void refresh() {
        adapter.setEnableLoadMore(true);
        adapter.loadMoreEnd(false);
        pageNo = 1;
        presenter.getDogList(new MailRequest(priceSort, nftCatagoryId), pageNo);
    }

    private void loadMore() {
        refreshLayout.setEnabled(false);
        pageNo = pageNo + 1;
        presenter.getDogList(new MailRequest(priceSort, nftCatagoryId), pageNo);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
    }

    @Override
    public void getDogListSuccess(List<DogMailDao> data) {
        adapter.loadMoreComplete();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        if (data == null || data.size() == 0) {
            if (pageNo == 1) {
                this.data.clear();
                adapter.notifyDataSetChanged();
            }
            return;
        }
        if (pageNo == 1) {
            this.data.clear();
            this.data.addAll(data);
        } else {
            this.data.addAll(data);
        }
        if (data.size() < 20) {
            adapter.loadMoreEnd();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getPropListSuccess(List<PropMailDao> data) {

    }

    @Override
    public void getBoxtSuccess(List<DogBoxDao> data) {
        boxDaoList.clear();
        DogBoxDao initDogBoxDao = new DogBoxDao();
        initDogBoxDao.setId(-1);
        initDogBoxDao.setName(getResources().getString(R.string.all));
        boxDaoList.add(0, initDogBoxDao);
        boxDaoList.addAll(data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(UpdateMailDogEvent event) {
        //刷新
        refresh();
    }

    @Override
    public void setPresenter(MailContract.MailPresenter presenter) {
        this.presenter = presenter;
    }

    private PopupWindow popupWindow;

    private void showSelectWindow(View v, TextView textView, ArrayList<String> items, int type) {
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.simple_item_textview) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv, item);
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    if (type == 0) {
                        //价格
                        onMenuPriceSelect(position, textView);
                    } else {
                        //种类
                        onMenuSelect(position, textView);
                    }
                }
            }
        });
        adapter.setNewData(items);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_type_select, null, false);
        view.measure(View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenWidth(getActivity()), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(ScreenUtils.getScreenHeight(getActivity()), View.MeasureSpec.AT_MOST)
        );
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);
        popupWindow = new PopupWindow(view, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(false);
        int measuredWidth = view.getMeasuredWidth();
        int[] outLocation = new int[2];
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        v.getLocationInWindow(outLocation);
        if (type == 0) {
            popupWindow.showAsDropDown(v, 0, 0, Gravity.BOTTOM);
        } else {
            popupWindow.showAsDropDown(v, -measuredWidth + (screenWidth - outLocation[0]), 0, Gravity.BOTTOM);
        }
    }

    private void onMenuSelect(int position, TextView textView) {
        textView.setText(boxDaoList.get(position).getName());
        if (boxDaoList.get(position).getId() == -1) {
            nftCatagoryId = "";
        } else {
            nftCatagoryId = boxDaoList.get(position).getId() + "";
        }
        refresh();
    }

    private void onMenuPriceSelect(int position, TextView textView) {
        textView.setText(priceList.get(position).getName());
        priceSort = priceList.get(position).getDirection();
        refresh();
    }
}
