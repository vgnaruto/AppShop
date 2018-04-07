package com.example.windows10.e_commerce;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    private static Menu instances;
    private GridAdapter gridAdapter;
    private GridView gridView;

    private ArrayList<Item> itemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        gridView = findViewById(R.id.grid_view);
        gridAdapter = new GridAdapter();

        itemMenu = new ArrayList<>();

        for(int i=0;i<7;i++){
            Item item = new Item(BitmapFactory.decodeResource(getResources(),
                    R.drawable.icon),"Judul_"+i,"Deskripsi_"+i);
            itemMenu.add(item);
        }
        gridAdapter.setItems(itemMenu);
        gridView.setAdapter(gridAdapter);

        instances = this;
    }
    public static Menu getInstance(){
        return instances;
    }
}
