package com.example.windows10.dapurukm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class KategoriAdapter extends BaseAdapter{
    private ViewHolder vh;
    private MainPresenter presenter;
    private MainActivity activity;
    private ArrayList<String> listOfKategori;

    public KategoriAdapter(ArrayList<String> listOfKategori, MainActivity activity){
        this.activity = activity;
        this.presenter = this.activity.getPresenter();
        this.listOfKategori = listOfKategori;
    }

    @Override
    public int getCount() {
        return listOfKategori.size();
    }

    @Override
    public String getItem(int position) {
        return listOfKategori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.kategori_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.updateView(getItem(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setSelectedKategori(getItem(position));
                activity.notifyHome();
                activity.onBackPressed();
            }
        });

        return convertView;
    }

    private class ViewHolder{
        private TextView tvNamaKategori;
        public ViewHolder(View view){
            tvNamaKategori = view.findViewById(R.id.tv_nama_kategori);
        }
        public void updateView(String nama){
            tvNamaKategori.setText(nama);
        }
    }
}
