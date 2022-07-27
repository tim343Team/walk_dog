package com.wallet.walkthedog.untils;

import java.util.Locale;

public class Utils {


    /**
     *时间转换成 HH:mm
     * @param time 秒
     * @return HH：mm
     */
    public static String getTime(long time){
        long hours =  time / 3600;
        long mintes = time / 60 % 60;
        return String.format(Locale.getDefault(),"%02d:%02d",hours,mintes);
    }

    public static String getFormat(double arg,String format){
        return String.format(Locale.getDefault(),format,arg);
    }

    public static String kmFormat(double arg){
        return getFormat(arg,"%.2dkm");
    }
}
