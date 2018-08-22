package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;

public class FragmentManageItem extends Fragment implements View.OnClickListener{
    private MainActivity ctx;
    private ListView listView;
    private ManageItemAdapter adapter;
    private Button simpanBtn;
    private TextView totalBarang;
    private ImageButton backButton;
    private MainPresenter presenter;
    private FloatingActionButton addProductFab;

    public FragmentManageItem() {
    }

    public static FragmentManageItem newInstance(MainActivity mainActivity, String title) {
        FragmentManageItem fragment = new FragmentManageItem();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialize(MainActivity activity){
        ctx = activity;
        presenter = ctx.getPresenter();
        adapter = new ManageItemAdapter(ctx);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_product, container, false);

        listView = view.findViewById(R.id.list_cart);
        listView.setAdapter(adapter);
        adapter.loadProducts();

        simpanBtn = view.findViewById(R.id.simpan_btn);
        backButton = view.findViewById(R.id.back_button);
        totalBarang = view.findViewById(R.id.tv_total_items);
        addProductFab = view.findViewById(R.id.fab_add_product);

        totalBarang.setText(adapter.getTotalJenisBarang());

        addProductFab.setOnClickListener(this);
        backButton.setOnClickListener(this);
        simpanBtn.setOnClickListener(this);
        return view;
    }
    public void remove(Product product){
        adapter.removeProduct(product);
    }

    public void removeHash(String productHash){
        adapter.removeHash(productHash);
    }

    public ManageItemAdapter getAdapter() {
        return adapter;
    }

    public void addProduct(Product product) {
        adapter.addProduct(product);
    }

    public void addProductHash(Product product){
        adapter.productToKey(product);
    }

    public ArrayList<Product> getProduct(){
        return  adapter.getProducts();
    }

    @Override
    public void onClick(View v) {
        if(v == backButton){
            ctx.onBackPressed();
        }else if(v == simpanBtn){
            ArrayList<Product> products = adapter.getProducts();
            ArrayList<String> productsHash = adapter.getProductsHash();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                if(ctx.getIndex().containsKey(productsHash.get(i))){
                    ctx.removeHash(productsHash.get(i));
                    ctx.getAllProduct().remove(product);
                }
                ctx.addProduct(product);
                ctx.createHash(product);
            }
            ctx.onBackPressed();
        }
        else if(v == addProductFab){
            ctx.getFragmentInputBarang().fillData(null);
            ctx.changePage(MainActivity.PAGE_INPUT_BARANG);
        }
    }

    public void notifData(){
        adapter.notifyDataSetChanged();
        totalBarang.setText(adapter.getTotalJenisBarang());
    }
}