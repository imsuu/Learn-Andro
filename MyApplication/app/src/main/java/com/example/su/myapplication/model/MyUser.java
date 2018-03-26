package com.example.su.myapplication.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/3/22.
 */

public class MyUser extends BmobUser {
    //头像
    BmobFile Photo;
    public BmobFile getPhoto() {
        return Photo;
    }

    public void setPhoto(BmobFile photo) {
        Photo = photo;
    }
}