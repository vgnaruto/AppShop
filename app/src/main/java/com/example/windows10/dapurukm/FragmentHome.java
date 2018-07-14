package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.Rect;
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
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class FragmentHome extends Fragment implements View.OnClickListener{
    private MainActivity ctx;
    private ViewPager newsPager;

    private GridProductAdapter adapter;
    private ExpandableHeightGridView gridView;
    private ImageButton btnNavigation;
    private TextView tvJudulKategori,tvClear;
    private ImageButton btnKategori,btnEvent,btnKurs,btnKomoditi,btnLoker;
    private LinearLayout llDuplikat, llLeftoverMenu;
    private ScrollView svContainer;

    private MainPresenter presenter;
    private String selectedKategori = "";

    public FragmentHome() {
    }

    public static FragmentHome newInstance(MainActivity mainActivity, String title) {
        FragmentHome fragment = new FragmentHome();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialize(MainActivity activity){
        ctx = activity;
        presenter = ctx.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsPager = view.findViewById(R.id.viewPager);
        tvJudulKategori = view.findViewById(R.id.tv_judul_kategori);
        btnNavigation = view.findViewById(R.id.btn_navigation);
        btnKategori = view.findViewById(R.id.button_kategori);
        btnEvent = view.findViewById(R.id.button_event);
        btnKurs = view.findViewById(R.id.button_kurs);
        btnKomoditi = view.findViewById(R.id.button_komoditi);
        btnLoker = view.findViewById(R.id.button_loker);
        tvClear = view.findViewById(R.id.tv_clear);

        llDuplikat = view.findViewById(R.id.ll_duplicate);
        llLeftoverMenu = view.findViewById(R.id.ll_leftover_menu);
        svContainer = view.findViewById(R.id.sv_container);

        svContainer.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                /*if(isFullyVisible(llLeftoverMenu)){
                    llDuplikat.setVisibility(View.INVISIBLE);
                }
                else{
                    llDuplikat.setVisibility(View.VISIBLE);
                }*/

                Rect scrollBounds = new Rect();
                svContainer.getHitRect(scrollBounds);

                if(llLeftoverMenu.getLocalVisibleRect(scrollBounds)){
                    llDuplikat.setVisibility(View.INVISIBLE);
                }
                else{
                    llDuplikat.setVisibility(View.VISIBLE);
                }
            }



            public boolean isFullyVisible(View v){
                Rect scrollBounds = new Rect();
                svContainer.getHitRect(scrollBounds);

                float top = v.getY();
                float bottom = top + v.getHeight();

                Log.d("fh bounds and menu", top + " " + bottom +
                        " " + scrollBounds.top + " " + scrollBounds.bottom +
                        " " + svContainer.getTop() + " " + (svContainer.getTop() + svContainer.getHeight()));

                if (scrollBounds.top <= top && scrollBounds.bottom >= bottom) {
                    return true;
                } else {
                    return false;
                }
            }
        });


        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, 4, new Bitmap[]{
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy1)).getBitmap(),
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy2)).getBitmap(),
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy3)).getBitmap(),
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy4)).getBitmap()
        });
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        newsPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(newsPager);

        tvClear.setVisibility(View.INVISIBLE);
        gridView = view.findViewById(R.id.grid_view);
        gridView.setExpanded(true);

        //TODO if kategori == null, getAllProduct, else getProduct with p.kategori == kategori
        adapter = new GridProductAdapter(ctx.getAllProduct(), ctx);
        gridView.setAdapter(adapter);

        btnNavigation.setOnClickListener(this);
        btnKategori.setOnClickListener(this);
        btnEvent.setOnClickListener(this);
        btnKurs.setOnClickListener(this);
        btnKomoditi.setOnClickListener(this);
        btnLoker.setOnClickListener(this);
        tvClear.setOnClickListener(this);

        return view;
    }

    public String getSelectedKategori() {
        return selectedKategori;
    }

    public void setSelectedKategori(String selectedKategori) {
        this.selectedKategori = selectedKategori;
    }

    private void setMainActivity(MainActivity ctx) {
        this.ctx = ctx;
    }

    public GridProductAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(GridProductAdapter adapter) {
        this.adapter = adapter;
    }

    public void reset() {
        adapter.resetAll();
    }

    @Override
    public void onClick(View v) {
        if(v == btnNavigation){
            ctx.openDrawer();
        }else if(v == btnKategori){
            ctx.changePage(MainActivity.PAGE_KATEGORI);
        }else if(v == btnEvent){

        }else if(v == btnKurs){

        }else if(v == btnKomoditi){

        }else if(v == btnLoker){

        }else if(v == tvClear){
            setSelectedKategori("");
            notifKategoriChanged();
        }
    }

    public void notifKategoriChanged() {
        tvJudulKategori.setText("Product "+selectedKategori);
        if(selectedKategori.equalsIgnoreCase("")){
            tvClear.setVisibility(View.INVISIBLE);
        }else{
            tvClear.setVisibility(View.VISIBLE);
        }
        adapter.setProducts(getProductFromKategori());
        adapter.notifyDataSetChanged();
    }

    private ArrayList<Product> getProductFromKategori(){
        if(!selectedKategori.equalsIgnoreCase("")){
            ArrayList<Product> result = new ArrayList<>();
            boolean flag = false;
            for(Product p : ctx.getAllProduct()){
                flag = false;
                for(String kat : p.getKategori()){
                    if(kat.equalsIgnoreCase(selectedKategori)){
                        result.add(p);
                        flag = true;
                    }
                    if(flag){
                        break;
                    }
                }
            }
            return result;
        }else{
            return ctx.getAllProduct();
        }
    }
}
