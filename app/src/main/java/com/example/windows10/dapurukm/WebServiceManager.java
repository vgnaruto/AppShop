package com.example.windows10.dapurukm;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WebServiceManager {
    private RequestQueue queue;
    private Gson gson;
    private MainActivity ctx;

    public WebServiceManager(MainActivity ctx){
        gson = new Gson();
        this.ctx = ctx;
        queue = Volley.newRequestQueue(this.ctx);
    }
    public void postCost(String url, String namaAgent, int posisi, final String weight, final String idSeller, final String idBuyer){
        final int fPosisi = posisi;
        final String fNamaAgent = namaAgent;
        final String apiKey = ctx.getResources().getString(R.string.API_KEY);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO PROSES RESPONSE
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject obj = json.getJSONObject("rajaongkir");
                            JSONArray data = obj.getJSONArray("results");
                            Agent[] agent = gson.fromJson(data.toString(), Agent[].class);
                            ctx.setAgents(agent);
                            ctx.notifyCheckOutAdapter(fPosisi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "ERROR");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key",apiKey);
                params.put("origin", idBuyer);
                params.put("destination", idSeller);
                params.put("weight", weight);
                params.put("courier", fNamaAgent);
                return params;
            }
        };
        queue.add(postRequest);
    }

    //PROVINSI-1
    public void getProvinsi(String url, final int index) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Provinsi[] provinsis = processResultProvinsi(response);
                ArrayList<Provinsi> listProvinsi = new ArrayList<>();
                ArrayList<String> namaProvinsi = new ArrayList<>();
                for (int i = 0; i < provinsis.length; i++) {
                    listProvinsi.add(provinsis[i]);
                    namaProvinsi.add(provinsis[i].getProvince());
                }
                ctx.setProvinsi(listProvinsi,namaProvinsi);

                String idProvinsi = listProvinsi.get(index).getProvince_id();
                String apiKey = ctx.getResources().getString(R.string.API_KEY);

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
    public void getKabupaten(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> namaKabupaten = new ArrayList<>();
                ArrayList<Kabupaten> listKabupaten = new ArrayList<>();
                Kabupaten[] kabupatens = processResultKabupaten(response);
                for (Kabupaten k : kabupatens) {
                    listKabupaten.add(k);
                    namaKabupaten.add(k.getCity_name() + "(" + k.getType() + ")");
                }
                ctx.setKabupaten(listKabupaten,namaKabupaten);
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
}
