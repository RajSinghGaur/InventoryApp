package com.rsgaur2000gmail.inventoryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

public class DetailView extends AppCompatActivity {
    InventoryManager inventoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        byte[] byteArray = getIntent().getByteArrayExtra("Image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        final InventoryClass object = new InventoryClass(getIntent().getStringExtra("Name"), getIntent().getStringExtra("Desc"), getIntent().getIntExtra("Quan", 0), getIntent().getFloatExtra("Price", (float) 0.0), bmp);
        final int pos = getIntent().getIntExtra("Pos", 0);
        final Intent intent = new Intent(DetailView.this, MainActivity.class);
        inventoryManager = new InventoryManager(this);
        populate(object);
        setContentView(R.layout.full_item_detail);
        Button button = (Button) findViewById(R.id.add);
        ImageView imageView = (ImageView) findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.add();
                inventoryManager.updateQuan(pos);
            }
        });
        button = (Button) findViewById(R.id.minus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.minus();
                inventoryManager.updateQuan(pos);
            }
        });

        button = (Button) findViewById(R.id.del_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryManager.deleteItem(pos);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.order);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "abc@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    public void populate(InventoryClass obj) {
        ImageView itemImage = (ImageView) findViewById(R.id.image_det);
        itemImage.setImageBitmap(obj.getImage());
        TextView textView = (TextView) findViewById(R.id.details);
        textView.setText(obj.getDesc());
        textView = (TextView) findViewById(R.id.quan_dit);
        textView.setText(String.format(Locale.getDefault(), "%d", obj.getQuan()));
    }

}
