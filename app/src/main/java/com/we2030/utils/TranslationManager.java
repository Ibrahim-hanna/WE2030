package com.we2030.utils;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;

public class TranslationManager {
    private static final String PREF_NAME = "TranslationPrefs";
    private static final String KEY_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "fr";
    private static final String BASE_URL = "https://translation.googleapis.com/language/translate/v2";
    
    private Context context;
    private SharedPreferences preferences;
    private OkHttpClient client;
    private String apiKey;
    
    public TranslationManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.client = new OkHttpClient();
        this.apiKey = "YOUR_API_KEY"; // Remplacez par votre clé API
    }
    
    public String getCurrentLanguage() {
        return preferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
    }
    
    public void setLanguage(String languageCode) {
        preferences.edit().putString(KEY_LANGUAGE, languageCode).apply();
    }
    
    public String translateText(String text, String targetLanguage) {
        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String url = String.format("%s?q=%s&target=%s&source=%s&key=%s",
                BASE_URL, encodedText, targetLanguage, getCurrentLanguage(), apiKey);
            
            Request request = new Request.Builder()
                .url(url)
                .build();
            
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Erreur de traduction: " + response);
                }
                
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONObject data = jsonResponse.getJSONObject("data");
                JSONObject translation = data.getJSONArray("translations").getJSONObject(0);
                
                return translation.getString("translatedText");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
    }
    
    public void translateAppContent(String targetLanguage) {
        // Implémentez la traduction de tout le contenu de l'application ici
        // Par exemple, les chaînes de caractères, les descriptions, etc.
    }
} 