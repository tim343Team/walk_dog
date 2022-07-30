package com.wallet.walkthedog.sp;

public class SafeGet<T> {
    T t;

    public SafeGet(T t) {
        this.t = t;
    }


    public SafeGet<T> onGet(SafeCall<T> call) {
        if (t != null) {
            call.call(t);
        }
        return this;
    }

    public SafeGet<T> onNull(Runnable runnable) {
        if (t == null) {
            runnable.run();
        }
        return this;
    }

    public interface SafeCall<T> {
        void call(T t);
    }
}
