package com.pcsahu.surakshasathi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferenceClass {


    private static final String USER_PREF = "user_todo";
    private static final String KEY_ARRAY_LIST = "myArrayList";
    private SharedPreferences appShared;
    private SharedPreferences.Editor prefsEditor;

    public SharedPreferenceClass(Context context){
        appShared = context.getSharedPreferences( USER_PREF , Activity.MODE_PRIVATE );
        this.prefsEditor = appShared.edit();

    }


    public String getValue_string(String key){
        return appShared.getString( key,"");
    }

    public void setValue_string(String key,String val){
        prefsEditor.putString( key,val ).commit();
    }


    public void saveArrayList(Context context, ArrayList<String> arrayList) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        editor.putString(KEY_ARRAY_LIST, json);
        editor.apply();
    }

    public ArrayList<String> getArrayList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString(KEY_ARRAY_LIST, "[]");

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }



    public void clear(){
        prefsEditor.clear().commit();
    }
}
