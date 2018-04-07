package com.example.windows10.e_commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private ArrayList<Item> items;

    public GridAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(Menu.getInstance()).inflate(R.layout.item_grid_view, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.updateItem((Item)getItem(position));
        return convertView;
    }

    public void setItems(ArrayList<Item> items){
        this.items = items;
    }

    private class ViewHolder{
        protected ImageView ivItem;
        protected TextView tvItemJudul;
        protected TextView tvItemDesc;
        public ViewHolder(View v){
            ivItem = v.findViewById(R.id.iv_item);
            tvItemJudul = v.findViewById(R.id.tv_item_judul);
            tvItemDesc = v.findViewById(R.id.tv_item_keterangan);
        }
        public void updateItem(Item item){
            ivItem.setImageBitmap(item.getIcon());
            tvItemJudul.setText(item.getNama());
            tvItemDesc.setText(item.getDeskripsi());
        }
    }
}
