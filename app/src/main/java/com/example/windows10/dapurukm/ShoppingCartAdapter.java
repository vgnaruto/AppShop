package com.example.windows10.dapurukm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingCartAdapter extends BaseAdapter {
    private MainActivity ui;
    private ArrayList<Product> products = new ArrayList<>();

    public ShoppingCartAdapter(MainActivity ui) {
        this.ui = ui;
    }

    public ShoppingCartAdapter(MainActivity ui, ArrayList<Product> prod) {
        this.ui = ui;
        products = prod;
    }

    public String getTotalItems(){
        int total = 0;
        for(Product p : products){
            total += p.getTotal();
        }
        return "("+total+")";
    }
    public int getTotalJenisBarang(){
        return products.size();
    }

    public String getTotalHarga(){
        int total = 0;
        for(Product p : products){
            int harga = Integer.parseInt(p.getHarga().substring(3).replaceAll("\\.","").trim());
            total += harga * p.getTotal();
        }
        return new MainPresenter().formatRupiah(total);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ui).inflate(R.layout.cart_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.updateView(getItem(position));
        return convertView;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }
    private class ViewHolder {
        protected TextView namaPerus, judulProduct, hargaProduct;
        protected EditText totalOrder;
        protected ImageButton btnMin, btnAdd, btnDelete;
        protected ImageView gambarProduct;

        public ViewHolder(View v) {
            namaPerus = v.findViewById(R.id.nama_perus);
            judulProduct = v.findViewById(R.id.nama_prod);
            hargaProduct = v.findViewById(R.id.harga_total);
            totalOrder = v.findViewById(R.id.total_buy);
            btnMin = v.findViewById(R.id.btn_min);
            btnAdd = v.findViewById(R.id.btn_add);
            btnDelete = v.findViewById(R.id.trash_btn);
            gambarProduct = v.findViewById(R.id.gambar_prod);
        }

        public void updateView(Product prod) {
            namaPerus.setText(prod.getSeller().getName());
            judulProduct.setText(prod.getNama());
            int harga = Integer.parseInt(prod.getHarga().substring(3).replaceAll("\\.", "").trim());
            int total = prod.getTotal();
            hargaProduct.setText(new MainPresenter().formatRupiah(harga * total));
            totalOrder.setText(prod.getTotal()+"");
            gambarProduct.setImageBitmap(prod.getFoto().get(0));
        }
    }
}
