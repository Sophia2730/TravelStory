package com.example.sophia.travelstory.Detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sophia.travelstory.R;

public class DocumentDetail extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String[] item;
    ImageButton btn_edit, btn_delete;
    String month, content, curLocation, selectItem[];
    int dbindex;
    DetailDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_detail);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        item = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        //spinner 설
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dbHelper = new DetailDBHelper(getApplicationContext(), "DOCUMENT.db", null, 1);
        final EditText edt_date, edt_content;
        edt_date = (EditText) findViewById(R.id.edt_docuDate);
        edt_content = (EditText) findViewById(R.id.edt_docuContent);

        Intent intent = getIntent();
        dbindex = intent.getIntExtra("dbindex", -1);
        curLocation = intent.getStringExtra("curLocation");
        selectItem = intent.getStringArrayExtra("selectItem");

        //intent로 받아온 값을 불러온다
        int i = initSpinner(selectItem[1]);
        spinner.setSelection(i);
        edt_date.setText(selectItem[2]);
        edt_content.setText(selectItem[3]);

        btn_delete = (ImageButton) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteDocument(selectItem[0], selectItem[1], selectItem[2], selectItem[3]);
                setResult(201);
                finish();
            }
        });

        btn_edit = (ImageButton) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_date.getText().toString().equals("") || Integer.parseInt(edt_date.getText().toString()) > 31)
                    Toast.makeText(DocumentDetail.this, "날짜를 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                else if (edt_content.getText().toString().equals("") == true)
                    Toast.makeText(DocumentDetail.this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
                else {
                    dbHelper.updateDocument(dbindex, month.substring(0, 3), edt_date.getText().toString(), edt_content.getText().toString());
                    setResult(202);
                    finish();
                }
            }
        });
    }

    public int initSpinner(String month) {      //헤딩 month값 불러와서 spinner에 불러올 때 사용
        for (int i = 0; i < 12; i++) {
            if (month.equals(item[i].substring(0, 3))) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        month = item[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        month = item[0];
    }
}
