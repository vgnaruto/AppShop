package com.example.windows10.dapurukm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    private FrameLayout frameLayout;
    private ArrayList<Fragment> fragments;
    private FragmentHome fragmentHome;
    private FragmentProduct fragmentProduct;

    private FragmentManager fragmentManager;

    public static int PAGE_HOME = 0;
    public static int PAGE_PRODUCT = 1;

    public static MainActivity instance;
    private Product selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        fragmentHome = FragmentHome.newInstance(this, "HOME FRAGMENT");
        fragmentProduct = FragmentProduct.newInstance(this, "PRODUCT FRAGMENT");

        fragments.add(fragmentHome);
        fragments.add(fragmentProduct);

        frameLayout = findViewById(R.id.fragment_container);

        fragmentManager = getSupportFragmentManager();
        changePage(PAGE_HOME);

        instance = this;
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(page == PAGE_HOME){
            if (fragmentHome.isAdded()) {
                ft.show(fragmentHome);
            } else {
                ft.add(R.id.fragment_container, fragmentHome).addToBackStack("HOME FRAGMENT");
            }
            hideOtherFrag(fragmentHome,ft);
        }else if(page == PAGE_PRODUCT){
            if (fragmentProduct.isAdded()) {
                ft.show(fragmentProduct);
            } else {
                ft.add(R.id.fragment_container, fragmentProduct).addToBackStack("PRODUCT FRAGMENT");
            }
            hideOtherFrag(fragmentProduct,ft);
        }
        ft.commit();
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

    public static MainActivity getInstance() {
        return instance;
    }

    public void setProduct(Product selected){
        this.selected = selected;
    }
    public Product getSelected(){
        return this.selected;
    }
}
