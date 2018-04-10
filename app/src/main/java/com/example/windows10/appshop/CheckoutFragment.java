package com.example.windows10.appshop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CheckoutFragment extends Fragment {
    private MainActivity ui;
    private ListView lvProduct;
    private FragmentListener listener;
    public CheckoutFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout,container, false);
        this.lvProduct = view.findViewById(R.id.list_view_product);

        Toolbar customToolbar = view.findViewById(R.id.custom_toolbar);
        ui.setSupportActionBar(customToolbar);
        ui.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }
    public static CheckoutFragment newInstance(MainActivity ui, String title){
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        fragment.setUI(ui);
        return fragment;
    }
    public void setUI(MainActivity ui){
        this.ui = ui;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.listener = (FragmentListener)context;
        }else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }
}
