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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

public class FragmentProfile extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private TextView profileNama, profileAlamat, profileNomor, profileEmail, profileKab, profileProv;
    private Button editBtn;
    private ImageButton backBtn;
    private MainPresenter presenter;
    private boolean viewCreated;

    public FragmentProfile() {
    }

    public static FragmentProfile newInstance(MainActivity mainActivity, String title) {
        FragmentProfile fragment = new FragmentProfile();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        Log.d("fp12345", "masuk ni");
        return fragment;
    }

    public void initialize(MainActivity activity){
        ctx = activity;
        presenter = ctx.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Log.d("fp12345", "masuk ocv");

        this.viewCreated = true;
        this.profileNama = view.findViewById(R.id.tv_nama_profile);
        this.profileEmail = view.findViewById(R.id.tv_email_profile);
        this.profileNomor = view.findViewById(R.id.tv_nomor_profile);
        this.profileAlamat = view.findViewById(R.id.tv_alamat_profile);
        this.profileProv = view.findViewById(R.id.tv_prov_profile);
        this.profileKab = view.findViewById(R.id.tv_kab_profile);
        this.editBtn = view.findViewById(R.id.btn_edit_profile);
        this.backBtn = view.findViewById(R.id.back_button);

        this.notifyUserChanged();

        this.backBtn.setOnClickListener(this);
        this.editBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == this.backBtn.getId()){
            ctx.onBackPressed();
        }
        else if(v.getId() == this.editBtn.getId()){
            ctx.changePage(MainActivity.PAGE_INFORMASI_DATA);
        }
    }

    public void notifyUserChanged(){
        User user;
        if(this.presenter.isLogin()){
            user = this.presenter.getUser();
            if(viewCreated) {
                this.profileNama.setText(user.getNama());
                this.profileNomor.setText(user.getNomorTelepon());
                this.profileEmail.setText(user.getEmail());
                this.profileAlamat.setText(user.getAlamat());
                if (user.getProvinsi() != null)
                    this.profileProv.setText(user.getProvinsi().getProvince());
                if (user.getKabupaten() != null)
                    this.profileKab.setText(user.getKabupaten().getCity_name());
            }
        }
    }
}