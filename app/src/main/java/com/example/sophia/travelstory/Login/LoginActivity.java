package com.example.sophia.travelstory.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sophia.travelstory.MainActivity;
import com.example.sophia.travelstory.R;

//맨 처음의 로그인 화면
public class LoginActivity extends Activity implements View.OnClickListener {
    ImageButton btn_register, btn_login;
    EditText edt_id, edt_passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = (ImageButton) findViewById(R.id.btn_register);
        btn_login = (ImageButton) findViewById(R.id.btn_login);
        edt_id = (EditText) findViewById(R.id.edt_id);
        edt_passwd = (EditText) findViewById(R.id.edt_passwd);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "USER.db", null, 1);
        Boolean islogin;    //로그인 성공 여부를 담는 변수
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_register:     //등록 버튼 눌럿을 경우
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:        //로그인 버튼 눌럿을 경우
                islogin = dbHelper.accessLogin(edt_id.getText().toString(), edt_passwd.getText().toString());   //로그인 가능한지 값을 받아옴
                if (islogin) {          //로그인 성공시
                    Toast.makeText(this, "로그인에 성공했습니다!!", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else                  //로그인 실패시
                    Toast.makeText(this, "로그인 정보를 다시 확인해주세요!!", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
