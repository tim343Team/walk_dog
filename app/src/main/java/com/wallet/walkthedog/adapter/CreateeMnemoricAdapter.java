package com.wallet.walkthedog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.MnemonicDao;

import java.util.ArrayList;
import java.util.List;

public class CreateeMnemoricAdapter extends RecyclerView.Adapter<CreateeMnemoricAdapter.ViewHolder> {
    private Context context;
    private List<MnemonicDao> data = new ArrayList();
    private int type;//1.create  2.backup

    public CreateeMnemoricAdapter(Context context, List<MnemonicDao> data, int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type == 1) {
            view = View.inflate(parent.getContext(), R.layout.adapter_create_mnemonic, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.adapter_create_mnemonic, null);
        }
        return new CreateeMnemoricAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateeMnemoricAdapter.ViewHolder holder, int position) {
        MnemonicDao mnemonicDao = data.get(position);
        holder.txtData.setText(mnemonicDao.getWords());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtData;

        public ViewHolder(View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.txt_data);
        }
    }
}
