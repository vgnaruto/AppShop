package com.example.windows10.dapurukm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class FragmentCheckout extends Fragment implements View.OnClickListener{
    private ImageButton backButton;
    private TextView tvKeterangan,tvTotalOrder,tvExpeditionFee,tvTotalPayment;
    private Button checkoutButton;
    private ExpandableHeightListView listView;

    private CheckoutAdapter adapter;
    private MainActivity ctx;
    private MainPresenter presenter;

    private int index =0;

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
        adapter = new CheckoutAdapter(ctx,presenter.getProduct());
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
        checkoutButton = view.findViewById(R.id.btnCheckout);
        listView = view.findViewById(R.id.list_view);

        adapter = new CheckoutAdapter(ctx,presenter.getProduct());
        listView.setExpanded(true);
        listView.setAdapter(adapter);

        User cUser = presenter.getUser();
        tvKeterangan.setText(presenter.formatKeterangan(cUser));

        backButton.setOnClickListener(this);
        checkoutButton.setOnClickListener(this);
        checkoutButton.setEnabled(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == backButton){
            ctx.onBackPressed();
        }else if(v == checkoutButton){
            final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ctx);
            dialogBuilder
                    .withTitle(null)
                    .withMessage("Silahkan lakukan transfer ke rekening XXX atas nama YYY dengan total sebesar "+ presenter.formatRupiah(adapter.getTotal()+adapter.getTotalShipping())+".")
                    .withMessageColor("#FFFFFF")
                    .withDialogColor("#e6005c")
                    .withEffect(Effectstype.Fadein)
                    .isCancelableOnTouchOutside(true)
                    .withButton1Text("Ok")
                    .withButton2Text("Cancel")
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //SEND EMAIL
                            final String username = getResources().getString(R.string.username_email);
                            final String password = getResources().getString(R.string.password_email);

                            final User currentUser = presenter.getUser();

                            Properties props = new Properties();
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.starttls.enable", "true");
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.port", "587");

                            Session session = Session.getInstance(props,
                                    new javax.mail.Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(username,password);
                                        }
                                    });

                            try {
                                final Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress(username));
                                message.setRecipients(Message.RecipientType.TO,
                                        InternetAddress.parse(currentUser.getEmail()));
                                message.setSubject("Invoice Dapur UKM");
                                message.setText(formatPesan());

                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    public Void doInBackground(Void... arg) {
                                        try {
                                            Transport.send(message);
                                        } catch (MessagingException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                }.execute();

                                System.out.println("Done");
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }

                            presenter.clearCart();
                            presenter.saveItemInCart(ctx.getSavedProducts());
                            ctx.changePage(MainActivity.PAGE_HOME);
                            ctx.setBottomNavIndex(0);
                            dialogBuilder.cancel();
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.cancel();
                        }
                    })
                    .show();
        }
    }

    public void notifData(){
        adapter.notifyDataSetChanged();
    }

    public void updateSpinnerAgent(int posisi, Agent[] agents){
        adapter.setAgents(posisi,agents);
        updatePayment();
    }
    public void updatePayment(){
        tvTotalOrder.setText(presenter.formatRupiah(adapter.getTotal()));
        tvExpeditionFee.setText(presenter.formatRupiah(adapter.getTotalShipping()));
        tvTotalPayment.setText(presenter.formatRupiah(adapter.getTotal()+adapter.getTotalShipping()));
        if((adapter.getCount()-1) == index){
            index++;
            checkoutButton.setEnabled(false);
        }else {
            checkoutButton.setEnabled(true);
            index = 0;
        }
    }
    public String formatPesan(){
        User currentUser = presenter.getUser();
        ArrayList<Product> item = adapter.getItem();
        double totalHargaProduct = 0;
        String result = "Halo "+currentUser.getNama()+",\n" +
                "Berikut adalah rincian pesanan.\n";
        for(int i=0;i<item.size();i++){
            Product current = item.get(i);
            String nb = current.getNama();
            int jumlah = current.getTotal();
            double harga = presenter.uangToInteger(current.getHarga()) * jumlah;
            result += (i+1)+"."+nb+"\n";
            result+="  Jumlah: "+jumlah+"\n  Harga :"+presenter.formatRupiah(harga)+"\n\n";
            totalHargaProduct += harga;
        }
        result+="\n\nTotal Harga Produk : "+presenter.formatRupiah(totalHargaProduct)+"\n";
        result+="Ongkos Kirim : "+presenter.formatRupiah(adapter.getTotalShipping())+"\n";
        result+="Total Pembayaran : "+presenter.formatRupiah(totalHargaProduct+adapter.getTotalShipping())+"\n\n";

        result += "Tujuan Pengiriman:\n\n"
                +currentUser.getNama()+"\n"
                +currentUser.getAlamat()+"\n"
                +currentUser.getProvinsi().getProvince()+","+currentUser.getKabupaten().getCity_name()+","+currentUser.getKodePos()+"\n"
                +"Telp: "+currentUser.getNomorTelepon();
        return result;
    }
}