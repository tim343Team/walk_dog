package com.wallet.walkthedog.net;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.wallet.walkthedog.untils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GsonWalkDogCallBack<T> extends AbsWalkDogCallBack<T> {

    static Gson gson = new Gson();

    @Override
    T conver(String string) throws ApiException {
        Type type = getSuperclassTypeParameter(this.getClass());
        T o = gson.fromJson(string, type);
        if (o instanceof RemoteData) {
            int code = ((RemoteData<?>) o).getCode();
            if (code != 0) {
                throw new ApiException(((RemoteData<?>) o).getMessage());
            }
        }
        return o;
    }

    @Override
    protected void onFail(Exception e) {
        ToastUtils.shortToast(e.getMessage());
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        assert parameterized != null;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    static class ApiException extends Exception {
        public ApiException(String messgae) {
            super(messgae);
        }
    }
}
