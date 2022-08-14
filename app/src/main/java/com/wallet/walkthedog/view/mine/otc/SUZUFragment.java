package com.wallet.walkthedog.view.mine.otc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wallet.walkthedog.R;

public class SUZUFragment extends Fragment {
    public static SUZUFragment newInstance(int advertiseType) {
        Bundle args = new Bundle();
        args.putInt("advertiseType",advertiseType);
        SUZUFragment fragment = new SUZUFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suzu,container,false);
    }
}
