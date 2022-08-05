package com.wallet.walkthedog.untils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;

import tim.com.libnetwork.utils.ConstantLanguages;
import tim.com.libnetwork.utils.SharedPreferencesUtils;

/**
 * @author YanLu
 * @since 17/5/12
 */

public class AppLanguageUtils {

    private static HashMap<String, Locale> mAllLanguages = new HashMap<String, Locale>() {{
        put(ConstantLanguages.ENGLISH, Locale.ENGLISH);
        put(ConstantLanguages.SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(ConstantLanguages.KOREA, Locale.KOREAN);
        put(ConstantLanguages.JAPAN, Locale.JAPAN);
        put(ConstantLanguages.RUSSIA, new Locale("ru"));
        put(ConstantLanguages.INDONESIA, new Locale("in"));
        put(ConstantLanguages.VIET_NAM, new Locale("vi"));
    }};

    private static boolean isSupportLanguage(String language) {
        return mAllLanguages.containsKey(language);
    }

    /**
     * 获取指定语言的locale信息，如果指定语言不存在{@link #mAllLanguages}，返回本机语言
     */
    private static Locale getLocaleByLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mAllLanguages.get(language);
        }
        return Locale.getDefault();
    }

    //获取系统语言
    public static Locale getSystemLocal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Resources.getSystem().getConfiguration().getLocales().get(0);//解决了获取系统默认错误的问题
        } else {
            return Resources.getSystem().getConfiguration().locale;
        }
    }

    public static void applyChange(Context context) {
        String language = SharedPreferencesUtils.getCurrentLanguages(context);
        Locale locale = getSystemLocal();
        if (language.equals(ConstantLanguages.ENGLISH)) {
            locale = Locale.ENGLISH;
        }else if(language.equals(ConstantLanguages.JAPAN)){
            locale = Locale.JAPANESE;
        } else {
            locale = Locale.CHINESE;
        }

//        Resources res = context.getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            conf.setLocale(locale);
//            LocaleList localeList = new LocaleList(locale);
//            LocaleList.setDefault(localeList);
//            conf.setLocales(localeList);
////            conf.locale = locale;
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            conf.setLocale(locale);
//        } else {
//            conf.locale = locale; //设置语言
//        }
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }
}
