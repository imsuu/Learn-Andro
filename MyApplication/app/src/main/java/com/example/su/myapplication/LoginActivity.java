package com.example.su.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.su.myapplication.model.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText loginEmail;
    private EditText password;
    private String loginEmailText;
    private String passwordText;
    private TextView signUp;
    private Button signIn;
    private TextView forgetPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // findView
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.password);
        signUp = (TextView) findViewById(R.id.signUp);
        forgetPsw = (TextView) findViewById(R.id.forgetPsw);
        signIn = (Button) findViewById(R.id.signIn);

        // 点击事件
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp:
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
                break;
            case R.id.signIn:
                go_signin();
                break;
        }
    }

    public void go_signin(){
        loginEmailText=loginEmail.getText().toString();
        passwordText=password.getText().toString();
        BmobUser bu2 = new BmobUser();
        bu2.setEmail(loginEmailText);
        bu2.setPassword(passwordText);
//        bu2.login(new SaveListener<BmobUser>() {
//            @Override
//            public void done(BmobUser bmobUser, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(LoginActivity.this, "登录成功:" , Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LoginActivity.this, "登录失败:" + e, Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        bu2.loginByAccount(loginEmailText, passwordText, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    Log.d("LoginActivity","success");
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this,"失败",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
