package com.example.windows10.appshop;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductFragment extends Fragment implements View.OnClickListener{
    private LinearLayout addCartButton;
    private TextView cartTv;
    private MainActivity ui;
    private ImageButton shoppingCartButton;
    private ImageButton wishlistButton;

    public ProductFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container, false);
        this.addCartButton = view.findViewById(R.id.button_cart);
        this.cartTv = view.findViewById(R.id.tv_cart);
        this.shoppingCartButton = view.findViewById(R.id.cart_button);
        this.wishlistButton = view.findViewById(R.id.wishlist_button);
        Toolbar customToolbar = view.findViewById(R.id.custom_toolbar);
        ui.setSupportActionBar(customToolbar);
        ui.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shoppingCartButton.setOnClickListener(this);
        addCartButton.setOnClickListener(this);
        wishlistButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == addCartButton){
            String currentText = cartTv.getText().toString();
            if(currentText.contains("ADD")){
                addCartButton.setBackgroundColor(getResources().getColor(R.color.gray_old));
                cartTv.setText("REMOVE FROM CART");
            }else if(currentText.contains("REMOVE")) {
                addCartButton.setBackgroundColor(getResources().getColor(R.color.orange));
                cartTv.setText("ADD TO CART");
            }
        }
        if(v == shoppingCartButton){

        }
    }

    public static ProductFragment newInstance(MainActivity ui,String title){
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        fragment.setUI(ui);
        return fragment;
    }
    public void setUI(MainActivity ui){
        this.ui = ui;
    }
}
