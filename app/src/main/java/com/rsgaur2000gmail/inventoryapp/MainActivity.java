package com.rsgaur2000gmail.inventoryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<InventoryClass> items = new ArrayList<InventoryClass>();
    TextView textView;
    ListView listView;
    Button button;
    InventoryManager inventoryManager;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.add_item);
        itemsAdapter = new ItemsAdapter(this);
        inventoryManager = new InventoryManager(this);
        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.no_items);
        button = (Button) findViewById(R.id.sale_dir);
        items.clear();
        inventoryManager.read();
        if (items.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            populateMain();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,InputActivity.class);
                if(intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(MainActivity.this,"Some error occured",Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                textView=(TextView)findViewById(R.id.id);
                int pos=(Integer.parseInt(textView.getText().toString()))-1;
                InventoryClass object=items.get(pos);
                Bitmap image=object.getImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent=new Intent(MainActivity.this,DetailView.class);
                intent.putExtra("Pos",pos);
                intent.putExtra("Image", byteArray);
                intent.putExtra("Name",object.getName());
                intent.putExtra("Desc",object.getDesc());
                intent.putExtra("Price",object.getPrice());
                intent.putExtra("Quan",object.getQuan());
                if(intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(MainActivity.this,"Some error occured",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void populateMain() {
        textView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(itemsAdapter);
    }
    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }
}
