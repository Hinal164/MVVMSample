package com.example.mvvmsampleproject.di;


import com.example.mvvmsampleproject.viewModel.UserProfileViewModel;
import com.example.mvvmsampleproject.ViewModelFactory;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsUserListViewModel(UserProfileViewModel userListViewModel);


    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
