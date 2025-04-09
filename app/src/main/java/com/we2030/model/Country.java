package com.we2030.model;

public class Country {
    private String id;
    private String name;
    private int flagResourceId;
    private String timeZone;
    private String group;
    private String stage;

    public Country(String id, String name, int flagResourceId, String timeZone, String group, String stage) {
        this.id = id;
        this.name = name;
        this.flagResourceId = flagResourceId;
        this.timeZone = timeZone;
        this.group = group;
        this.stage = stage;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getFlagResourceId() { return flagResourceId; }
    public String getTimeZone() { return timeZone; }
    public String getGroup() { return group; }
    public String getStage() { return stage; }
} 