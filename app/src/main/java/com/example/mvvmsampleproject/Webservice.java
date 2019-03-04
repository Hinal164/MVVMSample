package com.example.mvvmsampleproject;

import com.example.mvvmsampleproject.database.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Webservice {

    @GET("/posts")
    Call<List<User>> getUser();
}
