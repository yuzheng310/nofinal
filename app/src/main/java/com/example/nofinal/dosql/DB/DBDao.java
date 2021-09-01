package com.example.nofinal.dosql.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nofinal.Activity.MyApplication;
import com.example.nofinal.bean.CollectionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YUZHENG on 2021/9/1
 */

public class DBDao {

    public static final String TABLE_NAME = "collectionNews";//表名
    private static final String TITLE = "story_title";//id自增长
    private static final String IMAG = "story_imag";//姓名
    private static final String URL = "story_url";//年龄
    private static final String DATE = "date";//性别
    private static final String ID = "id";//id自增长
    private DBHelper dbHelper;
    //创建表结构
    public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement,"+
            TITLE + " text," +
            IMAG + " Nvarchar(255)," +
            URL + " Nvarchar(255)," +
            DATE + " text" +
            ")";


    private DBDao() {
        dbHelper = new DBHelper(MyApplication.getContext());
    }

    public static DBDao getInstance() {
        return InnerDB.instance;
    }

    private static class InnerDB {
        private static DBDao instance = new DBDao();
    }

    /**
     * 数据库插入数据
     *
     * @param bean 实体类
     * @param <T>  T
     */
    public synchronized <T> void insert(T bean) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            if (bean != null && bean instanceof CollectionBean) {
                CollectionBean collectionBean = (CollectionBean) bean;
                ContentValues cv = new ContentValues();
                cv.put(TITLE, collectionBean.getStory_title());
                cv.put(IMAG, collectionBean.getStory_imag());
                cv.put(URL, collectionBean.getStory_url());
                cv.put(DATE, collectionBean.getDate());
                db.insert(TABLE_NAME, null, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 删除表中所有的数据
     */
    public synchronized void clearAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from " + TABLE_NAME;

        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 查询数据
     *
     * @return List
     */
    public synchronized <T> List<T> query() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<T> list = new ArrayList<>();
        String querySql = "select * from " + TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(querySql, null);
            while (cursor.moveToNext()) {
                CollectionBean collectionBean = new CollectionBean();
                collectionBean.setStory_title(cursor.getString(cursor.getColumnIndex(TITLE)));
                collectionBean.setStory_imag(cursor.getString(cursor.getColumnIndex(IMAG)));
                collectionBean.setStory_url(cursor.getString(cursor.getColumnIndex(URL)));
                collectionBean.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                list.add((T) collectionBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

}

