package com.example.mvvmsampleproject;

import android.app.Application;
import android.graphics.Movie;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * This class that prepares the data for viewing in the UserProfileFragment and reacts to user interactions.
 * */
public class UserProfileViewModel extends ViewModel {
    private LiveData<List<User>> userList;
    private UserRepository userRepo;

  /*  public UserProfileViewModel(@NonNull Application application) {
        super(application);
        //user = UserRepository.getInstance().getUser();
    }*/
    //UserProfileFragment is informed when the data is updated.
    // Furthermore, because this LiveData field is lifecycle aware, it automatically cleans up references after they're no longer needed.


    final private MutableLiveData<List<User>> request = new MutableLiveData();
    final private LiveData<Resource<List<User>>> result = Transformations.switchMap(request, new Function<List<User>, LiveData<Resource<List<User>>>>() {
        @Override
        public LiveData<Resource<List<User>>> apply(List<User> input) {
            LiveData<Resource<List<User>>> resourceLiveData = userRepo.browseRepo();
            return resourceLiveData;
        }

    });
    @Inject
    public void setRepository(UserRepository repository) {
        this.userRepo = repository;
    }

    public static UserProfileViewModel create(FragmentActivity fragment) {
        UserProfileViewModel viewModel = ViewModelProviders.of(fragment).get(UserProfileViewModel.class);
        return viewModel;
    }

    public LiveData<Resource<List<User>>> getResult() {
        return result;
    }


    public LiveData<List<User>> getUser() {
        return this.userList;
    }
}
