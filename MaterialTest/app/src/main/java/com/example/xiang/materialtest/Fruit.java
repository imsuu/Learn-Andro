package com.example.xiang.materialtest;

/**
 * Created by XIANG! on 2018/3/15.
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }

    public int getImageId(){
        return  imageId;
    }
}
