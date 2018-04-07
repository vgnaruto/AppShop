package com.example.windows10.appshop;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    private ProductFragment productFragment;
    private FragmentManager fragmentManager;

    public static int PAGE_MAIN = 0;
    public static int PAGE_PRODUCT = 2;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisisalisasi semua fragment
        this.productFragment = ProductFragment.newInstance("Product Fragment");
        this.mainFragment = MainFragment.newInstance(this);
        this.fragmentManager = getSupportFragmentManager();
        instance = this;

        changePage(PAGE_MAIN);
    }

    public void changePage(int i) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (i == PAGE_MAIN) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (mainFragment.isAdded()) {
                ft.show(mainFragment);
            } else {
                ft.add(R.id.fragment_container, mainFragment);
            }
            if(productFragment.isAdded()){
                ft.hide(productFragment);
            }
        }
        if (i == PAGE_PRODUCT) {
            if (productFragment.isAdded()) {
                ft.show(productFragment);
            } else {
                ft.add(R.id.fragment_container, productFragment);
            }
            if(mainFragment.isAdded()){
                ft.hide(mainFragment);
            }
        }
        ft.commit();
    }
    public static MainActivity getInstance () {
        return instance;
    }
}