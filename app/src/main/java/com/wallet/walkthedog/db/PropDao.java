package com.wallet.walkthedog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.walkthedog.db.dao.PropCache;
import com.wallet.walkthedog.db.dao.UserCache;

import java.util.ArrayList;
import java.util.List;

public class PropDao {
    private static String TABLE_NAME = "prop";

    public static void createTable(SQLiteDatabase db) {
        String sql = "create table if not exists "
                + TABLE_NAME + " ("
                + " _id integer primary key,"
                + " uid varchar,"     //道具
                + " name varchar,"     //名称
                + " img varchar,"    //图片
                + " propNumberChain varchar,"    //编号
                + " dogid varchar)";     //狗狗id
        db.execSQL(sql);
    }

    public static void dropTable(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
    }

    public static List<PropCache> query(Context context, String selection, String[] selectionArgs) {
        SQLiteDatabase db = DaoMessageHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, "_id desc");
        List<PropCache> caches = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                String uid = cursor.getString(cursor.getColumnIndex("uid"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String img = cursor.getString(cursor.getColumnIndex("img"));
                String propNumberChain = cursor.getString(cursor.getColumnIndex("propNumberChain"));
                String dogId = cursor.getString(cursor.getColumnIndex("dogid"));

                PropCache cache = new PropCache();
                cache.setUid(uid);
                cache.setName(name);
                cache.setImg(img);
                cache.setPropNumberChain(propNumberChain);
                cache.setDogId(dogId);
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
