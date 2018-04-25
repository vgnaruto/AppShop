package com.example.windows10.dapurukm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

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

    private FragmentManager fragmentManager;

    public static int PAGE_HOME = 0;
    public static int PAGE_PRODUCT = 1;
    public static int PAGE_SHOPPING_CART = 2;

    public static MainActivity instance;
    private Product selected;

    private BottomNav bottomNav;
    private Stack<Integer> stack;
    private int currentPage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        fragmentHome = FragmentHome.newInstance(this, "HOME FRAGMENT");
        fragmentProduct = FragmentProduct.newInstance(this, "PRODUCT FRAGMENT");
        fragmentShoppingCart = FragmentShoppingCart.newInstance(this, "SHOPPING CART FRAGMENT");

        fragments.add(fragmentHome);
        fragments.add(fragmentProduct);
        fragments.add(fragmentShoppingCart);

        frameLayout = findViewById(R.id.fragment_container);

        fragmentManager = getSupportFragmentManager();

        stack = new Stack<>();

        bottomNav = findViewById(R.id.llmenu);
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_home, "Home").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_transaction, "Transaction").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_cart, "Cart").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_promo2, "Promo").addColorAtive(R.color.white));
        bottomNav.build();

        BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position){
                switch (position){
                    case 0:
                        changePage(MainActivity.PAGE_HOME);
                        break;
                    case 1:
                        break;
                    case 2:
                        changePage(MainActivity.PAGE_SHOPPING_CART);
                        break;
                    case 3:
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

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (page == PAGE_HOME) {
            showNavBar();
            if(currentPage == -1){
                currentPage = PAGE_HOME;
            }else{
                stack.push(currentPage);
            }
            if (fragmentHome.isAdded()) {
                ft.show(fragmentHome);
            } else {
                ft.add(R.id.fragment_container, fragmentHome).addToBackStack(null);
            }
            hideOtherFrag(fragmentHome, ft);
        } else if (page == PAGE_PRODUCT) {
            hideNavBar();
            stack.push(currentPage);
            currentPage = PAGE_PRODUCT;
            if (fragmentProduct.isAdded()) {
                ft.show(fragmentProduct);
            } else {
                ft.add(R.id.fragment_container, fragmentProduct).addToBackStack(null);
            }
            hideOtherFrag(fragmentProduct, ft);
        } else if (page == PAGE_SHOPPING_CART) {
            hideNavBar();
            stack.push(currentPage);
            currentPage = PAGE_SHOPPING_CART;
            if (fragmentShoppingCart.isAdded()) {
                ft.show(fragmentShoppingCart);
            } else {
                ft.add(R.id.fragment_container, fragmentShoppingCart).addToBackStack(null);
            }
            hideOtherFrag(fragmentShoppingCart, ft);
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

    public void setProduct(Product selected) {
        FragmentProduct.setSelected(selected);
    }

    public Product getSelected() {
        return this.selected;
    }
}
