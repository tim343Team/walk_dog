package com.wallet.walkthedog.app;


import com.wallet.walkthedog.config.AppConfig;

public class UrlFactory {
    public static final String host = AppConfig.BASE_URL;

    //发送验证码
    public static String getSendMailboxUrl() {
        return host + "/user/getEmailCheckCode.json";
    }

    //邮箱注册
    public static String getEmailRegisterUrl() {
        return host + "/user/emailRegister.json";
    }

    //邮箱登录
    public static String getEmailLoginUrl() {
        return host + "/user/login.json";
    }

    //密碼登录
    public static String getPasswordLoginUrl() {
        return host + "/user/login.json";
    }
}
