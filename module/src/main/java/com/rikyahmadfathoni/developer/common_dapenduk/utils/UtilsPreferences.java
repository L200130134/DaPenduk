package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;
import com.rikyahmadfathoni.developer.common_dapenduk.Constants;

public class UtilsPreferences {

    private SharedPreferences defaultPreferences;

    private String namePreferences;

    public UtilsPreferences() {
        initPreferences();
    }

    private UtilsPreferences(@Nullable String namePreferences) {
        this.namePreferences = namePreferences;
        initPreferences();
    }

    private void initPreferences() {
        defaultPreferences = BaseApplication.getInstance().getSharedPreferences(namePreferences != null
                ? namePreferences : Constants.DEFAULT_PREF_NAME, Context.MODE_PRIVATE);
    }

    public <T extends Parcelable> void setParcelable(String key, T object) {

        String value = new GsonBuilder().create().toJson(object);

        if (value != null) {

            SharedPreferences.Editor editor = defaultPreferences.edit();
            editor.apply();

            editor.putString(key, value).commit();
        }
    }

    public <T extends Parcelable> T getParcelable(String key, Class<T> mClass) {

        T parcelable = null;

        String value = defaultPreferences.getString(key, null);

        if (value != null) {

            parcelable = new GsonBuilder().create().fromJson(value, mClass);
        }

        return parcelable;
    }

    public void remove(String key) {

        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.apply();

        if (defaultPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public void clearAll() {
        try {
            SharedPreferences.Editor editorServices = defaultPreferences.edit();
            editorServices.clear();
            editorServices.apply();
            editorServices.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
