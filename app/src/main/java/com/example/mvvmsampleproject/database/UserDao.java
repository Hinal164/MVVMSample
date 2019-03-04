package com.example.mvvmsampleproject.database;

import com.example.mvvmsampleproject.database.entity.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<User> users);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> load(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUser(User... users);

    @Query("SELECT * FROM users ")
    LiveData<List<User>> loadUsers();
}
