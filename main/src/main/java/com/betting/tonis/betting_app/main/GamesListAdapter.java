package com.betting.tonis.betting_app.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.ViewHolder>{

    private final List<ListItem> gamesList;
    private final Context context;

    public GamesListAdapter(List<ListItem> gamesList, Context context) {
        this.gamesList = gamesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.games_list_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem listItem = gamesList.get(position);

        holder.team1Name.setText(listItem.getTeam1Name());
        holder.team2Name.setText(listItem.getTeam2Name());
        if (listItem.isScoreSet()) {
            holder.teamOnePrediction.setText(String.valueOf(listItem.getTeam1pScore()));
            holder.teamTwoPrediction.setText(String.valueOf(listItem.getTeam2pScore()));
        }

        holder.cardView.setOnClickListener(v -> {
            final int[] team1Score = {listItem.getTeam1pScore()};
            final int[] team2Score = {listItem.getTeam2pScore()};
            android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            LayoutInflater li = LayoutInflater.from(context);
            @SuppressLint("InflateParams") View mView = li.inflate(R.layout.dialog_bet, null);
            final TextView teamOneScore = mView.findViewById(R.id.score1);
            final TextView teamTwoScore = mView.findViewById(R.id.score2);
            TextView team1name = mView.findViewById(R.id.team1);
            TextView team2name = mView.findViewById(R.id.team2);
            ImageButton addTeam1 = mView.findViewById(R.id.add_team1);
            ImageButton addTeam2 = mView.findViewById(R.id.add_team2);
            ImageButton removeTeam1 = mView.findViewById(R.id.remove_team1);
            ImageButton removeTeam2 = mView.findViewById(R.id.remove_team2);
            Button submitScores = mView.findViewById(R.id.submit_button);
            team1name.setText(listItem.getTeam1Name());
            team2name.setText(listItem.getTeam2Name());
            teamOneScore.setText(String.valueOf(team1Score[0]));
            teamTwoScore.setText(String.valueOf(team2Score[0]));
            mBuilder.setView(mView);
            final android.support.v7.app.AlertDialog dialog = mBuilder.create();
            addTeam1.setOnClickListener(v1 -> addScore(team1Score, teamOneScore));
            removeTeam1.setOnClickListener(v12 -> removeScore(team1Score, teamOneScore));
            addTeam2.setOnClickListener(v13 -> addScore(team2Score, teamTwoScore));
            removeTeam2.setOnClickListener(v14 -> removeScore(team2Score, teamTwoScore));
            submitScores.setOnClickListener(v15 -> {
                listItem.setTeam1pScore(team1Score[0]);
                listItem.setTeam2pScore(team2Score[0]);
                listItem.setScoreset();
                notifyDataSetChanged();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void removeScore(int[] teamScore, TextView scoreTxtView) {
        if (teamScore[0] > 0) {
            teamScore[0]--;
        } else {
            Toast.makeText(context, "Score can not be nagative", Toast.LENGTH_LONG).show();
        }
        scoreTxtView.setText(String.valueOf(teamScore[0]));
    }

    private void addScore(int[] teamScore, TextView scoreTxtView) {
        teamScore[0]++;
        scoreTxtView.setText(String.valueOf(teamScore[0]));
    }


    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView team1Name;
        final TextView team2Name;
        final TextView teamOnePrediction;
        final TextView teamTwoPrediction;
        final CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            teamOnePrediction = itemView.findViewById(R.id.team1Prediction);
            teamTwoPrediction = itemView.findViewById(R.id.team2Prediction);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }


}
