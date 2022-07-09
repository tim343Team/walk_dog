package com.wallet.walkthedog.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.VerificationMnemonicAdapter;
import com.wallet.walkthedog.dao.MnemonicDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;

public class VerificationActivity extends BaseActivity {
    private List<MnemonicDao> dataConfirm = new ArrayList<>();
    private List<MnemonicDao> dataCreate = new ArrayList<>();
    private boolean isBind = false;
    private int selectMnemonNu = 0;//验证助记词的数量
    private int allMnemonNu = 12;//待验证助记词的数量
    private VerificationMnemonicAdapter adapter;
    private VerificationMnemonicAdapter adapter2;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.recyclerview_2)
    RecyclerView recyclerview2;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Context context, List<MnemonicDao> data, boolean isBind) {
        Intent intent = new Intent(context, VerificationActivity.class);
        intent.putExtra("data", (Serializable) data);
        intent.putExtra("isBind", isBind);
        context.startActivity(intent);
    }

    @Override
    protected void befSuperonCreate() {
        super.befSuperonCreate();
        //禁止截屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_verfification;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        initRecyclerView2();
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
        for (int i = 0; i < 12; i++) {
            dataConfirm.add(new MnemonicDao());
        }
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(VerificationActivity.this, 3));
        adapter = new VerificationMnemonicAdapter(VerificationActivity.this, dataConfirm, 1);
        recyclerview.setAdapter(adapter);
        adapter.OnclickListenerDelete(new VerificationMnemonicAdapter.OnclickListenerDelete() {
            @Override
            public void click(int position) {
                MnemonicDao mnemonicDao = dataConfirm.get(position);
                if (mnemonicDao.getWords() == null || mnemonicDao.getWords().isEmpty()) {
                    return;
                }
                //更新底部数据
                for (MnemonicDao mnemonicDao1 : dataCreate) {
                    if (mnemonicDao.getWords().equals(mnemonicDao1.getWords())) {
                        mnemonicDao1.setSelect(false);
                        break;
                    }
                }
                adapter2.notifyDataSetChanged();
                //更新选中数据
                mnemonicDao.setSelect(false);
                mnemonicDao.setWords("");
                adapter.notifyDataSetChanged();
                selectMnemonNu = selectMnemonNu - 1;
            }
        });
    }

    private void initRecyclerView2() {
        //TODO 测试数据
        for (int i = 0; i < 12; i++) {
            MnemonicDao mnemonicDao = new MnemonicDao();
            mnemonicDao.setWords("swords" + i);
            dataCreate.add(mnemonicDao);
        }
        recyclerview2.setHasFixedSize(true);
        recyclerview2.setLayoutManager(new GridLayoutManager(VerificationActivity.this, 3));
        adapter2 = new VerificationMnemonicAdapter(VerificationActivity.this, dataCreate, 2);
        recyclerview2.setAdapter(adapter2);
        adapter2.OnclickListenerItem(new VerificationMnemonicAdapter.OnclickListenerItem() {
            @Override
            public void click(int position) {
                //如果已经选择则点击不生效
                if (dataCreate.get(position).isSelect()) {
                    return;
                }
                dataCreate.get(position).setSelect(true);
                adapter2.notifyItemChanged(position);
                //把选择的值输入到上方列表中
                for (int i = 0; i < dataConfirm.size(); i++) {
                    MnemonicDao mnemonicDao = dataConfirm.get(i);
                    if (!mnemonicDao.isSelect()) {
                        mnemonicDao.setSelect(true);
                        mnemonicDao.setWords(dataCreate.get(position).getWords());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                selectMnemonNu = selectMnemonNu + 1;
                if(selectMnemonNu==allMnemonNu){
                    //TODO 如果已经验证完所以助记词助记词,调用接口
                    SettingPassWordActivity.actionStart(VerificationActivity.this,"2");
                }
            }
        });
    }
}
