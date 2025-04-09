package com.we2030.model;

import java.util.Date;

public class User {
    private String id;
    private String email;
    private String password;
    private String username;
    private String preferredCountryId;
    private String authMethod;
    private Date lastLogin;
    
    public User() {
    }
    
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPreferredCountryId() { return preferredCountryId; }
    public String getAuthMethod() { return authMethod; }
    public Date getLastLogin() { return lastLogin; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setUsername(String username) { this.username = username; }
    public void setPreferredCountryId(String preferredCountryId) { this.preferredCountryId = preferredCountryId; }
    public void setAuthMethod(String authMethod) { this.authMethod = authMethod; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
    
    public boolean login(String credentials) {
        // TODO: Implémenter la logique d'authentification
        return false;
    }
    
    public void updateProfile(String data) {
        // TODO: Implémenter la mise à jour du profil
    }
} 