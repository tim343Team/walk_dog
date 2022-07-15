package com.wallet.walkthedog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.walkthedog.db.dao.UserCache;

import java.util.ArrayList;
import java.util.List;

/**
 * ${description}
 *
 * @author weiqiliu
 * @version 1.0 2020/5/21
 */
public class UserDao {
    private static String TABLE_NAME = "user";

    public static void createTable(SQLiteDatabase db) {
        String sql = "create table if not exists "
                + TABLE_NAME + " ("
                + " _id integer primary key,"
                + " username varchar,"     //用户名
                + " avatar varchar,"    //用户头像
                + " email varchar,"    //邮箱
                + " uid varchar,"    //自己的用户id
                + " fuid varchar,"    //好友的用户id,也是环信的注册号码
                + " mobile varchar)";    //电话号码也是环信的注册号码
        db.execSQL(sql);
    }

    public static void dropTable(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
    }

    public static List<UserCache> query(Context context, String selection, String[] selectionArgs) {
        SQLiteDatabase db = DaoMessageHelper.getInstance(context).getReadableDatabase();
//        String selection = "uid=?";
//        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, "_id desc");
        List<UserCache> caches = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String uid = cursor.getString(cursor.getColumnIndex("uid"));
                String fuid = cursor.getString(cursor.getColumnIndex("fuid"));
                String mobile = cursor.getString(cursor.getColumnIndex("mobile"));

                UserCache cache = new UserCache();
                cache.setUsername(username);
                cache.setAvatar(avatar);
                cache.setEmail(email);
                cache.setUid(uid);
                cache.setFuid(fuid);
                cache.setMobile(mobile);
                caches.add(cache);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return caches;
    }

    public static void delete(Context context, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = DaoMessageHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void insert(Context context, ContentValues contentValues) {
        SQLiteDatabase db = DaoMessageHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        try {
            db.insert(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void update(Context context, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = DaoMessageHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        try {
            db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
