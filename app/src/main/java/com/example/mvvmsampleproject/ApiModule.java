package com.example.mvvmsampleproject;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    public Webservice providesCatalogApi(Retrofit retrofit) {
        return retrofit.create(Webservice.class);
    }
}
