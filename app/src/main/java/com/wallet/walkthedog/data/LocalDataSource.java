package com.wallet.walkthedog.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE;
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());

    public LocalDataSource(Context context) {
        this.context = context;
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

}
