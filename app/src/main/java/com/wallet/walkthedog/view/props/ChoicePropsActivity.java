package com.wallet.walkthedog.view.props;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.db.UserDao;
import com.wallet.walkthedog.db.dao.PropCache;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.view.email.EmailPresenter;
import com.wallet.walkthedog.view.home.HomeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class ChoicePropsActivity extends BaseActivity implements ChoicePropsContract.ChoicePropsView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int count;//可以選擇的數量
    private ChoicePropsContract.ChoicePropsPresenter presenter;
    private String dogId;
    private List<PropCache> propCache = new ArrayList<>();
    private boolean isChange = false;
    private int pageNo = 1;
    private MyPropsAdapter adapter;
    private List<PropDao> data = new ArrayList<>();

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, String dogId) {
        Intent intent = new Intent(activity, ChoicePropsActivity.class);
        intent.putExtra("dogId", dogId);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_choice_props;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new ChoicePropsPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        count = getIntent().getIntExtra("count", 0);
        dogId = getIntent().getStringExtra("dogId");
        getPropInfo(dogId);
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
        presenter.getUserProp(pageNo);
    }

    @Override
    public void finish() {
        if (isChange)
            EventBus.getDefault().post(new UpdateHomeData());
        super.finish();
    }

    private void getPropInfo(String dogId) {
        propCache.clear();
        String whereClause = "dogid=?";
        String[] whereArgs = {dogId};
        List<PropCache> propCaches = com.wallet.walkthedog.db.PropDao.query(this, whereClause, whereArgs);
        propCache.addAll(propCaches);
    }

    private void initRecyclerView() {
        //TODO 測試數據
//        for (int i = 0; i < 10; i++) {
//            PropDao propDao = new PropDao();
//            propDao.setId(i+"0");
//            data.add(propDao);
//        }
        GridLayoutManager manager = new GridLayoutManager(this, 2);
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
        adapter.OnclickListenerItem(new MyPropsAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                if (data.get(position).getType()==1) {
                    //移除装备
                    PropDao dao = data.get(position);
                    presenter.getRemoveProp(new OpreationPropRequest(dao.getId(),dogId),position);

                } else {
                    if (adapter.getSelectCount() > 2) {
                        //提示
                        NormalDialog dialog = NormalDialog.newInstance(R.string.cancle_props, R.mipmap.icon_normal_no, R.color.color_E12828);
                        dialog.setTheme(R.style.PaddingScreen);
                        dialog.setGravity(Gravity.CENTER);
                        dialog.show(getSupportFragmentManager(), "edit");
                        return;
                    } else {
                        //安装装备
                        PropDao dao = data.get(position);
                        presenter.getAddProp(new OpreationPropRequest(dao.getId(),dogId),position);
                    }
                }
            }
        });
    }

    private void loadMore() {
        pageNo = pageNo + 1;
        adapter.setEnableLoadMore(false);
//        refreshLayout.setEnabled(false);
        presenter.getUserProp(pageNo);
    }

    @Override
    public void getFail(Integer code, String toastMessage) {

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
        adapter.removeSelect(data.get(position));
        data.get(position).setType(3);
        adapter.notifyItemChanged(position);
        //从数据库删除
//        PropDao dao = data.get(position);
//        String whereClause = "dogid=? and uid=?";
//        String[] whereArgs = {dogId, dao.getId()};
//        com.wallet.walkthedog.db.PropDao.delete(ChoicePropsActivity.this, whereClause, whereArgs);
        isChange = true;
    }

    @Override
    public void getAddPropSuccess(int position) {
        adapter.addSelect(data.get(position));
        data.get(position).setType(1);
        adapter.notifyItemChanged(position);
        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        //插入数据库
//        PropDao dao = data.get(position);
//        ContentValues cv = new ContentValues();
//        cv.put("uid", dao.getId());
//        cv.put("name", dao.getName());
//        cv.put("img", dao.getImg());
//        cv.put("propNumberChain", dao.getPropNumberChain());
//        cv.put("dogId", dogId);
//        com.wallet.walkthedog.db.PropDao.insert(ChoicePropsActivity.this, cv);
        isChange = true;
    }

    @Override
    public void setPresenter(ChoicePropsContract.ChoicePropsPresenter presenter) {
        this.presenter = presenter;
    }
}
