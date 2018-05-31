package com.betting.tonis.betting_app.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {


    private List<ListItem> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        listItems = new ArrayList<>();
        Intent i = getIntent();
        listItems = (List<ListItem>) i.getSerializableExtra("ITEMLIST");

        recyclerView = findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadProducts();

        Button restartButton = findViewById(R.id.restart_btn);
        restartButton.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MainActivity.class)));
    }

    private void loadProducts() {
        String dataURL = "http://www.mocky.io/v2/5b0f8a4f3000006f001150c1";
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
                }, error -> Toast.makeText(ResultsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
