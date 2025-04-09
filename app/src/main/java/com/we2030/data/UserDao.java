package com.we2030.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.we2030.models.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserById(String userId);

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmailSync(String email);

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    LiveData<User> login(String email, String passwordHash);

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    User loginSync(String email, String passwordHash);

    @Query("UPDATE users SET lastLoginAt = :timestamp WHERE id = :userId")
    void updateLastLogin(String userId, long timestamp);

    @Query("UPDATE users SET selectedCountryId = :countryId WHERE id = :userId")
    void updateSelectedCountry(String userId, String countryId);

    @Query("UPDATE users SET notificationsEnabled = :enabled WHERE id = :userId")
    void updateNotificationsEnabled(String userId, boolean enabled);

    @Query("UPDATE users SET language = :language WHERE id = :userId")
    void updateLanguage(String userId, String language);
} 