package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

public class FragmentCheckout extends Fragment implements View.OnClickListener{
    private ImageButton backButton;
    private TextView tvKeterangan,tvTotalOrder,tvExpeditionFee,tvTotalPayment;
    private Button checkoutButton;
    private ExpandableHeightListView listView;

    private CheckoutAdapter adapter;
    private MainActivity ctx;
    private MainPresenter presenter;

    public FragmentCheckout(){

    }

    public static FragmentCheckout newInstance(MainActivity mainActivity, String title){
        FragmentCheckout fragment = new FragmentCheckout();
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
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        backButton = view.findViewById(R.id.back_button);
        tvKeterangan = view.findViewById(R.id.tv_keterangan_pembeli);
        tvTotalOrder = view.findViewById(R.id.tv_total_order);
        tvTotalPayment = view.findViewById(R.id.tv_total_payment);
        tvExpeditionFee = view.findViewById(R.id.tv_expedition_fee);
        checkoutButton = view.findViewById(R.id.checkout_btn);
        listView = view.findViewById(R.id.list_view);

        adapter = new CheckoutAdapter(ctx,presenter.getProduct());
        listView.setExpanded(true);
        listView.setAdapter(adapter);

        User cUser = presenter.getUser();
        tvKeterangan.setText(presenter.formatKeterangan(cUser));

        backButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == backButton){
            ctx.onBackPressed();
        }
    }

    public void notifData(){
        adapter.notifyDataSetChanged();
    }

    public void updateSpinnerAgent(int posisi){
        adapter.setAgents(posisi,ctx.getAgents());
        tvTotalOrder.setText(presenter.formatRupiah(adapter.getTotal()));
        tvExpeditionFee.setText(presenter.formatRupiah(adapter.getTotalShipping()));
        tvTotalPayment.setText(presenter.formatRupiah(adapter.getTotal()+adapter.getTotalShipping()));
    }
    public void updatePayment(){
        tvTotalOrder.setText(presenter.formatRupiah(adapter.getTotal()));
        tvExpeditionFee.setText(presenter.formatRupiah(adapter.getTotalShipping()));
        tvTotalPayment.setText(presenter.formatRupiah(adapter.getTotal()+adapter.getTotalShipping()));
    }
}