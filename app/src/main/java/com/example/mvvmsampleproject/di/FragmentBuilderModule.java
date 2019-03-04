package com.example.mvvmsampleproject.di;

import com.example.mvvmsampleproject.view.UserFragment;
import com.example.mvvmsampleproject.view.UserProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract UserProfileFragment contributeUserProfileFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

}
