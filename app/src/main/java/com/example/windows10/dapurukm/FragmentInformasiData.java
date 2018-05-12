package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

public class FragmentInformasiData extends Fragment{
    private MainActivity ctx;
    private MaterialEditText etNama,etAlamat,etKodePos,etNomorTelepon,etProvinsi,etKabupaten,etKecamatan;
    private ImageButton backButton;

    public FragmentInformasiData(){}
    public static FragmentInformasiData newInstance(MainActivity mainActivity, String title) {
        FragmentInformasiData fragment = new FragmentInformasiData();
        fragment.setMainActivity(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    private void setMainActivity(MainActivity ui){
        this.ctx = ui;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informasi_pembeli, container, false);

        backButton = view.findViewById(R.id.back_button);
        etProvinsi = view.findViewById(R.id.etProvinsi);
        etKabupaten = view.findViewById(R.id.etKabupaten);
        etKecamatan = view.findViewById(R.id.etKecamatan);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.onBackPressed();
            }
        });
        return view;
    }
}
/*

public class FragmentProduct extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private ViewPager imagePager;
    private ImageButton backButton, btnAdd, btnMin;
    private TextView prodNama, prodPrice;
    private ImageView[] bintang = new ImageView[5];
    private TextView totalOrder;
    private TextView sellerName, sellerAddress;
    private ExpandableTextView prodDeskripsi;
    private TextView priceTotal, priceEach;
    private Button checkOutBtn;
    private MainPresenter presenter;
    private static Product selected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        presenter = new MainPresenter();

        prodNama = view.findViewById(R.id.prod_nama);
        prodPrice = view.findViewById(R.id.prod_price);
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

        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, selected.getFoto().size(),
                selected.getFoto().toArray(new Bitmap[selected.getFoto().size()]));
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(imagePager);
        imagePager.setAdapter(viewPagerAdapter);

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

    @Override
    public void onResume() {
        super.onResume();
        totalOrder.setText("1");
    }

    private void setMainActivity(MainActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onClick(View v) {
        if(v == backButton){
            ctx.onBackPressed();
        }else if(v == checkOutBtn){
            if(selected.isInCart()){
                selected.setTotal(selected.getTotal()+Integer.parseInt(totalOrder.getText().toString()));
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

* */