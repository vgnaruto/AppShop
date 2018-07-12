package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentKategori extends Fragment {
    private KategoriAdapter adapter;
    private ListView listKategori;
    private ImageButton backButton;
    private MainPresenter presenter;
    private MainActivity ctx;

    public FragmentKategori(){}

    public static FragmentKategori newInstance(MainActivity mainActivity,String title){
        FragmentKategori fragment = new FragmentKategori();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialize(MainActivity main){
        this.ctx = main;
        presenter = ctx.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kategori, container, false);

        ArrayList<String> listOfKategori = new ArrayList<>();
        listOfKategori.add("Kategori 1");
        listOfKategori.add("Kategori 2");
        listOfKategori.add("Kategori 3");
        listOfKategori.add("Kategori 4");
        listOfKategori.add("Kategori 5");

        backButton = view.findViewById(R.id.back_button);
        listKategori = view.findViewById(R.id.list_kategori);
        adapter = new KategoriAdapter(listOfKategori,ctx);
        listKategori.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.onBackPressed();
            }
        });


        return view;
    }
}
