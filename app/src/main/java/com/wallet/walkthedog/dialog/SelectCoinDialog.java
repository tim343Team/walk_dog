package com.wallet.walkthedog.dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;

import java.util.List;

public class SelectCoinDialog<T extends SelectCoinDialog.SimpleText> extends BaseBottomDialog {

    public List<T> lists;

    public DogAppCallBack<Integer> callBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_select_coin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        BaseQuickAdapter<T, BaseViewHolder> adapter = new BaseQuickAdapter<T, BaseViewHolder>(R.layout.simple_item_textview_1, lists) {
            @Override
            protected void convert(BaseViewHolder helper, SimpleText item) {
                helper.setText(R.id.tv, item.getShowText());
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                if (callBack != null) {
                    callBack.call(position);
                }

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

    }


    public interface SimpleText {
        String getShowText();
    }


    public interface DogAppCallBack<T> {
        void call(T t);
    }

}
