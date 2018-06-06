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

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
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

        recyclerView = findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadProducts();

        Button resultsButton = findViewById(R.id.restart_btn);
        resultsButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ResultsActivity.class);
            i.putExtra("ITEMLIST", (Serializable) gamesList);
            startActivity(i);
        });

    }


    private void loadProducts() {
        String dataURL = "http://www.mocky.io/v2/5b0702b42f0000172bc61fe3";
        String dataURLBad = "http://www.mocky.io/v2/5b10c5fb2f0000770034f0ef";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                response -> {
                    try {
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

                        adapter = new GamesListAdapter(gamesList, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(MainActivity.this, error.getMessage() + "PLEASE CONTACT SUPPORT", Toast.LENGTH_LONG).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }


}
