package com.betting.tonis.betting_app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String dataURL = "http://www.mocky.io/v2/5b0702b42f0000172bc61fe3";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItems = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.games_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadProducts();

        Button resultsButton = findViewById(R.id.results_btn);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));
            }
        });

    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject bets = new JSONObject(response);
                            JSONArray teams = bets.getJSONArray("matches");
                            Log.d("responsestring", response);
                            for (int i = 0; i < teams.length(); i++){
                                JSONObject betsObject = teams.getJSONObject(i);
                                String team1Name = betsObject.getString("team1");
                                String team2Name = betsObject.getString("team2");

                                ListItem listItem = new ListItem(team1Name, team2Name);
                                listItems.add(listItem);
                            }

                            adapter = new GamesListAdapter(listItems, MainActivity.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }


}
