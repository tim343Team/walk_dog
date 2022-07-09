package com.wallet.walkthedog.app;


import com.wallet.walkthedog.config.AppConfig;

public class UrlFactory {
    public static final String host = AppConfig.BASE_URL;

    //获取一份助记词
    public static String getSysMnemonicWordsUrl() {
        return host + "/wallet/getSysMnemonicWords";
    }
}
