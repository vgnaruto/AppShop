package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

public class FragmentProduct extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private ViewPager imagePager;
    private ImageButton backButton, btnAdd, btnMin;
    private TextView prodNama, prodPrice, prodWeight;
    private ImageView[] bintang = new ImageView[5];
    private TextView totalOrder;
    private TextView sellerName, sellerAddress;
    private ExpandableTextView prodDeskripsi;
    private TextView priceTotal, priceEach;
    private Button checkOutBtn;
    private MainPresenter presenter;
    private static Product selected;
    private CircleIndicator indicator;

    public FragmentProduct() {
    }

    public static FragmentProduct newInstance(MainActivity mainActivity, String title) {
        FragmentProduct fragment = new FragmentProduct();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setSelected(Product prod) {
        selected = prod;
    }

    public void initialize(MainActivity activity){
        ctx = activity;
        presenter = ctx.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        prodNama = view.findViewById(R.id.prod_nama);
        prodPrice = view.findViewById(R.id.prod_price);
        prodWeight = view.findViewById(R.id.prod_weight);
        prodDeskripsi = (ExpandableTextView) view.findViewById(R.id.expand_tv).findViewById(R.id.expanded_text_view);
        for (int i = 0; i < bintang.length; i++) {
            switch (i) {
                case 0:
                    bintang[i] = view.findViewById(R.id.star1);
                    break;
                case 1:
                    bintang[i] = view.findViewById(R.id.star2);
                    break;
                case 2:
                    bintang[i] = view.findViewById(R.id.star3);
                    break;
                case 3:
                    bintang[i] = view.findViewById(R.id.star4);
                    break;
                case 4:
                    bintang[i] = view.findViewById(R.id.star5);
                    break;
            }
        }
        sellerName = view.findViewById(R.id.seller_name);
        sellerAddress = view.findViewById(R.id.seller_address);
        imagePager = view.findViewById(R.id.viewPager);
        totalOrder = view.findViewById(R.id.total_buy);
        btnAdd = view.findViewById(R.id.btn_add);
        btnMin = view.findViewById(R.id.btn_min);
        priceTotal = view.findViewById(R.id.tv_price_total);
        priceEach = view.findViewById(R.id.tv_price_each);
        checkOutBtn = view.findViewById(R.id.cart_button);
        backButton = view.findViewById(R.id.back_button);
        indicator = view.findViewById(R.id.indicator);

        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, selected.getFoto().size(),
                selected.getFoto().toArray(new Bitmap[selected.getFoto().size()]));
        indicator.setViewPager(imagePager);
        imagePager.setAdapter(viewPagerAdapter);
        prodWeight.setText(selected.getWeight()+" gr");
        sellerName.setText(selected.getSeller().getName());
        sellerAddress.setText(selected.getSeller().getAddress());
        prodNama.setText(selected.getNama());
        prodPrice.setText(selected.getHarga());

        priceTotal.setText(selected.getHarga());
        priceEach.setText("@ " + selected.getHarga());
        prodDeskripsi.setText(selected.getProductDetails());
        int rating = selected.getRating();
        Bitmap starOn = ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.ic_star_on)).getBitmap();
        Bitmap starOff = ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.ic_star_off)).getBitmap();

        for (ImageView b : bintang) {
            b.setImageBitmap(starOff);
        }
        for (int i = 0; i < rating; i++) {
            bintang[i].setImageBitmap(starOn);
        }
        backButton.setOnClickListener(this);
        checkOutBtn.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnMin.setOnClickListener(this);

        return view;
    }

    public void update(){
        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, selected.getFoto().size(),
                selected.getFoto().toArray(new Bitmap[selected.getFoto().size()]));
        indicator.setViewPager(imagePager);
        imagePager.setAdapter(viewPagerAdapter);
        prodWeight.setText(selected.getWeight()+" gr");
        sellerName.setText(selected.getSeller().getName());
        sellerAddress.setText(selected.getSeller().getAddress());
        prodNama.setText(selected.getNama());
        prodPrice.setText(selected.getHarga());

        priceTotal.setText(selected.getHarga());
        priceEach.setText("@ " + selected.getHarga());
        prodDeskripsi.setText(selected.getProductDetails());
        int rating = selected.getRating();
        Bitmap starOn = ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.ic_star_on)).getBitmap();
        Bitmap starOff = ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.ic_star_off)).getBitmap();

        for (ImageView b : bintang) {
            b.setImageBitmap(starOff);
        }
        for (int i = 0; i < rating; i++) {
            bintang[i].setImageBitmap(starOn);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        totalOrder.setText("1");
    }

    @Override
    public void onClick(View v) {
        if(v == backButton){
            ctx.onBackPressed();
        }else if(v == checkOutBtn){
            if(selected.isInCart()){
                Product replacedSelected = new Product(selected.getFoto(), selected.getHarga(),
                        selected.getNama(), selected.getProductDetails(), selected.getKategori(), selected.getSeller(),
                        selected.getRating(),Integer.parseInt(selected.getWeight()));
                replacedSelected.setInCart(selected.isInCart());
                replacedSelected.setTotal(selected.getTotal());
                Log.d("fp12345", "masuk sini");
                selected.setTotal(selected.getTotal()+Integer.parseInt(totalOrder.getText().toString()));
                ctx.changeSavedProductAmount(replacedSelected, selected);
            }else {
                selected.setTotal(Integer.parseInt(totalOrder.getText().toString()));
                ctx.sendToCart(selected);
                selected.setInCart(true);
            }
            ctx.changePage(MainActivity.PAGE_SHOPPING_CART);
        }else if(v == btnAdd){
            int angka = Integer.parseInt(totalOrder.getText().toString());
            totalOrder.setText(angka + 1 + "");
            int harga = Integer.parseInt(selected.getHarga().substring(3).replaceAll("\\.", ""));
            int total = Integer.parseInt(totalOrder.getText().toString());
            priceTotal.setText(presenter.formatRupiah(harga * total));
        }else if(v == btnMin){
            int angka = Integer.parseInt(totalOrder.getText().toString());
            if (angka != 1) {
                totalOrder.setText(angka - 1 + "");
            }
            int harga = Integer.parseInt(selected.getHarga().substring(3).replaceAll("\\.", ""));
            int total = Integer.parseInt(totalOrder.getText().toString());
            priceTotal.setText(presenter.formatRupiah(harga * total));
        }
    }
}