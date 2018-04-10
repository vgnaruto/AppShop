package com.example.windows10.appshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 4/7/2018.
 */

public class ProductGridAdapter extends BaseAdapter {
    private ArrayList<Product> product;
    private MainActivity mainActivity;

    public ProductGridAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.product = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return product.size();
    }

    @Override
    public Object getItem(int position) {
        return product.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mainActivity).inflate(R.layout.product_grid_list, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.updateItem((Product)getItem(position));
        return convertView;
    }

    public void setItems(ArrayList<Product> items){
        this.product = items;
    }

    private class ViewHolder{
        protected ImageView ivproduct;
        protected TextView tvproductName;
        public ViewHolder(View v){
            ivproduct = v.findViewById(R.id.iv_item);
            tvproductName = v.findViewById(R.id.tv_product_name);
        }
        public void updateItem(Product item){
            ivproduct.setImageBitmap(item.getImage());
            tvproductName.setText(item.getName());
        }
    }
}
