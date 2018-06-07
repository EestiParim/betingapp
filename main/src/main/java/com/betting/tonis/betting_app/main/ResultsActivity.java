package com.betting.tonis.betting_app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity {


    private List<ListItem> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_results);

        listItems = new ArrayList<>();
        Intent i = getIntent();
        listItems = (List<ListItem>) i.getSerializableExtra("ITEMLIST");

        recyclerView = findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadResults();

        if ((bundle != null)
                && (bundle.getSerializable("ITEMLIST") != null)) {
            listItems = (List<ListItem>) bundle.getSerializable("ITEMLIST");
        }

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String matchesList = gson.toJson(listItems);
        prefsEditor.putString("MatchesList", matchesList);
        prefsEditor.apply();

        Button restartButton = findViewById(R.id.restart_btn);
        restartButton.setOnClickListener(v -> {
            Object mGamesList = new ArrayList<>();
            Intent mI = new Intent(ResultsActivity.this, MainActivity.class);
            mI.putExtra("ITEMLIST", (Serializable) mGamesList);
            startActivity(mI);
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
        String json = gson.toJson(listItems);
        editor.putString("gamesList", json);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("ITEMLIST", (Serializable) listItems);
    }

    private void loadResults() {
        String dataURL = "http://www.mocky.io/v2/5b0f8a4f3000006f001150c1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                response -> {
                    try {
                        JSONObject bets = new JSONObject(response);
                        JSONArray teams = bets.getJSONArray("matches");
                        for (int i = 0; i < teams.length(); i++) {
                            JSONObject betsObject = teams.getJSONObject(i);
                            String team1Name = betsObject.getString("team1");
                            String team2Name = betsObject.getString("team2");
                            int team1Score = (betsObject.getInt("team1_points"));
                            int team2Score = betsObject.getInt("team2_points");
                            String hash = String.valueOf((team1Name + team2Name).hashCode());


                            ListItem matchingBet = listItems.stream()
                                    .filter(p -> p.getHash().equals(hash))
                                    .findFirst()
                                    .get();
                            matchingBet.setTeam1aScore(team1Score);
                            matchingBet.setTeam2aScore(team2Score);
                        }

                        adapter = new ResultsAdapter(listItems, ResultsActivity.this);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(ResultsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
