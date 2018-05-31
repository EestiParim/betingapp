package com.betting.tonis.betting_app.main;

public class ListItem {
    private String team1Name;
    private String team2Name;

    private int team1pScore;
    private int team2pScore;

    public ListItem(String team1Name, String team2Name){
        this.team1Name = team1Name;
        this.team2Name = team2Name;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
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
}
