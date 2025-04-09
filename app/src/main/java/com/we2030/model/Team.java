package com.we2030.model;

import java.util.List;
import java.util.ArrayList;

public class Team {
    private String id;
    private String name;
    private String countryId;
    private String coach;
    private int ranking;
    private List<String> players;

    public Team() {
        players = new ArrayList<>();
    }

    public Team(String id, String name, String countryId) {
        this();
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCountryId() { return countryId; }
    public String getCoach() { return coach; }
    public int getRanking() { return ranking; }
    public List<String> getPlayers() { return players; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCountryId(String countryId) { this.countryId = countryId; }
    public void setCoach(String coach) { this.coach = coach; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public void addPlayer(String playerId) {
        if (!players.contains(playerId)) {
            players.add(playerId);
        }
    }

    public void removePlayer(String playerId) {
        players.remove(playerId);
    }
} 