package com.example.sophia.travelstory.Detail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sophia on 2017. 5. 31..
 */

public class DetailDBHelper extends SQLiteOpenHelper {
    public DetailDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TRAVEL (_id INTEGER PRIMARY KEY AUTOINCREMENT, location TEXT, datefrom TEXT, dateto TEXT);");
        db.execSQL("CREATE TABLE DOCUMENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, location TEXT, month TEXT, date INTEGER, content VARCHAR(100));");
        db.execSQL("CREATE TABLE RECODE (_id INTEGER PRIMARY KEY AUTOINCREMENT, location TEXT, path TEXT, name TEXT, time TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //여행 추가하는 메소드
    public void insertTravel(String location, String dateFrom, String dateTo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TRAVEL VALUES(null, '" + location + "', '" + dateFrom + "', '" + dateTo + "');");
    }

    //여행 삭제하는 메소드
    public void deleteTravel(String where) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TRAVEL WHERE location = '" + where + "';");
    }

    //메모 추가하는 메소드
    public void insertDocument(String location, String month, String date, String content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DOCUMENT VALUES(null, '" + location + "', '" + month + "', '" + date + "', '" + content + "');");
        db.close();
    }

    //메모 삭제하는 메소드
    public void deleteDocument(String location, String month, String date, String content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DOCUMENT WHERE location = '" + location + "'AND month = '" + month + "'AND date = '" + date
                + "'AND content = '" + content + "';");
        db.close();
    }

    //메모 겡신하는 메소드
    public void updateDocument(int _id, String month, String date, String content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DOCUMENT SET month = '" + month + "', date = '" + date + "', content = '" + content +
                "' WHERE _id = " + _id + ";");
        db.close();
    }

    //녹음 저장하는 메소드
    public void insertRecode(String location, String path, String name, String time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RECODE VALUES(null, '" + location + "', '" + path + "', '" + name + "', '" + time + "');");
        db.close();
    }

    //녹음 삭제하는 메소드
    public void deleteRecode(String location, String path, String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM RECODE WHERE location = '" + location + "'AND path = '" + path + "'AND name = '" + name + "';");
        db.close();
    }
}
