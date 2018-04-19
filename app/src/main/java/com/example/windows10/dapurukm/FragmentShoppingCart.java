package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentShoppingCart extends Fragment {
    private MainActivity ctx;
    private ListView listView;
    private ShoppingCartAdapter adapter;
    private ImageView emptyImage;
    private LinearLayout llDeskripsi, checkOutBtn;
    private TextView totalBarang, totalHarga;

    public FragmentShoppingCart() {
    }

    public static FragmentShoppingCart newInstance(MainActivity mainActivity, String title) {
        FragmentShoppingCart fragment = new FragmentShoppingCart();
        fragment.setMainActivity(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        listView = view.findViewById(R.id.list_cart);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);

        emptyImage = view.findViewById(R.id.empty_image);
        llDeskripsi = view.findViewById(R.id.ll_keterangan);
        checkOutBtn = view.findViewById(R.id.checkout_btn);
        totalBarang = view.findViewById(R.id.tv_total_items);
        totalHarga = view.findViewById(R.id.tv_total_price);

        if(adapter.isEmpty()){
            emptyImage.setVisibility(View.VISIBLE);
            llDeskripsi.setVisibility(View.GONE);
            checkOutBtn.setVisibility(View.GONE);
        }else{
            llDeskripsi.setVisibility(View.VISIBLE);
            checkOutBtn.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.GONE);
        }

        totalBarang.setText(adapter.getTotalItems());
        totalHarga.setText(adapter.getTotalHarga());
        return view;
    }

    public void hideListView(){
        listView.setVisibility(View.INVISIBLE);
    }

    private void setMainActivity(MainActivity ctx) {
        this.ctx = ctx;
        adapter = new ShoppingCartAdapter(ctx);
    }

    public void addProduct(Product product) {
        adapter.addProduct(product);
    }
}