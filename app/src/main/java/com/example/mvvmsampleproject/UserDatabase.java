package com.example.mvvmsampleproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 11)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
