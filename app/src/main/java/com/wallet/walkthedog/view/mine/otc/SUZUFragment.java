package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.ContryItem;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.untils.Utils;

import java.util.List;

import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class SUZUFragment extends Fragment {
    public static SUZUFragment newInstance(int advertiseType) {
        Bundle args = new Bundle();
        args.putInt("advertiseType", advertiseType);
        SUZUFragment fragment = new SUZUFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suzu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText ed1 = view.findViewById(R.id.editTextTextPersonName);
        EditText ed2 = view.findViewById(R.id.editTextTextPersonName2);
        assert getArguments() != null;
        int advertiseType = getArguments().getInt("advertiseType");

        TextView tv_hint_1 = view.findViewById(R.id.tv_hint_1);
        if (advertiseType==1){
            tv_hint_1.setText("USDT");
        }else {
            tv_hint_1.setText("SUZU");
        }
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Utils.toDouble(ed1.getText().toString());
                if (amount <= 0) {
                    ToastUtils.shortToast(getString(R.string.please_input_amount));
                    return;
                }
                submit(amount, advertiseType);
            }
        });

    }

    private void submit(double amount, int advertiseType) {
        int type = 1;
        if (advertiseType == 1) {
            type = 2;
        }
        WonderfulOkhttpUtils.post().url(UrlFactory.walletExchange())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("type",String.valueOf(type))
                .addParams("amount",String.valueOf(amount))
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<Object>>() {
                    @Override
                    protected void onRes(RemoteData<Object> testRemoteData) {
                        ToastUtils.shortToast(getString(R.string.successful));
                    }
                });
    }
}
