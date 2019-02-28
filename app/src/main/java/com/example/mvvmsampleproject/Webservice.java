package com.example.mvvmsampleproject;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {

    String BASE_URL = "https://jsonplaceholder.typicode.com";
    @GET("/posts")
    LiveData<ApiResponse<List<User>>> getUser();
}
