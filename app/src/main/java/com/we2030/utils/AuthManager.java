package com.we2030.utils;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.we2030.data.AppDatabase;
import com.we2030.data.UserDao;
import com.we2030.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AuthManager {
    private static final String TAG = "AuthManager";
    private final Context context;
    private final UserDao userDao;
    private final UserPreferences userPreferences;
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public AuthManager(Context context) {
        this.context = context;
        this.userDao = AppDatabase.getDatabase(context).userDao();
        this.userPreferences = new UserPreferences(context);
        
        // Vérifier si un utilisateur est déjà connecté
        if (userPreferences.isLoggedIn()) {
            String userId = userPreferences.getUserId();
            String email = userPreferences.getUserEmail();
            String name = userPreferences.getUserName();
            User user = new User(userId, email, name, "");
            currentUser.postValue(user);
        }
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void register(String email, String name, String password) {
        executor.execute(() -> {
            try {
                // Vérifier si l'email existe déjà de manière synchrone
                User existingUser = userDao.getUserByEmailSync(email);
                if (existingUser != null) {
                    Log.e(TAG, "L'email existe déjà");
                    return;
                }

                // Créer un nouvel utilisateur
                String userId = UUID.randomUUID().toString();
                String passwordHash = hashPassword(password);
                User newUser = new User(userId, email, name, passwordHash);

                // Sauvegarder dans la base de données
                userDao.insert(newUser);

                // Sauvegarder dans les préférences
                userPreferences.saveUserInfo(userId, email, name);
                userPreferences.setLoggedIn(true);

                // Mettre à jour l'utilisateur courant
                currentUser.postValue(newUser);
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de l'inscription", e);
            }
        });
    }

    public void login(String email, String password) {
        executor.execute(() -> {
            try {
                String passwordHash = hashPassword(password);
                User user = userDao.loginSync(email, passwordHash);
                
                if (user != null) {
                    // Mettre à jour la date de dernière connexion
                    userDao.updateLastLogin(user.getId(), System.currentTimeMillis());

                    // Sauvegarder dans les préférences
                    userPreferences.saveUserInfo(user.getId(), user.getEmail(), user.getName());
                    userPreferences.setLoggedIn(true);

                    // Mettre à jour l'utilisateur courant sur le thread principal
                    currentUser.postValue(user);
                    Log.d(TAG, "Connexion réussie pour l'utilisateur: " + user.getEmail());
                } else {
                    Log.e(TAG, "Identifiants invalides pour l'email: " + email);
                    currentUser.postValue(null);
                }
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la connexion", e);
                currentUser.postValue(null);
            }
        });
    }

    public void logout() {
        executor.execute(() -> {
            try {
                userPreferences.logout();
                currentUser.postValue(null);
                Log.d(TAG, "Déconnexion réussie");
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la déconnexion", e);
            }
        });
    }

    public boolean isLoggedIn() {
        return userPreferences.isLoggedIn();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Erreur lors du hachage du mot de passe", e);
            return null;
        }
    }
} 