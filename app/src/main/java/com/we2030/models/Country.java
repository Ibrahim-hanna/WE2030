package com.we2030.models;

import java.io.Serializable;

public class Country implements Serializable {
    private String id;
    private String name;
    private int flagResourceId;
    private String timeZone;
    private String group;
    private String stage;
    private int points;
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;

    public Country(String id, String name, int flagResourceId, String timeZone, String group, String stage) {
        this.id = id;
        this.name = name;
        this.flagResourceId = flagResourceId;
        this.timeZone = timeZone;
        this.group = group;
        this.stage = stage;
        this.points = 0;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getFlagResourceId() { return flagResourceId; }
    public String getTimeZone() { return timeZone; }
    public String getGroup() { return group; }
    public String getStage() { return stage; }
    public int getPoints() { return points; }
    public int getMatchesPlayed() { return matchesPlayed; }
    public int getWins() { return wins; }
    public int getDraws() { return draws; }
    public int getLosses() { return losses; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
    public int getGoalDifference() { return goalsFor - goalsAgainst; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setPoints(int points) { this.points = points; }
    public void setMatchesPlayed(int matchesPlayed) { this.matchesPlayed = matchesPlayed; }
    public void setWins(int wins) { this.wins = wins; }
    public void setDraws(int draws) { this.draws = draws; }
    public void setLosses(int losses) { this.losses = losses; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    // Méthodes utilitaires
    public void updateStats(int goalsScored, int goalsConceded) {
        this.matchesPlayed++;
        this.goalsFor += goalsScored;
        this.goalsAgainst += goalsConceded;

        if (goalsScored > goalsConceded) {
            this.wins++;
            this.points += 3;
        } else if (goalsScored == goalsConceded) {
            this.draws++;
            this.points += 1;
        } else {
            this.losses++;
        }
    }
} 