package com.example.windows10.dapurukm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class FragmentHome extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private MainActivity ctx;
    private ViewPager newsPager;

    private GridProductAdapter adapter;
    private ExpandableHeightGridView gridView;

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;

    public FragmentHome() {
    }

    public static FragmentHome newInstance(MainActivity mainActivity, String title) {
        FragmentHome fragment = new FragmentHome();
        fragment.setMainActivity(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
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

        toolbar = view.findViewById(R.id.toolbar);
        ctx.setSupportActionBar(toolbar);

        this.navigationView = view.findViewById(R.id.nav_view);
        this.drawerLayout = view.findViewById(R.id.drawer_layout);
        toolbar = view.findViewById(R.id.toolbar);
        ctx.setSupportActionBar(toolbar);
        actionBar = ctx.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayShowTitleEnabled(false);

        setupDrawerContent(navigationView);

        return view;
    }

    private void setMainActivity(MainActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void setupDrawerContent(NavigationView nv) {
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
        }
        drawerLayout.closeDrawers();
        item.setChecked(true);
    }
}
