package br.com.nanothings.wildmobile.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferenceManager {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Class<?> classType;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public PreferenceManager(Context context, Class<?> classType) {
        this.context = context;
        this.classType = classType;
    }

    public void setPreference(String key, Object object) {
        preferences = context.getSharedPreferences(key.toUpperCase(), 0);
        editor = preferences.edit();
        editor.putString(key, new Gson().toJson(object));
        editor.commit();
    }

    public Object getPreference(String key) {
        preferences = context.getSharedPreferences(key.toUpperCase(), 0);
        editor = preferences.edit();

        String json = preferences.getString(key, "");

        return new Gson().fromJson(json, classType);
    }

    public void clearPreferences() {
        editor = context.getSharedPreferences("AUTH", 0).edit();
        editor.clear();
        editor.commit();
    }
}
