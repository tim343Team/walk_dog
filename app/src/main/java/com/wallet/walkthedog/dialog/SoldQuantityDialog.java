package com.wallet.walkthedog.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.ErrorCode;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.DogFoodWeightItemDao;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class SoldQuantityDialog extends BaseDialogFragment {


    public DialogConfimCall call;
    private PriceSelectAdapter adapter = new PriceSelectAdapter();
    private EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sold_quantity;
    }


    public static SoldQuantityDialog newInstance(double dogFood) {
        Bundle args = new Bundle();
        args.putDouble("dogFood", dogFood);
        SoldQuantityDialog fragment = new SoldQuantityDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void prepareView(View view) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            double dogFood = getArguments().getDouble("dogFood");
            view.<TextView>findViewById(R.id.tv_all).setText(getString(R.string.my_all_dog_food_s, dogFood)+"g");
        }
        editText = view.findViewById(R.id.edit_price);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_confim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    double price = Double.parseDouble(editText.getText().toString());
                    showPWDialog(price, adapter.getData().get(adapter.select).getId());
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    return;
                }
                if (!checkoutString(editable.toString())) {
                    editText.setText(editable.toString().substring(0,editable.toString().length()-1));
                }
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    private void showPWDialog(double price, int id) {
        View rootView = getView();
        PasswordDialog dialog = new PasswordDialog();
        if (rootView != null) {
            rootView.setVisibility(View.INVISIBLE);
        }
        dialog.setGravity(Gravity.CENTER);
        dialog.setCallback(new PasswordDialog.OperateCallback() {
            @Override
            public void callback(String password) {
                sellDoogFood(price, id,password, new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.shortToast(getString(R.string.sell_success));
                        if (call != null) {
                            call.call(SoldQuantityDialog.this);
                        }
                    }
                });
            }
        });
        dialog.setDismissCallback(new PasswordDialog.DismissCallback() {
            @Override
            public void call() {
                if (rootView != null) {
                    rootView.setVisibility(View.VISIBLE);
                }
            }
        });
        dialog.show(getChildFragmentManager(), "");
    }

    private void sellDoogFood(double price, int id,String pw, Runnable runnable) {
        WonderfulOkhttpUtils.get().url(UrlFactory.sellDogFood() + "?catId=" + id + "&price=" + price+"&passWord="+pw)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Double>>() {
                    @Override
                    protected void onRes(RemoteData<Double> testRemoteData) {
                        runnable.run();
                    }

                    @Override
                    protected void onFail(Exception e) {
                        //TODO 弹窗提示缺少错误码
//                        NormalErrorDialog dialog = NormalErrorDialog.newInstance(ErrorCode.getInstance(code).getMessage(), R.mipmap.icon_normal_no, R.color.color_E12828);
//                        dialog.setTheme(R.style.PaddingScreen);
//                        dialog.setGravity(Gravity.CENTER);
//                        dialog.show(getChildFragmentManager(), "edit");
                        super.onFail(e);
                    }
                });
    }

    private boolean check() {
        if (adapter.select == -1) {
            ToastUtils.shortToast(getString(R.string.please_select_weight));
            return false;
        }
        double price = 0.0;
        try {
            price = Double.parseDouble(editText.getText().toString());
        } catch (Exception ignored) {

        }
        if (price <= 0) {
            ToastUtils.shortToast(getString(R.string.please_input_price));
            return false;
        }

        return true;
    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getDogFoodCat();
    }

    private void getDogFoodCat() {
        WonderfulOkhttpUtils.get().url(UrlFactory.getDogFoodCat())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<List<DogFoodWeightItemDao>>>() {
                    @Override
                    protected void onRes(RemoteData<List<DogFoodWeightItemDao>> testRemoteData) {
                        onGetDogFoodCat(testRemoteData.getNotNullData());
                    }
                });
    }

    private void onGetDogFoodCat(List<DogFoodWeightItemDao> notNullData) {
        adapter.setNewData(notNullData);
    }


    static class PriceSelectAdapter extends BaseQuickAdapter<DogFoodWeightItemDao, BaseViewHolder> {
        int select = -1;

        public PriceSelectAdapter() {
            super(R.layout.item_price_select_item);
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (select == position) {
                        return;
                    }
                    if (select != -1) {
                        adapter.notifyItemChanged(select);
                    }
                    select = position;
                    adapter.notifyItemChanged(select);
                }
            });
        }

        @Override
        protected void convert(BaseViewHolder helper, DogFoodWeightItemDao item) {
            TextView tv = helper.getView(R.id.tv_weight);
            helper.setText(R.id.tv_weight, Utils.getFormat("%.0f", item.getWeight()));
            if (helper.getAdapterPosition() == select) {
                tv.setTextColor(Color.parseColor("#4D67C1"));
            } else {
                tv.setTextColor(Color.BLACK);
            }
        }
    }

    public boolean checkoutString(String text) {
        Pattern EMAIL = Pattern.compile("^\\d+(\\.\\d{0,4})?$");
        boolean flag = false;
        try {
            Matcher matcher = EMAIL.matcher(text);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public interface DialogConfimCall {
        void call(DialogFragment dialog);
    }
}
