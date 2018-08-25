package com.example.windows10.dapurukm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartAdapter extends BaseAdapter {
    private MainActivity ui;
    private ArrayList<Product> products = new ArrayList<>();
    private ViewHolder vh;
    private MainPresenter presenter;

    private ArrayList<String> savedProducts = new ArrayList<>();

    public ShoppingCartAdapter(MainActivity ui) {
        this.ui = ui;
        presenter = ui.getPresenter();
        HashMap<String,String> hm = new HashMap<>();
    }

//    public ShoppingCartAdapter(MainActivity ui, ArrayList<Product> prod) {
//        this.ui = ui;
//        products = prod;
//        for (int i = 0; i < products.size(); i++) {
//            savedProducts.add(productToKey(products.get(i)));
//        }
//    }

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
        long total = 0;
        for(Product p : products){
            long harga = Long.parseLong(p.getHarga().substring(3).replaceAll("\\.","").trim());
            total += harga * p.getTotal();
        }
        return presenter.formatRupiah(""+total);
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
        if (convertView == null) {
            convertView = LayoutInflater.from(ui).inflate(R.layout.cart_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Product currentProduct = getItem(position);
        vh.updateView(currentProduct);
        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.get(position).setInCart(false);
                products.remove(position);
                savedProducts.remove(position);
                ui.notifyShoppingCart();
            }
        });
        vh.btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angka = products.get(position).getTotal();
                if(angka == 1) {
                    products.get(position).setInCart(false);
                    products.remove(position);
                    savedProducts.remove(position);
                }else{
                    products.get(position).setTotal(angka - 1);
                    String toBeSaved = savedProducts.get(position).substring(
                            0, savedProducts.get(position).lastIndexOf('-') + 1);
                    savedProducts.remove(position);
                    savedProducts.add(position, toBeSaved + (angka - 1));
                    vh.updateView(products.get(position));
                }
                ui.notifyShoppingCart();
            }
        });
        vh.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angka = products.get(position).getTotal();
                if(angka >= products.get(position).getStock())angka = products.get(position).getStock() - 1;
                products.get(position).setTotal(angka+1);
                String toBeSaved = savedProducts.get(position).substring(
                        0, savedProducts.get(position).lastIndexOf('-') + 1);
                savedProducts.remove(position);
                savedProducts.add(position, toBeSaved + (angka + 1));
                vh.updateView(products.get(position));
                ui.notifyShoppingCart();
            }
        });
        return convertView;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setSavedProducts(ArrayList<String> savedProducts) {
        this.savedProducts = savedProducts;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<String> getSavedProducts() {
        return savedProducts;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void addProduct(Product product) {
        this.products.add(product);
        this.savedProducts.add(productToKey(product));
    }

    public void removeProduct(Product product){
        this.products.remove(product);
        this.savedProducts.remove(productToKey(product));
    }

    public String productToKey(Product product){
        return product.getSeller().getName() + "-" + product.getNama() + "-" + product.getTotal();
    }

    public void changeAmount(Product replaced, Product replacement) {
        this.savedProducts.add(this.savedProducts.indexOf(productToKey(replaced)), productToKey(replacement));
        this.savedProducts.remove(productToKey(replaced));
    }

    public Product checkInCart(Product item) {
        String compared = productToKey(item).substring(0, productToKey(item).lastIndexOf('-'));
        for (int i = 0; i < savedProducts.size(); i++) {
            if(compared.equals(this.savedProducts.get(i).
                    substring(0, this.savedProducts.get(i).lastIndexOf('-')))) {
                item = this.products.get(i);
                break;
            }
        }
        return item;
    }

    public void clearCart() {
        for(Product p : products){
            p.setInCart(false);
        }
        products = new ArrayList<>();
        savedProducts = new ArrayList<>();
        ui.notifyShoppingCart();
    }

    private class ViewHolder {
        protected TextView namaPerus, judulProduct, hargaProduct;
        protected TextView totalOrder;
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
//            int harga = Integer.parseInt(prod.getHarga().substring(3).replaceAll("\\.", "").trim());
//            int total = prod.getTotal();
            BigInteger harga = new BigInteger(prod.getHarga().substring(3).replaceAll("\\.", "").trim());
            harga = harga.multiply(BigInteger.valueOf(prod.getTotal()));
//            hargaProduct.setText(presenter.formatRupiah(""+(harga * total)));
            hargaProduct.setText(presenter.formatRupiah(harga.toString()));
            totalOrder.setText(prod.getTotal()+"");
            gambarProduct.setImageBitmap(prod.getFoto().get(0));
        }
    }
}
