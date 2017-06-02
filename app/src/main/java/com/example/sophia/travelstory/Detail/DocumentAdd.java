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

public class DocumentAdd extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String[] item;
    ImageButton btn_add;
    String month, curLocation;
    DetailDBHelper dbHelper;
    DocumentFragment document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_add);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        item = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        dbHelper = new DetailDBHelper(getApplicationContext(), "DOCUMENT.db", null, 1);
        final EditText edt_date, edt_content;
        edt_date = (EditText) findViewById(R.id.edt_docuDate);
        edt_content = (EditText) findViewById(R.id.edt_docuContent);

        Intent intent = getIntent();
        curLocation = intent.getStringExtra("curLocation");
        document = new DocumentFragment();

        btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edt_date.getText().toString()) > 31)
                Toast.makeText(DocumentAdd.this, "디비에 넣는 값\n" + curLocation + "/" + month + "/" + edt_date.getText().toString() + "/" + edt_content.getText().toString(), Toast.LENGTH_SHORT).show();
                dbHelper.insertDocument(curLocation, month, Integer.parseInt(edt_date.getText().toString()), edt_content.getText().toString());

                setResult(200);
                finish();
            }
        });
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
