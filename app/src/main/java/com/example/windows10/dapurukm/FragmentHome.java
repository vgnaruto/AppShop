package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHome extends Fragment {
    private MainActivity ctx;
    private ViewPager newsPager;

    private GridProductAdapter adapter;
    private ExpandableHeightGridView gridView;

    public FragmentHome(){}

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
        ImagePagerAdapter viewPagerAdapter = new ImagePagerAdapter(ctx, 4 , new Bitmap[]{
                ((BitmapDrawable)ctx.getResources().getDrawable(R.drawable.promo_dummy1)).getBitmap(),
                ((BitmapDrawable)ctx.getResources().getDrawable(R.drawable.promo_dummy2)).getBitmap(),
                ((BitmapDrawable)ctx.getResources().getDrawable(R.drawable.promo_dummy3)).getBitmap(),
                ((BitmapDrawable)ctx.getResources().getDrawable(R.drawable.promo_dummy4)).getBitmap()
        });
        newsPager.setAdapter(viewPagerAdapter);

        gridView = view.findViewById(R.id.grid_view);
        gridView.setExpanded(true);
        adapter = new GridProductAdapter(DataDummy.getProduct(), ctx);
        gridView.setAdapter(adapter);

        return view;
    }

    private void setMainActivity(MainActivity ctx){
        this.ctx = ctx;
    }
}
