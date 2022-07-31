package com.wallet.walkthedog.view.mine.otc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;

import java.util.ArrayList;

public class OTCBuyListFragment extends Fragment {

    public static OTCBuyListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        OTCBuyListFragment fragment = new OTCBuyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    OTCListAdapter otcListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = new RecyclerView(requireActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        otcListAdapter = new OTCListAdapter();
        recyclerView.setAdapter(otcListAdapter);
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        otcListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_buy) {
                    Intent intent = new Intent(requireActivity(),PurchaseOTCActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    static class OTCListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        public OTCListAdapter() {
            super(R.layout.item_list_buy, new ArrayList<Object>() {
                {
                    this.add(new Object());
                    this.add(new Object());
                    this.add(new Object());
                    this.add(new Object());
                    this.add(new Object());
                }
            });
        }

        @Override
        protected BaseViewHolder createBaseViewHolder(View view) {
            BaseViewHolder baseViewHolder = super.createBaseViewHolder(view);
            baseViewHolder.addOnClickListener(R.id.tv_buy);
            return baseViewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
        }
    }
}
