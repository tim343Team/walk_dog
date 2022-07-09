package com.wallet.walkthedog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.PropDao;

import java.util.ArrayList;
import java.util.List;

import tim.com.libnetwork.view.CircleImageView;

public class HomePropsAdapter extends RecyclerView.Adapter<HomePropsAdapter.MyViewHolder> {
    private Context context;
    private List<PropDao> data = new ArrayList<>();

    public HomePropsAdapter(Context context, List<PropDao> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_my_props, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomePropsAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
