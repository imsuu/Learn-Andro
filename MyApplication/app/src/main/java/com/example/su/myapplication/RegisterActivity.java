package com.example.su.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.su.myapplication.model.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView headView;
    private EditText nickName;
    private EditText loginEmail;
    private EditText password;
    private EditText psw_again;
    private String nickNameText;
    private String loginEmailText;
    private String passwordText;
    private String psw_againText;
    private Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
// findView
        headView = (CircleImageView) findViewById(R.id.img_2);
        nickName = (EditText) findViewById(R.id.nickName);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.password);
        psw_again = (EditText) findViewById(R.id.psw_again);
        signUp = (Button) findViewById(R.id.signUp1);

        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp1:
                nickNameText = nickName.getText().toString();
                loginEmailText = loginEmail.getText().toString();
                passwordText = password.getText().toString();
                psw_againText = psw_again.getText().toString();
                if (nickNameText.equals("") || loginEmailText.equals("") || passwordText.equals("") || psw_againText.equals(""))
                    Toast.makeText(getApplication(), "请填写完整", Toast.LENGTH_SHORT).show();
                else if (!passwordText.equals(psw_againText))
                    Toast.makeText(getApplication(), "请注意：您的两次密码填写不一致", Toast.LENGTH_SHORT).show();
                else
                    go_signup();
        }
    }

    public void go_signup() {
        BmobUser bu = new BmobUser();
//        bu.setUsername("sendi");
//        bu.setPassword("123456");
//        bu.setEmail("sendi@163.com");

        bu.setUsername(nickNameText);
        bu.setPassword(passwordText);
        bu.setEmail(loginEmailText);
//        Toast.makeText(RegisterActivity.this,nickNameText,Toast.LENGTH_SHORT).show();
//        Toast.makeText(RegisterActivity.this,passwordText,Toast.LENGTH_SHORT).show();
//        Toast.makeText(RegisterActivity.this,loginEmailText,Toast.LENGTH_SHORT).show();

//注意：不能用save方法进行注册
        bu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功:" + s.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败:" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
