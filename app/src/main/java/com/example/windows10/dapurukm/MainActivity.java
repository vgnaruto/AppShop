package com.example.windows10.dapurukm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.felix.bottomnavygation.BadgeIndicator;
import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    private FrameLayout frameLayout;
    private ArrayList<Fragment> fragments;
    private FragmentHome fragmentHome;
    private FragmentProduct fragmentProduct;
    private FragmentShoppingCart fragmentShoppingCart;
    private FragmentInformasiData fragmentInformasiData;
    private FragmentCheckout fragmentCheckout;

    private MainPresenter presenter;

    private FragmentManager fragmentManager;

    public static int PAGE_HOME = 0;
    public static int PAGE_PRODUCT = 1;
    public static int PAGE_SHOPPING_CART = 2;
    public static int PAGE_INFORMASI_DATA = 3;
    public static int PAGE_CHECKOUT = 4;

    public static MainActivity instance;
    private Product selected;

    private BottomNav bottomNav;
    private BadgeIndicator badgeCart;

    private Agent[] agents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.loadUser();

        fragments = new ArrayList<>();
        fragmentHome = FragmentHome.newInstance(this, "HOME FRAGMENT");
        fragmentProduct = FragmentProduct.newInstance(this, "PRODUCT FRAGMENT");
        fragmentShoppingCart = FragmentShoppingCart.newInstance(this, "SHOPPING CART FRAGMENT");
        fragmentInformasiData = FragmentInformasiData.newInstance(this, "INFORMASI DATA FRAGMENT");
        fragmentCheckout = FragmentCheckout.newInstance(this, "CHECKOUT FRAGMENT");

        fragments.add(fragmentHome);
        fragments.add(fragmentProduct);
        fragments.add(fragmentShoppingCart);
        fragments.add(fragmentInformasiData);
        fragments.add(fragmentCheckout);

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
        fragmentCheckout.notifData();
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
                ft.add(R.id.fragment_container, fragmentInformasiData).addToBackStack("fragment_data");
            }
            hideOtherFrag(fragmentInformasiData, ft);
        }else if(page == PAGE_CHECKOUT){
            hideNavBar();
            if (fragmentCheckout.isAdded()) {
                ft.show(fragmentCheckout);
            } else {
                ft.add(R.id.fragment_container, fragmentCheckout).addToBackStack("fragment_checkout");
            }
            hideOtherFrag(fragmentCheckout, ft);
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

    public MainPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void notifyUserChanged(){
        fragmentHome.setUser();
    }

    public void setKabupaten(ArrayList<Kabupaten> listKabupaten, ArrayList<String> namaKabupaten){
        fragmentInformasiData.setKabupaten(listKabupaten,namaKabupaten);
    }
    public void setProvinsi(ArrayList<Provinsi> listProvinsi, ArrayList<String> namaProvinsi){
        fragmentInformasiData.setProvinsi(listProvinsi,namaProvinsi);
    }
    public ArrayList<Product> getProduct(){
        return fragmentShoppingCart.getProduct();
    }

    public void setAgents(Agent[] agents){
        this.agents = agents;
    }

    public Agent[] getAgents() {
        return agents;
    }

    public void notifyCheckOutAdapter(int posisi){
        fragmentCheckout.updateSpinnerAgent(posisi);
    }
    public void notifyCheckOutAdapter(){
        fragmentCheckout.updatePayment();
    }
}
