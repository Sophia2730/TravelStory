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
        db.execSQL("CREATE TABLE DOCUMENT (location TEXT, month TEXT, date INTEGER, content VARCHAR(100));");
        db.execSQL("CREATE TABLE RECODE (location TEXT, path TEXT, name TEXT, time TEXT);");
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

    public void insertDocument(String location, String month, int date, String content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DOCUMENT VALUES('" + location + "', '" + month + "', '" + date + "', '" + content + "');");
        db.close();
    }

    public void deleteDocument(String location, String month, String date, String content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DOCUMENT WHERE location = '" + location + "'AND month = '" + month + "'AND date = '" + date
                + "'AND content = '" + content + "';");
        db.close();
    }

    public void insertRecode(String location, String path, String name,String time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RECODE VALUES('" + location + "', '" + path + "', '" + name + "', '" + time + "');");
        db.close();
    }

    public void deleteRecode(String location, String path, String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM RECODE WHERE location = '" + location + "'AND path = '" + path + "'AND name = '" + name + "';");
        db.close();
    }

//    public void updateDocument(String location, String Pmonth, String Pdate, String Pcontent, String month, int date, String content){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("UPDATE DOCUMENT SET month = '" + month + "', date = '" + date + "', content = '" + content +
//                "' WHERE location = '" + location  + "'AND month = '" + Pmonth + "'AND date = '" + Pdate
//                + "'AND content = '" + Pcontent +"';");
//        db.close();
//    }


}
