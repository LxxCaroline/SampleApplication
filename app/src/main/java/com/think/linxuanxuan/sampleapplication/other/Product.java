package com.think.linxuanxuan.sampleapplication.other;


public class Product {

    public int id;
    public String name;
    public float price;

    @Override
    public String toString() {
        return "id:" + id + ",name:" + name + ",price:" + price;
    }
}
