package com.wallet.walkthedog.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.wallet.walkthedog.app.RootApplication;

public class SharedPrefsHelper {
    private static final String SHARED_PREFS_NAME = "qb";

    private static final String SP_KEY_FIRST = "FIRST";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String SP_KEY_TOKEN = "SP_KEY_TOKEN";
    private static final String SIGN_PASSWORD = "SIGN_PASSWORD";

    private static SharedPrefsHelper instance;

    private SharedPreferences sharedPreferences;

    public static synchronized SharedPrefsHelper getInstance() {
        if (instance == null) {
            instance = new SharedPrefsHelper();
        }

        return instance;
    }

    private SharedPrefsHelper() {
        instance = this;
        sharedPreferences = RootApplication.getInstance().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存是否第一次进入
     */
    public void saveFirst() {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putBoolean(SP_KEY_FIRST, false).apply();
    }

    /**
     * 获取是否第一次进入
     */
    public boolean getFirst() {
        return sharedPreferences == null ? true : sharedPreferences.getBoolean(SP_KEY_FIRST, true);
    }

    /**
     * 是否登录
     */
    public void setLogin() {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putBoolean(IS_LOGIN, false).apply();
    }

    /**
     * 设置登录
     */
    public boolean isLogin() {
        return sharedPreferences == null ? true : sharedPreferences.getBoolean(IS_LOGIN, true);
    }

    /**
     * 保存验证sign_paddword
     */
    public void saveSignPassword(String tokenKey) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putString(SIGN_PASSWORD, tokenKey).apply();
    }

    /**
     * 获取验证sign_paddword
     */
    public String getSignPassword() {
        return sharedPreferences == null ? "" : sharedPreferences.getString(SIGN_PASSWORD, "");
    }
}
