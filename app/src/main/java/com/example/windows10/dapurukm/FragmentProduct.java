package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentProduct extends Fragment {
    private MainActivity ctx;
    private ViewPager imagePager;
    private ImageButton backButton,btnAdd,btnMin;
    private TextView prodNama,prodPrice,prodDeskripsi;
    private ImageView star1,star2,star3,star4,star5;
    private EditText totalOrder;
    private TextView sellerName,sellerAddress;

    public FragmentProduct(){}

    public static FragmentProduct newInstance(MainActivity mainActivity, String title) {
        FragmentProduct fragment = new FragmentProduct();
        fragment.setMainActivity(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        Product prod = ctx.getSelected();

        prodNama = view.findViewById(R.id.prod_nama);
        prodPrice = view.findViewById(R.id.prod_price);
        prodDeskripsi = view.findViewById(R.id.prod_deskripsi);
        star1 = view.findViewById(R.id.star1);
        star2 = view.findViewById(R.id.star2);
        star3 = view.findViewById(R.id.star3);
        star4 = view.findViewById(R.id.star4);
        star5 = view.findViewById(R.id.star5);
        sellerName = view.findViewById(R.id.seller_name);
        sellerAddress = view.findViewById(R.id.seller_address);
        imagePager = view.findViewById(R.id.viewPager);
        totalOrder = view.findViewById(R.id.total_buy);
        btnAdd = view.findViewById(R.id.btn_add);
        btnMin = view.findViewById(R.id.btn_min);

        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, prod.getFoto().size() ,
                prod.getFoto().toArray(new Bitmap[prod.getFoto().size()]));
        imagePager.setAdapter(viewPagerAdapter);

        sellerName.setText(prod.getSeller().getName());
        sellerAddress.setText(prod.getSeller().getAddress());
        prodNama.setText(prod.getNama());
        prodPrice.setText(prod.getHarga());
        prodDeskripsi.setText(prod.getProductDetails());
        int rating = prod.getRating();
        Bitmap starOn = ((BitmapDrawable)ctx.getResources().getDrawable(R.drawable.ic_star_on)).getBitmap();
        Bitmap starOff = ((BitmapDrawable)ctx.getResources().getDrawable(R.drawable.ic_star_off)).getBitmap();

        star1.setImageBitmap(starOff);
        star2.setImageBitmap(starOff);
        star3.setImageBitmap(starOff);
        star4.setImageBitmap(starOff);
        star5.setImageBitmap(starOff);
        for(int i=1;i<=rating;i++){
            switch (i){
                case 1:
                    star1.setImageBitmap(starOn);
                    break;
                case 2:
                    star2.setImageBitmap(starOn);
                    break;
                case 3:
                    star3.setImageBitmap(starOn);
                    break;
                case 4:
                    star4.setImageBitmap(starOn);
                    break;
                case 5:
                    star5.setImageBitmap(starOn);
                    break;
            }
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angka = Integer.parseInt(totalOrder.getText().toString());
                totalOrder.setText(angka+1+"");
            }
        });
        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angka = Integer.parseInt(totalOrder.getText().toString());
                if(angka != 1){
                    totalOrder.setText(angka-1+"");
                }
            }
        });

        return view;
    }

    private void setMainActivity(MainActivity ctx){
        this.ctx = ctx;
    }
}