package com.example.mvvmsampleproject;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class, ApiModule.class, AppModule.class , DaoModule.class})
public interface AppComponent {

    void inject(UserProfileViewModel viewModelModule);

    void inject(MainActivity mainActivity);


}
