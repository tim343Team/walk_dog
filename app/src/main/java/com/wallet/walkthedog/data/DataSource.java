package com.wallet.walkthedog.data;

public interface DataSource {

    interface DataCallback<T> {

        void onDataLoaded(T obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }


}
