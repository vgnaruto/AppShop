package com.example.windows10.appshop;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ProductFragment productFragment;
    private FragmentManager fragmentManager;

    public static int PAGE_PRODUCT = 2;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisisalisasi semua fragment
        this.productFragment = ProductFragment.newInstance("Product Fragment");
        this.fragmentManager = getSupportFragmentManager();
//        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        changePage(PAGE_PRODUCT);
        instance = this;
    }

    public void changePage(int i){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(i == PAGE_PRODUCT){
            if(productFragment.isAdded()){
                ft.show(productFragment);
            }else{
                ft.add(R.id.fragment_container,productFragment);
            }
        }
        ft.commit();
    }

    public static MainActivity getInstance(){
        return instance;
    }
}
