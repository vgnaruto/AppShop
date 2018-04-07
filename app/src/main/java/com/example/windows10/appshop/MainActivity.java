package com.example.windows10.appshop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    private MainFragment mainFragment;
    private ProductFragment productFragment;
    private FragmentManager fragmentManager;
    private CartFragment cartFragment;
    private WishlistFragment wishlistFragment;
    private OrderHistoryFragment orderHistoryFragment;
    private NewsListFragment newsListFragment;
    private NotificationFragment notificationFragment;

    public static int PAGE_MAIN = 0;
    public static int PAGE_PRODUCT = 2;
    public static int PAGE_CART = 8;
    public static int PAGE_WISHLIST = 9;
    public static int PAGE_ORDER_HISTORY = 10;
    public static int PAGE_NEWS_LIST = 11;
    public static int PAGE_NOTIFICATION = 12;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        //inisisalisasi semua fragment
        this.productFragment = ProductFragment.newInstance(this,"Product Fragment");
        this.cartFragment = CartFragment.newInstance(this, "Shopping Fragment");
        this.wishlistFragment = WishlistFragment.newInstance(this, "Wishlist Fragment");
        this.orderHistoryFragment = OrderHistoryFragment.newInstance(this, "Order History Fragment");
        this.newsListFragment = NewsListFragment.newInstance(this, "News Info Fragment");
        this.notificationFragment = NotificationFragment.newInstance(this, "Notification Fragment");
        this.mainFragment = MainFragment.newInstance(this);

        fragments.add(productFragment);
        fragments.add(cartFragment);
        fragments.add(mainFragment);
        fragments.add(wishlistFragment);
        fragments.add(orderHistoryFragment);
        fragments.add(newsListFragment);
        fragments.add(notificationFragment);

        this.fragmentManager = getSupportFragmentManager();
        instance = this;

        changePage(PAGE_NEWS_LIST);
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
        if(i == PAGE_CART){
            if(cartFragment.isAdded()){
                ft.show(cartFragment);
            }else{
                ft.add(R.id.fragment_container, cartFragment);
            }
            hideOtherFrag(cartFragment,ft);
        }
        if(i == PAGE_WISHLIST){
            if(wishlistFragment.isAdded()){
                ft.show(wishlistFragment);
            }else{
                ft.add(R.id.fragment_container, wishlistFragment);
            }
            hideOtherFrag(wishlistFragment,ft);
        }
        if(i == PAGE_ORDER_HISTORY){
            if(orderHistoryFragment.isAdded()){
                ft.show(orderHistoryFragment);
            }else{
                ft.add(R.id.fragment_container,orderHistoryFragment);
            }
            hideOtherFrag(orderHistoryFragment,ft);
        }
        if(i == PAGE_NEWS_LIST){
            if(newsListFragment.isAdded()){
                ft.show(newsListFragment);
            }else{
                ft.add(R.id.fragment_container, newsListFragment);
            }
            hideOtherFrag(newsListFragment,ft);
        }
        if(i == PAGE_NOTIFICATION){
            if(notificationFragment.isAdded()){
                ft.show(notificationFragment);
            }else{
                ft.add(R.id.fragment_container,notificationFragment);
            }
            hideOtherFrag(notificationFragment,ft);
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