package com.example.sophia.travelstory.Login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sophia on 2017. 5. 20..
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER (name TEXT, id TEXT PRIMARY KEY, passwd TEXT, email TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //USER 테이블에 값을 저장하는 메소드
    public void insert(String name, String id, String passwd, String email) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER VALUES('" + name + "', '" + id + "', '" + passwd + "', '" + email + "');");
        db.close();
    }

    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM USER;");
        db.close();
    }

    //디비에 저장된 아이디와 비밀번호가 맞는지 확인해서 boolean 값을 돌려주는 메소드
    public boolean accessLogin(String id, String passwd) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
        String curId, curPwd;
        while (cursor.moveToNext()) {
            curId = cursor.getString(1);
            curPwd = cursor.getString(2);
            if (curId.equals(id) & curPwd.equals(passwd))
                return true;
        }
        return false;
    }
}
