package com.example.mvvmsampleproject.database;

import com.example.mvvmsampleproject.database.entity.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 11, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
     public abstract UserDao userDao();

}
