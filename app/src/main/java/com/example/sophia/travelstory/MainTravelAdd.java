package com.example.sophia.travelstory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sophia.travelstory.Detail.DetailDBHelper;

public class MainTravelAdd extends Activity implements View.OnClickListener {
    final int REQUEST_FROM = 1001;
    final int REQUEST_TO = 1002;
    Button btn_datefrom, btn_dateto;
    ImageButton btn_ok, btn_cancel;
    EditText edt_location;
    int year, month, day;
    DetailDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add);

        dbHelper = new DetailDBHelper(getApplicationContext(), "TRAVEL.db", null, 1);

        edt_location = (EditText) findViewById(R.id.edt_location);
        btn_datefrom = (Button) findViewById(R.id.btn_datefrom);
        btn_datefrom.setPaintFlags(btn_datefrom.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_dateto = (Button) findViewById(R.id.btn_dateto);
        btn_dateto.setPaintFlags(btn_dateto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_ok = (ImageButton) findViewById(R.id.btn_add_ok);
        btn_cancel = (ImageButton) findViewById(R.id.btn_add_cancel);

        btn_datefrom.setOnClickListener(this);
        btn_dateto.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainTravelAdd.this, CalenderAdd.class);
        switch (v.getId()){
            case R.id.btn_datefrom:     //출발날짜 버튼 클릭시 select에 1을 넘겨줌
                intent.putExtra("select", 1);
                startActivityForResult(intent, REQUEST_FROM);
                break;
            case R.id.btn_dateto:       //도착날짜 버튼 클릭시 select에 2를 넘겨줌
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
                else {      //예외에 해당하지 않는다면 여행 추가
                    intent = new Intent(MainTravelAdd.this, MainActivity.class);
                    intent.putExtra("location", edt_location.getText().toString());
                    intent.putExtra("datefrom", btn_datefrom.getText().toString());
                    intent.putExtra("dateto", btn_dateto.getText().toString());

                    //db에 입력된 값 저장
                    dbHelper.insertTravel(edt_location.getText().toString(), btn_datefrom.getText().toString(), btn_dateto.getText().toString());
                    setResult(201, intent);
                    finish();
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
        boolean overday =false;
        if (requestCode == REQUEST_FROM & resultCode == RESULT_OK){         //출발날짜를 눌럿던 경우 값을 받아옴
            year = data.getIntExtra("year", 0);
            month = data.getIntExtra("month", 0);
            day = data.getIntExtra("day", 0);

            Toast.makeText(MainTravelAdd.this, "" + year + "/" + (month + 1) + "/"
                    + day, Toast.LENGTH_LONG).show();
            btn_datefrom.setText(year+"/"+(month+1)+"/"+day+"");
        }
        else if (requestCode == REQUEST_TO & resultCode == RESULT_OK){      //도착날짜를 눌럿던 경우 값을 받아
            //도착날짜가 출발날짜보다 늦지 않도록 설정
            if (year < data.getIntExtra("year", 0))
                overday = true;
            else if (year == data.getIntExtra("year", 0)) {
                if (month < data.getIntExtra("month", 0))
                    overday = true;
                else if (month == data.getIntExtra("month", 0)) {
                    if (day > data.getIntExtra("day", 0))
                        Toast.makeText(this, "날짜를 다시 설정해주세요!!", Toast.LENGTH_SHORT).show();
                    else
                        overday = true;
                }
                else if (month > data.getIntExtra("month", 0))
                    Toast.makeText(this, "날짜를 다시 설정해주세요!!", Toast.LENGTH_SHORT).show();
            }
            else if (year > data.getIntExtra("year", 0))
                Toast.makeText(this, "날짜를 다시 설정해주세요!!", Toast.LENGTH_SHORT).show();

            if (overday){       //예외에 해당하지 않는다면 값을 넘겨줌
                year = data.getIntExtra("year", 0);
                month = data.getIntExtra("month", 0);
                day = data.getIntExtra("day", 0);

                btn_dateto.setText(year + "/" + (month + 1) + "/" + day + "");
            }
        }
    }
}
