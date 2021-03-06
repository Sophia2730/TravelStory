package com.example.sophia.travelstory.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sophia.travelstory.R;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "USER.db", null, 1);

        final EditText edt_email = (EditText) findViewById(R.id.edt_email);
        final EditText edt_passwd = (EditText) findViewById(R.id.edt_passwd);
        final EditText edt_passwd2 = (EditText) findViewById(R.id.edt_passwd2);
        final EditText edt_name = (EditText) findViewById(R.id.edt_name);
        final EditText edt_id = (EditText) findViewById(R.id.edt_id);

        // 추가버튼 클릭시 DB에 데이터 추가
        ImageButton btn_register = (ImageButton) findViewById(R.id.btn_registeruser);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String passwd = edt_passwd.getText().toString();
                String passwdCheck = edt_passwd2.getText().toString();
                String email = edt_email.getText().toString();
                String id = edt_id.getText().toString();

                if (name.equals("") | passwd.equals("") | email.equals("") | id.equals(""))
                    Toast.makeText(RegisterActivity.this, "모든 정보를 입력해주세요!", Toast.LENGTH_SHORT).show();
                else if (!passwd.equals(passwdCheck))
                    Toast.makeText(RegisterActivity.this, "비밀번호가 서로 맞지 않습니다!!", Toast.LENGTH_SHORT).show();
                else {
                    try{                  //id는 primary key이기 때문에 오류 발생시 이미 가입된 아이디라는 것을 알려줌
                        dbHelper.insert(name, id, passwd, email);
                        Toast.makeText(RegisterActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "이미 가입된 아이디입니다!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
