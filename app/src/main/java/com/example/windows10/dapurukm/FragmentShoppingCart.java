package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class FragmentShoppingCart extends Fragment implements View.OnClickListener{
    private MainActivity ctx;
    private ListView listView;
    private ShoppingCartAdapter adapter;
    private ImageView emptyImage;
    private LinearLayout llDeskripsi;
    private Button checkOutBtn,contShoppingButton;
    private TextView totalBarang, totalHarga;
    private ImageButton backButton;
    private MainPresenter presenter;

    public FragmentShoppingCart() {
    }

    public static FragmentShoppingCart newInstance(MainActivity mainActivity, String title) {
        FragmentShoppingCart fragment = new FragmentShoppingCart();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialize(MainActivity activity){
        ctx = activity;
        presenter = ctx.getPresenter();
        adapter = new ShoppingCartAdapter(ctx);
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
        backButton = view.findViewById(R.id.back_button);
        contShoppingButton = view.findViewById(R.id.cont_btn);

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

        contShoppingButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        checkOutBtn.setOnClickListener(this);
        return view;
    }
    public void remove(Product product){
        adapter.removeProduct(product);
    }

    public ShoppingCartAdapter getAdapter() {
        return adapter;
    }

    public void notifData(){
        adapter.notifyDataSetChanged();
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
    }

    public int getTotalItems(){
        return  adapter.getTotalJenisBarang();
    }

    public void hideListView(){
        listView.setVisibility(View.INVISIBLE);
    }

    public void addProduct(Product product) {
        adapter.addProduct(product);
    }

    public ArrayList<Product> getProduct(){
        return  adapter.getProducts();
    }

    @Override
    public void onClick(View v) {
        if(v == contShoppingButton){
            ctx.changePage(MainActivity.PAGE_HOME);
        }else if(v == backButton){
            ctx.onBackPressed();
        }else if(v == checkOutBtn){
            if(presenter.isLogin()){
                Log.d("isLogin","login");
                ctx.changePage(MainActivity.PAGE_CHECKOUT);
            }else {
                Log.d("isLogin","not login");
                ctx.changePage(MainActivity.PAGE_INFORMASI_DATA);
            }
        }
    }

    public void changeAmount(Product replaced, Product replacement) {
        adapter.changeAmount(replaced, replacement);
    }

    public void clearCart() {
        adapter.clearCart();
    }
}