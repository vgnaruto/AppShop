package com.example.windows10.dapurukm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.felix.bottomnavygation.BadgeIndicator;
import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    private FrameLayout frameLayout;
    private ArrayList<Fragment> fragments;
    private FragmentHome fragmentHome;
    private FragmentProduct fragmentProduct;
    private FragmentShoppingCart fragmentShoppingCart;
    private FragmentInformasiData fragmentInformasiData;

    private FragmentManager fragmentManager;

    public static int PAGE_HOME = 0;
    public static int PAGE_PRODUCT = 1;
    public static int PAGE_SHOPPING_CART = 2;
    public static int PAGE_INFORMASI_DATA = 3;

    public static MainActivity instance;
    private Product selected;

    private BottomNav bottomNav;
    private BadgeIndicator badgeCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        fragmentHome = FragmentHome.newInstance(this, "HOME FRAGMENT");
        fragmentProduct = FragmentProduct.newInstance(this, "PRODUCT FRAGMENT");
        fragmentShoppingCart = FragmentShoppingCart.newInstance(this, "SHOPPING CART FRAGMENT");
        fragmentInformasiData = FragmentInformasiData.newInstance(this, "INFORMASI DATA FRAGMENT");

        fragments.add(fragmentHome);
        fragments.add(fragmentProduct);
        fragments.add(fragmentShoppingCart);
        fragments.add(fragmentInformasiData);

        frameLayout = findViewById(R.id.fragment_container);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fragmentManager.getBackStackEntryCount()==0){
                    showNavBar();
                    bottomNav.selectTab(0);
                    badgeCart.updateCount(fragmentShoppingCart.getTotalItems());
                }
            }
        });
        bottomNav = findViewById(R.id.llmenu);
        badgeCart = new BadgeIndicator(this, android.R.color.holo_red_dark, android.R.color.white);
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_home, "Home").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_cart, "Keranjang").addColorAtive(R.color.white).addBadgeIndicator(badgeCart));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_promo2, "Pemberitahuan").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_star_navbar, "Favorit").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_profile, "Profile").addColorAtive(R.color.white));
        bottomNav.build();

        BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position){
                switch (position){
                    case 0:
                        changePage(MainActivity.PAGE_HOME);
                        break;
                    case 1:
                        changePage(MainActivity.PAGE_SHOPPING_CART);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }

            @Override
            public void onTabLongSelected(int position) {
            }
        };
        bottomNav.setTabSelectedListener(listener);
        showNavBar();

        changePage(PAGE_HOME);
        instance = this;
    }

    public void hideNavBar(){
        bottomNav.setVisibility(View.GONE);
    }
    public void showNavBar(){
        bottomNav.setVisibility(View.VISIBLE);
    }

    public void notifyShoppingCart(){
        fragmentShoppingCart.notifData();
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (page == PAGE_HOME) {
            showNavBar();
            if (fragmentHome.isAdded()) {
                ft.show(fragmentHome);
            } else {
                ft.add(R.id.fragment_container, fragmentHome);
            }
            hideOtherFrag(fragmentHome, ft);
        } else if (page == PAGE_PRODUCT) {
            hideNavBar();
            if (fragmentProduct.isAdded()) {
                ft.show(fragmentProduct);
            } else {
                ft.add(R.id.fragment_container, fragmentProduct).addToBackStack("fragment_product");
            }
            hideOtherFrag(fragmentProduct, ft);
        } else if (page == PAGE_SHOPPING_CART) {
            hideNavBar();
            if (fragmentShoppingCart.isAdded()) {
                ft.show(fragmentShoppingCart);
            } else {
                ft.add(R.id.fragment_container, fragmentShoppingCart).addToBackStack("fragment_cart");
            }
            hideOtherFrag(fragmentShoppingCart, ft);
        }else if (page == PAGE_INFORMASI_DATA) {
            hideNavBar();
            if (fragmentInformasiData.isAdded()) {
                ft.show(fragmentInformasiData);
            } else {
                ft.add(R.id.fragment_container, fragmentInformasiData).addToBackStack("fragment_cart");
            }
            hideOtherFrag(fragmentInformasiData, ft);
        }
        ft.commit();
    }

    private void hideOtherFrag(Fragment visible, FragmentTransaction ft) {
        for (Fragment f : fragments) {
            if (f != visible) {
                if (f.isAdded()) {
                    ft.hide(f);
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
    public static MainActivity getInstance() {
        return instance;
    }

    public void sendToCart(Product selected) {
        fragmentShoppingCart.addProduct(selected);
    }
    public void removeFromCart(Product selected){
        fragmentShoppingCart.remove(selected);
    }

    public void setProduct(Product selected) {
        FragmentProduct.setSelected(selected);
    }

    public Product getSelected() {
        return this.selected;
    }

}
