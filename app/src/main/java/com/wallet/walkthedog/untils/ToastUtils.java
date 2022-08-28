package com.wallet.walkthedog.untils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.wallet.walkthedog.app.RootApplication;


public class ToastUtils {

    private ToastUtils() {
        //empty
    }

    public static void shortToast(Context context, int resource) {
        show(context,context.getString(resource), Toast.LENGTH_SHORT);
    }

    public static void shortToast(String message) {
        show(message, Toast.LENGTH_LONG);
    }

    public static void shortToast(@StringRes int resource) {
        show(RootApplication.getInstance().getString(resource), Toast.LENGTH_SHORT);
    }

    public static void longToast(String message) {
        show(message, Toast.LENGTH_LONG);
    }

    public static void longToast(@StringRes int resource) {
        show(RootApplication.getInstance().getString(resource), Toast.LENGTH_LONG);
    }

    private static void show(Context context,String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    private static void show(String message, int length) {
        Toast.makeText(RootApplication.getInstance(), message, length).show();
    }
}