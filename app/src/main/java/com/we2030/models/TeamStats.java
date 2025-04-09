package com.we2030.models;

public class TeamStats {
    private String teamName;
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;

    public TeamStats(String teamName, int matchesPlayed, int wins, int draws, int losses) {
        this.teamName = teamName;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        
        // Initialiser les buts (à titre d'exemple)
        switch(teamName) {
            case "Maroc":
                this.goalsFor = 4;
                this.goalsAgainst = 2;
                break;
            case "Espagne":
                this.goalsFor = 3;
                this.goalsAgainst = 2;
                break;
            case "Portugal":
                this.goalsFor = 2;
                this.goalsAgainst = 2;
                break;
            case "France":
                this.goalsFor = 1;
                this.goalsAgainst = 3;
                break;
            case "Argentine":
                this.goalsFor = 5;
                this.goalsAgainst = 0;
                break;
            case "Brésil":
                this.goalsFor = 3;
                this.goalsAgainst = 1;
                break;
            case "Angleterre":
                this.goalsFor = 1;
                this.goalsAgainst = 2;
                break;
            case "Allemagne":
                this.goalsFor = 0;
                this.goalsAgainst = 3;
                break;
            default:
                this.goalsFor = 0;
                this.goalsAgainst = 0;
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public int getPoints() {
        return (wins * 3) + draws;
    }
} 