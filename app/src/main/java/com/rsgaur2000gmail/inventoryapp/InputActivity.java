package com.rsgaur2000gmail.inventoryapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.rsgaur2000gmail.inventoryapp.MainActivity.items;

public class InputActivity extends AppCompatActivity {
    EditText editText;
    InventoryManager inventoryManager;
    private static int IMG_RESULT = 1;
    String ImageDecode;
    Button LoadImage;
    Bitmap img;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);

        final Intent intent = new Intent(InputActivity.this, MainActivity.class);
        final Toast toast = Toast.makeText(this, "Enter Info", Toast.LENGTH_SHORT);
        inventoryManager = new InventoryManager(this);
        final boolean flag[] = {true};
        Button button = (Button) findViewById(R.id.submit_input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag[0] = addData();
                if (flag[0])
                    toast.show();
                else
                    startActivity(intent);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.back_input);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        LoadImage = (Button) findViewById(R.id.image_input);
        LoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, IMG_RESULT);
            }
        });
    }

    public boolean addData() {
        String name, desc;
        int quan;
        float price;
        byte[] image;
        BitmapFactory.Options options = new BitmapFactory.Options();
        editText = (EditText) findViewById(R.id.name_input);
        if (editText.getText().toString().isEmpty()) return true;
        name = editText.getText().toString();
        editText = (EditText) findViewById(R.id.quantity_input);
        if (editText.getText().toString().isEmpty()) return true;
        quan = Integer.parseInt(editText.getText().toString());
        editText = (EditText) findViewById(R.id.price_input);
        if (editText.getText().toString().isEmpty()) return true;
        price = Float.parseFloat(editText.getText().toString());
        editText = (EditText) findViewById(R.id.desc_input);
        if (editText.getText().toString().isEmpty()) return true;
        desc = editText.getText().toString();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        } catch (NullPointerException e) {
            stream.reset();
        }
        image = stream.toByteArray();
        if (image.length == 0) return true;
        items.add(new InventoryClass(name, desc, quan, price, img));
        inventoryManager.insert(name, quan, price, desc, image);
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == IMG_RESULT && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                Uri mUri = resultData.getData();
                InputStream input=null;
                try {
                    input = this.getContentResolver().openInputStream(mUri);

                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(input, null, bmOptions);
                    input.close();
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;
                    int scaleFactor = Math.min(photoW / 24, photoH / 24);
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    input = this.getContentResolver().openInputStream(mUri);
                    img = BitmapFactory.decodeStream(input, null, bmOptions);
                    input.close();
                } catch (IOException e) {
                    img=null;
                    Toast.makeText(this,"Failed to load image",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}