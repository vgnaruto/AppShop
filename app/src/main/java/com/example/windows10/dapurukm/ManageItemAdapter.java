package com.example.windows10.dapurukm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageItemAdapter extends BaseAdapter {
    private MainActivity ui;
    private ArrayList<Product> products;
    private ArrayList<String> productsHash = new ArrayList<>();
    private ViewHolder vh;
    private MainPresenter presenter;

    public ManageItemAdapter(MainActivity ui) {
        this.ui = ui;
        presenter = ui.getPresenter();
        products = new ArrayList<>();
    }

    public void loadProducts(){
        ArrayList<Product> allProducts = ui.getAllProduct();
        for (int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getSeller().equals(presenter.getUser().getSeller())){
                if(!products.contains(allProducts.get(i)))products.add(allProducts.get(i));
            }
        }
    }

    public String getTotalJenisBarang(){
        return "(" + products.size() + ")";
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
            convertView = LayoutInflater.from(ui).inflate(R.layout.manage_product_item, parent, false);
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
                //to be removed
                ArrayList<Product> shoppingCartProducts = ui.getFragmentShoppingCart().getProduct();
                for (int i = 0; i < shoppingCartProducts.size(); i++) {
                    if (shoppingCartProducts.get(i).getSeller().equals(products.get(position).getSeller())) {
                        if (shoppingCartProducts.get(i).getNama().equals(products.get(position).getNama())) {
                            ui.getFragmentShoppingCart().getProduct().remove(i);
                            break;
                        }
                    }
                }
                ui.getAllProduct().remove(products.get(position));
                ui.removeHash(productsHash.get(position));
                productsHash.remove(position);
                products.remove(position);
                ui.notifyManageItem();
                ui.notifyHomeFragment();

            }
        });
        vh.btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angka = products.get(position).getTotal();
                if(angka > 0){
                    products.get(position).setStock(products.get(position).getStock() - 1);
                    vh.updateView(products.get(position));
                    ui.notifyManageItem();
                }
            }
        });
        vh.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.get(position).setStock(products.get(position).getStock() + 1);
                vh.updateView(products.get(position));
                ui.notifyManageItem();
            }
        });
        vh.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.getFragmentInputBarang().fillData(products.get(position));
                ui.changePage(MainActivity.PAGE_INPUT_BARANG);
            }
        });
        return convertView;
    }

    public ArrayList<Product> getProducts() {
        return products;
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

    public void productToKey(Product product){
        this.productsHash.add(product.getSeller().getName() + "-" + product.getNama());
    }

    public void removeHash(String hash){
        if(productsHash.contains(hash))productsHash.remove(hash);
    }

    public ArrayList<String> getProductsHash() {
        return productsHash;
    }

    private class ViewHolder {
        protected TextView namaPerus, judulProduct, hargaProduct;
        protected TextView totalOrder;
        protected ImageButton btnMin, btnAdd, btnDelete;
        protected ImageView gambarProduct;
        protected RelativeLayout rlContainer;

        public ViewHolder(View v) {
            namaPerus = v.findViewById(R.id.nama_perus);
            judulProduct = v.findViewById(R.id.nama_prod);
            hargaProduct = v.findViewById(R.id.harga);
            totalOrder = v.findViewById(R.id.total_prod);
            btnMin = v.findViewById(R.id.btn_min);
            btnAdd = v.findViewById(R.id.btn_add);
            btnDelete = v.findViewById(R.id.trash_btn);
            gambarProduct = v.findViewById(R.id.gambar_prod);
            rlContainer = v.findViewById(R.id.rl_item);
        }

        public void updateView(Product prod) {
            namaPerus.setText(prod.getSeller().getName());
            judulProduct.setText(prod.getNama());
//            int harga = Integer.parseInt(prod.getHarga().substring(3).replaceAll("\\.", "").trim());
            hargaProduct.setText(presenter.formatRupiah(prod.getHarga().substring(3).replaceAll("\\.", "").trim()));
            totalOrder.setText(prod.getStock()+"");
            gambarProduct.setImageBitmap(prod.getFoto().get(0));
        }
    }
}
