package com.wallet.walkthedog.app;

import android.content.Context;

import com.wallet.walkthedog.data.DataRepository;
import com.wallet.walkthedog.data.LocalDataSource;
import com.wallet.walkthedog.data.RemoteDataSource;


/**
 * ${description}
 *
 * @author weiqiliu
 * @version 1.0 2020/6/9
 */
public class Injection {
    public static DataRepository provideTasksRepository(Context context) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(context));
    }
}
