package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import me.relex.circleindicator.CircleIndicator;

public class FragmentHome extends Fragment implements View.OnClickListener{
    private MainActivity ctx;
    private ViewPager newsPager;

    private GridProductAdapter adapter;
    private ExpandableHeightGridView gridView;
    private ImageButton btnNavigation;

    private MainPresenter presenter;

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

        btnNavigation = view.findViewById(R.id.btn_navigation);

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

        btnNavigation.setOnClickListener(this);

        return view;
    }

    private void setMainActivity(MainActivity ctx) {
        this.ctx = ctx;
    }

    public GridProductAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(GridProductAdapter adapter) {
        this.adapter = adapter;
    }

    public void reset() {
        adapter.resetAll();
    }

    @Override
    public void onClick(View v) {
        if(v == btnNavigation){
            ctx.openDrawer();
        }
    }
}
