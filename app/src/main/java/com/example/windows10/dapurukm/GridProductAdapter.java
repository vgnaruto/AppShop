package com.example.windows10.dapurukm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridProductAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private MainActivity ui;

    public GridProductAdapter(ArrayList<Product> products, MainActivity ui){
        this.products = products;
        this.ui = ui;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ui).inflate(R.layout.main_grid_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.updateView(getItem(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.setProduct(getItem(position));
                ui.changePage(MainActivity.PAGE_PRODUCT);
            }
        });
        return convertView;
    }
    private class ViewHolder{
        private ImageView foto;
        private TextView namaProduct, hargaProduct, sellerProduct;
        public ViewHolder(View v){
            foto = v.findViewById(R.id.prod_gambar);
            namaProduct = v.findViewById(R.id.prod_judul);
            hargaProduct = v.findViewById(R.id.prod_harga);
            sellerProduct = v.findViewById(R.id.prod_namaperus);
        }
        public void updateView(Product product){
            foto.setImageBitmap(product.getFoto().get(0));
            namaProduct.setText(product.getNama());
            hargaProduct.setText(product.getHarga());
            sellerProduct.setText(product.getSeller().getName());
        }
    }
}
