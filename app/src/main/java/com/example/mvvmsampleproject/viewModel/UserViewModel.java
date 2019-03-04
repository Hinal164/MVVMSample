package com.example.mvvmsampleproject.viewModel;


import android.graphics.Movie;

import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
   /* private MutableLiveData<Integer> user;

    @Inject
    UserRepository userRepository;

    UserViewModel(UserRepository userRepository){
        this.userRepository=userRepository;
        user=new MutableLiveData<>();
    }


    public void loadUser(int id) {
       user.setValue(id);
    }

    public LiveData<Resource<User>> observeUser() {
        return Transformations.switchMap(user, newMovieId -> userRepository.loadOneUser(newMovieId));
    }*/
}
