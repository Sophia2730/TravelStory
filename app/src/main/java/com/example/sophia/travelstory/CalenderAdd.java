package com.example.sophia.travelstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class CalenderAdd extends AppCompatActivity {
    CalendarView calendarView;
    Intent calIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_add);

        calIntent = new Intent(CalenderAdd.this, MainTravelAdd.class);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {
                calIntent.putExtra("year", year);
                calIntent.putExtra("month", month);
                calIntent.putExtra("day", dayOfMonth);
                setResult(RESULT_OK, calIntent);

                finish();
            }
        });
    }
}
