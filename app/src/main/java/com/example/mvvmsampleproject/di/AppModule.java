package com.example.mvvmsampleproject.di;

import android.app.Application;

import com.example.mvvmsampleproject.repository.ApiConstants;
import com.example.mvvmsampleproject.database.UserDao;
import com.example.mvvmsampleproject.database.UserDatabase;
import com.example.mvvmsampleproject.Webservice;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module/*(includes = ViewModelModule.class)*/
public class AppModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
      //  okHttpClient.addInterceptor(new RequestInterceptor());
      //  okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    Webservice provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(Webservice.class);
    }

    @Provides
    @Singleton
    UserDatabase provideUserDatabase(Application application) {
        return Room.databaseBuilder(application, UserDatabase.class, "users.db").build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(UserDatabase userDatabase) {
        return userDatabase.userDao();
    }

}
