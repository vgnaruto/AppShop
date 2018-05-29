package com.example.windows10.dapurukm;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ASUS on 4/4/2018.
 */

public class FileManager{

    private String sharedPrefFile = "com.example.windows10.dapurukm";
    private SharedPreferences mPreferences;
    private Gson gson;

    public FileManager(Context context){
        this.mPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void saveFile(List<String> pd) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("data", this.gson.toJson(pd));
        preferencesEditor.apply();
    }

    public ArrayList<String> loadData() {
        String result = mPreferences.getString("data", "");
        return this.gson.fromJson(result, new TypeToken<List<String>>(){}.getType());
    }
}
