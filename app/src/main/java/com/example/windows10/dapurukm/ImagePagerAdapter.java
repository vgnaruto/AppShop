package com.example.windows10.dapurukm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagePagerAdapter extends PagerAdapter {

    private MainActivity context;
    private LayoutInflater layoutInflater;
    private Promo[] promos;

    public ImagePagerAdapter(MainActivity context, int banyak, Bitmap[] bm) {
        this.context = context;
        promos = new Promo[banyak];
        for(int i=0;i<promos.length;i++){
            promos[i] = new Promo(bm[i],
                    "PROMO MAKANAN",
                    "DESKRIPSI PROMO");
        }
    }

    @Override
    public int getCount() {
        return promos.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliding_news, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageBitmap(promos[position].getPoster());

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
