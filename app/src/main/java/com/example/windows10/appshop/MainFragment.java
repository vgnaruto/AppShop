package com.example.windows10.appshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ASUS on 4/7/2018.
 */

public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;

    private MainActivity mainActivity;
    private ViewPager viewPager;

    //private static Menu instances;
    //private GridAdapter gridAdapter;
    //private GridView gridView;

    //private ArrayList<Item> itemMenu;

    private LinearLayout cartButton;
    private TextView cartTv;

    public MainFragment() {
    }

    public static MainFragment newInstance(MainActivity mainActivity) {
        MainFragment fragment = new MainFragment();
        fragment.setMainActivity(mainActivity);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.mDrawerLayout = view.findViewById(R.id.drawer);

        NavigationView mNavigationView = view.findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(this);

        this.mToggle = new ActionBarDrawerToggle(this.mainActivity, this.mDrawerLayout, R.string.open, R.string.close);

        this.mDrawerLayout.addDrawerListener(this.mToggle);

        this.mToggle.syncState();
//        this.mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.mainActivity);
        viewPager.setAdapter(viewPagerAdapter);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == cartButton) {
            String currentText = cartTv.getText().toString();
            if (currentText.contains("ADD")) {
                cartButton.setBackgroundColor(getResources().getColor(R.color.gray_old));
                cartTv.setText("REMOVE FROM CART");
            } else if (currentText.contains("REMOVE")) {
                cartButton.setBackgroundColor(getResources().getColor(R.color.orange));
                cartTv.setText("ADD TO CART");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*
        if(id == R.id.calculator){
            this.changePage(1);
            this.mDrawerLayout.closeDrawers();
            Log.d("navigationItemSelected","Open Calculator");
        }
        if(id == R.id.history){
            this.changePage(2);
            this.mDrawerLayout.closeDrawers();
            Log.d("navigationItemSelected","Open History");
        }
        */
        return false;
    }

}
