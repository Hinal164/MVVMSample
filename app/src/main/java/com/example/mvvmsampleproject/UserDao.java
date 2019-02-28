package com.example.mvvmsampleproject;

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

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> load(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUser(User... users);

    @Query("SELECT * FROM user ")
    LiveData<List<User>> getUsers();
}
