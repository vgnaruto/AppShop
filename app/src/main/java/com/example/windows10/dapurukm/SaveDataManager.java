package com.example.windows10.dapurukm;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SaveDataManager {
    private String sharedPrefFile = "com.example.windows10.dapurukm";
    private SharedPreferences mPreferences;
    private Gson gson;

    public SaveDataManager(Context context){
        this.mPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
    }

    public void saveUser(User user){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        Gson gson = new Gson();
        preferencesEditor.putString("data_user", gson.toJson(user));
        preferencesEditor.apply();
    }

    public User loadUser(){
        Gson gson = new Gson();
        String token = mPreferences.getString("data_user","");
        if(!token.equals("")){
            User result = gson.fromJson(token, User.class);
            return result;
        }else{
            return null;
        }
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
