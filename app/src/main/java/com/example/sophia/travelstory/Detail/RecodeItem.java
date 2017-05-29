package com.example.sophia.travelstory.Detail;

/**
 * Created by sophia on 2017. 5. 19..
 */

public class RecodeItem {
    int resId;//이미지 리소스 id
    String name;//이름
    String date;//날짜
    String time;//시간

    //생성자
    public RecodeItem(int resId, String name, String date, String time ) {
        this.resId = resId;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
