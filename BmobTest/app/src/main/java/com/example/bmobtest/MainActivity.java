package com.example.bmobtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Person p2 = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一：默认初始化
        Bmob.initialize(this, "APPID");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

//添加
        Button button_add=(Button)findViewById(R.id.btn_add);
        button_add.setOnClickListener(this);
//查找
        Button button_fid=(Button)findViewById(R.id.btn_fid);
        button_fid.setOnClickListener(this);
//修改
        Button button_cha=(Button)findViewById(R.id.btn_cha);
        button_cha.setOnClickListener(this);
//删除
        Button button_del=(Button)findViewById(R.id.btn_del);
        button_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                p2.setName("lucky");
                p2.setAddress("北京海淀");
                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_fid:
                BmobQuery<Person> bmobQuery=new BmobQuery<Person>();
                bmobQuery.getObject("6e43063294", new QueryListener<Person>() {
                    @Override
                    public void done(Person person, BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_cha:
                p2.setAddress("北京朝阳");
                p2.update("6e43063294", new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"更新成功"+p2.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"更新失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_del:
                p2.setObjectId("e0dee31ed3");
                p2.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"删除成功"+p2.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"删除失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
