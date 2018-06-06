package com.betting.tonis.betting_app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> gamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gamesList = new ArrayList<>();
        SharedPreferences settings = getSharedPreferences("X", MODE_PRIVATE);
        settings.edit().clear().apply();

        Intent i = getIntent();
        gamesList = (List<ListItem>) i.getSerializableExtra("ITEMLIST");
        loadProducts();



        recyclerView = findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button resultsButton = findViewById(R.id.restart_btn);
        resultsButton.setOnClickListener(v -> {
            Intent mIntent = new Intent(MainActivity.this, ResultsActivity.class);
            if (gamesList.stream().anyMatch(ListItem::isScoreSet)) {
                mIntent.putExtra("ITEMLIST", (Serializable) gamesList);
                startActivity(mIntent);
            } else {
                Toast.makeText(MainActivity.this, "You need to place bet before you can see results", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastActivity", getClass().getName());
        Gson gson = new Gson();
        String json = gson.toJson(gamesList);
        editor.putString("gamesList", json);
        editor.apply();
    }
    private void loadProducts() {
        String dataURL = "http://www.mocky.io/v2/5b0702b42f0000172bc61fe3";
        String dataURLBad = "http://www.mocky.io/v2/5b185f473000005a008737f7";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                response -> {
                    try {
                        if (gamesList.isEmpty()) {
                            JSONObject bets = new JSONObject(response);
                            JSONArray teams = bets.getJSONArray("matches");
                            Log.d("responsestring", response);
                            for (int i = 0; i < teams.length(); i++) {
                                JSONObject betsObject = teams.getJSONObject(i);
                                String team1Name = betsObject.getString("team1");
                                String team2Name = betsObject.getString("team2");

                                ListItem listItem = new ListItem(team1Name, team2Name);
                                gamesList.add(listItem);
                            }
                        }
                        adapter = new GamesListAdapter(gamesList, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(MainActivity.this, error.getMessage() + "PLEASE CONTACT SUPPORT", Toast.LENGTH_LONG).show());
        Request<?> request = Volley.newRequestQueue(this).add(stringRequest).setShouldCache(true);
        Cache.Entry ca = new Cache.Entry();
        try {
            ca.data = request.getBody();
            ca.ttl = System.currentTimeMillis() + 60 * 1000;
            ca.softTtl = System.currentTimeMillis() + 60 * 1000;
            request.setCacheEntry(ca);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }


}
