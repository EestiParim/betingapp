package com.betting.tonis.betting_app.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private final List<ListItem> listItems;

    public ResultsAdapter(List<ListItem> listItems, Context context){
        this.listItems = listItems;
        Context context1 = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_list_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getTeam1Name());
        holder.textViewDesc.setText(listItem.getTeam2Name());
        holder.teamOnePrediction.setText(String.valueOf(listItem.getTeam1pScore()));
        holder.teamTwoPrediction.setText(String.valueOf(listItem.getTeam2pScore()));
        holder.teamOneActual.setText(String.valueOf(listItem.getTeam1aScore()));
        holder.teamTwoActual.setText(String.valueOf(listItem.getTeam2aScore()));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView teamOneActual;
        final TextView teamTwoActual;
        final TextView textViewHead;
        final TextView textViewDesc;
        final TextView teamOnePrediction;
        final TextView teamTwoPrediction;

        ViewHolder(View itemView) {
            super(itemView);
            textViewHead = itemView.findViewById(R.id.team1Name);
            textViewDesc = itemView.findViewById(R.id.team2Name);
            teamOnePrediction = itemView.findViewById(R.id.team1Prediction);
            teamTwoPrediction = itemView.findViewById(R.id.team2Prediction);
            teamOneActual = itemView.findViewById(R.id.team1Actual);
            teamTwoActual = itemView.findViewById(R.id.team2Actual);
        }
    }
}
