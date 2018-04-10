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

public class GridAdapter extends BaseAdapter {
    private ArrayList<Category> category;
    private MainActivity mainActivity;

    public GridAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.category = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int position) {
        return category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mainActivity).inflate(R.layout.category_grid_list, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.updateItem((Category)getItem(position));
        return convertView;
    }

    public void setItems(ArrayList<Category> items){
        this.category = items;
    }

    private class ViewHolder{
        protected ImageView ivCategory;
        protected TextView tvCategoryName;
        protected TextView tvCategoryDescription;
        public ViewHolder(View v){
            ivCategory = v.findViewById(R.id.iv_item);
            tvCategoryName = v.findViewById(R.id.tv_category_name);
            tvCategoryDescription = v.findViewById(R.id.tv_category_description);
        }
        public void updateItem(Category item){
            ivCategory.setImageBitmap(item.getImage());
            tvCategoryName.setText(item.getName());
            tvCategoryDescription.setText(item.getDescription());
        }
    }
}
