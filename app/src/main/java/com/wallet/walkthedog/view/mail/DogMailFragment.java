package com.wallet.walkthedog.view.mail;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.DogMailAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.bus_event.UpdateMailDogEvent;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.db.UserDao;
import com.wallet.walkthedog.db.dao.UserCache;
import com.wallet.walkthedog.view.mail.transaction.TransactionDogActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseLazyFragment;

public class DogMailFragment extends BaseLazyFragment implements MailContract.MailView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int pageNo = 1;
    private DogMailAdapter adapter;
    private List<DogMailDao> data = new ArrayList<>();
    private MailContract.MailPresenter presenter;

    @OnClick(R.id.ll_price)
    void selectPrice() {

    }

    @OnClick(R.id.ll_variety)
    void selectVariety() {

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
        presenter.getDogList(new MailRequest(),pageNo);
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
        String uid = "0";
        List<UserCache> userCaches = UserDao.query(getActivity(), null, null);
        if (userCaches.size() > 0) {
            uid = userCaches.get(0).getUid();
        }
        GridLayoutManager manager = new GridLayoutManager(getmActivity(), 2);
        recyclerView.setLayoutManager(manager);
        adapter = new DogMailAdapter(R.layout.adapter_dog_mail, data,uid);
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
                TransactionDogActivity.actionStart(getActivity(),item);
            }
        });
        adapter.setEnableLoadMore(false);
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
        presenter.getDogList(new MailRequest(),pageNo);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

    }

    @Override
    public void getDogListSuccess(List<DogMailDao> obj) {
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
    public void getPropListSuccess(List<PropMailDao> data) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(UpdateMailDogEvent event) {
        pageNo=1;
        //刷新
        presenter.getDogList(new MailRequest(),pageNo);
    }

    @Override
    public void setPresenter(MailContract.MailPresenter presenter) {
        this.presenter = presenter;
    }
}
