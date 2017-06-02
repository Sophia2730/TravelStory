package com.example.sophia.travelstory.Detail;

/**
 * Created by sophia on 2017. 5. 19..
 */

public class DocumentItem {
    String month;//날짜
    int date;
    String content;//시간

    //생성자
    public DocumentItem(String month, int date, String content) {
        this.month = month;
        this.date = date;
        this.content = content;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
