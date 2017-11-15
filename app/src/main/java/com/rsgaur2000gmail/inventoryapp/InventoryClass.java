package com.rsgaur2000gmail.inventoryapp;

import android.graphics.Bitmap;

public class InventoryClass {
    private String Name;
    private String Desc;
    private int Quan;
    private float Price;
    private Bitmap Image;

    public InventoryClass(String name,String desc,int quan,float price,Bitmap image){
        Name=name;
        Desc=desc;
        Quan=quan;
        Price=price;
        Image=image;
    }

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public float getPrice() {
        return Price;
    }

    public int getQuan() {
        return Quan;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void add(){Quan++;}
    public void minus(){
        if(Quan>0)
            Quan--;
        else
            Quan=0;
    }

    @Override
    public String toString(){
        return "Name: "+Name+"\nPrice: "+Price+"\nDescription: "+Desc;
    }
}
