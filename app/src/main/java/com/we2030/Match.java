package com.we2030;

public class Match {
    private String team1;
    private String team2;
    private String date;
    private String time;
    private String stadium;

    public Match(String team1, String team2, String date, String time, String stadium) {
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
        this.stadium = stadium;
    }

    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStadium() { return stadium; }
} 