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
        db.execSQL("CREATE TABLE RECODE (_id INTEGER PRIMARY KEY, name TEXT, date TEXT, time TEXT);");
        db.execSQL("CREATE TABLE DOCUMENT (_id INTEGER PRIMARY KEY, date TEXT, content VARCHAR(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertTravel(String location, String dateFrom, String dateTo) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO TRAVEL VALUES(null, '" + location + "', '" + dateFrom + "', '" + dateTo + "');");
        db.close();
    }

    public void deleteTravel(String where) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TRAVEL WHERE location = '" + where + "';");
        db.close();
    }
}
