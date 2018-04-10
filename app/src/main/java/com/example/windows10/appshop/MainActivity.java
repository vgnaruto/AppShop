package com.example.windows10.appshop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentListener{
    private ArrayList<Fragment> fragments;
    private MainFragment mainFragment;
    private ProductFragment productFragment;
    private FragmentManager fragmentManager;
    private CartFragment cartFragment;
    private WishlistFragment wishlistFragment;
    private OrderHistoryFragment orderHistoryFragment;
    private NewsListFragment newsListFragment;
    private NotificationFragment notificationFragment;
    private CheckoutFragment checkoutFragment;
    private SettingFragment settingFragment;

    public static int PAGE_MAIN = 0;
    public static int PAGE_PRODUCT = 2;
    public static int PAGE_CART = 8;
    public static int PAGE_WISHLIST = 9;
    public static int PAGE_ORDER_HISTORY = 10;
    public static int PAGE_NEWS_LIST = 11;
    public static int PAGE_NOTIFICATION = 12;
    public static int PAGE_CHECKOUT = 13;
    public static int PAGE_SETTING = 14;
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
        this.checkoutFragment = CheckoutFragment.newInstance(this, "Checkout Fragment");
        this.settingFragment = SettingFragment.newInstance(this, "Setting Fragment");
        this.mainFragment = MainFragment.newInstance(this, "Main Fragment");

        fragments.add(productFragment);
        fragments.add(cartFragment);
        fragments.add(mainFragment);
        fragments.add(wishlistFragment);
        fragments.add(orderHistoryFragment);
        fragments.add(newsListFragment);
        fragments.add(notificationFragment);
        fragments.add(checkoutFragment);
        fragments.add(settingFragment);

        this.fragmentManager = getSupportFragmentManager();
        instance = this;

        changePage(PAGE_MAIN);
    }

    @Override
    public void changePage(int i) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (i == PAGE_MAIN) {
            if (mainFragment.isAdded()) {
                ft.show(mainFragment);
            } else {
                ft.add(R.id.fragment_container, mainFragment).addToBackStack("MAIN FRAGMENT");
            }
            hideOtherFrag(mainFragment,ft);
        }
        if (i == PAGE_PRODUCT) {
            if (productFragment.isAdded()) {
                ft.show(productFragment);
            } else {
                ft.add(R.id.fragment_container, productFragment).addToBackStack("PRODUCT FRAGMENT");
            }
            hideOtherFrag(productFragment, ft);
        }
        if(i == PAGE_CART){
            if(cartFragment.isAdded()){
                ft.show(cartFragment);
            }else{
                ft.add(R.id.fragment_container, cartFragment).addToBackStack("CART FRAGMENT");
            }
            hideOtherFrag(cartFragment,ft);
        }
        if(i == PAGE_WISHLIST){
            if(wishlistFragment.isAdded()){
                ft.show(wishlistFragment);
            }else{
                ft.add(R.id.fragment_container, wishlistFragment).addToBackStack("WISHLIST FRAGMENT");
            }
            hideOtherFrag(wishlistFragment,ft);
        }
        if(i == PAGE_ORDER_HISTORY){
            if(orderHistoryFragment.isAdded()){
                ft.show(orderHistoryFragment);
            }else{
                ft.add(R.id.fragment_container,orderHistoryFragment).addToBackStack("ORDER HISTORY FRAGMENT");
            }
            hideOtherFrag(orderHistoryFragment,ft);
        }
        if(i == PAGE_NEWS_LIST){
            if(newsListFragment.isAdded()){
                ft.show(newsListFragment);
            }else{
                ft.add(R.id.fragment_container, newsListFragment).addToBackStack("NEWS LIST FRAGMENT");
            }
            hideOtherFrag(newsListFragment,ft);
        }
        if(i == PAGE_NOTIFICATION){
            if(notificationFragment.isAdded()){
                ft.show(notificationFragment);
            }else{
                ft.add(R.id.fragment_container,notificationFragment).addToBackStack("NOTIFICATION FRAGMENT");
            }
            hideOtherFrag(notificationFragment,ft);
        }
        if(i == PAGE_CHECKOUT){
            if(checkoutFragment.isAdded()){
                ft.show(checkoutFragment);
            }else{
                ft.add(R.id.fragment_container,checkoutFragment).addToBackStack("CHECKOUT FRAGMENT");
            }
            hideOtherFrag(checkoutFragment,ft);
        }
        if(i == PAGE_SETTING){
            if(settingFragment.isAdded()){
                ft.show(settingFragment);
            }else{
                ft.add(R.id.fragment_container,settingFragment).addToBackStack("SETTING FRAGMENT");
            }
            hideOtherFrag(settingFragment,ft);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return false;
    }
}