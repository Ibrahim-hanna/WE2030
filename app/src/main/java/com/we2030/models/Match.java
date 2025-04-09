package com.we2030.models;

import java.util.Date;

public class Match {
    private String id;
    private String stage;
    private String date;
    private String time;
    private String team1;
    private String team2;
    private String stadium;
    private int team1Flag;
    private int team2Flag;
    private int team1Score;
    private int team2Score;
    private String status; // "À venir", "En cours", "Terminé"

    public Match(String id, String stage, String date, String time, 
                String team1, String team2, String stadium, 
                int team1Flag, int team2Flag) {
        this.id = id;
        this.stage = stage;
        this.date = date;
        this.time = time;
        this.team1 = team1;
        this.team2 = team2;
        this.stadium = stadium;
        this.team1Flag = team1Flag;
        this.team2Flag = team2Flag;
        this.team1Score = 0;
        this.team2Score = 0;
        this.status = "À venir";
    }

    // Getters
    public String getId() { return id; }
    public String getStage() { return stage; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public String getStadium() { return stadium; }
    public int getTeam1Flag() { return team1Flag; }
    public int getTeam2Flag() { return team2Flag; }
    public int getTeam1Score() { return team1Score; }
    public int getTeam2Score() { return team2Score; }
    public String getStatus() { return status; }

    // Setters
    public void setTeam1Score(int score) { this.team1Score = score; }
    public void setTeam2Score(int score) { this.team2Score = score; }
    public void setStatus(String status) { this.status = status; }
} 