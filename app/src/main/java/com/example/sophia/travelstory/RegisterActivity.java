package com.example.sophia.travelstory;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "USER.db", null, 1);

        final TextView result = (TextView) findViewById(R.id.result);
        final EditText edt_email = (EditText) findViewById(R.id.edt_email);
        final EditText edt_passwd = (EditText) findViewById(R.id.edt_passwd);

        final ContentValues values = new ContentValues();

        // DB에 데이터 추가
        Button btn_register = (Button) findViewById(R.id.btn_registeruser);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String passwd = edt_passwd.getText().toString();

//                values.put("email", email);
//                values.put("passwd", passwd);
//                dbHelper.insert("USER", "NULL", values);

                dbHelper.insert(email, passwd);
                result.setText(dbHelper.getResult());
            }
        });

        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.delete();
//                result.setText(dbHelper.getResult());
            }
        });
    }
}
