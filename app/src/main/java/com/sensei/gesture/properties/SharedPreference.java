package com.sensei.gesture.properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SharedPreference(Context context, String preference_name) {
        this._context = context;
        pref = _context.getSharedPreferences(preference_name, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void save(String Gesture, String actionString) {
        editor.putString(Gesture, actionString);
        editor.commit();
    }

    public String getData(String Gesture) {
        return pref.getString(Gesture, "");
    }

    private void saveToSharedPreference(String Gesture, ArrayList<Properties> actions) {
        //convert ArrayList object to String by Gson
        Gson gson = new Gson();
        String json = gson.toJson(actions);
        //save to shared preference
        save(Gesture, json);
    }

    /**
     * Retrieving data from sharepref
     */
    private void retrieveFromSharedPreference(String key) {
        //retrieve data from shared preference
        String json = getData(key);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Properties>>(){}.getType();
        List<Properties> g = gson.fromJson(json, type);
        if (g == null) {
            g = new ArrayList<>();
        }
    }
}


