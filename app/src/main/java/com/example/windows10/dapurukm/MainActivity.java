package com.example.windows10.dapurukm;

import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.bottomnavygation.BadgeIndicator;
import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements FragmentListener, NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    private ArrayList<Fragment> fragments;
    private FragmentHome fragmentHome;
    private FragmentProduct fragmentProduct;
    private FragmentShoppingCart fragmentShoppingCart;
    private FragmentInformasiData fragmentInformasiData;
    private FragmentCheckout fragmentCheckout;
    private FragmentProfile fragmentProfile;
    private FragmentKategori fragmentKategori;
    private FragmentInputBarang fragmentInputBarang;
    private FragmentManageItem fragmentManageItem;

    private MainPresenter presenter;
    private ArrayList<Product> allProduct;

    private FragmentManager fragmentManager;

    private HashMap<String, Product> index;

    public static int PAGE_HOME = 0;
    public static int PAGE_PRODUCT = 1;
    public static int PAGE_SHOPPING_CART = 2;
    public static int PAGE_INFORMASI_DATA = 3;
    public static int PAGE_CHECKOUT = 4;
    public static int PAGE_KATEGORI = 5;
    public static int PAGE_PROFILE = 11;
    public static int PAGE_INPUT_BARANG = 12;
    public static int PAGE_MANAGE_ITEM = 13;

    public static MainActivity instance;
    private Product selected;

    private BottomNav bottomNav;
    private BadgeIndicator badgeCart;

    private TextView tvNamaUser;
    private TextView tvEmailUser;

    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;

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
        fragmentProfile = FragmentProfile.newInstance(this, "PROFILE FRAGMENT");
        fragmentKategori = FragmentKategori.newInstance(this, "KATEGORI FRAGMENT");
        fragmentInputBarang  = FragmentInputBarang.newInstance(this, "INPUT BARANG FRAGMENT");
        fragmentManageItem = FragmentManageItem.newInstance(this, "MANAGE ITEM FRAGMENT");

        fragments.add(fragmentHome);
        fragments.add(fragmentProduct);
        fragments.add(fragmentShoppingCart);
        fragments.add(fragmentInformasiData);
        fragments.add(fragmentCheckout);
        fragments.add(fragmentKategori);
        fragments.add(fragmentProfile);
        fragments.add(fragmentInputBarang);
        fragments.add(fragmentManageItem);

        frameLayout = findViewById(R.id.fragment_container);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() == 0) {
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
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_promo, "Promo").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_promo2, "Pemberitahuan").addColorAtive(R.color.white));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_profile, "Profile").addColorAtive(R.color.white));
        bottomNav.build();

        BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
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
                        if(presenter.isLogin())changePage(MainActivity.PAGE_PROFILE);
                        else changePage(MainActivity.PAGE_INFORMASI_DATA);
                        break;
                }
            }

            @Override
            public void onTabLongSelected(int position) {
            }
        };
        bottomNav.setTabSelectedListener(listener);
        showNavBar();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navView = navigationView.getHeaderView(0);
        tvNamaUser = navView.findViewById(R.id.etNamaUser);
        tvEmailUser = navView.findViewById(R.id.etEmailUser);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayShowTitleEnabled(false);

        setUser();

        changePage(PAGE_HOME);
        instance = this;

        this.index = new HashMap<>();
        allProduct = DataDummy.getProduct();
        for (int i = 0; i < allProduct.size(); i++) {
            createHash(allProduct.get(i));
        }

        hideAddProductMenu();
        Log.d("MA12345", "onCreate terus-terusan");
    }

    public void hideAddProductMenu(){
        if(!presenter.getUser().isPenjual()){
            navigationView.getMenu().findItem(R.id.menu_atur_barang).setVisible(false);
        }
        else{
            navigationView.getMenu().findItem(R.id.menu_atur_barang).setVisible(true);
        }
    }

    public HashMap<String, Product> getIndex() {
        return index;
    }

    public void createHash(Product product){
        this.index.put(product.getSeller().getName() + "-" + product.getNama(), product);
    }

    public void removeHash(String productKey){
        if(this.index.containsKey(productKey))
        this.index.remove(productKey);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);

        Log.d("MA12345", "onCreate belakangan menu");
        //menu.getItem(R.id.menu_atur_barang).setVisible(false);
        return true;
    }

    public ArrayList<Product> getAllProduct() {
        return allProduct;
    }

    public void setAllProduct(ArrayList<Product> allProduct) {
        this.allProduct = allProduct;
    }

    public void hideNavBar() {
        bottomNav.setVisibility(View.GONE);
    }

    public void showNavBar() {
        updateBadge();
        bottomNav.setVisibility(View.VISIBLE);
    }

    public void notifyShoppingCart() {
        fragmentShoppingCart.notifData();
        fragmentCheckout.notifData();
        //fm.saveFile(fragmentShoppingCart.getAdapter().getSavedProducts());
        //Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
    }

    public void notifyManageItem() {
        fragmentManageItem.notifData();
    }

    public void notifyHome(){
        fragmentHome.notifKategoriChanged();
    }

    public void setBottomNavIndex(int index){
        bottomNav.selectTab(index);
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        int drawerLocked = (page == PAGE_HOME)? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(drawerLocked);
        toggle.setDrawerIndicatorEnabled(page == PAGE_HOME);
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
                fragmentProduct.update();
            } else {
                ft.add(R.id.fragment_container, fragmentProduct).addToBackStack("fragment_product");
            }
            hideOtherFrag(fragmentProduct, ft);
        } else if (page == PAGE_SHOPPING_CART) {
            hideNavBar();
            if (fragmentShoppingCart.isAdded()) {
                ft.show(fragmentShoppingCart);
                notifyShoppingCart();
            } else {
                ft.add(R.id.fragment_container, fragmentShoppingCart).addToBackStack("fragment_cart");
            }
            hideOtherFrag(fragmentShoppingCart, ft);
        } else if (page == PAGE_INFORMASI_DATA) {
            hideNavBar();
            if (fragmentInformasiData.isAdded()) {
                ft.show(fragmentInformasiData);
            } else {
                ft.add(R.id.fragment_container, fragmentInformasiData).addToBackStack("fragment_data");
            }
            hideOtherFrag(fragmentInformasiData, ft);
        } else if (page == PAGE_CHECKOUT) {
            hideNavBar();
            if (fragmentCheckout.isAdded()) {
                ft.show(fragmentCheckout);
            } else {
                ft.add(R.id.fragment_container, fragmentCheckout).addToBackStack("fragment_checkout");
            }
            hideOtherFrag(fragmentCheckout, ft);
        } else if (page == PAGE_PROFILE) {
            hideNavBar();
            if (fragmentProfile.isAdded()) {
                ft.show(fragmentProfile);
            } else {
                ft.add(R.id.fragment_container, fragmentProfile).addToBackStack("fragment_profile");
            }
            hideOtherFrag(fragmentProfile, ft);
        } else if(page == PAGE_KATEGORI){
            hideNavBar();
            if (fragmentKategori.isAdded()) {
                ft.show(fragmentKategori);
            } else {
                ft.add(R.id.fragment_container, fragmentKategori).addToBackStack("fragment_kategori");
            }
            hideOtherFrag(fragmentKategori, ft);
        } else if(page == PAGE_INPUT_BARANG){
            hideNavBar();
            if (fragmentInputBarang.isAdded()) {
                ft.show(fragmentInputBarang);
            } else {
                ft.add(R.id.fragment_container, fragmentInputBarang).addToBackStack("fragment_input_barang");
            }
            hideOtherFrag(fragmentInputBarang, ft);
        } else if(page == PAGE_MANAGE_ITEM){
            hideNavBar();
            if (fragmentManageItem.isAdded()) {
                ft.show(fragmentManageItem);
            } else {
                ft.add(R.id.fragment_container, fragmentManageItem).addToBackStack("fragment_manage_item");
            }
            hideOtherFrag(fragmentManageItem, ft);
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
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void sendToCart(Product selected) {
        fragmentShoppingCart.addProduct(selected);
    }

    public void removeFromCart(Product selected) {
        fragmentShoppingCart.remove(selected);
    }

    public void changeSavedProductAmount(Product replaced, Product replacement) {
        fragmentShoppingCart.changeAmount(replaced, replacement);
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

    public void notifyUserChanged() {
        setUser();
    }

    public void setKabupaten(ArrayList<Kabupaten> listKabupaten, ArrayList<String> namaKabupaten) {
        fragmentInformasiData.setKabupaten(listKabupaten, namaKabupaten);
    }

    public void setProvinsi(ArrayList<Provinsi> listProvinsi, ArrayList<String> namaProvinsi) {
        fragmentInformasiData.setProvinsi(listProvinsi, namaProvinsi);
    }

    public ArrayList<Product> getProduct() {
        return fragmentShoppingCart.getProduct();
    }

    public void notifyCheckOutAdapter(int posisi,Agent[] agent) {
        fragmentCheckout.updateSpinnerAgent(posisi,agent);
    }

    public void notifyCheckOutAdapter() {
        fragmentCheckout.updatePayment();
    }

    @Override
    protected void onPause() {
        Log.d("Main", "onPause12345");
        super.onPause();
        presenter.saveItemInCart(fragmentShoppingCart.getAdapter().getSavedProducts());
        //Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        Log.d("Main", "onResume12345");
        super.onResume();
        ArrayList<String> stringProducts = presenter.loadItemForCart();
        ArrayList<Product> products = new ArrayList<>();
        if (stringProducts != null) {
            fragmentShoppingCart.getAdapter().setSavedProducts(stringProducts);
            for (int i = 0; i < stringProducts.size(); i++) {
                String savedProduct = stringProducts.get(i);
                int lastIndex = savedProduct.lastIndexOf('-');
                products.add(index.get(savedProduct.substring(0, lastIndex)));
                products.get(i).setTotal(Integer.parseInt(savedProduct.substring(lastIndex + 1)));
                products.get(i).setInCart(true);
            }
            if (!products.isEmpty()) {
                fragmentShoppingCart.getAdapter().setProducts(products);
                for (int i = 0; i < fragmentHome.getAdapter().getProducts().size(); i++) {
                    fragmentHome.getAdapter().getProducts().set(i, checkInCart(fragmentHome.getAdapter().getProducts().get(i)));
                }
            }
        }
        updateBadge();
    }

    public Product checkInCart(Product item) {
        return fragmentShoppingCart.getAdapter().checkInCart(item);
    }

    public void updateBadge(){
        badgeCart.updateCount(fragmentShoppingCart.getTotalItems());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("NAVIGATIONDRAWER","MASUK 1");
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO IMPLEMENTASI SETIAP MENUNYA
        int id = item.getItemId();
        Log.d("NAVIGATIONDRAWER","MASUK 2");
        if (id == R.id.menu_notifikasi) {
            Log.d("NAVIGATIONDRAWER","NOTIFIKASI");
        } else if (id == R.id.menu_transaksi) {
//            Log.d("NAVIGATIONDRAWER","TRANSAKSI");
            changePage(PAGE_SHOPPING_CART);
        } else if (id == R.id.menu_favorit) {
            Log.d("NAVIGATIONDRAWER","FAVORIT");
        } else if (id == R.id.menu_lihat_profile) {
//            Log.d("NAVIGATIONDRAWER","LIHAT PROFILE");
            if(presenter.isLogin())changePage(MainActivity.PAGE_PROFILE);
            else changePage(MainActivity.PAGE_INFORMASI_DATA);
        } else if (id == R.id.menu_penjualan) {
//            Log.d("NAVIGATIONDRAWER","PENJUALAN");
            changePage(PAGE_SHOPPING_CART);
        }else if (id == R.id.menu_keluar) {
            Log.d("NAVIGATIONDRAWER","KELUAR");
            presenter.saveItemInCart(getSavedProducts());
            System.exit(0);
        }else if (id == R.id.menu_informasi_khusus) {
            Log.d("NAVIGATIONDRAWER","INFORMASI KHUSUS");
        }else if (id == R.id.menu_event) {
            Log.d("NAVIGATIONDRAWER","EVENT");
        }else if (id == R.id.menu_lowongan_kerja) {
            Log.d("NAVIGATIONDRAWER","LOWONGAN KERJA");
        }else if (id == R.id.menu_trending_topic) {
            Log.d("NAVIGATIONDRAWER","TRENDING TOPIC");
        }else if (id == R.id.menu_atur_barang) {
            changePage(PAGE_MANAGE_ITEM);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void setupDrawerContent(NavigationView nv) {
//        nv.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        selectDrawerItem(item);
//                        return true;
//                    }
//                });
//    }

    public void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
        }
        drawerLayout.closeDrawers();
        item.setChecked(true);
    }

    public void setUser() {
        User cUser = presenter.getUser();
        tvNamaUser.setText(cUser.getNama());
        tvEmailUser.setText(cUser.getEmail());
    }

    public void clearCart() {
        fragmentShoppingCart.clearCart();
        fragmentHome.reset();
    }

    public List<String> getSavedProducts() {
        return fragmentShoppingCart.getAdapter().getSavedProducts();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public FragmentProfile getFragmentProfile() {
        return fragmentProfile;
    }

    public void openDrawer(){
        drawerLayout.openDrawer(navigationView);
    }

    public void setSelectedKategori(String kategori){
        fragmentHome.setSelectedKategori(kategori);
    }

    public void notifyHomeFragment(){
        fragmentHome.getAdapter().notifyDataSetChanged();
    }

    public void addProduct(Product newProduct){
        this.allProduct.add(newProduct);
        notifyHomeFragment();
    }

    public FragmentInputBarang getFragmentInputBarang() {
        return fragmentInputBarang;
    }

    public void addProducttoSeller(Product newProduct){
        fragmentManageItem.addProduct(newProduct);
        fragmentManageItem.addProductHash(newProduct);
    }

    public void removeProducttoSeller(Product newProduct){
        fragmentManageItem.remove(newProduct);
        fragmentManageItem.removeHash(newProduct.getSeller().getName() + "-" + newProduct.getNama());
    }

    public FragmentShoppingCart getFragmentShoppingCart() {
        return fragmentShoppingCart;
    }

    public FragmentCheckout getFragmentCheckout() {
        return fragmentCheckout;
    }
}
