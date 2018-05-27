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
        fragment.setMainActivity(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    private void setMainActivity(MainActivity ui) {
        this.ctx = ui;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informasi_pembeli, container, false);
        presenter = ctx.getPresenter();

        queue = Volley.newRequestQueue(ctx);
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
        getProvinsi("https://api.rajaongkir.com/starter/province?key=" + apiKey);

        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idProvinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition()).getProvince_id();
                String apiKey = getResources().getString(R.string.API_KEY);
                getKabupaten("https://api.rajaongkir.com/starter/city?province=" + idProvinsi + "&key=" + apiKey);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backButton.setOnClickListener(this);
        simpanButton.setOnClickListener(this);

        return view;
    }

    //PROVINSI-1
    private void getProvinsi(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Provinsi[] provinsis = processResultProvinsi(response);
                ArrayList<String> namaProvinsi = new ArrayList<>();
                for (int i = 0; i < provinsis.length; i++) {
                    listProvinsi.add(provinsis[i]);
                    namaProvinsi.add(provinsis[i].getProvince());
                }
                ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaProvinsi);
                adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProvinsi.setAdapter(adapterProvinsi);

                //GET KABUPATEN
                String idProvinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition()).getProvince_id();
                String apiKey = getResources().getString(R.string.API_KEY);
                getKabupaten("https://api.rajaongkir.com/starter/city?province=" + idProvinsi + "&key=" + apiKey);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    //PROVINSI-2
    private Provinsi[] processResultProvinsi(String content) {
        try {
            JSONObject json = new JSONObject(content);
            JSONObject obj = json.getJSONObject("rajaongkir");
            JSONArray data = obj.getJSONArray("results");
            return this.gson.fromJson(data.toString(), Provinsi[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //KABUPATEN-1
    private void getKabupaten(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listKabupaten = new ArrayList<>();
                ArrayList<String> namaKabupaten = new ArrayList<>();
                Kabupaten[] kabupatens = processResultKabupaten(response);
                for (Kabupaten k : kabupatens) {
                    listKabupaten.add(k);
                    namaKabupaten.add(k.getCity_name() + "(" + k.getType() + ")");
                }
                ArrayAdapter<String> adapterKabupaten = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaKabupaten);
                adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKabupaten.setAdapter(adapterKabupaten);

                //GET KECAMATAN
                String idKabupaten = listKabupaten.get(spinnerKabupaten.getSelectedItemPosition()).getCity_id();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    //KABUPATEN-2
    private Kabupaten[] processResultKabupaten(String content) {
        try {
            JSONObject json = new JSONObject(content);
            JSONObject obj = json.getJSONObject("rajaongkir");
            JSONArray data = obj.getJSONArray("results");
            return this.gson.fromJson(data.toString(), Kabupaten[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            ctx.onBackPressed();
        } else if (v == simpanButton) {
            if(isDataValid()){
                String nama = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                String kodePos = etKodePos.getText().toString();
                String noTelepon = etNomorTelepon.getText().toString();
                String email = etEmail.getText().toString();
                Provinsi provinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition());
                Kabupaten kabupaten = listKabupaten.get(spinnerKabupaten.getSelectedItemPosition());
                User user = new User(nama,alamat,provinsi,kabupaten,kodePos,noTelepon,email);
                presenter.setUser(user);

                Toast toast = Toast.makeText(ctx,"Berhasil daftar!", Toast.LENGTH_SHORT);
                toast.show();

                //TODO CHANGE TO CHECKOUT PAGE
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
        if(etEmail.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }
}
