package com.example.windows10.appshop;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductFragment extends Fragment implements View.OnClickListener{
    private LinearLayout cartButton;
    private TextView cartTv;

    public ProductFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container, false);
        this.cartButton = view.findViewById(R.id.button_cart);
        this.cartTv = view.findViewById(R.id.tv_cart);

        cartButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == cartButton){
            String currentText = cartTv.getText().toString();
            if(currentText.contains("ADD")){
                cartButton.setBackgroundColor(getResources().getColor(R.color.gray_old));
                cartTv.setText("REMOVE FROM CART");
            }else if(currentText.contains("REMOVE")) {
                cartButton.setBackgroundColor(getResources().getColor(R.color.orange));
                cartTv.setText("ADD TO CART");
            }
        }
    }
}
