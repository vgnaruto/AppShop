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
import android.widget.ImageButton;

public class CartFragment extends Fragment implements View.OnClickListener{
    private MainActivity ui;
    private FragmentListener listener;
    private ImageButton buttonCheckOut;
    public CartFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container, false);
        Toolbar customToolbar = view.findViewById(R.id.custom_toolbar);
        ui.setSupportActionBar(customToolbar);
        ui.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.buttonCheckOut = view.findViewById(R.id.button_check_out);
        this.buttonCheckOut.setOnClickListener(this);
        return view;
    }
    public static CartFragment newInstance(MainActivity ui, String title){
        CartFragment fragment = new CartFragment();
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

    @Override
    public void onClick(View v) {
        if(v == buttonCheckOut){
            listener.changePage(MainActivity.PAGE_CHECKOUT);
        }
    }
}
