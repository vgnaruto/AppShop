package com.example.windows10.appshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrderHistoryFragment extends Fragment{
    private MainActivity ui;
    public OrderHistoryFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history,container,false);
        Toolbar customToolbar = view.findViewById(R.id.custom_toolbar);
        ui.setSupportActionBar(customToolbar);
        ui.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }

    public static OrderHistoryFragment newInstance(MainActivity ui,String title){
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        fragment.setUI(ui);
        return fragment;
    }
    public void setUI(MainActivity ui){
        this.ui = ui;
    }
}
