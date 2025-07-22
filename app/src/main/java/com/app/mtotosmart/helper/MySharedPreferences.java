package com.app.mtotosmart.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.mtotosmart.model.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MySharedPreferences {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    // save profile
    public void setProfile(Profile pf){
        preferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pf);
        editor.putString("profile", json).apply();
    }

    public Profile getProfile(){
        preferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("profile", null);
        Type type = new TypeToken<Profile>() {}.getType();
        return gson.fromJson(json, type);
    }
}
