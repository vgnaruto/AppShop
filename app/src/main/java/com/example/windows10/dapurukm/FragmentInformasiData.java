package com.example.windows10.dapurukm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentInformasiData extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private MaterialEditText etNama, etAlamat, etKodePos, etNomorTelepon, etEmail;
    private Spinner spinnerProvinsi, spinnerKabupaten;
    private ImageButton backButton;
    private Button simpanButton;

    private ArrayList<Provinsi> listProvinsi = new ArrayList<>();
    private ArrayList<Kabupaten> listKabupaten = new ArrayList<>();
    private RequestQueue queue;
    private Gson gson = new Gson();

    private MainPresenter presenter;

    public FragmentInformasiData() {
    }

    public static FragmentInformasiData newInstance(MainActivity mainActivity, String title) {
        FragmentInformasiData fragment = new FragmentInformasiData();
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
        View view = inflater.inflate(R.layout.fragment_informasi_pembeli, container, false);

//        queue = Volley.newRequestQueue(ctx);
        backButton = view.findViewById(R.id.back_button);
        simpanButton = view.findViewById(R.id.btn_simpan);

        etNama = view.findViewById(R.id.etNama);
        etAlamat = view.findViewById(R.id.etAlamat);
        etKodePos = view.findViewById(R.id.etKodePos);
        etNomorTelepon = view.findViewById(R.id.etNoTelepon);
        etEmail = view.findViewById(R.id.etEmail);

        spinnerProvinsi = view.findViewById(R.id.spinner_provinsi);
        spinnerKabupaten = view.findViewById(R.id.spinner_kabupaten);

        String apiKey = getResources().getString(R.string.API_KEY);
        presenter.getProvinsi("https://api.rajaongkir.com/starter/province?key=" + apiKey, 0);

        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idProvinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition()).getProvince_id();
                String apiKey = getResources().getString(R.string.API_KEY);
                presenter.getKabupaten("https://api.rajaongkir.com/starter/city?province=" + idProvinsi + "&key=" + apiKey);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backButton.setOnClickListener(this);
        simpanButton.setOnClickListener(this);

        return view;
    }

    public void setProvinsi(ArrayList<Provinsi> listProvinsi, ArrayList<String> namaProvinsi) {
        this.listProvinsi = listProvinsi;
        ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaProvinsi);
        adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(adapterProvinsi);
    }

    public void setKabupaten(ArrayList<Kabupaten> listKabupaten, ArrayList<String> namaKabupaten) {
        this.listKabupaten = listKabupaten;
        ArrayAdapter<String> adapterKabupaten = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaKabupaten);
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKabupaten.setAdapter(adapterKabupaten);
    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            ctx.onBackPressed();
        } else if (v == simpanButton) {
            if (isDataValid()) {
                String nama = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                String kodePos = etKodePos.getText().toString();
                String noTelepon = etNomorTelepon.getText().toString();
                String email = etEmail.getText().toString();
                Provinsi provinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition());
                Kabupaten kabupaten = listKabupaten.get(spinnerKabupaten.getSelectedItemPosition());
                User user = new User(nama, alamat, provinsi, kabupaten, kodePos, noTelepon, email);
                presenter.setUser(user);
                presenter.saveUser();

                Toast toast = Toast.makeText(ctx, "Berhasil daftar!", Toast.LENGTH_SHORT);
                toast.show();
                ctx.hideKeyboard();
                ctx.onBackPressed();
            }
        }
    }

    public boolean isDataValid() {
        if (etNama.getText().toString().isEmpty()) {
            return false;
        }
        if (etAlamat.getText().toString().isEmpty()) {
            return false;
        }
        if (etKodePos.getText().toString().isEmpty()) {
            return false;
        }
        if (etNomorTelepon.getText().toString().isEmpty()) {
            return false;
        }
        if (etEmail.getText().toString().isEmpty()) {
            return false;
        }
        if(!isValidEmail(etEmail.getText())){
            return false;
        }
        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
