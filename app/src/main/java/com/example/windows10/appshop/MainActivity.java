package com.example.windows10.appshop;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    private MainFragment mainFragment;
    private ProductFragment productFragment;
    private FragmentManager fragmentManager;
    private ShoppingFragment shoppingFragment;

    public static int PAGE_MAIN = 0;
    public static int PAGE_PRODUCT = 2;
    public static int PAGE_SHOPPING_CART = 8;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        //inisisalisasi semua fragment
        this.productFragment = ProductFragment.newInstance(this,"Product Fragment");
        this.shoppingFragment = ShoppingFragment.newInstance(this, "Shopping Fragment");
        this.mainFragment = MainFragment.newInstance(this);

        fragments.add(productFragment);
        fragments.add(shoppingFragment);
        fragments.add(mainFragment);

        this.fragmentManager = getSupportFragmentManager();
        instance = this;

        changePage(PAGE_SHOPPING_CART);
    }

    public void changePage(int i) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (i == PAGE_MAIN) {
            if (mainFragment.isAdded()) {
                ft.show(mainFragment);
            } else {
                ft.add(R.id.fragment_container, mainFragment);
            }
            hideOtherFrag(mainFragment,ft);
        }
        if (i == PAGE_PRODUCT) {
            if (productFragment.isAdded()) {
                ft.show(productFragment);
            } else {
                ft.add(R.id.fragment_container, productFragment);
            }
            hideOtherFrag(productFragment, ft);
        }
        if(i == PAGE_SHOPPING_CART){
            if(shoppingFragment.isAdded()){
                ft.show(shoppingFragment);
            }else{
                ft.add(R.id.fragment_container, shoppingFragment);
            }
            hideOtherFrag(shoppingFragment,ft);
        }
        ft.commit();
    }
    public static MainActivity getInstance () {
        return instance;
    }

    private void hideOtherFrag(Fragment visible, FragmentTransaction ft){
        for(Fragment f : fragments){
            if(f != visible){
                if(f.isAdded()) {
                    ft.hide(f);
                }
            }
        }
    }
}