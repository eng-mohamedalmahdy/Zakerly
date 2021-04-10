package com.graduationproject.zakerly.intro.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;



public class SplashViewModelFactory  implements ViewModelProvider.Factory{

     private SplashRepository splashRepository ;
     private SplashFragment splashFragment;
    public SplashViewModelFactory( SplashRepository splashRepository,SplashFragment splashFragment) {
        this.splashRepository=splashRepository;
        this.splashFragment = splashFragment;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) { // create object from viewModel class

        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(splashRepository,splashFragment) ;
        }
        throw new IllegalArgumentException("Unable to construct viewmodel") ;
    }
}
