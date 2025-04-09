package com.we2030.model;

import java.util.Date;

public class Match {
    private String id;
    private Date date;
    private String teamId1;
    private String teamId2;
    private String stage;
    private int score1;
    private int score2;
    private String stadiumId;
    
    public Match() {
    }
    
    public Match(String id, Date date, String teamId1, String teamId2, String stage) {
        this.id = id;
        this.date = date;
        this.teamId1 = teamId1;
        this.teamId2 = teamId2;
        this.stage = stage;
    }
    
    // Getters
    public String getId() { return id; }
    public Date getDate() { return date; }
    public String getTeamId1() { return teamId1; }
    public String getTeamId2() { return teamId2; }
    public String getStage() { return stage; }
    public int getScore1() { return score1; }
    public int getScore2() { return score2; }
    public String getStadiumId() { return stadiumId; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setTeamId1(String teamId1) { this.teamId1 = teamId1; }
    public void setTeamId2(String teamId2) { this.teamId2 = teamId2; }
    public void setStage(String stage) { this.stage = stage; }
    public void setScore1(int score1) { this.score1 = score1; }
    public void setScore2(int score2) { this.score2 = score2; }
    public void setStadiumId(String stadiumId) { this.stadiumId = stadiumId; }
    
    public void updateScore(int score1, int score2) {
        this.score1 = score1;
        this.score2 = score2;
    }
    
    public String getStadiumDetails() {
        // TODO: Implémenter la récupération des détails du stade
        return null;
    }
} 