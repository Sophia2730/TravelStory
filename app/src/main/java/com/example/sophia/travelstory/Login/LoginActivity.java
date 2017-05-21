package com.example.sophia.travelstory.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sophia.travelstory.MainActivity;
import com.example.sophia.travelstory.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_register, btn_login;
    EditText edt_id, edt_passwd;
    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_id = (EditText) findViewById(R.id.edt_id);
        edt_passwd = (EditText) findViewById(R.id.edt_passwd);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "USER.db", null, 1);
        Boolean islogin;
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
//                islogin = dbHelper.accessLogin(edt_id.getText().toString(), edt_passwd.getText().toString());
//                if (islogin) {
//                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                } else
//                    Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();

                break;
        }

    }


}
