package com.graduationproject.zakerly.intro.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;



public class SplashViewModelFactory  implements ViewModelProvider.Factory{

     private SplashRepository splashRepository ;
     public SplashViewModelFactory( SplashRepository splashRepository) {
        this.splashRepository=splashRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) { // create object from viewModel class

        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(splashRepository) ;
        }
        throw new IllegalArgumentException("Unable to construct viewmodel") ;
    }


}
