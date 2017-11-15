package com.rsgaur2000gmail.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

import static android.R.attr.button;
import static android.R.attr.start;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.rsgaur2000gmail.inventoryapp.MainActivity.items;


public class ItemsAdapter extends ArrayAdapter<InventoryClass> {
    public ItemsAdapter(Context context) {
        super(context, 0, items);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.detail_layout, parent, false);
        }
        final InventoryClass currentItem = getItem(position);
        TextView textView = (TextView) listItemView.findViewById(R.id.name_dir);
        textView.setText(currentItem.getName());
        textView = (TextView) listItemView.findViewById(R.id.price_dir);
        textView.setText(String.format(Locale.getDefault(),"%.2f",currentItem.getPrice()));
        textView = (TextView) listItemView.findViewById(R.id.quantity_dir);
        textView.setText(String.format(Locale.getDefault(),"%d",currentItem.getQuan()));
        textView=(TextView)listItemView.findViewById(R.id.id);
        textView.setText(String.format(Locale.getDefault(),"%d",items.indexOf(currentItem)+1));
        Button button=(Button)listItemView.findViewById(R.id.sale_dir);
        final View listItemView1=listItemView;
        button.setOnClickListener(new View.OnClickListener() {
            InventoryManager inventoryManager=new InventoryManager(getContext());
            int id=items.indexOf(currentItem);
            @Override
            public void onClick(View v) {
                items.get(id).minus();
                inventoryManager.updateQuan(id);
                TextView textView = (TextView) listItemView1.findViewById(R.id.quantity_dir);
                textView.setText(String.format(Locale.getDefault(),"%d",currentItem.getQuan()));
            }
        });
        return listItemView;
    }
}
