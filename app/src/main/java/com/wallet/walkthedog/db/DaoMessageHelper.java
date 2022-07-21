package com.wallet.walkthedog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ${description}
 *
 * @author weiqiliu
 * @version 1.0 2020/5/21
 */
public class DaoMessageHelper extends SQLiteOpenHelper {
    public static final int SCHEMA_VERSION = 2;
    public static final String DB_NAME = "message.db";

    private DaoMessageHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
    }

    public synchronized static DaoMessageHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DaoMessageHelper(context);
        }
        return instance;
    }

    private static DaoMessageHelper instance = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAllTables(db);
        onCreate(db);
    }


    public static void createAllTables(SQLiteDatabase db) {
        UserDao.createTable(db);
        PropDao.createTable(db);
    }

    public static void dropAllTables(SQLiteDatabase db) {
        UserDao.dropTable(db);
        PropDao.dropTable(db);
    }
}
