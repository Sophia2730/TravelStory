package com.example.sophia.travelstory;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainTravelAdd extends AppCompatActivity implements View.OnClickListener {
    final int REQUEST_FROM = 1001;
    final int REQUEST_TO = 1002;
    Button btn_datefrom, btn_dateto, btn_ok, btn_cancel;
    EditText edt_location;
    int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add);

        edt_location = (EditText) findViewById(R.id.edt_location);
        btn_datefrom = (Button) findViewById(R.id.btn_datefrom);
        btn_datefrom.setPaintFlags(btn_datefrom.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_dateto = (Button) findViewById(R.id.btn_dateto);
        btn_dateto.setPaintFlags(btn_dateto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_ok = (Button) findViewById(R.id.btn_add_ok);
        btn_cancel = (Button) findViewById(R.id.btn_add_cancel);

        btn_datefrom.setOnClickListener(this);
        btn_dateto.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainTravelAdd.this, CalenderAdd.class);
        switch (v.getId()){
            case R.id.btn_datefrom:
                intent.putExtra("select", 1);
                startActivityForResult(intent, REQUEST_FROM);
                break;
            case R.id.btn_dateto:
                intent.putExtra("select", 2);
                startActivityForResult(intent, REQUEST_TO);
                break;
            case R.id.btn_add_ok:
                if (edt_location.getText().toString().length() == 0)
                    Toast.makeText(this, "여행 장소를 작성해주세요!", Toast.LENGTH_SHORT).show();
                else if (btn_datefrom.getText().toString().equals(""))
                    Toast.makeText(this, "출발 날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                else if (btn_dateto.getText().toString().equals(""))
                    Toast.makeText(this, "도착 날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                else {      //여행 추가
                    intent = new Intent(MainTravelAdd.this, MainActivity.class);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    startActivity(intent);
                }
                break;
            case R.id.btn_add_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FROM & resultCode == RESULT_OK){
            year = data.getIntExtra("year", 0);
            month = data.getIntExtra("month", 0);
            day = data.getIntExtra("day", 0);

            Toast.makeText(MainTravelAdd.this, "" + year + "/" + (month + 1) + "/"
                    + day, Toast.LENGTH_LONG).show();
            btn_datefrom.setText(year+"/"+(month+1)+"/"+day+"");
        }
        else if (requestCode == REQUEST_TO & resultCode == RESULT_OK){
            if (year > data.getIntExtra("year", 0))
                Toast.makeText(this, "날짜를 다시 설정해주세요!!", Toast.LENGTH_SHORT).show();
            else if (month > data.getIntExtra("month", 0))
                Toast.makeText(this, "날짜를 다시 설정해주세요!!", Toast.LENGTH_SHORT).show();
            else if (day > data.getIntExtra("day", 0))
                Toast.makeText(this, "날짜를 다시 설정해주세요!!", Toast.LENGTH_SHORT).show();
            else {
                year = data.getIntExtra("year", 0);
                month = data.getIntExtra("month", 0);
                day = data.getIntExtra("day", 0);

                Toast.makeText(MainTravelAdd.this, "" + year + "/" + (month + 1) + "/"
                        + day, Toast.LENGTH_LONG).show();
                btn_dateto.setText(year + "/" + (month + 1) + "/" + day + "");
            }
        }

    }
}
