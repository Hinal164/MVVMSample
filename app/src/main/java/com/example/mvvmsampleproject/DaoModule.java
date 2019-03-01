package com.example.mvvmsampleproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Movie;

import java.util.List;

import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {

    Context context;

    public DaoModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public UserDatabase provideUserDatabase(Application app) {
        return Room.databaseBuilder(context,
                UserDatabase.class, "User_database").build();
    }

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

    @Database(entities = {User.class}, version = 1111, exportSchema = false)
    public static abstract class UserDatabase extends RoomDatabase {
        public abstract UserDao userDao ();
    }
}
