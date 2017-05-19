package com.example.sophia.travelstory;

/**
 * Created by sophia on 2017. 5. 19..
 */

public class TravelItem {
    int resId;      //이미지 리소스 id
    String location;//여행장소
    String period;  //여행기간

    //생성자
    public TravelItem(int resId, String location, String period) {
        this.resId = resId;
        this.location = location;
        this.period = period;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
