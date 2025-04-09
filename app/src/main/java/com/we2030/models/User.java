package com.we2030.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String email;
    private String name;
    private String passwordHash;
    private String selectedCountryId;
    private boolean notificationsEnabled;
    private String language;
    private long createdAt;
    private long lastLoginAt;

    public User(@NonNull String id, String email, String name, String passwordHash) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.notificationsEnabled = true;
        this.language = "fr";
        this.createdAt = System.currentTimeMillis();
        this.lastLoginAt = System.currentTimeMillis();
    }

    // Getters
    @NonNull
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPasswordHash() { return passwordHash; }
    public String getSelectedCountryId() { return selectedCountryId; }
    public boolean isNotificationsEnabled() { return notificationsEnabled; }
    public String getLanguage() { return language; }
    public long getCreatedAt() { return createdAt; }
    public long getLastLoginAt() { return lastLoginAt; }

    // Setters
    public void setId(@NonNull String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setSelectedCountryId(String selectedCountryId) { this.selectedCountryId = selectedCountryId; }
    public void setNotificationsEnabled(boolean notificationsEnabled) { this.notificationsEnabled = notificationsEnabled; }
    public void setLanguage(String language) { this.language = language; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setLastLoginAt(long lastLoginAt) { this.lastLoginAt = lastLoginAt; }
} 