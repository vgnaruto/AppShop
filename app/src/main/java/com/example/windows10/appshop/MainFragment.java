package com.example.windows10.appshop;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ASUS on 4/7/2018.
 */

public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;

    private MainActivity mainActivity;
    private ViewPager viewPager;

    private RelativeLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private GridAdapter gridAdapter;
    private GridView gridView;

    private ArrayList<Category> categoryList;

    public MainFragment() {
    }

    public static MainFragment newInstance(MainActivity mainActivity, String title) {
        MainFragment fragment = new MainFragment();
        fragment.setMainActivity(mainActivity);
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.mDrawerLayout = view.findViewById(R.id.drawer);
        sliderDotspanel =  view.findViewById(R.id.SliderDots);
        NavigationView mNavigationView = view.findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(this);

        this.mToggle = new ActionBarDrawerToggle(this.mainActivity, this.mDrawerLayout, R.string.open, R.string.close);

        this.mDrawerLayout.addDrawerListener(this.mToggle);

        this.mToggle.syncState();
        //this.mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.mainActivity);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(mainActivity);
            dots[i].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.schedule(new ImageTimer(), 2000, 4000);

        gridView = view.findViewById(R.id.grid_view);
        gridAdapter = new GridAdapter(this.mainActivity);

        categoryList = new ArrayList<>();

        for(int i=0;i<8;i++){
            Category item = new Category(BitmapFactory.decodeResource(getResources(),
                    R.drawable.test_img1),"Judul_"+i,"Deskripsi_"+i);
            categoryList.add(item);
        }
        gridAdapter.setItems(categoryList);
        gridView.setAdapter(gridAdapter);


        return view;
    }

    @Override
    public void onClick(View v) {

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

    private class ImageTimer extends TimerTask{

        @Override
        public void run(){
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
