package com.wallet.walkthedog;

import com.wallet.walkthedog.net.RemoteData;

public abstract class ListTest<T> extends Test<T> {

//
//
//
//

    interface Covert<T, O> {

        O convert(T data);
    }

    public void test(T t) {
        doing(t);
    }

    public static <T> T covert(RemoteData<T> data) {
        return data.getData();
    }


}
