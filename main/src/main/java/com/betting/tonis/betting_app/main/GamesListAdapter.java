package com.betting.tonis.betting_app.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.ViewHolder>{

    private List<ListItem> listItems;
    private Context context;

    public GamesListAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
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
        final ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getTeam1Name());
        holder.textViewDesc.setText(listItem.getTeam2Name());
        holder.teamOnePrediction.setText(String.valueOf(listItem.getTeam1pScore()));
        holder.teamTwoPrediction.setText(String.valueOf(listItem.getTeam2pScore()));

        holder.textViewHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","CLICKED ON: " + listItem );
                final int[] team1Score = {listItem.getTeam1pScore()};
                final int[] team2Score = {listItem.getTeam2pScore()};
                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                View mView = li.inflate(R.layout.dialog_bet, null);
                final TextView teamOneScore = (TextView) mView.findViewById(R.id.score1);
                final TextView teamTwoScore = (TextView) mView.findViewById(R.id.score2);
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
                addTeam1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        team1Score[0]++;
                        teamOneScore.setText(String.valueOf(team1Score[0]));
                    }
                });
                removeTeam1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        team1Score[0]--;
                        teamOneScore.setText(String.valueOf(team1Score[0]));
                    }
                });
                addTeam2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        team2Score[0]++;
                        teamTwoScore.setText(String.valueOf(team2Score[0]));
                    }
                });
                removeTeam2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        team2Score[0]--;
                        teamTwoScore.setText(String.valueOf(team2Score[0]));
                    }
                });
                submitScores.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listItem.setTeam1pScore(team1Score[0]);
                        listItem.setTeam2pScore(team2Score[0]);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView teamOnePrediction;
        public TextView teamTwoPrediction;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.team1Name);
            textViewDesc = (TextView) itemView.findViewById(R.id.team2Name);
            teamOnePrediction = itemView.findViewById(R.id.team1Prediction);
            teamTwoPrediction = itemView.findViewById(R.id.team2Prediction);
        }
    }


}
