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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Spinner;

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

public class FragmentInformasiData extends Fragment {
    private MainActivity ctx;
    private MaterialEditText etNama, etAlamat, etKodePos, etNomorTelepon;
    private Spinner spinnerProvinsi, spinnerKabupaten, spinnerKecamatan;
    private ImageButton backButton;

    private ArrayList<Provinsi> listProvinsi = new ArrayList<>();
    private ArrayList<Kabupaten> listKabupaten = new ArrayList<>();
    private ArrayList<Kecamatan> listKecamatan = new ArrayList<>();
    private RequestQueue queue;
    private Gson gson = new Gson();

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
        queue = Volley.newRequestQueue(ctx);
        backButton = view.findViewById(R.id.back_button);
        spinnerProvinsi = view.findViewById(R.id.spinner_provinsi);
        spinnerKabupaten = view.findViewById(R.id.spinner_kabupaten);
        spinnerKecamatan = view.findViewById(R.id.spinner_kecamatan);

        getProvinsi("http://dev.farizdotid.com/api/daerahindonesia/provinsi");

        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idProvinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition()).getId();
                getKabupaten("http://dev.farizdotid.com/api/daerahindonesia/provinsi/"+idProvinsi+"/kabupaten");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idKabupaten = listKabupaten.get(spinnerKabupaten.getSelectedItemPosition()).getId();
                getKecamatan("http://dev.farizdotid.com/api/daerahindonesia/provinsi/kabupaten/"+idKabupaten+"/kecamatan");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.onBackPressed();
            }
        });

        return view;
    }

    //PROVINSI-1
    private void getProvinsi(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Provinsi[] provinsis = processResultProvinsi(response);
                ArrayList<String> namaProvinsi = new ArrayList<>();
                for (int i = 0; i < provinsis.length; i++) {
                    listProvinsi.add(provinsis[i]);
                    namaProvinsi.add(provinsis[i].getNama());
                }
                ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaProvinsi);
                adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProvinsi.setAdapter(adapterProvinsi);

                //GET KABUPATEN
                String idProvinsi = listProvinsi.get(spinnerProvinsi.getSelectedItemPosition()).getId();
                getKabupaten("http://dev.farizdotid.com/api/daerahindonesia/provinsi/"+idProvinsi+"/kabupaten");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    //PROVINSI-2
    private Provinsi[] processResultProvinsi(String content){
        try {
            JSONObject json = new JSONObject(content);
            JSONArray data = json.getJSONArray("semuaprovinsi");
            return this.gson.fromJson(data.toString(), Provinsi[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //KABUPATEN-1
    private void getKabupaten(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listKabupaten = new ArrayList<>();
                ArrayList<String> namaKabupaten = new ArrayList<>();
                Kabupaten[] kabupatens = processResultKabupaten(response);
                for(Kabupaten k : kabupatens){
                    listKabupaten.add(k);
                    namaKabupaten.add(k.getNama());
                }
                ArrayAdapter<String> adapterKabupaten = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaKabupaten);
                adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKabupaten.setAdapter(adapterKabupaten);

                //GET KECAMATAN
                String idKabupaten = listKabupaten.get(spinnerKabupaten.getSelectedItemPosition()).getId();
                getKecamatan("http://dev.farizdotid.com/api/daerahindonesia/provinsi/kabupaten/"+idKabupaten+"/kecamatan");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    //KABUPATEN-2
    private Kabupaten[] processResultKabupaten(String content){
        try {
            JSONObject json = new JSONObject(content);
            JSONArray data = json.getJSONArray("daftar_kecamatan");
            return this.gson.fromJson(data.toString(), Kabupaten[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //KECAMATAN-1
    private void getKecamatan(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listKecamatan = new ArrayList<>();
                ArrayList<String> namaKecamatan = new ArrayList<>();
                Kecamatan[] kecamatans = processResultKecamatan(response);
                for(Kecamatan k : kecamatans){
                    listKecamatan.add(k);
                    namaKecamatan.add(k.getNama());
                }
                ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, namaKecamatan);
                adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKecamatan.setAdapter(adapterKecamatan);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    //KECAMATAN-2
    private Kecamatan[] processResultKecamatan(String content){
        try {
            JSONObject json = new JSONObject(content);
            JSONArray data = json.getJSONArray("daftar_kecamatan");
            return this.gson.fromJson(data.toString(), Kecamatan[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
