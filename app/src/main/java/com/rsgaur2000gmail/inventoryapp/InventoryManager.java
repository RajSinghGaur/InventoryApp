package com.rsgaur2000gmail.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.rsgaur2000gmail.inventoryapp.MainActivity.items;

public class InventoryManager extends SQLiteOpenHelper {
    public InventoryManager(Context context){
        super(context,DbData.DbName,null,DbData.DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable="CREATE TABLE "+DbData.TableData.TableName+" ( "+DbData.TableData.ColName+" VARCHAR,"
                +DbData.TableData.ColQuantity+" INT(3),"+DbData.TableData.ColPrice+" REAL,"
                +DbData.TableData.ColDesc+" VARCHAR,"+ DbData.TableData.ColImages+" BLOB)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVer,int newVer){
        db.execSQL("DROP TABLE IF EXISTS "+DbData.TableData.TableName);
        onCreate(db);
    }

    public void insert(String name,int quan,float price,String desc,byte[] image){

        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("Habit name in DB Helper " + name);

        ContentValues values = new ContentValues();
        values.put(DbData.TableData.ColName, name);
        values.put(DbData.TableData.ColQuantity, quan);
        values.put(DbData.TableData.ColPrice, price);
        values.put(DbData.TableData.ColDesc, desc);
        values.put(DbData.TableData.ColImages, image);

        db.insertWithOnConflict(DbData.TableData.TableName,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();

    }

    public void deleteItem(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DbData.TableData.TableName,DbData.TableData.ColName+"=?",new String[]{items.get(id).getName()});
    }

    public void updateQuan(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DbData.TableData.ColQuantity,items.get(id).getQuan());
        db.update(DbData.TableData.TableName,values,DbData.TableData.ColName+"=?",new String[]{items.get(id).getName()});
    }

    private Cursor getHabitsCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DbData.TableData.TableName,null,null,null,null,null,null);
    }
    public void read(){
        try {
            Cursor c = getHabitsCursor();
            readCursor(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readCursor(Cursor c){
        int nameIndex = c.getColumnIndex(DbData.TableData.ColName);
        int priceIndex=c.getColumnIndex(DbData.TableData.ColPrice);
        int quanIndex=c.getColumnIndex(DbData.TableData.ColQuantity);
        int descIndex=c.getColumnIndex(DbData.TableData.ColDesc);
        int imageIndex=c.getColumnIndex(DbData.TableData.ColImages);
        if (c != null && c.moveToFirst()){
            do {
                items.add(new InventoryClass(c.getString(nameIndex),c.getString(descIndex),c.getInt(quanIndex),c.getFloat(priceIndex), BitmapFactory.decodeByteArray(c.getBlob(imageIndex),0,c.getBlob(imageIndex).length,new BitmapFactory.Options())));
            } while(c.moveToNext());
        }

        c.close();
    }
}
