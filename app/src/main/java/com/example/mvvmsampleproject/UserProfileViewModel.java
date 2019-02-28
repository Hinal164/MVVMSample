package com.example.mvvmsampleproject;

import android.app.Application;
import android.graphics.Movie;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * This class that prepares the data for viewing in the UserProfileFragment and reacts to user interactions.
 * */
public class UserProfileViewModel extends AndroidViewModel {
    private LiveData<List<User>> userList;
    private UserRepository userRepo;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        //user = UserRepository.getInstance().getUser();
    }
    //UserProfileFragment is informed when the data is updated.
    // Furthermore, because this LiveData field is lifecycle aware, it automatically cleans up references after they're no longer needed.
   /* final private MutableLiveData<Request> request = new MutableLiveData();
    final private LiveData<Resource<GitHubResponse>> result = Transformations.switchMap(request, new Function<Request, LiveData<Resource<GitHubResponse>>>() {
        @Override
        public LiveData<Resource<GitHubResponse>> apply(Request input) {
            LiveData<Resource<GitHubResponse>> resourceLiveData = userRepo.browseRepo();
            return resourceLiveData;
        }
    });*/
    public LiveData<Resource<List<User>>> observeUserList() {
        /*
         * Now we got a situation, to observe a live data, observer must be a lifecycle owner.
         * ViewModel is not a lifecycle owner because it doesn't have any lifecycle which a live data needs.
         *
         * Methods that we have in Transformation class make the original lifecycle mechanism available here.
         * Thus, we are able to observe changes at this place and perform operations on that and
         * so our ui capable of having the updated data. */
        return Transformations.switchMap(userList, new Function<List<User>, LiveData<Resource<List<User>>>>() {
            @Override
            public LiveData<Resource<List<User>>> apply(List<User> list) {
                return userRepo.browseRepo();
            }
        });
    }

    public LiveData<List<User>> getUser() {
        return this.userList;
    }
}
