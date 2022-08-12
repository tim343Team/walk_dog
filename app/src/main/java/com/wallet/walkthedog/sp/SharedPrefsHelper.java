package com.wallet.walkthedog.sp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.wallet.walkthedog.app.RootApplication;
import com.wallet.walkthedog.dao.UserInfoDao;

public class SharedPrefsHelper {
    private static final String SHARED_PREFS_NAME = "qb";

    private static final String SP_KEY_FIRST = "FIRST";
    private static final String USER_TOKEN = "USER_TOKEN";
    private static final String SP_KEY_TOKEN = "SP_KEY_TOKEN";
    private static final String SIGN_PASSWORD = "SIGN_PASSWORD";
    private static final String CURRENT_DOG_ID = "CURRENT_DOG_ID";
    private static final String CARD_TYPE = "CARD_TYPE";

    private static final String USERINFO_KEY = "USERINFO_KEY";


    private static SharedPrefsHelper instance;
    private static final Gson gson = new Gson();

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
     * 获取token
     */
    public String getToken() {
        return sharedPreferences == null ? "" : sharedPreferences.getString(USER_TOKEN, "");
    }

    /**
     * 保存token
     */
    public void saveToken(String tokenKey) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putString(USER_TOKEN, tokenKey).apply();
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

    /**
     * 获取当前狗狗id
     */
    public String getDogId() {
        return sharedPreferences == null ? "" : sharedPreferences.getString(CURRENT_DOG_ID, "0");
    }

    /**
     * 保存当前狗狗id
     */
    public void saveDogId(String id) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putString(CURRENT_DOG_ID, id).apply();
    }

    /**
     * 保存当前選擇卡
     */
    public void saveCard(int type) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putInt(CARD_TYPE, type).apply();
    }

    /**
     * 獲取当前選擇卡
     */
    public int getCard() {
        return sharedPreferences == null ? 0 : sharedPreferences.getInt(CARD_TYPE, 0);
    }

    @Nullable
    public UserInfoDao getUserInfo() {
        String userinfo_key = sharedPreferences.getString(USERINFO_KEY, "");
        UserInfoDao userInfoDao = null;
        try {
            userInfoDao = gson.fromJson(userinfo_key, UserInfoDao.class);
        } catch (Exception e) {

        }
        return userInfoDao;
    }

    public void saveUserInfo(UserInfoDao dao) {
        if (dao != null) {
            sharedPreferences.edit().putString(USERINFO_KEY, gson.toJson(dao)).apply();
        } else {
            sharedPreferences.edit().remove(USERINFO_KEY).apply();
        }
    }

    public SafeGet<UserInfoDao> AsyncGetUserInfo() {
        return new SafeGet<>(getUserInfo());
    }


}
