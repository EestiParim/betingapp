package com.betting.tonis.betting_app.main;

import java.io.Serializable;
class ListItem implements Serializable {
    private final String hash;
    private final String team1Name;
    private final String team2Name;
    private boolean scoreSet;

    private int team1pScore;
    private int team2pScore;
    private int team1aScore;
    private int team2aScore;

    public ListItem(String team1Name, String team2Name){
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.hash = String.valueOf((team1Name + team2Name).hashCode());
        this.scoreSet = false;
    }

    public boolean isScoreSet() {
        return scoreSet;
    }

    public void setScoreset() {
        scoreSet = true;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public int getTeam1aScore() {
        return team1aScore;
    }

    public int getTeam2aScore() {
        return team2aScore;
    }

    public int getTeam1pScore() {
        return team1pScore;
    }

    public int getTeam2pScore() {
        return team2pScore;
    }

    public void setTeam1pScore(int team1pScore) {
        this.team1pScore = team1pScore;
    }

    public void setTeam2pScore(int team2pScore) {
        this.team2pScore = team2pScore;
    }

    public void setTeam1aScore(int team1aScore) {
        this.team1aScore = team1aScore;
    }

    public void setTeam2aScore(int team2aScore) {
        this.team2aScore = team2aScore;
    }
    public String getHash(){
        return hash;
    }
}
