package com.example.su.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.bmob.v3.Bmob;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "bb54bce7bd52e042e7de4ce0df7d1ecb");
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        Button btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intent= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                Intent intent1= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent1);
        }
    }
}
