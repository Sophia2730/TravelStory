package com.example.sophia.travelstory.Detail;

/**
 * Created by sophia on 2017. 5. 19..
 */

public class DocumentItem {
    String month;//날짜
    String date;
    String content;//시간

    //생성자
    public DocumentItem(String month, String date, String content) {
        this.month = month;
        this.date = date;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
