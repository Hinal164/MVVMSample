package com.example.mvvmsampleproject.di;

import android.app.Application;

import com.example.mvvmsampleproject.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = { AppModule.class, AndroidInjectionModule.class,
        ActivityBuilderModule.class })
public interface AppComponent {

    void inject(MyApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
