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

import java.util.ArrayList;
import java.util.List;

public class MutSelectCoinDialog<T extends SelectCoinDialog.SimpleText> extends BaseBottomDialog {

    public List<T> lists;

    public SelectCoinDialog.DogAppCallBack<List<Integer>> callBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_select_coin_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Integer> select = new ArrayList<>();
        view.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.isEmpty()) {
                    return;
                }
                if (callBack != null) {
                    callBack.call(select);
                }
                dismiss();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        BaseQuickAdapter<T, BaseViewHolder> adapter = new BaseQuickAdapter<T, BaseViewHolder>(R.layout.simple_item_textview_2, lists) {
            {
                setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (!select.contains(position)) {
                            select.add(position);
                        } else {
                            select.remove(Integer.valueOf(position));
                        }
                        notifyItemChanged(position);
                    }
                });
            }

            @Override
            protected void convert(BaseViewHolder helper, SelectCoinDialog.SimpleText item) {
                helper.setText(R.id.tv, item.getShowText());
                if (select.contains(helper.getAdapterPosition())){
                    helper.setImageResource(R.id.imageView,R.mipmap.icon_select_blue);
                }else {
                    helper.setImageResource(R.id.imageView,R.mipmap.icon_unselect_gray);
                }
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

    }
}
