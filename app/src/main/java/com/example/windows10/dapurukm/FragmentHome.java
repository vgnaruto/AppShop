package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

public class FragmentHome extends Fragment/* implements NavigationView.OnNavigationItemSelectedListener*/ {
    private MainActivity ctx;
    private ViewPager newsPager;

    private GridProductAdapter adapter;
    private ExpandableHeightGridView gridView;

//    private NavigationView navigationView;
//    private Toolbar toolbar;
//    private DrawerLayout drawerLayout;
//    private ActionBar actionBar;

    private MainPresenter presenter;
//    private TextView tvNamaUser;
//    private TextView tvEmailUser;

    public FragmentHome() {
    }

    public static FragmentHome newInstance(MainActivity mainActivity, String title) {
        FragmentHome fragment = new FragmentHome();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialize(MainActivity activity){
        ctx = activity;
        presenter = ctx.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsPager = view.findViewById(R.id.viewPager);
        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, 4, new Bitmap[]{
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy1)).getBitmap(),
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy2)).getBitmap(),
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy3)).getBitmap(),
                ((BitmapDrawable) ctx.getResources().getDrawable(R.drawable.promo_dummy4)).getBitmap()
        });
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        newsPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(newsPager);

        gridView = view.findViewById(R.id.grid_view);
        gridView.setExpanded(true);
        adapter = new GridProductAdapter(DataDummy.getProduct(), ctx);
        gridView.setAdapter(adapter);

//        toolbar = view.findViewById(R.id.toolbar);
//        ctx.setSupportActionBar(toolbar);
//
//        this.drawerLayout = view.findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                ctx, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.setDrawerListener(toggle);
//        toggle.syncState();
//
//        this.navigationView = view.findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        View navView = navigationView.getHeaderView(0);
//        tvNamaUser = navView.findViewById(R.id.etNamaUser);
//        tvEmailUser = navView.findViewById(R.id.etEmailUser);
//
//        toolbar = view.findViewById(R.id.toolbar);
//        ctx.setSupportActionBar(toolbar);
//        actionBar = ctx.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayShowTitleEnabled(false);
//
//        setupDrawerContent(navigationView);
//        setUser();

        return view;
    }

    private void setMainActivity(MainActivity ctx) {
        this.ctx = ctx;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        Log.d("NAVIGATIONDRAWER","MASUK 1");
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Log.d("NAVIGATIONDRAWER","MASUK 2");
//        if (id == R.id.menu_notifikasi) {
//            Log.d("NAVIGATIONDRAWER","NOTIFIKASI");
//        } else if (id == R.id.menu_transaksi) {
//            Log.d("NAVIGATIONDRAWER","TRANSAKSI");
//        } else if (id == R.id.menu_favorit) {
//            Log.d("NAVIGATIONDRAWER","FAVORIT");
//        } else if (id == R.id.menu_lihat_profile) {
//            Log.d("NAVIGATIONDRAWER","LIHAT PROFILE");
//        } else if (id == R.id.menu_penjualan) {
//            Log.d("NAVIGATIONDRAWER","PENJUALAN");
//        }else if (id == R.id.menu_keluar) {
//            Log.d("NAVIGATIONDRAWER","KELUAR");
//        }else if (id == R.id.menu_informasi_khusus) {
//            Log.d("NAVIGATIONDRAWER","INFORMASI KHUSUS");
//        }else if (id == R.id.menu_event) {
//            Log.d("NAVIGATIONDRAWER","EVENT");
//        }else if (id == R.id.menu_lowongan_kerja) {
//            Log.d("NAVIGATIONDRAWER","LOWONGAN KERJA");
//        }else if (id == R.id.menu_trending_topic) {
//            Log.d("NAVIGATIONDRAWER","TRENDING TOPIC");
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
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
//
//    public void selectDrawerItem(MenuItem item) {
//        switch (item.getItemId()) {
//        }
//        drawerLayout.closeDrawers();
//        item.setChecked(true);
//    }
//
//    public void setUser() {
//        User cUser = presenter.getUser();
//        tvNamaUser.setText(cUser.getNama());
//        tvEmailUser.setText(cUser.getEmail());
//    }

    public GridProductAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(GridProductAdapter adapter) {
        this.adapter = adapter;
    }
}
