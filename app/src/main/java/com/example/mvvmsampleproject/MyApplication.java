package com.example.mvvmsampleproject;

import android.app.Application;

public class MyApplication extends Application {
    private static AppComponent appComponent;


    public static AppComponent getAppComponent() {
        return appComponent;
    }



    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
               // .apiModule(new ApiModule())
               // .networkModule(new NetModule("https://api.github.com"))
               // .databaseModule(new DaoModule(this))
              //  .repositoryModule(new RepositoryModule())
                .build();

    }
}
