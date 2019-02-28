package com.example.mvvmsampleproject;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class UserRepository {

    /* private static UserRepository userRepository;
     private Webservice webservice;

     private UserRepository() {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Webservice.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         webservice = retrofit.create(Webservice.class);
     }

     public static synchronized UserRepository getInstance() {
         if (userRepository == null) {
             userRepository = new UserRepository();
         }
         return userRepository;
     }

     public LiveData<List<User>> getUser() {
         final MutableLiveData<List<User>> data = new MutableLiveData<>();
         webservice.getUser().enqueue(new Callback<List<User>>() {
             @Override
             public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                 data.setValue(response.body());
                 Log.d("user %s",data.toString());
             }

             @Override
             public void onFailure(Call<List<User>> call, Throwable t) {
                 Log.d("user %s","Failure");
             }
         });
         return data;
     }
 */
    private Webservice webservice;
    private UserDao userDao;
    private Executor executor;
    private UserDatabase userDatabase;

    @Inject
    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }
    public LiveData<Resource<List<User>>> browseRepo() {
        LiveData<Resource<List<User>>> liveData = new NetworkBoundResource<List<User>, List<User>>() {
            @Override
            protected void saveCallResult(@NonNull List<User> items) {
                User[] arr = new User[items.size()];
                items.toArray(arr);
                userDatabase.userDao().insertAllUser(arr);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<User> data) {
                return true;//let's always refresh to be up to date. data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return  userDatabase.userDao().getUsers();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<User>>> createCall() {
                LiveData<ApiResponse<List<User>>> response = webservice.getUser();
                return response;
            }
        }.getAsLiveData();

        return liveData;
    }

}
