package com.example.nofinal.dosql.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by YUZHENG on 2021/9/1
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "test.db";
    public static final int DB_VERSION = 3;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.e("TTT", "onCreate");
        Log.e("TTT", "DBDao.SQL_CREATE_TABLE is " + DBDao.SQL_CREATE_TABLE);
        int initDBVersion = 1;
        database.execSQL(DBDao.SQL_CREATE_TABLE);
        onUpgrade(database, initDBVersion, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.e("TTT", "onUpgrade" + oldVersion + "," + newVersion);
    }

}

