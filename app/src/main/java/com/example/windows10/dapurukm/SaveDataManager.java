package com.example.windows10.dapurukm;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SaveDataManager {
    private String sharedPrefFile = "com.example.windows10.dapurukm";
    private SharedPreferences mPreferences;

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
}
