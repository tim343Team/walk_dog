package com.wallet.walkthedog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.MnemonicDao;

import java.util.ArrayList;
import java.util.List;

public class VerificationMnemonicAdapter extends RecyclerView.Adapter<VerificationMnemonicAdapter.ViewHolder> {
    private Context context;
    private List<MnemonicDao> data = new ArrayList();
    private int type;//1.confirm  2.create

    public VerificationMnemonicAdapter(Context context, List<MnemonicDao> data, int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @Override
    public VerificationMnemonicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type == 1) {
            view = View.inflate(parent.getContext(), R.layout.adapter_confirm_mnemonic, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.adapter_create_mnemonic_2, null);
        }
        return new VerificationMnemonicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationMnemonicAdapter.ViewHolder holder, int position) {
        if (type == 1) {
            MnemonicDao mnemonicDao = data.get(position);
            holder.txtData.setText(mnemonicDao.getWords());
//            if (mnemonicDao.isSelect()) {
//                holder.rootView.setBackgroundResource(R.drawable.rectangle_white_purple);
//            } else {
//                holder.rootView.setBackgroundResource(R.drawable.rectangle_white_gray_gap);
//                for (int i = 0; i < data.size(); i++) {
//                    if(data.get(i).getWords()==null || data.get(i).getWords().isEmpty()){
//                        if (i == position) {
//                            holder.rootView.setBackgroundResource(R.drawable.rectangle_white_purple_gap);
//                        }
//                        break;
//                    }
//                }
//            }
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickDelete.click(position);
                }
            });
        } else {
            MnemonicDao mnemonicDao = data.get(position);
            holder.txtData.setText(mnemonicDao.getWords());
            if (mnemonicDao.isSelect()) {
                holder.txtData.setTextColor(ContextCompat.getColor(context, R.color.color_ADAEB3));
            } else {
                holder.txtData.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.click(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtData;
        private View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.txt_data);
            rootView = itemView.findViewById(R.id.ll_root);
        }
    }

    OnclickListenerItem itemClick;

    public void OnclickListenerItem(OnclickListenerItem itemClick) {
        this.itemClick = itemClick;
    }

    public interface OnclickListenerItem {
        void click(int position);

    }

    OnclickListenerDelete itemClickDelete;

    public void OnclickListenerDelete(OnclickListenerDelete itemClickDelete) {
        this.itemClickDelete = itemClickDelete;
    }

    public interface OnclickListenerDelete {
        void click(int position);

    }
}
