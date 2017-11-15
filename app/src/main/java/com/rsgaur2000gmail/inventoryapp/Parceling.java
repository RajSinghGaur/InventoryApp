package com.rsgaur2000gmail.inventoryapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SUMITRAGAHLOT45 on 08-11-2017.
 */

public class Parceling implements Parcelable {

    public Parceling(){}

    private int quan;
    private float price;
    private String name,desc;
    private Bitmap image;

    public void writeObject(InventoryClass obj){
        quan=obj.getQuan();
        price=obj.getPrice();
        name=obj.getName();
        desc=obj.getDesc();
        image=obj.getImage();
    }

    public InventoryClass readObject(){
        return new InventoryClass(name,desc,quan,price,image);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel,int flags){
        parcel.writeInt(quan);
        parcel.writeFloat(price);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeByteArray(new byte[]{});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InventoryClass createFromParcel(Parcel in) {
            return null;
        }

        public InventoryClass[] newArray(int size) {
            return null;
        }
    };
}

