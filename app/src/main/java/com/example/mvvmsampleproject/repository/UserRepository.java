package com.example.mvvmsampleproject.repository;

import com.example.mvvmsampleproject.NetworkBoundResource;
import com.example.mvvmsampleproject.Resource;
import com.example.mvvmsampleproject.Webservice;
import com.example.mvvmsampleproject.database.UserDao;
import com.example.mvvmsampleproject.database.entity.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import retrofit2.Call;

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

    private final UserDao userDao;
    private final Webservice webservice;

    @Inject
    UserRepository(UserDao userDao,Webservice webservice) {
        this.userDao = userDao;
        this.webservice = webservice;
    }

    /**
     * This method fetches the popular articles from the service.
     * Once the fetching is done the data is cached to local db so that the app can even work offline
     *
     * @return List of articles
     */
    public LiveData<Resource<List<User>>> loadUsers() {
        return new NetworkBoundResource<List<User>, List<User>>() {

            @Override
            protected void saveCallResult(List<User> item) {
                if(item!=null)
                    userDao.save(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<User> data) {
                return data == null || data.isEmpty() ;

            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return userDao.loadUsers();
            }

            @NonNull
            @Override
            protected Call<List<User>> createCall() {
                return webservice.getUser();
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<User>> loadOneUser(int id) {
        return new NetworkBoundResource<User , User>(){

            @Override
            protected void saveCallResult(User item) {

            }

            @Override
            protected boolean shouldFetch(User data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.load(id);
            }

            @NonNull
            @Override
            protected Call<User> createCall() {
                return null;
            }
        }.getAsLiveData();
    }
}
