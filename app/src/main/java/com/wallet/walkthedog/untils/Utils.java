package com.wallet.walkthedog.untils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import tim.com.libnetwork.utils.WonderfulPermissionUtils;

public class Utils {


    /**
     * 时间转换成 HH:mm
     *
     * @param time 秒
     * @return HH：mm
     */
    public static String getTime(long time) {
        long hours = time / 3600;
        long mintes = time / 60 % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", hours, mintes);
    }

    /**
     * 时间转换成 HH:mm:ss
     *
     * @param time 秒
     * @return HH：mm
     */
    public static String getTimeSecond(long time) {
        long mintes = time / 60 % 60;
        long s = time % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", mintes, s);
    }

    public static String getFormat(String format, Object... arg) {
        try {
            return String.format(Locale.getDefault(), format, arg);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param arg double
     * @return eg 2.1km
     */
    public static String getKmFormat(double arg) {
        return getFormat("%.2fkm", arg);
    }

    /**
     * @param arg double
     * @return 1.11
     */
    public static String getScale2(double arg) {
        return getFormat("%.2f", arg);
    }


    public static String timeFormat(long date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(date));
    }

    //保存二维码到相册
    public static boolean saveImage29(Context context, Bitmap toBitmap, String name) {
        //TODO 注意在调用前需要动态注册sdcard权限哟
        try {
            save(toBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    private static void save(Bitmap saveBitmap) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ATC/";
        Calendar now = new GregorianCalendar();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String fileName = simpleDate.format(now.getTime());
        File folderFile = new File(dir);
        if (!folderFile.exists()) {
            // mkdir() 不会创建多级目录，所以导致后面的代码报错 提示文件或目录不存在
            // folderFile.mkdir();
            // mkdirs() 则会创建多级目录
            folderFile.mkdirs();
        }
        File file = new File(dir + fileName + ".jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建文件输出流对象用来向文件中写入数据
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //将bitmap存储为jpg格式的图片
        saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        //刷新文件流
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(file);
    }


    public static void copyText(Context context, String str) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText(null, str);
        cm.setPrimaryClip(myClip);
    }


}
