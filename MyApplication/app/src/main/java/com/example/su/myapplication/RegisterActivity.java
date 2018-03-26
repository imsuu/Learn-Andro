package com.example.su.myapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.su.myapplication.model.MyUser;
import com.nanchen.compresshelper.CompressHelper;
import com.nanchen.compresshelper.FileUtil;


import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;


import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView headView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private File oldFile;
    private File newFile;

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

        // 全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

// findView
        headView = (CircleImageView) findViewById(R.id.img_head);
        nickName = (EditText) findViewById(R.id.nickName);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.password);
        psw_again = (EditText) findViewById(R.id.psw_again);
        signUp = (Button) findViewById(R.id.signUp1);

        signUp.setOnClickListener(this);
        headView.setOnClickListener(this);
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
                break;
            case R.id.img_head:
                Toast.makeText(this,"asadasda",Toast.LENGTH_SHORT).show();
                takePhoto();
                break;
        }
    }


    public void takePhoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!");
                return;
            }
            try {
                oldFile = FileUtil.getTempFile(this, data.getData());
                clearImage();
                compress();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
    }

    public void compress() {
        // 默认的压缩方法，多张图片只需要直接加入循环即可
        newFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(oldFile);
        Uri uri= Uri.parse(newFile.getAbsolutePath());
        headView.setImageURI(uri);
       // headView.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
        showError(String.format("Size : %s", getReadableFileSize(newFile.length())));
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    //Toast
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    //清除
    private void clearImage() {
        headView.setImageDrawable(null);
        headView.setBackgroundColor(getRandomColor());
    }

    //获得
    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    //注册
    public void go_signup() {
        final BmobFile bmobFile = new BmobFile(new File(newFile.getAbsolutePath()));

        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    showError("上传文件成功:" );
                    MyUser bu = new MyUser();
                    //数据插入
                    bu.setheadPortrait(bmobFile);
                    bu.setUsername(nickNameText);
                    bu.setPassword(passwordText);
                    bu.setEmail(loginEmailText);
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
                }else{
                    showError("上传文件失败：" + e.getMessage());
                }

            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }
}
