package com.wallet.walkthedog;

import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

abstract class Test<T> {

    public Test() {
        Class<?> superclass = this.getClass();
        Type type = getSuperclassTypeParameter(superclass);



        System.out.println(type.getTypeName());

        System.out.println( new TypeToken<List<String>>(){}.getType().getTypeName());


    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    abstract void doing(T t);
}