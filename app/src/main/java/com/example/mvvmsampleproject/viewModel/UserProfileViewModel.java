package com.example.mvvmsampleproject.viewModel;

import com.example.mvvmsampleproject.Resource;
import com.example.mvvmsampleproject.database.entity.User;
import com.example.mvvmsampleproject.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * This class that prepares the data for viewing in the UserProfileFragment and reacts to user interactions.
 */
public class UserProfileViewModel extends ViewModel {

    private LiveData<Resource<List<User>>> userList;
    private MutableLiveData<Integer> user;

    @Inject
    UserRepository userRepository;

    @Inject
    UserProfileViewModel(UserRepository userRepository) {
        userList = userRepository.loadUsers();
        user = new MutableLiveData<>();
    }

    public LiveData<Resource<List<User>>> getUserList() {
        return userList;
    }

    public LiveData<Resource<User>> observeUser() {
        return Transformations.switchMap(user, userId -> userRepository.loadOneUser(userId));
    }

    public void loadUser(int id) {
        user.setValue(id);
    }
}
