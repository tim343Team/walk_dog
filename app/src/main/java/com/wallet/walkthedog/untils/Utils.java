package com.wallet.walkthedog.untils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String getFormat(String format,Object... arg){
        try {
           return String.format(Locale.getDefault(),format,arg);
        }catch (Exception e){
            return "";
        }
    }

    /**
     *
     * @param arg double
     * @return eg 2.1km
     */
    public static String getKmFormat(double arg){
        return getFormat("%.2fkm",arg);
    }

    /**
     *
     * @param arg double
     * @return 1.11
     */
    public static String getScale2(double arg){
        return getFormat("%.2f",arg);
    }


    public static String timeFormat(long date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.getDefault());
        return sdf.format(new Date(date));
    }

}
