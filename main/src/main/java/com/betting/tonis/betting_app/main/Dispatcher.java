package com.betting.tonis.betting_app.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Dispatcher extends Activity {

    Class<?> activityClass;
    private Object mGamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);

        try {
            activityClass = Class.forName(
                    prefs.getString("lastActivity", MainActivity.class.getName()));
        } catch (ClassNotFoundException ex) {
            activityClass = MainActivity.class;
        }

        loadData();
        Intent i = new Intent(this, activityClass);
        i.putExtra("ITEMLIST", (Serializable) mGamesList);
        startActivity(i);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("gamesList", null);
        Type type = new TypeToken<List<ListItem>>() {
        }.getType();
        mGamesList = gson.fromJson(json, type);
        if (mGamesList == null) {
            mGamesList = new ArrayList<>();
        }
    }
}
